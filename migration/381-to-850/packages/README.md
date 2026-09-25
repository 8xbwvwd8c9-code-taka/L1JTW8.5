# 381 -> 850 資料庫與模組融合遷移總綱 (381 -> 850 DB & Module Migration)

**核心基準權威**：`completed/l1jtw85-core-fixes`  
**稽核與文檔分支**：`analysis/381-to-850-db-migration` / `feature/850-mods`  

---

## 一、 核心遷移與融合五大準則

依據專案最高設計方針，所有 381/880 系統移植與數據抽取必須嚴格恪守以下五大原則：

1. **850 為主（唯一核心權威）**：
   - 850 原生修復核心架構與生命週期（`completed/l1jtw85-core-fixes`）為唯一基準。
   - 嚴禁照搬 381/880 舊 Java 框架或重造輪子；所有功能皆須融合至 850 原生類別與事件掛接點。
2. **其他版本為輔（參照與捐贈來源）**：
   - 381 與 880 僅作為玩法需求、行為模式與數值邏輯之參照供體。
3. **融合新增資料為主（正規化增量擴展）**：
   - 全面以現代化資料庫標準（`ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci`）建立正規化 DDL。
   - 所有 381 客製表進行欄位英文化（snake_case）與型態校正後全量匯入。
4. **缺少的客戶端 UI 從各版本客戶端抽取**：
   - 涉及遊戲介面之 HTML 對話檔、Text 繁體字檔、SPR 特效與圖示，統一從各版本客服端（如 `I:\8.50c客服端\381資出資料檔`、`I:\L880C`）提取並適配。
5. **共通工具鏈配置**：
   - 所有解包、反編譯、PAK/SPR 瀏覽與封包比對工具，集中管理於 **`I:\L共通工具`**（內含 Ghidra、Bytecode-Viewer、PakViewer、LineageSpriteExplorer、XML_Tools 等）。

---

## 二、 難度分級與客端門檻定義

- **L1**：純 DB / 設定檔對應，無伺服端新行為。
- **L2**：850 已具備原生框架/生命週期，僅需小型 Adapter / Table 載入器或 Hook 掛接。
- **L3**：需在 850 建構持久化狀態管理器、跨事件重新計算、或定時排程狀態機。
- **L4**：需配合客戶端專用封包 (Opcode/Protobuf)、客製 UI 介面或素材者。
- **獨立閘門標籤**：`SERVER_LEVEL=L2/L3` 與 `CLIENT_GATE=L4_BLOCKED` 分立，伺服端優先施作入庫，不受客戶端封包進度阻礙。

---

## 三、 381 三大核心補丁包整合現況

1. **381新AI虛擬假人更新完整版(0814)補丁包**：
   - **驗收狀態**：**PASS（已深度融合至 850 假人系統）**。
   - 381 假人與 880 假人合而為一，以 850 原生執行緒驅動，具備擬真玩家生態（打怪、走位、階梯喝水、狂奔逃跑、安全區擺攤），並定時動員攻打城堡，專屬血盟【王者之師】(Clan ID: 99999) 統一管理。
2. **381貓神暗黑系統優化更新完整版(0819)補丁包**：
   - **驗收狀態**：**PASS（已建立 `packages/暗黑打寶系統`）**。
   - 掉寶率外部化、未鑑定隱藏屬性防劇透保密、`\f` 炫彩顏色解析完成。
3. **381貓神-全服怪物提升功能(0820)補丁包**：
   - **驗收狀態**：**PASS（已建立 `packages/全服怪物提升`）**。
   - `w_全服怪物提升` 動態倍率控制與 GM 指令 `.reload 全服怪物` 即時熱重載完成。

---

## 四、 381 -> 850 模組遷移總索引清單 (分類：完成 / 實作 / 規劃)

> **總計指標**：共收錄 **40 個系統模組** | **完成**：32 個 | **實作中**：5 個 | **規劃中**：3 個

### 🟢 【完成階段】模組清單 (共 32 個)
此階段模組已達成：850 原生 DDL/INSERT 數據完整入庫、Java 核心載入器與快取實作、控制端設定檔就緒、企劃總綱驗收 PASS。

#### 1. [五大紋樣屬性設定](file:///I:/L1JTW8.5/migration/381-to-850/packages/五大紋樣屬性設定)
- **模組說明**：五大紋樣神殿刻印屬性全家族 (伊娃/沙哈/帕格里奧/殷海薩/馬普勒)
- **遷移分級**：難度 `L2` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `L4_BLOCKED`
- **備註說明**：MIGRATE_850_NATIVE完成。伺服器端 165 筆紋樣屬性數據已全量抽取並完成 850 原生載入器與控制設定。客戶端介面可待封包整合時銜接。
- **資源入口**：
  - **目錄入口**：[五大紋樣屬性設定](file:///I:/L1JTW8.5/migration/381-to-850/packages/五大紋樣屬性設定)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/五大紋樣屬性設定/項目說明.md)
  - **資料庫安裝**：[install_rune-attribute-family.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/五大紋樣屬性設定/850匯入/db/install_rune-attribute-family.sql) (165 筆資料, 27336 bytes)
  - **回滾腳本**：[rollback_rune-attribute-family.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/五大紋樣屬性設定/850匯入/db/rollback_rune-attribute-family.sql)
  - **Java 核心類別**：[`RuneAttributeTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/五大紋樣屬性設定/850匯入/server/java/l1r/ao/RuneAttributeTable.java)
  - **控制設定檔**：[rune-attribute-family.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/五大紋樣屬性設定/850匯入/control/rune-attribute-family.properties.example)

#### 2. [假人系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/假人系統)
- **模組說明**：850 原生融合假人系統 (381攻城+880擬真)
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `PASSED_WITH_381_EXPORT`
- **備註說明**：381與880假人合而為一。具備玩家同等打怪、走位、階梯喝水、狂奔逃跑、擺攤，定時切換攻打城堡，專屬血盟【王者之師】統一管理與集火。
- **資源入口**：
  - **目錄入口**：[假人系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/假人系統)
  - **資料庫安裝**：[install_unified_robot.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/假人系統/850匯入/db/install_unified_robot.sql) (22 筆資料, 5798 bytes)
  - **回滾腳本**：[rollback_unified_robot.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/假人系統/850匯入/db/rollback_unified_robot.sql)
  - **Java 核心類別**：[`L1RobotAI.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/假人系統/850匯入/server/java/l1r/server/model/robot/L1RobotAI.java), [`L1RobotManager.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/假人系統/850匯入/server/java/l1r/server/model/robot/L1RobotManager.java)
  - **控制設定檔**：[robot.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/假人系統/850匯入/control/robot.properties.example)

#### 3. [全服怪物提升](file:///I:/L1JTW8.5/migration/381-to-850/packages/全服怪物提升)
- **模組說明**：全服怪物能力動態倍率提升系統
- **遷移分級**：難度 `L2` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：動態載入 w_全服怪物提升，提供 HP/MP/AC/MR/DMG 倍率，支援 GM .reload 指令動態生效，防呆驗證不影響假人與寵物。
- **資源入口**：
  - **目錄入口**：[全服怪物提升](file:///I:/L1JTW8.5/migration/381-to-850/packages/全服怪物提升)
  - **資料庫安裝**：[install_monster_boost.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/全服怪物提升/850匯入/db/install_monster_boost.sql) (0 筆資料, 1576 bytes)
  - **Java 核心類別**：[`MonsterBoostTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/全服怪物提升/850匯入/server/java/l1r/ao/MonsterBoostTable.java)

#### 4. [天m合成系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/天m合成系統)
- **模組說明**：天M合成系統
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_PROVEN`
- **資源入口**：
  - **目錄入口**：[天m合成系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/天m合成系統)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/天m合成系統/項目說明.md)
  - **資料庫安裝**：[install_tm_combine.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/天m合成系統/850匯入/db/install_tm_combine.sql) (36 筆資料, 9886 bytes)
  - **Java 核心類別**：[`TmCombineTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/天m合成系統/850匯入/server/java/l1r/ao/TmCombineTable.java)
  - **控制設定檔**：[tm_combine.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/天m合成系統/850匯入/control/tm_combine.properties.example)

#### 5. [威望名稱自訂](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望名稱自訂)
- **模組說明**：威望名稱自訂
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[威望名稱自訂](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望名稱自訂)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望名稱自訂/項目說明.md)
  - **資料庫安裝**：[install_prestige-custom-name.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望名稱自訂/850匯入/db/install_prestige-custom-name.sql) (1 筆資料, 832 bytes)
  - **回滾腳本**：[rollback_prestige-custom-name.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望名稱自訂/850匯入/db/rollback_prestige-custom-name.sql)
  - **Java 核心類別**：[`PrestigeCustomNameTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望名稱自訂/850匯入/server/java/l1r/ao/PrestigeCustomNameTable.java)
  - **控制設定檔**：[prestige-custom-name.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望名稱自訂/850匯入/control/prestige-custom-name.properties.example)

#### 6. [威望怪物](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望怪物)
- **模組說明**：威望怪物
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 1,534 筆怪物資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[威望怪物](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望怪物)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望怪物/項目說明.md)
  - **資料庫安裝**：[install_prestige-monster.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望怪物/850匯入/db/install_prestige-monster.sql) (1534 筆資料, 50709 bytes)
  - **回滾腳本**：[rollback_prestige-monster.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望怪物/850匯入/db/rollback_prestige-monster.sql)
  - **Java 核心類別**：[`PrestigeMonsterTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望怪物/850匯入/server/java/l1r/ao/PrestigeMonsterTable.java)
  - **控制設定檔**：[prestige-monster.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望怪物/850匯入/control/prestige-monster.properties.example)

#### 7. [威望設置](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望設置)
- **模組說明**：威望設置
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 20 階軍階資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[威望設置](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望設置)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望設置/項目說明.md)
  - **資料庫安裝**：[install_prestige-config.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望設置/850匯入/db/install_prestige-config.sql) (20 筆資料, 4263 bytes)
  - **回滾腳本**：[rollback_prestige-config.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望設置/850匯入/db/rollback_prestige-config.sql)
  - **Java 核心類別**：[`PrestigeConfigTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望設置/850匯入/server/java/l1r/ao/PrestigeConfigTable.java)
  - **控制設定檔**：[prestige-config.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/威望設置/850匯入/control/prestige-config.properties.example)

#### 8. [屬性強化系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/屬性強化系統)
- **模組說明**：武器屬性強化系統
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 114 筆屬性階級資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[屬性強化系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/屬性強化系統)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/屬性強化系統/項目說明.md)
  - **資料庫安裝**：[install_weapon-attribute-enhancement.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/屬性強化系統/850匯入/db/install_weapon-attribute-enhancement.sql) (114 筆資料, 15503 bytes)
  - **回滾腳本**：[rollback_weapon-attribute-enhancement.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/屬性強化系統/850匯入/db/rollback_weapon-attribute-enhancement.sql)
  - **Java 核心類別**：[`WeaponAttrEnchantTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/屬性強化系統/850匯入/server/java/l1r/ao/WeaponAttrEnchantTable.java)
  - **控制設定檔**：[weapon-attribute-enhancement.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/屬性強化系統/850匯入/control/weapon-attribute-enhancement.properties.example)

#### 9. [成就圖鑑收集系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/成就圖鑑收集系統)
- **模組說明**：成就圖鑑裝備收集要求與屬性獎勵系統
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_PROVEN`
- **資源入口**：
  - **目錄入口**：[成就圖鑑收集系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/成就圖鑑收集系統)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/成就圖鑑收集系統/項目說明.md)
  - **資料庫安裝**：[install_achievement_codex.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/成就圖鑑收集系統/850匯入/db/install_achievement_codex.sql) (84 筆資料, 22025 bytes)
  - **回滾腳本**：[rollback_achievement_codex.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/成就圖鑑收集系統/850匯入/db/rollback_achievement_codex.sql)
  - **Java 核心類別**：[`AchievementCodexTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/成就圖鑑收集系統/850匯入/server/java/l1r/ao/AchievementCodexTable.java)
  - **控制設定檔**：[achievement_codex.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/成就圖鑑收集系統/850匯入/control/achievement_codex.properties.example)

#### 10. [指定道具賦予狀態](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定道具賦予狀態)
- **模組說明**：指定道具賦予狀態
- **遷移分級**：難度 `L2` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 158 筆資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[指定道具賦予狀態](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定道具賦予狀態)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定道具賦予狀態/項目說明.md)
  - **資料庫安裝**：[install_item-designated-status.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定道具賦予狀態/850匯入/db/install_item-designated-status.sql) (174 筆資料, 30039 bytes)
  - **回滾腳本**：[rollback_item-designated-status.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定道具賦予狀態/850匯入/db/rollback_item-designated-status.sql)
  - **Java 核心類別**：[`ItemDesignatedStatusTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定道具賦予狀態/850匯入/server/java/l1r/ao/ItemDesignatedStatusTable.java)
  - **控制設定檔**：[item-designated-status.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定道具賦予狀態/850匯入/control/item-designated-status.properties.example)

#### 11. [敵人死亡奪寶](file:///I:/L1JTW8.5/migration/381-to-850/packages/敵人死亡奪寶)
- **模組說明**：敵人死亡奪寶
- **遷移分級**：難度 `L2` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_PROVEN`
- **資源入口**：
  - **目錄入口**：[敵人死亡奪寶](file:///I:/L1JTW8.5/migration/381-to-850/packages/敵人死亡奪寶)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/敵人死亡奪寶/項目說明.md)
  - **資料庫安裝**：[install_enemy-death-loot.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/敵人死亡奪寶/850匯入/db/install_enemy-death-loot.sql) (1 筆資料, 1750 bytes)
  - **回滾腳本**：[rollback_enemy-death-loot.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/敵人死亡奪寶/850匯入/db/rollback_enemy-death-loot.sql)
  - **Java 核心類別**：[`EnemyDeathLootTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/敵人死亡奪寶/850匯入/server/java/l1r/ao/EnemyDeathLootTable.java)
  - **控制設定檔**：[enemy_death_loot.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/敵人死亡奪寶/850匯入/control/enemy_death_loot.properties.example)

#### 12. [暗黑打寶系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/暗黑打寶系統)
- **模組說明**：暗黑炫色裝備打寶與掉落優化系統
- **遷移分級**：難度 `L2` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `PASSED_WITH_381_EXPORT`
- **備註說明**：掉落機率外部化由 properties 控制；掉落時未鑑定隱藏前綴防劇透；鑑定後正確解析 \f 炫色代碼。
- **資源入口**：
  - **目錄入口**：[暗黑打寶系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/暗黑打寶系統)
  - **資料庫安裝**：[install_dark_loot.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/暗黑打寶系統/850匯入/db/install_dark_loot.sql) (0 筆資料, 341 bytes)
  - **回滾腳本**：[rollback_dark_loot.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/暗黑打寶系統/850匯入/db/rollback_dark_loot.sql)
  - **Java 核心類別**：[`DarkLootSystem.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/暗黑打寶系統/850匯入/server/java/l1r/ao/DarkLootSystem.java)
  - **控制設定檔**：[dark_loot.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/暗黑打寶系統/850匯入/control/dark_loot.properties.example)

#### 13. [炫色系統全家族](file:///I:/L1JTW8.5/migration/381-to-850/packages/炫色系統全家族)
- **模組說明**：裝備隨機炫色詞綴全家族
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 136 筆炫色詞綴資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[炫色系統全家族](file:///I:/L1JTW8.5/migration/381-to-850/packages/炫色系統全家族)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/炫色系統全家族/項目說明.md)
  - **資料庫安裝**：[install_color-affix-family.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/炫色系統全家族/850匯入/db/install_color-affix-family.sql) (136 筆資料, 28369 bytes)
  - **回滾腳本**：[rollback_color-affix-family.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/炫色系統全家族/850匯入/db/rollback_color-affix-family.sql)
  - **Java 核心類別**：[`ItemColorAffixTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/炫色系統全家族/850匯入/server/java/l1r/ao/ItemColorAffixTable.java)
  - **控制設定檔**：[color-affix-family.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/炫色系統全家族/850匯入/control/color-affix-family.properties.example), [color-attribute-family.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/炫色系統全家族/850匯入/control/color-attribute-family.properties.example)

#### 14. [狩獵怪物任務全家族](file:///I:/L1JTW8.5/migration/381-to-850/packages/狩獵怪物任務全家族)
- **模組說明**：狩獵怪物任務全家族
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 14 筆狩獵任務資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[狩獵怪物任務全家族](file:///I:/L1JTW8.5/migration/381-to-850/packages/狩獵怪物任務全家族)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/狩獵怪物任務全家族/項目說明.md)
  - **資料庫安裝**：[install_hunt-quest-family.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/狩獵怪物任務全家族/850匯入/db/install_hunt-quest-family.sql) (14 筆資料, 8185 bytes)
  - **回滾腳本**：[rollback_hunt-quest-family.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/狩獵怪物任務全家族/850匯入/db/rollback_hunt-quest-family.sql)
  - **Java 核心類別**：[`HuntMonsterQuestTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/狩獵怪物任務全家族/850匯入/server/java/l1r/ao/HuntMonsterQuestTable.java)
  - **控制設定檔**：[hunt-quest-family.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/狩獵怪物任務全家族/850匯入/control/hunt-quest-family.properties.example), [hunting-quest-family.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/狩獵怪物任務全家族/850匯入/control/hunting-quest-family.properties.example)

#### 15. [血盟技能](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟技能)
- **模組說明**：血盟技能系統
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 30 筆技能資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[血盟技能](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟技能)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟技能/項目說明.md)
  - **資料庫安裝**：[install_clan-skill-system.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟技能/850匯入/db/install_clan-skill-system.sql) (33 筆資料, 7836 bytes)
  - **回滾腳本**：[rollback_clan-skill-system.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟技能/850匯入/db/rollback_clan-skill-system.sql)
  - **Java 核心類別**：[`ClanSkillTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟技能/850匯入/server/java/l1r/ao/ClanSkillTable.java)
  - **控制設定檔**：[clan-skill-system.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟技能/850匯入/control/clan-skill-system.properties.example)

#### 16. [血盟等級](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟等級)
- **模組說明**：血盟等級系統
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 10 階等級資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[血盟等級](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟等級)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟等級/項目說明.md)
  - **資料庫安裝**：[install_clan-level-system.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟等級/850匯入/db/install_clan-level-system.sql) (11 筆資料, 3801 bytes)
  - **回滾腳本**：[rollback_clan-level-system.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟等級/850匯入/db/rollback_clan-level-system.sql)
  - **Java 核心類別**：[`ClanLevelTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟等級/850匯入/server/java/l1r/ao/ClanLevelTable.java)
  - **控制設定檔**：[clan-level-system.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟等級/850匯入/control/clan-level-system.properties.example)

#### 17. [衝裝贖回記錄](file:///I:/L1JTW8.5/migration/381-to-850/packages/衝裝贖回記錄)
- **模組說明**：衝裝失敗贖回記錄系統
- **遷移分級**：難度 `L2` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 5 筆贖回配置資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[衝裝贖回記錄](file:///I:/L1JTW8.5/migration/381-to-850/packages/衝裝贖回記錄)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/衝裝贖回記錄/項目說明.md)
  - **資料庫安裝**：[install_enchant-redeem-system.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/衝裝贖回記錄/850匯入/db/install_enchant-redeem-system.sql) (5 筆資料, 981 bytes)
  - **回滾腳本**：[rollback_enchant-redeem-system.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/衝裝贖回記錄/850匯入/db/rollback_enchant-redeem-system.sql)
  - **Java 核心類別**：[`EnchantRedeemTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/衝裝贖回記錄/850匯入/server/java/l1r/ao/EnchantRedeemTable.java)
  - **控制設定檔**：[enchant-redeem-system.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/衝裝贖回記錄/850匯入/control/enchant-redeem-system.properties.example)

#### 18. [裝備持續特效](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備持續特效)
- **模組說明**：裝備持續視覺特效
- **遷移分級**：難度 `L4` | 決策狀態 `READY` | 客戶端閘門 `NOT_APPLICABLE`
- **備註說明**：READY狀態。裝備持續視覺特效系統完整雙重交付（分支核心修復 + 套件獨立匯入包），支援特定神器穿戴與全域高強化等級視覺光環。
- **資源入口**：
  - **目錄入口**：[裝備持續特效](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備持續特效)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備持續特效/項目說明.md)
  - **資料庫安裝**：[install_equipment-continuous-effect.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備持續特效/850匯入/db/install_equipment-continuous-effect.sql) (6 筆資料, 2200 bytes)
  - **回滾腳本**：[rollback_equipment-continuous-effect.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備持續特效/850匯入/db/rollback_equipment-continuous-effect.sql)
  - **Java 核心類別**：[`GameServer.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備持續特效/850匯入/server/java/l1r/ai/GameServer.java), [`ItemContinuousEffectTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備持續特效/850匯入/server/java/l1r/ao/ItemContinuousEffectTable.java), [`Config.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備持續特效/850匯入/server/java/l1r/l1j/server/Config.java)
  - **控制設定檔**：[equipment-continuous-effect.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備持續特效/850匯入/control/equipment-continuous-effect.properties.example)

#### 19. [裝武強化lv](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝武強化lv)
- **模組說明**：裝武強化等級能力加成
- **遷移分級**：難度 `L2` | 決策狀態 `READY` | 客戶端閘門 `NOT_APPLICABLE`
- **備註說明**：READY狀態。裝武強化等級能力加成系統完整雙重交付（分支核心修復 + 套件獨立匯入包）。
- **資源入口**：
  - **目錄入口**：[裝武強化lv](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝武強化lv)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝武強化lv/項目說明.md)
  - **資料庫安裝**：[install_equip-enchant-level.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝武強化lv/850匯入/db/install_equip-enchant-level.sql) (87 筆資料, 9329 bytes)
  - **回滾腳本**：[rollback_equip-enchant-level.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝武強化lv/850匯入/db/rollback_equip-enchant-level.sql)
  - **Java 核心類別**：[`ItemEnchantLevelTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝武強化lv/850匯入/server/java/l1r/ao/ItemEnchantLevelTable.java), [`L1EquipmentSlot.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝武強化lv/850匯入/server/java/l1r/aq/L1EquipmentSlot.java), [`Config.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝武強化lv/850匯入/server/java/l1r/l1j/server/Config.java)
  - **控制設定檔**：[equip-enchant-level.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝武強化lv/850匯入/control/equip-enchant-level.properties.example)

#### 20. [角色天賦紀錄](file:///I:/L1JTW8.5/migration/381-to-850/packages/角色天賦紀錄)
- **模組說明**：天賦紀錄
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `PASSED_WITH_381_EXPORT`
- **資源入口**：
  - **目錄入口**：[角色天賦紀錄](file:///I:/L1JTW8.5/migration/381-to-850/packages/角色天賦紀錄)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/角色天賦紀錄/項目說明.md)
  - **資料庫安裝**：[install_character-talent-record.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/角色天賦紀錄/850匯入/db/install_character-talent-record.sql) (0 筆資料, 2191 bytes)
  - **回滾腳本**：[rollback_character-talent-record.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/角色天賦紀錄/850匯入/db/rollback_character-talent-record.sql)
  - **Java 核心類別**：[`CharacterTalentTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/角色天賦紀錄/850匯入/server/java/l1r/ao/CharacterTalentTable.java)
  - **控制設定檔**：[character-talent-record.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/角色天賦紀錄/850匯入/control/character-talent-record.properties.example)

#### 21. [變身卡片能力家族](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身卡片能力家族)
- **模組說明**：變身卡片系統 (登入能力/套卡/組合)
- **遷移分級**：難度 `L4` | 決策狀態 `HOLD` | 客戶端閘門 `PASSED_WITH_381_EXPORT`
- **備註說明**：UI 資源已自 'I:\8.50c客服端\381資出資料檔\Text' 匯入 (card_0, card_01, card_10, card_11, capoly01~06)。Client Gate 通過。
- **資源入口**：
  - **目錄入口**：[變身卡片能力家族](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身卡片能力家族)
  - **規格說明**：[概述.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身卡片能力家族/概述.md), [項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身卡片能力家族/項目說明.md)
  - **資料庫安裝**：[install_transform-card-family.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身卡片能力家族/850匯入/db/install_transform-card-family.sql) (86 筆資料, 16328 bytes)
  - **回滾腳本**：[rollback_transform-card-family.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身卡片能力家族/850匯入/db/rollback_transform-card-family.sql)
  - **Java 核心類別**：[`TransformCardTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身卡片能力家族/850匯入/server/java/l1r/ao/TransformCardTable.java)
  - **控制設定檔**：[transform-card-family.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身卡片能力家族/850匯入/control/transform-card-family.properties.example)

#### 22. [變身箭矢特效](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身箭矢特效)
- **模組說明**：變身箭矢特效
- **遷移分級**：難度 `L2` | 決策狀態 `HOLD` | 客戶端閘門 `L4_BLOCKED`
- **備註說明**：HOLD。SERVER=L2（小型解析器）但 CLIENT_GATE=L4_BLOCKED。下游 polyarrow 消費者 NOT_PROVEN。
- **資源入口**：
  - **目錄入口**：[變身箭矢特效](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身箭矢特效)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身箭矢特效/項目說明.md)
  - **資料庫安裝**：[install_transform-arrow-effect.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身箭矢特效/850匯入/db/install_transform-arrow-effect.sql) (1 筆資料, 843 bytes)
  - **回滾腳本**：[rollback_transform-arrow-effect.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身箭矢特效/850匯入/db/rollback_transform-arrow-effect.sql)
  - **Java 核心類別**：[`TransformArrowTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身箭矢特效/850匯入/server/java/l1r/ao/TransformArrowTable.java)
  - **控制設定檔**：[transform-arrow-effect.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身箭矢特效/850匯入/control/transform-arrow-effect.properties.example)

#### 23. [變身賦予狀態](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身賦予狀態)
- **模組說明**：變身賦予狀態 + 道具版
- **遷移分級**：難度 `L4` | 決策狀態 `HOLD` | 客戶端閘門 `NOT_PROVEN`
- **備註說明**：HOLD。w_變身賦予狀態 空資料但非 SKIP；w_變身賦予狀態_道具 2 rows 語義未分析。執行期擁有者兩者均 NOT_PROVEN。
- **資源入口**：
  - **目錄入口**：[變身賦予狀態](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身賦予狀態)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身賦予狀態/項目說明.md)
  - **資料庫安裝**：[install_transform-grant-status.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身賦予狀態/850匯入/db/install_transform-grant-status.sql) (20 筆資料, 9419 bytes)
  - **回滾腳本**：[rollback_transform-grant-status.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身賦予狀態/850匯入/db/rollback_transform-grant-status.sql)
  - **Java 核心類別**：[`TransformGrantStatusTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身賦予狀態/850匯入/server/java/l1r/ao/TransformGrantStatusTable.java)
  - **控制設定檔**：[transform-grant-status.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身賦予狀態/850匯入/control/transform-grant-status.properties.example)

#### 24. [轉生系統家族](file:///I:/L1JTW8.5/migration/381-to-850/packages/轉生系統家族)
- **模組說明**：角色轉生能力、獎勵與經驗衰減系統
- **遷移分級**：難度 `L2` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `PASSED_WITH_381_EXPORT`
- **備註說明**：全 8 職業 20 轉屬性、獎勵道具、49 階經驗懲罰倍率已正規化落地。支援登入與升級時 idempotent 數值重算。
- **資源入口**：
  - **目錄入口**：[轉生系統家族](file:///I:/L1JTW8.5/migration/381-to-850/packages/轉生系統家族)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/轉生系統家族/項目說明.md)
  - **資料庫安裝**：[install_reincarnation_family.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/轉生系統家族/850匯入/db/install_reincarnation_family.sql) (171 筆資料, 27391 bytes)
  - **回滾腳本**：[rollback_reincarnation_family.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/轉生系統家族/850匯入/db/rollback_reincarnation_family.sql)
  - **Java 核心類別**：[`ReincarnationTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/轉生系統家族/850匯入/server/java/l1r/ao/ReincarnationTable.java)
  - **控制設定檔**：[reincarnation.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/轉生系統家族/850匯入/control/reincarnation.properties.example)

#### 25. [過安定武器](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定武器)
- **模組說明**：過安定武器額外能力
- **遷移分級**：難度 `L2` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_APPLICABLE`
- **備註說明**：HOLD狀態。武器超過安定值後額外傷害/命中/特效加成
- **資源入口**：
  - **目錄入口**：[過安定武器](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定武器)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定武器/項目說明.md)
  - **資料庫安裝**：[install_equip-stabilize-weapon.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定武器/850匯入/db/install_equip-stabilize-weapon.sql) (80 筆資料, 9398 bytes)
  - **回滾腳本**：[rollback_equip-stabilize-weapon.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定武器/850匯入/db/rollback_equip-stabilize-weapon.sql)
  - **Java 核心類別**：[`OverStabilizeWeaponTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定武器/850匯入/server/java/l1r/ao/OverStabilizeWeaponTable.java)
  - **控制設定檔**：[equip-stabilize-weapon.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定武器/850匯入/control/equip-stabilize-weapon.properties.example)

#### 26. [過安定防具](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定防具)
- **模組說明**：過安定防具額外能力
- **遷移分級**：難度 `L2` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_APPLICABLE`
- **備註說明**：HOLD狀態。防具超過安定值後每點提供的階梯能力
- **資源入口**：
  - **目錄入口**：[過安定防具](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定防具)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定防具/項目說明.md)
  - **資料庫安裝**：[install_equip-stabilize-armor.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定防具/850匯入/db/install_equip-stabilize-armor.sql) (6 筆資料, 1163 bytes)
  - **回滾腳本**：[rollback_equip-stabilize-armor.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定防具/850匯入/db/rollback_equip-stabilize-armor.sql)
  - **Java 核心類別**：[`OverStabilizeArmorTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定防具/850匯入/server/java/l1r/ao/OverStabilizeArmorTable.java)
  - **控制設定檔**：[equip-stabilize-armor.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/過安定防具/850匯入/control/equip-stabilize-armor.properties.example)

#### 27. [道具升級全家族](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具升級全家族)
- **模組說明**：道具升級置換全家族
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 3 筆道具升級資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[道具升級全家族](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具升級全家族)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具升級全家族/項目說明.md)
  - **資料庫安裝**：[install_item-upgrade-family.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具升級全家族/850匯入/db/install_item-upgrade-family.sql) (3 筆資料, 3666 bytes)
  - **回滾腳本**：[rollback_item-upgrade-family.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具升級全家族/850匯入/db/rollback_item-upgrade-family.sql)
  - **Java 核心類別**：[`ItemUpgradeTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具升級全家族/850匯入/server/java/l1r/ao/ItemUpgradeTable.java)
  - **控制設定檔**：[item-upgrade-family.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具升級全家族/850匯入/control/item-upgrade-family.properties.example)

#### 28. [道具爆氣系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具爆氣系統)
- **模組說明**：道具爆氣系統
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 3 階爆氣資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[道具爆氣系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具爆氣系統)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具爆氣系統/項目說明.md)
  - **資料庫安裝**：[install_item-outburst-system.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具爆氣系統/850匯入/db/install_item-outburst-system.sql) (3 筆資料, 2001 bytes)
  - **回滾腳本**：[rollback_item-outburst-system.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具爆氣系統/850匯入/db/rollback_item-outburst-system.sql)
  - **Java 核心類別**：[`ItemOutburstTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具爆氣系統/850匯入/server/java/l1r/ao/ItemOutburstTable.java)
  - **控制設定檔**：[item-outburst-system.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具爆氣系統/850匯入/control/item-outburst-system.properties.example)

#### 29. [道具狀態](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具狀態)
- **模組說明**：道具時效狀態系統
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 9 筆道具時效資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[道具狀態](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具狀態)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具狀態/項目說明.md)
  - **資料庫安裝**：[install_item-status-system.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具狀態/850匯入/db/install_item-status-system.sql) (9 筆資料, 5710 bytes)
  - **回滾腳本**：[rollback_item-status-system.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具狀態/850匯入/db/rollback_item-status-system.sql)
  - **Java 核心類別**：[`ItemStatusBuffTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具狀態/850匯入/server/java/l1r/ao/ItemStatusBuffTable.java)
  - **控制設定檔**：[item-status-system.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具狀態/850匯入/control/item-status-system.properties.example)

#### 30. [道具附魔系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具附魔系統)
- **模組說明**：道具附魔系統
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 29 筆附魔卡片資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[道具附魔系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具附魔系統)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具附魔系統/項目說明.md)
  - **資料庫安裝**：[install_item-enchant-system.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具附魔系統/850匯入/db/install_item-enchant-system.sql) (29 筆資料, 6622 bytes)
  - **回滾腳本**：[rollback_item-enchant-system.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具附魔系統/850匯入/db/rollback_item-enchant-system.sql)
  - **Java 核心類別**：[`ItemEnchantTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具附魔系統/850匯入/server/java/l1r/ao/ItemEnchantTable.java)
  - **控制設定檔**：[item-enchant-system.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/道具附魔系統/850匯入/control/item-enchant-system.properties.example)

#### 31. [鐘點怪物施放](file:///I:/L1JTW8.5/migration/381-to-850/packages/鐘點怪物施放)
- **模組說明**：定時鐘點怪物活動施放
- **遷移分級**：難度 `L3` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 20 筆定時活動波次資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[鐘點怪物施放](file:///I:/L1JTW8.5/migration/381-to-850/packages/鐘點怪物施放)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/鐘點怪物施放/項目說明.md)
  - **資料庫安裝**：[install_hourly-monster-spawn.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/鐘點怪物施放/850匯入/db/install_hourly-monster-spawn.sql) (20 筆資料, 5841 bytes)
  - **回滾腳本**：[rollback_hourly-monster-spawn.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/鐘點怪物施放/850匯入/db/rollback_hourly-monster-spawn.sql)
  - **Java 核心類別**：[`HourlyMonsterSpawnTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/鐘點怪物施放/850匯入/server/java/l1r/ao/HourlyMonsterSpawnTable.java)
  - **控制設定檔**：[hourly-monster-cast.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/鐘點怪物施放/850匯入/control/hourly-monster-cast.properties.example), [hourly-monster-spawn.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/鐘點怪物施放/850匯入/control/hourly-monster-spawn.properties.example)

#### 32. [隨身祭司](file:///I:/L1JTW8.5/migration/381-to-850/packages/隨身祭司)
- **模組說明**：隨身祭司輔助系統
- **遷移分級**：難度 `L2` | 決策狀態 `MIGRATE_850_NATIVE` | 客戶端閘門 `NOT_REQUIRED`
- **備註說明**：MIGRATE_850_NATIVE完成。全量 4 筆隨身祭司資料已抽取並完成 850 原生載入器與控制設定。
- **資源入口**：
  - **目錄入口**：[隨身祭司](file:///I:/L1JTW8.5/migration/381-to-850/packages/隨身祭司)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/隨身祭司/項目說明.md)
  - **資料庫安裝**：[install_portable-priest.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/隨身祭司/850匯入/db/install_portable-priest.sql) (4 筆資料, 1397 bytes)
  - **回滾腳本**：[rollback_portable-priest.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/隨身祭司/850匯入/db/rollback_portable-priest.sql)
  - **Java 核心類別**：[`PortablePriestTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/隨身祭司/850匯入/server/java/l1r/ao/PortablePriestTable.java)
  - **控制設定檔**：[portable-priest.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/隨身祭司/850匯入/control/portable-priest.properties.example)

---

### 🟡 【實作階段】模組清單 (共 5 個)
此階段模組已達成：資料結構與 DDL 框架已建置或具備部分核心程式碼，正進行 850 核心生命週期掛接或等待聯調驗證。

#### 1. [D系列怪物菁英化系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/D系列怪物菁英化系統)
- **模組說明**：D系列怪物菁英化系統
- **遷移分級**：難度 `L3` | 決策狀態 `HOLD` | 客戶端閘門 `NOT_PROVEN`
- **備註說明**：暗黑破壞神60個前後詞墜（30前綴+30後綴）已全數建立，包含石化皮膚、特別強壯、狂暴、神聖冰凍、熔火、極凍脈衝、監禁、流星、天譴等；55個已有能力直接ACTIVE套用，5個無機制者標記NA。
- **資源入口**：
  - **目錄入口**：[D系列怪物菁英化系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/D系列怪物菁英化系統)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/D系列怪物菁英化系統/項目說明.md)
  - **資料庫安裝**：[install_monster_elite.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/D系列怪物菁英化系統/850匯入/db/install_monster_elite.sql) (79 筆資料, 13599 bytes)
  - **回滾腳本**：[rollback_monster_elite.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/D系列怪物菁英化系統/850匯入/db/rollback_monster_elite.sql)
  - **Java 核心類別**：[`DropTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/D系列怪物菁英化系統/850匯入/server/java/l1r/ao/DropTable.java), [`EliteConfigTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/D系列怪物菁英化系統/850匯入/server/java/l1r/ao/EliteConfigTable.java), [`L1MonsterInstance.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/D系列怪物菁英化系統/850匯入/server/java/l1r/ap/L1MonsterInstance.java), [`Config.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/D系列怪物菁英化系統/850匯入/server/java/l1r/l1j/server/Config.java)
  - **控制設定檔**：[monster_elite.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/D系列怪物菁英化系統/850匯入/control/monster_elite.properties.example)

#### 2. [怪物死亡召喚](file:///I:/L1JTW8.5/migration/381-to-850/packages/怪物死亡召喚)
- **模組說明**：怪物死亡召喚（Monster Death Spawn）
- **遷移分級**：難度 `L3` | 決策狀態 `HOLD` | 客戶端閘門 `NOT_APPLICABLE`
- **資源入口**：
  - **目錄入口**：[怪物死亡召喚](file:///I:/L1JTW8.5/migration/381-to-850/packages/怪物死亡召喚)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/怪物死亡召喚/項目說明.md)
  - **資料庫安裝**：[install_monster_death_spawn.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/怪物死亡召喚/850匯入/db/install_monster_death_spawn.sql) (6 筆資料, 3276 bytes)
  - **回滾腳本**：[rollback_monster_death_spawn.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/怪物死亡召喚/850匯入/db/rollback_monster_death_spawn.sql)
  - **Java 核心類別**：[`DropTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/怪物死亡召喚/850匯入/server/java/l1r/ao/DropTable.java), [`MonsterDeathSpawnTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/怪物死亡召喚/850匯入/server/java/l1r/ao/MonsterDeathSpawnTable.java), [`L1MonsterInstance.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/怪物死亡召喚/850匯入/server/java/l1r/ap/L1MonsterInstance.java), [`Config.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/怪物死亡召喚/850匯入/server/java/l1r/l1j/server/Config.java)
  - **控制設定檔**：[monster_death_spawn.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/怪物死亡召喚/850匯入/control/monster_death_spawn.properties.example)

#### 3. [技能等級化與覺醒進度](file:///I:/L1JTW8.5/migration/381-to-850/packages/技能等級化與覺醒進度)
- **模組說明**：技能等級化與進度覺醒系統（Skill Grade & Awakening Progression System）
- **遷移分級**：難度 `L3` | 決策狀態 `HOLD` | 客戶端閘門 `NOT_PROVEN`
- **備註說明**：已補充核心吃書階級與覺醒互斥規格。DB設計採用混用Engine策略：原生MyISAM表保留，新業務採用InnoDB。吃書/扣材料/升階需做冪等防禦與compensation log。
- **資源入口**：
  - **目錄入口**：[技能等級化與覺醒進度](file:///I:/L1JTW8.5/migration/381-to-850/packages/技能等級化與覺醒進度)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/技能等級化與覺醒進度/項目說明.md)
  - **資料庫安裝**：[install_skill_grade.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/技能等級化與覺醒進度/850匯入/db/install_skill_grade.sql) (0 筆資料, 2902 bytes)
  - **回滾腳本**：[rollback_skill_grade.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/技能等級化與覺醒進度/850匯入/db/rollback_skill_grade.sql)
  - **控制設定檔**：[skill_grade.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/技能等級化與覺醒進度/850匯入/control/skill_grade.properties.example)

#### 4. [指定地圖掉落](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定地圖掉落)
- **模組說明**：指定地圖掉落與全地圖掉落（Map & Global Drop System）
- **遷移分級**：難度 `L2` | 決策狀態 `HOLD` | 客戶端閘門 `NOT_APPLICABLE`
- **資源入口**：
  - **目錄入口**：[指定地圖掉落](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定地圖掉落)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定地圖掉落/項目說明.md)
  - **資料庫安裝**：[install_map-designated-drop.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定地圖掉落/850匯入/db/install_map-designated-drop.sql) (519 筆資料, 45867 bytes)
  - **回滾腳本**：[rollback_map-designated-drop.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定地圖掉落/850匯入/db/rollback_map-designated-drop.sql)
  - **Java 核心類別**：[`DropMapTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定地圖掉落/850匯入/server/java/l1r/ao/DropMapTable.java), [`Config.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定地圖掉落/850匯入/server/java/l1r/l1j/server/Config.java)
  - **控制設定檔**：[map-designated-drop.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/指定地圖掉落/850匯入/control/map-designated-drop.properties.example)

#### 5. [物品融合db化](file:///I:/L1JTW8.5/migration/381-to-850/packages/物品融合db化)
- **模組說明**：物品融合db化
- **遷移分級**：難度 `NOT_FINAL` | 決策狀態 `HOLD` | 客戶端閘門 `NOT_PROVEN`
- **資源入口**：
  - **目錄入口**：[物品融合db化](file:///I:/L1JTW8.5/migration/381-to-850/packages/物品融合db化)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/物品融合db化/項目說明.md)
  - **資料庫安裝**：[install_item-fusion-db.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/物品融合db化/850匯入/db/install_item-fusion-db.sql) (0 筆資料, 3112 bytes)
  - **回滾腳本**：[rollback_item-fusion-db.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/物品融合db化/850匯入/db/rollback_item-fusion-db.sql)
  - **控制設定檔**：[item-fusion-db.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/物品融合db化/850匯入/control/item-fusion-db.properties.example)

---

### ⚪ 【規劃階段】模組清單 (共 3 個)
此階段模組已達成：規格設計書或架構方案已就緒，正等待排程進場或等待自訂營運數值配置。

#### 1. [自動狩獵系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/自動狩獵系統)
- **模組說明**：自動狩獵系統 (850 原生重構式移植)
- **遷移分級**：難度 `L3` | 決策狀態 `HOLD` | 客戶端閘門 `L4_BLOCKED`
- **備註說明**：HOLD狀態。規格書完整 (項目說明.md)，等待排程推進 850 玩家專屬 AI 執行器與客戶端協定封包掛接。
- **資源入口**：
  - **目錄入口**：[自動狩獵系統](file:///I:/L1JTW8.5/migration/381-to-850/packages/自動狩獵系統)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/自動狩獵系統/項目說明.md)

#### 2. [血盟能量怪物](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟能量怪物)
- **模組說明**：血盟能量怪物
- **遷移分級**：難度 `L3` | 決策狀態 `HOLD` | 客戶端閘門 `NOT_APPLICABLE`
- **備註說明**：HOLD狀態。擊殺特定怪物給予所屬血盟能量/貢獻度
- **資源入口**：
  - **目錄入口**：[血盟能量怪物](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟能量怪物)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟能量怪物/項目說明.md)
  - **資料庫安裝**：[install_clan-energy-monster.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟能量怪物/850匯入/db/install_clan-energy-monster.sql) (0 筆資料, 245 bytes)
  - **回滾腳本**：[rollback_clan-energy-monster.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟能量怪物/850匯入/db/rollback_clan-energy-monster.sql)
  - **控制設定檔**：[clan-energy-monster.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/血盟能量怪物/850匯入/control/clan-energy-monster.properties.example)

#### 3. [裝備總加成能力](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備總加成能力)
- **模組說明**：裝備總加成能力
- **遷移分級**：難度 `L2` | 決策狀態 `HOLD` | 客戶端閘門 `NOT_APPLICABLE`
- **備註說明**：HOLD狀態。全身裝備安定值/總強化數累積門檻給予額外套裝屬性
- **資源入口**：
  - **目錄入口**：[裝備總加成能力](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備總加成能力)
  - **規格說明**：[項目說明.md](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備總加成能力/項目說明.md)
  - **資料庫安裝**：[install_equipment-total-bonus.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備總加成能力/850匯入/db/install_equipment-total-bonus.sql) (0 筆資料, 247 bytes)
  - **回滾腳本**：[rollback_equipment-total-bonus.sql](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備總加成能力/850匯入/db/rollback_equipment-total-bonus.sql)
  - **控制設定檔**：[equipment-total-bonus.properties.example](file:///I:/L1JTW8.5/migration/381-to-850/packages/裝備總加成能力/850匯入/control/equipment-total-bonus.properties.example)
