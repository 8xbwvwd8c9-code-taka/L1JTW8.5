# 驗證清單 — item-fusion-db (物品融合db化)

> [!WARNING]
> decision=HOLD — 以下驗證項目均為**未來執行前**的必要條件。不得在 HOLD 狀態下執行。

## 資料庫驗證
- [ ] `w_物品融合db化` 資料表已成功建立
- [ ] 欄位定義符合 381 atu381_0906.sql 中的 CREATE TABLE
- [ ] 至少有 1 筆測試 INSERT 資料可正常寫入
- [ ] PRIMARY KEY (`item_id`) 正常運作

## 核心程式驗證（HOLD 解除後）
- [ ] 850 核心有載入 `w_物品融合db化` 的相關程式
- [ ] 融合動作觸發後正確消耗材料
- [ ] 成功率(rnd) 機制正常
- [ ] 失敗時材料消耗邏輯正確（removeItem=1 時消耗）
- [ ] 職業/等級限制正常運作

## 與暗黑系統整合驗證
- [ ] 確認850暗黑系統規格
- [ ] 物品融合與暗黑系統相容性確認

## 阻斷事項
1. **BLOCKED**: 381 SQL 0 bytes — 需從 381 live server 取得實際 INSERT 資料
2. **NOT_PROVEN**: 850 核心是否有此功能的 Java 實作
3. **NOT_PROVEN**: 暗黑系統整合方向未定
