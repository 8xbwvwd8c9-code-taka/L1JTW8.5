#!/usr/bin/env python3
import argparse
import importlib.metadata as metadata
import re
from collections import defaultdict
from pathlib import Path

import capstone
from capstone import Cs, CS_ARCH_X86, CS_MODE_32, CS_AC_READ, CS_AC_WRITE
from capstone.x86 import X86_OP_IMM, X86_OP_MEM, X86_OP_REG
from capstone.x86_const import (
    X86_REG_EAX, X86_REG_EBX, X86_REG_ECX, X86_REG_EDX,
    X86_REG_ESI, X86_REG_EDI, X86_REG_EBP, X86_REG_ESP,
)

CAPSTONE_PACKAGE_VERSION = metadata.version("capstone")
CAPSTONE_BINDING_VERSION = getattr(capstone, "__version__", "UNKNOWN")

METHOD_RE = re.compile(
    r"^METHOD SLOT=(\d+) VA=(0x[0-9A-Fa-f]+) RVA=(0x[0-9A-Fa-f]+) "
    r"PROTECT=(0x[0-9A-Fa-f]+) BYTES=(.*)$"
)

VOLATILE = {X86_REG_EAX, X86_REG_ECX, X86_REG_EDX}
REGS = {X86_REG_EAX, X86_REG_EBX, X86_REG_ECX, X86_REG_EDX, X86_REG_ESI, X86_REG_EDI, X86_REG_EBP, X86_REG_ESP}


def first_value(lines, prefix):
    for line in lines:
        if line.startswith(prefix):
            return line[len(prefix):].strip()
    return ""


def hx(s):
    try:
        return int(s, 16)
    except Exception:
        return None


def parse_methods(lines):
    out = []
    for raw in lines:
        m = METHOD_RE.match(raw.strip())
        if not m:
            continue
        slot, va, rva, protect, byte_text = m.groups()
        try:
            blob = bytes(int(x, 16) for x in byte_text.split())
        except ValueError:
            blob = b""
        out.append({
            "slot": int(slot),
            "va": hx(va),
            "rva": hx(rva),
            "protect": hx(protect),
            "blob": blob,
        })
    return out


def access_text(insn, idx, op):
    a = getattr(op, "access", 0)
    if a & CS_AC_READ and a & CS_AC_WRITE:
        return "RW"
    if a & CS_AC_WRITE:
        return "W"
    if a & CS_AC_READ:
        return "R"
    if idx == 0 and insn.mnemonic in {"mov", "add", "sub", "and", "or", "xor", "inc", "dec", "pop"}:
        return "W"
    return "R"


def method_decode(md, method, module_base, module_end):
    insns = []
    thunk_target_rva = None
    for insn in md.disasm(method["blob"], method["va"]):
        insns.append(insn)
        if insn.mnemonic == "jmp":
            if len(insn.operands) >= 1 and insn.operands[0].type == X86_OP_IMM:
                target = int(insn.operands[0].imm) & 0xFFFFFFFF
                if module_base <= target < module_end:
                    thunk_target_rva = target - module_base
            break
        if insn.mnemonic.startswith("ret") or insn.mnemonic in {"iret", "iretd"}:
            break
        if len(insns) >= 220:
            break

    reg_alias = {X86_REG_ECX: 0}
    stack_alias = {}
    accesses = []
    calls = []

    for insn in insns:
        # LEA derives an address; it is not a memory dereference and must not count as a field read.
        if insn.mnemonic != "lea":
            for idx, op in enumerate(insn.operands):
                if op.type != X86_OP_MEM:
                    continue
                base_reg = op.mem.base
                if base_reg in reg_alias and op.mem.index == 0:
                    owner_off = int(reg_alias[base_reg]) + int(op.mem.disp)
                    if 0 <= owner_off <= 0x800:
                        accesses.append({
                            "slot": method["slot"],
                            "method_rva": method["rva"],
                            "insn_rva": int(insn.address - module_base),
                            "offset": owner_off,
                            "access": access_text(insn, idx, op),
                            "asm": f"{insn.mnemonic} {insn.op_str}".strip(),
                        })

        # Record thiscall-style helper calls when ECX aliases owner/subobject.
        if insn.mnemonic == "call" and len(insn.operands) >= 1:
            this_off = reg_alias.get(X86_REG_ECX)
            target_rva = None
            if insn.operands[0].type == X86_OP_IMM:
                target = int(insn.operands[0].imm) & 0xFFFFFFFF
                if module_base <= target < module_end:
                    target_rva = target - module_base
            if this_off is not None and 0 <= this_off <= 0x800:
                calls.append({
                    "slot": method["slot"],
                    "method_rva": method["rva"],
                    "insn_rva": int(insn.address - module_base),
                    "this_offset": int(this_off),
                    "target_rva": target_rva,
                    "asm": f"{insn.mnemonic} {insn.op_str}".strip(),
                })

        ops = insn.operands
        handled_dst = False
        if insn.mnemonic == "mov" and len(ops) >= 2:
            dst, src = ops[0], ops[1]
            if dst.type == X86_OP_REG:
                handled_dst = True
                if src.type == X86_OP_REG and src.reg in reg_alias:
                    reg_alias[dst.reg] = reg_alias[src.reg]
                elif src.type == X86_OP_MEM and src.mem.base == X86_REG_EBP and src.mem.index == 0 and int(src.mem.disp) in stack_alias:
                    reg_alias[dst.reg] = stack_alias[int(src.mem.disp)]
                else:
                    reg_alias.pop(dst.reg, None)
            elif dst.type == X86_OP_MEM and dst.mem.base == X86_REG_EBP and dst.mem.index == 0:
                key = int(dst.mem.disp)
                if src.type == X86_OP_REG and src.reg in reg_alias:
                    stack_alias[key] = reg_alias[src.reg]
                else:
                    stack_alias.pop(key, None)
        elif insn.mnemonic == "lea" and len(ops) >= 2 and ops[0].type == X86_OP_REG and ops[1].type == X86_OP_MEM:
            handled_dst = True
            dst, src = ops[0], ops[1]
            if src.mem.index == 0 and src.mem.base in reg_alias:
                reg_alias[dst.reg] = int(reg_alias[src.mem.base]) + int(src.mem.disp)
            elif src.mem.base == X86_REG_EBP and src.mem.index == 0 and int(src.mem.disp) in stack_alias:
                reg_alias[dst.reg] = stack_alias[int(src.mem.disp)]
            else:
                reg_alias.pop(dst.reg, None)

        if not handled_dst and len(ops) >= 1 and ops[0].type == X86_OP_REG:
            dst_reg = ops[0].reg
            if dst_reg in REGS and insn.mnemonic not in {"cmp", "test", "push"}:
                reg_alias.pop(dst_reg, None)

        if insn.mnemonic == "call":
            for r in VOLATILE:
                reg_alias.pop(r, None)

    return accesses, calls, len(insns), thunk_target_rva


def main():
    ap = argparse.ArgumentParser(description="Decode bounded INVWIN vtable methods and owner-relative field accesses.")
    ap.add_argument("--input", required=True)
    ap.add_argument("--output", required=True)
    args = ap.parse_args()

    input_path = Path(args.input)
    output_path = Path(args.output)
    lines = input_path.read_text(encoding="utf-8", errors="replace").splitlines()

    client_sha = first_value(lines, "CLIENT_SHA256=")
    authority = first_value(lines, "CLIENT_AUTHORITY=")
    module_base = hx(first_value(lines, "MODULE_BASE="))
    module_size = hx(first_value(lines, "MODULE_SIZE="))
    memory_write = first_value(lines, "MEMORY_WRITE=")
    mem_private = first_value(lines, "MEM_PRIVATE_SCAN=")
    heap_scan = first_value(lines, "HEAP_SCAN=")
    vtable_rva = hx(first_value(lines, "INVWIN_VTABLE_RVA="))
    methods = parse_methods(lines)

    identity_ok = bool(
        client_sha == "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"
        and authority == "1"
        and module_base is not None
        and module_size is not None
        and vtable_rva == 0x00EDE180
        and memory_write == "NO"
        and mem_private == "NO"
        and heap_scan == "NO"
        and CAPSTONE_PACKAGE_VERSION == "5.0.9"
    )

    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    module_end = module_base + module_size if module_base is not None and module_size is not None else 0

    all_accesses = []
    all_calls = []
    thunk_rows = []
    unique = {}
    for m in methods:
        unique.setdefault(m["rva"], m)
    for rva, m in sorted(unique.items()):
        accesses, calls, _icount, thunk_target = method_decode(md, m, module_base, module_end)
        all_accesses.extend(accesses)
        all_calls.extend(calls)
        if thunk_target is not None:
            thunk_rows.append((m["slot"], m["rva"], thunk_target))

    by_offset = defaultdict(list)
    for a in all_accesses:
        by_offset[a["offset"]].append(a)
    calls_by_offset = defaultdict(list)
    for c in all_calls:
        calls_by_offset[c["this_offset"]].append(c)

    focus_offsets = sorted({o for o in set(by_offset) | set(calls_by_offset) if 0x180 <= o <= 0x280})
    mutator_offsets = sorted({a["offset"] for a in all_accesses if "W" in a["access"]})
    call_offsets = sorted(calls_by_offset)

    out = []
    out.append("MODE=850_INVENTORY_INVWIN_VTABLE_SEMANTIC_V8")
    out.append(f"INPUT={input_path}")
    out.append(f"CAPSTONE_PACKAGE_VERSION={CAPSTONE_PACKAGE_VERSION}")
    out.append(f"CAPSTONE_BINDING_VERSION={CAPSTONE_BINDING_VERSION}")
    out.append(f"CLIENT_SHA256={client_sha}")
    out.append(f"CLIENT_AUTHORITY={authority}")
    out.append(f"MODULE_BASE=0x{module_base:08X}" if module_base is not None else "MODULE_BASE=UNKNOWN")
    out.append(f"MODULE_SIZE=0x{module_size:X}" if module_size is not None else "MODULE_SIZE=UNKNOWN")
    out.append("INVWIN_VTABLE_RVA=0x00EDE180")
    out.append(f"IDENTITY_GATE={'PASS' if identity_ok else 'FAIL'}")
    out.append(f"VTABLE_SLOTS_CAPTURED={len(methods)}")
    out.append(f"UNIQUE_METHODS_DECODED={len(unique)}")
    out.append(f"UNRESOLVED_THUNK_METHODS={len(thunk_rows)}")
    out.append("RUNTIME_ATTACH=READ_ONLY_MODULE_IMAGE")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")
    out.append("")

    out.append("[OFFSET_SUMMARY]")
    for off in sorted(set(by_offset) | set(calls_by_offset)):
        acc = by_offset.get(off, [])
        calls = calls_by_offset.get(off, [])
        methods_touch = sorted({x["method_rva"] for x in acc} | {x["method_rva"] for x in calls})
        reads = sum(1 for x in acc if "R" in x["access"])
        writes = sum(1 for x in acc if "W" in x["access"])
        out.append(
            f"OFFSET=0x{off:03X} METHODS={len(methods_touch)} READS={reads} WRITES={writes} THISCALLS={len(calls)}"
        )

    out.append("")
    out.append("[FOCUS_0180_0280]")
    if not focus_offsets:
        out.append("NONE")
    for off in focus_offsets:
        for a in by_offset.get(off, []):
            out.append(
                f"ACCESS OFFSET=0x{off:03X} SLOT={a['slot']:02d} METHOD_RVA=0x{a['method_rva']:08X} "
                f"INSN_RVA=0x{a['insn_rva']:08X} ACCESS={a['access']} ASM={a['asm']}"
            )
        for c in calls_by_offset.get(off, []):
            target = f"0x{c['target_rva']:08X}" if c["target_rva"] is not None else "INDIRECT_OR_OUTSIDE"
            out.append(
                f"THISCALL OFFSET=0x{off:03X} SLOT={c['slot']:02d} METHOD_RVA=0x{c['method_rva']:08X} "
                f"INSN_RVA=0x{c['insn_rva']:08X} TARGET_RVA={target} ASM={c['asm']}"
            )

    out.append("")
    out.append("[THUNKS] ")
    if not thunk_rows:
        out.append("NONE")
    for slot, method_rva, target_rva in thunk_rows:
        out.append(f"THUNK SLOT={slot:02d} METHOD_RVA=0x{method_rva:08X} TARGET_RVA=0x{target_rva:08X}")

    out.append("")
    out.append("[DECISION]")
    access_220 = len(by_offset.get(0x220, []))
    call_220 = len(calls_by_offset.get(0x220, []))
    out.append(f"OFFSET_0x220_ACCESS_COUNT={access_220}")
    out.append(f"OFFSET_0x220_THISCALL_COUNT={call_220}")
    out.append("OFFSET_0x220_CODE_RELEVANT=" + ("YES" if (access_220 + call_220) > 0 else "NO"))
    out.append("FOCUS_OFFSETS=" + (",".join(f"0x{x:03X}" for x in focus_offsets) if focus_offsets else "NONE"))
    out.append("MUTATOR_OFFSETS=" + (",".join(f"0x{x:03X}" for x in mutator_offsets if 0x180 <= x <= 0x280) or "NONE"))
    out.append("THISCALL_SUBOBJECT_OFFSETS=" + (",".join(f"0x{x:03X}" for x in call_offsets if 0x180 <= x <= 0x280) or "NONE"))
    if not identity_ok:
        status = "REJECT_IDENTITY_GATE"
        nxt = "Fix only authority/capture inputs; do not widen scan scope."
    elif (access_220 + call_220) > 0:
        status = "PASS_INVWIN_0X220_CODE_RELEVANT"
        nxt = "Trace only methods touching +0x220 and their depth-1 helpers; require controlled semantic correlation before any backing-layout promotion."
    elif focus_offsets:
        status = "PASS_INVWIN_FOCUS_OFFSETS_FOUND"
        nxt = "Rank only code-relevant INVWIN offsets in 0x180..0x280; do not revive +0x220 without code evidence."
    elif thunk_rows:
        status = "INVWIN_VTABLE_THUNKS_REQUIRE_DEPTH1"
        nxt = "Resolve only the listed vtable thunk targets at depth 1; do not broaden to heap scans."
    else:
        status = "INVWIN_VTABLE_STORAGE_NOT_EXPOSED"
        nxt = "Use bounded call-site/static owner flow from INVWIN/GRID methods; do not broaden to heap scans."
    out.append(f"STATUS={status}")
    out.append("COLLECTION_LAYOUT_PROVEN=NO")
    out.append("FORMAL_WP5=NOT_YET")
    out.append("FORMAL_WP6=NOT_YET")
    out.append(f"NEXT={nxt}")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")

    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_text("\n".join(out) + "\n", encoding="utf-8")
    print("\n".join(out))
    print(f"OUTPUT={output_path}")


if __name__ == "__main__":
    main()
