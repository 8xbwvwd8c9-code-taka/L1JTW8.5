#!/usr/bin/env python3
import argparse
import importlib.metadata as importlib_metadata
import re
import struct
from dataclasses import dataclass, field
from pathlib import Path

TARGET_MODULES = {"lineage.exe", "chigamec.dll"}
SEND_NAMES = {"send", "sendto"}
MAX_XREFS = 48
MAX_CALLERS = 96
MAX_FUNC_BYTES = 512
MAX_INSNS = 220

@dataclass
class Xref:
    name: str
    iat_rva: int
    call_rva: int
    kind: str

@dataclass
class EvidenceFile:
    name: str = ""
    path: str = ""
    image_base: int = 0
    xrefs: list = field(default_factory=list)

@dataclass
class Section:
    name: str
    va: int
    vsize: int
    raw_ptr: int
    raw_size: int
    chars: int

    @property
    def executable(self):
        return (self.chars & 0x20000000) != 0

def parse_evidence(text):
    blocks = []
    cur = None
    for raw in text.splitlines():
        line = raw.strip()
        if line == "[FILE]":
            if cur is not None:
                blocks.append(cur)
            cur = EvidenceFile()
            continue
        if cur is None:
            continue
        if line.startswith("NAME="):
            cur.name = line[5:].strip()
        elif line.startswith("PATH="):
            cur.path = line[5:].strip()
        elif line.startswith("IMAGE_BASE="):
            try:
                cur.image_base = int(line.split("=", 1)[1], 16)
            except ValueError:
                cur.image_base = 0
        elif line.startswith("CORE_XREF "):
            m = re.search(
                r"NAME=(\S+)\s+IAT_RVA=0x([0-9A-Fa-f]+)\s+CALL_RVA=0x([0-9A-Fa-f]+)\s+KIND=(.+)$",
                line,
            )
            if m:
                cur.xrefs.append(
                    Xref(
                        m.group(1).lower(),
                        int(m.group(2), 16),
                        int(m.group(3), 16),
                        m.group(4).strip(),
                    )
                )
    if cur is not None:
        blocks.append(cur)
    return blocks

def parse_pe(data):
    if len(data) < 0x100 or data[:2] != b"MZ":
        raise ValueError("not a PE/MZ image")
    peoff = struct.unpack_from("<I", data, 0x3C)[0]
    if peoff + 24 > len(data) or data[peoff:peoff + 4] != b"PE\0\0":
        raise ValueError("invalid PE signature")
    num_sections = struct.unpack_from("<H", data, peoff + 6)[0]
    size_opt = struct.unpack_from("<H", data, peoff + 20)[0]
    opt = peoff + 24
    if opt + size_opt > len(data):
        raise ValueError("truncated optional header")
    magic = struct.unpack_from("<H", data, opt)[0]
    if magic == 0x10B:
        image_base = struct.unpack_from("<I", data, opt + 28)[0]
    elif magic == 0x20B:
        image_base = struct.unpack_from("<Q", data, opt + 24)[0]
    else:
        raise ValueError("unsupported PE optional header")
    sec_off = opt + size_opt
    sections = []
    for i in range(num_sections):
        p = sec_off + i * 40
        if p + 40 > len(data):
            break
        name = data[p:p + 8].split(b"\0", 1)[0].decode("ascii", "replace")
        vsize, va, raw_size, raw_ptr = struct.unpack_from("<IIII", data, p + 8)
        chars = struct.unpack_from("<I", data, p + 36)[0]
        sections.append(Section(name, va, vsize, raw_ptr, raw_size, chars))
    return image_base, sections

def rva_to_offset(rva, sections, data_len):
    for s in sections:
        span = max(s.vsize, s.raw_size)
        if s.va <= rva < s.va + span:
            off = s.raw_ptr + (rva - s.va)
            if 0 <= off < data_len:
                return off
    return None

def section_for_rva(rva, sections):
    for s in sections:
        if not s.executable:
            continue
        span = max(s.vsize, s.raw_size)
        if s.va <= rva < s.va + span:
            return s
    return None

def find_function_start(call_rva, data, sections):
    sec = section_for_rva(call_rva, sections)
    if sec is None:
        return call_rva, "NO_EXEC_SECTION"
    call_off = rva_to_offset(call_rva, sections, len(data))
    sec_off = rva_to_offset(sec.va, sections, len(data))
    if call_off is None or sec_off is None:
        return call_rva, "NO_RAW_MAP"
    lower = max(sec_off, call_off - 0x200)
    best = None
    for off in range(call_off - 3, lower - 1, -1):
        if data[off:off + 3] == b"\x55\x8B\xEC":
            best = off
            break
    if best is not None:
        return sec.va + (best - sec_off), "PROLOGUE_55_8B_EC"
    for off in range(call_off - 1, lower, -1):
        if data[off] == 0xCC:
            nxt = off + 1
            while nxt < call_off and data[nxt] == 0xCC:
                nxt += 1
            if nxt < call_off:
                return sec.va + (nxt - sec_off), "AFTER_INT3_PADDING"
    return max(sec.va, call_rva - 0x60), "FALLBACK_WINDOW"

def decode_function(data, sections, start_rva):
    from capstone import Cs, CS_ARCH_X86, CS_MODE_32
    from capstone.x86 import X86_OP_IMM
    off = rva_to_offset(start_rva, sections, len(data))
    if off is None:
        return [], [], []
    blob = data[off:min(len(data), off + MAX_FUNC_BYTES)]
    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    rows = []
    direct_calls = []
    imm_5e = []
    for idx, insn in enumerate(md.disasm(blob, start_rva)):
        if idx >= MAX_INSNS:
            break
        asm = f"{insn.mnemonic} {insn.op_str}".rstrip()
        rows.append((insn.address, insn.bytes.hex(" ").upper(), asm))
        if insn.mnemonic.lower() == "call" and insn.operands:
            op = insn.operands[0]
            if op.type == X86_OP_IMM:
                direct_calls.append(int(op.imm) & 0xFFFFFFFF)
        for op in insn.operands:
            if op.type == X86_OP_IMM and (int(op.imm) & 0xFFFFFFFF) == 0x5E:
                imm_5e.append((insn.address, asm))
                break
        if insn.mnemonic.lower() in {"ret", "retf"} and insn.address > start_rva + 4:
            break
    return rows, direct_calls, imm_5e

def find_direct_callers(target_rva, data, sections):
    callers = []
    for sec in sections:
        if not sec.executable or sec.raw_size <= 0:
            continue
        raw_start = sec.raw_ptr
        raw_end = min(len(data), raw_start + sec.raw_size)
        for off in range(raw_start, max(raw_start, raw_end - 4)):
            if data[off] != 0xE8 or off + 5 > raw_end:
                continue
            rel = struct.unpack_from("<i", data, off + 1)[0]
            call_rva = sec.va + (off - raw_start)
            dest = (call_rva + 5 + rel) & 0xFFFFFFFF
            if dest == target_rva:
                callers.append(call_rva)
                if len(callers) >= MAX_CALLERS:
                    return callers
    return callers

def main():
    ap = argparse.ArgumentParser(description="Bounded static send-wrapper/caller trace for 850 UseItem V18")
    ap.add_argument("--evidence", required=True)
    ap.add_argument("--output", required=True)
    args = ap.parse_args()

    try:
        capstone_version = importlib_metadata.version("capstone")
    except importlib_metadata.PackageNotFoundError:
        raise SystemExit("capstone package not installed")

    evidence_path = Path(args.evidence)
    text = evidence_path.read_text(encoding="utf-8", errors="replace")
    blocks = [b for b in parse_evidence(text) if b.name.lower() in TARGET_MODULES]

    selected = []
    for b in blocks:
        for x in b.xrefs:
            if x.name in SEND_NAMES:
                selected.append((b, x))
                if len(selected) >= MAX_XREFS:
                    break
        if len(selected) >= MAX_XREFS:
            break

    out = []
    out.append("MODE=850_USEITEM_SEND_CALLERS_V18")
    out.append(f"EVIDENCE={evidence_path}")
    out.append(f"CAPSTONE_PACKAGE_VERSION={capstone_version}")
    out.append("TARGET_MODULES=Lineage.exe,chigamec.dll")
    out.append("NETWORK_NAMES=send,sendto")
    out.append("RUNTIME_SCAN=NO")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")
    out.append("SEND_PACKET=NO")
    out.append(f"SEND_XREFS_SELECTED={len(selected)}")
    out.append("")

    wrapper_keys = set()
    total_callers = 0
    total_5e = 0
    missing_files = 0

    for b, x in selected:
        p = Path(b.path)
        if not p.is_file():
            missing_files += 1
            out.append(f"[XREF] MODULE={b.name} CALL_RVA=0x{x.call_rva:08X} STATUS=MISSING_FILE PATH={b.path}")
            continue
        data = p.read_bytes()
        try:
            image_base, sections = parse_pe(data)
        except Exception as ex:
            out.append(f"[XREF] MODULE={b.name} CALL_RVA=0x{x.call_rva:08X} STATUS=PE_ERROR ERROR={type(ex).__name__}:{ex}")
            continue
        start_rva, boundary = find_function_start(x.call_rva, data, sections)
        key = (b.name.lower(), start_rva)
        out.append(
            f"[XREF] MODULE={b.name} NETWORK={x.name} IAT_RVA=0x{x.iat_rva:08X} "
            f"CALL_RVA=0x{x.call_rva:08X} WRAPPER_RVA=0x{start_rva:08X} BOUNDARY={boundary}"
        )
        if key in wrapper_keys:
            out.append("  DUPLICATE_WRAPPER=YES")
            continue
        wrapper_keys.add(key)

        rows, direct_calls, imm_5e = decode_function(data, sections, start_rva)
        total_5e += len(imm_5e)
        out.append(f"  WRAPPER_INSNS={len(rows)} DIRECT_CALLS={len(direct_calls)} IMM_0x5E={len(imm_5e)}")
        for addr, asm in imm_5e:
            out.append(f"  WRAPPER_0x5E RVA=0x{addr:08X} ASM={asm}")
        for addr, by, asm in rows:
            out.append(f"  WRAPPER_DISASM RVA=0x{addr:08X} BYTES={by} ASM={asm}")

        callsites = find_direct_callers(start_rva, data, sections)
        total_callers += len(callsites)
        out.append(f"  DIRECT_CALLERS={len(callsites)}")
        seen_caller_funcs = set()
        for call_rva in callsites:
            caller_start, caller_boundary = find_function_start(call_rva, data, sections)
            if caller_start in seen_caller_funcs:
                continue
            seen_caller_funcs.add(caller_start)
            crows, ccalls, c5e = decode_function(data, sections, caller_start)
            total_5e += len(c5e)
            out.append(
                f"  [CALLER] CALL_RVA=0x{call_rva:08X} FUNCTION_RVA=0x{caller_start:08X} "
                f"BOUNDARY={caller_boundary} INSNS={len(crows)} IMM_0x5E={len(c5e)}"
            )
            for addr, asm in c5e:
                out.append(f"    CALLER_0x5E RVA=0x{addr:08X} ASM={asm}")
            for addr, by, asm in crows:
                out.append(f"    CALLER_DISASM RVA=0x{addr:08X} BYTES={by} ASM={asm}")

    out.append("")
    out.append("[DECISION]")
    out.append(f"UNIQUE_SEND_WRAPPERS={len(wrapper_keys)}")
    out.append(f"DIRECT_CALLERS_TOTAL={total_callers}")
    out.append(f"IMM_0x5E_HITS={total_5e}")
    out.append(f"MISSING_TARGET_FILES={missing_files}")
    if not selected:
        status = "BLOCKED_NO_SEND_XREFS_IN_EVIDENCE"
    elif not wrapper_keys:
        status = "BLOCKED_NO_WRAPPER_BODY"
    elif total_5e > 0:
        status = "PASS_SEND_CALLERS_WITH_0x5E_CANDIDATES"
    else:
        status = "PASS_SEND_WRAPPERS_NO_0x5E_YET"
    out.append(f"STATUS={status}")
    out.append("OBJECT_ID_SEMANTICS_PROVEN=NO")
    out.append("WP7_NATIVE_USEITEM_PASS=NO")
    out.append("MEMORY_WRITE=NO")
    out.append("SEND_PACKET=NO")

    Path(args.output).write_text("\n".join(out) + "\n", encoding="utf-8")

if __name__ == "__main__":
    main()
