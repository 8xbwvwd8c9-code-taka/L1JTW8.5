#!/usr/bin/env python3
import argparse
import hashlib
import importlib.metadata as metadata
from pathlib import Path

import capstone
from capstone import Cs, CS_ARCH_X86, CS_MODE_32, CS_AC_WRITE
from capstone.x86 import X86_OP_MEM, X86_OP_REG, X86_OP_IMM, X86_REG_EBP, X86_REG_ECX

CAPSTONE_PACKAGE_VERSION = metadata.version("capstone")
CAPSTONE_BINDING_VERSION = getattr(capstone, "__version__", "UNKNOWN")
TARGET_ASSIGN_RVA = 0x00C9A1E3
TARGET_LOAD_RVA = 0x00C9A1DD
ROOT_GLOBAL_VA = 0x016BCEE8
STACK_DISP = -0x3B8


def parse_meta(path):
    values = {}
    for raw in Path(path).read_text(encoding="utf-8", errors="replace").splitlines():
        if "=" in raw:
            k, v = raw.split("=", 1)
            values[k.strip()] = v.strip()
    return values


def hx(text):
    return int(text, 16)


def fmt(insn):
    return f"0x{insn.address:08X}  {bytes(insn.bytes).hex(' ').upper():<32}  {insn.mnemonic} {insn.op_str}".rstrip()


def is_stack_slot_mem(op, write_required=False):
    if op.type != X86_OP_MEM:
        return False
    if write_required and not (getattr(op, "access", 0) & CS_AC_WRITE):
        return False
    mem = op.mem
    return mem.base == X86_REG_EBP and mem.index == 0 and int(mem.disp) == STACK_DISP


def is_stack_slot_write(insn):
    return any(is_stack_slot_mem(op, True) for op in insn.operands)


def is_target_load(insn):
    if insn.mnemonic != "mov" or len(insn.operands) < 2:
        return False
    dst, src = insn.operands[0], insn.operands[1]
    return dst.type == X86_OP_REG and dst.reg == X86_REG_ECX and is_stack_slot_mem(src, False)


def absolute_mem_target(op):
    if op.type != X86_OP_MEM:
        return None
    mem = op.mem
    if mem.base or mem.index:
        return None
    return int(mem.disp) & 0xFFFFFFFF


def is_target_assign(insn):
    if insn.mnemonic != "mov" or len(insn.operands) < 2:
        return False
    dst, src = insn.operands[0], insn.operands[1]
    return absolute_mem_target(dst) == ROOT_GLOBAL_VA and src.type == X86_OP_REG and src.reg == X86_REG_ECX


def classify_source(md, insn):
    if insn.mnemonic == "mov" and len(insn.operands) >= 2:
        src = insn.operands[1]
        if src.type == X86_OP_REG:
            return "REG:" + md.reg_name(src.reg).upper()
        if src.type == X86_OP_IMM:
            return "IMM:0x%08X" % (int(src.imm) & 0xFFFFFFFF)
        if src.type == X86_OP_MEM:
            return "MEM:" + insn.op_str.split(",", 1)[1].strip() if "," in insn.op_str else "MEM"
    if insn.mnemonic == "lea" and len(insn.operands) >= 2:
        return "LEA_SOURCE"
    return "OTHER"


def find_function_stream(md, blob, start_va, target_load_va, target_assign_va):
    candidates = []
    seen_prologues = set()
    target_off = target_assign_va - start_va
    search_end = min(len(blob) - 3, max(0, target_off))
    for off in range(search_end + 1):
        hotpatch = off + 5 <= len(blob) and blob[off:off+5] == b"\x8B\xFF\x55\x8B\xEC"
        normal = blob[off:off+3] == b"\x55\x8B\xEC"
        if not (hotpatch or normal):
            continue
        prologue_off = off + 2 if hotpatch else off
        prologue_va = start_va + prologue_off
        if prologue_va in seen_prologues:
            continue
        seen_prologues.add(prologue_va)

        stream = list(md.disasm(blob[prologue_off:], prologue_va))
        by_addr = {i.address: i for i in stream}
        load = by_addr.get(target_load_va)
        assign = by_addr.get(target_assign_va)
        if load is None or assign is None:
            continue
        if not is_target_load(load) or not is_target_assign(assign):
            continue
        if load.address + load.size != assign.address:
            continue
        candidates.append((prologue_va, stream))
    # Nearest valid standard prologue is preferred, but all candidates are reported.
    candidates.sort(key=lambda x: x[0], reverse=True)
    return candidates


def previous_def(md, stream, idx, reg_name, max_back=12):
    reg_name = reg_name.upper()
    lo = max(0, idx - max_back)
    for j in range(idx - 1, lo - 1, -1):
        insn = stream[j]
        try:
            _, regs_write = insn.regs_access()
        except Exception:
            continue
        written = {md.reg_name(r).upper() for r in regs_write}
        if reg_name in written:
            return j, insn
    return None, None


def main():
    ap = argparse.ArgumentParser(description="Trace [EBP-0x3B8] provenance feeding the 850 ROOT global assignment.")
    ap.add_argument("--bin", required=True)
    ap.add_argument("--meta", required=True)
    ap.add_argument("--output", required=True)
    args = ap.parse_args()

    blob = Path(args.bin).read_bytes()
    meta = parse_meta(args.meta)
    start_va = hx(meta["WINDOW_START_VA"])
    module_base = hx(meta["MODULE_BASE"])
    target_load_va = module_base + TARGET_LOAD_RVA
    target_assign_va = module_base + TARGET_ASSIGN_RVA
    expected_sha = meta.get("WINDOW_SHA256", "").upper()
    actual_sha = hashlib.sha256(blob).hexdigest().upper()

    identity_ok = (
        meta.get("CLIENT_AUTHORITY") == "1"
        and meta.get("MEMORY_WRITE") == "NO"
        and meta.get("HEAP_SCAN") == "NO"
        and meta.get("MEM_PRIVATE_SCAN") == "NO"
        and expected_sha == actual_sha
        and CAPSTONE_PACKAGE_VERSION == "5.0.9"
    )

    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    functions = find_function_stream(md, blob, start_va, target_load_va, target_assign_va)

    out = []
    out.append("MODE=850_INVENTORY_ROOT_ASSIGNMENT_SOURCE_V5")
    out.append(f"CLIENT_SHA256={meta.get('CLIENT_SHA256','')}")
    out.append(f"CLIENT_AUTHORITY={meta.get('CLIENT_AUTHORITY','')}")
    out.append(f"PID={meta.get('PID','')}")
    out.append(f"PROCESS_START_UTC={meta.get('PROCESS_START_UTC','')}")
    out.append(f"MODULE_BASE={meta.get('MODULE_BASE','')}")
    out.append(f"WINDOW_START_RVA={meta.get('WINDOW_START_RVA','')}")
    out.append(f"WINDOW_END_RVA={meta.get('WINDOW_END_RVA','')}")
    out.append(f"WINDOW_SHA256={actual_sha}")
    out.append(f"CAPSTONE_PACKAGE_VERSION={CAPSTONE_PACKAGE_VERSION}")
    out.append(f"CAPSTONE_BINDING_VERSION={CAPSTONE_BINDING_VERSION}")
    out.append("TARGET_ASSIGN_RVA=0x00C9A1E3")
    out.append("TARGET_LOAD_RVA=0x00C9A1DD")
    out.append("TARGET_STACK_SLOT=[EBP-0x3B8]")
    out.append(f"IDENTITY_GATE={'PASS' if identity_ok else 'FAIL'}")
    out.append("RUNTIME_TARGET_ATTACH=READ_ONLY_MODULE_IMAGE_WINDOW")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")
    out.append("")

    out.append("[FUNCTION_CANDIDATES]")
    out.append(f"COUNT={len(functions)}")
    for n, (fva, stream) in enumerate(functions, 1):
        out.append(f"FUNCTION N={n} START_RVA=0x{fva-module_base:08X} INSTRUCTIONS={len(stream)}")

    chosen = functions[0] if functions else None
    writes = []
    if chosen:
        fva, stream = chosen
        assign_idx = next((i for i, x in enumerate(stream) if x.address == target_assign_va), None)
        load_idx = next((i for i, x in enumerate(stream) if x.address == target_load_va), None)
        if load_idx is not None:
            for idx, insn in enumerate(stream[:load_idx]):
                if is_stack_slot_write(insn):
                    writes.append((idx, insn))

        out.append("")
        out.append("[CHOSEN_FUNCTION]")
        out.append(f"START_RVA=0x{fva-module_base:08X}")
        if load_idx is not None:
            out.append("LOAD=" + fmt(stream[load_idx]))
        if assign_idx is not None:
            out.append("ASSIGN=" + fmt(stream[assign_idx]))

        out.append("")
        out.append("[STACK_SLOT_WRITES]")
        out.append(f"COUNT={len(writes)}")
        for n, (idx, insn) in enumerate(writes, 1):
            src = classify_source(md, insn)
            rva = insn.address - module_base
            out.append(f"WRITE N={n} RVA=0x{rva:08X} SOURCE={src} ASM={insn.mnemonic} {insn.op_str}")
            if src.startswith("REG:"):
                reg = src.split(":", 1)[1]
                j, defin = previous_def(md, stream, idx, reg)
                if defin is not None:
                    out.append(f"  PREV_REG_DEF_RVA=0x{defin.address-module_base:08X} ASM={defin.mnemonic} {defin.op_str}")
                    if j is not None and j > 0 and stream[j-1].mnemonic == "call":
                        out.append(f"  CALL_BEFORE_REG_DEF_RVA=0x{stream[j-1].address-module_base:08X} ASM={stream[j-1].mnemonic} {stream[j-1].op_str}")
            lo = max(0, idx - 4)
            hi = min(len(stream), idx + 4)
            out.append("  CONTEXT_BEGIN")
            for k in range(lo, hi):
                p = ">" if k == idx else " "
                out.append(f"  {p} {fmt(stream[k])}")
            out.append("  CONTEXT_END")
    else:
        out.append("")
        out.append("[STACK_SLOT_WRITES]")
        out.append("COUNT=0")

    out.append("")
    out.append("[DECISION]")
    if not identity_ok:
        status = "REJECT_IDENTITY_GATE"
        nxt = "Fix only authority/window inputs; do not widen scope."
    elif not functions:
        status = "NO_STANDARD_FUNCTION_CHAIN"
        nxt = "Use the x32dbg startup lane to capture the assignment source; do not broaden memory scanning."
    elif len(writes) == 0:
        status = "STACK_SOURCE_NOT_FOUND_IN_WINDOW"
        nxt = "Increase only the same module-code window if function start is inside the window; do not scan heap or MEM_PRIVATE."
    elif len(writes) == 1:
        status = "PASS_UNIQUE_STACK_SLOT_WRITE_CANDIDATE"
        nxt = "Adjudicate this single stack-slot writer against the startup x32dbg capture before owner promotion."
    else:
        status = "PASS_MULTIPLE_STACK_SLOT_WRITE_CANDIDATES"
        nxt = "Use control-flow/runtime startup evidence to select the executed stack-slot writer; do not infer dominance from textual order."
    out.append(f"STATUS={status}")
    out.append("OWNER_PROMOTION=NOT_YET")
    out.append("FORMAL_WP5=NOT_YET")
    out.append("FORMAL_WP6=NOT_YET")
    out.append(f"NEXT={nxt}")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")

    Path(args.output).write_text("\n".join(out) + "\n", encoding="utf-8")
    print("\n".join(out))
    print(f"OUTPUT={args.output}")


if __name__ == "__main__":
    main()
