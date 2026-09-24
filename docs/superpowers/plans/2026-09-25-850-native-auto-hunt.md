# 850 Native Auto-Hunt Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 在不覆蓋、不照抄 880 核心的前提下，以 850 原生服務、生命週期及執行緒建立可驗證的玩家自動狩獵系統，並交付獨立遷移套件。

**Architecture:** 新增 `l1r.auto` 作為可獨立測試的規則、狀態機與控制器邊界；850 的 `L1PcInstance`、地圖、封包、道具及執行緒類別只加入薄型委派。880 僅保存為證據，所有執行碼依 850 實際 API 重新撰寫。

**Tech Stack:** Java 8、850 normalized source、Python 3 `unittest`、MySQL SQL、既有 `GeneralThreadPool`、既有 production-rebuild 工具。

**Spec:** `migration/381-to-850/packages/自動狩獵系統/項目說明.md`

## Global Constraints

- 850 是唯一的核心控制基準；880 只提供缺口、規則與資料證據。
- 不得複製 880 方法、不得用 880 類別覆蓋 850 類別、不得改造 850 以配合 880 架構。
- `850匯入/核心/` 只放本項目重新實作或實際修改的 Java/class。
- 新增程式必須支援 Java 8，不新增第三方依賴。
- 自動狩獵未啟用時，角色、NPC、寵物、召喚物、戰鬥、施法、移動與傳送流程保持原狀。
- DB 預設禁止掛機，只有明確設定 `mapids.autobot=1` 的地圖允許。
- 執行中狀態不持久化；重新登入或重啟後一律為停止。
- 所有生產碼遵守先紅、後綠、再重構的 TDD 循環。
- 工作樹中的既有非本項目變更不得加入任何本項目提交。

## Review Focus

- 同一角色收到並行啟動命令時只能建立一個 session；Task 3 的競態測試必須鎖定。
- 執行期間由允許地圖切換到禁止地圖時必須先停止再做下一次動作；Task 4、Task 5 的測試必須鎖定。
- 跨午夜時段（例如 23:00–18:00）與起訖相同的停用設定不能混淆；Task 2 的邊界測試必須鎖定。
- 掛機符到期、登出與人工停止同時發生時只能清理及通知一次；Task 3、Task 7 的冪等測試必須鎖定。
- 非法技能 ID、負範圍、零間隔及超大數值必須在 action 邊界拒絕；Task 6、Task 7 的輸入測試必須鎖定。

---

### Task 1: 建立獨立套件骨架與來源證據清單

**Files:**
- Create: `migration/381-to-850/packages/自動狩獵系統/manifest.json`
- Create: `migration/381-to-850/packages/自動狩獵系統/880來源證據/來源清單.md`
- Create: `migration/381-to-850/packages/自動狩獵系統/驗證/package_contract.json`
- Modify: `migration/381-to-850/packages/tests/test_validate_module_package.py`

**Interfaces:**
- Consumes: 現有 `validate_module_package.py` 套件驗證規則。
- Produces: `module_id=自動狩獵系統` 的 manifest、固定交付目錄及 880→850 對照清單。

- [ ] **Step 1: 寫入失敗的套件契約測試**

在 `test_validate_module_package.py` 增加測試，建立本項目路徑後斷言 `manifest.json` 存在，且 `imports` 只允許 `java/class/db/control/client`；斷言 client 清單不得包含 `china_autohelper_server-common.bin`。

```python
def test_auto_hunt_package_has_native_850_contract(self):
    manifest = load_manifest(PACKAGES / "自動狩獵系統")
    self.assertEqual("自動狩獵系統", manifest["module_id"])
    self.assertEqual("850_NATIVE_REIMPLEMENTATION", manifest["strategy"])
    self.assertNotIn("china_autohelper_server-common.bin", manifest["imports"]["client"])
```

- [ ] **Step 2: 執行測試並確認因 manifest 不存在而失敗**

Run: `python -m unittest migration.381-to-850.packages.tests.test_validate_module_package -v`

Expected: FAIL，原因為 `自動狩獵系統/manifest.json` 不存在或缺少 `strategy`。

- [ ] **Step 3: 建立最小 manifest 與來源清單**

`manifest.json` 明確記錄：880 `PcAI.java`、`L1PcInstance.java`、`L1ActionPc.java`、`Hang_fu.java`、`L1EquipmentTimer.java`、`mapids.sql`；850 對照為 `l1r.auto` 新元件及薄型整合檔。所有 import 陣列起始為空，狀態為 `PLANNED`。

- [ ] **Step 4: 建立目錄並執行套件測試**

建立 `880來源證據/{核心,DB,客戶端}`、`850匯入/{核心,DB,客戶端}`、`驗證`，再執行同一測試。

Expected: PASS；一般套件驗證也維持 PASS。

- [ ] **Step 5: 提交套件骨架**

```powershell
git add -- 'migration/381-to-850/packages/自動狩獵系統' 'migration/381-to-850/packages/tests/test_validate_module_package.py'
git commit -m "test(migration): define auto-hunt package contract"
```

### Task 2: 實作純規則設定與時段政策

**Files:**
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntSettings.java`
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntPolicy.java`
- Create: `tools/migration/tests/test_auto_hunt_policy.py`

**Interfaces:**
- Produces: `AutoHuntSettings.create(int range, int intervalSeconds, int minMp, int openerSkillId, int attackSkillId, boolean polite, boolean noTargetTeleport, boolean bossTeleport)`。
- Produces: `AutoHuntPolicy.isHourAllowed(int startHour, int endHour, int currentHour)` 與 `validateSettings(AutoHuntSettings)`。

- [ ] **Step 1: 寫入失敗的政策測試**

測試以 `javac -source 8 -target 8` 編譯真實 Java 類別及小型 harness，覆蓋：正常時段、跨午夜、起訖相同為停用、0/23 邊界、負範圍、零間隔、MP 超出角色上限、負技能 ID。

```java
assertTrue(AutoHuntPolicy.isHourAllowed(23, 18, 23));
assertTrue(AutoHuntPolicy.isHourAllowed(23, 18, 0));
assertFalse(AutoHuntPolicy.isHourAllowed(23, 18, 18));
assertFalse(AutoHuntPolicy.isHourAllowed(8, 8, 8));
expectIllegalArgument(() -> AutoHuntSettings.create(-1, 2, 20, 0, 0, false, false, false));
```

- [ ] **Step 2: 執行並確認類別不存在造成 RED**

Run: `python -m unittest tools.migration.tests.test_auto_hunt_policy -v`

Expected: FAIL，`javac` 回報 `package l1r.auto does not exist`。

- [ ] **Step 3: 實作不可變設定與時段政策**

範圍限制 `0..200`、間隔 `1..60` 秒、最低 MP `0..100000`、技能 ID `0..Integer.MAX_VALUE`。時段採 `[start,end)`；`start==end` 回傳 false；任一小時不在 `0..23` 時丟出 `IllegalArgumentException`。

- [ ] **Step 4: 執行政策測試與 normalized 編譯診斷**

Run: `python -m unittest tools.migration.tests.test_auto_hunt_policy -v`

Run: `python tools/normalized-recovery/compile-normalized-core.py`

Expected: 政策測試全部 PASS；normalized 編譯沒有新增以 `l1r/auto/AutoHuntSettings.java` 或 `AutoHuntPolicy.java` 開頭的錯誤。

- [ ] **Step 5: 提交規則層**

```powershell
git add -- recovery/normalized-src-vf/l1r/auto tools/migration/tests/test_auto_hunt_policy.py
git commit -m "feat(core): add native 850 auto-hunt policy"
```

### Task 3: 實作 session 狀態機與冪等生命週期

**Files:**
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntStopReason.java`
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntActor.java`
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntSession.java`
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntController.java`
- Create: `tools/migration/tests/test_auto_hunt_session.py`

**Interfaces:**
- `AutoHuntActor`: `isOnline()`, `isDead()`, `isDisabled()`, `isMapAllowed()`, `hasEntitlement()`, `isHourAllowed()`, `performOneCycle()`, `clearAutoHuntState()`, `onAutoHuntStopped(AutoHuntStopReason)`。
- `AutoHuntController.start(AutoHuntActor, AutoHuntSettings)` 回傳 boolean；`stop(AutoHuntStopReason)`；`run()`。
- `AutoHuntStopReason`: `MANUAL, LOGOUT, DEAD, DISABLED, MAP_FORBIDDEN, ENTITLEMENT_EXPIRED, TIME_FORBIDDEN, ERROR`。

- [ ] **Step 1: 寫入失敗的生命週期測試**

建立真實 fake actor，測試正常執行一輪、每種停止條件、連續兩次 stop 只清理通知一次，以及 16 個執行緒同時 start 只有一次成功。

```java
AtomicInteger starts = new AtomicInteger();
parallel(16, () -> { if (controller.start(actor, settings)) starts.incrementAndGet(); });
assertEquals(1, starts.get());
controller.stop(AutoHuntStopReason.LOGOUT);
controller.stop(AutoHuntStopReason.MANUAL);
assertEquals(1, actor.clearCount);
assertEquals(1, actor.stopNoticeCount);
```

- [ ] **Step 2: 執行並確認缺少狀態機造成 RED**

Run: `python -m unittest tools.migration.tests.test_auto_hunt_session -v`

Expected: FAIL，缺少 `AutoHuntController` 等類別。

- [ ] **Step 3: 以原子狀態實作控制器**

使用 `AtomicReference<AutoHuntSession>` 管理單一 session。`start` 以 compare-and-set 建立；`stop` 先原子移除 session，再執行一次清理與通知。`run` 每輪依固定優先序驗證 online、dead、disabled、map、entitlement、hour，最後才呼叫 `performOneCycle()`；任何 Throwable 轉成 `ERROR` 停止並保留記錄入口。

- [ ] **Step 4: 執行生命週期與政策測試**

Run: `python -m unittest tools.migration.tests.test_auto_hunt_session tools.migration.tests.test_auto_hunt_policy -v`

Expected: 全部 PASS，並行啟動值固定為 1。

- [ ] **Step 5: 提交狀態機**

```powershell
git add -- recovery/normalized-src-vf/l1r/auto tools/migration/tests/test_auto_hunt_session.py
git commit -m "feat(core): add idempotent auto-hunt lifecycle"
```

### Task 4: 加入 850 原生地圖掛機權限

**Files:**
- Create: `db/migrations/20260925_auto_hunt.sql`
- Create: `db/migrations/20260925_auto_hunt_rollback.sql`
- Modify: `recovery/normalized-src-vf/l1r/ax/L1Map.java`
- Modify: `recovery/normalized-src-vf/l1r/ao/MapsTable.java`
- Create: `tools/migration/tests/test_auto_hunt_map_gate.py`

**Interfaces:**
- Produces: `L1Map.isAutoHuntAllowed()`，預設 false。
- `MapsTable` 從 `mapids.autobot` 載入權限。

- [ ] **Step 1: 寫入失敗的 schema 與 Java 契約測試**

測試斷言 install SQL 使用 `INFORMATION_SCHEMA.COLUMNS` 保證可重跑，新增 `autobot TINYINT(1) NOT NULL DEFAULT 0`；rollback 只移除本欄位。Java harness 建立 `new L1Map()` 後斷言預設 false，再設定 true 後讀回 true。

- [ ] **Step 2: 執行並確認 SQL、方法缺失造成 RED**

Run: `python -m unittest tools.migration.tests.test_auto_hunt_map_gate -v`

Expected: FAIL，缺少 migration 檔與 `isAutoHuntAllowed()`。

- [ ] **Step 3: 實作 DB 與地圖欄位傳遞**

在 `L1Map` 新增 private boolean 與命名 getter/setter；`MapsTable.b()` 讀取 `autobot`。不得重用既有混淆欄位 `i..t`，避免改變其他地圖旗標語意。

- [ ] **Step 4: 執行地圖測試及 normalized 編譯診斷**

Run: `python -m unittest tools.migration.tests.test_auto_hunt_map_gate -v`

Run: `python tools/normalized-recovery/compile-normalized-core.py`

Expected: 測試 PASS；兩個修改檔沒有新增編譯錯誤。

- [ ] **Step 5: 提交地圖權限**

```powershell
git add -- db/migrations/20260925_auto_hunt.sql db/migrations/20260925_auto_hunt_rollback.sql recovery/normalized-src-vf/l1r/ax/L1Map.java recovery/normalized-src-vf/l1r/ao/MapsTable.java tools/migration/tests/test_auto_hunt_map_gate.py
git commit -m "feat(db): gate auto-hunt by 850 map policy"
```

### Task 5: 將控制器接入 850 玩家與執行緒池

**Files:**
- Create: `recovery/normalized-src-vf/l1r/auto/L1PcAutoHuntActor.java`
- Modify: `recovery/normalized-src-vf/l1r/ap/L1PcInstance.java`
- Modify: `recovery/normalized-src-vf/l1r/bi/GeneralThreadPool.java`
- Create: `tools/migration/tests/test_auto_hunt_player_integration.py`

**Interfaces:**
- `L1PcInstance.startAutoHunt(AutoHuntSettings)`、`stopAutoHunt(AutoHuntStopReason)`、`isAutoHunting()`。
- `GeneralThreadPool.schedulePlayerTask(Runnable,long)` 回傳 `ScheduledFuture<?>`，內部委派既有玩家 scheduled pool。
- `L1PcAutoHuntActor` 是唯一可直接操作 `L1PcInstance` 的自動狩獵 adapter。

- [ ] **Step 1: 寫入失敗的薄型整合契約測試**

靜態 AST/文字契約斷言 `L1PcInstance` 只持有 `AutoHuntController` 並提供三個委派方法，不得含 `searchTarget`、`noTarget`、`onTarget` 或從 880 複製的 AI 迴圈。Java harness 測試 controller 使用 injected scheduler 時只排程一次，stop 後 future 被取消。

- [ ] **Step 2: 執行並確認整合方法不存在造成 RED**

Run: `python -m unittest tools.migration.tests.test_auto_hunt_player_integration -v`

Expected: FAIL，找不到委派方法及 adapter。

- [ ] **Step 3: 實作 adapter 與薄型玩家委派**

`L1PcAutoHuntActor` 將 online/dead/disabled/map 判定映射到 850 現有 API；找不到可證明語意的方法時先以最保守 false/停止處理，不以方法名猜測。玩家類別不放尋怪或攻擊演算法。排程統一透過 `GeneralThreadPool` 玩家池。

- [ ] **Step 4: 執行整合測試及差異編譯**

Run: `python -m unittest tools.migration.tests.test_auto_hunt_player_integration -v`

Run: `python tools/normalized-recovery/compile-normalized-core.py`

Expected: normalized 編譯結果沒有新增以本任務 Java 路徑開頭的 javac error signature。

- [ ] **Step 5: 提交玩家整合**

```powershell
git add -- recovery/normalized-src-vf/l1r/auto/L1PcAutoHuntActor.java recovery/normalized-src-vf/l1r/ap/L1PcInstance.java recovery/normalized-src-vf/l1r/bi/GeneralThreadPool.java tools/migration/tests/test_auto_hunt_player_integration.py
git commit -m "feat(core): integrate auto-hunt with 850 player lifecycle"
```

### Task 6: 以 850 戰鬥與技能服務完成單輪狩獵

**Files:**
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntTargetSelector.java`
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntTarget.java`
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntTargetContext.java`
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntCombat.java`
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntCombatContext.java`
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntAction.java`
- Modify: `recovery/normalized-src-vf/l1r/auto/L1PcAutoHuntActor.java`
- Create: `tools/migration/tests/test_auto_hunt_combat.py`

**Interfaces:**
- `AutoHuntTarget` 提供 `isMonster()`, `isAlive()`, `isVisible()`, `distance()`, `isEngagedByOtherPlayer()`。
- `AutoHuntTargetContext.candidates()` 回傳當前 850 世界物件轉接後的候選集合。
- `AutoHuntTargetSelector.select(AutoHuntTargetContext, AutoHuntSettings)` 只回傳合法、存活、可見、位於範圍內的怪物。
- `AutoHuntCombatContext` 提供技能合法性、路徑、移動、普通攻擊及施法委派。
- `AutoHuntCombat.act(AutoHuntCombatContext, AutoHuntSettings)` 回傳 `AutoHuntAction`：`MOVED, ATTACKED, CAST, CLEARED_TARGET, IDLE`。
- 技能消耗委派 850 技能執行服務，不自行修改 HP/MP；測試另外鎖定若 adapter 必須扣除消耗，HP 與 MP 分開寫入。

- [ ] **Step 1: 寫入失敗的目標及戰鬥測試**

涵蓋死亡、隱身、玩家、超距、已被他人攻擊的禮貌模式目標；涵蓋未學技能、非法 ID、MP 不足、冷卻、路徑失敗、近距普通攻擊。加入 880 缺陷回歸：HP 消耗不得呼叫 MP setter。

```java
assertEquals(IDLE, combat.act(context.withSkillId(-1), settings));
assertEquals(CLEARED_TARGET, combat.act(context.withPath(false), settings));
assertEquals(90, context.currentHpAfterCost(10));
assertEquals(40, context.currentMpAfterCost(10));
```

- [ ] **Step 2: 執行並確認戰鬥元件缺失造成 RED**

Run: `python -m unittest tools.migration.tests.test_auto_hunt_combat -v`

Expected: FAIL，缺少 selector/combat 類別。

- [ ] **Step 3: 以 850 服務實作最小單輪動作**

先從 850 世界物件集合篩選怪物，再呼叫既有可走判定、移動、普通攻擊與技能執行入口。沒有目標時只執行設定允許的受控行為；找不到路徑立即清除目標。不得呼叫 NPC、寵物或召喚物 AI。

- [ ] **Step 4: 執行戰鬥、狀態機及增量編譯測試**

Run: `python -m unittest tools.migration.tests.test_auto_hunt_combat tools.migration.tests.test_auto_hunt_session -v`

Expected: 全部 PASS；增量編譯無本任務新增錯誤。

- [ ] **Step 5: 提交戰鬥元件**

```powershell
git add -- recovery/normalized-src-vf/l1r/auto tools/migration/tests/test_auto_hunt_combat.py
git commit -m "feat(core): add 850-native auto-hunt combat cycle"
```

### Task 7: 加入掛機符、設定 action、授權時間與停止掛鉤

**Files:**
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntActionRouter.java`
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntEntitlement.java`
- Create: `recovery/normalized-src-vf/l1r/auto/AutoHuntRepository.java`
- Modify: `recovery/normalized-src-vf/l1r/aj/C_ItemUSe.java`
- Modify: `recovery/normalized-src-vf/l1r/aj/C_NpcAction.java`
- Modify: `recovery/normalized-src-vf/l1r/aj/C_Disconnect.java`
- Modify: `recovery/normalized-src-vf/l1r/aj/C_Restart.java`
- Modify: `recovery/normalized-src-vf/l1r/l1j/server/Config.java`
- Create: `db/migrations/20260925_auto_hunt_items.sql`
- Create: `db/migrations/20260925_auto_hunt_items_rollback.sql`
- Create: `config/auto-hunt.properties.example`
- Create: `tools/migration/tests/test_auto_hunt_actions.py`

**Interfaces:**
- `AutoHuntActionRouter.handles(String)` 與 `handle(L1PcInstance,String)`。
- action 白名單：`autohunt-open/start/stop`, `autohunt-range-*`, `autohunt-opener-*`, `autohunt-attack-*`, `autohunt-interval-*`, `autohunt-minmp-*`, `autohunt-polite`, `autohunt-no-target-teleport`, `autohunt-boss-teleport`。
- `AutoHuntEntitlement.tick(boolean running)` 僅在 running 時扣一秒並只觸發一次到期事件。
- `AutoHuntRepository.load(int characterId)` 與 `save(int characterId, AutoHuntSettings, int remainingSeconds)` 保存設定與剩餘時間，不保存執行中狀態。
- Config 欄位：`AutoHuntEnabled`, `AutoHuntStartHour`, `AutoHuntEndHour`, `AutoHuntItemId`。

- [ ] **Step 1: 寫入失敗的 action、授權與 config 測試**

測試 action 白名單、非法數值、未學技能、負值/溢位、停用總開關；測試 100 次並行到期 tick 只觸發一次；測試 item SQL 對 850 靜態 DB 的 641359 無衝突檢查，並在執行 DB 已存在不同資料時 `SIGNAL SQLSTATE '45000'` 停止。測試 disconnect/restart 路徑明確呼叫 `stopAutoHunt(LOGOUT)`。

- [ ] **Step 2: 執行並確認 router/entitlement 不存在造成 RED**

Run: `python -m unittest tools.migration.tests.test_auto_hunt_actions -v`

Expected: FAIL，缺少 router/entitlement 與 config 欄位。

- [ ] **Step 3: 實作服務端驗證及 UI 封包入口**

掛機符預設 ID 為 641359；migration 只有在 ID 未使用或現有資料與本項目定義完全一致時才能繼續。`C_ItemUSe` 依 `AutoHuntItemId` 開啟 `S_Html`。`C_NpcAction` 在一般 NPC 分派前只攔截 `autohunt-` 前綴，再交給 router；router 對技能學習、範圍、間隔及 MP 條件做服務端驗證。設定頁名稱固定 `guajiqd`。新增 `character_auto_hunt` 表，以 `char_id` 為主鍵保存設定與剩餘秒數，不保存 running 欄位。

- [ ] **Step 4: 實作時間扣除與生命週期停止**

授權扣時使用 850 玩家 scheduled pool；未執行時不扣時；到期呼叫 `stopAutoHunt(ENTITLEMENT_EXPIRED)`。斷線與重新開始角色流程先呼叫冪等 stop。Config 從 850 `ConfigTable` 讀取四個明確鍵，非法小時使 `AutoHuntEnabled=false` 並記錄錯誤。

- [ ] **Step 5: 執行 action、生命週期、政策與增量編譯測試**

Run: `python -m unittest tools.migration.tests.test_auto_hunt_actions tools.migration.tests.test_auto_hunt_session tools.migration.tests.test_auto_hunt_policy -v`

Expected: 全部 PASS；修改類別無新增 javac error signature。

- [ ] **Step 6: 提交入口與授權**

```powershell
git add -- recovery/normalized-src-vf/l1r/auto recovery/normalized-src-vf/l1r/aj/C_ItemUSe.java recovery/normalized-src-vf/l1r/aj/C_NpcAction.java recovery/normalized-src-vf/l1r/aj/C_Disconnect.java recovery/normalized-src-vf/l1r/aj/C_Restart.java recovery/normalized-src-vf/l1r/l1j/server/Config.java db/migrations/20260925_auto_hunt_items.sql db/migrations/20260925_auto_hunt_items_rollback.sql config/auto-hunt.properties.example tools/migration/tests/test_auto_hunt_actions.py
git commit -m "feat(core): add validated auto-hunt controls and entitlement"
```

### Task 8: 組裝獨立交付包、驗證客戶端缺口並更新首頁

**Files:**
- Modify: `migration/381-to-850/packages/自動狩獵系統/manifest.json`
- Modify: `migration/381-to-850/packages/自動狩獵系統/項目說明.md`
- Create: `migration/381-to-850/packages/自動狩獵系統/驗證/驗證報告.md`
- Create: `migration/381-to-850/packages/自動狩獵系統/驗證/client_hashes.sha256`
- Modify: `README.md`
- Modify: `migration/381-to-850/packages/tests/test_validate_module_package.py`

**Interfaces:**
- Produces: 可稽核套件，manifest 每個來源檔、850 目標檔、SHA-256、驗證命令與結果均完整。
- Produces: 首頁的問題、原因、解決方式、驗證結果及項目連結。

- [ ] **Step 1: 寫入失敗的完成態套件測試**

測試要求 manifest `decision=READY` 時：每個 import 路徑存在、hash 相符、`850匯入/核心` 只有 `.java/.class`、相同 autohelper bin 未被複製、驗證報告列出所有測試與編譯結果、README 含本項目連結。

- [ ] **Step 2: 執行並確認交付檔尚未齊全造成 RED**

Run: `python -m unittest migration.381-to-850.packages.tests.test_validate_module_package -v`

Expected: FAIL，缺少完成態 imports、hash、驗證報告或 README 連結。

- [ ] **Step 3: 複製本項目檔案並完成 manifest**

只將 Tasks 2–7 實際新增/修改的 Java 放進 `850匯入/核心`，SQL 放進 `850匯入/DB`。`880來源證據` 只保存來源副本與清單，不作 850 執行碼。每個檔案計算 SHA-256 並寫入 manifest。

- [ ] **Step 4: 驗證客戶端素材**

記錄兩個 `china_autohelper_server-common.bin` 的 SHA-256；使用 850 實際客戶端啟動整合驗證 `guajiqd`。若畫面可顯示，client imports 維持空；若明確缺少 `guajiqd`，只從 880 抽取該單一 HTML/文字資源並記錄來源及 hash，不複製共同 autohelper bin。

- [ ] **Step 5: 執行完整測試與建置證據**

Run: `python -m unittest discover -s tools/migration/tests -p 'test_auto_hunt_*.py' -v`

Run: `python -m unittest discover -s migration/381-to-850/packages/tests -p 'test_*.py' -v`

Run: `python tools/normalized-recovery/compile-normalized-core.py`

Run: `powershell -ExecutionPolicy Bypass -File tools/production-rebuild/build-production-test.ps1`

Expected: 自動狩獵與套件測試 0 failures；normalized 診斷沒有新增本項目錯誤；production test build 符合既有 baseline gate。將命令、時間、exit code、通過數與既有基線錯誤寫入驗證報告。

- [ ] **Step 6: 更新首頁及完成態文件**

README 說明：850 缺少服務端自動狩獵核心、客戶端共同 bin 並非缺口、採 850 原生重構、修正 880 HP/MP 與無效時段設定、列出驗證結果並連結 `項目說明.md`。

- [ ] **Step 7: 提交完整獨立套件**

```powershell
git add -- 'migration/381-to-850/packages/自動狩獵系統' 'migration/381-to-850/packages/tests/test_validate_module_package.py' README.md
git commit -m "docs(migration): package verified 850 auto-hunt system"
```

### Task 9: 最終需求稽核與支線交付

**Files:**
- Verify only: `migration/381-to-850/packages/自動狩獵系統/`
- Verify only: 本計畫 Tasks 2–7 列出的 850 核心、DB、config 與測試檔。

**Interfaces:**
- Produces: 可供另一個對話執行 A2A 驗證的 commit 清單、測試命令、已知限制及驗證入口。

- [ ] **Step 1: 逐條核對規格完成條件**

依 `項目說明.md` 第 13 節建立勾稽表；每一條必須對應檔案或新鮮命令輸出，不以提交存在代替驗證。

- [ ] **Step 2: 確認提交範圍未混入其他工作**

Run: `git log --oneline --name-status --regexp-ignore-case --grep='auto[- ]hunt'`

Expected: 只出現計畫列出的自動狩獵、套件、測試與 README 檔案；任何其他檔案都先停止交付並移出本項目提交。

- [ ] **Step 3: 產生 A2A 驗證交接內容**

交接內容必須包含：規格連結、計畫連結、commit SHA 清單、完整驗證命令、實際結果、850/880 邊界、DB install/rollback、客戶端判定與未完成限制。不得宣稱未執行的客戶端人工測試通過。

- [ ] **Step 4: 移入使用者指定的 BUG 修補完成支線**

先讀取實際遠端支線清單，精確確認完成支線名稱，再以非破壞性的 merge/cherry-pick 將本項目提交移入。不得猜測支線名稱，不得使用 `reset --hard` 或覆蓋遠端歷史。

- [ ] **Step 5: 在完成支線重跑最終驗證**

重跑 Task 8 Step 5 全部命令；只有新鮮結果符合預期後，才在 A2A 交接中標記可驗證。
