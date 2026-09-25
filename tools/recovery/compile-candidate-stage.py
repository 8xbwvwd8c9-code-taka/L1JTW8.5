#!/usr/bin/env python3
import csv
import json
import os
import shutil
import subprocess
from pathlib import Path

ROOT = Path(".")
SRC = ROOT / "_recovery-stage-src"
REC = ROOT / "recovery"
BUILD = REC / "candidate-build-classes"
LOG = REC / "candidate_full_tree_javac.log"
SOURCE_LIST = REC / "candidate_source_files.txt"
STATE = REC / "candidate_full_tree_compile.json"
REPORT = REC / "CANDIDATE_FULL_TREE_COMPILE.md"
MISSING = REC / "candidate_class_set_missing.txt"
EXTRA = REC / "candidate_class_set_extra.txt"
RAW_MISSING = REC / "candidate_class_set_missing_raw.txt"
RAW_EXTRA = REC / "candidate_class_set_extra_raw.txt"
INVENTORY = REC / "class_inventory.csv"
TRANSFORM = REC / "stage_transform.json"

REC.mkdir(parents=True, exist_ok=True)
if BUILD.exists():
    shutil.rmtree(BUILD)
BUILD.mkdir(parents=True, exist_ok=True)

sources = sorted(SRC.rglob("*.java"))
SOURCE_LIST.write_text("\n".join(str(p).replace("\\", "/") for p in sources) + "\n", encoding="utf-8")

lib_jars = sorted((ROOT / "lib").glob("*.jar"))
embedded_refs = [REC / "compile-ref-protobuf-obf.jar"]
for ref in embedded_refs:
    if not ref.exists():
        raise SystemExit(f"missing recovery compile reference: {ref}")
classpath_jars = lib_jars + embedded_refs
classpath = os.pathsep.join(str(p) for p in classpath_jars)

cmd = [
    "javac",
    "-encoding", "UTF-8",
    "-source", "8",
    "-target", "8",
    "-proc:none",
    "-Xmaxerrs", "5000",
    "-Xmaxwarns", "5000",
    "-cp", classpath,
    "-d", str(BUILD),
    "@" + str(SOURCE_LIST),
]

with LOG.open("w", encoding="utf-8", errors="replace") as log:
    proc = subprocess.run(cmd, stdout=log, stderr=subprocess.STDOUT, text=True)

built_paths = sorted(p.relative_to(BUILD).as_posix() for p in BUILD.rglob("*.class"))

donor_app = set()
if INVENTORY.exists():
    with INVENTORY.open(encoding="utf-8-sig", newline="") as f:
        donor_app = {row["ClassPath"] for row in csv.DictReader(f)}

renames = {}
if TRANSFORM.exists():
    data = json.loads(TRANSFORM.read_text(encoding="utf-8"))
    renames = data.get("recovery_only_class_renames", {})

# Map source-representation built names back to donor class names for class-set accounting.
built_to_donor = {}
for old_fq, new_fq in renames.items():
    old_path = old_fq.replace(".", "/")
    new_path = new_fq.replace(".", "/")
    built_to_donor[new_path + ".class"] = old_path + ".class"

normalized_built = set()
for p in built_paths:
    mapped = built_to_donor.get(p, p)
    # Also normalize any nested class generated from a renamed top-level class.
    if mapped == p:
        for old_fq, new_fq in renames.items():
            old_prefix = old_fq.replace(".", "/")
            new_prefix = new_fq.replace(".", "/")
            if p.startswith(new_prefix + "$"):
                mapped = old_prefix + p[len(new_prefix):]
                break
    normalized_built.add(mapped)

raw_built = set(built_paths)
raw_missing = sorted(donor_app - raw_built)
raw_extra = sorted(raw_built - donor_app)
missing = sorted(donor_app - normalized_built)
extra = sorted(normalized_built - donor_app)

RAW_MISSING.write_text("\n".join(raw_missing) + ("\n" if raw_missing else ""), encoding="utf-8")
RAW_EXTRA.write_text("\n".join(raw_extra) + ("\n" if raw_extra else ""), encoding="utf-8")
MISSING.write_text("\n".join(missing) + ("\n" if missing else ""), encoding="utf-8")
EXTRA.write_text("\n".join(extra) + ("\n" if extra else ""), encoding="utf-8")

version = subprocess.run(["javac", "-version"], capture_output=True, text=True)
compiler = (version.stderr or version.stdout).strip()

state = {
    "compiler": compiler,
    "source_level": "8",
    "target_level": "8",
    "source_root": SRC.as_posix(),
    "donor_jar_on_classpath": False,
    "third_party_jars": [p.as_posix() for p in lib_jars],
    "embedded_compile_refs": [p.as_posix() for p in embedded_refs],
    "java_sources_submitted": len(sources),
    "compile_exit_code": proc.returncode,
    "generated_class_files_total": len(built_paths),
    "donor_application_classes": len(donor_app),
    "built_application_classes_normalized": len(normalized_built & donor_app),
    "missing_application_classes_normalized": len(missing),
    "extra_application_classes_normalized": len(extra),
    "raw_missing_application_classes": len(raw_missing),
    "raw_extra_application_classes": len(raw_extra),
    "recovery_only_renames": renames,
}
STATE.write_text(json.dumps(state, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")

status = "PASS" if proc.returncode == 0 and not missing and not extra else "FAIL"
lines = [
    "# L1JTW8.5 Candidate Full-Tree Source-Only Compile",
    "",
    f"Status: **{status}**",
    "",
    "## Candidate transform",
    "",
    "- Seven CFR hard-tail files are replaced by Vineflower candidates.",
    "- Java-keyword class names are recovery-only renamed in the ephemeral source tree.",
    "- Donor bytecode is unchanged.",
    "",
    "## Build boundary",
    "",
    "- Game donor JAR on compile classpath: **NO**",
    "- Repository third-party JARs under lib/ are binary dependencies.",
    "- recovery/compile-ref-protobuf-obf.jar supplies only donor-embedded obfuscated Protobuf package a/**.",
    "- No ai..bj or l1j.server game classes are included in that compile reference.",
    f"- Candidate source root: **{SRC.as_posix()}**",
    "",
    "## Result",
    "",
    f"- Compiler: **{compiler}**",
    "- Source / target: **8 / 8**",
    f"- Java sources submitted: **{len(sources)}**",
    f"- javac exit code: **{proc.returncode}**",
    f"- Generated class files: **{len(built_paths)}**",
    f"- Donor application classes: **{len(donor_app)}**",
    f"- Built donor application classes after rename normalization: **{len(normalized_built & donor_app)}**",
    f"- Missing / extra after normalization: **{len(missing)} / {len(extra)}**",
    f"- Raw missing / extra before normalization: **{len(raw_missing)} / {len(raw_extra)}**",
    "",
    "## Interpretation",
    "",
    "- PASS requires javac exit 0 and normalized class-set missing/extra = 0/0.",
    "- On FAIL, candidate_full_tree_javac.log is the next recovery queue.",
]
REPORT.write_text("\n".join(lines) + "\n", encoding="utf-8")

print(json.dumps(state, indent=2))
# Always exit 0 so diagnostic evidence is committed.
