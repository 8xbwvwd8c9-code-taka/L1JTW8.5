#!/usr/bin/env python3
import json,re,struct,sys,zipfile
from collections import defaultdict,Counter
from pathlib import Path

DONOR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
OFFICIAL=Path(sys.argv[1])
MAP=Path("recovery/protobuf_2_5_0_order_mapping.json")
OUT=Path("recovery/protobuf_2_5_0_member_mapping.json")
OBJ=re.compile(r"L([^;]+);")

def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4

def parse(data):
    p=0; magic,p=u4(data,p)
    if magic!=0xCAFEBABE: raise ValueError("bad")
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
    def member(kind):
        nonlocal p
        fl,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p); ac,p=u2(data,p)
        code=False; exn=0; code_len=None
        for _ in range(ac):
            ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai)
            if kind=="method" and an=="Code":
                q=p; _,q=u2(data,q); _,q=u2(data,q); code_len,q=u4(data,q); q+=code_len
                exn,q=u2(data,q); code=True
            p+=ln
        return {"name":utf(ni),"desc":utf(di),"flags":fl,"code":code,"code_len":code_len,"exn":exn}
    fc,p=u2(data,p); fields=[member("field") for _ in range(fc)]
    mc,p=u2(data,p); methods=[member("method") for _ in range(mc)]
    return {"name":cls(thisi),"fields":fields,"methods":methods}

def load(path):
    out={}
    with zipfile.ZipFile(path) as z:
        for n in z.namelist():
            if n.endswith(".class") and not n.startswith("META-INF/"):
                x=parse(z.read(n)); out[x["name"]]=x
    return out

D=load(DONOR); O=load(OFFICIAL)
state=json.loads(MAP.read_text(encoding="utf-8"))
CMAP=state["mapping"]
if len(CMAP)!=246 or len(set(CMAP.values()))!=246:
    raise SystemExit("class mapping not closed 246/246")

def trans_desc(desc):
    def repl(m):
        n=m.group(1)
        return "L"+CMAP.get(n,n)+";"
    return OBJ.sub(repl,desc)

# compare only linkage-relevant flags; synthetic/bridge kept as discriminators too
FIELD_MASK=0x50df
METHOD_MASK=0x1dff | 0x0040 | 0x1000

rows=[]; ambiguous=[]; missing=[]
field_total=method_total=field_unique=method_unique=0
for dn,on in CMAP.items():
    dx=D[dn]; ox=O[on]
    # fields
    og=defaultdict(list)
    for m in ox["fields"]:
        og[(m["desc"],m["flags"]&FIELD_MASK)].append(m)
    for m in dx["fields"]:
        field_total+=1
        k=(trans_desc(m["desc"]),m["flags"]&FIELD_MASK)
        cs=og.get(k,[])
        if len(cs)==1:
            field_unique+=1
            rows.append({"kind":"field","donor_class":dn,"donor_name":m["name"],"donor_desc":m["desc"],
                         "official_class":on,"official_name":cs[0]["name"],"official_desc":cs[0]["desc"],
                         "match":"descriptor_flags_unique"})
        elif not cs:
            missing.append({"kind":"field","class":dn,"official_class":on,"name":m["name"],"desc":m["desc"],"flags":m["flags"]})
        else:
            ambiguous.append({"kind":"field","class":dn,"official_class":on,"name":m["name"],"desc":m["desc"],
                              "candidates":[{"name":x["name"],"desc":x["desc"],"flags":x["flags"]} for x in cs]})
    # methods
    og=defaultdict(list)
    for m in ox["methods"]:
        og[(m["desc"],m["flags"]&METHOD_MASK,m["code"],m["exn"])].append(m)
    for m in dx["methods"]:
        method_total+=1
        k=(trans_desc(m["desc"]),m["flags"]&METHOD_MASK,m["code"],m["exn"])
        cs=og.get(k,[])
        if len(cs)==1:
            method_unique+=1
            rows.append({"kind":"method","donor_class":dn,"donor_name":m["name"],"donor_desc":m["desc"],
                         "official_class":on,"official_name":cs[0]["name"],"official_desc":cs[0]["desc"],
                         "match":"descriptor_flags_code_exception_unique"})
        elif not cs:
            missing.append({"kind":"method","class":dn,"official_class":on,"name":m["name"],"desc":m["desc"],
                            "flags":m["flags"],"code":m["code"],"exn":m["exn"]})
        else:
            ambiguous.append({"kind":"method","class":dn,"official_class":on,"name":m["name"],"desc":m["desc"],
                              "candidates":[{"name":x["name"],"desc":x["desc"],"flags":x["flags"],"code":x["code"],"exn":x["exn"]} for x in cs]})

out={
 "gate":"PROTOBUF_2_5_0_MEMBER_MAPPING",
 "classes":len(CMAP),
 "field_total":field_total,"field_unique":field_unique,
 "method_total":method_total,"method_unique":method_unique,
 "unique_total":field_unique+method_unique,
 "member_total":field_total+method_total,
 "ambiguous":len(ambiguous),"missing":len(missing),
 "class_mapping_one_to_one":True,
 "rows":rows,"ambiguous_rows":ambiguous,"missing_rows":missing
}
OUT.write_text(json.dumps(out,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:v for k,v in out.items() if k not in ("rows","ambiguous_rows","missing_rows")},indent=2))
print("AMBIGUOUS_SAMPLE="+json.dumps(ambiguous[:60],indent=2))
print("MISSING_SAMPLE="+json.dumps(missing[:60],indent=2))
