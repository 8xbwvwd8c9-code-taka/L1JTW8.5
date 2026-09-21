#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
TMP=Path('recovery/compile-ref-protobuf-l1rpb.obligationfix.jar')
OUT=Path('recovery/protobuf_abstract_obligation_normalization.json')
MD=Path('recovery/PROTOBUF_ABSTRACT_OBLIGATION_NORMALIZATION.md')

ACC_ABSTRACT=0x0400
PAIRS=[
  ('l1rpb/ab.class','l1rpb/c.class','parser'),
  ('l1rpb/b$a.class','l1rpb/p$a.class','builder'),
]

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

def method_inventory(data):
  r=R(data); cp=parse_cp(r)
  r.skip(6)
  r.skip(2*r.u2())
  for _ in range(r.u2()): skip_member(r)
  out={}
  for _ in range(r.u2()):
    flags=r.u2(); ni=r.u2(); di=r.u2()
    name=utf(cp,ni); desc=utf(cp,di)
    skip_attrs(r)
    out[(name,desc)]=flags
  return out

def prune_parent(data, removable):
  r=R(data); cp=parse_cp(r)
  r.skip(6)
  r.skip(2*r.u2())
  fc=r.u2()
  for _ in range(fc): skip_member(r)
  methods_count_pos=r.p; mc=r.u2()
  kept=[]; removed=[]
  for _ in range(mc):
    st=r.p
    flags=r.u2(); ni=r.u2(); di=r.u2()
    name=utf(cp,ni); desc=utf(cp,di)
    skip_attrs(r)
    raw=data[st:r.p]
    key=(name,desc)
    if key in removable:
      if not (flags & ACC_ABSTRACT):
        raise SystemExit(f'candidate parent method not abstract: {name}{desc} flags={flags:#x}')
      removed.append({'name':name,'descriptor':desc,'flags':flags})
    else:
      kept.append(raw)
  tail=data[r.p:]
  out=bytearray(data[:methods_count_pos])
  out+=struct.pack('>H',len(kept))
  for raw in kept: out+=raw
  out+=tail
  return bytes(out),removed

if not JAR.exists(): raise SystemExit(f'missing {JAR}')
with zipfile.ZipFile(JAR,'r') as zin:
  raw_by_name={n:zin.read(n) for n in zin.namelist()}

plans=[]
for parent,child,role in PAIRS:
  if parent not in raw_by_name or child not in raw_by_name:
    raise SystemExit(f'missing pair classes: {parent} / {child}')
  pm=method_inventory(raw_by_name[parent])
  cm=method_inventory(raw_by_name[child])
  removable=set()
  for key,pflags in pm.items():
    cflags=cm.get(key)
    if (pflags & ACC_ABSTRACT) and cflags is not None and not (cflags & ACC_ABSTRACT):
      removable.add(key)
  plans.append({'parent':parent,'child':child,'role':role,'removable':sorted(removable)})

if not all(x['removable'] for x in plans):
  raise SystemExit(f'empty derived obligation set: {plans}')

hits=[]
with zipfile.ZipFile(JAR,'r') as zin, zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
  plan_by_parent={x['parent']:x for x in plans}
  for info in zin.infolist():
    raw=zin.read(info.filename)
    if info.filename in plan_by_parent:
      plan=plan_by_parent[info.filename]
      raw,removed=prune_parent(raw,set(tuple(x) for x in plan['removable']))
      hits += [{'parent':info.filename,'child':plan['child'],'role':plan['role'],**x} for x in removed]
    zout.writestr(info,raw)

expected=sum(len(x['removable']) for x in plans)
if len(hits)!=expected:
  TMP.unlink(missing_ok=True)
  raise SystemExit(f'expected {expected} derived obligations removed, got {len(hits)}')
TMP.replace(JAR)

state={
  'derivation':'remove only parent abstract methods with exact same name+descriptor concrete implementation in paired runtime child',
  'pairs':plans,
  'expected_obligations':expected,
  'removed_obligations':len(hits),
  'targets':hits,
  'scope':'recovery compile reference only',
  'donor_jar_changed':False,
  'recovered_source_changed':False,
  'method_bytecode_changed':False,
  'method_descriptors_changed':False,
  'gameplay_logic_changed':False,
  'normalization_required_for_donor_compare':True,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
  '# Protobuf Abstract Obligation Normalization\n\n'
  + f'- Derived obligations removed: **{len(hits)} / {expected}**\n'
  + '- Rule: parent method is abstract AND paired child contains exact same name+descriptor as concrete binary method.\n'
  + '- Pairs: `ab -> c` (parser), `b$a -> p$a` (builder).\n'
  + '- Donor JAR changed: **NO**\n'
  + '- Recovered game source changed: **NO**\n'
  + '- Method descriptors / bytecode changed: **NO / NO**\n'
  + '- Gameplay logic changed: **NO**\n'
  + '- Scope: recovery compile reference only.\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
