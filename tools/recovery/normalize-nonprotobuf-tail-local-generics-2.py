#!/usr/bin/env python3
import json
from pathlib import Path

STAGE=Path('_normalized-stage-src')
OUT=Path('recovery/nonprotobuf_tail_local_generic_normalization_2.json')
MD=Path('recovery/NONPROTOBUF_TAIL_LOCAL_GENERIC_NORMALIZATION_2.md')

targets={
  'l1r/ap/L1NpcInstance.java':[
    ('ArrayList var1 = new ArrayList<>();','ArrayList<L1GroundInventory> var1 = new ArrayList<>();',2),
    ('LinkedList var18 = new LinkedList<>();','LinkedList<int[]> var18 = new LinkedList<>();',1),
  ],
  'l1r/al/L1GfxNpc.java':[
    ('ArrayList var20 = new ArrayList<>();','ArrayList<int[]> var20 = new ArrayList<>();',1),
    ('ArrayList var24 = new ArrayList<>();','ArrayList<Integer> var24 = new ArrayList<>();',1),
  ],
  'l1r/bf/S_044.java':[
    ('HashMap var6 = new HashMap<>(var1.eh());','HashMap<Integer, ?> var6 = new HashMap<>(var1.eh());',1),
    ('HashMap var13 = new HashMap<>(var7.eh());','HashMap<Integer, ?> var13 = new HashMap<>(var7.eh());',1),
  ],
  'l1r/aj/C_SkillBuyItemOK.java':[
    ('CopyOnWriteArrayList var5 = new CopyOnWriteArrayList<>();','CopyOnWriteArrayList<Integer> var5 = new CopyOnWriteArrayList<>();',1),
  ],
  'l1r/aj/C_SkillBuyOK.java':[
    ('CopyOnWriteArrayList var6 = new CopyOnWriteArrayList<>();','CopyOnWriteArrayList<Integer> var6 = new CopyOnWriteArrayList<>();',1),
  ],
}

changes=[]
total=0
for rel,repls in targets.items():
    p=STAGE/rel
    text=p.read_text(encoding='utf-8',errors='replace')
    file_n=0
    for old,new,expected in repls:
        n=text.count(old)
        if n!=expected:
            raise SystemExit(f'generic target count {n} != {expected}: {rel} :: {old}')
        text=text.replace(old,new)
        file_n+=n
        total+=n
    p.write_text(text,encoding='utf-8')
    changes.append({'file':rel,'declarations_normalized':file_n})

expected=9
state={
  'error_family':'RAW_LOCAL_COLLECTION_GENERIC_ERASURE_TAIL_2',
  'declarations_normalized':total,
  'expected_declarations':expected,
  'changes':changes,
  'runtime_erasure_changed':False,
  'collection_instances_changed':False,
  'control_flow_changed':False,
  'gameplay_logic_changed':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
ok=(total==expected)
MD.write_text(
  '# Non-Protobuf Tail Local Generic Normalization 2\n\n'
  + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
  + f'- Local declarations normalized: **{total} / {expected}**\n'
  + '- Runtime erasure / collection instances changed: **NO / NO**\n'
  + '- Control flow / gameplay logic changed: **NO / NO**\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
if not ok:
    raise SystemExit(f'expected {expected} declarations, got {total}')
