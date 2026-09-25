# 381原始資料索引 ─ transform-card-family

## 源 SQL 文件
| 檔案 | 大小 | 狀態 |
|------|------|------|
| w_變身卡片能力組合套卡_202609221205.sql | 2992 bytes | INSERT-only；10 rows；**schema 已從 INSERT 推斷** |
| w_變身卡片能力登入_202609221205.sql | 15139 bytes | INSERT-only；64 rows；**schema 已從 INSERT 推斷** |

## w_變身卡片能力登入 欄位（從 INSERT 推斷）
```
解鎖時的描述    VARCHAR  -- 登入卡描述文字
顯示變形名稱    VARCHAR  -- 顯示名稱
動作指令       VARCHAR  -- a1..a64（CardBookCmd UI action）
任務編號       INT      -- quest_id（解鎖識別鍵）
變身編號       INT      -- poly_id（381 polymorph ID）
變身時間       INT      -- poly_duration_sec（3600 or 1800）
變身材料編號    INT      -- consume_item_id（均為 40308）
變身材料數量    INT      -- consume_item_cnt（均為 1）
力量(STR)     TINYINT  -- add_str
敏捷(DEX)     TINYINT  -- add_dex
體質(CON)     TINYINT  -- add_con
智力(INT)     TINYINT  -- add_int
感知(WIS)     TINYINT  -- add_wis
魅力(CHA)     TINYINT  -- add_cha
防禦(AC)      TINYINT  -- add_ac
最大HP        SMALLINT -- add_max_hp
最大MP        SMALLINT -- add_max_mp
HP恢復        TINYINT  -- add_hpr
MP恢復        TINYINT  -- add_mpr
攻擊(DMG)     TINYINT  -- add_dmg
弓攻(BowDMG)  TINYINT  -- add_bow_dmg
命中(HIT)     TINYINT  -- add_hit
弓命中(BowHIT) TINYINT -- add_bow_hit
物理減傷       TINYINT  -- reduction_dmg
魔法減傷       TINYINT  -- reduction_magic_dmg
魔抗(MR)      TINYINT  -- add_mr
SP            TINYINT  -- add_sp
魔法命中       TINYINT  -- add_magic_hit（推測欄位名）
火屬性         TINYINT  -- add_fire
風屬性         TINYINT  -- add_wind
地屬性         TINYINT  -- add_earth
水屬性         TINYINT  -- add_water
```
共 33 欄位

## w_變身卡片能力組合套卡 欄位（從 INSERT 推斷）
```
序號(set_id)          INT       PRIMARY KEY
套卡名稱              VARCHAR
需求的變身卡編號       VARCHAR   -- 逗號分隔，均為 '1' (literal)
需求的任務編號         VARCHAR   -- 逗號分隔的 quest IDs
需求的變身卡名稱       VARCHAR   -- 逗號分隔的名稱說明
套卡任務編號           INT       -- set_quest_id (5100..5109)
力量(STR)  DEX CON INT WIS CHA AC maxHP maxMP HPR MPR
DMG BowDMG HIT BowHIT PhysRed MagRed MR SP MagHIT
FireRes WindRes EarthRes WaterRes
```
共 30 欄位

## 關鍵 Java 類別（381 donor）
| 類別 | 用途 |
|------|------|
| `com.add.system.ACardTable` | w_變身卡片能力登入 載入器 |
| `com.add.system.ACard` | 卡片執行期模型 |
| `com.add.system.CardSetTable` | w_變身卡片能力組合套卡 載入器 |
| `com.add.system.CardPolySet` | 套卡模型 |
| `com.add.system.CardBookCmd` | UI/控制 |
| `C_LoginToServer.getCard()` | 登入時增量 stat 應用 |
| `Cards.execute()` | 卡片 Quest + 套卡解鎖 |

## 稽核文件參照
- `_共用稽核/modules/TRANSFORM_CARD_COLLECTION_SET_AUDIT.md`
- `_共用稽核/modules/TRANSFORM_CARD_LOGIN_ABILITY_AUDIT.md`

## 850 DDL 對應原則（850-first）
| 381 table | 850 table（目標） |
|---|---|
| `w_變身卡片能力登入` | `w_transform_card_login` |
| `w_變身卡片能力組合套卡` | `w_transform_card_set` |
| (新增 junction) | `w_transform_card_set_require` |

## 已知 Poly ID 分組
- 20000..20009：STR 系列（10 張，3600s）
- 19000..19015：DEX/CON/INT/WIS 系列（16 張，3600s）
- 13600,13604,16421,16422,18601,18605,21635,21639,21646,21650：混合系列（10 張，3600s）
- 18611,20005,18606,18610,20015,20019,20010,20014,20025,20029,20020,20024,20045,20049,20040,20044,20869,20873,20864,20868,20030,20034,20100,20104,20120,20124,20058,20062：神話系列（28 張，1800s）