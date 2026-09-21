#!/usr/bin/env python3
import json,re
from pathlib import Path

P=Path('_normalized-stage-src/l1r/aq/L1Craft.java')
OUT=Path('recovery/l1craft_static_factory_transform.json')
MD=Path('recovery/L1CRAFT_STATIC_FACTORY_TRANSFORM.md')

text=P.read_text(encoding='utf-8',errors='replace')
changes=[]

patterns=[
  (re.compile(r'(?<![A-Za-z0-9_.$])a\(([^;\n]*?\.M\(\)\.g\(\))\)'), r'l1rpb.g.a(\1)', 'protobuf_bytes'),
  (re.compile(r'(?<![A-Za-z0-9_.$])a\(""\)'), 'l1rpb.g.a("")', 'empty_string'),
  (re.compile(r'(?<![A-Za-z0-9_.$])a\(([^;\n]*?\.t\(\))\)'), r'l1rpb.g.a(\1)', 'item_text_bytes'),
]
for rx,repl,label in patterns:
    text,n=rx.subn(repl,text)
    if n: changes.append({'kind':label,'sites':n})

P.write_text(text,encoding='utf-8')
total=sum(x['sites'] for x in changes)
state={
  'error_family':'L1CRAFT_STATIC_IMPORT_OVERLOAD_SHADOW',
  'qualified_sites':total,
  'changes':changes,
  'target':'l1rpb.g.a(...)',
  'method_target_changed':False,
  'gameplay_logic_changed':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
 '# L1Craft Static Factory Qualification\n\n'
 + f'- Qualified `a.g.a(...)` sites: **{total}**\n'
 + '- Recovery target: `l1rpb.g.a(...)`.\n'
 + '- Fixes static-import overload shadowing by L1Craft.a(...).\n'
 + '- Method target / gameplay logic changed: **NO / NO**\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
if total<=0: raise SystemExit('no L1Craft static factory sites normalized')
