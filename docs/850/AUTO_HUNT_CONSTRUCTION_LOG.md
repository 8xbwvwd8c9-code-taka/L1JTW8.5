# L1JTW8.5 自動狩獵首頁狀態 — 2026-09-26

> 正式首頁：`docs/850/AUTO_HUNT_CONSTRUCTION_LOG.md`
> 舊版完整施工紀錄已保留在 Git history；重整前精確 blob SHA：`aea69bd87c9f6b2462bf0cfe43c41b98fba43d5d`。

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
AUTO_POTION=CLOSED
AUTO_POTION_THRESHOLD_CONTROLLER=IMPLEMENTED
AUTO_POTION_850_ITEM_ADAPTER=IMPLEMENTED
AUTO_POTION_SETTINGS=IMPLEMENTED
AUTO_POTION_SERVICE_WIRING=IMPLEMENTED
MP_POTION_POLICY=NOT_STARTED
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

### Phase-3 HP Auto Potion

已完成：

- `AutoHuntConsumableController`
  - PERCENT / ABSOLUTE threshold
  - threshold boundary 使用 `<=`
  - 百分比比較使用 `long`
  - `BLOCKED / COOLDOWN / NO_ITEM / CONSUMED / REJECTED`
- `AutoHunt850ConsumableAdapter`
  - HP 讀 `pc.ea()`
  - max HP 讀 `pc.ew()`
  - inventory 讀 850 `pc.j()` 真實背包
  - HP potion material type 僅 23..25
  - 保留 C_ItemUSe 的角色 gate、藥水禁止狀態、level gate、item delay / delay-effect gate
  - 實際喝水呼叫 850 `aw.d.a(pc, item)`
  - delay-effect 成功使用後更新 item timestamp
  - 呼叫 850 `av.a.a(pc.aK(), item)` 掛 native item delay
  - auto-hunt 不直接修改 HP
- `AutoHuntRuntimeSettings`
  - `autoHpPotionOn`
  - `hpPotionMode`
  - `hpPotionThreshold`
  - `hpPotionItemId`
  - `hpPotionCooldownMs`
  - 舊 constructor 明確代表 HP potion disabled
- `AutoHuntService`
  - action priority：HP Potion -> Skill -> Move -> Basic Attack
  - 只有 `CONSUMED` 會吃掉當前 tick
  - potion cooldown 與 Skill / Move / Attack timing 獨立
  - Stop / reset 清 potion timing state

TDD / regression commits：

- `242d7be9cb0b303696fceb7bb38ed286359cfdec` — controller cooldown RED
- `0d073e73b83dcd98846f0dd16f485b359855eb99` — controller cooldown GREEN
- `5f133ed5f68a272344cbee85ee43e7d4e0d38c10` — adapter RED
- `9b90c16625b070fca10d1cb112e67520e6fc2a05` — adapter GREEN
- `56c8abb7a3b77b5e54d52919b0098829bdb9fe45` — settings RED
- `ee5b2b87ea13902bb33153aa0069272c3177e085` — settings GREEN
- `d5a42dfa74af2f9cf8823c9f9c147f4e5deaf118` — service wiring RED
- `5306065444b791459f6f1420becba7fae88855fc` — service wiring GREEN

Fresh closure verification：

```text
AUTO_HUNT_CONSUMABLE_CONTROLLER_TEST=PASS
AUTO_HUNT_850_CONSUMABLE_ADAPTER_TEST=PASS
SETTINGS_GREEN=PASS
AUTO_HUNT_SERVICE_POTION_WIRING_TEST=PASS
```

注意：上述為 HP Auto Potion 模組 closure regression；**沒有宣告 full-project compile PASS**。

### Auto Potion 使用者要求

- HP/MP 抓角色真實數值
- 支援百分比門檻 / 精準數字門檻
- 喝水走 850 原生物品使用流程
- 不直接改 HP/MP
- 補水 CD 與 Skill / Move / Attack 獨立
- 此模組不混撿物、回收、溶解、技能補血

HP potion 已完成；MP potion policy 仍獨立保留為未開始，不因 HP closure 自動視為完成。

### 重要 donor 區分

L381 `Atu_supply_Timer` 是每 30 秒處理庫存補充／購買的 supply service，**不是戰鬥中依 HP 門檻喝水的 controller**。兩者不得混為一談。

## 下一步

```text
1. MP potion policy（獨立設計；不得直接複製 HP 規則）
2. 880 UI/settings transport mapping，僅在有真實 donor schema 後接線
3. 後續 pickup / recycle / dissolve 仍保持獨立模組
4. full-project compile 只有在實際執行後才能宣告 PASS
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
