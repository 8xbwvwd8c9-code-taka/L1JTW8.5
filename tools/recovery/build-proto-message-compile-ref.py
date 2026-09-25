#!/usr/bin/env python3
import json
import zipfile
from pathlib import Path

SRC = Path("l1jserver2.jar")
OUT = Path("recovery/compile-ref-proto-messages.jar")
REPORT = Path("recovery/COMPILE_REF_PROTO_MESSAGES.md")
STATE = Path("recovery/compile_ref_proto_messages.json")

if not SRC.exists():
    raise SystemExit(f"missing donor JAR: {SRC}")

with zipfile.ZipFile(SRC, "r") as zin:
    names = [
        n for n in zin.namelist()
        if n.endswith(".class") and (n.startswith("a/") or n.startswith("an/"))
    ]
    a_names = [n for n in names if n.startswith("a/")]
    an_names = [n for n in names if n.startswith("an/")]
    if not a_names or not an_names:
        raise SystemExit(f"unexpected compile-ref inventory: a={len(a_names)} an={len(an_names)}")

    with zipfile.ZipFile(OUT, "w", compression=zipfile.ZIP_DEFLATED) as zout:
        for name in sorted(names):
            zout.writestr(name, zin.read(name))

top_a = sorted({n.split("$",1)[0] for n in a_names})
top_an = sorted({n.split("$",1)[0] for n in an_names})

state = {
    "source_jar": SRC.as_posix(),
    "output_jar": OUT.as_posix(),
    "scope": ["a/**", "an/**"],
    "a_class_files": len(a_names),
    "a_top_level_classes": len(top_a),
    "an_class_files": len(an_names),
    "an_top_level_classes": len(top_an),
    "purpose": "NON_PROTO_DIAGNOSTIC_ONLY",
    "final_source_only_gate": False,
    "other_game_packages_included": False,
}
STATE.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")

md = [
    "# Proto Runtime + Message Compile Reference",
    "",
    "Purpose: isolate the Java-source representation problems in the obfuscated protobuf message package while compiling the rest of the recovered game core.",
    "",
    "- Scope: **a/** (embedded protobuf runtime) + **an/** (protobuf message classes)",
    f"- a/** class files: **{len(a_names)}**",
    f"- a/** top-level classes: **{len(top_a)}**",
    f"- an/** class files: **{len(an_names)}**",
    f"- an/** top-level classes: **{len(top_an)}**",
    "- Other game packages ai..bj: **NOT INCLUDED**",
    "- l1j.server: **NOT INCLUDED**",
    "- Gate type: **NON_PROTO_DIAGNOSTIC_ONLY**",
    "",
    "This reference must not be treated as final source-only recovery success. The an/** source family remains a separate recovery work item.",
]
REPORT.write_text("\n".join(md) + "\n", encoding="utf-8")
print(json.dumps(state, indent=2))
