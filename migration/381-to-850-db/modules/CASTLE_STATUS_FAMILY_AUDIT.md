# 381 -> 850 Castle Status Family Audit

## Scope
381 tables:
- w_城堡狀態師
- w_城堡狀態獎勵

Current content:
- castle-status NPC rows = 1
- castle reward rows = 7
- both split artifacts are INSERT-only
- CREATE schemas not proven

## Donor runtime

NPC/action owner:
- com.lineage.william.castleid_npc
- reached from donor C_NPCAction via castleid_npc.forNpcQuest(...)

Reward owner:
- com.lineage.william.CastleOriginal
- startup gate: com.lineage.data.event.CastleOriginal
- login hook: C_LoginToServer -> CastleOriginal.forCastleOriginal(pc)

## w_城堡狀態師

Current row:
- npcid=98000
- action=aaa
- castle=1
- minimum level=1
- class restriction=0
- required item=40308 x500000
- buff skills=26,42
- buff duration=1800
- teleport target=32767,32767,map4
- access time=unrestricted by current row
- non-owner fallback teleport=32767,32768,map4

Proven runtime capabilities:
- validates owning clan castle id
- level restriction
- optional class restriction
- weekday/hour window
- item cost
- configured buff skill list
- configured fixed buff duration
- teleport + timed-map ownership
- non-owner message/teleport

### NPC donor risks

1. Non-owner branch may consume configured item:
   if the player is not a castle owner, runtime checks whether the item exists and then consumes configured count before showing the illegal-entry message.
   Current row can therefore consume 40308 x500000 from an unauthorized player who has enough item.

2. Eligible branch consumes item before buff/teleport completion.
   Partial failure can lose cost after validation but before full action completion.

3. Lazy static cache with swallowed DB exceptions and no reload.

4. Class mapping supports only donor classes 1..7. 850 class model must not inherit this hardcoded assumption.

5. Buff duration uses one scalar for every listed skill rather than each native skill duration.

## w_城堡狀態獎勵

Current rows:
- 7 castle ids: 1..7
- all require level >=52
- all current vectors are identical

Current configured vector:
- max HP +300
- max MP +100
- melee damage +5
- ranged damage +5
- melee hit +3
- ranged hit +3
- MR +10
- SP +3
- AC improvement +5
- CON +1
- WIS +1
- CHA +2
- physical reduction +5
- HPR +8
- MPR +4
- other current fields 0

## Reward lifecycle

Donor authoritative input:
- player's current clan
- clan.castleId
- current level

Application:
- C_LoginToServer invokes CastleOriginal.forCastleOriginal(pc)
- method directly mutates live player stats
- max HP/MP also increase current HP/MP by same delta
- no castle-reward-owned removal/recompute path was proven

Therefore:
```text
OWNER=CLAN_CASTLE_STATE
APPLY_EVENT=LOGIN
REMOVE_EVENT=NOT_PROVEN
RECOMPUTE=NO
DRIFT_RISK=YES_IF_HOOK_REPLAYED
STALE_OWNERSHIP_RISK=YES_IF_CASTLE_CHANGES_WHILE_ONLINE
```

If castle ownership changes while player remains online, current donor path does not prove immediate removal/addition.

## Proven donor logic defect: AddDmg gating

Most reward fields are incorrectly nested under:
```java
if (AddDmg != 0) {
   apply AddDmg;
   if (AddBowDmg != 0) ...
   if (AddHit != 0) ...
   ...
}
```

Thus when AddDmg=0, many independent fields are skipped even when non-zero.

Affected dependent region includes:
- AddBowDmg
- AddHit
- AddBowHit
- AddMr
- AddSp
- AddAc
- elemental resistances
- STR/DEX/CON/WIS/INT/CHA
- physical reduction
- HPR/MPR
- weight reduction

Current rows all have AddDmg=5, so current data is not broken by this defect.
Do not reproduce this structural coupling in 850.

## Unused configured reward fields

Loader reads:
- reduction_magic_dmg
- checkReincarnation

Current apply method does not use either field.

```text
REDUCTION_MAGIC_DMG_RUNTIME=UNUSED
CHECK_REINCARNATION_RUNTIME=UNUSED
```

Do not migrate these fields as active semantics until a separate consumer is proven.

## 850-native assessment

850 authority already has castle primitives:
- CastleTable
- L1CastleLocation
- L1CastleWar
- castle master packet/state

850 also has native:
- stat primitives
- skill execution
- teleport
- item inventory/cost
- clan ownership

No equivalent generic castle-reward / castle-action composition framework is proven.

Preferred target:
```text
CastleOwner
  derives current castle ownership from authoritative clan/castle state

CastleRewardDefinition
  level gate
  modifier vector

CastleRewardRecompute
  recompute from current authoritative castle ownership

CastleActionDefinition
  NPC/action
  access gate
  item cost
  native skill list
  teleport
```

The reward numeric payload can reuse shared StatModifierDefinition, but lifecycle ownership is CASTLE_STATE.

Do not use login-only incremental add as the final 850 model.

## Shared modifier boundary

```text
SHARED_STAT_VECTOR=YES
SHARED_LIFECYCLE=NO
OWNER=CASTLE_STATE
TIMED_OWNER_SHARED=NO
EQUIPMENT_OWNER_SHARED=NO
TRANSFORM_OWNER_SHARED=NO
COLLECTION_OWNER_SHARED=NO
```

## Client dependency

Current active content uses:
- native NPC action/dialog messaging
- native skills
- native teleport
- native status packets

No custom client protocol/resource dependency is proven.

```text
CLIENT_DEP=NO_CURRENT_PROOF
```

## Classification

```text
NPC_LEVEL=L3
REWARD_LEVEL=L3
FAMILY_LEVEL=L3
```

Reason:
- server lifecycle ownership
- castle-state transition/recompute requirement
- item/buff/teleport integration
- no proven custom client resource requirement

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_城堡狀態_FAMILY
NPC_ROWS=1
REWARD_ROWS=7
NPC_RUNTIME=castleid_npc
REWARD_RUNTIME=CastleOriginal
REWARD_APPLY_HOOK=C_LoginToServer
OWNER=CLAN_CASTLE_STATE
SHARED_MODIFIER=YES_STAT_VECTOR_ONLY
RECOMPUTE=NO_DONOR
DRIFT_RISK=YES
STALE_OWNER_RISK=YES
ADD_DMG_GATING_BUG=PROVEN
REDUCTION_MAGIC_DMG=UNUSED
CHECK_REINCARNATION=UNUSED
850_NATIVE=PARTIAL
CLIENT_DEP=NO_CURRENT_PROOF
LEVEL=L3
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=850 castle-state recompute owner; ownership-change hook; semantic item mapping 40308; NPC/action mapping; atomic item-cost/buff/teleport ordering; HP/MP clamp/rebuild semantics
```
