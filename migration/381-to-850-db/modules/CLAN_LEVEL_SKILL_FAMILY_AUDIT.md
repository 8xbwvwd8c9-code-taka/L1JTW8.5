# 381 -> 850 Clan Level / Skill Family Audit

## Scope
381 tables:
- w_血盟等級
- w_血盟技能
- w_血盟能量怪物 (current empty split; tracked separately as SKIP)

Current rows:
- w_血盟等級 = 10
- w_血盟技能 = 30 (3 skill families x 10 levels)
- CREATE schemas not proven

## Donor runtimes

### Clan level
- loader/runtime: com.lineage.william.ClanOriginal
- login hook: C_LoginToServer -> ClanOriginal.forIntensifyArmor(pc)
- owner input: pc clan -> clan.getClanLevel()

### Clan skill
- loader: RewardClanSkillsTable
- lifecycle/apply: ClanSkillDBSet.add(pc)
- event gate: ClanSkillDBSet.START
- login/clan hook: C_LoginToServer clan section -> ClanSkillDBSet.add(pc)
- owner input: clan.getClanSkillId() + clan.getClanSkillLv()

## Clan-level model

w_血盟等級 currently defines level 1..10.

The donor directly adds the matched clan-level vector at login:
- max HP/MP
- melee/ranged damage
- melee/ranged hit
- MR/SP/AC
- elemental resistances
- STR/DEX/CON/WIS/INT/CHA
- physical and magic reduction
- EXP rate
- HPR/MPR
- weight reduction

Max HP/MP application also adds the same configured amount to current HP/MP.

No clan-level-owned subtract/recompute path is proven.

```text
CLAN_LEVEL_OWNER=CLAN_STATE
APPLY_EVENT=LOGIN
REMOVE_EVENT=NOT_PROVEN
RECOMPUTE=NO
DRIFT_RISK=YES_IF_REPLAYED
STALE_STATE_RISK=YES_IF_CLAN_LEVEL_CHANGES_WHILE_ONLINE
```

## Clan-skill model

Current skill families:
- ClanSkillId 1: stat/damage profile, levels 1..10
- ClanSkillId 2: HP/MP profile, levels 1..10
- ClanSkillId 3: MR/reduction profile, levels 1..10

ClanSkillDBSet.add(pc):
- reads current clan skill id/lv
- validates optional reincarnation/high-level gates
- loads one L1ClanSkills row
- directly mutates live player stats
- no inverse/remove path is proven here

```text
CLAN_SKILL_OWNER=CLAN_STATE
APPLY_EVENT=LOGIN_CLAN_LOAD
REMOVE_EVENT=NOT_PROVEN
RECOMPUTE=NO
DRIFT_RISK=YES_IF_REPLAYED
STALE_STATE_RISK=YES_IF_CLAN_SKILL_CHANGES_WHILE_ONLINE
```

## Shared owner conclusion

Clan level and clan skill are different definitions but the same lifecycle domain.

Preferred 850 ownership:

```text
ClanStateOwner
  authoritative:
    clan membership
    clan level
    clan skill id/lv

  recompute:
    ClanLevelModifier
  + ClanSkillModifier
  = effective clan-derived modifier
```

Do not preserve two independent login-time incremental systems.

## Shared StatModifier boundary

Both tables use the same general numeric vocabulary already identified in other modules.

```text
SHARED_STAT_VECTOR=YES
SHARED_LIFECYCLE=YES_WITHIN_CLAN_DOMAIN
OWNER=CLAN_STATE
TIMED_OWNER_SHARED=NO
EQUIPMENT_OWNER_SHARED=NO
TRANSFORM_OWNER_SHARED=NO
COLLECTION_OWNER_SHARED=NO
CASTLE_OWNER_SHARED=NO
```

Clan level and clan skill MAY share one ClanStateOwner because membership/state transitions affect both.

## Current data anomalies

### w_血盟技能
- ClanSkillId=1 level 6 name says Lv.3 while row level is 6.
  This is a display/name mismatch, not a runtime level mismatch.
- Material/MaterialCount/MaterialLevel are comma-tokenized parallel arrays.
  Current source must be validated semantically before migration.
- MaterialCount values are very large and differ by level; do not normalize by assumption.
- Item id 40308 semantic mapping to 850 remains unproven.

### w_血盟等級
- Contribution is 0 for all current rows.
- ExpRate is stored/read as double.
- current level vectors are cumulative-by-row definitions, not proven incremental deltas.
  Target should treat each row as the full modifier for that clan level unless a progression rule proves otherwise.

## Runtime application safety

Donor additive model assumes:
- player object is at baseline before login hook
- hook runs exactly once
- clan level/skill does not need mid-session removal/replacement

Counterexample:
```text
B -> login add V(level5)
  -> hook replay
  -> B + 2V(level5)
```

Mid-session transition risk:
```text
level5 active
-> clan upgrades to level6
-> no proven remove(level5)/apply(level6)
```

850 should recompute from current authoritative clan state.

## HP/MP invariant

Clan level can add max HP/MP and also increases current HP/MP.
Clan skill family 2 adds max HP/MP without adding current HP/MP in ClanSkillDBSet.add().

Therefore donor has inconsistent HP/MP policy across two clan-owned systems.

850 must define one explicit policy and preserve:
```text
0 <= currentHP <= maxHP
0 <= currentMP <= maxMP
```

## 850-native assessment

850 authority already has:
- clan membership/state
- clan persistence
- player stat primitives
- login clan loading
- character status packets

A direct native clan-level/clan-skill modifier engine equivalent is not yet proven.

Preferred minimal extension:
- ClanStateOwner
- data-driven ClanLevelDefinition / ClanSkillDefinition
- shared StatModifierDefinition
- idempotent recompute on login and clan state changes

No donor framework transplant is justified.

## Client dependency

Current active behavior uses server-side stat/status packets and textual messages.
No custom client resource/protocol dependency is proven for the numeric effect layer.

```text
CLIENT_DEP=NO_CURRENT_PROOF
```

Separate UI/upgrade-control paths for purchasing or changing clan skill may still require dedicated audit.

## Classification

```text
CLAN_LEVEL_LEVEL=L3
CLAN_SKILL_LEVEL=L3
FAMILY_LEVEL=L3
```

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_血盟等級_血盟技能_FAMILY
CLAN_LEVEL_ROWS=10
CLAN_SKILL_ROWS=30
CLAN_LEVEL_RUNTIME=ClanOriginal
CLAN_SKILL_RUNTIME=RewardClanSkillsTable+ClanSkillDBSet
OWNER=CLAN_STATE
LOGIN_APPLY=PROVEN
REMOVE_PATH=NOT_PROVEN
DONOR_RECOMPUTE=NO
DRIFT_RISK=YES
STALE_STATE_RISK=YES
SHARED_MODIFIER=YES
SHARED_CLAN_OWNER=YES
850_NATIVE=PARTIAL
CLIENT_DEP=NO_CURRENT_PROOF
LEVEL=L3
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=850 ClanStateOwner/recompute contract; clan state-change hooks; material/item semantic mapping; skill-upgrade action path; HP/MP policy; CREATE schemas
```


## Clan skill upgrade / forget control path

Donor control owner:
- `com.lineage.data.npc.Npc_clan`

For DB-driven clan skills (`ClanSkillDBSet.START`):

### Learn / upgrade

Command `2`:
1. resolves selected ClanSkillId
2. computes next ClanSkillLv
3. loads next `L1ClanSkills`
4. validates Material / MaterialCount / MaterialLevel arrays
5. consumes all configured materials
6. writes:
   - clan.setClanSkillId(...)
   - clan.setClanSkillLv(...)
7. persists through `ClanReading.updateClanSkill(clan)`

No call to:
- remove old ClanSkill modifier
- add new ClanSkill modifier
- recompute online clan members

is present in this action path.

### Forget

Command `4`:
1. records current skill name
2. sets ClanSkillId=0
3. sets ClanSkillLv=0
4. persists through `ClanReading.updateClanSkill(clan)`

No stat removal/recompute is performed for online members.

Therefore:
```text
MID_SESSION_UPGRADE_STATE_CHANGE=PROVEN
MID_SESSION_STAT_RECOMPUTE=NO
MID_SESSION_OLD_MODIFIER_REMOVE=NO
MID_SESSION_NEW_MODIFIER_APPLY=NO
RELOG_REQUIRED_FOR_EFFECT_REFRESH=EFFECTIVELY_YES
```

This is a concrete donor lifecycle defect for online members.

## Material transaction model

Upgrade material arrays are parallel:
- Material
- MaterialCount
- MaterialLevel

Donor performs two passes:
1. validate all required materials
2. consume each requirement sequentially

This avoids consuming when validation already fails, but the consume phase is not transactional across multiple materials.

Potential partial failure:
- item 1 consumed successfully
- later consume operation fails or runtime exception occurs
- clan skill state may not update, but earlier material is already lost

850 target should use an atomic/rollback-safe material settlement policy.

## Skill selection scope

Current source contains ClanSkillId 1..3 only.
Npc_clan accepts a selected id when:
`clanSkillId <= 10`

Thus ids 4..10 are accepted by the control gate but currently have no source row; lookup returns null and no upgrade occurs.

Do not assume 10 active skill families exist merely because the UI/control gate allows ids <=10.

## ClanStateOwner recompute triggers

850 should trigger clan-derived modifier recomputation on at least:

- login / clan attachment
- clan level change
- clan skill learn
- clan skill level upgrade
- clan skill forget/reset
- join clan
- leave clan
- clan disband
- any administrative mutation of clan level/skill
- reload of clan modifier definitions if runtime reload is supported

Required safe transition:
```text
old = authoritative previous ClanState modifier
new = recompute(current clan state)

effective = baseline - old + new
```

or preferably rebuild from authoritative modifier sources rather than trusting incremental mutation history.

## Additional donor defects

1. Skill upgrade/forget does not refresh online member stats.
   - impact: stale modifier until relog
   - do not reproduce: state mutation must trigger recompute

2. Multi-material consumption is not atomic.
   - impact: partial item loss on mid-consume failure
   - do not reproduce: validate + atomic settlement / rollback

3. Control gate permits skill ids <=10 while current DB defines only 1..3.
   - impact: UI/control may expose nonexistent ids if misrouted
   - do not reproduce: source-defined identity validation

4. `getMaterialName()` returns `note.toString()` without null guard.
   - if any source row has null/misaligned material arrays, this can throw.
   - current populated rows have all three arrays, so current content does not trigger it.

5. Material array lengths are not explicitly cross-validated before indexed use.
   - malformed future rows could cause index errors.
   - migration installer should validate equal lengths.


## Clan level configuration / contribution path

`ConfigClan` loads:
- 10 level-up material definitions (`clanlv1..10`)
- parallel material counts (`clanlvcount1..10`)
- required clan energy (`clanenergy1..10`)
- required clan adena (`clanadena1..10`)
- player contribution limits
- contribution/reset policy

The loader validates material-id/count array length equality and builds 10 `ClanLevelUpCondition` objects.

Current configured level-up requirements are therefore proven as config-owned, not from the `Contribution` column of `w_血盟等級`.

Current `w_血盟等級.Contribution` values are all 0 and no direct consumption of that column is proven.

```text
LEVEL_UP_REQUIREMENT_OWNER=ConfigClan
W_血盟等級_CONTRIBUTION_RUNTIME=NOT_PROVEN
```

`ClanContribution` event enables the subsystem and loads `NpcClanContribution`.
The current split SQL for `w_血盟能量怪物` is empty, so current monster-energy source contributes no configured rows.

## Clan-level upgrade action closure

`clan_lv` NPC is proven as a display/status NPC:
- clan level
- clan contribution
- player contribution
- clan/player adena contribution

However the exact action consumer that:
- checks `ClanLevelUpCondition`
- consumes required materials/energy/adena
- calls `setClanLevel()`

is NOT_PROVEN in the currently traced donor paths.

Do not invent the upgrade executor.

```text
CLAN_LEVEL_DISPLAY_NPC=PROVEN
CLAN_LEVEL_UPGRADE_EXECUTOR=NOT_PROVEN
```

## Join / leave lifecycle defect

`C_JoinClan` performs membership negotiation but no clan-derived modifier apply/recompute is proven on successful join.

`C_LeaveClan` and clan disband cleanup clear clan identity/contribution fields for online players, but do not call any inverse for:
- `ClanOriginal.forIntensifyArmor`
- `ClanSkillDBSet.add`
- a ClanState modifier removal/recompute path

Therefore an online player can retain previously applied clan-level / clan-skill stats after leaving until character state is rebuilt/relogged.

Likewise a player joining a clan does not have a proven immediate application path for clan level/skill bonuses.

```text
JOIN_RECOMPUTE=NOT_PROVEN
LEAVE_RECOMPUTE=NO
DISBAND_RECOMPUTE=NO
STALE_MODIFIER_AFTER_LEAVE=PROVEN_RISK
STALE_MODIFIER_AFTER_JOIN=PROVEN_RISK
```

This elevates ClanState recompute from a design preference to a required correctness contract.

## Required 850 transition contract

Every authoritative ClanState transition must trigger one idempotent recompute:

```text
membership old -> new
clan level old -> new
clan skill id/lv old -> new

oldModifier = previously owned clan modifier
newModifier = derive(current authoritative ClanState)

replace(oldModifier,newModifier)
```

Required events:
- login
- successful join
- leave
- kick
- clan disband
- clan level-up
- clan skill learn
- clan skill upgrade
- clan skill forget
- GM/admin mutation
- definition reload, if supported


## Clan level upgrade executor closure

Targeted donor inspection covered:
- `ConfigClan`
- `ClanLevelUpCondition`
- `ClanContribution`
- `NpcClanContribution`
- `clan_lv`
- `C_NPCAction`
- clan join/leave/create packets
- `ClanReading` / `ClanTable`
- `L1Clan`

Proven:
- level-up requirement definitions exist in `ConfigClan`
- 10 `ClanLevelUpCondition` objects are built
- clan state persists `clan_level`, `clan_contribution`, `clan_adena`
- `clan_lv` displays current clan/player contribution state
- `ClanTable.updateClan()` persists level/contribution/adena when called

Not proven:
- any runtime consumer of `ConfigClan.clansLevelUpCondition`
- any action path that atomically consumes configured materials + clan energy + clan adena
- any action path that invokes `L1Clan.setClanLevel()` for a live upgrade
- any online-member stat recompute after clan level change

```text
CLAN_LEVEL_REQUIREMENT_MODEL=PROVEN
CLAN_LEVEL_PERSISTENCE=PROVEN
CLAN_LEVEL_DISPLAY=PROVEN
CLAN_LEVEL_UPGRADE_EXECUTOR=NOT_PROVEN
CLAN_LEVEL_RUNTIME_REACHABILITY=NOT_PROVEN
```

This means current donor evidence supports a partially implemented / disconnected level-up subsystem.

Do not implement 850 migration by guessing the missing action semantics.

## Contribution ownership clarification

Three separate concepts exist:

1. `w_血盟等級.Contribution`
   - current rows all 0
   - runtime consumer not proven

2. `clan_data.clan_contribution`
   - persisted on `L1Clan`
   - displayed by `clan_lv`

3. `ConfigClan.clanenergyN`
   - explicit per-level requirement values

These must not be merged by name alone.

Target migration must keep them semantically distinct until the original upgrade executor is recovered or behavior is intentionally redesigned.
