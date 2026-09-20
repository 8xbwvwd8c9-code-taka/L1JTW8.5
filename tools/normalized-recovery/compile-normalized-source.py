#!/usr/bin/env python3
import csv
import json
import os
import re
import shutil
import subprocess
from pathlib import Path

SRC = Path("recovery/normalized-src-vf")
REC = Path("recovery")
BUILD = REC / "normalized-build-classes"
LOG = REC / "normalized-javac.log"
STATE = REC / "normalized_compile.json"
REPORT = REC / "NORMALIZED_COMPILE.md"
MAP = REC / "source_namespace_map.csv"

if BUILD.exists():
    shutil.rmtree(BUILD)
BUILD.mkdir(parents=True, exist_ok=True)

sources = sorted(SRC.rglob("*.java"))
source_list = REC / "normalized-source-files.txt"
source_list.write_text("\n".join(p.as_posix() for p in sources) + "\n", encoding="utf-8")

deps = sorted(Path("lib").glob("*.jar"))
pb = REC / "compile-ref-protobuf-obf.jar"
if pb.exists():
    deps.append(pb)
cp = os.pathsep.join(str(p) for p in deps)

cmd = [
    "javac","-encoding","UTF-8","-source","8","-target","8","-proc:none",
    "-Xmaxerrs","5000","-Xmaxwarns","5000",
    "-cp",cp,"-d",str(BUILD),"@"+str(source_list)
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

donor = set()
with (REC/"class_inventory.csv").open(encoding="utf-8-sig", newline="") as f:
    for row in csv.DictReader(f):
        donor.add(row["ClassPath"])

missing = sorted(donor - normalized_back)
extra = sorted(normalized_back - donor)

log_text = LOG.read_text(encoding="utf-8", errors="replace")
error_headers = re.findall(r"^.+?\.java:\d+: error: (.+)$", log_text, flags=re.M)
files = re.findall(r"^(.+?\.java):\d+: error:", log_text, flags=re.M)

state = {
    "compiler_exit": proc.returncode,
    "java_sources": len(sources),
    "generated_classes": len(built),
    "donor_application_classes": len(donor),
    "built_donor_classes_normalized": len(donor & normalized_back),
    "missing_classes": len(missing),
    "extra_classes": len(extra),
    "javac_error_headers": len(error_headers),
    "javac_error_files": len(set(files)),
    "donor_game_jar_on_classpath": False,
    "dependencies": [p.as_posix() for p in deps],
}
STATE.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")
(REC/"normalized-class-missing.txt").write_text("\n".join(missing)+("\n" if missing else ""), encoding="utf-8")
(REC/"normalized-class-extra.txt").write_text("\n".join(extra)+("\n" if extra else ""), encoding="utf-8")

status = "PASS" if proc.returncode == 0 and not missing and not extra else "FAIL"
REPORT.write_text(
    "# L1JTW8.5 Normalized Source Compile\n\n"
    f"Status: **{status}**\n\n"
    f"- Java sources: **{len(sources)}**\n"
    f"- javac exit: **{proc.returncode}**\n"
    f"- javac error headers/files: **{len(error_headers)} / {len(set(files))}**\n"
    f"- Generated classes: **{len(built)}**\n"
    f"- Donor application classes: **{len(donor)}**\n"
    f"- Built donor classes after namespace normalization: **{len(donor & normalized_back)}**\n"
    f"- Missing / extra: **{len(missing)} / {len(extra)}**\n"
    "- Donor game JAR on classpath: **NO**\n",
    encoding="utf-8",
)
print(json.dumps(state, indent=2))
