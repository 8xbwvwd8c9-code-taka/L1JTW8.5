#!/usr/bin/env python3
import json
import re
import shutil
from pathlib import Path

BASE = Path("recovery/normalized-src-vf")
STAGE = Path("_normalized-stage-src")
REC = Path("recovery")
STATE = REC / "normalized_stage_transform.json"
REPORT = REC / "NORMALIZED_STAGE_TRANSFORM.md"

if not BASE.exists():
    raise SystemExit(f"missing normalized source tree: {BASE}")

if STAGE.exists():
    shutil.rmtree(STAGE)
shutil.copytree(BASE, STAGE)

changed_files = []
replacement_count = 0

# Error-family 1 only:
# Relocate standalone references to embedded protobuf root package a/** -> l1rpb/**
# inside normalized protobuf message sources. Do not touch member expressions such
# as this.a.*, var.a.*, or unrelated game code.
for p in sorted((STAGE / "l1r" / "an").glob("*.java")):
    text = p.read_text(encoding="utf-8", errors="replace")
    original = text

    # imports are unambiguous package references.
    text, n1 = re.subn(r"(?m)^(\s*import\s+)a\.", r"\1l1rpb.", text)

    # Fully-qualified runtime type/package references emitted by Vineflower.
    # Do NOT rewrite direct calls like a.d(...), a.f(...), a.h(...): generated
    # protobuf code also uses "a" as a parser/static field.
    runtime_type_rx = re.compile(
        r"(?<![A-Za-z0-9_.$])a\.([A-Za-z_$][\w$]*)(?=(?:\.|\s|<|\[|>|,|\)))"
    )
    text, n2 = runtime_type_rx.subn(r"l1rpb.\1", text)

    # A package cannot directly own a method. This catches accidental rewrites
    # such as parser-field a.d(...) -> l1rpb.d(...).
    suspicious = []
    for sm in re.finditer(r"(?<!new\s)\bl1rpb\.([A-Za-z_$][\w$]*)\s*\(", text):
        suspicious.append({
            "file": p.relative_to(STAGE).as_posix(),
            "line": text.count("\n", 0, sm.start()) + 1,
            "match": sm.group(0),
        })
    if suspicious:
        raise SystemExit(
            f"suspicious direct l1rpb method rewrite in {p}: {suspicious[:5]}"
        )

    if text != original:
        p.write_text(text, encoding="utf-8")
        changed_files.append(p.relative_to(STAGE).as_posix())
        replacement_count += n1 + n2

state = {
    "source_root": BASE.as_posix(),
    "stage_root": STAGE.as_posix(),
    "error_family": "PROTOBUF_ROOT_PACKAGE_SHADOW",
    "old_runtime_package": "a/**",
    "new_runtime_package": "l1rpb/**",
    "changed_files": changed_files,
    "changed_file_count": len(changed_files),
    "replacement_count": replacement_count,
    "nested_identity_collisions_repaired": False,
    "gameplay_logic_changed": False,
}
STATE.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")
REPORT.write_text(
    "# Normalized Recovery Stage Transform\n\n"
    "Current work unit repairs exactly one source-representation family: embedded protobuf runtime root-package shadowing.\n\n"
    f"- Changed files: **{len(changed_files)}**\n"
    f"- Package-reference replacements: **{replacement_count}**\n"
    "- a/** -> l1rpb/**: **RECOVERY-ONLY**\n"
    "- Nested same-name class collisions repaired in this unit: **NO**\n"
    "- Gameplay logic changed: **NO**\n"
    "- Final donor comparison must normalize l1rpb/** back to a/**.\n",
    encoding="utf-8",
)
print(json.dumps(state, indent=2))
