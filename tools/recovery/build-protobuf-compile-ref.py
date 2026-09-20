#!/usr/bin/env python3
import json
import zipfile
from pathlib import Path

SRC = Path("l1jserver2.jar")
OUT = Path("recovery/compile-ref-protobuf-obf.jar")
REPORT = Path("recovery/COMPILE_REF_PROTOBUF_OBF.md")

if not SRC.exists():
    raise SystemExit(f"missing donor JAR: {SRC}")

with zipfile.ZipFile(SRC, "r") as zin:
    names = [n for n in zin.namelist() if n.startswith("a/") and n.endswith(".class")]
    if not names:
        raise SystemExit("no obfuscated protobuf classes found under a/")

    with zipfile.ZipFile(OUT, "w", compression=zipfile.ZIP_DEFLATED) as zout:
        for name in sorted(names):
            zout.writestr(name, zin.read(name))

top = sorted({n.split("$",1)[0] for n in names})
state = {
    "source_jar": SRC.as_posix(),
    "output_jar": OUT.as_posix(),
    "class_files": len(names),
    "top_level_classes": len(top),
    "scope": "a/** only",
    "game_core_classes_included": False,
}
Path("recovery/compile_ref_protobuf_obf.json").write_text(
    json.dumps(state, indent=2) + "\n", encoding="utf-8"
)

md = [
    "# Obfuscated Protobuf Compile Reference",
    "",
    "Purpose: provide the embedded/obfuscated Protobuf runtime as a binary dependency while keeping all L1J game classes source-only.",
    "",
    f"- Source: `{SRC.as_posix()}`",
    f"- Output: `{OUT.as_posix()}`",
    f"- Scope: **a/** only**",
    f"- Class files: **{len(names)}**",
    f"- Top-level classes: **{len(top)}**",
    "- L1J game packages included: **NO**",
    "- ai..bj application classes included: **NO**",
    "- l1j.server classes included: **NO**",
    "",
    "This is a recovery-only compile reference. It is not a replacement runtime JAR.",
]
REPORT.write_text("\n".join(md) + "\n", encoding="utf-8")
print(json.dumps(state, indent=2))
