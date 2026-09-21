#!/usr/bin/env python3
import json,re
from pathlib import Path

STAGE=Path('_normalized-stage-src')
OUT=Path('recovery/nonprotobuf_runtime_g_call_normalization.json')
MD=Path('recovery/NONPROTOBUF_RUNTIME_G_CALL_NORMALIZATION.md')

changes=[]

# L1Craft: imported static l1rpb.g.a(...) is shadowed by local a(int...) overloads.
p=STAGE/'l1r/aq/L1Craft.java'
text=p.read_text(encoding='utf-8',errors='replace')
patterns=[
  ('message_bytes', re.compile(r'(?<![A-Za-z0-9_.$])a\(([^\n;]*?\.M\(\)\.g\(\))\)')),
  ('item_bytes', re.compile(r'(?<![A-Za-z0-9_.$])a\((var\d+\.t\(\)|this\.i\.t\(\))\)')),
  ('empty_string', re.compile(r'(?<![A-Za-z0-9_.$])a\(""\)')),
]
craft_counts={}
for name,rx in patterns:
    text,n=rx.subn(lambda m: 'l1rpb.g.a('+m.group(1)+')' if m.lastindex else 'l1rpb.g.a("")', text)
    craft_counts[name]=n
p.write_text(text,encoding='utf-8')
changes.append({'file':'l1r/aq/L1Craft.java','counts':craft_counts})

# S_ProtoBuffers: instance int field `g` shadows imported runtime type g at two static call sites.
p=STAGE/'l1r/be/S_ProtoBuffers.java'
text=p.read_text(encoding='utf-8',errors='replace')
spb_targets=[
  'g.a(new byte[]{-30, 112, -1})',
  'g.a(var18.s())',
  'g.a(var27.getString("char_name").getBytes(Config.k))',
  'g.a(new byte[]{-1, 0, -1})',
]
n=0
for old in spb_targets:
    count=text.count(old)
    if count!=1:
        raise SystemExit(f'expected exactly one S_ProtoBuffers target {old}, got {count}')
    text=text.replace(old,'l1rpb.'+old,1)
    n+=1
p.write_text(text,encoding='utf-8')
changes.append({'file':'l1r/be/S_ProtoBuffers.java','runtime_g_sites':n,'targets':spb_targets})

craft_total=sum(craft_counts.values())
state={
  'error_family':'RUNTIME_G_STATIC_CALL_NAME_SHADOW',
  'craft_runtime_g_sites':craft_total,
  'craft_counts':craft_counts,
  's_protobuffers_runtime_g_sites':n,
  'changes':changes,
  'call_target_changed':False,
  'argument_expressions_changed':False,
  'gameplay_logic_changed':False,
  'normalization_required_for_donor_compare':True,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
# Current javac evidence: 18 L1Craft overload-resolution failures and 4 S_ProtoBuffers g-shadow failures.
ok=(craft_total==18 and craft_counts.get('message_bytes')==14 and craft_counts.get('item_bytes')==2 and craft_counts.get('empty_string')==2 and n==4)
MD.write_text(
 '# Non-Protobuf Runtime g Call Normalization\n\n'
 + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
 + f'- L1Craft runtime `g.a(...)` sites: **{craft_total} / 18**\n'
 + f'- S_ProtoBuffers runtime `g.a(...)` sites: **{n} / 4**\n'
 + '- Call target changed: **NO** (static import made explicit)\n'
 + '- Argument expressions changed: **NO**\n'
 + '- Gameplay logic changed: **NO**\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
if not ok: raise SystemExit(f'runtime g call gate failed: craft={craft_total}, sprotobuf={n}, detail={craft_counts}')
