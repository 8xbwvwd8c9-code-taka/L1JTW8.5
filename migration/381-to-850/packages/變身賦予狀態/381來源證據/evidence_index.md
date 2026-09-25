# 381原始資料索引 ─ transform-grant-status

## 源 SQL 文件
| 檔案 | 大小 | 狀態 |
|------|------|------|
| w_變身賦予狀態_202609221205.sql | 0 bytes | 無資料（HOLD；空 SQL ≠ SKIP） |
| w_變身賦予狀態_道具_202609221205.sql | 2774 bytes | INSERT-only；18 rows |

## w_變身賦予狀態_道具 欄位（從 INSERT 確認）
```
note            VARCHAR   -- 說明文字
gfxId           INT       -- polymorph 外觀 ID（同時作為 buff 圖示）
deadExp         TINYINT   -- 死亡是否扣除（全為 0）
cancellation    TINYINT   -- 受傷/條件取消（1=是，其餘 0）
addStr/Dex/Con/Int/Wis/Cha  TINYINT  -- 基礎屬性
addAc           TINYINT
addMaxHp/Mp     SMALLINT
addHpr/Mpr      TINYINT
addDmg/BowDmg/Hit/BowHit  TINYINT
reduction_dmg/magic_dmg   TINYINT
addMr/Sp        TINYINT
addFire/Wind/Earth/Water  TINYINT
addExp          VARCHAR   -- 值為 '0' 或 '10'（850 轉為 DECIMAL(5,2)）
Potion_Heal     TINYINT
PVPdmg/PVPdmgReduction    TINYINT
add_magic_hit   TINYINT
regist_stun/stone/sleep/freeze/sustain/blind  TINYINT
```

## 完整 18 rows 資料摘要
| gfx_id | note | 主要 stat |
|--------|------|-----------|
| 13450 | 被騎士S換 | STR+5, DEX+5, INT+1, maxHP+100, maxMP+100, DMG+10, BowDMG+5, HIT+10, BowHIT+5, SP+5, cancel=1 |
| 13715 | 13715 | HPR+2, MPR+2, DMG+2, SP+1 |
| 13717 | 13717 | HPR+2, MPR+2, DMG+2, SP+1 |
| 13719 | 13719 | HPR+2, MPR+2, DMG+2, SP+1 |
| 13721 | 13721 | HPR+2, MPR+2, DMG+2, SP+1 |
| 13723 | 13723 | HPR+2, MPR+2, DMG+2, SP+1 |
| 13725 | 13725 | HPR+2, MPR+2, DMG+2, SP+1 |
| 13727 | 13727 | HPR+2, MPR+2, DMG+2, SP+1 |
| 13729 | 13729 | HPR+2, MPR+2, DMG+2, SP+1 |
| 13731 | 13731 | HPR+2, MPR+2, DMG+2, SP+1 |
| 13733 | 13733 | HPR+2, MPR+2, DMG+2, SP+1 |
| 23370 | 變形：召喚白馬卡片 | maxHP+50, HPR+2 |
| 23375 | 變形：白馬卡片 | MPR+3 |
| 24014 | 變形：召喚騎士S | maxMP+50, HPR+3 |
| 24024 | 變形：射手 | DMG+3 |
| 23797 | 變形：巨人系 | AC+2, regist_stun+5 |
| 24004 | 變形：面甲帽 | MR+2, addExp=10.00 |
| 24009 | 變形：惡 | AC+3, regist_blind+5 |
| 23648 | 變形：藍蜥蜴明珠系 | STR+1, DEX+1, INT+1 |

> [!NOTE] 共 19 rows 含義：gfxId 13715 到 13733 奇數共 10 rows（13715,13717,13719,13721,13723,13725,13727,13729,13731,13733）+ 其他 8 rows + 13450 = 19。

## 850 DDL（已實作）
- `w_transform_grant_status`：主表（空，待確認執行期擁有者）
- `w_transform_grant_status_item`：道具觸發表（18 rows INSERT 完成）
- `add_exp` 改為 `DECIMAL(5,2)`（原 VARCHAR）

## 執行期擁有者（均 NOT_PROVEN）
- w_變身賦予狀態：在 381 Java source 中的載入器尚未確認
- w_變身賦予狀態_道具：道具使用 hook 的分派類別尚未確認

## 稽核文件參照
- `_共用稽核/inventory/SQL_FULL_INVENTORY.csv`（row 300-301）
- `_共用稽核/modules/DESIGNATED_ITEM_STATUS_AUDIT.md`（語義邊界參照）

## 語義邊界（不可混用 lifecycle）
| 表 | Lifecycle | Expiry |
|---|---|---|
| w_transform_grant_status | polymorph | de-poly |
| w_transform_grant_status_item | item-use buff | timer/cancellation |
| w_道具狀態 | item-use timed | timer（character_buff） |
| w_指定道具賦予狀態 | equipped item | unequip（isEquipped） |