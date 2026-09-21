#!/usr/bin/env python3
import json
from pathlib import Path

STAGE=Path('_normalized-stage-src')
OUT=Path('recovery/nonprotobuf_tail_local_generic_normalization_3.json')
MD=Path('recovery/NONPROTOBUF_TAIL_LOCAL_GENERIC_NORMALIZATION_3.md')

targets={
  'l1r/be/S_ShopSellList.java':[
    ('ArrayList var4 = new ArrayList<>();','ArrayList<int[]> var4 = new ArrayList<>();'),
  ],
  'l1r/be/S_SkillBuy.java':[
    ('ArrayList var2 = a(var1);','ArrayList<Integer> var2 = a(var1);'),
  ],
  'l1r/be/S_SkillBuyItem.java':[
    ('ArrayList var2 = S_SkillBuy.a(var1);','ArrayList<Integer> var2 = S_SkillBuy.a(var1);'),
  ],
  'l1r/ap/L1ItemInstance.java':[
    ('ArrayList var24 = ArmorSetTable.a().a(this.N());','ArrayList<ArmorSetTable.L1R_a> var24 = ArmorSetTable.a().a(this.N());'),
  ],
  'l1r/aj/C_ProtoBuffers.java':[
    ('ArrayList var152 = new ArrayList<>();','ArrayList<L1ItemInstance> var152 = new ArrayList<>();'),
  ],
  'l1r/aj/C_ShopWorld.java':[
    ('ConcurrentHashMap var28 = ShopWorldTable.a().a(var2.a());','ConcurrentHashMap<Integer, L1ItemInstance> var28 = ShopWorldTable.a().a(var2.a());'),
  ],
  'l1r/al/L1Buff.java':[
    ('Collection var5 = null;','Collection<L1PcInstance> var5 = null;'),
  ],
  'l1r/al/L1Recall.java':[
    ('Collection var4 = null;','Collection<L1PcInstance> var4 = null;'),
  ],
  'l1r/ao/MobQuestWeekTable.java':[
    ('ArrayList var2 = new ArrayList<>();','ArrayList<MobQuestWeekTable.L1R_a> var2 = new ArrayList<>();'),
  ],
  'l1r/ao/RankingTable.java':[
    ('int var7 = var3.get(var6);','int var7 = (Integer)var3.get(var6);'),
  ],
  'l1r/ao/ShopTable.java':[
    ('HashMap var4 = ItemTable.a().c();','HashMap<Integer, L1Item> var4 = ItemTable.a().c();'),
    ('List var6 = var1.b();','List<L1ShopItem> var6 = var1.b();'),
  ],
  'l1r/bd/L1MonsterTrap.java':[
    ('List var3 = this.a(var2.fu(), 5);','List<Point> var3 = this.a(var2.fu(), 5);'),
  ],
  'l1r/be/S_Board.java':[
    ('List var3 = L1BoardTopic.a(var2, 8);','List<L1BoardTopic> var3 = L1BoardTopic.a(var2, 8);'),
  ],
  'l1r/be/S_CharEvent.java':[
    ('ArrayList var2 = new ArrayList<>(var1.values());','ArrayList<ShopWorldTable.L1R_b> var2 = new ArrayList<>(var1.values());'),
  ],
  'l1r/be/S_FixWeaponList.java':[
    ('ArrayList var2 = new ArrayList<>();','ArrayList<L1ItemInstance> var2 = new ArrayList<>();'),
  ],
  'l1r/be/S_Mail.java':[
    ('ArrayList var3 = MailTable.a().a(var1.fr(), var2);','ArrayList<L1Mail> var3 = MailTable.a().a(var1.fr(), var2);'),
  ],
  'l1r/be/S_PacketBox.java':[
    ('CopyOnWriteArrayList var3 = L1Master.a().b(var2);','CopyOnWriteArrayList<L1PcInstance> var3 = L1Master.a().b(var2);'),
  ],
  'l1r/be/S_PetList.java':[
    ('ArrayList var3 = new ArrayList<>();','ArrayList<L1ItemInstance> var3 = new ArrayList<>();'),
  ],
  'l1r/be/S_PledgeWarehouseHistory.java':[
    ('ArrayList var2 = this.f(var1);','ArrayList<S_PledgeWarehouseHistory.L1R_a> var2 = this.f(var1);'),
  ],
  'l1r/bi/LineageUtil.java':[
    ('List var5 = L1World.a().c(var0, 1);','List<L1PcInstance> var5 = L1World.a().c(var0, 1);'),
  ],
}

changes=[]
total=0
for rel,repls in targets.items():
    p=STAGE/rel
    text=p.read_text(encoding='utf-8',errors='replace')
    nfile=0
    for old,new in repls:
        n=text.count(old)
        if n!=1:
            raise SystemExit(f'tail-3 target count {n} != 1: {rel} :: {old}')
        text=text.replace(old,new,1)
        nfile+=1
        total+=1
    p.write_text(text,encoding='utf-8')
    changes.append({'file':rel,'sites_normalized':nfile})

expected=21
state={
 'error_family':'RAW_LOCAL_COLLECTION_GENERIC_ERASURE_TAIL_3',
 'sites_normalized':total,
 'expected_sites':expected,
 'changes':changes,
 'runtime_erasure_changed':False,
 'collection_instances_changed':False,
 'control_flow_changed':False,
 'gameplay_logic_changed':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')
ok=(total==expected)
MD.write_text(
 '# Non-Protobuf Tail Local Generic Normalization 3\n\n'
 + f'Status: **{"PASS" if ok else "FAIL"}**\n\n'
 + f'- Generic/cast sites normalized: **{total} / {expected}**\n'
 + '- Runtime erasure / collection instances changed: **NO / NO**\n'
 + '- Control flow / gameplay logic changed: **NO / NO**\n',
 encoding='utf-8')
print(json.dumps(state,indent=2))
if not ok:
    raise SystemExit(f'expected {expected} sites, got {total}')
