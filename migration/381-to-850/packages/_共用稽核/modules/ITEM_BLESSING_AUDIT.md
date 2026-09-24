# 381 -> 850 Item Blessing System Audit

## Migration authority

Target authority:
- 850 core/runtime/UI/protocol/data model

381 is donor-only:
- source behavior reference
- source data reference

Do NOT port the 381 UI/core framework wholesale.

## Scope

381 table:
- `w_物品祝福系統`

Primary donor runtime:
- `New_BlessItem`
- `Reel_item_bless_weapon`
- `Reel_item_bless_armor`
- `Reel_item_bless_great`

## What the table contains

Current source rows define bonus vectors by equipment class/type.

Fields:
- type
- item_type
- ac
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
- All_message

The table does NOT define:
- blessing scroll item ID
- success chance
- failure-destroys-target policy

Those behaviors come from the item executor configuration for the three blessing scroll classes.

Therefore this table alone is not a complete installable module.

## Donor flow

### Weapon

`Reel_item_bless_weapon`:
1. target must be a weapon
2. target must not already be blessed/sealed
3. base item must allow blessing
4. roll against executor parameter `_xianzhi`
5. on success:
   - set `bless=0`
   - apply `New_BlessItem.forIntensifyweapon`
   - persist target
   - consume scroll
   - refresh item status
6. on failure:
   - consume scroll
   - optionally destroy target when executor parameter `type2=true`

### Armor

`Reel_item_bless_armor`:
- same general model
- accepts defined armor use types
- explicitly rejects cursed target

### Accessory

`Reel_item_bless_great`:
- accepts use types 23/24/37/40
- same blessing/failure model

## Permanent per-item bonus

A successful blessing does more than change the ordinary blessing flag.

`New_BlessItem` permanently adds configured values to the target `L1ItemInstance`, including:
- Attack
- Hit
- SP
- STR/DEX/INT/CON/WIS/CHA
- HP/MP
- MR
- damage reduction
- HPR/MPR
- potion recovery
- AC

It then persists the individual item fields through `CharItemsTable`.

Therefore this is:

```text
blessing state
+ persistent per-instance equipment stat mutation
```

not merely a static blessed-item flag.

## Current rule coverage

Current DB includes:
- armor/accessory categories
- multiple weapon categories

Weapon rule matching is based on base weapon type.
Armor/accessory rule matching is based on use type.

No item-ID-specific bonus table exists here.

## Critical donor issues

### 1. Blessing chance is outside this table

`_xianzhi` is supplied through the item executor configuration.

The split `w_物品祝福系統` SQL cannot reproduce probability behavior by itself.

### 2. Failure destruction is outside this table

`type2` controls whether failed blessing destroys the target item.

This is also executor configuration, not DB rule data.

### 3. Two broadcast layers exist

Success can broadcast through:
- `New_BlessItem.All_message`
- generic `BroadcastUtil` configuration in the scroll executor

A 850 migration should not preserve duplicate donor broadcast paths by default.

Use one 850-native announcement policy.

### 4. Runtime persists many item fields individually

The donor performs multiple persistence calls after mutation.

Target should use the 850 item persistence model atomically where possible.

### 5. Rules are category-wide

A rule applies to all items of the matching type if:
- target is blessing-eligible
- executor accepts its category

Migration must ensure 850 item classification values are semantically mapped, not copied numerically from 381.

## 850-first target strategy

Do NOT port `New_BlessItem` as a parallel equipment system.

Preferred target design:

```text
850 blessing action
  -> target eligibility
  -> success/failure policy
  -> set native blessing state
  -> apply target-native per-item bonus profile
  -> persist using 850 item persistence
  -> refresh using 850 item-status packet
```

Use existing 850 item-action selection/targeting behavior.

If 850 already has a general per-item modifier/power framework, represent blessed bonus as one reusable modifier profile.

If not, add only the minimal generic per-item bonus fields actually required by current active rows.

## 850 comparison

Targeted inspection did not prove a direct 850 equivalent of:
- `Reel_item_bless_*`
- `New_BlessItem`
- the same persistent blessed-bonus profile system

850 does have generic:
- item instance
- equipment slot
- inventory persistence
- item status/update primitives

but direct semantic coverage for these donor per-item fields is not yet proven.

Therefore:

```text
NATIVE_850_BLESS_BONUS=NOT_PROVEN
REUSABLE_850_PRIMITIVES=YES
```

## Client/UI dependency

The donor interaction is standard target-item usage plus normal item/status/system-message packets.

No dedicated custom HTML or custom binary protocol is proven.

```text
CLIENT_PROTOCOL_DEP=NO_PROVEN_CUSTOM_PROTOCOL
CLIENT_RESOURCE_DEP=NO_PROVEN_CUSTOM_RESOURCE
```

850 UI should remain authoritative.

## Recommended split

### Core action

A reusable 850-native blessing action:
- target validation
- probability
- optional target destruction on failure
- item state update
- audit/logging

### Bonus profiles

DB-backed category profiles:
- weapon type
- armor/accessory type
- stat deltas
- success announcement

### Scroll definitions

Keep chance/failure policy in the 850-native item/action configuration instead of embedding 381 executor classes.

## Difficulty

`LEVEL=L3`

Reason:
- persistent per-item mutation
- item action integration
- equipment stat integration
- persistence and rollback
- category mapping
- probability/failure policy from separate source
- no custom client dependency

This should be implemented in 850-native form, not as a donor runtime transplant.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_物品祝福系統
LEVEL=L3
TARGET_POLICY=850_NATIVE_FIRST
CORE_DEP=YES
DB_DEP=YES
PER_ITEM_PERSISTENCE=YES
BLESS_FLAG_CHANGE=YES
PER_ITEM_STAT_MUTATION=YES
SUCCESS_CHANCE_IN_TABLE=NO
FAIL_DESTROY_POLICY_IN_TABLE=NO
SCROLL_EXECUTOR_CONFIG_REQUIRED=YES
CLIENT_PROTOCOL_DEP=NO_PROVEN_CUSTOM_PROTOCOL
CLIENT_RESOURCE_DEP=NO_PROVEN_CUSTOM_RESOURCE
NATIVE_850_EQUIVALENT=NOT_PROVEN
850_REUSABLE_PRIMITIVES=YES
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=scroll configuration authority,category semantic mapping,target per-item modifier model,persistence equivalence
```
