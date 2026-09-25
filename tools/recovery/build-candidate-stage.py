#!/usr/bin/env python3
import json
import re
import shutil
from pathlib import Path

BASE = Path("recovered-src-obf")
VF1 = Path("recovery/vineflower-hard-tail")
VF2 = Path("recovery/vineflower-stage2")
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

PROTOBUF_STAGE2 = [
    "an/a.java","an/b.java","an/c.java","an/d.java","an/e.java",
    "an/f.java","an/g.java","an/h.java","an/i.java",
]

CLASS_RENAMES = {
    "be.do": "be.l1r_do_spmr",
    "bf.do": "bf.l1r_do_s134",
}

MEMBER_RENAMES = {
    "ap.u field do:I": "l1r_do_field",
    "ap.u method do:()I": "l1r_do_effect_heal",
    "bg.b field do:I": "l1r_do_1014",
    "bj.e field do:I": "l1r_do_157",
}

if STAGE.exists():
    shutil.rmtree(STAGE)
shutil.copytree(BASE, STAGE)

replaced = []
for rel in HARD_TAIL:
    src = VF1 / rel
    dst = STAGE / rel
    if not src.exists():
        raise SystemExit(f"missing Vineflower hard-tail override: {src}")
    shutil.copy2(src, dst)
    replaced.append(rel)

protobuf_replaced = []
for rel in PROTOBUF_STAGE2:
    src = VF2 / rel
    dst = STAGE / rel
    if not src.exists():
        raise SystemExit(f"missing Vineflower protobuf override: {src}")
    shutil.copy2(src, dst)
    protobuf_replaced.append(rel)

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

# Java-keyword top-level class names: recovery-only source representation rename.
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
    for old_fq, new_fq in CLASS_RENAMES.items():
        text = apply_simple_use(text, old_fq, new_fq)

    # Exactly one JVM method named "do" exists in the application audit: ap.u.do()I.
    text = re.sub(r"\.do\s*\(", ".l1r_do_effect_heal(", text)

    # Fully-qualified constant references, if present.
    text = text.replace("bg.b.do", "bg.b.l1r_do_1014")
    text = text.replace("bj.e.do", "bj.e.l1r_do_157")

    if text != original:
        p.write_text(text, encoding="utf-8")
        changed_files.append(p.relative_to(STAGE).as_posix())

# Protobuf builder legality repair.
# Donor bytecode proves these 44 sites are:
#   invokestatic <message synthetic accessor>():Z -> pop -> return
# Both CFR and Vineflower inline that accessor as an illegal bare boolean field read.
# Re-expressing it as an empty if preserves the field read side effect and lets javac
# regenerate the required synthetic accessor automatically.
protobuf_empty_if_repairs = []
protobuf_rx = re.compile(r"^(\s*)([A-Za-z_$][\w$]*(?:\.[A-Za-z_$][\w$]*)+\.m);\s*$")
for rel in PROTOBUF_STAGE2:
    p = STAGE / rel
    lines = p.read_text(encoding="utf-8", errors="replace").splitlines()
    out_lines = []
    for lineno, line in enumerate(lines, 1):
        m = protobuf_rx.match(line)
        if m:
            indent, expr = m.groups()
            out_lines.append(f"{indent}if ({expr}) {{}}")
            protobuf_empty_if_repairs.append({
                "file": rel,
                "line": lineno,
                "expression": expr,
            })
        else:
            out_lines.append(line)
    p.write_text("\n".join(out_lines) + "\n", encoding="utf-8")

if len(protobuf_empty_if_repairs) != 44:
    raise SystemExit(
        f"protobuf empty-if repair count mismatch: "
        f"{len(protobuf_empty_if_repairs)} != 44"
    )

# Precise declaration/private-field repairs.
p = STAGE / "ap/u.java"
text = p.read_text(encoding="utf-8", errors="replace")
text = re.sub(r"(?m)^(\s*private\s+int\s+)do(\s*=\s*0\s*;)", r"\1l1r_do_field\2", text, count=1)
text = text.replace("this.do +=", "this.l1r_do_field +=")
text = text.replace("return this.do;", "return this.l1r_do_field;")
text = re.sub(r"(?m)^(\s*public\s+int\s+)do(\s*\(\s*\)\s*\{)", r"\1l1r_do_effect_heal\2", text, count=1)
p.write_text(text, encoding="utf-8")

for rel, new_name, expected in [
    ("bg/b.java", "l1r_do_1014", "1014"),
    ("bj/e.java", "l1r_do_157", "157"),
]:
    p = STAGE / rel
    text = p.read_text(encoding="utf-8", errors="replace")
    text, n = re.subn(
        r"(?m)^(\s*public\s+static\s+final\s+int\s+)do(\s*=\s*" + expected + r"\s*;)",
        rf"\1{new_name}\2",
        text,
        count=1,
    )
    if n != 1:
        raise SystemExit(f"keyword member declaration not found: {rel}")
    p.write_text(text, encoding="utf-8")

# Reject any remaining obvious Java-keyword member declaration/use before javac.
keyword_residuals = []
checks = [
    re.compile(r"\b(?:int|long|short|byte|boolean|char|float|double|void)\s+do\b"),
    re.compile(r"\.do\s*\("),
]
for p in STAGE.rglob("*.java"):
    text = p.read_text(encoding="utf-8", errors="replace")
    for rx in checks:
        for m in rx.finditer(text):
            keyword_residuals.append({
                "file": p.relative_to(STAGE).as_posix(),
                "line": text.count("\n", 0, m.start()) + 1,
                "match": m.group(0),
            })

state = {
    "hard_tail_overrides": replaced,
    "protobuf_stage2_overrides": protobuf_replaced,
    "recovery_only_class_renames": CLASS_RENAMES,
    "recovery_only_member_renames": MEMBER_RENAMES,
    "reference_files_changed_by_class_rename": sorted(changed_files),
    "protobuf_empty_if_repairs": protobuf_empty_if_repairs,
    "protobuf_empty_if_repair_count": len(protobuf_empty_if_repairs),
    "keyword_member_residuals": keyword_residuals,
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
md += ["", "## Vineflower protobuf overrides", ""]
md += [f"- {x}" for x in protobuf_replaced]
md += [
    "",
    "## Java-source representation renames",
    "",
    "- be.do -> be.l1r_do_spmr",
    "- bf.do -> bf.l1r_do_s134",
    "- ap.u field do:I -> l1r_do_field",
    "- ap.u method do:()I -> l1r_do_effect_heal",
    "- bg.b field do:I -> l1r_do_1014",
    "- bj.e field do:I -> l1r_do_157",
    "",
    "## Protobuf builder legality repair",
    "",
    f"- Donor-verified empty boolean-read sites repaired: **{len(protobuf_empty_if_repairs)}**",
    "- Repair form: bare boolean field read -> empty if expression.",
    "- Donor proof: synthetic accessor ()Z -> pop -> return.",
    "",
    f"Keyword-member residuals before javac: **{len(keyword_residuals)}**",
]
(REC / "STAGE_TRANSFORM.md").write_text("\n".join(md) + "\n", encoding="utf-8")

if keyword_residuals:
    raise SystemExit("keyword member residuals remain; see recovery/stage_transform.json")

print(json.dumps(state, indent=2))
