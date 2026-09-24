#!/usr/bin/env python3
import argparse
import re
from collections import defaultdict, deque
from pathlib import Path

import capstone
from capstone import Cs, CS_ARCH_X86, CS_MODE_32, CS_AC_READ, CS_AC_WRITE
from capstone.x86 import *

EXPECTED_SHA = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"
MAX_OWNER_OFFSET = 0x800
MAX_STATES = 4096
MAX_VISITS_PER_PC = 12
VOLATILE = {X86_REG_EAX, X86_REG_ECX, X86_REG_EDX}

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
        blob = bytes.fromhex(m.group("bytes"))
        out.append({
            "slot": int(m.group("slot")),
            "va": int(m.group("va"), 16),
            "rva": int(m.group("rva"), 16),
            "blob": blob,
        })
    return out


def state_key(pc, reg_alias, stack_alias):
    regs = tuple(sorted((int(k), int(v)) for k, v in reg_alias.items()))
    stacks = tuple(sorted((int(k), int(v)) for k, v in stack_alias.items()))
    return pc, regs, stacks


def access_text(insn, idx, op):
    access = getattr(op, "access", 0)
    if access:
        r = bool(access & CS_AC_READ)
        w = bool(access & CS_AC_WRITE)
        if r and w:
            return "RW"
        if w:
            return "W"
        if r:
            return "R"
    # Conservative fallback. LEA is intentionally not counted as a memory access elsewhere.
    if idx == 0 and insn.mnemonic in {"mov", "add", "sub", "and", "or", "xor", "inc", "dec", "pop"}:
        return "W"
    return "R"


def clone_alias(d):
    return dict(d)


def apply_alias_effects(insn, reg_alias, stack_alias):
    ops = insn.operands
    handled_dst = False

    if insn.mnemonic == "mov" and len(ops) >= 2:
        dst, src = ops[0], ops[1]
        if dst.type == X86_OP_REG:
            handled_dst = True
            if src.type == X86_OP_REG and src.reg in reg_alias:
                reg_alias[dst.reg] = reg_alias[src.reg]
            elif src.type == X86_OP_MEM and src.mem.base == X86_REG_EBP and src.mem.index == 0 and int(src.mem.disp) in stack_alias:
                reg_alias[dst.reg] = stack_alias[int(src.mem.disp)]
            else:
                reg_alias.pop(dst.reg, None)
        elif dst.type == X86_OP_MEM and dst.mem.base == X86_REG_EBP and dst.mem.index == 0:
            key = int(dst.mem.disp)
            if src.type == X86_OP_REG and src.reg in reg_alias:
                stack_alias[key] = reg_alias[src.reg]
            else:
                stack_alias.pop(key, None)

    elif insn.mnemonic == "lea" and len(ops) >= 2 and ops[0].type == X86_OP_REG and ops[1].type == X86_OP_MEM:
        handled_dst = True
        dst, src = ops[0], ops[1]
        if src.mem.index == 0 and src.mem.base in reg_alias:
            reg_alias[dst.reg] = int(reg_alias[src.mem.base]) + int(src.mem.disp)
        elif src.mem.base == X86_REG_EBP and src.mem.index == 0 and int(src.mem.disp) in stack_alias:
            reg_alias[dst.reg] = stack_alias[int(src.mem.disp)]
        else:
            reg_alias.pop(dst.reg, None)

    if not handled_dst and len(ops) >= 1 and ops[0].type == X86_OP_REG:
        dst_reg = ops[0].reg
        if insn.mnemonic not in {"cmp", "test", "push", "call", "jmp"} and not insn.mnemonic.startswith("j"):
            reg_alias.pop(dst_reg, None)

    if insn.mnemonic == "call":
        for r in VOLATILE:
            reg_alias.pop(r, None)


def decode_method_cfg(md, method, module_base, module_end):
    blob = method["blob"]
    start = method["va"]
    end = start + len(blob)
    accesses = []
    calls = []
    branch_targets = set()
    unresolved = []
    visited_states = set()
    pc_visits = defaultdict(int)
    queue = deque()
    queue.append((start, {X86_REG_ECX: 0}, {}))
    state_count = 0
    instructions_seen = 0

    while queue and state_count < MAX_STATES:
        pc, reg_alias, stack_alias = queue.popleft()
        if pc < start or pc >= end:
            unresolved.append((pc, "OUTSIDE_BLOB"))
            continue
        skey = state_key(pc, reg_alias, stack_alias)
        if skey in visited_states:
            continue
        visited_states.add(skey)
        pc_visits[pc] += 1
        if pc_visits[pc] > MAX_VISITS_PER_PC:
            continue
        state_count += 1

        off = pc - start
        insn_list = list(md.disasm(blob[off:off + 15], pc, count=1))
        if not insn_list:
            unresolved.append((pc, "DECODE_FAIL"))
            continue
        insn = insn_list[0]
        instructions_seen += 1
        next_pc = insn.address + insn.size

        # Owner-relative memory access. LEA only forms an address and is not counted as a read.
        if insn.mnemonic != "lea":
            for idx, op in enumerate(insn.operands):
                if op.type != X86_OP_MEM or op.mem.index != 0:
                    continue
                base_reg = op.mem.base
                if base_reg not in reg_alias:
                    continue
                owner_off = int(reg_alias[base_reg]) + int(op.mem.disp)
                if 0 <= owner_off <= MAX_OWNER_OFFSET:
                    accesses.append({
                        "slot": method["slot"],
                        "method_rva": method["rva"],
                        "insn_rva": int(insn.address - module_base),
                        "offset": owner_off,
                        "access": access_text(insn, idx, op),
                        "asm": f"{insn.mnemonic} {insn.op_str}".strip(),
                    })

        # Record thiscall/subobject helper calls before volatile clobber.
        if insn.mnemonic == "call" and insn.operands:
            this_off = reg_alias.get(X86_REG_ECX)
            target_rva = None
            target_va = None
            if insn.operands[0].type == X86_OP_IMM:
                target_va = int(insn.operands[0].imm) & 0xFFFFFFFF
                if module_base <= target_va < module_end:
                    target_rva = target_va - module_base
            if this_off is not None and 0 <= int(this_off) <= MAX_OWNER_OFFSET:
                calls.append({
                    "slot": method["slot"],
                    "method_rva": method["rva"],
                    "insn_rva": int(insn.address - module_base),
                    "this_offset": int(this_off),
                    "target_rva": target_rva,
                    "asm": f"{insn.mnemonic} {insn.op_str}".strip(),
                })

        # Apply alias effects for fallthrough / branch successor state.
        next_regs = clone_alias(reg_alias)
        next_stack = clone_alias(stack_alias)
        apply_alias_effects(insn, next_regs, next_stack)

        mnem = insn.mnemonic.lower()
        if mnem.startswith("ret") or mnem in {"iret", "iretd", "int3", "ud2"}:
            continue

        # Direct unconditional jump: follow only target, never linear-fall through.
        if mnem == "jmp":
            if insn.operands and insn.operands[0].type == X86_OP_IMM:
                target = int(insn.operands[0].imm) & 0xFFFFFFFF
                branch_targets.add(target)
                if start <= target < end:
                    queue.append((target, clone_alias(next_regs), clone_alias(next_stack)))
                else:
                    unresolved.append((target, "DIRECT_JMP_OUTSIDE_BLOB"))
            else:
                unresolved.append((insn.address, "INDIRECT_JMP"))
            continue

        # Conditional branch: conservatively explore target and fallthrough with identical alias state.
        if mnem.startswith("j") and mnem != "jmp":
            if insn.operands and insn.operands[0].type == X86_OP_IMM:
                target = int(insn.operands[0].imm) & 0xFFFFFFFF
                branch_targets.add(target)
                if start <= target < end:
                    queue.append((target, clone_alias(next_regs), clone_alias(next_stack)))
                else:
                    unresolved.append((target, "COND_JMP_OUTSIDE_BLOB"))
            queue.append((next_pc, clone_alias(next_regs), clone_alias(next_stack)))
            continue

        queue.append((next_pc, next_regs, next_stack))

    truncated = bool(queue) or state_count >= MAX_STATES
    return accesses, calls, branch_targets, unresolved, state_count, instructions_seen, truncated


def main():
    ap = argparse.ArgumentParser(description="Depth-1 bounded CFG decode for INVWIN vtable methods.")
    ap.add_argument("--input", required=True)
    ap.add_argument("--output", required=True)
    args = ap.parse_args()

    input_path = Path(args.input)
    output_path = Path(args.output)
    lines = input_path.read_text(encoding="utf-8", errors="replace").splitlines()

    client_sha = first_value(lines, "CLIENT_SHA256=")
    authority = first_value(lines, "CLIENT_AUTHORITY=")
    module_base = hx(first_value(lines, "MODULE_BASE="))
    module_size = hx(first_value(lines, "MODULE_SIZE="))
    heap_scan = first_value(lines, "HEAP_SCAN=")
    mem_private = first_value(lines, "MEM_PRIVATE_SCAN=")
    memory_write = first_value(lines, "MEMORY_WRITE=")
    methods = parse_methods(lines)

    identity_pass = (
        client_sha == EXPECTED_SHA and authority == "1" and
        heap_scan == "NO" and mem_private == "NO" and memory_write == "NO" and
        len(methods) == 64
    )
    if not identity_pass:
        raise SystemExit("V8b identity/safety gate failed")

    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    module_end = module_base + module_size

    all_accesses = []
    all_calls = []
    total_states = 0
    total_insns = 0
    truncated_slots = []
    unresolved_by_slot = defaultdict(list)
    branches_by_slot = defaultdict(set)

    for method in methods:
        acc, calls, branches, unresolved, states, insns, truncated = decode_method_cfg(md, method, module_base, module_end)
        all_accesses.extend(acc)
        all_calls.extend(calls)
        total_states += states
        total_insns += insns
        branches_by_slot[method["slot"]].update(branches)
        unresolved_by_slot[method["slot"]].extend(unresolved)
        if truncated:
            truncated_slots.append(method["slot"])

    by_offset = defaultdict(lambda: {"methods": set(), "reads": 0, "writes": 0, "rw": 0, "calls": 0})
    for a in all_accesses:
        row = by_offset[a["offset"]]
        row["methods"].add(a["slot"])
        if a["access"] == "R": row["reads"] += 1
        elif a["access"] == "W": row["writes"] += 1
        else: row["rw"] += 1
    for c in all_calls:
        by_offset[c["this_offset"]]["calls"] += 1

    focus_offsets = sorted(o for o in by_offset if 0x180 <= o <= 0x280)
    mutator_offsets = sorted(o for o, r in by_offset.items() if r["writes"] or r["rw"])
    subobject_offsets = sorted(o for o, r in by_offset.items() if r["calls"] and o != 0)

    out = []
    out.append("MODE=850_INVENTORY_INVWIN_CFG_V8B")
    out.append(f"INPUT={input_path}")
    out.append(f"CAPSTONE_PACKAGE_VERSION={capstone.__version__}")
    out.append(f"CLIENT_SHA256={client_sha}")
    out.append(f"CLIENT_AUTHORITY={authority}")
    out.append(f"MODULE_BASE=0x{module_base:08X}")
    out.append(f"MODULE_SIZE=0x{module_size:X}")
    out.append("IDENTITY_GATE=PASS")
    out.append(f"VTABLE_METHODS=64")
    out.append(f"CFG_STATES_VISITED={total_states}")
    out.append(f"CFG_INSTRUCTIONS_DECODED={total_insns}")
    out.append(f"CFG_TRUNCATED_SLOTS={','.join(f'{x:02d}' for x in truncated_slots) if truncated_slots else 'NONE'}")
    out.append("CFG_SCOPE=EXISTING_576_BYTE_METHOD_BLOBS_ONLY")
    out.append("RUNTIME_ATTACH=NO")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")
    out.append("")
    out.append("[OFFSET_SUMMARY]")
    for off in sorted(by_offset):
        r = by_offset[off]
        out.append(
            f"OFFSET=0x{off:03X} METHODS={len(r['methods'])} READS={r['reads']} WRITES={r['writes']} RW={r['rw']} THISCALLS={r['calls']}"
        )

    out.append("")
    out.append("[FOCUS_0180_0280]")
    if focus_offsets:
        for off in focus_offsets:
            r = by_offset[off]
            out.append(f"OFFSET=0x{off:03X} METHODS={len(r['methods'])} READS={r['reads']} WRITES={r['writes']} RW={r['rw']} THISCALLS={r['calls']}")
    else:
        out.append("NONE")

    out.append("")
    out.append("[FOCUS_ACCESS_DETAIL]")
    focus_detail = [a for a in all_accesses if 0x180 <= a["offset"] <= 0x280]
    if focus_detail:
        for a in sorted(focus_detail, key=lambda x: (x["offset"], x["slot"], x["insn_rva"])):
            out.append(
                f"OFFSET=0x{a['offset']:03X} SLOT={a['slot']:02d} METHOD_RVA=0x{a['method_rva']:08X} INSN_RVA=0x{a['insn_rva']:08X} ACCESS={a['access']} ASM={a['asm']}"
            )
    else:
        out.append("NONE")

    out.append("")
    out.append("[SUBOBJECT_CALLS_0180_0280]")
    focus_calls = [c for c in all_calls if 0x180 <= c["this_offset"] <= 0x280]
    if focus_calls:
        for c in sorted(focus_calls, key=lambda x: (x["this_offset"], x["slot"], x["insn_rva"])):
            target = f"0x{c['target_rva']:08X}" if c["target_rva"] is not None else "INDIRECT_OR_OUTSIDE"
            out.append(
                f"OFFSET=0x{c['this_offset']:03X} SLOT={c['slot']:02d} METHOD_RVA=0x{c['method_rva']:08X} INSN_RVA=0x{c['insn_rva']:08X} TARGET_RVA={target} ASM={c['asm']}"
            )
    else:
        out.append("NONE")

    out.append("")
    out.append("[CFG_UNRESOLVED]")
    unresolved_count = 0
    for slot in sorted(unresolved_by_slot):
        unique = sorted(set(unresolved_by_slot[slot]))
        for addr, kind in unique:
            unresolved_count += 1
            if kind.endswith("OUTSIDE_BLOB"):
                rva = addr - module_base if module_base <= addr < module_end else None
                rvatext = f"0x{rva:08X}" if rva is not None else "OUTSIDE_MODULE"
                out.append(f"SLOT={slot:02d} KIND={kind} TARGET_RVA={rvatext}")
            else:
                rva = addr - module_base if module_base <= addr < module_end else None
                rvatext = f"0x{rva:08X}" if rva is not None else "OUTSIDE_MODULE"
                out.append(f"SLOT={slot:02d} KIND={kind} AT_RVA={rvatext}")
    if unresolved_count == 0:
        out.append("NONE")

    off220 = by_offset.get(0x220, {"reads": 0, "writes": 0, "rw": 0, "calls": 0})
    off220_access = off220["reads"] + off220["writes"] + off220["rw"]
    off220_calls = off220["calls"]
    focus_text = ",".join(f"0x{x:03X}" for x in focus_offsets) if focus_offsets else "NONE"
    mut_text = ",".join(f"0x{x:03X}" for x in mutator_offsets if 0x180 <= x <= 0x280) or "NONE"
    sub_text = ",".join(f"0x{x:03X}" for x in subobject_offsets if 0x180 <= x <= 0x280) or "NONE"

    if focus_offsets:
        status = "PASS_INVWIN_FOCUS_OFFSETS_FOUND"
        nxt = "Rank only these code-relevant INVWIN offsets for controlled fixed-offset validation; do not reopen +0x220 unless it is among them."
    elif unresolved_count:
        status = "INVWIN_CFG_DEPTH1_EXTERNAL_TARGETS_REMAIN"
        nxt = "Capture only the listed outside-blob direct targets at one additional bounded depth; do not broaden heap or object scans."
    else:
        status = "PASS_INVWIN_0180_0280_NO_CODE_RELEVANCE"
        nxt = "Retire INVWIN+0x180..+0x280 as direct backing candidates and pivot to code-proven pointer fields below 0x180."

    out.append("")
    out.append("[DECISION]")
    out.append(f"OFFSET_0x220_ACCESS_COUNT={off220_access}")
    out.append(f"OFFSET_0x220_THISCALL_COUNT={off220_calls}")
    out.append(f"OFFSET_0x220_CODE_RELEVANT={'YES' if (off220_access or off220_calls) else 'NO'}")
    out.append(f"FOCUS_OFFSETS={focus_text}")
    out.append(f"MUTATOR_OFFSETS={mut_text}")
    out.append(f"THISCALL_SUBOBJECT_OFFSETS={sub_text}")
    out.append(f"UNRESOLVED_CFG_TARGET_COUNT={unresolved_count}")
    out.append(f"STATUS={status}")
    out.append("COLLECTION_LAYOUT_PROVEN=NO")
    out.append("FORMAL_WP5=NOT_YET")
    out.append("FORMAL_WP6=NOT_YET")
    out.append(f"NEXT={nxt}")
    out.append("HEAP_SCAN=NO")
    out.append("MEM_PRIVATE_SCAN=NO")
    out.append("MEMORY_WRITE=NO")

    output_path.write_text("\n".join(out) + "\n", encoding="utf-8")
    print("\n".join(out))


if __name__ == "__main__":
    main()
