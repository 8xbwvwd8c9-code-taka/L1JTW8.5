#!/usr/bin/env python3
import csv
import re
from pathlib import Path

SRC = Path("recovery/protobuf-builder-javap.txt")
CSV_OUT = Path("recovery/protobuf-builder-bytecode-summary.csv")
MD_OUT = Path("recovery/PROTOBUF_BUILDER_BYTECODE_SUMMARY.md")

text = SRC.read_text(encoding="utf-8-sig", errors="replace")
lines = text.splitlines()

rows = []
current_class = None
i = 0

method_header_rx = re.compile(r"^\s*(private|public|protected).*\([^;]*\);\s*$")
insn_rx = re.compile(r"^\s*(\d+):\s+([a-z][a-z0-9_]*)\s*(.*)$")

while i < len(lines):
    line = lines[i]
    if line.startswith("===== CLASS ") and line.endswith(" ====="):
        current_class = line[len("===== CLASS "):-len(" =====")]
        i += 1
        continue

    if current_class and method_header_rx.match(line) and "(" in line:
        header = line.strip()
        descriptor = ""
        instructions = []
        j = i + 1
        while j < len(lines):
            s = lines[j]
            if s.startswith("===== CLASS "):
                break
            if j > i + 1 and method_header_rx.match(s):
                break
            if s.strip().startswith("descriptor:"):
                descriptor = s.split("descriptor:",1)[1].strip()
            m = insn_rx.match(s)
            if m:
                instructions.append((int(m.group(1)), m.group(2), m.group(3).strip()))
            # stop after Code block when next member-like header begins
            if instructions and j + 1 < len(lines):
                nxt = lines[j+1]
                if method_header_rx.match(nxt):
                    j += 1
                    break
            j += 1

        if header.startswith("private void ") and descriptor == "()V":
            getstatic = [x for x in instructions if x[1] == "getstatic"]
            if getstatic:
                rows.append({
                    "Class": current_class,
                    "MethodHeader": header,
                    "Descriptor": descriptor,
                    "InstructionCount": len(instructions),
                    "GetstaticCount": len(getstatic),
                    "GetstaticOperands": " | ".join(x[2] for x in getstatic),
                    "Instructions": " ; ".join(f"{off}:{op} {arg}".strip() for off,op,arg in instructions),
                })
        i = max(i + 1, j)
        continue
    i += 1

with CSV_OUT.open("w", encoding="utf-8", newline="") as f:
    fields = ["Class","MethodHeader","Descriptor","InstructionCount","GetstaticCount","GetstaticOperands","Instructions"]
    w = csv.DictWriter(f, fieldnames=fields)
    w.writeheader()
    w.writerows(rows)

patterns = {}
for r in rows:
    ops = []
    for part in r["Instructions"].split(" ; "):
        bits = part.split(":",1)
        if len(bits) == 2:
            op = bits[1].strip().split(" ",1)[0]
            ops.append(op)
    key = " -> ".join(ops)
    patterns[key] = patterns.get(key, 0) + 1

md = [
    "# L1JTW8.5 Protobuf Builder Bytecode Summary",
    "",
    f"- Builder private-void methods with GETSTATIC: **{len(rows)}**",
    f"- Distinct opcode patterns: **{len(patterns)}**",
    "",
    "## Opcode patterns",
    "",
    "| Pattern | Count |",
    "|---|---:|",
]
for k,v in sorted(patterns.items(), key=lambda kv:(-kv[1],kv[0])):
    md.append(f"| `{k}` | {v} |")

md += ["", "## Methods", ""]
for r in rows:
    md += [
        f"### {r['Class']} :: {r['MethodHeader']}",
        "",
        f"- Descriptor: `{r['Descriptor']}`",
        f"- GETSTATIC: `{r['GetstaticOperands']}`",
        f"- Instructions: `{r['Instructions']}`",
        "",
    ]

MD_OUT.write_text("\n".join(md) + "\n", encoding="utf-8")

print(f"ROWS={len(rows)}")
for k,v in sorted(patterns.items(), key=lambda kv:(-kv[1],kv[0])):
    print(f"PATTERN {v} {k}")
