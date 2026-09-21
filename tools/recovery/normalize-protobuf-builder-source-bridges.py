#!/usr/bin/env python3
import json,re
from pathlib import Path

ROOT=Path('_normalized-stage-src/l1r/an')
OUT=Path('recovery/normalized_builder_source_bridge_transform.json')
MD=Path('recovery/NORMALIZED_BUILDER_SOURCE_BRIDGE_TRANSFORM.md')

FILES=sorted(ROOT.glob('PBMessageALL*.java'))
EXPECTED={'i()':44,'j()':44,'d(h,n)':44,'c(x)':44}

patterns={
  'i()': re.compile(
    r'(?ms)^\s*// \$VF: synthetic method\s*\n'
    r'\s*@Override\s*\n'
    r'\s*public\s+x\.a\s+i\s*\(\s*\)\s*\{\s*\n'
    r'\s*return\s+this\.n\(\);\s*\n'
    r'\s*\}\s*'
  ),
  'j()': re.compile(
    r'(?ms)^\s*// \$VF: synthetic method\s*\n'
    r'\s*@Override\s*\n'
    r'\s*public\s+x\.a\s+j\s*\(\s*\)\s*\{\s*\n'
    r'\s*return\s+this\.m\(\);\s*\n'
    r'\s*\}\s*'
  ),
  'd(h,n)': re.compile(
    r'(?ms)^\s*// \$VF: synthetic method\s*\n'
    r'\s*@Override\s*\n'
    r'\s*public\s+x\.a\s+d\s*\(\s*a\.h\s+([A-Za-z0-9_$]+)\s*,\s*n\s+([A-Za-z0-9_$]+)\s*\)'
    r'\s*throws\s+IOException\s*\{\s*\n'
    r'\s*return\s+this\.e\(\s*\1\s*,\s*\2\s*\);\s*\n'
    r'\s*\}\s*'
  ),
  'c(x)': re.compile(
    r'(?ms)^\s*// \$VF: synthetic method\s*\n'
    r'\s*@Override\s*\n'
    r'\s*public\s+x\.a\s+c\s*\(\s*x\s+([A-Za-z0-9_$]+)\s*\)\s*\{\s*\n'
    r'\s*return\s+this\.d\(\s*\1\s*\);\s*\n'
    r'\s*\}\s*'
  ),
}

counts={k:0 for k in EXPECTED}
per_file={}
before_parser_object=0
after_parser_object=0

parser_obj=re.compile(
  r'(?ms)^\s*// \$VF: synthetic method\s*\n\s*@Override\s*\n'
  r'\s*public\s+Object\s+d\s*\(\s*a\.h\s+[A-Za-z0-9_$]+\s*,\s*n\s+[A-Za-z0-9_$]+\s*\)'
)

for path in FILES:
    s=path.read_text(encoding='utf-8')
    before_parser_object += len(parser_obj.findall(s))
    local={}
    for fam,pat in patterns.items():
        hits=list(pat.finditer(s))
        local[fam]=len(hits)
        counts[fam]+=len(hits)
        s,n=pat.subn('',s)
        if n!=local[fam]:
            raise SystemExit(f'{path}: replacement mismatch {fam}: hits={local[fam]} replaced={n}')
    path.write_text(s,encoding='utf-8')
    after_parser_object += len(parser_obj.findall(s))
    per_file[str(path)] = local

if counts != EXPECTED:
    raise SystemExit(f'expected exact counts {EXPECTED}, got {counts}')
if before_parser_object != 44 or after_parser_object != 44:
    raise SystemExit(f'parser Object d(h,n) preservation gate failed: before={before_parser_object} after={after_parser_object}')

residual={}
for fam,pat in patterns.items():
    residual[fam]=sum(len(pat.findall(p.read_text(encoding='utf-8'))) for p in FILES)
if any(residual.values()):
    raise SystemExit(f'residual builder source bridges: {residual}')

state={
  'transform':'REMOVE_RECOVERED_BUILDER_VF_SYNTHETIC_BRIDGES',
  'files':len(FILES),
  'families':counts,
  'total_removed':sum(counts.values()),
  'parser_object_d_h_n_before':before_parser_object,
  'parser_object_d_h_n_after':after_parser_object,
  'residual_builder_bridge_counts':residual,
  'per_file':per_file,
  'scope':'normalized source only',
  'real_typed_provider_methods_changed':False,
  'parser_bridges_changed':False,
  'gameplay_logic_changed':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
  '# Normalized Builder Source Bridge Transform\n\n'
  '- Removed only exact Vineflower synthetic builder bridges.\n'
  '- i(): 44\n- j(): 44\n- d(h,n): 44\n- c(x): 44\n'
  '- Total: 176.\n'
  '- Parser Object d(h,n) bridges preserved: 44 before / 44 after.\n'
  '- Typed provider bodies unchanged.\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
