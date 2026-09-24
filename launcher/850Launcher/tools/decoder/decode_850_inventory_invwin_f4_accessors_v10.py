#!/usr/bin/env python3
import argparse
import re
from pathlib import Path

from capstone import Cs, CS_ARCH_X86, CS_MODE_32
from capstone.x86 import X86_OP_MEM, X86_OP_REG, X86_OP_IMM, X86_REG_EAX, X86_REG_ECX, X86_REG_EBP

EXPECTED_SHA = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"
EXPECTED = {
    "BEGIN_MUT": 0x004CD870,
    "BEGIN_CONST": 0x004CD890,
    "END_MUT": 0x004CD8B0,
    "END_CONST": 0x004CD8D0,
}


def kv(lines, key):
    p = key + "="
    for line in lines:
        if line.startswith(p):
            return line[len(p):].strip()
    return ""


def parse(lines):
    rx = re.compile(r"^ACCESSOR NAME=([^ ]+) RVA=0x([0-9A-Fa-f]+) VA=0x([0-9A-Fa-f]+) BYTES=([0-9A-Fa-f ]+)$")
    out = {}
    for line in lines:
        m = rx.match(line.strip())
        if not m:
            continue
        out[m.group(1)] = {
            "rva": int(m.group(2), 16),
            "va": int(m.group(3), 16),
            "blob": bytes.fromhex(m.group(4)),
        }
    return out


def decode_accessor(md, rec):
    reg_alias = {X86_REG_ECX: 0}
    candidates = []
    insn_text = []
    for insn in md.disasm(rec["blob"], rec["va"]):
        insn_text.append(f"0x{insn.address:08X} {insn.mnemonic} {insn.op_str}".strip())
        ops = insn.operands
        # Typical accessor forms:
        #   mov eax, ecx ; add eax, imm ; ret
        #   lea eax, [ecx+imm] ; ret
        #   mov eax, [ecx+imm] ; ... (record but classify value-return separately)
        if insn.mnemonic == "lea" and len(ops) >= 2 and ops[0].type == X86_OP_REG and ops[0].reg == X86_REG_EAX and ops[1].type == X86_OP_MEM:
            src = ops[1]
            if src.mem.index == 0 and src.mem.base in reg_alias:
                candidates.append(("ADDRESS", int(reg_alias[src.mem.base]) + int(src.mem.disp), insn.address))
        if insn.mnemonic == "mov" and len(ops) >= 2 and ops[0].type == X86_OP_REG and ops[0].reg == X86_REG_EAX:
            src = ops[1]
            if src.type == X86_OP_REG and src.reg in reg_alias:
                reg_alias[X86_REG_EAX] = reg_alias[src.reg]
            elif src.type == X86_OP_MEM and src.mem.index == 0 and src.mem.base in reg_alias:
                candidates.append(("VALUE", int(reg_alias[src.mem.base]) + int(src.mem.disp), insn.address))
                reg_alias.pop(X86_REG_EAX, None)
            else:
                reg_alias.pop(X86_REG_EAX, None)
        elif insn.mnemonic in {"add", "sub"} and len(ops) >= 2 and ops[0].type == X86_OP_REG and ops[0].reg == X86_REG_EAX and ops[1].type == X86_OP_IMM and X86_REG_EAX in reg_alias:
            d = int(ops[1].imm)
            if insn.mnemonic == "sub":
                d = -d
            reg_alias[X86_REG_EAX] += d
        elif insn.mnemonic.startswith("ret"):
            if X86_REG_EAX in reg_alias:
                candidates.append(("ADDRESS", int(reg_alias[X86_REG_EAX]), insn.address))
            break
        if len(insn_text) >= 32:
            break
    # Prefer an address-return candidate nearest RET; otherwise report value-return.
    addr = [c for c in candidates if c[0] == "ADDRESS"]
    val = [c for c in candidates if c[0] == "VALUE"]
    chosen = addr[-1] if addr else (val[-1] if val else None)
    return chosen, insn_text


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--input", required=True)
    ap.add_argument("--output", required=True)
    args = ap.parse_args()

    lines = Path(args.input).read_text(encoding="utf-8", errors="replace").splitlines()
    sha = kv(lines, "CLIENT_SHA256")
    authority = kv(lines, "CLIENT_AUTHORITY")
    module_base = int(kv(lines, "MODULE_BASE"), 16)
    if sha != EXPECTED_SHA or authority != "1":
        raise SystemExit("identity gate failed")

    recs = parse(lines)
    missing = [n for n in EXPECTED if n not in recs]
    if missing:
        raise SystemExit("missing accessors: " + ",".join(missing))
    for n, rva in EXPECTED.items():
        if recs[n]["rva"] != rva:
            raise SystemExit(f"unexpected RVA for {n}: 0x{recs[n]['rva']:08X}")

    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    report = []
    report += [
        "MODE=850_INVENTORY_INVWIN_F4_ACCESSORS_V10",
        f"CLIENT_SHA256={sha}",
        "CLIENT_AUTHORITY=1",
        f"MODULE_BASE=0x{module_base:08X}",
        "SUBOBJECT_OWNER=INVWIN+0xF4",
        "RUNTIME_ATTACH=READ_ONLY_MODULE_IMAGE",
        "HEAP_SCAN=NO",
        "MEM_PRIVATE_SCAN=NO",
        "MEMORY_WRITE=NO",
        "",
    ]

    resolved = {}
    for name in EXPECTED:
        rec = recs[name]
        chosen, asm = decode_accessor(md, rec)
        report.append(f"[ACCESSOR_{name}]")
        report.append(f"RVA=0x{rec['rva']:08X}")
        if chosen is None:
            report.append("RETURN_KIND=UNRESOLVED")
            report.append("RETURN_FIELD_OFFSET=UNRESOLVED")
        else:
            kind, off, at = chosen
            resolved[name] = (kind, off)
            report.append(f"RETURN_KIND={kind}")
            report.append(f"RETURN_FIELD_OFFSET=0x{off:X}")
            report.append(f"EVIDENCE_VA=0x{at:08X}")
        for s in asm[:16]:
            report.append("ASM=" + s)
        report.append("")

    begin_offsets = {resolved[n][1] for n in ("BEGIN_MUT", "BEGIN_CONST") if n in resolved and resolved[n][0] == "ADDRESS"}
    end_offsets = {resolved[n][1] for n in ("END_MUT", "END_CONST") if n in resolved and resolved[n][0] == "ADDRESS"}
    begin_agree = len(begin_offsets) == 1
    end_agree = len(end_offsets) == 1
    begin_off = next(iter(begin_offsets)) if begin_agree else None
    end_off = next(iter(end_offsets)) if end_agree else None
    distinct = begin_off is not None and end_off is not None and begin_off != end_off

    report.append("[DECISION]")
    report.append(f"BEGIN_ACCESSORS_AGREE={'YES' if begin_agree else 'NO'}")
    report.append(f"END_ACCESSORS_AGREE={'YES' if end_agree else 'NO'}")
    report.append("BEGIN_FIELD_OFFSET=" + (f"0x{begin_off:X}" if begin_off is not None else "UNRESOLVED"))
    report.append("END_FIELD_OFFSET=" + (f"0x{end_off:X}" if end_off is not None else "UNRESOLVED"))
    report.append(f"BEGIN_END_DISTINCT={'YES' if distinct else 'NO'}")
    if begin_agree and end_agree and distinct:
        report.append("ELEMENT_STRIDE_FROM_INDEX_HELPER=4")
        report.append("SIZE_FORMULA=(END-BEGIN)/4")
        report.append("STATUS=PASS_F4_BEGIN_END_ACCESSORS_PROVEN")
        report.append("COLLECTION_SHAPE=STRONGLY_SUPPORTED_NOT_YET_RUNTIME_VALIDATED")
        report.append("NEXT=Read only INVWIN+0xF4+BEGIN_FIELD_OFFSET and END_FIELD_OFFSET in the restart-stable owner graph, then compare END-BEGIN across controlled distinct-record add/remove operations.")
    else:
        report.append("STATUS=ACCESSOR_LAYOUT_UNRESOLVED")
        report.append("COLLECTION_SHAPE=NOT_PROMOTED")
        report.append("NEXT=Do not widen scanning; inspect only unresolved accessor bodies/callees.")
    report.append("FORMAL_WP5=NOT_YET")
    report.append("FORMAL_WP6=NOT_YET")
    report.append("HEAP_SCAN=NO")
    report.append("MEM_PRIVATE_SCAN=NO")
    report.append("MEMORY_WRITE=NO")

    Path(args.output).write_text("\n".join(report) + "\n", encoding="utf-8")
    print("\n".join(report))

if __name__ == "__main__":
    main()
