#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
TMP=Path('recovery/compile-ref-protobuf-l1rpb.builder-current-b-kf-int-obj.jar')
OUT=Path('recovery/protobuf_builder_current_b_kf_int_obj_alias_experiment.json')
MD=Path('recovery/PROTOBUF_BUILDER_CURRENT_B_KF_INT_OBJ_ALIAS_EXPERIMENT.md')

TARGET='l1rpb/p$a.class'
SRC=('a','(Ll1rpb/k$f;ILjava/lang/Object;)Ll1rpb/p$a;')
ALIAS=('b','(Ll1rpb/k$f;ILjava/lang/Object;)Ll1rpb/p$a;')
ERASED=('b','(Ll1rpb/k$f;ILjava/lang/Object;)Ll1rpb/x$a;')
ACC_SYNTHETIC=0x1000
ACC_ABSTRACT=0x0400

class R:
    def __init__(self,b): self.b=bytearray(b); self.p=0
    def u1(self): v=self.b[self.p]; self.p+=1; return v
    def u2(self): v=struct.unpack_from('>H',self.b,self.p)[0]; self.p+=2; return v
    def u4(self): v=struct.unpack_from('>I',self.b,self.p)[0]; self.p+=4; return v
    def skip(self,n): self.p+=n

def parse_cp(data):
    r=R(data)
    if r.u4()!=0xCAFEBABE: raise SystemExit('not class')
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
        else: raise SystemExit(f'unknown cp tag {tag}')
        i+=1
    return r.p,cp

def utf(cp,idx):
    e=cp[idx]
    return e[1].decode('utf-8','replace') if e and e[0]==1 else None

def skip_attrs(data,pos):
    n=struct.unpack_from('>H',data,pos)[0]; pos+=2
    for _ in range(n):
        ln=struct.unpack_from('>I',data,pos+2)[0]
        pos+=6+ln
    return pos

def inventory(data):
    cp_end,cp=parse_cp(data)
    pos=cp_end+6
    ic=struct.unpack_from('>H',data,pos)[0]; pos+=2+2*ic
    fc=struct.unpack_from('>H',data,pos)[0]; pos+=2
    for _ in range(fc): pos=skip_attrs(data,pos+6)
    count_off=pos
    mc=struct.unpack_from('>H',data,pos)[0]; pos+=2
    rows=[]; blocks=[]
    for _ in range(mc):
        st=pos
        flags,ni,di=struct.unpack_from('>HHH',data,pos)
        pos=skip_attrs(data,pos+6)
        raw=bytes(data[st:pos])
        rows.append({'flags':flags,'name_idx':ni,'desc_idx':di,'name':utf(cp,ni),'descriptor':utf(cp,di),'raw':raw})
        blocks.append(raw)
    return cp,count_off,mc,rows,blocks,pos

with zipfile.ZipFile(JAR,'r') as zin:
    raw_map={n:zin.read(n) for n in zin.namelist()}

raw=raw_map[TARGET]
cp,count_off,mc,rows,blocks,methods_end=inventory(raw)
inv={(r['name'],r['descriptor']):r for r in rows}

if ERASED not in inv: raise SystemExit(f'missing erased bridge {ERASED}')
if not (inv[ERASED]['flags'] & ACC_SYNTHETIC): raise SystemExit('erased bridge not synthetic')
if SRC not in inv: raise SystemExit(f'missing typed provider {SRC}')
if inv[SRC]['flags'] & ACC_ABSTRACT: raise SystemExit('typed provider abstract')
if ALIAS in inv: raise SystemExit(f'alias already exists {ALIAS}')

name_idx=next((i for i,e in enumerate(cp) if e and e[0]==1 and e[1]==ALIAS[0].encode()),None)
if name_idx is None: raise SystemExit('missing Utf8 b')

dup=bytearray(inv[SRC]['raw'])
struct.pack_into('>H',dup,2,name_idx)

patched=bytearray()
patched+=raw[:count_off]
patched+=struct.pack('>H',mc+1)
for b in blocks: patched+=b
patched+=dup
patched+=raw[methods_end:]
raw_map[TARGET]=bytes(patched)

with zipfile.ZipFile(JAR,'r') as zin, zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
    for info in zin.infolist():
        zout.writestr(info,raw_map[info.filename])
TMP.replace(JAR)

state={
 'experiment':'BUILDER_CURRENT_B_KF_INT_OBJECT_TYPED_ALIAS_P_A',
 'target_class':TARGET,
 'erased_bridge':{'name':ERASED[0],'descriptor':ERASED[1],'flags':inv[ERASED]['flags']},
 'typed_provider':{'name':SRC[0],'descriptor':SRC[1],'flags':inv[SRC]['flags']},
 'alias':{'name':ALIAS[0],'descriptor':ALIAS[1]},
 'source_collision_scan':'zero b(k$f,int,Object) declarations found across PBMessageALL*.java recovered source',
 'provider_choice':'single donor-proven p$a provider',
 'compile_ref_only':True,
 'donor_jar_changed':False,
 'recovered_source_changed':False,
 'existing_methods_changed':False,
 'added_method_count':1,
 'gameplay_logic_changed':False,
 'pass_signal':'44 current b(k$f,int,Object) obligations disappear or advance; parser/nonprotobuf remain 0'
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
 '# Builder Current b(k$f,int,Object) Alias A/B\n\n'
 '- Adds exactly one recovery compile-ref typed alias at p$a.\n'
 '- Source collision scan: zero exact b(k$f,int,Object) declarations.\n'
 '- Donor provider-map proves synthetic b(k$f,int,Object)->x$a delegates to typed a(k$f,int,Object)->p$a with TBuilderType Signature.\n'
 '- Existing donor methods/source/gameplay unchanged.\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
