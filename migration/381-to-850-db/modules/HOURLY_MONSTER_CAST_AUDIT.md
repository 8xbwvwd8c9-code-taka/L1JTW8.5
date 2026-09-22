# L381 `w_鐘點怪物施放` → L1JTW8.5 / 850 審計

## Gate

- `STATUS=BLOCKED`
- `MODULE=w_鐘點怪物施放`
- `LEVEL=L1`
- `PRODUCTION_PORT=NO`
- 判定原則：L381 僅作 donor/reference；未將任何 Java、核心或 live DB 變更帶入 850。

## Required validation

| 欄位 | 結果 | 證據／說明 |
|---|---|---|
| `SOURCE_ROWS` | `20` | donor exact split SQL 共 20 筆 tuple；來源：`L381/DB/381_DB_AI用/w_鐘點怪物施放_202609221205.sql`。 |
| `SOURCE_SCHEMA` | `NOT_PROVEN` | SQL 只有 `INSERT INTO atu381.w_鐘點怪物施放`，沒有 `CREATE TABLE` 或 `ALTER TABLE`；850 也沒有該表的已證明 schema。 |
| `DONOR_RUNTIME` | `PROVEN` | `L381/src/com/lineage/william/NowTimeSpawn.java`：singleton constructor 呼叫 `loadChackDrop()`，執行 `SELECT * FROM w_鐘點怪物施放`，再以 `fillChackDrop` 載入 `_ItemIdIndex`。 |
| `TRIGGER` | `PROVEN`（donor） | SQL 的時間欄位為 `限制星期`、`時`、`分`；runtime loader 明確讀取 `時`、`分`、`限制星期`。實際呼叫／每分鐘觸發器未在本次窄範圍證據中完成閉合。 |
| `TIMER/SCHEDULE` | `NOT_PROVEN`（850） | donor runtime 本身只載入設定；L381 另有 `GeneralThreadPool`／time-controller 基礎，但未把呼叫者閉合為本模組。850 可見通用 `GeneralThreadPool` 排程框架，沒有 `NowTimeSpawn` 或該表專用 scheduler 的證據。 |
| `SPAWN_OR_SKILL` | `PROVEN`（donor spawn） | 欄位含 `NPC編號`、`數量`、座標、地圖、`隨機中心生怪`、`存在時間`；模組語意是定時 spawn/公告，不是可證明的 mob skill cast。實際 spawn hook 呼叫者未閉合。 |
| `PERSISTENCE` | `NOT_PROVEN` | 只有 SELECT loader 證據；沒有可確認的 850 schema、寫入流程或 runtime persistence。 |
| `CLIENT_DEP` | `NOT_PROVEN` | SQL 的 `公告內容`、`特殊公告`、`畫面特效`、傳送門欄位可能需要封包／資源，但本次未找到可證明的 850 對應契約；不假設 ID 相容。 |
| `850_NATIVE_EQUIVALENT` | `NO` | 850 repository tree 未找到 `NowTimeSpawn`、`w_鐘點怪物施放` 或同名專用 runtime；僅存在通用 `GeneralThreadPool` 與既有 spawn/skill 基礎，不能視為功能等價物。 |
| `LEVEL` | `L1` | 功能邊界可由 donor SQL + loader 證明，但 schema、850 runtime ownership、trigger closure 不完整；依政策不得升級為可移植。 |
| `PRODUCTION_PORT` | `NO` | 僅文件審計；未修改 production/core Java、live DB、UI 或 client resource。 |

## Donor facts

SQL 20 筆資料涵蓋每日（`限制星期=-1`）14:00–21:55 的公告與怪物配置；其中包含 NPC ID、數量、地圖 89、座標、隨機半徑、公告文字，以及部分存在時間（例如 1800、1200 秒）。這些數值屬 donor data，未驗證 850 的 NPC／map／resource ID，不能直接匯入。

`NowTimeSpawn` 的 loader 讀取欄位：`sn`、`時`、`分`、`NPC編號`、`X座標`、`Y座標`、`地圖編號`、`數量`、`公告內容`、`特殊公告`、`畫面特效`、`是否為傳送門`、`x`、`y`、`m`、`存在時間`、`限制星期`、`隨機中心生怪`。

## 850 comparison

850 目前可證明的 native 基礎包括：

- `recovery/normalized-src-vf/l1r/bi/GeneralThreadPool.java`：通用 scheduled executor。
- `recovery/normalized-src-vf/l1r/aq/L1Spawn.java`、`L1SpawnBoss.java`、`L1SpawnEffect.java`：既有 spawn 類別。
- `recovery/normalized-src-vf/l1r/bg/L1SkillTimerCreator.java`、`L1SkillTimer__obf_c.java`、`L1SkillTimer__obf_d.java`、`L1SkillTimer__obf_f.java`：既有 skill timer 類別。

本次未找到上述基礎與 `w_鐘點怪物施放` 的已閉合 loader、schedule、spawn/cast hook、NPC ownership 或 persistence 關聯；因此不可宣稱 850-native equivalent。

## Blockers / next action

- `BLOCKERS=指定 850 SQL 不在本地工作樹；donor split 沒有 CREATE schema；850 沒有同名 table/runtime ownership 的閉合證據；NPC/map/skill/client ID 未驗證。`
- `NEXT=NONE`
- 後續若要進入 L2/L3，必須先提供或確認 850 schema 與 850 runtime 的實際 ownership，再另立變更；本審計不創造 schema、不匯入 donor DB、不修改 production code。
