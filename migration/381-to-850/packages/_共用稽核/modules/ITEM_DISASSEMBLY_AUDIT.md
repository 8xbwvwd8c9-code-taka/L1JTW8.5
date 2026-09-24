# 381 -> 850 Item Disassembly Audit

## Migration authority

Target authority:
- 850 core/runtime/UI/protocol/data model

381 is used only as:
- functional intent donor
- source data donor

Do NOT port a 381 UI/core framework merely to reproduce donor implementation details.

## Scope

381 table:
- `w_物品分解系統`

Current split source contains one row:

```text
道具編號       = 92058
被分解物品     = 41
分解名稱       = 武士刀
機率           = 42
獲得物品       = 40308
物品名稱       = 細劍
獲得最小數量   = 1
獲得最大數量   = 5
分解成功訊息   = \aD分解成功
分解失敗訊息   = \aG分解失敗
```

The split artifact is INSERT-only; CREATE schema is not present.

## Runtime trace result

A targeted search of the current 381 source did NOT find a runtime that reads:
- `w_物品分解系統`
- `被分解物品`
- `獲得最小數量`
- `分解成功訊息`

The current class named:

```text
com.lineage.william.ItemRecycle
```

is NOT this system.

It currently implements a later, separate:
- `x_刪物自動回收系統`

with delete-item -> currency recycling semantics.

Therefore:

```text
DONOR_RUNTIME=NOT_PROVEN
DONOR_UI=NOT_PROVEN
DONOR_TRIGGER=NOT_PROVEN
```

Do not infer this table's old runtime from `ItemRecycle`.

## What the DB row proves

The data itself proves the source model contains fields for:
- one control/source item ID
- one target item ID
- a probability
- one reward item ID
- reward min/max count
- success/failure messages

However, without runtime evidence, these details remain unproven:
- whether `道具編號` is a consumable scroll/tool or recipe ID
- whether the target is selected through item-target packet data
- whether target item is always consumed
- whether target enchant/bless/state restrictions exist
- whether failure consumes target/tool/both
- whether quantity >1 is supported
- whether output min/max is inclusive
- whether the displayed `物品名稱` field has runtime meaning
- whether messages use standard system text or custom UI

## 850-first implementation policy

Because the donor runtime is not authoritative, do NOT recreate an unknown 381 flow.

If this feature is retained, define it using 850-native interaction patterns.

Preferred target model:

```text
item-disassembly rule
  trigger item / action
  target item semantic identity
  success chance
  reward item semantic identity
  reward min/max
  success/failure message
  explicit consume policy
```

Preferred runtime:
1. use an existing 850 item-target action pattern if available
2. validate target item
3. validate consume policy
4. roll using 850 RNG utility
5. consume atomically
6. award through 850 inventory APIs
7. use existing 850 system-message packets

No 381-specific HTML or packet behavior should be introduced unless later source evidence proves a required client behavior that 850 cannot represent.

## 850 native fit

No dedicated item-disassembly framework was found by targeted path inspection.

850 does have generic primitives that are likely reusable:
- item executor/action dispatch
- inventory remove/consume
- inventory store/reward
- RNG
- system message packets
- craft/exchange-style data models

But exact equivalence is not proven.

```text
NATIVE_850_DISASSEMBLY=NOT_PROVEN
REUSABLE_850_PRIMITIVES=YES
```

## Classification

The feature itself is a small server-side transactional item transform.

If the missing semantics are later recovered, it should likely be implemented as:
- 850-native small item action
- DB-backed rule
- no donor framework port

Therefore:

```text
TARGET_COMPLEXITY=L2_CANDIDATE
AUDIT_LEVEL=L2
```

This is not L3 merely because donor runtime is missing; missing source is a blocker, not complexity evidence.

It is not L4 because no custom client protocol/UI/resource dependency is proven.

## Data-quality note

The visible row labels:
- target item `41` as `武士刀`
- reward item `40308` as `細劍`

Those textual labels must NOT be treated as authoritative item identity.

Migration must resolve both IDs semantically against 381/850 item tables.

Do not trust the note/name fields over actual item definitions.

## Required decisions before implementation

Because donor runtime is absent, the migration contract must explicitly define:

```text
CONSUME_TRIGGER_ON_SUCCESS = ?
CONSUME_TRIGGER_ON_FAILURE = ?
CONSUME_TARGET_ON_SUCCESS = ?
CONSUME_TARGET_ON_FAILURE = ?
TARGET_ENCHANT_POLICY = ?
TARGET_BLESS_POLICY = ?
TARGET_EQUIPPED_POLICY = ?
REWARD_RANGE_INCLUSIVE = ?
QUANTITY_MODE = ?
```

Until these are sourced from another authoritative artifact or intentionally specified for the 850 design, implementation must remain blocked.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_物品分解系統
LEVEL=L2
TARGET_POLICY=850_NATIVE_FIRST
CORE_DEP=SMALL_ITEM_ACTION_EXPECTED
DB_DEP=YES
SOURCE_ROWS=1
DONOR_RUNTIME=NOT_PROVEN
DONOR_UI=NOT_PROVEN
DONOR_TRIGGER=NOT_PROVEN
381_ITEMRECYCLE_IS_SAME_MODULE=NO
CLIENT_PROTOCOL_DEP=NOT_PROVEN
CLIENT_RESOURCE_DEP=NOT_PROVEN
850_NATIVE_EQUIVALENT=NOT_PROVEN
850_REUSABLE_PRIMITIVES=YES
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=consume semantics,target restrictions,item semantic mapping,authoritative trigger behavior
```
