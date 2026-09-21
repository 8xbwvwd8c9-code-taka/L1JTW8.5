#!/usr/bin/env python3
import json,re
from pathlib import Path

STAGE=Path('_normalized-stage-src')
REC=Path('recovery')
OUT=REC/'normalized_protobuf_builder_caller_refs.json'
MD=REC/'NORMALIZED_PROTOBUF_BUILDER_CALLER_REFS.md'

rx=re.compile(r'\b(PBMessageALL\d*)\.L1R_a\.L1R_a\b')
changes=[]; total=0
for p in sorted(STAGE.rglob('*.java')):
    if '/l1r/an/' in p.as_posix():
        continue
    text=p.read_text(encoding='utf-8',errors='replace')
    text2,n=rx.subn(r'\1.L1R_a.L1R_Builder',text)
    if n:
        p.write_text(text2,encoding='utf-8')
        total+=n
        changes.append({'file':p.relative_to(STAGE).as_posix(),'rewrites':n})

residual=[]
for p in sorted(STAGE.rglob('*.java')):
    if '/l1r/an/' in p.as_posix(): continue
    text=p.read_text(encoding='utf-8',errors='replace')
    n=len(rx.findall(text))
    if n: residual.append({'file':p.relative_to(STAGE).as_posix(),'count':n})

state={
  'error_family':'PROTOBUF_BUILDER_RECOVERY_RENAME_CALLERS',
  'old_identity':'PBMessageALL*.L1R_a.L1R_a',
  'new_identity':'PBMessageALL*.L1R_a.L1R_Builder',
  'rewrites':total,
  'changed_files':changes,
  'residual_old_refs':residual,
  'gameplay_logic_changed':False,
  'source_representation_only':True,
  'normalization_required_for_donor_compare':True,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
ok=(total>0 and not residual)
MD.write_text(
  '# Normalized Protobuf Builder Caller References\n\n'
  + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
  + f'- Caller rewrites: **{total}**\n'
  + f'- Changed files: **{len(changes)}**\n'
  + f'- Residual old refs: **{sum(x["count"] for x in residual)}**\n'
  + '- Gameplay logic changed: **NO**\n'
  + '- Recovery source identity only: **YES**\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
if not ok: raise SystemExit('protobuf builder caller normalization gate failed')
