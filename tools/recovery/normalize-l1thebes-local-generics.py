#!/usr/bin/env python3
import json
from pathlib import Path

P=Path('_normalized-stage-src/l1r/as/L1ThebesBattle.java')
OUT=Path('recovery/l1thebes_local_generic_normalization.json')
MD=Path('recovery/L1THEBES_LOCAL_GENERIC_NORMALIZATION.md')
text=P.read_text(encoding='utf-8',errors='replace')

targets={
 'ArrayList var1 = new ArrayList<>();':'ArrayList<L1ThebesBattle.L1R_g> var1 = new ArrayList<>();',
 'ArrayList var2 = new ArrayList<>();':'ArrayList<L1ThebesBattle.L1R_g> var2 = new ArrayList<>();',
 'ArrayList var3 = new ArrayList<>();':'ArrayList<L1ThebesBattle.L1R_g> var3 = new ArrayList<>();',
}
counts={}; total=0
for old,new in targets.items():
    n=text.count(old)
    counts[old]=n
    text=text.replace(old,new)
    total+=n

# var2 appears once more in a(L1PcInstance); total expected declarations = 4.
P.write_text(text,encoding='utf-8')
ok=(counts['ArrayList var1 = new ArrayList<>();']==1 and counts['ArrayList var2 = new ArrayList<>();']==2 and counts['ArrayList var3 = new ArrayList<>();']==1 and total==4)
state={
 'error_family':'RAW_LOCAL_COLLECTION_TOARRAY_ERASURE',
 'file':'l1r/as/L1ThebesBattle.java',
 'local_declarations_normalized':total,
 'counts':counts,
 'element_type':'L1ThebesBattle.L1R_g',
 'runtime_erasure_changed':False,
 'control_flow_changed':False,
 'gameplay_logic_changed':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
 '# L1ThebesBattle Local Generic Normalization\n\n'
 + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
 + f'- Raw local `ArrayList` declarations typed: **{total} / 4**\n'
 + '- Purpose: preserve `toArray(L1R_g[])` source return type instead of raw `Object[]`.\n'
 + '- Runtime erasure changed: **NO**\n'
 + '- Gameplay logic changed: **NO**\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
if not ok: raise SystemExit(f'L1Thebes generic gate failed: {counts}')
