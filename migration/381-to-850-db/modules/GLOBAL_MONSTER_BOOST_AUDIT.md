# L381 `w_全服怪物提升` → L1JTW8.5/850 遷移審計

## 結論

- `STATUS=PASS`
- `MODULE=w_全服怪物提升`
- `LEVEL=L4`
- `CORE_DEP=YES`
- `DB_DEP=YES`
- `CLIENT_DEP=NOT_PROVEN`
- 850 目前沒有可由指定資料表直接驅動的等價功能。
- 381 提供的檔案只有一筆資料列，沒有在可檢查的 381 Java 原始碼中找到 loader、事件註冊或 runtime hook；單獨匯入該 SQL 不會證明怪物能力已被提升。

## 審計範圍與證據

| 項目 | 證據 | 判定 |
|---|---|---|
| L381 主程式 | `I:\L381\Atu-381伺服器端\src`；以表名、模組名及欄位名作 targeted search | 未找到 `w_全服怪物提升`、`hp_rate`、`mp_rate`、`mr_rate`、`ac_add`、`dmg_rate` 的 Java 使用點 |
| L381 DB package | `I:\L381\Atu-381伺服器端\DB\381_DB_AI用\w_全服怪物提升_202609221205.sql:1-2` | 精確 SQL 為 `INSERT INTO atu381.w_全服怪物提升 (id,hp_rate,mp_rate,mr_rate,ac_add,dmg_rate) VALUES (1,100,100,100,0,100);` |
| Schema | 指定 DB package 檔案只有 INSERT，沒有 `CREATE TABLE` 或欄位註解 | table DDL 未在指定 package 提供；不能推斷缺失 schema/type |
| 850 DB | `origin/completed/l1jtw85-core-fixes:db/8.5.sql`；exact search `w_全服怪物提升`、`hp_rate`、`dmg_rate` 無命中 | 850 DB 沒有已確認的同名表或欄位 |
| 850 startup | `recovery/normalized-src-vf/l1r/ai/GameServer.java:117-192`（目標分支） | 啟動明確初始化 `NpcTable`、`SpawnTable` 等；沒有怪物提升 table 初始化 |
| 850 NPC loader | `recovery/normalized-src-vf/l1r/ao/NpcTable.java:36-116` | 只執行 `SELECT * FROM npc`，將 npc 的 hp/mp/ac/mr/base_damage/random_damage 等欄位載入 `L1Npc` |
| 850 spawn/runtime | `recovery/normalized-src-vf/l1r/ao/SpawnTable.java:42-109,220-288` | 從 `spawnlist` 建立 spawn；runtime 建立 `L1NpcInstance`，對 `L1MonsterInstance` 僅套用既有 spawn flags/通用初始化，沒有全服倍率表查詢 |
| 850 monster class | `recovery/normalized-src-vf/l1r/ap/L1MonsterInstance.java` | 類別存在，但 target exact search 未找到本模組欄位或倍率 hook |

## DB schema / row

已知資料只有：

```sql
INSERT INTO atu381.w_全服怪物提升
  (id, hp_rate, mp_rate, mr_rate, ac_add, dmg_rate)
VALUES
  (1, 100, 100, 100, 0, 100);
```

此 row 的數值代表欄位值，但沒有可由來源證實的計算語意、單位、適用範圍或更新策略。指定 DB package 沒有 CREATE TABLE，因此不補寫推定 DDL。

## Loader、startup 與 runtime trace

### L381

在 `I:\L381\Atu-381伺服器端\src` 內對模組表名、欄位名及相關中文名做 targeted search，沒有命中 Java loader、table class、startup registration、reload command 或 monster hook。故目前證據只能確認「DB package 存在」，不能確認「L381 runtime 曾消費此表」。

### 850

850 的已確認路徑是：

1. `GameServer` 啟動 `NpcTable`，再啟動 `SpawnTable`。
2. `NpcTable` 由 `npc` 載入模板欄位，包括 hp、mp、ac、mr、base_damage、random_damage。
3. `SpawnTable` 由 `spawnlist` 找到 `npc_templateid`，再透過 `NpcTable` 建立 instance。
4. `SpawnTable` 的 instance 建立區段只處理座標、方向、spawn effect、world registration、monster 通用 flag；沒有讀取 `w_全服怪物提升`。

因此，若要保留此模組功能，至少需要可驗證的通用 monster spawn/creation hook，再在該 hook 中讀取已載入的倍率設定並對 hp/mp/mr/ac/damage 做一致套用。這屬於 core/runtime 變更，不是 DB-only 遷移。

## NPC/monster filters、stat modifications、map filters

- L381：未找到可證實的 NPC type filter、map filter、boss exclusion、spawn exclusion 或 stat modification code。
- 850：現有 `NpcTable` 以 `impl` 識別模板類型，`SpawnTable` 以 `L1MonsterInstance` 做部分通用處理；未找到本模組所需的全服 filter 或倍率套用。
- 因此不能宣稱「全服」包含哪些 instance，也不能宣稱是否排除 boss、pet、summon、event mob、dungeon map 或特定 map。

## Config switches / persistence / client protocol

- 381 source targeted search 未找到本模組專用 config key、reload switch 或 command。
- 850 target startup/config evidence 未找到本模組專用 switch。
- DB row 是設定資料，不是角色或怪物持久化狀態；目前沒有 loader，故 persistence lifecycle 未建立。
- 此功能只涉及 server-side monster stats。沒有找到 client opcode、packet、UI、resource 或 protocol field；`CLIENT_DEP=NOT_PROVEN`，不是 YES。之所以保留 NOT_PROVEN，是因為 381 runtime hook 本身不存在於可檢查來源，不能由「未搜尋到」推導完整 client independence。

## 850 等價功能判定

判定：**未確認等價功能**。

850 的 `npc` 表已原生存放每個 NPC 的基礎 hp/mp/ac/mr/damage 欄位，並由 `NpcTable` 載入；這是逐 NPC 靜態能力，不是 `w_全服怪物提升` 的全服倍率設定。target branch exact search 也沒有找到該表名、欄位名或等價 monster scaling table/hook。

## 遷移分類與 package boundary

`L4`：需要新增或修改核心 runtime 的 monster creation/spawn 套用點，且來源缺少已證實的 loader、schema DDL、filters 與語意。不可安全分類為 L1 DB-only，也不是僅加入 generic reusable hook 就能完成，因為 hook 的倍率語意與範圍尚未由 381 證據定義。

建議 package boundary（僅供後續實作規劃，本次不實作）：

- DB package：保留單一 migration SQL，限定 `w_全服怪物提升` 的 DDL/seed/update；可獨立 import/remove。
- Adapter/table：獨立讀取該表並暴露 immutable config；不存在資料表時應有明確 disabled/error 行為，不把 SQL 查詢散落到 monster core。
- Core integration：只提供一個明確的 monster-spawn stat policy hook；不要把 DB schema 或中文表名嵌入 `L1MonsterInstance`。
- Filters/policy：在 adapter/policy 層明確定義 L1Monster、boss、summon、event、map 範圍後才可實作。

## Blockers

1. 指定 381 DB package 沒有 CREATE TABLE，只有 INSERT；無法在不猜測的前提下確認 exact schema/types/constraints。
2. 381 可檢查 Java source 沒有 table loader 或 runtime consumer；無法證明原模組實際生效點。
3. 欄位倍率/加值的計算語意與套用時機未提供。
4. NPC/monster、boss、summon、event、map filter 未提供。
5. 850 沒有現成等價 table/hook，且本次禁止修改 production core。

## 最終欄位

```text
STATUS=PASS
MODULE=w_全服怪物提升
LEVEL=L4
CORE_DEP=YES
DB_DEP=YES
CLIENT_DEP=NOT_PROVEN
TARGET_DOC=migration/381-to-850-db/modules/GLOBAL_MONSTER_BOOST_AUDIT.md
COMMIT=pending
BLOCKERS=missing 381 DDL; missing verified 381 loader/runtime hook; undefined stat/filter semantics; no 850 equivalent hook
```
