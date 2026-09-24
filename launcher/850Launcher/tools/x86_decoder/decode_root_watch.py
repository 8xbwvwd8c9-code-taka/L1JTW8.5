#!/usr/bin/env python3
from __future__ import annotations

import argparse
import re
import sys
from dataclasses import dataclass
from pathlib import Path
from typing import Iterable


def parse_args() -> argparse.Namespace:
    p = argparse.ArgumentParser(description="Decode 850 hardware-watch writer windows with Capstone x86/32.")
    p.add_argument("--input", required=True)
    p.add_argument("--output", required=True)
    p.add_argument("--vendor", default="")
    return p.parse_args()


args = parse_args()
if args.vendor:
    sys.path.insert(0, str(Path(args.vendor).resolve()))

try:
    from capstone import Cs, CS_ARCH_X86, CS_MODE_32
    from capstone.x86_const import X86_OP_IMM, X86_OP_MEM, X86_OP_REG, X86_REG_INVALID
except Exception as exc:  # pragma: no cover - surfaced in report/exit
    print(f"STATUS=BLOCKED_CAPSTONE_IMPORT ERROR={type(exc).__name__}:{exc}")
    sys.exit(20)


HIT_RE = re.compile(
    r"^HIT NAME=(?P<name>\S+)\s+N=(?P<n>\d+)\s+TID=(?P<tid>\d+)\s+WATCH=(?P<watch>0x[0-9A-Fa-f]+)\s+"
    r"POST_WRITE_VALUE=(?P<value>\S+)\s+EIP=(?P<eip>0x[0-9A-Fa-f]+)\s+EIP_RVA=(?P<eip_rva>\S+)"
)
CODE_RE = re.compile(r"^\s*CODE_FROM=(?P<from>0x[0-9A-Fa-f]+)\s+BYTES=(?P<bytes>(?:[0-9A-Fa-f]{2}(?:\s+|$))*)")


@dataclass
class Hit:
    name: str
    number: int
    tid: int
    watch: int
    value: str
    eip: int
    eip_rva: str
    code_from: int | None = None
    code: bytes = b""


@dataclass(frozen=True)
class Candidate:
    address: int
    size: int
    mnemonic: str
    op_str: str
    target_write: bool
    source_kind: str
    source_text: str

    @property
    def end(self) -> int:
        return self.address + self.size


def first_value(lines: list[str], prefix: str) -> str:
    for line in lines:
        if line.startswith(prefix):
            return line[len(prefix):].strip()
    return ""


def parse_hex(text: str) -> int | None:
    if not text.lower().startswith("0x"):
        return None
    try:
        return int(text[2:], 16)
    except ValueError:
        return None


def parse_hits(lines: list[str]) -> list[Hit]:
    out: list[Hit] = []
    current: Hit | None = None
    for line in lines:
        m = HIT_RE.match(line)
        if m:
            current = Hit(
                name=m.group("name"),
                number=int(m.group("n")),
                tid=int(m.group("tid")),
                watch=int(m.group("watch"), 16),
                value=m.group("value").upper(),
                eip=int(m.group("eip"), 16),
                eip_rva=m.group("eip_rva").upper(),
            )
            out.append(current)
            continue
        if current is not None:
            cm = CODE_RE.match(line)
            if cm:
                current.code_from = int(cm.group("from"), 16)
                raw = cm.group("bytes").strip()
                current.code = bytes(int(x, 16) for x in raw.split()) if raw else b""
                current = None
    return out


def operand_source(insn) -> tuple[str, str]:
    try:
        if len(insn.operands) < 2:
            return "NONE", ""
        src = insn.operands[1]
        if src.type == X86_OP_REG:
            return "REG", insn.reg_name(src.reg).upper()
        if src.type == X86_OP_IMM:
            return "IMM", f"0x{(src.imm & 0xFFFFFFFF):08X}"
        if src.type == X86_OP_MEM:
            return "MEM", insn.op_str
    except Exception:
        pass
    return "OTHER", ""


def writes_absolute_target(insn, target: int) -> bool:
    try:
        if not insn.operands:
            return False
        dst = insn.operands[0]
        if dst.type != X86_OP_MEM:
            return False
        mem = dst.mem
        if mem.base != X86_REG_INVALID or mem.index != X86_REG_INVALID:
            return False
        return (mem.disp & 0xFFFFFFFF) == (target & 0xFFFFFFFF)
    except Exception:
        return False


def decode_candidates(hit: Hit) -> list[Candidate]:
    if hit.code_from is None or not hit.code:
        return []
    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True

    unique: dict[tuple[int, int, str, str, bool, str, str], Candidate] = {}
    # The capture begins before the writer but not necessarily at an instruction boundary.
    # Try each possible x86 alignment offset. Only sequences containing an instruction whose
    # end address is exactly the post-write EIP can be candidates.
    max_shift = min(15, max(0, len(hit.code) - 1))
    for shift in range(max_shift + 1):
        chunk = hit.code[shift:]
        start = hit.code_from + shift
        try:
            for insn in md.disasm(chunk, start):
                if insn.address >= hit.eip:
                    break
                if insn.address + insn.size == hit.eip:
                    src_kind, src_text = operand_source(insn)
                    c = Candidate(
                        address=insn.address,
                        size=insn.size,
                        mnemonic=insn.mnemonic,
                        op_str=insn.op_str,
                        target_write=writes_absolute_target(insn, hit.watch),
                        source_kind=src_kind,
                        source_text=src_text,
                    )
                    key = (c.address, c.size, c.mnemonic, c.op_str, c.target_write, c.source_kind, c.source_text)
                    unique[key] = c
                    break
        except Exception:
            continue
    return sorted(unique.values(), key=lambda c: (not c.target_write, c.address, c.size, c.mnemonic, c.op_str))


def fmt_rva(address: int, module_base: int | None) -> str:
    if module_base is None or address < module_base:
        return "OUTSIDE"
    return f"0x{address - module_base:08X}"


def main() -> int:
    input_path = Path(args.input)
    output_path = Path(args.output)
    if not input_path.is_file():
        print(f"STATUS=BLOCKED_INPUT_MISSING INPUT={input_path}")
        return 21

    lines = input_path.read_text(encoding="utf-8", errors="replace").splitlines()
    module_base = parse_hex(first_value(lines, "MODULE_BASE="))
    sha = first_value(lines, "CLIENT_SHA256=")
    authority = first_value(lines, "CLIENT_AUTHORITY=")
    pid = first_value(lines, "PID=")
    process_start = first_value(lines, "PROCESS_START_UTC=")
    memory_write = first_value(lines, "TARGET_MEMORY_WRITE=")
    hits = parse_hits(lines)

    out: list[str] = []
    out.append("MODE=850_ROOT_WATCH_CAPSTONE_DECODER")
    out.append(f"INPUT={input_path}")
    out.append(f"CLIENT_SHA256={sha}")
    out.append(f"CLIENT_AUTHORITY={authority}")
    out.append(f"PID={pid}")
    out.append(f"PROCESS_START_UTC={process_start}")
    out.append(f"MODULE_BASE={first_value(lines, 'MODULE_BASE=')}")
    out.append(f"TARGET_MEMORY_WRITE={memory_write}")
    out.append("DECODER=CAPSTONE_X86_32")
    out.append("RAW_E8_USED=NO")
    out.append("")
    out.append("[HITS]")

    proven = 0
    ambiguous = 0
    no_writer = 0
    for hit in hits:
        candidates = decode_candidates(hit)
        target = [c for c in candidates if c.target_write]
        if len(target) == 1:
            status = "PROVEN_TARGET_WRITER"
            proven += 1
        elif len(target) > 1:
            status = "AMBIGUOUS_TARGET_WRITER"
            ambiguous += 1
        else:
            status = "NO_TARGET_WRITER_DECODED"
            no_writer += 1

        out.append(
            f"HIT N={hit.number} TID={hit.tid} WATCH=0x{hit.watch:08X} VALUE={hit.value} "
            f"POST_EIP=0x{hit.eip:08X} POST_EIP_RVA={hit.eip_rva} STATUS={status} "
            f"CANDIDATES={len(candidates)} TARGET_MATCHES={len(target)}"
        )
        for c in candidates:
            out.append(
                f"  INSN VA=0x{c.address:08X} RVA={fmt_rva(c.address, module_base)} SIZE={c.size} "
                f"END=0x{c.end:08X} TARGET_WRITE={'YES' if c.target_write else 'NO'} "
                f"SRC_KIND={c.source_kind} SRC={c.source_text or '-'} MNEMONIC={c.mnemonic} OPS={c.op_str}"
            )

    identity_ok = bool(sha) and authority == "1" and bool(pid) and bool(process_start) and memory_write == "NO"
    out.append("")
    out.append("[SUMMARY]")
    out.append(f"IDENTITY_SAFETY_GATE={'PASS' if identity_ok else 'FAIL'}")
    out.append(f"WATCH_HITS={len(hits)}")
    out.append(f"PROVEN_TARGET_WRITERS={proven}")
    out.append(f"AMBIGUOUS_TARGET_WRITERS={ambiguous}")
    out.append(f"NO_TARGET_WRITER_DECODED={no_writer}")
    if not identity_ok:
        status = "REJECT_IDENTITY_OR_SAFETY_GATE"
    elif not hits:
        status = "NO_WATCH_HITS"
    elif ambiguous:
        status = "DECODER_AMBIGUOUS"
    elif proven == len(hits):
        status = "PASS_ALL_HITS_DECODER_ALIGNED"
    elif proven:
        status = "PARTIAL_DECODER_ALIGNMENT"
    else:
        status = "NO_DECODER_CONFIRMED_TARGET_WRITER"
    out.append(f"STATUS={status}")
    out.append("PROMOTION_RULE=Only PROVEN_TARGET_WRITER instructions may be used as writer evidence.")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")

    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_text("\n".join(out) + "\n", encoding="utf-8")
    print("\n".join(out))
    print(f"OUTPUT={output_path}")
    return 0 if identity_ok else 22


if __name__ == "__main__":
    raise SystemExit(main())
