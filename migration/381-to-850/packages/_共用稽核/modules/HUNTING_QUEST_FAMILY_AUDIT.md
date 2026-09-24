# 381 -> 850 Hunting Quest Family Audit

## Migration authority

Target authority:
- 850 quest lifecycle
- 850 NPC/action UI
- 850 monster-death hooks
- 850 persistence/protocol/data model

381 is donor-only:
- feature intent
- quest content/data
- behavioral reference

Do NOT port 381 quest UI/core wholesale.

## Scope

381 tables:
- `w_狩獵怪物任務`
- `w_狩獵怪物任務_地圖`
- `w_狩獵怪物任務_系統`

Primary 381 runtime:
- `ServerQuestMobTable`
- `ServerQuestMaPTable`
- `L1PcQuest`
- `L1MonsterInstance`

The three tables represent two different kill-tracking models plus an NPC/action wrapper.

---

## A. w_狩獵怪物任務

Current rows: 4.

Quest IDs:
```text
9002 火窟
9004 象牙塔
9005 龍之谷
9007 遺忘地區
```

All current rows:
- require level 52
- start at quest step 1
- finish at step 255
- award EXP 200000
- include configured reward item arrays
- track multiple explicit NPC template IDs with individual required counts

Example:
```text
quest 9002:
mob_id    = 45203,45206,45284,45291,45365
mob_count = 40,50,60,60,20
```

### 381 persistence

`L1PcQuest.set_step(...)` creates a `CharQuest`.

When `QuestMobSet.START` is enabled it initializes:

```text
mob_count[]
```

from `ServerQuestMobTable`.

Kill progress is persisted through:
```text
CharacterQuestReading.updateQuest(...)
```

So this is a true persistent per-character quest counter model.

### 381 kill hook

On monster death, for the resolved player killer:

```java
if (QuestMobSet.START && !monster.isResurrect() && !pc.isInParty()) {
    ServerQuestMobTable.get().checkQuestMob(pc, monster.getNpcId());
}
```

Important donor semantics:
- resurrection kills excluded
- party players excluded entirely
- only the resolved killer is credited

Do not preserve these restrictions automatically unless desired for 850 design.

### 381 completion

`ServerQuestMobTable`:
- matches quest ID + current quest step
- finds killed mob ID
- increments the matching counter
- persists progress
- checks all configured counters
- advances/saves configured quest step
- can grant configured items/EXP/teleport depending on row data

---

## B. w_狩獵怪物任務_地圖

Current rows: 2.

```text
9050 古魯丁地監
  npc=93065
  action=555000
  maps=9,10,11,12,13
  required kills=10
  entry cost=44070 x10
  reward=44070 x100

9051 象牙塔
  npc=93065
  action=555001
  maps=78,79,80,81,82
  required kills=10
  entry cost=44070 x10
  reward=44070 x100
```

This is NOT the same counter model as `w_狩獵怪物任務`.

It tracks:

```text
any eligible monster killed while player is on one of configured maps
```

rather than configured monster IDs.

### 381 state model

When accepted:
- quest step becomes 1
- allowed map IDs are copied into player save fields
- remaining-kill count is copied into `pc.get_other3().type1`

On each qualifying monster death:
```java
ServerQuestMaPTable.check(pc)
```

which decrements:
```text
pc.get_other3().type1
```

and sends:
```text
顯示狩獵文字 + remaining count
```

When count reaches zero:
- skill effect `80552` is used as a short completion guard
- reward settlement is performed
- map/save fields are reset
- quest step becomes 255

### Critical architecture difference

The map-task remaining count is NOT stored in `L1PcQuest.mob_count[]`.

It is held in custom player fields:
```text
other3.type1
Save_Quest_Map1..5
```

This is donor-specific state coupling and should NOT be ported to 850.

850 should store all hunting progress in its native quest/task persistence model.

---

## C. w_狩獵怪物任務_系統

Current rows: 8.

It contains paired actions for:
- accept quest
- complete/redeem quest

for the same daily areas.

Examples:
```text
6003 accept 9002
6004 complete 9002
6007 accept 9004
6008 complete 9004
6009 accept 9005
6010 complete 9005
6013 accept 9007
6014 complete 9007
```

The table is a generic NPC/action condition/reward wrapper.

Fields include:
- NPC/action
- min/max level
- class/poly checks
- quest checks
- item/material checks
- reward items
- quest writes
- HTML
- teleport
- time/week window
- EXP
- trigger-item deletion
- quest-end flag

For current hunting rows, the active subset is much smaller.

Do NOT port this broad generic 381 action engine to 850.

Convert the active hunting actions into 850-native quest/NPC flow.

---

# 850 native capability

850 already contains a native quest system with kill-tracking behavior.

Proven target components:
- `QuestNewTable`
- `L1QuestNew`
- `MobQuestWeekTable`
- `L1MonsterInstance`

850 `L1MonsterInstance` already performs monster-death quest progress updates.

Observed target behavior:
```text
for each active L1QuestNew:
  compare configured monster IDs
  increment matching kill counter
```

850 also has weekly monster quest data:
```text
mob_quest_week
  mob_number
  count
```

Therefore:

```text
850_NATIVE_KILL_QUEST_FRAMEWORK=YES
```

This materially changes migration strategy.

---

# 850-first migration strategy

## Explicit monster-ID quests

`w_狩獵怪物任務` should be converted into the 850 native quest model.

Do NOT port:
- `ServerQuestMobTable`
- 381 `L1PcQuest.mob_count`
- `QuestMobSet`

Preferred mapping:
```text
381 quest rule
-> 850 QuestNew/L1QuestNew definition
-> 850 native kill counters
-> 850 native completion/reward
```

This is primarily an L2 data/framework adaptation.

## Map-any-kill quests

`w_狩獵怪物任務_地圖` should also use the 850 quest framework.

Add only a minimal generic objective predicate if 850 lacks it:

```text
objective type = KILL_ANY_ON_MAP_SET
map set
required count
```

Progress must be persisted in the 850 quest record, not custom player save fields.

This is:
```text
L2 if 850 objective predicates already support map conditions
L3 only if a new generic map-kill objective adapter is required
```

## NPC accept/complete flow

`w_狩獵怪物任務_系統` should NOT become a parallel generic condition engine.

Use:
- existing 850 NPC/action routing
- 850 quest acceptance
- 850 quest completion/reward flow
- existing 850 UI

Only active hunting semantics should be represented.

---

# Donor defects / migration hazards

## 1. Hardcoded quest-range exclusivity

`ServerQuestMaPTable` hardcodes large quest ranges:
```text
9050..9069
9001..9030
```

to prevent simultaneous map/daily quests.

Target should use an explicit quest-group/exclusive-group field.

Do not port hardcoded ID scans.

## 2. Concrete donor bug in checkquest()

Observed:
```java
if (quest 9058 step == 1) {
    set_step(9050, 255);
}
```

This appears to complete 9050 instead of 9058.

Do not reproduce this behavior.

## 3. Map quest state is split across unrelated player fields

Donor uses:
- quest step
- `other3.type1..type6`
- `Save_Quest_Map1..5`
- skill effect `80552`

This creates restart/login consistency risk.

850 should use one authoritative quest state model.

## 4. Party behavior differs

Explicit monster quest path currently excludes:
```text
pc.isInParty()
```

Map-any-kill path is invoked from the same resolved-killer death flow but does not independently implement contribution/party sharing.

850 migration must explicitly choose:
- last hitter only
- party nearby
- hate/contribution participants

Do not inherit donor behavior accidentally.

## 5. Reward item IDs require semantic mapping

Current source includes custom IDs such as:
- 240314
- 240316
- 240317
- 240319
- 240490
- 44070

Map by semantic identity, not numeric equality.

## 6. NPC/action IDs are donor UI details

NPC IDs:
- 93059
- 93065

Actions:
- 6003,6004,...
- 555000,555001

These are not target contracts.

850 UI/NPC routing is authoritative.

## 7. Split SQL is INSERT-only

No authoritative CREATE schema is present for the three source tables.

Schema recreation should follow the target 850 quest model, not donor schema cloning.

---

# Client/UI dependency

381 uses NPC/action routing and server messages.

No custom client packet protocol is required to preserve the underlying hunting feature.

Because 850 UI is authoritative:

```text
381_UI_PORT=NO
CLIENT_PROTOCOL_EXTENSION=NO
CLIENT_RESOURCE_EXTENSION=NO_PROVEN_NEED
```

If 850 already exposes native quest panels/status updates, use them.

---

# Difficulty

Family-level:
```text
LEVEL=L3
```

Why L3 overall:
- explicit monster quests are mostly L2 conversion
- 850 already has native kill quest tracking
- map-any-kill objective may require one small generic lifecycle extension
- donor NPC/system engine must be replaced by 850-native quest flow

Sub-classification:
```text
w_狩獵怪物任務       = L2 candidate
w_狩獵怪物任務_地圖  = L2/L3
w_狩獵怪物任務_系統  = DO_NOT_PORT; convert active rows to 850 quest actions
```

---

# Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_狩獵怪物任務 family
LEVEL=L3
TARGET_POLICY=850_NATIVE_FIRST
SOURCE_TABLES=3
EXPLICIT_MOB_QUEST_ROWS=4
MAP_ANY_KILL_ROWS=2
NPC_ACTION_ROWS=8
850_NATIVE_KILL_QUEST_FRAMEWORK=YES
850_NATIVE_COMPONENTS=QuestNewTable+L1QuestNew+MobQuestWeekTable
381_SERVERQUESTMOB_PORT=NO
381_SERVERQUESTMAP_PORT=NO
381_GENERIC_NPC_SYSTEM_PORT=NO
PARTY_SEMANTICS=NEEDS_DECISION
QUEST_ID_OWNERSHIP=NEEDS_VALIDATION
ITEM_MAPPING=REQUIRED
SOURCE_SCHEMA=NOT_PROVEN
DONOR_BUG_9058_COMPLETES_9050=YES
BLOCKERS=850 quest field mapping,map-kill objective support,quest ID ownership,item semantic mapping,party credit policy
```
