#!/usr/bin/env python3
import argparse
import importlib.metadata as importlib_metadata
import re
from pathlib import Path

EXPECTED_SHA = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"
MAX_DISASM_INSNS = 160
KNOWN_CONTAINER_HELPERS = {
    0x004CE990: "SIZE",
    0x004CC180: "INDEX",
    0x004CD870: "BEGIN_MUT",
    0x004CD890: "BEGIN_CONST",
    0x004CD8B0: "END_MUT",
    0x004CD8D0: "END_CONST",
}
TARGET_RE = re.compile(r"^TARGET_RVA=0x(?P<rva>[0-9A-Fa-f]+) BYTES=(?P<bytes>[0-9A-Fa-f ]+)$")
SUSPICIOUS_MNEMONICS = {"int1", "retf", "lcall", "ljmp", "hlt", "in", "out", "insb", "insd", "outsb", "outsd"}


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


def classify_known_helper(target_rva, direct_calls, asm_texts):
    calls = set(int(x) for x in direct_calls)
    text = "\n".join(asm_texts).lower()
    if target_rva == 0x004CC180:
        return "INDEX_ACCESSOR" if 0x004CD870 in calls and "*4" in text else "ANCHOR_MISMATCH"
    if target_rva == 0x004CE990:
        need = {0x004CD8D0, 0x004CD890}
        has_div4 = "sar " in text and ", 2" in text
        return "SIZE_ACCESSOR" if need.issubset(calls) and has_div4 else "ANCHOR_MISMATCH"
    return "UNCLASSIFIED"


def suspicious_ratio(mnemonics, limit=24):
    head = mnemonics[:limit]
    if not head:
        return 1.0
    return sum(1 for x in head if x in SUSPICIOUS_MNEMONICS) / len(head)


def disassemble(blob, base_va, module_base):
    from capstone import Cs, CS_ARCH_X86, CS_MODE_32
    from capstone.x86 import X86_OP_IMM
    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    rows = []
    direct_calls = []
    mnemonics = []
    back_edges = 0
    indirect_calls = 0
    cmp_test = 0
    for i, insn in enumerate(md.disasm(blob, base_va)):
        if i >= MAX_DISASM_INSNS:
            break
        asm = f"{insn.mnemonic} {insn.op_str}".rstrip()
        rows.append((insn.address - module_base, insn.bytes.hex(" ").upper(), asm))
        m = insn.mnemonic.lower()
        mnemonics.append(m)
        if m in {"cmp", "test"}:
            cmp_test += 1
        if m.startswith("j") and insn.operands and insn.operands[0].type == X86_OP_IMM:
            if int(insn.operands[0].imm) < insn.address:
                back_edges += 1
        if m == "call":
            if insn.operands and insn.operands[0].type == X86_OP_IMM:
                va = int(insn.operands[0].imm) & 0xFFFFFFFF
                direct_calls.append(va - module_base if va >= module_base else va)
            else:
                indirect_calls += 1
    return rows, direct_calls, mnemonics, back_edges, indirect_calls, cmp_test


def main():
    ap = argparse.ArgumentParser(description="Decode bounded exact-target runtime helper captures for INVWIN V16R")
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
    heap = first_value(lines, "HEAP_SCAN=")
    priv = first_value(lines, "MEM_PRIVATE_SCAN=")
    write = first_value(lines, "MEMORY_WRITE=")
    targets = parse_targets(lines)
    if sha != EXPECTED_SHA or authority != "1" or exact != "YES" or heap != "NO" or priv != "NO" or write != "NO":
        raise SystemExit("V16R authority/safety gate failed")
    if not targets:
        raise SystemExit("V16R no captured targets")

    try:
        capstone_package = importlib_metadata.version("capstone")
    except importlib_metadata.PackageNotFoundError:
        raise SystemExit("capstone package not installed")

    out = []
    out.append("MODE=850_INVENTORY_INVWIN_RUNTIME_HELPER_DECODE_V16R")
    out.append(f"INPUT={src}")
    out.append(f"CAPSTONE_PACKAGE_VERSION={capstone_package}")
    out.append(f"CLIENT_SHA256={sha}")
    out.append("CLIENT_AUTHORITY=1")
    out.append(f"MODULE_BASE=0x{module_base:08X}")
    out.append(f"TARGET_COUNT={len(targets)}")
    out.append("SOURCE=RUNTIME_MEM_IMAGE_EXACT_TARGETS")
    out.append("EXACT_TARGET_ONLY=YES")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")
    out.append("")
    out.append("[TARGETS]")

    anchor_results = {}
    for rva, blob in targets:
        rows, calls, mnems, backs, indirect, cmps = disassemble(blob, module_base + rva, module_base)
        asm_texts = [x[2] for x in rows]
        anchor = classify_known_helper(rva, calls, asm_texts)
        if rva in (0x004CC180, 0x004CE990):
            anchor_results[rva] = anchor
        ratio = suspicious_ratio(mnems)
        container_hits = [(x, KNOWN_CONTAINER_HELPERS[x]) for x in calls if x in KNOWN_CONTAINER_HELPERS]
        out.append(f"TARGET_RVA=0x{rva:08X} BYTES={len(blob)} DECODED_INSNS={len(rows)} SUSPICIOUS_RATIO={ratio:.3f} ANCHOR_CLASS={anchor}")
        out.append(f"  SEMANTICS BACK_EDGES={backs} INDIRECT_CALLS={indirect} CMP_TEST={cmps} DIRECT_CALLS={len(calls)}")
        out.append("  DIRECT_TARGETS=" + (",".join(f"0x{x:08X}" for x in calls) if calls else "NONE"))
        out.append("  CONTAINER_HELPERS=" + (",".join(f"0x{x:08X}:{n}" for x,n in container_hits) if container_hits else "NONE"))
        for rrva, b, asm in rows:
            out.append(f"  DISASM RVA=0x{rrva:08X} BYTES={b} ASM={asm}")

    anchor_ok = (
        anchor_results.get(0x004CC180) == "INDEX_ACCESSOR" and
        anchor_results.get(0x004CE990) == "SIZE_ACCESSOR"
    )
    out.append("")
    out.append("[DECISION]")
    out.append("RUNTIME_ANCHOR_004CC180=" + anchor_results.get(0x004CC180, "MISSING"))
    out.append("RUNTIME_ANCHOR_004CE990=" + anchor_results.get(0x004CE990, "MISSING"))
    out.append("RUNTIME_CODE_SANITY=" + ("PASS" if anchor_ok else "FAIL"))
    out.append("STATUS=" + ("PASS_RUNTIME_HELPER_CAPTURE_VALIDATED" if anchor_ok else "BLOCKED_RUNTIME_HELPER_ANCHOR_MISMATCH"))
    out.append("AUTO_PROMOTION_ALLOWED=NO")
    out.append("ITEM_RECORD_TRAVERSAL_PROVEN=NO")
    out.append("OBJECT_ID_SEMANTICS_PROVEN=NO")
    out.append("FORMAL_WP5=NOT_YET")
    out.append("FORMAL_WP6=NOT_YET")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")
    dst.parent.mkdir(parents=True, exist_ok=True)
    dst.write_text("\n".join(out) + "\n", encoding="utf-8")
    if not anchor_ok:
        raise SystemExit("V16R known runtime helper anchor validation failed")


if __name__ == "__main__":
    main()
