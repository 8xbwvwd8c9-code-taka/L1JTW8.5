#!/usr/bin/env python3
import json,struct,zipfile
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
TMP=Path('recovery/compile-ref-protobuf-l1rpb.parserraw.jar')
OUT=Path('recovery/protobuf_parser_class_signature_experiment.json')
TARGET='l1rpb/c.class'

def cp_end_utf8(data):
  pos=8; n=struct.unpack_from('>H',data,pos)[0]; pos+=2; utf={}; i=1
  while i<n:
    tag=data[pos]; pos+=1
    if tag==1:
      ln=struct.unpack_from('>H',data,pos)[0]; pos+=2; utf[i]=data[pos:pos+ln]; pos+=ln
    elif tag in (3,4): pos+=4
    elif tag in (5,6): pos+=8; i+=1
    elif tag in (7,8,16,19,20): pos+=2
    elif tag in (9,10,11,12,17,18): pos+=4
    elif tag==15: pos+=3
    else: raise ValueError(f'cp tag {tag}')
    i+=1
  return pos,utf

def copy_attrs(data,pos,count,utf,strip_sig=False):
  kept=[]; removed=0
  for _ in range(count):
    st=pos; ni=struct.unpack_from('>H',data,pos)[0]; ln=struct.unpack_from('>I',data,pos+2)[0]; pos+=6+ln
    if strip_sig and utf.get(ni)==b'Signature': removed+=1
    else: kept.append(data[st:pos])
  out=bytearray(struct.pack('>H',len(kept)))
  for x in kept: out+=x
  return bytes(out),pos,removed

def patch(data):
  cp_end,utf=cp_end_utf8(data); pos=cp_end; out=bytearray(data[:cp_end])
  out+=data[pos:pos+6]; pos+=6
  ic=struct.unpack_from('>H',data,pos)[0]; out+=data[pos:pos+2+2*ic]; pos+=2+2*ic
  fc=struct.unpack_from('>H',data,pos)[0]; out+=data[pos:pos+2]; pos+=2
  for _ in range(fc):
    out+=data[pos:pos+6]; pos+=6; ac=struct.unpack_from('>H',data,pos)[0]; pos+=2
    attrs,pos,_=copy_attrs(data,pos,ac,utf,False); out+=attrs
  mc=struct.unpack_from('>H',data,pos)[0]; out+=data[pos:pos+2]; pos+=2
  for _ in range(mc):
    out+=data[pos:pos+6]; pos+=6; ac=struct.unpack_from('>H',data,pos)[0]; pos+=2
    attrs,pos,_=copy_attrs(data,pos,ac,utf,False); out+=attrs
  cc=struct.unpack_from('>H',data,pos)[0]; pos+=2
  attrs,pos,removed=copy_attrs(data,pos,cc,utf,True); out+=attrs
  if pos!=len(data): raise ValueError(f'parse ended {pos}/{len(data)}')
  return bytes(out),removed

removed=0
with zipfile.ZipFile(JAR,'r') as zin, zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
  for info in zin.infolist():
    raw=zin.read(info.filename)
    if info.filename==TARGET: raw,removed=patch(raw)
    zout.writestr(info,raw)
if removed!=1:
  TMP.unlink(missing_ok=True); raise SystemExit(f'expected exactly 1 class Signature removed from c.class, got {removed}')
TMP.replace(JAR)
state={
 'target':TARGET,
 'class_signature_attrs_removed':removed,
 'method_signature_attrs_removed':0,
 'interface_table_changed':False,
 'method_descriptors_changed':False,
 'bytecode_changed':False,
 'gameplay_logic_changed':False,
 'scope':'recovery compile reference only',
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
print(json.dumps(state,indent=2))
