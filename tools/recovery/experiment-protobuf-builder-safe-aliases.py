#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
MAP=Path('recovery/protobuf_builder_provider_map.json')
TMP=Path('recovery/compile-ref-protobuf-l1rpb.builder-safe-aliases.jar')
OUT=Path('recovery/protobuf_builder_safe_alias_experiment.json')
MD=Path('recovery/PROTOBUF_BUILDER_SAFE_ALIAS_EXPERIMENT.md')

ACC_ABSTRACT=0x0400

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

m=json.loads(MAP.read_text(encoding='utf-8'))
safe=m.get('safe_typed_aliases',[])
if len(safe)!=21:
    raise SystemExit(f'expected exactly 21 safe aliases from provider map, got {len(safe)}')

by_owner={}
for x in safe:
    by_owner.setdefault(x['provider_owner'],[]).append(x)

with zipfile.ZipFile(JAR,'r') as zin:
    jar_names=zin.namelist()
    raw_map={n:zin.read(n) for n in jar_names}

results=[]
for owner,items in sorted(by_owner.items()):
    cls=owner.replace('.','/')+'.class'
    if cls not in raw_map:
        raise SystemExit(f'missing class {cls}')

    original=raw_map[cls]
    cp,count_off,mc,rows,blocks,methods_end=inventory(original)
    inv={(r['name'],r['descriptor']):r for r in rows}
    utf_indices={}
    for i,e in enumerate(cp):
        if e and e[0]==1:
            utf_indices.setdefault(e[1].decode('utf-8','replace'),i)

    add=[]
    for x in items:
        src=(x['typed_target_name'],x['typed_target_descriptor'])
        alias=(x['alias_name'],x['alias_descriptor'])

        if alias in inv:
            results.append({**x,'status':'ALREADY_PRESENT'})
            continue
        if src not in inv:
            raise SystemExit(f'{owner}: missing typed target {src}')
        tr=inv[src]
        if tr['flags'] & ACC_ABSTRACT:
            raise SystemExit(f'{owner}: typed target unexpectedly abstract {src}')
        if not x.get('typed_target_signatures') or not any('TBuilderType;' in s for s in x['typed_target_signatures']):
            raise SystemExit(f'{owner}: missing TBuilderType proof for {alias}')
        name_idx=utf_indices.get(x['alias_name'])
        if not name_idx:
            raise SystemExit(f'{owner}: missing Utf8 for alias name {x["alias_name"]}')

        dup=bytearray(tr['raw'])
        struct.pack_into('>H',dup,2,name_idx)
        add.append(bytes(dup))
        inv[alias]={'flags':tr['flags'],'name':alias[0],'descriptor':alias[1],'raw':bytes(dup)}
        results.append({**x,'status':'ADDED'})

    if add:
        patched=bytearray()
        patched+=original[:count_off]
        patched+=struct.pack('>H',mc+len(add))
        for b in blocks: patched+=b
        for b in add: patched+=b
        patched+=original[methods_end:]
        raw_map[cls]=bytes(patched)

with zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
    for name in jar_names:
        info=None
        # preserve original metadata where possible
        with zipfile.ZipFile(JAR,'r') as zin:
            info=zin.getinfo(name)
        zout.writestr(info,raw_map[name])
TMP.replace(JAR)

added=[x for x in results if x['status']=='ADDED']
present=[x for x in results if x['status']=='ALREADY_PRESENT']
state={
 'experiment':'BUILDER_XA_SAFE_TYPED_ALIAS_SET',
 'safe_alias_input_count':len(safe),
 'added_alias_count':len(added),
 'already_present_count':len(present),
 'results':results,
 'selection_rule':m.get('selection_rule'),
 'compile_ref_only':True,
 'donor_jar_changed':False,
 'recovered_source_changed':False,
 'existing_methods_changed':False,
 'added_methods_only':True,
 'gameplay_logic_changed':False,
 'parser_scope_changed':False,
 'pass_signal':'builder 44 errors disappear or advance with parser=0 and nonprotobuf=0',
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
 '# Builder Safe Typed Alias Experiment\n\n'
 + f'- Safe aliases from donor proof: **{len(safe)}**\n'
 + f'- Added this step: **{len(added)}**\n'
 + f'- Already present: **{len(present)}**\n'
 + '- Recovery compile-ref only; source and donor JAR unchanged.\n'
 + '- Only methods proven by exact synthetic provider + same-parameter delegate + concrete TBuilderType target are included.\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
