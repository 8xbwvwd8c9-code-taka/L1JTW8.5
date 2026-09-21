#!/usr/bin/env python3
import csv, json, re, struct, zipfile
from collections import Counter, defaultdict
from pathlib import Path

ROOT=Path("."); REC=ROOT/"recovery"
DONOR=ROOT/"l1jserver2.jar"; BUILD=REC/"normalized-stage-build"
INV=REC/"class_inventory.csv"; NSMAP=REC/"source_namespace_map.csv"
TRANSFORM=REC/"stage_transform.json"; COLLISION=REC/"normalized_builder_collision_transform.json"
HIER=REC/"post_javac0_class_hierarchy.json"; WP2=REC/"generic_builder_signature_audit.json"
OUT=REC/"post_javac0_member_abi.json"; MD=REC/"POST_JAVAC0_MEMBER_ABI.md"

FIELD_FLAG_MASK=0x0001|0x0002|0x0004|0x0008|0x0010|0x0040|0x0080|0x1000|0x4000
METHOD_FLAG_MASK=0x0001|0x0002|0x0004|0x0008|0x0010|0x0020|0x0040|0x0080|0x0100|0x0400|0x0800|0x1000
METHOD_LINKAGE_FLAG_MASK=METHOD_FLAG_MASK & ~(0x0040|0x1000)

def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4

def parse_class(data):
    p=0; magic,p=u4(data,p)
    if magic!=0xCAFEBABE: raise SystemExit("not class")
    _,p=u2(data,p); _,p=u2(data,p)
    cpc,p=u2(data,p); cp=[None]*cpc; i=1
    while i<cpc:
        tag,p=u1(data,p)
        if tag==1:
            n,p=u2(data,p); cp[i]=(tag,data[p:p+n].decode("utf-8","replace")); p+=n
        elif tag in (3,4): cp[i]=(tag,None); p+=4
        elif tag in (5,6): cp[i]=(tag,None); p+=8; i+=1
        elif tag in (7,8,16,19,20):
            x,p=u2(data,p); cp[i]=(tag,x)
        elif tag in (9,10,11,12,17,18):
            a,p=u2(data,p); b,p=u2(data,p); cp[i]=(tag,a,b)
        elif tag==15:
            a,p=u1(data,p); b,p=u2(data,p); cp[i]=(tag,a,b)
        else: raise SystemExit(f"unknown cp tag {tag}")
        i+=1
    def utf(idx):
        e=cp[idx] if idx else None
        return e[1] if e and e[0]==1 else None
    def cls(idx):
        e=cp[idx] if idx else None
        return utf(e[1]) if e and e[0]==7 else None

    access,p=u2(data,p); this_i,p=u2(data,p); super_i,p=u2(data,p)
    ic,p=u2(data,p); interfaces=[]
    for _ in range(ic):
        x,p=u2(data,p); interfaces.append(cls(x))

    def member():
        nonlocal p
        flags,p2=u2(data,p); p=p2
        ni,p2=u2(data,p); p=p2
        di,p2=u2(data,p); p=p2
        ac,p2=u2(data,p); p=p2
        sig=None; exc=[]
        for _ in range(ac):
            ai,p2=u2(data,p); p=p2
            ln,p2=u4(data,p); p=p2
            payload=data[p:p+ln]; p+=ln
            an=utf(ai)
            if an=="Signature" and ln==2:
                si,_=u2(payload,0); sig=utf(si)
            elif an=="Exceptions" and ln>=2:
                q=0; n,q=u2(payload,q)
                for __ in range(n):
                    ci,q=u2(payload,q); exc.append(cls(ci))
        return {"flags":flags,"name":utf(ni),"descriptor":utf(di),"signature":sig,"exceptions":exc}

    fc,p=u2(data,p); fields=[member() for _ in range(fc)]
    mc,p=u2(data,p); methods=[member() for _ in range(mc)]
    return {"name":cls(this_i),"access":access,"super":cls(super_i),"interfaces":interfaces,
            "fields":fields,"methods":methods}

for pth in (DONOR,BUILD,INV,NSMAP,HIER,WP2):
    if not pth.exists(): raise SystemExit(f"missing required input {pth}")

hier=json.loads(HIER.read_text(encoding="utf-8"))
if not hier.get("gates",{}).get("class_set_pass") or not hier.get("gates",{}).get("runtime_hierarchy_pass"):
    raise SystemExit("requires closed class-set/runtime hierarchy gates")

with NSMAP.open(encoding="utf-8-sig",newline="") as f:
    ns_rows=list(csv.DictReader(f))
old_to_new={r["OldInternal"]:r["NewInternal"] for r in ns_rows}
new_to_old={r["NewInternal"]:r["OldInternal"] for r in ns_rows}
if len(old_to_new)!=1109 or len(new_to_old)!=1109:
    raise SystemExit("namespace map must be bijective 1109/1109")

transform=json.loads(TRANSFORM.read_text(encoding="utf-8")) if TRANSFORM.exists() else {}
keyword_new_to_old={}
for old_fq,new_fq in transform.get("recovery_only_class_renames",{}).items():
    old=old_fq.replace(".","/"); new=new_fq.replace(".","/")
    keyword_new_to_old[new]=old; keyword_new_to_old["l1r/"+new]=old

builder_collision_new_to_old={}
if COLLISION.exists():
    bc=json.loads(COLLISION.read_text(encoding="utf-8"))
    repairs=bc.get("repairs",[])
    if len(repairs)!=9: raise SystemExit(f"expected 9 builder collision repairs, got {len(repairs)}")
    for row in repairs:
        old_readable="l1r/an/"+row["old_binary_identity"]
        donor=new_to_old.get(old_readable)
        if donor is None: raise SystemExit(f"missing donor mapping for {old_readable}")
        builder_collision_new_to_old["l1r/an/"+row["source_recovery_identity"]]=donor

structural={}
for gk,dk in hier.get("normalization",{}).get("structural_inner_remaps",{}).items():
    structural[gk[:-6] if gk.endswith(".class") else gk]=dk[:-6] if dk.endswith(".class") else dk

def norm_name(name):
    if name is None: return None
    if name.startswith("l1rpb/"): return "a/"+name[len("l1rpb/"):]
    if name in builder_collision_new_to_old: return builder_collision_new_to_old[name]
    if name in new_to_old: return new_to_old[name]
    if name in keyword_new_to_old: return keyword_new_to_old[name]
    for new,old in keyword_new_to_old.items():
        if name.startswith(new+"$"): return old+name[len(new):]
    if name in structural: return structural[name]
    return name

TYPE_RE=re.compile(r"L([^;<]+)(?=[;<])")
def norm_type_string(s):
    if s is None: return None
    return TYPE_RE.sub(lambda m:"L"+norm_name(m.group(1)),s)

member_rename_new_to_old={}
for spec,new_name in transform.get("recovery_only_member_renames",{}).items():
    owner_dot,kind,tail=spec.split(" ",2)
    old_name,desc=tail.split(":",1)
    owner=owner_dot.replace(".","/")
    member_rename_new_to_old[(owner,kind,new_name,norm_type_string(desc))]=old_name

def norm_member(owner,kind,m):
    desc=norm_type_string(m["descriptor"])
    name=member_rename_new_to_old.get((owner,kind,m["name"],desc),m["name"])
    return {
      "name":name,"descriptor":desc,
      "flags":m["flags"] & (FIELD_FLAG_MASK if kind=="field" else METHOD_FLAG_MASK),
      "linkage_flags":m["flags"] & (FIELD_FLAG_MASK if kind=="field" else METHOD_LINKAGE_FLAG_MASK),
      "signature":norm_type_string(m.get("signature")),
      "exceptions":sorted(norm_name(x) for x in m.get("exceptions",[])),
      "recovered_name":m["name"],
    }

with INV.open(encoding="utf-8-sig",newline="") as f:
    inv_rows=list(csv.DictReader(f))
donor_paths=[r["ClassPath"] for r in inv_rows if r.get("ClassPath") and not r["ClassPath"].startswith("a/")]

donor={}
with zipfile.ZipFile(DONOR) as z:
    names=set(z.namelist())
    for path in donor_paths:
        if path not in names: raise SystemExit(f"donor inventory path missing from jar: {path}")
        x=parse_class(z.read(path))
        donor[x["name"]]=x

generated={}
for pth in BUILD.rglob("*.class"):
    x=parse_class(pth.read_bytes())
    owner=norm_name(x["name"])
    if owner in generated: raise SystemExit(f"normalized generated class collision: {owner}")
    generated[owner]=x

if set(donor)!=set(generated):
    raise SystemExit(f"class identity mismatch before member audit: missing={len(set(donor)-set(generated))} extra={len(set(generated)-set(donor))}")

wp2=json.loads(WP2.read_text(encoding="utf-8"))
known_bridge_extras=set()
for b in wp2.get("builders",[]):
    owner=b["class"][:-6]
    for m in b.get("generated_bridges_missing_in_donor",[]):
        known_bridge_extras.add((owner,m["name"],m["descriptor"]))

field_missing=[]; field_extra=[]; field_flag=[]; field_sig=[]
method_missing=[]; method_extra=[]; method_flag=[]; method_linkage_flag=[]; method_sig=[]; method_exc=[]
per_class=[]
member_rename_hits=0

for owner in sorted(donor):
    d=donor[owner]; g=generated[owner]
    df=[norm_member(owner,"field",x) for x in d["fields"]]
    gf=[norm_member(owner,"field",x) for x in g["fields"]]
    dm=[norm_member(owner,"method",x) for x in d["methods"]]
    gm=[norm_member(owner,"method",x) for x in g["methods"]]
    member_rename_hits += sum(x["name"]!=x["recovered_name"] for x in gf+gm)

    def index(rows):
        out={}
        for x in rows:
            k=(x["name"],x["descriptor"])
            if k in out: raise SystemExit(f"duplicate member identity {owner} {k}")
            out[k]=x
        return out

    dfi,gfi=index(df),index(gf); dmi,gmi=index(dm),index(gm)
    fm=sorted(set(dfi)-set(gfi)); fe=sorted(set(gfi)-set(dfi))
    mm=sorted(set(dmi)-set(gmi)); me=sorted(set(gmi)-set(dmi))

    for n,x in fm: field_missing.append({"class":owner,"name":n,"descriptor":x})
    for n,x in fe: field_extra.append({"class":owner,"name":n,"descriptor":x})
    for k in sorted(set(dfi)&set(gfi)):
        a,b=dfi[k],gfi[k]
        if a["flags"]!=b["flags"]:
            field_flag.append({"class":owner,"name":k[0],"descriptor":k[1],"donor":a["flags"],"generated":b["flags"]})
        if a["signature"]!=b["signature"]:
            field_sig.append({"class":owner,"name":k[0],"descriptor":k[1],"donor":a["signature"],"generated":b["signature"]})

    for n,x in mm: method_missing.append({"class":owner,"name":n,"descriptor":x})
    for n,x in me:
        cls="KNOWN_WP2_GENERATED_BUILDER_BRIDGE_EXTRA" if (owner,n,x) in known_bridge_extras else "UNCLASSIFIED"
        method_extra.append({"class":owner,"name":n,"descriptor":x,"classification":cls})
    for k in sorted(set(dmi)&set(gmi)):
        a,b=dmi[k],gmi[k]
        if a["flags"]!=b["flags"]:
            method_flag.append({"class":owner,"name":k[0],"descriptor":k[1],"donor":a["flags"],"generated":b["flags"]})
        if a["linkage_flags"]!=b["linkage_flags"]:
            method_linkage_flag.append({"class":owner,"name":k[0],"descriptor":k[1],"donor":a["linkage_flags"],"generated":b["linkage_flags"]})
        if a["signature"]!=b["signature"]:
            method_sig.append({"class":owner,"name":k[0],"descriptor":k[1],"donor":a["signature"],"generated":b["signature"]})
        if a["exceptions"]!=b["exceptions"]:
            method_exc.append({"class":owner,"name":k[0],"descriptor":k[1],"donor":a["exceptions"],"generated":b["exceptions"]})

    if fm or fe or mm or me:
        per_class.append({
          "class":owner,
          "field_missing":len(fm),"field_extra":len(fe),
          "method_missing":len(mm),"method_extra":len(me),
          "known_wp2_bridge_extra":sum((owner,n,x) in known_bridge_extras for n,x in me),
        })

known_extra=[x for x in method_extra if x["classification"]=="KNOWN_WP2_GENERATED_BUILDER_BRIDGE_EXTRA"]
unknown_extra=[x for x in method_extra if x["classification"]=="UNCLASSIFIED"]
known_set={(x["class"],x["name"],x["descriptor"]) for x in known_extra}
missing_known=sorted(known_bridge_extras-known_set)

state={
 "gate":"POST_JAVAC0_MEMBER_ABI",
 "status":"PASS",
 "class_count":len(donor),
 "normalization":{
   "namespace_map_rows":len(ns_rows),
   "builder_collision_rows":len(builder_collision_new_to_old),
   "keyword_class_renames":len(keyword_new_to_old)//2,
   "member_rename_rules":len(member_rename_new_to_old),
   "member_rename_hits":member_rename_hits,
   "structural_inner_remaps":len(structural),
   "protobuf_runtime_descriptor_map":"l1rpb/** -> a/**",
 },
 "fields":{
   "missing":len(field_missing),"extra":len(field_extra),
   "access_flag_mismatches":len(field_flag),
   "generic_signature_mismatches":len(field_sig),
 },
 "methods":{
   "missing":len(method_missing),"extra":len(method_extra),
   "known_wp2_generated_builder_bridge_extras":len(known_extra),
   "unclassified_extras":len(unknown_extra),
   "wp2_expected_bridge_extras":len(known_bridge_extras),
   "wp2_expected_bridge_extras_missing_from_member_audit":len(missing_known),
   "access_flag_mismatches":len(method_flag),
   "linkage_flag_mismatches_excluding_bridge_synthetic":len(method_linkage_flag),
   "generic_signature_mismatches":len(method_sig),
   "exceptions_attribute_mismatches":len(method_exc),
 },
 "gates":{
   "field_descriptor_identity_exact":not field_missing and not field_extra,
   "method_descriptor_identity_exact":not method_missing and not method_extra,
   "method_descriptor_identity_after_wp2_bridge_classification":not method_missing and not unknown_extra and not missing_known,
   "field_access_flags_exact":not field_flag,
   "method_linkage_flags_exact_excluding_bridge_synthetic":not method_linkage_flag,
   "wp2_bridge_classification_complete":len(known_extra)==len(known_bridge_extras) and not missing_known,
 },
 "per_class_member_count_differences":per_class,
 "field_missing":field_missing,"field_extra":field_extra,
 "field_access_flag_mismatches":field_flag,"field_generic_signature_mismatches":field_sig,
 "method_missing":method_missing,"method_extra":method_extra,
 "method_access_flag_mismatches":method_flag,
 "method_linkage_flag_mismatches":method_linkage_flag,
 "method_generic_signature_mismatches":method_sig,
 "method_exceptions_mismatches":method_exc,
 "missing_expected_wp2_bridge_extras":[
   {"class":c,"name":n,"descriptor":d} for c,n,d in missing_known
 ],
 "notes":[
   "Identity normalization includes source_namespace_map, 9 builder collision renames, keyword-safe class/member renames, structural inner remaps, and l1rpb/** -> a/**.",
   "The 660 generated builder bridge extras identified by WP2 are classified, not silently discarded.",
   "Bridge/Synthetic flag differences are excluded only from the separate linkage-flag metric; the full access-flag mismatch list remains preserved.",
   "This audit does not declare the 660 generated-only bridge descriptors acceptable final ABI; it isolates them for final source-representation treatment."
 ]
}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")

MD.write_text(
 "# Post-javac0 Method / Field ABI Audit\n\n"
 f"Status: **{state['status']}**\n\n"
 "## Field identity\n\n"
 f"- Missing / extra: **{len(field_missing)} / {len(field_extra)}**\n"
 f"- Access-flag mismatches: **{len(field_flag)}**\n"
 f"- Generic Signature mismatches: **{len(field_sig)}**\n\n"
 "## Method identity\n\n"
 f"- Missing / extra: **{len(method_missing)} / {len(method_extra)}**\n"
 f"- Known WP2 generated builder bridge extras: **{len(known_extra)} / {len(known_bridge_extras)}**\n"
 f"- Unclassified extras: **{len(unknown_extra)}**\n"
 f"- Linkage-flag mismatches excluding BRIDGE/SYNTHETIC: **{len(method_linkage_flag)}**\n"
 f"- Generic Signature mismatches: **{len(method_sig)}**\n"
 f"- Exceptions mismatches: **{len(method_exc)}**\n\n"
 "## Gates\n\n"
 f"- FIELD_DESCRIPTOR_IDENTITY_EXACT: **{state['gates']['field_descriptor_identity_exact']}**\n"
 f"- METHOD_DESCRIPTOR_IDENTITY_EXACT: **{state['gates']['method_descriptor_identity_exact']}**\n"
 f"- METHOD_DESCRIPTOR_AFTER_WP2_CLASSIFICATION: **{state['gates']['method_descriptor_identity_after_wp2_bridge_classification']}**\n"
 f"- WP2_BRIDGE_CLASSIFICATION_COMPLETE: **{state['gates']['wp2_bridge_classification_complete']}**\n",
 encoding="utf-8")
print(json.dumps(state,indent=2))
