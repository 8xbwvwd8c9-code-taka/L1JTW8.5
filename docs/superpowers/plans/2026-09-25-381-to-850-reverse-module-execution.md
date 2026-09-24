# 381 → 850 Reverse Module Execution Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 依 `I:\天堂企劃.txt` 由最後一項往第一項，逐項完成 381 → 850 的控制、DB、核心、客戶端稽核，產生隔離匯入包，通過驗證後移入完成支線並更新首頁。

**Architecture:** 先建立一份不可跳號的反向工作佇列，以及 package manifest／目錄／雜湊驗證器。每次只開啟一個項目，使用 850 native owner 與最小 extension 實作；每項從最新 `work/l1jtw85-core-fixes` 驗證，再精確 promotion 到最新 `completed/l1jtw85-core-fixes`。`work/l1jtw85-fast-dev-build` 只透過 path map 消費相同成果。

**Tech Stack:** Java 8、Python 3、PowerShell 7、JSON、SQL／InnoDB、GitHub Actions、850 recovered normalized／obfuscated source、381 DB／Java／Config／XML、8.50c 客戶端素材。

**Spec:** `docs/superpowers/specs/2026-09-24-381-to-850-module-packaging-design.md`

## Global Constraints

- 850 是唯一目標核心；381 只提供行為、資料語意與資源證據。
- 執行順序固定依 `I:\天堂企劃.txt` 由後往前；除非使用者書面改序，不可先做前面的項目。
- 每次只有一個 active module；同一 lifecycle family 也必須逐項建立獨立資料夾及 manifest。
- 每個套件只收該項目的 Java／class；class 必須包含必要的 `$*.class`，不得收完整 JAR。
- 客戶端只抽取已由服務端呼叫鏈證明必要的最小素材；大型 ZIP 不得進套件。
- SQL 空檔、零資料列、缺少同名類別或關鍵字未命中不能證明功能不存在；證據不足使用 `HOLD`／`NOT_PROVEN`。
- 所有匯入檔都要在 manifest 記錄目的路徑及 SHA-256；未列檔案不得 promotion。
- 每項從最新遠端 head 重建；禁止 force-push，禁止以舊核心快照覆蓋較新修復。
- 維護 normalized／obfuscated 成對契約；修改既有共享核心檔時，重跑先前受影響模組的回歸驗證。
- 完成支線首頁必須記錄問題、根因、解法、DB／控制／核心／客戶端證據、提交與 CI。
- 保留主工作區的 `config/server.properties`、runtime logs、`.lck` 狀態、JAR 備份及其他未提交使用者檔案。

## Review Focus

- 企劃檔被改名或捷徑目標改變：queue build 必須以解析後實體檔 SHA-256 失敗關閉，不能沿用舊順序。
- 同一 Java 檔被兩項修改：promotion 必須以最新完成支線重建並執行先前模組回歸，不能以資料夾覆蓋順序合併。
- manifest 漏列內部類別：validator 必須拒絕缺少對應 `$*.class` 或多出的其他模組 class。
- 381 ID 與 850 ID 數值相同但語意不同：identity gate 必須要求 DB／呼叫方證據，不能只比數值。
- 遠端 head 在驗證與 push 間前進：promotion gate 必須停止，重新同步、重建及驗證。

---

## File Structure

### 共用工具與權威資料

- Create: `migration/381-to-850/packages/reverse-order.json` — 企劃反向佇列、來源 SHA-256 與狀態。
- Create: `migration/381-to-850/packages/schema/module-manifest.schema.json` — manifest 的 JSON Schema。
- Create: `migration/381-to-850/packages/tools/build_reverse_queue.py` — 從實體企劃檔比對固定反向序列。
- Create: `migration/381-to-850/packages/tools/validate_module_package.py` — 驗證目錄、manifest、路徑、雜湊及 Java/class 範圍。
- Create: `migration/381-to-850/packages/tools/claim_next_module.py` — 只允許領取 queue 中第一個未完成項目。
- Create: `migration/381-to-850/packages/tests/test_reverse_queue.py` — 反向順序、來源漂移及跳號測試。
- Create: `migration/381-to-850/packages/tests/test_validate_module_package.py` — 隔離、雜湊、inner class、client gate 測試。
- Create: `migration/381-to-850/packages/tests/test_claim_next_module.py` — 單一 active module 與完成條件測試。
- Modify: `README.md` — 完成首頁逐項報告。
- Modify: `migration/381-to-850/packages/_共用稽核/README.md` — canonical migration ledger。
- Modify: `migration/381-to-850/packages/_共用稽核/inventory/SQL_FULL_INVENTORY.csv` — 項目與 381 SQL 的多對多關聯。

### 每項固定檔案

每個 `<module>` 建立：

- `migration/381-to-850/packages/<module>/項目說明.md`
- `migration/381-to-850/packages/<module>/manifest.json`
- `migration/381-to-850/packages/<module>/850匯入/server/java/`
- `migration/381-to-850/packages/<module>/850匯入/server/class/`
- `migration/381-to-850/packages/<module>/850匯入/db/`
- `migration/381-to-850/packages/<module>/850匯入/control/`
- `migration/381-to-850/packages/<module>/850匯入/data/`
- `migration/381-to-850/packages/<module>/850匯入/client/`
- `migration/381-to-850/packages/<module>/381來源證據/`
- `migration/381-to-850/packages/<module>/驗證/`

Java、SQL、設定及測試的最終檔名由該項稽核證據決定；未完成稽核前不得虛構 class 或 table 名稱。此規則是失敗關閉，不是待補內容。

## Canonical Reverse Queue

`reverse-order.json` 必須依下列順序保存。括號中的 `family` 只決定回歸範圍，不合併項目資料夾：

```text
01 w_變身賦予狀態_道具                 transform-status
02 w_變身賦予狀態                      transform-status
03 w_變身箭矢特效                      transform-status
04 w_變身卡片能力登入                  transform-card
05 w_變身卡片能力組合套卡              transform-card
06 w_隨機能力炫色武器                  random-affix
07 w_隨機能力炫色防具                  random-affix
08 w_隨機能力炫色名稱                  random-affix
09 w_裝備持續特效                      equipment-effect
10 w_炫色_素質設定                     random-affix
11 character_炫色_記錄資料             random-affix
12 馬普勒紋樣屬性設定                  deity-pattern
13 殷海薩紋樣屬性設定                  deity-pattern
14 帕格里奧紋樣屬性設定                deity-pattern
15 沙哈紋樣屬性設定                    deity-pattern
16 伊娃紋樣屬性設定                    deity-pattern
17 w_屬性強化系統                      weapon-attribute
18 w_鐘點怪物施放                      scheduled-monster
19 w_道具爆氣系統                      item-outburst
20 w_道具附魔系統                      item-enchant
21 w_道具狀態                          item-status
22 w_道具升級系統                      item-upgrade
23 w_道具升級                          item-upgrade
24 w_狩獵怪物任務_系統                 hunting-quest
25 w_狩獵怪物任務_地圖                 hunting-quest
26 w_狩獵怪物任務                      hunting-quest
27 w_威望設置                          prestige
28 w_威望怪物                          prestige
29 w_威望名稱自訂                      prestige
30 w_血盟等級                          clan-state
31 w_血盟能量怪物                      clan-state
32 w_血盟技能                          clan-state
33 w_成就圖鑑收集獎勵                  achievement
34 w_成就圖鑑收集設定                  achievement
35 w_天m合成系統                       synthesis
36 character_天賦紀錄                  talent
37 william_pc_轉生經驗                 rebirth
38 william_pc_轉生_giveitem            rebirth
39 william_pc_轉生                     rebirth
40 w_隨身祭司                          portable-priest
41 w_衝裝贖回記錄                      enchant-redemption
42 w_敵人死亡奪寶                      death-loot
43 w_過安定武器                        over-safe-enchant
44 w_過安定防具                        over-safe-enchant
45 w_裝備總加成能力                    equipment-total
46 w_裝武強化lv                        equipment-level
47 w_指定道具賦予狀態                  designated-item-status
48 全地圖掉落                          map-drop
49 w_指定地圖掉落                      map-drop
50 w_物品融合db化                      item-fusion
51 w_怪物死亡召喚                      monster-revival
52 D系列的怪物菁英化系統               elite-monster
53 技能等級化                          skill-tier
```

第 48 項來自企劃檔中「跟全地圖掉落」的明確功能文字，作為獨立稽核項目；若查證後它只是第 49 項的模式，決策使用 `MERGE`，資料夾及證據仍保留。

---

### Task 1: 建立反向佇列與來源漂移防護

**Files:**
- Create: `migration/381-to-850/packages/reverse-order.json`
- Create: `migration/381-to-850/packages/tools/build_reverse_queue.py`
- Create: `migration/381-to-850/packages/tests/test_reverse_queue.py`

**Interfaces:**
- Consumes: `I:\天堂企劃.txt` 的文字內容；本計畫核對時 SHA-256 為 `339ECF09DEF3973AA26CF406CB598D6B8845D12BD09364A926BC396EDEC5D851`。
- Produces: `load_queue(path: Path) -> list[dict]`、`verify_source(queue: dict, source: Path) -> list[str]`。

- [ ] **Step 1: 寫入失敗測試**

測試固定斷言 `items[0].module_name == "w_變身賦予狀態_道具"`、`items[-1].module_name == "技能等級化"`、序號為 1..53、來源內容變更時 `verify_source` 回傳 `SOURCE_SHA256_MISMATCH`，並拒絕重複名稱及缺號。

- [ ] **Step 2: 執行 RED**

Run: `python migration/381-to-850/packages/tests/test_reverse_queue.py -v`

Expected: FAIL，因 `build_reverse_queue` 尚不存在。

- [ ] **Step 3: 實作最小佇列工具**

工具使用 `hashlib.sha256(source.read_bytes()).hexdigest()`，載入 JSON 後檢查連續序號、唯一 `module_id`／`module_name`、首尾名稱及 `source.sha256`。錯誤以穩定代碼清單回傳，CLI 有錯時 exit 1。

- [ ] **Step 4: 執行 GREEN 及真實來源驗證**

Run: `python migration/381-to-850/packages/tests/test_reverse_queue.py -v`

Expected: PASS。

Run: `python migration/381-to-850/packages/tools/build_reverse_queue.py --source I:\天堂企劃.txt --check migration/381-to-850/packages/reverse-order.json`

Expected: `QUEUE_OK count=53 first=w_變身賦予狀態_道具 last=技能等級化`。

- [ ] **Step 5: 提交**

```powershell
git add -- migration/381-to-850/packages/reverse-order.json migration/381-to-850/packages/tools/build_reverse_queue.py migration/381-to-850/packages/tests/test_reverse_queue.py
git commit -m "feat(migration): lock reverse project queue"
```

### Task 2: 建立 manifest schema 與隔離套件驗證器

**Files:**
- Create: `migration/381-to-850/packages/schema/module-manifest.schema.json`
- Create: `migration/381-to-850/packages/tools/validate_module_package.py`
- Create: `migration/381-to-850/packages/tests/test_validate_module_package.py`

**Interfaces:**
- Consumes: 單一模組資料夾與 `manifest.json`。
- Produces: `validate_package(package_dir: Path, repo_root: Path) -> list[str]`；空清單表示通過。

- [ ] **Step 1: 寫入 RED 測試**

測試建立暫存套件並覆蓋：合法空 `HOLD` 包、漏列檔案、SHA-256 不符、跨模組 Java/class、缺少 `$Inner.class`、完整 JAR、client 檔無呼叫證據、`HOLD` 宣稱 deployable、Windows 路徑逃逸、相同邏輯檔缺少 `core_fixes`／`fast_dev_build` 映射。

- [ ] **Step 2: 執行 RED**

Run: `python migration/381-to-850/packages/tests/test_validate_module_package.py -v`

Expected: FAIL，因 validator 尚不存在。

- [ ] **Step 3: 實作 schema 與 validator**

schema 強制 `schema_version=1`、決策列舉、imports 六類陣列、path_map、dependencies、validation、rollback。validator 遍歷 `850匯入`，拒絕 `.jar`／`.zip`，比對 manifest 完整集合與 SHA-256，並以 Java binary name 檢查 class family。

- [ ] **Step 4: 執行 GREEN**

Run: `python migration/381-to-850/packages/tests/test_validate_module_package.py -v`

Expected: PASS，且每種拒絕案例回傳測試指定的穩定代碼。

- [ ] **Step 5: 提交**

```powershell
git add -- migration/381-to-850/packages/schema migration/381-to-850/packages/tools/validate_module_package.py migration/381-to-850/packages/tests/test_validate_module_package.py
git commit -m "feat(migration): validate isolated module packages"
```

### Task 3: 建立單一 active module 領取與完成狀態機

**Files:**
- Create: `migration/381-to-850/packages/tools/claim_next_module.py`
- Create: `migration/381-to-850/packages/tests/test_claim_next_module.py`
- Modify: `migration/381-to-850/packages/reverse-order.json`

**Interfaces:**
- Consumes: queue 與模組 validation report。
- Produces: `claim_next(queue: dict, actor: str) -> dict`、`complete_active(queue: dict, module_id: str, evidence: dict) -> dict`。

- [ ] **Step 1: 寫入 RED 測試**

測試斷言只能領取最前面的 `PENDING`，已有 `ACTIVE` 時拒絕第二次領取，缺少 package validation／work commit／completed commit／homepage evidence 時不能標記 `COMPLETED`，`HOLD` 可結束調查但不視為部署完成。

- [ ] **Step 2: 執行 RED**

Run: `python migration/381-to-850/packages/tests/test_claim_next_module.py -v`

Expected: FAIL，因狀態機尚不存在。

- [ ] **Step 3: 實作狀態機**

允許狀態：`PENDING -> ACTIVE -> HOLD|COMPLETED`；`HOLD -> ACTIVE` 需附新證據。`COMPLETED` 必須保存 work／completed SHA、validation 路徑、homepage anchor、CI URL 與完成時間。

- [ ] **Step 4: 執行 GREEN**

Run: `python migration/381-to-850/packages/tests/test_claim_next_module.py -v`

Expected: PASS。

- [ ] **Step 5: 提交**

```powershell
git add -- migration/381-to-850/packages/reverse-order.json migration/381-to-850/packages/tools/claim_next_module.py migration/381-to-850/packages/tests/test_claim_next_module.py
git commit -m "feat(migration): enforce sequential module claims"
```

### Task 4: 建立單一項目的固定稽核與 TDD promotion 流程

**Files:**
- Create per item: `migration/381-to-850/packages/<module>/項目說明.md`
- Create per item: `migration/381-to-850/packages/<module>/manifest.json`
- Create per item: `migration/381-to-850/packages/<module>/381來源證據/source-index.json`
- Create per item: `migration/381-to-850/packages/<module>/驗證/validation.json`
- Modify as proven: 850 normalized／obfuscated Java、DB migration、control、data、client files
- Modify: `README.md`
- Modify: `migration/381-to-850/packages/_共用稽核/README.md`

**Interfaces:**
- Consumes: `claim_next_module.py` 唯一 active module、381/850 證據與前一個 completed head。
- Produces: 一個 `HOLD` 證據包，或一個可 promotion 且首頁完成的 `COMPLETED` 包。

- [ ] **Step 1: 領取並鎖定基準**

Run: `git fetch origin`，記錄 `origin/work/l1jtw85-core-fixes`、`origin/completed/l1jtw85-core-fixes`、`origin/work/l1jtw85-fast-dev-build` SHA；執行 `claim_next_module.py --actor <conversation-id>`。Expected: 只回傳 queue 最前面的項目。

- [ ] **Step 2: 完成八段證據索引**

依序記錄 381 DB、381 core、381 control、850 core、850 DB、850 control、client、隔離／回滾。每筆含實體路徑、table/class/symbol、行號或 SQL object、SHA-256 及結論；非命中記為 `NOT_PROVEN`，不能記為不存在。

- [ ] **Step 3: 寫項目 RED 測試**

在 `migration/381-to-850/packages/<module>/驗證/` 建立該項專用 gate；必須先對歷史 850 completed head 證明缺口。測試至少覆蓋正常路徑、無效資料列、控制停用、DB 失敗／rollback、重複登入或重算、已識別的 client gate。

- [ ] **Step 4: 執行 RED 並保存原始輸出摘要**

Expected: 失敗原因與該項缺口一致；若無法重現，完成 `HOLD` 包並在 queue 記錄欠缺的確切證據，不寫實作碼。

- [ ] **Step 5: 實作最小 850-first 修復**

只修改 RED 所需的 native owner／hook／DB／control；不匯入整套 381 framework。經證明需要的 client 素材才抽入 `850匯入/client`，並保存服務端呼叫鏈與 ID 映射。

- [ ] **Step 6: 編譯並抽取該項 class**

使用倉庫 production-rebuild／normalized-recovery 工具或該項明確 classpath 進行 Java 8 編譯。只複製 manifest 中 Java 所產生的 class family，包含 `$*.class`；記錄 JDK、命令、classpath 與 SHA-256。

- [ ] **Step 7: 執行 GREEN、failure-path 與 package gate**

重跑該項測試、受影響既有模組回歸、DB/control gate、client mapping gate、`validate_module_package.py` 與 `git diff --check`。Expected: 全部 exit 0。

- [ ] **Step 8: 提交 work 成果**

只加入該項精確檔案與必要共享基礎；commit subject 使用 `feat(<module-id>): ...` 或 `fix(<module-id>): ...`。push 前再次 fetch，若遠端前進則在新 head 重建。

- [ ] **Step 9: promotion 到 completed**

在最新 `completed/l1jtw85-core-fixes` 建立候選，只導入 manifest 列出的 production 檔案、驗證報告及首頁內容。重跑 Step 7；成功後普通 fast-forward push。

- [ ] **Step 10: 完成 queue 紀錄**

執行 `complete_active`，寫入 work SHA、completed SHA、CI URL、validation 路徑與首頁 anchor。重新跑 queue、package 及首頁一致性測試，然後才可領取下一項。

### Task 5: 反向批次 A — 變身狀態與箭矢

**Modules in exact order:** 01 `w_變身賦予狀態_道具`、02 `w_變身賦予狀態`、03 `w_變身箭矢特效`。

**Evidence anchors:** `DESIGNATED_ITEM_STATUS_AUDIT.md`、`TRANSFORM_ARROW_EFFECT_AUDIT.md`、381 transform/poly loaders 與 850 polymorph／equip／login owners。

- [ ] **Step 1:** 對 01 完整執行 Task 4；測試 item ownership、equip/unequip、login recompute、stack replacement 與 row-level client gate。
- [ ] **Step 2:** 對 02 完整執行 Task 4；測試 transform enter/replace/exit、logout、death、dispel 與 idempotent recompute。
- [ ] **Step 3:** 對 03 完整執行 Task 4；測試 poly→arrow GFX resolver、未知 poly fallback、control off 與 client GFX 映射。
- [ ] **Step 4:** 重跑 01–03 family regression，確認 shared transformation owner 不重複加成。

### Task 6: 反向批次 B — 變身卡片

**Modules in exact order:** 04 `w_變身卡片能力登入`、05 `w_變身卡片能力組合套卡`。

**Evidence anchors:** `TRANSFORM_CARD_LOGIN_ABILITY_AUDIT.md`、`TRANSFORM_CARD_COLLECTION_SET_AUDIT.md`、381 `C_LoginToServer.getCard(pc)`、850 quest／collection owner。

- [ ] **Step 1:** 對 04 完整執行 Task 4；測試 quest ownership、重複登入、刪卡／失效及 authoritative recompute。
- [ ] **Step 2:** 對 05 完整執行 Task 4；測試完整套卡、缺一卡、重複卡、套卡變更與 Base Card/Set 去重。
- [ ] **Step 3:** 重跑 04–05 family regression，證明登入能力與組合能力不重疊疊加。

### Task 7: 反向批次 C — 炫色、隨機能力與持續特效

**Modules in exact order:** 06 `w_隨機能力炫色武器`、07 `w_隨機能力炫色防具`、08 `w_隨機能力炫色名稱`、09 `w_裝備持續特效`、10 `w_炫色_素質設定`、11 `character_炫色_記錄資料`。

**Evidence anchors:** `RANDOM_COLOR_ABILITY_AUDIT.md`、`SPECIAL_COLOR_ATTRIBUTE_AUDIT.md`、`EQUIPMENT_CONTINUOUS_EFFECT_AUDIT.md`、850 item-instance persistence/equip lifecycle。

- [ ] **Step 1:** 對 06 執行 Task 4；測試武器 objid 持久化、reroll、交易、裝卸及戰鬥 proc。
- [ ] **Step 2:** 對 07 執行 Task 4；測試防具多件聚合、裝卸、交換與重算。
- [ ] **Step 3:** 對 08 執行 Task 4；測試名稱格式、未知 affix、client Text/色碼 gate。
- [ ] **Step 4:** 對 09 執行 Task 4；若 runtime owner 仍未證明，以 `HOLD` 證據包結束後才進 10。
- [ ] **Step 5:** 對 10 執行 Task 4；測試定義 lookup、上下限、未知能力及控制停用。
- [ ] **Step 6:** 對 11 執行 Task 4；測試角色／物品 ownership、唯一鍵、重登與刪除清理。
- [ ] **Step 7:** 重跑 06–11 family regression，驗證共享 modifier representation 與分離 lifecycle owner。

### Task 8: 反向批次 D — 五神紋樣

**Modules in exact order:** 12 `馬普勒紋樣屬性設定`、13 `殷海薩紋樣屬性設定`、14 `帕格里奧紋樣屬性設定`、15 `沙哈紋樣屬性設定`、16 `伊娃紋樣屬性設定`。

**Evidence anchors:** 381 五份 DDL/data/loader/hook、850 character modifier/persistence、實際 client UI/protocol 資源。

- [ ] **Step 1:** 對 12 執行 Task 4；固定共用 PatternOwner 介面前先證明馬普勒欄位與生命週期。
- [ ] **Step 2:** 對 13 執行 Task 4；測試殷海薩與既有 PatternOwner 的欄位差異及 rank boundary。
- [ ] **Step 3:** 對 14 執行 Task 4；測試帕格里奧戰鬥屬性與 recompute。
- [ ] **Step 4:** 對 15 執行 Task 4；測試沙哈遠程／敏捷語意與 skill/item ID 映射。
- [ ] **Step 5:** 對 16 執行 Task 4；測試伊娃魔法／回復語意與 modifier 移除。
- [ ] **Step 6:** 重跑 12–16，確認五份資料隔離、共用 owner 不混用 table ownership；確認 client gate 可否分離。

### Task 9: 反向批次 E — 屬性、排程、爆氣、附魔與道具狀態

**Modules in exact order:** 17 `w_屬性強化系統`、18 `w_鐘點怪物施放`、19 `w_道具爆氣系統`、20 `w_道具附魔系統`、21 `w_道具狀態`。

**Evidence anchors:** `WEAPON_ATTRIBUTE_ENHANCEMENT_AUDIT.md`、`SCHEDULED_MONSTER_EVENT_SPAWN_AUDIT.md`、`ITEM_OUTBURST_AUDIT.md`、`ITEM_STATUS_TIMED_BUFF_AUDIT.md`。

- [ ] **Step 1:** 對 17 執行 Task 4；測試 per-weapon objid、proc chance boundary、屬性抵抗與交易／刪除。
- [ ] **Step 2:** 對 18 執行 Task 4；測試時間窗、重啟補償、重複觸發、spawn cleanup，禁止 donor 10 秒 polling loop。
- [ ] **Step 3:** 對 19 執行 Task 4；測試 toggle、資源扣除、移動／死亡／登出取消及 exactly-once drain。
- [ ] **Step 4:** 對 20 執行 Task 4；測試材料交易、成功／失敗、保護語意、objid 狀態及 client 顯示 gate。
- [ ] **Step 5:** 對 21 執行 Task 4；測試 timed modifier persistence、replacement、expiry、重登及 apply-once。
- [ ] **Step 6:** 重跑 17–21 combat/item-session regression。

### Task 10: 反向批次 F — 道具升級、狩獵任務與威望

**Modules in exact order:** 22 `w_道具升級系統`、23 `w_道具升級`、24 `w_狩獵怪物任務_系統`、25 `w_狩獵怪物任務_地圖`、26 `w_狩獵怪物任務`、27 `w_威望設置`、28 `w_威望怪物`、29 `w_威望名稱自訂`。

**Evidence anchors:** `ITEM_UPGRADE_SYSTEMS_AUDIT.md`、`HUNTING_QUEST_FAMILY_AUDIT.md`、`PRESTIGE_DEEP_AUDIT.md`。

- [ ] **Step 1:** 對 22 執行 Task 4；先界定系統設定、保護、機率及 transaction owner。
- [ ] **Step 2:** 對 23 執行 Task 4；區分普通 craft 轉換與 in-place objid/template/state 路徑。
- [ ] **Step 3:** 對 24 執行 Task 4；建立 hunting quest owner、狀態轉移及持久化契約。
- [ ] **Step 4:** 對 25 執行 Task 4；測試 map allow/deny、離圖、重登與未知 map。
- [ ] **Step 5:** 對 26 執行 Task 4；測試 kill attribution、party/summon、quota、領獎 exactly-once。
- [ ] **Step 6:** 對 27 執行 Task 4；建立 prestige rank/threshold 設定與 authoritative recompute。
- [ ] **Step 7:** 對 28 執行 Task 4；測試 kill attribution、死亡調整、重試及持久化。
- [ ] **Step 8:** 對 29 執行 Task 4；測試 rank→名稱、未知 rank、Text/client gate。
- [ ] **Step 9:** 重跑 22–29，分別驗證 item-upgrade、hunting、prestige family invariants。

### Task 11: 反向批次 G — 血盟、成就、合成與天賦

**Modules in exact order:** 30 `w_血盟等級`、31 `w_血盟能量怪物`、32 `w_血盟技能`、33 `w_成就圖鑑收集獎勵`、34 `w_成就圖鑑收集設定`、35 `w_天m合成系統`、36 `character_天賦紀錄`。

**Evidence anchors:** `CLAN_LEVEL_SKILL_FAMILY_AUDIT.md`、`CLAN_ENERGY_MONSTER_AUDIT.md`、`ACHIEVEMENT_COLLECTION_AUDIT.md`、`TIANM_SYNTHESIS_AUDIT.md`。

- [ ] **Step 1:** 對 30 執行 Task 4；測試 ClanStateOwner、升級、入退盟、解散及登入重算。
- [ ] **Step 2:** 對 31 執行 Task 4；runtime/control owner 未證明時建立完整 `HOLD` 包，不以空資料跳過。
- [ ] **Step 3:** 對 32 執行 Task 4；測試學習／忘技、權限、入退盟及與等級 modifier 去重。
- [ ] **Step 4:** 對 33 執行 Task 4；測試 reward eligibility、領取 CAS、重試與 exactly-once。
- [ ] **Step 5:** 對 34 執行 Task 4；測試 collection definition、identity mapping、增刪及 recompute。
- [ ] **Step 6:** 對 35 執行 Task 4；測試 N-of-tier、pity、失敗返還、ownership 與並發合成。
- [ ] **Step 7:** 對 36 執行 Task 4；測試 talent allocation、上限、reset、重登及角色刪除。
- [ ] **Step 8:** 重跑 30–36 的 clan／achievement／synthesis／talent 回歸。

### Task 12: 反向批次 H — 轉生、隨身服務與贖回

**Modules in exact order:** 37 `william_pc_轉生經驗`、38 `william_pc_轉生_giveitem`、39 `william_pc_轉生`、40 `w_隨身祭司`、41 `w_衝裝贖回記錄`。

**Evidence anchors:** 381 對應 DDL/loader/NPC/action，850 EXP/level/item grant/NPC/transaction owners。

- [ ] **Step 1:** 對 37 執行 Task 4；測試轉生次數→EXP 曲線、邊界、溢位與重算。
- [ ] **Step 2:** 對 38 執行 Task 4；測試 reward mapping、容量不足、DB rollback、重試及 exactly-once。
- [ ] **Step 3:** 對 39 執行 Task 4；測試資格、角色狀態轉換、三表以上交易邊界與失敗恢復。
- [ ] **Step 4:** 對 40 執行 Task 4；測試召喚／解散、費用、地圖限制、登出與 AI lifecycle。
- [ ] **Step 5:** 對 41 執行 Task 4；測試衝裝失敗紀錄、objid/item identity、贖回交易、重複請求及過期。
- [ ] **Step 6:** 重跑 37–41 economic/identity regression。

### Task 13: 反向批次 I — 死亡奪寶與裝備強化族群

**Modules in exact order:** 42 `w_敵人死亡奪寶`、43 `w_過安定武器`、44 `w_過安定防具`、45 `w_裝備總加成能力`、46 `w_裝武強化lv`、47 `w_指定道具賦予狀態`。

**Evidence anchors:** `EQUIPMENT_TOTAL_BONUS_AUDIT.md`、`DESIGNATED_ITEM_STATUS_AUDIT.md`、381 death/spawn/enchant/equip hooks。

- [ ] **Step 1:** 對 42 執行 Task 4；測試可視區邊緣 spawn、target attribution、掉落／奪寶 ownership、despawn 與地圖限制。
- [ ] **Step 2:** 對 43 執行 Task 4；測試武器安定值以上 boundary、破壞／保護、廣播與 DB rollback。
- [ ] **Step 3:** 對 44 執行 Task 4；測試防具安定值、套裝聚合、破壞／保護及重登。
- [ ] **Step 4:** 對 45 執行 Task 4；runtime owner 未證明時使用 `HOLD`，證明後測試 equip aggregate authoritative recompute。
- [ ] **Step 5:** 對 46 執行 Task 4；測試裝武等級 mapping、強化變更、交易、裝卸及上限。
- [ ] **Step 6:** 對 47 執行 Task 4；測試指定 template/objid、equip lifecycle、替換、client-dependent row gate。
- [ ] **Step 7:** 重跑 42–47 equipment/death family regression。

### Task 14: 反向批次 J — 掉落、融合與死亡復活

**Modules in exact order:** 48 `全地圖掉落`、49 `w_指定地圖掉落`、50 `w_物品融合db化`、51 `w_怪物死亡召喚`。

**Evidence anchors:** `ITEM_FUSION_DB_AUDIT.md`、`MONSTER_DEATH_SPAWN_AUDIT.md`、850 drop owner/spawn/craft/transaction paths。

- [ ] **Step 1:** 對 48 執行 Task 4；測試全圖規則、boss/normal filter、party/summon attribution、控制停用與重複掉落。
- [ ] **Step 2:** 對 49 執行 Task 4；測試 map-specific override、全圖規則優先序、未知 map 與多規則疊加。
- [ ] **Step 3:** 對 50 執行 Task 4；runtime/schema 未證明時建立 `HOLD` 包；證明後測試材料原子扣除、輸出、失敗及重試。
- [ ] **Step 4:** 對 51 執行 Task 4；測試普通怪 1%、BOSS 0.5%、角色周邊復活、每次能力 +1.5%、強化上限 100%、每次復活掉落率 +1%、遞迴觸發與 cleanup。百分比語意以企劃為需求，實際計算用整數／定點測試避免浮點漂移。
- [ ] **Step 5:** 重跑 48–51 drop/spawn/economy regression。

### Task 15: 反向批次 K — D 系列菁英怪與技能等級化

**Modules in exact order:** 52 `D系列的怪物菁英化系統`、53 `技能等級化`。

**Evidence anchors:** `I:\天堂企劃.txt`、381 monster/skill/affix evidence、850 monster spawn/combat/drop/skill owners。

- [ ] **Step 1:** 對 52 執行 Task 4；測試小怪 HP ×1.3、BOSS HP 不變、10% 生成、掉落率 ×2、世界難度增加詞綴、物理／魔法抗性分離、召喚隊友 AI 傷害抗性及相同 spawn 不重複 elite 化。前後綴數量與世界難度的完整對照沒有證據時，該欄保持 `NOT_PROVEN`，不自行編表。
- [ ] **Step 2:** 對 53 執行 Task 4；先查支線既有計畫與 381/850 skill schema，再測試白／綠／藍／紅／紫／金階級順序、技能書 identity、學習／替換、紫色合成金色的原子材料處理及失敗結果。合成機率與取得來源若企劃未給數值，維持 DB 控制且預設不啟用，不能猜值。
- [ ] **Step 3:** 重跑 52–53 及所有 shared combat/modifier regressions。

### Task 16: 全量一致性、首頁、fast-dev 映射與 A2A 驗證交接

**Files:**
- Modify: `README.md`
- Modify: `migration/381-to-850/packages/_共用稽核/README.md`
- Modify: `migration/381-to-850/packages/reverse-order.json`
- Create: `migration/381-to-850/packages/FINAL_VALIDATION.md`
- Create: temporary handoff via `handoff` skill

**Interfaces:**
- Consumes: 53 個 queue records、packages、work/completed commits 與 CI runs。
- Produces: 可由新對話獨立重跑的完成證據與 A2A prompt。

- [ ] **Step 1: 驗證 queue 與 packages**

Run queue、claim、package 全測試。Expected: 53 項皆為 `COMPLETED` 或有明確 `HOLD` 證據；不得存在 `ACTIVE`、漏號、混檔、雜湊錯誤或未列 import。

- [ ] **Step 2: 驗證首頁與 canonical ledger**

逐項比對 queue 的 homepage anchor、work/completed SHA、CI URL、decision、server level、client gate；首頁必須包含問題與解決方法，`HOLD` 不得描述為已修復。

- [ ] **Step 3: 驗證 fast-dev path map**

在最新 `origin/work/l1jtw85-fast-dev-build` 執行 package map／repair sync 測試，確認每項邏輯檔能映射到新放置方式，且不產生第二套核心內容。

- [ ] **Step 4: 全量回歸與遠端包含關係**

執行所有 module validation、production-rebuild 可用 gate、DB/control schema gate、客戶端 mapping gate及 GitHub Actions；記錄精確命令、退出碼與 run URL。確認所有 completed SHA 可由遠端完成支線到達。

- [ ] **Step 5: 建立 A2A**

使用 `handoff` skill 產生暫存交接，要求新對話 fetch 遠端、獨立驗證 53 項順序與包隔離、抽查 Java/class 可重建性、重跑 DB/control/runtime/client gate，並確認主工作區使用者檔案未被修改。

- [ ] **Step 6: 最終提交與推送**

在遠端未前進的前提下提交 `FINAL_VALIDATION.md`、首頁、ledger 與 queue 完成紀錄，普通 push；遠端前進則回到 Step 1 重建驗證。

---

## Completion Criteria

1. 53 個反向項目依固定順序逐項處理，沒有跳號或同時 active。
2. 每項都有獨立資料夾、`項目說明.md`、manifest、來源證據及驗證資料。
3. 每個 production Java/class 只屬於該項目、class 可由記錄的 Java/toolchain 重建。
4. 每項都完成控制、DB、核心及必要客戶端查證；未證明事項明確維持 `HOLD`／`NOT_PROVEN`。
5. 通過驗證的項目已 promotion 到 `completed/l1jtw85-core-fixes`，並在首頁記錄問題與解法。
6. `work/l1jtw85-fast-dev-build` 可用 path map 消費相同成果，沒有功能分叉。
7. A2A 交接能讓另一對話不信任本對話的 PASS 標籤，仍可獨立得到相同結論。
