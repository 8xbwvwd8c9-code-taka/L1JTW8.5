# L381 `w_血盟技能` 深度遷移審計

## 結論

- **等級：L4**。
- 這不是 DB-only 模組。它需要 L1Clan、ClanTable、玩家 stat API、登入流程、NPC action/HTML 與攻擊/魔法 runtime hook 的共同修改。
- 850 目標資料庫及 `completed/l1jtw85-core-fixes` 來源中，未找到 `w_血盟技能`、`clanskill_id`、`clanskill_lv`、`RewardClanSkillsTable` 或 `ClanSkillDBSet` 的等價物。
- L381 的移除生命週期未被證明；離會、踢出、解散只清玩家的 clan id/name，沒有看到反向扣除已套用 stat 的程式。

## 來源與目標

來源：

- L381 主程式：`I:\L381\Atu-381伺服器端主\src`
- L381 DB：`I:\L381\Atu-381伺服器端主\DB\381_DB_AI用\w_血盟技能_202609221205.sql`
- 目標 authority：`I:\L1JTW8.5` Git ref `completed/l1jtw85-core-fixes`
- 目標 DB：`I:\L1JTW8.5\db\無使用給AI檢查用資料庫DB`

## DB schema 與資料

### 技能定義表

L381 明確使用表 `w_血盟技能`，欄位為：

`ClanSkillId`, `ClanSkillLv`, `ClanSkillName`, `Note`, `Material`, `MaterialCount`, `MaterialLevel`, `CheckLvturn`, `CheckLevel`, `AddMaxHp`, `AddMaxMp`, `AddHpr`, `AddMpr`, `AddStr`, `AddCon`, `AddDex`, `AddInt`, `AddWis`, `AddCha`, `ReductionDmg`, `ReductionMagicDmg`, `AddWater`, `AddWind`, `AddAc`, `AddSp`, `AddMr`, `AddDmg`, `AddBowDmg`, `AddHit`, `AddBowHit`, `AddFire`, `AddEarth`。

資料有三個技能 ID，每個到 10 級。材料資料使用 item ID `40308`，首級另有 `1`、`50000000`、`70000000` 等材料欄值；這些欄位的實際用途由 `Npc_clan` 購買流程讀取。

### `clan_data` 欄位

血盟技能持久化欄位是：

- `clanskill_id`：目前技能 ID。
- `clanskill_lv`：目前技能等級。
- `clanskill`：另一套計時型血盟技能的啟用旗標；不是 DB 版 stat 套用本身，但共用 `L1Clan`、`ConfigClan` 與 `clan_data`。
- `skilltime`：計時型血盟技能時間欄位；不應與 DB 版技能進度欄位混用。

`ClanTable.load()` 以 `SELECT * FROM clan_data` 讀取上述欄位；`updateClanSkill()` 只更新 `clanskill_id`、`clanskill_lv`。建立血盟及一般 `updateClan()` SQL 沒有寫入 `clanskill_id`、`clanskill_lv`，因此新建血盟依賴 DB 預設值，且此設計不能被當作完整的 schema migration contract。

850 `clan_data` schema 只有核心血盟欄位及 `watch_clanid`，沒有上述四個血盟技能欄位；目標 DB 也沒有 `w_血盟技能` 表。

## loader、runtime state 與啟動

- `RewardClanSkillsTable` 讀取 `w_血盟技能`，建立 `L1ClanSkills`。template 保存材料、等級條件與所有 stat/effect 欄位。
- `ClanSkillDBSet.execute()` 初始化 `RewardClanSkillsTable`，建立技能名稱/說明快取並把 `START` 設為 true。
- `ClanSkillDBSet` 與另一套 `ClanSkillSet` 互斥；兩者都存在於 `Npc_clan`，不能同時啟動。
- `ConfigClan.clanskill` 由設定檔讀取，`GameServer` 以此註冊/啟動血盟技能相關事件。這個設定名稱與計時型血盟技能共用，移植時必須拆出模組專屬開關或明確保留相容層。
- `L1Clan` 保存 `ClanSkillId`、`ClanSkillLv`；另保存 `_clanskill`、`_skilltime`。後兩者屬另一套計時型功能，不能拿來替代 DB 版技能選擇狀態。

## 購買、學習、重置與保存

`Npc_clan` 的 DB 版 action 路徑：

1. 讀取 `pc.getClan().getClanSkillId()` / `getClanSkillLv()`。
2. 以 `RewardClanSkillsTable.getClanSkillsList(id, level)` 取得下一級資料。
3. 讀取 `Material`、`MaterialCount`、`MaterialLevel`，檢查並扣除材料/條件。
4. 設定 `L1Clan.setClanSkillId()`、`setClanSkillLv()`。
5. 呼叫 `ClanReading.get().updateClanSkill(clan)`，更新 `clan_data.clanskill_id`、`clan_data.clanskill_lv`。
6. 重置 action 將兩個 runtime 欄位設為 0，再呼叫同一個保存方法。

這證明技能所有權/進度是**血盟級**持久化，不是角色 quest 或 character_other 的技能所有權。

## 登入與 stat/effect 套用

`C_LoginToServer` 在角色確認屬於血盟後呼叫 `ClanSkillDBSet.add(pc)`。該方法：

- 讀血盟的 skill ID/level；
- 讀 `RewardClanSkillsTable`；
- 檢查 `CheckLvturn`、`CheckLevel`；
- 直接對玩家 runtime 呼叫增量 API；
- 發送 `S_OwnCharStatus`、HP/MP、SP/MR 封包及技能說明訊息。

SQL 定義中可被修改的 effect 欄位及對應 API 為：

- 最大 HP：`addMaxHp`
- 最大 MP：`addMaxMp`
- HP 回復：`addHpr`
- MP 回復：`addMpr`
- STR/DEX/CON/INT/WIS/CHA：對應 `addStr/addDex/addCon/addInt/addWis/addCha`
- AC：以 `addAc(-AddAc)` 改善防禦值
- SP：`addSp`
- MR：`addMr`
- 近戰傷害/弓傷害：`addDmgup`、`addBowDmgup`
- 近戰命中/弓命中：`addHitup`、`addBowHitup`
- 傷害減免/魔法傷害減免：`add_reduction_dmg`、`add_magic_reduction_dmg`
- 四屬性抗性：`addWater/addWind/addFire/addEarth`

本次 SQL 實際非零效果可核實為：

- ID 1：STR、DEX、INT、`AddDmg`、`AddBowDmg`，各隨等級增加。
- ID 2：最大 HP、最大 MP，最高各 5000。
- ID 3：`ReductionDmg`、`ReductionMagicDmg`、MR，最高分別 20、20、50。

## 移除、重算與生命週期證據

### 已證明的行為

- relog：登入時再次呼叫 `ClanSkillDBSet.add(pc)`，因此會再次加值。
- 技能重置：只清血盟的 skill ID/level 並保存；未看到對線上成員逐一反向扣除 stat。
- `ClanSkillTimer`：只處理 `_clanskill`/`skilltime` 的計時旗標，不處理 DB 版 `ClanSkillId`/`ClanSkillLv`，不能視為 stat remove/recalc。

### 未找到的反向路徑

在 `C_LeaveClan`、`C_BanClan`、`L1ClanJoin` 及登入清理路徑中，已找到清除 `clanid`、`clanname` 的操作，但未找到與 `ClanSkillDBSet.add` 對稱的負向 stat 操作、快照扣除或完整 stat 重算。`C_LoginToServer` 的 `ClanSkillDBSet.add` 反而證明 relog 會重新套用。

因此以下情境目前不能宣稱正確：

- 玩家離會後立即移除已加 stat；
- 被踢後立即移除已加 stat；
- 血盟解散後移除線上成員 stat；
- 技能重置後移除線上成員 stat；
- 重複登入不累加舊值。

這是遷移 blocker；850 adapter 必須以可重建的 modifier ledger、login-time baseline/recalc，或明確的 inverse/remove API 解決，而不能直接複製 `ClanSkillDBSet.add`。

## NPC、action、設定與 client/UI 依賴

- handler：`Npc_clan`。
- DB 版技能 action 讀取、查看、購買、重置及說明都集中在此 handler。
- 已核實 HTML template action `j_c2`；其他 `Npc_clan` 分支使用該 handler 的 NPC action 字串與 `S_NPCTalkReturn`。
- 需求包含 server-side HTML/template 資源與客戶端可顯示的 NPC 對話協定；850 目標中未找到可證明存在的同名技能 HTML 資源。
- 因此 client/UI 依賴為 **YES（HTML 資源需逐項移植驗證）**，不能假設 850 已有 UI。

## 850 等價性

以目標 DB、`completed/l1jtw85-core-fixes` normalized source 做 targeted exact search：未找到 `w_血盟技能`、`clanskill`、`ClanSkillDBSet`、`RewardClanSkillsTable` 或 `ClanSkill` 等價框架。850 的 `clan_data` 也沒有本模組所需欄位。

結論是：850 沒有已證明的等價血盟技能框架；本模組需新增資料表/欄位、loader、runtime modifier hook、NPC action 與 UI 資源。

## 獨立安裝、回滾與邊界

建議把模組邊界定義為：

1. 新增獨立技能定義表，例如以明確前綴命名，不替換 850 `clan_data`。
2. 以獨立血盟技能狀態表保存 clan ID、skill ID、level；若採 `clan_data` additive columns，必須由本模組 migration 單獨擁有並可回滾。
3. `L1Clan` 只接一個 optional provider/adapter，不與 `w_血盟等級` 的 level/contribution 欄位共用 owner。
4. 加入可逆的 member modifier apply/remove/rebuild service；離會、踢出、解散、重置及登入均走同一服務。
5. NPC handler、config key、HTML 資源及啟動註冊列為模組檔案；停用模組時不應刪除核心血盟資料。

目前尚未達到可安全獨立安裝的狀態，原因不是資料表難度，而是 stat remove/recalc contract 缺失。

## 驗證矩陣

| 項目 | 結果 | 證據 |
|---|---|---|
| DB schema | PASS（L381）/ FAIL（850 直接相容） | `w_血盟技能`、`clanskill_id`、`clanskill_lv` |
| loader/storage | PASS | `RewardClanSkillsTable`、`ClanTable`、`updateClanSkill` |
| learn/reset | PASS | `Npc_clan` action 與 `ClanReading.updateClanSkill` |
| login apply | PASS | `C_LoginToServer` → `ClanSkillDBSet.add` |
| leave/kick/disband remove | NOT PROVEN | 未找到 inverse/remove/recalc |
| 850 equivalent | ABSENT | targeted exact search 無結果 |
| client/UI | YES | `Npc_clan`/`S_NPCTalkReturn`/`j_c2`，850 資源未證明存在 |
| package isolation | DESIGN REQUIRED | 必須獨立 schema owner、config、modifier service |

## 主要證據檔

- `I:\L381\Atu-381伺服器端主\DB\381_DB_AI用\w_血盟技能_202609221205.sql`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\data\event\ClanSkillDBSet.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\data\npc\Npc_clan.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\server\datatables\RewardClanSkillsTable.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\server\datatables\sql\ClanTable.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\server\clientpackets\C_LoginToServer.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\server\model\L1Clan.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\server\timecontroller\event\ClanSkillTimer.java`
- `I:\L1JTW8.5\db\無使用給AI檢查用資料庫DB\clan_data.sql`
