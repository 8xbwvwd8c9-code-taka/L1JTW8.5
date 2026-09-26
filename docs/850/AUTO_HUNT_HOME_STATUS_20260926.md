# L1JTW8.5 自動狩獵首頁狀態 — 2026-09-26

> 這份狀態檔是 `AUTO_HUNT_CONSTRUCTION_LOG.md` 首頁重整時的來源。正式首頁仍為 `docs/850/AUTO_HUNT_CONSTRUCTION_LOG.md`。

## 固定規則

- Branch: `work/850-auto-hunting`
- 不建立其他自動狩獵／核心施工支線。
- Host/runtime authority: 850 repaired core
- Core donor: L381
- UI/control donor: L880C
- 每完成 MAP、掛點、功能或修正，記錄來源、決策、修改檔案、驗證與 blocker。

## 目前總進度

```text
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
880_UI_SETTINGS_TRANSPORT=UNVERIFIED
FULL_PROJECT_COMPILE=NOT_CLAIMED
```

## 已完成

### Session / lifecycle

- 1 Player = 1 AutoHuntSession = 1 active ScheduledFuture + generation token
- start/stop idempotent
- stale task rejection
- disconnect / restart synchronous stop
- death synchronous stop
- teleport / map change invalidates stale target/path/action

### Phase-1 combat

- Target Search
- Basic Move
- Basic Attack
- 普攻走 850 原生 combat path，不直接改 HP

### Phase-2 單體主動技能

- per-skill independent cooldown
- `skills.reuseDelay` = milliseconds
- 850 native skill executor 保留 MP/HP/耗材與技能效果權威
- 技能成功送出後該 tick 結束；其餘狀態回退 Move -> Basic Attack
- Boss／菁英怪技能池與玩家自動狩獵完全分離

## 目前施工：Auto Potion / Consumable

已完成：

- `AutoHuntConsumableController`
- `AutoHuntConsumableControllerTest`
- PERCENT / ABSOLUTE threshold
- threshold boundary 使用 `<=`
- 百分比比較使用 long，避免乘法溢位
- blocked / missing item / native reject result

目前 production commit：

- `5c0de6942b80aac7c0711a050ca244d72cf2d4bc` — test regression
- `9df8a5a613e16e61525b63a3a08fbbd720cf9e7b` — threshold controller

尚未完成，不能宣告 Auto Potion CLOSED：

1. `AutoHuntRuntimeSettings` HP potion fields
2. 850 真實 HP/maxHP/inventory adapter
3. 850 native `C_ItemUse` / Potion / ItemDelay 路徑 mapping
4. `AutoHuntService` tick wiring
5. potion independent cooldown / reset semantics
6. fresh adapter + service regression
7. MP potion policy尚未實作

### Auto Potion 使用者要求

- HP/MP 抓角色真實數值
- 支援百分比門檻 / 精準數字門檻
- 喝水走 850 原生物品使用流程
- 不直接改 HP/MP
- 補水 CD 與 Skill / Move / Attack 獨立
- 此模組不混撿物、回收、溶解、技能補血

### 重要 donor 區分

L381 `Atu_supply_Timer` 是每 30 秒處理庫存補充／購買的 supply service，**不是戰鬥中依 HP 門檻喝水的 controller**。兩者不得混為一談。

## 下一步

```text
1. Recheck work/850-auto-hunting HEAD
2. Map 850 C_ItemUse / potion handler / item delay / inventory lookup
3. TDD AutoHunt850 consumable adapter
4. Extend explicit RuntimeSettings; no hidden defaults
5. Wire potion into AutoHuntService
6. Verify priority relative to Skill -> Move -> Attack
7. Stop/reset regression
8. Fresh closure suite
9. Only then set AUTO_POTION=CLOSED
```

## 關鍵施工紀錄

- `AUTO_HUNT_PHASE1_SESSION_LOG_20260926.md`
- `AUTO_HUNT_PHASE1_LIFECYCLE_HOOK_LOG_20260926.md`
- `AUTO_HUNT_PHASE1_DEATH_HOOK_LOG_20260926.md`
- `AUTO_HUNT_PHASE1_TARGET_POLICY_LOG_20260926.md`
- `AUTO_HUNT_PHASE1_BASIC_ATTACK_LOG_20260926.md`
- `AUTO_HUNT_PHASE2_SINGLE_TARGET_SKILL_LOG_20260926.md`
- `AUTO_HUNT_HANDOFF_20260926.md`

## 禁止事項

- 不另開 auto-hunt/core 支線
- 不整批 merge completed core branch
- 不照抄 381 多 timer topology
- 不直接改 HP/MP 代替原生物品／技能流程
- 不捏造 880 packet/settings schema
- 不把 Boss/菁英詞綴技能規則混進玩家自動狩獵
- 不把 pickup/recycle/dissolve/supply purchasing 混進 combat potion
- 未實際驗證不得宣告 full-project compile PASS
