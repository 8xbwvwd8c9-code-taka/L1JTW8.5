# 成就圖鑑收集系統 驗證檢查表 (Validation Checklist)

- **所屬套件**：`成就圖鑑收集系統`
- **對應企劃**：
  - `L3,w_成就圖鑑收集設定,N/A`
  - `L3,w_成就圖鑑收集獎勵,N/A`
- **最終狀態**：`MIGRATE_850_NATIVE` (PASS)

---

## 驗證項目

### 1. 資料庫層 (Database Schema & Data)
- [x] **DDL 建立**：`install_achievement_codex.sql` 包含 `w_achievement_codex` (收集要求) 與 `w_achievement_reward` (屬性獎勵) 兩張正規化 InnoDB/utf8mb4 資料表。
- [x] **全量規則入庫**：42 筆成就任務條件，涵蓋 A~Z, A1~A10，綁定 quest_id 340000~340041。
- [x] **全量獎勵入庫**：42 筆 1:1 對應的永久能力加成（HP/MP、防禦、近遠攻命、魔攻、減傷、抗性、PVP等）。
- [x] **回滾腳本**：`rollback_achievement_codex.sql` 可乾淨移除兩張表。

### 2. 伺服器端核心 (Server Core & Lifecycle)
- [x] **雙向快取載入**：`AchievementCodexTable.java` 提供 `_entryByQuest`、`_entryByAction`、`_rewardByQuest` 三重快取。
- [x] **收集與交付判定**：支援多道具材料驗證、最低強化值門檻防呆比對。
- [x] **永久加成派發**：提供 `getReward(questId)` 供角色登入與完成任務時動態套用與屬性重算。

### 3. 控制端設定 (Configuration)
- [x] `achievement_codex.properties.example` 提供系統開關與防呆控制。

### 4. 企劃同步
- [x] `I:\天堂企劃.txt` 已標記 `(完成)`。
