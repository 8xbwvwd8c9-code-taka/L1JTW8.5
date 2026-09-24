#!/usr/bin/env python3
import argparse
import importlib.metadata as metadata
import re
from dataclasses import dataclass
from pathlib import Path

import capstone
from capstone import Cs, CS_ARCH_X86, CS_MODE_32, CS_AC_WRITE
from capstone.x86 import X86_OP_MEM, X86_OP_IMM, X86_OP_REG

CAPSTONE_PACKAGE_VERSION = metadata.version("capstone")
CAPSTONE_BINDING_VERSION = getattr(capstone, "__version__", "UNKNOWN")

REF_RE = re.compile(
    r"^REF RVA=(0x[0-9A-Fa-f]+) KIND=(\S+) IMM32=(\S+) "
    r"FUNC_START_RVA=(\S+) BYTES=(.*)$"
)


def first_value(lines, prefix):
    for line in lines:
        if line.startswith(prefix):
            return line[len(prefix):].strip()
    return ""


def parse_hex(text):
    try:
        return int(text, 16)
    except Exception:
        return None


@dataclass
class Ref:
    literal_rva: int
    kind: str
    imm32: str
    func_start_rva: str
    code: bytes


def parse_refs(lines):
    refs = []
    in_global = False
    for raw in lines:
        line = raw.strip()
        if line == "[GLOBAL_ABSOLUTE_XREFS]":
            in_global = True
            continue
        if in_global and line.startswith("["):
            break
        if not in_global:
            continue
        m = REF_RE.match(line)
        if not m:
            continue
        rva, kind, imm32, func_start, byte_text = m.groups()
        try:
            blob = bytes(int(x, 16) for x in byte_text.split())
        except ValueError:
            blob = b""
        refs.append(Ref(parse_hex(rva), kind, imm32, func_start, blob))
    return refs


def absolute_mem_target(op):
    if op.type != X86_OP_MEM:
        return None
    mem = op.mem
    if mem.base or mem.index:
        return None
    return int(mem.disp) & 0xFFFFFFFF


def classify_writer(md, writer):
    source = "UNKNOWN"
    writer_class = "TARGET_WRITE_OTHER"
    if writer.mnemonic == "mov" and len(writer.operands) >= 2:
        src = writer.operands[1]
        if src.type == X86_OP_IMM:
            value = int(src.imm) & 0xFFFFFFFF
            source = f"IMM32:0x{value:08X}"
            writer_class = "ZERO_CLEAR" if value == 0 else "NONZERO_IMMEDIATE_ASSIGNMENT"
        elif src.type == X86_OP_REG:
            reg = md.reg_name(src.reg).upper()
            source = f"REG:{reg}"
            writer_class = "REGISTER_ASSIGNMENT"
        else:
            source = "OTHER"
            writer_class = "OTHER_ASSIGNMENT"
    return writer_class, source


def decode_ref(md, module_base, root_va, ref):
    # V4B emits 48 bytes starting exactly 16 bytes before the 4-byte absolute-address literal.
    # That start may be in the middle of an x86 instruction. Try every possible instruction
    # boundary in the preceding 15 bytes and keep only instructions which both cover the
    # literal and write an absolute memory operand to ROOT_GLOBAL_VA.
    context_start = (module_base + ref.literal_rva - 16) & 0xFFFFFFFF
    literal_va = (module_base + ref.literal_rva) & 0xFFFFFFFF
    literal_off = literal_va - context_start

    aligned_candidates = []
    target_candidates = []
    lo = max(0, literal_off - 15)
    hi = min(len(ref.code), literal_off + 1)

    for start_off in range(lo, hi):
        start_va = context_start + start_off
        decoded = list(md.disasm(ref.code[start_off:], start_va, count=1))
        if len(decoded) != 1:
            continue
        insn = decoded[0]
        insn_end = insn.address + insn.size
        if not (insn.address <= literal_va < insn_end):
            continue
        aligned_candidates.append(insn)

        writes_target = False
        for op in insn.operands:
            access = getattr(op, "access", 0)
            if op.type != X86_OP_MEM or not (access & CS_AC_WRITE):
                continue
            if absolute_mem_target(op) == root_va:
                writes_target = True
                break
        if writes_target:
            target_candidates.append(insn)

    writer = target_candidates[0] if len(target_candidates) == 1 else None
    writer_class = "UNRESOLVED"
    source = "UNKNOWN"
    if writer is not None:
        writer_class, source = classify_writer(md, writer)

    # Build context from the chosen instruction boundary only; do not trust arbitrary-window
    # linear disassembly for promotion. This context is diagnostic, not authority.
    context_instructions = []
    writer_index = None
    if writer is not None:
        writer_off = writer.address - context_start
        prefix_start = max(0, writer_off - 20)
        for candidate_start in range(prefix_start, writer_off + 1):
            seq = list(md.disasm(ref.code[candidate_start:], context_start + candidate_start))
            for idx, insn in enumerate(seq):
                if insn.address == writer.address and insn.size == writer.size:
                    context_instructions = seq
                    writer_index = idx
                    break
            if writer_index is not None:
                break
        if writer_index is None:
            context_instructions = [writer]
            writer_index = 0

    return {
        "writer": writer,
        "writer_index": writer_index,
        "instructions": context_instructions,
        "context_start": context_start,
        "literal_va": literal_va,
        "source": source,
        "class": writer_class,
        "aligned_candidates": aligned_candidates,
        "target_candidates": target_candidates,
    }


def fmt_insn(insn):
    return f"0x{insn.address:08X}  {bytes(insn.bytes).hex(' ').upper():<30}  {insn.mnemonic} {insn.op_str}".rstrip()


def main():
    ap = argparse.ArgumentParser(description="Offline Capstone decoder for V4B root-global store candidates.")
    ap.add_argument("--input", required=True, help="850_inventory_root_global_xref_v4b.txt")
    ap.add_argument("--output", required=True, help="decoded writer report")
    args = ap.parse_args()

    input_path = Path(args.input)
    output_path = Path(args.output)
    lines = input_path.read_text(encoding="utf-8", errors="replace").splitlines()

    module_base = parse_hex(first_value(lines, "MODULE_BASE="))
    root_va = parse_hex(first_value(lines, "SEED_GLOBAL_VA="))
    root_rva = first_value(lines, "SEED_GLOBAL_RVA=")
    seed_value = first_value(lines, "SEED_GLOBAL_DWORD=")
    seed_class = first_value(lines, "SEED_GLOBAL_CLASS=")
    client_sha = first_value(lines, "CLIENT_SHA256=")
    authority = first_value(lines, "CLIENT_AUTHORITY=")
    memory_write = first_value(lines, "MEMORY_WRITE=")

    refs = parse_refs(lines)
    store_refs = [r for r in refs if r.kind.startswith("STORE_")]

    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True

    decoded = []
    if module_base is not None and root_va is not None:
        decoded = [(r, decode_ref(md, module_base, root_va, r)) for r in store_refs]

    zero = [(r, d) for r, d in decoded if d["class"] == "ZERO_CLEAR"]
    reg_assign = [(r, d) for r, d in decoded if d["class"] == "REGISTER_ASSIGNMENT"]
    imm_assign = [(r, d) for r, d in decoded if d["class"] == "NONZERO_IMMEDIATE_ASSIGNMENT"]
    unresolved = [(r, d) for r, d in decoded if d["writer"] is None]

    identity_ok = bool(
        module_base is not None
        and root_va is not None
        and client_sha
        and authority == "1"
        and memory_write == "NO"
        and CAPSTONE_PACKAGE_VERSION == "5.0.9"
    )

    out = []
    out.append("MODE=850_INVENTORY_V4B_WRITER_DECODER")
    out.append(f"INPUT={input_path}")
    out.append(f"CAPSTONE_PACKAGE_VERSION={CAPSTONE_PACKAGE_VERSION}")
    out.append(f"CAPSTONE_BINDING_VERSION={CAPSTONE_BINDING_VERSION}")
    out.append("ARCH=x86")
    out.append("MODE_BITS=32")
    out.append(f"CLIENT_SHA256={client_sha}")
    out.append(f"CLIENT_AUTHORITY={authority}")
    out.append(f"ROOT_GLOBAL_RVA={root_rva}")
    out.append(f"ROOT_GLOBAL_VA={'0x%08X' % root_va if root_va is not None else 'UNKNOWN'}")
    out.append(f"SEED_GLOBAL_DWORD={seed_value}")
    out.append(f"SEED_GLOBAL_CLASS={seed_class}")
    out.append(f"IDENTITY_GATE={'PASS' if identity_ok else 'FAIL'}")
    out.append("RUNTIME_TARGET_ATTACH=NO")
    out.append("HEAP_DEREFERENCE=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")
    out.append("")
    out.append("[STORE_WRITERS]")

    for n, (ref, d) in enumerate(decoded, 1):
        writer = d["writer"]
        out.append(
            f"WRITER N={n} REF_RVA=0x{ref.literal_rva:08X} KIND={ref.kind} "
            f"FUNC_START_RVA={ref.func_start_rva} CLASS={d['class']} SOURCE={d['source']} "
            f"ALIGNED_CANDIDATES={len(d['aligned_candidates'])} TARGET_CANDIDATES={len(d['target_candidates'])}"
        )
        if writer is None:
            out.append("  STATUS=NO_UNIQUE_DECODER_ALIGNED_TARGET_WRITER")
            continue
        writer_rva = (writer.address - module_base) & 0xFFFFFFFF
        post_rva = (writer.address + writer.size - module_base) & 0xFFFFFFFF
        out.append(
            f"  WRITER_RVA=0x{writer_rva:08X} POST_WRITE_EIP_RVA=0x{post_rva:08X} "
            f"ASM={writer.mnemonic} {writer.op_str} BYTES={bytes(writer.bytes).hex(' ').upper()}"
        )
        idx = d["writer_index"]
        if idx is not None:
            lo = max(0, idx - 5)
            hi = min(len(d["instructions"]), idx + 4)
            out.append("  CONTEXT_BEGIN")
            for i in range(lo, hi):
                prefix = ">" if i == idx else " "
                out.append(f"  {prefix} {fmt_insn(d['instructions'][i])}")
            out.append("  CONTEXT_END")

    out.append("")
    out.append("[SUMMARY]")
    out.append(f"GLOBAL_XREFS={len(refs)}")
    out.append(f"STORE_CANDIDATES={len(store_refs)}")
    out.append(f"DECODED_TARGET_WRITERS={len(decoded) - len(unresolved)}")
    out.append(f"REGISTER_ASSIGNMENTS={len(reg_assign)}")
    out.append(f"NONZERO_IMMEDIATE_ASSIGNMENTS={len(imm_assign)}")
    out.append(f"ZERO_CLEARS={len(zero)}")
    out.append(f"UNRESOLVED={len(unresolved)}")
    out.append("RAW_BYTE_OPCODE_PROMOTION=NO")
    out.append("DECODER_ALIGNED_PROMOTION=YES")

    seed_nonzero = seed_value.upper().startswith("0X") and seed_value.upper() != "0X00000000"
    if not identity_ok:
        status = "REJECT_IDENTITY_OR_DECODER_GATE"
        next_step = "Fix only report identity/version inputs; do not widen scan scope."
    elif len(zero) >= 1 and (len(reg_assign) + len(imm_assign)) >= 1:
        status = "PASS_STATIC_ASSIGNMENT_AND_ZERO_CLEAR"
        if seed_nonzero:
            next_step = "Use writer contexts to identify the unique construction assignment; current global is nonzero, but register-store value semantics still require correlation before owner promotion."
        else:
            next_step = "Static assignment and teardown clear exist; correlate construction writer semantics before owner promotion."
    elif len(zero) >= 1:
        status = "PASS_STATIC_ZERO_CLEAR_ONLY"
        next_step = "Need a decoder-aligned construction assignment to the same global; do not broaden memory scanning."
    else:
        status = "STATIC_OWNER_LIFECYCLE_NOT_YET"
        next_step = "Inspect only the decoder-aligned target writer contexts; do not rerun broad scans."

    out.append(f"STATUS={status}")
    out.append("OWNER_PROMOTION=NOT_YET")
    out.append("FORMAL_WP5=NOT_YET")
    out.append("FORMAL_WP6=NOT_YET")
    out.append(f"NEXT={next_step}")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")

    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_text("\n".join(out) + "\n", encoding="utf-8")
    print("\n".join(out))
    print(f"OUTPUT={output_path}")


if __name__ == "__main__":
    main()
