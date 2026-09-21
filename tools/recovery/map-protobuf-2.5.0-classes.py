#!/usr/bin/env python3
import json, struct, sys, zipfile
from collections import defaultdict, Counter
from pathlib import Path

DONOR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
OFFICIAL=Path(sys.argv[1]) if len(sys.argv)>1 else Path("protobuf-java-2.5.0.jar")
OUT=Path("recovery/protobuf_2_5_0_class_mapping.json")

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
    access,p=u2(data,p); this_i,p=u2(data,p); super_i,p=u2(data,p)
    ic,p=u2(data,p); interfaces=[]
    for _ in range(ic):
        x,p=u2(data,p); interfaces.append(cls(x))
    def member(kind):
        nonlocal p
        flags,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p); ac,p=u2(data,p)
        code=None
        for _ in range(ac):
            ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai)
            if kind=="method" and an=="Code":
                q=p
                ms,q=u2(data,q); ml,q=u2(data,q); clen,q=u4(data,q); q+=clen
                exn,q=u2(data,q); q+=8*exn; sub,q=u2(data,q)
                code=(ms,ml,clen,exn,sub)
            p+=ln
        return {"flags":flags,"name":utf(ni),"desc":utf(di),"code":code}
    fc,p=u2(data,p); fields=[member("field") for _ in range(fc)]
    mc,p=u2(data,p); methods=[member("method") for _ in range(mc)]
    ac,p=u2(data,p); sf=None; inners=[]
    for _ in range(ac):
        ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai); payload=data[p:p+ln]
        if an=="SourceFile" and ln==2:
            si,_=u2(payload,0); sf=utf(si)
        elif an=="InnerClasses" and ln>=2:
            q=0; n,q=u2(payload,q)
            for __ in range(n):
                ii,q=u2(payload,q); oi,q=u2(payload,q); ni,q=u2(payload,q); fl,q=u2(payload,q)
                inners.append((cls(ii),cls(oi),utf(ni),fl))
        p+=ln
    return {"name":cls(this_i),"super":cls(super_i),"access":access,"major":major,"minor":minor,
            "interfaces":interfaces,"fields":fields,"methods":methods,"source_file":sf,"inners":inners}

def read(path):
    out=[]
    with zipfile.ZipFile(path) as z:
        for n in sorted(z.namelist()):
            if n.endswith(".class") and not n.startswith("META-INF/"):
                x=parse(z.read(n)); x["path"]=n; out.append(x)
    return out

d=read(DONOR); o=read(OFFICIAL)
def key(x):
    return (x["source_file"],x["major"],x["minor"],x["access"] & 0x7631,
            len(x["interfaces"]),len(x["fields"]),len(x["methods"]),
            sum(m["code"] is not None for m in x["methods"]))
def desc_shape(desc):
    if not desc: return desc
    # erase object identities but preserve primitive/array/object structure and arity
    out=[]; i=0
    while i<len(desc):
        ch=desc[i]
        if ch=="L":
            j=desc.find(";",i)
            if j<0: out.append("L?;"); break
            out.append("L?;"); i=j+1
        else:
            out.append(ch); i+=1
    return "".join(out)
def rich(x):
    return key(x)+(
      tuple(sorted((f["flags"] & 0x50df,desc_shape(f["desc"])) for f in x["fields"])),
      tuple(sorted((m["flags"] & 0x1dff,desc_shape(m["desc"]),bool(m["code"])) for m in x["methods"])),
      len(x["inners"]),
    )

dg=defaultdict(list); og=defaultdict(list)
for x in d: dg[key(x)].append(x)
for x in o: og[key(x)].append(x)

mapping=[]; ambiguous=[]
for k in sorted(set(dg)|set(og),key=str):
    ds=dg.get(k,[]); os=og.get(k,[])
    if len(ds)==1 and len(os)==1:
        mapping.append((ds[0],os[0],"basic_unique"))
        continue
    dr=defaultdict(list); orr=defaultdict(list)
    for x in ds: dr[rich(x)].append(x)
    for x in os: orr[rich(x)].append(x)
    usedd=set(); usedo=set()
    for rk in set(dr)&set(orr):
        if len(dr[rk])==1 and len(orr[rk])==1:
            a=dr[rk][0]; b=orr[rk][0]
            mapping.append((a,b,"rich_unique")); usedd.add(a["name"]); usedo.add(b["name"])
    rd=[x for x in ds if x["name"] not in usedd]
    ro=[x for x in os if x["name"] not in usedo]
    if rd or ro:
        ambiguous.append({"key":str(k),"donor":[x["name"] for x in rd],"official":[x["name"] for x in ro]})

rows=[]
code_equal=0; code_diff=0
for a,b,why in mapping:
    ac=sorted(m["code"] for m in a["methods"] if m["code"] is not None)
    bc=sorted(m["code"] for m in b["methods"] if m["code"] is not None)
    eq=ac==bc
    code_equal+=eq; code_diff+=not eq
    rows.append({"donor":a["name"],"official":b["name"],"source_file":a["source_file"],
                 "method":why,"code_shape_equal":eq})
state={"gate":"PROTOBUF_2_5_0_CLASS_MAPPING","donor_classes":len(d),"official_classes":len(o),
       "mapped":len(rows),"ambiguous_groups":len(ambiguous),
       "ambiguous_donor_classes":sum(len(x["donor"]) for x in ambiguous),
       "ambiguous_official_classes":sum(len(x["official"]) for x in ambiguous),
       "mapped_code_shape_equal":code_equal,"mapped_code_shape_diff":code_diff,
       "mapping":rows,"ambiguous":ambiguous}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:v for k,v in state.items() if k not in ("mapping","ambiguous")},indent=2))
print("AMBIGUOUS="+json.dumps(ambiguous,indent=2))
