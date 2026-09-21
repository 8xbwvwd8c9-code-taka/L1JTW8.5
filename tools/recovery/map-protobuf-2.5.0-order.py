#!/usr/bin/env python3
# Extends stable 230/246 mapping with validated InnerClasses sibling-order inference.
import json, re, struct, sys, zipfile
from collections import defaultdict, Counter
from pathlib import Path

DONOR=Path(sys.argv[2]) if len(sys.argv)>2 else Path("recovery/compile-ref-protobuf-l1rpb.jar")
OFFICIAL=Path(sys.argv[1])
OUT=Path("recovery/protobuf_2_5_0_order_mapping.json")
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
    ac,p=u2(data,p); sf=None; inn=[]; enclosing_owner=None; enclosing_name=None; enclosing_desc=None
    for _ in range(ac):
        ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai); pay=data[p:p+ln]
        if an=="SourceFile" and ln==2:
            si,_=u2(pay,0); sf=utf(si)
        elif an=="InnerClasses":
            q=0; n,q=u2(pay,q)
            for __ in range(n):
                ii,q=u2(pay,q); oi,q=u2(pay,q); ni,q=u2(pay,q); fl,q=u2(pay,q)
                inn.append((cls(ii),cls(oi),utf(ni),fl))
        elif an=="EnclosingMethod" and ln==4:
            q=0; ci,q=u2(pay,q); mi,q=u2(pay,q)
            enclosing_owner=cls(ci)
            if mi:
                e=cp[mi]
                if e and e[0]==12:
                    enclosing_name=utf(e[1]); enclosing_desc=utf(e[2])
        p+=ln
    strings=[]; nums=[]
    for e in cp:
        if not e: continue
        if e[0]==8:
            s=utf(e[1])
            if s is not None: strings.append(s)
        elif e[0] in (3,4,5,6): nums.append((e[0],e[1]))
    outer=None
    for ii,oi,ni,fl in inn:
        if ii==cls(thisi): outer=oi; break
    return {"name":cls(thisi),"super":cls(superi),"interfaces":ifs,"fields":fs,"methods":ms,
            "source":sf,"access":access,"major":major,"minor":minor,"inners":inn,
            "strings":Counter(strings),"nums":Counter(nums),"outer":outer,
            "enclosing_owner":enclosing_owner,"enclosing_name":enclosing_name,"enclosing_desc":enclosing_desc}

def load(path):
    out={}
    with zipfile.ZipFile(path) as z:
        for n in z.namelist():
            if n.endswith(".class") and not n.startswith("META-INF/"):
                x=parse(z.read(n)); out[x["name"]]=x
    return out

D=load(DONOR); O=load(OFFICIAL); DSET=set(D); OSET=set(O)
def dshape(desc): return OBJ.sub("L?;",desc or "")
def key(x):
    return (x["source"],x["major"],x["minor"],x["access"]&0x7631,
            len(x["interfaces"]),len(x["fields"]),len(x["methods"]),
            sum(1 for *_,code in x["methods"] if code))
def rich(x):
    return key(x)+(
      tuple(sorted((f&0x50df,dshape(d)) for f,n,d,c in x["fields"])),
      tuple(sorted((f&0x1dff,dshape(d),c) for f,n,d,c in x["methods"])),
      len(x["inners"]),
    )

# stable 191 seed
dg=defaultdict(list); og=defaultdict(list)
for n,x in D.items(): dg[key(x)].append(n)
for n,x in O.items(): og[key(x)].append(n)
mapping={}
for k in set(dg)|set(og):
    ds=dg.get(k,[]); os=og.get(k,[])
    if len(ds)==1 and len(os)==1:
        mapping[ds[0]]=os[0]; continue
    dr=defaultdict(list); orr=defaultdict(list)
    for n in ds: dr[rich(D[n])].append(n)
    for n in os: orr[rich(O[n])].append(n)
    for rk in set(dr)&set(orr):
        if len(dr[rk])==1 and len(orr[rk])==1:
            mapping[dr[rk][0]]=orr[rk][0]

def refs(x):
    out=[]
    if x["super"]: out.append(("S",x["super"]))
    out += [("I",n) for n in x["interfaces"]]
    for f,n,d,c in x["fields"]: out += [("F",z) for z in OBJ.findall(d or "")]
    for f,n,d,c in x["methods"]: out += [("M",z) for z in OBJ.findall(d or "")]
    for ii,oi,ni,fl in x["inners"]:
        if ii==x["name"] and oi: out.append(("OUTER",oi))
        if oi==x["name"] and ii: out.append(("CHILD",ii))
    return out

def candidate_score(dn,on):
    dx,ox=D[dn],O[on]
    if key(dx)!=key(ox): return None
    score=0
    if dx["strings"]==ox["strings"]: score+=40
    if dx["nums"]==ox["nums"]: score+=25
    if rich(dx)==rich(ox): score+=20
    orefs=Counter(refs(ox))
    for kind,target in refs(dx):
        mapped=mapping.get(target)
        if mapped:
            score += 5 if orefs[(kind,mapped)]>0 else -20
    dout=dx["outer"]; oout=ox["outer"]
    if dout and oout and dout in mapping:
        score += 50 if mapping[dout]==oout else -100
    return score

# stable candidate iterations -> expected 230
for it in range(20):
    used=set(mapping.values())
    remD=[n for n in D if n not in mapping]
    remO=[n for n in O if n not in used]
    byO=defaultdict(list)
    for on in remO: byO[key(O[on])].append(on)
    proposals={}; reverse=defaultdict(list)
    for dn in remD:
        cs=[]
        for on in byO.get(key(D[dn]),[]):
            sc=candidate_score(dn,on)
            if sc is not None: cs.append((sc,on))
        cs.sort(reverse=True)
        if cs and (len(cs)==1 or cs[0][0]>cs[1][0]):
            proposals[dn]=cs[0]; reverse[cs[0][1]].append((cs[0][0],dn))
    added=0
    for dn,(sc,on) in proposals.items():
        contenders=sorted(reverse[on],reverse=True)
        if (len(contenders)==1 or contenders[0][0]>contenders[1][0]) and contenders[0][1]==dn:
            mapping[dn]=on; added+=1
    if not added: break

mapped_before_order=len(mapping)

# Ordered direct children as recorded in each parent's InnerClasses table.
def ordered_children(x, universe):
    seen=set(); out=[]
    for ii,oi,ni,fl in x["inners"]:
        if oi==x["name"] and ii in universe and ii not in seen:
            seen.add(ii); out.append(ii)
    return out

# Validate sibling-order preservation using already mapped children.
order_pairs=0; order_agree=0; order_disagree=[]
for dp,op in list(mapping.items()):
    dc=ordered_children(D[dp],DSET); oc=ordered_children(O[op],OSET)
    orank={n:i for i,n in enumerate(oc)}
    known=[(i,mapping[n]) for i,n in enumerate(dc) if n in mapping and mapping[n] in orank]
    for a in range(len(known)):
        for b in range(a+1,len(known)):
            di1,on1=known[a]; di2,on2=known[b]
            order_pairs+=1
            same=(di1<di2)==(orank[on1]<orank[on2])
            if same: order_agree+=1
            else: order_disagree.append((dp,op,known[a],known[b]))

# Apply rank mapping only if all observed mapped sibling pairs preserve order.
order_safe=(order_pairs>0 and not order_disagree)
order_added=[]
if order_safe:
    changed=True
    while changed:
        changed=False
        used=set(mapping.values())
        for dp,op in list(mapping.items()):
            dc=ordered_children(D[dp],DSET); oc=ordered_children(O[op],OSET)
            if len(dc)!=len(oc): continue
            for idx,(dn,on) in enumerate(zip(dc,oc)):
                if dn in mapping:
                    if mapping[dn]!=on:
                        order_safe=False
                        break
                    continue
                if on in used: continue
                # Require same local source/shape before accepting ordered pair.
                if key(D[dn])==key(O[on]):
                    mapping[dn]=on; used.add(on); order_added.append((dn,on,idx)); changed=True
            if not order_safe: break
        if not order_safe: break

# Resolve anonymous classes using EnclosingMethod owner and compiler-generated numeric suffix.
used=set(mapping.values())
anon_added=[]
changed=True
while changed:
    changed=False
    used=set(mapping.values())
    remD=[n for n in D if n not in mapping]
    remO=[n for n in O if n not in used]
    for dn in list(remD):
        dx=D[dn]
        de=dx.get("enclosing_owner")
        candidates=[]
        for on in remO:
            ox=O[on]
            # Same local shape is mandatory.
            if key(dx)!=key(ox): continue
            # Enclosing owner, when present, must agree through existing mapping.
            oe=ox.get("enclosing_owner")
            if de and de in mapping:
                if mapping[de] != oe: continue
            # Preserve compiler anonymous suffix when both are numeric.
            dm=re.search(r"\$(\d+)$",dn)
            om=re.search(r"\$(\d+)$",on)
            if dm and om and dm.group(1)!=om.group(1): continue
            candidates.append(on)
        if len(candidates)==1:
            on=candidates[0]
            mapping[dn]=on; anon_added.append((dn,on)); changed=True

# Validate numeric anonymous suffix preservation on all mapped anonymous pairs.
suffix_pairs=0; suffix_agree=0; suffix_disagree=[]
for dn,on in mapping.items():
    dm=re.search(r"\$(\d+)$",dn)
    om=re.search(r"\$(\d+)$",on)
    if dm and om:
        suffix_pairs+=1
        if dm.group(1)==om.group(1): suffix_agree+=1
        else: suffix_disagree.append((dn,on,dm.group(1),om.group(1)))

used=set(mapping.values())
remD=[n for n in D if n not in mapping]; remO=[n for n in O if n not in used]
state={
 "gate":"PROTOBUF_2_5_0_ORDER_MAPPING",
 "mapped_before_order":mapped_before_order,
 "order_validation_pairs":order_pairs,
 "order_validation_agree":order_agree,
 "order_validation_disagree":len(order_disagree),
 "order_safe":order_safe,
 "order_added":len(order_added),
 "anonymous_added":len(anon_added),
 "anonymous_added_pairs":anon_added,
 "suffix_validation_pairs":suffix_pairs,
 "suffix_validation_agree":suffix_agree,
 "suffix_validation_disagree":len(suffix_disagree),
 "suffix_disagree_pairs":suffix_disagree,
 "mapped":len(mapping),
 "unmapped_donor":len(remD),"unmapped_official":len(remO),
 "one_to_one":len(mapping)==len(set(mapping.values())),
 "mapping":dict(sorted(mapping.items())),
 "order_added_pairs":order_added,
 "remaining_donor":sorted(remD),"remaining_official":sorted(remO),
}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:v for k,v in state.items() if k not in ("mapping","order_added_pairs","remaining_donor","remaining_official")},indent=2))
print("ORDER_ADDED="+json.dumps(order_added,indent=2))
print("REMAINING_DONOR="+json.dumps(sorted(remD),indent=2))
print("REMAINING_OFFICIAL="+json.dumps(sorted(remO),indent=2))
