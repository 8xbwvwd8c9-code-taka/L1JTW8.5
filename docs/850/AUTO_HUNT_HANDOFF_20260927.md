# L1JTW8.5 自動狩獵對話交接包 — 2026-09-27

## GOAL

繼續 `work/850-auto-hunting` 的 850 自動狩獵整合。下一優先不是再做外掛 WinForms UI，而是追 850 遊戲內原生自動狩獵按鈕／UI entry，確認它背後的 client action / packet / server dispatch，再接到既有 `AutoHuntService`。

## CURRENT BRANCH

```text
REPO=8xbwvwd8c9-code-taka/L1JTW8.5
BRANCH=work/850-auto-hunting
BASE_HEAD_BEFORE_THIS_HANDOFF=7fb5c09108a245438cc676d7e9fbf4e1e2457b40
PREVIOUS_HEAD=ea438ad36d1448c4958aad5d7097d67d817ed7b6
```

`7fb5c091...` 已更新正式首頁：

```text
docs/850/AUTO_HUNT_CONSTRUCTION_LOG.md
```

內容已記錄 2026-09-27 原生 UI 方向校正。

新對話第一件事：重新抓 `work/850-auto-hunting` HEAD；若比本交接更晚，先看新 commit 再施工。

## AUTHORITIES

```text
HOST_RUNTIME_AUTHORITY=850 repaired core
CORE_BEHAVIOR_DONOR=L381
UI_CONTROL_PROTOCOL_DONOR=L880C
850_CLASS_MAPPING_AUTHORITY=class_source_mapping.csv
FINAL_AUTO_HUNT_UI_AUTHORITY=850 native in-game client entry, once verified
```

固定原則：

> 381 負責「怎麼自動狩獵」，880 提供「UI/control/protocol donor」，850 負責「真正執行」，而 850 已存在的遊戲內按鈕／UI 優先於另造外部 UI。

## CLOSED MODULES

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
```

### HP Auto Potion 已完成行為

- PERCENT / ABSOLUTE threshold。
- threshold boundary = `<=`。
- HP = `pc.ea()`。
- max HP = `pc.ew()`。
- inventory = 850 `pc.j()`。
- HP potion material type = 23..25。
- 實際藥水行為 = `aw.d.a(pc, item)`。
- 850 item delay = `av.a.a(pc.aK(), item)`。
- delay-effect timestamp 保留。
- auto-hunt 不直接改 HP。
- potion cooldown 獨立於 skill / move / attack timing。
- action priority = Potion -> Skill -> Move -> Basic Attack。
- 成功喝水會 consume current tick；其他結果 fall through 到正常戰鬥流程。
- Stop/reset 清 potion timing。

已存在 regression：

```text
AUTO_HUNT_CONSUMABLE_CONTROLLER_TEST=PASS
AUTO_HUNT_850_CONSUMABLE_ADAPTER_TEST=PASS
SETTINGS_GREEN=PASS
AUTO_HUNT_SERVICE_POTION_WIRING_TEST=PASS
```

注意：這些只代表模組 closure，`FULL_PROJECT_COMPILE=NOT_CLAIMED`。

## 2026-09-27 RECOVERED FINDING — LAUNCHER UI IS NOT FINAL AUTO-HUNT UI

另一支線：

```text
REPO=8xbwvwd8c9-code-taka/L1JTW8.5-code-fixes
BRANCH=work/850-launcher-helper
```

確認 `launcher/850Launcher/MainForm.cs` 是額外建立的 WinForms launcher/helper UI，包含：

- 藥水
- 狀態
- 特殊
- 物品
- 熱鍵
- 定時
- developer runtime/probe tabs

其中 `BuildHotkeyTab()` 明確只保留 F1-F4 指令群組介面，等待 runtime bridge 驗證。

因此正式決策：

```text
DO_NOT_EXTEND_AUTO_HUNT_IN_LAUNCHER_WINFORMS
PREFER_850_NATIVE_IN_GAME_BUTTON_AND_UI_ENTRY
```

`work/850-launcher-helper` 仍可繼續處理 launcher/helper 的登入、memory/runtime bridge、UseItem/Skill 等研究，但不能變成第二套 auto-hunt UI authority。

## EXISTING AUTO-HUNT ARCHITECTURE — DO NOT REDESIGN

既定資料流：

```text
850 native UI / verified donor control
  ↓
850 Packet Adapter
  ↓
AutoHuntService
  ↓
AutoHuntSession
  ↓
AutoHuntScheduler
  ↓
Target / Move / Combat / Skill / Consumable / Loot / Safety / MapRule
  ↓
850 repaired core
```

核心 API 方向：

```text
startAutoHunt(pc, config)
stopAutoHunt(pc, reason)
updateAutoHuntConfig(pc, config)
getAutoHuntState(pc)
```

UI 不直接執行 AI，不直接建立 timer，不直接操作 HP/MP/target state。

## NEXT PRIORITY — 850 NATIVE BUTTON / UI TRACE

### UI-A — Find exact native entry

目標：找出使用者目前在 850 遊戲內看到的自動狩獵按鈕。

需要確認：

- button/resource identifier
- click/event entry
- UI open/close or toggle state
- 是否送 packet
- 是否呼叫 client native command/function

禁止僅由 icon/text/resource 名稱猜 handler。

### UI-B — Behavior correlation

在 authoritative 850 client 上做 ON/OFF correlation：

```text
button OFF -> ON
button ON  -> OFF
```

同步觀察：

- native send/call
- packet bytes / command id（若存在）
- UI state mutation
- server response
- repeated click behavior

### UI-C — Server dispatch

如果有 packet / command：

```text
client action
  ↓
packet/command
  ↓
PacketHandler / command dispatch
  ↓
850 Packet Adapter
```

要找 exact handler，不可捏造 opcode。

### UI-D — Adapter wiring

verified transport 只能接：

```text
AutoHuntService.start
AutoHuntService.stop
AutoHuntService.updateConfig
AutoHuntService.getState
```

不得另建第二套 session/timer/state machine。

### UI-E — Regression gate

至少驗證：

```text
1 player = max 1 AutoHuntSession
repeated ON does not duplicate scheduled task
repeated OFF is idempotent
ON -> OFF -> ON generation rejects stale callbacks
death/disconnect/restart cleanup remains intact
```

## 880 DONOR RULE

L880C 仍優先查：

- Auto Hunt UI
- 設定欄位
- 按鈕
- client packet
- client -> server flow
- config payload
- start/stop control

但只能當 donor。

```text
DO_NOT_COPY_880_ABSOLUTE_ADDRESS
DO_NOT_TRUST_880_HALF_CORE_AS_BEHAVIOR_AUTHORITY
DO_NOT_INVENT_880_PACKET_FIELDS
DO_NOT_ASSUME_880_BUTTON_EVENT_EQUALS_850_EVENT
```

若 850 原生按鈕已有自己的 transport，優先用 850；880 只補 UI/settings 語意。

## STILL OPEN

```text
850_NATIVE_AUTO_HUNT_UI_ENTRY=NOT_MAPPED
850_NATIVE_BUTTON_EVENT=NOT_MAPPED
850_NATIVE_UI_TRANSPORT=NOT_MAPPED
880_UI_SETTINGS_TRANSPORT=UNVERIFIED
MP_POTION_POLICY=NOT_STARTED
PICKUP=NOT_IN_SCOPE
RECYCLE=NOT_IN_SCOPE
DISSOLVE=NOT_IN_SCOPE
FULL_PROJECT_COMPILE=NOT_CLAIMED
```

MP potion 先不要插隊；目前先完成 native UI transport trace。

## NEXT EXECUTION ORDER

```text
1. Recheck work/850-auto-hunting HEAD.
2. Inspect any commits newer than this handoff.
3. Identify exact 850 native auto-hunt button/UI entry.
4. Correlate ON/OFF with client send/call behavior.
5. Identify exact server-side dispatch/handler if transport exists.
6. Implement/adjust only the 850 Packet Adapter boundary.
7. Wire verified command to existing AutoHuntService.
8. Add duplicate-click/session regression.
9. Only after transport is proven, map complete UI settings.
10. Keep MP potion/pickup/recycle/dissolve as separate later modules.
```

## DO NOT

- 不另開 auto-hunt/core 支線。
- 不把自動狩獵最終 UI 搬到 launcher WinForms。
- 不整批 merge repaired core。
- 不整包照搬 381 timer topology。
- 不採用 381 XML 作最終 UI。
- 不以 880 half-core 取代 381 behavior authority。
- 不捏造 opcode / packet / settings schema。
- 不直接修改 HP/MP 代替 850 原生 item/skill path。
- 不建立平行 AutoHuntSession / timer / state system。
- 未實際跑 full build 不宣告 `FULL_PROJECT_COMPILE=PASS`。

## VALIDATION BEFORE NEXT CLOSE

```text
NATIVE_BUTTON_ENTRY=PROVEN
ON_OFF_BEHAVIOR=PROVEN
TRANSPORT_OR_NATIVE_CALL=PROVEN
SERVER_HANDLER=PROVEN_OR_DOCUMENTED_AS_NOT_USED
PACKET_ADAPTER_WIRING=PROVEN
NO_DUPLICATE_SESSION=PASS
NO_DUPLICATE_TASK=PASS
STALE_CALLBACK_GATE=PASS
LIFECYCLE_CLEANUP_REGRESSION=PASS
DOCS_UPDATED=PASS
```

## A2A CONTINUATION INSTRUCTION

```text
GOAL
Continue L1JTW8.5 auto-hunt integration on work/850-auto-hunting. Prioritize tracing the existing 850 in-game auto-hunt button/UI entry and its verified transport into the existing AutoHuntService.

MUST
- Stay on work/850-auto-hunting.
- Recheck branch HEAD before any write.
- Use 850 repaired core as runtime authority.
- Use L381 as behavior donor.
- Use L880C only as UI/control/protocol donor.
- Trace the exact 850 native button/event/send/handler path before implementing transport.
- Reuse AutoHuntService/Session/Scheduler; do not create parallel state.
- Record exact source/path/method, decision, modified files, validation, and blockers.
- SUBAGENTS=0.
- NO_REPO_WIDE_SCAN.

DO NOT
- Do not create another branch.
- Do not extend auto-hunt as a second UI inside launcher WinForms.
- Do not invent opcode, packet layout, or settings fields.
- Do not copy donor absolute addresses.
- Do not bypass 850 combat/skill/item/lifecycle APIs.
- Do not mix MP potion, pickup, recycle, dissolve, or supply purchasing into the native UI transport task.
- Do not claim full-project compile PASS unless it is actually run.

VALIDATE
- Exact 850 native button/UI entry identified.
- ON/OFF behavior correlated with client send/native call.
- Exact server dispatch/handler identified when applicable.
- Verified transport reaches AutoHuntService only through the adapter boundary.
- Repeated clicks cannot create duplicate sessions/tasks.
- Existing death/disconnect/restart/teleport/map-change cleanup still passes.
- Documentation is updated on the same branch.

FINAL
Report STATUS, BRANCH, HEAD, NATIVE_UI_ENTRY, CLIENT_TRANSPORT, SERVER_HANDLER, ADAPTER_WIRING, MODIFIED_FILES, VALIDATION, BLOCKERS, NEXT.
```

## FINAL STATUS AT HANDOFF CREATION

```text
BRANCH=work/850-auto-hunting
BASE_HEAD=7fb5c09108a245438cc676d7e9fbf4e1e2457b40
CORE_AUTO_HUNT_MVP=IMPLEMENTED_THROUGH_HP_POTION
NATIVE_850_UI_TRACE=NEXT_PRIORITY
LAUNCHER_WINFORMS_AUTO_HUNT_UI=DO_NOT_EXTEND
MP_POTION_POLICY=DEFERRED
FULL_PROJECT_COMPILE=NOT_CLAIMED
NEXT=Trace 850 native auto-hunt button -> transport -> adapter -> AutoHuntService
```
