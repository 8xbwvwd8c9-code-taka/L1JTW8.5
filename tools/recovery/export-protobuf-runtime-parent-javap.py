#!/usr/bin/env python3
import json
import subprocess
from pathlib import Path

REC = Path("recovery")
JAR = REC / "compile-ref-protobuf-l1rpb.jar"
OUT = REC / "protobuf_runtime_parent_javap.txt"
STATE = REC / "protobuf_runtime_parent_javap.json"

targets = ["l1rpb.p$a", "l1rpb.a$a", "l1rpb.x$a", "l1rpb.y$a", "l1rpb.c", "l1rpb.ab"]
chunks = []
rows = []
for cls in targets:
    cp = subprocess.run(
        ["javap", "-classpath", str(JAR), "-p", "-s", cls],
        text=True, capture_output=True
    )
    rows.append({"class": cls, "exit_code": cp.returncode})
    chunks.append(f"===== {cls} =====\n{cp.stdout}\n{cp.stderr}\n")
OUT.write_text("\n".join(chunks), encoding="utf-8")
STATE.write_text(json.dumps({"jar": str(JAR), "targets": rows}, indent=2)+"\n", encoding="utf-8")
print(OUT.read_text(encoding="utf-8"))
failed = [x for x in rows if x["exit_code"] != 0]
if failed:
    raise SystemExit(f"javap validation failed: {failed}")
