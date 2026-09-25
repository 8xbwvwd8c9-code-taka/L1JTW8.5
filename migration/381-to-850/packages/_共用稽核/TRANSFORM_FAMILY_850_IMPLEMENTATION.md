# 381 -> 850 變身系列五模組實作與補足規格書

> **範圍**：
> 1. `w_變身卡片能力登入`
> 2. `w_變身卡片能力組合套卡`
> 3. `w_變身箭矢特效`
> 4. `w_變身賦予狀態`
> 5. `w_變身賦予狀態_道具`

---

## 1. 執行原則與架構方針 (850-First)
依據最新指示「**缺少的部分寫在MD檔 繼續實作 850優先 沒有的表格就依照DB核心控制後新增**」：
1. **850 優先**：廢棄 381 冗餘與易漂移機制（如 CardBookCmd 逐項累加造成重覆登入漂移），改以 850 原生生命週期與重算機制實作。
2. **正規化 DDL 新增**：381 缺乏 CREATE TABLE 語法，全數依據 850 規範重構為標準 InnoDB/utf8mb4 資料表結構。
3. **DB 核心開關整合**：系統由 DB 配置、properties 控制檔及載入器統一控制。

---

## 2. 缺少部分補充與 850 實作對照

### 2.1 變身卡片能力家族 (登入 + 套卡)
- **381 缺陷與缺少處**：
  - 缺乏 CREATE TABLE DDL。
  - `需求的變身卡編號` 在 381 資料中全部為無效的重複 literal `1`，無法作為主鍵。
  - 套卡需求為逗號分隔字串並行陣列，違反關聯式資料庫正規化設計。
  - 登入直接累加屬性，重複調用產生數值漂移（DRIFT_RISK=HIGH）。
- **850 補足與實作設計**：
  - **`w_transform_card_login`**：以 `quest_id` 作為主鍵，規範 64 張卡片。
  - **`w_transform_card_set`**：以 `set_id` 作為主鍵，定義 10 組套卡加成。
  - **`w_transform_card_set_require`**：新建關聯對照表（Junction Table），取代字串並行陣列。
  - **Java 載入器**：[`l1r.ao.TransformCardTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身卡片能力家族/850匯入/server/java/l1r/ao/TransformCardTable.java)
  - **控制設定檔**：[`transform-card-family.properties.example`](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身卡片能力家族/850匯入/control/transform-card-family.properties.example)

### 2.2 變身箭矢特效
- **381 缺陷與缺少處**：
  - 缺乏 CREATE TABLE DDL。
  - 381 僅依賴 `com.lineage.william.ArrowGfxid` 於玩家身上註冊 `polyarrow` 變數，缺乏下游封包分派。
- **850 補足與實作設計**：
  - **`w_transform_arrow_effect`**：建立標準對照表（`poly_id` PK -> `arrow_gfx_id`）。
  - **Java 載入器**：[`l1r.ao.TransformArrowTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身箭矢特效/850匯入/server/java/l1r/ao/TransformArrowTable.java)，提供 `getArrowGfxId(polyId, defaultGfx)` 安全降級查詢。
  - **控制設定檔**：[`transform-arrow-effect.properties.example`](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身箭矢特效/850匯入/control/transform-arrow-effect.properties.example)

### 2.3 變身賦予狀態 (主表 + 道具版)
- **381 缺陷與缺少處**：
  - `w_變身賦予狀態` 在 381 來源中為 0 bytes（缺少資料與結構）。
  - `w_變身賦予狀態_道具` 具有 18 rows 資料但缺乏 CREATE TABLE。
  - 原 `addExp` 為 VARCHAR 字串（`'0'`, `'10'`），型別不安全。
- **850 補足與實作設計**：
  - **`w_transform_grant_status`**：建立變身生命週期主表結構。
  - **`w_transform_grant_status_item`**：建立道具觸發之變身狀態表，將 `add_exp` 規範化為 `DECIMAL(5,2)`。
  - **Java 載入器**：[`l1r.ao.TransformGrantStatusTable.java`](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身賦予狀態/850匯入/server/java/l1r/ao/TransformGrantStatusTable.java)
  - **控制設定檔**：[`transform-grant-status.properties.example`](file:///I:/L1JTW8.5/migration/381-to-850/packages/變身賦予狀態/850匯入/control/transform-grant-status.properties.example)

---

## 3. 已建立與更新之檔案清單

| 套件 | 檔案路徑 | 說明 |
|---|---|---|
| **變身卡片能力家族** | `850匯入/db/install_transform-card-family.sql` | 完整 3 張資料表 DDL + 64 卡片 + 10 套卡 + 36 需求關聯 INSERT |
| | `850匯入/db/rollback_transform-card-family.sql` | 正確順序之 DROP TABLE |
| | `850匯入/control/transform-card-family.properties.example` | DB 控制與細部設定 |
| | `850匯入/server/java/l1r/ao/TransformCardTable.java` | 850 原生載入器類別 |
| | `manifest.json` | 更新模組資訊與 Java 類別引入 |
| **變身箭矢特效** | `850匯入/db/install_transform-arrow-effect.sql` | `w_transform_arrow_effect` DDL 與 1 筆對照資料 |
| | `850匯入/db/rollback_transform-arrow-effect.sql` | DROP TABLE 回滾檔 |
| | `850匯入/control/transform-arrow-effect.properties.example` | 總開關與降級 GFX 控制 |
| | `850匯入/server/java/l1r/ao/TransformArrowTable.java` | 850 原生載入器與查詢 API |
| | `manifest.json` | 更新模組資訊與 Java 類別引入 |
| **變身賦予狀態** | `850匯入/db/install_transform-grant-status.sql` | 兩張資料表 DDL + 18 筆道具狀態 INSERT |
| | `850匯入/db/rollback_transform-grant-status.sql` | 依依賴順序回滾 DROP TABLE |
| | `850匯入/control/transform-grant-status.properties.example` | 變身解除強制清除設定 |
| | `850匯入/server/java/l1r/ao/TransformGrantStatusTable.java` | 850 原生載入器類別 |
| | `manifest.json` | 更新模組資訊與 Java 類別引入 |
---

## 4. 用戶端 UI 資源匯入與補足 (來源：`I:\8.50c客服端\381資出資料檔`)

依使用者指示，於 `I:\8.50c客服端\381資出資料檔\Text` 查核並補足 381 缺少之用戶端 UI 對話框檔：

### 4.1 變身卡片系統用戶端 UI (已匯入套件 `client/html` 目錄)
- **主選單與入口**：
  - `card_01-c.html`：變身卡池 / 娃娃卡池 主入口對話框。
- **變身卡片能力檢視與操作**：
  - `card_0-c.html`：能力卡狀態檢視面板（支援力量/敏捷/體質/HP/MP/傷害/屬性抗性等多項 `<var src="#">` 變數替換，動作：`polycard`, `cardset`, `cardset2`）。
  - `card_D0-c.html`：卡片詳細面板對照檔。
- **套卡組合與總能力查詢**：
  - `card_10-c.html` / `card_D10-c.html`：組合套卡模式種類檢視面板（支援 34 組變數）。
  - `card_11-c.html` / `card_D11-c.html`：單卡與套組卡能力加成總合檢視面板（支援 42 組變數）。
- **卡池分階選單與清單**：
  - `capoly-c.html`：卡池階層選單（一般、高級、稀有、英雄、傳說、神話）。
  - `capoly01-c.html`：一般變身卡池（P01 狼人 ~ P12 獵人戴爾）。
  - `capoly02-c.html`：高級變身卡池（P13 黑騎士 ~ P24 歐瑞(綠)）。
  - `capoly03-c.html`：稀有變身卡池（P25 夜巡 ~ P42 歐瑞(藍)）。
  - `capoly04-c.html`：英雄變身卡池（P43 刺客首領(紅) ~ P68 女武神埃爾）。
  - `capoly05-c.html`：傳說變身卡池（P69 素還真 ~ P91 漆黑死亡騎士）。
  - `capoly06-c.html`：神話變身卡池（P92 齊天大聖孫悟空 ~ P114 魔化克特）。

### 4.2 用戶端閘門 (Client Gate) 狀態變更
- **變身卡片能力家族**：
  - 狀態由 `L4_BLOCKED` 轉為 **`PASSED_WITH_381_EXPORT`**。
  - 對話框 HTML 均已自 381 導出檔複製入 `packages/變身卡片能力家族/850匯入/client/html/`。
  - 核心對應載入器 `TransformCardTable.java` 能與 `card_0-c.html`、`card_10-c.html`、`card_11-c.html` 之變數直接對齊。