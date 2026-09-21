#!/usr/bin/env python3
import json, re, struct, zipfile
from collections import Counter, defaultdict
from pathlib import Path

BUILD=Path("recovery/normalized-stage-build")
OFFICIAL=Path("lib/protobuf-java-2.5.0.jar")
DONOR=Path("recovery/compile-ref-protobuf-l1rpb-pristine.jar")
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
    def mem(kind):
        nonlocal p
        fl,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p); ac,p=u2(data,p)
        code_len=None; code_bytes=None; exn=0
        for _ in range(ac):
            ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai)
            if kind=="method" and an=="Code":
                q=p; _,q=u2(data,q); _,q=u2(data,q); code_len,q=u4(data,q)
                code_bytes=data[q:q+code_len]; q+=code_len
                exn,q=u2(data,q)
            p+=ln
        return {"flags":fl,"name":utf(ni),"desc":utf(di),"code_len":code_len,"code":code_bytes,"exn":exn}
    fc,p=u2(data,p); fs=[mem("field") for _ in range(fc)]
    mc,p=u2(data,p); ms=[mem("method") for _ in range(mc)]
    return cls(thisi),fs,ms

# Compiler-insensitive opcode categories used only as a disambiguator.
FIXED={
0x10:1,0x11:2,0x12:1,0x13:2,0x14:2,0x15:1,0x16:1,0x17:1,0x18:1,0x19:1,
0x36:1,0x37:1,0x38:1,0x39:1,0x3a:1,0x84:2,
0x99:2,0x9a:2,0x9b:2,0x9c:2,0x9d:2,0x9e:2,0x9f:2,0xa0:2,0xa1:2,0xa2:2,0xa3:2,0xa4:2,0xa5:2,0xa6:2,
0xa7:2,0xa8:2,0xa9:1,0xb2:2,0xb3:2,0xb4:2,0xb5:2,0xb6:2,0xb7:2,0xb8:2,0xb9:4,0xba:4,
0xbb:2,0xbc:1,0xbd:2,0xc0:2,0xc1:2,0xc5:3,0xc6:2,0xc7:2,0xc8:4,0xc9:4}
LOCAL={}
for base,s in [(0x15,0x1a),(0x16,0x1e),(0x17,0x22),(0x18,0x26),(0x19,0x2a),(0x36,0x3b),(0x37,0x3f),(0x38,0x43),(0x39,0x47),(0x3a,0x4b)]:
    LOCAL[base]=base
    for x in range(s,s+4): LOCAL[x]=base
CONST=set(range(0x01,0x10))|{0x10,0x11,0x12,0x13,0x14}
def opcode_shape(code):
    if code is None: return None
    out=[]; p=0
    while p<len(code):
        op=code[p]; p+=1
        if op==0xaa:
            out.append("SWITCH"); p+=(-p)%4
            if p+12>len(code): break
            _,lo,hi=struct.unpack_from(">iii",code,p); p+=12+4*max(0,hi-lo+1)
        elif op==0xab:
            out.append("SWITCH"); p+=(-p)%4
            if p+8>len(code): break
            _,np=struct.unpack_from(">ii",code,p); p+=8+8*max(0,np)
        elif op==0xc4:
            if p>=len(code): break
            sub=code[p]; p+=1; out.append(("LOCAL",LOCAL.get(sub,sub))); p+=4 if sub==0x84 else 2
        else:
            if op in CONST: out.append("CONST")
            elif op in LOCAL: out.append(("LOCAL",LOCAL[op]))
            elif op in (0xb2,0xb3,0xb4,0xb5): out.append("FIELD")
            elif op in (0xb6,0xb7,0xb8,0xb9,0xba): out.append("INVOKE")
            elif op in set(range(0x99,0xa9))|{0xc6,0xc7,0xc8,0xc9}: out.append("BRANCH")
            else: out.append(op)
            p+=FIXED.get(op,0)
    return tuple(out)

official={}
with zipfile.ZipFile(OFFICIAL) as z:
    for n in z.namelist():
        if n.endswith(".class") and not n.startswith("META-INF/"):
            cn,fs,ms=parse_members(z.read(n)); official[cn]={"fields":fs,"methods":ms}
donor={}
with zipfile.ZipFile(DONOR) as z:
    for n in z.namelist():
        if n.endswith(".class") and not n.startswith("META-INF/"):
            cn,fs,ms=parse_members(z.read(n)); donor[cn]={"fields":fs,"methods":ms}

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
    dmember=None
    if owner in donor:
        pool=donor[owner]["fields" if kind=="field" else "methods"]
        dm=[m for m in pool if m["name"]==name and m["desc"]==desc]
        if len(dm)==1: dmember=dm[0]
    if off_owner in official:
        for m in official[off_owner]["fields" if kind=="field" else "methods"]:
            if m["desc"]!=tdesc: continue
            # Preserve staticness and broad linkage semantics when donor member is known.
            if dmember is not None:
                if bool(m["flags"]&0x0008)!=bool(dmember["flags"]&0x0008): continue
                if kind=="method":
                    for bit in (0x0400,0x0100,0x0020): # abstract/native/synchronized
                        if bool(m["flags"]&bit)!=bool(dmember["flags"]&bit): break
                    else:
                        pass
                    if any(bool(m["flags"]&bit)!=bool(dmember["flags"]&bit) for bit in (0x0400,0x0100,0x0020)):
                        continue
            cands.append({"name":m["name"],"desc":m["desc"],"flags":m["flags"],"static":bool(m["flags"]&0x0008),
                          "opcode_shape_match": (kind=="method" and dmember is not None and opcode_shape(dmember["code"])==opcode_shape(m["code"]))})
    # Exact preserved names are authoritative when descriptor/static shape already match.
    exact=[x for x in cands if x["name"]==name]
    if len(exact)==1:
        cands=exact
    elif kind=="method" and dmember is not None:
        shaped=[x for x in cands if x.get("opcode_shape_match")]
        if len(shaped)==1: cands=shaped
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
