#!/usr/bin/env python3
import json
import re
from pathlib import Path

REC = Path("recovery")
LOG = REC / "normalized_stage_javac.log"
OUT = REC / "protobuf_override_conflicts.json"
MD = REC / "PROTOBUF_OVERRIDE_CONFLICTS.md"

if not LOG.exists():
    raise SystemExit(f"missing javac log: {LOG}")

lines = LOG.read_text(encoding="utf-8", errors="replace").splitlines()
targets = (
    "cannot override",
    "does not override abstract method",
    "does not override or implement a method from a supertype",
    "cannot implement",
)

blocks = []
for i, line in enumerate(lines):
    if ": error: " not in line:
        continue
    if "/l1r/an/" not in line:
        continue
    header_msg = line.split(": error: ", 1)[1]
    block = lines[i:min(len(lines), i + 12)]
    joined = "\n".join(block)
    if any(t in joined for t in targets):
        blocks.append({
            "header": line,
            "message": header_msg,
            "context": block,
        })

sig_counts = {}
examples = {}
for b in blocks:
    norm = []
    for ln in b["context"]:
        ln = re.sub(r"^.*?/l1r/an/", "l1r/an/", ln)
        ln = re.sub(r":\d+:", ":LINE:", ln)
        ln = re.sub(r"\bPBMessageALL\d*\b", "PBMessageALLX", ln)
        ln = re.sub(r"\bL1R_Builder\b", "L1R_BUILDER", ln)
        norm.append(ln)
    sig = "\n".join(norm)
    sig_counts[sig] = sig_counts.get(sig, 0) + 1
    examples.setdefault(sig, b["context"])

ordered = sorted(sig_counts.items(), key=lambda x: (-x[1], x[0]))
state = {
    "protobuf_override_related_blocks": len(blocks),
    "normalized_signature_count": len(ordered),
    "top_signatures": [
        {
            "count": count,
            "signature": sig,
            "example": examples[sig],
        }
        for sig, count in ordered[:40]
    ],
}
OUT.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")

md = [
    "# Protobuf Override Conflict Details",
    "",
    f"- Override-related javac blocks: **{len(blocks)}**",
    f"- Normalized structural signatures: **{len(ordered)}**",
    "",
]
for idx, (sig, count) in enumerate(ordered[:20], 1):
    md += [
        f"## Signature {idx} — count {count}",
        "",
        "~~~text",
        *examples[sig],
        "~~~",
        "",
    ]
MD.write_text("\n".join(md) + "\n", encoding="utf-8")
print(json.dumps(state, indent=2))
