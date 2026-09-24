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


def parse_targets(lines):
    out = []
    rx = re.compile(r"^TARGET NAME=([^ ]+) RVA=0x([0-9A-Fa-f]+) VA=0x([0-9A-Fa-f]+) BYTES=([0-9A-Fa-f ]+)$")
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


def decode_return(md, target, module_base, module_end):
    reg_alias = {X86_REG_ECX: 0}
    stack_alias = {}
    eax_owner_offset = None
    calls = []
    asm = []

    for idx, insn in enumerate(md.disasm(target["blob"], target["va"])):
        asm.append((insn.address - module_base, insn.mnemonic, insn.op_str))
        ops = insn.operands

        if insn.mnemonic == "call" and ops:
            trva = None
            if ops[0].type == X86_OP_IMM:
                va = int(ops[0].imm) & 0xFFFFFFFF
                if module_base <= va < module_end:
                    trva = va - module_base
            calls.append((insn.address - module_base, trva, f"{insn.mnemonic} {insn.op_str}".strip()))

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
                if dst.reg == X86_REG_EAX:
                    eax_owner_offset = reg_alias.get(X86_REG_EAX)
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
            if dst.reg == X86_REG_EAX:
                eax_owner_offset = reg_alias.get(X86_REG_EAX)
        elif insn.mnemonic in {"add", "sub"} and len(ops) >= 2 and ops[0].type == X86_OP_REG and ops[1].type == X86_OP_IMM:
            handled_dst = True
            r = ops[0].reg
            if r in reg_alias:
                delta = int(ops[1].imm)
                if insn.mnemonic == "sub":
                    delta = -delta
                reg_alias[r] = int(reg_alias[r]) + delta
            if r == X86_REG_EAX:
                eax_owner_offset = reg_alias.get(X86_REG_EAX)

        if not handled_dst and ops and ops[0].type == X86_OP_REG:
            r = ops[0].reg
            if insn.mnemonic not in {"cmp", "test", "push"}:
                reg_alias.pop(r, None)
                if r == X86_REG_EAX:
                    eax_owner_offset = None

        if insn.mnemonic == "call":
            for r in (X86_REG_EAX, X86_REG_ECX, X86_REG_EDX):
                reg_alias.pop(r, None)
            eax_owner_offset = None

        if insn.mnemonic.startswith("ret"):
            return {
                "return_offset": eax_owner_offset,
                "calls": calls,
                "asm": asm,
                "status": "RETURN_OWNER_ADDRESS" if eax_owner_offset is not None else "UNRESOLVED",
            }
        if idx >= 80:
            break

    return {"return_offset": None, "calls": calls, "asm": asm, "status": "UNRESOLVED"}


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

    targets = parse_targets(lines)
    if len(targets) != 2:
        raise SystemExit(f"expected 2 targets, got {len(targets)}")

    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    module_end = module_base + module_size

    decoded = {}
    report = [
        "MODE=850_INVENTORY_INVWIN_F4_BASE_ACCESSORS_V11",
        f"CLIENT_SHA256={sha}",
        "CLIENT_AUTHORITY=1",
        f"MODULE_BASE=0x{module_base:08X}",
        "SUBOBJECT_OWNER=INVWIN+0xF4",
        "RUNTIME_ATTACH=READ_ONLY_MODULE_IMAGE",
        "HEAP_SCAN=NO",
        "MEM_PRIVATE_SCAN=NO",
        "MEMORY_WRITE=NO",
    ]

    for t in targets:
        d = decode_return(md, t, module_base, module_end)
        decoded[t["name"]] = d
        report += ["", f"[TARGET_{t['name']}]", f"RVA=0x{t['rva']:08X}", f"RETURN_KIND={d['status']}"]
        report.append("RETURN_OWNER_OFFSET=" + (f"0x{d['return_offset']:X}" if d["return_offset"] is not None else "UNRESOLVED"))
        for rva, mnem, opstr in d["asm"]:
            report.append(f"ASM=0x{module_base+rva:08X} {mnem} {opstr}".rstrip())
        for rva, trva, text in d["calls"]:
            report.append(f"CALL RVA=0x{rva:08X} TARGET_RVA=" + (f"0x{trva:08X}" if trva is not None else "INDIRECT_OR_OUTSIDE") + f" ASM={text}")

    mut = decoded.get("BASE_MUT", {})
    const = decoded.get("BASE_CONST", {})
    mo = mut.get("return_offset")
    co = const.get("return_offset")
    agree = mo is not None and co is not None and mo == co

    report += ["", "[DECISION]"]
    report.append(f"BASE_ACCESSORS_AGREE={'YES' if agree else 'NO'}")
    report.append("BASE_FIELD_ADDRESS_OFFSET=" + (f"0x{mo:X}" if agree else "UNRESOLVED"))
    report.append("BEGIN_FIELD_OFFSET=" + (f"0x{mo:X}" if agree else "UNRESOLVED"))
    report.append("END_FIELD_OFFSET=" + (f"0x{mo+4:X}" if agree else "UNRESOLVED"))
    report.append("ELEMENT_STRIDE=4")
    report.append("SIZE_FORMULA=(END-BEGIN)/4")
    status = "PASS_F4_BEGIN_END_LAYOUT_PROVEN_STATIC" if agree else "F4_BASE_ACCESSOR_UNRESOLVED"
    report.append(f"STATUS={status}")
    report.append("COLLECTION_RUNTIME_DELTA_PROVEN=NO")
    report.append("FORMAL_WP5=NOT_YET")
    report.append("FORMAL_WP6=NOT_YET")
    report.append("NEXT=If PASS, perform only fixed-offset runtime reads of INVWIN+0xF4+BEGIN/END and controlled distinct-record add/remove; require symmetric +/-4 byte delta before promotion.")
    report.append("HEAP_SCAN=NO")
    report.append("MEM_PRIVATE_SCAN=NO")
    report.append("MEMORY_WRITE=NO")

    Path(args.output).write_text("\n".join(report) + "\n", encoding="utf-8")
    print("\n".join(report))


if __name__ == "__main__":
    main()
