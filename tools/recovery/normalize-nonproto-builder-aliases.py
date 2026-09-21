#!/usr/bin/env python3
import json,re
from pathlib import Path

STAGE=Path('_normalized-stage-src/l1r')
OUT=Path('recovery/nonproto_builder_alias_transform.json')
MD=Path('recovery/NONPROTO_BUILDER_ALIAS_TRANSFORM.md')

rx=re.compile(r'\b(PBMessageALL(?:[2-9])?)\.L1R_a\.L1R_a\b')
changes=[]; total=0
for p in sorted(STAGE.rglob('*.java')):
    if '/an/' in p.as_posix():
        continue
    text=p.read_text(encoding='utf-8',errors='replace')
    text2,n=rx.subn(r'\1.L1R_a.L1R_Builder',text)
    if n:
        p.write_text(text2,encoding='utf-8')
        total+=n
        changes.append({'file':p.relative_to(STAGE).as_posix(),'replacements':n})

state={
  'error_family':'NONPROTO_PROTOBUF_BUILDER_ALIAS',
  'replacements':total,
  'changed_files':len(changes),
  'changes':changes,
  'old_identity':'PBMessageALL*.L1R_a.L1R_a',
  'recovery_identity':'PBMessageALL*.L1R_a.L1R_Builder',
  'gameplay_logic_changed':False,
  'normalization_required_for_donor_compare':True,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
 '# Non-Protobuf Protobuf Builder Alias Transform\n\n'
 + f'- Replacements: **{total}**\n'
 + f'- Changed files: **{len(changes)}**\n'
 + '- Old source identity: `PBMessageALL*.L1R_a.L1R_a`\n'
 + '- Recovery identity: `PBMessageALL*.L1R_a.L1R_Builder`\n'
 + '- Gameplay logic changed: **NO**\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
if total==0: raise SystemExit('expected non-Protobuf builder alias references')
