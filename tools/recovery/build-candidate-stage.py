#!/usr/bin/env python3
import json
import re
import shutil
from pathlib import Path

BASE = Path("recovered-src-obf")
VF = Path("recovery/vineflower-hard-tail")
STAGE = Path("_recovery-stage-src")
REC = Path("recovery")

HARD_TAIL = [
    "aj/aw.java",
    "aj/bx.java",
    "al/ab.java",
    "ao/aw.java",
    "ao/v.java",
    "be/dc.java",
    "bf/b.java",
]

RENAMES = {
    "be.do": "be.l1r_do_spmr",
    "bf.do": "bf.l1r_do_s134",
}

if STAGE.exists():
    shutil.rmtree(STAGE)
shutil.copytree(BASE, STAGE)

replaced = []
for rel in HARD_TAIL:
    src = VF / rel
    dst = STAGE / rel
    if not src.exists():
        raise SystemExit(f"missing Vineflower override: {src}")
    shutil.copy2(src, dst)
    replaced.append(rel)

def apply_simple_use(text: str, old_fq: str, new_fq: str) -> str:
    old_pkg, old_simple = old_fq.rsplit(".", 1)
    new_pkg, new_simple = new_fq.rsplit(".", 1)

    had_import = re.search(r"(?m)^\s*import\s+" + re.escape(old_fq) + r"\s*;", text) is not None
    text = re.sub(
        r"(?m)^(\s*import\s+)" + re.escape(old_fq) + r"(\s*;)",
        rf"\1{new_fq}\2",
        text,
    )
    text = text.replace(old_fq, new_fq)

    if had_import:
        # Only class-reference contexts; never global-replace Java keyword 'do'.
        text = re.sub(r"\bnew\s+" + re.escape(old_simple) + r"\s*\(", f"new {new_simple}(", text)
        text = re.sub(r"\(" + re.escape(old_simple) + r"\)", f"({new_simple})", text)
        text = re.sub(r"\b" + re.escape(old_simple) + r"\s*\.", new_simple + ".", text)
        text = re.sub(
            r"\b" + re.escape(old_simple) + r"\s+([A-Za-z_$][\w$]*)\s*([=;,\)])",
            rf"{new_simple} \1\2",
            text,
        )
        text = re.sub(r"<\s*" + re.escape(old_simple) + r"\s*>", f"<{new_simple}>", text)
    return text

# Rename declaration files first.
decls = [
    ("be/do.java", "do", "l1r_do_spmr"),
    ("bf/do.java", "do", "l1r_do_s134"),
]
for rel, old, new in decls:
    p = STAGE / rel
    text = p.read_text(encoding="utf-8", errors="replace")
    text = re.sub(r"\bpublic\s+class\s+" + re.escape(old) + r"\b", f"public class {new}", text, count=1)
    text = re.sub(r"\bpublic\s+" + re.escape(old) + r"\s*\(", f"public {new}(", text)
    new_path = p.with_name(new + ".java")
    new_path.write_text(text, encoding="utf-8")
    p.unlink()

changed_files = []
for p in STAGE.rglob("*.java"):
    text = p.read_text(encoding="utf-8", errors="replace")
    original = text
    for old_fq, new_fq in RENAMES.items():
        text = apply_simple_use(text, old_fq, new_fq)
    if text != original:
        p.write_text(text, encoding="utf-8")
        changed_files.append(p.relative_to(STAGE).as_posix())

state = {
    "hard_tail_overrides": replaced,
    "recovery_only_class_renames": RENAMES,
    "reference_files_changed_by_rename": sorted(changed_files),
}
(REC / "stage_transform.json").write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")

md = [
    "# L1JTW8.5 Recovery Candidate Transform",
    "",
    "This transform builds an ephemeral source tree for compile validation. It does not alter donor bytecode.",
    "",
    "## Vineflower hard-tail overrides",
    "",
]
md += [f"- {x}" for x in replaced]
md += [
    "",
    "## Java-source representation renames",
    "",
    "- be.do -> be.l1r_do_spmr (SourceFile=S_SPMR.java)",
    "- bf.do -> bf.l1r_do_s134 (SourceFile=S_134.java)",
    "",
    "Reason: do is a Java language keyword although the JVM classfile name is valid.",
    "These are recovery-only names and must be normalized in later donor-vs-built ABI/class-set comparison.",
    "",
    f"Files changed by rename references: **{len(changed_files)}**",
]
(REC / "STAGE_TRANSFORM.md").write_text("\n".join(md) + "\n", encoding="utf-8")

print(json.dumps(state, indent=2))
