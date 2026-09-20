#!/usr/bin/env python3
import json,struct,zipfile
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
TMP=Path('recovery/compile-ref-protobuf-l1rpb.sigexp.jar')
OUT=Path('recovery/protobuf_ab_signature_experiment.json')
TARGET='l1rpb/ab.class'

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

def attrs(data,pos,count,utf,strip=False):
    kept=[]; removed=0
    for _ in range(count):
        st=pos; ni=struct.unpack_from('>H',data,pos)[0]; ln=struct.unpack_from('>I',data,pos+2)[0]; pos+=6+ln
        if strip and utf.get(ni)==b'Signature': removed+=1
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
        aa,pos,_=attrs(data,pos,ac,utf,False); out+=aa
    mc=struct.unpack_from('>H',data,pos)[0]; out+=data[pos:pos+2]; pos+=2; rem=0
    for _ in range(mc):
        out+=data[pos:pos+6]; pos+=6; ac=struct.unpack_from('>H',data,pos)[0]; pos+=2
        aa,pos,n=attrs(data,pos,ac,utf,True); rem+=n; out+=aa
    cc=struct.unpack_from('>H',data,pos)[0]; pos+=2
    aa,pos,_=attrs(data,pos,cc,utf,False); out+=aa
    if pos!=len(data): raise ValueError(f'parse ended {pos}/{len(data)}')
    return bytes(out),rem

removed=0
with zipfile.ZipFile(JAR,'r') as zin, zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
    for info in zin.infolist():
        raw=zin.read(info.filename)
        if info.filename==TARGET: raw,removed=patch(raw)
        zout.writestr(info,raw)
if removed<=0:
    TMP.unlink(missing_ok=True); raise SystemExit('no method Signature attrs removed from ab.class')
TMP.replace(JAR)
state={'target':TARGET,'method_signature_attrs_removed':removed,'class_signature_retained':True,'descriptors_changed':False,'bytecode_changed':False,'gameplay_logic_changed':False,'experiment_scope':'recovery compile-ref only'}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
print(json.dumps(state,indent=2))
