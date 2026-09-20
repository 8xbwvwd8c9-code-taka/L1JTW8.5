#!/usr/bin/env python3
import json
import re
from pathlib import Path

STAGE = Path("_normalized-stage-src")
REC = Path("recovery")
OUT = REC / "normalized_parser_bridge_transform.json"
MD = REC / "NORMALIZED_PARSER_BRIDGE_TRANSFORM.md"

rx = re.compile(
    r"\n\s*// \$VF: synthetic method\s*\n"
    r"\s*@Override\s*\n"
    r"\s*public Object d\(l1rpb\.h var1, n var2\) throws s \{\s*\n"
    r"\s*return this\.c\(var1, var2\);\s*\n"
    r"\s*\}\s*",
    re.MULTILINE,
)

changes=[]
for p in sorted((STAGE / "l1r" / "an").glob("PBMessageALL*.java")):
    text=p.read_text(encoding="utf-8", errors="replace")
    text2,n=rx.subn("\n",text)
    if n:
        p.write_text(text2,encoding="utf-8")
        changes.append({"file":p.relative_to(STAGE).as_posix(),"removed":n})

total=sum(x["removed"] for x in changes)
state={
    "error_family":"PROTOBUF_EXPLICIT_SYNTHETIC_PARSER_BRIDGE",
    "expected_bridge_count":44,
    "removed_bridge_count":total,
    "changed_files":len(changes),
    "changes":changes,
    "typed_parser_method_retained":True,
    "gameplay_logic_changed":False,
    "bridge_expected_to_be_regenerated_by_javac":True,
}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
status="PASS" if total==44 else "FAIL"
MD.write_text(
    "# Normalized Parser Bridge Transform\n\n"
    f"Status: **{status}**\n\n"
    f"- Expected synthetic parser bridges: **44**\n"
    f"- Removed explicit bridges: **{total}**\n"
    "- Typed parser bodies retained: **YES**\n"
    "- Gameplay logic changed: **NO**\n"
    "- javac must regenerate equivalent erased bridge descriptors for final ABI comparison.\n",
    encoding="utf-8",
)
print(json.dumps(state,indent=2))
if total != 44:
    raise SystemExit(f"expected 44 parser bridges, got {total}")
