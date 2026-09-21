#!/usr/bin/env python3
import json, struct, zipfile
from collections import Counter, defaultdict
from pathlib import Path

REC=Path("recovery")
BUILD=REC/"source-only-application-build"
RUNTIME=REC/"protobuf-2.5.0-source-built-donor-abi.jar"
OUT=REC/"source_only_exact_runtime_linkage_classification.json"

ACC_BRIDGE=0x0040
ACC_SYNTHETIC=0x1000

# Fixed operand lengths excluding opcode byte. Special: tableswitch/lookupswitch/wide.
OPLEN=[0]*256
for op,n in {
  0x10:1,0x11:2,0x12:1,0x13:2,0x14:2,
  0x15:1,0x16:1,0x17:1,0x18:1,0x19:1,
  0x36:1,0x37:1,0x38:1,0x39:1,0x3a:1,
  0x84:2,0x99:2,0x9a:2,0x9b:2,0x9c:2,0x9d:2,0x9e:2,
  0x9f:2,0xa0:2,0xa1:2,0xa2:2,0xa3:2,0xa4:2,0xa5:2,0xa6:2,
  0xa7:2,0xa8:2,0xa9:1,
  0xb2:2,0xb3:2,0xb4:2,0xb5:2,0xb6:2,0xb7:2,0xb8:2,0xb9:4,0xba:4,
  0xbb:2,0xbc:1,0xbd:2,0xc0:2,0xc1:2,0xc5:3,0xc6:2,0xc7:2,
  0xc8:4,0xc9:4
}.items(): OPLEN[op]=n

CP_REF_OPS={0xb2,0xb3,0xb4,0xb5,0xb6,0xb7,0xb8,0xb9}

def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4
def i4(b,p): return struct.unpack_from(">i",b,p)[0],p+4

def walk_code(code):
    p=0
    while p<len(code):
        op=code[p]; start=p; p+=1
        if op==0xaa:
            while p%4: p+=1
            default,p=i4(code,p); low,p=i4(code,p); high,p=i4(code,p)
            if high < low:
                raise SystemExit(f"invalid tableswitch range low={low} high={high}")
            p += 4*(high-low+1)
        elif op==0xab:
            while p%4: p+=1
            default,p=i4(code,p); npairs,p=i4(code,p)
            if npairs < 0:
                raise SystemExit(f"invalid lookupswitch npairs={npairs}")
            p += 8*npairs
        elif op==0xc4:
            sub=code[p]; p+=1
            p += 4 if sub==0x84 else 2
        else:
            n=OPLEN[op]
            operand=code[p:p+n]; p+=n
            if op in CP_REF_OPS and len(operand)>=2:
                yield start,op,struct.unpack(">H",operand[:2])[0]

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
        return (utf(e[1]),utf(e[2])) if e and e[0]==12 else (None,None)
    access,p=u2(data,p); this_i,p=u2(data,p); super_i,p=u2(data,p)
    ic,p=u2(data,p); interfaces=[]
    for _ in range(ic):
        x,p=u2(data,p); interfaces.append(cls(x))

    def skip_attrs(p):
        ac,p=u2(data,p)
        for _ in range(ac):
            _,p=u2(data,p); ln,p=u4(data,p); p+=ln
        return p

    fc,p=u2(data,p)
    fields=[]
    for _ in range(fc):
        fl,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p)
        fields.append((utf(ni),utf(di),fl)); p=skip_attrs(p)

    mc,p=u2(data,p); methods=[]
    for _ in range(mc):
        fl,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p)
        name=utf(ni); desc=utf(di)
        ac,p=u2(data,p); code=None
        for __ in range(ac):
            ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai); payload=data[p:p+ln]; p+=ln
            if an=="Code":
                q=0; _,q=u2(payload,q); _,q=u2(payload,q); clen,q=u4(payload,q)
                code=payload[q:q+clen]
        methods.append({"name":name,"descriptor":desc,"flags":fl,"code":code})

    def cpref(idx):
        e=cp[idx] if idx<len(cp) else None
        if not e or e[0] not in (9,10,11): return None
        owner=cls(e[1]); name,desc=nt(e[2])
        return {"kind":{9:"field",10:"method",11:"interface_method"}[e[0]],"owner":owner,"name":name,"descriptor":desc}
    return {"name":cls(this_i),"super":cls(super_i),"interfaces":interfaces,"fields":fields,"methods":methods,"cpref":cpref}

runtime={}
with zipfile.ZipFile(RUNTIME) as z:
    for n in z.namelist():
        if n.endswith(".class") and not n.startswith("META-INF/"):
            x=parse_class(z.read(n)); runtime[x["name"]]=x

app={}
for pth in BUILD.rglob("*.class"):
    x=parse_class(pth.read_bytes()); app[x["name"]]=x

defs={**runtime,**app}
def has_member(kind,owner,name,desc,seen=None):
    if seen is None: seen=set()
    if not owner or owner in seen: return False
    seen.add(owner); c=defs.get(owner)
    if not c: return None  # external/JDK inheritance not modeled here
    rows=c["fields"] if kind=="field" else [(m["name"],m["descriptor"],m["flags"]) for m in c["methods"]]
    if any(n==name and d==desc for n,d,_ in rows): return True
    if name=="<init>": return False
    results=[]
    if c["super"]: results.append(has_member(kind,c["super"],name,desc,seen))
    for i in c["interfaces"]: results.append(has_member(kind,i,name,desc,seen))
    if True in results: return True
    if None in results: return None
    return False

rows=[]
for owner,c in app.items():
    for m in c["methods"]:
        if not m["code"]: continue
        for off,op,idx in walk_code(m["code"]):
            ref=c["cpref"](idx)
            if not ref or not ref["owner"] or not ref["owner"].startswith("l1rpb/"): continue
            status=has_member("field" if ref["kind"]=="field" else "method",ref["owner"],ref["name"],ref["descriptor"])
            if status is True: continue
            rows.append({
              "source_class":owner,
              "source_method":m["name"],
              "source_descriptor":m["descriptor"],
              "source_flags":m["flags"],
              "source_bridge":bool(m["flags"] & ACC_BRIDGE),
              "source_synthetic":bool(m["flags"] & ACC_SYNTHETIC),
              "bytecode_offset":off,
              "resolution":"EXTERNAL_CHAIN" if status is None else "UNRESOLVED_EXACT_RUNTIME",
              **ref,
            })

counts=Counter((r["resolution"],r["source_bridge"],r["source_synthetic"]) for r in rows)
by_target=Counter((r["resolution"],r["owner"],r["name"],r["descriptor"]) for r in rows)
non_bridge=[r for r in rows if r["resolution"]=="UNRESOLVED_EXACT_RUNTIME" and not (r["source_bridge"] or r["source_synthetic"])]
bridge=[r for r in rows if r["resolution"]=="UNRESOLVED_EXACT_RUNTIME" and (r["source_bridge"] or r["source_synthetic"])]
external=[r for r in rows if r["resolution"]=="EXTERNAL_CHAIN"]

state={
 "gate":"SOURCE_ONLY_EXACT_RUNTIME_LINKAGE_CLASSIFICATION",
 "application_classes":len(app),
 "exact_runtime_classes":len(runtime),
 "bytecode_unresolved_or_external_refs":len(rows),
 "unresolved_exact_runtime_refs":len(non_bridge)+len(bridge),
 "unresolved_from_bridge_or_synthetic_methods":len(bridge),
 "unresolved_from_non_bridge_methods":len(non_bridge),
 "external_chain_refs":len(external),
 "classification_counts":[{"resolution":k[0],"bridge":k[1],"synthetic":k[2],"count":v} for k,v in sorted(counts.items())],
 "top_targets":[{"resolution":k[0],"owner":k[1],"name":k[2],"descriptor":k[3],"count":v} for k,v in by_target.most_common(100)],
 "non_bridge_unresolved_sample":non_bridge[:300],
 "bridge_unresolved_sample":bridge[:300],
 "external_chain_sample":external[:100],
 "note":"EXTERNAL_CHAIN means resolution leaves the application/exact-protobuf universe (for example into java/**); it is not counted as a proven compile-view-only dependency."
}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
