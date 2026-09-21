#!/usr/bin/env python3
import json, re, struct, sys, zipfile
from collections import defaultdict, Counter
from pathlib import Path

DONOR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
OFFICIAL=Path(sys.argv[1])
OUT=Path("recovery/protobuf_2_5_0_candidate_mapping.json")
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

# Reproduce stable 191-class seed used in the prior probe.
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

seed=len(mapping)

def refs(x):
    out=[]
    if x["super"]: out.append(("S",x["super"]))
    out += [("I",n) for n in x["interfaces"]]
    for f,n,d,c in x["fields"]:
        out += [("F",z) for z in OBJ.findall(d or "")]
    for f,n,d,c in x["methods"]:
        out += [("M",z) for z in OBJ.findall(d or "")]
    for ii,oi,ni,fl in x["inners"]:
        if ii==x["name"] and oi: out.append(("OUTER",oi))
        if oi==x["name"] and ii: out.append(("CHILD",ii))
    return out

def candidate_score(dn,on):
    dx,ox=D[dn],O[on]
    if key(dx)!=key(ox): return None
    score=0
    # exact local constants are strong but not mandatory.
    if dx["strings"]==ox["strings"]: score+=40
    if dx["nums"]==ox["nums"]: score+=25
    if rich(dx)==rich(ox): score+=20

    # Use already mapped reference identities.
    orefs=Counter(refs(ox))
    for kind,target in refs(dx):
        mapped=mapping.get(target)
        if mapped:
            if orefs[(kind,mapped)]>0: score+=5
            else: score-=20
    # outer mapping is especially strong.
    dout=[oi for ii,oi,ni,fl in dx["inners"] if ii==dn and oi]
    oout=[oi for ii,oi,ni,fl in ox["inners"] if ii==on and oi]
    if dout and oout and dout[0] in mapping:
        score += 50 if mapping[dout[0]]==oout[0] else -100
    return score

iters=[]
for it in range(20):
    used=set(mapping.values())
    remD=[n for n in D if n not in mapping]
    remO=[n for n in O if n not in used]
    byO=defaultdict(list)
    for on in remO: byO[key(O[on])].append(on)

    proposals={}
    reverse=defaultdict(list)
    for dn in remD:
        cs=[]
        for on in byO.get(key(D[dn]),[]):
            sc=candidate_score(dn,on)
            if sc is not None: cs.append((sc,on))
        cs.sort(reverse=True)
        if cs and (len(cs)==1 or cs[0][0]>cs[1][0]):
            proposals[dn]=cs[0]
            reverse[cs[0][1]].append((cs[0][0],dn))

    added=[]
    for dn,(sc,on) in proposals.items():
        contenders=reverse[on]
        contenders.sort(reverse=True)
        if len(contenders)==1 or contenders[0][0]>contenders[1][0]:
            if contenders[0][1]==dn:
                mapping[dn]=on; added.append((dn,on,sc))
    iters.append({"iteration":it+1,"added":len(added),"mapped":len(mapping)})
    if not added: break

used=set(mapping.values())
remD=[n for n in D if n not in mapping]; remO=[n for n in O if n not in used]
byD=defaultdict(list); byO=defaultdict(list)
for n in remD: byD[D[n]["source"]].append(n)
for n in remO: byO[O[n]["source"]].append(n)
remaining=[]
for sf in sorted(set(byD)|set(byO),key=str):
    remaining.append({"source_file":sf,"donor":sorted(byD[sf]),"official":sorted(byO[sf])})

state={"gate":"PROTOBUF_2_5_0_CANDIDATE_MAPPING","donor":len(D),"official":len(O),
       "seed_mapped":seed,"iterations":iters,"mapped":len(mapping),
       "unmapped_donor":len(remD),"unmapped_official":len(remO),
       "one_to_one":len(mapping)==len(set(mapping.values())),
       "mapping":dict(sorted(mapping.items())),"remaining":remaining}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:v for k,v in state.items() if k not in ("mapping","remaining")},indent=2))
print("REMAINING="+json.dumps(remaining,indent=2))
