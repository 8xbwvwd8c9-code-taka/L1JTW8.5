#!/usr/bin/env python3
import json, struct, sys, zipfile
from collections import Counter, defaultdict
from pathlib import Path

DONOR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
OFFICIAL=Path(sys.argv[1])
OUT=Path("recovery/protobuf_2_5_0_opcode_tiered_fingerprint.json")

FIXED={
0x10:1,0x11:2,0x12:1,0x13:2,0x14:2,
0x15:1,0x16:1,0x17:1,0x18:1,0x19:1,
0x36:1,0x37:1,0x38:1,0x39:1,0x3a:1,0x84:2,
0x99:2,0x9a:2,0x9b:2,0x9c:2,0x9d:2,0x9e:2,
0x9f:2,0xa0:2,0xa1:2,0xa2:2,0xa3:2,0xa4:2,0xa5:2,0xa6:2,
0xa7:2,0xa8:2,0xa9:1,
0xb2:2,0xb3:2,0xb4:2,0xb5:2,0xb6:2,0xb7:2,0xb8:2,
0xb9:4,0xba:4,0xbb:2,0xbc:1,0xbd:2,0xc0:2,0xc1:2,
0xc5:3,0xc6:2,0xc7:2,0xc8:4,0xc9:4,
}

# Canonicalize short local forms and compiler-width variants.
LOCAL_CANON={}
for base,short0 in [(0x15,0x1a),(0x16,0x1e),(0x17,0x22),(0x18,0x26),(0x19,0x2a)]:
    LOCAL_CANON[base]=base
    for x in range(short0,short0+4): LOCAL_CANON[x]=base
for base,short0 in [(0x36,0x3b),(0x37,0x3f),(0x38,0x43),(0x39,0x47),(0x3a,0x4b)]:
    LOCAL_CANON[base]=base
    for x in range(short0,short0+4): LOCAL_CANON[x]=base

CONST_OPS=set(range(0x01,0x0f+1))|{0x10,0x11,0x12,0x13,0x14}
RETURN_OPS={0xac,0xad,0xae,0xaf,0xb0,0xb1}
FIELD_OPS={0xb2,0xb3,0xb4,0xb5}
INVOKE_OPS={0xb6,0xb7,0xb8,0xb9,0xba}
TYPE_OPS={0xbb,0xbc,0xbd,0xc0,0xc1,0xc5}
BRANCH_OPS=set(range(0x99,0xa9))|{0xc6,0xc7,0xc8,0xc9}
ARRAY_LOAD=set(range(0x2e,0x36)); ARRAY_STORE=set(range(0x4f,0x57))
ARITH=set(range(0x60,0x84)); CONVERT=set(range(0x85,0x94)); COMPARE=set(range(0x94,0x99))

def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4

def decode(code):
    out=[]; p=0; n=len(code)
    while p<n:
        op=code[p]; p+=1
        if op==0xaa:
            out.append(op); pad=(-p)%4; p+=pad
            if p+12>n: break
            _,low,high=struct.unpack_from(">iii",code,p); p+=12+4*max(0,high-low+1)
        elif op==0xab:
            out.append(op); pad=(-p)%4; p+=pad
            if p+8>n: break
            _,npairs=struct.unpack_from(">ii",code,p); p+=8+8*max(0,npairs)
        elif op==0xc4:
            if p>=n: break
            sub=code[p]; p+=1; out.append(sub)
            p+=4 if sub==0x84 else 2
        else:
            out.append(op); p+=FIXED.get(op,0)
    return out

def tier1(seq):
    out=[]
    for op in seq:
        if op in LOCAL_CANON: out.append(LOCAL_CANON[op])
        elif op in CONST_OPS: out.append(0x12) # CONST/LDC family
        elif op==0xc8: out.append(0xa7)
        elif op==0xc9: out.append(0xa8)
        else: out.append(op)
    return tuple(out)

def tier2(seq):
    out=[]
    for op in seq:
        if op in CONST_OPS: out.append("CONST")
        elif op in LOCAL_CANON:
            b=LOCAL_CANON[op]; out.append("LOAD" if b<=0x19 else "STORE")
        elif op in ARRAY_LOAD: out.append("ALOAD")
        elif op in ARRAY_STORE: out.append("ASTORE")
        elif op in FIELD_OPS: out.append("FIELD")
        elif op in INVOKE_OPS: out.append("INVOKE")
        elif op in TYPE_OPS: out.append("TYPE")
        elif op in RETURN_OPS: out.append("RETURN")
        elif op in BRANCH_OPS: out.append("BRANCH")
        elif op in (0xaa,0xab): out.append("SWITCH")
        elif op in ARITH: out.append("ARITH")
        elif op in CONVERT: out.append("CONVERT")
        elif op in COMPARE: out.append("COMPARE")
        elif op==0x84: out.append("IINC")
        elif op in (0xbf,): out.append("THROW")
        elif op in (0xc2,0xc3): out.append("MONITOR")
        elif op in (0x57,0x58): out.append("POP")
        elif op in (0x59,0x5a,0x5b,0x5c,0x5d,0x5e,0x5f): out.append("STACK")
        else: out.append(f"O{op:02x}")
    return tuple(out)

def parse(data):
    p=0; magic,p=u4(data,p)
    if magic!=0xCAFEBABE: raise ValueError("bad")
    _,p=u2(data,p); _,p=u2(data,p)
    cpc,p=u2(data,p); cp=[None]*cpc; i=1
    while i<cpc:
        tag,p=u1(data,p)
        if tag==1:
            ln,p=u2(data,p); cp[i]=(tag,data[p:p+ln].decode("utf-8","replace")); p+=ln
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
    _,p=u2(data,p); _,p=u2(data,p); _,p=u2(data,p)
    ic,p=u2(data,p); p+=2*ic
    def skip_field():
        nonlocal p
        p+=6; ac,p2=u2(data,p); p=p2
        for _ in range(ac): _,p=u2(data,p); ln,p=u4(data,p); p+=ln
    fc,p=u2(data,p)
    for _ in range(fc): skip_field()
    methods=[]; mc,p=u2(data,p)
    for _ in range(mc):
        fl,p=u2(data,p); _,p=u2(data,p); _,p=u2(data,p); ac,p=u2(data,p)
        seq=None; exn=0
        for __ in range(ac):
            ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai)
            if an=="Code":
                q=p; _,q=u2(data,q); _,q=u2(data,q); clen,q=u4(data,q)
                seq=decode(data[q:q+clen]); q+=clen
                exn,q=u2(data,q)
            p+=ln
        if seq is not None: methods.append((fl & 0x1dff,tuple(seq),exn))
    ac,p=u2(data,p); sf=None
    for _ in range(ac):
        ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai); pay=data[p:p+ln]
        if an=="SourceFile" and ln==2:
            si,_=u2(pay,0); sf=utf(si)
        p+=ln
    return sf,methods

def agg(path,tier):
    buckets=defaultdict(Counter)
    with zipfile.ZipFile(path) as z:
        for n in z.namelist():
            if not n.endswith(".class") or n.startswith("META-INF/"): continue
            sf,ms=parse(z.read(n))
            for fl,seq,exn in ms:
                norm=seq if tier==0 else (tier1(seq) if tier==1 else tier2(seq))
                buckets[sf][(fl,norm,exn)]+=1
    return buckets

state={"gate":"PROTOBUF_2_5_0_OPCODE_TIERED_FINGERPRINT","tiers":{}}
for tier in (0,1,2):
    D=agg(DONOR,tier); O=agg(OFFICIAL,tier); files=sorted(set(D)|set(O),key=str)
    rows=[]
    for sf in files:
        rows.append({"source_file":sf,"match":D[sf]==O[sf],
                     "donor_only":sum((D[sf]-O[sf]).values()),
                     "official_only":sum((O[sf]-D[sf]).values())})
    state["tiers"][str(tier)]={
      "source_files":len(files),
      "all_match":all(r["match"] for r in rows),
      "mismatch_files":[r["source_file"] for r in rows if not r["match"]],
      "donor_only_methods":sum(r["donor_only"] for r in rows),
      "official_only_methods":sum(r["official_only"] for r in rows),
      "rows":rows,
    }
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:{kk:vv for kk,vv in v.items() if kk!="rows"} for k,v in state["tiers"].items()},indent=2))
