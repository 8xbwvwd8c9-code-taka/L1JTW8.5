#!/usr/bin/env python3
import json,struct,zipfile
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
TMP=Path('recovery/compile-ref-protobuf-l1rpb.innerfix.jar')
OUT=Path('recovery/protobuf_inner_visibility_normalization.json')
MD=Path('recovery/PROTOBUF_INNER_VISIBILITY_NORMALIZATION.md')
TARGET=b'l1rpb/p$b'

def parse_cp(data):
    pos=8
    cp_count=struct.unpack_from('>H',data,pos)[0]; pos+=2
    cp=[None]*cp_count
    i=1
    while i<cp_count:
        tag=data[pos]; pos+=1
        if tag==1:
            n=struct.unpack_from('>H',data,pos)[0]; pos+=2
            cp[i]=(tag,data[pos:pos+n]); pos+=n
        elif tag in (3,4): cp[i]=(tag,None); pos+=4
        elif tag in (5,6): cp[i]=(tag,None); pos+=8; i+=1
        elif tag in (7,8,16,19,20):
            idx=struct.unpack_from('>H',data,pos)[0]; cp[i]=(tag,idx); pos+=2
        elif tag in (9,10,11,12,17,18): cp[i]=(tag,None); pos+=4
        elif tag==15: cp[i]=(tag,None); pos+=3
        else: raise ValueError(f'unknown cp tag {tag} at {i}')
        i+=1
    return pos,cp

def class_name(cp,class_idx):
    if not class_idx: return None
    tag,name_idx=cp[class_idx]
    if tag!=7: return None
    utag,raw=cp[name_idx]
    return raw if utag==1 else None

def skip_members(data,pos,count):
    for _ in range(count):
        pos+=6
        ac=struct.unpack_from('>H',data,pos)[0]; pos+=2
        for _ in range(ac):
            ln=struct.unpack_from('>I',data,pos+2)[0]
            pos+=6+ln
    return pos

def patch_class(data):
    cp_end,cp=parse_cp(data)
    pos=cp_end
    pos+=6 # access,this,super
    ic=struct.unpack_from('>H',data,pos)[0]; pos+=2+2*ic
    fc=struct.unpack_from('>H',data,pos)[0]; pos+=2
    pos=skip_members(data,pos,fc)
    mc=struct.unpack_from('>H',data,pos)[0]; pos+=2
    pos=skip_members(data,pos,mc)
    class_ac=struct.unpack_from('>H',data,pos)[0]; pos+=2
    out=bytearray(data)
    changed=0
    details=[]
    for _ in range(class_ac):
        name_idx=struct.unpack_from('>H',data,pos)[0]
        ln=struct.unpack_from('>I',data,pos+2)[0]
        payload=pos+6
        name=cp[name_idx][1] if cp[name_idx] and cp[name_idx][0]==1 else b''
        if name==b'InnerClasses':
            n=struct.unpack_from('>H',data,payload)[0]
            ep=payload+2
            for _ in range(n):
                inner_idx,outer_idx,inner_name_idx,flags=struct.unpack_from('>HHHH',data,ep)
                if class_name(cp,inner_idx)==TARGET:
                    new_flags=(flags & ~0x0004) | 0x0001
                    if new_flags!=flags:
                        struct.pack_into('>H',out,ep+6,new_flags)
                        changed+=1
                        details.append({
                          'old_flags':flags,
                          'new_flags':new_flags,
                          'outer':(class_name(cp,outer_idx) or b'').decode('utf-8','replace'),
                        })
                ep+=8
        pos+=6+ln
    return bytes(out),changed,details

if not JAR.exists(): raise SystemExit(f'missing {JAR}')
total=0; classes=[]
with zipfile.ZipFile(JAR,'r') as zin, zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
    for info in zin.infolist():
        raw=zin.read(info.filename)
        if info.filename.endswith('.class'):
            raw,n,details=patch_class(raw)
            if n:
                total+=n; classes.append({'class':info.filename,'entries_changed':n,'details':details})
        zout.writestr(info,raw)
TMP.replace(JAR)
state={
  'target_inner_class':'l1rpb/p$b',
  'entries_changed':total,
  'classes_changed':classes,
  'transform':'InnerClasses ACC_PROTECTED -> ACC_PUBLIC',
  'class_access_flags_changed':False,
  'method_descriptors_changed':False,
  'bytecode_changed':False,
  'gameplay_logic_changed':False,
  'recovery_compile_ref_only':True,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
  '# Protobuf Inner Visibility Normalization\n\n'
  f'- InnerClasses entries widened: **{total}**\n'
  '- Target: `l1rpb/p$b` only.\n'
  '- `ACC_PROTECTED` -> `ACC_PUBLIC` in InnerClasses metadata only.\n'
  '- Class access flags changed: **NO** (class is already public).\n'
  '- Method descriptors / bytecode changed: **NO / NO**\n'
  '- Gameplay logic changed: **NO**\n'
  '- Scope: recovery compile reference only.\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
if total==0: raise SystemExit('no p$b InnerClasses entries patched')
