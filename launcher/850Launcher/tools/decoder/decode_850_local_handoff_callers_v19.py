#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import importlib.metadata as importlib_metadata
import re
import struct
from dataclasses import dataclass
from pathlib import Path

AUTHORITATIVE_SHA256 = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"
MAX_FOCUS_XREFS = 64
MAX_DIRECT_CALLERS = 96
MAX_FUNCTION_BYTES = 640
MAX_INSNS = 260
MAX_TOP_CANDIDATES = 3

STRONG_WRITE_NAMES = {
    "ssl_write",
    "bio_write",
    "send",
    "sendto",
    "wsasend",
    "wsasendto",
}
CRYPTO_HANDOFF_NAMES = {
    "evp_encryptupdate",
    "evp_cipherupdate",
    "evp_encryptfinal_ex",
    "evp_cipherfinal_ex",
    "aes_encrypt",
    "rc4",
}


@dataclass
class LocalXref:
    dll: str
    name: str
    iat_rva: int
    call_rva: int
    kind: str


@dataclass
class Section:
    name: str
    va: int
    vsize: int
    raw_ptr: int
    raw_size: int
    chars: int

    @property
    def executable(self) -> bool:
        return bool(self.chars & 0x20000000)


def parse_v18_local_evidence(text: str):
    meta = {}
    xrefs = []
    section = ""
    for raw in text.splitlines():
        line = raw.strip()
        if not line:
            continue
        if line.startswith("[") and line.endswith("]"):
            section = line
            continue
        if section == "[LOCAL_CLIENT_IAT_XREFS]" and line.startswith("DLL="):
            m = re.match(
                r"DLL=(\S+)\s+NAME=(\S+)\s+IAT_RVA=0x([0-9A-Fa-f]+)\s+"
                r"CALL_RVA=0x([0-9A-Fa-f]+)\s+KIND=(.+)$",
                line,
            )
            if m:
                xrefs.append(LocalXref(
                    dll=m.group(1),
                    name=m.group(2),
                    iat_rva=int(m.group(3), 16),
                    call_rva=int(m.group(4), 16),
                    kind=m.group(5).strip(),
                ))
            continue
        if "=" in line and not line.startswith(("DLL=", "LOCAL_CLIENT_MODULE=")):
            key, value = line.split("=", 1)
            if key and " " not in key:
                meta[key] = value
    return meta, xrefs


def select_focus_xrefs(xrefs, max_xrefs: int = MAX_FOCUS_XREFS):
    libeay = [x for x in xrefs if x.dll.lower() == "libeay32.dll"]
    selected = libeay if libeay else list(xrefs)
    selected.sort(key=lambda x: (x.call_rva, x.dll.lower(), x.name.lower()))
    return selected[:max_xrefs]


def parse_pe(data: bytes):
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
    if magic != 0x10B:
        raise ValueError("only PE32/x86 is supported")
    image_base = struct.unpack_from("<I", data, opt + 28)[0]
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


def rva_to_offset(rva: int, sections, data_len: int):
    for s in sections:
        span = max(s.vsize, s.raw_size)
        if s.va <= rva < s.va + span:
            off = s.raw_ptr + (rva - s.va)
            if 0 <= off < data_len:
                return off
    return None


def section_for_rva(rva: int, sections):
    for s in sections:
        span = max(s.vsize, s.raw_size)
        if s.executable and s.va <= rva < s.va + span:
            return s
    return None


def find_function_start(call_rva: int, data: bytes, sections):
    sec = section_for_rva(call_rva, sections)
    if sec is None:
        return call_rva, "NO_EXEC_SECTION"
    call_off = rva_to_offset(call_rva, sections, len(data))
    sec_off = rva_to_offset(sec.va, sections, len(data))
    if call_off is None or sec_off is None:
        return call_rva, "NO_RAW_MAP"

    lower = max(sec_off, call_off - 0x240)

    for off in range(call_off - 3, lower - 1, -1):
        if data[off:off + 3] == b"\x55\x8B\xEC":
            return sec.va + (off - sec_off), "PROLOGUE_55_8B_EC"

    for off in range(call_off - 1, lower - 1, -1):
        if data[off] == 0xCC:
            nxt = off + 1
            while nxt < call_off and data[nxt] == 0xCC:
                nxt += 1
            if nxt < call_off:
                return sec.va + (nxt - sec_off), "AFTER_INT3_PADDING"

    return max(sec.va, call_rva - 0x60), "FALLBACK_WINDOW"


def find_direct_callers(target_rva: int, data: bytes, sections, max_callers: int = MAX_DIRECT_CALLERS):
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
                if len(callers) >= max_callers:
                    return callers
    return callers


def decode_function(data: bytes, sections, start_rva: int):
    from capstone import Cs, CS_ARCH_X86, CS_MODE_32
    from capstone.x86 import X86_OP_IMM, X86_OP_MEM

    off = rva_to_offset(start_rva, sections, len(data))
    if off is None:
        return [], [], [], []

    blob = data[off:min(len(data), off + MAX_FUNCTION_BYTES)]
    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    rows = []
    direct_calls = []
    imm_5e = []
    arg32_hints = []

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

        if insn.mnemonic.lower() in {"push", "mov", "lea"}:
            if any(op.type == X86_OP_MEM for op in insn.operands):
                arg32_hints.append((insn.address, asm))

        if insn.mnemonic.lower() in {"ret", "retf"} and insn.address > start_rva + 4:
            break

    return rows, direct_calls, imm_5e, arg32_hints


def rank_candidate(dll: str, name: str, boundary: str, imm_5e_hits: int, direct_caller_count: int):
    score = 0
    if dll.lower() == "libeay32.dll":
        score += 4
    lname = name.lower()
    if lname in STRONG_WRITE_NAMES:
        score += 4
    elif lname in CRYPTO_HANDOFF_NAMES:
        score += 2
    if boundary == "PROLOGUE_55_8B_EC":
        score += 1
    elif boundary == "AFTER_INT3_PADDING":
        score += 0.5
    if imm_5e_hits:
        score += 6 + min(imm_5e_hits - 1, 2)
    if direct_caller_count:
        score += min(direct_caller_count, 3)
    return score


def sha256_bytes(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest().upper()


def main():
    ap = argparse.ArgumentParser(
        description="V19 bounded Lin.bin2 local-module handoff owner/caller classifier for 850 WP7"
    )
    ap.add_argument("--client", required=True)
    ap.add_argument("--evidence", required=True)
    ap.add_argument("--output", required=True)
    ap.add_argument("--expected-sha256", default=AUTHORITATIVE_SHA256)
    args = ap.parse_args()

    client_path = Path(args.client)
    evidence_path = Path(args.evidence)
    output_path = Path(args.output)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    data = client_path.read_bytes()
    actual_sha = sha256_bytes(data)
    hash_ok = actual_sha == args.expected_sha256.upper()

    evidence_text = evidence_path.read_text(encoding="utf-8", errors="replace")
    meta, all_xrefs = parse_v18_local_evidence(evidence_text)
    focus = select_focus_xrefs(all_xrefs)

    evidence_hash = meta.get("CLIENT_SHA256", "").upper()
    evidence_authority = meta.get("CLIENT_AUTHORITY", "") == "1"
    evidence_matches_client = (not evidence_hash) or evidence_hash == actual_sha

    out = [
        "MODE=850_LOCAL_HANDOFF_CALLERS_V19",
        f"CLIENT={client_path}",
        f"CLIENT_SHA256={actual_sha}",
        f"CLIENT_AUTHORITY={1 if hash_ok else 0}",
        f"V18_EVIDENCE={evidence_path}",
        f"V18_CLIENT_AUTHORITY={1 if evidence_authority else 0}",
        f"V18_HASH_MATCH={1 if evidence_matches_client else 0}",
        f"LOCAL_XREFS_TOTAL={len(all_xrefs)}",
        f"FOCUS_XREFS={len(focus)}",
        "FOCUS_POLICY=LIBEAY32_ONLY_IF_PRESENT_ELSE_ALL_V18_LOCAL_XREFS",
        "STATIC_FILE_READ_ONLY=YES",
        "RUNTIME_ATTACH=NO",
        "HEAP_SCAN=NO",
        "MEM_PRIVATE_SCAN=NO",
        "MEMORY_WRITE=NO",
        "PACKET_SEND=NO",
        "",
    ]

    if not hash_ok:
        out += [
            "[DECISION]",
            "STATUS=BLOCKED_CLIENT_HASH_MISMATCH",
            "OBJECT_ID_PROVEN=NO",
            "WP7_NATIVE_USEITEM_PASS=NO",
            "NEXT=Use only the authoritative 8.50c Lin.bin2 before classifying handoff owners.",
        ]
        output_path.write_text("\n".join(out) + "\n", encoding="utf-8")
        return 0

    if not evidence_authority or not evidence_matches_client:
        out += [
            "[DECISION]",
            "STATUS=BLOCKED_V18_EVIDENCE_AUTHORITY",
            "OBJECT_ID_PROVEN=NO",
            "WP7_NATIVE_USEITEM_PASS=NO",
            "NEXT=Regenerate V18 local-module evidence from the same authoritative Lin.bin2.",
        ]
        output_path.write_text("\n".join(out) + "\n", encoding="utf-8")
        return 0

    image_base, sections = parse_pe(data)
    out.append(f"IMAGE_BASE=0x{image_base:08X}")

    try:
        capstone_version = importlib_metadata.version("capstone")
    except importlib_metadata.PackageNotFoundError:
        capstone_version = "NOT_INSTALLED"
    out.append(f"CAPSTONE_PACKAGE_VERSION={capstone_version}")

    candidates = []

    for x in focus:
        owner_rva, boundary = find_function_start(x.call_rva, data, sections)
        rows, direct_calls, imm_5e, arg_hints = decode_function(data, sections, owner_rva)
        caller_sites = find_direct_callers(owner_rva, data, sections)

        caller_funcs = []
        caller_seen = set()
        caller_5e_total = 0
        caller_arg_hint_total = 0
        for callsite in caller_sites:
            caller_start, caller_boundary = find_function_start(callsite, data, sections)
            if caller_start in caller_seen:
                continue
            caller_seen.add(caller_start)
            crows, ccalls, c5e, cargs = decode_function(data, sections, caller_start)
            caller_funcs.append((caller_start, caller_boundary, len(crows), len(c5e), len(cargs)))
            caller_5e_total += len(c5e)
            caller_arg_hint_total += len(cargs)

        total_5e = len(imm_5e) + caller_5e_total
        score = rank_candidate(x.dll, x.name, boundary, total_5e, len(caller_sites))
        candidates.append({
            "score": score,
            "xref": x,
            "owner_rva": owner_rva,
            "boundary": boundary,
            "insns": len(rows),
            "direct_calls": direct_calls,
            "imm_5e": imm_5e,
            "arg_hints": arg_hints,
            "caller_sites": caller_sites,
            "caller_funcs": caller_funcs,
            "caller_5e_total": caller_5e_total,
            "caller_arg_hint_total": caller_arg_hint_total,
        })

    candidates.sort(key=lambda c: (-c["score"], c["owner_rva"], c["xref"].call_rva))
    top = []
    top_owner_rvas = set()
    for c in candidates:
        if c["owner_rva"] in top_owner_rvas:
            continue
        top_owner_rvas.add(c["owner_rva"])
        top.append(c)
        if len(top) >= MAX_TOP_CANDIDATES:
            break

    out += ["", "[CANDIDATES]"]
    for idx, c in enumerate(candidates, 1):
        x = c["xref"]
        out.append(
            f"CANDIDATE={idx} SCORE={c['score']:.1f} DLL={x.dll} NAME={x.name} "
            f"IAT_RVA=0x{x.iat_rva:08X} CALL_RVA=0x{x.call_rva:08X} "
            f"OWNER_RVA=0x{c['owner_rva']:08X} BOUNDARY={c['boundary']} "
            f"OWNER_INSNS={c['insns']} OWNER_0x5E={len(c['imm_5e'])} "
            f"DIRECT_CALLER_SITES={len(c['caller_sites'])} "
            f"CALLER_0x5E={c['caller_5e_total']} ARG32_HINTS={len(c['arg_hints']) + c['caller_arg_hint_total']}"
        )
        if c["caller_funcs"]:
            unique = ",".join(f"0x{row[0]:08X}" for row in c["caller_funcs"][:8])
            out.append(f"  CALLER_FUNCTION_RVAS={unique}")
        for addr, asm in c["imm_5e"][:4]:
            out.append(f"  OWNER_0x5E RVA=0x{addr:08X} ASM={asm}")
        out.append("  OBJECT_ID_SEMANTICS=NOT_PROVEN_STATIC_HINT_ONLY")

    out += ["", "[TOP_EXACT_TARGETS]"]
    for idx, c in enumerate(top, 1):
        x = c["xref"]
        out.append(
            f"TARGET={idx} MODULE=Lin.bin2 OWNER_RVA=0x{c['owner_rva']:08X} "
            f"CALL_RVA=0x{x.call_rva:08X} DLL={x.dll} NAME={x.name} SCORE={c['score']:.1f}"
        )

    if not focus:
        status = "BLOCKED_NO_LOCAL_IAT_XREFS"
        next_action = "Regenerate V18 local-module evidence; no bounded local handoff callsites were available."
    elif not candidates:
        status = "BLOCKED_NO_FUNCTION_OWNER"
        next_action = "Capture only the exact V18 callsite code bytes that failed owner mapping."
    else:
        status = "PASS_BOUNDED_HANDOFF_CANDIDATES"
        targets = ",".join(f"0x{c['owner_rva']:08X}" for c in top)
        next_action = (
            "Controlled runtime correlation on only these Lin.bin2 owner RVAs "
            f"({targets}): no-action -> one manual potion use -> no-action; "
            "prove whether a 32-bit runtime item identity enters the action path."
        )

    out += [
        "",
        "[DECISION]",
        f"STATUS={status}",
        f"TOP_TARGET_COUNT={len(top)}",
        "OBJECT_ID_PROVEN=NO",
        "ITEM_SPECIFIC_ACTION_PROVEN=NO",
        "NATIVE_SEND_PATH_PROVEN=NO",
        "WP7_NATIVE_USEITEM_PASS=NO",
        "MEMORY_WRITE=NO",
        "PACKET_SEND=NO",
        f"NEXT={next_action}",
    ]

    output_path.write_text("\n".join(out) + "\n", encoding="utf-8")


if __name__ == "__main__":
    main()
