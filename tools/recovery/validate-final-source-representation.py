#!/usr/bin/env python3
import csv, json, sys, zipfile
from pathlib import Path

ROOT=Path(".")
REC=ROOT/"recovery"
MAP=ROOT/"class_source_mapping.csv"
APP=REC/"class_inventory.csv"
IDENTITY=REC/"remaining_932_source_identity_audit.json"
WP5=REC/"source_only_application_compile.json"

if len(sys.argv) != 2:
    raise SystemExit("usage: validate-final-source-representation.py <protobuf-2.5.0-sources.jar>")
PROTO_SRC=Path(sys.argv[1])

for p in [MAP,APP,IDENTITY,WP5,PROTO_SRC]:
    if not p.exists():
        raise SystemExit(f"missing required input: {p}")

with MAP.open(encoding="utf-8-sig",newline="") as f:
    mappings=list(csv.DictReader(f))
with APP.open(encoding="utf-8-sig",newline="") as f:
    app_rows=list(csv.DictReader(f))
identity=json.loads(IDENTITY.read_text(encoding="utf-8"))
wp5=json.loads(WP5.read_text(encoding="utf-8"))

auth={r["Class"]:r["SourceFile"] for r in mappings if r.get("Class")}
app_top=[r for r in app_rows if r.get("Kind")=="TOP_LEVEL"]
app_sources={r["SourceFile"] for r in app_top}
proto_rows=[r for r in mappings if r.get("Class","").startswith("a.")]
proto_sourcefiles={r["SourceFile"] for r in proto_rows}
remaining=len(auth)-len(app_top)-len(proto_rows)

app_java_files=list((ROOT/"recovered-src-obf").rglob("*.java"))

with zipfile.ZipFile(PROTO_SRC) as z:
    names=set(z.namelist())
    official_proto_java={Path(n).name for n in names if n.startswith("com/google/protobuf/") and n.endswith(".java") and "/" not in n[len("com/google/protobuf/"):-5]}

missing_proto=sorted(proto_sourcefiles-official_proto_java)
extra_proto=sorted(official_proto_java-proto_sourcefiles)

cats=identity.get("categories",{})
identity_ok=(
    identity.get("total_source_mappings")==1765 and
    identity.get("application_source_mappings")==788 and
    identity.get("protobuf_source_mappings")==45 and
    identity.get("remaining_source_mappings")==932 and
    identity.get("classified")==932 and
    identity.get("unknown")==0 and
    identity.get("ambiguous")==0 and
    identity.get("duplicate_class_assignments")==0 and
    sum(v for v in cats.values() if isinstance(v,int))==932
)

state={
  "gate":"FINAL_SOURCE_REPRESENTATION",
  "authoritative_source_mappings":len(auth),
  "application":{
    "top_level_source_mappings":len(app_top),
    "java_files_present":len(app_java_files),
    "representation":"recovered-src-obf/**/*.java",
  },
  "protobuf":{
    "source_mappings":len(proto_rows),
    "required_source_files":len(proto_sourcefiles),
    "official_source_files_found":len(official_proto_java),
    "missing_required_source_files":missing_proto,
    "extra_official_top_level_source_files":extra_proto,
    "representation":"protobuf-java-2.5.0 official source archive; source-built exact donor ABI proven by WP5",
  },
  "third_party":{
    "source_mappings":remaining,
    "identity_evidence_valid":identity_ok,
    "representation":"external dependencies + deterministic identity manifest; not vendored into application source tree",
    "categories":cats,
  },
  "wp5_source_only_compile_pass":bool(wp5.get("pass")),
  "donor_binary_required_for_source_representation":False,
}
state["pass"]=(
    len(auth)==1765 and
    len(app_top)==788 and
    len(app_java_files)==788 and
    len(proto_rows)==45 and
    len(proto_sourcefiles)==45 and
    not missing_proto and
    remaining==932 and
    identity_ok and
    wp5.get("pass") is True
)
print(json.dumps(state,indent=2))
(REC/"final_source_representation.json").write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
if not state["pass"]:
    raise SystemExit(1)
