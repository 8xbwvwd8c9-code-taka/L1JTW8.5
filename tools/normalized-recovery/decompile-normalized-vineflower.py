#!/usr/bin/env python3
import shutil
import subprocess
import sys
import tempfile
import zipfile
from pathlib import Path

JAR = Path("recovery/l1jserver2-source-normalized.jar")
REF = Path("recovery/l1jserver2-nonapp-ref.jar")
OUT = Path("recovery/normalized-src-vf")

if len(sys.argv) != 2:
    raise SystemExit("usage: decompile-normalized-vineflower.py <vineflower.jar>")
VF = Path(sys.argv[1])

if OUT.exists():
    shutil.rmtree(OUT)
OUT.mkdir(parents=True, exist_ok=True)

tmp = Path(tempfile.mkdtemp(prefix="l1jtw85-norm-vf-"))
classes = tmp / "classes"
classes.mkdir(parents=True, exist_ok=True)
try:
    with zipfile.ZipFile(JAR) as zf:
        app = [n for n in zf.namelist() if n.startswith("l1r/") and n.endswith(".class")]
        for n in app:
            p = classes / n
            p.parent.mkdir(parents=True, exist_ok=True)
            p.write_bytes(zf.read(n))

    cmd = [
        "java","-jar",str(VF),
        "--folder","--log-level=warn",
        "--use-lvt-names=0","--use-method-parameters=0",
        "--remove-synthetic=0","--remove-bridge=0",
        "--skip-extra-files=1","--rename-members=0",
        f"-e={REF}",str(classes),str(OUT),
    ]
    proc = subprocess.run(cmd, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
    Path("recovery/normalized-vineflower.log").write_text(proc.stdout, encoding="utf-8", errors="replace")
    java_count = len(list(OUT.rglob("*.java")))
    Path("recovery/NORMALIZED_DECOMPILE.md").write_text(
        "# Normalized Vineflower Decompile\n\n"
        f"- Vineflower exit: **{proc.returncode}**\n"
        f"- Java files: **{java_count}**\n"
        f"- Source root: {OUT.as_posix()}\n",
        encoding="utf-8",
    )
    print(f"VINEFLOWER_EXIT={proc.returncode}")
    print(f"JAVA_COUNT={java_count}")
    if proc.returncode != 0:
        raise SystemExit(proc.returncode)
finally:
    shutil.rmtree(tmp, ignore_errors=True)
