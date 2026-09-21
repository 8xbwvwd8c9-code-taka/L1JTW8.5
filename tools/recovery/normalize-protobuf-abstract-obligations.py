#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
TMP=Path('recovery/compile-ref-protobuf-l1rpb.obligationfix.jar')
OUT=Path('recovery/protobuf_abstract_obligation_normalization.json')
MD=Path('recovery/PROTOBUF_ABSTRACT_OBLIGATION_NORMALIZATION.md')

TARGETS={
  'l1rpb/b$a.class': {('b','(Ll1rpb/h;Ll1rpb/n;)Ll1rpb/b$a;')},
  'l1rpb/ab.class': {('e','(Ljava/io/InputStream;Ll1rpb/n;)Ljava/lang/Object;')},
}
ACC_ABSTRACT=0x0400

class R:
  def __init__(self,b): self.b=b; self.p=0
  def u1(self): v=self.b[self.p]; self.p+=1; return v
  def u2(self): v=struct.unpack_from('>H',self.b,self.p)[0]; self.p+=2; return v
  def u4(self): v=struct.unpack_from('>I',self.b,self.p)[0]; self.p+=4; return v
  def skip(self,n): self.p+=n

def parse_cp(r):
  if r.u4()!=0xCAFEBABE: raise ValueError('not class')
  r.skip(4)
  count=r.u2(); cp=[None]*count; i=1
  while i<count:
    tag=r.u1()
    if tag==1:
      n=r.u2(); raw=r.b[r.p:r.p+n]; r.skip(n); cp[i]=(1,raw)
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

def skip_member(r):
  r.skip(6); skip_attrs(r)

def patch_class(data,wanted):
  r=R(data); cp=parse_cp(r); cp_end=r.p
  prefix_end=cp_end
  r.skip(6)
  r.skip(2*r.u2())
  fields_count_pos=r.p; fc=r.u2()
  for _ in range(fc): skip_member(r)
  methods_count_pos=r.p; mc=r.u2()
  methods_start=r.p
  kept=[]; removed=[]
  for _ in range(mc):
    st=r.p
    flags=r.u2(); ni=r.u2(); di=r.u2()
    name=utf(cp,ni); desc=utf(cp,di)
    skip_attrs(r)
    raw=data[st:r.p]
    key=(name,desc)
    if key in wanted:
      if not (flags & ACC_ABSTRACT):
        raise SystemExit(f'target method is not abstract before pruning: {name}{desc} flags={flags:#x}')
      removed.append({'name':name,'descriptor':desc,'flags':flags})
    else:
      kept.append(raw)
  methods_end=r.p
  # preserve class attributes untouched
  tail=data[methods_end:]
  out=bytearray()
  out+=data[:methods_count_pos]
  out+=struct.pack('>H',len(kept))
  for raw in kept: out+=raw
  out+=tail
  return bytes(out),removed

if not JAR.exists(): raise SystemExit(f'missing {JAR}')
hits=[]
with zipfile.ZipFile(JAR,'r') as zin, zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
  for info in zin.infolist():
    raw=zin.read(info.filename); removed=[]
    if info.filename in TARGETS:
      raw,removed=patch_class(raw,TARGETS[info.filename])
      hits += [{'class':info.filename,**x} for x in removed]
    zout.writestr(info,raw)

expected=sum(len(v) for v in TARGETS.values())
if len(hits)!=expected:
  TMP.unlink(missing_ok=True)
  raise SystemExit(f'expected {expected} abstract obligations removed, got {len(hits)}')
TMP.replace(JAR)

state={
  'expected_obligations':expected,
  'removed_obligations':len(hits),
  'targets':hits,
  'scope':'recovery compile reference only',
  'donor_jar_changed':False,
  'recovered_source_changed':False,
  'method_bytecode_changed':False,
  'gameplay_logic_changed':False,
  'reason':'JVM-valid erased abstract obligations are not representable by recovered Java source after documented normalization',
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
  '# Protobuf Abstract Obligation Normalization\n\n'
  + f'- Removed compile-ref abstract obligations: **{len(hits)} / {expected}**\n'
  + '- Donor JAR changed: **NO**\n'
  + '- Recovered game source changed: **NO**\n'
  + '- Method bytecode changed: **NO**\n'
  + '- Gameplay logic changed: **NO**\n'
  + '- Scope: recovery compile reference only.\n'
  + '- Final ABI validation remains against the unmodified donor runtime identity.\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
