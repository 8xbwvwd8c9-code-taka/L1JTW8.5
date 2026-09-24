# Master Craft Family Audit: High-Level Skill Exchange

## Scope
381 MasterCraft category: 高級技能兌換

Visible outputs include:
- Royal: 王者之劍
- Knight: 衝擊之暈, 反擊屏障
- Elf: 三重矢, 大地屏障, 水之防護, 污濁之水, 精準射擊, 烈焰之魂
- Mage: 靈魂昇華, 絕對屏障, 聖結界, 流星雨, 究極光裂術
- Dark Elf: 雙重破壞, 破壞盔甲

## Major compatibility finding: item IDs changed

381 MasterCraft stores 381 item IDs directly.
850 already contains most of these skills/books/crystals, but under different item IDs.

Examples:
- 381 40164 技術書(衝擊之暈) -> 850 41526
- 381 41148 技術書(反擊屏障) -> 850 41530
- 381 40240 精靈水晶(三重矢) -> 850 41571
- 381 40249 精靈水晶(大地屏障) -> 850 41596
- 381 41151 精靈水晶(水之防護) -> 850 41599
- 381 41152 精靈水晶(污濁之水) -> 850 41612
- 381 41153 精靈水晶(精準射擊) -> 850 41613
- 381 41149 精靈水晶(烈焰之魂) -> 850 41614
- 381 40224 魔法書(靈魂昇華) -> 850 41518
- 381 40223 魔法書(絕對屏障) -> 850 41517
- 381 40213 魔法書(聖結界) -> 850 41507
- 381 40219 魔法書(流星雨) -> 850 41513
- 381 40222 魔法書(究極光裂術) -> 850 41516
- 381 40275 黑暗精靈水晶(雙重破壞) -> 850 41546
- 381 80038 黑暗精靈水晶(破壞盔甲) -> 850 41551

Therefore:
**Do not import 381 item IDs.**
Rebuild the craft rows against 850 item IDs.

## Runtime compatibility

850 already has native spellbook/skill-item definitions for the mapped outputs.
That means:
- no 381 skill-learning ItemExecutor should be ported for these standard books/crystals
- use 850 existing item definitions and existing skill IDs
- only add craft/exchange data

This family is therefore much easier than the VIP/badge family.

## Exception: Royal 王者之劍

381 defines 王者之劍 with custom classname:
- `teleport.ItemKingShockStun`

No equivalent 850 item was confirmed in the current item-table evidence.

This is NOT a normal skillbook and must be separated from the rest of the high-level skill exchange.

Classification:
- Knight/Elf/Mage/Dark Elf standard skill books: **L2**
- Royal 王者之劍: **L3 provisional**, pending audit of `ItemKingShockStun`

## Material dependency

All shown recipes consume:
- adena 40308 x10,000,000
- custom material 240240 x100

So the standard skill-book recipes still depend on item 240240.
Before implementation:
- recover exact 381 definition/behavior for 240240
- determine whether 850 already has a semantic equivalent
- if 240240 is merely a currency/token, migrate its item row independently
- if it uses a custom executor, keep that dependency explicit

## Correct migration model

Do not port:
- 381 spellbook etcitem rows
- 381 skill-learning executors
- old 381 item IDs

Instead create a native 850 craft package that maps:
```
381 semantic item -> 850 existing item_id
```

Example:
```
craft-high-skill-exchange/
  install_craft.sql
  DEPENDENCIES.md
  rollback.sql
  item-id-map.md
```

Required dependencies:
- 850 standard skillbook/crystal rows (already present)
- 40308 adena
- 240240 or its migrated equivalent
- craft NPC/category binding

## Difficulty
Standard high-level skill exchange:
**L2 confirmed**

Royal custom sword:
**L3 provisional**

## Key rule
For cross-version migrations, semantic identity (skill/name/function) overrides numeric item ID.
Never preserve donor item IDs when the target already has the same native item under a different ID.


## 240240 dependency resolved

381 `droplist` uses item 240240 as **Boss證明**.
Observed drops:
- numerous boss NPCs
- quantity 1
- configured drop rate 1000000 in the donor data

381 `etcitem` row for `Boss證明` is a plain item:
- classname: `0`
- type: other
- use type: normal
- material: paper
- no custom executor

Therefore 240240 is a simple token/currency item, not a runtime-core dependency.

Migration consequence:
- create/migrate one plain Boss證明 etcitem row using an 850-safe item ID
- map all high-skill craft recipes to that migrated token ID
- optionally migrate boss droplist rows as a separate acquisition module
- do not hard-code donor 240240 if the target ID namespace conflicts

This removes the last core blocker for the standard skill exchange.

### Final classification
Standard Knight/Elf/Mage/Dark-Elf high-skill exchange:
**L2 confirmed / implementation-ready design**

Dependencies:
- 850 existing native skillbook/crystal IDs
- adena 40308
- migrated plain Boss證明 token
- native 850 craft rows
- optional boss-drop acquisition package

Royal 王者之劍 remains separate because its behavior is custom.

## Royal 王者之劍 behavior resolved

381 class:
`com.lineage.data.item_etcitem.teleport.ItemKingShockStun`

Behavior:
- Crown-only use
- blocked in safety zone
- cooldown flag skill id 9001
- consumes configurable MP
- consumes one configurable magic gem
- targeted L1Character in range
- configurable success chance
- configurable stun duration min/max
- applies configured stun skill effect
- spawns configured visual effect NPC
- supports PC, monster, summon and pet targets

This is not a passive item and not a standard skillbook.

It depends on:
- `ConfigPrinceSkill.KING_SWORD_MP_COST`
- `KING_SWORD_GEM_ID`
- `KING_SWORD_RANGE`
- `KING_SWORD_COOLDOWN`
- `KING_SWORD_CHANCE`
- `KING_SWORD_MIN_SEC/MAX_SEC`
- `KING_SWORD_STUN_SKILL`
- `KING_SWORD_EFFECT_NPCID`
- target/action item-use packet plumbing

Classification:
**L3 confirmed**

Keep it out of the standard skill-exchange L2 package.
