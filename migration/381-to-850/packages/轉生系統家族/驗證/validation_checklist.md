# 轉生系統家族 驗證檢查表 (Validation Checklist)

- **所屬套件**：`轉生系統家族`
- **對應企劃**：
  - `L2,william_pc_轉生,N/A`
  - `L2,william_pc_轉生_giveitem,N/A`
  - `L2,william_pc_轉生經驗,N/A`
- **最終狀態**：`MIGRATE_850_NATIVE` (PASS)

---

## 驗證項目

### 1. 資料庫層 (Database Schema & Data)
- [x] **DDL 建立**：`install_reincarnation_family.sql` 包含 `w_reincarnation`、`w_reincarnation_giveitem`、`w_reincarnation_exp` 三張正規化 InnoDB/utf8mb4 資料表。
- [x] **主表資料全量入庫**：`w_reincarnation` 涵蓋全 8 大職業（王族、騎士、妖精、法師、黑妖、龍騎、幻術、戰士）1~20 轉之 160 筆完整數據。
- [x] **獎勵表資料**：`w_reincarnation_giveitem` 設定轉生指定轉數獎勵道具與數量。
- [x] **經驗衰減表資料**：`w_reincarnation_exp` 包含 1~49 階轉生經驗獲取倍率衰減資料。
- [x] **回滾乾淨度**：`rollback_reincarnation_family.sql` 正確 DROP 三張擴展表，不殘留任何孤兒結構。

### 2. 伺服器端核心 (Server Core & Lifecycle)
- [x] **原生快取載入**：`ReincarnationTable.java` 實作高效複合鍵記憶體索引 `(type << 8) | mete_level`。
- [x] **能力計算**：支援生命值 (HP)、魔力 (MP)、物理/魔法減傷、六維屬性、近遠雙攻雙命、屬性抗性與狀態抗性全維度賦予。
- [x] **防漂移與冪等性 (Idempotent)**：登入、升級與轉生重算時重置既有加成後再套用，防止數值重複疊加漂移。
- [x] **經驗倍率掛接**：提供 `getExpPenalty(meteLevel)` 供戰鬥經驗計算鏈呼叫。

### 3. 控制端設定 (Configuration)
- [x] `reincarnation.properties.example` 提供系統總開關 (`ReincarnationEnable`)、最高轉生次數 (`MaxReincarnationLevel`)、需求等級 (`RequiredLevel`)、轉生後重置等級 (`ResetLevel`) 等。

### 4. 企劃同步
- [x] `I:\天堂企劃.txt` 已標記 `(完成)`。
