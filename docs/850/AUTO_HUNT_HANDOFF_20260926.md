# L1JTW8.5 自動狩獵對話交接包 — 2026-09-26

## GOAL

繼續 `work/850-auto-hunting` 的 850 自動狩獵開發。不得另開自動狩獵或核心施工支線。

## AUTHORITIES

- Host/runtime authority: 850 repaired core
- Core donor: L381
- UI/control donor: L880C
- 850 類別 mapping authority: `recovery/class_source_mapping.csv`

## CURRENT BRANCH

- Repo: `8xbwvwd8c9-code-taka/L1JTW8.5`
- Branch: `work/850-auto-hunting`
- Known HEAD before homepage/handoff recording: `9df8a5a613e16e61525b63a3a08fbbd720cf9e7b`
- Commit: `feat: add auto-hunt consumable threshold controller`

開始新對話後第一件事：重新抓 branch HEAD；若比上述 SHA 更新，先看新 commit 再施工，不要覆蓋。

## CLOSED

### MAP / lifecycle

- MAP-A L381 controller/control state: CLOSED
- MAP-B L381 runtime/timer/settings/teleport/action coordination: CLOSED
- MAP-C 850 lifecycle/scheduler/map policy: CLOSED
- 1 Player = 1 AutoHuntSession = 1 active ScheduledFuture + generation token
- start/stop idempotent
- disconnect hook: CLOSED
- restart hook: CLOSED
- death synchronous hook: CLOSED
- teleport/map-change stale action invalidation: CLOSED

### Phase-1 combat

- Target Search: CLOSED
- Basic Move: CLOSED
- Basic Attack: CLOSED
- Basic Attack uses 850 native combat path, not direct HP mutation.

### Phase-2 single-target active skill

- SINGLE_TARGET_ACTIVE_SKILL=CLOSED
- Own per-skill cooldown keyed by skillId
- `skills.reuseDelay` confirmed milliseconds
- 850 native skill executor remains authoritative for MP/HP/item deduction and actual effects
- Auto-hunt does not use client anti-speed mutation as its own cooldown mechanism
- Successful skill attempt consumes current tick; otherwise falls back to Move -> Basic Attack
- Boss/elite monster skill-pool rules are unrelated to player auto-hunt and must not be mixed into this module

## CURRENT WORK — AUTO POTION / CONSUMABLE

### Implemented

Production file:

`recovered-src-obf/auto/hunt/AutoHuntConsumableController.java`

Test:

`tools/auto-hunt/AutoHuntConsumableControllerTest.java`

Commits immediately after skill closure:

- `5c0de6942b80aac7c0711a050ca244d72cf2d4bc` — `test: add auto-hunt consumable policy regression`
- `9df8a5a613e16e61525b63a3a08fbbd720cf9e7b` — `feat: add auto-hunt consumable threshold controller`

Controller currently supports:

- `Mode.PERCENT`
- `Mode.ABSOLUTE`
- enabled/disabled
- threshold validation
- exact boundary consume (`<= threshold`)
- overflow-safe percentage comparison using `long`
- blocked state
- missing item
- native use accepted/rejected result

Current Host contract:

```text
currentHp()
maxHp()
isBlocked()
hasConsumable(itemId)
useConsumable(itemId)
```

### NOT YET IMPLEMENTED

Do NOT mark Auto Potion closed yet.

Missing:

1. `AutoHuntRuntimeSettings` potion fields
   - HP potion enabled
   - threshold mode: percent / absolute
   - threshold value
   - selected item id
   - potion/action cooldown if required by native host behavior
2. 850 adapter that reads real player HP/maxHP/inventory and uses native item-use path
3. Verify exact 850 native `C_ItemUse` / Potion / ItemDelay execution chain
4. `AutoHuntService` wiring
5. Decide action priority relative to Skill / Move / Attack
6. reset semantics on Stop
7. fresh regression closure
8. construction log entry for consumable module

### User requirements for potion

- HP/MP values come from actual character values
- threshold supports percentage or precise numeric value
- potion must go through 850 native item behavior; do not directly change HP/MP
- consumable cooldown independent from Move / Attack / Skill timing
- do not mix pickup, recycling, dissolution, or skill-healing into this module

Current controller only models HP threshold. MP potion policy was not yet implemented at handoff time.

## IMPORTANT DONOR DISTINCTION

L381 `Atu_supply_Timer` is a **replenishment/purchasing** service (30-second low-frequency stock replenishment), not the high-frequency HP potion consumption controller. Do not confuse supply purchasing with actual combat-time potion drinking.

Preserve donor behavior where relevant, but 850 execution must use host-native APIs.

## 880 STATUS

880 visible source/history has not provided authoritative fields for all runtime settings such as Boss handling, avoid-occupied, patrol, and auto-magic. Therefore `AutoHuntRuntimeSettings` is intentionally transport-agnostic. Do not invent an 880 packet/schema/default and claim it is donor-backed.

## KEY FILES

### Production

- `recovered-src-obf/auto/hunt/AutoHuntService.java`
- `recovered-src-obf/auto/hunt/AutoHunt850Session.java`
- `recovered-src-obf/auto/hunt/AutoHuntLifecycle.java`
- `recovered-src-obf/auto/hunt/AutoHuntRuntimeSettings.java`
- `recovered-src-obf/auto/hunt/AutoHunt850TargetProvider.java`
- `recovered-src-obf/auto/hunt/AutoHunt850TargetSelector.java`
- `recovered-src-obf/auto/hunt/AutoHunt850Mover.java`
- `recovered-src-obf/auto/hunt/AutoHunt850Attacker.java`
- `recovered-src-obf/auto/hunt/AutoHunt850SkillCaster.java`
- `recovered-src-obf/auto/hunt/AutoHuntSkillController.java`
- `recovered-src-obf/auto/hunt/AutoHuntConsumableController.java`

### Logs

- `docs/850/AUTO_HUNT_CONSTRUCTION_LOG.md`
- `docs/850/AUTO_HUNT_PHASE1_SESSION_LOG_20260926.md`
- `docs/850/AUTO_HUNT_PHASE1_LIFECYCLE_HOOK_LOG_20260926.md`
- `docs/850/AUTO_HUNT_PHASE1_DEATH_HOOK_LOG_20260926.md`
- `docs/850/AUTO_HUNT_PHASE1_TARGET_POLICY_LOG_20260926.md`
- `docs/850/AUTO_HUNT_PHASE1_BASIC_ATTACK_LOG_20260926.md`
- `docs/850/AUTO_HUNT_PHASE2_SINGLE_TARGET_SKILL_LOG_20260926.md`

## NEXT EXECUTION ORDER

1. Recheck `work/850-auto-hunting` HEAD.
2. Map 850 native item-use chain (`C_ItemUse` / potion handler / item-delay handling / inventory item lookup).
3. TDD an `AutoHunt850ConsumableHost` or equivalent adapter.
4. Extend `AutoHuntRuntimeSettings` with explicit HP potion settings; no hidden defaults.
5. Wire potion attempt into `AutoHuntService` with independent timing.
6. Recommended priority to validate, not assume: emergency potion before offensive skill/move/attack.
7. Add Stop/reset regression.
8. Run fresh controller + adapter + service suite.
9. Only then mark `AUTO_POTION=CLOSED` and update the homepage/log.

## DO NOT

- Do not create another auto-hunt branch.
- Do not merge the whole completed core branch.
- Do not copy 381 timer topology literally.
- Do not directly mutate HP/MP for potion or skill effects.
- Do not invent 880 settings/packet fields without evidence.
- Do not mix Boss/elite affix skill-pool rules into player auto-hunt.
- Do not mix pickup/recycle/dissolve/supply purchasing into combat potion consumption.
- Do not claim full-project compile unless actually run.

## VALIDATION RULE

Before claiming a module closed, use fresh verification from branch content. TDD order remains RED -> implementation -> GREEN. If a verifier itself is stale, fix the verifier first and do not call the stale failure a production bug.

## FINAL STATUS

```text
BRANCH=work/850-auto-hunting
KNOWN_HEAD=9df8a5a613e16e61525b63a3a08fbbd720cf9e7b
LIFECYCLE=CLOSED
TARGET_SEARCH=CLOSED
BASIC_MOVE=CLOSED
BASIC_ATTACK=CLOSED
SINGLE_TARGET_ACTIVE_SKILL=CLOSED
AUTO_POTION=IN_PROGRESS
AUTO_POTION_THRESHOLD_CONTROLLER=IMPLEMENTED
AUTO_POTION_850_ITEM_ADAPTER=NOT_STARTED
AUTO_POTION_SETTINGS=NOT_STARTED
AUTO_POTION_SERVICE_WIRING=NOT_STARTED
PICKUP=NOT_IN_SCOPE
RECYCLE=NOT_IN_SCOPE
DISSOLVE=NOT_IN_SCOPE
FULL_PROJECT_COMPILE=NOT_CLAIMED
NEXT=map 850 native item-use path, then adapter/settings/service TDD
```
