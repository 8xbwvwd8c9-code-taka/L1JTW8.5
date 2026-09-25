# 381原始資料索引 ─ transform-arrow-effect

## 源 SQL 文件
| 檔案 | 大小 | 狀態 |
|------|------|------|
| w_變身箭矢特效_202609221205.sql | 125 bytes | INSERT-only；1 row；schema 已從 INSERT 推斷 |

## 實際 INSERT（完整）
```sql
INSERT INTO atu381.w_變身箭矢特效 (`備`, polyid, arrowgfxid) VALUES
  ('變身編號-箭矢特效對應', 6611, 8121);
```

## 欄位結構（從 INSERT 推斷）
| 欄位 | 型別（推斷） | 850 欄位名 | 值 |
|------|---|---|---|
| 備 | VARCHAR(100) | note | '變身編號-箭矢特效對應' |
| polyid | INT | poly_id | 6611 |
| arrowgfxid | INT | arrow_gfx_id | 8121 |

## 850 DDL（已實作）
```sql
CREATE TABLE `w_transform_arrow_effect` (
  `poly_id`      INT          NOT NULL,
  `arrow_gfx_id` INT          NOT NULL,
  `note`         VARCHAR(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`poly_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

## 關鍵 Java 類別（381 donor）
| 類別 | 用途 |
|------|------|
| `com.lineage.william.ArrowGfxid` | 載入器 + forItemUSe() 執行期 |

## 執行期邏輯
```
ArrowGfxid.forItemUSe(user, poly):
  if (!effectSlot67) → polyarrow=0
  if (poly==6611)    → polyarrow=8121
  else               → polyarrow=66 (fallback)
下游：getpolyarrow → ranged attack packet (NOT_PROVEN)
```

## 稽核文件參照
- `_共用稽核/modules/TRANSFORM_ARROW_EFFECT_AUDIT.md`

## 與其他模組關係
- w_自訂變形卷軸 poly 13216..13220 無此 arrow 映射（已稽核確認）
- 依賴 850 native polymorph lifecycle，不需重寫 transformation lifecycle
- 技能特效 67 為 poly 狀態旗標，需在 850 確認對應位置