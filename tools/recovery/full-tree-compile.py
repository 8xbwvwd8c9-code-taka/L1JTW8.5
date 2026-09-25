#!/usr/bin/env python3
import csv
import json
import os
import shutil
import subprocess
from pathlib import Path

ROOT = Path(".")
SRC = ROOT / "recovered-src-obf"
REC = ROOT / "recovery"
BUILD = REC / "build-classes"
LOG = REC / "full_tree_javac.log"
SOURCE_LIST = REC / "source_files.txt"
STATE = REC / "full_tree_compile.json"
REPORT = REC / "FULL_TREE_COMPILE.md"
MISSING = REC / "class_set_missing.txt"
EXTRA = REC / "class_set_extra.txt"
INVENTORY = REC / "class_inventory.csv"

REC.mkdir(parents=True, exist_ok=True)
if BUILD.exists():
    shutil.rmtree(BUILD)
BUILD.mkdir(parents=True, exist_ok=True)

sources = sorted(SRC.rglob("*.java"))
SOURCE_LIST.write_text("\n".join(str(p).replace("\\", "/") for p in sources) + "\n", encoding="utf-8")

lib_jars = sorted((ROOT / "lib").glob("*.jar"))
classpath = os.pathsep.join(str(p) for p in lib_jars)

cmd = [
    "javac",
    "-encoding", "UTF-8",
    "-source", "8",
    "-target", "8",
    "-proc:none",
    "-cp", classpath,
    "-d", str(BUILD),
    "@" + str(SOURCE_LIST),
]

with LOG.open("w", encoding="utf-8", errors="replace") as log:
    proc = subprocess.run(cmd, stdout=log, stderr=subprocess.STDOUT, text=True)

built_paths = sorted(
    p.relative_to(BUILD).as_posix()
    for p in BUILD.rglob("*.class")
)

donor_app = set()
if INVENTORY.exists():
    with INVENTORY.open(encoding="utf-8-sig", newline="") as f:
        donor_app = {row["ClassPath"] for row in csv.DictReader(f)}

built_set = set(built_paths)
missing = sorted(donor_app - built_set)
extra = sorted(built_set - donor_app)

MISSING.write_text("\n".join(missing) + ("\n" if missing else ""), encoding="utf-8")
EXTRA.write_text("\n".join(extra) + ("\n" if extra else ""), encoding="utf-8")

state = {
    "compiler": subprocess.run(["javac", "-version"], capture_output=True, text=True).stderr.strip()
                or subprocess.run(["javac", "-version"], capture_output=True, text=True).stdout.strip(),
    "source_level": "8",
    "target_level": "8",
    "donor_jar_on_classpath": False,
    "third_party_jars": [p.as_posix() for p in lib_jars],
    "java_sources_submitted": len(sources),
    "compile_exit_code": proc.returncode,
    "generated_class_files_total": len(built_paths),
    "donor_application_classes": len(donor_app),
    "built_application_classes": len(built_set & donor_app),
    "missing_application_classes": len(missing),
    "extra_application_classes": len(extra),
}
STATE.write_text(json.dumps(state, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")

status = "PASS" if proc.returncode == 0 and not missing else "FAIL"
lines = [
    "# L1JTW8.5 Full-Tree Source-Only Compile",
    "",
    f"Status: **{status}**",
    "",
    "## Build boundary",
    "",
    "- Game donor JAR on compile classpath: **NO**",
    "- Only repository third-party JARs under `lib/` are used as binary dependencies.",
    "- Recovered game sources: `recovered-src-obf/`.",
    "",
    "## Result",
    "",
    f"- Compiler: **{state['compiler']}**",
    f"- Source / target: **8 / 8**",
    f"- Java sources submitted: **{state['java_sources_submitted']}**",
    f"- javac exit code: **{state['compile_exit_code']}**",
    f"- Generated class files: **{state['generated_class_files_total']}**",
    f"- Donor application classes: **{state['donor_application_classes']}**",
    f"- Built donor application classes: **{state['built_application_classes']}**",
    f"- Missing donor application classes: **{state['missing_application_classes']}**",
    f"- Extra built classes: **{state['extra_application_classes']}**",
    "",
    "## Validation meaning",
    "",
    "- PASS requires javac exit 0 and zero missing donor application classes.",
    "- An extra built class is not automatically accepted; it requires source/ABI review.",
    "- On FAIL, `full_tree_javac.log`, `class_set_missing.txt`, and `class_set_extra.txt` are the recovery queue inputs.",
]
REPORT.write_text("\n".join(lines) + "\n", encoding="utf-8")

print(json.dumps(state, indent=2))
# Intentionally exit 0 so recovery evidence is committed even when javac fails.
