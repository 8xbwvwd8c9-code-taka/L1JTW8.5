#!/usr/bin/env python3
import json, re, struct, sys, zipfile
from collections import defaultdict, Counter
from pathlib import Path

DONOR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
OFFICIAL=Path(sys.argv[1])
OUT=Path("recovery/protobuf_2_5_0_mapping_closure.json")
OBJ=re.compile(r"L([^;<]+)")

def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4
def parse(data):
    p=0; magic,p=u4(data,p)
    if magic!=0xCAFEBABE: raise ValueError("bad class")
    minor,p=u2(data,p); major,p=u2(data,p)
    cpc,p=u2(data,p); cp=[None]*cpc; i=1
    while i<cpc:
        tag,p=u1(data,p)
        if tag==1:
            n,p=u2(data,p); cp[i]=(tag,data[p:p+n].decode("utf-8","replace")); p+=n
        elif tag==3:
            v=struct.unpack_from(">i",data,p)[0]; p+=4; cp[i]=(tag,v)
        elif tag==4:
            v=struct.unpack_from(">I",data,p)[0]; p+=4; cp[i]=(tag,v)
        elif tag==5:
            v=struct.unpack_from(">q",data,p)[0]; p+=8; cp[i]=(tag,v); i+=1
        elif tag==6:
            v=struct.unpack_from(">Q",data,p)[0]; p+=8; cp[i]=(tag,v); i+=1
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
    def member(kind):
        nonlocal p
        fl,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p); ac,p=u2(data,p)
        code_len=None; exn=None
        for _ in range(ac):
            ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai)
            if kind=="m" and an=="Code":
                q=p; _,q=u2(data,q); _,q=u2(data,q); code_len,q=u4(data,q); q+=code_len
                exn,q=u2(data,q)
            p+=ln
        return {"flags":fl,"name":utf(ni),"desc":utf(di),"code_len":code_len,"exn":exn}
    fc,p=u2(data,p); fs=[member("f") for _ in range(fc)]
    mc,p=u2(data,p); ms=[member("m") for _ in range(mc)]
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
    strings=[]; nums=[]
    for e in cp:
        if not e: continue
        if e[0]==8:
            s=utf(e[1])
            if s is not None: strings.append(s)
        elif e[0] in (3,4,5,6): nums.append((e[0],e[1]))
    return {"name":cls(thisi),"super":cls(superi),"interfaces":ifs,"fields":fs,"methods":ms,
            "source":sf,"access":access,"major":major,"minor":minor,"inners":inn,
            "strings":Counter(strings),"nums":Counter(nums)}

def load(path):
    out={}
    with zipfile.ZipFile(path) as z:
        for n in z.namelist():
            if n.endswith(".class") and not n.startswith("META-INF/"):
                x=parse(z.read(n)); out[x["name"]]=x
    return out

D=load(DONOR); O=load(OFFICIAL); DSET=set(D); OSET=set(O)

def dshape(desc):
    return OBJ.sub("L?;",desc or "")
def initial(x):
    return (x["source"],x["major"],x["minor"],x["access"]&0x7631,
            len(x["interfaces"]),len(x["fields"]),len(x["methods"]),
            tuple(sorted((f["flags"]&0x50df,dshape(f["desc"])) for f in x["fields"])),
            tuple(sorted((m["flags"]&0x1dff,dshape(m["desc"]),m["code_len"] is not None) for m in x["methods"])),
            tuple(sorted(x["strings"].items())),tuple(sorted(x["nums"].items())),
            sum(1 for ii,oi,ni,fl in x["inners"] if ii==x["name"]),
            sum(1 for ii,oi,ni,fl in x["inners"] if oi==x["name"]))

# seed unique matches by name-insensitive class-local structure.
dg=defaultdict(list); og=defaultdict(list)
for n,x in D.items(): dg[initial(x)].append(n)
for n,x in O.items(): og[initial(x)].append(n)
mapping={}
for k in set(dg)&set(og):
    if len(dg[k])==1 and len(og[k])==1:
        mapping[dg[k][0]]=og[k][0]

def mapped_ref(n, side):
    if n is None: return ("NONE",)
    if side=="D":
        if n in mapping: return ("M",mapping[n])
        return ("I",) if n in DSET else ("E",n)
    else:
        # official references can be compared directly if some donor maps to them.
        return ("M",n) if n in set(mapping.values()) else (("I",) if n in OSET else ("E",n))

def relsig(x,side):
    rel=[]
    rel.append(("S",mapped_ref(x["super"],side)))
    for n in x["interfaces"]: rel.append(("I",mapped_ref(n,side)))
    for f in x["fields"]:
        refs=tuple(sorted(mapped_ref(n,side) for n in OBJ.findall(f["desc"] or "")))
        rel.append(("F",f["flags"]&0x50df,dshape(f["desc"]),refs))
    for m in x["methods"]:
        refs=tuple(sorted(mapped_ref(n,side) for n in OBJ.findall(m["desc"] or "")))
        rel.append(("M",m["flags"]&0x1dff,dshape(m["desc"]),refs,m["code_len"] is not None))
    for ii,oi,ni,fl in x["inners"]:
        if ii==x["name"]: rel.append(("OUTER",mapped_ref(oi,side),fl))
        if oi==x["name"]: rel.append(("CHILD",mapped_ref(ii,side),fl))
    return tuple(sorted(rel,key=str))

iters=[]
for it in range(20):
    usedO=set(mapping.values())
    remD=[n for n in D if n not in mapping]
    remO=[n for n in O if n not in usedO]
    dgroups=defaultdict(list); ogroups=defaultdict(list)
    for n in remD:
        dgroups[(initial(D[n]),relsig(D[n],"D"))].append(n)
    for n in remO:
        ogroups[(initial(O[n]),relsig(O[n],"O"))].append(n)
    added=[]
    for k in set(dgroups)&set(ogroups):
        if len(dgroups[k])==1 and len(ogroups[k])==1:
            dn=dgroups[k][0]; on=ogroups[k][0]
            mapping[dn]=on; added.append((dn,on))
    iters.append({"iteration":it+1,"added":len(added),"mapped":len(mapping)})
    if not added: break

usedO=set(mapping.values())
remD=[n for n in D if n not in mapping]; remO=[n for n in O if n not in usedO]
# summarize remaining by SourceFile.
byD=defaultdict(list); byO=defaultdict(list)
for n in remD: byD[D[n]["source"]].append(n)
for n in remO: byO[O[n]["source"]].append(n)
remaining=[]
for sf in sorted(set(byD)|set(byO),key=str):
    remaining.append({"source_file":sf,"donor":sorted(byD[sf]),"official":sorted(byO[sf])})

state={"gate":"PROTOBUF_2_5_0_MAPPING_CLOSURE","donor":len(D),"official":len(O),
       "seed_mapped":iters[0]["mapped"]-iters[0]["added"] if iters else len(mapping),
       "iterations":iters,"mapped":len(mapping),
       "unmapped_donor":len(remD),"unmapped_official":len(remO),
       "one_to_one":len(mapping)==len(set(mapping.values())),
       "mapping":dict(sorted(mapping.items())),
       "remaining":remaining}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:v for k,v in state.items() if k not in ("mapping","remaining")},indent=2))
print("REMAINING="+json.dumps(remaining,indent=2))
