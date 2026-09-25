# 驗證清單 ─ transform-grant-status

## DDL 驗證
- [x] `w_transform_grant_status` CREATE TABLE 已寫入（主表，空資料）
- [x] `w_transform_grant_status_item` CREATE TABLE 已寫入（18 rows）
- [x] `add_exp` 改為 DECIMAL(5,2)（原欄位 VARCHAR）
- [x] rollback DROP TABLE 順序正確（item → 主表）

## w_transform_grant_status_item 資料（18 rows）驗收
- [x] gfxId=13450（被騎士S換）：STR+5, DEX+5, INT+1, maxHP+100, maxMP+100, DMG+10, BowDMG+5, HIT+10, BowHIT+5, SP+5, cancellation=1
- [x] gfxId=13715..13733（奇數，10 rows）：HPR+2, MPR+2, DMG+2, SP+1 each
- [x] gfxId=23370（召喚白馬卡片）：maxHP+50, HPR+2
- [x] gfxId=23375（白馬卡片）：MPR+3
- [x] gfxId=24014（召喚騎士S）：maxMP+50, HPR+3
- [x] gfxId=24024（射手）：DMG+3
- [x] gfxId=23797（巨人系）：AC+2, regist_stun+5
- [x] gfxId=24004（面甲帽）：MR+2, add_exp=10.00
- [x] gfxId=24009（惡）：AC+3, regist_blind+5
- [x] gfxId=23648（藍蜥蜴明珠系）：STR+1, DEX+1, INT+1

## 前置（HOLD 解除條件）

### w_transform_grant_status（主表，空資料）
- [ ] **[Core]** 確認 381 Java source 中是否有載入 `w_變身賦予狀態` 的類別
  → 搜尋：`w_變身賦予狀態`、`GrantStatus`、`TransformStatus`
- [ ] 確認空 SQL 原因：功能保留（待填資料）/ 功能廢棄 / 已移入其他表
- [ ] 決定：HOLD → 若確認廢棄改為 SKIP_USER_DECISION

### w_transform_grant_status_item（道具觸發）
- [ ] **[Core]** 確認 381 執行期擁有者（道具使用 hook）
  → 可能的類別：類似 `L1PcInstance.useItem` 的分派
- [ ] 確認 `cancellation=1` 的觸發條件（受傷 / 移動 / 特定狀態）
- [ ] 確認 `gfx_id` 是否同時作為 polymorph ID 使用（還是純 buff 圖示）
- [ ] 確認 `add_exp` DECIMAL 轉換正確（原值 '0'/'10'）

## 伺服器實作（Core 確認後）
- [ ] TransformationOwner（若主表有資料）
  - apply hook：polymorph start
  - remove hook：de-polymorph
- [ ] ItemUseBuffOwner（w_transform_grant_status_item）
  - apply hook：道具使用事件
  - cancellation 條件處理
  - 不共用 TimedBuff/EquippedItem lifecycle

## 客戶端 Gate（需 per-row 確認）
- [ ] gfxId per-row：確認 13450, 13715..13733, 23370, 23375, 24014, 24024, 23797, 24004, 24009, 23648 在 850 客戶端有效
- [ ] regist_* 欄位在 850 是否有對應的抗性 API

## DB 驗收
- [x] DDL 已輸出（install SQL）
- [ ] 在 850 測試 DB 上執行 install SQL 無報錯
- [ ] 在 850 測試 DB 上執行 rollback SQL 無報錯
- [ ] 確認 DECIMAL(5,2) 在 850 MySQL 版本相容（MariaDB/MySQL 5.7+）