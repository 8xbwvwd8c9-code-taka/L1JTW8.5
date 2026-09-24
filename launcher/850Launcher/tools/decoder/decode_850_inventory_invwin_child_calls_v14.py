#!/usr/bin/env python3
import argparse
import re
from collections import defaultdict, deque
from pathlib import Path

import capstone
from capstone import Cs, CS_ARCH_X86, CS_MODE_32
from capstone.x86 import *

EXPECTED_SHA = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"
TARGET_OFFSETS = (0x148, 0x14C, 0x150, 0x154)
MAX_STATES = 8192
MAX_VISITS_PER_PC = 16
VOLATILE = {X86_REG_EAX, X86_REG_ECX, X86_REG_EDX}

METHOD_RE = re.compile(
    r"^METHOD SLOT=(?P<slot>\d+) VA=0x(?P<va>[0-9A-Fa-f]+) RVA=0x(?P<rva>[0-9A-Fa-f]+) .*? BYTES=(?P<bytes>[0-9A-Fa-f ]+)$"
)
VTABLE_SLOT_RE = re.compile(
    r"^VTABLE_SLOT\[(?P<slot>\d+)\]=VA:0x(?P<va>[0-9A-Fa-f]+) RVA:0x(?P<rva>[0-9A-Fa-f]+) CLASS:(?P<class>\S+)$"
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


def parse_v13(lines):
    children = {}
    current = None
    for raw in lines:
        line = raw.strip()
        if line.startswith("[CHILD_") and line.endswith("]"):
            current = int(line[len("[CHILD_"):-1], 16)
            children[current] = {"offset": current, "object": None, "vtable": None, "slots": {}}
            continue
        if current is None:
            continue
        row = children[current]
        if line.startswith("OBJECT=0x"):
            row["object"] = int(line.split("=", 1)[1], 16)
        elif line.startswith("VTABLE=0x"):
            row["vtable"] = int(line.split("=", 1)[1], 16)
        else:
            m = VTABLE_SLOT_RE.match(line)
            if m:
                row["slots"][int(m.group("slot"))] = int(m.group("rva"), 16)
    return children


def freeze_map(d):
    return tuple(sorted((int(k), v if isinstance(v, tuple) else int(v)) for k, v in d.items()))


def state_key(pc, owner, child, vtable, funcptr, s_owner, s_child, s_vtable, s_funcptr):
    return (
        pc, freeze_map(owner), freeze_map(child), freeze_map(vtable), freeze_map(funcptr),
        freeze_map(s_owner), freeze_map(s_child), freeze_map(s_vtable), freeze_map(s_funcptr)
    )


def clone_state(*maps):
    return tuple(dict(x) for x in maps)


def clear_reg(reg, owner, child, vtable, funcptr):
    owner.pop(reg, None)
    child.pop(reg, None)
    vtable.pop(reg, None)
    funcptr.pop(reg, None)


def copy_reg(dst, src, owner, child, vtable, funcptr):
    clear_reg(dst, owner, child, vtable, funcptr)
    if src in owner:
        owner[dst] = owner[src]
    if src in child:
        child[dst] = child[src]
    if src in vtable:
        vtable[dst] = vtable[src]
    if src in funcptr:
        funcptr[dst] = funcptr[src]


def save_stack(key, src, owner, child, vtable, funcptr, s_owner, s_child, s_vtable, s_funcptr):
    for m in (s_owner, s_child, s_vtable, s_funcptr):
        m.pop(key, None)
    if src in owner:
        s_owner[key] = owner[src]
    if src in child:
        s_child[key] = child[src]
    if src in vtable:
        s_vtable[key] = vtable[src]
    if src in funcptr:
        s_funcptr[key] = funcptr[src]


def load_stack(dst, key, owner, child, vtable, funcptr, s_owner, s_child, s_vtable, s_funcptr):
    clear_reg(dst, owner, child, vtable, funcptr)
    if key in s_owner:
        owner[dst] = s_owner[key]
    if key in s_child:
        child[dst] = s_child[key]
    if key in s_vtable:
        vtable[dst] = s_vtable[key]
    if key in s_funcptr:
        funcptr[dst] = s_funcptr[key]


def decode_method(md, method, module_base, module_end):
    blob = method["blob"]
    start = method["va"]
    end = start + len(blob)
    queue = deque()
    queue.append((start, {X86_REG_ECX: 0}, {}, {}, {}, {}, {}, {}, {}))
    visited = set()
    visits = defaultdict(int)
    events = []
    unresolved = []
    states = 0

    while queue and states < MAX_STATES:
        pc, owner, child, vtable, funcptr, s_owner, s_child, s_vtable, s_funcptr = queue.popleft()
        if pc < start or pc >= end:
            unresolved.append((pc, "OUTSIDE_BLOB"))
            continue
        key = state_key(pc, owner, child, vtable, funcptr, s_owner, s_child, s_vtable, s_funcptr)
        if key in visited:
            continue
        visited.add(key)
        visits[pc] += 1
        if visits[pc] > MAX_VISITS_PER_PC:
            continue
        states += 1

        off = pc - start
        insns = list(md.disasm(blob[off:off+15], pc, count=1))
        if not insns:
            unresolved.append((pc, "DECODE_FAIL"))
            continue
        insn = insns[0]
        ops = insn.operands
        next_pc = insn.address + insn.size
        asm = f"{insn.mnemonic} {insn.op_str}".strip()

        # Record direct owner-field references to the four code-proven child pointer fields.
        for op in ops:
            if op.type != X86_OP_MEM or op.mem.index != 0:
                continue
            base_reg = op.mem.base
            if base_reg in owner:
                owner_off = int(owner[base_reg]) + int(op.mem.disp)
                if owner_off in TARGET_OFFSETS:
                    events.append({
                        "kind": "FIELD_ACCESS", "offset": owner_off,
                        "slot": method["slot"], "method_rva": method["rva"],
                        "insn_rva": int(insn.address - module_base), "detail": asm,
                    })

        # Record child-object calls before call clobbering.
        if insn.mnemonic == "call" and ops:
            callop = ops[0]
            ecx_child = child.get(X86_REG_ECX)
            if callop.type == X86_OP_IMM and ecx_child in TARGET_OFFSETS:
                target_va = int(callop.imm) & 0xFFFFFFFF
                target_rva = target_va - module_base if module_base <= target_va < module_end else None
                events.append({
                    "kind": "DIRECT_CHILD_CALL", "offset": ecx_child,
                    "slot": method["slot"], "method_rva": method["rva"],
                    "insn_rva": int(insn.address - module_base),
                    "target_rva": target_rva, "detail": asm,
                })
            elif callop.type == X86_OP_MEM and callop.mem.index == 0:
                base_reg = callop.mem.base
                if base_reg in vtable:
                    off_child = vtable[base_reg]
                    disp = int(callop.mem.disp)
                    vslot = disp // 4 if disp >= 0 and disp % 4 == 0 else None
                    events.append({
                        "kind": "VIRTUAL_CHILD_CALL", "offset": off_child,
                        "slot": method["slot"], "method_rva": method["rva"],
                        "insn_rva": int(insn.address - module_base),
                        "virtual_slot": vslot, "detail": asm,
                    })
            elif callop.type == X86_OP_REG and callop.reg in funcptr:
                off_child, vslot = funcptr[callop.reg]
                events.append({
                    "kind": "VIRTUAL_CHILD_CALL", "offset": off_child,
                    "slot": method["slot"], "method_rva": method["rva"],
                    "insn_rva": int(insn.address - module_base),
                    "virtual_slot": vslot, "detail": asm,
                })

        # Apply symbolic alias effects.
        n_owner, n_child, n_vtable, n_funcptr, ns_owner, ns_child, ns_vtable, ns_funcptr = clone_state(
            owner, child, vtable, funcptr, s_owner, s_child, s_vtable, s_funcptr
        )

        handled_dst = False
        if insn.mnemonic == "mov" and len(ops) >= 2:
            dst, src = ops[0], ops[1]
            if dst.type == X86_OP_REG:
                handled_dst = True
                if src.type == X86_OP_REG:
                    copy_reg(dst.reg, src.reg, n_owner, n_child, n_vtable, n_funcptr)
                elif src.type == X86_OP_MEM and src.mem.index == 0:
                    clear_reg(dst.reg, n_owner, n_child, n_vtable, n_funcptr)
                    if src.mem.base == X86_REG_EBP:
                        load_stack(dst.reg, int(src.mem.disp), n_owner, n_child, n_vtable, n_funcptr,
                                   ns_owner, ns_child, ns_vtable, ns_funcptr)
                    elif src.mem.base in owner:
                        owner_off = int(owner[src.mem.base]) + int(src.mem.disp)
                        if owner_off in TARGET_OFFSETS:
                            n_child[dst.reg] = owner_off
                    elif src.mem.base in child and int(src.mem.disp) == 0:
                        n_vtable[dst.reg] = child[src.mem.base]
                    elif src.mem.base in vtable:
                        disp = int(src.mem.disp)
                        if disp >= 0 and disp % 4 == 0:
                            n_funcptr[dst.reg] = (vtable[src.mem.base], disp // 4)
                else:
                    clear_reg(dst.reg, n_owner, n_child, n_vtable, n_funcptr)
            elif dst.type == X86_OP_MEM and dst.mem.base == X86_REG_EBP and dst.mem.index == 0 and src.type == X86_OP_REG:
                save_stack(int(dst.mem.disp), src.reg, n_owner, n_child, n_vtable, n_funcptr,
                           ns_owner, ns_child, ns_vtable, ns_funcptr)

        elif insn.mnemonic == "lea" and len(ops) >= 2 and ops[0].type == X86_OP_REG and ops[1].type == X86_OP_MEM:
            handled_dst = True
            dst, src = ops[0], ops[1]
            clear_reg(dst.reg, n_owner, n_child, n_vtable, n_funcptr)
            if src.mem.index == 0 and src.mem.base in owner:
                n_owner[dst.reg] = int(owner[src.mem.base]) + int(src.mem.disp)
            elif src.mem.base == X86_REG_EBP and src.mem.index == 0:
                load_stack(dst.reg, int(src.mem.disp), n_owner, n_child, n_vtable, n_funcptr,
                           ns_owner, ns_child, ns_vtable, ns_funcptr)

        if not handled_dst and ops and ops[0].type == X86_OP_REG:
            if insn.mnemonic not in {"cmp", "test", "push", "call", "jmp"} and not insn.mnemonic.startswith("j"):
                clear_reg(ops[0].reg, n_owner, n_child, n_vtable, n_funcptr)

        if insn.mnemonic == "call":
            for r in VOLATILE:
                clear_reg(r, n_owner, n_child, n_vtable, n_funcptr)

        mnem = insn.mnemonic.lower()
        if mnem.startswith("ret") or mnem in {"iret", "iretd", "int3", "ud2"}:
            continue

        if mnem == "jmp":
            if ops and ops[0].type == X86_OP_IMM:
                target = int(ops[0].imm) & 0xFFFFFFFF
                if start <= target < end:
                    queue.append((target, n_owner, n_child, n_vtable, n_funcptr, ns_owner, ns_child, ns_vtable, ns_funcptr))
                else:
                    unresolved.append((target, "DIRECT_JMP_OUTSIDE_BLOB"))
            else:
                unresolved.append((insn.address, "INDIRECT_JMP"))
            continue

        if mnem.startswith("j") and mnem != "jmp":
            if ops and ops[0].type == X86_OP_IMM:
                target = int(ops[0].imm) & 0xFFFFFFFF
                if start <= target < end:
                    queue.append((target, *clone_state(n_owner, n_child, n_vtable, n_funcptr, ns_owner, ns_child, ns_vtable, ns_funcptr)))
                else:
                    unresolved.append((target, "COND_JMP_OUTSIDE_BLOB"))
            queue.append((next_pc, n_owner, n_child, n_vtable, n_funcptr, ns_owner, ns_child, ns_vtable, ns_funcptr))
            continue

        queue.append((next_pc, n_owner, n_child, n_vtable, n_funcptr, ns_owner, ns_child, ns_vtable, ns_funcptr))

    return events, unresolved, states, bool(queue)


def main():
    ap = argparse.ArgumentParser(description="V14 parent->child semantic analysis for INVWIN code-proven child pointers.")
    ap.add_argument("--v8-raw", required=True)
    ap.add_argument("--v13", required=True)
    ap.add_argument("--output", required=True)
    args = ap.parse_args()

    v8_path = Path(args.v8_raw)
    v13_path = Path(args.v13)
    out_path = Path(args.output)
    v8 = v8_path.read_text(encoding="utf-8", errors="replace").splitlines()
    v13 = v13_path.read_text(encoding="utf-8", errors="replace").splitlines()

    sha8 = first_value(v8, "CLIENT_SHA256=")
    sha13 = first_value(v13, "CLIENT_SHA256=")
    auth8 = first_value(v8, "CLIENT_AUTHORITY=")
    auth13 = first_value(v13, "CLIENT_AUTHORITY=")
    module_base = hx(first_value(v8, "MODULE_BASE="))
    module_size = hx(first_value(v8, "MODULE_SIZE="))
    methods = parse_methods(v8)
    children = parse_v13(v13)

    if not (sha8 == EXPECTED_SHA == sha13 and auth8 == "1" and auth13 == "1"):
        raise SystemExit("V14 client authority gate failed")
    if len(methods) != 64:
        raise SystemExit(f"V14 requires 64 captured INVWIN vtable methods, got {len(methods)}")
    if first_value(v8, "HEAP_SCAN=") != "NO" or first_value(v8, "MEM_PRIVATE_SCAN=") != "NO" or first_value(v8, "MEMORY_WRITE=") != "NO":
        raise SystemExit("V14 V8 safety gate failed")
    if first_value(v13, "HEAP_SCAN=") != "NO" or first_value(v13, "MEM_PRIVATE_SCAN=") != "NO" or first_value(v13, "MEMORY_WRITE=") != "NO":
        raise SystemExit("V14 V13 safety gate failed")

    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    module_end = module_base + module_size
    all_events = []
    all_unresolved = []
    total_states = 0
    truncated = []

    for method in methods:
        events, unresolved, states, was_truncated = decode_method(md, method, module_base, module_end)
        all_events.extend(events)
        all_unresolved.extend((method["slot"], x, why) for x, why in unresolved)
        total_states += states
        if was_truncated:
            truncated.append(method["slot"])

    summary = {}
    for off in TARGET_OFFSETS:
        summary[off] = {
            "field_methods": set(), "field_accesses": 0,
            "direct_methods": set(), "direct_calls": 0, "direct_targets": set(),
            "virtual_methods": set(), "virtual_calls": 0, "virtual_slots": set(),
        }
    for e in all_events:
        row = summary[e["offset"]]
        if e["kind"] == "FIELD_ACCESS":
            row["field_methods"].add(e["slot"]); row["field_accesses"] += 1
        elif e["kind"] == "DIRECT_CHILD_CALL":
            row["direct_methods"].add(e["slot"]); row["direct_calls"] += 1
            if e.get("target_rva") is not None:
                row["direct_targets"].add(e["target_rva"])
        elif e["kind"] == "VIRTUAL_CHILD_CALL":
            row["virtual_methods"].add(e["slot"]); row["virtual_calls"] += 1
            if e.get("virtual_slot") is not None:
                row["virtual_slots"].add(e["virtual_slot"])

    # This is only a usage-density indicator, not an inventory semantic proof.
    ranked = sorted(
        TARGET_OFFSETS,
        key=lambda o: (
            summary[o]["virtual_calls"] * 4 + summary[o]["direct_calls"] * 3 + summary[o]["field_accesses"],
            summary[o]["virtual_calls"], summary[o]["direct_calls"], summary[o]["field_accesses"]
        ),
        reverse=True,
    )
    most_used = ranked[0] if ranked else None

    out = []
    out.append("MODE=850_INVENTORY_INVWIN_CHILD_CALLS_V14")
    out.append(f"V8_RAW={v8_path}")
    out.append(f"V13={v13_path}")
    out.append(f"CAPSTONE_PACKAGE_VERSION={capstone.__version__}")
    out.append(f"CLIENT_SHA256={sha8}")
    out.append("CLIENT_AUTHORITY=1")
    out.append(f"MODULE_BASE=0x{module_base:08X}")
    out.append(f"MODULE_SIZE=0x{module_size:X}")
    out.append(f"V8_PID={first_value(v8, 'PID=')}")
    out.append(f"V13_PID={first_value(v13, 'PID=')}")
    out.append(f"PROCESS_INSTANCE_MATCH={'YES' if first_value(v8, 'PID=') == first_value(v13, 'PID=') and first_value(v8, 'PROCESS_START_UTC=') == first_value(v13, 'PROCESS_START_UTC=') else 'NO'}")
    out.append("TARGET_OFFSETS=0x148,0x14C,0x150,0x154")
    out.append(f"CFG_STATES_VISITED={total_states}")
    out.append(f"CFG_TRUNCATED_SLOTS={','.join(f'{x:02d}' for x in truncated) if truncated else 'NONE'}")
    out.append("RUNTIME_ATTACH=NO")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")
    out.append("")

    out.append("[V13_CHILD_IDENTITY]")
    for off in TARGET_OFFSETS:
        c = children.get(off, {})
        obj = c.get("object")
        vt = c.get("vtable")
        out.append(
            f"OFFSET=0x{off:03X} OBJECT={'0x%08X' % obj if obj is not None else 'UNKNOWN'} "
            f"VTABLE={'0x%08X' % vt if vt is not None else 'UNKNOWN'} VTABLE_SLOTS={len(c.get('slots', {}))}"
        )

    out.append("")
    out.append("[CHILD_USAGE_SUMMARY]")
    for off in TARGET_OFFSETS:
        r = summary[off]
        targets = ",".join(f"0x{x:08X}" for x in sorted(r["direct_targets"])) or "NONE"
        vslots = ",".join(str(x) for x in sorted(r["virtual_slots"])) or "NONE"
        out.append(
            f"OFFSET=0x{off:03X} FIELD_METHODS={len(r['field_methods'])} FIELD_ACCESSES={r['field_accesses']} "
            f"DIRECT_CALL_METHODS={len(r['direct_methods'])} DIRECT_CALLS={r['direct_calls']} DIRECT_TARGETS={targets} "
            f"VIRTUAL_CALL_METHODS={len(r['virtual_methods'])} VIRTUAL_CALLS={r['virtual_calls']} VIRTUAL_SLOTS={vslots}"
        )

    out.append("")
    out.append("[CALL_DETAIL]")
    details = [e for e in all_events if e["kind"] != "FIELD_ACCESS"]
    if not details:
        out.append("NONE")
    else:
        for e in sorted(details, key=lambda x: (x["offset"], x["slot"], x["insn_rva"], x["kind"])):
            extra = ""
            if e["kind"] == "DIRECT_CHILD_CALL":
                extra = f" TARGET_RVA={'0x%08X' % e['target_rva'] if e.get('target_rva') is not None else 'OUTSIDE'}"
            elif e["kind"] == "VIRTUAL_CHILD_CALL":
                extra = f" VIRTUAL_SLOT={e.get('virtual_slot') if e.get('virtual_slot') is not None else 'UNRESOLVED'}"
            out.append(
                f"OFFSET=0x{e['offset']:03X} KIND={e['kind']} INVWIN_SLOT={e['slot']:02d} "
                f"METHOD_RVA=0x{e['method_rva']:08X} INSN_RVA=0x{e['insn_rva']:08X}{extra} ASM={e['detail']}"
            )

    out.append("")
    out.append("[FIELD_ACCESS_DETAIL]")
    fields = [e for e in all_events if e["kind"] == "FIELD_ACCESS"]
    if not fields:
        out.append("NONE")
    else:
        for e in sorted(fields, key=lambda x: (x["offset"], x["slot"], x["insn_rva"])):
            out.append(
                f"OFFSET=0x{e['offset']:03X} INVWIN_SLOT={e['slot']:02d} METHOD_RVA=0x{e['method_rva']:08X} "
                f"INSN_RVA=0x{e['insn_rva']:08X} ASM={e['detail']}"
            )

    out.append("")
    out.append("[DECISION]")
    out.append(f"MOST_USED_CHILD_OFFSET={'0x%03X' % most_used if most_used is not None else 'NONE'}")
    out.append("MOST_USED_IS_INVENTORY_PROOF=NO")
    out.append("ITEM_RECORD_TRAVERSAL_PROVEN=NO")
    out.append("STATUS=PASS_PARENT_CHILD_USAGE_CLASSIFIED")
    out.append("FORMAL_WP5=NOT_YET")
    out.append("FORMAL_WP6=NOT_YET")
    out.append("NEXT=Inspect only the called child virtual slots/direct helpers identified above and intersect with the V13 child-vtable semantic lane; do not widen scanning.")
    out.append(f"UNRESOLVED_CFG_TARGET_COUNT={len(all_unresolved)}")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")

    out_path.write_text("\n".join(out) + "\n", encoding="utf-8")
    print("\n".join(out))


if __name__ == "__main__":
    main()
