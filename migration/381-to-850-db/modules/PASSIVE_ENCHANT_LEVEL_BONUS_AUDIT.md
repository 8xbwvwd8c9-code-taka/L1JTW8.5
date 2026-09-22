# L381 被動強化等級加成審計

## 結論

- **等級：L4**。
- `w_裝武強化lv` 與 `w_飾品等級` 都是 DB 定義表，但效果不是 DB-only：L381 在裝備/卸下時由 `L1EquipmentSlot` 直接修改玩家 runtime stats。
- 加成是 **item-specific + type-specific + exact enchant level**；不是全域依 enchant level 套用，也不是以一個最高 tier 累積所有低 tier。
- 850 的 `character_items` 保有 `enchantlvl`，但 targeted search 未找到兩張表、等價 loader/template 或等價 equip hook；需要 runtime hook。
- 本報告不包含 `w_道具附魔系統`、socket state 或 `server_item_power_update`。

## 來源與 loader

來源：

- `I:\L381\Atu-381伺服器端主\DB\381_DB_AI用\w_裝武強化lv_202609221205.sql`
- `I:\L381\Atu-381伺服器端主\DB\381_DB_AI用\w_飾品等級_202609221205.sql`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\william\EnchantOrginal.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\william\EnchantAccessory.java`

`EnchantOrginal`：

- 執行 `SELECT * FROM w_裝武強化lv`。
- 逐列讀取 `type`, `itemid`, `level` 與所有加成欄位。
- 建立 `L1WilliamEnchantOrginal`，以內部遞增 id 存入 `_ArmorIndex`。
- `GameServer` 啟動時呼叫 `EnchantOrginal.getInstance()`。

`EnchantAccessory`：

- 執行 `SELECT * FROM w_飾品等級`。
- 逐列讀取 `type`, `strength`, `level` 與 accessory 加成欄位。
- 建立 `L1WilliamEnchantAccessory` 存入 `_ArmorIndex`。
- `GameServer` 啟動時呼叫 `EnchantAccessory.getInstance()`。

兩個 L381 SQL 檔均提供 INSERT rows，但未提供 CREATE TABLE schema；因此完整 SQL column type、key、default 仍需以實際 DB schema 補證，不能從 INSERT 推定。

## 精確匹配規則

### `w_裝武強化lv`

`L1WilliamEnchantOrginal.getAddArmorOrginal(pc, item)` 與對稱的 `getReductionArmorOrginal` 只有在三個條件同時成立時命中：

```text
row.type   == item.getItem().getType2()
row.itemid == item.getItem().getItemId()
row.level  == item.getEnchantLevel()
```

因此它是：

- item-specific：同一 enchant level 對不同 item ID 可有不同效果；
- type-specific：`type` 必須匹配 `Type2`；
- exact-level：只命中當前精確等級；
- 非 global：沒有只依 enchant level 的全域 row。

SQL rows 顯示 type 1 的武器/裝備 item IDs 115、116、122、125、126、127、128、129、130、131、220、223、224、268、269 等在 7/8/9/10 級有 PVP damage rows；type 2 另有 item IDs 190051、190052、190053、190054、20079 等特定物品 rows。

### `w_飾品等級`

`L1WilliamEnchantAccessory` 的匹配條件為：

```text
row.type     == item.getItem().getUseType()
row.strength == item.getItem().get_greater()
row.level    == item.getEnchantLevel()
```

因此 accessory 加成同時由 accessory use type、物品的 `_greater` 分類與精確 enchant level 決定。它同樣不是 global，也不是只按 enchant level 的共用加成。

### 累積/最高 tier 判定

loader 只保存各 row；apply 方法只找一筆精確匹配 row，找到後 `break`。沒有遍歷 `level <= currentEnchantLevel` 的累積邏輯，也沒有取 `max(level)` 的最高 tier lookup。

結論：**同一物品在當前等級套用該等級 row 的完整加成；低等級 row 不會在同一呼叫中再累加。** 裝備升級後由卸下/重裝或 equipment recalc 以新等級重新匹配。

## 受影響 stat

兩個 template 的共通欄位及 runtime API 為：

- STR、DEX、CON、INT、WIS、CHA
- AC
- 最大 HP、最大 MP
- HP 回復、MP 回復
- 近戰傷害、弓傷害
- 近戰命中、弓命中
- 傷害減免
- MR
- SP
- PVP damage
- PVP damage reduction
- potion heal 加成、potion healing 數量/效果
- magic hit

`w_裝武強化lv` 另有：

- `weaponSkillDmg`（double）
- `weaponSkillChance`

其 apply 路徑以 `setWeaponSkillDmg`、`setWeaponSkillChance` 寫入；remove 路徑直接設為 0，而不是保存前一個來源值。

SQL rows 的非零欄位仍需依每列內容解讀；本報告列出 template/runtime 支援的完整 stat surface，不把 socket/item-power 欄位混入。

## 裝備套用與移除

`L1EquipmentSlot` 是主要 lifecycle hook：

- 裝備 armor/equipment 時呼叫 `L1WilliamEnchantOrginal.getAddArmorOrginal(owner, item)`。
- 對 accessory 路徑呼叫 `L1WilliamEnchantAccessory.getAddArmorOrginal(owner, item)`。
- 卸下/重算時呼叫對應 `getReductionArmorOrginal`。
- 裝備與卸下都發送 `S_SPMR`、`S_OwnCharStatus`；因此效果是 runtime modifier，而非只在 item description 顯示。

`L1ItemStatus` 也讀取兩個 loader/template，將 matching 加成納入物品狀態/顯示計算；但真正改變玩家 stat 的 direct add/remove hook 是 `L1EquipmentSlot` 及兩個 `L1William...` template。

## Weapon、armor、accessory 行為

### Weapon/armor (`w_裝武強化lv`)

- type 由 `item.getItem().getType2()` 匹配。
- item ID 與精確 enchant level 同時限制。
- 可修改攻擊、命中、弓攻擊/命中、SP、PVP、HP/MP、六圍、AC、MR、回復、減傷與技能傷害/機率等。
- table 名稱雖含「裝武」，實際資料同時存在 type 1 與 type 2 rows；不可只以表名推定只有武器。

### Accessory (`w_飾品等級`)

- type 由 `UseType` 匹配，不同於前表的 `Type2`。
- 另以 `item.get_greater()` 作 strength 分類。
- 支援與前表大致相同的 stat surface，但 template 沒有 weapon skill damage/chance 欄位。

## 850 比對

850 `character_items` 明確保存 `enchantlvl`，並以 `id` 作 item instance primary key；這只能提供普通 enchant level 的 instance state，不代表已存在 L381 兩套被動加成。

對 850 DB 及 `completed/l1jtw85-core-fixes` authority 做 targeted exact search，未找到：

- `w_裝武強化lv`
- `w_飾品等級`
- `EnchantOrginal`
- `EnchantAccessory`
- `L1WilliamEnchantOrginal`
- `L1WilliamEnchantAccessory`

因此 850 的已知遷移 delta 是：

1. 新增/匯入兩張獨立定義表或一個明確等價 schema。
2. 移植 loader/template 欄位與 exact matching contract。
3. 在 850 equipment apply/remove/recalc 增加 provider hook。
4. 驗證 850 的 `Type2`、`UseType`、`_greater` 與 stat API 語意後才能 mapping。

現有 `enchantlvl` 可作 lookup input；不能只匯入 DB rows 後宣稱功能完成。

## DB-only 與模組隔離

DB-only 不可行。若不修改 runtime hook，新增 rows 不會改變玩家 stat。

建議模組獨立擁有：

- `w_裝武強化lv` definition schema/loader；
- `w_飾品等級` definition schema/loader；
- template/provider API；
- `L1EquipmentSlot` additive hook；
- 對應 remove/recalc contract。

本模組不擁有、不修改：

- `character_item_power`、`hole_1..hole_5`、`L1ItemPower`；
- `server_item_power_update`、`ItemPowerUpdateTable`；
- base item rows 或普通 enchant persistence。

回滾時須先停用 equipment provider，再移除 definition tables/rows；不應刪除 850 `character_items.enchantlvl`。

## 分類與 blockers

| 項目 | 結果 |
|---|---|
| loader | PASS：`EnchantOrginal`、`EnchantAccessory` |
| DB schema | PARTIAL：INSERT 欄位已知，CREATE/key/type 未在來源 SQL 提供 |
| threshold | PASS：exact `itemid + type/useType + level`；無累積/最高 tier |
| equip/recalc | PASS：`L1EquipmentSlot` add/remove |
| affected stats | PASS：template/runtime 欄位已列全 |
| 850 equivalent | ABSENT（targeted search） |
| DB-only | NO |
| client dependency | NOT_PROVEN：主要為 server runtime/stat path；`S_OwnCharStatus`/`S_SPMR` 是既有狀態封包，未找到專屬 HTML dependency |
| migration level | L4 |

Blockers：850 尚無等價 core/table；來源缺 CREATE schema；需先確認 850 equipment recalc API 與 `Type2/UseType/_greater` 語意，並避免與 item-power/update 模組共用欄位或 owner。

## 主要證據檔

- `I:\L381\Atu-381伺服器端主\DB\381_DB_AI用\w_裝武強化lv_202609221205.sql`
- `I:\L381\Atu-381伺服器端主\DB\381_DB_AI用\w_飾品等級_202609221205.sql`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\william\EnchantOrginal.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\william\EnchantAccessory.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\william\L1WilliamEnchantOrginal.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\william\L1WilliamEnchantAccessory.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\server\model\L1EquipmentSlot.java`
- `I:\L381\Atu-381伺服器端主\src\com\lineage\server\model\Instance\L1ItemStatus.java`
- `I:\L1JTW8.5\db\無使用給AI檢查用資料庫DB\character_items.sql`
