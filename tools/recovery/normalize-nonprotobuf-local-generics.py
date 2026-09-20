#!/usr/bin/env python3
import json
from pathlib import Path

OUT=Path('recovery/nonprotobuf_local_generic_normalization.json')
MD=Path('recovery/NONPROTOBUF_LOCAL_GENERIC_NORMALIZATION.md')
changes=[]

# DropTable: L1HateList.d() is consumed only as L1HateList.L1R_a elements here.
p=Path('_normalized-stage-src/l1r/ao/DropTable.java')
text=p.read_text(encoding='utf-8',errors='replace')
old='CopyOnWriteArrayList var4 = var2.d();'
new='CopyOnWriteArrayList<L1HateList.L1R_a> var4 = var2.d();'
n=text.count(old)
if n!=1: raise SystemExit(f'DropTable target count {n} != 1')
text=text.replace(old,new,1)
p.write_text(text,encoding='utf-8')
changes.append({'file':'l1r/ao/DropTable.java','replacements':1,'element_type':'L1HateList.L1R_a'})

# L1SkillExecutor: both L1World branches are iterated strictly as L1Object.
p=Path('_normalized-stage-src/l1r/bf/L1SkillExecutor.java')
text=p.read_text(encoding='utf-8',errors='replace')
old='ArrayList var5;'
new='ArrayList<L1Object> var5;'
n=text.count(old)
if n!=1: raise SystemExit(f'L1SkillExecutor target count {n} != 1')
text=text.replace(old,new,1)
p.write_text(text,encoding='utf-8')
changes.append({'file':'l1r/bf/L1SkillExecutor.java','replacements':1,'element_type':'L1Object'})

state={
  'error_family':'RAW_LOCAL_COLLECTION_GENERIC_ERASURE',
  'changes':changes,
  'local_declarations_normalized':2,
  'runtime_erasure_changed':False,
  'control_flow_changed':False,
  'call_targets_changed':False,
  'gameplay_logic_changed':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
MD.write_text(
 '# Non-Protobuf Local Generic Normalization\n\n'
 + 'Status: **PASS**\n\n'
 + '- `DropTable`: raw `CopyOnWriteArrayList` -> `CopyOnWriteArrayList<L1HateList.L1R_a>`.\n'
 + '- `L1SkillExecutor`: raw `ArrayList` -> `ArrayList<L1Object>`.\n'
 + '- Runtime erasure changed: **NO**\n'
 + '- Control flow / call targets changed: **NO / NO**\n'
 + '- Gameplay logic changed: **NO**\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
