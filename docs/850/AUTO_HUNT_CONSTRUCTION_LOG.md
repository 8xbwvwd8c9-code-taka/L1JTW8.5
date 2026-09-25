# L1JTW8.5 自動狩獵施工紀錄

- Branch: `work/850-auto-hunting`
- Rule: 不建立其他自動狩獵／核心施工支線。
- Host authority: 850 repaired core
- Core donor: L381
- UI/control donor: L880C
- Record policy: 每完成一個 MAP、掛點或功能，同步紀錄來源、判斷、修改檔案、驗證與未解問題。

## 2026-09-25 — MAP-A / L381 主控制器

### Source

- Repo: `8xbwvwd8c9-code-taka/L381`
- Branch: `main`
- File: `src/com/add/Atu_auto_Controller.java`
- Related files discovered in `src/com/add/`:
  - `Atu_auto_Controller.java`
  - `Atu_move_Timer.java`
  - `Atu_magic_Timer.java`
  - `Atu_safe_Timer.java`
  - `Atu_supply_Timer.java`
  - `Atu_summon_Timer.java`
  - `Atu_settings_IO.java`
  - `AtuAutoTeleport.java`
  - `AutoAttackUpdate.java`
  - `AutoAttackUpdateNew.java`

### Confirmed responsibility

`Atu_auto_Controller` is the L381 auto-hunt control/configuration entry point. It handles only `ata_` actions and keeps per-character state in concurrent maps for supply, magic, summon, movement and safety settings.

Settings are lazily loaded per character by `ensureLoaded()` and persisted by `Atu_settings_IO`.

### Confirmed start gate

`doStart(L1PcInstance pc)` performs these checks before starting:

1. global auto-bot switch (`AutoBot.START`)
2. blocked hours (`ConfigAtuAuto.BLOCK_HOURS_CHECK`)
3. allowed map (`ConfigAtuAuto.isMapAllowed(...)`)
4. player alive / HP > 0
5. existing old/new auto-hunt conflict via `pc.isActived()`
6. cooldown skill effect `99666`
7. movement mode selected: patrol or teleport

Before starting, it also clears/reset old movement/teleport state and several skill-effect timers.

The actual start transition is:

```text
pc.setActived(true)
pc.setAtuNewGuaji(true)
pc.startAI()
```

### Confirmed stop behavior

`doStop(L1PcInstance pc)` refuses to stop the legacy auto-hunt when the current session is not marked as the new Atu session. For an Atu session it:

- `pc.setActived(false)`
- cancels pending auto teleport
- unlocks the player
- clears patrol anchor/radius
- kills old timer/effect IDs 8853 / 6930 / 6931 / 6932
- updates the UI state

### Porting consequence for 850

Do **not** copy the L381 start/stop state model literally.

L381 mixes:

- legacy `isActived/setActived`
- new `isAtuNewGuaji/setAtuNewGuaji`
- skill-effect timer IDs as lifecycle/control state
- multiple responsibility-specific timers

For 850 these responsibilities must be mapped onto a single authoritative auto-hunt session/state model after MAP-B/MAP-C confirms the 850-native scheduler and lifecycle hooks.

## 2026-09-25 — MAP-B / L381 runtime loop and timer split

### Runtime ownership

Confirmed files:

- `src/com/lineage/server/model/Instance/L1PcInstance.java`
- `src/com/lineage/server/model/Instance/PcAI.java`

`L1PcInstance.startAI()` is the concrete transition from controller state into runtime execution. It:

1. creates `pcMove` when the character has no follow master,
2. sets `_aiRunning=true`,
3. sets `actived=true`,
4. constructs `new PcAI(this)`,
5. calls `PcAI.startAI()`.

`PcAI.startAI()` submits the task to `NpcAiThreadPool`.

`PcAI.run()` owns the per-character main loop. While the player has a valid max HP it repeatedly calls `AIProcess()` and sleeps according to the previous action type. On normal loop termination it waits through the dead state, clears targets, then resets `aiRunning` and `actived`.

### Main action pipeline

`PcAI.processAction()` currently performs the following confirmed sequence/responsibilities:

- terminate on dead / offline / HP<=0 / inactive state,
- enemy/red-player safety checks,
- overweight shutdown,
- no-weapon shutdown,
- blocked-hour shutdown,
- map-allowed gate,
- patrol-radius correction,
- movement-rate check,
- target validity and target refresh/search,
- no-target roaming through `noTarget()`,
- single-target auto magic before normal attack,
- normal target handling through `onTarget()`,
- movement/action interval classification for next loop sleep.

For the new Atu path `AIProcess()` also takes `AtuActionGate.lock.tryLock()` and gives pending/safety teleport priority before normal actions. This confirms that L381 already had to add an action-level serialization gate because movement, magic and teleport execute from different scheduler contexts.

### Timer responsibility split

#### `src/com/add/Atu_move_Timer.java`

- Scheduler: `GeneralThreadPool.scheduleAtFixedRate`
- Period: 1 second
- Scope: scans all online world players
- Responsibility: idle-attack teleport trigger
- Uses `AtuMoveState.lastAttackMs` rather than merely target visibility
- Skips inactive/non-Atu/dead/teleport/private-shop states
- Actual teleport is centralized through `AtuAutoTeleport.tryTeleport(...)`
- On timer exception it cancels its future and starts a replacement timer

#### `src/com/add/Atu_safe_Timer.java`

- Scheduler: `GeneralThreadPool.scheduleAtFixedRate`
- Period: 1 second
- Scope: scans all players
- Responsibility: safety escape detection
- Reasons confirmed: attacked by player, boss nearby, enemy-name nearby, polite-mode nearby player
- Uses the same centralized `AtuAutoTeleport.tryTeleport(...)` execution path
- On timer exception it cancels/restarts the global timer

#### `src/com/add/Atu_magic_Timer.java`

- Scheduler model: dedicated `Thread` loop with 1-second sleep
- Scope: scans all players
- Responsibility: area auto-magic
- Acquires the per-player `AtuActionGate` before running magic/safety teleport logic
- Single-target magic is **not** driven by this one-second loop; `PcAI` calls `tryCastSingleMagic()` during each player action cycle before normal attack
- Skill usability checks include learned skill, configured whitelist, weapon requirement, MP threshold, actual range and `glanceCheck()` line-of-sight

#### `src/com/add/Atu_supply_Timer.java`

- Scheduler: `PcOtherThreadPool.scheduleAtFixedRate`
- Period: 30 seconds
- Scope: scans all players
- Responsibility: configured consumable/item replenishment
- Requires online connection, alive state, non-teleport state and active Atu session
- Purchases directly by storing item then consuming adena when inventory count falls below threshold

### MAP-B architectural evidence

L381 is not a single-loop architecture. It combines:

```text
Controller
  -> L1PcInstance.startAI()
     -> per-player PcAI on NpcAiThreadPool

Global timers/threads
  -> move/idle teleport
  -> safety teleport
  -> area magic
  -> supply
  -> other Atu timers still to map
```

This creates multiple execution contexts that can act on the same character. `AtuActionGate` and `AtuAutoTeleport` are compensating coordination mechanisms for that split.

### Porting consequence for 850

Do not assume the L381 timer split is the desired 850 architecture. Preserve behavior first, then use MAP-C to decide which periodic responsibilities can be folded into an 850-native per-character session/task and which truly belong to low-frequency global services.

In particular, the following must be proven before implementation:

- one authoritative task/session owner per PC,
- idempotent start/stop,
- stale task prevention,
- death/logout/disconnect/restart/teleport/map-transfer reset hooks,
- action serialization between movement, attack, magic and teleport,
- whether 850 already has reusable scheduled-task ownership patterns.

## 2026-09-26 — MAP-B closure / summon, persistence, teleport and action serialization

### `src/com/add/Atu_summon_Timer.java`

- Scheduler model: dedicated `Thread` with a 1-second sleep loop.
- Scope: scans every online player in `World.get().getAllPlayers()`.
- Gate: requires active/new Atu session, alive state, non-teleport/private-shop state and `AtuActionGate.isSkillReady()`.
- Coordination: obtains the same per-character `AtuActionGate.lock` used by PcAI/magic/teleport; pending/safety teleport has priority.
- Responsibilities:
  - detect whether a live summon already exists,
  - select wizard summon or elf elemental skill,
  - apply retry interval using a per-player timestamp map,
  - cast summon through native `L1SkillUse`,
  - optionally haste pets/summons,
  - heal pets/summons by configured HP/MP thresholds.
- Port note: preserve summon/heal decisions, but do not create another permanent global thread in 850 unless MAP-C proves a need. These decisions are candidates for a lower-frequency phase of the per-player auto-hunt session.

### `src/com/add/Atu_settings_IO.java`

381 persists each character's auto-hunt settings to:

```text
./data/atu/{charId}.cfg
```

using Java `Properties`.

Persisted groups:

- supply master/item/quantity settings,
- auto magic, MP threshold, single/area skill and intervals,
- summon/heal/haste thresholds,
- patrol/teleport mode/radius/idle timeout,
- safety escape toggles and enemy-name list.

Port note: the **configuration schema/semantics** are useful donor evidence, but the filesystem-per-character persistence mechanism is not assumed for 850. 850 persistence must follow the host's existing DB/config conventions after host mapping.

### `src/com/add/AtuActionGate.java`

381 uses a fair per-character `ReentrantLock` as an action serialization primitive. It also keeps:

- a monotonic skill-ready deadline (`skillReadyAtNanos`),
- a teleport completion sequence (`teleportSequence`),
- a thread-local marker preventing duplicated auto-teleport visuals.

The design explicitly distinguishes packet paths, which may wait for an action, from global polling timers, which use `tryLock()` and skip a busy player rather than blocking an entire scan.

Port consequence: 850 must retain the **single-character action serialization invariant**, but it does not need to copy this exact class if a single-owner session/scheduler can remove most competing execution contexts.

### `src/com/add/AtuAutoTeleport.java`

This is not a simple direct teleport helper. Confirmed flow:

```text
trigger wants teleport
  -> acquire per-character AtuActionGate
  -> revalidate reason/current scene
  -> CAS pendingTeleport from null to intent
  -> schedule windup callback on GeneralThreadPool
  -> release lock while waiting
  -> callback reacquires lock
  -> verify same pending intent / position / map / teleport sequence / resource legality
  -> call native C_UseSkill or C_ItemUSe auto-teleport entry
  -> confirm teleport sequence advanced
  -> reset movement/target state and apply retry cooldown
```

Important invariants:

- trigger-time checks are repeated at commit time,
- only one pending auto-teleport intent exists per character,
- delayed callback cannot act after the character/map/position/teleport sequence changed,
- actual resource consumption and final legality remain in native skill/item handlers,
- the action lock is never held across the visual windup delay.

This is strong donor evidence for stale-action prevention. In 850, the behavior should be represented by session generation/task identity plus native teleport validation rather than copying every 381 compatibility field.

### L381 runtime topology — closed for architecture purposes

Confirmed runtime topology is now:

```text
Atu_auto_Controller
  -> per-character state maps + settings persistence
  -> L1PcInstance.startAI()
       -> PcAI on NpcAiThreadPool
            -> per-character main action loop
            -> target/search/move/attack
            -> single-target magic

Global side execution contexts
  -> Atu_move_Timer     : GSTPool, 1 s, idle teleport
  -> Atu_safe_Timer     : GSTPool, 1 s, safety teleport
  -> Atu_magic_Timer    : dedicated Thread, 1 s, area magic
  -> Atu_summon_Timer   : dedicated Thread, 1 s, summon/pet support
  -> Atu_supply_Timer   : PcOtherThreadPool, 30 s, replenishment

Coordination layer
  -> AtuActionGate      : per-character serialization
  -> AtuAutoTeleport    : pending intent + delayed revalidation/native commit
```

### MAP-B conclusion

For 850, preserve the **behaviors and invariants**, not the 381 scheduler topology.

The strongest candidate architecture remains:

- one authoritative per-character auto-hunt session/task for high-frequency target/move/combat/skill/safety decisions,
- native 850 skill/item/attack/movement/teleport APIs for execution,
- low-frequency services only where host evidence justifies them,
- one session generation/task identity to invalidate delayed work,
- explicit lifecycle stop/reset at 850 death/logout/disconnect/restart/teleport/map-change hooks.

This remains a candidate until MAP-C verifies 850-native thread/lifecycle patterns.

### Current design status

- MAP-A L381 controller/control state: confirmed.
- MAP-B L381 main runtime loop: confirmed.
- MAP-B move/safe/magic/supply/summon responsibilities: confirmed.
- MAP-B settings persistence and teleport/action coordination: confirmed.
- MAP-B L381 architecture responsibility mapping: CLOSED for initial 850 architecture selection.
- MAP-C 850 repaired-core lifecycle/scheduler mapping: IN PROGRESS.
- 880 UI/control mapping: pending.
- No production auto-hunt implementation has been copied into 850.

## 2026-09-26 — MAP-C / 850 repaired-core scheduler and lifecycle mapping

### Authority correction

`class_source_mapping.csv` on `completed/l1jtw85-core-fixes` is authoritative for recovered class identities. Confirmed mappings used by this MAP are:

```text
L1PcInstance      -> ap.u
GeneralThreadPool -> bi.e
C_Disconnect      -> aj.ah
C_Restart         -> aj.bv
L1Teleport        -> aq.am
L1Map             -> ax.b
MapsTable         -> ao.ao
ClientThread      -> bj.d
```

An earlier working note incorrectly associated `L1PcInstance` with `fx.kh` and `GeneralThreadPool` with `ap.u`; that mapping is rejected and must not be reused.

### Native scheduler API — `bi.e` / GeneralThreadPool

850 already exposes two execution families:

- general executor/scheduler
- player executor/scheduler

The periodic APIs return `ScheduledFuture<?>` and use `scheduleAtFixedRate(...)`. Delayed `delay<=0` helpers execute immediately and return `null`, so ownership code must not assume every submission returns a cancellable future.

For a persistent per-player auto-hunt loop, the native fit is the player scheduled pool (`bi.e.a().b(runnable, initialDelay, period)`) with the returned future owned by the player/session object.

### Native per-player task ownership — `ap.u` / L1PcInstance

850 already keeps multiple `ScheduledFuture<?>` fields directly on the player object. Existing lifecycle style is explicit and idempotent:

```text
start:
  if future == null:
    future = GeneralThreadPool.schedule...

stop:
  if future != null:
    future.cancel(true)
    future = null
```

`fv()` also demonstrates grouped teardown of several player-owned monitor futures by canceling and nulling every future.

Port consequence: 850 auto-hunt should use the same host-native ownership pattern instead of 381's global scanning timers. Start/stop must remain idempotent and a session generation/token must additionally reject an already-running stale callback after stop/restart.

### Disconnect and restart convergence

`C_Disconnect` delegates to `ClientThread.c()`.

`ClientThread.c()` and its error/finally paths synchronize on the active `L1PcInstance`, call `pc.p()`, then clear the active character reference. `C_Restart` follows the same player cleanup primitive: synchronize on `pc`, call `pc.p()`, clear `client.activeChar`.

`L1PcInstance.p()` is therefore the authoritative full logout/restart cleanup convergence point. It already calls `fv()`, clears world/account/player state, stops other player-owned tasks (`b()` / `d()`), and detaches the client.

Port consequence: auto-hunt teardown must be part of the same player-owned cleanup contract. Disconnect and Restart must not require separate global-timer cleanup code.

### Death entry

The 850 player damage path calls `L1PcInstance.b(lastAttacker)` when HP reaches zero. That method synchronizes on the player, rejects duplicate death, marks the player dead, applies death state, then submits the remaining death work to the player executor.

Port consequence: auto-hunt stop must occur at this synchronous death transition (or through a stop primitive invoked by it), before asynchronous death processing is queued. Waiting for a later periodic check permits an avoidable stale action window.

### Teleport / map-transfer path

`L1Teleport` (`aq.am`) has a two-stage pattern:

1. set destination X/Y/map/heading and optional effect/packet state,
2. commit teleport through the central `a(pc)` path.

The commit path marks teleporting, moves the object between world/map structures, updates player location, moves pets/dolls, refreshes visibility/status and finally clears teleporting state.

Port consequence:

- delayed auto-hunt actions must carry session generation/task identity and revalidate current map/location before commit;
- the auto-hunt runtime must not execute movement/attack while native teleport state is active;
- a map-id change must invalidate target/path state immediately even if auto-hunt itself is allowed to continue on the destination map;
- safety/idle teleport must call native 850 teleport/item/skill paths rather than mutating coordinates directly.

### Map-policy APIs

`MapsTable` (`ao.ao`) populates the live `L1Map` (`ax.b`) instances from DB table `mapids`, including map policy flags such as teleportable, escapable, usable_item, usable_skill and related rules.

`L1Map` exposes both tile/path passability and these map policy booleans. Auto-hunt therefore must consume native map state for path/skill/item/teleport legality; it must not duplicate a second hard-coded map-policy truth source for rules already represented by 850.

### MAP-C architecture decision

Host evidence now supports this 850-native topology:

```text
L1PcInstance / AutoHuntSession
  owns exactly one ScheduledFuture
  owns generation/session identity
  owns target/path/action timestamps

player scheduled pool
  -> one per-character tick
       -> validate generation + active session
       -> validate connected/alive/not teleporting
       -> detect map change and clear stale target/path
       -> serialize one action decision
       -> execute through native 850 APIs

lifecycle stop/reset
  -> explicit user Stop
  -> synchronous death transition
  -> L1PcInstance.p() for disconnect/logout/restart
  -> teleport/map change invalidates stale target/path/delayed work
```

This intentionally does **not** copy L381's global move/safe/magic/summon scanning threads. Low-frequency supply/support work should first be attempted as phases within the same per-player session; split services require separate host evidence.

### MAP-C remaining proof before closure

1. Identify the exact 850 public/source-level insertion surface that will own the new session without editing the read-only recovered authority branch.
2. Add a regression verifier for start/start, stop/stop, stop-then-stale-tick, death, cleanup/restart and map-change generation invalidation.
3. Freeze the Phase-1 file list and only then begin production implementation.

### Current design status

- MAP-A: CLOSED.
- MAP-B: CLOSED.
- MAP-C scheduler/lifecycle/map-policy evidence: mapped.
- MAP-C implementation insertion surface + regression harness: NEXT.
- Production auto-hunt implementation: not started.
