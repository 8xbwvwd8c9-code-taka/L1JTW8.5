#!/usr/bin/env python3
import json,re,struct,sys,zipfile
from collections import defaultdict
from pathlib import Path

DONOR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
OFFICIAL=Path(sys.argv[1])
MAP=Path("recovery/protobuf_2_5_0_order_mapping.json")
OUT=Path("recovery/protobuf_2_5_0_member_mapping_v2.json")
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
        elif tag in (7,8,16,19,20): x,p=u2(data,p); cp[i]=(tag,x)
        elif tag in (9,10,11,12,17,18): a,p=u2(data,p); b,p=u2(data,p); cp[i]=(tag,a,b)
        elif tag==15: a,p=u1(data,p); b,p=u2(data,p); cp[i]=(tag,a,b)
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
    def member(kind,idx):
        nonlocal p
        fl,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p); ac,p=u2(data,p)
        for _ in range(ac):
            _,p=u2(data,p); ln,p=u4(data,p); p+=ln
        return {"name":utf(ni),"desc":utf(di),"flags":fl,"index":idx}
    fc,p=u2(data,p); fields=[member("field",i) for i in range(fc)]
    mc,p=u2(data,p); methods=[member("method",i) for i in range(mc)]
    return {"name":cls(thisi),"fields":fields,"methods":methods}

def load(path):
    out={}
    with zipfile.ZipFile(path) as z:
        for n in z.namelist():
            if n.endswith(".class") and not n.startswith("META-INF/"):
                x=parse(z.read(n)); out[x["name"]]=x
    return out

D=load(DONOR); O=load(OFFICIAL)
CMAP=json.loads(MAP.read_text(encoding="utf-8"))["mapping"]
if len(CMAP)!=246 or len(set(CMAP.values()))!=246: raise SystemExit("class map not 246/246")

def tdesc(desc):
    return OBJ.sub(lambda m:"L"+CMAP.get(m.group(1),m.group(1))+";",desc)

# Ignore SYNTHETIC/BRIDGE for first ABI identity pass; preserve linkage/behavioral modifiers.
FMASK=0x00ff
MMASK=0x0d3f  # visibility/static/final/synchronized/native/abstract/strict

rows=[]; unresolved=[]
method_order_pairs=method_order_agree=0
field_order_pairs=field_order_agree=0

def solve(kind,dms,oms):
    mapped={}; used=set()

    def core(m):
        mask=FMASK if kind=="field" else MMASK
        return (tdesc(m["desc"]),m["flags"]&mask)

    # Phase 1: exact preserved names + ABI.
    for di,d in enumerate(dms):
        cs=[oi for oi,o in enumerate(oms) if oi not in used and core(d)==(o["desc"],o["flags"]&(FMASK if kind=="field" else MMASK)) and d["name"]==o["name"]]
        if len(cs)==1:
            mapped[di]=cs[0]; used.add(cs[0])

    # Phase 2: JVM special names (<init>/<clinit>) + ABI.
    for di,d in enumerate(dms):
        if di in mapped or d["name"] not in ("<init>","<clinit>"): continue
        cs=[oi for oi,o in enumerate(oms) if oi not in used and core(d)==(o["desc"],o["flags"]&MMASK) and o["name"]==d["name"]]
        if len(cs)==1:
            mapped[di]=cs[0]; used.add(cs[0])

    # Phase 3: unique ABI signature among remaining on both sides.
    changed=True
    while changed:
        changed=False
        dg=defaultdict(list); og=defaultdict(list)
        for di,d in enumerate(dms):
            if di not in mapped: dg[core(d)].append(di)
        for oi,o in enumerate(oms):
            if oi not in used:
                mask=FMASK if kind=="field" else MMASK
                og[(o["desc"],o["flags"]&mask)].append(oi)
        for k in set(dg)&set(og):
            if len(dg[k])==1 and len(og[k])==1:
                di=dg[k][0]; oi=og[k][0]
                mapped[di]=oi; used.add(oi); changed=True

    return mapped,used

for dn,on in CMAP.items():
    dx,ox=D[dn],O[on]
    for kind in ("field","method"):
        dms=dx[kind+"s"]; oms=ox[kind+"s"]
        mp,used=solve(kind,dms,oms)

        # collect order validation from all current mapped pairs within identical ABI groups.
        bysig=defaultdict(list)
        for di,oi in mp.items():
            d=dms[di]; o=oms[oi]
            mask=FMASK if kind=="field" else MMASK
            sig=(tdesc(d["desc"]),d["flags"]&mask)
            bysig[sig].append((di,oi))
        for pairs in bysig.values():
            pairs=sorted(pairs)
            for a in range(len(pairs)):
                for b in range(a+1,len(pairs)):
                    if kind=="method":
                        method_order_pairs+=1
                        if (pairs[a][0]<pairs[b][0])==(pairs[a][1]<pairs[b][1]): method_order_agree+=1
                    else:
                        field_order_pairs+=1
                        if (pairs[a][0]<pairs[b][0])==(pairs[a][1]<pairs[b][1]): field_order_agree+=1

        # Save mapped rows.
        for di,oi in mp.items():
            d,o=dms[di],oms[oi]
            rows.append({"kind":kind,"donor_class":dn,"donor_name":d["name"],"donor_desc":d["desc"],"donor_index":di,
                         "official_class":on,"official_name":o["name"],"official_desc":o["desc"],"official_index":oi})

        # Remaining candidate groups.
        mask=FMASK if kind=="field" else MMASK
        for di,d in enumerate(dms):
            if di in mp: continue
            sig=(tdesc(d["desc"]),d["flags"]&mask)
            cs=[oi for oi,o in enumerate(oms) if oi not in used and (o["desc"],o["flags"]&mask)==sig]
            unresolved.append({"kind":kind,"donor_class":dn,"official_class":on,"donor_name":d["name"],
                               "donor_desc":d["desc"],"donor_index":di,
                               "candidate_count":len(cs),
                               "candidates":[{"name":oms[oi]["name"],"desc":oms[oi]["desc"],"index":oi} for oi in cs]})

member_total=sum(len(x["fields"])+len(x["methods"]) for x in D.values())
state={
 "gate":"PROTOBUF_2_5_0_MEMBER_MAPPING_V2",
 "classes":len(CMAP),"member_total":member_total,"mapped":len(rows),"unresolved":len(unresolved),
 "zero_candidate":sum(1 for x in unresolved if x["candidate_count"]==0),
 "multi_candidate":sum(1 for x in unresolved if x["candidate_count"]>1),
 "method_order_pairs":method_order_pairs,"method_order_agree":method_order_agree,
 "method_order_disagree":method_order_pairs-method_order_agree,
 "field_order_pairs":field_order_pairs,"field_order_agree":field_order_agree,
 "field_order_disagree":field_order_pairs-field_order_agree,
 "rows":rows,"unresolved_rows":unresolved,
}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:v for k,v in state.items() if k not in ("rows","unresolved_rows")},indent=2))
print("UNRESOLVED_SAMPLE="+json.dumps(unresolved[:80],indent=2))
