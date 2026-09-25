# Global Drop, Designated Item Status, and Equipment Total Bonus Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 在 850 核心完成獨立百分比全地圖掉落、指定道具能力狀態，以及可設定總強化／總過安定門檻的裝備總加成。

**Architecture:** 建立無 DB 相依的規則與共用可逆能力向量核心，再由三個 850 table/service adapter 接到既有 `DropTable`、`C_ItemUSe`、`L1EquipmentSlot` 與登入裝備還原流程。指定道具和裝備總加成共享能力向量及 owner 差異更新，但不共享生命週期；全地圖掉落完全獨立且不讀任何掉落倍率。

**Tech Stack:** Java 8、JDBC/MySQL、既有 850 normalized source、Python `unittest` package validator、`javac` 自執行 Java contract tests。

**Spec:** `docs/superpowers/specs/2026-09-25-global-drop-item-status-equipment-bonus-design.md`

## Global Constraints

- 850 核心、裝備生命週期、能力 API、封包與持久化是唯一權威。
- 全地圖掉落 `chance` 是 0～100 的整數百分比，預設 1；不受任何核心倍率影響。
- 指定道具第一版只啟用已證實安全的伺服器能力；未驗證客戶端效果不執行。
- 裝備總加成同時支援 `TOTAL_ENCHANT`、`OVER_SAFE_ENCHANT`、`HIGHEST`、`CUMULATIVE`。
- 三個模組預設關閉；缺表或壞資料只停用對應模組，不得阻止伺服器啟動。
- 保留工作樹中既有修改；每次提交只加入本計畫列出的檔案。

## Review Focus

- `chance=0/1/100` 的邊界與亂數 off-by-one：0 永不掉，100 必掉，1 精確代表 1%。
- 同一角色重複登入還原、重複穿戴或重複卸除：owner 集合保持冪等，不重複加減能力。
- 設定重載後移除舊 owner：必須使用原套用快照反向移除，不能用新 DB 值扣除。
- 同一裝備規則 group 混用 metric 或 stack mode：整組拒絕載入，其他 group 繼續運作。
- max HP／MP 加成移除：當前值限制在新上限，角色不得保留超額 HP／MP。

---

### Task 1: 建立可獨立測試的規則核心與測試執行器

**Files:**
- Create: `recovery/normalized-src-vf/l1r/ao/GlobalDropRule.java`
- Create: `recovery/normalized-src-vf/l1r/ao/EquipmentTotalBonusRule.java`
- Create: `recovery/normalized-src-vf/l1r/ao/EquipmentTotalBonusCalculator.java`
- Create: `tools/migration/java-tests/l1r/ao/FeatureRuleContractTest.java`
- Create: `tools/migration/run-850-feature-contract-tests.ps1`

**Interfaces:**
- Produces: `GlobalDropRule(int id, int itemId, int chance)`, `boolean shouldDrop(int roll)`。
- Produces: `EquipmentTotalBonusRule.Metric`, `EquipmentTotalBonusRule.StackMode` 與不可變 rule value object。
- Produces: `EquipmentTotalBonusCalculator.totalEnchant(List<EquipmentValue>)`、`overSafeEnchant(...)`、`selectRules(...)`。

- [ ] **Step 1: 寫規則核心的失敗 contract tests**

```java
public static void main(String[] args) {
    assertFalse(new GlobalDropRule(1, 40010, 0).shouldDrop(1));
    assertTrue(new GlobalDropRule(2, 40010, 1).shouldDrop(1));
    assertFalse(new GlobalDropRule(2, 40010, 1).shouldDrop(2));
    assertTrue(new GlobalDropRule(3, 40010, 100).shouldDrop(100));

    List<EquipmentValue> gear = Arrays.asList(
        new EquipmentValue(7, 6), new EquipmentValue(10, 4));
    assertEquals(17, EquipmentTotalBonusCalculator.totalEnchant(gear));
    assertEquals(7, EquipmentTotalBonusCalculator.overSafeEnchant(gear));

    assertRuleIds(Arrays.asList(30), select("A", HIGHEST, 35));
    assertRuleIds(Arrays.asList(10, 20, 30), select("B", CUMULATIVE, 35));
}
```

- [ ] **Step 2: 執行測試並確認 RED**

Run: `pwsh -File tools/migration/run-850-feature-contract-tests.ps1`

Expected: FAIL，`GlobalDropRule`、`EquipmentTotalBonusRule` 與 calculator 尚不存在。

- [ ] **Step 3: 實作最小純規則類別**

```java
public boolean shouldDrop(int roll) {
    return this.chance > 0 && roll >= 1 && roll <= 100 && roll <= this.chance;
}

public static int overSafeEnchant(List<EquipmentValue> items) {
    int total = 0;
    for (EquipmentValue item : items) {
        total += Math.max(0, item.enchantLevel - item.safeEnchantLevel);
    }
    return total;
}
```

`selectRules` 必須先依 `groupId` 分組；HIGHEST 每組只回傳達標且 threshold 最大的 rule，CUMULATIVE 回傳該組全部達標 rule。所有輸入 collection 回傳不可變結果。

- [ ] **Step 4: 執行測試並確認 GREEN**

Run: `pwsh -File tools/migration/run-850-feature-contract-tests.ps1`

Expected: PASS，輸出 `FeatureRuleContractTest: PASS`。

- [ ] **Step 5: 提交規則核心**

```powershell
git add -- recovery/normalized-src-vf/l1r/ao/GlobalDropRule.java recovery/normalized-src-vf/l1r/ao/EquipmentTotalBonusRule.java recovery/normalized-src-vf/l1r/ao/EquipmentTotalBonusCalculator.java tools/migration/java-tests/l1r/ao/FeatureRuleContractTest.java tools/migration/run-850-feature-contract-tests.ps1
git commit -m "feat(core): add drop and equipment rule calculators"
```

### Task 2: 建立共用能力向量與 owner 差異更新

**Files:**
- Create: `recovery/normalized-src-vf/l1r/aq/StatModifier.java`
- Create: `recovery/normalized-src-vf/l1r/aq/StatModifierTarget.java`
- Create: `recovery/normalized-src-vf/l1r/aq/StatModifierOwners.java`
- Create: `recovery/normalized-src-vf/l1r/aq/L1PcStatModifierTarget.java`
- Modify: `tools/migration/java-tests/l1r/ao/FeatureRuleContractTest.java`

**Interfaces:**
- Produces: immutable `StatModifier` with builder and supported 850 stat fields。
- Produces: `StatModifierTarget` delta methods matching the proven `L1PcInstance` APIs。
- Produces: `StatModifierOwners.reconcile(Map<String, StatModifier> desired, StatModifierTarget target)`。
- Produces: `L1PcStatModifierTarget(L1PcInstance pc)` adapter。

- [ ] **Step 1: 新增失敗測試，證明 owner 冪等與舊快照移除**

```java
FakeTarget target = new FakeTarget();
StatModifierOwners owners = new StatModifierOwners();
StatModifier oldValue = modifier().str(2).maxHp(50).build();
owners.reconcile(singletonMap("item:7", oldValue), target);
owners.reconcile(singletonMap("item:7", oldValue), target);
assertEquals(2, target.str);
assertEquals(50, target.maxHp);

StatModifier changedDbValue = modifier().str(9).maxHp(200).build();
owners.reconcile(Collections.<String, StatModifier>emptyMap(), target);
assertEquals(0, target.str);
assertEquals(0, target.maxHp); // 必須扣 oldValue，不可扣 changedDbValue
```

另加多 owner 任意順序移除，以及 max HP／MP 移除後 clamp 的 fake-target 測試。

- [ ] **Step 2: 執行測試並確認 RED**

Run: `pwsh -File tools/migration/run-850-feature-contract-tests.ps1`

Expected: FAIL，缺少能力向量類別。

- [ ] **Step 3: 實作能力向量與差異更新**

`StatModifierOwners` 保存 `Map<String, StatModifier> applied`；reconcile 順序固定為先移除不存在或值已變更的舊 owner，再加入不存在或值已變更的新 owner。`StatModifier.apply(target, sign)` 是唯一能力增減入口。

`L1PcStatModifierTarget` 使用已由 `ItemEnchantLevelTable` 證實的映射：`bN/bR/bP/bV/bX/bT`、`bL(-ac)`、`F/D/G/H`、`co/cp/ab/aa`、`X/U/Q`。套用後送 `S_SPMR`；移除 max HP／MP 後以 850 getter/setter 限制當前值。

- [ ] **Step 4: 執行測試並確認 GREEN**

Run: `pwsh -File tools/migration/run-850-feature-contract-tests.ps1`

Expected: PASS。

- [ ] **Step 5: 提交能力元件**

```powershell
git add -- recovery/normalized-src-vf/l1r/aq/StatModifier.java recovery/normalized-src-vf/l1r/aq/StatModifierTarget.java recovery/normalized-src-vf/l1r/aq/StatModifierOwners.java recovery/normalized-src-vf/l1r/aq/L1PcStatModifierTarget.java tools/migration/java-tests/l1r/ao/FeatureRuleContractTest.java
git commit -m "feat(core): add reversible stat modifier owners"
```

### Task 3: 實作獨立全地圖掉落 table 與核心掛點

**Files:**
- Create: `recovery/normalized-src-vf/l1r/ao/GlobalDropTable.java`
- Modify: `recovery/normalized-src-vf/l1r/ao/DropTable.java:80-90`
- Modify: `recovery/normalized-src-vf/l1r/ai/GameServer.java:145-175`
- Modify: `recovery/normalized-src-vf/l1r/l1j/server/Config.java:115-250`
- Modify: `tools/migration/java-tests/l1r/ao/FeatureRuleContractTest.java`
- Replace: `migration/381-to-850/packages/指定地圖掉落/850匯入/db/install_map-designated-drop.sql`
- Modify: `migration/381-to-850/packages/指定地圖掉落/850匯入/db/rollback_map-designated-drop.sql`
- Modify: `migration/381-to-850/packages/指定地圖掉落/850匯入/control/map-designated-drop.properties.example`

**Interfaces:**
- Produces: `GlobalDropTable.a()` singleton loader and `void apply(L1NpcInstance npc, L1Inventory inventory)`。
- Consumes: `GlobalDropRule.shouldDrop(Random.a(100) + 1)`。
- Config: `GlobalDropEnabled`, default `0`。

- [ ] **Step 1: 新增失敗測試固定倍率隔離與多列獨立判定**

在 contract test 建立兩個相同 item ID 的 rules，注入 roll sequence `1, 100`，斷言第一列成功、第二列失敗；測試 API 不接受 drop-rate multiplier 參數，防止倍率滲入。

- [ ] **Step 2: 執行測試並確認 RED**

Run: `pwsh -File tools/migration/run-850-feature-contract-tests.ps1`

Expected: FAIL，尚無 rules evaluator/table contract。

- [ ] **Step 3: 實作 SQL 與 loader**

```sql
CREATE TABLE IF NOT EXISTS `w_global_drop` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `item_id` int NOT NULL,
  `chance` tinyint unsigned NOT NULL DEFAULT 1,
  `enabled` tinyint(1) NOT NULL DEFAULT 1,
  `note` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`), KEY `idx_global_drop_enabled` (`enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

loader 只保留 `enabled=1`、`0<=chance<=100` 且 `ItemTable.a().b(itemId)` 有效的列。`apply` 不讀 `Config.E`、`Config.F`、`MapsTable`、elite、活動或玩家倍率；成功時固定建立 count=1、enchant=0 的新 item 並加入傳入 inventory。

- [ ] **Step 4: 接入核心一次性掛點**

在 `DropTable.a(L1NpcInstance, L1Inventory)` 開頭以傳入的 `var2` 呼叫 `GlobalDropTable.a().apply(var1, var2)`；移除舊 `DropMapTable` 對 `mapid=-1` 的全域處理，指定地圖功能仍由 `MapDesignatedDropSwitch` 獨立控制。`GameServer` 在 `ItemTable` 後初始化 `GlobalDropTable`。

- [ ] **Step 5: 執行 contract test 與來源防倍率掃描**

Run:

```powershell
pwsh -File tools/migration/run-850-feature-contract-tests.ps1
rg -n "Config\.(E|F)|MapsTable|dropRateMultiplier" recovery/normalized-src-vf/l1r/ao/GlobalDropTable.java
```

Expected: tests PASS；`rg` 無輸出。

- [ ] **Step 6: 提交全地圖掉落**

```powershell
git add -- recovery/normalized-src-vf/l1r/ao/GlobalDropRule.java recovery/normalized-src-vf/l1r/ao/GlobalDropTable.java recovery/normalized-src-vf/l1r/ao/DropTable.java recovery/normalized-src-vf/l1r/ao/DropMapTable.java recovery/normalized-src-vf/l1r/ai/GameServer.java recovery/normalized-src-vf/l1r/l1j/server/Config.java migration/381-to-850/packages/指定地圖掉落/850匯入 tools/migration/java-tests/l1r/ao/FeatureRuleContractTest.java
git commit -m "feat(drop): add independent global drop rules"
```

### Task 4: 實作共用能力向量 DB loader

**Files:**
- Create: `recovery/normalized-src-vf/l1r/ao/StatModifierTable.java`
- Modify: `recovery/normalized-src-vf/l1r/ai/GameServer.java`
- Create: `migration/381-to-850/packages/指定道具賦予狀態/850匯入/db/install_stat-modifier.sql`
- Create: `migration/381-to-850/packages/指定道具賦予狀態/850匯入/db/rollback_stat-modifier.sql`

**Interfaces:**
- Produces: `StatModifierTable.a()`、`StatModifier get(int modifierId)`、immutable snapshot map。
- Consumes: `w_stat_modifier` rows and returns `StatModifier`。

- [ ] **Step 1: 新增失敗測試固定 ResultSet 欄位映射**

新增 `StatModifier.fromValues(Map<String, Number>)` contract test，至少覆蓋 STR、AC（符號）、max HP、近戰命中、MR、SP，並斷言未知欄位不會靜默變更能力。

- [ ] **Step 2: 執行測試並確認 RED**

Run: `pwsh -File tools/migration/run-850-feature-contract-tests.ps1`

Expected: FAIL，缺少 value mapping。

- [ ] **Step 3: 實作 DDL、loader 與啟動順序**

DDL 為所有支援能力欄位提供 `NOT NULL DEFAULT 0`；`modifier_id` 主鍵、`enabled` 預設 1。`StatModifierTable` 在 `ItemTable` 後、指定道具及裝備規則 table 前初始化。缺表時 snapshot 為空且只記錄一次 WARNING。

- [ ] **Step 4: 執行測試並確認 GREEN**

Run: `pwsh -File tools/migration/run-850-feature-contract-tests.ps1`

Expected: PASS。

- [ ] **Step 5: 提交 loader**

```powershell
git add -- recovery/normalized-src-vf/l1r/ao/StatModifierTable.java recovery/normalized-src-vf/l1r/ai/GameServer.java migration/381-to-850/packages/指定道具賦予狀態/850匯入/db/install_stat-modifier.sql migration/381-to-850/packages/指定道具賦予狀態/850匯入/db/rollback_stat-modifier.sql tools/migration/java-tests/l1r/ao/FeatureRuleContractTest.java
git commit -m "feat(core): load shared stat modifier vectors"
```

### Task 5: 實作指定道具狀態的 850 owner 與切換流程

**Files:**
- Create: `recovery/normalized-src-vf/l1r/ao/DesignatedItemStatusTable.java`
- Create: `recovery/normalized-src-vf/l1r/aq/DesignatedItemStatusService.java`
- Modify: `recovery/normalized-src-vf/l1r/aj/C_ItemUSe.java:103-260`
- Modify: `recovery/normalized-src-vf/l1r/au/L1PcInventory.java:250-275,520-545`
- Modify: `recovery/normalized-src-vf/l1r/ap/L1PcInstance.java:200-220,545-560`
- Modify: `recovery/normalized-src-vf/l1r/ai/GameServer.java`
- Modify: `recovery/normalized-src-vf/l1r/l1j/server/Config.java`
- Replace: `migration/381-to-850/packages/指定道具賦予狀態/850匯入/db/install_item-designated-status.sql`
- Replace: `migration/381-to-850/packages/指定道具賦予狀態/850匯入/db/rollback_item-designated-status.sql`
- Modify: `migration/381-to-850/packages/指定道具賦予狀態/850匯入/control/item-designated-status.properties.example`
- Modify: `tools/migration/java-tests/l1r/ao/FeatureRuleContractTest.java`

**Interfaces:**
- Produces: immutable `DesignatedItemStatusRule(itemId, type, modifierId, enabled, clientFlags...)`。
- Produces: `boolean DesignatedItemStatusService.toggle(L1PcInstance pc, L1ItemInstance item)`。
- Produces: `void reconcile(L1PcInstance pc)` and `void beforeItemLeavesInventory(...)`。
- Config: `DesignatedItemStatusEnabled`, default `0`。

- [ ] **Step 1: 新增失敗測試固定 type 互斥及確定性勝出**

```java
List<ItemState> invalid = Arrays.asList(
    new ItemState(90L, 70001, 3, true),
    new ItemState(80L, 70002, 3, true));
Map<Integer, ItemState> winners = DesignatedItemStatusService.chooseByType(invalid);
assertEquals(80L, winners.get(3).objectId); // 最小 object ID 確定性勝出
```

另測不同 type 可共存、同 item 重複 reconcile 不增加 owner、移除後 owner 消失。

- [ ] **Step 2: 執行測試並確認 RED**

Run: `pwsh -File tools/migration/run-850-feature-contract-tests.ps1`

Expected: FAIL，service 尚不存在。

- [ ] **Step 3: 實作 schema、table 與 service**

`w_item_designated_status` 至少含 `item_id` unique、`type`、`modifier_id`、`enabled`，以及 `poly_id/title/effect_icon/skin_id` 與四個 `*_verified` 欄位。第一版 loader 即使 verified 欄位為 1 也不執行客戶端效果，直到另有客戶端驗證任務；必須記錄 `CLIENT_EFFECT_SKIPPED`。

service 以背包實際 item 的 equipped flag 為權威；owner key 是 `designated-item:<objectId>`。同 type 衝突時保留 object ID 最小者，清除其餘 equipped flag 並透過現有 inventory save/update API 持久化。

- [ ] **Step 4: 接入使用、刪除與登入還原**

`C_ItemUSe` 在一般 item 分派前先查 table；命中時完全交給 `toggle` 並 return，避免原流程消耗道具。`L1PcInventory` 在刪除／轉移已裝備指定道具前呼叫 cleanup。`L1PcInstance` 持有專用 `StatModifierOwners`，登入 inventory 還原完成後呼叫 reconcile。

- [ ] **Step 5: 執行測試並確認 GREEN**

Run: `pwsh -File tools/migration/run-850-feature-contract-tests.ps1`

Expected: PASS。

- [ ] **Step 6: 提交指定道具狀態**

```powershell
git add -- recovery/normalized-src-vf/l1r/ao/DesignatedItemStatusTable.java recovery/normalized-src-vf/l1r/aq/DesignatedItemStatusService.java recovery/normalized-src-vf/l1r/aj/C_ItemUSe.java recovery/normalized-src-vf/l1r/au/L1PcInventory.java recovery/normalized-src-vf/l1r/ap/L1PcInstance.java recovery/normalized-src-vf/l1r/ai/GameServer.java recovery/normalized-src-vf/l1r/l1j/server/Config.java migration/381-to-850/packages/指定道具賦予狀態/850匯入 tools/migration/java-tests/l1r/ao/FeatureRuleContractTest.java
git commit -m "feat(items): add designated passive item status"
```

### Task 6: 實作裝備總加成 table 與裝備生命週期重算

**Files:**
- Create: `recovery/normalized-src-vf/l1r/ao/EquipmentTotalBonusTable.java`
- Create: `recovery/normalized-src-vf/l1r/aq/EquipmentTotalBonusService.java`
- Modify: `recovery/normalized-src-vf/l1r/aq/L1EquipmentSlot.java:22-505`
- Modify: `recovery/normalized-src-vf/l1r/ap/L1PcInstance.java`
- Modify: `recovery/normalized-src-vf/l1r/ai/GameServer.java`
- Modify: `recovery/normalized-src-vf/l1r/l1j/server/Config.java`
- Replace: `migration/381-to-850/packages/裝備總加成能力/850匯入/db/install_equipment-total-bonus.sql`
- Replace: `migration/381-to-850/packages/裝備總加成能力/850匯入/db/rollback_equipment-total-bonus.sql`
- Modify: `migration/381-to-850/packages/裝備總加成能力/850匯入/control/equipment-total-bonus.properties.example`
- Modify: `tools/migration/java-tests/l1r/ao/FeatureRuleContractTest.java`

**Interfaces:**
- Produces: `EquipmentTotalBonusTable.a()` and immutable grouped rules。
- Produces: `EquipmentTotalBonusService.reconcile(L1PcInstance pc, List<L1ItemInstance> equipped)`。
- Consumes: `item.G()` as enchant、`item.a().x()` as 850 safe enchant、`item.g() || item.h()` as equipment eligibility。
- Config: `EquipmentTotalBonusEnabled`, default `0`。

- [ ] **Step 1: 新增失敗測試涵蓋 group 驗證與 owner 差異**

測試同 group 混用 metric／stack mode 時 `validateGroups` 排除整組；合法 HIGHEST 與 CUMULATIVE 同時存在於不同 group 時，各自產生 `equipment-total:<ruleId>` owner。加入門檻下降、卸裝與空裝備案例。

- [ ] **Step 2: 執行測試並確認 RED**

Run: `pwsh -File tools/migration/run-850-feature-contract-tests.ps1`

Expected: FAIL，table/service 尚不存在。

- [ ] **Step 3: 實作 schema 與 loader**

```sql
CREATE TABLE IF NOT EXISTS `w_equipment_total_bonus` (
  `rule_id` int unsigned NOT NULL AUTO_INCREMENT,
  `group_id` varchar(40) NOT NULL,
  `metric` enum('TOTAL_ENCHANT','OVER_SAFE_ENCHANT') NOT NULL,
  `stack_mode` enum('HIGHEST','CUMULATIVE') NOT NULL,
  `threshold` int unsigned NOT NULL,
  `modifier_id` int unsigned NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT 1,
  `note` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`rule_id`),
  UNIQUE KEY `uq_equipment_total_tier` (`group_id`,`threshold`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

loader 驗證 modifier 存在、threshold 非負、enum 合法、同 group 模式一致；壞 group 隔離，合法 group 繼續載入。

- [ ] **Step 4: 接入 850 裝備掛點**

在 `L1EquipmentSlot.a(item)` 完成加入 `d` 後、以及 `b(item)` 完成移除 `d` 後呼叫一次 reconcile。登入逐件還原會多次觸發，但 owner 差異更新保證結果正確；最後登入完成再 reconcile 一次作一致性收斂。強化流程若可對已穿戴物直接變更 `G()`，在該變更成功後呼叫同一 reconcile。

- [ ] **Step 5: 執行測試並確認 GREEN**

Run: `pwsh -File tools/migration/run-850-feature-contract-tests.ps1`

Expected: PASS。

- [ ] **Step 6: 提交裝備總加成**

```powershell
git add -- recovery/normalized-src-vf/l1r/ao/EquipmentTotalBonusTable.java recovery/normalized-src-vf/l1r/aq/EquipmentTotalBonusService.java recovery/normalized-src-vf/l1r/aq/L1EquipmentSlot.java recovery/normalized-src-vf/l1r/ap/L1PcInstance.java recovery/normalized-src-vf/l1r/ai/GameServer.java recovery/normalized-src-vf/l1r/l1j/server/Config.java migration/381-to-850/packages/裝備總加成能力/850匯入 tools/migration/java-tests/l1r/ao/FeatureRuleContractTest.java
git commit -m "feat(equipment): add total enchant threshold bonuses"
```

### Task 7: 同步三個 migration packages、manifest 與可部署 class

**Files:**
- Modify: `migration/381-to-850/packages/指定地圖掉落/manifest.json`
- Modify: `migration/381-to-850/packages/指定道具賦予狀態/manifest.json`
- Modify: `migration/381-to-850/packages/裝備總加成能力/manifest.json`
- Modify: three `項目說明.md` and three `驗證/validation_checklist.md`
- Create/Update: three packages under `850匯入/server/java/` and `850匯入/server/class/`
- Modify: `migration/381-to-850/packages/tests/test_validate_module_package.py`

**Interfaces:**
- Consumes: compiled classes from `recovery/normalized-core-build-classes`。
- Produces: package manifests accepted by `validate_module_package.py` with exact SHA-256 and class outputs。

- [ ] **Step 1: 新增失敗 package tests**

新增三個 fixture/assertion，要求 `decision=ADAPT`、`deployable=true`、imports 使用 validator 現行 object schema、每個 Java source 列出所有 inner class outputs，且 DB install/rollback/control files 全部列入 hash。

- [ ] **Step 2: 執行測試並確認 RED**

Run: `python -m unittest migration/381-to-850/packages/tests/test_validate_module_package.py -v`

Expected: FAIL，現有三個 HOLD manifest 尚未符合 deployable schema。

- [ ] **Step 3: 編譯並同步 source/class**

Run: `python tools/normalized-recovery/compile-normalized-core.py`

只在 `compiler_exit=0` 時，將本功能 Java 原始碼及對應 `.class`／inner classes 複製到各 package。共用能力類別以 `指定道具賦予狀態` package 作 owner；`裝備總加成能力` manifest 明列對該 package/module 的 dependency，避免同一 class 重複擁有。

- [ ] **Step 4: 更新 manifest、說明與 checklist**

三份決策改為 `ADAPT`，server level 依實際 owner 複雜度記錄；指定道具保留 `CLIENT_GATE=PARTIAL_SERVER_ONLY`。manifest hash 從實際 bytes 計算，path map 同時列出 core-fixes 與 fast-dev-build 的邏輯目的地。

- [ ] **Step 5: 驗證三個 package**

Run:

```powershell
python -m unittest migration/381-to-850/packages/tests/test_validate_module_package.py -v
python migration/381-to-850/packages/tools/validate_module_package.py migration/381-to-850/packages/指定地圖掉落 --repo-root .
python migration/381-to-850/packages/tools/validate_module_package.py migration/381-to-850/packages/指定道具賦予狀態 --repo-root .
python migration/381-to-850/packages/tools/validate_module_package.py migration/381-to-850/packages/裝備總加成能力 --repo-root .
```

Expected: 全部 PASS，無 unlisted file、hash mismatch、missing class output。

- [ ] **Step 6: 提交 package 交付物**

```powershell
git add -- migration/381-to-850/packages/指定地圖掉落 migration/381-to-850/packages/指定道具賦予狀態 migration/381-to-850/packages/裝備總加成能力 migration/381-to-850/packages/tests/test_validate_module_package.py
git commit -m "build(migration): package three 850 gameplay modules"
```

### Task 8: 完整驗證與交付紀錄

**Files:**
- Create: `migration/381-to-850/packages/三模組驗證紀錄.md`

**Interfaces:**
- Consumes: all preceding runtime, SQL, package, and test outputs。
- Produces: reproducible verification record with command, exit code, and known baseline failures。

- [ ] **Step 1: 執行功能 contract tests**

Run: `pwsh -File tools/migration/run-850-feature-contract-tests.ps1`

Expected: PASS。

- [ ] **Step 2: 執行 package test suite**

Run: `python -m unittest discover -s migration/381-to-850/packages/tests -p 'test_*.py' -v`

Expected: PASS。

- [ ] **Step 3: 執行 normalized core compile gate**

Run: `python tools/normalized-recovery/compile-normalized-core.py`

Expected: `compiler_exit=0`。若 repository baseline 本來非零，紀錄修改前後 `javac_error_headers` 與本功能檔案是否有新增錯誤；不得把非零結果描述為通過。

- [ ] **Step 4: 執行 SQL 靜態與回滾核對**

確認三份 install 可重複執行、rollback 只移除本模組物件，且 `w_global_drop.chance` 是 `0..100 DEFAULT 1`。若有可用測試 DB，再依序執行 install → schema assertions → rollback；沒有 DB 時明確標為未執行，不推測成功。

- [ ] **Step 5: 寫入驗證紀錄**

記錄每個命令的日期、exit code、PASS/FAIL、失敗檔案與錯誤摘要；列出尚未啟用的客戶端效果及預設關閉的三個設定鍵。

- [ ] **Step 6: 最終 diff 檢查與提交**

Run:

```powershell
git diff --check
git status --short
```

只 stage 本計畫檔案與驗證紀錄，不包含使用者既有修改。

```powershell
git add -- migration/381-to-850/packages/三模組驗證紀錄.md
git commit -m "test(migration): verify three 850 gameplay modules"
```
