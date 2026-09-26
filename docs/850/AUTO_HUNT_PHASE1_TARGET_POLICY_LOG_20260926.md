# L1JTW8.5 Auto Hunt Phase-1 Target Policy Log

DATE=2026-09-26
BRANCH=work/850-auto-hunting

## DONOR / AUTHORITY
- Behavior donor: L381 `L1PcInstance.findAtuTarget()` / `isAtuSelectableTarget()` / `atuAutoEngageRange()`.
- Runtime authority: 850 repaired core on this branch.
- UI/client/protocol donor checked: L880C main.
- 850 class mapping confirmed: `ap.s=L1MonsterInstance`, `ap.u=L1PcInstance`, `aq.aq=L1World`.

## 381 RULES PORTED
- Candidate set is monsters only.
- Search cap is 10 tiles.
- Nearest selectable monster wins.
- Boss excluded unless configured to be treated as normal.
- Exclude NPC 81257.
- Exclude blocked-effect targets 33/50/1011/1009.
- Exclude dead / HP<=0 / hidden / attackSpeed==0.
- Per-player unreachable marker is `pcId+100000`; only when that marker exists and a 1-tile attack line is still unavailable is the target rejected.
- Optional avoid-occupied mode: if the mob is already targeting another character and can still attack that target at its ranged value, do not steal it.
- Patrol mode allows a target outside the patrol radius only when boundary overflow is within engage range.
- Engage range: no weapon => 1; weapon range is authoritative; negative weapon range => 9; enabled single-target magic may extend range to the skill ranged value.

## IMPLEMENTATION
- `recovered-src-obf/auto/hunt/AutoHuntTargetPolicy.java`
- `recovered-src-obf/auto/hunt/AutoHuntBossResolver.java`
- `recovered-src-obf/auto/hunt/AutoHuntBossIndex.java`
- `recovered-src-obf/auto/hunt/AutoHuntBossIndexLoader.java`
- `recovered-src-obf/auto/hunt/AutoHuntEngageRange.java`
- `recovered-src-obf/auto/hunt/AutoHuntTargetSelector.java`
- `recovered-src-obf/auto/hunt/AutoHunt850TargetSelector.java`
- `recovered-src-obf/auto/hunt/AutoHuntRuntimeSettings.java`
- `recovered-src-obf/auto/hunt/AutoHunt850TargetProvider.java`
- `recovered-src-obf/auto/hunt/AutoHuntTargetState.java`
- `recovered-src-obf/ap/AutoHuntTargetBridge.java`
- `recovered-src-obf/auto/hunt/AutoHunt850Session.java` target-provider hook / current target state
- `tools/auto-hunt/AutoHuntTargetPolicyTest.java`
- `tools/auto-hunt/AutoHuntBossIndexTest.java`
- `tools/auto-hunt/AutoHuntEngageRangeTest.java`
- `tools/auto-hunt/AutoHuntRuntimeSettingsTest.java`
- `tools/auto-hunt/AutoHuntTargetStateTest.java`
- `tools/auto-hunt/AutoHunt850TargetStateTest.java`
- `tools/auto-hunt/AutoHunt850TargetProviderTest.java`
- `tools/auto-hunt/validate_target_selector_adapter.py`

## VALIDATION
Target policy RED: missing `AutoHuntTargetPolicy` -> javac failed as expected.
Target policy GREEN: compile exit 0; run `AUTO_HUNT_TARGET_POLICY_TEST=PASS`.
Correction regression: confirmed unreachable-marker-only gate and avoid-occupied behavior; fresh compile/run PASS.
Boss index: compile exit 0; `AUTO_HUNT_BOSS_INDEX_TEST=PASS`.
Engage range: compile exit 0; `AUTO_HUNT_ENGAGE_RANGE_TEST=PASS`.
850 adapter contract stub compile: `ADAPTER_STUB_COMPILE_EXIT=0`.
Runtime settings RED: missing class -> javac failed with 3 errors / exit 1.
Runtime settings GREEN: `AUTO_HUNT_RUNTIME_SETTINGS_TEST=PASS`.
Target state RED: missing class -> javac failed with 3 errors / exit 1.
Target state GREEN: `AUTO_HUNT_TARGET_STATE_TEST=PASS`.
850 session target-state RED: missing provider/currentTarget API -> javac failed with 2 errors / exit 1.
850 session target-state GREEN: `AUTO_HUNT_850_TARGET_STATE_TEST=PASS`.
850 target provider RED: missing provider/selector boundary -> javac failed with 4 errors / exit 1.
850 target provider GREEN: `AUTO_HUNT_850_TARGET_PROVIDER_TEST=PASS`.

## 850 ADAPTER MAPPING
Confirmed:
- visible candidate source: `aq.aq.a().b((aq.aa)pc, 10)`
- NPC template ID: `mob.U_().b()`
- HP: `mob.ea()`
- dead: `mob.eX()`
- hidden status: `mob.ac()`; recovered setter parameter is explicitly named `hiddenStatus`
- attack speed: `mob.O()`; recovered setter parameter is explicitly named `atkspeed`
- skill effect presence: `mob.bB(skillId)`
- per-player unreachable marker: `mob.bB(pc.fr()+100000)`
- 1-tile attack line: `pc.c(mob.fs(), mob.ft(), 1)`
- mob ranged value: `mob.U_().s()` (NpcTable `ranged` column)
- mob current target: protected `ap.t.m`, exposed read-only through same-package `ap.AutoHuntTargetBridge`
- current-target attack-line gate: `mob.c(target.fs(), target.ft(), mob.U_().s())`
- equipped weapon item: `pc.v()`
- weapon template: `pc.v().a()`
- weapon range: `pc.v().a().aB()` (weapon table `range` column is loaded through setter `aa(int range)`)
- skill template: `ao.be.a().a(skillId)`
- skill ranged: `skill.p()` (skills table `ranged` column is loaded through setter `o(int)`)

## BOSS CLASSIFICATION
- 850 `npc` template loader (`ao.au` / NpcTable) does not contain an `is_boss` field.
- Boss spawns are loaded separately from `spawnlist_boss` by `ao.e` / BossSpawnTable using `npc_id`.
- `AutoHuntBossIndexLoader` performs one-shot `SELECT DISTINCT npc_id FROM spawnlist_boss` using the existing 850 DB/close pattern and replaces the in-memory snapshot.
- Target scanning reads only the in-memory `AutoHuntBossIndex`; no DB query occurs in the tick loop.

## 880 UI / PROTOCOL EVIDENCE
Checked L880C `main` at `8dcd1f84a7748fef91029adb52eca7bc0eab74e7`.

Visible launcher package on main contains compiled launcher artifacts and `LinHelperZ.ini`; no source-level WPF/ViewModel/packet model for auto-hunt is present in the visible tree. `LinHelperZ.ini` contains heal/item/skill/polymorph lists and `[Global]` display flags (`NightMode`, `ExpEffect`, `GoldEffect`, `ItemNotify`, `DamageTally`).

Repository code search and visible recent history returned no source evidence for these target-behavior controls:
- `treatBossAsNormal`
- `avoidOccupied` / no-steal
- patrol enable/origin/radius
- `autoMagicOn`
- `singleSkillId`

Decision: mark these fields `UNSUPPORTED_BY_880_DONOR_EVIDENCE` for now. Do not invent an 880 packet ID, UI binding, storage key, or default value.

`AutoHuntRuntimeSettings` is therefore a transport-agnostic 850 behavior contract. Every value is explicit and validated; a future verified 880 UI/packet adapter may populate it without changing target behavior code.

## TARGET STATE CONTRACT
- Session owns one identity-based `AutoHuntTargetState<ap.s>`.
- first acquire -> action generation increments.
- same target identity -> no generation change.
- target replacement -> action generation increments exactly once.
- target clear -> action generation increments exactly once.
- repeated clear -> idempotent.
- `AutoHunt850Session.TargetProvider` is optional so existing lifecycle-only regression remains valid; configured sessions can provide real target selection without inventing transport defaults.
- `AutoHunt850TargetProvider` maps explicit runtime settings plus live 850 weapon/skill ranges into `AutoHuntTargetSelector`.

## REMAINING BEFORE UI ACTIVATION
- bind `AutoHuntBossIndexLoader` to a verified startup/auto-hunt bootstrap point
- identify or implement the actual 880 UI/packet transport for `AutoHuntRuntimeSettings`
- do not call configured auto-hunt start from an unverified client packet

## PHASE-1 CORE STATUS
Target policy = READY.
850 target selector = READY.
Settings-to-selector provider = READY.
Session target acquire/retain/replace/clear state = READY.
880 settings transport = BLOCKED_BY_DONOR_EVIDENCE, intentionally not guessed.

NEXT=Map 381 Move behavior to 850 movement/path APIs using `AutoHunt850Session.currentTarget()`. Basic Attack remains blocked until Move regression passes.