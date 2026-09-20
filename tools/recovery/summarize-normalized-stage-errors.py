#!/usr/bin/env python3
import json
import re
from collections import Counter
from pathlib import Path

REC = Path("recovery")
LOG = REC / "normalized_stage_javac.log"
OUT = REC / "normalized_stage_error_families.json"
MD = REC / "NORMALIZED_STAGE_ERROR_FAMILIES.md"

if not LOG.exists():
    raise SystemExit(f"missing log: {LOG}")

lines = LOG.read_text(encoding="utf-8", errors="replace").splitlines()
errors = []
for line in lines:
    if ": error: " not in line:
        continue
    left, msg = line.split(": error: ", 1)
    path = left.rsplit(":", 1)[0]
    errors.append((path, msg))

def family(path: str, msg: str) -> str:
    in_proto = "/l1r/an/" in path or path.startswith("_normalized-stage-src/l1r/an/")
    if "class L1R_a is already defined in class PBMessageALL" in msg:
        return "PROTOBUF_NESTED_BUILDER_IDENTITY"
    if "has protected access in p" in msg:
        return "PROTOBUF_PROTECTED_NESTED_RUNTIME_TYPE" if in_proto else "PROTECTED_ACCESS"
    if "does not override or implement a method from a supertype" in msg:
        return "PROTOBUF_OVERRIDE_BRIDGE" if in_proto else "NONPROTO_OVERRIDE_BRIDGE"
    if "cannot override" in msg or "cannot implement" in msg or "does not override abstract method" in msg:
        return "PROTOBUF_OVERRIDE_BRIDGE" if in_proto else "NONPROTO_OVERRIDE_BRIDGE"
    if "cannot find symbol" in msg:
        return "PROTOBUF_CANNOT_FIND_SYMBOL" if in_proto else "NONPROTO_CANNOT_FIND_SYMBOL"
    if "package a does not exist" in msg or "package l1rpb" in msg:
        return "PROTOBUF_RUNTIME_NAMESPACE_RESIDUAL"
    if "int cannot be dereferenced" in msg:
        return "PROTOBUF_NAME_SHADOW_OR_OVERLOAD" if in_proto else "NONPROTO_NAME_SHADOW_OR_OVERLOAD"
    if "'void' type not allowed here" in msg or "void cannot be converted" in msg:
        return "PROTOBUF_OVERLOAD_RETURN_AMBIGUITY" if in_proto else "NONPROTO_OVERLOAD_RETURN_AMBIGUITY"
    if "Object cannot be converted" in msg or "Object[] cannot be converted" in msg:
        return "PROTOBUF_GENERIC_ERASURE" if in_proto else "NONPROTO_GENERIC_ERASURE"
    if "no suitable constructor found for S_ProtoBuffers" in msg:
        return "NONPROTO_PROTOBUF_CONSTRUCTOR_RESOLUTION"
    if "reference to " in msg and " is ambiguous" in msg:
        return "PROTOBUF_AMBIGUOUS_REFERENCE" if in_proto else "NONPROTO_AMBIGUOUS_REFERENCE"
    return "PROTOBUF_OTHER" if in_proto else "NONPROTO_OTHER"

families = Counter()
files = Counter()
for path, msg in errors:
    fam = family(path, msg)
    families[fam] += 1
    files[path] += 1

proto_errors = sum(v for k, v in families.items() if k.startswith("PROTOBUF_"))
nonproto_errors = sum(v for k, v in families.items() if k.startswith("NONPROTO_") or k == "PROTECTED_ACCESS")

state = {
    "total_error_headers": len(errors),
    "protobuf_error_headers": proto_errors,
    "nonprotobuf_error_headers": nonproto_errors,
    "families": dict(families.most_common()),
    "top_error_files": files.most_common(50),
}
OUT.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")

md = [
    "# Normalized Stage Error Families",
    "",
    f"- Total error headers: **{len(errors)}**",
    f"- Protobuf-classified: **{proto_errors}**",
    f"- Non-Protobuf-classified: **{nonproto_errors}**",
    "",
    "| Family | Errors |",
    "|---|---:|",
]
md += [f"| {k} | {v} |" for k, v in families.most_common()]
md += [
    "",
    "## Rule",
    "",
    "This report is diagnostic only. It does not modify recovered source or donor bytecode.",
]
MD.write_text("\n".join(md) + "\n", encoding="utf-8")
print(json.dumps(state, indent=2))
