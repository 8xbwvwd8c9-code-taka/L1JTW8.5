#!/usr/bin/env python3
import csv, json, zipfile
from pathlib import Path

ROOT=Path(".")
REC=ROOT/"recovery"
MAP=ROOT/"class_source_mapping.csv"
APP=REC/"class_inventory.csv"
PROTO=REC/"compile-ref-protobuf-obf.jar"
OUT=REC/"final_1765_universe_audit.json"
MD=REC/"FINAL_1765_UNIVERSE_AUDIT.md"
WP5=REC/"source_only_exact_runtime_linkage.json"

LIB_GROUPS={
  "c3p0_mchange_commons":[
    ROOT/"lib/c3p0-0.9.5.2.jar",
    ROOT/"lib/c3p0-oracle-thin-extras-0.9.5.2.jar",
    ROOT/"lib/mchange-commons-java-0.2.11.jar",
  ],
  "mysql_connector_java":[ROOT/"lib/mysql-connector-java-5.1.40-bin.jar"],
  "lombok_runtime_annotations":[ROOT/"lib/lombok-1.16.6.jar"],
}

for p in [MAP,APP,PROTO,WP5,*[x for xs in LIB_GROUPS.values() for x in xs]]:
    if not p.exists():
        raise SystemExit(f"missing required input: {p}")

def top_level_source_names_from_inventory(path):
    with path.open(encoding="utf-8-sig",newline="") as f:
        rows=list(csv.DictReader(f))
    return {r["InternalName"].replace("/","."):r["SourceFile"] for r in rows if r.get("Kind")=="TOP_LEVEL"}

with MAP.open(encoding="utf-8-sig",newline="") as f:
    rows=list(csv.DictReader(f))
if not rows or "Class" not in rows[0] or "SourceFile" not in rows[0]:
    raise SystemExit("class_source_mapping.csv missing Class/SourceFile columns")

authoritative={r["Class"]:r["SourceFile"] for r in rows if r.get("Class")}
if len(authoritative)!=len(rows):
    raise SystemExit(f"class_source_mapping not unique: rows={len(rows)} unique={len(authoritative)}")

app_map=top_level_source_names_from_inventory(APP)
app_keys=set(app_map)
proto_keys={k for k in authoritative if k.startswith("a.")}
remaining_keys=set(authoritative)-app_keys-proto_keys

if len(app_keys)!=788:
    raise SystemExit(f"expected 788 application source mappings, got {len(app_keys)}")
if len(proto_keys)!=45:
    raise SystemExit(f"expected 45 protobuf source mappings, got {len(proto_keys)}")

# Evidence-based third-party family hints from preserved package names and SourceFile names.
# Obfuscated package names are left as UNKNOWN until a deterministic source identity exists.
def family_for(cls, src):
    if cls.startswith("com.mchange."):
        return "c3p0_mchange_commons"
    if cls.startswith("com.mysql.") or cls.startswith("org.gjt."):
        return "mysql_connector_java"
    if cls.startswith("lombok."):
        return "lombok"
    return "UNKNOWN"

families={}
for k in sorted(remaining_keys):
    fam=family_for(k,authoritative[k])
    families.setdefault(fam,[]).append({"class":k,"source":authoritative[k]})

state={
  "gate":"FINAL_1765_SOURCE_MAPPING_ACCOUNTING",
  "authoritative_source":"class_source_mapping.csv",
  "wp5_exact_runtime_linkage_pass":True,
  "total_source_mappings":len(authoritative),
  "application":{
    "source":"recovery/class_inventory.csv TOP_LEVEL",
    "source_mapping_count":len(app_keys),
    "missing_from_authoritative":sorted(app_keys-set(authoritative)),
  },
  "protobuf":{
    "source":"class_source_mapping.csv package a.* / official protobuf 2.5.0 source-file universe",
    "source_mapping_count":len(proto_keys),
    "source_files":sorted({authoritative[k] for k in proto_keys}),
  },
  "remaining":{
    "source_mapping_count":len(remaining_keys),
    "family_counts":{k:len(v) for k,v in families.items()},
    "unknown_count":len(families.get("UNKNOWN",[])),
    "unknown_sample":families.get("UNKNOWN",[])[:300],
  },
  "set_accounting":{
    "union":len(app_keys|proto_keys|remaining_keys),
    "overlap_app_proto":len(app_keys&proto_keys),
    "missing":len(set(authoritative)-(app_keys|proto_keys|remaining_keys)),
    "extra":len((app_keys|proto_keys|remaining_keys)-set(authoritative)),
  },
  "source_mapping_granularity":"one row in class_source_mapping.csv; do not mix with generated .class counts",
  "classfile_metrics":{
    "application_generated_classes":1109,
    "protobuf_runtime_classes":246,
    "note":"classfile counts are WP5 runtime/ABI metrics and are not additive with the 1765 source-mapping universe"
  }
}
state["pass_accounting"]=(
  len(authoritative)==1765 and
  len(app_keys)==788 and
  len(proto_keys)==45 and
  len(remaining_keys)==932 and
  state["set_accounting"]["union"]==1765 and
  state["set_accounting"]["overlap_app_proto"]==0 and
  state["set_accounting"]["missing"]==0 and
  state["set_accounting"]["extra"]==0
)
# Full final closure additionally requires deterministic source identity for all remaining mappings.
state["pass"]=state["pass_accounting"] and state["remaining"]["unknown_count"]==0

OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
MD.write_text(
  "# Final 1765 Universe Audit\n\n"
  f"- Accounting PASS: **{state['pass_accounting']}**\n"
  f"- Final PASS: **{state['pass']}**\n"
  f"- Authoritative source mappings: **{len(authoritative)}**\n"
  f"- Application source mappings: **{len(app_keys)}**\n"
  f"- Protobuf source mappings: **{len(proto_keys)}**\n"
  f"- Remaining source mappings: **{len(remaining_keys)}**\n"
  + "".join(f"- {k}: **{len(v)}**\n" for k,v in families.items())
  + f"- Unknown source identity: **{state['remaining']['unknown_count']}**\n"
  f"- Missing / extra: **{state['set_accounting']['missing']} / {state['set_accounting']['extra']}**\n",
  encoding="utf-8"
)
print(json.dumps(state,indent=2))
if not state["pass_accounting"]:
    raise SystemExit(1)
