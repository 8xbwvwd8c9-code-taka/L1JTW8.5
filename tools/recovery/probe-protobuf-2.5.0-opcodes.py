#!/usr/bin/env python3
import json, struct, sys, zipfile
from collections import Counter, defaultdict
from pathlib import Path

DONOR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
OFFICIAL=Path(sys.argv[1])
OUT=Path("recovery/protobuf_2_5_0_opcode_fingerprint.json")

# JVM opcode operand lengths for fixed-width instructions.
FIXED={
0x10:1,0x11:2,0x12:1,0x13:2,0x14:2,
0x15:1,0x16:1,0x17:1,0x18:1,0x19:1,
0x36:1,0x37:1,0x38:1,0x39:1,0x3a:1,
0x84:2,
0x99:2,0x9a:2,0x9b:2,0x9c:2,0x9d:2,0x9e:2,
0x9f:2,0xa0:2,0xa1:2,0xa2:2,0xa3:2,0xa4:2,0xa5:2,0xa6:2,
0xa7:2,0xa8:2,0xa9:1,
0xb2:2,0xb3:2,0xb4:2,0xb5:2,0xb6:2,0xb7:2,0xb8:2,
0xb9:4,0xba:4,0xbb:2,0xbc:1,0xbd:2,0xc0:2,0xc1:2,
0xc5:3,0xc6:2,0xc7:2,0xc8:4,0xc9:4,
}
def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4

def opcode_seq(code):
    out=[]; p=0; n=len(code)
    while p<n:
        op=code[p]; out.append(op); p+=1
        if op==0xaa: # tableswitch
            pad=(-p)%4; p+=pad
            if p+12>n: break
            default,low,high=struct.unpack_from(">iii",code,p); p+=12
            cnt=max(0,high-low+1); p+=4*cnt
        elif op==0xab: # lookupswitch
            pad=(-p)%4; p+=pad
            if p+8>n: break
            default,npairs=struct.unpack_from(">ii",code,p); p+=8
            p+=8*max(0,npairs)
        elif op==0xc4: # wide
            if p>=n: break
            sub=code[p]; out.append(sub); p+=1
            p+=4 if sub==0x84 else 2
        else:
            p+=FIXED.get(op,0)
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
    _,p=u2(data,p); _,p=u2(data,p); _,p=u2(data,p)
    ic,p=u2(data,p); p+=2*ic
    def skip_field():
        nonlocal p
        p+=6; ac,p2=u2(data,p); p=p2
        for _ in range(ac):
            _,p=u2(data,p); ln,p=u4(data,p); p+=ln
    fc,p=u2(data,p)
    for _ in range(fc): skip_field()
    methods=[]
    mc,p=u2(data,p)
    for _ in range(mc):
        fl,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p); ac,p=u2(data,p)
        seq=None; exn=0
        for __ in range(ac):
            ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai)
            if an=="Code":
                q=p
                _,q=u2(data,q); _,q=u2(data,q); clen,q=u4(data,q)
                code=data[q:q+clen]; q+=clen
                exn,q=u2(data,q)
                seq=opcode_seq(code)
            p+=ln
        methods.append((fl & 0x1dff,seq,exn))
    ac,p=u2(data,p); sf=None
    for _ in range(ac):
        ai,p=u2(data,p); ln,p=u4(data,p); an=utf(ai); pay=data[p:p+ln]
        if an=="SourceFile" and ln==2:
            si,_=u2(pay,0); sf=utf(si)
        p+=ln
    return sf,methods

def agg(path):
    buckets=defaultdict(Counter)
    with zipfile.ZipFile(path) as z:
        for n in z.namelist():
            if not n.endswith(".class") or n.startswith("META-INF/"): continue
            sf,ms=parse(z.read(n))
            for fl,seq,exn in ms:
                if seq is not None:
                    buckets[sf][(fl,seq,exn)]+=1
    return buckets

D=agg(DONOR); O=agg(OFFICIAL)
files=sorted(set(D)|set(O),key=str)
rows=[]
for sf in files:
    rows.append({
      "source_file":sf,
      "match":D[sf]==O[sf],
      "donor_methods":sum(D[sf].values()),
      "official_methods":sum(O[sf].values()),
      "donor_only":sum((D[sf]-O[sf]).values()),
      "official_only":sum((O[sf]-D[sf]).values()),
    })
state={
 "gate":"PROTOBUF_2_5_0_OPCODE_FINGERPRINT",
 "source_files":len(files),
 "all_source_buckets_match":all(r["match"] for r in rows),
 "mismatch_files":[r["source_file"] for r in rows if not r["match"]],
 "donor_only_methods":sum(r["donor_only"] for r in rows),
 "official_only_methods":sum(r["official_only"] for r in rows),
 "rows":rows,
}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:v for k,v in state.items() if k!="rows"},indent=2))
for r in rows:
    if not r["match"]: print(json.dumps(r))
