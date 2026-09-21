#!/usr/bin/env python3
import json,struct,zipfile
from pathlib import Path
DONOR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
BUILT=Path("recovery/protobuf-2.5.0-source-built-donor-abi.jar")
OUT=Path("recovery/protobuf_2_5_0_source_built_abi_validation.json")
FMASK=0x50df
MMASK=0x1dff

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
    access,p=u2(data,p); ti,p=u2(data,p); si,p=u2(data,p)
    ic,p=u2(data,p); ifs=[]
    for _ in range(ic):
        x,p=u2(data,p); ifs.append(cls(x))
    def mem(mask):
        nonlocal p
        fl,p=u2(data,p); ni,p=u2(data,p); di,p=u2(data,p); ac,p=u2(data,p)
        for _ in range(ac):
            _,p=u2(data,p); ln,p=u4(data,p); p+=ln
        return (utf(ni),utf(di),fl&mask)
    fc,p=u2(data,p); fields=[mem(FMASK) for _ in range(fc)]
    mc,p=u2(data,p); methods=[mem(MMASK) for _ in range(mc)]
    return {"name":cls(ti),"super":cls(si),"interfaces":tuple(ifs),"access":access,
            "major":major,"minor":minor,"fields":fields,"methods":methods}

def load(path):
    out={}
    with zipfile.ZipFile(path) as z:
        for n in z.namelist():
            if n.endswith(".class") and not n.startswith("META-INF/"):
                x=parse(z.read(n)); out[x["name"]]=x
    return out

D=load(DONOR); B=load(BUILT)
missing=sorted(set(D)-set(B)); extra=sorted(set(B)-set(D))
rows=[]
for n in sorted(set(D)&set(B)):
    d,b=D[n],B[n]
    rows.append({
      "class":n,
      "major_match":d["major"]==b["major"],
      "access_match":(d["access"]&0x7631)==(b["access"]&0x7631),
      "super_match":d["super"]==b["super"],
      "interfaces_match":d["interfaces"]==b["interfaces"],
      "field_table_match":d["fields"]==b["fields"],
      "method_table_match":d["methods"]==b["methods"],
      "donor_fields":len(d["fields"]),"built_fields":len(b["fields"]),
      "donor_methods":len(d["methods"]),"built_methods":len(b["methods"]),
    })
state={
 "gate":"PROTOBUF_2_5_0_SOURCE_BUILT_ABI_VALIDATION",
 "donor_classes":len(D),"built_classes":len(B),
 "missing":len(missing),"extra":len(extra),
 "major_mismatch":sum(not r["major_match"] for r in rows),
 "access_mismatch":sum(not r["access_match"] for r in rows),
 "super_mismatch":sum(not r["super_match"] for r in rows),
 "interface_mismatch":sum(not r["interfaces_match"] for r in rows),
 "field_table_mismatch":sum(not r["field_table_match"] for r in rows),
 "method_table_mismatch":sum(not r["method_table_match"] for r in rows),
 "pass":len(D)==246 and len(B)==246 and not missing and not extra and
        all(r["access_match"] and r["super_match"] and r["interfaces_match"] and r["field_table_match"] and r["method_table_match"] for r in rows),
 "missing_classes":missing,"extra_classes":extra,
 "mismatch_rows":[r for r in rows if not all([r["access_match"],r["super_match"],r["interfaces_match"],r["field_table_match"],r["method_table_match"]])]
}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps({k:v for k,v in state.items() if k not in ("missing_classes","extra_classes","mismatch_rows")},indent=2))
print("MISMATCH_SAMPLE="+json.dumps(state["mismatch_rows"][:40],indent=2))
if not state["pass"]:
    raise SystemExit(1)
