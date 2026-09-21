#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

REC=Path("recovery")
SRC=REC/"protobuf-2.5.0-source-built-donor-abi.jar"
OUT=REC/"protobuf-2.5.0-source-built-compile-view.jar"
STATE=REC/"protobuf_2_5_0_source_built_compile_view.json"
TMP=REC/"protobuf-2.5.0-source-built-compile-view.tmp.jar"

ACC_ABSTRACT=0x0400
TARGET_INNER=b"l1rpb/p$b"
REMOVE={
    "l1rpb/ab.class": {("e","(Ljava/io/InputStream;Ll1rpb/n;)Ljava/lang/Object;")},
    "l1rpb/a$a.class": {("d","()Ll1rpb/a$a;")},
}

class R:
    def __init__(self,b): self.b=b; self.p=0
    def u1(self): v=self.b[self.p]; self.p+=1; return v
    def u2(self): v=struct.unpack_from(">H",self.b,self.p)[0]; self.p+=2; return v
    def u4(self): v=struct.unpack_from(">I",self.b,self.p)[0]; self.p+=4; return v
    def skip(self,n): self.p+=n

def parse_cp(r):
    if r.u4()!=0xCAFEBABE: raise ValueError("not class")
    r.skip(4)
    count=r.u2(); cp=[None]*count; i=1
    while i<count:
        tag=r.u1()
        if tag==1:
            n=r.u2(); raw=r.b[r.p:r.p+n]; r.skip(n); cp[i]=(1,raw)
        elif tag in (3,4): r.skip(4)
        elif tag in (5,6): r.skip(8); i+=1
        elif tag in (7,8,16,19,20):
            cp[i]=(tag,r.u2())
        elif tag in (9,10,11,12,17,18): r.skip(4)
        elif tag==15: r.skip(3)
        else: raise ValueError(f"unknown cp tag {tag}")
        i+=1
    return cp

def utf(cp,idx):
    if not idx: return None
    e=cp[idx]
    return e[1].decode("utf-8","replace") if e and e[0]==1 else None

def cls(cp,idx):
    if not idx: return None
    e=cp[idx]
    return cp[e[1]][1] if e and e[0]==7 and cp[e[1]] and cp[e[1]][0]==1 else None

def skip_attrs(r):
    for _ in range(r.u2()):
        r.skip(2); r.skip(r.u4())

def skip_member(r):
    r.skip(6); skip_attrs(r)

def remove_abstract_methods(data,wanted):
    r=R(data); cp=parse_cp(r)
    r.skip(6); r.skip(2*r.u2())
    for _ in range(r.u2()): skip_member(r)
    count_pos=r.p; mc=r.u2()
    kept=[]; removed=[]
    for _ in range(mc):
        st=r.p
        flags=r.u2(); ni=r.u2(); di=r.u2()
        name=utf(cp,ni); desc=utf(cp,di)
        skip_attrs(r)
        raw=data[st:r.p]
        if (name,desc) in wanted:
            if not (flags & ACC_ABSTRACT):
                raise SystemExit(f"compile-view target is not abstract: {name}{desc} flags={flags:#x}")
            removed.append((name,desc,flags))
        else:
            kept.append(raw)
    out=bytearray(data[:count_pos])
    out+=struct.pack(">H",len(kept))
    for raw in kept: out+=raw
    out+=data[r.p:]
    return bytes(out),removed

def widen_inner_visibility(data):
    r=R(data); cp=parse_cp(r)
    pos=r.p
    pos+=6
    ic=struct.unpack_from(">H",data,pos)[0]; pos+=2+2*ic
    fc=struct.unpack_from(">H",data,pos)[0]; pos+=2
    rr=R(data); rr.p=pos
    for _ in range(fc): skip_member(rr)
    pos=rr.p
    mc=struct.unpack_from(">H",data,pos)[0]; pos+=2
    rr.p=pos
    for _ in range(mc): skip_member(rr)
    pos=rr.p
    ac=struct.unpack_from(">H",data,pos)[0]; pos+=2
    out=bytearray(data); changed=0
    for _ in range(ac):
        name_i=struct.unpack_from(">H",data,pos)[0]
        ln=struct.unpack_from(">I",data,pos+2)[0]
        payload=pos+6
        if utf(cp,name_i)=="InnerClasses":
            n=struct.unpack_from(">H",data,payload)[0]
            q=payload+2
            for __ in range(n):
                inner_i,outer_i,inner_name_i,flags=struct.unpack_from(">HHHH",data,q)
                if cls(cp,inner_i)==TARGET_INNER:
                    new_flags=(flags & ~0x0004) | 0x0001
                    if new_flags!=flags:
                        struct.pack_into(">H",out,q+6,new_flags)
                        changed+=1
                q+=8
        pos+=6+ln
    return bytes(out),changed

if not SRC.exists(): raise SystemExit(f"missing exact source-built ABI jar: {SRC}")
removed=[]
visibility=0
with zipfile.ZipFile(SRC,"r") as zin, zipfile.ZipFile(TMP,"w",zipfile.ZIP_DEFLATED) as zout:
    class_count=sum(1 for n in zin.namelist() if n.endswith(".class"))
    if class_count!=246: raise SystemExit(f"exact source-built jar must contain 246 classes, got {class_count}")
    for info in zin.infolist():
        raw=zin.read(info.filename)
        if info.filename.endswith(".class"):
            raw,n=widen_inner_visibility(raw)
            visibility+=n
            if info.filename in REMOVE:
                raw,hits=remove_abstract_methods(raw,REMOVE[info.filename])
                removed += [{"class":info.filename,"name":n,"descriptor":d,"flags":f} for n,d,f in hits]
        zout.writestr(info,raw)

expected_removed=sum(len(v) for v in REMOVE.values())
if len(removed)!=expected_removed:
    TMP.unlink(missing_ok=True)
    raise SystemExit(f"expected {expected_removed} compile-only obligations removed, got {len(removed)}")
if visibility==0:
    TMP.unlink(missing_ok=True)
    raise SystemExit("expected at least one p$b InnerClasses visibility patch")
TMP.replace(OUT)

state={
    "gate":"SOURCE_BUILT_PROTOBUF_COMPILE_VIEW",
    "source_exact_abi_jar":SRC.as_posix(),
    "output_compile_view":OUT.as_posix(),
    "source_built_classes":246,
    "abstract_obligations_removed":removed,
    "abstract_obligation_count":len(removed),
    "inner_visibility_target":"l1rpb/p$b",
    "inner_visibility_entries_widened":visibility,
    "runtime_exact_abi_jar_modified":False,
    "donor_binary_used":False,
    "method_bytecode_changed":False,
    "gameplay_logic_changed":False,
    "scope":"javac compile view only; runtime/linkage validation remains against exact source-built donor ABI",
}
STATE.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
print(json.dumps(state,indent=2))
