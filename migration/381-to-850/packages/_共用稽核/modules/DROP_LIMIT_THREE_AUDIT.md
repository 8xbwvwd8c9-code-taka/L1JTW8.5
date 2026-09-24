# 381 -> 850 Drop Limit Three Audit

## Scope
Table:
- w_物品掉落限制三

Current visible row:
```text
item_id=9
totalCount=100000000
appearCount=11
stored_time=2026-09-13 03:02:23
min_minutes=720
max_minutes=900
```

## Donor runtime
- loader/runtime: `Drop_limit`
- reservation: `reserveDrop(itemId,count)`
- eligibility: `canDropNow(count)`
- persistence: `upDateNextSpawnTime(...)`

## Current quota math
```text
CURRENT_REMAINING=100000000-11=99999989
```

Reservation boundary:
```text
1 <= count <= Integer.MAX_VALUE
count <= totalCount-appearCount
```

Thus:
```text
CAP_BOUNDARY=remaining allowed; remaining+1 rejected
BATCH_BOUNDARY=1..2147483647 accepted by count-domain guard
```

## Timer support and off-by-one
Donor:
```java
time=min;
if (max>min) {
    time += RandomArrayList.getInt(max-min);
}
```

`getInt(rang)` returns integer values `0..rang-1`.

Current:
```text
min=720
max=900
support=720..899
support_size=180
expected=809.5 minutes
```

Therefore:
```text
MAX_VALUE_REACHABLE=NO
INTENDED_LOOKING_RANGE=720..900
DONOR_RANGE=720..899
EXPECTED_DIFF=0.5 minute
MAX_DIFF=1 minute
```

## Strict time gate
```java
now.after(next_drop_time)
```

Exact:
```text
now < next => reject
now == next => reject
now > next => allow
```

## Throughput
For one-item reservations:
```text
min delay 720m => max 2/day
expected 809.5m => 2880/1619 ~= 1.7789/day
max delay 899m => 1440/899 ~= 1.6018/day
```

Current cap is extremely large relative to timer rate.

For remaining=99999989 and batch=1:
```text
min-delay exhaustion ~= 136,986.29 years
expected-delay exhaustion ~= 154,012.15 years
max-delay exhaustion ~= 171,043.36 years
```

So current practical limiting factor is timer cadence, not totalCount.

## nextCount overflow
Runtime:
```java
int nextCount = appearCount + (int)count;
```

Under valid non-malformed preconditions:
```text
count <= totalCount-appearCount
totalCount <= Integer.MAX_VALUE
```
therefore:
```text
appearCount+count <= totalCount <= Integer.MAX_VALUE
```

So:
```text
NEXTCOUNT_OVERFLOW=IMPOSSIBLE_UNDER_VALID_STATE
```

## Malformed-state behavior
Examples:
```text
total=-1, appeared=0 => reject
total=100, appeared=-1 => remaining=101
total=Integer.MAX_VALUE, appeared=Integer.MIN_VALUE
=> arithmetic remaining=4294967295
=> request still capped at Integer.MAX_VALUE
```

Thus malformed negative persisted state can create nonsensical quota semantics even if immediate int overflow is avoided.

Target must validate:
```text
total>=0
appeared>=0
appeared<=total
```

## Reset cache divergence
`resetDropLimit(itemId)` persists:
```text
appearCount=0
nextTime=NULL
```

but supplied runtime does not mutate the in-memory object.

Therefore:
```text
PERSISTED_RESET=YES
IN_MEMORY_RESET=NO
POTENTIAL_STALE_CACHE=YES
```

A reload or explicit cache mutation is required.

## Concurrency
`reserveDrop()` synchronizes on `Drop_limit.class`.

For remaining=1 and two concurrent count=1 requests:
- serialized checks allow at most one successful reservation if DB persistence succeeds.

Persistence order:
```text
compute
-> DB update
-> if success mutate memory
```

If DB update fails:
- method returns false
- memory remains unchanged
- retry remains possible

Thus:
```text
OVER_ISSUANCE_FROM_RESERVATION_LAYER=NO_PROVEN
```
under supplied synchronized path.

## Safe 850 model
Use widened state:
```text
long total
long appeared
long request
long remaining=total-appeared
```

Validate:
```text
total>=0
appeared>=0
appeared<=total
request>0
request<=remaining
```

Timer policy must explicitly define whether max interval is inclusive or exclusive.
Use checked long arithmetic for minute->millisecond conversion and timestamp addition.

## Classification
```text
DATA_LEVEL=L1
RUNTIME_LEVEL=L3
CLIENT_DEP=NO_CURRENT_PROOF
FINAL_LEVEL=L3
```

## Status
```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_物品掉落限制三
SOURCE_ROWS=1
DONOR_RUNTIME=Drop_limit
CURRENT_REMAINING=99999989
CURRENT_TIMER_SUPPORT=720..899
TIMER_EXPECTED_MINUTES=809.5
TIMER_OFF_BY_ONE=PROVEN
STRICT_GATE=NOW_AFTER_ONLY
CAP_BOUNDARY=PROVEN
BATCH_BOUNDARY=1..2147483647
NEXTCOUNT_OVERFLOW=NO_VALID_STATE
MALFORMED_STATE_RISK=YES
RESET_CACHE_RISK=YES
CONCURRENCY_SERIALIZATION=PROVEN
850_NATIVE=NOT_PROVEN
CLIENT_DEP=NO_CURRENT_PROOF
LEVEL=L3
BLOCKERS=850 reservation owner; timer interval policy; malformed-state validation; reset cache synchronization; semantic item mapping
```
