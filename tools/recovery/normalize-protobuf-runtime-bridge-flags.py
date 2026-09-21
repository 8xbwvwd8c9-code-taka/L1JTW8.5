#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

JAR = Path('recovery/compile-ref-protobuf-l1rpb.jar')
TMP = Path('recovery/compile-ref-protobuf-l1rpb.bridgefix.jar')
OUT = Path('recovery/protobuf_runtime_bridge_flag_normalization.json')
MD = Path('recovery/PROTOBUF_RUNTIME_BRIDGE_FLAG_NORMALIZATION.md')

ACC_SYNTHETIC = 0x1000
TARGET_CLASSES = {'l1rpb/p$a.class', 'l1rpb/c.class'}
EXACT_TARGETS = {
    'l1rpb/a$a.class': {('d', '(Ljava/io/InputStream;Ll1rpb/n;)Ll1rpb/y$a;')},
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

def patch_class(data, exact=None):
    r=R(data); cp=parse_cp(r)
    r.skip(6)
    r.skip(2*r.u2())
    for _ in range(r.u2()):
        r.skip(6); skip_attrs(r)
    hits=[]
    for _ in range(r.u2()):
        off=r.p
        flags=r.u2(); name_idx=r.u2(); desc_idx=r.u2()
        name=utf(cp,name_idx); desc=utf(cp,desc_idx)
        selected = (exact is None and (flags & ACC_SYNTHETIC)) or (exact is not None and (name,desc) in exact)
        if selected:
            if not (flags & ACC_SYNTHETIC):
                raise SystemExit(f'exact runtime bridge target is not synthetic: {name}{desc}')
            r.set_u2(off, flags & ~ACC_SYNTHETIC)
            hits.append({'name':name,'descriptor':desc,'old_flags':flags,'new_flags':flags & ~ACC_SYNTHETIC})
        skip_attrs(r)
    return bytes(r.b),hits

if not JAR.exists(): raise SystemExit(f'missing {JAR}')

all_hits=[]
with zipfile.ZipFile(JAR,'r') as zin, zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
    for info in zin.infolist():
        raw=zin.read(info.filename); hits=[]
        if info.filename in TARGET_CLASSES:
            raw,hits=patch_class(raw)
            all_hits += [{'class':info.filename,**h} for h in hits]
        elif info.filename in EXACT_TARGETS:
            raw,hits=patch_class(raw, EXACT_TARGETS[info.filename])
            all_hits += [{'class':info.filename,**h} for h in hits]
        zout.writestr(info,raw)

expected_min=2
if len(all_hits)<expected_min:
    TMP.unlink(missing_ok=True)
    raise SystemExit(f'expected at least {expected_min} synthetic bridge methods, patched {len(all_hits)}')

TMP.replace(JAR)
state={
    'target_classes': sorted(TARGET_CLASSES),
    'exact_targets': {k: sorted(list(v)) for k,v in EXACT_TARGETS.items()},
    'target_methods_minimum': expected_min,
    'target_methods_patched': len(all_hits),
    'targets': all_hits,
    'transform': 'clear ACC_SYNTHETIC on methods in exact recovery compile-ref parent-class whitelist only',
    'method_names_changed': False,
    'method_descriptors_changed': False,
    'bytecode_changed': False,
    'gameplay_logic_changed': False,
    'recovery_compile_ref_only': True,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
    '# Protobuf Runtime Bridge Flag Normalization\n\n'
    + f'- Target methods patched: **{len(all_hits)}**\n'
    + '- Change: clear ACC_SYNTHETIC on methods in l1rpb/p$a.class and l1rpb/c.class, plus exact a$a d(InputStream,n):y$a.\n'
    + '- Method names/descriptors changed: **NO / NO**\n'
    + '- Bytecode changed: **NO**\n'
    + '- Gameplay logic changed: **NO**\n'
    + '- Scope: recovery compile reference only.\n',
    encoding='utf-8'
)
print(json.dumps(state,indent=2))
