#!/usr/bin/env python3
import json
import struct
import zipfile
from pathlib import Path

SRC = Path("l1jserver2.jar")
DST = Path("recovery/l1jserver2-sanitized-ref.jar")
STATE = Path("recovery/sanitized_ref.json")
REPORT = Path("recovery/SANITIZED_REF.md")

ACC_SYNTHETIC = 0x1000

class R:
    def __init__(self, b):
        self.b = bytearray(b)
        self.p = 0
    def u1(self):
        v=self.b[self.p]; self.p+=1; return v
    def u2(self):
        v=struct.unpack_from(">H",self.b,self.p)[0]; self.p+=2; return v
    def u4(self):
        v=struct.unpack_from(">I",self.b,self.p)[0]; self.p+=4; return v
    def skip(self,n): self.p+=n
    def set_u2(self,off,v): struct.pack_into(">H",self.b,off,v)

def skip_attrs(r):
    n=r.u2()
    for _ in range(n):
        r.skip(2)
        r.skip(r.u4())

def patch_class(data):
    r=R(data)
    if r.u4()!=0xCAFEBABE:
        return data,(0,0,0)
    r.skip(4)
    cp_count=r.u2()
    i=1
    while i<cp_count:
        tag=r.u1()
        if tag==1: r.skip(r.u2())
        elif tag in (3,4): r.skip(4)
        elif tag in (5,6): r.skip(8); i+=1
        elif tag in (7,8,16,19,20): r.skip(2)
        elif tag in (9,10,11,12,17,18): r.skip(4)
        elif tag==15: r.skip(3)
        else: raise ValueError(f"unknown cp tag {tag} at cp#{i}")
        i+=1

    cc=cf=cm=0
    off=r.p; flags=r.u2()
    if flags & ACC_SYNTHETIC:
        r.set_u2(off,flags & ~ACC_SYNTHETIC); cc=1
    r.skip(4)
    r.skip(2*r.u2())

    for _ in range(r.u2()):
        off=r.p; flags=r.u2()
        if flags & ACC_SYNTHETIC:
            r.set_u2(off,flags & ~ACC_SYNTHETIC); cf+=1
        r.skip(4); skip_attrs(r)

    for _ in range(r.u2()):
        off=r.p; flags=r.u2()
        if flags & ACC_SYNTHETIC:
            r.set_u2(off,flags & ~ACC_SYNTHETIC); cm+=1
        r.skip(4); skip_attrs(r)

    return bytes(r.b),(cc,cf,cm)

totals=[0,0,0]
class_count=0
changed_classes=0
with zipfile.ZipFile(SRC,"r") as zin, zipfile.ZipFile(DST,"w") as zout:
    for info in zin.infolist():
        data=zin.read(info.filename)
        if info.filename.endswith(".class"):
            class_count+=1
            data,chg=patch_class(data)
            if any(chg): changed_classes+=1
            for i,v in enumerate(chg): totals[i]+=v
        zi=zipfile.ZipInfo(info.filename,info.date_time)
        zi.compress_type=info.compress_type
        zi.comment=info.comment
        zi.extra=info.extra
        zi.internal_attr=info.internal_attr
        zi.external_attr=info.external_attr
        zi.create_system=info.create_system
        zout.writestr(zi,data)

state={
    "source":SRC.as_posix(),
    "output":DST.as_posix(),
    "purpose":"PER_CLASS_COMPILE_REFERENCE_ONLY",
    "logic_changed":False,
    "class_count":class_count,
    "changed_classes":changed_classes,
    "class_flags_stripped":totals[0],
    "field_flags_stripped":totals[1],
    "method_flags_stripped":totals[2],
    "final_full_tree_reference":False,
}
STATE.write_text(json.dumps(state,indent=2)+"\n",encoding="utf-8")
REPORT.write_text(
    "# L1JTW8.5 Sanitized Compile Reference\n\n"
    "Recovery-only donor reference. Only ACC_SYNTHETIC bits are cleared. Bytecode instructions/descriptors are unchanged.\n\n"
    f"- Classes: **{class_count}**\n"
    f"- Changed classes: **{changed_classes}**\n"
    f"- Synthetic class flags cleared: **{totals[0]}**\n"
    f"- Synthetic field flags cleared: **{totals[1]}**\n"
    f"- Synthetic method/ctor flags cleared: **{totals[2]}**\n"
    "- Use: **PER_CLASS_COMPILE_REFERENCE_ONLY**\n"
    "- Valid final full-tree dependency: **NO**\n",
    encoding="utf-8"
)
print(json.dumps(state,indent=2))
