#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
TMP=Path('recovery/compile-ref-protobuf-l1rpb.builder-e-alias.jar')
OUT=Path('recovery/protobuf_builder_e_typed_alias_experiment.json')
MD=Path('recovery/PROTOBUF_BUILDER_E_TYPED_ALIAS_EXPERIMENT.md')

TARGET_CLASS='l1rpb/a$a.class'
ACC_ABSTRACT=0x0400
ACC_SYNTHETIC=0x1000

PAIRS=[
    {
      'bridge':('e','(Ljava/io/InputStream;)Ll1rpb/x$a;'),
      'typed':('a','(Ljava/io/InputStream;)Ll1rpb/a$a;'),
    },
    {
      'bridge':('e','(Ljava/io/InputStream;Ll1rpb/n;)Ll1rpb/x$a;'),
      'typed':('a','(Ljava/io/InputStream;Ll1rpb/n;)Ll1rpb/a$a;'),
    },
]

class R:
    def __init__(self,b): self.b=bytearray(b); self.p=0
    def u1(self): v=self.b[self.p]; self.p+=1; return v
    def u2(self): v=struct.unpack_from('>H',self.b,self.p)[0]; self.p+=2; return v
    def u4(self): v=struct.unpack_from('>I',self.b,self.p)[0]; self.p+=4; return v
    def skip(self,n): self.p+=n

def parse_cp(data):
    r=R(data)
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
    return r.p,cp

def utf(cp,idx):
    e=cp[idx]
    if not e or e[0]!=1: return None
    return e[1].decode('utf-8','replace')

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
        rows.append({
          'flags':flags,'name_idx':ni,'desc_idx':di,
          'name':utf(cp,ni),'descriptor':utf(cp,di),'raw':raw,
        })
        blocks.append(raw)
    return cp,count_off,mc,rows,blocks,pos

with zipfile.ZipFile(JAR,'r') as zin:
    names=zin.namelist()
    if TARGET_CLASS not in names:
        raise SystemExit(f'missing {TARGET_CLASS}')
    original=zin.read(TARGET_CLASS)

cp,count_off,mc,rows,blocks,methods_end=inventory(original)
inv={(r['name'],r['descriptor']):r for r in rows}
utf_indices={}
for i,e in enumerate(cp):
    if e and e[0]==1:
        utf_indices.setdefault(e[1].decode('utf-8','replace'),i)

aliases=[]
alias_blocks=[]
for pair in PAIRS:
    bridge=pair['bridge']; typed=pair['typed']
    if bridge not in inv:
        raise SystemExit(f'missing erased interface provider bridge {bridge}')
    if typed not in inv:
        raise SystemExit(f'missing typed generic provider {typed}')
    br=inv[bridge]; tr=inv[typed]
    if not (br['flags'] & ACC_SYNTHETIC):
        raise SystemExit(f'expected bridge ACC_SYNTHETIC: {bridge} flags={br["flags"]:#x}')
    if br['flags'] & ACC_ABSTRACT:
        raise SystemExit(f'bridge unexpectedly abstract: {bridge}')
    if tr['flags'] & ACC_ABSTRACT:
        raise SystemExit(f'typed provider unexpectedly abstract: {typed}')

    alias=(bridge[0],typed[1])
    if alias in inv:
        raise SystemExit(f'typed alias already exists: {alias}')
    name_idx=utf_indices.get(bridge[0])
    if not name_idx:
        raise SystemExit(f'missing Utf8 name for {bridge[0]}')

    dup=bytearray(tr['raw'])
    struct.pack_into('>H',dup,2,name_idx)
    alias_blocks.append(bytes(dup))
    aliases.append({
      'bridge_name':bridge[0],
      'bridge_descriptor':bridge[1],
      'bridge_flags':br['flags'],
      'typed_provider_name':typed[0],
      'typed_provider_descriptor':typed[1],
      'typed_provider_flags':tr['flags'],
      'alias_name':alias[0],
      'alias_descriptor':alias[1],
      'typed_provider_bytecode_and_signature_copied_exactly':True,
    })

patched=bytearray()
patched+=original[:count_off]
patched+=struct.pack('>H',mc+len(alias_blocks))
for b in blocks: patched+=b
for b in alias_blocks: patched+=b
patched+=original[methods_end:]

with zipfile.ZipFile(JAR,'r') as zin, zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
    for info in zin.infolist():
        raw=bytes(patched) if info.filename==TARGET_CLASS else zin.read(info.filename)
        zout.writestr(info,raw)
TMP.replace(JAR)

state={
 'experiment':'BUILDER_A_A_TYPED_E_ALIAS_FROM_A',
 'class':TARGET_CLASS,
 'aliases':aliases,
 'selection_rule':'synthetic x$a-return e(InputStream[,n]) exists + concrete a$a-return a(InputStream[,n]) exists',
 'compile_ref_only':True,
 'donor_jar_changed':False,
 'recovered_source_changed':False,
 'existing_method_names_changed':False,
 'existing_method_descriptors_changed':False,
 'existing_bytecode_changed':False,
 'added_alias_method_count':len(aliases),
 'gameplay_logic_changed':False,
 'baseline':'44 builder / 0 parser / 0 nonprotobuf errors',
 'pass_signal':'current e(InputStream,n) builder obligation disappears or advances without parser/nonprotobuf regression',
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
 '# Builder Typed e Provider Alias Experiment\n\n'
 + f'- Aliases added: **{len(aliases)}**\n'
 + '- Recovery compile-ref only.\n'
 + '- Existing donor methods/bytecode unchanged.\n'
 + '- Requires exact synthetic bridge + concrete typed provider evidence before mutation.\n'
 + '- Gameplay/source/protocol logic unchanged.\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
