#!/usr/bin/env python3
import json,re
from pathlib import Path

STAGE=Path('_normalized-stage-src')
REC=Path('recovery')
OUT=REC/'protobuf_external_builder_identity_transform.json'
MD=REC/'PROTOBUF_EXTERNAL_BUILDER_IDENTITY_TRANSFORM.md'

rx=re.compile(r'\b(PBMessageALL\d*)\.L1R_a\.L1R_a\b')
changes=[]
for p in sorted(STAGE.rglob('*.java')):
    text=p.read_text(encoding='utf-8',errors='replace')
    n=0
    def repl(m):
        nonlocal_dummy=None
        return m.group(1)+'.L1R_a.L1R_Builder'
    new,n=rx.subn(lambda m:m.group(1)+'.L1R_a.L1R_Builder',text)
    if n:
        p.write_text(new,encoding='utf-8')
        changes.append({'file':p.relative_to(STAGE).as_posix(),'sites':n})

total=sum(x['sites'] for x in changes)
state={
  'error_family':'PROTOBUF_EXTERNAL_BUILDER_IDENTITY_MAPPING',
  'old_identity':'PBMessageALL*.L1R_a.L1R_a',
  'new_identity':'PBMessageALL*.L1R_a.L1R_Builder',
  'changed_files':len(changes),
  'rewritten_sites':total,
  'changes':changes,
  'gameplay_logic_changed':False,
  'source_representation_only':True,
  'normalization_required_for_donor_compare':True,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
  '# Protobuf External Builder Identity Transform\n\n'
  + f'- Changed files: **{len(changes)}**\n'
  + f'- Rewritten external builder references: **{total}**\n'
  + '- Mapping: `PBMessageALL*.L1R_a.L1R_a` -> `PBMessageALL*.L1R_a.L1R_Builder`.\n'
  + '- Gameplay logic changed: **NO**\n'
  + '- Source representation only: **YES**\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
if total<=0: raise SystemExit('no external builder identity references found')
