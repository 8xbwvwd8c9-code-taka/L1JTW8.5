#!/usr/bin/env python3
import argparse
import hashlib
import importlib.metadata as importlib_metadata
import re
import struct
from collections import defaultdict, deque
from pathlib import Path

EXPECTED_SHA = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"
MAX_STATES = 4096
MAX_VISITS_PER_PC = 12
HELPER_CAPTURE_BYTES = 384
MAX_DISASM_INSNS = 160
KNOWN_CONTAINER_HELPERS = {
    0x004CE990: "SIZE",
    0x004CC180: "INDEX",
    0x004CD870: "BEGIN_MUT",
    0x004CD890: "BEGIN_CONST",
    0x004CD8B0: "END_MUT",
    0x004CD8D0: "END_CONST",
}
METHOD_RE = re.compile(
    r"^METHOD SLOT=(?P<slot>\d+) VA=0x(?P<va>[0-9A-Fa-f]+) RVA=0x(?P<rva>[0-9A-Fa-f]+) .*? BYTES=(?P<bytes>[0-9A-Fa-f ]+)$"
)


def first_value(lines, prefix):
    for line in lines:
        if line.startswith(prefix):
            return line[len(prefix):].strip()
    return ""


def hx(text):
    if not text:
        raise ValueError("missing hex value")
    return int(text, 16)


def parse_methods(lines):
    out = []
    for line in lines:
        m = METHOD_RE.match(line.strip())
        if not m:
            continue
        out.append({
            "slot": int(m.group("slot")),
            "va": int(m.group("va"), 16),
            "rva": int(m.group("rva"), 16),
            "blob": bytes.fromhex(m.group("bytes")),
        })
    return out


def merge_helper_reasons(rows):
    by = {}
    for r in rows:
        t = int(r["target_rva"])
        x = by.setdefault(t, {"target_rva": t, "reasons": set(), "callsites": set()})
        x["reasons"].add(str(r["reason"]))
        x["callsites"].add((int(r["method_rva"]), int(r["insn_rva"]), str(r["reason"])))
    out = []
    for t in sorted(by):
        x = by[t]
        out.append({
            "target_rva": t,
            "reasons": sorted(x["reasons"]),
            "callsite_count": len(x["callsites"]),
            "callsites": sorted(x["callsites"]),
        })
    return out


def rva_to_file_offset(rva, sections):
    rva = int(rva)
    for s in sections:
        va = int(s["virtual_address"])
        vs = int(s["virtual_size"])
        rs = int(s["raw_size"])
        rp = int(s["raw_ptr"])
        span = max(vs, rs)
        if va <= rva < va + span:
            delta = rva - va
            if delta >= rs:
                return None
            return rp + delta
    return None


def section_for_rva(rva, sections):
    rva = int(rva)
    for s in sections:
        va = int(s["virtual_address"])
        span = max(int(s["virtual_size"]), int(s["raw_size"]))
        if va <= rva < va + span:
            return s
    return None


def strong_data_semantics(s):
    return any(int(s.get(k, 0)) > 0 for k in ("find_lookup_hits", "objectid_cmp_hits", "erase_insert_hits"))


def parse_pe_sections(data):
    if len(data) < 0x100 or data[:2] != b"MZ":
        raise ValueError("not a PE/MZ image")
    pe_off = struct.unpack_from("<I", data, 0x3C)[0]
    if pe_off + 24 > len(data) or data[pe_off:pe_off+4] != b"PE\0\0":
        raise ValueError("invalid PE signature")
    coff = pe_off + 4
    machine, nsects, _ts, _sym, _nsym, opt_size, _chars = struct.unpack_from("<HHIIIHH", data, coff)
    opt = coff + 20
    if opt + opt_size > len(data):
        raise ValueError("truncated PE optional header")
    magic = struct.unpack_from("<H", data, opt)[0]
    if magic != 0x10B:
        raise ValueError(f"expected PE32 magic 0x10B, got 0x{magic:X}")
    image_base = struct.unpack_from("<I", data, opt + 28)[0]
    size_of_image = struct.unpack_from("<I", data, opt + 56)[0]
    sec_off = opt + opt_size
    sections = []
    for i in range(nsects):
        p = sec_off + i * 40
        if p + 40 > len(data):
            raise ValueError("truncated section table")
        name = data[p:p+8].split(b"\0", 1)[0].decode("ascii", errors="replace")
        virtual_size, virtual_address, raw_size, raw_ptr = struct.unpack_from("<IIII", data, p + 8)
        characteristics = struct.unpack_from("<I", data, p + 36)[0]
        sections.append({
            "name": name,
            "virtual_size": virtual_size,
            "virtual_address": virtual_address,
            "raw_size": raw_size,
            "raw_ptr": raw_ptr,
            "characteristics": characteristics,
            "executable": bool(characteristics & 0x20000000),
        })
    return {"machine": machine, "image_base": image_base, "size_of_image": size_of_image, "sections": sections}


def _norm_map(d):
    return tuple(sorted((int(k), str(v)) for k, v in d.items()))


def _state_key(pc, owner_alias, ret_alias, vtable_alias, fn_alias, pending_pushes):
    return (pc, _norm_map(owner_alias), _norm_map(ret_alias), _norm_map(vtable_alias), _norm_map(fn_alias), tuple(bool(x) for x in pending_pushes))


def discover_helper_reasons(methods, module_base, module_end):
    from capstone import Cs, CS_ARCH_X86, CS_MODE_32
    from capstone.x86 import X86_OP_REG, X86_OP_MEM, X86_OP_IMM, X86_REG_EAX, X86_REG_ECX, X86_REG_EDX

    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    rows = []
    unresolved = []
    total_states = 0
    total_insns = 0
    truncated_slots = []

    for method in methods:
        start = method["va"]
        end = start + len(method["blob"])
        queue = deque([(start, {X86_REG_ECX: 0}, {}, {}, {}, tuple())])
        seen = set()
        visits = defaultdict(int)
        states = 0
        truncated = False

        while queue:
            if states >= MAX_STATES:
                truncated = True
                break
            pc, owner_alias, ret_alias, vtable_alias, fn_alias, pending_pushes = queue.popleft()
            if not (start <= pc < end):
                unresolved.append((method["slot"], pc, "OUTSIDE_BLOB"))
                continue
            sk = _state_key(pc, owner_alias, ret_alias, vtable_alias, fn_alias, pending_pushes)
            if sk in seen:
                continue
            seen.add(sk)
            visits[pc] += 1
            if visits[pc] > MAX_VISITS_PER_PC:
                continue
            states += 1
            total_states += 1

            off = pc - start
            decoded = list(md.disasm(method["blob"][off:off+15], pc, count=1))
            if not decoded:
                unresolved.append((method["slot"], pc, "DECODE_FAIL"))
                continue
            insn = decoded[0]
            total_insns += 1
            ops = insn.operands
            mnem = insn.mnemonic.lower()
            next_pc = insn.address + insn.size

            def add_reason(target_rva, reason, at=None):
                rows.append({
                    "target_rva": int(target_rva),
                    "reason": reason,
                    "method_rva": method["rva"],
                    "insn_rva": int((at if at is not None else insn.address) - module_base),
                })

            for op in ops:
                if op.type == X86_OP_MEM and op.mem.index == 0 and op.mem.base in ret_alias:
                    helper = int(ret_alias[op.mem.base])
                    add_reason(helper, "RETURN_OBJECT_DEREF")

            if mnem == "call" and ops:
                if ops[0].type == X86_OP_REG and ops[0].reg in fn_alias:
                    helper, _slot = fn_alias[ops[0].reg]
                    add_reason(helper, "RETURN_OBJECT_VCALL")
                elif ops[0].type == X86_OP_MEM and ops[0].mem.index == 0 and ops[0].mem.base in vtable_alias:
                    helper = int(vtable_alias[ops[0].mem.base])
                    add_reason(helper, "RETURN_OBJECT_VCALL")

            no = dict(owner_alias)
            nr = dict(ret_alias)
            nv = dict(vtable_alias)
            nf = dict(fn_alias)
            npush = tuple(pending_pushes)

            if mnem == "push" and ops:
                is_owner = ops[0].type == X86_OP_REG and owner_alias.get(ops[0].reg) == 0
                npush = (npush + (bool(is_owner),))[-8:]

            if mnem == "mov" and len(ops) >= 2 and ops[0].type == X86_OP_REG:
                d, src = ops[0].reg, ops[1]
                no.pop(d, None); nr.pop(d, None); nv.pop(d, None); nf.pop(d, None)
                if src.type == X86_OP_REG:
                    if src.reg in owner_alias: no[d] = owner_alias[src.reg]
                    if src.reg in ret_alias: nr[d] = ret_alias[src.reg]
                    if src.reg in vtable_alias: nv[d] = vtable_alias[src.reg]
                    if src.reg in fn_alias: nf[d] = fn_alias[src.reg]
                elif src.type == X86_OP_MEM and src.mem.index == 0:
                    if src.mem.base in ret_alias and int(src.mem.disp) == 0:
                        nv[d] = ret_alias[src.mem.base]
                    elif src.mem.base in vtable_alias:
                        disp = int(src.mem.disp)
                        if disp >= 0 and disp % 4 == 0:
                            nf[d] = (vtable_alias[src.mem.base], disp // 4)

            elif mnem == "lea" and len(ops) >= 2 and ops[0].type == X86_OP_REG and ops[1].type == X86_OP_MEM:
                d, src = ops[0].reg, ops[1]
                no.pop(d, None); nr.pop(d, None); nv.pop(d, None); nf.pop(d, None)
                if src.mem.index == 0 and src.mem.base in owner_alias:
                    no[d] = int(owner_alias[src.mem.base]) + int(src.mem.disp)

            elif ops and ops[0].type == X86_OP_REG and mnem not in {"cmp", "test", "push", "call", "jmp"} and not mnem.startswith("j"):
                d = ops[0].reg
                no.pop(d, None); nr.pop(d, None); nv.pop(d, None); nf.pop(d, None)

            if mnem == "call" and ops and ops[0].type == X86_OP_IMM:
                target_va = int(ops[0].imm) & 0xFFFFFFFF
                target_rva = target_va - module_base if module_base <= target_va < module_end else None
                if target_rva is not None:
                    if owner_alias.get(X86_REG_ECX) == 0:
                        add_reason(target_rva, "THIS_INVWIN")
                    if any(npush):
                        add_reason(target_rva, "STACK_ARG_INVWIN")
                    nr = {X86_REG_EAX: target_rva}
                else:
                    nr.pop(X86_REG_EAX, None)
                for reg in (X86_REG_ECX, X86_REG_EDX):
                    no.pop(reg, None); nr.pop(reg, None); nv.pop(reg, None); nf.pop(reg, None)
                nv.pop(X86_REG_EAX, None); nf.pop(X86_REG_EAX, None)
                npush = tuple()
            elif mnem == "call":
                for reg in (X86_REG_EAX, X86_REG_ECX, X86_REG_EDX):
                    no.pop(reg, None); nr.pop(reg, None); nv.pop(reg, None); nf.pop(reg, None)
                npush = tuple()

            if mnem.startswith("ret") or mnem in {"iret", "iretd", "int3", "ud2"}:
                continue
            if mnem == "jmp":
                if ops and ops[0].type == X86_OP_IMM:
                    target = int(ops[0].imm) & 0xFFFFFFFF
                    if start <= target < end:
                        queue.append((target, no, nr, nv, nf, npush))
                    else:
                        unresolved.append((method["slot"], target, "DIRECT_JMP_OUTSIDE_BLOB"))
                else:
                    unresolved.append((method["slot"], insn.address, "INDIRECT_JMP"))
                continue
            if mnem.startswith("j") and mnem != "jmp":
                if ops and ops[0].type == X86_OP_IMM:
                    target = int(ops[0].imm) & 0xFFFFFFFF
                    if start <= target < end:
                        queue.append((target, dict(no), dict(nr), dict(nv), dict(nf), tuple(npush)))
                    else:
                        unresolved.append((method["slot"], target, "COND_JMP_OUTSIDE_BLOB"))
                queue.append((next_pc, no, nr, nv, nf, npush))
                continue
            queue.append((next_pc, no, nr, nv, nf, npush))

        if truncated:
            truncated_slots.append(method["slot"])

    return merge_helper_reasons(rows), unresolved, total_states, total_insns, truncated_slots


def disassemble_helper(blob, base_va, module_base):
    from capstone import Cs, CS_ARCH_X86, CS_MODE_32
    from capstone.x86 import X86_OP_IMM
    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    lines = []
    direct_calls = []
    back_edges = 0
    indirect_calls = 0
    cmp_count = 0
    for i, insn in enumerate(md.disasm(blob, base_va)):
        if i >= MAX_DISASM_INSNS:
            break
        lines.append(f"RVA=0x{insn.address-module_base:08X} BYTES={insn.bytes.hex(' ').upper()} ASM={insn.mnemonic} {insn.op_str}".rstrip())
        m = insn.mnemonic.lower()
        if m in {"cmp", "test"}: cmp_count += 1
        if m.startswith("j") and insn.operands and insn.operands[0].type == X86_OP_IMM:
            if int(insn.operands[0].imm) < insn.address:
                back_edges += 1
        if m == "call":
            if insn.operands and insn.operands[0].type == X86_OP_IMM:
                va = int(insn.operands[0].imm) & 0xFFFFFFFF
                direct_calls.append(va - module_base if va >= module_base else va)
            else:
                indirect_calls += 1
    container_hits = [(rva, KNOWN_CONTAINER_HELPERS[rva]) for rva in direct_calls if rva in KNOWN_CONTAINER_HELPERS]
    sem = {
        "find_lookup_hits": 0,
        "container_hits": len(container_hits),
        "objectid_cmp_hits": 0,
        "erase_insert_hits": 0,
        "back_edges": back_edges,
        "indirect_calls": indirect_calls,
        "cmp_count": cmp_count,
        "direct_calls": direct_calls,
        "container_helpers": container_hits,
    }
    return lines, sem


def main():
    ap = argparse.ArgumentParser(description="V16 bounded static direct-helper lane for INVWIN")
    ap.add_argument("--v8-raw", required=True)
    ap.add_argument("--client", required=True)
    ap.add_argument("--output", required=True)
    args = ap.parse_args()

    v8_path = Path(args.v8_raw)
    client_path = Path(args.client)
    out_path = Path(args.output)
    lines = v8_path.read_text(encoding="utf-8", errors="replace").splitlines()
    client_sha = first_value(lines, "CLIENT_SHA256=")
    authority = first_value(lines, "CLIENT_AUTHORITY=")
    module_base = hx(first_value(lines, "MODULE_BASE="))
    module_size = hx(first_value(lines, "MODULE_SIZE="))
    heap_scan = first_value(lines, "HEAP_SCAN=")
    mem_private = first_value(lines, "MEM_PRIVATE_SCAN=")
    memory_write = first_value(lines, "MEMORY_WRITE=")
    methods = parse_methods(lines)

    if client_sha != EXPECTED_SHA or authority != "1" or heap_scan != "NO" or mem_private != "NO" or memory_write != "NO":
        raise SystemExit("V16 V8 authority/safety gate failed")
    if len(methods) != 64:
        raise SystemExit(f"V16 requires 64 V8 methods, got {len(methods)}")
    data = client_path.read_bytes()
    actual_sha = hashlib.sha256(data).hexdigest().upper()
    if actual_sha != EXPECTED_SHA:
        raise SystemExit(f"client authority mismatch: {actual_sha}")
    pe = parse_pe_sections(data)
    if pe["image_base"] != module_base:
        raise SystemExit(f"PE image base mismatch: file=0x{pe['image_base']:08X} v8=0x{module_base:08X}")

    try:
        capstone_package = importlib_metadata.version("capstone")
    except importlib_metadata.PackageNotFoundError:
        raise SystemExit("capstone package not installed")

    helpers, unresolved, states, insns, truncated = discover_helper_reasons(methods, module_base, module_base + module_size)

    o = []
    o.append("MODE=850_INVENTORY_INVWIN_DIRECT_HELPER_STATIC_V16")
    o.append(f"V8_RAW={v8_path}")
    o.append(f"CLIENT={client_path}")
    o.append(f"CAPSTONE_PACKAGE_VERSION={capstone_package}")
    o.append(f"CLIENT_SHA256={actual_sha}")
    o.append("CLIENT_AUTHORITY=1")
    o.append(f"MODULE_BASE=0x{module_base:08X}")
    o.append(f"MODULE_SIZE=0x{module_size:X}")
    o.append(f"PE_SIZE_OF_IMAGE=0x{pe['size_of_image']:X}")
    o.append(f"HELPER_TARGET_COUNT={len(helpers)}")
    o.append(f"CFG_STATES_VISITED={states}")
    o.append(f"CFG_INSTRUCTIONS_DECODED={insns}")
    o.append(f"CFG_TRUNCATED_SLOTS={','.join(str(x) for x in truncated) if truncated else 'NONE'}")
    o.append("HELPER_DEPTH=1")
    o.append("CAPTURE_BYTES_PER_HELPER=384")
    o.append("RUNTIME_ATTACH=NO")
    o.append("STATIC_CLIENT_FILE_READ=YES")
    o.append("HEAP_SCAN=NO")
    o.append("MEM_PRIVATE_SCAN=NO")
    o.append("MEMORY_WRITE=NO")
    o.append("")
    o.append("[HELPER_TARGETS]")

    strong_targets = []
    for h in helpers:
        rva = int(h["target_rva"])
        sec = section_for_rva(rva, pe["sections"])
        file_off = rva_to_file_offset(rva, pe["sections"])
        reasons = ",".join(h["reasons"])
        sec_name = sec["name"] if sec else "NONE"
        exec_text = "YES" if sec and sec["executable"] else "NO"
        off_text = f"0x{file_off:X}" if file_off is not None else "NONE"
        o.append(f"TARGET_RVA=0x{rva:08X} REASONS={reasons} CALLSITES={h['callsite_count']} SECTION={sec_name} EXECUTABLE={exec_text} FILE_OFFSET={off_text}")
        for mrva, irva, reason in h["callsites"]:
            o.append(f"  CALLSITE METHOD_RVA=0x{mrva:08X} INSN_RVA=0x{irva:08X} REASON={reason}")
        if file_off is None or not sec or not sec["executable"]:
            o.append("  CAPTURE=SKIPPED_UNMAPPABLE_OR_NONEXECUTABLE")
            continue
        blob = data[file_off:file_off + HELPER_CAPTURE_BYTES]
        disasm, sem = disassemble_helper(blob, module_base + rva, module_base)
        strong = strong_data_semantics(sem)
        if strong: strong_targets.append(rva)
        ch = ",".join(f"0x{x:08X}:{name}" for x,name in sem["container_helpers"]) or "NONE"
        o.append(
            f"  SEMANTICS BACK_EDGES={sem['back_edges']} INDIRECT_CALLS={sem['indirect_calls']} CMP_TEST={sem['cmp_count']} "
            f"CONTAINER_HELPER_HITS={sem['container_hits']} CONTAINER_HELPERS={ch} STRONG_DATA_SEMANTICS={'YES' if strong else 'NO'}"
        )
        o.append("  RAW=" + blob.hex(" ").upper())
        for line in disasm:
            o.append("  DISASM " + line)

    o.append("")
    o.append("[DECISION]")
    o.append("STATUS=PASS_DIRECT_HELPER_STATIC_CAPTURE")
    o.append("STRONG_DATA_HELPERS=" + (",".join(f"0x{x:08X}" for x in strong_targets) if strong_targets else "NONE"))
    o.append("AUTO_PROMOTION_ALLOWED=NO")
    o.append("ITEM_RECORD_TRAVERSAL_PROVEN=NO")
    o.append("OBJECT_ID_SEMANTICS_PROVEN=NO")
    o.append("FORMAL_WP5=NOT_YET")
    o.append("FORMAL_WP6=NOT_YET")
    o.append(f"UNRESOLVED_CFG_TARGET_COUNT={len(unresolved)}")
    o.append("NEXT=Classify only these depth-1 helper bodies and intersect with independent agent evidence. Generic UI/container traversal is not inventory proof.")
    o.append("HEAP_SCAN=NO")
    o.append("MEM_PRIVATE_SCAN=NO")
    o.append("MEMORY_WRITE=NO")
    out_path.parent.mkdir(parents=True, exist_ok=True)
    out_path.write_text("\n".join(o) + "\n", encoding="utf-8")


if __name__ == "__main__":
    main()
