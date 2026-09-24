# 381 -> 850 Designated Item Status Audit

## Scope
381 table: `w_指定道具賦予狀態`

Current split content:
- SOURCE_ROWS=158
- distinct type groups: 0,1,2,3,4,5,7,8,9,10,11,16,17,18,22,23,24,25,26,27,28
- type 28 alone contains 60 rows

Primary donor runtime:
- `com.lineage.data.item_etcitem.add.VIP`
- `com.lineage.server.datatables.ItemVIPTable`
- `com.lineage.server.templates.L1ItemVIP`

## Proven donor model

This is NOT the same lifecycle as `w_道具狀態`.

`w_道具狀態` is timed-effect based.
`w_指定道具賦予狀態` is persistent item-toggle/equipped-state based.

Execution:
```text
use configured item
-> VIP.execute()
-> if already equipped:
     clear equipped flag
     ItemVIPTable.deleItemVIP()
     persist inventory equip state
   else:
     scan inventory for equipped configured item with same type
     if same type found -> reject
     else:
       set equipped flag
       ItemVIPTable.addItemVIP()
       persist inventory equip state
```

Thus `type` is an exclusivity group and one equipped donor item per type is allowed.

## Runtime effects

`ItemVIPTable.addItemVIP` can apply:
- polymorph
- custom displayed title
- weapon magic damage/proc values
- STR/DEX/CON/INT/WIS/CHA
- AC
- max HP/max MP
- HPR/MPR
- melee/ranged damage/hit
- physical/magic reduction
- MR/SP
- elemental resistance
- abnormal-status resistance
- EXP modifier
- spawned skin
- adena/GF modifier
- effect icon

`deleItemVIP` attempts to subtract/reverse the same vector.

## Persistence ownership

The donor does not use `character_buff` for the core item state.
It persists `L1ItemInstance.isEquipped()` through inventory update/save.

This makes inventory equipment state the authoritative ownership signal for this module.

Do NOT merge this lifecycle with the timed-effect persistence model from `w_道具狀態`.

## Current-content observations

The 158 rows include multiple semantic families:
- progression badges/relics
- VIP items
- event badges
- title-bearing items
- polymorph-card style items
- high-value permanent-on-equip stat vectors

Type 28 contains 60 transformation-card-like rows and uses poly IDs plus large stat vectors.

Current source also contains values requiring semantic validation before migration, including:
- donor poly IDs
- title strings
- effect_icon
- skin_id
- weapon_dmg / weapon_pro
- GF/adena/EXP modifiers

## 850-first decision

850 already has:
- inventory item instances
- equipment state/persistence primitives
- stat APIs
- polymorph
- status packets

But an equivalent generic "equip any etcitem as a mutually-exclusive passive modifier" framework is NOT proven.

Therefore:
```text
850_NATIVE_INVENTORY_STATE=YES
850_NATIVE_STAT_PRIMITIVES=YES
850_NATIVE_POLYMORPH=YES
850_NATIVE_GENERIC_PASSIVE_ITEM_FRAMEWORK=NOT_PROVEN
```

Preferred target is a small 850-owned passive-item/effect adapter if these rows are retained.

Do not port `ItemVIPTable` wholesale.

## Important fusion boundary

`w_道具狀態` and `w_指定道具賦予狀態` share a stat-vector vocabulary but have different ownership:

```text
w_道具狀態
  owner = timed effect
  expiry = timer
  persistence = buff remaining-time policy

w_指定道具賦予狀態
  owner = equipped item instance
  expiry = unequip/remove
  persistence = inventory equipment state
```

They may share ONE reusable stat-modifier definition/apply/remove component in 850,
but MUST NOT share the same lifecycle/state owner.

## Client dependency

Current donor runtime includes:
- polymorph IDs
- title display
- effect icons
- optional skin entities
- custom extended item-slot display behavior in `VIP.execute()`

Therefore client/resource dependency is active for at least part of current content.

This cannot be treated as pure server-only DB mapping.

## Risks

- double add on login/re-equip if equipment restore and modifier restore both fire
- double subtract on repeated unequip/remove
- item removed/destroyed while equipped without cleanup
- same-type exclusivity not enforced atomically
- title/poly/skin cleanup can remove an unrelated newer effect
- current/max HP/MP invariant after removing large bonuses
- semantic mismatch of donor poly/effect IDs in 850
- old donor class/type semantics may not match 850

## Classification

`LEVEL=L4`

Reason:
- 158 active rows
- persistent item ownership
- mutually-exclusive passive groups
- large reversible stat vectors
- polymorph/title/effect/skin client-visible behavior
- inventory slot/equipment UI coupling
- no proven direct 850 equivalent framework

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_指定道具賦予狀態
LEVEL=L4
TARGET_POLICY=850_NATIVE_FIRST
SOURCE_ROWS=158
DONOR_EXECUTOR=VIP
DONOR_RUNTIME=ItemVIPTable
OWNERSHIP=EQUIPPED_ITEM_INSTANCE
TYPE_EXCLUSIVITY=PROVEN
850_NATIVE_INVENTORY_STATE=YES
850_NATIVE_STAT_PRIMITIVES=YES
850_NATIVE_POLYMORPH=YES
850_NATIVE_GENERIC_PASSIVE_ITEM_FRAMEWORK=NOT_PROVEN
SHARED_WITH_ITEM_STATUS=STAT_VECTOR_ONLY
SHARED_LIFECYCLE=NO
CLIENT_DEP=YES_PARTIAL
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=CREATE schema absent; semantic item mapping; poly/title/effect/skin mapping; inventory-slot UI compatibility; login apply-once ownership; safe cleanup ordering; HP/MP clamp; type-group policy validation
```
