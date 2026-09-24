# 驗證清單 — character-talent-record

## 前置條件（執行前必須全部通過）
- [ ] 850 DB 中確認無同名表衝突
- [ ] 850 character 主表 PK 欄位名稱確認為 char_obj_id（或更新 install SQL）
- [ ] 850 核心載入器確認（NOT_PROVEN）
- [ ] Client 天賦系統協定確認（NOT_PROVEN）

## 安裝後驗證
- [ ] `SELECT COUNT(*) FROM character_天賦紀錄` 可執行
- [ ] 天賦點數購買後 tfcount 正確累加
- [ ] 天賦重置後 tfcount1 遞增
- [ ] 能力加成實際反映於角色屬性

## 回滾驗證
- [ ] DROP TABLE 後核心無錯誤啟動
