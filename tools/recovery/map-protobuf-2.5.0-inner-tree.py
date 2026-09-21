#!/usr/bin/env python3
import json, re, struct, sys, zipfile
from collections import defaultdict, Counter
from pathlib import Path

DONOR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
OFFICIAL=Path(sys.argv[1])
OUT=Path("recovery/protobuf_2_5_0_inner_tree_mapping.json")
OBJ=re.compile(r"L([^;<]+)")

def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4
def parse(data):
    p=0; magic,p=u4(data,p)
    if magic!=0xCAFEBABE: raise ValueError("bad")
    minor,p=u2(data,p); major,p=u2(data,p)
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

    access,p=u2(data,p); thisi,p=u2(data,p); superi,p=u2(data,p)
    ic,p=u2(data,p); ifs=[]
    for _ in range(ic):
        x,p=u2(data,p); ifs.append(cls(x))

    def mem(kind):
        nonlocal p
        fl,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p); ac,p=u2(data,p)
        code=False
        for _ in range(ac):
            ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai)
            if kind=="m" and an=="Code": code=True
            p+=ln
        return (fl,utf(ni),utf(di),code)

    fc,p=u2(data,p); fs=[mem("f") for _ in range(fc)]
    mc,p=u2(data,p); ms=[mem("m") for _ in range(mc)]

    ac,p=u2(data,p); sf=None; inn=[]
    for _ in range(ac):
        ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai); pay=data[p:p+ln]
        if an=="SourceFile" and ln==2:
            si,_=u2(pay,0); sf=utf(si)
        elif an=="InnerClasses":
            q=0; n,q=u2(pay,q)
            for __ in range(n):
                ii,q=u2(pay,q); oi,q=u2(pay,q); ni,q=u2(pay,q); fl,q=u2(pay,q)
                inn.append((cls(ii),cls(oi),utf(ni),fl))
        p+=ln

    outer=None; inner_name=None; inner_flags=None
    for ii,oi,ni,fl in inn:
        if ii==cls(thisi):
            outer=oi; inner_name=ni; inner_flags=fl
            break

    return {"name":cls(thisi),"super":cls(superi),"interfaces":ifs,"fields":fs,"methods":ms,
            "source":sf,"access":access,"major":major,"minor":minor,
            "outer":outer,"inner_name":inner_name,"inner_flags":inner_flags}

def load(path):
    out={}
    with zipfile.ZipFile(path) as z:
        for n in z.namelist():
            if n.endswith(".class") and not n.startswith("META-INF/"):
                x=parse(z.read(n)); out[x["name"]]=x
    return out

D=load(DONOR); O=load(OFFICIAL)
DSET=set(D); OSET=set(O)

def dshape(desc): return OBJ.sub("L?;",desc or "")
def sig(x):
    return (
      x["source"],x["major"],x["minor"],x["access"]&0x7631,
      len(x["interfaces"]),len(x["fields"]),len(x["methods"]),
      sum(1 for *_,code in x["methods"] if code),
      tuple(sorted((f&0x50df,dshape(d)) for f,n,d,c in x["fields"])),
      tuple(sorted((f&0x1dff,dshape(d),c) for f,n,d,c in x["methods"])),
      x["inner_flags"],
    )

Dchildren=defaultdict(list); Ochildren=defaultdict(list)
for n,x in D.items(): Dchildren[x["outer"]].append(n)
for n,x in O.items(): Ochildren[x["outer"]].append(n)

mapping={}
# Top-level: SourceFile is unique across the 45 runtime source files.
Dtop=defaultdict(list); Otop=defaultdict(list)
for n,x in D.items():
    if x["outer"] is None: Dtop[x["source"]].append(n)
for n,x in O.items():
    if x["outer"] is None: Otop[x["source"]].append(n)
for sf in set(Dtop)|set(Otop):
    if len(Dtop.get(sf,[]))==1 and len(Otop.get(sf,[]))==1:
        mapping[Dtop[sf][0]]=Otop[sf][0]

levels=[]
for depth in range(12):
    added=[]
    for dp,op in list(mapping.items()):
        dcs=[n for n in Dchildren.get(dp,[]) if n not in mapping]
        used=set(mapping.values())
        ocs=[n for n in Ochildren.get(op,[]) if n not in used]
        if not dcs and not ocs: continue

        dg=defaultdict(list); og=defaultdict(list)
        for n in dcs: dg[sig(D[n])].append(n)
        for n in ocs: og[sig(O[n])].append(n)
        for k in set(dg)&set(og):
            if len(dg[k])==1 and len(og[k])==1:
                mapping[dg[k][0]]=og[k][0]
                added.append((dg[k][0],og[k][0]))
    levels.append({"depth":depth+1,"added":len(added),"mapped":len(mapping)})
    if not added: break

used=set(mapping.values())
remD=[n for n in D if n not in mapping]
remO=[n for n in O if n not in used]

# Summarize unresolved children under mapped parents.
unresolved=[]
for dp,op in sorted(mapping.items()):
    dcs=sorted(n for n in Dchildren.get(dp,[]) if n not in mapping)
    ocs=sorted(n for n in Ochildren.get(op,[]) if n not in used)
    if dcs or ocs:
        unresolved.append({"donor_parent":dp,"official_parent":op,"donor_children":dcs,"official_children":ocs})

state={"gate":"PROTOBUF_2_5_0_INNER_TREE_MAPPING","donor":len(D),"official":len(O),
       "top_level_mapped":sum(1 for d,o in mapping.items() if D[d]["outer"] is None),
       "levels":levels,"mapped":len(mapping),"unmapped_donor":len(remD),"unmapped_official":len(remO),
       "one_to_one":len(mapping)==len(set(mapping.values())),
       "mapping":dict(sorted(mapping.items())),"unresolved":unresolved}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:v for k,v in state.items() if k not in ("mapping","unresolved")},indent=2))
print("UNRESOLVED="+json.dumps(unresolved,indent=2))
