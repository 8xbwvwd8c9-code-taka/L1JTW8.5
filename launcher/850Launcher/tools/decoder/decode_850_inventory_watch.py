#!/usr/bin/env python3
import argparse
import importlib.metadata as metadata
import re
from dataclasses import dataclass
from pathlib import Path

import capstone
from capstone import Cs, CS_ARCH_X86, CS_MODE_32, CS_AC_WRITE
from capstone.x86 import X86_OP_MEM

CAPSTONE_PACKAGE_VERSION = metadata.version("capstone")
CAPSTONE_BINDING_VERSION = getattr(capstone, "__version__", "UNKNOWN")

HEX_RE = re.compile(r"^0x[0-9A-Fa-f]+$")
HIT_RE = re.compile(
    r"^HIT NAME=(\S+)\s+N=(\d+)\s+TID=(\d+)\s+WATCH=(\S+)\s+"
    r"POST_WRITE_VALUE=(\S+)\s+EIP=(\S+)\s+EIP_RVA=(\S+)\s+"
    r"EAX=(\S+)\s+EBX=(\S+)\s+ECX=(\S+)\s+EDX=(\S+)\s+"
    r"ESI=(\S+)\s+EDI=(\S+)\s+EBP=(\S+)\s+ESP=(\S+)\s+DR6=(\S+)$"
)
CODE_RE = re.compile(r"^\s*CODE_FROM=(\S+)\s+BYTES=(.*)$")


def parse_hex(text: str):
    if not text or not HEX_RE.match(text):
        return None
    return int(text, 16)


def first_value(lines, prefix):
    for line in lines:
        if line.startswith(prefix):
            return line[len(prefix):].strip()
    return ""


@dataclass
class Hit:
    name: str
    number: int
    tid: int
    watch: int
    post_value: str
    eip: int
    eip_rva_text: str
    regs: dict
    code_from: int | None = None
    code: bytes | None = None


def parse_report(lines):
    hits = []
    pending = None
    for raw in lines:
        line = raw.rstrip("\r\n")
        m = HIT_RE.match(line)
        if m:
            vals = list(m.groups())
            regs = {
                "eax": parse_hex(vals[7]),
                "ebx": parse_hex(vals[8]),
                "ecx": parse_hex(vals[9]),
                "edx": parse_hex(vals[10]),
                "esi": parse_hex(vals[11]),
                "edi": parse_hex(vals[12]),
                "ebp": parse_hex(vals[13]),
                "esp": parse_hex(vals[14]),
            }
            pending = Hit(
                name=vals[0],
                number=int(vals[1]),
                tid=int(vals[2]),
                watch=parse_hex(vals[3]),
                post_value=vals[4].upper(),
                eip=parse_hex(vals[5]),
                eip_rva_text=vals[6].upper(),
                regs=regs,
            )
            hits.append(pending)
            continue

        m = CODE_RE.match(line)
        if m and pending is not None:
            pending.code_from = parse_hex(m.group(1))
            byte_text = m.group(2).strip()
            try:
                pending.code = bytes(int(x, 16) for x in byte_text.split()) if byte_text else b""
            except ValueError:
                pending.code = None
            pending = None
    return hits


def resolve_mem_address(md, op, regs):
    mem = op.mem
    absolute = not mem.base and not mem.index
    total = int(mem.disp)

    if mem.base:
        name = md.reg_name(mem.base).lower()
        if name not in regs or regs[name] is None:
            return None, absolute
        total += regs[name]

    if mem.index:
        name = md.reg_name(mem.index).lower()
        if name not in regs or regs[name] is None:
            return None, absolute
        total += regs[name] * int(mem.scale)

    return total & 0xFFFFFFFF, absolute


def decode_hit(md, hit: Hit):
    result = {
        "status": "NO_CODE_WINDOW",
        "candidates": [],
        "target_candidates": [],
    }
    if hit.code_from is None or hit.code is None or hit.eip is None or hit.watch is None:
        return result

    eip_offset = hit.eip - hit.code_from
    if eip_offset <= 0 or eip_offset > len(hit.code):
        result["status"] = "EIP_OUTSIDE_CODE_WINDOW"
        return result

    lo = max(0, eip_offset - 15)
    hi = eip_offset
    for start_off in range(lo, hi):
        blob = hit.code[start_off:eip_offset]
        if not blob:
            continue
        start_va = hit.code_from + start_off
        decoded = list(md.disasm(blob, start_va, count=1))
        if len(decoded) != 1:
            continue
        insn = decoded[0]
        if insn.address != start_va or insn.address + insn.size != hit.eip:
            continue

        writes = []
        for op_index, op in enumerate(insn.operands):
            if op.type != X86_OP_MEM:
                continue
            access = getattr(op, "access", 0)
            if not (access & CS_AC_WRITE):
                continue
            addr, absolute = resolve_mem_address(md, op, hit.regs)
            writes.append((op_index, addr, absolute))

        candidate = {
            "start": insn.address,
            "end": insn.address + insn.size,
            "size": insn.size,
            "mnemonic": insn.mnemonic,
            "op_str": insn.op_str,
            "bytes": bytes(insn.bytes).hex(" ").upper(),
            "writes": writes,
        }
        result["candidates"].append(candidate)

        if any(absolute and addr == hit.watch for _, addr, absolute in writes):
            result["target_candidates"].append(candidate)

    tc = result["target_candidates"]
    if len(tc) == 1:
        result["status"] = "PASS_UNIQUE_TARGET_WRITER"
    elif len(tc) > 1:
        result["status"] = "AMBIGUOUS_MULTIPLE_TARGET_WRITERS"
    elif result["candidates"]:
        result["status"] = "ALIGNED_INSTRUCTION_BUT_ABSOLUTE_TARGET_NOT_PROVEN"
    else:
        result["status"] = "NO_ALIGNED_PREDECESSOR"
    return result


def main():
    ap = argparse.ArgumentParser(description="Decode 850 hardware-watch writer instructions with Capstone x86-32.")
    ap.add_argument("--input", required=True, help="850_inventory_root_global_watch.txt")
    ap.add_argument("--output", required=True, help="decoder report path")
    args = ap.parse_args()

    input_path = Path(args.input)
    output_path = Path(args.output)
    lines = input_path.read_text(encoding="utf-8", errors="replace").splitlines()

    sha = first_value(lines, "CLIENT_SHA256=")
    authority = first_value(lines, "CLIENT_AUTHORITY=")
    pid = first_value(lines, "PID=")
    start_utc = first_value(lines, "PROCESS_START_UTC=")
    root_rva = first_value(lines, "ROOT_GLOBAL_RVA=")
    root_va = first_value(lines, "ROOT_GLOBAL_VA=")
    memory_write = first_value(lines, "TARGET_MEMORY_WRITE=")

    hits = parse_report(lines)
    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True

    decoded = [(hit, decode_hit(md, hit)) for hit in hits]
    unique_pass = sum(1 for _, d in decoded if d["status"] == "PASS_UNIQUE_TARGET_WRITER")
    ambiguous = sum(1 for _, d in decoded if d["status"].startswith("AMBIGUOUS"))
    unresolved = len(decoded) - unique_pass - ambiguous

    identity_ok = bool(sha and authority == "1" and pid and start_utc and root_rva and root_va and memory_write == "NO")
    version_ok = CAPSTONE_PACKAGE_VERSION == "5.0.9"

    out = []
    out.append("MODE=850_INVENTORY_WATCH_CAPSTONE_DECODER")
    out.append(f"INPUT={input_path}")
    out.append(f"CAPSTONE_VERSION={CAPSTONE_PACKAGE_VERSION}")
    out.append(f"CAPSTONE_PACKAGE_VERSION={CAPSTONE_PACKAGE_VERSION}")
    out.append(f"CAPSTONE_BINDING_VERSION={CAPSTONE_BINDING_VERSION}")
    out.append("ARCH=x86")
    out.append("MODE_BITS=32")
    out.append("ABSOLUTE_MEMORY_TARGET_REQUIRED=YES")
    out.append(f"CLIENT_SHA256={sha}")
    out.append(f"CLIENT_AUTHORITY={authority}")
    out.append(f"PID={pid}")
    out.append(f"PROCESS_START_UTC={start_utc}")
    out.append(f"ROOT_GLOBAL_RVA={root_rva}")
    out.append(f"ROOT_GLOBAL_VA={root_va}")
    out.append(f"TARGET_MEMORY_WRITE={memory_write}")
    out.append(f"IDENTITY_GATE={'PASS' if identity_ok else 'FAIL'}")
    out.append(f"PACKAGE_VERSION_GATE={'PASS' if version_ok else 'FAIL'}")
    out.append("")
    out.append("[DECODED_HITS]")

    for hit, d in decoded:
        out.append(
            f"HIT N={hit.number} TID={hit.tid} POST_WRITE_VALUE={hit.post_value} "
            f"EIP=0x{hit.eip:08X} EIP_RVA={hit.eip_rva_text} STATUS={d['status']}"
        )
        out.append(
            f"  CODE_FROM={'0x%08X' % hit.code_from if hit.code_from is not None else 'NONE'} "
            f"CODE_LEN={len(hit.code) if hit.code is not None else 0} WATCH=0x{hit.watch:08X}"
        )
        for index, c in enumerate(d["candidates"]):
            targets = ",".join(
                ("ABS:" if absolute else "REG:") + ("UNKNOWN" if addr is None else f"0x{addr:08X}")
                for _, addr, absolute in c["writes"]
            ) or "NONE"
            out.append(
                f"  CANDIDATE[{index}] START=0x{c['start']:08X} END=0x{c['end']:08X} "
                f"SIZE={c['size']} ASM={c['mnemonic']} {c['op_str']} BYTES={c['bytes']} "
                f"MEM_WRITE_TARGETS={targets}"
            )
        if len(d["target_candidates"]) == 1:
            c = d["target_candidates"][0]
            out.append(
                f"  UNIQUE_WRITER START=0x{c['start']:08X} END=0x{c['end']:08X} "
                f"ASM={c['mnemonic']} {c['op_str']}"
            )

    out.append("")
    out.append("[SUMMARY]")
    out.append(f"HITS={len(decoded)}")
    out.append(f"UNIQUE_TARGET_WRITERS={unique_pass}")
    out.append(f"AMBIGUOUS={ambiguous}")
    out.append(f"UNRESOLVED={unresolved}")
    out.append("RAW_BYTE_OPCODE_PROMOTION=NO")
    out.append("DECODER_ALIGNED_PROMOTION=YES")
    out.append("ABSOLUTE_MEMORY_TARGET_REQUIRED=YES")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")

    if not identity_ok or not version_ok:
        status = "REJECT_IDENTITY_OR_DECODER_VERSION_GATE"
    elif not decoded:
        status = "NO_WATCH_HITS"
    elif unique_pass == len(decoded):
        status = "PASS_ALL_HITS_UNIQUE_DECODER_WRITERS"
    elif unique_pass > 0:
        status = "PARTIAL_DECODER_PROOF"
    else:
        status = "NO_UNIQUE_DECODER_WRITER"
    out.append(f"STATUS={status}")

    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_text("\n".join(out) + "\n", encoding="utf-8")
    print("\n".join(out))
    print(f"OUTPUT={output_path}")


if __name__ == "__main__":
    main()
