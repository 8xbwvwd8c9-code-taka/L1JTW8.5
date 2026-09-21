#!/usr/bin/env python3
import json, struct, zipfile
from pathlib import Path

JAR=Path('recovery/compile-ref-protobuf-l1rpb.jar')
TMP=Path('recovery/compile-ref-protobuf-l1rpb.obligationfix.jar')
OUT=Path('recovery/protobuf_abstract_obligation_normalization.json')
MD=Path('recovery/PROTOBUF_ABSTRACT_OBLIGATION_NORMALIZATION.md')

ACC_ABSTRACT=0x0400

# One already-verified source-representation obligation:
# generated builders provide covariant implementations, but javac cannot
# represent the obfuscated/raw BuilderType relationship cleanly.
EXACT_REMOVE={
  'l1rpb/b$a.class': {('b','(Ll1rpb/h;Ll1rpb/n;)Ll1rpb/b$a;')},
}

# ABI-derived obligation pairs.  If an abstract method in the left class has an
# exact name+descriptor concrete method in the right class, the donor JVM
# inheritance path already provides the implementation.  Recovery javac can
# nevertheless reject it after source/namespace normalization, so the abstract
# compile-time obligation is removed from the recovery-only reference.
PAIR_RULES=[
  ('l1rpb/ab.class','l1rpb/c.class'),
  ('l1rpb/y$a.class','l1rpb/b$a.class'),
  ('l1rpb/a$a.class','l1rpb/p$a.class'),
]

# Only prune the currently observed source-unrepresentable family.
# Other exact parent/child pairs are real public parser/builder API and must
# remain visible because generated source calls them directly.
SAFE_DERIVED={
  'l1rpb/ab.class': {
    ('e','(Ljava/io/InputStream;)Ljava/lang/Object;'),
    ('e','(Ljava/io/InputStream;Ll1rpb/n;)Ljava/lang/Object;'),
    ('f','(Ljava/io/InputStream;Ll1rpb/n;)Ljava/lang/Object;'),
  },
  'l1rpb/y$a.class': {
    ('d','(Ljava/io/InputStream;)Ll1rpb/y$a;'),
    ('d','(Ljava/io/InputStream;Ll1rpb/n;)Ll1rpb/y$a;'),
  },
  'l1rpb/a$a.class': {
    ('d','()Ll1rpb/a$a;'),
  },
}

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

def read_methods(data):
  r=R(data); cp=parse_cp(r)
  r.skip(6)
  r.skip(2*r.u2())
  for _ in range(r.u2()):
    r.skip(6); skip_attrs(r)
  rows=[]
  for _ in range(r.u2()):
    flags=r.u2(); ni=r.u2(); di=r.u2()
    name=utf(cp,ni); desc=utf(cp,di)
    rows.append({'name':name,'descriptor':desc,'flags':flags,'abstract':bool(flags & ACC_ABSTRACT)})
    skip_attrs(r)
  return rows

def skip_member(r):
  r.skip(6); skip_attrs(r)

def patch_class(data,wanted):
  r=R(data); cp=parse_cp(r)
  r.skip(6)
  r.skip(2*r.u2())
  fc=r.u2()
  for _ in range(fc): skip_member(r)
  methods_count_pos=r.p
  mc=r.u2()
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
  tail=data[r.p:]
  out=bytearray()
  out+=data[:methods_count_pos]
  out+=struct.pack('>H',len(kept))
  for raw in kept: out+=raw
  out+=tail
  return bytes(out),removed

if not JAR.exists(): raise SystemExit(f'missing {JAR}')

with zipfile.ZipFile(JAR,'r') as zin:
  raw_map={name:zin.read(name) for name in zin.namelist() if name.endswith('.class')}

derived={}
pair_details=[]
for abstract_cls, concrete_cls in PAIR_RULES:
  if abstract_cls not in raw_map or concrete_cls not in raw_map:
    raise SystemExit(f'missing pair classes: {abstract_cls} / {concrete_cls}')
  am=read_methods(raw_map[abstract_cls])
  cm=read_methods(raw_map[concrete_cls])
  concrete={(x['name'],x['descriptor']) for x in cm if not x['abstract']}
  paired_all={(x['name'],x['descriptor']) for x in am if x['abstract'] and (x['name'],x['descriptor']) in concrete}
  selected=paired_all & SAFE_DERIVED.get(abstract_cls,set())
  if not selected:
    raise SystemExit(f'no safe ABI-derived obligations found for {abstract_cls} <- {concrete_cls}')
  missing_safe=SAFE_DERIVED.get(abstract_cls,set())-paired_all
  if missing_safe:
    raise SystemExit(f'safe obligations missing donor concrete evidence for {abstract_cls}: {sorted(missing_safe)}')
  derived.setdefault(abstract_cls,set()).update(selected)
  pair_details.append({
    'abstract_class':abstract_cls,
    'concrete_provider':concrete_cls,
    'paired_count_all':len(paired_all),
    'selected_count':len(selected),
    'selected_methods':[{'name':n,'descriptor':d} for n,d in sorted(selected)],
  })

targets={k:set(v) for k,v in EXACT_REMOVE.items()}
for cls,methods in derived.items():
  targets.setdefault(cls,set()).update(methods)

# Mandatory evidence for the currently observed 88-error family.
required={
  ('l1rpb/ab.class','e','(Ljava/io/InputStream;)Ljava/lang/Object;'),
  ('l1rpb/ab.class','e','(Ljava/io/InputStream;Ll1rpb/n;)Ljava/lang/Object;'),
  ('l1rpb/y$a.class','d','(Ljava/io/InputStream;)Ll1rpb/y$a;'),
  ('l1rpb/y$a.class','d','(Ljava/io/InputStream;Ll1rpb/n;)Ll1rpb/y$a;'),
  ('l1rpb/ab.class','f','(Ljava/io/InputStream;Ll1rpb/n;)Ljava/lang/Object;'),
  ('l1rpb/a$a.class','d','()Ll1rpb/a$a;'),
}
actual={(cls,n,d) for cls,methods in targets.items() for n,d in methods}
missing_required=sorted(required-actual)
if missing_required:
  raise SystemExit(f'missing required derived obligations: {missing_required}')

hits=[]
with zipfile.ZipFile(JAR,'r') as zin, zipfile.ZipFile(TMP,'w',zipfile.ZIP_DEFLATED) as zout:
  for info in zin.infolist():
    raw=zin.read(info.filename); removed=[]
    if info.filename in targets:
      raw,removed=patch_class(raw,targets[info.filename])
      hits += [{'class':info.filename,**x} for x in removed]
    zout.writestr(info,raw)

expected=sum(len(v) for v in targets.values())
if len(hits)!=expected:
  TMP.unlink(missing_ok=True)
  raise SystemExit(f'expected {expected} abstract obligations removed, got {len(hits)}')
TMP.replace(JAR)

state={
  'exact_obligations':sum(len(v) for v in EXACT_REMOVE.values()),
  'derived_pair_rules':pair_details,
  'expected_obligations':expected,
  'removed_obligations':len(hits),
  'targets':hits,
  'required_current_family_present':True,
  'scope':'recovery compile reference only',
  'donor_jar_changed':False,
  'recovered_source_changed':False,
  'method_bytecode_changed':False,
  'gameplay_logic_changed':False,
  'reason':'exact donor ABI concrete implementations satisfy JVM inheritance, while normalized Java source cannot express the obfuscated generic/covariant obligation cleanly',
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
  '# Protobuf Abstract Obligation Normalization\n\n'
  + f'- Removed compile-ref abstract obligations: **{len(hits)} / {expected}**\n'
  + f'- Exact special-case obligations: **{state["exact_obligations"]}**\n'
  + f'- ABI-derived pair rules: **{len(pair_details)}**\n'
  + '- Derived pruning is restricted to exact donor ABI obligations with concrete providers, including the final parser f(InputStream,n) and builder d() family.\n'
  + '- Required current parser/builder bridge obligation family present: **YES**\n'
  + '- Donor JAR changed: **NO**\n'
  + '- Recovered game source changed: **NO**\n'
  + '- Method bytecode changed: **NO**\n'
  + '- Gameplay logic changed: **NO**\n'
  + '- Scope: recovery compile reference only.\n'
  + '- Final ABI validation remains against the unmodified donor runtime identity.\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
