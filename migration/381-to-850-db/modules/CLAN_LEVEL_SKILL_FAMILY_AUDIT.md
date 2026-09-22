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
