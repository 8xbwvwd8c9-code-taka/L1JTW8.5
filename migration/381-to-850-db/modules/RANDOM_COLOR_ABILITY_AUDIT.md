# 381 -> 850 Random Color Ability Audit

## Scope

381 tables:
- `w_隨機能力炫色名稱`
- `w_隨機能力炫色武器`
- `w_隨機能力炫色防具`

This audit intentionally does NOT merge:
- `w_炫色_素質設定`

That is a separate, broader color/stat rule family and must be traced independently.

## Current DB content

### Color/type names

`w_隨機能力炫色名稱` contains 7 visible type names:

```text
1 紫紅
2 土黃
3 紫
4 綠
5 紅
6 暗綠
7 暗藍
```

### Weapon ability rows

`w_隨機能力炫色武器` contains 7 visible rows keyed by:
- `check_type=1..7`

Each row defines:
- `ran`
- Attack
- Hit
- Sp
- Str
- Dex
- Int
- Con
- Cha
- Wis
- Hp
- Mp
- Mr
- ReductionDmg
- Hpr
- Mpr
- hppotion

### Armor ability rows

`w_隨機能力炫色防具` contains the same 7-type structure with armor-specific values.

All current split files are INSERT-only; authoritative CREATE schema is not proven.

## Entry item / trigger family

Observed item executors:
- `Armor_Color`
- `Armor_Color1`
- GM variants
- box/NPC variants

For a target item:
- weapon paths use `drop_type_weapon_*`
- armor paths use `drop_type_armor_*`

The system checks whether the base item is eligible for color/random-power processing.

## Persistent type marker

The donor uses:

```text
item.getItemArmorType()
item.setItemArmorType(...)
```

as the persistent random/color ability type marker.

Values:
```text
0 = no current color ability
1..7 = configured random color type
```

This field name is misleading: it is not simply the item's ordinary armor-slot type in this subsystem.

Target migration must not map it blindly to an unrelated 850 armor type field.

## Random selection flow

Example weapon path:
1. pick a random type in `1..7`
2. locate the matching `check_type` row
3. roll `1..100 <= ran`
4. if success:
   - set `itemArmorType = check_type`
   - add configured per-item stats
   - persist each stat field
5. on failure:
   - donor path may retry/fall through depending on the specific executor variant

The reset/clean paths:
1. inspect current `itemArmorType`
2. subtract the previously configured row's stats
3. set `itemArmorType=0`
4. invoke a fresh roll path

Therefore the current type marker is required to reverse old bonuses correctly.

## Per-item stat persistence

The donor directly mutates `L1ItemInstance` fields such as:
- itemStr
- itemDex
- itemInt
- itemCon
- itemWis
- itemCha
- itemHp
- itemMp
- itemSp
- itemAttack
- itemBowAttack
- itemHit
- itemBowHit
- itemMr
- itemReductionDmg
- itemHpr
- itemMpr
- itemhppotion
- other extended item-power fields in related variants

It then calls `CharItemsTable` update methods, including:
- `updateItemAttack`
- `updateItemHit`
- `updateItemSp`
- `updateItemStr`
- `updateItemDex`
- `updateItemInt`
- `updateItemCon`
- `updateItemWis`
- `updateItemCha`
- `updateItemHp`
- `updateItemMp`
- `updateItemMr`
- `updateItemReductionDmg`
- `updateItemHpr`
- `updateItemMpr`
- `updateItemhppotion`
- `updateItemArmorType`

This is a persistent per-instance equipment mutation system, not static weapon/armor table content.

## Equip/combat dependency

Because bonuses live on the item instance, full migration requires the target equipment/combat stack to actually consume those per-instance fields while the item is equipped.

Required proof includes:
- item-load persistence
- equip apply/remove
- attack/hit/SP/stat paths
- HP/MP/MR/reduction/HPR/MPR
- potion recovery
- any other extended fields used by active variants

A DB-only migration is insufficient.

## Item display dependency

The donor sends:
- `S_ItemStatus(item)`
- inventory update/save calls

and stores a color/type marker.

The current source evidence does not prove a custom binary protocol uniquely required by this subsystem.

Therefore:

```text
CLIENT_PROTOCOL_DEP=NOT_PROVEN
CLIENT_DISPLAY_DEP=YES
```

The exact name/color presentation path must be validated separately.

## Donor architecture hazards

### 1. Misnamed persistence field

`itemArmorType` is overloaded as the color/ability type ID.

Target must use a dedicated field such as:
```text
random_affix_type
```
instead of overloading an unrelated armor semantic.

### 2. Rule rollback depends on mutable DB configuration

The clean/reset path subtracts stats according to the CURRENT DB row associated with the stored type.

If administrators change a type's stat values after items were rolled, old items can be cleaned using new values, causing stat drift.

Target should persist either:
- the actual rolled affix values per item, or
- an immutable/versioned affix definition ID.

### 3. Multiple persistence writes per roll

The donor writes many item columns individually.

Partial failure can leave a mixed/partially persisted affix.

Target should persist the affix state atomically.

### 4. Loader is lazy one-shot

The `drop_type_*` classes use static `BUILD_DATA` and load once.

Runtime DB edits are not proven to hot-reload consistently.

### 5. SQL exceptions are often swallowed

Several donor loaders simply return on `SQLException`.

Target should fail validation loudly.

### 6. Probability model is type-first then chance

The donor first chooses one of 7 types, then applies that row's `ran`.

Therefore effective probability is not simply the configured `ran` percentage across all rolls.

Example with equal type selection:
```text
effective(type X success) ≈ 1/7 * ran%
```

unless a retry loop in the specific entry variant changes the eventual distribution.

Migration must preserve the intended entry-path semantics, not just copy `ran`.

### 7. Current name table is a separate mapping

`w_隨機能力炫色名稱` provides textual/color labels, while weapon/armor rows provide stats.

Do not infer row identity from array order; use explicit `check_type` mapping.

## 850 comparison

850 already has some item-power / per-instance equipment infrastructure from other audited modules, but an equivalent seven-type random color-affix framework has not been proven.

Required target capabilities still need explicit proof:
- persistent per-item affix type/value fields
- load/save
- equip-time stat application
- item-status display
- reroll/reset semantics

Therefore:

```text
NATIVE_850_EQUIVALENT=NOT_PROVEN
```

## Recommended migration shape

```text
random-color-affix-core/
  immutable/versioned affix definition
  weapon/armor eligibility
  roll engine
  per-item persistent affix record
  atomic save
  equip/remove integration
  reset/reroll
  item-status rendering

random-color-affix-rules/
  type names
  weapon definitions
  armor definitions
```

Prefer persisting the actual affix values or immutable affix-version identity so later configuration edits cannot corrupt existing items.

## Difficulty

`LEVEL=L3`

Reason:
- per-item persistent state
- equipment/combat integration
- reroll/reset
- many stat APIs
- DB rule loaders
- no proven custom client protocol

If later validation proves the colored item display itself requires unsupported custom client resources/protocol, UI/display can be split into a separate L4 dependency rather than inflating the core now.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_隨機能力炫色名稱+w_隨機能力炫色武器+w_隨機能力炫色防具
LEVEL=L3
CORE_DEP=YES
DB_DEP=YES
PER_ITEM_PERSISTENCE=YES
TYPE_COUNT=7
WEAPON_RULES=7
ARMOR_RULES=7
TYPE_MARKER=itemArmorType
RESET_DEPENDS_ON_RULE_DATA=YES
ATOMIC_PERSISTENCE=NO
CLIENT_DISPLAY_DEP=YES
CLIENT_PROTOCOL_DEP=NOT_PROVEN
NATIVE_850_EQUIVALENT=NOT_PROVEN
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=schema,target per-item fields,equip/combat field coverage,atomic persistence,display mapping,reroll semantics
```
