# L1JTW8.5 Auto Hunt Phase-1 Target / Move Log

DATE=2026-09-26
BRANCH=work/850-auto-hunting

## DONOR / AUTHORITY
- Behavior donor: L381 `L1PcInstance.findAtuTarget()` / `isAtuSelectableTarget()` / `atuAutoEngageRange()` / `onTarget()` / `pcMove` / `PcAI`.
- Runtime authority: 850 repaired core on this branch.
- UI/client/protocol donor checked: L880C main.
- 850 class mapping confirmed: `ap.s=L1MonsterInstance`, `ap.u=L1PcInstance`, `aq.aq=L1World`.

## 381 TARGET RULES PORTED
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

## 381 MOVE RULES PORTED
- `pcMove.moveDirection()` is a planner: it returns one next heading and does not itself attack.
- Donor path search is capped at 20 tiles.
- When already inside engage range, movement is not required.
- Path failure marks the current monster with `pcId+100000` for 20 seconds and returns to target search instead of walking through walls.
- Real movement is a separate execution step which updates heading, map occupancy, coordinates and movement packets.
- New donor auto-hunt treats move and attack as separate paced actions; a successful movement consumes move timing, target search/attack does not.

## IMPLEMENTATION
Target/search:
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

Move/session:
- `recovered-src-obf/auto/hunt/AutoHuntMovePlanner.java`
- `recovered-src-obf/auto/hunt/AutoHuntMoveController.java`
- `recovered-src-obf/auto/hunt/AutoHunt850Mover.java`
- `recovered-src-obf/auto/hunt/AutoHuntSession.java`
- `recovered-src-obf/auto/hunt/AutoHunt850Session.java`
- `recovered-src-obf/auto/hunt/AutoHuntService.java`

Regression / verifier:
- `tools/auto-hunt/AutoHuntTargetPolicyTest.java`
- `tools/auto-hunt/AutoHuntBossIndexTest.java`
- `tools/auto-hunt/AutoHuntEngageRangeTest.java`
- `tools/auto-hunt/AutoHuntRuntimeSettingsTest.java`
- `tools/auto-hunt/AutoHuntTargetStateTest.java`
- `tools/auto-hunt/AutoHunt850TargetStateTest.java`
- `tools/auto-hunt/AutoHunt850TargetProviderTest.java`
- `tools/auto-hunt/AutoHuntMovePlannerTest.java`
- `tools/auto-hunt/AutoHuntMoveControllerTest.java`
- `tools/auto-hunt/AutoHunt850TargetActionTest.java`
- `tools/auto-hunt/validate_target_selector_adapter.py`
- `tools/auto-hunt/validate_move_adapter.py`
- `tools/auto-hunt/validate_service_move_wiring.py`

## VALIDATION
Target policy RED: missing `AutoHuntTargetPolicy` -> javac failed as expected.
Target policy GREEN: compile exit 0; `AUTO_HUNT_TARGET_POLICY_TEST=PASS`.
Correction regression: unreachable-marker-only gate and avoid-occupied behavior -> PASS.
Boss index: `AUTO_HUNT_BOSS_INDEX_TEST=PASS`.
Engage range: `AUTO_HUNT_ENGAGE_RANGE_TEST=PASS`.
850 target adapter contract stub compile: PASS.
Runtime settings RED: missing class -> 3 javac errors / exit 1.
Runtime settings GREEN: `AUTO_HUNT_RUNTIME_SETTINGS_TEST=PASS`.
Target state RED: missing class -> 3 javac errors / exit 1.
Target state GREEN: `AUTO_HUNT_TARGET_STATE_TEST=PASS`.
850 target-state RED: missing provider/currentTarget API -> 2 javac errors / exit 1.
850 target-state GREEN: `AUTO_HUNT_850_TARGET_STATE_TEST=PASS`.
850 target provider RED: missing provider/selector boundary -> 4 javac errors / exit 1.
850 target provider GREEN: `AUTO_HUNT_850_TARGET_PROVIDER_TEST=PASS`.
Move planner RED: missing `AutoHuntMovePlanner` -> 26 javac errors / exit 1.
Move planner GREEN: `AUTO_HUNT_MOVE_PLANNER_TEST=PASS`, exit 0.
Move controller RED: missing `AutoHuntMoveController` -> 27 javac errors / exit 1.
Move controller GREEN: `AUTO_HUNT_MOVE_CONTROLLER_TEST=PASS`, exit 0.
850 move adapter RED: missing `AutoHunt850Mover.java` -> verifier exit 1.
850 move adapter GREEN: `AUTO_HUNT_850_MOVE_ADAPTER=PASS`, exit 0.
Target action RED: missing `TargetAction` contract -> javac exit 1.
Target action GREEN: `AUTO_HUNT_850_TARGET_ACTION_TEST=PASS`, exit 0; duplicate Stop remains idempotent.
Provider engage-range RED: missing `engageRange()` -> javac exit 1.
Provider engage-range GREEN: `AUTO_HUNT_850_TARGET_PROVIDER_TEST=PASS`, exit 0.
Service wiring RED: missing `start(pc, settings)` -> verifier exit 1.
Service wiring GREEN: `AUTO_HUNT_SERVICE_MOVE_WIRING=PASS`, exit 0.

Validation scope note: these are pure Java regressions, stub-compiled 850 adapter behavior tests and contract verifiers. A full recovered-source project compile has not been claimed. GitHub reports no status checks and no workflow runs for the Move wiring commit.

## 850 TARGET ADAPTER MAPPING
Confirmed:
- visible candidate source: `aq.aq.a().b((aq.aa)pc, 10)`
- NPC template ID: `mob.U_().b()`
- HP: `mob.ea()`
- dead: `mob.eX()`
- hidden status: `mob.ac()`
- attack speed: `mob.O()`
- skill effect presence: `mob.bB(skillId)`
- per-player unreachable marker: `mob.bB(pc.fr()+100000)`
- 1-tile attack line: `pc.c(mob.fs(), mob.ft(), 1)`
- mob ranged value: `mob.U_().s()`
- mob current target: protected `ap.t.m`, exposed read-only by `ap.AutoHuntTargetBridge`
- current-target attack-line gate: `mob.c(target.fs(), target.ft(), mob.U_().s())`
- equipped weapon item: `pc.v()`
- weapon template: `pc.v().a()`
- weapon range: `pc.v().a().aB()`
- skill template: `ao.be.a().a(skillId)`
- skill ranged: `skill.p()`

## 850 MOVE ADAPTER MAPPING
Confirmed from repaired 850 runtime:
- manual move packet handler is `aj.bj` / `C_MoveChar`.
- occupancy-aware path gate for server AI: `pc.fq().c(x, y, heading)`.
- dungeon/transition gate before direct movement: `aq.l.a().a(nextX, nextY, pc.fq().b(), pc)`.
- release old occupancy: `pc.fq().a(pc.fu(), true)`.
- mutate position: `pc.fu().a(nextX, nextY)`.
- heading: `pc.ct(heading)`.
- occupy destination: `pc.fq().a(pc.fu(), false)`.
- trap processing: `ao.bi.a().a(pc)`.
- movement packet: `be.cb` / `S_MoveCharPacket`.
- normal broadcast path: `pc.b(new cb(pc))`; ghost broadcast path: `pc.c(new cb(pc))`.
- because auto-hunt movement is server-driven, the player also receives `pc.a(new cb(pc))` self echo.
- native MOVE interval is read with `pc.ce().b(aq.ak.a.a)`.
- client anti-speed state mutator `pc.ce().a(aq.ak.a.a)` is intentionally NOT called by auto-hunt; that path is for validating incoming client movement and could flag server-owned automation.
- unreachable target marker: `target.j(pc.fr()+100000, 20000)`.

## BOSS CLASSIFICATION / BOOTSTRAP
- 850 `npc` template loader does not expose an `is_boss` field.
- Boss spawns are loaded from `spawnlist_boss` using `npc_id`.
- `AutoHuntBossIndexLoader` performs `SELECT DISTINCT npc_id FROM spawnlist_boss` once and replaces the in-memory snapshot.
- Configured start `AutoHuntService.start(pc, settings)` now binds the one-shot Boss index load before constructing Target Search / Move.
- Boss-index SQL failure is fail-closed: configured auto-hunt does not start with unknown Boss classification.
- Target scanning never performs a DB query in the tick loop.

## 880 UI / PROTOCOL EVIDENCE
Checked L880C `main` at `8dcd1f84a7748fef91029adb52eca7bc0eab74e7`.

Visible launcher package contains compiled launcher artifacts and `LinHelperZ.ini`; no source-level WPF/ViewModel/packet model for auto-hunt is present in the visible tree. `LinHelperZ.ini` contains heal/item/skill/polymorph lists and `[Global]` display flags (`NightMode`, `ExpEffect`, `GoldEffect`, `ItemNotify`, `DamageTally`).

Repository code search and visible recent history returned no source evidence for:
- `treatBossAsNormal`
- `avoidOccupied` / no-steal
- patrol enable/origin/radius
- `autoMagicOn`
- `singleSkillId`

Decision: `UNSUPPORTED_BY_880_DONOR_EVIDENCE`. Do not invent an 880 packet ID, UI binding, storage key or default value.

`AutoHuntRuntimeSettings` remains a transport-agnostic 850 behavior contract. A future verified 880 UI/packet adapter may populate it without changing Target Search / Move behavior.

## TARGET / ACTION STATE CONTRACT
- Session owns one identity-based `AutoHuntTargetState<ap.s>`.
- acquire / replace / clear invalidates action generation; same target identity does not.
- map change independently invalidates action generation.
- `TargetAction` executes only after target selection.
- `TargetAction=false` clears current target and returns the next tick to Target Search.
- Move retains target for `MOVED`, `COOLDOWN`, `STEP_REJECTED`, `IN_RANGE`.
- Move clears target for `INVALID_TARGET`, `UNREACHABLE`.
- all session stop paths run `onStop()`: manual Stop, death, disconnect/restart cleanup and teleport gate therefore clear target and reset Move cooldown.

## PHASE-1 CORE STATUS
Target Search = READY.
Basic Move = READY.
Boss-index configured bootstrap = READY.
Session Target/Move state = READY.
880 settings transport = BLOCKED_BY_DONOR_EVIDENCE, intentionally not guessed.
Full-project compile = NOT_CLAIMED.

## REMAINING BEFORE UI ACTIVATION
- identify or implement the actual verified 880 UI/packet transport for `AutoHuntRuntimeSettings`.
- do not call configured auto-hunt start from an unverified client packet.

NEXT=Basic Attack. Reuse 850 native combat/range/speed APIs; do not add skills, consumables, loot or direct HP mutation until Basic Attack regression is closed.
