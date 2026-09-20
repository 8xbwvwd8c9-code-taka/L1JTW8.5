#!/usr/bin/env python3
import json,re
from pathlib import Path

STAGE=Path('_normalized-stage-src')
REC=Path('recovery')
OUT=REC/'normalized_runtime_import_transform.json'
MD=REC/'NORMALIZED_RUNTIME_IMPORT_TRANSFORM.md'

rx=re.compile(r'(?m)^(?P<prefix>\s*import\s+(?:static\s+)?)a\.(?P<tail>[A-Za-z_$][\w$]*(?:\.[A-Za-z_$][\w$]*)*\s*;)')
changes=[]
for p in sorted(STAGE.rglob('*.java')):
    text=p.read_text(encoding='utf-8',errors='replace')
    def repl(m):
        changes.append({'file':p.relative_to(STAGE).as_posix(),'old':m.group(0).strip(),'new':(m.group('prefix')+'l1rpb.'+m.group('tail')).strip()})
        return m.group('prefix')+'l1rpb.'+m.group('tail')
    new=rx.sub(repl,text)
    if new!=text:
        p.write_text(new,encoding='utf-8')

state={
  'error_family':'PROTOBUF_RUNTIME_NAMESPACE_RESIDUAL_IMPORTS',
  'expected_import_rewrites':13,
  'import_rewrites':len(changes),
  'changes':changes,
  'gameplay_logic_changed':False,
  'normalization_required_for_donor_compare':True,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
status='PASS' if len(changes)==13 else 'FAIL'
MD.write_text(
  '# Normalized Protobuf Runtime Import Transform\n\n'
  f'Status: **{status}**\n\n'
  f'- Runtime imports rewritten: **{len(changes)} / 13**\n'
  '- Transform: `import a.*` / `import static a.*` -> `l1rpb.*`.\n'
  '- Scope: imports only; no method/body rewrite.\n'
  '- Gameplay logic changed: **NO**\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
if status!='PASS':
    raise SystemExit(f'expected 13 residual runtime imports, got {len(changes)}')
