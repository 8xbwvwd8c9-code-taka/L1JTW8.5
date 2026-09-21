#!/usr/bin/env python3
import json,sys
from pathlib import Path

SRC=Path("recovery/protobuf_2_5_0_member_mapping_v2.json")
OUT=Path("recovery/protobuf_2_5_0_member_mapping_v3.json")
state=json.loads(SRC.read_text(encoding="utf-8"))
rows=state["rows"]
unresolved=state["unresolved_rows"]

index_pairs=len(rows)
index_agree=sum(1 for r in rows if r["donor_index"]==r["official_index"])
index_disagree=[r for r in rows if r["donor_index"]!=r["official_index"]]

resolved=[]
still=[]
if index_pairs>0 and not index_disagree:
    for r in unresolved:
        same=[c for c in r["candidates"] if c["index"]==r["donor_index"]]
        if len(same)==1:
            c=same[0]
            resolved.append({
              "kind":r["kind"],
              "donor_class":r["donor_class"],"donor_name":r["donor_name"],"donor_desc":r["donor_desc"],"donor_index":r["donor_index"],
              "official_class":r["official_class"],"official_name":c["name"],"official_desc":c["desc"],"official_index":c["index"],
              "match":"validated_member_table_index"
            })
        else:
            still.append(r)
else:
    still=list(unresolved)

allrows=rows+resolved
out={
 "gate":"PROTOBUF_2_5_0_MEMBER_MAPPING_V3",
 "member_total":state["member_total"],
 "pre_mapped":len(rows),
 "index_validation_pairs":index_pairs,
 "index_validation_agree":index_agree,
 "index_validation_disagree":len(index_disagree),
 "index_safe":index_pairs>0 and not index_disagree,
 "index_resolved":len(resolved),
 "mapped":len(allrows),
 "unresolved":len(still),
 "coverage":len(allrows)/state["member_total"] if state["member_total"] else 0,
 "rows":allrows,
 "unresolved_rows":still,
 "index_disagree_rows":index_disagree
}
OUT.write_text(json.dumps(out,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:v for k,v in out.items() if k not in ("rows","unresolved_rows","index_disagree_rows")},indent=2))
print("UNRESOLVED_SAMPLE="+json.dumps(still[:80],indent=2))
print("INDEX_DISAGREE_SAMPLE="+json.dumps(index_disagree[:40],indent=2))
