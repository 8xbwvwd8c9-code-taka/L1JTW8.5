#!/usr/bin/env python3
import json,re
from pathlib import Path

STAGE=Path('_normalized-stage-src/l1r/an')
OUT=Path('recovery/protobuf_parser_raw_source_experiment.json')
MD=Path('recovery/PROTOBUF_PARSER_RAW_SOURCE_EXPERIMENT.md')

rx=re.compile(r'new\s+l1rpb\.c<([A-Za-z0-9_.$]+)>\(\)\s*\{')
total=0; changes=[]
for p in sorted(STAGE.glob('PBMessageALL*.java')):
  text=p.read_text(encoding='utf-8',errors='replace')
  text2,n=rx.subn('new l1rpb.c() {',text)
  if n:
    p.write_text(text2,encoding='utf-8')
    total+=n; changes.append({'file':p.name,'rewrites':n})

residual=[]
for p in sorted(STAGE.glob('PBMessageALL*.java')):
  n=len(rx.findall(p.read_text(encoding='utf-8',errors='replace')))
  if n: residual.append({'file':p.name,'count':n})

state={
 'expected_raw_parser_sites':44,
 'raw_parser_sites':total,
 'changes':changes,
 'residual_parameterized_c_sites':residual,
 'field_type_ab_unchanged':True,
 'anonymous_parser_body_changed':False,
 'gameplay_logic_changed':False,
 'source_representation_only':True,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
ok=(total==44 and not residual)
MD.write_text(
 '# Protobuf Raw Parser Source Experiment\n\n'
 + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
 + f'- `new l1rpb.c<Message>()` -> raw `new l1rpb.c()`: **{total} / 44**\n'
 + '- Static field type `ab<Message>` unchanged: **YES**\n'
 + '- Anonymous parser body changed: **NO**\n'
 + '- Gameplay logic changed: **NO**\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
if not ok: raise SystemExit(f'expected 44 raw parser sites, got {total}, residual={residual}')
