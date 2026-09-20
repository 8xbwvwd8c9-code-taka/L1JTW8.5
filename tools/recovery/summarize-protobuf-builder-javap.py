#!/usr/bin/env python3
import csv
import re
from collections import Counter
from pathlib import Path

SRC = Path("recovery/protobuf-builder-javap.txt")
CSV_OUT = Path("recovery/protobuf-builder-bytecode-summary.csv")
MD_OUT = Path("recovery/PROTOBUF_BUILDER_BYTECODE_SUMMARY.md")

text = SRC.read_text(encoding="utf-8-sig", errors="replace")
lines = text.splitlines()

class_rx = re.compile(r"^===== CLASS (.+) =====$")
insn_rx = re.compile(r"^\s*(\d+):\s+([a-z][a-z0-9_]*)\s*(.*)$")

current_class = ""
current_method = ""
entries = []
method_like_rx = re.compile(r"^\s{2,}[^ ].*\(.*\).*$")

for i,line in enumerate(lines):
    m = class_rx.match(line)
    if m:
        current_class = m.group(1)
        current_method = ""
        continue

    if method_like_rx.match(line) and not line.strip().startswith(("descriptor:","Code:","LineNumberTable:","LocalVariableTable:")):
        current_method = line.strip()

    ins = insn_rx.match(line)
    if not ins or ins.group(2) != "getstatic":
        continue

    context = []
    for j in range(max(0,i-4), min(len(lines),i+7)):
        mi = insn_rx.match(lines[j])
        if mi:
            context.append((int(mi.group(1)),mi.group(2),mi.group(3).strip()))

    entries.append({
        "Class": current_class,
        "Method": current_method,
        "Offset": int(ins.group(1)),
        "Operand": ins.group(3).strip(),
        "Context": " ; ".join(f"{off}:{op} {arg}".strip() for off,op,arg in context),
    })

with CSV_OUT.open("w", encoding="utf-8", newline="") as f:
    fields=["Class","Method","Offset","Operand","Context"]
    w=csv.DictWriter(f,fieldnames=fields)
    w.writeheader()
    w.writerows(entries)

operand_counts=Counter(e["Operand"] for e in entries)
pattern_counts=Counter()
for e in entries:
    ops=[]
    for part in e["Context"].split(" ; "):
        if ":" in part:
            ops.append(part.split(":",1)[1].strip().split(" ",1)[0])
    pattern_counts[" -> ".join(ops)] += 1

md=[
    "# L1JTW8.5 Protobuf Builder Bytecode Summary",
    "",
    f"- Total GETSTATIC instructions: **{len(entries)}**",
    f"- Unique GETSTATIC operands: **{len(operand_counts)}**",
    "",
    "## GETSTATIC operands",
    "",
    "| Operand | Count |",
    "|---|---:|",
]
for operand,count in operand_counts.most_common():
    md.append(f"| `{operand.replace('|','\\|')}` | {count} |")

md += ["", "## Context opcode patterns", "", "| Pattern | Count |", "|---|---:|"]
for pattern,count in pattern_counts.most_common():
    md.append(f"| `{pattern}` | {count} |")

md += ["", "## GETSTATIC contexts", ""]
for e in entries:
    md += [
        f"### {e['Class']} :: {e['Method'] or 'UNKNOWN_METHOD'}",
        "",
        f"- Operand: `{e['Operand']}`",
        f"- Context: `{e['Context']}`",
        "",
    ]

MD_OUT.write_text("\n".join(md)+"\n",encoding="utf-8")
print(f"GETSTATIC={len(entries)}")
for operand,count in operand_counts.most_common(20):
    print(f"OPERAND {count} {operand}")
