# 381 → 850 全項目稽核與獨立匯入包設計

日期：2026-09-24
狀態：使用者已批准設計；待書面規格複核

## 目的

依 `天堂企劃.txt` 所列功能與 381 資料庫全量清冊，逐項查核控制來源、DB、服務端核心與客戶端素材。850 缺少的功能，以 381 的行為與資料語意作為來源證據，重新整合或改良到 850 架構；每個項目產出互不混雜、可獨立審查與匯入的 850-first 套件。

本規格固定稽核、封裝、驗證與支線流向。它不代表任何項目已完成移植，也不授權在證據不足時推測行為。

## 權威來源與路徑

### 850 目標權威

- 目前核心工作支線：`work/l1jtw85-core-fixes`
- 驗證完成支線：`completed/l1jtw85-core-fixes`
- 未來開發配置支線：`work/l1jtw85-fast-dev-build`
- 850 DB 基準：`db/8.5.sql` 及倉庫內拆分檢查資料

`work/l1jtw85-fast-dev-build` 只改變放置與開發流程時，功能核心仍以相同的 850 行為契約驗證。套件必須用 manifest 保存「邏輯檔案 → 當前 core-fixes 路徑 → 未來 fast-dev-build 路徑」映射，避免目錄調整改變功能語意。

### 381 來源證據

- 381 服務端：`I:\L381\Atu-381伺服器端主`
- 381 客戶端輸出資料：`I:\8.50c客服端\381資出資料檔`
- 專案需求來源：`C:\Users\taka\Desktop\天堂企劃.txt`

381 僅供查明功能、DB 語意、控制開關、事件 hook、生命週期及必要素材；不得整套取代 850 核心。

## 與既有稽核的關係

本規格承接 `analysis/381-to-850-db-migration` 的既有設計及清冊：

- 320 份 381 SQL 必須逐項完成比對。
- 850 是唯一目標架構，381 是 donor/reference。
- SQL 空檔、零資料列、缺少同名類別或關鍵字未命中都不能證明功能不存在。
- 證據不足一律標記 `NOT_PROVEN` 或 `HOLD`。
- 伺服器難度與客戶端阻塞分開記錄。
- specialized audit、canonical ledger 與套件說明不得互相矛盾；舊結論若失效，須標記 `SUPERSEDED` 並指向新權威。

`天堂企劃.txt` 是需求範圍，320 項 SQL 清冊是 DB 掃描範圍。兩者以 manifest 建立多對多關聯：一個企劃項目可以對應多個資料表；一個共用資料表也必須記錄被哪些企劃項目引用。

## 每項固定稽核順序

每個項目都按下列順序查證並保留可定位證據：

1. **381 DB**：DDL、INSERT、欄位、有效資料列、索引、資料語意。
2. **381 核心**：loader、table、model、handler、啟動註冊及真實 runtime callsite。
3. **381 控制**：DB、Config、XML、properties、NPC action 或 hard-coded 條件。
4. **850 核心**：native owner、hook、scheduler、交易、持久化與重算路徑。
5. **850 DB**：原生 schema、資料轉換或最小必要 extension。
6. **850 控制**：設定來源、預設值、停用路徑、錯誤處理與重載方式。
7. **客戶端**：只在已證明需要時查 UI、Sprite、GFX、poly、icon、Text、opcode、protobuf 或 launcher 資源。
8. **隔離與回滾**：確認項目能獨立安裝、停用、驗證與回滾。
9. **決策**：記錄 `MIGRATE`、`ADAPT`、`MERGE`、`HOLD` 或 `SKIP`，並附權威理由。

每項都要回答：控制在哪裡、DB 擁有什麼、核心由誰負責、真實 hook 在哪裡、850 已有什麼、缺少什麼、客戶端是否真的必要，以及如何驗證。

## 850-first 實作原則

- 優先使用 850 原生核心、資料表、封包、排程器、技能、製作、任務及 persistence 路徑。
- 381 行為無法由 850 表達時，只新增最小且可測試的 850 extension。
- 不直接複製整套 381 framework；每個 class 都必須有該項目的 runtime 證據與 850 接口對照。
- 永久能力與登入重套能力採 authoritative、idempotent recompute，避免增量重複疊加。
- 涉及物品、貨幣、角色、血盟或全服限額時，交易邊界、affected-row、未知 commit 結果與重試策略必須明確。
- DB ID、NPC、skill、item、poly、GFX 等數字必須完成語意映射；數值相同不能直接視為相同內容。
- optional 模組不得暗中依賴另一個 optional 模組。共用能力只能下沉到明確的 850 共用介面，各模組仍保有自己的生命週期與資料 ownership。

## 每項套件的固定資料夾結構

每個項目使用一個獨立資料夾，名稱採可穩定辨識的項目名稱：

```text
migration/381-to-850/packages/<項目名稱>/
├─ 項目說明.md
├─ manifest.json
├─ 850匯入/
│  ├─ server/
│  │  ├─ java/
│  │  └─ class/
│  ├─ db/
│  ├─ control/
│  ├─ data/
│  └─ client/
├─ 381來源證據/
└─ 驗證/
```

### `850匯入/`

這是唯一可直接進入 850 的交付內容，內部相對路徑必須鏡射實際 850 目的地：

- `server/java/`：只放該項目實際需要的 Java 原始檔，保留 package 相對路徑。
- `server/class/`：只放該項目由驗證後原始碼編譯出的 class，保留 package 相對路徑；匿名類別及內部類別的 `$*.class` 必須一併收錄。
- `db/`：該項目的 install、upgrade、data migration、rollback 或 schema 檢查檔。
- `control/`：該項目的 properties、XML、設定範例與預設值說明。
- `data/`：850 服務端執行期確定需要的 HTML、XML 或其他資料檔。
- `client/`：只有已證明 850 客戶端會呼叫的最小素材集合。

禁止放入完整 `l1jserver2.jar`、完整核心快照、整包客戶端壓縮檔或其他項目的 Java/class。沒有某一類內容時保留 manifest 空清單，不以佔位二進位檔補齊。

### `381來源證據/`

只保存該項目的必要 donor 證據或索引，不作直接匯入來源。大型來源檔優先用雜湊、原始絕對路徑、行號／表名與擷取說明記錄；只有審查必須且可合法隔離的最小片段才複製進來。

### `驗證/`

保存該項目的重現、測試、編譯、DB/control、runtime、客戶端資源對照與回滾驗證結果。PASS 標籤必須能追溯到命令、輸出摘要、提交與檔案雜湊。

## `項目說明.md` 必填內容

每份說明至少包含：

1. 項目名稱、別名、企劃來源與清冊 ID。
2. 功能目的及明確的非目標。
3. 381 DB／核心／控制／客戶端來源證據。
4. 850 現況、原生替代能力與缺口。
5. 採用的 `MIGRATE`／`ADAPT`／`MERGE`／`HOLD`／`SKIP` 決策及理由。
6. DB schema、資料 ownership、索引、交易與回滾設計。
7. 核心 owner、hook、啟動註冊、重載及錯誤處理。
8. 控制開關、預設值與停用方式。
9. 客戶端素材需求、呼叫方及 ID／GFX／Text 映射；沒有證據時寫 `NOT_PROVEN`。
10. 匯入順序、相依條件、風險及驗證方法。
11. Java/class 清單、來源提交、編譯環境及雜湊。
12. 目前 `core-fixes` 與未來 `fast-dev-build` 的放置差異。

## `manifest.json` 契約

manifest 必須是機器可讀、路徑固定且可驗證的單一真相來源，至少包含：

```json
{
  "schema_version": 1,
  "module_id": "stable-id",
  "module_name": "項目名稱",
  "decision": "HOLD",
  "server_level": "NOT_FINAL",
  "client_gate": "NOT_PROVEN",
  "source": {
    "requirements": [],
    "381_db": [],
    "381_core": [],
    "381_control": [],
    "381_client": []
  },
  "imports": {
    "java": [],
    "class": [],
    "db": [],
    "control": [],
    "data": [],
    "client": []
  },
  "path_map": {
    "core_fixes": [],
    "fast_dev_build": []
  },
  "dependencies": [],
  "validation": [],
  "rollback": []
}
```

所有匯入檔案都必須在 `imports` 中列出並記錄 SHA-256；manifest 未列出的檔案不能進入完成支線。`decision=HOLD` 時不得填入宣稱可部署的 production 匯入結果。

## Java／class 封裝與可重建性

- 每個 Java/class 必須只服務目前項目；共用類別須移到明確的 shared-foundation 套件，並由相依項目在 manifest 引用，不能複製出多份漂移版本。
- class 必須由同一套件列出的 Java 與已記錄工具鏈產生，記錄 JDK、classpath、命令、輸出檔及 SHA-256。
- Java package 路徑、class 路徑與 850 實際載入位置必須一致。
- 修改既有核心檔時，套件放置的是該項目驗證後的目標檔；同一核心檔若被多項修改，正式整合前必須以最新 850 完成支線重建，不能依資料夾覆蓋順序決定結果。
- normalized／obfuscated 來源若兩者皆屬 850 維護契約，必須成對驗證；class 只能來自權威編譯來源。

## 客戶端素材規則

- 先證明服務端對素材的實際呼叫鏈，再從 `I:\8.50c客服端\381資出資料檔` 抽取。
- 只收錄該項目必需的檔案、ID 對照、匯入位置、呼叫來源與 SHA-256。
- Sprite、Text、Tile、圖示、poly、GFX 或 UI 資源不得只因名稱相似就搬移。
- 同一素材被多項共用時，建立明確 shared-client dependency；各項 manifest 引用它，不在每個資料夾重複保存。
- 大型 ZIP 只作來源證據，不直接放入項目套件。

## 難度與狀態

- `L1`：主要為 DB、資料或設定映射，沒有實質新服務端行為。
- `L2`：850 已有 native framework／生命週期，只需轉換、小型 adapter、hook 或 lookup。
- `L3`：需要新或重寫的持久 owner、跨事件重算、交易／併發結算、session／timer 或深層戰鬥行為。
- `L4`：伺服器工作外，另有已證明且不可避開的客戶端 UI、Sprite、protocol 或特殊資源工作。

可拆分時分別記錄 `server_level` 與 `client_gate`。未完成全鏈證據前維持 `NOT_FINAL`／`HOLD`。

## 驗證與完成門檻

單一項目只有同時符合以下條件才能從工作支線移入 `completed/l1jtw85-core-fixes`：

1. 381 DB、核心、控制及必要客戶端證據可定位。
2. 850 DB、核心 owner、hook 與控制鏈已確認。
3. 歷史失敗或功能缺口具有可重現的 RED 證據。
4. 最小修復通過該項目的 source contract、Java 8 編譯或基準錯誤比對、runtime 與 failure-path 測試。
5. DB schema、storage engine、索引、交易、migration 與 rollback gate 通過。
6. Java/class 只含該項目，內部類別完整，manifest 路徑及雜湊一致。
7. 必要客戶端素材已證明、最小化並完成 ID／呼叫鏈驗證；不需要時有明確證據。
8. 套件能獨立安裝、停用及回滾，且未隱含依賴其他 optional 模組。
9. 已在最新完成支線重建候選，未覆蓋其他修復或使用舊核心快照。
10. 首頁與項目說明已記錄問題、根因、解決方法、驗證結果、提交及 CI 連結。

任何一項不足都維持 `HOLD`，不能以文件已建立、檔案已複製或靜態 PASS 標記宣告完成。

## 支線與紀錄流程

1. `analysis/381-to-850-db-migration` 維護全量清冊、來源證據與決策。
2. `work/l1jtw85-core-fixes` 承接已批准項目的修復、套件、測試與 promotion gate。
3. 每次工作前 fetch 並以最新遠端 head 重建，禁止 force-push。
4. 每個 commit 原則上只含一個項目；不可分割的 lifecycle family 可同一 commit，但 manifest 仍逐項獨立。
5. 驗證成功後，只把精確檔案範圍 promotion 到 `completed/l1jtw85-core-fixes`。
6. 完成首頁新增該項目的問題、根因、解決方法、DB／控制／核心／客戶端摘要與驗證證據。
7. `work/l1jtw85-fast-dev-build` 透過 manifest/path-map 消費相同功能成果，不另造一套核心真相。

## 失敗與衝突處理

- 遠端 head 在驗證後改變：停止 promotion，對最新 head 重新建置及驗證。
- 同一核心檔由多項修改：做語意整合並重跑所有受影響項目測試，禁止以檔案複製先後覆蓋。
- 381 行為、企劃需求與 850 安全／一致性契約衝突：保留需求語意，以 850 架構重寫；衝突及取捨寫入說明。
- DB schema、控制來源或 runtime owner 無法證明：標記 `NOT_PROVEN`／`HOLD`，列出缺少證據，不猜測。
- 客戶端素材無法映射：伺服器部分可獨立完成時分離 client gate；無法分離時整項保持阻塞。
- class 無法由所列 Java 重建：驗證失敗，不得移入完成支線。

## 規格完成後的下一階段

書面規格經使用者複核後，先用 `superpowers:writing-plans` 產生分階段實作計畫。計畫至少分為：

1. 需求項目與 320 SQL 清冊關聯。
2. 套件模板、manifest schema 與驗證器。
3. 逐項 DB／控制／核心／客戶端稽核。
4. 按依賴與難度分批實作及測試。
5. promotion、首頁報告與 A2A 獨立驗證交接。

未經書面規格複核與實作計畫批准，不開始大量搬檔或修改 production 核心。

## 驗收結果

最終交付必須讓另一個對話只依遠端提交、項目套件及 A2A 指令，就能獨立查驗：

- 每個企劃／DB 項目都已被列入且沒有混檔。
- 850 控制、DB、核心與必要客戶端素材均有證據。
- 381 內容經 850-first 改良，而非未審查的整套複製。
- 每個 Java/class 都只屬於該項目並可重建。
- 完成支線只含通過驗證的成果。
- 首頁完整說明問題與解決方法。
