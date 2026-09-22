# 381 -> 850 Random Drop Enchant Audit

## Scope
Module: `w_怪物掉落隨機強化`

381 runtime:
- `DropItemEnchantTable`
- `L1DropEnchant`
- `SetDrop.additem(...)`

## Current source content
Current split artifact:
```text
DB/381_DB_AI用/w_怪物掉落隨機強化_202609221205.sql
SIZE=0 bytes
```

Therefore:
```text
ACTIVE_CONTENT=NONE_PROVEN
SOURCE_SCHEMA=NOT_PROVEN
```

Do not synthesize CREATE TABLE from runtime accessors.

## Donor runtime semantics
`DropItemEnchantTable.load()` queries:
```sql
SELECT * FROM w_怪物掉落隨機強化
```

Runtime expects:
- `npc_id`
- `item_id`
- `drop_enchants`

`drop_enchants` is a comma-separated integer list.

Rules are grouped by NPC ID and matched again by item ID at drop creation time.

`getEnchant(...)` chooses:
```text
one array element uniformly at random
```

If duplicate values are present in the CSV, they implicitly act as weights.

## Drop hook
381 `SetDrop.additem(...)` creates normal droplist items, then:
1. gets random-enchant rules for `npc.getNpcId()`
2. finds matching `itemId`
3. chooses one configured enchant value
4. calls `item.setEnchantLevel(level)`
5. stores the generated item

For non-stackable drops, every generated item rolls independently.

## Donor hazards

### 1. Loader mutates production data
If a configured `item_id` does not exist, `DropItemEnchantTable.errorItem(...)` executes:
```sql
DELETE FROM w_怪物掉落隨機強化 WHERE item_id=?
```

A loader should not destructively repair configuration. Target migration must fail validation/log instead.

### 2. Empty enchant arrays are unsafe
The random selector assumes at least one parsed enchant value.

### 3. CSV duplicates alter probability
Because selection is by array index, repeated enchant values increase their probability. This may be intentional weighting or accidental duplication; preserve only after data authority is known.

### 4. Stackable items also receive enchant
The donor hook applies an enchant value even on the stackable branch. Target should validate allowed item types rather than blindly applying enchant metadata.

## 850 comparison
850 native `DropTable` already reads:
```text
droplist.enchantlvl
```

and applies that fixed enchant value to generated drops.

Thus:
```text
850_FIXED_DROP_ENCHANT=YES
850_RANDOM_ENCHANT_LIST=NOT_PROVEN
```

A single fixed enchant rule can be expressed natively in 850. A per-drop random list cannot be proven equivalent by simply adding multiple droplist rows, because duplicate droplist rows can alter drop-count/chance semantics.

## Migration decision
Current content is empty, so there is no active behavior to migrate.

```text
CURRENT_CONTENT_MIGRATION=SKIP
```

If future source rows appear:
- one deterministic enchant per mob/item -> rewrite to 850 native `droplist.enchantlvl` (L2 candidate)
- multiple random enchant outcomes -> minimal drop-generation extension required (L3)

Do not port the donor loader/hook until active content demonstrates the need.

## Difficulty
```text
FRAMEWORK_LEVEL=L3
CURRENT_CONTENT=NONE
CURRENT_MIGRATION=SKIP
```

Framework L3 reason: random per-instance enchant requires a drop-generation hook and rule loader.
Current migration skip reason: zero active source content and 850 already supports fixed enchant natively.

## Status
```text
STATUS=PASS
MODULE=w_怪物掉落隨機強化
FRAMEWORK_LEVEL=L3
CURRENT_CONTENT=NONE
CURRENT_MIGRATION=SKIP
CORE_DEP_IF_USED=YES
DB_DEP_IF_USED=YES
DROP_HOOK=SetDrop.additem
CLIENT_DEP=NO
850_FIXED_DROP_ENCHANT=YES
850_RANDOM_LIST_EQUIVALENT=NOT_PROVEN
SOURCE_SQL=EMPTY_0_BYTES
SOURCE_SCHEMA=NOT_PROVEN
PRODUCTION_PORT=NO
```
