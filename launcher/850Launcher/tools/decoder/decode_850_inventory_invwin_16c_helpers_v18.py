#!/usr/bin/env python3
import argparse
import importlib.metadata as importlib_metadata
import re
from pathlib import Path

EXPECTED_SHA = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"
EXPECTED_TARGETS = {
    0x0087E750,
    0x0087F5C0,
    0x0087F6E0,
    0x0084EFD0,
    0x00854A30,
    0x00854A40,
}
TARGET_RE = re.compile(r"^TARGET_RVA=0x(?P<rva>[0-9A-Fa-f]+) BYTES=(?P<bytes>[0-9A-Fa-f ]+)$")
MAX_DISASM_INSNS = 220


def first_value(lines, prefix):
    for line in lines:
        if line.startswith(prefix):
            return line[len(prefix):].strip()
    return ""


def parse_targets(lines):
    out = []
    for line in lines:
        m = TARGET_RE.match(line.strip())
        if m:
            out.append((int(m.group("rva"), 16), bytes.fromhex(m.group("bytes"))))
    return out


def disassemble(blob, base_va, module_base):
    from capstone import Cs, CS_ARCH_X86, CS_MODE_32
    from capstone.x86 import X86_OP_IMM, X86_OP_MEM, X86_REG_ECX

    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    rows = []
    direct_calls = []
    this_mem = []
    rets = []
    for i, insn in enumerate(md.disasm(blob, base_va)):
        if i >= MAX_DISASM_INSNS:
            break
        asm = f"{insn.mnemonic} {insn.op_str}".rstrip()
        rva = insn.address - module_base
        rows.append((rva, insn.bytes.hex(" ").upper(), asm))
        if insn.mnemonic.lower() == "call" and insn.operands and insn.operands[0].type == X86_OP_IMM:
            va = int(insn.operands[0].imm) & 0xFFFFFFFF
            direct_calls.append(va - module_base if va >= module_base else va)
        for op in insn.operands:
            if op.type == X86_OP_MEM and op.mem.base == X86_REG_ECX:
                this_mem.append((rva, int(op.mem.disp), asm))
        if insn.mnemonic.lower().startswith("ret"):
            rets.append(rva)
    return rows, direct_calls, this_mem, rets


def main():
    ap = argparse.ArgumentParser(description="Decode bounded INVWIN+0x16C begin/end helper lane V18")
    ap.add_argument("--input", required=True)
    ap.add_argument("--output", required=True)
    args = ap.parse_args()

    src = Path(args.input)
    dst = Path(args.output)
    lines = src.read_text(encoding="utf-8", errors="replace").splitlines()
    sha = first_value(lines, "CLIENT_SHA256=")
    authority = first_value(lines, "CLIENT_AUTHORITY=")
    module_base = int(first_value(lines, "MODULE_BASE="), 16)
    exact = first_value(lines, "EXACT_TARGET_ONLY=")
    image_only = first_value(lines, "RUNTIME_MEM_IMAGE_ONLY=")
    focus = first_value(lines, "FOCUS=")
    heap = first_value(lines, "HEAP_SCAN=")
    priv = first_value(lines, "MEM_PRIVATE_SCAN=")
    remote = first_value(lines, "REMOTE_CALL=")
    write = first_value(lines, "MEMORY_WRITE=")
    targets = parse_targets(lines)

    if sha != EXPECTED_SHA or authority != "1":
        raise SystemExit("V18 authority gate failed")
    if exact != "YES" or image_only != "YES" or heap != "NO" or priv != "NO" or remote != "NO" or write != "NO":
        raise SystemExit("V18 safety gate failed")
    if focus != "INVWIN_PLUS_0x16C_BEGIN_END_HELPERS":
        raise SystemExit(f"V18 focus mismatch: {focus}")
    got = {rva for rva, _ in targets}
    if got != EXPECTED_TARGETS:
        raise SystemExit(f"V18 exact-target mismatch missing={sorted(EXPECTED_TARGETS-got)} extra={sorted(got-EXPECTED_TARGETS)}")

    try:
        capstone_package = importlib_metadata.version("capstone")
    except importlib_metadata.PackageNotFoundError:
        raise SystemExit("capstone package not installed")

    out = []
    out += [
        "MODE=850_INVENTORY_INVWIN_16C_HELPER_DECODE_V18",
        f"INPUT={src}",
        f"CAPSTONE_PACKAGE_VERSION={capstone_package}",
        f"CLIENT_SHA256={sha}",
        "CLIENT_AUTHORITY=1",
        f"MODULE_BASE=0x{module_base:08X}",
        f"TARGET_COUNT={len(targets)}",
        "SOURCE=RUNTIME_MEM_IMAGE_EXACT_TARGETS",
        "EXACT_TARGET_ONLY=YES",
        "RUNTIME_MEM_IMAGE_ONLY=YES",
        "FOCUS=INVWIN_PLUS_0x16C_BEGIN_END_HELPERS",
        "HELPER_DEPTH=1",
        "HEAP_SCAN=NO",
        "MEM_PRIVATE_SCAN=NO",
        "REMOTE_CALL=NO",
        "MEMORY_WRITE=NO",
        "",
        "[TARGETS]",
    ]

    classifications = {}
    for rva, blob in targets:
        rows, calls, this_mem, rets = disassemble(blob, module_base + rva, module_base)
        if rva == 0x0087E750:
            cls = "CONTAINER_ADAPTER_CANDIDATE"
        elif rva == 0x0087F5C0:
            cls = "BEGIN_SOURCE_CANDIDATE"
        elif rva == 0x0087F6E0:
            cls = "END_SOURCE_CANDIDATE"
        elif rva == 0x0084EFD0:
            cls = "ITERATOR_ASSIGN_CANDIDATE"
        elif rva in (0x00854A30, 0x00854A40):
            cls = "ITERATOR_STORAGE_HELPER_CANDIDATE"
        else:
            cls = "UNCLASSIFIED"
        classifications[rva] = cls
        out.append(f"TARGET_RVA=0x{rva:08X} BYTES={len(blob)} DECODED_INSNS={len(rows)} CLASS={cls}")
        out.append("  DIRECT_TARGETS=" + (",".join(f"0x{x:08X}" for x in calls) if calls else "NONE"))
        if this_mem:
            out.append("  THIS_MEM=" + ";".join(f"RVA=0x{rrva:08X},DISP={disp:+#x},ASM={asm}" for rrva, disp, asm in this_mem))
        else:
            out.append("  THIS_MEM=NONE")
        out.append("  RETS=" + (",".join(f"0x{x:08X}" for x in rets) if rets else "NONE"))
        for rrva, b, asm in rows:
            out.append(f"  DISASM RVA=0x{rrva:08X} BYTES={b} ASM={asm}")

    out += [
        "",
        "[DECISION]",
        "STATUS=PASS_V18_HELPER_CAPTURE_VALIDATED",
        "AUTO_PROMOTION_ALLOWED=NO",
        "BEGIN_END_STORAGE_PROVEN=NO",
        "ELEMENT_IDENTITY_PROVEN=NO",
        "ITEM_RECORD_TRAVERSAL_PROVEN=NO",
        "OBJECT_ID_SEMANTICS_PROVEN=NO",
        "ITEM_ID_SEMANTICS_PROVEN=NO",
        "COUNT_SEMANTICS_PROVEN=NO",
        "FORMAL_WP5=NOT_YET",
        "FORMAL_WP6=NOT_YET",
        "HEAP_SCAN=NO",
        "MEM_PRIVATE_SCAN=NO",
        "REMOTE_CALL=NO",
        "MEMORY_WRITE=NO",
    ]

    dst.parent.mkdir(parents=True, exist_ok=True)
    dst.write_text("\n".join(out) + "\n", encoding="utf-8")


if __name__ == "__main__":
    main()
