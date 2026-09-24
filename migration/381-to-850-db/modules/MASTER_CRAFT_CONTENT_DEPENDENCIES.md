# Master Craft Content Dependency Audit

## Scope
Source: 381 `x_大師製作系統`
Target: 850 native `craft/craft_exchange`

## Row inventory
- total rows observed: 63
- visible: 61
- hidden/example: 2
- NPCs:
  - 99200: 2
  - 99201: 24
  - 99202: 37

## Feature complexity
Among the 61 visible rows:
- inherit material state/bless/extra stats: 0
- bonus output item: 0
- HP/MP cost: 0
- batch settlement: 0
- quantity input: 0
- custom output enchant mode: 0
- class restriction: 1
- chance <100: 10
- fail-return item: 10
- chance boost item: 1
- full-server announcement: 61

Interpretation:
Most rows are structurally compatible with 850 native `craft`.
The main cross-cutting missing behavior is the 381 success announcement.
Therefore:
- if announcement is optional, most recipes are effectively native Tier A.
- if announcement must be preserved, a small generic Tier B craft-success announcement hook is sufficient.
- no evidence that these 61 visible rows require MasterCraft's advanced state-inheritance engine.

## 850 recipe overlap
Compared visible 381 outputs against 850 `craft`:
- exact existing recipe outputs: 0/61
- same output already present in 850 craft: 0/61

These are content additions, not duplicate recipes.

## Critical dependency result: item definitions

Visible output item IDs: 61
Found in 850 weapon/armor/etcitem tables: 1
Missing from 850 item tables: 60

Only confirmed existing output:
- item 127: 鋼鐵瑪那魔杖

Examples of missing 381 custom outputs:
- 330000-330004 ability greaves
- 1310162/1310172/1310182 death-knight greaves
- 120480-120482 extreme death-knight greaves
- 401001/401002/401003 rings
- 401008-401011 belts
- 70220/70229/70238 earrings
- 70247/70257/70267 necklaces
- 240104-240114 map badges
- 240128-240137 VIP progression items

Material dependency:
- distinct material IDs: 68
- found in 850: 28
- missing from 850: 40

Missing materials include chained custom content such as:
- 330000-330005
- 1310171/1310181/1310191
- 240127-240137
- 240104-240113
- 240207-240217
- 240240
- 44070

## Migration consequence
`x_大師製作系統` cannot be migrated as a craft-table-only module.

Correct package boundary is CONTENT FAMILY based:

Example:
```
modules/craft-greaves/
  install_items.sql
  install_craft.sql
  rollback.sql
  DEPENDENCIES.md

modules/craft-vip-chain/
  install_items.sql
  install_craft.sql
  rollback.sql
  DEPENDENCIES.md

modules/craft-map-badges/
  install_items.sql
  install_craft.sql
  rollback.sql
  DEPENDENCIES.md
```

Each package must carry:
1. required weapon/armor/etcitem rows
2. any dependent item feature tables/classes
3. craft rows
4. NPC/menu/category binding
5. rollback order
6. client resource dependency check

## Important rule
Do NOT create placeholder item rows merely to satisfy craft foreign content.
Every missing item must be traced to its actual 381 definition and behavior before migration.

Item definition audit must include:
- base table row
- classname / custom executor
- item power/effect tables
- equip/use hooks
- image/name/client resource dependencies

## Current difficulty
Recipe engine itself: mostly **L2**.
Content dependency bundle: **L2-L3 per family**, depending on item behavior.

Therefore the next unit of analysis should not be “all MasterCraft”.
Split by content family and trace each family's missing item dependencies first.
