#!/usr/bin/env python3
import json, struct, sys, zipfile
from collections import defaultdict, Counter
from pathlib import Path

DONOR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
OFFICIAL=Path(sys.argv[1])
OUT=Path("recovery/protobuf_2_5_0_semantic_constants.json")

def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4

def parse(data):
    p=0; magic,p=u4(data,p)
    if magic!=0xCAFEBABE: raise ValueError("bad class")
    _,p=u2(data,p); _,p=u2(data,p)
    cpc,p=u2(data,p); cp=[None]*cpc; i=1
    while i<cpc:
        tag,p=u1(data,p)
        if tag==1:
            n,p=u2(data,p); raw=data[p:p+n]; p+=n
            cp[i]=(tag,raw.decode("utf-8","replace"))
        elif tag==3:
            val=struct.unpack_from(">i",data,p)[0]; p+=4; cp[i]=(tag,val)
        elif tag==4:
            val=struct.unpack_from(">I",data,p)[0]; p+=4; cp[i]=(tag,val)
        elif tag==5:
            val=struct.unpack_from(">q",data,p)[0]; p+=8; cp[i]=(tag,val); i+=1
        elif tag==6:
            val=struct.unpack_from(">Q",data,p)[0]; p+=8; cp[i]=(tag,val); i+=1
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
    access,p=u2(data,p); _,p=u2(data,p); _,p=u2(data,p)
    ic,p=u2(data,p); p+=2*ic
    def skip_member():
        nonlocal p
        p+=6; ac,p2=u2(data,p); p=p2
        for _ in range(ac):
            _,p=u2(data,p); ln,p=u4(data,p); p+=ln
    fc,p=u2(data,p)
    for _ in range(fc): skip_member()
    mc,p=u2(data,p)
    for _ in range(mc): skip_member()
    ac,p=u2(data,p); sf=None
    for _ in range(ac):
        ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai); payload=data[p:p+ln]
        if an=="SourceFile" and ln==2:
            si,_=u2(payload,0); sf=utf(si)
        p+=ln
    strings=[]
    nums=[]
    for e in cp:
        if not e: continue
        if e[0]==8:
            s=utf(e[1])
            if s is not None: strings.append(s)
        elif e[0] in (3,4,5,6):
            nums.append((e[0],e[1]))
    return sf,strings,nums

def agg(path):
    s=defaultdict(Counter); n=defaultdict(Counter); counts=Counter()
    with zipfile.ZipFile(path) as z:
        for name in z.namelist():
            if not name.endswith(".class") or name.startswith("META-INF/"): continue
            sf,ss,nn=parse(z.read(name)); counts[sf]+=1
            s[sf].update(ss); n[sf].update(nn)
    return s,n,counts

ds,dn,dc=agg(DONOR); os,on,oc=agg(OFFICIAL)
files=sorted(set(dc)|set(oc),key=str)
rows=[]
for sf in files:
    rows.append({
      "source_file":sf,
      "class_count_donor":dc[sf],"class_count_official":oc[sf],
      "string_match":ds[sf]==os[sf],
      "numeric_match":dn[sf]==on[sf],
      "donor_string_only":sum((ds[sf]-os[sf]).values()),
      "official_string_only":sum((os[sf]-ds[sf]).values()),
      "donor_numeric_only":sum((dn[sf]-on[sf]).values()),
      "official_numeric_only":sum((on[sf]-dn[sf]).values()),
    })
state={
 "gate":"PROTOBUF_2_5_0_SEMANTIC_CONSTANTS",
 "source_files":len(files),
 "class_count_match":dc==oc,
 "all_string_constants_match":all(r["string_match"] for r in rows),
 "all_numeric_constants_match":all(r["numeric_match"] for r in rows),
 "string_mismatch_files":[r["source_file"] for r in rows if not r["string_match"]],
 "numeric_mismatch_files":[r["source_file"] for r in rows if not r["numeric_match"]],
 "rows":rows,
}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:v for k,v in state.items() if k!="rows"},indent=2))
for r in rows:
    if not r["string_match"] or not r["numeric_match"]: print(json.dumps(r))
