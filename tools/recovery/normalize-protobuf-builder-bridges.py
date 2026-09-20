#!/usr/bin/env python3
import json
import re
from pathlib import Path

STAGE=Path('_normalized-stage-src')
REC=Path('recovery')
OUT=REC/'normalized_builder_bridge_transform.json'
MD=REC/'NORMALIZED_BUILDER_BRIDGE_TRANSFORM.md'

# Remove only Vineflower-emitted synthetic @Override bridge methods.
# Synthetic constructors/accessors without @Override are retained.
rx=re.compile(
    r'\n\s*// \$VF: synthetic method\s*\n'
    r'\s*@Override\s*\n'
    r'\s*public\s+[^\n\{]+\{\s*\n'
    r'\s*return\s+[^;]+;\s*\n'
    r'\s*\}\s*',
    re.MULTILINE,
)

changes=[]
for p in sorted((STAGE/'l1r'/'an').glob('PBMessageALL*.java')):
    text=p.read_text(encoding='utf-8',errors='replace')
    before=len(re.findall(r'// \$VF: synthetic method',text))
    text2,n=rx.subn('\n',text)
    if n:
        p.write_text(text2,encoding='utf-8')
        changes.append({'file':p.relative_to(STAGE).as_posix(),'removed_override_bridges':n,'synthetic_markers_before':before})

total=sum(x['removed_override_bridges'] for x in changes)
state={
  'error_family':'PROTOBUF_EXPLICIT_SYNTHETIC_BUILDER_BRIDGES',
  'changed_files':len(changes),
  'removed_override_bridges':total,
  'changes':changes,
  'synthetic_constructors_retained':True,
  'typed_methods_retained':True,
  'generic_superclass_retained':True,
  'gameplay_logic_changed':False,
  'bridges_expected_to_be_regenerated_by_javac':True,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
status='PASS' if len(changes)==9 and total>0 else 'FAIL'
MD.write_text(
  '# Normalized Protobuf Builder Bridge Transform\n\n'
  f'Status: **{status}**\n\n'
  f'- Changed protobuf files: **{len(changes)} / 9**\n'
  f'- Removed explicit synthetic @Override bridges: **{total}**\n'
  '- Generic builder superclass retained: **YES**\n'
  '- Typed builder methods retained: **YES**\n'
  '- Synthetic constructors retained: **YES**\n'
  '- Gameplay logic changed: **NO**\n'
  '- javac is expected to regenerate erased/covariant bridges for ABI normalization.\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
if status!='PASS':
    raise SystemExit('builder bridge transform did not hit expected 9 protobuf files')
