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

### Current design status

- MAP-A controller gate: partially complete and documented.
- MAP-B main runtime loop: confirmed.
- MAP-B move/safe/magic/supply timers: confirmed.
- MAP-B summon/settings/teleport helper: pending.
- MAP-C 850-native lifecycle/scheduler mapping: pending.
- 880 UI/control mapping: pending.
- No production auto-hunt implementation has been copied into 850.

### Next evidence

1. Inspect `Atu_summon_Timer`, `Atu_settings_IO`, `AtuAutoTeleport` and action-state helpers.
2. Trace exact stop/reset methods around `L1PcInstance` AI state.
3. Map repaired 850 `GeneralThreadPool`, `L1PcInstance`, `L1ActionPc`, map and client lifecycle classes.
4. Map L880C UI/control path only after donor runtime responsibilities are fully enumerated.
