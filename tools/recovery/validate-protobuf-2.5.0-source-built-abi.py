#!/usr/bin/env python3
import json,struct,zipfile
from pathlib import Path
DONOR=Path("recovery/compile-ref-protobuf-l1rpb.jar")
BUILT=Path("recovery/protobuf-2.5.0-source-built-donor-abi.jar")
OUT=Path("recovery/protobuf_2_5_0_source_built_abi_validation.json")
FMASK=0x50df
MMASK=0x1dff

FLAG_NAMES={
  0x0001:"PUBLIC",0x0002:"PRIVATE",0x0004:"PROTECTED",0x0008:"STATIC",
  0x0010:"FINAL",0x0020:"SYNCHRONIZED",0x0040:"BRIDGE",0x0080:"VARARGS",
  0x0100:"NATIVE",0x0400:"ABSTRACT",0x0800:"STRICT",0x1000:"SYNTHETIC"
}

def u1(b,p): return b[p],p+1
def u2(b,p): return struct.unpack_from(">H",b,p)[0],p+2
def u4(b,p): return struct.unpack_from(">I",b,p)[0],p+4
def flag_names(v):
    return [name for bit,name in FLAG_NAMES.items() if v&bit]
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
        return {"name":utf(ni),"desc":utf(di),"flags":fl&mask,"flags_raw":fl}
    fc,p=u2(data,p); fields=[mem(FMASK) for _ in range(fc)]
    mc,p=u2(data,p); methods=[mem(MMASK) for _ in range(mc)]
    return {"name":cls(ti),"super":cls(si),"interfaces":ifs,"access":access,
            "major":major,"minor":minor,"fields":fields,"methods":methods}

def load(path):
    out={}
    with zipfile.ZipFile(path) as z:
        for n in z.namelist():
            if n.endswith(".class") and not n.startswith("META-INF/"):
                x=parse(z.read(n)); out[x["name"]]=x
    return out

def member_tuple(m):
    return (m["name"],m["desc"],m["flags"])
def indexed_diff(ds,bs):
    out=[]
    for i in range(max(len(ds),len(bs))):
        d=ds[i] if i<len(ds) else None
        b=bs[i] if i<len(bs) else None
        if d is not None and b is not None and member_tuple(d)==member_tuple(b):
            continue
        row={"index":i,"donor":d,"built":b}
        if d is not None and b is not None:
            row["same_name"]=d["name"]==b["name"]
            row["same_desc"]=d["desc"]==b["desc"]
            row["flag_xor"]=(d["flags"]^b["flags"])
            row["donor_flag_names"]=flag_names(d["flags"])
            row["built_flag_names"]=flag_names(b["flags"])
            row["flag_xor_names"]=flag_names(d["flags"]^b["flags"])
        out.append(row)
    return out

D=load(DONOR); B=load(BUILT)
missing=sorted(set(D)-set(B)); extra=sorted(set(B)-set(D))
rows=[]
method_flag_xor={}
interface_rows=[]
for n in sorted(set(D)&set(B)):
    d,b=D[n],B[n]
    field_diff=indexed_diff(d["fields"],b["fields"])
    method_diff=indexed_diff(d["methods"],b["methods"])
    if d["interfaces"]!=b["interfaces"]:
        interface_rows.append({"class":n,"donor":d["interfaces"],"built":b["interfaces"]})
    for x in method_diff:
        if x.get("same_name") and x.get("same_desc"):
            key="0x%04x"%x["flag_xor"]
            method_flag_xor[key]=method_flag_xor.get(key,0)+1
    rows.append({
      "class":n,
      "major_match":d["major"]==b["major"],
      "access_match":(d["access"]&0x7631)==(b["access"]&0x7631),
      "super_match":d["super"]==b["super"],
      "interfaces_match":d["interfaces"]==b["interfaces"],
      "field_table_match":[member_tuple(x) for x in d["fields"]]==[member_tuple(x) for x in b["fields"]],
      "method_table_match":[member_tuple(x) for x in d["methods"]]==[member_tuple(x) for x in b["methods"]],
      "donor_fields":len(d["fields"]),"built_fields":len(b["fields"]),
      "donor_methods":len(d["methods"]),"built_methods":len(b["methods"]),
      "field_diffs":field_diff,
      "method_diffs":method_diff,
    })
mismatch=[r for r in rows if not all([r["access_match"],r["super_match"],r["interfaces_match"],r["field_table_match"],r["method_table_match"]])]
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
 "method_flag_xor_same_signature":dict(sorted(method_flag_xor.items())),
 "interface_rows":interface_rows,
 "pass":len(D)==246 and len(B)==246 and not missing and not extra and
        all(r["access_match"] and r["super_match"] and r["interfaces_match"] and r["field_table_match"] and r["method_table_match"] for r in rows),
 "missing_classes":missing,"extra_classes":extra,
 "mismatch_rows":mismatch
}
OUT.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
summary={k:v for k,v in state.items() if k not in ("missing_classes","extra_classes","mismatch_rows","interface_rows")}
print(json.dumps(summary,indent=2))
print("INTERFACE_MISMATCHES="+json.dumps(interface_rows,indent=2))
print("MISMATCH_SAMPLE="+json.dumps(mismatch[:20],indent=2))
if not state["pass"]:
    raise SystemExit(1)
