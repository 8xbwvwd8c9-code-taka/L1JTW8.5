#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
TMP=Path('recovery/compile-ref-protobuf-l1rpb.parserbridge.jar')
OUT=Path('recovery/protobuf_parser_bridge_flag_experiment.json')
MD=Path('recovery/PROTOBUF_PARSER_BRIDGE_FLAG_EXPERIMENT.md')

ACC_BRIDGE=0x0040
ACC_PUBLIC=0x0001
ACC_SYNTHETIC=0x1000
TARGET_CLASS='l1rpb/c.class'
TARGETS={
 ('f','(Ljava/io/InputStream;)Ljava/lang/Object;'),
 ('f','(Ljava/io/InputStream;Ll1rpb/n;)Ljava/lang/Object;'),
}

class R:
    def __init__(self,b): self.b=bytearray(b); self.p=0
    def u1(self): v=self.b[self.p]; self.p+=1; return v
    def u2(self): v=struct.unpack_from('>H',self.b,self.p)[0]; self.p+=2; return v
    def u4(self): v=struct.unpack_from('>I',self.b,self.p)[0]; self.p+=4; return v
    def skip(self,n): self.p+=n
    def set_u2(self,off,v): struct.pack_into('>H',self.b,off,v)

def parse_cp(r):
    if r.u4()!=0xCAFEBABE: raise ValueError('not class')
    r.skip(4); count=r.u2(); cp=[None]*count; i=1
    while i<count:
        tag=r.u1()
        if tag==1:
            n=r.u2(); raw=bytes(r.b[r.p:r.p+n]); r.skip(n); cp[i]=(1,raw)
        elif tag in (3,4): r.skip(4)
        elif tag in (5,6): r.skip(8); i+=1
        elif tag in (7,8,16,19,20): r.skip(2)
        elif tag in (9,10,11,12,17,18): r.skip(4)
        elif tag==15: r.skip(3)
        else: raise ValueError(f'unknown cp tag {tag} at {i}')
        i+=1
    return cp

def utf(cp,idx):
    e=cp[idx]
    if not e or e[0]!=1: return None
    return e[1].decode('utf-8','replace')

def skip_attrs(r):
    for _ in range(r.u2()):
        r.skip(2); r.skip(r.u4())

def patch(data):
    r=R(data); cp=parse_cp(r)
    r.skip(6); r.skip(2*r.u2())
    for _ in range(r.u2()):
        r.skip(6); skip_attrs(r)
    hits=[]
    for _ in range(r.u2()):
        off=r.p; flags=r.u2(); ni=r.u2(); di=r.u2()
        name=utf(cp,ni); desc=utf(cp,di)
        if (name,desc) in TARGETS:
            if not (flags & ACC_PUBLIC): raise SystemExit(f'target not public: {name}{desc} {flags:#x}')
            if not (flags & ACC_SYNTHETIC): raise SystemExit(f'target not synthetic: {name}{desc} {flags:#x}')
            if flags & ACC_BRIDGE: raise SystemExit(f'target already bridge: {name}{desc} {flags:#x}')
            new_flags=flags | ACC_BRIDGE
            r.set_u2(off,new_flags)
            hits.append({'name':name,'descriptor':desc,'old_flags':flags,'new_flags':new_flags})
        skip_attrs(r)
    return bytes(r.b),hits

hits=[]
with zipfile.ZipFile(JAR,'r') as zin, zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
    for info in zin.infolist():
        raw=zin.read(info.filename)
        if info.filename==TARGET_CLASS:
            raw,hits=patch(raw)
        zout.writestr(info,raw)
if len(hits)!=2:
    TMP.unlink(missing_ok=True)
    raise SystemExit(f'expected exactly two parser f bridge targets, got {len(hits)}')
TMP.replace(JAR)
state={
 'experiment':'PARSER_F_EXACT_ADD_ACC_BRIDGE',
 'class':TARGET_CLASS,
 'hits':hits,
 'change':'add ACC_BRIDGE while preserving ACC_PUBLIC|ACC_SYNTHETIC',
 'compile_ref_only':True,
 'donor_jar_changed':False,
 'recovered_source_changed':False,
 'method_names_changed':False,
 'method_descriptors_changed':False,
 'bytecode_changed':False,
 'gameplay_logic_changed':False,
 'pass_signal':'parser 44 obligations disappear with nonprotobuf remaining 0',
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
 '# Protobuf Parser Bridge Flag Experiment\n\n'
 '- Targets: exact two source-visible c.f(InputStream[,n]) erased provider methods.\n'
 '- Change: add ACC_BRIDGE only; preserve ACC_PUBLIC and ACC_SYNTHETIC.\n'
 '- Parser APIs are not pruned.\n'
 '- Donor JAR/source/bytecode/gameplay: unchanged.\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
