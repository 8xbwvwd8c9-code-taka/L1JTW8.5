# 381 -> 850 Level-Up Reward Audit

## Scope

381 tables:
- `w_升級獎勵`
- `w_升級獎勵_任務`

Runtime:
- `com.lineage.william.Reward`
- `com.lineage.william.Reward1`
- `L1PcInstance.levelUp(...)`

Related but separate limited-reward framework:
- `lvgiveitemcount`
- `L1WilliamLimitedReward`

Do not merge the limited-reward framework into these two tables.

## Current content

### w_升級獎勵

Current split content contains 9 rows.

Observed rows:
- configured at level 99
- all standard donor classes enabled
- reward item currently `40308`
- quest IDs `200001..200009`
- quest step `1`

Important semantic warning:
- donor item ID `40308` must NOT be assumed valid in 850 by numeric ID alone.
- semantic item mapping is required.

One row note says `Lv.90獎勵禮盒` but the configured `level` and message still say 99.
Treat this as source-content inconsistency, not something to silently correct.

### w_升級獎勵_任務

Current split content contains class quest auto-completion rewards for levels:
- 15
- 30
- 45
- 50

It grants legacy class quest reward items and writes quest completion steps, usually:
```text
quest_step=255
```

Quest IDs include the donor's existing class quest IDs such as:
```text
101,102,103
106,107,108
112,113,114
118,120
122
124,125,126,127
129,130,131,132
133,134,135,136
```

These IDs are ownership-critical and must be checked against 850 quest semantics.

## DB schema authority

Current split SQL artifacts are INSERT-only.

The donor runtime reads:
- level
- give_royal
- give_knight
- give_mage
- give_elf
- give_darkelf
- give_dragonKnight
- give_illusionist
- getItem
- count
- enchantlvl
- quest_id
- quest_step
- message

The split SQL additionally contains:
- 註解
- Metempsychosis

Current `Reward` / `Reward1` code does not prove use of `Metempsychosis`.

Do not infer the CREATE schema, keys, defaults, or intended reincarnation semantics.

## Level-up hook

381 `L1PcInstance.levelUp(...)` calls:

```java
Reward.getItem(this);
Reward1.getItem(this);
```

after the player's level/stat update and save path.

The same lifecycle also optionally invokes the separate limited-reward framework:

```java
if (lvgiveitemcount.START) {
    L1WilliamLimitedReward.check_Task_For_Level(this);
}
```

Therefore the two `w_升級獎勵*` tables are not the same subsystem as limited global-count rewards.

## Reward evaluation

Both `Reward` and `Reward1` use the same model.

A rule is eligible when:

```text
pc.level >= configured level
AND reward arrays exist
AND current quest step != configured quest step
AND current class is enabled
```

On success:
1. parse item IDs/counts/enchant levels
2. create each item
3. set count
4. apply enchant only to weapon/armor
5. add to inventory when possible
6. otherwise drop/store item on the ground at player location
7. send reward message
8. set quest step to the configured completion step

## Persistence / duplicate protection

Duplicate protection is quest-backed:

```text
pc.getQuest().get_step(quest_id) != quest_step
```

After grant:

```text
pc.getQuest().set_step(quest_id, quest_step)
```

Therefore migration requires:
- quest persistence
- quest ID ownership validation
- atomic or recoverable reward + completion semantics

## Important donor hazards

### 1. Multi-item grant is not atomic

The donor sets the quest completion step inside the per-item grant loop.

If a multi-item reward partially fails, the completion state may already be written after an earlier item.

Target migration should grant the rule transactionally or at least set completion only after the full reward set succeeds.

### 2. Item arrays must be aligned

These CSV fields are parallel arrays:

```text
getItem
count
enchantlvl
```

The donor assumes compatible lengths.

Target startup validation must reject mismatched arrays.

### 3. Cache is process-local and one-shot

Both `Reward` and `Reward1` lazy-load table data once via static `GET_ITEM`.

DB edits are not proven to hot-reload.

### 4. Loader exceptions are swallowed

The donor loader catches generic exceptions without a strong failure report.

Target should log/fail clearly.

### 5. Ground fallback changes delivery semantics

If inventory cannot accept the item, donor behavior places it at the player's current map position.

Target migration must explicitly decide whether to preserve:
- ground fallback
- mailbox/warehouse fallback
- reject/defer reward

Do not change this silently.

### 6. Threshold uses >=

A player who skips levels can still qualify later because the rule condition is:

```text
pc.level >= rule.level
```

This is useful catch-up behavior and should be preserved unless deliberately redesigned.

### 7. Source content has inconsistent annotations

Example:
- row note says Lv.90
- configured level is 99
- message says Lv.99

Data authority must be decided explicitly before migration.

## Client dependency

Proven behavior uses only normal:
- item grants
- system/server messages
- existing quest persistence

No dedicated NPC HTML, custom UI, or custom client protocol is required.

```text
CLIENT_DEP=NO
```

## 850 comparison

850 recovered `L1PcInstance` has a clear level-up lifecycle and invokes its existing quest progression path:

```text
level change
-> stat recalculation
-> status update
-> QuestNewTable...
```

850 also has generic item grant and quest infrastructure.

However targeted inspection did NOT prove a native DB-driven equivalent that combines:
- per-level rules
- class masks
- item/count/enchant arrays
- per-character one-time quest-step ownership

The existing `L1LevelPresent` is an administrative/account delivery command and is not equivalent to automatic per-character level-up reward rules.

Therefore:

```text
NATIVE_850_EQUIVALENT=NOT_PROVEN
```

## Migration strategy

Do not port both donor classes verbatim.

Use one shared rule engine:

```text
level-up-reward-core/
  rule loader
  class eligibility
  level threshold
  quest duplicate guard
  item grant transaction
  completion write

level-up-reward-rules/
  normal rewards
  legacy quest-completion rewards
```

The two source tables can remain logically separate while sharing one runtime.

Recommended target behavior:
- validate quest ownership
- semantic-map item IDs
- validate array lengths
- set completion only after complete grant
- explicit inventory-full policy
- strong loader logging
- optional reload support

## Difficulty

`LEVEL=L3`

Reason:
- requires level-up lifecycle integration
- DB rule loader
- quest persistence integration
- item grant transaction
- no dedicated client dependency

The content itself is simple, but 850 does not currently prove an equivalent configurable reward framework, so this is not safe to classify as L2 data-only conversion.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_升級獎勵+w_升級獎勵_任務
LEVEL=L3
CORE_DEP=YES
DB_DEP=YES
LEVEL_HOOK=L1PcInstance.levelUp
RUNTIME=Reward+Reward1
QUEST_DEP=YES
PERSISTENCE=character quest step
CLIENT_DEP=NO
NATIVE_850_LEVEL_LIFECYCLE=YES
NATIVE_850_RULE_FRAMEWORK=NOT_PROVEN
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=schema,quest ownership,item semantic mapping,delivery/atomicity policy
```
