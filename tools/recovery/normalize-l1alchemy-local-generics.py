#!/usr/bin/env python3
import json
from pathlib import Path

P=Path('_normalized-stage-src/l1r/aq/L1Alchemy.java')
OUT=Path('recovery/l1alchemy_local_generic_normalization.json')
MD=Path('recovery/L1ALCHEMY_LOCAL_GENERIC_NORMALIZATION.md')

text=P.read_text(encoding='utf-8',errors='replace')
targets={
  'ArrayList var2 = new ArrayList<>();':'ArrayList<L1Item> var2 = new ArrayList<>();',
  'ArrayList var9 = new ArrayList<>();':'ArrayList<L1Item> var9 = new ArrayList<>();',
  'ArrayList var17 = new ArrayList<>();':'ArrayList<L1Item> var17 = new ArrayList<>();',
}
counts={}; total=0
for old,new in targets.items():
    n=text.count(old)
    counts[old]=n
    text=text.replace(old,new)
    total+=n
P.write_text(text,encoding='utf-8')

# var2 occurs in b(int) and c(int); var9/var17 once each.
ok=(counts['ArrayList var2 = new ArrayList<>();']==2 and counts['ArrayList var9 = new ArrayList<>();']==1 and counts['ArrayList var17 = new ArrayList<>();']==1)
state={
  'error_family':'RAW_LOCAL_COLLECTION_GENERIC_ERASURE',
  'file':'l1r/aq/L1Alchemy.java',
  'local_declarations_normalized':total,
  'counts':counts,
  'field_element_type':'L1Item',
  'runtime_erasure_changed':False,
  'control_flow_changed':False,
  'gameplay_logic_changed':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
 '# L1Alchemy Local Generic Normalization\n\n'
 + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
 + f'- Local raw `ArrayList` declarations typed as `ArrayList<L1Item>`: **{total} / 4**\n'
 + '- Runtime erasure changed: **NO**\n'
 + '- Control flow changed: **NO**\n'
 + '- Gameplay logic changed: **NO**\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
if not ok: raise SystemExit(f'L1Alchemy generic gate failed: {counts}')
