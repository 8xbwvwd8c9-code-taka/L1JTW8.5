# 381 -> 850 Castle War Victory Reward Audit

## Scope
Table:
- w_城戰獲勝獎勵

Current rows:
- 7
- castle_id 1..7
- itemid 44070
- count 20000
- recover=0
- CREATE schema not proven

## Donor runtime

Loader/runtime:
- com.lineage.server.datatables.CastleWarGiftTable

Settlement callsite:
- ServerWarExecutor.finishCastleWar(...)
- when reward=true:
  CastleWarGiftTable.get().get_gift(castleId)

Therefore:
```text
CALLSITE=PROVEN
RUNTIME_REACHABILITY=PROVEN_IF_TABLE_LOADED
SETTLEMENT_OWNER=CASTLE_WAR_END
RECIPIENT_OWNER=CURRENT_CASTLE_CLAN
RECIPIENT_SCOPE=ONLINE_CLAN_MEMBERS_ONLY
```

## Proven donor double-distribution bug

`CastleWarGiftTable.get_gift(key)` performs the reward loop once inside `finally`, then repeats the same reward loop again after the try/finally block.

For current rows:
```text
configured R = 20000
runtime passes D = 2
actual per member = 40000
correct per member = 20000
overpay per member = 20000
runtime multiplier = 2
over-issuance = 100%
```

Formula:
```text
PER_MEMBER = R * D
TOTAL_DISTRIBUTED = R * D * M
EXPECTED_CORRECT_TOTAL = R * M
OVERPAY_PER_MEMBER = R * (D - 1)
TOTAL_OVERPAY = R * (D - 1) * M
```

where M is eligible online member count.

## Economy scale proof

Current donor, all seven castles:

M=10:
- correct=1,400,000
- donor=2,800,000
- overpay=1,400,000

M=30:
- correct=4,200,000
- donor=8,400,000
- overpay=4,200,000

M=50:
- correct=7,000,000
- donor=14,000,000
- overpay=7,000,000

M=100:
- correct=14,000,000
- donor=28,000,000
- overpay=14,000,000

## Duplicate DB rows

If N identical DB rows exist for one castle while donor still performs two runtime passes:

```text
effective_multiplier = 2 * N
per_member = 20000 * 2 * N = 40000 * N
total = 40000 * N * M
```

Therefore DB duplication and runtime duplication multiply.

## recover semantics

Current rows use recover=0.

If a future row uses recover=1, each runtime pass performs global recovery of the item before granting the current owner clan.

Under the current double-pass bug:

```text
pass 1: recover all X -> grant R
pass 2: recover all X -> grant R
```

Final state:
```text
recipient final = R
non-recipient final = 0
```

Thus recover=1 masks the double-grant in final recipient balance, but globally deletes the same item from non-recipients.

This is a destructive global ownership model and must not be copied into 850 without explicit policy.

## Integer safety

Use long for aggregate issuance.

Tested:
- count * passes * M * 7 * 30 overflows int at M=1000
- count * passes * M * 7 overflows int at M=10000
- count * passes * M overflows int at M=100000

No long overflow for tested M <= 1,000,000 and 7 castles * 30 periods.
Maximum tested:
8,400,000,000,000
< Long.MAX_VALUE.

Safe:
```java
long perMember = ((long) count) * passes;
long totalIssued = ((long) count) * passes * members * castleCount * periods;
```

Prefer checked multiplication for production accounting/logging.

## Idempotency requirement

850 settlement must be idempotent.

Recommended key:
```text
(castle_id, war_instance_id, reward_row_id, player_id)
```

Invariant:
```text
grant iff key has not been settled
```

Repeated settlement calls 1, 2, or 10 times must still produce exactly one configured reward R for each key.

## Table initialization blocker

`CastleWarGiftTable.get()` only creates the singleton.
It does not call `load()`.

The settlement callsite directly invokes:
```text
CastleWarGiftTable.get().get_gift(castleId)
```

Targeted donor startup/runtime inspection did not prove a `CastleWarGiftTable.get().load()` call.

Therefore:
```text
TABLE_LOADER=EXISTS
TABLE_AUTO_LOAD=NO
TABLE_LOAD_CALLSITE=NOT_PROVEN
CACHE_INITIALIZATION=BLOCKED
```

If no hidden/omitted callsite exists, the reward map stays empty and the proven settlement call becomes a no-op.

This must be resolved before treating donor behavior as active production behavior.

## 850 native assessment

850 authority already has native castle-war lifecycle:
- L1CastleWar
- L1War
- CastleTable
- L1CastleLocation
- clan ownership

The native war-end owner is proven conceptually in `L1CastleWar.a(L1Castle)`, which owns the castle war end lifecycle and schedule reset.

No equivalent configurable victory-reward table/settlement layer is proven.

Preferred 850 shape:
```text
native CastleWarEnd
-> resolve authoritative winning/current owner clan
-> load reward definitions by castle
-> settle once per war instance
-> grant through idempotency key
```

Do not port donor duplicate loop or global recover semantics blindly.

## Item mapping

Donor item:
```text
44070 x20000
```

850 semantic identity for 44070 is NOT_PROVEN.

Do not assume numeric identity across versions.

```text
ITEM_MAPPING=NOT_PROVEN
DIRECT_ID_SAFE=NO
```

## Classification

```text
DATA_LEVEL=L1
SETTLEMENT_RUNTIME_LEVEL=L3
CLIENT_DEP=NO_CURRENT_PROOF
FINAL_LEVEL=L3
```

No custom client resource/protocol dependency is proven.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_城戰獲勝獎勵
SOURCE_ROWS=7
DONOR_RUNTIME=CastleWarGiftTable
CALLSITE=ServerWarExecutor.finishCastleWar
CALLSITE_PROVEN=YES
TABLE_LOAD_CALLSITE=NOT_PROVEN
RECIPIENTS=ONLINE_CURRENT_CASTLE_CLAN_MEMBERS
CORRECT_PER_MEMBER=20000
DONOR_PER_MEMBER=40000
OVERPAY_PER_MEMBER=20000
RUNTIME_MULTIPLIER=2
OVER_ISSUANCE_PERCENT=100
DUPLICATE_ROW_FORMULA=effective_multiplier=2*N
RECOVER_RECIPIENT_FINAL=20000
RECOVER_NONRECIPIENT_FINAL=0
INT_OVERFLOW_RISK=YES_AGGREGATES
LONG_OVERFLOW=NO_TESTED_DOMAIN
IDEMPOTENCY_REQUIRED=YES
850_NATIVE_WAR_OWNER=YES
850_NATIVE_REWARD_LAYER=NOT_PROVEN
ITEM_MAPPING=NOT_PROVEN
CLIENT_DEP=NO_CURRENT_PROOF
LEVEL=L3
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=table load callsite; item 44070 semantic mapping; 850 reward definition/settlement layer; idempotency persistence/key; recover policy
```
