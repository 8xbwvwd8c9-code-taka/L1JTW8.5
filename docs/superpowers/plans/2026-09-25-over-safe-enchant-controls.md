# 過安定防具與武器控制 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 以 850 原生強化流程建立武器與防具各自的 DB 控制、預設 +15 上限及可供後續能力計算使用的 mode/rule 資料，但第一階段不修改任何傷害、防禦或角色能力公式。

**Architecture:** `EnchantCapPolicy` 提供不依賴 DB 的純上限判定；`OverSafeArmorTable` 與 `OverSafeWeaponTable` 各自載入獨立控制表及規則表。`Enchant.java` 只在確定增加強化值的路徑呼叫政策，並在扣除卷軸與修改物品前拒絕超限結果。

**Tech Stack:** Java 8、850 normalized source、MySQL、Python 3 `unittest`、既有 normalized-recovery／production-rebuild 工具。

**Specs:**
- `migration/381-to-850/packages/過安定防具/項目說明.md`
- `migration/381-to-850/packages/過安定武器/項目說明.md`

## Global Constraints

- 850 是唯一核心控制基準；381 只提供 DB 規則證據。
- `mode=0` 關閉、`mode=1` 單階、`mode=2` 累加；預設為 0。
- 武器、防具 `max_enchant_level` 各自可控，預設均為 15。
- 強化上限獨立於 mode；mode 0 仍執行上限。
- 任何超限結果必須在卷軸扣除、裝備更新、廣播及成功紀錄前拒絕。
- 既有高於新上限的物品不降級、不刪除，但不得再提升。
- 850 既有更嚴格的特定道具 +9／+15 限制不得被放寬。
- 第一階段不修改傷害、命中、防禦、減傷、HP、MP、SP、特效、成功率或穿脫能力。
- 兩個遷移套件維持獨立 manifest、SQL、來源證據及驗證報告。
- 新增 Java 支援 Java 8，不新增第三方依賴。

## Review Focus

- +14 普通 +1 可到 +15，但 +15 再增加必須無消耗拒絕；Task 5 測試。
- 祝福卷軸抽到 +2／+3 而結果超過上限時不得消耗；Task 5 測試。
- mode 非 0/1/2、控制列缺失、DB 失敗或負上限時使用安全預設；Tasks 3–4 測試。
- 特定道具原有 +9 上限必須優先於可調 +15；Task 5 測試。
- 直接呼叫 `Enchant.a(pc,item,addLevel)` 的路徑不得繞過上限；Task 5 call-site census 測試。

---

### Task 1: 鎖定兩個獨立套件契約與來源證據

**Files:**
- Modify: `migration/381-to-850/packages/tests/test_validate_module_package.py`
- Modify: `migration/381-to-850/packages/過安定防具/manifest.json`
- Modify: `migration/381-to-850/packages/過安定武器/manifest.json`
- Create: `migration/381-to-850/packages/過安定防具/381來源證據/DB/w_過安定防具_202609221205.sql`
- Create: `migration/381-to-850/packages/過安定武器/381來源證據/DB/w_過安定武器_202609221205.sql`

**Interfaces:**
- Produces: 符合現行 schema 的兩份 manifest，decision 為 `ADAPT`、deployable 為 false。
- Produces: 防具 6 筆、武器 80 筆來源證據及 SHA-256。

- [ ] **Step 1: 寫入失敗的套件契約測試**

```python
def test_over_safe_packages_are_db_controlled_and_not_yet_deployable(self):
    for name in ("過安定防具", "過安定武器"):
        manifest = load_manifest(PACKAGES / name)
        self.assertEqual("ADAPT", manifest["decision"])
        self.assertFalse(manifest["deployable"])
        self.assertEqual("NOT_APPLICABLE", manifest["client_gate"])
        self.assertEqual([], manifest["imports"]["client"])
```

- [ ] **Step 2: 執行並確認舊 manifest 結構造成 RED**

Run: `python -m unittest migration/381-to-850/packages/tests/test_validate_module_package.py -v`

Expected: FAIL，原因包含缺少 `deployable`、舊字串 imports 或不存在的匯入檔名。

- [ ] **Step 3: 複製來源 SQL並重建 manifest**

以原始 bytes 複製兩份來源 SQL；manifest 列出當下實際存在的 placeholder SQL/control 檔、SHA-256 及兩種 path_map，`requirements` 明列三種 mode、預設 +15、第一階段不接能力。`850_gap` 內容移入 notes，避免加入 validator 不接受的頂層欄位。

- [ ] **Step 4: 執行套件結構測試**

Run: `python -m unittest migration/381-to-850/packages/tests/test_validate_module_package.py -v`

Expected: 契約測試 PASS，兩個套件 validator 均回報 PACKAGE_OK；`deployable=false` 表示檔案仍是準備材料，不代表結構驗證失敗。

- [ ] **Step 5: 提交契約與證據**

```powershell
git add -- 'migration/381-to-850/packages/過安定防具' 'migration/381-to-850/packages/過安定武器' 'migration/381-to-850/packages/tests/test_validate_module_package.py'
git commit -m "test(migration): define over-safe enchant package contracts"
```

### Task 2: 建立純上限政策

**Files:**
- Create: `recovery/normalized-src-vf/l1r/aw/EnchantCapPolicy.java`
- Create: `tools/migration/tests/test_enchant_cap_policy.py`

**Interfaces:**
- Produces: `EnchantCapPolicy.isIncreaseAllowed(int currentLevel, int addLevel, int maxLevel)`。
- Produces: `EnchantCapPolicy.safeMaxLevel(int configuredLevel)`；負值回傳 15，其餘回傳原值。

- [ ] **Step 1: 寫入失敗的 Java 行為測試**

Python 測試用 Java 8 編譯真實 policy 與 harness：

```java
assertTrue(EnchantCapPolicy.isIncreaseAllowed(14, 1, 15));
assertFalse(EnchantCapPolicy.isIncreaseAllowed(15, 1, 15));
assertFalse(EnchantCapPolicy.isIncreaseAllowed(14, 2, 15));
assertTrue(EnchantCapPolicy.isIncreaseAllowed(15, -1, 15));
assertEquals(15, EnchantCapPolicy.safeMaxLevel(-1));
```

另測 `currentLevel + addLevel` 使用 long 運算，避免 int overflow 被誤判為可接受。

- [ ] **Step 2: 執行並確認類別不存在造成 RED**

Run: `python -m unittest tools.migration.tests.test_enchant_cap_policy -v`

Expected: FAIL，`javac` 回報缺少 `EnchantCapPolicy`。

- [ ] **Step 3: 實作最小純政策**

`addLevel <= 0` 一律允許，確保降級流程不受阻擋；正增加時以 long 計算 projected level 並比較安全上限。

- [ ] **Step 4: 執行政策測試與 normalized 診斷**

Run: `python -m unittest tools.migration.tests.test_enchant_cap_policy -v`

Run: `python tools/normalized-recovery/compile-normalized-core.py`

Expected: 政策測試 0 failures；沒有新增以 `EnchantCapPolicy.java` 開頭的 javac error signature。

- [ ] **Step 5: 提交純政策**

```powershell
git add -- recovery/normalized-src-vf/l1r/aw/EnchantCapPolicy.java tools/migration/tests/test_enchant_cap_policy.py
git commit -m "feat(core): add configurable enchant cap policy"
```

### Task 3: 建立防具控制 DB、規則表與載入器

**Files:**
- Replace: `migration/381-to-850/packages/過安定防具/850匯入/db/install_equip-stabilize-armor.sql`
- Replace: `migration/381-to-850/packages/過安定防具/850匯入/db/rollback_equip-stabilize-armor.sql`
- Delete: `migration/381-to-850/packages/過安定防具/850匯入/control/equip-stabilize-armor.properties.example`
- Modify: `migration/381-to-850/packages/過安定防具/manifest.json`
- Create: `recovery/normalized-src-vf/l1r/ao/OverSafeArmorTable.java`
- Create: `tools/migration/tests/test_over_safe_armor_control.py`

**Interfaces:**
- Produces: `OverSafeArmorTable.getInstance()`, `getMode()`, `getMaxEnchantLevel()`, `reload()`。
- Produces: `over_safe_armor_control` singleton 與 `over_safe_armor_rule` 六筆規則。

- [ ] **Step 1: 寫入失敗的 SQL 與載入器測試**

測試 install SQL 建立 InnoDB 表、控制列 `(1,0,15)`、六筆 rule、唯一 Level；rollback 只移除本項目兩表。Java harness 以注入 row 測 `0/1/2`、非法 mode、負上限、缺列與 reload snapshot。

- [ ] **Step 2: 執行並確認 placeholder SQL／缺少類別造成 RED**

Run: `python -m unittest tools.migration.tests.test_over_safe_armor_control -v`

Expected: FAIL，install SQL 沒有 DDL 且 loader 不存在。

- [ ] **Step 3: 實作 SQL 與 loader**

控制表欄位固定為 `id, mode, max_enchant_level, updated_at`；規則表為 `level, physical_reduction, magic_reduction, hp, mp`。loader 完成查詢後一次替換 immutable snapshot；讀取失敗保留 `mode=0,max=15` 並寫 SEVERE log。

- [ ] **Step 4: 執行防具控制測試及編譯診斷**

Run: `python -m unittest tools.migration.tests.test_over_safe_armor_control -v`

Run: `python migration/381-to-850/packages/tools/validate_module_package.py 'migration/381-to-850/packages/過安定防具' --repo-root .`

Run: `python tools/normalized-recovery/compile-normalized-core.py`

Expected: 測試 PASS、PACKAGE_OK；loader 沒有新增編譯錯誤。manifest 移除已刪除的 properties，改列正式 SQL。

- [ ] **Step 5: 提交防具控制**

```powershell
git add -- 'migration/381-to-850/packages/過安定防具/850匯入' recovery/normalized-src-vf/l1r/ao/OverSafeArmorTable.java tools/migration/tests/test_over_safe_armor_control.py
git commit -m "feat(db): add over-safe armor controls"
```

### Task 4: 建立武器控制 DB、規則表與載入器

**Files:**
- Replace: `migration/381-to-850/packages/過安定武器/850匯入/db/install_equip-stabilize-weapon.sql`
- Replace: `migration/381-to-850/packages/過安定武器/850匯入/db/rollback_equip-stabilize-weapon.sql`
- Delete: `migration/381-to-850/packages/過安定武器/850匯入/control/equip-stabilize-weapon.properties.example`
- Modify: `migration/381-to-850/packages/過安定武器/manifest.json`
- Create: `recovery/normalized-src-vf/l1r/ao/OverSafeWeaponTable.java`
- Create: `tools/migration/tests/test_over_safe_weapon_control.py`

**Interfaces:**
- Produces: `OverSafeWeaponTable.getInstance()`, `getMode()`, `getMaxEnchantLevel()`, `reload()`。
- Produces: `over_safe_weapon_control` singleton 與以 `(weapon_type,level)` 為主鍵的 80 筆規則。

- [ ] **Step 1: 寫入失敗的 SQL 與載入器測試**

測試控制列 `(1,0,15)`、80 筆來源 row、武器類別/Level 複合唯一鍵、381 所有欄位原值保存、rollback 邊界、非法控制值與 reload snapshot。

- [ ] **Step 2: 執行並確認 placeholder SQL／缺少類別造成 RED**

Run: `python -m unittest tools.migration.tests.test_over_safe_weapon_control -v`

Expected: FAIL，install SQL 沒有 DDL 且 loader 不存在。

- [ ] **Step 3: 實作 SQL 與 loader**

規則表保存 `weapon_type, level, melee_damage, ranged_damage, melee_hit, ranged_hit, damage, double_damage, random_damage, random_elf, min_damage, max_damage, consume_hp, consume_mp, gfx_id, sp`。loader 錯誤策略與防具一致，不接入戰鬥公式。

- [ ] **Step 4: 執行武器控制測試及編譯診斷**

Run: `python -m unittest tools.migration.tests.test_over_safe_weapon_control -v`

Run: `python migration/381-to-850/packages/tools/validate_module_package.py 'migration/381-to-850/packages/過安定武器' --repo-root .`

Run: `python tools/normalized-recovery/compile-normalized-core.py`

Expected: 測試 PASS、PACKAGE_OK；loader 沒有新增編譯錯誤。manifest 移除已刪除的 properties，改列正式 SQL。

- [ ] **Step 5: 提交武器控制**

```powershell
git add -- 'migration/381-to-850/packages/過安定武器/850匯入' recovery/normalized-src-vf/l1r/ao/OverSafeWeaponTable.java tools/migration/tests/test_over_safe_weapon_control.py
git commit -m "feat(db): add over-safe weapon controls"
```

### Task 5: 在 850 Enchant 核心集中執行武器／防具上限

**Files:**
- Modify: `recovery/normalized-src-vf/l1r/aw/Enchant.java`
- Modify: `recovery/normalized-src-vf/l1r/aj/C_ItemUSe.java`
- Create: `tools/migration/tests/test_enchant_cap_integration.py`

**Interfaces:**
- Consumes: `EnchantCapPolicy`, `OverSafeArmorTable`, `OverSafeWeaponTable`。
- Changes: public direct-add entry returns boolean so callers consume source items only when mutation occurred。

- [ ] **Step 1: 寫入失敗的核心整合測試**

使用真實核心方法的最小 Java fixture 與靜態 call-site census，覆蓋：武器/防具 +14→+15、+15→+16、+14 加 2、負增加、既有 +16 再增加、mode 0 仍限制、DB 上限 20、特定 +9 物品、所有 `Enchant.a(...,addLevel)` 呼叫者。

```java
assertTrue(applyIncrease(WEAPON, 14, 1, 15).changed());
assertFalse(applyIncrease(WEAPON, 15, 1, 15).changed());
assertFalse(applyIncrease(ARMOR, 14, 2, 15).consumedScroll());
assertTrue(applyIncrease(ARMOR, 16, -1, 15).changed());
```

- [ ] **Step 2: 執行並確認核心尚未檢查造成 RED**

Run: `python -m unittest tools.migration.tests.test_enchant_cap_integration -v`

Expected: FAIL，超限案例仍修改裝備或 consume path 未受 boolean 結果控制。

- [ ] **Step 3: 重構 direct-add 入口為可判斷結果**

將 `Enchant.a(L1PcInstance,L1ItemInstance,int)` 改為 boolean：非武器/防具保持原行為；武器取 weapon max，防具取 armor max；超限送拒絕訊息並回傳 false；成功修改回傳 true。更新 `C_ItemUSe` 每個直接呼叫點，只有 true 才扣除對應來源道具。

- [ ] **Step 4: 接入一般卷軸成功分支**

`Enchant.b` 與 `Enchant.c` 在成功亂數及實際 addLevel 已確定後先呼叫 policy；false 時立即 return 且不執行其後卷軸扣除。失敗破壞、失敗不變、詛咒降級原路徑不改。保留所有特定道具既有檢查順序。

- [ ] **Step 5: 執行整合、政策與控制測試**

Run: `python -m unittest tools.migration.tests.test_enchant_cap_integration tools.migration.tests.test_enchant_cap_policy tools.migration.tests.test_over_safe_armor_control tools.migration.tests.test_over_safe_weapon_control -v`

Expected: 全部 PASS；call-site census 證明沒有直接增加路徑繞過 boolean 結果。

- [ ] **Step 6: 執行 normalized 與 production build gate**

Run: `python tools/normalized-recovery/compile-normalized-core.py`

Run: `powershell -ExecutionPolicy Bypass -File tools/production-rebuild/build-production-test.ps1`

Expected: 沒有新增本任務檔案的錯誤；production build 符合既有 baseline gate。

- [ ] **Step 7: 提交核心上限**

```powershell
git add -- recovery/normalized-src-vf/l1r/aw/Enchant.java recovery/normalized-src-vf/l1r/aj/C_ItemUSe.java tools/migration/tests/test_enchant_cap_integration.py
git commit -m "feat(core): enforce DB-controlled enchant caps"
```

### Task 6: 組裝兩個獨立套件並補首頁紀錄

**Files:**
- Modify: `migration/381-to-850/packages/過安定防具/manifest.json`
- Modify: `migration/381-to-850/packages/過安定武器/manifest.json`
- Create: `migration/381-to-850/packages/過安定防具/驗證/驗證報告.md`
- Create: `migration/381-to-850/packages/過安定武器/驗證/驗證報告.md`
- Modify: `README.md`

**Interfaces:**
- Produces: 兩個 validator PASS 的獨立套件、SHA-256、core_fixes/fast_dev_build 路徑映射及驗證證據。

- [ ] **Step 1: 將完成態契約寫成失敗測試**

manifest `deployable=true` 時，測試要求所有 imports 存在且 hash 正確、Java 具有 class_outputs、兩種 path_map 完整、client 為空、validation 列出四組測試及兩種 build gate。

- [ ] **Step 2: 執行並確認尚未組裝造成 RED**

Run: `python -m unittest migration/381-to-850/packages/tests/test_validate_module_package.py -v`

Expected: FAIL，缺少套件內 Java/class 輸出、hash、path_map 或驗證報告。

- [ ] **Step 3: 分別組裝套件**

防具包只收防具 loader、共用 cap policy、必要 Enchant/C_ItemUSe 目標檔及防具 SQL；武器包只收武器 loader、共用 cap policy、必要 Enchant/C_ItemUSe 目標檔及武器 SQL。manifest 對共用核心標記相同 SHA-256，並在 dependencies 記錄兩包不可安裝不同版本。

- [ ] **Step 4: 寫入新鮮驗證結果**

重跑 Task 5 Step 5–6 與兩個 package validator，將命令、時間、exit code、測試數、baseline 錯誤及未執行項目分別寫入驗證報告。

- [ ] **Step 5: 更新首頁**

README 記錄：850 原本沒有一般裝備全域上限；新增 DB 可控的武器/防具上限；預設 +15；mode 定義；第一階段不改戰鬥能力；連結兩份 `項目說明.md`。

- [ ] **Step 6: 提交獨立交付包**

```powershell
git add -- 'migration/381-to-850/packages/過安定防具' 'migration/381-to-850/packages/過安定武器' 'migration/381-to-850/packages/tests/test_validate_module_package.py' README.md
git commit -m "docs(migration): package over-safe enchant controls"
```

### Task 7: 最終稽核與 A2A 驗證交接

**Files:**
- Verify only: 本計畫列出的 Java、SQL、測試、兩個套件及 README。

**Interfaces:**
- Produces: commit 清單、驗證命令、實際結果、DB install/rollback 與限制說明。

- [ ] **Step 1: 逐條核對兩份規格第 7 節**

每個驗收條件必須對應自動化測試或命令輸出；不得以檔案存在取代行為驗證。

- [ ] **Step 2: 確認沒有能力公式修改**

Run: `$baseCommit = git merge-base HEAD origin/work/l1jtw85-core-fixes; git diff --word-diff=porcelain $baseCommit HEAD -- recovery/normalized-src-vf/l1r | rg -n 'damage|hit|reduction|HP|MP|L1EquipmentSlot'`

Expected: 只有規格、測試名稱或既有上下文；沒有新增角色能力套用或戰鬥公式。

- [ ] **Step 3: 確認提交範圍**

Run: `git log --oneline --name-status --regexp-ignore-case --grep='over-safe\|enchant cap'`

Expected: 只包含本計畫列出的檔案；發現其他工作檔案即停止交付。

- [ ] **Step 4: 產生 A2A 交接**

交接包含兩份規格、此計畫、commit SHA、測試/build 結果、預設 DB 值、mode 定義、安裝／回滾順序，以及「第一階段未接傷害／防禦能力」限制。

- [ ] **Step 5: 移入實際 BUG 修補完成支線後重驗**

先列出遠端分支並精確確認完成支線名稱；以非破壞性的 merge/cherry-pick 移入，不猜名稱、不改寫遠端歷史。完成支線上重跑 Tasks 5–6 的全部測試與 build gate，取得新鮮結果後才標記可驗證。
