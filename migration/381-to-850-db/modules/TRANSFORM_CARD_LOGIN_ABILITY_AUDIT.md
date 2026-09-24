# 381 -> 850 Transformation Card Login Ability Audit

## Scope

381 table:
- `w_變身卡片能力登入`

Current source:
- SOURCE_ROWS=64
- each row binds:
  - unlock quest id
  - polymorph id/time
  - optional polymorph consumable
  - additive stat vector
  - HTML/action command metadata
- split artifact is INSERT-only
- CREATE schema not proven

Primary donor loader:
- `com.add.system.ACardTable`

Runtime model:
- `com.add.system.ACard`
- `com.add.system.CardBookCmd`

Related set-bonus loader:
- `com.add.system.CardSetTable`
- table `w_變身卡片能力組合套卡`

## Proven ownership model

Card ownership/unlock is represented by quest completion:

```text
pc.getQuest().get_step(card.getQuestId()) != 0
```

This is used by `CardBookCmd` to:
- determine whether a card is logged/unlocked
- allow polymorph use
- aggregate/display unlocked card stats

Therefore:
```text
UNLOCK_OWNER=QUEST_STATE
CARD_IDENTITY=TABLE_ROW/QUEST_ID
POLY_USE=PROVEN
STAT_DISPLAY_AGGREGATION=PROVEN
STAT_APPLICATION_ON_LOGIN=NOT_PROVEN
```

Do not assume displayed aggregate stats are actually applied until the login/recalc hook is traced.

## Polymorph path

`CardBookCmd` command `polycard`:
- fetches currently selected `ACard`
- requires unlock quest state
- consumes configured polymorph material if required
- calls 381 `L1PolyMorph.doPoly`
- uses per-row polymorph time

Current data:
- early cards generally use 3600 seconds
- later mythic cards generally use 1800 seconds
- configured consume item is commonly 40308 x1

Semantic item and polymorph IDs require 850 mapping.

## UI dependency

The donor uses custom HTML/dialog actions:
- row command `a1..a64`
- `card_0`
- `card_10`
- `card_11`
- commands `polycard`, `cardset`, `cardset2`

Therefore the donor presentation layer is not server-only.

850 should preserve card/unlock semantics while preferring 850-native UI/client behavior where available.

## Stat vector

Rows can define:
- STR/DEX/CON/INT/WIS/CHA
- AC
- HP/MP
- HPR/MPR
- melee/ranged damage
- melee/ranged hit
- physical/magic reduction
- SP
- magic hit
- MR
- fire/wind/earth/water resistance

This vector overlaps the shared modifier vocabulary already seen in:
- `w_道具狀態`
- `w_指定道具賦予狀態`
- transform-status family

A shared 850 StatModifierDefinition can represent the numeric payload.

However lifecycle ownership differs:
```text
CARD_COLLECTION_OWNER = unlocked quest/card collection state
```

It must not reuse timed-buff or equipped-item ownership.

## Critical runtime gap

`CardBookCmd.CardAllSet()` proves aggregate calculation for display only.

The targeted trace did not yet prove where the same unlocked-card and set-bonus vectors are applied to player stats at login/recalc.

Therefore:
```text
LOGIN_STAT_APPLY=NOT_PROVEN
LOGIN_REAPPLY_IDEMPOTENCE=NOT_PROVEN
LOGOUT_CLEANUP=NOT_PROVEN
RECALC_OWNER=NOT_PROVEN
```

This is the primary blocker before migration design is considered closed.

## 850-first model

Preferred target split:

```text
CardDefinition
  unlock identity
  polymorph identity
  polymorph duration
  consume requirements
  modifier vector

CollectionOwner
  authoritative unlocked set
  quest/collection persistence
  aggregate modifier recomputation

PolymorphAction
  native 850 polymorph path

Presentation
  850-native UI where possible
```

For stat safety, prefer recomputing:
```text
EffectiveCardBonus = sum(all authoritative unlocked card/set vectors)
```
from collection state instead of replaying incremental add operations at every login.

## Client dependency

Active current data contains many custom polymorph IDs and HTML/dialog actions.

Therefore:
```text
CLIENT_POLY_MAPPING_REQUIRED=YES
CUSTOM_HTML_CURRENT_DONOR=YES
CLIENT_DEP=YES
```

Do not assume 381 polymorph IDs are valid in 850.

## Classification

`LEVEL=L4`

Reason:
- active 64-row collection
- quest-based persistent ownership
- stat aggregation
- polymorph IDs
- consumable polymorph actions
- custom HTML/dialog layer
- actual login stat-application hook still unclosed

If 850-native UI and polymorph mappings cover the content, server modifier portion itself may be L3.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_變身卡片能力登入
LEVEL=L4
SOURCE_ROWS=64
DONOR_LOADER=ACardTable
DONOR_MODEL=ACard
DONOR_UI=CardBookCmd
UNLOCK_OWNER=QUEST_STATE
POLY_USE=PROVEN
STAT_DISPLAY_AGGREGATION=PROVEN
STAT_APPLICATION_ON_LOGIN=NOT_PROVEN
SHARED_MODIFIER=YES_STAT_VECTOR_ONLY
850_NATIVE_POLYMORPH=YES
CLIENT_DEP=YES
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=login/recalc stat application hook; semantic polymorph mapping; custom UI migration; quest/card identity mapping; set-bonus integration; apply-once/recompute ownership
```
