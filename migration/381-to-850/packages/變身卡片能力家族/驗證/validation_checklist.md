# 驗證清單 ─ transform-card-family

## DDL 驗證
- [x] `w_transform_card_login` CREATE TABLE 已寫入（64 rows INSERT）
- [x] `w_transform_card_set` CREATE TABLE 已寫入（10 rows INSERT）
- [x] `w_transform_card_set_require` Junction Table 已寫入（per-set quest IDs）
- [x] rollback DROP TABLE 已寫入

## 資料完整性（基礎卡片 64 rows）
- [x] quest_id 範圍：5001..5064（連續，無跳號）
- [x] action_cmd：a1..a64（連續，無重複）
- [x] 3600s 卡片（a1..a36）：STR/DEX/CON/INT/WIS/CHA 各有加成
- [x] 1800s 卡片（a37..a64）：神話卡，無 stat 加成，純 polymorph
- [x] 全部消耗道具 40308 x1
- [x] card-id 欄位不再使用（850 DDL 不包含此欄位）

## 資料完整性（套卡 10 rows）
- [x] set_id 1..10，set_quest 5100..5109
- [x] Junction table 涵蓋全部 需求 quest IDs：
  - Set1: 5001..5010（10 quests）
  - Set2: 5011..5016（6 quests）
  - Set3: 5017..5020（4 quests）
  - Set4: 5021..5024（4 quests）
  - Set5: 5025..5026（2 quests）
  - Set6: 5027..5028（2 quests）
  - Set7: 5029..5030（2 quests）
  - Set8: 5031..5032（2 quests）
  - Set9: 5033..5034（2 quests）
  - Set10: 5035..5036（2 quests）

## 套卡 Stat 向量驗收
| set | set_quest | stat |
|-----|-----------|------|
| 1 | 5100 | STR+1 |
| 2 | 5101 | DEX+2 |
| 3 | 5102 | CON+3 |
| 4 | 5103 | INT+4 |
| 5 | 5104 | WIS+5 |
| 6 | 5105 | CHA+7 |
| 7 | 5106 | STR+8 |
| 8 | 5107 | DEX+9 |
| 9 | 5108 | CON+10 |
| 10 | 5109 | INT+11 |

## 前置（HOLD 解除條件）

### Core
- [ ] **[最高優先]** 封閉 `C_LoginToServer.getCard()` → 實際 stat 應用路徑
  → 確認是否增量加法（DRIFT_RISK）還是有 recompute 保護
- [ ] 設計 850 idempotent CollectionBonus recompute 策略
  → 每次 login：sum(unlocked card stats) + sum(active set stats) = 一次 stat rebuild
  → 避免 donor 的 login 增量加法 drift

### Client Gate（L4_BLOCKED）
- [ ] Poly ID → 850 客戶端資源對照表建立
  - 3600s 系列：20000..20009（STR）, 19000..19015（DEX/CON/INT/WIS）
  - 混合系列：13600,13604,16421,16422,18601,18605,21635,21639,21646,21650
  - 1800s 神話：18611,20005..20868 等 28 個 poly ID
- [ ] action a1..a64 → 850 CardBookUI 路徑確認
- [ ] 消耗道具 40308 → 850 item_id 語義映射

### DB
- [ ] quest_id 5001..5064、5100..5109 在 850 quest 系統中可用
- [ ] 在 850 測試 DB 執行 install SQL 無報錯
- [ ] 在 850 測試 DB 執行 rollback SQL 無報錯

## 伺服器實作（Core + Client 確認後）
- [ ] CollectionOwner（單一，涵蓋基礎卡 + 套卡）
- [ ] 解鎖事件：card quest complete → 觸發 CollectionOwner.recompute()
- [ ] 套卡推導：所有需求 quest 完成 → set_quest step=1
- [ ] 登入恢復：login → CollectionOwner.apply()（一次性 recompute，非增量）
- [ ] 稀疏鍵安全迭代（用 map entries，不用 i=0..size()）
- [ ] PolymorphAction：使用 850 native polymorph（不移植 381 CardBookCmd）

## MAX_COLLECTION_VECTOR 驗收
計算：card stats 總和 + set stats 總和
- 預期（從稽核文件）：STR23, DEX25, CON27, INT33, WIS17, CHA7
- [ ] 在 850 CollectionOwner 實作後，用完整解鎖狀態驗算此向量