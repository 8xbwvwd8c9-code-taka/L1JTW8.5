#!/usr/bin/env python3
import json
import re
from pathlib import Path

SRC = Path("_normalized-stage-src/l1r/an")
REC = Path("recovery")
STATE = REC / "normalized_builder_collision_transform.json"
REPORT = REC / "NORMALIZED_BUILDER_COLLISION_TRANSFORM.md"

FILES = [
    "PBMessageALL.java","PBMessageALL2.java","PBMessageALL3.java",
    "PBMessageALL4.java","PBMessageALL5.java","PBMessageALL6.java",
    "PBMessageALL7.java","PBMessageALL8.java","PBMessageALL9.java",
]

def matching_brace(text: str, open_pos: int) -> int:
    depth = 0
    in_str = False
    in_chr = False
    esc = False
    line_comment = False
    block_comment = False
    i = open_pos
    while i < len(text):
        c = text[i]
        n = text[i+1] if i + 1 < len(text) else ""

        if line_comment:
            if c == "\n":
                line_comment = False
            i += 1
            continue
        if block_comment:
            if c == "*" and n == "/":
                block_comment = False
                i += 2
                continue
            i += 1
            continue
        if in_str:
            if esc:
                esc = False
            elif c == "\\":
                esc = True
            elif c == '"':
                in_str = False
            i += 1
            continue
        if in_chr:
            if esc:
                esc = False
            elif c == "\\":
                esc = True
            elif c == "'":
                in_chr = False
            i += 1
            continue

        if c == "/" and n == "/":
            line_comment = True
            i += 2
            continue
        if c == "/" and n == "*":
            block_comment = True
            i += 2
            continue
        if c == '"':
            in_str = True
            i += 1
            continue
        if c == "'":
            in_chr = True
            i += 1
            continue
        if c == "{":
            depth += 1
        elif c == "}":
            depth -= 1
            if depth == 0:
                return i
        i += 1
    raise ValueError("unmatched brace")

repairs = []
for name in FILES:
    p = SRC / name
    if not p.exists():
        raise SystemExit(f"missing staged protobuf source: {p}")

    text = p.read_text(encoding="utf-8", errors="replace")
    before = text
    outer = name[:-5]

    # Only the builder nested inside message L1R_a collides with its enclosing
    # source-level type. Builders inside L1R_c/L1R_e/... may keep L1R_a.
    old_q = f"{outer}.L1R_a.L1R_a"
    new_q = f"{outer}.L1R_a.L1R_Builder"
    q_count = text.count(old_q)
    if q_count == 0:
        raise SystemExit(f"no qualified L1R_a builder refs found: {name}")
    text = text.replace(old_q, new_q)

    # Find the first builder declaration. File order places message L1R_a first.
    rx = re.compile(
        r"public static final class L1R_a extends p\.a<"
        + re.escape(new_q)
        + r">"
    )
    m = rx.search(text)
    if not m:
        raise SystemExit(f"target builder declaration not found: {name}")

    decl_start = m.start()
    open_pos = text.find("{", m.end())
    if open_pos < 0:
        raise SystemExit(f"builder opening brace not found: {name}")
    close_pos = matching_brace(text, open_pos)

    block = text[decl_start:close_pos+1]
    block, decl_n = re.subn(
        r"public static final class L1R_a\b",
        "public static final class L1R_Builder",
        block,
        count=1,
    )
    if decl_n != 1:
        raise SystemExit(f"builder declaration rename count != 1: {name}")

    # Rename constructor identifiers only inside this builder block.
    block, ctor_n = re.subn(
        r"(?m)^(\s*(?:(?:private|public|protected)\s+)?)L1R_a(\s*\()",
        r"\1L1R_Builder\2",
        block,
    )
    if ctor_n == 0:
        raise SystemExit(f"builder constructors not renamed: {name}")

    text = text[:decl_start] + block + text[close_pos+1:]

    if text == before:
        raise SystemExit(f"no changes made: {name}")

    # Gate: the illegal qualified identity must be gone, while other builders
    # named L1R_a remain intact for their non-colliding enclosing messages.
    if old_q in text:
        raise SystemExit(f"residual illegal builder identity: {name}")
    if "public static final class L1R_Builder" not in text:
        raise SystemExit(f"renamed builder missing: {name}")

    p.write_text(text, encoding="utf-8")
    repairs.append({
        "file": f"l1r/an/{name}",
        "old_binary_identity": f"{outer}$L1R_a$L1R_a",
        "source_recovery_identity": f"{outer}$L1R_a$L1R_Builder",
        "qualified_references_rewritten": q_count,
        "constructors_renamed": ctor_n,
    })

if len(repairs) != 9:
    raise SystemExit(f"repair count mismatch: {len(repairs)} != 9")

state = {
    "error_family": "NESTED_BUILDER_ENCLOSING_NAME_COLLISION",
    "repair_count": len(repairs),
    "repairs": repairs,
    "gameplay_logic_changed": False,
    "normalization_required_for_donor_compare": True,
}
STATE.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")

md = [
    "# Normalized Builder Collision Transform",
    "",
    "Recovery-only rename for the one builder per PBMessageALL* file whose source-level simple name collides with its enclosing message type.",
    "",
    f"- Files repaired: **{len(repairs)}**",
    "- Donor bytecode changed: **NO**",
    "- Gameplay logic changed: **NO**",
    "- Other non-colliding L1R_a builders renamed: **NO**",
    "- Final donor comparison must normalize L1R_Builder back to the donor nested identity.",
    "",
    "## Repairs",
    "",
]
for r in repairs:
    md.append(
        f"- `{r['old_binary_identity']}` -> `{r['source_recovery_identity']}` "
        f"(refs={r['qualified_references_rewritten']}, ctors={r['constructors_renamed']})"
    )
REPORT.write_text("\n".join(md) + "\n", encoding="utf-8")
print(json.dumps(state, indent=2))
