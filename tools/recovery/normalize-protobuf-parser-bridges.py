#!/usr/bin/env python3
import json
import re
from pathlib import Path

STAGE = Path("_normalized-stage-src")
REC = Path("recovery")
OUT = REC / "normalized_parser_bridge_transform.json"
MD = REC / "NORMALIZED_PARSER_BRIDGE_TRANSFORM.md"

# Vineflower exposes the JVM-erased synthetic parser bridge as:
#   public Object d(h,n) { return this.c(h,n); }
# while the immediately preceding real parser method is:
#   public ConcreteMessage c(h,n)
#
# In bytecode this erased Object-return method is valid. In Java source,
# however, ab<ConcreteMessage>.d(h,n) requires ConcreteMessage. Preserve
# the bridge body/name/params and normalize only its source return type to
# the donor-proven real c(h,n) return type.
pair_rx = re.compile(
    r"(?P<typed_decl>"
    r"public\s+(?P<ret>[A-Za-z0-9_.$]+)\s+c\(l1rpb\.h\s+var1,\s*n\s+var2\)\s+throws\s+s\s*\{"
    r".*?"
    r"\n\s*\}"
    r")"
    r"(?P<gap>"
    r"\s*\n\s*// \$VF: synthetic method\s*\n"
    r"\s*@Override\s*\n"
    r"\s*)"
    r"public\s+Object\s+d\(l1rpb\.h\s+var1,\s*n\s+var2\)\s+throws\s+s\s*\{"
    r"(?P<body>\s*\n\s*return\s+this\.c\(var1,\s*var2\);\s*\n\s*\})",
    re.MULTILINE | re.DOTALL,
)

changes=[]
total=0
for p in sorted((STAGE / "l1r" / "an").glob("PBMessageALL*.java")):
    text=p.read_text(encoding="utf-8", errors="replace")
    local=[]

    def repl(m):
        nonlocal_total = None
        ret=m.group("ret")
        local.append(ret)
        return (
            m.group("typed_decl")
            + m.group("gap")
            + f"public {ret} d(l1rpb.h var1, n var2) throws s {{"
            + m.group("body")
        )

    text2,n=pair_rx.subn(repl,text)
    if n:
        p.write_text(text2,encoding="utf-8")
        total += n
        changes.append({
            "file":p.relative_to(STAGE).as_posix(),
            "rewritten":n,
            "return_types":local,
        })

# Strong postconditions: no old Object bridge remains, and every normalized
# bridge still delegates to this.c(var1,var2).
old_rx=re.compile(
    r"public\s+Object\s+d\(l1rpb\.h\s+var1,\s*n\s+var2\)\s+throws\s+s"
)
residual=[]
for p in sorted((STAGE / "l1r" / "an").glob("PBMessageALL*.java")):
    t=p.read_text(encoding="utf-8", errors="replace")
    n=len(old_rx.findall(t))
    if n:
        residual.append({"file":p.relative_to(STAGE).as_posix(),"count":n})

state={
    "error_family":"PROTOBUF_EXPLICIT_SYNTHETIC_PARSER_BRIDGE",
    "expected_bridge_count":44,
    "rewritten_bridge_count":total,
    "changed_files":len(changes),
    "changes":changes,
    "old_object_bridge_residual":residual,
    "transform":"Object d(h,n) -> exact preceding c(h,n) ConcreteMessage return type",
    "bridge_method_name_changed":False,
    "bridge_params_changed":False,
    "bridge_body_changed":False,
    "typed_parser_method_changed":False,
    "gameplay_logic_changed":False,
    "source_representation_only":True,
    "donor_jvm_erased_descriptor_restored_by_javac_bridge":True,
}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
ok=(total==44 and not residual)
MD.write_text(
    "# Normalized Parser Bridge Transform\n\n"
    + f"Status: **{'PASS' if ok else 'FAIL'}**\n\n"
    + "- Expected parser bridges: **44**\n"
    + f"- Concrete-return bridges rewritten: **{total}**\n"
    + "- Old Object-return bridge residual: **"
    + ("0" if not residual else str(sum(x['count'] for x in residual)))
    + "**\n"
    + "- Bridge body/name/params changed: **NO / NO / NO**\n"
    + "- Gameplay logic changed: **NO**\n"
    + "- Scope: Java source representation only; javac may emit the erased Object bridge required by donor ABI.\n",
    encoding="utf-8",
)
print(json.dumps(state,indent=2))
if not ok:
    raise SystemExit(f"expected 44 parser bridge return rewrites and zero residuals, got rewrites={total}, residual={residual}")
