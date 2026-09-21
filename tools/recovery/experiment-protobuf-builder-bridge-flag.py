#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
TMP=Path('recovery/compile-ref-protobuf-l1rpb.builderbridgeflag.jar')
OUT=Path('recovery/protobuf_builder_bridge_flag_experiment.json')
MD=Path('recovery/PROTOBUF_BUILDER_BRIDGE_FLAG_EXPERIMENT.md')

ACC_ABSTRACT=0x0400
ACC_SYNTHETIC=0x1000
ABSTRACT_CLASS='l1rpb/y$a.class'
PROVIDER_CLASS='l1rpb/b$a.class'
KNOWN_CURRENT=('c','([BIILl1rpb/n;)Ll1rpb/y$a;')

class R:
    def __init__(self,b): self.b=bytearray(b); self.p=0
    def u1(self): v=self.b[self.p]; self.p+=1; return v
    def u2(self): v=struct.unpack_from('>H',self.b,self.p)[0]; self.p+=2; return v
    def u4(self): v=struct.unpack_from('>I',self.b,self.p)[0]; self.p+=4; return v
    def skip(self,n): self.p+=n
    def set_u2(self,off,v): struct.pack_into('>H',self.b,off,v)

def parse_cp(r):
    if r.u4()!=0xCAFEBABE: raise ValueError('not class')
    r.skip(4)
    count=r.u2(); cp=[None]*count; i=1
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

def read_methods(data):
    r=R(data); cp=parse_cp(r)
    r.skip(6)
    r.skip(2*r.u2())
    for _ in range(r.u2()):
        r.skip(6); skip_attrs(r)
    rows=[]
    for _ in range(r.u2()):
        flags=r.u2(); ni=r.u2(); di=r.u2()
        rows.append({
            'name':utf(cp,ni),
            'descriptor':utf(cp,di),
            'flags':flags,
            'abstract':bool(flags & ACC_ABSTRACT),
            'synthetic':bool(flags & ACC_SYNTHETIC),
        })
        skip_attrs(r)
    return rows

def patch(data, targets):
    r=R(data); cp=parse_cp(r)
    r.skip(6)
    r.skip(2*r.u2())
    for _ in range(r.u2()):
        r.skip(6); skip_attrs(r)
    hits=[]
    for _ in range(r.u2()):
        off=r.p
        flags=r.u2(); ni=r.u2(); di=r.u2()
        name=utf(cp,ni); desc=utf(cp,di)
        if (name,desc) in targets:
            if flags & ACC_ABSTRACT:
                raise SystemExit(f'provider unexpectedly abstract: {name}{desc} flags={flags:#x}')
            if not (flags & ACC_SYNTHETIC):
                raise SystemExit(f'provider not ACC_SYNTHETIC before experiment: {name}{desc} flags={flags:#x}')
            new_flags=flags & ~ACC_SYNTHETIC
            r.set_u2(off,new_flags)
            hits.append({
                'name':name,'descriptor':desc,
                'old_flags':flags,'new_flags':new_flags,
            })
        skip_attrs(r)
    return bytes(r.b),hits

if not JAR.exists():
    raise SystemExit(f'missing {JAR}')

with zipfile.ZipFile(JAR,'r') as zin:
    raw_map={name:zin.read(name) for name in zin.namelist() if name.endswith('.class')}

for required in (ABSTRACT_CLASS,PROVIDER_CLASS):
    if required not in raw_map:
        raise SystemExit(f'missing class: {required}')

abstracts={
    (x['name'],x['descriptor'])
    for x in read_methods(raw_map[ABSTRACT_CLASS])
    if x['abstract']
}
provider_rows=read_methods(raw_map[PROVIDER_CLASS])
targets={
    (x['name'],x['descriptor'])
    for x in provider_rows
    if (x['name'],x['descriptor']) in abstracts
    and not x['abstract']
    and x['synthetic']
}

if KNOWN_CURRENT not in targets:
    raise SystemExit(f'known current builder obligation/provider is not in exact ABI-derived target set: {KNOWN_CURRENT}')
if len(targets)<2:
    raise SystemExit(f'expected multiple exact y$a <- b$a synthetic providers, got {len(targets)}')

hits=[]
with zipfile.ZipFile(JAR,'r') as zin, zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
    for info in zin.infolist():
        raw=zin.read(info.filename)
        if info.filename==PROVIDER_CLASS:
            raw,hits=patch(raw,targets)
        zout.writestr(info,raw)

if len(hits)!=len(targets):
    TMP.unlink(missing_ok=True)
    raise SystemExit(f'expected {len(targets)} exact provider patches, got {len(hits)}')

TMP.replace(JAR)
state={
    'experiment':'BUILDER_Y_A_TO_B_A_EXACT_PROVIDER_VISIBILITY',
    'abstract_owner':ABSTRACT_CLASS,
    'provider_class':PROVIDER_CLASS,
    'derived_exact_provider_count':len(targets),
    'targets':[{'name':n,'descriptor':d} for n,d in sorted(targets)],
    'hits':hits,
    'selection_rule':'abstract method in current y$a + exact same name/descriptor concrete ACC_SYNTHETIC method in b$a',
    'known_current_obligation_present':True,
    'compile_ref_only':True,
    'donor_jar_changed':False,
    'recovered_source_changed':False,
    'method_name_changed':False,
    'method_descriptor_changed':False,
    'bytecode_changed':False,
    'gameplay_logic_changed':False,
    'baseline':'88 protobuf / 0 non-protobuf errors; builder current method c(byte[],int,int,n)',
    'pass_signal':'builder 44-error family disappears or advances beyond y$a<-b$a exact provider family without non-protobuf regression',
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
    '# Protobuf Builder Bridge Flag Experiment\n\n'
    + f'- Abstract owner: {ABSTRACT_CLASS}\n'
    + f'- Concrete provider: {PROVIDER_CLASS}\n'
    + f'- Exact synthetic providers selected: **{len(targets)}**\n'
    + '- Rule: same JVM name + descriptor, abstract in y$a, concrete + ACC_SYNTHETIC in b$a.\n'
    + '- Change: clear ACC_SYNTHETIC only on that exact ABI-derived provider set.\n'
    + '- Parser runtime: UNCHANGED\n'
    + '- Donor JAR: UNCHANGED\n'
    + '- Recovered source: UNCHANGED\n'
    + '- Bytecode: UNCHANGED\n'
    + '- Gameplay logic: UNCHANGED\n',
    encoding='utf-8'
)
print(json.dumps(state,indent=2))
