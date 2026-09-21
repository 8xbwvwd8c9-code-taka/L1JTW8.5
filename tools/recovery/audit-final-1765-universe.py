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

def classset_from_jar(path):
    with zipfile.ZipFile(path) as z:
        return {n for n in z.namelist() if n.endswith(".class") and not n.startswith("META-INF/")}

wp5=json.loads(WP5.read_text(encoding="utf-8"))
if not wp5.get("pass") or wp5.get("application_classes")!=1109 or wp5.get("exact_source_built_protobuf_classes")!=246 or wp5.get("compile_view_runtime_dependency_count")!=0:
    raise SystemExit("WP5 exact-runtime linkage is not closed")

with MAP.open(encoding="utf-8-sig",newline="") as f:
    rows=list(csv.DictReader(f))
if not rows or "Class" not in rows[0]:
    raise SystemExit("class_source_mapping.csv missing Class column")

# Authoritative mapping identities are dotted binary names. Normalize to class paths.
authoritative={r["Class"].replace(".","/")+".class" for r in rows if r.get("Class")}
if len(authoritative)!=len(rows):
    raise SystemExit(f"class_source_mapping not unique: rows={len(rows)} unique={len(authoritative)}")

with APP.open(encoding="utf-8-sig",newline="") as f:
    app_rows=list(csv.DictReader(f))
application={r["ClassPath"] for r in app_rows if r.get("ClassPath")}
protobuf=classset_from_jar(PROTO)

app_in=application & authoritative
proto_in=protobuf & authoritative
app_out=application-authoritative
proto_out=protobuf-authoritative
overlap=application & protobuf
remaining=authoritative-application-protobuf

lib_sets={k:set().union(*(classset_from_jar(p) for p in paths)) for k,paths in LIB_GROUPS.items()}
category_hits={k:remaining & s for k,s in lib_sets.items()}
category_union=set().union(*category_hits.values())
category_overlap={}
ks=list(category_hits)
for i,a in enumerate(ks):
    for b in ks[i+1:]:
        x=category_hits[a]&category_hits[b]
        if x: category_overlap[f"{a}&{b}"]=sorted(x)

unclassified=remaining-category_union
classified_extra=category_union-remaining

expected_categories={
  "c3p0_mchange_commons":241,
  "mysql_connector_java":112,
  "lombok_runtime_annotations":57,
}

state={
  "gate":"FINAL_1765_UNIVERSE_ACCOUNTING",
  "authoritative_source":"class_source_mapping.csv",
  "wp5_exact_runtime_linkage_pass":True,
  "total_target":len(authoritative),
  "application":{
    "source":"recovery/class_inventory.csv",
    "raw_count":len(application),
    "in_authoritative_count":len(app_in),
    "outside_authoritative_count":len(app_out),
    "outside_authoritative_sample":sorted(app_out)[:100],
  },
  "protobuf":{
    "source":"recovery/compile-ref-protobuf-obf.jar",
    "raw_count":len(protobuf),
    "in_authoritative_count":len(proto_in),
    "outside_authoritative_count":len(proto_out),
    "outside_authoritative_sample":sorted(proto_out)[:100],
  },
  "application_protobuf_overlap":len(overlap),
  "remaining":{
    "count":len(remaining),
    "categories":{k:len(v) for k,v in category_hits.items()},
    "expected_categories":expected_categories,
    "unclassified_count":len(unclassified),
    "unclassified":sorted(unclassified),
    "category_overlap_count":sum(len(v) for v in category_overlap.values()),
    "category_overlaps":category_overlap,
    "exact_class_list":sorted(remaining),
  },
  "set_accounting":{
    "union":len(application|protobuf|remaining),
    "authoritative":len(authoritative),
    "missing":len(authoritative-(application|protobuf|remaining)),
    "extra":len((application|protobuf|remaining)-authoritative),
  },
}
state["pass"]=(
  len(authoritative)==1765 and
  len(application)==1109 and len(app_out)==0 and
  len(protobuf)==246 and len(proto_out)==0 and
  len(overlap)==0 and
  len(remaining)==410 and
  all(len(category_hits[k])==v for k,v in expected_categories.items()) and
  not unclassified and not category_overlap and
  state["set_accounting"]["union"]==1765 and
  state["set_accounting"]["missing"]==0 and
  state["set_accounting"]["extra"]==0
)
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
MD.write_text(
  "# Final 1765 Universe Audit\n\n"
  f"- PASS: **{state['pass']}**\n"
  f"- Authoritative: **{len(authoritative)}**\n"
  f"- Application: **{len(application)}**\n"
  f"- Protobuf: **{len(protobuf)}**\n"
  f"- Remaining: **{len(remaining)}**\n"
  + "".join(f"- {k}: **{len(v)}**\n" for k,v in category_hits.items())
  + f"- Unclassified: **{len(unclassified)}**\n"
  f"- App/Proto overlap: **{len(overlap)}**\n"
  f"- Missing / extra: **{state['set_accounting']['missing']} / {state['set_accounting']['extra']}**\n",
  encoding="utf-8"
)
print(json.dumps(state,indent=2))
if not state["pass"]:
    raise SystemExit(1)
