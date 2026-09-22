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
