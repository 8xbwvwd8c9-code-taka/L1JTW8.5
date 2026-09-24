# 381 -> 850 Limited Real-Time Reward Audit

## Scope
Module: `w_即時獎勵系統`

381 runtime:
- `com.lineage.william.LimitedReward`
- `com.lineage.william.L1WilliamLimitedReward`
- `com.lineage.data.event.lvgiveitemcount`
- level hook in `L1PcInstance.levelUp(...)`

This is distinct from `w_升級獎勵` / `w_升級獎勵_任務`: this module adds a shared server-wide limited quota.

## Current DB content
Current split SQL contains 16 rows with IDs `0..15`.
Rules support activity toggle, class bitmask, minimum level, optional trigger item, reward item/count/enchant, global total/current winner count, per-character quest guard, and broadcasts.

Current active rows:
```text
id 10..15 = activity 1
id 0..9  = activity 0
```

Current reward item is predominantly `44070`; semantic mapping is required before 850 migration.

## Source-content inconsistencies
Do not silently normalize donor data.

Examples:
- id 0 note says "第1名3000元寶" but `total_People=10` and reward count=`1000`
- id 4 note says "5名100元寶" but reward count=`1000`

## Loader
`LimitedReward` loads:
```sql
SELECT * FROM w_即時獎勵系統
```

It indexes by rule `id` and `check_item`.

Runtime reads:
`id, activity, check_classId, check_level, check_item, check_itemCount, surplus_msg, surplus_msg_color, give_item_id, give_itemCount, give_itemEnchantlvl, total_People, appear_People, quest_id, quest_step, message, message_end`.

Current split SQL is INSERT-only; CREATE schema is not proven.

## Trigger paths

### Level trigger
381 `L1PcInstance.levelUp(...)` calls:
```java
if (lvgiveitemcount.START) {
    L1WilliamLimitedReward.check_Task_For_Level(this);
}
```

Eligibility requires active rule, remaining global quota, unfinished per-character quest, minimum level and class mask.

### Item trigger
`L1WilliamLimitedReward.check_Task_For_Item(...)` supports item-triggered rules through `check_item`.

Current visible rows all use `check_item=0`, so current content is level-driven, although the framework contains an item-triggered path.

## Class mask
```text
1 crown
2 knight
4 elf
8 wizard
16 dark elf
32 dragon knight
64 illusionist
128 warrior
255 all
```

## Grant and persistence
On success runtime:
1. creates reward item(s)
2. writes per-character quest step
3. increments in-memory `appear_People`
4. persists `UPDATE w_即時獎勵系統 SET appear_People=? WHERE id=?`
5. broadcasts remaining count
6. when exhausted may persist `activity=0`

Persistence authorities:
```text
per-character: quest step
global: w_即時獎勵系統.appear_People + activity
```

## Critical donor hazards

### 1. Global quota allocation is not atomic
No transaction/synchronization is proven around:
```text
total_People > appear_People
-> grant
-> appear_People++
-> UPDATE DB
```
Concurrent players can race for the final slot and over-issue rewards.

### 2. Reward, character completion and global quota are not one transaction
A crash between item grant, quest write and global count UPDATE can produce duplicate/replayed or miscounted rewards.

### 3. Dense numeric ID assumption
`check_Task_For_Level` loops `0..size-1` and calls `getTemplate(i)`, assuming rule IDs are dense from zero. Current data happens to satisfy this; arbitrary IDs are unsafe.

### 4. check_item index can collide
`_itemIdIndex1` is keyed only by `check_item`. Multiple non-zero rules sharing a trigger item overwrite each other.

### 5. Mutable class-mask decoder
`set_use_type(...)` sets reusable boolean class flags; immutable mask checks are safer for the target.

### 6. CSV arrays are not validated
Reward item/count/enchant arrays require equal-length startup validation.

### 7. DB update failure has no reward rollback
Global state UPDATE failure can occur after irreversible delivery.

## Client dependency
Only normal item delivery, quest state and server messages are proven.
```text
CLIENT_DEP=NO
```

## 850 comparison
850 has level-up, item-grant and quest primitives, but targeted inspection did not prove an equivalent persistent global-quota reward framework.

```text
NATIVE_850_EQUIVALENT=NOT_PROVEN
```

## Recommended migration shape
```text
limited-reward-core/
  immutable rule model
  trigger adapters
  class-mask check
  atomic quota claim
  idempotent reward delivery
  per-character completion
  global state persistence

limited-reward-rules/
  install.sql
  rollback.sql
  validation.sql
```

Target must atomically claim quota before irreversible delivery and provide idempotent/recoverable completion.

## Difficulty
`LEVEL=L3`

Reason: lifecycle hook + DB loader + character quest persistence + mutable global persistence + concurrency-safe quota allocation; no custom client dependency.

## Status
```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_即時獎勵系統
LEVEL=L3
CORE_DEP=YES
DB_DEP=YES
LEVEL_HOOK=L1PcInstance.levelUp
ITEM_TRIGGER_FRAMEWORK=YES
CURRENT_CONTENT_TRIGGER=LEVEL
CHAR_PERSISTENCE=quest step
GLOBAL_PERSISTENCE=appear_People+activity
CLIENT_DEP=NO
NATIVE_850_EQUIVALENT=NOT_PROVEN
SOURCE_SCHEMA=NOT_PROVEN
ACTIVE_ROWS=6/16
CONCURRENCY_SAFE=NO
BLOCKERS=schema,item mapping,quest ownership,atomic quota/idempotency design,data inconsistencies
```
