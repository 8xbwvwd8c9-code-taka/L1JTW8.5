# 381 → 850 DB／核心／客戶端移植稽核設計

## 目標與範圍

以修復完成的 850 核心為唯一目標架構，逐項查明 381 DB 模組移植到 850 所需的 DB、核心與客戶端工作，並將難易度、依賴、阻塞與決策記錄在既有 `analysis/381-to-850-db-migration` 支線。

本階段只比較、查證與記錄，不修改 850 production 核心、不產生 production SQL，也不建立額外支線。

## 權威順序

遇到舊文件、早期對話與程式現況衝突時，依序採用：

1. `completed/l1jtw85-core-fixes` 中已驗證的 850 核心現況。
2. 最新 specialized module audit 的完整證據。
3. 使用者後期明確決策。
4. 舊總表、早期分類與歷史紀錄。

381 僅作功能、資料語意、事件 hook、生命週期與客戶端需求的參考，不作目標架構權威。

## 支線與寫入規則

- 稽核紀錄只寫入並推送至既有 `analysis/381-to-850-db-migration`。
- 不建立新的 `analysis/*`、`work/*`、`repair/*`、`feature/*` 或其他支線。
- `completed/l1jtw85-core-fixes` 在本階段只供唯讀比對。
- 本地工作目錄僅作比對；完成紀錄必須提交並推送遠端，不以未提交檔案作為成果。
- 其他支線的未提交檔案不得被修改、搬移或納入 migration commit。

## 模組隔離

每個 optional DB 模組必須獨立稽核，並保留未來獨立安裝與回滾能力。每份紀錄必須說明：

- 自己擁有的 table、欄位與資料。
- 允許引用的 850 base table 或 native service。
- 必須新增或重寫的 850 hook、loader、owner 或 adapter。
- NPC、HTML、XML、Config、properties、packet、protobuf 或客戶端資源依賴。
- 未來 `install.sql`、`rollback.sql` 與不可逆執行期副作用的責任邊界。

optional 模組不得偷偷依賴另一個 optional 模組。若兩個模組需要相同能力，只能記錄為 850 共用基礎介面；生命週期與資料 ownership 仍由各模組自行負責。

## 單一模組稽核流程

每個 DB 項目依固定順序完成：

1. **381 DB**：DDL、INSERT、欄位、有效資料列與資料語意。
2. **381 核心**：loader、model、handler、startup registration 與真實 runtime callsite。
3. **381 控制來源**：DB、Config、XML、properties 或 hard-coded 條件。
4. **850 核心**：native owner、hook、scheduler、transaction、persistence 與重算路徑。
5. **850 DB**：native schema、可轉換資料結構或最小必要 extension。
6. **客戶端**：只有實際需要 UI、Sprite、GFX、poly、Text、icon、opcode、protobuf 或 launcher 資源時才列入。
7. **隔離性**：確認能獨立安裝、停用及回滾，不依賴其他 optional DB 模組。
8. **難易度**：依 850 尚缺少的能力評級，不以 381 donor framework 大小評級。
9. **決策**：記錄 `MIGRATE`、`ADAPT`、`MERGE`、`HOLD` 或 `SKIP`，並附權威理由。

## 難易度標準

- **L1**：主要是 DB、資料或設定映射，沒有實質新 server 行為。
- **L2**：850 已有 native framework 與生命週期，只需資料轉換、小型 adapter、hook 或 lookup。
- **L3**：需要新增或重寫 persistent owner、跨事件 authoritative recompute、共享交易／併發結算、session／timer lifecycle 或深層戰鬥行為。
- **L4**：server 工作之外，另有已證明且不可避開的客戶端 UI、Sprite、protocol 或特殊資源工作。

可拆分時分別記錄 `SERVER_LEVEL`、`CLIENT_DEP` 與 `CLIENT_GATE`。不得因 SQL 為空、沒有 active row、缺少 CREATE schema、找不到同名 Java class 或簡單關鍵字未命中就判定 `SKIP`。

## 850-first 改寫原則

- 優先使用 850 native core、資料表、packet、scheduler、skill、craft、quest 與 persistence 路徑。
- 不整套搬移 381 Java framework，只記錄 native path 無法表達的最小 extension。
- 多個 stat 模組可以共用 modifier 表示法，但不得共用錯誤的 lifecycle owner。
- 永久或登入重套能力優先採 authoritative、idempotent recompute，避免 incremental add 造成 stale modifier 或重複疊加。
- DB ID 必須完成語意映射；數字相同不能直接視為相同內容。

## 已鎖定的更正

### `w_自動學習技能`

`STATUS=SKIP_USER_DECISION`、`DECISION=SKIP`。舊 `READY FOR IMPLEMENTATION DESIGN / L2` 內容屬歷史分析，不得再進入實作佇列。

### `w_變身卡片能力登入`

381 `C_LoginToServer.getCard(pc)` 已證明會依 quest ownership 在登入時套用角色能力：`STAT_APPLICATION_ON_LOGIN=PROVEN`、`UNLOCK_OWNER=QUEST_STATE`。

剩餘阻塞為 850 `CollectionOwner` 與 idempotent recompute、quest/card identity、polymorph/UI/client mapping、authoritative CREATE schema，以及 Base Card 與 Set 整合後避免重複疊加。

### Clan family

`w_血盟等級` 與 `w_血盟技能` 屬同一 `ClanState` lifecycle family，目標為 `ClanStateOwner → ClanLevelModifier + ClanSkillModifier`。兩個 optional DB 包仍須能獨立安裝與回滾；升級、忘技、入盟、退盟、解散與登入都要納入 authoritative recompute 稽核。

### Craft family

- 不整套搬移 `MasterCraft`；普通配方優先轉換為 850 `craft`、`craft_exchange` 或 `html_craft`。
- 70904 已由 850 native craft 135–156 取代，不重複匯入。
- 80089 相關兩筆在 NPC／spawn 未證明前維持 `HOLD`。
- 80102 的 `fillis10` 維持 client resource gate。
- craft 144 的 `40747` 數量差異維持未決，不自行選擇 381 的 x5000 或 850 的 x1。

## 文件更新與驗收

每次完成單一模組稽核：

1. 先更新 specialized audit。
2. 再更新 `migration/381-to-850-db/README.md` canonical ledger。
3. 涉及隔離邊界時更新 `MODULE_ISOLATION_AUDIT.md`。
4. 舊結論保留時標記 `SUPERSEDED` 並指向新權威。
5. 一個 commit 只收錄一個模組或一組不可分割的 lifecycle family。

完成條件：381 與 850 證據可定位、server level 與 client gate 分開、隔離邊界明確、未證明事項標記 `NOT_PROVEN` 或 `HOLD`、各文件不矛盾，且 commit 已推送遠端既有 migration 支線。

## 第一批工作

1. 將 `AUTO_LEARN_SKILL.md` 改為 `SKIP_USER_DECISION`。
2. 將 `TRANSFORM_CARD_LOGIN_ABILITY_AUDIT.md` 的登入能力 consumer 改為 `PROVEN` 並重寫 blocker。
3. 在 `MODULE_ISOLATION_AUDIT.md` 標記相關早期 L2／L3／L4 結論為 `SUPERSEDED`。
4. 確認首頁 canonical ledger 與三份 specialized／isolation 文件一致。

完成漂移清理後，再依首頁 audit queue 一顆一顆繼續未完成 DB 模組，不混查多個無直接 lifecycle 關係的項目。
