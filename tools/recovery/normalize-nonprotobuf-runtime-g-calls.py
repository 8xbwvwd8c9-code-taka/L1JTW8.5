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
rx=re.compile(r'(?<![A-Za-z0-9_.$])g\.a\((new byte\[\]\{[^\n]+?\}|var\d+\.s\(\))\)')
text,n=rx.subn(lambda m:'l1rpb.g.a('+m.group(1)+')',text)
p.write_text(text,encoding='utf-8')
changes.append({'file':'l1r/be/S_ProtoBuffers.java','runtime_g_sites':n})

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
# Known current javac family: 13 byte[]->int + 2 String->int in L1Craft, 2 g-shadow sites in S_ProtoBuffers.
ok=(craft_total==15 and craft_counts.get('empty_string')==2 and n==2)
MD.write_text(
 '# Non-Protobuf Runtime g Call Normalization\n\n'
 + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
 + f'- L1Craft runtime `g.a(...)` sites: **{craft_total} / 15**\n'
 + f'- S_ProtoBuffers runtime `g.a(...)` sites: **{n} / 2**\n'
 + '- Call target changed: **NO** (static import made explicit)\n'
 + '- Argument expressions changed: **NO**\n'
 + '- Gameplay logic changed: **NO**\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
if not ok: raise SystemExit(f'runtime g call gate failed: craft={craft_total}, sprotobuf={n}, detail={craft_counts}')
