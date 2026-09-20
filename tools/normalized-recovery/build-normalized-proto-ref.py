#!/usr/bin/env python3
import json
import zipfile
from pathlib import Path

SRC = Path("recovery/l1jserver2-source-normalized.jar")
OUT = Path("recovery/compile-ref-normalized-proto.jar")
STATE = Path("recovery/compile_ref_normalized_proto.json")
REPORT = Path("recovery/COMPILE_REF_NORMALIZED_PROTO.md")

if not SRC.exists():
    raise SystemExit(f"missing normalized JAR: {SRC}")

with zipfile.ZipFile(SRC, "r") as zin:
    names = [
        n for n in zin.namelist()
        if n.endswith(".class") and (n.startswith("a/") or n.startswith("l1r/an/"))
    ]
    runtime = [n for n in names if n.startswith("a/")]
    messages = [n for n in names if n.startswith("l1r/an/")]
    if not runtime or not messages:
        raise SystemExit(f"unexpected normalized proto inventory: runtime={len(runtime)} messages={len(messages)}")
    with zipfile.ZipFile(OUT, "w", compression=zipfile.ZIP_DEFLATED) as zout:
        for n in sorted(names):
            zout.writestr(n, zin.read(n))

state = {
    "source": SRC.as_posix(),
    "output": OUT.as_posix(),
    "runtime_a_classes": len(runtime),
    "normalized_message_classes": len(messages),
    "other_game_packages_included": False,
    "purpose": "NORMALIZED_CORE_DIAGNOSTIC_ONLY",
    "final_source_only_gate": False,
}
STATE.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")
REPORT.write_text(
    "# Normalized Protobuf Compile Reference\n\n"
    "Purpose: isolate PBMessage source-recovery problems while compiling the rest of the normalized core.\n\n"
    f"- a/** runtime classes: **{len(runtime)}**\n"
    f"- l1r/an/** normalized message classes: **{len(messages)}**\n"
    "- Other game packages: **NOT INCLUDED**\n"
    "- Final source-only gate: **NO**\n\n"
    "This reference is diagnostic-only and must be removed for final donor-free validation.\n",
    encoding="utf-8",
)
print(json.dumps(state, indent=2))
