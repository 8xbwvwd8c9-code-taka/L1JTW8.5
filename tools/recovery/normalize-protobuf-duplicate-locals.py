#!/usr/bin/env python3
import json,re
from pathlib import Path

P=Path('_normalized-stage-src/l1r/an/PBMessageALL7.java')
OUT=Path('recovery/protobuf_lengthx_local_transform.json')
MD=Path('recovery/PROTOBUF_LENGTHX_LOCAL_TRANSFORM.md')

text=P.read_text(encoding='utf-8',errors='replace')
rx=re.compile(r'(?m)^(\s*)int lengthx = var1\.s\(\);\n\s*var23 = var1\.f\(lengthx\);')
matches=list(rx.finditer(text))
if len(matches)!=4:
    raise SystemExit(f'expected 4 lengthx declarations in PBMessageALL7, got {len(matches)}')

parts=[]; last=0; changes=[]
for idx,m in enumerate(matches,1):
    parts.append(text[last:m.start()])
    if idx==1:
        parts.append(m.group(0))
    else:
        name=f'lengthx_{idx}'
        repl=f'{m.group(1)}int {name} = var1.s();\n{m.group(1)}var23 = var1.f({name});'
        parts.append(repl)
        changes.append({'occurrence':idx,'new_name':name})
    last=m.end()
parts.append(text[last:])
text2=''.join(parts)
P.write_text(text2,encoding='utf-8')

state={
  'error_family':'PROTOBUF_DUPLICATE_LOCAL_NAME',
  'file':'l1r/an/PBMessageALL7.java',
  'declarations_found':len(matches),
  'declarations_renamed':len(changes),
  'changes':changes,
  'control_flow_changed':False,
  'expressions_changed':False,
  'gameplay_logic_changed':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
ok=(len(matches)==4 and len(changes)==3)
MD.write_text(
  '# Protobuf Duplicate Local Normalization\n\n'
  + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
  + '- Target: `PBMessageALL7.L1R_e(h,n)` switch parser.\n'
  + '- Original `int lengthx` declarations: **4**\n'
  + '- Recovery-only local renames: **3**\n'
  + '- Control flow / expressions changed: **NO / NO**\n'
  + '- Gameplay logic changed: **NO**\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
if not ok: raise SystemExit('duplicate local normalization gate failed')
