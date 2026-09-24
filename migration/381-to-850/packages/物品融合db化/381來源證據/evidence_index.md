# 381來源證據索引 — item-fusion-db (物品融合db化)

## 企劃條目
- 天堂企劃.txt 第4條：`L2,w_物品融合db化,N/A`
- 備注：適合暗黑系統 先暫存

## 381 資料庫檔案
| 檔案 | 路徑 | 大小 | 狀態 |
|------|------|------|------|
| `w_物品融合db化_202609221205.sql` | `DB/381_DB_AI用/` | **0 bytes** | BLOCKED |
| `atu381_0906.sql` | `DB/` | 14,204,491 bytes | 備用（含 CREATE TABLE） |

## 從 atu381_0906.sql 取得的 CREATE TABLE
- 行號約：5650-5680（依實際檔案）
- 確認方式：`Select-String -Path atu381_0906.sql -Pattern 'w_物品融合db化'`
- 結果：找到 `CREATE TABLE w_物品融合db化` 定義

## 381 server_event 設定（從 atu381_0906.sql）
```
-- 尚未在 server_event 中找到對應 ItemFusionDBSet 條目
-- 推測為較新功能，可能在 381 版本後期加入
```

## 結論
```
DATA_STATE=NO_ACTIVE_DATA
SOURCE_SCHEMA=PROVEN (via atu381_0906.sql)
CREATE=PROVEN (via atu381_0906.sql)
INSERT=NOT_PROVEN (0 bytes split file, no live data available)
DONOR_RUNTIME=NOT_PROVEN
```
