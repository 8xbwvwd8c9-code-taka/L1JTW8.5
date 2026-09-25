#!/usr/bin/env python3
import csv
import json
import keyword
import re
from collections import Counter
from pathlib import Path

SRC = Path("recovered-src-obf")
REC = Path("recovery")
INV = REC / "class_inventory.csv"

JAVA_KEYWORDS = {
    "abstract","assert","boolean","break","byte","case","catch","char","class","const",
    "continue","default","do","double","else","enum","extends","final","finally","float",
    "for","goto","if","implements","import","instanceof","int","interface","long","native",
    "new","package","private","protected","public","return","short","static","strictfp",
    "super","switch","synchronized","this","throw","throws","transient","try","void",
    "volatile","while","true","false","null","_"
}

MARKERS = {
    "PSEUDO_GOTO": re.compile(r"\*\*\s*GOTO\b|^\s*lbl\d+:", re.M),
    "UNABLE_STRUCTURE": re.compile(r"Unable to fully structure", re.I),
    "COULD_NOT_DECOMPILE": re.compile(r"Could not decompile|could not be decompiled|Decompilation failed", re.I),
    "WHILE_FALSE": re.compile(r"while\s*\(\s*false\s*\)", re.I),
    "VOID_LOCAL": re.compile(r"\bvoid\s+[A-Za-z_$][\w$]*\s*[;=]"),
}

rows = []
per_file = []
for path in sorted(SRC.rglob("*.java")):
    text = path.read_text(encoding="utf-8", errors="replace")
    counts = {}
    for name, rx in MARKERS.items():
        matches = list(rx.finditer(text))
        counts[name] = len(matches)
        for m in matches[:2000]:
            line = text.count("\n", 0, m.start()) + 1
            snippet = text.splitlines()[line-1].strip()[:240]
            rows.append({
                "File": path.as_posix(),
                "Line": line,
                "Type": name,
                "Snippet": snippet,
            })
    if any(counts.values()):
        per_file.append({"File": path.as_posix(), **counts, "Total": sum(counts.values())})

keyword_classes = []
if INV.exists():
    with INV.open(encoding="utf-8-sig", newline="") as f:
        for row in csv.DictReader(f):
            if row["Kind"] != "TOP_LEVEL":
                continue
            simple = row["InternalName"].rsplit("/", 1)[-1]
            if simple in JAVA_KEYWORDS:
                keyword_classes.append({
                    "InternalName": row["InternalName"],
                    "SourceFile": row["SourceFile"],
                    "Keyword": simple,
                })

keyword_ref_rows = []
if keyword_classes:
    for path in sorted(SRC.rglob("*.java")):
        text = path.read_text(encoding="utf-8", errors="replace")
        lines = text.splitlines()
        for kc in keyword_classes:
            dotted = kc["InternalName"].replace("/", ".")
            simple = kc["Keyword"]
            patterns = [
                re.compile(r"\bimport\s+" + re.escape(dotted) + r"\s*;"),
                re.compile(r"\bnew\s+" + re.escape(simple) + r"\s*\("),
                re.compile(r"\b" + re.escape(dotted) + r"\b"),
            ]
            for i, line in enumerate(lines, 1):
                if any(rx.search(line) for rx in patterns):
                    keyword_ref_rows.append({
                        "File": path.as_posix(),
                        "Line": i,
                        "KeywordClass": dotted,
                        "Snippet": line.strip()[:240],
                    })

with (REC / "hard_tail_markers.csv").open("w", encoding="utf-8", newline="") as f:
    fields = ["File","Line","Type","Snippet"]
    w = csv.DictWriter(f, fieldnames=fields)
    w.writeheader()
    w.writerows(rows)

with (REC / "hard_tail_files.csv").open("w", encoding="utf-8", newline="") as f:
    fields = ["File", *MARKERS.keys(), "Total"]
    w = csv.DictWriter(f, fieldnames=fields)
    w.writeheader()
    w.writerows(sorted(per_file, key=lambda r: (-r["Total"], r["File"])))

with (REC / "java_keyword_classes.csv").open("w", encoding="utf-8", newline="") as f:
    fields = ["InternalName","SourceFile","Keyword"]
    w = csv.DictWriter(f, fieldnames=fields)
    w.writeheader()
    w.writerows(keyword_classes)

with (REC / "java_keyword_references.csv").open("w", encoding="utf-8", newline="") as f:
    fields = ["File","Line","KeywordClass","Snippet"]
    w = csv.DictWriter(f, fieldnames=fields)
    w.writeheader()
    w.writerows(keyword_ref_rows)

marker_counts = Counter(r["Type"] for r in rows)
state = {
    "java_sources_scanned": len(list(SRC.rglob("*.java"))),
    "files_with_hard_tail_markers": len(per_file),
    "marker_counts": dict(marker_counts),
    "java_keyword_top_level_classes": len(keyword_classes),
    "java_keyword_references": len(keyword_ref_rows),
}
(REC / "hard_tail_state.json").write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")

md = [
    "# L1JTW8.5 Hard-Tail Recovery Inventory",
    "",
    f"- Java sources scanned: **{state['java_sources_scanned']}**",
    f"- Files with decompiler/reconstruction markers: **{state['files_with_hard_tail_markers']}**",
    f"- Java-keyword top-level class names: **{state['java_keyword_top_level_classes']}**",
    f"- Java-keyword class references found: **{state['java_keyword_references']}**",
    "",
    "## Marker counts",
    "",
    "| Marker | Count |",
    "|---|---:|",
]
for k in MARKERS:
    md.append(f"| {k} | {marker_counts.get(k,0)} |")
md += ["", "## Java-keyword classes", ""]
if keyword_classes:
    for x in keyword_classes:
        md.append(f"- `{x['InternalName']}` → `{x['SourceFile']}` (keyword: `{x['Keyword']}`)")
else:
    md.append("- None")
md += [
    "",
    "## Recovery policy",
    "",
    "- Pseudo-GOTO / unstructured files are hard-tail candidates for alternate decompiler output and bytecode arbitration.",
    "- Java-keyword class names require a recovery-only source representation rename; donor bytecode remains unchanged.",
    "- Do not mass-edit gameplay logic to make javac pass.",
]
(REC / "HARD_TAIL_INVENTORY.md").write_text("\n".join(md) + "\n", encoding="utf-8")

print(json.dumps(state, indent=2))
