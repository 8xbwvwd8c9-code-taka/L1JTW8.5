# 381 -> 850 Item Time Limit Audit

## Scope
Table:
- w_物品時間限制

Visible rows: 35

## Current duration set
Distinct normalized durations: 8

```text
1h
3h
10h
12h
23h50m
72h / 3d
168h / 7d
720h / 30d
```

Statistics:
```text
ROWS=35
MIN=1h
MAX=720h=30d
MEAN=30446/105 h ~= 289.9619047619 h
MEDIAN=72h
MODE=720h / 30d
MODE_ROWS=13
```

## Normalization proof

Use:
```text
TOTAL_MINUTES=d*1440+h*60+m
TOTAL_MILLISECONDS=TOTAL_MINUTES*60*1000
```

Proven equivalences:
```text
3d = 72h = 4320m = 259,200,000ms
7d = 168h = 10080m = 604,800,000ms
30d = 720h = 43200m = 2,592,000,000ms
```

All visible labels containing explicit duration claims match their configured normalized duration.

## Critical int millisecond overflow

Current maximum:
```text
30d=720h=2,592,000,000ms
```

This exceeds:
```text
Integer.MAX_VALUE=2,147,483,647
```

Therefore:
```text
CURRENT_INT_MILLIS_OVERFLOW=YES
FIRST_CURRENT_OVERFLOW_DURATION=720h
MAX_SAFE_WHOLE_HOURS_INT_MS=596
```

Any target implementation must widen before multiplication.

Safe:
```java
long millis =
    ((long)d * 24L * 60L * 60L * 1000L)
  + ((long)h * 60L * 60L * 1000L)
  + ((long)m * 60L * 1000L);
```

For epoch addition:
```text
nowMillis <= Long.MAX_VALUE - durationMillis
```
must hold before adding.

## Canonicalization

Recommended canonical arithmetic form:
```text
d>=0
0<=h<24
0<=m<60
```

Examples:
```text
72h  -> 3d 0h 0m
168h -> 7d 0h 0m
720h -> 30d 0h 0m
23h50m -> 0d 23h 50m
```

Current source intentionally uses non-canonical large-hour encodings, so 850 must choose:
- normalize, or
- preserve source form while evaluating by total duration.

Do not reject h>=24 without an explicit migration policy because current valid content relies on it.

## Persistence invariant

Correct item-instance expiry model:
```text
expiry E is absolute and remains attached to item instance
remaining = E-now
```

Example:
```text
duration=72h
logout after 10h
offline 20h
correct remaining=42h
```

If login reconstructs:
```text
E=loginTime+72h
```
then relog incorrectly restores 72h.

Therefore:
```text
RELOGIN_TIMER_RESET_MUST_NOT_OCCUR
```

## Transfer invariant

For 30d item transferred after 5d:
```text
correct remaining=25d
incorrect receiver reset=30d
```

If expiry ownership is item-instance based, ownership transfer must not recreate duration.

## Stack split/merge policy

For a stack with expiry E:
- split can safely copy E to both child stacks.

For two same-item stacks with:
```text
E1 != E2
```
a single scalar expiry cannot preserve both timers after merge.

Therefore:
```text
STACK_MERGE_POLICY_REQUIRED=YES
```

Target must explicitly decide whether timed items:
- cannot merge across different expiries,
- preserve earliest expiry,
- preserve latest expiry,
- or track per-unit expiry.

This is semantic policy, not arithmetic.

## Known donor data owners

Definition loader:
- `ItemTimeTable`
- loads `w_物品時間限制`
- stores `Map<itemid,int[]{d,h,m}>`

Instance persistence:
- `character_items_time`
- `CharItemsTimeTable`
- binds absolute `Timestamp usertime` to `itemr_obj_id`

This strongly indicates per-item-instance absolute expiry persistence exists, but the exact constructor/action path that converts d/h/m into `usertime` remains to be closed.

## Duplicate itemid semantics

Definition loader uses:
```text
TIME.put(itemid,value)
```

Therefore:
```text
N duplicate definitions -> 1 retained
N-1 lost
last loaded row wins
```

No current duplicate itemid is asserted here unless separately proven.

## Test vectors

```text
(0,1,0)   => 3,600,000ms
(0,3,0)   => 10,800,000ms
(0,10,0)  => 36,000,000ms
(0,12,0)  => 43,200,000ms
(0,23,50) => 85,800,000ms
(3,0,0)   => 259,200,000ms
(0,72,0)  => 259,200,000ms
(0,168,0) => 604,800,000ms
(0,720,0) => 2,592,000,000ms
```

## Classification

```text
DATA_LEVEL=L1
INSTANCE_LIFECYCLE_LEVEL=L3
CLIENT_DEP=NO_CURRENT_PROOF
FINAL_LEVEL=L3
```

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_物品時間限制
ROWS=35
DISTINCT_DURATIONS=8
MIN_DURATION=1h
MAX_DURATION=30d
INT_MILLIS_OVERFLOW=YES_AT_30D
SAFE_LONG_FORMULA=PROVEN
THREE_DAY_EQUIVALENCE=YES
THIRTY_DAY_EQUIVALENCE=YES
LABEL_MISMATCHES=NONE
INSTANCE_EXPIRY_PERSISTENCE=character_items_time
RELOGIN_INVARIANT=ABSOLUTE_EXPIRY_REQUIRED
TRANSFER_INVARIANT=NO_RESET
STACK_MERGE_POLICY_REQUIRED=YES
DUPLICATE_ITEMID_LAST_WRITE_WINS=YES
850_NATIVE=PARTIAL
CLIENT_DEP=NO_CURRENT_PROOF
LEVEL=L3
BLOCKERS=exact d/h/m -> usertime creation path; expiry deletion/disable action; offline countdown policy verification; transfer/split/merge hooks; 850 item-instance timed-item mapping
```


## Runtime lifecycle closure

Definition application is now proven in `L1Inventory.set_time_item(item)`.

For items entering a player inventory through the normal `storeItem(...)` path:

```text
if item.get_time()==null
  lookup ItemTimeTable.TIME[itemId]
  Calendar now
  add DAY_OF_MONTH by d
  add HOUR_OF_DAY by h
  add MINUTE by m
  Timestamp ts=calendar millis
  item.set_time(ts)
  CharItemsTimeReading.addTime(itemObjId,ts)
```

Therefore:
```text
DURATION_TO_EXPIRY_PATH=PROVEN
EXPIRY_MODEL=ABSOLUTE_TIMESTAMP
PERSISTENCE_KEY=item_obj_id
DONOR_INT_MILLIS_OVERFLOW=NO_CURRENT_PATH
```

The donor uses `Calendar.add` rather than a single int-millisecond multiplication, so the 30-day int overflow is a target rewrite hazard, not a proven donor failure.

### Trade behavior

`storeTradeItem(...)` does not call `set_time_item`.

For a non-merged item instance this is desirable because the same `L1ItemInstance` already carries its absolute `_time`, so transfer does not recreate the duration.

However, stackable trade/store merge paths can merge incoming count into an existing stack and return the existing item without reconciling two expiry timestamps.

Therefore:
```text
NONMERGED_TRANSFER_TIMER_RESET=NO_PROVEN
STACK_MERGE_TIMER_RECONCILIATION=NOT_PROVEN
STACK_TIMER_LOSS_RISK=YES_IF_TIMED_STACKABLE_ITEMS_EXIST
```

## Expiry consumer

`ServerItemUserTimer`:
- starts every 60,000 ms
- scans `WorldItem.get().all()`
- ignores items with null time
- expires normal timed items when `item.get_time().before(now)`

Exact gate:
```text
expiry < now  => expired
expiry == now => not yet expired
expiry > now  => active
```

Thus:
```text
EXPIRY_GATE=STRICT_BEFORE
SCAN_PERIOD_MS=60000
DELETION_JITTER=UP_TO_APPROX_ONE_SCAN_PERIOD_PLUS_SCAN_COST
```

The loop also sleeps 5 ms per timed item, so total scan duration grows with timed-item population.

For expired equipped items:
- unequip first
- then remove from inventory

For expired pet-related item:
- pet drop/delete cleanup follows.

## character_items_time cleanup asymmetry

In the visible expiry path, explicit:
```text
CharItemsTimeTable.delete(item.getId())
```
is performed inside the VIP-item branch.

For ordinary timed-item expiry, no direct `character_items_time` delete is proven before inventory removal.

Separately, `CharItemsTimeTable.load()` cleans a time row when its referenced item object cannot be found.

Therefore:
```text
GENERAL_EXPIRY_TIME_ROW_IMMEDIATE_DELETE=NOT_PROVEN
STALE_TIME_ROW_AFTER_EXPIRY=PROVEN_RISK
EVENTUAL_CLEANUP_ON_TIME_TABLE_LOAD=PROVEN
```

Target 850 should delete item + expiry metadata atomically or in one ownership transaction.

## Updated lifecycle status

```text
DEFINITION_LOAD=PROVEN
DURATION_TO_EXPIRY=PROVEN
ABSOLUTE_TIMESTAMP_PERSISTENCE=PROVEN
RELOGIN_RESET_RISK=DONOR_MITIGATED_BY_PERSISTED_TIMESTAMP
NONMERGED_TRANSFER_PRESERVES_INSTANCE_TIME=PROVEN_BY_OBJECT_FLOW
STACK_MERGE_EXPIRY_POLICY=NOT_PROVEN
EXPIRY_CONSUMER=PROVEN
EXPIRY_SCAN_PERIOD=60S
STRICT_BEFORE_GATE=PROVEN
GENERAL_TIME_ROW_CLEANUP=INCOMPLETE/DEFERRED
```
