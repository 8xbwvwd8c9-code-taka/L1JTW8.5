# 381 來源證據索引 — character-talent-record

| 檔案 | 路徑 | 大小 | 說明 |
|------|------|------|------|
| character_天賦紀錄_202609221205.sql | I:\L381\Atu-381伺服器端主\DB\381_DB_AI用\ | 425 bytes | 資料匯出，含 2 筆樣本 |
| atu381_0906.sql | I:\L381\Atu-381伺服器端主\DB\ | 14MB | CREATE TABLE schema at line 1164 |

## 相關 NPC/道具
- Item 92131「天賦點數+1」— 由 NPC 99101 (推廣幣商人) 以 500 推廣幣販售 (atu381_0906.sql shop 段)

## 欄位語義推斷
- `tfcount` — 已使用天賦點數總計
- `tfcount1` — 天賦重置次數（推斷）
- 其餘欄位與 w_成就圖鑑收集獎勵 的 reward 欄位結構一致，為累積能力加成
