#!/usr/bin/env python3
import json
import os
import shutil
import subprocess
from collections import Counter
from pathlib import Path

ROOT = Path(".")
SRC = ROOT / "recovery" / "normalized-src-vf"
REC = ROOT / "recovery"
BUILD = REC / "normalized-nonproto-build"
LOG = REC / "normalized_nonproto_javac.log"
STATE = REC / "normalized_nonproto_compile.json"
REPORT = REC / "NORMALIZED_NONPROTO_COMPILE.md"
SOURCE_LIST = REC / "normalized_nonproto_source_files.txt"

if not SRC.exists():
    raise SystemExit(f"missing normalized source tree: {SRC}")

if BUILD.exists():
    shutil.rmtree(BUILD)
BUILD.mkdir(parents=True)

sources = sorted(
    p for p in SRC.rglob("*.java")
    if "/l1r/an/" not in p.as_posix()
)
SOURCE_LIST.write_text(
    "\n".join(p.as_posix() for p in sources) + "\n",
    encoding="utf-8",
)

lib_jars = sorted((ROOT / "lib").glob("*.jar"))
proto_ref = REC / "compile-ref-normalized-proto.jar"
if not proto_ref.exists():
    raise SystemExit(f"missing normalized protobuf diagnostic ref: {proto_ref}")

classpath = os.pathsep.join(str(p) for p in (lib_jars + [proto_ref]))
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
        path = left.rsplit(":", 1)[0]
        rel = path.replace(SRC.as_posix() + "/", "")
        files[rel] += 1
        messages[msg] += 1
    except Exception:
        pass

built = sorted(p.relative_to(BUILD).as_posix() for p in BUILD.rglob("*.class"))
state = {
    "gate": "NORMALIZED_NONPROTO_DIAGNOSTIC_ONLY",
    "source_root": SRC.as_posix(),
    "excluded_source_package": "l1r/an/**",
    "compile_reference": proto_ref.as_posix(),
    "donor_full_game_jar_on_classpath": False,
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
    "# L1JTW8.5 Normalized Non-Protobuf Diagnostic Compile",
    "",
    f"Status: **{'PASS' if proc.returncode == 0 else 'FAIL'}**",
    "",
    "## Boundary",
    "",
    "- Gate: **NORMALIZED_NONPROTO_DIAGNOSTIC_ONLY**",
    "- l1r/an/** source excluded: **YES**",
    "- a/** runtime + normalized l1r/an/** donor compile reference: **YES**",
    "- Other normalized game packages supplied from source: **YES**",
    "- Full donor game JAR on classpath: **NO**",
    "- This gate is diagnostic only; it cannot satisfy final source-only PASS.",
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
    "## Meaning",
    "",
    "- If this gate is near-clean, protobuf source representation is the dominant blocker.",
    "- Remaining non-protobuf failures become the next isolated error-family queue.",
]
REPORT.write_text("\n".join(md) + "\n", encoding="utf-8")
print(json.dumps(state, indent=2))
