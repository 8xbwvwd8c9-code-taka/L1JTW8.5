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

### Current design status

- 381 remains behavior authority.
- No production auto-hunt code has been copied into 850 yet.
- Final scheduler/session architecture remains gated on MAP-B and MAP-C evidence.

### Next evidence

1. Trace `L1PcInstance.startAI()` and the real runtime loop.
2. Inspect each `Atu_*_Timer` responsibility and scheduling model.
3. Locate 850 repaired-core lifecycle/thread/action equivalents.
4. Map 880 UI/control packet path.
