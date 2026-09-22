# L381 `w_道具附魔系統` 深度遷移審計

## 結論

- **等級：L4**。
- 狀態是 per-item instance state：以 `character_item_power.item_obj_id` 對應 `L1ItemInstance.id`，保存 `hole_count` 與 `hole_1..hole_5`。
- 850 目前只有一般 `character_items.id`、`item_id`、`enchantlvl` 等欄位；未找到 `character_item_power`、`hole_1..hole_5` 或等價 ItemPower runtime。
- 模組需要 item-instance persistence、`L1ItemInstance` 欄位、`L1EquipmentSlot` 聚合、攻擊/物品狀態 hook、ItemExecutor、封包與 HTML/UI 配套，不能 DB-only，也不可併入普通道具成長。

## 來源與目標

- L381 source：`I:\L381\Atu-381伺服器端主\src`
- L381 DB：`I:\L381\Atu-381伺服器端主\DB\381_DB_AI用`
- 主要設定資料：`w_道具附魔系統_202609221205.sql`
- per-item state 檔：`character_item_power_202609221205.sql`（目前檔案長度為 **0 bytes**，沒有可直接匯入的 CREATE/INSERT schema 證據）
- 850 authority：`I:\L1JTW8.5` Git ref `completed/l1jtw85-core-fixes`
- 850 DB：`I:\L1JTW8.5\db\無使用給AI檢查用資料庫DB`

## DB 與 ownership

### 程式實際要求的表與欄位

`CharItemsPowerTable` 明確執行：

```sql
SELECT * FROM character_item_power
INSERT INTO character_item_power
  (item_obj_id, hole_count, hole_1, hole_2, hole_3, hole_4, hole_5)
UPDATE character_item_power
  SET hole_count=?, hole_1=?, hole_2=?, hole_3=?, hole_4=?, hole_5=?
  WHERE item_obj_id=?
DELETE FROM character_item_power WHERE item_obj_id=?
```

載入時以 `WorldItem.get().getItem(item_obj_id)` 找到 item instance，並把 `L1ItemPower_name` 掛到該 `L1ItemInstance` 的 `_power_name`。因此 ownership key 是物品 object ID，而不是 `char_id`、base `item_id` 或 stack count。

`L1ItemPower_name` 的 per-instance state 欄位為：`item_obj_id`、`hole_count`、`hole_1`、`hole_2`、`hole_3`、`hole_4`、`hole_5`。

### 來源 DB 完整性 blocker

`character_item_power_202609221205.sql` 是空檔，故無法從來源 DB 檔案證明資料型別、PRIMARY KEY、NULL/default、索引、外鍵或既有 rows。以上欄位只由 L381 SQL access code 證明；報告不推定缺失 schema。

### 設定資料表

`w_道具附魔系統` 保存每個可放入孔位的 power item 設定。欄位包括：

`type`, `itemid`, `note`, `powercount`, `powername`, `probability_unequi`, `unequipment`, `probability_polyid`, `polyid`, `polyid_time`, `probability`, `skill_id`, `target_to`, `addMaxHP`, `addMaxMP`, `add_str`, `add_con`, `add_dex`, `add_int`, `add_wis`, `add_cha`, `add_hp`, `add_mp`, `add_hpr`, `add_mpr`, `add_sp`, `hit_modifier`, `dmg_modifier`, `bow_hit_modifier`, `bow_dmg_modifier`, `double_dmg_chance`, `add_ac`, `m_def`, `addDamageReductionByArmor`, `gif`。

本報告只處理附魔/孔位狀態；`server_item_power_update` 是另一個普通/特殊物品升級資料面，未合併、未審計。

## power 選擇、孔位與持久化流程

### power 選擇/放入

`Power` ItemExecutor：

1. 由 action data 取得 target object ID，從玩家 inventory 取 `L1ItemInstance`。
2. 要求 target 已有 `get_power_name()`；沒有則拒絕。
3. 要求 target 未裝備。
4. 以 `PowerItemTable` 讀取投入物品的 `L1PowerItem`，驗證 Weapon/Armor/All 類型與 `useType`。
5. 以 `powercount` 限制同一 power item 可重複放入次數。
6. 依第一個空孔 `hole_1` 到 `hole_5`，並要求 `hole_count` 足夠，使用 `Random.nextInt(1000)` 與 `PowerItemSet.HOLER` 機率判定。
7. 成功後把投入 item ID 寫入對應 hole，送 `S_ItemStatus`、`S_ItemName`，再呼叫 `CharItemPowerReading.updateItem(target.getId(), target.get_power_name())`。

因此孔位內容是 power item 的 **item ID**；效果值來自 `PowerItemTable`/`w_道具附魔系統` 定義，不是把效果快照寫入 `character_item_power`。

### 孔位開啟

目前可直接證明的語意是：`hole_count` 決定可用孔數；`Power` 只在 `hole_count >= 1..5` 時使用對應孔。`PowerItemSet` 載入時限制 `WEAPONHOLE` 最大為 5，並觸發 `CharItemPowerReading.load()`。

但在目前 targeted search 中，沒有形成完整、可引用的「哪個 item executor/NPC action 首次建立 `L1ItemPower_name`、設定 `hole_count` 並呼叫 `storeItem`」證據。這不是可推定的細節，故孔位開啟入口列為 migration blocker；不能只依 `hole_1..hole_5` 欄位自行設計新開孔規則。

### 拔出/重置

`takePower_hole1` 到 `takePower_hole5` 各自處理對應孔：成功率通過後把該 power item ID 放回 inventory、將該 hole 設為 0、送 item status/name 封包並呼叫 `updateItem`。這證明拔出是 per-item state update，不是刪除整個 item row。

## equip apply/remove 與 runtime hooks

### 裝備聚合

`L1EquipmentSlot` 在裝備與卸下/重建 stat 的兩條聚合路徑都檢查 `eq.get_power_name()`，逐一讀取 hole 1 至 hole 5，將 power 設定累加到裝備結果。可影響的欄位包括：

- AC
- STR、DEX、CON、WIS、INT、CHA
- 最大 HP、最大 MP
- HP/MP regeneration
- damage modifier、hit modifier
- magic resistance
- SP
- bow hit、bow damage
- power 設定中的其他裝備攻擊/防禦聚合欄位

該路徑是裝備套用/移除的主要 hook；它以重新聚合結果處理，沒有把 per-item power 寫入 base item row。

### 其他 runtime

- `L1ItemStatus` 把 hole effect 納入物品狀態/名稱輸出。
- `L1ItemInstance` 的名稱生成依 `hole_count` 顯示 hole 1 至 hole 5 的 power 名稱。
- `L1AttackPc` 對武器的每個 hole 呼叫 `skillEffice` 與 poly/effect 判定，因此某些 power 會進入攻擊/技能 runtime。
- `L1ItemInstance` 另有 weapon damage/SP 相關 getter 直接讀 `PowerItemTable` 的 hole effect。

## 交易、倉庫、死亡、重啟

- **重啟：已證明有載入路徑。** `PowerItemSet.execute()` 與 `Npc_ShopX` 的特定 reload 路徑呼叫 `CharItemPowerReading.load()`；loader 依 object ID 將資料掛回 `WorldItem` 的 item instance。
- **交易/倉庫：未證明完整。** `character_item_power` 不保存 `char_id`，理論上可隨 object ID 移轉，但目前 targeted evidence 沒有證明交易、倉庫搬運時會保留或重新掛接 `_power_name`。
- **死亡掉落：未證明完整。** 目前沒有找到死亡流程對 `character_item_power` 的明確搬運/刪除證據。
- **刪除物品：部分證據。** `CharItemsPowerTable.delItem(item_obj_id)` 可刪除 per-item row，但本次沒有證明所有 inventory item deletion path 都呼叫它。

因此 migration 必須把 object ID 穩定性、交易/倉庫移轉、死亡掉落及刪除 cleanup 做成明確 contract；不可把 state 平展到 base item 或角色欄位。

## packets、HTML 與 client dependency

已核實的 server packet 依賴：

- `S_ItemStatus`
- `S_ItemName`
- `S_ServerMessage`

ItemExecutor 由 item action 註冊/分派，且 power item 類別與 `PowerItemTable`、`Npc_ShopX` 有關。這是 server-side action 與 item UI 顯示依賴。

本次 targeted source/850 DB 檢查未證明 850 已有相同 power item action、HTML/template 或 client 顯示資源，因此 client dependency 分類為 **YES / 需逐項驗證**，不是可假設相容。

## 850 比對

850 `character_items` 目前明確欄位為：`id`, `item_id`, `char_id`, `item_name`, `count`, `is_equipped`, `enchantlvl`, `is_id`, `durability`, `charge_count`, `temp_value`, `last_used`, `bless`, `attr_enchant_kind`, `attr_enchant_level`, `super_enchant_field_1..4`, `limit_time`。

它具備 per-instance primary key `id`，可作為 object identity 基礎；但沒有 `character_item_power` 的 hole state，也沒有可核實的 `L1ItemPower`、`L1EquipmentSlot` power aggregation 或 `PowerItemTable` 等價框架。850 migration delta 至少包括：

1. 獨立 per-item power state table/schema，主鍵必須綁 850 item instance `character_items.id`。
2. power definition table/loader，保留 `w_道具附魔系統` 的欄位語意。
3. item instance attachment、load/save/delete cleanup。
4. 裝備聚合與武器攻擊 hooks。
5. ItemExecutor、拔出/放入 action、packet/UI 資源。

不能把資料塞入 `character_items` 的 base item definition 或普通 progression 欄位；也不能併入 staged `server_item_power_update`。

## 獨立安裝、回滾與風險

建議模組 owner 為：

- 一張獨立 per-item state table；
- 一張獨立 power definition table；
- `CharItemPowerReading`/storage adapter；
- `L1ItemInstance` optional power attachment；
- `L1EquipmentSlot`、`L1AttackPc`、`L1ItemStatus` 的明確 provider hook；
- Power/拔出 executors、config 與 packets/UI。

回滾需先停用 apply hooks，再保留或匯出 per-item rows，最後才移除定義與 action；不可直接刪除 `character_item_power`，否則會遺失每個物品的 hole state。若 850 尚未建立穩定 object-ID migration map，不能執行資料搬移。

## 驗證矩陣

| 項目 | 結果 | 證據 |
|---|---|---|
| persistence key/object ownership | PASS | `item_obj_id` → `WorldItem` → `L1ItemInstance.id` |
| DB columns complete | BLOCKED | code 要求 7 欄，但來源 SQL 檔為 0 bytes |
| hole semantics | PARTIAL | `hole_count` 控制 1..5；完整開孔 creator 未證明 |
| power roll/selection | PASS | `Power` 使用 target object、類型、powercount、隨機成功率 |
| equip apply/remove | PASS（runtime aggregation） | `L1EquipmentSlot` 正向/反向聚合路徑 |
| restart | PASS（loader exists） | `CharItemPowerReading.load` |
| trade/storage/death | NOT PROVEN | 未找到完整移轉 contract |
| 850 equivalent | ABSENT | targeted exact search 無等價 power framework |
| client dependency | YES | item action、`S_ItemStatus`、`S_ItemName`、`S_ServerMessage` |
| rollback | DESIGN REQUIRED | per-item rows 必須保留/可匯出 |

## 主要證據檔

- `I:\L381\Atu-381伺服器端主\DB\381_DB_AI用\w_道具附魔系統_202609221205.sql`
- `I:\L381\Atu-381伺服器端主\DB\381_DB_AI用\character_item_power_202609221205.sql`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\server\datatables\sql\CharItemsPowerTable.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\server\datatables\lock\CharItemPowerReading.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\server\model\Instance\L1ItemPower.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\server\model\Instance\L1ItemInstance.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\server\model\L1EquipmentSlot.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\data\item_etcitem\poweritem\Power.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\data\item_etcitem\add\takePower_hole1.java` 至 `takePower_hole5.java`
- `I:\L1JTW8.5\db\無使用給AI檢查用資料庫DB\character_items.sql`
