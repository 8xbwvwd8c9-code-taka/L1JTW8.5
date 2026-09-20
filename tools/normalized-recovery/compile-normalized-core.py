#!/usr/bin/env python3
import csv
import json
import os
import re
import shutil
import subprocess
from collections import Counter
from pathlib import Path

SRC = Path("recovery/normalized-src-vf")
REC = Path("recovery")
WIP = REC / "wip"
BUILD = REC / "normalized-core-build-classes"
REF = REC / "compile-ref-normalized-proto.jar"
MAP = REC / "source_namespace_map.csv"
INV = REC / "class_inventory.csv"
LOG = WIP / "normalized-core-javac.log"
STATE = WIP / "normalized_core_compile.json"
REPORT = WIP / "NORMALIZED_CORE_COMPILE.md"
FAIL_CSV = WIP / "NORMALIZED_CORE_FAIL.csv"
SOURCE_LIST = WIP / "normalized-core-source-files.txt"
MISSING = WIP / "normalized-core-class-missing.txt"
EXTRA = WIP / "normalized-core-class-extra.txt"

WIP.mkdir(parents=True, exist_ok=True)
if not REF.exists():
    raise SystemExit(f"missing normalized proto reference: {REF}")
if BUILD.exists():
    shutil.rmtree(BUILD)
BUILD.mkdir(parents=True, exist_ok=True)

sources = sorted(
    p for p in SRC.rglob("*.java")
    if not p.relative_to(SRC).as_posix().startswith("l1r/an/")
)
SOURCE_LIST.write_text("\n".join(p.as_posix() for p in sources) + "\n", encoding="utf-8")

deps = sorted(Path("lib").glob("*.jar")) + [REF]
cp = os.pathsep.join(str(p) for p in deps)

cmd = [
    "javac","-encoding","UTF-8","-source","8","-target","8","-proc:none",
    "-Xmaxerrs","5000","-Xmaxwarns","5000",
    "-cp",cp,"-d",str(BUILD),"@"+str(SOURCE_LIST)
]
with LOG.open("w", encoding="utf-8", errors="replace") as log:
    proc = subprocess.run(cmd, stdout=log, stderr=subprocess.STDOUT, text=True)

built = sorted(p.relative_to(BUILD).as_posix() for p in BUILD.rglob("*.class"))

rename = {}
with MAP.open(encoding="utf-8-sig", newline="") as f:
    for row in csv.DictReader(f):
        rename[row["NewInternal"]] = row["OldInternal"]

normalized_back = set()
new_tops = sorted(rename.keys(), key=len, reverse=True)
for p in built:
    internal = p[:-6]
    mapped = None
    for new_top in new_tops:
        old_top = rename[new_top]
        if internal == new_top:
            mapped = old_top
            break
        if internal.startswith(new_top + "$"):
            mapped = old_top + internal[len(new_top):]
            break
    normalized_back.add((mapped or internal) + ".class")

donor_core = set()
with INV.open(encoding="utf-8-sig", newline="") as f:
    for row in csv.DictReader(f):
        cp = row["ClassPath"]
        if not cp.startswith("an/"):
            donor_core.add(cp)

missing = sorted(donor_core - normalized_back)
extra = sorted(normalized_back - donor_core)
MISSING.write_text("\n".join(missing)+("\n" if missing else ""), encoding="utf-8")
EXTRA.write_text("\n".join(extra)+("\n" if extra else ""), encoding="utf-8")

text = LOG.read_text(encoding="utf-8", errors="replace")
rx = re.compile(r"^recovery/normalized-src-vf/([^:]+\.java):(\d+): error: (.*)$", re.M)
errors = [{"File":m.group(1),"Line":int(m.group(2)),"Error":m.group(3)} for m in rx.finditer(text)]
by_file = {}
for e in errors:
    x = by_file.setdefault(e["File"], {"count":0,"primary":e["Error"]})
    x["count"] += 1
families = Counter(e["Error"] for e in errors)

with FAIL_CSV.open("w", encoding="utf-8", newline="") as f:
    fields=["File","ErrorHeaders","PrimaryError"]
    w=csv.DictWriter(f,fieldnames=fields)
    w.writeheader()
    for file,data in sorted(by_file.items(), key=lambda kv:(-kv[1]["count"],kv[0])):
        w.writerow({"File":file,"ErrorHeaders":data["count"],"PrimaryError":data["primary"]})

state = {
    "compiler_exit": proc.returncode,
    "java_sources_excluding_pbmessage": len(sources),
    "javac_error_headers": len(errors),
    "javac_error_files": len(by_file),
    "generated_classes": len(built),
    "donor_core_classes_excluding_an": len(donor_core),
    "built_donor_core_classes": len(donor_core & normalized_back),
    "missing_core_classes": len(missing),
    "extra_core_classes": len(extra),
    "normalized_proto_reference_used": True,
    "final_source_only_gate": False,
    "top_failure_families": families.most_common(40),
}
STATE.write_text(json.dumps(state, indent=2, ensure_ascii=False)+"\n", encoding="utf-8")

md=[
    "# L1JTW8.5 Normalized Core Compile (PBMessage Isolated)",
    "",
    "Diagnostic gate: compile normalized game core while PBMessage source is isolated behind a normalized bytecode reference.",
    "",
    f"- Java sources (excluding l1r/an): **{len(sources)}**",
    f"- javac exit: **{proc.returncode}**",
    f"- error headers/files: **{len(errors)} / {len(by_file)}**",
    f"- generated classes: **{len(built)}**",
    f"- donor core classes excluding an/**: **{len(donor_core)}**",
    f"- built donor core classes: **{len(donor_core & normalized_back)}**",
    f"- missing / extra core classes: **{len(missing)} / {len(extra)}**",
    "",
    "## Top failure families",
    "",
    "| Error | Files/headers |",
    "|---|---:|",
]
for k,v in families.most_common(30):
    md.append(f"| {k.replace('|','\\|')} | {v} |")
md += [
    "",
    "## Boundary",
    "",
    "- compile-ref-normalized-proto.jar is diagnostic-only.",
    "- Final PASS still requires PBMessage source recovery and donor-free full-tree compile.",
]
REPORT.write_text("\n".join(md)+"\n", encoding="utf-8")
print(json.dumps(state, indent=2, ensure_ascii=False))
