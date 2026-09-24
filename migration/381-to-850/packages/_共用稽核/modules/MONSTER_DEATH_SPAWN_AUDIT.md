# 381 -> 850 Monster Death Spawn Audit

## Scope

Module:
- `w_怪物死亡召喚`

381 runtime:
- `com.lineage.data.event.Npe_DeadSpan`
- `com.lineage.william.Npc_Dead_span`
- `L1MonsterInstance.receiveDamage(...)` death path

## Source DB state

Current split DB artifact:

```text
DB/381_DB_AI用/w_怪物死亡召喚_202609221205.sql
SIZE=0 bytes
```

Therefore:
- CREATE schema is not available from the split artifact.
- active INSERT content is not available from the split artifact.
- column types/defaults/indexes/keys must NOT be inferred.

Runtime code references these column names only:

```text
死亡NPC編號
召喚NPC編號
召喚NPC時間
死亡NPC說話
tele_x
tele_y
tele_mapid
```

These names prove runtime expectations, not a safe install schema.

## Event gate

`Npe_DeadSpan.execute(...)` only enables:

```text
Npe_DeadSpan.START = true
```

The actual rule data is loaded lazily by `Npc_Dead_span.getData17()`.

## Loader/runtime

`Npc_Dead_span.getData17()` executes:

```sql
SELECT * FROM w_怪物死亡召喚
```

Rows are cached in a process-global list on first use.

For a matching dead monster NPC ID, `forresolvent(pc, mob)` can:

1. broadcast configured text
2. spawn a configured NPC
3. keep the spawned NPC for configured minutes
4. teleport the resolved player to configured X/Y/map

## Death hook

381 `L1MonsterInstance.receiveDamage(...)` invokes:

```java
if (Npe_DeadSpan.START) {
    Npc_Dead_span.forresolvent(atkpc, this);
}
```

This is wired into the monster death transition after the monster is marked dead.

## State / persistence

Rule cache:
- process-local static list
- lazy-loaded once
- no runtime reload path proven

Player/monster persistence:
- no module-specific character persistence proven
- no per-player progress table proven

Spawn lifetime:
- passed to `L1SpawnUtil.spawn(...)` as configured minutes x 60

## Behavioral notes / hazards

### 1. Missing schema is a hard migration blocker

The only current split SQL artifact is empty. Do not synthesize CREATE TABLE from Java getter names.

Required before installable migration:
- authoritative CREATE TABLE, or
- live/source schema dump explicitly supplied for this table

### 2. Cache is one-shot

`NO_MORE_GET_DATA17` prevents re-reading after first use.

DB edits made while the server is running are not proven to take effect until restart.

### 3. Exception handling hides loader failures

`getData17()` catches generic `Exception` and does not log/rethrow in the recovered donor code.

A missing table, bad column, or SQL error can silently leave the module with an empty cache.

Migration should log loader failures explicitly.

### 4. Trigger ownership follows resolved attacker

The hook passes `atkpc` from the damage/death path.

Pet/summon/effect ownership normalization is not proven by this module itself and must be validated against the target death pipeline before porting exact behavior.

### 5. Spawn/teleport are server-side behaviors

The proven feature set uses normal:
- server broadcast
- server spawn
- teleport

No dedicated custom client protocol, HTML, or UI dependency is proven.

## 850 comparison

Targeted inspection did not find:
- `Npe_DeadSpan`
- `Npc_Dead_span`
- an equivalent table/module named `w_怪物死亡召喚`

A native 850 equivalent is therefore NOT_PROVEN.

Do not treat absence of these donor class names as proof that 850 lacks all generic spawn or teleport primitives; only the module-level equivalent is not found.

## Migration shape

If/when authoritative schema is available, prefer a minimal isolated L3 module:

```text
monster-death-spawn-core/
  rule loader
  death hook adapter
  spawn action
  broadcast action
  teleport action
  cache lifecycle

monster-death-spawn-rules/
  install.sql
  rollback.sql
  validation.sql
```

Use 850-native spawn/teleport primitives where possible; do not wholesale port the donor helper.

## Difficulty

`LEVEL=L3`

Reason:
- monster death lifecycle hook
- independent rule loader/cache
- NPC spawn runtime
- optional teleport/broadcast
- no dedicated client dependency proven

The current migration is not installable because schema authority is missing.

## Status

```text
STATUS=BLOCKED
MODULE=w_怪物死亡召喚
LEVEL=L3
CORE_DEP=YES
DB_DEP=YES
DEATH_HOOK=L1MonsterInstance.receiveDamage
EVENT_GATE=Npe_DeadSpan.START
RUNTIME=Npc_Dead_span.forresolvent
PERSISTENCE=NONE_PROVEN
CLIENT_PROTOCOL_DEP=NO
CLIENT_RESOURCE_DEP=NOT_PROVEN
NATIVE_850_EQUIVALENT=NOT_PROVEN
SOURCE_SQL=EMPTY_0_BYTES
BLOCKER=AUTHORITATIVE_SCHEMA_MISSING
```
