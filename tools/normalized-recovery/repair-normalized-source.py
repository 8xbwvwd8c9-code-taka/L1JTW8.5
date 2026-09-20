#!/usr/bin/env python3
import csv
import json
import re
from pathlib import Path

SRC = Path("recovery/normalized-src-vf")
JAVAP = Path("recovery/protobuf-builder-javap.txt")
MAP = Path("recovery/source_namespace_map.csv")
OUT_JSON = Path("recovery/normalized_source_repairs.json")
OUT_MD = Path("recovery/NORMALIZED_SOURCE_REPAIRS.md")

# old internal -> new internal, including inner classes
ns = {}
with MAP.open(encoding="utf-8-sig", newline="") as f:
    for row in csv.DictReader(f):
        ns[row["OldInternal"]] = row["NewInternal"]

lines = JAVAP.read_text(encoding="utf-8-sig", errors="replace").splitlines()
current = ""
protobuf = []
for i,line in enumerate(lines):
    cm = re.match(r"^===== CLASS (.+) =====$", line)
    if cm:
        current = cm.group(1).replace(".", "/")
        continue
    mh = re.match(r"^\s*private void ([A-Za-z0-9_$]+)\(\);?\s*$", line)
    if not mh:
        continue
    block = "\n".join(lines[i+1:i+10])
    m = re.search(
        r"\b0:\s+invokestatic\s+#\d+\s+// Method ([^:]+):\(\)Z"
        r"[\s\S]*?\b3:\s+pop[\s\S]*?\b4:\s+return",
        block,
    )
    if not m:
        continue
    owner_method = m.group(1)
    dot = owner_method.rfind(".")
    owner = owner_method[:dot]
    target = owner_method[dot+1:]
    protobuf.append({
        "builder": current,
        "builder_method": mh.group(1),
        "owner": owner,
        "target_method": target,
    })

if len(protobuf) != 44:
    raise SystemExit(f"expected 44 protobuf accessor repairs, got {len(protobuf)}")

protobuf_repairs = []
for ent in protobuf:
    owner = ent["owner"]
    if owner not in ns:
        raise SystemExit(f"normalized owner missing: {owner}")
    new_owner = ns[owner]
    top = new_owner.rsplit("/",1)[-1]
    expr = top.replace("$", ".")
    top_internal = owner.split("$",1)[0]
    new_top = ns[top_internal]
    source_path = SRC / (new_top + ".java")
    if not source_path.exists():
        raise SystemExit(f"source missing: {source_path}")
    text = source_path.read_text(encoding="utf-8", errors="replace")
    rx = re.compile(r"(?m)^(\s*)" + re.escape(expr) + r"\.m;\s*$")
    repl = r"\1" + expr + "." + ent["target_method"] + "();"
    text2, n = rx.subn(repl, text, count=1)
    if n != 1:
        raise SystemExit(f"protobuf repair not found exactly once: {source_path} :: {expr}.m")
    source_path.write_text(text2, encoding="utf-8")
    protobuf_repairs.append({
        "file": source_path.as_posix(),
        "expression": expr + ".m",
        "replacement": expr + "." + ent["target_method"] + "()",
        "bytecode": "invokestatic ()Z -> pop -> return",
    })

# Java-keyword members confirmed by classfile audit.
pc = SRC / "l1r/ap/L1PcInstance.java"
text = pc.read_text(encoding="utf-8", errors="replace")
rules = [
    (r"(?m)^(\s*private\s+int\s+)do(\s*=\s*0\s*;)", r"\1l1r_do_field\2", 1),
    (r"\bthis\.do\s*\+=", "this.l1r_do_field +=", 1),
    (r"(?m)^(\s*public\s+int\s+)do(\s*\(\s*\)\s*\{)", r"\1l1r_do_effect_heal\2", 1),
    (r"\breturn\s+this\.do\s*;", "return this.l1r_do_field;", 1),
]
for pattern,repl,expected in rules:
    text,n = re.subn(pattern,repl,text,count=expected)
    if n != expected:
        raise SystemExit(f"L1PcInstance keyword repair mismatch: {pattern} count={n}")
pc.write_text(text,encoding="utf-8")

# The classfile audit proves this is the only application method named "do".
method_calls = 0
for p in SRC.rglob("*.java"):
    text = p.read_text(encoding="utf-8", errors="replace")
    text2,n = re.subn(r"\.do\s*\(", ".l1r_do_effect_heal(", text)
    if n:
        p.write_text(text2,encoding="utf-8")
        method_calls += n

for rel,new_name,value in [
    ("l1r/bg/L1SkillId.java","l1r_do_1014","1014"),
    ("l1r/bj/Opcodes.java","l1r_do_157","157"),
]:
    p=SRC/rel
    text=p.read_text(encoding="utf-8", errors="replace")
    text,n=re.subn(
        r"(?m)^(\s*public\s+static\s+final\s+int\s+)do(\s*=\s*"+value+r"\s*;)",
        r"\1"+new_name+r"\2",text,count=1
    )
    if n!=1:
        raise SystemExit(f"constant keyword repair mismatch: {rel}")
    p.write_text(text,encoding="utf-8")

constant_refs=0
for p in SRC.rglob("*.java"):
    text=p.read_text(encoding="utf-8", errors="replace")
    text2,n1=re.subn(r"\bL1SkillId\.do\b","L1SkillId.l1r_do_1014",text)
    text2,n2=re.subn(r"\bOpcodes\.do\b","Opcodes.l1r_do_157",text2)
    if n1+n2:
        p.write_text(text2,encoding="utf-8")
        constant_refs += n1+n2

# Package-shadow repair: embedded protobuf class a.g is imported as g, while
# some game classes also declare a field/local named 'a'. In Java source,
# a.g.a(...) is then parsed as dereferencing that primitive/local 'a'.
# Using the imported type name g.a(...) is bytecode-equivalent and avoids
# the namespace shadowing. Only apply in files that explicitly import a.g.
protobuf_g_shadow_repairs = 0
protobuf_g_shadow_files = []
for p in SRC.rglob("*.java"):
    text = p.read_text(encoding="utf-8", errors="replace")
    if not re.search(r"(?m)^import\s+a\.g\s*;\s*$", text):
        continue
    text2, n = re.subn(r"\ba\.g\.a\s*\(", "g.a(", text)
    if n:
        p.write_text(text2, encoding="utf-8")
        protobuf_g_shadow_repairs += n
        protobuf_g_shadow_files.append(p.as_posix())


# Vineflower preserves obfuscator-generated Comparator bridge methods as
# compare(Object,Object), while the typed implementation remains under its
# obfuscated name (usually a(T,T)). That shape is legal JVM bytecode but
# illegal Java source for Comparator<T>. Reconstruct a typed compare(T,T)
# wrapper that delegates to the preserved implementation. This is a
# recovery-only source representation repair; donor bytecode remains ground truth.
comparator_bridge_repairs = []
bridge_rx = re.compile(
    r"(?ms)^(?P<indent>\s*)// \$VF: synthetic method\s*\n"
    r"(?P=indent)@Override\s*\n"
    r"(?P=indent)public int compare\(Object (?P<v1>[A-Za-z_$][\w$]*), Object (?P<v2>[A-Za-z_$][\w$]*)\) \{\s*\n"
    r"(?P=indent)\s*return this\.(?P<delegate>[A-Za-z_$][\w$]*)\(\((?P<t1>[^)]+)\)(?P=v1), \((?P<t2>[^)]+)\)(?P=v2)\);\s*\n"
    r"(?P=indent)\}"
)
for p in SRC.rglob("*.java"):
    text = p.read_text(encoding="utf-8", errors="replace")
    def _bridge_repl(m):
        t1 = m.group("t1").strip()
        t2 = m.group("t2").strip()
        if t1 != t2:
            return m.group(0)
        comparator_bridge_repairs.append({
            "file": p.as_posix(),
            "type": t1,
            "delegate": m.group("delegate"),
        })
        ind = m.group("indent")
        v1 = m.group("v1")
        v2 = m.group("v2")
        return (
            f"{ind}@Override\n"
            f"{ind}public int compare({t1} {v1}, {t1} {v2}) {{\n"
            f"{ind}   return this.{m.group('delegate')}({v1}, {v2});\n"
            f"{ind}}}"
        )
    text2 = bridge_rx.sub(_bridge_repl, text)
    if text2 != text:
        p.write_text(text2, encoding="utf-8")

# Fail fast on the exact known invalid forms.
residuals=[]
checks=[
    re.compile(r"(?m)^\s*PBMessageALL\d*\.[A-Za-z_$][\w$]*\.m;\s*$"),
    re.compile(r"\b(?:int|long|short|byte|boolean|char|float|double|void)\s+do\b"),
    re.compile(r"\.do\s*\("),
    re.compile(r"\b(?:L1SkillId|Opcodes)\.do\b"),
]
for p in SRC.rglob("*.java"):
    text=p.read_text(encoding="utf-8",errors="replace")
    for rx in checks:
        for m in rx.finditer(text):
            residuals.append({
                "file":p.as_posix(),
                "line":text.count("\n",0,m.start())+1,
                "match":m.group(0),
            })

state={
    "protobuf_repairs":len(protobuf_repairs),
    "protobuf_evidence":"44 donor methods: invokestatic ()Z -> pop -> return",
    "keyword_member_repairs":4,
    "l1pc_method_call_sites_rewritten":method_calls,
    "constant_refs_rewritten":constant_refs,
    "protobuf_g_shadow_repairs":protobuf_g_shadow_repairs,
    "protobuf_g_shadow_files":protobuf_g_shadow_files,
    "comparator_bridge_repairs":len(comparator_bridge_repairs),
    "comparator_bridge_files":sorted({x["file"] for x in comparator_bridge_repairs}),
    "residual_invalid_forms":residuals,
}
OUT_JSON.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
md=[
    "# Normalized Source Repairs",
    "",
    f"- Protobuf bytecode-backed repairs: **{len(protobuf_repairs)}**",
    "- Java-keyword member identities repaired: **4**",
    f"- L1PcInstance method call sites rewritten: **{method_calls}**",
    f"- L1SkillId/Opcodes constant references rewritten: **{constant_refs}**",
    f"- Embedded protobuf a.g package-shadow calls repaired: **{protobuf_g_shadow_repairs}**",
    f"- Package-shadow files: **{len(protobuf_g_shadow_files)}**",
    f"- Comparator bridge source repairs: **{len(comparator_bridge_repairs)}**",
    f"- Comparator bridge files: **{len({x['file'] for x in comparator_bridge_repairs})}**",
    f"- Residual known-invalid forms: **{len(residuals)}**",
    "",
    "Protobuf repair authority: donor javap. Each repaired builder initializer is invokestatic ()Z, pop, return.",
    "No gameplay behavior is inferred from decompiler output.",
]
OUT_MD.write_text("\n".join(md)+"\n",encoding="utf-8")
if residuals:
    raise SystemExit("known invalid forms remain")
print(json.dumps(state,indent=2))
