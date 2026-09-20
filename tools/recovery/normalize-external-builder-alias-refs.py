#!/usr/bin/env python3
import json,re
from pathlib import Path

STAGE=Path('_normalized-stage-src')
OUT=Path('recovery/normalized_external_builder_alias_refs.json')
MD=Path('recovery/NORMALIZED_EXTERNAL_BUILDER_ALIAS_REFS.md')

rx=re.compile(r'\b(PBMessageALL(?:[2-9])?)\.L1R_a\.L1R_a\b')
changes=[]; total=0; residual=[]
for p in sorted(STAGE.rglob('*.java')):
    rel=p.relative_to(STAGE).as_posix()
    if rel.startswith('l1r/an/'):
        continue
    text=p.read_text(encoding='utf-8',errors='replace')
    text2,n=rx.subn(r'\1.L1R_a.L1R_Builder',text)
    if n:
        p.write_text(text2,encoding='utf-8')
        total+=n
        changes.append({'file':rel,'replacements':n})
    for m in rx.finditer(text2):
        residual.append({'file':rel,'offset':m.start(),'match':m.group(0)})

state={
  'error_family':'EXTERNAL_NESTED_BUILDER_IDENTITY_ALIAS',
  'replacements':total,
  'changed_files':len(changes),
  'residual_external_refs':len(residual),
  'changes':changes,
  'mapping':'PBMessageALL*.L1R_a.L1R_a -> PBMessageALL*.L1R_a.L1R_Builder',
  'gameplay_logic_changed':False,
  'source_identity_only':True,
  'normalization_required_for_donor_compare':True,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
ok=(total>0 and not residual)
MD.write_text(
  '# External Nested Builder Alias References\n\n'
  + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
  + f'- Replacements: **{total}**\n'
  + f'- Changed files: **{len(changes)}**\n'
  + f'- Residual external old refs: **{len(residual)}**\n'
  + '- Gameplay logic changed: **NO**\n'
  + '- Source identity normalization only: **YES**\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
if not ok: raise SystemExit('external builder alias normalization gate failed')
