#!/usr/bin/env python3
import csv
import json
import os
import shutil
import subprocess
from collections import Counter
from pathlib import Path

ROOT = Path(".")
SRC = ROOT / "_recovery-stage-src"
REC = ROOT / "recovery"
BUILD = REC / "nonproto-build-classes"
LOG = REC / "nonproto_javac.log"
SOURCE_LIST = REC / "nonproto_source_files.txt"
STATE = REC / "nonproto_compile.json"
REPORT = REC / "NONPROTO_COMPILE.md"
INVENTORY = REC / "class_inventory.csv"
TRANSFORM = REC / "stage_transform.json"

if BUILD.exists():
    shutil.rmtree(BUILD)
BUILD.mkdir(parents=True, exist_ok=True)

sources = sorted(p for p in SRC.rglob("*.java") if p.relative_to(SRC).parts[0] != "an")
SOURCE_LIST.write_text("\n".join(str(p).replace("\\","/") for p in sources) + "\n", encoding="utf-8")

lib_jars = sorted((ROOT / "lib").glob("*.jar"))
compile_ref = REC / "compile-ref-proto-messages.jar"
if not compile_ref.exists():
    raise SystemExit(f"missing compile reference: {compile_ref}")
classpath = os.pathsep.join(str(p) for p in (lib_jars + [compile_ref]))

cmd = [
    "javac",
    "-encoding","UTF-8",
    "-source","8",
    "-target","8",
    "-proc:none",
    "-Xmaxerrs","20000",
    "-Xmaxwarns","5000",
    "-cp",classpath,
    "-d",str(BUILD),
    "@" + str(SOURCE_LIST),
]

with LOG.open("w", encoding="utf-8", errors="replace") as log:
    proc = subprocess.run(cmd, stdout=log, stderr=subprocess.STDOUT, text=True)

donor_nonproto = set()
with INVENTORY.open(encoding="utf-8-sig", newline="") as f:
    for row in csv.DictReader(f):
        cp = row["ClassPath"]
        if not cp.startswith("an/"):
            donor_nonproto.add(cp)

built = {p.relative_to(BUILD).as_posix() for p in BUILD.rglob("*.class")}

renames = {}
if TRANSFORM.exists():
    renames = json.loads(TRANSFORM.read_text(encoding="utf-8")).get("recovery_only_class_renames", {})

normalized = set()
for p in built:
    mapped = p
    for old_fq,new_fq in renames.items():
        old_prefix = old_fq.replace(".","/")
        new_prefix = new_fq.replace(".","/")
        if p == new_prefix + ".class":
            mapped = old_prefix + ".class"
            break
        if p.startswith(new_prefix + "$"):
            mapped = old_prefix + p[len(new_prefix):]
            break
    normalized.add(mapped)

missing = sorted(donor_nonproto - normalized)
extra = sorted(normalized - donor_nonproto)

log_text = LOG.read_text(encoding="utf-8", errors="replace")
error_headers = []
for line in log_text.splitlines():
    if ": error:" in line:
        error_headers.append(line)

error_files = Counter()
error_messages = Counter()
for line in error_headers:
    try:
        left,msg = line.split(": error: ",1)
        path = left.rsplit(":",1)[0]
        rel = path.replace("_recovery-stage-src/","")
        error_files[rel] += 1
        error_messages[msg] += 1
    except Exception:
        pass

state = {
    "gate": "NON_PROTO_DIAGNOSTIC_ONLY",
    "compiler": (subprocess.run(["javac","-version"],capture_output=True,text=True).stderr or subprocess.run(["javac","-version"],capture_output=True,text=True).stdout).strip(),
    "source_level": "8",
    "target_level": "8",
    "donor_jar_on_classpath": False,
    "excluded_source_package": "an/**",
    "compile_reference": compile_ref.as_posix(),
    "java_sources_submitted": len(sources),
    "compile_exit_code": proc.returncode,
    "generated_class_files_total": len(built),
    "donor_nonproto_application_classes": len(donor_nonproto),
    "built_nonproto_application_classes_normalized": len(normalized & donor_nonproto),
    "missing_nonproto_application_classes": len(missing),
    "extra_nonproto_application_classes": len(extra),
    "javac_error_headers": len(error_headers),
    "error_file_count": len(error_files),
    "top_error_files": error_files.most_common(30),
    "top_error_messages": error_messages.most_common(30),
}
STATE.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")
(REC/"nonproto_class_set_missing.txt").write_text("\n".join(missing)+("\n" if missing else ""),encoding="utf-8")
(REC/"nonproto_class_set_extra.txt").write_text("\n".join(extra)+("\n" if extra else ""),encoding="utf-8")

md = [
    "# L1JTW8.5 Non-Protobuf Diagnostic Compile",
    "",
    f"Status: **{'PASS' if proc.returncode == 0 else 'FAIL'}**",
    "",
    "## Boundary",
    "",
    "- Gate: **NON_PROTO_DIAGNOSTIC_ONLY**",
    "- an/** source excluded: **YES**",
    "- a/** + an/** donor compile-reference: **YES**",
    "- All other ai..bj/l1j.server game classes supplied from source: **YES**",
    "- Donor full game JAR on classpath: **NO**",
    "",
    "## Result",
    "",
    f"- Java sources submitted: **{len(sources)}**",
    f"- javac exit code: **{proc.returncode}**",
    f"- Generated class files: **{len(built)}**",
    f"- Donor non-protobuf application classes: **{len(donor_nonproto)}**",
    f"- Built donor non-protobuf classes: **{len(normalized & donor_nonproto)}**",
    f"- Missing / extra non-protobuf classes: **{len(missing)} / {len(extra)}**",
    f"- javac error headers: **{len(error_headers)}**",
    f"- Error files: **{len(error_files)}**",
    "",
    "## Top error files",
    "",
    "| File | Errors |",
    "|---|---:|",
]
md += [f"| {k} | {v} |" for k,v in error_files.most_common(30)]
md += [
    "",
    "## Meaning",
    "",
    "This gate isolates an/** protobuf source representability problems. A PASS here is not final full-source recovery PASS.",
]
REPORT.write_text("\n".join(md)+"\n",encoding="utf-8")
print(json.dumps(state, indent=2))
