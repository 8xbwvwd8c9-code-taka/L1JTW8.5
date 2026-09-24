# 381 -> 850 Random Drop Enchant Audit

## Migration authority
- 850 is the target/core authority.
- 381 is donor/reference only.
- Empty source data is **not** evidence that the feature can be skipped.

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

Therefore only the following is proven:
```text
DATA_STATE=NO_ACTIVE_DATA
SOURCE_SCHEMA=NOT_PROVEN
```

This does **not** justify `SKIP`, because the donor runtime is already proven.

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
`getEnchant(...)` chooses one configured array element uniformly at random. Duplicate values implicitly act as weights.

## Drop hook
381 `SetDrop.additem(...)` creates normal droplist items, then:
1. gets random-enchant rules for `npc.getNpcId()`
2. finds matching `itemId`
3. chooses one configured enchant value
4. calls `item.setEnchantLevel(level)`
5. stores the generated item

For non-stackable drops, generated items roll independently.

## Donor hazards
### Loader mutates production data
If a configured `item_id` does not exist, donor loader executes a DELETE against the configuration table. Target must validate/log instead of destructively repairing configuration.

### Empty arrays
The random selector assumes at least one parsed enchant value.

### CSV duplicates
Repeated values alter probability by index weighting. Preserve only if authoritative data proves this is intended.

### Stackable items
Donor applies enchant metadata on the stackable branch too. 850 must validate allowed item types.

## 850 comparison
850 native `DropTable` already reads:
```text
droplist.enchantlvl
```

and applies a fixed enchant value to generated drops.

Therefore:
```text
850_FIXED_DROP_ENCHANT=YES
850_RANDOM_ENCHANT_LIST=NOT_PROVEN
```

A single deterministic enchant can map to the 850 native drop table. A random list cannot be assumed equivalent to multiple droplist rows because that can change drop-count/chance semantics.

## Migration paths
```text
PATH_A: one deterministic enchant per mob/item
  -> 850 droplist.enchantlvl
  -> SERVER_LEVEL=L2

PATH_B: multiple random enchant outcomes / weighted outcomes
  -> minimal 850 drop-generation rule extension
  -> SERVER_LEVEL=L3
```

The active path cannot be finalized while source rows/schema are unavailable.

## Decision
```text
STATUS=HOLD
DATA_STATE=NO_ACTIVE_DATA
DONOR_RUNTIME=PROVEN
CURRENT_MIGRATION=HOLD
DIFFICULTY=NOT_FINAL
```

Do not port the donor loader/hook wholesale. When authoritative rows appear, choose the smallest 850-native path required by the actual semantics.

## Status
```text
STATUS=HOLD
AUDIT=PASS
MODULE=w_怪物掉落隨機強化
TARGET_POLICY=850_NATIVE_FIRST
SOURCE_SQL=EMPTY_0_BYTES
SOURCE_SCHEMA=NOT_PROVEN
DATA_STATE=NO_ACTIVE_DATA
DONOR_RUNTIME=PROVEN
DROP_HOOK=SetDrop.additem
CORE_DEP_IF_USED=YES
DB_DEP_IF_USED=YES
CLIENT_DEP=NO
850_FIXED_DROP_ENCHANT=YES
850_RANDOM_LIST_EQUIVALENT=NOT_PROVEN
L2_PATH=fixed_enchant_native_droplist
L3_PATH=random_or_weighted_per_instance_extension
DIFFICULTY=NOT_FINAL
CURRENT_MIGRATION=HOLD
PRODUCTION_PORT=NO
BLOCKERS=authoritative schema/data and intended random-enchant semantics
```
