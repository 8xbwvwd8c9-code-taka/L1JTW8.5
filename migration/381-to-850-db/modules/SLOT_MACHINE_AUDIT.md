# 381 -> 850 Slot Machine Audit

## Scope
Module: `w_拉霸系統`

381 runtime:
- `com.lineage.server.datatables.LaBarGameTable`
- integration: `C_NPCAction`

Current rule:
```text
note=拉霸
npcid=93056
action=labar
GiveItem=40033,40034,40035,40036,40037,40038
GiveItemCount=1,1,1,1,1,3
GiveItemEnchantlvl=0,0,0,0,0,0
checkMoney=40308
checkMoneyCount=500
random=90
```

## DB authority
Current split SQL is INSERT-only. CREATE schema, PK/default/index authority is not proven.

## Entry / action hook
`C_NPCAction` calls:
```java
LaBarGameTable.forLaBarGame(cmd, pc, npc, npcId, objId)
```
before normal final NPC action handling.

The rule matches:
- NPC template ID
- action string

The current action is `labar`.

The exact HTML/menu resource that emits `labar` was not located in the current repository tree.

Therefore:
```text
NPC_ACTION_DEP=YES
HTML_RESOURCE=NOT_PROVEN
CLIENT_PROTOCOL_DEP=NOT_PROVEN
```

Do not assign L4 solely from an unproven external HTML/resource dependency.

## Runtime flow
For a matching rule:
1. reject while skill effect `123456` is active
2. check configured payment item/count
3. consume payment
4. emit each configured reward item name as a blue-message sequence, sleeping 500 ms between names
5. roll success/failure
6. on success independently choose:
   - reward item index
   - reward count index
   - reward enchant index
7. create reward
8. add to inventory, else ground fallback
9. send result messages
10. set skill effect `123456` for 10 seconds

No module-specific persistence is used.

## Critical donor defects / semantic hazards

### 1. Handler always returns false
`LaBarGameTable.forLaBarGame(...)` ends with:
```java
return false;
```
including after a matching rule has been processed.

But `C_NPCAction` only stops when this returns true.

Therefore the slot-machine action can fall through into later `npc.onFinalAction(pc, cmd)` / normal NPC handling after already consuming currency and resolving the game.

Target must return/mark HANDLED after a matched action.

### 2. Configured probability is off by one
The donor condition is:
```java
RandomArrayList.getInc(100, 1) >= 100 - random
```

`getInc(100,1)` produces the underlying 0..99-style integer plus 1.

For `random=90`, the passing values are `10..100`: 91 discrete outcomes out of 100, assuming the underlying list is uniformly distributed.

So configured 90 does not represent an exact 90% threshold.

Target should define probability explicitly, e.g. `roll(1..100) <= chance`.

### 3. Item/count/enchant are selected independently
The donor does NOT select one aligned tuple.

It separately rolls:
```text
rndItem
rndCount
rndEnchantlvl
```

Thus CSV position does not bind an item's count or enchant.

Current data has one count value of 3 among otherwise 1s, so any selected item can independently receive that count roll.

Migration must decide whether this independent behavior is intentional. Do not silently convert to tuple-aligned rewards.

### 4. Non-stackable count/message mismatch
For non-stackable items donor forces:
```text
item.count = 1
```
but the success message still prints the independently rolled `giveCountGet`.

A count roll of 3 can therefore be displayed even when only one non-stackable item is created.

### 5. Payment is consumed before success roll
Failure still costs the configured payment. That is proven donor behavior and should be explicit in target rules.

### 6. Blocking sleep occurs in packet/action processing
The visual sequence calls `Thread.sleep(500)` once per configured reward candidate.

Current 6-item rule blocks about 3 seconds in this action path before resolution.

Target should not reproduce blocking sleeps in the packet thread; use scheduled/non-blocking presentation if visual pacing is retained.

### 7. Cooldown uses a magic skill-effect ID
`123456` is used as a 10-second anti-repeat flag.

Target should allocate explicit module state/effect ownership rather than reuse an unregistered magic number without collision validation.

## 850 comparison
Targeted 850 tree inspection found no `LaBarGameTable`/slot-machine equivalent.

850 has generic NPC/action and item primitives, but an equivalent configurable:
- payment
- animated candidate display
- chance result
- random reward
- cooldown
framework is not proven.

```text
NATIVE_850_EQUIVALENT=NOT_PROVEN
```

## Client/UI assessment
The proven implementation uses ordinary server packets/messages and a normal NPC action string.

A menu/action producer is required for `labar`, but its HTML/resource was not found.

Therefore:
```text
CLIENT_PROTOCOL_DEP=NO_PROVEN_CUSTOM_PROTOCOL
HTML_MENU_DEP=POSSIBLE_NOT_PROVEN
```

Current evidence supports L3, not L4.

## Recommended migration shape
```text
slot-reward-core/
  rule loader
  NPC action adapter
  payment transaction
  explicit probability
  reward selection policy
  non-blocking presentation
  cooldown ownership

slot-reward-rules/
  install.sql
  rollback.sql
  validation.sql
```

Before formal migration, explicitly choose:
- independent item/count/enchant rolls (donor-exact), or
- aligned reward tuples (likely safer semantics)

This must be a product/data decision, not an implicit code cleanup.

## Difficulty
`LEVEL=L3`

Reason:
- custom DB rule runtime
- NPC action hook
- payment/reward transaction
- cooldown
- non-trivial RNG behavior
- no proven custom client dependency

## Status
```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_拉霸系統
LEVEL=L3
CORE_DEP=YES
DB_DEP=YES
NPC_ACTION_DEP=YES
ACTION=labar
ACTIVE_RULES=1
PERSISTENCE=NONE
CLIENT_PROTOCOL_DEP=NOT_PROVEN
HTML_RESOURCE=NOT_PROVEN
NATIVE_850_EQUIVALENT=NOT_PROVEN
SOURCE_SCHEMA=NOT_PROVEN
DONOR_HANDLER_FALLTHROUGH_BUG=YES
DONOR_PROBABILITY_OFF_BY_ONE=YES
BLOCKERS=schema,item semantic mapping,NPC/menu producer,RNG/reward tuple policy,cooldown ownership
```
