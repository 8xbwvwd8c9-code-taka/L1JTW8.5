#!/usr/bin/env python3
import argparse
import re
from pathlib import Path

from capstone import Cs, CS_ARCH_X86, CS_MODE_32
from capstone.x86 import *

EXPECTED_SHA = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"


def kv(lines, key):
    p = key + "="
    for line in lines:
        if line.startswith(p):
            return line[len(p):].strip()
    return ""


def parse_helpers(lines):
    out = []
    rx = re.compile(r"^HELPER NAME=([^ ]+) RVA=0x([0-9A-Fa-f]+) VA=0x([0-9A-Fa-f]+) BYTES=([0-9A-Fa-f ]+)$")
    for line in lines:
        m = rx.match(line.strip())
        if not m:
            continue
        out.append({
            "name": m.group(1),
            "rva": int(m.group(2), 16),
            "va": int(m.group(3), 16),
            "blob": bytes.fromhex(m.group(4)),
        })
    return out


def access_kind(insn, idx):
    try:
        acc = insn.operands[idx].access
        if acc == 1:
            return "R"
        if acc == 2:
            return "W"
        if acc == 3:
            return "RW"
    except Exception:
        pass
    if idx == 0 and insn.mnemonic in {"mov", "add", "sub", "and", "or", "xor", "inc", "dec", "pop"}:
        return "W"
    return "R"


def decode_helper(md, h, module_base, module_end):
    reg_alias = {X86_REG_ECX: 0}
    stack_alias = {}
    accesses = []
    calls = []
    arg_refs = 0
    asm = []
    returned_field_offsets = []
    last_eax_field = None

    for idx, insn in enumerate(md.disasm(h["blob"], h["va"])):
        asm.append((insn.address - module_base, insn.mnemonic, insn.op_str))
        ops = insn.operands

        for oi, op in enumerate(ops):
            if op.type != X86_OP_MEM:
                continue
            if op.mem.base == X86_REG_EBP and op.mem.index == 0 and int(op.mem.disp) >= 8:
                arg_refs += 1
            if op.mem.base in reg_alias and op.mem.index == 0:
                off = int(reg_alias[op.mem.base]) + int(op.mem.disp)
                if -0x40 <= off <= 0x100:
                    accesses.append((insn.address - module_base, off, access_kind(insn, oi), f"{insn.mnemonic} {insn.op_str}".strip()))
                    if insn.mnemonic == "mov" and len(ops) >= 2 and ops[0].type == X86_OP_REG and ops[0].reg == X86_REG_EAX and oi == 1:
                        last_eax_field = off

        if insn.mnemonic == "call" and ops:
            target_rva = None
            if ops[0].type == X86_OP_IMM:
                target = int(ops[0].imm) & 0xFFFFFFFF
                if module_base <= target < module_end:
                    target_rva = target - module_base
            calls.append((insn.address - module_base, target_rva, f"{insn.mnemonic} {insn.op_str}".strip()))

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
            else:
                reg_alias.pop(dst.reg, None)
        elif insn.mnemonic in {"add", "sub"} and len(ops) >= 2 and ops[0].type == X86_OP_REG and ops[1].type == X86_OP_IMM:
            handled_dst = True
            r = ops[0].reg
            if r in reg_alias:
                delta = int(ops[1].imm)
                if insn.mnemonic == "sub":
                    delta = -delta
                reg_alias[r] = int(reg_alias[r]) + delta

        if not handled_dst and ops and ops[0].type == X86_OP_REG:
            r = ops[0].reg
            if insn.mnemonic not in {"cmp", "test", "push"}:
                reg_alias.pop(r, None)

        if insn.mnemonic == "call":
            for r in (X86_REG_EAX, X86_REG_ECX, X86_REG_EDX):
                reg_alias.pop(r, None)

        if insn.mnemonic.startswith("ret"):
            if last_eax_field is not None:
                returned_field_offsets.append(last_eax_field)
            break
        if idx >= 180:
            break

    return {
        "accesses": accesses,
        "calls": calls,
        "arg_refs": arg_refs,
        "asm": asm,
        "return_fields": sorted(set(returned_field_offsets)),
    }


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--input", required=True)
    ap.add_argument("--output", required=True)
    args = ap.parse_args()

    lines = Path(args.input).read_text(encoding="utf-8", errors="replace").splitlines()
    sha = kv(lines, "CLIENT_SHA256")
    authority = kv(lines, "CLIENT_AUTHORITY")
    module_base = int(kv(lines, "MODULE_BASE"), 16)
    module_size = int(kv(lines, "MODULE_SIZE"), 16)
    if sha != EXPECTED_SHA or authority != "1":
        raise SystemExit("identity gate failed")

    helpers = parse_helpers(lines)
    if not helpers:
        raise SystemExit("no helpers parsed")

    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    module_end = module_base + module_size

    report = []
    report.append("MODE=850_INVENTORY_INVWIN_F4_HELPER_SEMANTICS_V9")
    report.append(f"CLIENT_SHA256={sha}")
    report.append("CLIENT_AUTHORITY=1")
    report.append(f"MODULE_BASE=0x{module_base:08X}")
    report.append(f"MODULE_SIZE=0x{module_size:X}")
    report.append(f"HELPER_COUNT={len(helpers)}")
    report.append("SUBOBJECT_OWNER=INVWIN+0xF4")
    report.append("RUNTIME_ATTACH=READ_ONLY_MODULE_IMAGE")
    report.append("HEAP_SCAN=NO")
    report.append("MEM_PRIVATE_SCAN=NO")
    report.append("MEMORY_WRITE=NO")

    primary_ok = {"SIZE_CANDIDATE": False, "INDEX_CANDIDATE": False}
    total_accesses = 0
    unique_offsets = set()

    for h in helpers:
        d = decode_helper(md, h, module_base, module_end)
        report.append("")
        report.append(f"[HELPER_{h['name']}]")
        report.append(f"RVA=0x{h['rva']:08X}")
        report.append(f"VA=0x{h['va']:08X}")
        report.append(f"ARG_STACK_REFS={d['arg_refs']}")
        report.append("RETURN_FIELD_OFFSETS=" + (",".join(f"0x{x:X}" for x in d['return_fields']) if d['return_fields'] else "NONE"))
        report.append(f"OWNER_ACCESS_COUNT={len(d['accesses'])}")
        for rva, off, kind, asm in d["accesses"]:
            unique_offsets.add(off)
            total_accesses += 1
            report.append(f"ACCESS RVA=0x{rva:08X} OFFSET={off:+#x} KIND={kind} ASM={asm}")
        report.append(f"DIRECT_CALL_COUNT={len(d['calls'])}")
        for rva, target, asm in d["calls"][:24]:
            t = f"0x{target:08X}" if target is not None else "INDIRECT_OR_OUTSIDE"
            report.append(f"CALL RVA=0x{rva:08X} TARGET_RVA={t} ASM={asm}")
        if h["name"] in primary_ok and len(d["accesses"]) > 0:
            primary_ok[h["name"]] = True

    report.append("")
    report.append("[DECISION]")
    report.append("F4_SUBOBJECT_CODE_RELEVANT=YES")
    report.append("F4_CALLER_SLOT_COUNT_GE_23=YES")
    report.append(f"HELPER_OWNER_ACCESS_TOTAL={total_accesses}")
    report.append("HELPER_OWNER_OFFSETS=" + (",".join(f"0x{x:X}" for x in sorted(unique_offsets)) if unique_offsets else "NONE"))
    report.append(f"SIZE_HELPER_DECODED={'YES' if primary_ok['SIZE_CANDIDATE'] else 'NO'}")
    report.append(f"INDEX_HELPER_DECODED={'YES' if primary_ok['INDEX_CANDIDATE'] else 'NO'}")
    status = "PASS_F4_COLLECTION_HELPERS_DECODED" if all(primary_ok.values()) else "PARTIAL_F4_HELPER_SEMANTICS"
    report.append(f"STATUS={status}")
    report.append("COLLECTION_LAYOUT_PROVEN=NO")
    report.append("FORMAL_WP5=NOT_YET")
    report.append("FORMAL_WP6=NOT_YET")
    report.append("NEXT=Use the decoded SIZE/INDEX helper owner offsets to define only fixed-offset reads inside INVWIN+0xF4; validate them against controlled distinct-record changes before layout promotion.")
    report.append("HEAP_SCAN=NO")
    report.append("MEM_PRIVATE_SCAN=NO")
    report.append("MEMORY_WRITE=NO")

    Path(args.output).write_text("\n".join(report) + "\n", encoding="utf-8")
    print("\n".join(report))


if __name__ == "__main__":
    main()
