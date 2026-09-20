#!/usr/bin/env python3
import csv
import json
import keyword
from pathlib import Path

REC = Path("recovery")
INVENTORY = REC / "class_inventory.csv"
STATE = REC / "source_identity_collisions.json"
REPORT = REC / "SOURCE_IDENTITY_COLLISIONS.md"

JAVA_KEYWORDS = {
    "abstract","assert","boolean","break","byte","case","catch","char","class",
    "const","continue","default","do","double","else","enum","extends","final",
    "finally","float","for","goto","if","implements","import","instanceof","int",
    "interface","long","native","new","package","private","protected","public",
    "return","short","static","strictfp","super","switch","synchronized","this",
    "throw","throws","transient","try","void","volatile","while","true","false","null"
}

if not INVENTORY.exists():
    raise SystemExit(f"missing inventory: {INVENTORY}")

with INVENTORY.open(encoding="utf-8-sig", newline="") as f:
    rows = list(csv.DictReader(f))

paths = sorted({r["ClassPath"] for r in rows if r.get("ClassPath")})
top_level_keyword = []
nested_enclosing_name_collisions = []
nested_adjacent_name_collisions = []

for cp in paths:
    if not cp.endswith(".class"):
        continue
    binary = cp[:-6]
    simple_binary = binary.rsplit("/", 1)[-1]
    parts = simple_binary.split("$")
    top = parts[0]

    if "$" not in simple_binary and top in JAVA_KEYWORDS:
        top_level_keyword.append(cp)

    if len(parts) <= 1:
        continue

    for i in range(1, len(parts)):
        cur = parts[i]
        if cur.isdigit():
            continue
        ancestors = [x for x in parts[:i] if not x.isdigit()]
        if cur in ancestors:
            nested_enclosing_name_collisions.append({
                "class_path": cp,
                "nested_simple_name": cur,
                "ancestor_chain": parts[:i],
            })
            break

    for i in range(1, len(parts)):
        if parts[i].isdigit():
            continue
        if parts[i] == parts[i - 1]:
            nested_adjacent_name_collisions.append(cp)
            break

# The embedded protobuf runtime occupies root package a/**.
# In Java source, any visible type named "a" shadows the package token "a",
# so an/** generated-message source is especially vulnerable.
protobuf_root = "a"
protobuf_shadow_paths = []
for cp in paths:
    if not cp.startswith("an/") or not cp.endswith(".class"):
        continue
    simple_binary = cp[:-6].rsplit("/", 1)[-1]
    parts = [p for p in simple_binary.split("$") if not p.isdigit()]
    if protobuf_root in parts:
        protobuf_shadow_paths.append(cp)

state = {
    "inventory_class_paths": len(paths),
    "java_keyword_top_level_classes": sorted(set(top_level_keyword)),
    "java_keyword_top_level_count": len(set(top_level_keyword)),
    "nested_enclosing_name_collision_count": len({x["class_path"] for x in nested_enclosing_name_collisions}),
    "nested_enclosing_name_collisions": nested_enclosing_name_collisions,
    "nested_adjacent_name_collision_count": len(set(nested_adjacent_name_collisions)),
    "nested_adjacent_name_collisions": sorted(set(nested_adjacent_name_collisions)),
    "protobuf_runtime_root_package": protobuf_root,
    "protobuf_shadow_class_count": len(set(protobuf_shadow_paths)),
    "protobuf_shadow_classes": sorted(set(protobuf_shadow_paths)),
    "interpretation": {
        "donor_bytecode_valid": True,
        "java_source_representation_requires_normalization": True,
        "gameplay_logic_change_required": False,
    },
}
STATE.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")

md = [
    "# L1JTW8.5 Source Identity Collision Audit",
    "",
    "Purpose: identify JVM-valid identities that cannot be represented directly by Java source without recovery-only naming normalization.",
    "",
    f"- Inventory class paths: **{len(paths)}**",
    f"- Java-keyword top-level classes: **{state['java_keyword_top_level_count']}**",
    f"- Nested names colliding with an enclosing type: **{state['nested_enclosing_name_collision_count']}**",
    f"- Adjacent parent/child same-name collisions: **{state['nested_adjacent_name_collision_count']}**",
    f"- an/** class paths containing type name `a` while protobuf runtime root is `a/**`: **{state['protobuf_shadow_class_count']}**",
    "",
    "## Nested/enclosing collisions",
    "",
]
md += [f"- `{x['class_path']}`" for x in nested_enclosing_name_collisions]
md += [
    "",
    "## Interpretation",
    "",
    "- These are source-representation blockers, not evidence of donor gameplay defects.",
    "- Recovery-only renames must be normalized back to donor binary identities during class-set / hierarchy / ABI comparison.",
    "- The protobuf a/** namespace collision must be handled as a representation problem; do not alter message semantics to make javac quiet.",
]
REPORT.write_text("\n".join(md) + "\n", encoding="utf-8")
print(json.dumps(state, indent=2))
