#!/usr/bin/env python3
import shutil
import subprocess
import sys
import tempfile
import zipfile
from pathlib import Path

JAR = Path("l1jserver2.jar")
OUT = Path("recovery/vineflower-stage2")
TARGETS = [
    "an/a","an/b","an/c","an/d","an/e","an/f","an/g","an/h","an/i",
    "ap/u","aq/c","bg/b","bj/e",
]

if len(sys.argv) != 2:
    raise SystemExit("usage: decompile-stage2-vineflower.py <vineflower.jar>")
VF = Path(sys.argv[1])

OUT.mkdir(parents=True, exist_ok=True)
tmp_root = Path(tempfile.mkdtemp(prefix="l1jtw85-vf2-"))
classes_root = tmp_root / "classes"
vf_out = tmp_root / "out"
classes_root.mkdir(parents=True, exist_ok=True)
vf_out.mkdir(parents=True, exist_ok=True)

try:
    with zipfile.ZipFile(JAR) as zf:
        names = set(zf.namelist())
        for target in TARGETS:
            prefix = target + "$"
            wanted = [n for n in names if n == target + ".class" or (n.startswith(prefix) and n.endswith(".class"))]
            if not wanted:
                raise SystemExit(f"no class family found for {target}")
            for n in wanted:
                p = classes_root / n
                p.parent.mkdir(parents=True, exist_ok=True)
                p.write_bytes(zf.read(n))

    cmd = [
        "java", "-jar", str(VF),
        "--folder",
        "--log-level=warn",
        "--use-lvt-names=0",
        "--use-method-parameters=0",
        "--remove-synthetic=0",
        "--remove-bridge=0",
        "--skip-extra-files=1",
        "--rename-members=1",
        f"-e={JAR}",
        str(classes_root),
        str(vf_out),
    ]
    proc = subprocess.run(cmd, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
    (OUT / "vineflower-stage2.log").write_text(proc.stdout, encoding="utf-8", errors="replace")

    copied = 0
    for target in TARGETS:
        expected = vf_out / (target + ".java")
        if not expected.exists():
            fallbacks = list(vf_out.rglob(Path(target).name + ".java"))
            expected = fallbacks[0] if fallbacks else expected
        if expected.exists():
            dst = OUT / (target + ".java")
            dst.parent.mkdir(parents=True, exist_ok=True)
            shutil.copy2(expected, dst)
            copied += 1
            print(f"PASS {target} -> {dst}")
        else:
            print(f"MISS {target}")

    print(f"VINEFLOWER_EXIT={proc.returncode}")
    print(f"COPIED={copied}/{len(TARGETS)}")
finally:
    shutil.rmtree(tmp_root, ignore_errors=True)
