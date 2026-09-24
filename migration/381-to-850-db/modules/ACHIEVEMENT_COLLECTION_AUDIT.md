# 381 -> 850 Achievement Collection Audit

## Scope

381 modules:
- `w_成就圖鑑收集設定`
- `w_成就圖鑑收集獎勵`

Primary runtime:
- `com.lineage.data.npc.event.Npc_GQuest`
- `com.add.system.L1BlendTable_1`
- `com.add.system.L1Blend1`
- `com.lineage.william.L1Blend_buff`

Entry NPC:
- name: `貓大天堂^\f=成就圖鑑`
- classname: `event.Npc_GQuest`
- gfxid: `237`

## Current DB content

`w_成就圖鑑收集設定` currently contains 42 visible collection rules.

Quest IDs:
```text
340000 .. 340041
```

Each rule may define:
- npc id
- action
- display note
- level gate
- class gate
- required item IDs
- required item counts
- required enchant levels
- success/failure text
- global broadcast
- quest ID
- ability description

`w_成就圖鑑收集獎勵` maps the same quest IDs to permanent stat bonuses.

Observed reward dimensions include:
- melee/ranged damage
- melee/ranged hit
- SP
- STR/DEX/INT/CON/CHA/WIS
- HP/MP
- MR
- damage reduction
- HPR/MPR
- potion recovery
- EXP modifier
- AC
- weight reduction
- status resistances
- PvP fields

## Rule loader

`L1BlendTable_1.loadBlendTable()` reads:

```sql
SELECT * FROM w_成就圖鑑收集設定 ORDER BY id
```

Rules are keyed by:

```text
npcid + action
```

The loader also builds an ordered achievement quest list used by the UI status pages.

### Schema authority warning

Current split SQL artifacts are INSERT-only.

The runtime requires at least:
- `id` for ORDER BY
- all rule columns listed above

Because CREATE TABLE is not present in the split artifact, a production install schema must not be synthesized from Java accessors alone.

## NPC/UI flow

NPC `event.Npc_GQuest` is the entry point.

Current `Npc_GQuest.talk(...)` first delegates to:

```java
L1BlendTable_1.getInstance().showAchievementCollect(pc, npc)
```

The dynamic UI uses:
- `collect`
- `collect1`
- `collect2`
- `collect3`
- `collect4`
- `collect5`
- `collect6`

Individual achievement detail uses:

```text
ItemBlend2
```

Therefore the proven donor implementation has an HTML/client-resource dependency.

Current repository tree inspection did not locate these HTML resource files themselves, so resource completeness remains NOT_PROVEN.

## Completion flow

`L1Blend1.CheckCraftItem(...)`:

1. rejects already-completed achievement
2. checks level
3. checks class
4. checks required item IDs
5. checks exact required enchant values
6. checks counts
7. consumes matching items
8. optionally broadcasts completion
9. sets quest step to `1`
10. applies reward via `L1Blend_buff.forIntensifyArmor(pc)`
11. sets quest step to `2`

Core sequence:

```java
pc.getQuest().set_step(quest, 1);
L1Blend_buff.forIntensifyArmor(pc);
pc.getQuest().set_step(quest, 2);
```

The temporary step=`1` is the reward-application gate.

## Persistence

Completion state is stored through the existing quest subsystem:

```text
pc.getQuest().get_step(quest)
pc.getQuest().set_step(quest, ...)
```

Therefore this module depends on the existing character quest persistence path.

The current donor logic treats:

```text
step 2 = completed
```

## Permanent stat reward path

`L1Blend_buff` lazily loads:

```sql
SELECT * FROM w_成就圖鑑收集獎勵
```

It applies rows only while:

```text
pc.getQuest().get_step(ruleQuest) == 1
```

The applied bonuses are also accumulated into `pc.get_other()` fields.

That is significant because login restoration does not need to call `L1Blend_buff` again if the aggregate persistent `other` values are authoritative.

## Login reapply

381 `C_LoginToServer` loads quest state and later reconstructs persistent stat additions through the character's `other` data.

The login path contains:

```text
pc.getQuest().load()
...
getOther(pc)
```

and `getOther(pc)` reapplies persisted aggregate combat/stat values such as damage, hit, SP, attributes, HP/MP-related additions and other stored bonuses.

Therefore:

```text
LOGIN_REAPPLY=PROVEN_VIA_PERSISTED_OTHER_AGGREGATE
```

The achievement subsystem itself does not re-run every completed quest reward at login; completion state and persisted aggregate bonus state are separate concerns.

## Donor architecture notes

### 1. Quest ID is ownership-critical

Current active achievement IDs are:

```text
340000..340041
```

These IDs must be checked against 850 quest ownership before migration.

Do not assume this range is free.

### 2. UI code and DB are coupled by row order

`L1BlendTable_1` stores achievement quest IDs in load order and renders completion status by index.

The current table is loaded with:

```sql
ORDER BY id
```

Therefore row ordering is part of the current UI behavior.

Migration should use an explicit display_order field rather than silently depending on source PK order.

### 3. Hard UI capacity

The current dynamic status array supports up to 200 achievements.

Current content uses only 42, but this is a framework constraint.

### 4. Reward cache is lazy/one-shot

`L1Blend_buff.BUILD_DATA` causes reward DB data to be loaded once per process lifetime.

Runtime DB edits are not proven to hot-reload.

### 5. Reward loader error handling is weak

The donor reward loader catches SQL exceptions and returns without a strong surfaced failure path.

Target migration should fail loudly/log clearly when reward data cannot load.

### 6. Material data requires aligned arrays

These fields are parallel arrays encoded as comma-separated strings:

```text
materials
materials_count
materials_enchants
```

The loader assumes compatible lengths.

Migration validation must reject length mismatch before runtime.

### 7. Exact enchant semantics

The donor checks:

```text
checkEnchantItem(itemId, enchant, count)
consumeEnchantItem(itemId, enchant, count)
```

This is not a generic "minimum enchant" rule unless the underlying inventory implementation explicitly behaves that way.

Target behavior must preserve the intended exact/minimum semantics only after verifying the target inventory API.

## 850 comparison

Targeted 850 branch inspection found:

```text
data/contents/achievement-common.bin
```

but did NOT prove an equivalent server-side framework for:
- NPC collection registration
- material + enchant submission
- quest completion ownership
- permanent stat reward mapping
- donor-compatible collection HTML flow

Therefore:

```text
NATIVE_850_EQUIVALENT=NOT_PROVEN
```

The presence of `achievement-common.bin` alone is not sufficient evidence of server runtime compatibility.

## Client/UI dependency

Proven donor UI names:

```text
collect
collect1
collect2
collect3
collect4
collect5
collect6
ItemBlend2
```

The current L381 repository tree did not expose matching HTML files by filename.

So:

```text
CLIENT_HTML_DEP=YES
CLIENT_HTML_RESOURCE_PRESENT=NOT_PROVEN
PROTOCOL_CUSTOMIZATION=NOT_PROVEN
```

This is enough for L4 because functional completion requires donor-specific NPC HTML/menu resources even without a custom packet protocol.

## Recommended migration shape

Do not wholesale port the donor implementation.

Suggested split:

```text
achievement-collection-core/
  rule loader
  completion-state adapter
  material/enchant validator
  reward transaction
  permanent-stat persistence adapter

achievement-collection-ui/
  NPC action routing
  page model
  HTML/menu adapter

achievement-collection-rules/
  install.sql
  rollback.sql
  validation.sql
```

Prefer:
- explicit `display_order`
- explicit schema
- atomic consume + completion + reward transaction
- quest-ID collision validation
- startup validation for reward/rule parity
- deterministic login reapply from one authoritative persistence model

## Migration blockers

Before formal installable migration:

1. obtain authoritative CREATE schema for both `w_成就圖鑑*` tables
2. prove quest IDs `340000..340041` are free or remap them
3. locate/provide required client HTML resources
4. map all donor item IDs semantically to 850
5. validate all permanent-stat APIs against 850
6. choose persistence authority:
   - quest-completion-derived recalculation, or
   - persisted aggregate stat record
7. validate rollback/recalculation semantics

## Difficulty

`LEVEL=L4`

Reason:
- DB rule engine
- NPC action integration
- material/enchant consumption
- character quest persistence
- permanent stat persistence/reapply
- client HTML/menu dependency

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_成就圖鑑收集設定+w_成就圖鑑收集獎勵
LEVEL=L4
CORE_DEP=YES
DB_DEP=YES
NPC_DEP=YES
NPC_CLASS=event.Npc_GQuest
QUEST_DEP=YES
QUEST_RANGE=340000..340041
PERSISTENCE=character quest + persisted other aggregate
LOGIN_REAPPLY=PROVEN_VIA_PERSISTED_OTHER_AGGREGATE
CLIENT_HTML_DEP=YES
CLIENT_HTML=collect,collect1..collect6,ItemBlend2
CLIENT_HTML_RESOURCE_PRESENT=NOT_PROVEN
CLIENT_PROTOCOL_DEP=NOT_PROVEN
NATIVE_850_EQUIVALENT=NOT_PROVEN
SOURCE_SCHEMA=NOT_PROVEN
ACTIVE_RULES=42
BLOCKERS=schema,quest ownership,HTML resources,item mapping,target stat API
```
