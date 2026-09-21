#!/usr/bin/env python3
import csv, json, re, struct, zipfile
from collections import defaultdict
from pathlib import Path

ROOT=Path(".")
REC=ROOT/"recovery"
BUILD=REC/"normalized-stage-build"
STATE=REC/"normalized_stage_compile.json"
DONOR=ROOT/"l1jserver2.jar"
INV=REC/"class_inventory.csv"
NSMAP=REC/"source_namespace_map.csv"
TRANSFORM=REC/"stage_transform.json"
BUILDER_COLLISION=REC/"normalized_builder_collision_transform.json"
OUT=REC/"post_javac0_class_hierarchy.json"
REPORT=REC/"POST_JAVAC0_CLASS_HIERARCHY.md"

def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4

def parse_class(data):
    p=0
    magic,p=u4(data,p)
    if magic!=0xCAFEBABE: raise ValueError("not class")
    minor,p=u2(data,p); major,p=u2(data,p)
    cpc,p=u2(data,p)
    cp=[None]*cpc
    i=1
    while i<cpc:
        tag,p=u1(data,p)
        if tag==1:
            n,p=u2(data,p); raw=data[p:p+n]; p+=n; cp[i]=(tag,raw.decode("utf-8","replace"))
        elif tag in (3,4):
            cp[i]=(tag,None); p+=4
        elif tag in (5,6):
            cp[i]=(tag,None); p+=8; i+=1
        elif tag in (7,8,16,19,20):
            x,p=u2(data,p); cp[i]=(tag,x)
        elif tag in (9,10,11,12,17,18):
            a,p=u2(data,p); b,p=u2(data,p); cp[i]=(tag,a,b)
        elif tag==15:
            a,p=u1(data,p); b,p=u2(data,p); cp[i]=(tag,a,b)
        else:
            raise ValueError(f"unknown cp tag {tag}")
        i+=1

    def utf(idx):
        if not idx: return None
        e=cp[idx]
        return e[1] if e and e[0]==1 else None
    def cls(idx):
        if not idx: return None
        e=cp[idx]
        return utf(e[1]) if e and e[0]==7 else None
    def nt(idx):
        if not idx: return (None,None)
        e=cp[idx]
        return (utf(e[1]),utf(e[2])) if e and e[0]==12 else (None,None)

    access,p=u2(data,p); this_i,p=u2(data,p); super_i,p=u2(data,p)
    ic,p=u2(data,p)
    interfaces=[]
    for _ in range(ic):
        x,p=u2(data,p); interfaces.append(cls(x))

    def skip_members(pos):
        n,pos=u2(data,pos)
        for _ in range(n):
            pos+=6
            ac,pos=u2(data,pos)
            for __ in range(ac):
                _,pos=u2(data,pos); ln,pos=u4(data,pos); pos+=ln
        return pos

    p=skip_members(p)  # fields
    p=skip_members(p)  # methods

    ac,p=u2(data,p)
    source=None; signature=None; enclosing=None; inner_self=None
    nest_host=None; nest_members=[]
    this_name=cls(this_i)
    for _ in range(ac):
        name_i,p=u2(data,p); ln,p=u4(data,p)
        name=utf(name_i); payload=data[p:p+ln]; p+=ln
        q=0
        if name=="SourceFile" and ln==2:
            idx,_=u2(payload,0); source=utf(idx)
        elif name=="Signature" and ln==2:
            idx,_=u2(payload,0); signature=utf(idx)
        elif name=="EnclosingMethod" and ln==4:
            ci,q=u2(payload,q); mi,q=u2(payload,q)
            mn,md=nt(mi)
            enclosing={"class":cls(ci),"method_name":mn,"method_descriptor":md}
        elif name=="InnerClasses":
            n,q=u2(payload,q)
            for __ in range(n):
                ii,q=u2(payload,q); oi,q=u2(payload,q); ni,q=u2(payload,q); fl,q=u2(payload,q)
                if cls(ii)==this_name:
                    inner_self={"outer":cls(oi),"inner_name":utf(ni),"flags":fl}
        elif name=="NestHost" and ln==2:
            idx,_=u2(payload,0); nest_host=cls(idx)
        elif name=="NestMembers":
            n,q=u2(payload,q)
            for __ in range(n):
                idx,q=u2(payload,q); nest_members.append(cls(idx))
    return {
        "name":this_name,"major":major,"minor":minor,"access":access,
        "super":cls(super_i),"interfaces":interfaces,"source_file":source,
        "signature":signature,"inner_self":inner_self,"enclosing":enclosing,
        "nest_host":nest_host,"nest_members":nest_members,
    }

compile_state=json.loads(STATE.read_text(encoding="utf-8"))
if compile_state.get("compile_exit_code")!=0:
    state={"gate":"POST_JAVAC0_CLASS_HIERARCHY","skipped":True,"reason":"normalized compile is not javac0",
           "compile_exit_code":compile_state.get("compile_exit_code")}
    OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
    REPORT.write_text("# Post-javac0 Class/Hierarchy Audit\n\nSKIPPED: normalized compile is not javac0.\n",encoding="utf-8")
    print(json.dumps(state,indent=2))
    raise SystemExit(0)

for pth in (BUILD,DONOR,INV,NSMAP):
    if not pth.exists(): raise SystemExit(f"missing required input: {pth}")

renames={}
if TRANSFORM.exists():
    try: renames=json.loads(TRANSFORM.read_text(encoding="utf-8")).get("recovery_only_class_renames",{})
    except Exception: renames={}

with NSMAP.open(encoding="utf-8-sig",newline="") as f:
    ns_rows=list(csv.DictReader(f))
ns_old_to_new={r["OldInternal"]:r["NewInternal"] for r in ns_rows}
ns_new_to_old={r["NewInternal"]:r["OldInternal"] for r in ns_rows}
if len(ns_old_to_new)!=1109 or len(ns_new_to_old)!=1109:
    raise SystemExit(f"namespace map must be bijective 1109/1109, got {len(ns_old_to_new)}/{len(ns_new_to_old)}")

# Keyword-safe source renames happen after namespace normalization.
keyword_new_to_old={}
for old_fq,new_fq in renames.items():
    old=old_fq.replace(".","/")
    new=new_fq.replace(".","/")
    keyword_new_to_old[new]=old
    keyword_new_to_old["l1r/"+new]=old

# Exact nine-source nested builder rename repair:
# readable namespace L1R_a$L1R_a -> source-safe L1R_a$L1R_Builder.
builder_collision_new_to_old={}
if BUILDER_COLLISION.exists():
    bc=json.loads(BUILDER_COLLISION.read_text(encoding="utf-8"))
    repairs=bc.get("repairs",[])
    if len(repairs)!=9:
        raise SystemExit(f"builder collision repair count must be 9, got {len(repairs)}")
    for row in repairs:
        old_readable="l1r/an/"+row["old_binary_identity"]
        new_readable="l1r/an/"+row["source_recovery_identity"]
        donor=ns_new_to_old.get(old_readable)
        if donor is None:
            raise SystemExit(f"missing namespace donor mapping for {old_readable}")
        builder_collision_new_to_old[new_readable]=donor

def norm_name(name):
    if name is None: return None
    # Recovery-only embedded protobuf runtime relocation.
    if name.startswith("l1rpb/"):
        return "a/"+name[len("l1rpb/"):]
    # Exact source-safe nested builder rename takes precedence.
    if name in builder_collision_new_to_old:
        return builder_collision_new_to_old[name]
    # Authoritative donor <-> readable-source namespace mapping.
    if name in ns_new_to_old:
        return ns_new_to_old[name]
    # Keyword-safe top-level source aliases.
    if name in keyword_new_to_old:
        return keyword_new_to_old[name]
    for new,old in keyword_new_to_old.items():
        if name.startswith(new+"$"):
            return old+name[len(new):]
    return name

_type_token=re.compile(r"L([^;<]+)(?=[;<])")
def norm_sig(s):
    if s is None: return None
    return _type_token.sub(lambda m:"L"+norm_name(m.group(1)),s)

def normalize_meta(m):
    x=dict(m)
    original_name=x["name"]
    x["name"]=norm_name(x["name"]); x["super"]=norm_name(x["super"])
    x["interfaces"]=sorted(norm_name(v) for v in x["interfaces"])
    x["signature"]=norm_sig(x["signature"])
    if x["inner_self"]:
        x["inner_self"]=dict(x["inner_self"])
        x["inner_self"]["outer"]=norm_name(x["inner_self"]["outer"])
        # source namespace intentionally renames named inner segments (L1R_*).
        # Normalize the readable InnerClasses.inner_name back to donor simple segment.
        if x["inner_self"].get("inner_name") is not None and "$" in x["name"]:
            x["inner_self"]["inner_name"]=x["name"].rsplit("$",1)[1]
    if x["enclosing"]:
        x["enclosing"]=dict(x["enclosing"])
        x["enclosing"]["class"]=norm_name(x["enclosing"]["class"])
        x["enclosing"]["method_descriptor"]=norm_sig(x["enclosing"]["method_descriptor"])
    x["nest_host"]=norm_name(x["nest_host"])
    x["nest_members"]=sorted(norm_name(v) for v in x["nest_members"])
    x["recovered_internal_name"]=original_name
    return x

with INV.open(encoding="utf-8-sig",newline="") as f:
    rows=list(csv.DictReader(f))
donor_paths=[r["ClassPath"] for r in rows if r.get("ClassPath")]
# Embedded protobuf runtime a/** is supplied via recovery compile-ref, not generated application source.
donor_app_paths=[p for p in donor_paths if not p.startswith("a/")]
donor_runtime_paths=[p for p in donor_paths if p.startswith("a/")]

generated_files=sorted(BUILD.rglob("*.class"))
generated_raw={}
for pth in generated_files:
    rel=pth.relative_to(BUILD).as_posix()
    generated_raw[rel]=parse_class(pth.read_bytes())

donor_raw={}
with zipfile.ZipFile(DONOR,"r") as z:
    names=set(z.namelist())
    for pth in donor_app_paths:
        if pth in names:
            donor_raw[pth]=parse_class(z.read(pth))

raw_gen=set(generated_raw)
raw_donor=set(donor_app_paths)
raw_missing=sorted(raw_donor-raw_gen)
raw_extra=sorted(raw_gen-raw_donor)

gen_norm={}
for raw,m in generated_raw.items():
    nm=normalize_meta(m); key=nm["name"]+".class"
    gen_norm[key]=(raw,nm)

donor_norm={}
for raw,m in donor_raw.items():
    nm=normalize_meta(m); key=nm["name"]+".class"
    donor_norm[key]=(raw,nm)

missing=set(donor_norm)-set(gen_norm)
extra=set(gen_norm)-set(donor_norm)

def fp(m):
    enc=m.get("enclosing") or {}
    inner=m.get("inner_self") or {}
    return (
        m.get("source_file"),m.get("super"),tuple(m.get("interfaces") or []),
        enc.get("class"),enc.get("method_name"),enc.get("method_descriptor"),
        inner.get("inner_name"),bool(m.get("access",0)&0x1000)
    )

# Conservative structural remap for inner/local/anonymous drift only.
by_fp_d=defaultdict(list); by_fp_g=defaultdict(list)
for k in missing:
    if "$" in k: by_fp_d[fp(donor_norm[k][1])].append(k)
for k in extra:
    if "$" in k: by_fp_g[fp(gen_norm[k][1])].append(k)
structural={}
for key,ds in by_fp_d.items():
    gs=by_fp_g.get(key,[])
    if len(ds)==1 and len(gs)==1:
        structural[gs[0]]=ds[0]

for gk,dk in structural.items():
    raw,nm=gen_norm.pop(gk)
    nm=dict(nm); nm["name"]=dk[:-6]
    gen_norm[dk]=(raw,nm)

missing=sorted(set(donor_norm)-set(gen_norm))
extra=sorted(set(gen_norm)-set(donor_norm))
common=sorted(set(donor_norm)&set(gen_norm))

super_mm=[]; iface_mm=[]; inner_mm=[]; enc_mm=[]; nest_mm=[]; sig_mm=[]; src_mm=[]
for k in common:
    dm=donor_norm[k][1]; gm=gen_norm[k][1]
    if dm["super"]!=gm["super"]: super_mm.append({"class":k,"donor":dm["super"],"generated":gm["super"]})
    if dm["interfaces"]!=gm["interfaces"]: iface_mm.append({"class":k,"donor":dm["interfaces"],"generated":gm["interfaces"]})
    if dm["inner_self"]!=gm["inner_self"]: inner_mm.append({"class":k,"donor":dm["inner_self"],"generated":gm["inner_self"]})
    if dm["enclosing"]!=gm["enclosing"]: enc_mm.append({"class":k,"donor":dm["enclosing"],"generated":gm["enclosing"]})
    if dm["nest_host"]!=gm["nest_host"] or dm["nest_members"]!=gm["nest_members"]:
        nest_mm.append({"class":k,"donor":{"host":dm["nest_host"],"members":dm["nest_members"]},
                        "generated":{"host":gm["nest_host"],"members":gm["nest_members"]}})
    if dm["signature"]!=gm["signature"]: sig_mm.append({"class":k,"donor":dm["signature"],"generated":gm["signature"]})
    if dm["source_file"]!=gm["source_file"]: src_mm.append({"class":k,"donor":dm["source_file"],"generated":gm["source_file"]})

def classes_with_super(table,super_name):
    return sorted(k for k,(_,m) in table.items() if m.get("super")==super_name)

donor_builders=classes_with_super(donor_norm,"a/p$a")
gen_builders=classes_with_super(gen_norm,"a/p$a")
donor_parsers=classes_with_super(donor_norm,"a/c")
gen_parsers=classes_with_super(gen_norm,"a/c")
builder_common=set(donor_builders)&set(gen_builders)
parser_common=set(donor_parsers)&set(gen_parsers)

majors_d=sorted({m["major"] for _,m in donor_norm.values()})
majors_g=sorted({m["major"] for _,m in gen_norm.values()})

state={
 "gate":"POST_JAVAC0_CLASS_HIERARCHY",
 "skipped":False,
 "baseline":{
   "java_sources_submitted":compile_state.get("java_sources_submitted"),
   "compile_exit_code":compile_state.get("compile_exit_code"),
   "javac_error_headers":compile_state.get("javac_error_headers"),
   "generated_class_files_total":compile_state.get("generated_class_files_total"),
   "full_donor_game_jar_on_classpath":compile_state.get("full_donor_game_jar_on_classpath"),
 },
 "donor_inventory":{
   "csv_total":len(donor_paths),"application_comparable":len(donor_app_paths),
   "embedded_protobuf_runtime_excluded":len(donor_runtime_paths),
   "parsed_application_classes":len(donor_raw),
 },
 "generated_inventory":{"raw_total":len(generated_raw)},
 "raw_class_set":{"missing":len(raw_missing),"extra":len(raw_extra),
                  "missing_sample":raw_missing[:100],"extra_sample":raw_extra[:100]},
 "normalization":{
   "identity_authority":"recovery/source_namespace_map.csv",
   "namespace_map_rows":len(ns_rows),
   "namespace_map_old_unique":len(ns_old_to_new),
   "namespace_map_new_unique":len(ns_new_to_old),
   "builder_collision_identity_rows":len(builder_collision_new_to_old),
   "protobuf_runtime_reference":"l1rpb/** -> a/** for hierarchy descriptors",
   "recovery_only_class_renames":renames,
   "structural_inner_remaps":structural,
   "structural_inner_remap_count":len(structural),
 },
 "normalized_class_set":{"donor":len(donor_norm),"generated":len(gen_norm),
                         "missing":len(missing),"extra":len(extra),
                         "missing_classes":missing,"extra_classes":extra},
 "hierarchy":{
   "common_classes":len(common),
   "superclass_mismatches":len(super_mm),"interface_mismatches":len(iface_mm),
   "inner_self_mismatches":len(inner_mm),"enclosing_method_mismatches":len(enc_mm),
   "nest_mismatches":len(nest_mm),"class_signature_mismatches":len(sig_mm),
   "sourcefile_mismatches":len(src_mm),
   "superclass_mismatch_sample":super_mm[:100],
   "interface_mismatch_sample":iface_mm[:100],
   "inner_self_mismatch_sample":inner_mm[:100],
   "enclosing_mismatch_sample":enc_mm[:100],
   "nest_mismatch_sample":nest_mm[:100],
   "class_signature_mismatch_sample":sig_mm[:100],
   "sourcefile_mismatch_sample":src_mm[:100],
 },
 "builders":{
   "donor_count":len(donor_builders),"generated_count":len(gen_builders),
   "mapped_common":len(builder_common),
   "donor_only":sorted(set(donor_builders)-set(gen_builders)),
   "generated_only":sorted(set(gen_builders)-set(donor_builders)),
 },
 "parsers":{
   "donor_count":len(donor_parsers),"generated_count":len(gen_parsers),
   "mapped_common":len(parser_common),
   "donor_only":sorted(set(donor_parsers)-set(gen_parsers)),
   "generated_only":sorted(set(gen_parsers)-set(donor_parsers)),
 },
 "classfile_major_versions":{"donor":majors_d,"generated":majors_g},
 "gates":{
   "class_set_pass":len(missing)==0 and len(extra)==0,
   "runtime_hierarchy_pass":len(super_mm)==0 and len(iface_mm)==0 and len(inner_mm)==0 and len(enc_mm)==0,
   "generic_signature_pass":len(sig_mm)==0,
   "nest_metadata_pass":len(nest_mm)==0,
 },
 "notes":[
   "source_namespace_map.csv is the authoritative 1109-entry donor<->recovered class identity map.",
   "Embedded donor protobuf runtime a/** is excluded from application generated-class parity only if present in class_inventory.csv; current inventory determines the actual count.",
   "Structural anonymous/local remapping is conservative: only unique same-fingerprint inner-class pairs are remapped.",
   "Method/field ABI is intentionally outside this gate."
 ]
}
OUT.write_text(json.dumps(state,indent=2,ensure_ascii=False)+"\n",encoding="utf-8")
REPORT.write_text(
 "# Post-javac0 Class Set / Hierarchy Audit\n\n"
 f"- Generated classes: **{len(generated_raw)}**\n"
 f"- Comparable donor application classes: **{len(donor_norm)}**\n"
 f"- Normalized missing / extra: **{len(missing)} / {len(extra)}**\n"
 f"- Superclass mismatches: **{len(super_mm)}**\n"
 f"- Interface mismatches: **{len(iface_mm)}**\n"
 f"- Inner ownership mismatches: **{len(inner_mm)}**\n"
 f"- EnclosingMethod mismatches: **{len(enc_mm)}**\n"
 f"- Class Signature mismatches: **{len(sig_mm)}**\n"
 f"- Builder super=a/p$a donor/generated: **{len(donor_builders)} / {len(gen_builders)}**\n"
 f"- Parser super=a/c donor/generated: **{len(donor_parsers)} / {len(gen_parsers)}**\n"
 f"- CLASS_SET_PASS: **{state['gates']['class_set_pass']}**\n"
 f"- RUNTIME_HIERARCHY_PASS: **{state['gates']['runtime_hierarchy_pass']}**\n",
 encoding="utf-8"
)
print(json.dumps(state,indent=2,ensure_ascii=False))
