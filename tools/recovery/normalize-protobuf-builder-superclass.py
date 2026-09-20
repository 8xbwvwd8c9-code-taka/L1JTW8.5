#!/usr/bin/env python3
import json
import re
from pathlib import Path

STAGE = Path("_normalized-stage-src")
REC = Path("recovery")
OUT = REC / "normalized_protobuf_raw_super_transform.json"
MD = REC / "NORMALIZED_PROTOBUF_RAW_SUPER_TRANSFORM.md"

files = sorted((STAGE / "l1r" / "an").glob("PBMessageALL*.java"))
rx = re.compile(r"extends\s+p\.a<([^>]+)>")

changes = []
for p in files:
    text = p.read_text(encoding="utf-8", errors="replace")
    matches = list(rx.finditer(text))
    if not matches:
        continue
    new_text, count = rx.subn("extends p.a", text)
    if count:
        p.write_text(new_text, encoding="utf-8")
        changes.append({
            "file": p.relative_to(STAGE).as_posix(),
            "count": count,
            "type_arguments": [m.group(1) for m in matches],
        })

total = sum(x["count"] for x in changes)
state = {
    "error_family": "PROTOBUF_GENERIC_SUPERCLASS_SOURCE_REPRESENTATION",
    "expected_builder_count": 44,
    "rewritten_builder_count": total,
    "changed_files": len(changes),
    "changes": changes,
    "jvm_descriptors_changed": False,
    "generic_signature_metadata_normalized": True,
    "gameplay_logic_changed": False,
    "normalization_required_for_donor_compare": True,
}
OUT.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")

status = "PASS" if total == 44 else "FAIL"
MD.write_text(
    "# Normalized Protobuf Raw Superclass Transform\n\n"
    f"Status: **{status}**\n\n"
    f"- Expected protobuf builders: **44**\n"
    f"- Rewritten builders: **{total}**\n"
    "- Transform: extends p.a<ConcreteBuilder> -> extends p.a\n"
    "- JVM method/field descriptors changed: **NO**\n"
    "- Gameplay logic changed: **NO**\n"
    "- Generic Signature metadata requires normalization during donor comparison: **YES**\n",
    encoding="utf-8",
)
print(json.dumps(state, indent=2))
if total != 44:
    raise SystemExit(f"expected 44 protobuf builders, got {total}")
