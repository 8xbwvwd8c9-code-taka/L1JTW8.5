# 850 高端道具製作架構規範書 (381 XML / HTML 製作 -> 850 原生核心統一化)

> **核心方針**：
> 「850有高端道具製作，XML製作或是html對話製作，改為850為主」
> - **全面以 850 原生機制為主**：拒絕移植 381 冗餘自訂框架（如 381 自行開發的 XML 解析引擎、`L1BlendTable`、`MasterCraft` 龐大類別）。
> - **統一收斂至 850 原生資料結構與流程**：
>   1. **高版本原生介面製作**：使用 850 `craft` 與 `craft_exchange` 資料表，支援原生存檔、機率、材料扣除、大成功、失敗返還與 protobuf 製作 UI。
>   2. **NPC 對話製作 (Legacy / 傳統流程)**：僅在特定 NPC 互動對話強烈需要時，使用 850 原生 `html_craft` 資料表，不走 381 XML 代碼。

---

## 1. 850 原生製作架構對照表

850 權威核心擁有完整修復之製作系統（參考 `completed/l1jtw85-core-fixes` 與 `8.5.sql`）：

| 850 原生資料表 | 負責職責 | 支援特性 | 取代之 381 舊架構 |
|---|---|---|---|
| **`craft`** | 850 高端/高版本製作核心表 | - 成品 ID、數量、固定強化值<br>- 多項材料清單、數量、強化度、祝福限制<br>- 成功機率 (`change`)、額外加成材料 (`add_chance_itemid`)<br>- 失敗返還道具與數量 (`fail_itemid`, `fail_item_count`)<br>- 大成功機率 (`perfect_chance`)<br>- 等級/正義值/善惡值限制 | 381 `ItemMaking.xml`<br>381 `SingleItemMaking.xml`<br>381 `x_大師製作系統`<br>381 `w_火神裝備製作` |
| **`craft_exchange`** | 多選一/材料兌換映射表 | - 支援特定材料替換/互換產出 (`material_itemid` -> `exchange_itemid`)<br>- 獨立兌換數量與強化度 | 381 替代材料陣列<br>381 碎片兌換 XML |
| **`html_craft`** | NPC 傳統對話式製作表 | - 綁定 `npcid` + `action`<br>- 支援輸入數量 (`isInputable`)<br>- 成功/失敗對話檔 (`success_html`, `fail_html`)<br>- 原生扣除材料與給予成品 | 381 NPC 對話觸發之製作<br>381 `w_npc製作`<br>381 `w_道具火神製作` |

---

## 2. 轉換與收斂規則 (Tier 分級治理)

### 2.1 Tier A：100% 850 原生可直接表達之配方 (Direct Native)
此類配方完全不需修改 850 Java 核心，直接將 381 配方轉為 850 `craft` 或 `html_craft` SQL：
- **固定成品輸出**（物品 ID、數量、強化值）。
- **標準材料清單**（材料 ID、需求數量、限定強化值、祝福要求）。
- **成功機率**（0~100% 機率計算）。
- **失敗保底/返還**（退回指定材料或金幣）。
- **等級限制**（min_level / max_level）。
- **範例已確認項目**：
  - 381 NPC 70520（煉金術師）：補足 N/O/P 動作至 `html_craft`。
  - 381 NPC 80102（傢飾商）：補足 b/c/d/e/f 動作至 `html_craft`。
  - 381 NPC 70904（黑妖工匠）：已完整由 850 原生 `craft` 表（ID 135~156）直接接管，**絕不重複匯入 381 XML**。

### 2.2 Tier B：小幅度擴充之配方 (Native + Small Extension)
若 381 配方具備以下特性，依 850 核心風格擴充少數通用欄位，不自造新表：
- **全服公告 (Broadcast)**：於 850 製作結算後掛鉤原生全服廣播封包。
- **職業限制 (Class Restriction)**：於 850 `L1Craft.java` 檢查玩家職業 flag。
- **自訂加成道具上限 (Boost Item Cap)**：對齊 `craft.add_chance_itemid` 之數量控制。

### 2.3 Tier C：狀態繼承與進階變形 (Advanced Mutation)
- 涉及「材料附加屬性繼承」、「舊裝備物件 ID 繼承原地變更」者：
  - 統一納入 850 特殊製作處理器，不走 381 `L1Blend` 舊類別。

---

## 3. 落地執行清單與稽核邊界

1. **禁止再移植 381 XML 解析器**：
   - 包含 `ItemMaking.xml` / `SingleItemMaking.xml` 之載入框架已正式廢止。
   - 所有的配方資料一律以 850 SQL (`craft` / `craft_exchange` / `html_craft`) 形式落地。
2. **高端製作優先走 850 高版本 UI**：
   - 能透過 `craft` 表支援的項目，優先採用高版本客戶端原生製作介面（Protobuf 協定），提供清晰之成功率與材料預覽。
3. **對話製作收斂至 `html_craft`**：
   - 傳統村莊工匠、煉金術師等 NPC 談話選項，統一填入 `html_craft`，維持 850 單一真理來源。