# 381 -> 850 Scheduled Monster / Event Spawn Audit

## Migration authority

Target authority:
- 850 spawn lifecycle
- 850 scheduler/thread pool
- 850 announcement packets
- 850 NPC/portal implementation

381 is donor-only.

## Scope

381 table:
- `w_鐘點怪物施放`

Donor runtime:
- `com.lineage.william.NowTimeSpawn`
- `com.lineage.data.event.NowTimeSpawn`
- `ServerSpawnMob`
- `StartTimer_Server`

## Current content

Current split SQL contains 20 visible rows.

The content describes:
- announcement-only time points
- scheduled NPC spawn
- scheduled monster waves
- random-radius spawn
- optional lifetime
- optional portal mode
- weekday restriction
- normal + special announcements
- optional screen effects

Current event sequence includes an Infinite Battle style schedule:
- 20:30 ticket seller
- 21:00 warning
- 21:01..21:55 sequential monster waves

Several 14:00..19:00 rows are announcement-only.

## Source schema blocker

Runtime requires:

```text
sn
```

as the rule identity:

```java
int id = rs.getInt("sn");
_ItemIdIndex.put(id, rule);
```

Current split INSERT column list does NOT include `sn`.

Therefore:

```text
RULE_ID_AUTHORITY=NOT_PROVEN
SOURCE_SCHEMA=NOT_PROVEN
```

Do not infer `sn` from INSERT row order.

## Donor scheduler

`ServerSpawnMob.start()` schedules a task every 10 seconds.

At runtime it reads current:
- hour
- minute

and processes rules only when:

```text
_omm != currentMinute
```

This prevents repeated execution during the same minute.

Rule scheduling supports:

```text
限制星期 > 0 -> matching weekday only
限制星期 == -1 -> every day
```

## Announcement behavior

For each matched rule:

```text
公告內容   -> S_SystemMessage
特殊公告   -> S_PacketBoxGree
畫面特效[] -> S_PacketBox(83, effectId)
```

Rows may contain announcements even when NPC ID/count/location are zero.

Thus the source table is both:
- spawn schedule
- event announcement schedule

## Spawn behavior

For a normal NPC/monster row:

```text
NPC編號
數量
X/Y
地圖
隨機中心生怪
存在時間
```

The donor creates runtime NPC instances directly.

When count > 1:
- each NPC is spawned independently

Random placement uses:
- X +/- randomRange
- Y +/- randomRange
- passability check
- up to 50 attempts

If `存在時間 > 0` donor sets:
```text
npc.spawnTime = time
```

The exact cleanup owner is outside this table but timed existence is part of the donor contract.

## Portal mode

When:

```text
是否為傳送門 = 1
```

donor uses a dedicated `spawnDoor(...)` path.

The table also contains destination fields:
- x
- y
- m

However the current visible source rows all use:

```text
是否為傳送門 = 0
```

Therefore portal support is donor framework capability but not active current content.

Do not implement it during initial 850 migration unless a future active row requires it.

## Current active feature subset

Visible content uses:
- daily scheduling (`限制星期=-1`)
- announcements
- normal NPC/monster spawn
- random spawn radius
- limited lifetime on selected NPC/BOSS rows

Visible content does NOT use:
- portal mode
- destination x/y/m
- screen effect IDs

This is important for minimizing the target extension.

## 850 native capability

850 already has native scheduled boss spawn support:

```text
L1SpawnBoss
```

Proven capabilities include:
- scheduled spawn delay/time
- weekday restrictions
- recurring boss spawn
- native spawn lifecycle
- native thread-pool scheduling

850 also has:
- `AnnounceTimer`
- normal world announcement infrastructure
- normal NPC spawn primitives
- generic scheduler

Therefore:

```text
850_NATIVE_SCHEDULED_SPAWN=YES
850_NATIVE_ANNOUNCEMENT_PRIMITIVES=YES
```

Do NOT port:
- donor 10-second polling loop
- donor `NowTimeSpawn` cache
- donor `ServerSpawnMob`

## 850-first mapping

### Scheduled monster/NPC rows

Prefer conversion to:
- existing 850 boss/scheduled spawn configuration

where target semantics fit.

### Announcement-only rows

Use 850-native scheduled announcement mechanism or one small generic event-schedule layer.

Do not create fake NPC rows merely to trigger announcements.

### Wave grouping

The current donor table implicitly creates an event sequence through adjacent timestamps.

Target should optionally model:

```text
event_group = infinite_battle
sequence/order
time
spawn/announce action
```

This is cleaner than relying on unrelated row IDs.

### Lifetime

If 850 scheduled spawn does not natively support temporary NPC lifetime, add one small reusable:
```text
despawn_after_seconds
```
extension to the target spawn lifecycle.

Do not recreate the donor direct-object spawn implementation.

## Donor hazards

### 1. Rule ID omitted from split SQL

Runtime requires `sn`, but current INSERT does not supply it.

### 2. Polling scheduler is unnecessary in 850

The donor wakes every 10 seconds and scans every rule.

850 has scheduled task primitives and should schedule the next concrete event instead.

### 3. Restart/missed-event semantics are undefined

The donor triggers only when the server is alive during the matching minute.

If the server starts after a scheduled minute, the event is missed.

Target must explicitly choose:
- miss past events
- catch up within grace period
- start only next schedule

Do not inherit this accidentally.

### 4. Duplicate schedule identity

Without authoritative `sn`, same-time rows must be treated as independent action rows based on content, not guessed IDs.

### 5. Random spawn fallback may place on center tile

After 50 failed location attempts donor falls back to center location.

Target should use native 850 spawn placement/passability behavior.

### 6. Announcement and spawn are coupled

A spawn failure does not conceptually require announcement failure.

850 should treat actions independently and log each result.

### 7. Source content labels can disagree with NPC IDs/messages

Example wave names/announcement text do not always line up cleanly with the next NPC label.

Do not use `name` or announcement text as authoritative NPC identity.

NPC identity must be mapped semantically from the actual 381 NPC definition.

## Client dependency

Current active content uses standard:
- system messages
- green announcement box
- ordinary NPC/monster objects

No custom client protocol is proven.

Current `畫面特效` values are NULL.

```text
CLIENT_PROTOCOL_DEP=NO
CUSTOM_EFFECT_DEP_CURRENT_CONTENT=NO
```

## Difficulty

Family level:

```text
LEVEL=L3
```

Subclassification:

```text
scheduled NPC/monster rows matching L1SpawnBoss = L2 candidate
announcement-only rows                         = L2 candidate
temporary lifetime extension                   = L2/L3
event grouping/schedule adapter                = L3 small generic extension
portal capability                              = SKIP current content
```

Why overall L3:
- mixed schedule action types
- target event grouping
- temporary lifetime parity
- schema/identity blocker

But this is explicitly an 850-native migration, not a runtime transplant.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_鐘點怪物施放
LEVEL=L3
TARGET_POLICY=850_NATIVE_FIRST
SOURCE_ROWS=20
DONOR_RUNTIME=NowTimeSpawn+ServerSpawnMob
RULE_ID_FIELD=sn
RULE_ID_IN_SPLIT_INSERT=NO
RULE_ID_AUTHORITY=NOT_PROVEN
850_NATIVE_SCHEDULED_SPAWN=YES
850_NATIVE_COMPONENT=L1SpawnBoss
850_NATIVE_ANNOUNCEMENT_PRIMITIVES=YES
POLLING_RUNTIME_PORT=NO
PORTAL_CURRENT_CONTENT=NO
SCREEN_EFFECT_CURRENT_CONTENT=NO
CLIENT_PROTOCOL_DEP=NO
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=authoritative schema,NPC semantic mapping,lifetime mapping,event schedule policy,restart/missed-event policy
```
