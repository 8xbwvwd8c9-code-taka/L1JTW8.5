#!/usr/bin/env python3
import json
import os
import shutil
import subprocess
from collections import Counter
from pathlib import Path

ROOT = Path(".")
SRC = ROOT / "_normalized-stage-src"
REC = ROOT / "recovery"
BUILD = REC / "normalized-stage-build"
LOG = REC / "normalized_stage_javac.log"
STATE = REC / "normalized_stage_compile.json"
REPORT = REC / "NORMALIZED_STAGE_COMPILE.md"
SOURCE_LIST = REC / "normalized_stage_source_files.txt"

if not SRC.exists():
    raise SystemExit(f"missing normalized stage: {SRC}")

if BUILD.exists():
    shutil.rmtree(BUILD)
BUILD.mkdir(parents=True)

sources = sorted(SRC.rglob("*.java"))
SOURCE_LIST.write_text("\n".join(p.as_posix() for p in sources) + "\n", encoding="utf-8")

lib_jars = sorted((ROOT / "lib").glob("*.jar"))
runtime_ref = REC / "compile-ref-protobuf-l1rpb.jar"
if not runtime_ref.exists():
    raise SystemExit(f"missing relocated protobuf ref: {runtime_ref}")
classpath = os.pathsep.join(str(p) for p in (lib_jars + [runtime_ref]))

cmd = [
    "javac",
    "-encoding", "UTF-8",
    "-source", "8",
    "-target", "8",
    "-proc:none",
    "-Xmaxerrs", "20000",
    "-Xmaxwarns", "5000",
    "-cp", classpath,
    "-d", str(BUILD),
    "@" + str(SOURCE_LIST),
]

with LOG.open("w", encoding="utf-8", errors="replace") as log:
    proc = subprocess.run(cmd, stdout=log, stderr=subprocess.STDOUT, text=True)

log_text = LOG.read_text(encoding="utf-8", errors="replace")
headers = [x for x in log_text.splitlines() if ": error:" in x]
files = Counter()
messages = Counter()
for line in headers:
    try:
        left, msg = line.split(": error: ", 1)
        rel = left.rsplit(":", 1)[0].replace(SRC.as_posix() + "/", "")
        files[rel] += 1
        messages[msg] += 1
    except Exception:
        pass

built = sorted(p.relative_to(BUILD).as_posix() for p in BUILD.rglob("*.class"))
state = {
    "gate": "NORMALIZED_STAGE_SOURCE_COMPILE",
    "source_root": SRC.as_posix(),
    "runtime_reference": runtime_ref.as_posix(),
    "full_donor_game_jar_on_classpath": False,
    "java_sources_submitted": len(sources),
    "compile_exit_code": proc.returncode,
    "generated_class_files_total": len(built),
    "javac_error_headers": len(headers),
    "error_file_count": len(files),
    "top_error_files": files.most_common(50),
    "top_error_messages": messages.most_common(50),
}
STATE.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")

md = [
    "# L1JTW8.5 Normalized Stage Source Compile",
    "",
    f"Status: **{'PASS' if proc.returncode == 0 else 'FAIL'}**",
    "",
    "## Boundary",
    "",
    "- Normalized game core supplied from source: **YES**",
    "- Full donor game JAR on classpath: **NO**",
    "- Embedded protobuf runtime supplied from relocated recovery-only binary reference: **YES**",
    "- Current transform family: **PROTOBUF_ROOT_PACKAGE_SHADOW**",
    "",
    "## Result",
    "",
    f"- Java sources submitted: **{len(sources)}**",
    f"- javac exit code: **{proc.returncode}**",
    f"- Generated class files: **{len(built)}**",
    f"- javac error headers: **{len(headers)}**",
    f"- Error files: **{len(files)}**",
    "",
    "## Top error files",
    "",
    "| File | Errors |",
    "|---|---:|",
]
md += [f"| {k} | {v} |" for k, v in files.most_common(50)]
md += [
    "",
    "## Next",
    "",
    "- Compare against the previous normalized compile (3983 errors / 64 files).",
    "- If protobuf namespace-shadow errors collapse, handle nested same-name builder identities as the next isolated family.",
]
REPORT.write_text("\n".join(md) + "\n", encoding="utf-8")
print(json.dumps(state, indent=2))
