#!/usr/bin/env python3
import argparse
import importlib.metadata as importlib_metadata
import re
from collections import defaultdict, deque
from pathlib import Path

EXPECTED_SHA = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"
MAX_OWNER_OFFSET = 0x17F
MAX_STATES = 4096
MAX_VISITS_PER_PC = 16
RETIRED_OFFSETS = {0x0F4, 0x148, 0x14C, 0x150, 0x154}
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


def filter_rows(rows):
    return [
        r for r in rows
        if 0 <= int(r["offset"]) <= MAX_OWNER_OFFSET
        and int(r["offset"]) not in RETIRED_OFFSETS
    ]


def dedupe_rows(rows):
    out = []
    seen = set()
    for r in rows:
        key = (
            int(r.get("offset", -1)),
            int(r.get("method_rva", -1)),
            int(r.get("insn_rva", -1)),
            str(r.get("kind", "")),
            str(r.get("detail", "")),
        )
        if key in seen:
            continue
        seen.add(key)
        out.append(r)
    return out


def promotable(s):
    calls = int(s.get("thiscalls", 0)) + int(s.get("direct_calls", 0)) + int(s.get("virtual_calls", 0))
    lifecycle = (
        int(s.get("assigns", 0)) > 0
        and int(s.get("reads", 0)) > 0
        and int(s.get("clears", 0)) > 0
        and calls > 0
    )
    data_sem = int(s.get("data_helpers", 0)) > 0 and int(s.get("lookup_cmp", 0)) > 0
    return bool(lifecycle or data_sem)


def candidate_score(s):
    calls = int(s.get("direct_calls", 0)) + int(s.get("virtual_calls", 0))
    score = 0
    if s.get("aligned4"): score += 1
    if int(s.get("assigns", 0)): score += 2
    if int(s.get("clears", 0)): score += 2
    if int(s.get("reads", 0)): score += 1
    if int(s.get("null_checks", 0)): score += 1
    if calls: score += min(4, calls)
    if int(s.get("methods", 0)) >= 2: score += 2
    if int(s.get("data_helpers", 0)): score += 2
    if promotable(s): score += 6
    return score


def _copy(d):
    return dict(d)


def _state_key(pc, addr_alias, child_alias, vtable_alias, fn_alias, stack_addr, stack_child, stack_vtable, stack_fn):
    def norm(d):
        return tuple(sorted((int(k), str(v)) for k, v in d.items()))
    return (pc, norm(addr_alias), norm(child_alias), norm(vtable_alias), norm(fn_alias), norm(stack_addr), norm(stack_child), norm(stack_vtable), norm(stack_fn))


def analyze_method(method, module_base, module_end):
    import capstone
    from capstone import Cs, CS_ARCH_X86, CS_MODE_32
    from capstone.x86 import (
        X86_OP_REG, X86_OP_MEM, X86_OP_IMM,
        X86_REG_EAX, X86_REG_ECX, X86_REG_EDX, X86_REG_EBP,
    )

    VOLATILE = {X86_REG_EAX, X86_REG_ECX, X86_REG_EDX}
    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    blob = method["blob"]
    start = method["va"]
    end = start + len(blob)
    events = []
    unresolved = []
    state_count = 0
    insn_count = 0
    truncated = False
    visits = defaultdict(int)
    seen_states = set()
    queue = deque()
    queue.append((start, {X86_REG_ECX: 0}, {}, {}, {}, {}, {}, {}, {}))

    def add_event(offset, insn, kind, detail=""):
        if 0 <= offset <= MAX_OWNER_OFFSET:
            events.append({
                "offset": int(offset),
                "slot": method["slot"],
                "method_rva": method["rva"],
                "insn_rva": int(insn.address - module_base),
                "kind": kind,
                "detail": detail,
                "asm": f"{insn.mnemonic} {insn.op_str}".strip(),
            })

    def clear_reg(reg, *maps):
        for m in maps:
            m.pop(reg, None)

    while queue:
        if state_count >= MAX_STATES:
            truncated = True
            break
        pc, addr_alias, child_alias, vtable_alias, fn_alias, stack_addr, stack_child, stack_vtable, stack_fn = queue.popleft()
        if not (start <= pc < end):
            unresolved.append((pc, "OUTSIDE_BLOB"))
            continue
        key = _state_key(pc, addr_alias, child_alias, vtable_alias, fn_alias, stack_addr, stack_child, stack_vtable, stack_fn)
        if key in seen_states:
            continue
        seen_states.add(key)
        visits[pc] += 1
        if visits[pc] > MAX_VISITS_PER_PC:
            continue
        state_count += 1

        off = pc - start
        decoded = list(md.disasm(blob[off:off+15], pc, count=1))
        if not decoded:
            unresolved.append((pc, "DECODE_FAIL"))
            continue
        insn = decoded[0]
        insn_count += 1
        ops = insn.operands
        mnem = insn.mnemonic.lower()
        next_pc = insn.address + insn.size

        for idx, op in enumerate(ops):
            if op.type != X86_OP_MEM or op.mem.index != 0 or op.mem.base not in addr_alias:
                continue
            field_off = int(addr_alias[op.mem.base]) + int(op.mem.disp)
            if not (0 <= field_off <= MAX_OWNER_OFFSET):
                continue
            is_write = idx == 0 and mnem in {"mov", "add", "sub", "and", "or", "xor", "inc", "dec", "pop"}
            add_event(field_off, insn, "WRITE" if is_write else "READ")

            if mnem in {"cmp", "test"}:
                zeroish = any(other.type == X86_OP_IMM and int(other.imm) == 0 for other in ops)
                if zeroish:
                    add_event(field_off, insn, "NULL_CHECK")

            if is_write and mnem == "mov" and idx == 0 and len(ops) >= 2:
                src = ops[1]
                if src.type == X86_OP_IMM and int(src.imm) == 0:
                    add_event(field_off, insn, "CLEAR_ZERO")
                elif src.type in {X86_OP_REG, X86_OP_MEM}:
                    add_event(field_off, insn, "ASSIGN_POINTER")

        if mnem in {"test", "cmp"} and len(ops) >= 2:
            regs = [op.reg for op in ops if op.type == X86_OP_REG]
            imm_zero = any(op.type == X86_OP_IMM and int(op.imm) == 0 for op in ops)
            for reg in regs:
                if reg in child_alias and (imm_zero or (mnem == "test" and len(regs) == 2 and regs[0] == regs[1])):
                    add_event(int(child_alias[reg]), insn, "NULL_CHECK", "REGISTER_ORIGIN")

        if mnem == "call" and ops:
            called = False
            if ops[0].type == X86_OP_IMM and X86_REG_ECX in child_alias:
                target_va = int(ops[0].imm) & 0xFFFFFFFF
                target_rva = target_va - module_base if module_base <= target_va < module_end else None
                detail = f"TARGET_RVA=0x{target_rva:08X}" if target_rva is not None else f"TARGET_VA=0x{target_va:08X}"
                if target_rva in KNOWN_CONTAINER_HELPERS:
                    detail += f" HELPER={KNOWN_CONTAINER_HELPERS[target_rva]}"
                add_event(int(child_alias[X86_REG_ECX]), insn, "DIRECT_CALL", detail)
                called = True
            if ops[0].type == X86_OP_REG and ops[0].reg in fn_alias:
                field_off, slot = fn_alias[ops[0].reg]
                add_event(int(field_off), insn, "VIRTUAL_CALL", f"VIRTUAL_SLOT={int(slot)}")
                called = True
            if ops[0].type == X86_OP_MEM and ops[0].mem.index == 0 and ops[0].mem.base in vtable_alias:
                disp = int(ops[0].mem.disp)
                if disp >= 0 and disp % 4 == 0:
                    add_event(int(vtable_alias[ops[0].mem.base]), insn, "VIRTUAL_CALL", f"VIRTUAL_SLOT={disp//4}")
                    called = True
            if not called and X86_REG_ECX in child_alias:
                add_event(int(child_alias[X86_REG_ECX]), insn, "THISCALL_UNRESOLVED")

        na, nc, nv, nf = _copy(addr_alias), _copy(child_alias), _copy(vtable_alias), _copy(fn_alias)
        sa, sc, sv, sf = _copy(stack_addr), _copy(stack_child), _copy(stack_vtable), _copy(stack_fn)

        if mnem == "mov" and len(ops) >= 2:
            dst, src = ops[0], ops[1]
            if dst.type == X86_OP_REG:
                d = dst.reg
                clear_reg(d, na, nc, nv, nf)
                if src.type == X86_OP_REG:
                    if src.reg in addr_alias: na[d] = addr_alias[src.reg]
                    if src.reg in child_alias: nc[d] = child_alias[src.reg]
                    if src.reg in vtable_alias: nv[d] = vtable_alias[src.reg]
                    if src.reg in fn_alias: nf[d] = fn_alias[src.reg]
                elif src.type == X86_OP_MEM and src.mem.index == 0:
                    if src.mem.base == X86_REG_EBP:
                        k = int(src.mem.disp)
                        if k in stack_addr: na[d] = stack_addr[k]
                        if k in stack_child: nc[d] = stack_child[k]
                        if k in stack_vtable: nv[d] = stack_vtable[k]
                        if k in stack_fn: nf[d] = stack_fn[k]
                    elif src.mem.base in addr_alias:
                        field_off = int(addr_alias[src.mem.base]) + int(src.mem.disp)
                        if 0 <= field_off <= MAX_OWNER_OFFSET:
                            nc[d] = field_off
                    elif src.mem.base in child_alias and int(src.mem.disp) == 0:
                        nv[d] = int(child_alias[src.mem.base])
                    elif src.mem.base in vtable_alias:
                        disp = int(src.mem.disp)
                        if disp >= 0 and disp % 4 == 0:
                            nf[d] = (int(vtable_alias[src.mem.base]), disp // 4)
            elif dst.type == X86_OP_MEM and dst.mem.base == X86_REG_EBP and dst.mem.index == 0:
                k = int(dst.mem.disp)
                for stack_map in (sa, sc, sv, sf): stack_map.pop(k, None)
                if src.type == X86_OP_REG:
                    if src.reg in addr_alias: sa[k] = addr_alias[src.reg]
                    if src.reg in child_alias: sc[k] = child_alias[src.reg]
                    if src.reg in vtable_alias: sv[k] = vtable_alias[src.reg]
                    if src.reg in fn_alias: sf[k] = fn_alias[src.reg]

        elif mnem == "lea" and len(ops) >= 2 and ops[0].type == X86_OP_REG and ops[1].type == X86_OP_MEM:
            d, src = ops[0].reg, ops[1]
            clear_reg(d, na, nc, nv, nf)
            if src.mem.index == 0 and src.mem.base in addr_alias:
                na[d] = int(addr_alias[src.mem.base]) + int(src.mem.disp)
            elif src.mem.base == X86_REG_EBP and src.mem.index == 0 and int(src.mem.disp) in stack_addr:
                na[d] = int(stack_addr[int(src.mem.disp)])
        elif ops and ops[0].type == X86_OP_REG and mnem not in {"cmp", "test", "push", "call", "jmp"} and not mnem.startswith("j"):
            clear_reg(ops[0].reg, na, nc, nv, nf)

        if mnem == "call":
            for reg in VOLATILE:
                clear_reg(reg, na, nc, nv, nf)

        if mnem.startswith("ret") or mnem in {"iret", "iretd", "int3", "ud2"}:
            continue
        if mnem == "jmp":
            if ops and ops[0].type == X86_OP_IMM:
                target = int(ops[0].imm) & 0xFFFFFFFF
                if start <= target < end:
                    queue.append((target, na, nc, nv, nf, sa, sc, sv, sf))
                else:
                    unresolved.append((target, "DIRECT_JMP_OUTSIDE_BLOB"))
            else:
                unresolved.append((insn.address, "INDIRECT_JMP"))
            continue
        if mnem.startswith("j") and mnem != "jmp":
            if ops and ops[0].type == X86_OP_IMM:
                target = int(ops[0].imm) & 0xFFFFFFFF
                if start <= target < end:
                    queue.append((target, _copy(na), _copy(nc), _copy(nv), _copy(nf), _copy(sa), _copy(sc), _copy(sv), _copy(sf)))
                else:
                    unresolved.append((target, "COND_JMP_OUTSIDE_BLOB"))
            queue.append((next_pc, na, nc, nv, nf, sa, sc, sv, sf))
            continue
        queue.append((next_pc, na, nc, nv, nf, sa, sc, sv, sf))

    return events, unresolved, state_count, insn_count, truncated


def summarize(events):
    by = defaultdict(lambda: {
        "methods_set": set(), "reads":0, "writes":0, "null_checks":0,
        "assigns":0, "clears":0, "direct_calls":0, "virtual_calls":0,
        "thiscalls":0, "data_helpers":0, "lookup_cmp":0,
        "virtual_slots":set(), "direct_targets":set(), "aligned4":False,
    })
    for e in events:
        off = int(e["offset"])
        s = by[off]
        s["methods_set"].add(int(e["slot"]))
        s["aligned4"] = (off % 4 == 0)
        k = e["kind"]
        if k == "READ": s["reads"] += 1
        elif k == "WRITE": s["writes"] += 1
        elif k == "NULL_CHECK": s["null_checks"] += 1
        elif k == "ASSIGN_POINTER": s["assigns"] += 1
        elif k == "CLEAR_ZERO": s["clears"] += 1
        elif k == "DIRECT_CALL":
            s["direct_calls"] += 1; s["thiscalls"] += 1
            mt = re.search(r"TARGET_RVA=0x([0-9A-Fa-f]+)", e["detail"])
            if mt: s["direct_targets"].add(int(mt.group(1),16))
            if "HELPER=" in e["detail"]: s["data_helpers"] += 1
        elif k == "VIRTUAL_CALL":
            s["virtual_calls"] += 1; s["thiscalls"] += 1
            ms = re.search(r"VIRTUAL_SLOT=(\d+)", e["detail"])
            if ms: s["virtual_slots"].add(int(ms.group(1)))
        elif k == "THISCALL_UNRESOLVED": s["thiscalls"] += 1
    out = {}
    for off, s in by.items():
        s["methods"] = len(s.pop("methods_set"))
        s["score"] = candidate_score(s)
        s["promotable"] = promotable(s) and s["aligned4"]
        out[off] = s
    return out


def main():
    ap = argparse.ArgumentParser(description="Offline INVWIN owner-field lifecycle classifier V15")
    ap.add_argument("--input", required=True)
    ap.add_argument("--output", required=True)
    args = ap.parse_args()

    inp = Path(args.input)
    outp = Path(args.output)
    lines = inp.read_text(encoding="utf-8", errors="replace").splitlines()
    client_sha = first_value(lines, "CLIENT_SHA256=")
    authority = first_value(lines, "CLIENT_AUTHORITY=")
    module_base = hx(first_value(lines, "MODULE_BASE="))
    module_size = hx(first_value(lines, "MODULE_SIZE="))
    heap_scan = first_value(lines, "HEAP_SCAN=")
    mem_private = first_value(lines, "MEM_PRIVATE_SCAN=")
    memory_write = first_value(lines, "MEMORY_WRITE=")
    methods = parse_methods(lines)

    if client_sha != EXPECTED_SHA or authority != "1" or heap_scan != "NO" or mem_private != "NO" or memory_write != "NO":
        raise SystemExit("V15 authority/safety gate failed")
    if len(methods) != 64:
        raise SystemExit(f"V15 requires 64 captured INVWIN methods, got {len(methods)}")

    try:
        capstone_package = importlib_metadata.version("capstone")
    except importlib_metadata.PackageNotFoundError:
        raise SystemExit("capstone package not installed")

    module_end = module_base + module_size
    all_events = []
    unresolved_total = 0
    states_total = 0
    insns_total = 0
    truncated_slots = []
    for method in methods:
        ev, unresolved, states, insns, truncated = analyze_method(method, module_base, module_end)
        all_events.extend(ev)
        unresolved_total += len(unresolved)
        states_total += states
        insns_total += insns
        if truncated: truncated_slots.append(method["slot"])

    raw_event_count = len(all_events)
    all_events = dedupe_rows(filter_rows(all_events))
    summary = summarize(all_events)
    ranked = sorted(summary.items(), key=lambda kv: (-kv[1]["score"], kv[0]))
    candidates = [(off,s) for off,s in ranked if s["promotable"]]
    best = candidates[0][0] if candidates else None

    o = []
    o.append("MODE=850_INVENTORY_INVWIN_OWNER_FIELD_LIFECYCLE_V15")
    o.append(f"INPUT={inp}")
    o.append(f"CAPSTONE_PACKAGE_VERSION={capstone_package}")
    o.append(f"CLIENT_SHA256={client_sha}")
    o.append(f"CLIENT_AUTHORITY={authority}")
    o.append(f"MODULE_BASE=0x{module_base:08X}")
    o.append(f"MODULE_SIZE=0x{module_size:X}")
    o.append("VTABLE_METHODS=64")
    o.append(f"CFG_STATES_VISITED={states_total}")
    o.append(f"CFG_INSTRUCTIONS_DECODED={insns_total}")
    o.append(f"CFG_TRUNCATED_SLOTS={','.join(str(x) for x in truncated_slots) if truncated_slots else 'NONE'}")
    o.append(f"RAW_EVENT_COUNT={raw_event_count}")
    o.append(f"UNIQUE_EVENT_COUNT={len(all_events)}")
    o.append("DEDUP_KEY=OFFSET+METHOD_RVA+INSN_RVA+KIND+DETAIL")
    o.append("OWNER_OFFSET_SCOPE=0x000..0x17F")
    o.append("EXCLUDED_OFFSETS=0x0F4,0x148,0x14C,0x150,0x154")
    o.append("RUNTIME_ATTACH=NO")
    o.append("HEAP_SCAN=NO")
    o.append("MEM_PRIVATE_SCAN=NO")
    o.append("MEMORY_WRITE=NO")
    o.append("")
    o.append("[OFFSET_SUMMARY]")
    for off, s in ranked:
        vs = ",".join(str(x) for x in sorted(s["virtual_slots"])) or "NONE"
        dt = ",".join(f"0x{x:08X}" for x in sorted(s["direct_targets"])) or "NONE"
        o.append(
            f"OFFSET=0x{off:03X} ALIGN4={'YES' if s['aligned4'] else 'NO'} METHODS={s['methods']} "
            f"READS={s['reads']} WRITES={s['writes']} NULL_CHECKS={s['null_checks']} ASSIGNS={s['assigns']} CLEARS={s['clears']} "
            f"DIRECT_CALLS={s['direct_calls']} VIRTUAL_CALLS={s['virtual_calls']} THISCALLS={s['thiscalls']} "
            f"CONTAINER_HELPER_HITS={s['data_helpers']} VIRTUAL_SLOTS={vs} DIRECT_TARGETS={dt} "
            f"SCORE={s['score']} CANDIDATE={'YES' if s['promotable'] else 'NO'}"
        )

    o.append("")
    o.append("[CANDIDATE_EVIDENCE]")
    if candidates:
        candidate_offsets = {off for off,_ in candidates}
        for e in sorted((e for e in all_events if e["offset"] in candidate_offsets), key=lambda e:(e["offset"],e["method_rva"],e["insn_rva"],e["kind"],e["detail"])):
            o.append(
                f"OFFSET=0x{e['offset']:03X} KIND={e['kind']} INVWIN_SLOT={e['slot']:02d} METHOD_RVA=0x{e['method_rva']:08X} "
                f"INSN_RVA=0x{e['insn_rva']:08X} DETAIL={e['detail'] or 'NONE'} ASM={e['asm']}"
            )
    else:
        o.append("NONE")

    o.append("")
    o.append("[DECISION]")
    o.append("STATUS=PASS_OWNER_FIELD_LIFECYCLE_CLASSIFIED")
    o.append("CANDIDATE_OFFSETS=" + (",".join(f"0x{off:03X}" for off,_ in candidates) if candidates else "NONE"))
    o.append("BEST_CANDIDATE=" + (f"0x{best:03X}" if best is not None else "NONE"))
    o.append("BEST_CANDIDATE_IS_INVENTORY_PROOF=NO")
    o.append("ITEM_RECORD_TRAVERSAL_PROVEN=NO")
    o.append("OBJECT_ID_SEMANTICS_PROVEN=NO")
    o.append("FORMAL_WP5=NOT_YET")
    o.append("FORMAL_WP6=NOT_YET")
    o.append(f"UNRESOLVED_CFG_TARGET_COUNT={unresolved_total}")
    o.append("NEXT=Intersect only V15 candidate offsets with independent agent semantics; inspect exact helper/call slots before any new runtime probe.")
    o.append("HEAP_SCAN=NO")
    o.append("MEM_PRIVATE_SCAN=NO")
    o.append("MEMORY_WRITE=NO")

    outp.parent.mkdir(parents=True, exist_ok=True)
    outp.write_text("\n".join(o)+"\n", encoding="utf-8")


if __name__ == "__main__":
    main()
