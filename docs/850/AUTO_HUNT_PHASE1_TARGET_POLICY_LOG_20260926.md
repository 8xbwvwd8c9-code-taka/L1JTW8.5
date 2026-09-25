# L1JTW8.5 Auto Hunt Phase-1 Target Policy Log

DATE=2026-09-26
BRANCH=work/850-auto-hunting

## DONOR / AUTHORITY
- Behavior donor: L381 `L1PcInstance.findAtuTarget()` / `isAtuSelectableTarget()`.
- Runtime authority: 850 repaired core on this branch.
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

## IMPLEMENTATION
- `recovered-src-obf/auto/hunt/AutoHuntTargetPolicy.java`
- `recovered-src-obf/auto/hunt/AutoHuntBossResolver.java`
- `recovered-src-obf/auto/hunt/AutoHuntBossIndex.java`
- `recovered-src-obf/auto/hunt/AutoHunt850TargetSelector.java`
- `recovered-src-obf/ap/AutoHuntTargetBridge.java`
- `tools/auto-hunt/AutoHuntTargetPolicyTest.java`
- `tools/auto-hunt/AutoHuntBossIndexTest.java`
- `tools/auto-hunt/validate_target_selector_adapter.py`

## VALIDATION
Target policy RED: missing `AutoHuntTargetPolicy` -> javac failed as expected.
Target policy GREEN: compile exit 0; run `AUTO_HUNT_TARGET_POLICY_TEST=PASS`.
Correction regression: confirmed unreachable-marker-only gate and avoid-occupied behavior; fresh compile/run PASS.
Boss index: compile exit 0; `AUTO_HUNT_BOSS_INDEX_TEST=PASS`.
850 adapter contract stub compile: `ADAPTER_STUB_COMPILE_EXIT=0`.

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

## BOSS CLASSIFICATION
- 850 `npc` template loader (`ao.au` / NpcTable) does not contain an `is_boss` field.
- Boss spawns are loaded separately from `spawnlist_boss` by `ao.e` / BossSpawnTable using `npc_id`.
- `AutoHuntBossIndex` therefore accepts an externally loaded snapshot of boss NPC IDs; target scanning does not query DB every tick.
- Loader/bootstrap from `spawnlist_boss` is still pending and must happen once, outside the tick loop.

## REMAINING BEFORE MOVE
- wire one-time `spawnlist_boss.npc_id` loader/bootstrap into `AutoHuntBossIndex`
- map 381 `atuAutoEngageRange()` to 850 weapon range / future UI settings
- wire selector into `AutoHunt850Session.onTick()` only after the above settings inputs are explicit
- then validate target refresh/replace/clear lifecycle

NEXT=Boss index loader + engage-range/settings adapter -> session target state -> Move. Basic Attack remains blocked until Move target-state validation passes.
