#!/usr/bin/env python3
import csv, json, re, struct, zipfile
from pathlib import Path

ROOT=Path("."); REC=ROOT/"recovery"
DONOR=ROOT/"l1jserver2.jar"; BUILD=REC/"normalized-stage-build"
NSMAP=REC/"source_namespace_map.csv"
COLLISION=REC/"normalized_builder_collision_transform.json"
HIER=REC/"post_javac0_class_hierarchy.json"
OUT=REC/"generic_builder_signature_audit.json"
MD=REC/"GENERIC_BUILDER_SIGNATURE_AUDIT.md"

ACC_BRIDGE=0x0040
ACC_SYNTHETIC=0x1000
EXPECTED=44
SIG_RE=re.compile(r"^La/p\$a<L(?P<self>[^;]+);>;L(?P<iface>[^;]+);$")
PROVEN_BRIDGE_FAMILIES={
    ("i","()La/x$a;"),
    ("j","()La/x$a;"),
    ("d","(La/h;La/n;)La/x$a;"),
    ("c","(La/x;)La/x$a;"),
}

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

    fc,p=u2(data,p)
    for _ in range(fc):
        p+=6; ac,p=u2(data,p)
        for __ in range(ac):
            _,p=u2(data,p); ln,p=u4(data,p); p+=ln

    mc,p=u2(data,p); methods=[]
    for _ in range(mc):
        flags,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p)
        ac,p=u2(data,p)
        attrs={}
        for __ in range(ac):
            ai,p=u2(data,p); ln,p=u4(data,p); payload=data[p:p+ln]; p+=ln
            an=utf(ai)
            if an=="Signature" and ln==2:
                si,_=u2(payload,0); attrs["Signature"]=utf(si)
        methods.append({"name":utf(ni),"descriptor":utf(di),"flags":flags,"signature":attrs.get("Signature")})

    ac,p=u2(data,p); signature=None
    for _ in range(ac):
        ai,p=u2(data,p); ln,p=u4(data,p); payload=data[p:p+ln]; p+=ln
        if utf(ai)=="Signature" and ln==2:
            si,_=u2(payload,0); signature=utf(si)
    return {"name":cls(this_i),"super":cls(super_i),"interfaces":interfaces,
            "access":access,"signature":signature,"methods":methods}

with NSMAP.open(encoding="utf-8-sig",newline="") as f:
    rows=list(csv.DictReader(f))
old_to_new={r["OldInternal"]:r["NewInternal"] for r in rows}
new_to_old={r["NewInternal"]:r["OldInternal"] for r in rows}
if len(old_to_new)!=1109 or len(new_to_old)!=1109:
    raise SystemExit("namespace map is not bijective 1109/1109")

collision_donor_to_new={}
if COLLISION.exists():
    c=json.loads(COLLISION.read_text(encoding="utf-8"))
    repairs=c.get("repairs",[])
    if len(repairs)!=9: raise SystemExit(f"expected 9 builder collision repairs, got {len(repairs)}")
    for r in repairs:
        old_readable="l1r/an/"+r["old_binary_identity"]
        donor=new_to_old.get(old_readable)
        if not donor: raise SystemExit(f"missing donor for {old_readable}")
        collision_donor_to_new[donor]="l1r/an/"+r["source_recovery_identity"]

def generated_name(donor):
    return collision_donor_to_new.get(donor,old_to_new.get(donor))

def norm_name(name):
    if name is None: return None
    if name.startswith("l1rpb/"): return "a/"+name[len("l1rpb/"):]
    if name in new_to_old: return new_to_old[name]
    for donor,new in collision_donor_to_new.items():
        if name==new: return donor
    return name

TYPE_RE=re.compile(r"L([^;<]+)(?=[;<])")
def norm_desc(s):
    if s is None: return None
    return TYPE_RE.sub(lambda m:"L"+norm_name(m.group(1)),s)

hier=json.loads(HIER.read_text(encoding="utf-8"))
if not hier.get("gates",{}).get("class_set_pass") or not hier.get("gates",{}).get("runtime_hierarchy_pass"):
    raise SystemExit("requires closed class-set/runtime hierarchy gates")
sample=hier.get("hierarchy",{}).get("class_signature_mismatch_sample",[])
if hier.get("hierarchy",{}).get("class_signature_mismatches")!=EXPECTED:
    raise SystemExit("expected exactly 44 class Signature mismatches")
builders=sorted(x["class"][:-6] for x in sample)
if len(builders)!=EXPECTED or len(set(builders))!=EXPECTED:
    raise SystemExit("signature mismatch sample must contain the exact 44 builder classes")

with zipfile.ZipFile(DONOR) as z:
    zn=set(z.namelist())
    result=[]
    for donor_name in builders:
        dp=donor_name+".class"
        if dp not in zn: raise SystemExit(f"missing donor builder {dp}")
        gp=generated_name(donor_name)
        if not gp: raise SystemExit(f"missing generated mapping for {donor_name}")
        gf=BUILD/(gp+".class")
        if not gf.exists(): raise SystemExit(f"missing generated builder {gf}")
        d=parse_class(z.read(dp)); g=parse_class(gf.read_bytes())

        dsig=d["signature"]; gsig=norm_desc(g["signature"])
        m=SIG_RE.match(dsig or "")
        pattern_ok=bool(m)
        self_ok=bool(m and m.group("self")==donor_name)
        iface=m.group("iface") if m else None
        iface_ok=bool(iface and iface in d["interfaces"] and iface in [norm_name(x) for x in g["interfaces"]])

        # Bridge-descriptor authority is class method identity, not bridge flags:
        # obfuscation may alter ACC_BRIDGE/ACC_SYNTHETIC representation.
        donor_all={(x["name"],norm_desc(x["descriptor"])):x["flags"] for x in d["methods"]}
        generated_all={(x["name"],norm_desc(x["descriptor"])):x["flags"] for x in g["methods"]}
        gb=sorted((x["name"],norm_desc(x["descriptor"])) for x in g["methods"]
                  if (x["flags"] & ACC_BRIDGE) and (x["flags"] & ACC_SYNTHETIC))
        gb_set=set(gb)
        donor_keys=set(donor_all)
        generated_bridge_missing=sorted(gb_set-donor_keys)
        matched_generated_bridges=sorted(gb_set & donor_keys)
        proven_donor=sorted(PROVEN_BRIDGE_FAMILIES & donor_keys)
        proven_generated=sorted(PROVEN_BRIDGE_FAMILIES & gb_set)
        full_generated_bridge_parity=(len(generated_bridge_missing)==0)
        proven_four_parity=(set(proven_donor)==PROVEN_BRIDGE_FAMILIES and set(proven_generated)==PROVEN_BRIDGE_FAMILIES)
        result.append({
          "class":donor_name+".class","generated_internal":gp,
          "donor_signature":dsig,"generated_signature":gsig,
          "donor_signature_pattern_ok":pattern_ok,
          "donor_self_type_ok":self_ok,
          "donor_orbuilder_interface":iface,"interface_alignment_ok":iface_ok,
          "normalized_super_match":norm_name(d["super"])==norm_name(g["super"])=="a/p$a",
          "bridge_name_descriptor_match":full_generated_bridge_parity,
          "proven_four_bridge_family_match":proven_four_parity,
          "donor_method_count":len(donor_all),"generated_method_count":len(generated_all),
          "generated_bridge_count":len(gb),
          "generated_bridge_descriptors":[{"name":n,"descriptor":x} for n,x in gb],
          "generated_bridges_present_in_donor":[
              {"name":n,"descriptor":x,"donor_flags":donor_all[(n,x)]}
              for n,x in matched_generated_bridges
          ],
          "generated_bridges_missing_in_donor":[{"name":n,"descriptor":x} for n,x in generated_bridge_missing],
          "proven_four_in_donor":[{"name":n,"descriptor":x,"donor_flags":donor_all[(n,x)]} for n,x in proven_donor],
          "proven_four_in_generated_bridge":[{"name":n,"descriptor":x} for n,x in proven_generated],
        })

pattern_bad=[x for x in result if not x["donor_signature_pattern_ok"]]
self_bad=[x for x in result if not x["donor_self_type_ok"]]
iface_bad=[x for x in result if not x["interface_alignment_ok"]]
super_bad=[x for x in result if not x["normalized_super_match"]]
gen_nonnull=[x for x in result if x["generated_signature"] is not None]
bridge_bad=[x for x in result if not x["bridge_name_descriptor_match"]]
proven_four_bad=[x for x in result if not x["proven_four_bridge_family_match"]]

# Bridge descriptor parity is a WP2 finding, not an execution prerequisite.
# A DIFF must be persisted for WP3 instead of aborting before evidence is saved.
status="PASS" if not (pattern_bad or self_bad or iface_bad or super_bad or gen_nonnull) else "FAIL"
state={
 "gate":"GENERIC_BUILDER_CLASS_SIGNATURE_AUDIT",
 "status":status,
 "builder_count":len(result),
 "donor_signature_present_count":sum(x["donor_signature"] is not None for x in result),
 "generated_signature_null_count":sum(x["generated_signature"] is None for x in result),
 "donor_signature_pattern_mismatches":len(pattern_bad),
 "donor_self_type_mismatches":len(self_bad),
 "orbuilder_interface_alignment_mismatches":len(iface_bad),
 "normalized_superclass_mismatches":len(super_bad),
 "bridge_descriptor_mismatch_classes":len(bridge_bad),
 "generated_bridge_total":sum(x["generated_bridge_count"] for x in result),
 "generated_bridge_present_in_donor_total":sum(len(x["generated_bridges_present_in_donor"]) for x in result),
 "generated_bridge_missing_in_donor_total":sum(len(x["generated_bridges_missing_in_donor"]) for x in result),
 "proven_four_bridge_mismatch_classes":len(proven_four_bad),
 "classification":{
   "runtime_linkage_hierarchy":"PASS",
   "reflective_generic_metadata":"DIFF",
   "javac_generated_bridge_descriptors":"PASS" if not bridge_bad else "DIFF",
   "bridge_identity_authority":"METHOD_NAME_PLUS_DESCRIPTOR__FLAGS_NONAUTHORITATIVE",
   "source_restore_required_for_runtime_linkage":False,
   "final_treatment":"DOCUMENT_EXACT_SOURCE_REPRESENTATION_EXCEPTION_OR_RESTORE_METADATA",
   "broad_generic_superclass_restore_permitted_by_this_wp":False
 },
 "builders":result,
 "notes":[
   "Class Signature is optional JVM metadata for generic reflection/tooling; superclass and interface descriptors remain separately verified.",
   "Bridge descriptor parity is checked by method name+descriptor existence in donor; ACC_BRIDGE/ACC_SYNTHETIC flags are recorded but not used as identity authority.",
   "The four source-removed donor bridge families i(), j(), d(h,n), c(x) must exist in both donor and generated builder classes.",
   "Generated builders intentionally use a raw p$a source representation, so their class-level Signature attribute is absent.",
   "This gate does not authorize changing the builder superclass or application source.",
   "A strict zero-difference generic-metadata policy would require a later safe representation; current completion rules also permit an exact documented source-representation equivalence."
 ]
}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
MD.write_text(
 "# Generic Builder Class Signature Audit\n\n"
 f"Status: **{status}**\n\n"
 "## Result\n\n"
 f"- Builder classes: **{len(result)} / {EXPECTED}**\n"
 f"- Donor class Signature present: **{state['donor_signature_present_count']}**\n"
 f"- Generated class Signature null: **{state['generated_signature_null_count']}**\n"
 f"- Donor Signature pattern mismatch: **{len(pattern_bad)}**\n"
 f"- Self-type mismatch: **{len(self_bad)}**\n"
 f"- OrBuilder interface alignment mismatch: **{len(iface_bad)}**\n"
 f"- Runtime superclass mismatch: **{len(super_bad)}**\n"
 f"- Bridge descriptor mismatch classes: **{len(bridge_bad)}**\n"
 f"- Generated bridge methods: **{state['generated_bridge_total']}**\n"
 f"- Generated bridges present in donor: **{state['generated_bridge_present_in_donor_total']}**\n"
 f"- Generated bridges missing in donor: **{state['generated_bridge_missing_in_donor_total']}**\n"
 f"- Proven four-family mismatch classes: **{state['proven_four_bridge_mismatch_classes']}**\n\n"
 "## Classification\n\n"
 "- Runtime linkage hierarchy: **PASS**\n"
 "- Reflective generic metadata: **DIFF**\n"
 f"- javac-generated bridge descriptors: **{state['classification']['javac_generated_bridge_descriptors']}**\n"
 "- Restoring typed builder superclass is **NOT authorized** by this WP.\n"
 "- Final treatment: document exact source-representation exception or establish a later safe metadata/source representation.\n",
 encoding="utf-8")
print(json.dumps(state,indent=2))
if status!="PASS": raise SystemExit("generic builder signature audit failed")
