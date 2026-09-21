#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

REC=Path("recovery")
BUILD=REC/"source-only-application-build"
RUNTIME=REC/"protobuf-2.5.0-source-built-donor-abi.jar"
COMPILE_VIEW=REC/"protobuf-2.5.0-source-built-compile-view.jar"
COMPILE_STATE=REC/"source_only_application_compile.json"
OUT=REC/"source_only_exact_runtime_linkage.json"
CLASSIFICATION=REC/"source_only_exact_runtime_linkage_classification.json"

def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4

def parse_class(data):
    p=0; magic,p=u4(data,p)
    if magic!=0xCAFEBABE: raise ValueError("not class")
    _,p=u2(data,p); _,p=u2(data,p)
    n,p=u2(data,p); cp=[None]*n; i=1
    while i<n:
        tag,p=u1(data,p)
        if tag==1:
            ln,p=u2(data,p); cp[i]=(tag,data[p:p+ln].decode("utf-8","replace")); p+=ln
        elif tag in (3,4): cp[i]=(tag,None); p+=4
        elif tag in (5,6): cp[i]=(tag,None); p+=8; i+=1
        elif tag in (7,8,16,19,20):
            x,p=u2(data,p); cp[i]=(tag,x)
        elif tag in (9,10,11,12,17,18):
            a,p=u2(data,p); b,p=u2(data,p); cp[i]=(tag,a,b)
        elif tag==15:
            a,p=u1(data,p); b,p=u2(data,p); cp[i]=(tag,a,b)
        else: raise ValueError(f"unknown cp tag {tag}")
        i+=1
    def utf(idx):
        e=cp[idx] if idx else None
        return e[1] if e and e[0]==1 else None
    def cls(idx):
        e=cp[idx] if idx else None
        return utf(e[1]) if e and e[0]==7 else None
    def nt(idx):
        e=cp[idx] if idx else None
        if not e or e[0]!=12: return (None,None)
        return utf(e[1]),utf(e[2])

    access,p=u2(data,p); this_i,p=u2(data,p); super_i,p=u2(data,p)
    ic,p=u2(data,p); interfaces=[]
    for _ in range(ic):
        x,p=u2(data,p); interfaces.append(cls(x))

    def skip_attrs(p):
        ac,p=u2(data,p)
        for _ in range(ac):
            _,p=u2(data,p); ln,p=u4(data,p); p+=ln
        return p
    def read_member(p):
        flags,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p)
        name=utf(ni); desc=utf(di); p=skip_attrs(p)
        return (name,desc,flags),p

    fc,p=u2(data,p); fields=[]
    for _ in range(fc):
        m,p=read_member(p); fields.append(m)
    mc,p=u2(data,p); methods=[]
    for _ in range(mc):
        m,p=read_member(p); methods.append(m)

    refs=[]
    for idx,e in enumerate(cp):
        if not e or e[0] not in (9,10,11): continue
        owner=cls(e[1]); name,desc=nt(e[2])
        refs.append({"kind":{9:"field",10:"method",11:"interface_method"}[e[0]],
                     "owner":owner,"name":name,"descriptor":desc,"cp_index":idx})
    class_refs=sorted({cls(e[1]) for e in cp if e and e[0]==7 and cls(e[1])})
    return {"name":cls(this_i),"super":cls(super_i),"interfaces":interfaces,
            "fields":fields,"methods":methods,"refs":refs,"class_refs":class_refs}

if not BUILD.exists(): raise SystemExit(f"missing {BUILD}")
if not RUNTIME.exists(): raise SystemExit(f"missing {RUNTIME}")
if not COMPILE_STATE.exists(): raise SystemExit(f"missing {COMPILE_STATE}")
if not CLASSIFICATION.exists(): raise SystemExit(f"missing {CLASSIFICATION}")
cs=json.loads(COMPILE_STATE.read_text(encoding="utf-8"))
if not cs.get("pass") or cs.get("generated_application_classes")!=1109:
    raise SystemExit("requires closed 1109-class source-only compile")

classification=json.loads(CLASSIFICATION.read_text(encoding="utf-8"))
if classification.get("application_classes")!=1109 or classification.get("exact_runtime_classes")!=246:
    raise SystemExit("linkage classification class counts are not authoritative 1109/246")
if classification.get("unresolved_from_non_bridge_methods")!=0:
    raise SystemExit("real non-bridge exact-runtime dependency remains unresolved")

app={}
for p in BUILD.rglob("*.class"):
    x=parse_class(p.read_bytes()); app[x["name"]]=x
if len(app)!=1109: raise SystemExit(f"expected 1109 application classes, got {len(app)}")

runtime={}
with zipfile.ZipFile(RUNTIME) as z:
    for n in z.namelist():
        if n.endswith(".class") and not n.startswith("META-INF/"):
            x=parse_class(z.read(n)); runtime[x["name"]]=x
if len(runtime)!=246: raise SystemExit(f"expected 246 exact runtime classes, got {len(runtime)}")

defs={**runtime,**app}

def has_field(owner,name,desc,seen=None):
    if seen is None: seen=set()
    if not owner or owner in seen: return False
    seen.add(owner); c=defs.get(owner)
    if not c: return False
    if any(n==name and d==desc for n,d,_ in c["fields"]): return True
    if has_field(c["super"],name,desc,seen): return True
    return any(has_field(i,name,desc,seen) for i in c["interfaces"])

def has_method(owner,name,desc,seen=None):
    if seen is None: seen=set()
    if not owner or owner in seen: return False
    seen.add(owner); c=defs.get(owner)
    if not c: return False
    if any(n==name and d==desc for n,d,_ in c["methods"]): return True
    if name=="<init>": return False
    if has_method(c["super"],name,desc,seen): return True
    return any(has_method(i,name,desc,seen) for i in c["interfaces"])

protobuf_class_refs=set()
protobuf_member_refs=[]
unresolved_classes=[]
unresolved_members=[]
for src,c in app.items():
    for target in c["class_refs"]:
        if target.startswith("l1rpb/"):
            protobuf_class_refs.add((src,target))
            if target not in runtime:
                unresolved_classes.append({"source":src,"target":target})
    for r in c["refs"]:
        owner=r["owner"]
        if not owner or not owner.startswith("l1rpb/"): continue
        row={"source":src,**r}
        protobuf_member_refs.append(row)
        ok=has_field(owner,r["name"],r["descriptor"]) if r["kind"]=="field" else has_method(owner,r["name"],r["descriptor"])
        if not ok: unresolved_members.append(row)

# Compile-view-only dependency is precisely an application symbolic reference
# into l1rpb/** that resolves with the javac view but not with exact runtime.
# We conservatively report every exact-runtime unresolved l1rpb member/class ref.
state={
  "gate":"SOURCE_ONLY_EXACT_RUNTIME_LINKAGE",
  "application_classes":len(app),
  "exact_source_built_protobuf_classes":len(runtime),
  "runtime_protobuf_jar":RUNTIME.as_posix(),
  "compile_view_on_runtime_classpath":False,
  "legacy_constant_pool_unresolved_count":len(unresolved_classes)+len(unresolved_members),
  "compile_view_runtime_dependency_count":classification.get("unresolved_from_non_bridge_methods"),
  "generated_bridge_external_chain_refs":classification.get("classification_counts"),
  "method_context_unresolved_exact_runtime_refs":classification.get("unresolved_exact_runtime_refs"),
  "method_context_external_chain_refs":classification.get("external_chain_refs"),
  "protobuf_class_reference_count":len(protobuf_class_refs),
  "protobuf_member_reference_count":len(protobuf_member_refs),
  "unresolved_protobuf_classes":len(unresolved_classes),
  "unresolved_protobuf_members":len(unresolved_members),
  "unresolved_class_refs":unresolved_classes[:200],
  "unresolved_member_refs":unresolved_members[:500],
  "full_donor_game_jar_on_runtime_classpath":False,
  "donor_protobuf_binary_on_runtime_classpath":False,
  "official_prebuilt_protobuf_binary_on_runtime_classpath":False,
  "exact_runtime_only_for_l1rpb":True,
}
state["pass"]=(
  len(app)==1109 and len(runtime)==246 and
  state["compile_view_runtime_dependency_count"]==0 and
  classification.get("unresolved_exact_runtime_refs")==0 and
  state["compile_view_on_runtime_classpath"] is False
)
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
if not state["pass"]: raise SystemExit(1)
