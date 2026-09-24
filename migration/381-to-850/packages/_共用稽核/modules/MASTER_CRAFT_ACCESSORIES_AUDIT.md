# Master Craft Family Audit: Accessories

## Scope
381 MasterCraft accessory outputs:
- 401001 / 401002 / 401003: Hylas rings
- 401008 / 401009 / 401010 / 401011: Light Einhasad belts
- 70220 / 70229 / 70238: Meister earrings
- 70247 / 70257 / 70267: Eternal necklaces

## Core/item behavior result

All located 381 armor definitions use:
- `classname='0'`

No matching dependency rows were found in:
- `armor_set`
- `w_裝備總加成能力`
- `w_裝備持續特效`

Therefore these items do not currently depend on a custom ItemExecutor, set bonus, or persistent equipment-effect module.

Their effects are encoded directly in ordinary armor columns.

## Families

### Hylas rings
Examples:
- 力量海力斯戒指
- 敏捷海力斯戒指
- 魔力海力斯戒指

Observed direct fields include:
- AC
- STR/DEX
- HP/MP
- HPR/MPR
- MR / standard stat columns

Classification: **L2**

### Light Einhasad belts
Examples:
- 光之殷海薩力量腰帶
- 光之殷海薩敏捷腰帶
- 光之殷海薩智力腰帶
- 光之殷海薩魅力腰帶

Base and +1..+5 variants are separate armor rows in 381.
Effects scale through ordinary armor columns.

No custom classname found.

Classification:
- base belt rows: **L2**
- staged +1..+5 rows: **L2 data/content**, but progression/upgrade path must be traced separately.
  Do not assume normal enchant mechanics because these appear as distinct item definitions.

### Meister earrings
Examples:
- 麥斯特的紅光耳環
- 麥斯特的藍光耳環
- 麥斯特的紫光耳環
- stage 1..8 rows exist as distinct armor definitions

Again:
- classname 0
- stats encoded directly in armor columns
- no matching set/effect-table dependency found

Classification:
- item definitions: **L2**
- stage progression source: **needs separate upgrade-system trace**

### Eternal necklaces
Examples:
- 力量永恆項鍊
- 敏捷永恆項鍊
- 智力永恆項鍊
- additional HP/MP variants
- stage 1..9 rows exist

Effects are direct armor-row stats.
No custom classname/effect-table dependency found.

Classification:
- item definitions: **L2**
- stage progression: **needs separate upgrade-system trace**

## 850 compatibility

850 armor schema already supports the major fields needed by these rows:
- armor type ring/amulet/belt/earring
- STR/DEX/CON/INT/WIS/CHA
- HP/MP
- HPR/MPR
- MR
- damage reduction
- hit/damage modifiers
- PVP damage/reduction
- magic hit
- exp and other extended stats

So there is no current evidence that these accessory items require a new equip-core model.

## Main blocker

Same as the greaves audit:
381 split `armor_202609221205.sql` does not contain `item_id` in its INSERT column list.

Names/stats are available, but item identity must be recovered from:
- authoritative full 381 dump, or
- live DB query

Do not infer item IDs from row order.

## Recommended package split

```
craft-hylas-rings/
craft-einhasad-belts/
craft-meister-earrings/
craft-eternal-necklaces/
```

Each package:
- exact armor rows with item IDs
- native 850 craft rows
- required material rows
- client invgfx/name/resource validation
- rollback

Progression systems for staged variants are separate modules and must not be bundled unless direct dependency is proven.

## Difficulty
Item + craft migration:
- **L2**

Possible stage-upgrade mechanism:
- separate audit; likely belongs to one of the 381 item-upgrade modules rather than MasterCraft itself.
