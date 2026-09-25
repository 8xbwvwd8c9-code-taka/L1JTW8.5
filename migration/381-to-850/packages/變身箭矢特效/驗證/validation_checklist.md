# 驗證清單 ─ transform-arrow-effect

## DDL 驗證
- [x] `w_transform_arrow_effect` CREATE TABLE 已寫入（install SQL 完成）
- [x] `poly_id` INT PK, `arrow_gfx_id` INT, `note` VARCHAR(100)
- [x] INSERT 1 row：(6611, 8121, '變身編號-箭矢特效對應')
- [x] rollback DROP TABLE 已寫入

## 前置（HOLD 解除條件）
- [ ] **[Core]** 封閉 `getpolyarrow` / `setpolyarrow` 下游消費者路徑
  → 確認在 850 ranged attack packet 中是否讀取 polyarrow 欄位
- [ ] **[Client]** poly ID 6611 在 850 客戶端 GFX 資源確認
  → 搜尋 850 client 資源（sprites/poly definition）中是否有 6611
- [ ] **[Client]** arrowgfxid 8121 在 850 客戶端有效性確認
  → 搜尋 850 ranged attack effect 定義中是否有 8121

## 伺服器實作（CLIENT_GATE 解除後）
- [ ] 實作 850 poly→arrowGFX resolver（查 w_transform_arrow_effect table）
  - 掛鉤點：polymorph apply hook（`L1PolyMorph.doPoly` 等價位置）
  - 掛鉤點：de-polymorph hook（還原 polyarrow=0 或 fallback）
- [ ] 技能特效 67 在 850 的識別方式確認
- [ ] fallback 值（66）在 850 的語義等價確認
- [ ] 不移植 `ArrowGfxid` 類別；使用 850 原生查表

## DB 驗收
- [x] `w_transform_arrow_effect` DDL 已輸出
- [ ] 在 850 測試 DB 上執行 install SQL 無報錯
- [ ] 在 850 測試 DB 上執行 rollback SQL 無報錯
- [ ] 確認 poly_id=6611 為 850 `polymorphs` 表中的有效 polyid

## 資料驗證
- [x] 1 row 資料已確認：poly 6611 → arrow 8121
- [ ] 與 w_自訂變形卷軸 的 poly 13216..13220 無交集（已稽核確認）
- [ ] 若日後新增映射，poly_id PRIMARY KEY 自動防止重複