#!/usr/bin/env python3
import os
import shutil
import subprocess
import sys
import tempfile
import zipfile
from pathlib import Path

JAR = Path("l1jserver2.jar")
OUT = Path("recovery/vineflower-hard-tail")
TARGETS = [
    "aj/aw",
    "aj/bx",
    "al/ab",
    "ao/aw",
    "ao/v",
    "be/dc",
    "bf/b",
]

if len(sys.argv) != 2:
    raise SystemExit("usage: decompile-hard-tail-vineflower.py <vineflower.jar>")

VF = Path(sys.argv[1])
if not JAR.exists():
    raise SystemExit(f"missing {JAR}")
if not VF.exists():
    raise SystemExit(f"missing {VF}")

OUT.mkdir(parents=True, exist_ok=True)
tmp_root = Path(tempfile.mkdtemp(prefix="l1jtw85-vf-"))
try:
    with zipfile.ZipFile(JAR) as zf:
        for target in TARGETS:
            class_path = target + ".class"
            data = zf.read(class_path)
            p = tmp_root / class_path
            p.parent.mkdir(parents=True, exist_ok=True)
            p.write_bytes(data)

    for target in TARGETS:
        class_file = tmp_root / (target + ".class")
        work_out = tmp_root / ("out-" + target.replace("/", "_"))
        work_out.mkdir(parents=True, exist_ok=True)

        cmd = [
            "java", "-jar", str(VF),
            "--folder",
            "--log-level=warn",
            "--use-lvt-names=0",
            "--use-method-parameters=0",
            "--remove-synthetic=0",
            "--remove-bridge=0",
            "--skip-extra-files=1",
            f"-e={JAR}",
            str(class_file),
            str(work_out),
        ]
        proc = subprocess.run(cmd, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
        log_path = OUT / (target.replace("/", ".") + ".vineflower.log")
        log_path.write_text(proc.stdout, encoding="utf-8", errors="replace")

        expected = work_out / (target + ".java")
        if not expected.exists():
            # Vineflower sometimes writes only the simple-name path for a direct class input.
            fallback = list(work_out.rglob(Path(target).name + ".java"))
            if fallback:
                expected = fallback[0]

        if proc.returncode != 0 or not expected.exists():
            print(f"FAIL {target} exit={proc.returncode} output={expected.exists()}")
            continue

        dst = OUT / (target + ".java")
        dst.parent.mkdir(parents=True, exist_ok=True)
        shutil.copy2(expected, dst)
        print(f"PASS {target} -> {dst}")
finally:
    shutil.rmtree(tmp_root, ignore_errors=True)
