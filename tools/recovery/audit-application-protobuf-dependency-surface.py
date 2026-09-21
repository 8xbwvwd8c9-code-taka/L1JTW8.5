#!/usr/bin/env python3
import json, re, struct, zipfile
from collections import Counter, defaultdict
from pathlib import Path

BUILD=Path("recovery/normalized-stage-build")
OFFICIAL=Path("lib/protobuf-java-2.5.0.jar")
MAP=Path("recovery/protobuf_2_5_0_order_mapping.json")
OUT=Path("recovery/application_protobuf_dependency_surface.json")
if not BUILD.exists(): raise SystemExit("missing normalized-stage-build")
if not OFFICIAL.exists(): raise SystemExit("missing official protobuf 2.5.0 jar")
if not MAP.exists(): raise SystemExit("missing protobuf class mapping")

CMAP=json.loads(MAP.read_text(encoding="utf-8"))["mapping"]
if len(CMAP)!=246: raise SystemExit(f"class map incomplete: {len(CMAP)}")

def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4

def parse_cp(data):
    p=0; magic,p=u4(data,p)
    if magic!=0xCAFEBABE: raise ValueError("bad class")
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
        else: raise ValueError(tag)
        i+=1
    def utf(idx):
        e=cp[idx] if idx else None
        return e[1] if e and e[0]==1 else None
    def cls(idx):
        e=cp[idx] if idx else None
        return utf(e[1]) if e and e[0]==7 else None
    refs=[]
    for e in cp:
        if not e or e[0] not in (9,10,11): continue
        owner=cls(e[1]); nt=cp[e[2]]
        if not nt or nt[0]!=12: continue
        name=utf(nt[1]); desc=utf(nt[2])
        refs.append((e[0],owner,name,desc))
    return refs

# Official class member inventory.
def parse_members(data):
    p=0; magic,p=u4(data,p)
    if magic!=0xCAFEBABE: raise ValueError("bad class")
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
        else: raise ValueError(tag)
        i+=1
    def utf(idx):
        e=cp[idx] if idx else None
        return e[1] if e and e[0]==1 else None
    def cls(idx):
        e=cp[idx] if idx else None
        return utf(e[1]) if e and e[0]==7 else None
    _,p=u2(data,p); thisi,p=u2(data,p); _,p=u2(data,p)
    ic,p=u2(data,p); p+=2*ic
    def mem():
        nonlocal p
        fl,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p); ac,p=u2(data,p)
        for _ in range(ac):
            _,p=u2(data,p); ln,p=u4(data,p); p+=ln
        return {"flags":fl,"name":utf(ni),"desc":utf(di)}
    fc,p=u2(data,p); fs=[mem() for _ in range(fc)]
    mc,p=u2(data,p); ms=[mem() for _ in range(mc)]
    return cls(thisi),fs,ms

official={}
with zipfile.ZipFile(OFFICIAL) as z:
    for n in z.namelist():
        if n.endswith(".class") and not n.startswith("META-INF/"):
            cn,fs,ms=parse_members(z.read(n)); official[cn]={"fields":fs,"methods":ms}

OBJ=re.compile(r"L([^;]+);")
def trans_desc(desc):
    return OBJ.sub(lambda m:"L"+CMAP.get(m.group(1),m.group(1))+";",desc)

surface=Counter()
callers=defaultdict(set)
for p in BUILD.rglob("*.class"):
    rel=p.relative_to(BUILD).as_posix()
    # only application classes; runtime ref is not emitted into application build dir anyway.
    try: refs=parse_cp(p.read_bytes())
    except Exception: continue
    for tag,owner,name,desc in refs:
        if owner and owner.startswith("l1rpb/"):
            k=(tag,owner,name,desc)
            surface[k]+=1
            callers[k].add(rel)

rows=[]; unique=ambiguous=missing=0
for (tag,owner,name,desc),uses in sorted(surface.items(),key=lambda kv:str(kv[0])):
    off_owner=CMAP.get(owner)
    kind="field" if tag==9 else "method"
    tdesc=trans_desc(desc)
    cands=[]
    if off_owner in official:
        for m in official[off_owner]["fields" if kind=="field" else "methods"]:
            if m["desc"]==tdesc:
                # invoke/static shape from member flags where available
                is_static=bool(m["flags"] & 0x0008)
                cands.append({"name":m["name"],"desc":m["desc"],"flags":m["flags"],"static":is_static})
    if len(cands)==1:
        status="UNIQUE"; unique+=1
    elif len(cands)==0:
        status="MISSING"; missing+=1
    else:
        status="AMBIGUOUS"; ambiguous+=1
    rows.append({
      "kind":kind,"cp_tag":tag,"donor_owner":owner,"donor_name":name,"donor_desc":desc,
      "official_owner":off_owner,"official_desc":tdesc,"uses":uses,
      "caller_count":len(callers[(tag,owner,name,desc)]),
      "callers":sorted(callers[(tag,owner,name,desc)])[:50],
      "status":status,"candidates":cands,
    })

out={
 "gate":"APPLICATION_PROTOBUF_DEPENDENCY_SURFACE",
 "application_classes":len(list(BUILD.rglob("*.class"))),
 "unique_memberrefs":len(rows),
 "reference_uses_total":sum(surface.values()),
 "unique_mapped":unique,"ambiguous":ambiguous,"missing":missing,
 "class_mapping":len(CMAP),
 "official_runtime":"protobuf-java-2.5.0",
 "rows":rows
}
OUT.write_text(json.dumps(out,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:v for k,v in out.items() if k!="rows"},indent=2))
print("AMBIGUOUS="+json.dumps([r for r in rows if r["status"]=="AMBIGUOUS"][:100],indent=2))
print("MISSING="+json.dumps([r for r in rows if r["status"]=="MISSING"][:100],indent=2))
