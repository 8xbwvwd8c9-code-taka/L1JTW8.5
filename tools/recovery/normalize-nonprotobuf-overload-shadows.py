#!/usr/bin/env python3
import json,re
from pathlib import Path

OUT=Path('recovery/nonprotobuf_overload_shadow_normalization.json')
MD=Path('recovery/NONPROTOBUF_OVERLOAD_SHADOW_NORMALIZATION.md')
changes=[]

def load(rel):
    p=Path('_normalized-stage-src')/rel
    return p,p.read_text(encoding='utf-8',errors='replace')

# L1Character: L1PcInstance.b(L1Character):void shadows inherited b(L1Object):boolean.
p,text=load('l1r/aq/L1Character.java')
rx=re.compile(r'\b(var\d+)\.b\(this\)')
text,n=rx.subn(r'\1.b((L1Object)this)',text)
if n!=4: raise SystemExit(f'L1Character visibility cast count {n} != 4')
p.write_text(text,encoding='utf-8'); changes.append({'file':'l1r/aq/L1Character.java','sites':n,'target':'b(L1Object):boolean'})

# L1MonsterInstance: same visibility overload shadow through L1PcInstance receiver.
p,text=load('l1r/ap/L1MonsterInstance.java')
text,n=rx.subn(r'\1.b((L1Object)this)',text)
if n!=4: raise SystemExit(f'L1MonsterInstance visibility cast count {n} != 4')
p.write_text(text,encoding='utf-8'); changes.append({'file':'l1r/ap/L1MonsterInstance.java','sites':n,'target':'b(L1Object):boolean'})

# L1PcInstance: boolean context must select inherited d(L1Character):boolean, not monster d(L1PcInstance):void.
p,text=load('l1r/ap/L1PcInstance.java')
old='if (var4.d(this)) {'; new='if (var4.d((L1Character)this)) {'
n=text.count(old)
if n!=1: raise SystemExit(f'L1PcInstance boolean d site count {n} != 1')
text=text.replace(old,new,1); p.write_text(text,encoding='utf-8')
changes.append({'file':'l1r/ap/L1PcInstance.java','sites':1,'target':'d(L1Character):boolean'})

# C_ItemUSe: ct(int) requires L1Character.a(L1Object):int; L1PcInstance.a(L1Character):void otherwise wins source overload resolution.
p,text=load('l1r/aj/C_ItemUSe.java')
targets=['var3.ct(var3.a(var183));','var3.ct(var3.a(var150));']
n=0
for old in targets:
    if text.count(old)!=1: raise SystemExit(f'C_ItemUSe target count !=1: {old}')
    arg=re.search(r'a\((var\d+)\)',old).group(1)
    text=text.replace(old,f'var3.ct(var3.a((L1Object){arg}));',1); n+=1
p.write_text(text,encoding='utf-8'); changes.append({'file':'l1r/aj/C_ItemUSe.java','sites':n,'target':'a(L1Object):int'})

# L1DoorInstance: null is source-ambiguous with inherited d(ServerBasePacket); donor-side private door update is d(L1PcInstance).
p,text=load('l1r/ap/L1DoorInstance.java')
old='this.d(null);'; new='this.d((L1PcInstance)null);'
n=text.count(old)
if n!=4: raise SystemExit(f'L1DoorInstance null cast count {n} != 4')
text=text.replace(old,new); p.write_text(text,encoding='utf-8')
changes.append({'file':'l1r/ap/L1DoorInstance.java','sites':n,'target':'d(L1PcInstance):void'})

# L1GfxInstance: ct(int) requires L1Character.a(L1Object):int; a(L1Character):void otherwise wins.
p,text=load('l1r/ap/L1GfxInstance.java')
old='var1.ct(var1.a(var2));'; new='var1.ct(var1.a((l1r.aq.L1Object)var2));'
n=text.count(old)
if n!=1: raise SystemExit(f'L1GfxInstance overload target count {n} != 1')
text=text.replace(old,new,1); p.write_text(text,encoding='utf-8')
changes.append({'file':'l1r/ap/L1GfxInstance.java','sites':1,'target':'a(L1Object):int'})

# L1PcInstance: boolean visibility context must select inherited b(L1Object), not b(L1PcInstance):void.
p,text=load('l1r/ap/L1PcInstance.java')
old='if (!this.b(var1) && var1.fp() == this.fp() && !(var1 instanceof L1EffectInstance)) {'
new='if (!this.b((L1Object)var1) && var1.fp() == this.fp() && !(var1 instanceof L1EffectInstance)) {'
n=text.count(old)
if n!=1: raise SystemExit(f'L1PcInstance boolean b site count {n} != 1')
text=text.replace(old,new,1); p.write_text(text,encoding='utf-8')
changes.append({'file':'l1r/ap/L1PcInstance.java','sites':1,'target':'b(L1Object):boolean'})

# S_000: source overload resolution selects void overloads unless receiver arguments are widened to L1Object.
p,text=load('l1r/bf/S_000.java')
targets=[
  ('var3.r && var6.e(var1) > 5.0','var3.r && var6.e((l1r.aq.L1Object)var1) > 5.0','e(L1Object):double'),
  ('var1.ct(var1.a(var2));','var1.ct(var1.a((l1r.aq.L1Object)var2));','a(L1Object):int'),
]
n=0
for old,new,target in targets:
    if text.count(old)!=1: raise SystemExit(f'S_000 overload target count !=1: {old}')
    text=text.replace(old,new,1)
    n+=1
p.write_text(text,encoding='utf-8')
changes.append({'file':'l1r/bf/S_000.java','sites':n,'target':'compile-time L1Object overload selection'})

total=sum(x['sites'] for x in changes)
state={
 'error_family':'JAVA_SOURCE_OVERLOAD_SHADOW_REPRESENTATION',
 'sites_normalized':total,
 'expected_sites':19,
 'changes':changes,
 'method_descriptors_changed':False,
 'control_flow_changed':False,
 'arguments_runtime_values_changed':False,
 'gameplay_logic_changed':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
ok=(total==19)
MD.write_text(
 '# Non-Protobuf Overload Shadow Normalization\n\n'
 + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
 + f'- Source call sites normalized: **{total} / 19**\n'
 + '- Transform: compile-time casts only; runtime argument values unchanged.\n'
 + '- Control flow changed: **NO**\n'
 + '- Gameplay logic changed: **NO**\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
if not ok: raise SystemExit('overload shadow normalization gate failed')
