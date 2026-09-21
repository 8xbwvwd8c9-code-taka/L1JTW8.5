#!/usr/bin/env python3
import json
from pathlib import Path

STAGE=Path('_normalized-stage-src')
OUT=Path('recovery/nonprotobuf_tail_local_generic_normalization.json')
MD=Path('recovery/NONPROTOBUF_TAIL_LOCAL_GENERIC_NORMALIZATION.md')

targets = {
  'l1r/bi/CalcExp.java': [
    ('CopyOnWriteArrayList var5 = var2.d();',
     'CopyOnWriteArrayList<L1HateList.L1R_a> var5 = var2.d();'),
  ],
  'l1r/bf/S_000.java': [
    ('ArrayList var5 = new ArrayList<>();',
     'ArrayList<L1Character> var5 = new ArrayList<>();'),
  ],
  'l1r/bf/S_017.java': [
    ('ArrayList var8 = this.a(var1, var7, this.b.q());',
     'ArrayList<L1Character> var8 = this.a(var1, var7, this.b.q());'),
  ],
  'l1r/bf/S_022.java': [
    ('ArrayList var8 = this.a(var1, var7, this.b.q());',
     'ArrayList<L1Character> var8 = this.a(var1, var7, this.b.q());'),
  ],
  'l1r/bf/S_025.java': [
    ('ArrayList var9 = this.a(var1, var7, var8);',
     'ArrayList<L1Character> var9 = this.a(var1, var7, var8);'),
  ],
  'l1r/bf/S_030.java': [
    ('ArrayList var9 = this.a(var1, var7, var8);',
     'ArrayList<L1Character> var9 = this.a(var1, var7, var8);'),
  ],
  'l1r/bf/S_065.java': [
    ('ArrayList var9 = this.a(var1, var7, var8);',
     'ArrayList<L1Character> var9 = this.a(var1, var7, var8);'),
  ],
  'l1r/bf/S_074.java': [
    ('ArrayList var9 = this.a(var1, var7, var8);',
     'ArrayList<L1Character> var9 = this.a(var1, var7, var8);'),
  ],
  'l1r/bf/S_184.java': [
    ('ArrayList var9 = this.a(var1, var7, var8);',
     'ArrayList<L1Character> var9 = this.a(var1, var7, var8);'),
  ],
  'l1r/be/S_ShopBuyList.java': [
    ('List var4 = var3.b();','List<L1ShopItem> var4 = var3.b();'),
    ('List var3 = var2.b();','List<L1ShopItem> var3 = var2.b();'),
    ('List var5 = var4.b();','List<L1ShopItem> var5 = var4.b();'),
  ],
  'l1r/aj/C_Result.java': [
    ('HashMap var32 = LuckyDrawTable.a().c(var2.e().d());',
     'HashMap<Integer, L1ItemInstance> var32 = LuckyDrawTable.a().c(var2.e().d());'),
    ('CopyOnWriteArrayList var44 = var37.aU();',
     'CopyOnWriteArrayList<L1PrivateShopSellList> var44 = var37.aU();'),
    ('List var41 = var35.aV();',
     'List<L1PrivateShopBuyList> var41 = var35.aV();'),
  ],
  'l1r/as/L1SoulTower.java': [
    ('ArrayList var15 = new ArrayList<>();','ArrayList<L1DoorInstance> var15 = new ArrayList<>();'),
    ('ArrayList var33 = new ArrayList<>();','ArrayList<L1DoorInstance> var33 = new ArrayList<>();'),
    ('ArrayList var36 = new ArrayList<>();','ArrayList<L1DoorInstance> var36 = new ArrayList<>();'),
  ],
  'l1r/as/L1OrimBattle.java': [
    ('ArrayList var3 = this.a(this.G, 91510, var2);',
     'ArrayList<L1NpcInstance> var3 = this.a(this.G, 91510, var2);'),
    ('ArrayList var5 = this.a(var4, 91511, 1);',
     'ArrayList<L1NpcInstance> var5 = this.a(var4, 91511, 1);'),
  ],
  'l1r/au/L1Inventory.java': [
    ('ArrayList var3 = new ArrayList<>();','ArrayList<L1ItemInstance> var3 = new ArrayList<>();'),
    ('ArrayList var2 = new ArrayList<>();','ArrayList<L1ItemInstance> var2 = new ArrayList<>();'),
  ],
}

changes=[]
total=0
for rel,repls in targets.items():
    p=STAGE/rel
    text=p.read_text(encoding='utf-8',errors='replace')
    file_n=0
    for old,new in repls:
        n=text.count(old)
        if n != 1:
            raise SystemExit(f'generic target count {n} != 1: {rel} :: {old}')
        text=text.replace(old,new,1)
        file_n += 1
        total += 1
    p.write_text(text,encoding='utf-8')
    changes.append({'file':rel,'declarations_normalized':file_n})

expected=22
state={
  'error_family':'RAW_LOCAL_COLLECTION_GENERIC_ERASURE_TAIL',
  'declarations_normalized':total,
  'expected_declarations':expected,
  'changes':changes,
  'runtime_erasure_changed':False,
  'collection_instances_changed':False,
  'control_flow_changed':False,
  'element_values_changed':False,
  'gameplay_logic_changed':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
ok=(total==expected)
MD.write_text(
  '# Non-Protobuf Tail Local Generic Normalization\n\n'
  + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
  + f'- Local declarations normalized: **{total} / {expected}**\n'
  + '- Runtime erasure changed: **NO**\n'
  + '- Collection instances / element values changed: **NO / NO**\n'
  + '- Control flow changed: **NO**\n'
  + '- Gameplay logic changed: **NO**\n',
  encoding='utf-8'
)
print(json.dumps(state,indent=2))
if not ok:
    raise SystemExit(f'expected {expected} generic declarations, got {total}')
