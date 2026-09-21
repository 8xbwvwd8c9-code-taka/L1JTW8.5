#!/usr/bin/env python3
import json, re, struct, sys, zipfile
from collections import defaultdict, Counter
from pathlib import Path

DONOR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
OFFICIAL=Path(sys.argv[1])
OUT=Path("recovery/protobuf_2_5_0_graph_mapping.json")
OBJ_RE=re.compile(r"L([^;<]+)")

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
    for _ in range(ic): x,p=u2(data,p); ifs.append(cls(x))
    def mem():
        nonlocal p
        fl,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p); ac,p=u2(data,p)
        for _ in range(ac): _,p=u2(data,p); ln,p=u4(data,p); p+=ln
        return (fl,utf(ni),utf(di))
    fc,p=u2(data,p); fs=[mem() for _ in range(fc)]
    mc,p=u2(data,p); ms=[mem() for _ in range(mc)]
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
    return {"name":cls(thisi),"super":cls(superi),"interfaces":ifs,"fields":fs,"methods":ms,
            "source":sf,"access":access,"major":major,"minor":minor,"inners":inn}

def load(path):
    d={}
    with zipfile.ZipFile(path) as z:
        for n in z.namelist():
            if n.endswith(".class") and not n.startswith("META-INF/"):
                x=parse(z.read(n)); d[x["name"]]=x
    return d

D=load(DONOR); O=load(OFFICIAL)
DSET=set(D); OSET=set(O)

def extcat(n, internal):
    if n is None: return ("NONE",)
    if n in internal: return ("INTERNAL",)
    # external protobuf references should not exist outside set; JDK names can remain exact
    return ("EXT",n)

def desc_parts(desc, internal):
    refs=[]
    for n in OBJ_RE.findall(desc or ""):
        refs.append(("INTERNAL",) if n in internal else ("EXT",n))
    shape=OBJ_RE.sub("L?;",desc or "")
    return shape,tuple(refs)

def base(x, internal):
    fshape=sorted((fl & 0x50df,)+desc_parts(de,internal) for fl,_,de in x["fields"])
    mshape=sorted((fl & 0x1dff,)+desc_parts(de,internal) for fl,_,de in x["methods"])
    self_inner=sum(1 for ii,oi,ni,fl in x["inners"] if ii==x["name"])
    self_outer=sum(1 for ii,oi,ni,fl in x["inners"] if oi==x["name"])
    return (x["source"],x["major"],x["minor"],x["access"]&0x7631,
            len(x["interfaces"]),len(x["fields"]),len(x["methods"]),
            tuple(fshape),tuple(mshape),self_inner,self_outer)

# shared initial color based only name-insensitive features
features=sorted(set(base(x,DSET) for x in D.values())|set(base(x,OSET) for x in O.values()),key=str)
cmap={v:i for i,v in enumerate(features)}
DC={n:cmap[base(x,DSET)] for n,x in D.items()}
OC={n:cmap[base(x,OSET)] for n,x in O.items()}

def refs_for(x, internal, colors):
    rel=[]
    def add(kind,n):
        if n is None: rel.append((kind,"NONE")); return
        if n in internal: rel.append((kind,"I",colors[n]))
        else: rel.append((kind,"E",n))
    add("S",x["super"])
    for n in x["interfaces"]: add("I",n)
    for fl,na,de in x["fields"]:
        for n in OBJ_RE.findall(de or ""): add("F",n)
    for fl,na,de in x["methods"]:
        for n in OBJ_RE.findall(de or ""): add("M",n)
    # only relationships involving this class in InnerClasses table
    for ii,oi,ni,fl in x["inners"]:
        if ii==x["name"]: add("OUTER",oi)
        if oi==x["name"]: add("CHILD",ii)
    return tuple(sorted(rel,key=str))

iters=[]
for it in range(12):
    ds={n:(DC[n],refs_for(x,DSET,DC)) for n,x in D.items()}
    os={n:(OC[n],refs_for(x,OSET,OC)) for n,x in O.items()}
    vals=sorted(set(ds.values())|set(os.values()),key=str)
    mp={v:i for i,v in enumerate(vals)}
    nd={n:mp[v] for n,v in ds.items()}; no={n:mp[v] for n,v in os.items()}
    changed=sum(DC[n]!=nd[n] for n in D)+sum(OC[n]!=no[n] for n in O)
    DC,OC=nd,no
    iters.append({"iteration":it+1,"colors":len(vals),"changed":changed})
    if changed==0: break

dg=defaultdict(list); og=defaultdict(list)
for n,c in DC.items(): dg[c].append(n)
for n,c in OC.items(): og[c].append(n)
mapping=[]; ambiguous=[]; mismatch=[]
for c in sorted(set(dg)|set(og)):
    ds=sorted(dg.get(c,[])); os=sorted(og.get(c,[]))
    if len(ds)==1 and len(os)==1:
        mapping.append({"donor":ds[0],"official":os[0],"color":c})
    elif len(ds)==len(os):
        ambiguous.append({"color":c,"donor":ds,"official":os})
    else:
        mismatch.append({"color":c,"donor":ds,"official":os})
state={"gate":"PROTOBUF_2_5_0_GRAPH_MAPPING","donor":len(D),"official":len(O),
       "iterations":iters,"mapped":len(mapping),"ambiguous_groups":len(ambiguous),
       "ambiguous_donor":sum(len(x["donor"]) for x in ambiguous),
       "mismatch_groups":len(mismatch),
       "mapping":mapping,"ambiguous":ambiguous,"mismatch":mismatch}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:v for k,v in state.items() if k not in ("mapping","ambiguous","mismatch")},indent=2))
print("AMBIGUOUS="+json.dumps(ambiguous,indent=2))
print("MISMATCH="+json.dumps(mismatch,indent=2))
