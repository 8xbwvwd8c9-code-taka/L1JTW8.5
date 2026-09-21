#!/usr/bin/env python3
import json
import shutil
import subprocess
import sys
import tempfile
import zipfile
from pathlib import Path

SRC = Path("recovery/compile-ref-protobuf-l1rpb.jar")
OUT = Path("recovery/protobuf-runtime-src-vf")
STATE = Path("recovery/protobuf_runtime_source_decompile.json")
MD = Path("recovery/PROTOBUF_RUNTIME_SOURCE_DECOMPILE.md")

if len(sys.argv) != 2:
    raise SystemExit("usage: decompile-protobuf-runtime-vineflower.py <vineflower.jar>")

VF = Path(sys.argv[1])
if not SRC.exists():
    raise SystemExit(f"missing {SRC}")
if not VF.exists():
    raise SystemExit(f"missing {VF}")

if OUT.exists():
    shutil.rmtree(OUT)
OUT.mkdir(parents=True)

tmp = Path(tempfile.mkdtemp(prefix="l1jtw85-pbrt-vf-"))
vfout = tmp / "out"
vfout.mkdir()

try:
    cmd = [
        "java", "-jar", str(VF),
        "--log-level=warn",
        "--use-lvt-names=0",
        "--use-method-parameters=0",
        "--remove-synthetic=0",
        "--remove-bridge=0",
        "--skip-extra-files=1",
        "--rename-members=0",
        str(SRC), str(vfout),
    ]
    proc = subprocess.run(
        cmd,
        stdout=subprocess.PIPE,
        stderr=subprocess.STDOUT,
        text=True,
    )
    (OUT / "vineflower.log").write_text(
        proc.stdout,
        encoding="utf-8",
        errors="replace",
    )

    java_files = sorted(vfout.rglob("*.java"))
    copied = 0
    for p in java_files:
        rel = p.relative_to(vfout)
        dst = OUT / rel
        dst.parent.mkdir(parents=True, exist_ok=True)
        shutil.copy2(p, dst)
        copied += 1

    class_files = 0
    top_level_classes = set()
    with zipfile.ZipFile(SRC) as zf:
        for name in zf.namelist():
            if not name.endswith(".class"):
                continue
            class_files += 1
            top_level_classes.add(name.split("$", 1)[0])

    source_stems = {
        p.relative_to(vfout).with_suffix("").as_posix()
        for p in java_files
    }
    expected_stems = {
        Path(name).with_suffix("").as_posix()
        for name in top_level_classes
    }
    missing_source_stems = sorted(expected_stems - source_stems)
    extra_source_stems = sorted(source_stems - expected_stems)

    state = {
        "source_jar": str(SRC),
        "vineflower_exit_code": proc.returncode,
        "runtime_class_files": class_files,
        "runtime_top_level_classes": len(top_level_classes),
        "java_files_copied": copied,
        "expected_top_level_java_files": len(top_level_classes),
        "missing_source_stems": missing_source_stems,
        "extra_source_stems": extra_source_stems,
        "file_count_gate_is_final_completeness_gate": False,
        "final_completeness_gate": "post-javac normalized class-set comparison",
        "package": "l1rpb",
        "gameplay_logic_changed": False,
    }
    STATE.write_text(
        json.dumps(state, indent=2) + "\n",
        encoding="utf-8",
    )

    ok = proc.returncode == 0 and copied > 0

    MD.write_text(
        "# Protobuf Runtime Source Decompile\n\n"
        + f"Status: **{'PASS' if ok else 'FAIL'}**\n\n"
        + f"- Runtime class files: **{class_files}**\n"
        + f"- Top-level classes: **{len(top_level_classes)}**\n"
        + f"- Vineflower Java files: **{copied}**\n"
        + f"- Missing source stems by filename: **{len(missing_source_stems)}**\n"
        + f"- Extra source stems by filename: **{len(extra_source_stems)}**\n"
        + "- File-count mismatch is diagnostic only.\n"
        + "- Final completeness gate: post-javac normalized class-set comparison.\n"
        + "- Input is the recovery-relocated l1rpb/** runtime JAR.\n"
        + "- Gameplay logic changed: **NO**\n",
        encoding="utf-8",
    )

    print(json.dumps(state, indent=2))
    if not ok:
        raise SystemExit(
            f"protobuf runtime decompile failed: files={copied} exit={proc.returncode}"
        )
finally:
    shutil.rmtree(tmp, ignore_errors=True)
