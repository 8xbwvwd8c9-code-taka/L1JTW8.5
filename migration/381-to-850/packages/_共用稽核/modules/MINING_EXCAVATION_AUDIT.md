# 381 -> 850 Mining / Excavation Audit

## Scope

381 module:
- `w_挖掘系統`

Runtime:
- `com.lineage.data.item_etcitem.add.Excavate`
- `com.lineage.william.ExcavateTable`

## Current DB content

Current split SQL contains 2 rules.

```text
map 4
  min level 20
  required item 92079
  reward tier 0 chance 100
  reward item 40010 x10

map 310
  min level 10
  required item 44070
  reward tier 0 chance 100
  reward item 40010 x10
```

Other reward tiers are disabled with zero chances/items.

Current split SQL is INSERT-only, so CREATE schema/key/default authority is not proven.

## Entry item

The mining action is exposed through an item executor:

```text
classname = add.Excavate
```

Observed donor etcitem row:

```text
name = 十字稿(挖礦)
classname = add.Excavate
use_type = power
delay_id = 3
delay_time = 5000
```

Using the item calls:

```java
ExcavateTable.forExcavate(pc);
```

Therefore migration requires an item-use hook or equivalent action entry.

## Runtime state machine

Constants:

```text
MINING_SKILL_ID      = 10010
MINING_POLY_ID       = 3642
MINING_ACTION_ID     = 7
MINING_POLY_TIME_SEC = 30
MINING_CAST_TIME_MS  = 10000
MINING_LOOP_DELAY_MS = 3000
```

Start sequence:

1. validate online/player state
2. toggle-stop an existing mining task if present
3. reject invalid state
4. resolve rule by current map
5. verify minimum level
6. verify required item exists
7. capture start X/Y/map
8. mark active miner
9. polymorph to gfx/poly `3642`
10. send action `7`
11. schedule completion after 10 seconds

Completion revalidates:

```text
online
network connection
not teleporting
not dead
not ghost
not private shop
not fishing
same map
same X
same Y
minimum level
required item still present
```

Only then is a reward rolled.

After reward, if still valid, donor sets skill effect `10010` for 3000 ms.

## Stop / toggle behavior

The module stores:
- `ConcurrentHashMap<Integer,ScheduledFuture<?>> _miningTasks`
- `Set<Integer> _stopRequested`
- `Set<Integer> _activeMiners`

Using the mining item while an active task exists acts as a stop/toggle request.

Stopping can:
- cancel a pending scheduled task
- remove the loop effect
- undo polymorph
- restore visual state

This state is process-memory only.

## Reward model

The table supports five tiers:

```text
Random  -> Give_Item / Give_Item_Count
Random1 -> Give_Item1 / Give_Item_Count1
Random2 -> Give_Item2 / Give_Item_Count2
Random3 -> Give_Item3 / Give_Item_Count3
Random4 -> Give_Item4 / Give_Item_Count4
```

The donor performs an `if / else-if` chain.

Important: each tier performs a NEW independent random roll.

Conceptually:

```text
roll1 <= Random
else roll2 <= Random1
else roll3 <= Random2
...
```

This is not a single cumulative weighted distribution.

Current content only enables the first tier at 100, so the distinction is dormant for current data but critical for future migration.

## Item/count selection semantics

Within a successful tier:
- reward item is randomly selected from the tier's item array
- reward count is independently selected from the tier's count array

These are not position-aligned tuples.

For non-stackable items donor forces instance count to 1, but the message still uses the rolled count value.

Target implementation must choose and document whether to preserve donor-independent selection or move to explicit reward tuples.

## Client dependency

Proven donor presentation uses:
- polymorph ID `3642`
- action ID `7`
- standard system messages
- normal item packets

No custom packet protocol is proven.

However visual compatibility requires target client resources to support the selected polymorph/action combination.

```text
CLIENT_PROTOCOL_DEP=NO_PROVEN_CUSTOM_PROTOCOL
CLIENT_GFX_DEP=YES
POLY_ID=3642
ACTION_ID=7
```

This remains L3 unless client resource incompatibility is later proven to require custom client work.

## 850 comparison

Targeted 850 branch inspection found no mining/excavation framework or `Excavate` equivalent.

850 has generic:
- item-use execution
- player state
- scheduled task/thread pool primitives
- polymorph/action packets
- inventory/item creation

but no proven equivalent configurable mining state machine.

```text
NATIVE_850_EQUIVALENT=NOT_PROVEN
```

## Donor hazards

### 1. Magic skill-effect ownership

`10010` is used as mining loop/cooldown state.

Target must validate effect-ID ownership or replace it with module-owned state.

### 2. Polymorph ownership

The donor forcibly applies mining polymorph `3642` and calls `undoPoly(pc)` when stopping.

That can conflict with an unrelated player polymorph if the state machine does not preserve prior morph ownership.

Target should restore only morph state it owns, or snapshot/reconcile prior state.

### 3. Scheduled task lifecycle

Task state is process-memory only.

Disconnect/restart cleanup depends on surrounding lifecycle behavior and is not proven by this module alone.

Target needs explicit cancellation on:
- logout
- disconnect
- teleport
- death
- map change

or completion-time checks plus bounded stale-task cleanup.

### 4. Required tool is checked, not consumed

The required item acts as a possession/tool gate.

It is NOT consumed per mining cycle.

Do not convert `Check_Item` into a payment cost.

### 5. Reward probabilities are independent sequential rolls

Multiple non-zero Random fields do not sum to a normal weighted distribution.

Target rules should either:
- preserve exact sequential-roll semantics, or
- migrate to explicit normalized weights after data-owner approval.

### 6. DB data does not prove item semantic identity

IDs `92079`, `44070`, and `40010` must be mapped semantically to 850.

Numeric reuse alone is unsafe.

### 7. Empty rule handling can null the static array

Constructor behavior sets `_array=null` when no rules load.

Most runtime paths guard this, but target should use an immutable empty collection instead.

## Recommended migration shape

```text
mining-core/
  rule loader
  item-use entry adapter
  miner session state
  lifecycle cancellation
  stationary cast validation
  reward resolver
  visual adapter

mining-rules/
  install.sql
  rollback.sql
  validation.sql
```

Recommended target flow:

```text
use tool
-> validate map/level/tool/state
-> create owned mining session
-> send mining visual
-> schedule completion
-> revalidate position/state/tool
-> resolve reward
-> close or re-arm session
```

Do not use a generic skill-effect ID as the sole authoritative session lock.

## Difficulty

`LEVEL=L3`

Reason:
- item-use integration
- scheduled state machine
- map/position validation
- lifecycle cancellation
- polymorph/action presentation
- DB-driven reward logic
- no proven custom client protocol

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_挖掘系統
LEVEL=L3
CORE_DEP=YES
DB_DEP=YES
ITEM_USE_DEP=YES
ENTRY_CLASS=add.Excavate
ACTIVE_RULES=2
SESSION_STATE=IN_MEMORY
CAST_TIME_MS=10000
LOOP_DELAY_MS=3000
MINING_EFFECT_ID=10010
POLY_ID=3642
ACTION_ID=7
REQUIRED_ITEM_CONSUMED=NO
CLIENT_GFX_DEP=YES
CLIENT_PROTOCOL_DEP=NOT_PROVEN
NATIVE_850_EQUIVALENT=NOT_PROVEN
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=schema,item semantic mapping,effect ownership,poly/action compatibility,lifecycle cleanup,reward probability policy
```
