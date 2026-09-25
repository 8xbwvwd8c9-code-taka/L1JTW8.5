-- =======================================================
-- 850導入 / DB install ─ transform-grant-status
-- 涵蓋：w_transform_grant_status / w_transform_grant_status_item
-- 來源：w_變身賦予狀態（0 rows）/ w_變身賦予狀態_道具（18 rows）
-- 決定：HOLD（執行期擁有者 NOT_PROVEN）
-- =======================================================

-- ----- 主表：w_transform_grant_status（目前空，無 INSERT） -----
CREATE TABLE IF NOT EXISTS `w_transform_grant_status` (
  `id`                  INT          NOT NULL AUTO_INCREMENT,
  `note`                VARCHAR(100) DEFAULT '',
  `gfx_id`              INT          DEFAULT 0 COMMENT 'polymorph 外觀 ID',
  `dead_exp`            TINYINT      NOT NULL DEFAULT 0 COMMENT '死亡是否扣除（0=不扣）',
  `cancellation`        TINYINT      NOT NULL DEFAULT 0 COMMENT '受傷/條件取消（1=是）',
  `add_str`             SMALLINT     NOT NULL DEFAULT 0,
  `add_dex`             SMALLINT     NOT NULL DEFAULT 0,
  `add_con`             SMALLINT     NOT NULL DEFAULT 0,
  `add_int`             SMALLINT     NOT NULL DEFAULT 0,
  `add_wis`             SMALLINT     NOT NULL DEFAULT 0,
  `add_cha`             SMALLINT     NOT NULL DEFAULT 0,
  `add_ac`              SMALLINT     NOT NULL DEFAULT 0,
  `add_max_hp`          SMALLINT     NOT NULL DEFAULT 0,
  `add_max_mp`          SMALLINT     NOT NULL DEFAULT 0,
  `add_hpr`             SMALLINT     NOT NULL DEFAULT 0,
  `add_mpr`             SMALLINT     NOT NULL DEFAULT 0,
  `add_dmg`             SMALLINT     NOT NULL DEFAULT 0,
  `add_bow_dmg`         SMALLINT     NOT NULL DEFAULT 0,
  `add_hit`             SMALLINT     NOT NULL DEFAULT 0,
  `add_bow_hit`         SMALLINT     NOT NULL DEFAULT 0,
  `reduction_dmg`       SMALLINT     NOT NULL DEFAULT 0,
  `reduction_magic_dmg` SMALLINT     NOT NULL DEFAULT 0,
  `add_mr`              SMALLINT     NOT NULL DEFAULT 0,
  `add_sp`              SMALLINT     NOT NULL DEFAULT 0,
  `add_fire`            SMALLINT     NOT NULL DEFAULT 0,
  `add_wind`            SMALLINT     NOT NULL DEFAULT 0,
  `add_earth`           SMALLINT     NOT NULL DEFAULT 0,
  `add_water`           SMALLINT     NOT NULL DEFAULT 0,
  `add_exp`             DECIMAL(5,2) NOT NULL DEFAULT 0.00 COMMENT '原欄位 VARCHAR；值為 0 或 10',
  `potion_heal`         SMALLINT     NOT NULL DEFAULT 0,
  `pvp_dmg`             SMALLINT     NOT NULL DEFAULT 0,
  `pvp_dmg_reduction`   SMALLINT     NOT NULL DEFAULT 0,
  `add_magic_hit`       SMALLINT     NOT NULL DEFAULT 0,
  `regist_stun`         SMALLINT     NOT NULL DEFAULT 0,
  `regist_stone`        SMALLINT     NOT NULL DEFAULT 0,
  `regist_sleep`        SMALLINT     NOT NULL DEFAULT 0,
  `regist_freeze`       SMALLINT     NOT NULL DEFAULT 0,
  `regist_sustain`      SMALLINT     NOT NULL DEFAULT 0,
  `regist_blind`        SMALLINT     NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 主表目前無資料（來源 w_變身賦予狀態 為 0 bytes）

-- ----- 道具表：w_transform_grant_status_item（18 rows） -----
CREATE TABLE IF NOT EXISTS `w_transform_grant_status_item` (
  `id`                  INT          NOT NULL AUTO_INCREMENT,
  `note`                VARCHAR(100) DEFAULT '',
  `gfx_id`              INT          DEFAULT 0 COMMENT 'polymorph 外觀 ID（道具使用後觸發）',
  `dead_exp`            TINYINT      NOT NULL DEFAULT 0 COMMENT '死亡是否扣除（0=不扣）',
  `cancellation`        TINYINT      NOT NULL DEFAULT 0 COMMENT '受傷/條件取消（1=是）',
  `add_str`             SMALLINT     NOT NULL DEFAULT 0,
  `add_dex`             SMALLINT     NOT NULL DEFAULT 0,
  `add_con`             SMALLINT     NOT NULL DEFAULT 0,
  `add_int`             SMALLINT     NOT NULL DEFAULT 0,
  `add_wis`             SMALLINT     NOT NULL DEFAULT 0,
  `add_cha`             SMALLINT     NOT NULL DEFAULT 0,
  `add_ac`              SMALLINT     NOT NULL DEFAULT 0,
  `add_max_hp`          SMALLINT     NOT NULL DEFAULT 0,
  `add_max_mp`          SMALLINT     NOT NULL DEFAULT 0,
  `add_hpr`             SMALLINT     NOT NULL DEFAULT 0,
  `add_mpr`             SMALLINT     NOT NULL DEFAULT 0,
  `add_dmg`             SMALLINT     NOT NULL DEFAULT 0,
  `add_bow_dmg`         SMALLINT     NOT NULL DEFAULT 0,
  `add_hit`             SMALLINT     NOT NULL DEFAULT 0,
  `add_bow_hit`         SMALLINT     NOT NULL DEFAULT 0,
  `reduction_dmg`       SMALLINT     NOT NULL DEFAULT 0,
  `reduction_magic_dmg` SMALLINT     NOT NULL DEFAULT 0,
  `add_mr`              SMALLINT     NOT NULL DEFAULT 0,
  `add_sp`              SMALLINT     NOT NULL DEFAULT 0,
  `add_fire`            SMALLINT     NOT NULL DEFAULT 0,
  `add_wind`            SMALLINT     NOT NULL DEFAULT 0,
  `add_earth`           SMALLINT     NOT NULL DEFAULT 0,
  `add_water`           SMALLINT     NOT NULL DEFAULT 0,
  `add_exp`             DECIMAL(5,2) NOT NULL DEFAULT 0.00 COMMENT '原欄位 VARCHAR；值為 0 或 10',
  `potion_heal`         SMALLINT     NOT NULL DEFAULT 0,
  `pvp_dmg`             SMALLINT     NOT NULL DEFAULT 0,
  `pvp_dmg_reduction`   SMALLINT     NOT NULL DEFAULT 0,
  `add_magic_hit`       SMALLINT     NOT NULL DEFAULT 0,
  `regist_stun`         SMALLINT     NOT NULL DEFAULT 0,
  `regist_stone`        SMALLINT     NOT NULL DEFAULT 0,
  `regist_sleep`        SMALLINT     NOT NULL DEFAULT 0,
  `regist_freeze`       SMALLINT     NOT NULL DEFAULT 0,
  `regist_sustain`      SMALLINT     NOT NULL DEFAULT 0,
  `regist_blind`        SMALLINT     NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 18 rows（來源：w_變身賦予狀態_道具）
-- 欄位順序：note, gfx_id, dead_exp, cancellation,
--   add_str, add_dex, add_con, add_int, add_wis, add_cha,
--   add_ac, add_max_hp, add_max_mp, add_hpr, add_mpr,
--   add_dmg, add_bow_dmg, add_hit, add_bow_hit,
--   reduction_dmg, reduction_magic_dmg, add_mr, add_sp,
--   add_fire, add_wind, add_earth, add_water,
--   add_exp, potion_heal, pvp_dmg, pvp_dmg_reduction, add_magic_hit,
--   regist_stun, regist_stone, regist_sleep, regist_freeze, regist_sustain, regist_blind
INSERT INTO `w_transform_grant_status_item`
  (`note`,`gfx_id`,`dead_exp`,`cancellation`,
   `add_str`,`add_dex`,`add_con`,`add_int`,`add_wis`,`add_cha`,
   `add_ac`,`add_max_hp`,`add_max_mp`,`add_hpr`,`add_mpr`,
   `add_dmg`,`add_bow_dmg`,`add_hit`,`add_bow_hit`,
   `reduction_dmg`,`reduction_magic_dmg`,`add_mr`,`add_sp`,
   `add_fire`,`add_wind`,`add_earth`,`add_water`,
   `add_exp`,`potion_heal`,`pvp_dmg`,`pvp_dmg_reduction`,`add_magic_hit`,
   `regist_stun`,`regist_stone`,`regist_sleep`,`regist_freeze`,`regist_sustain`,`regist_blind`)
VALUES
  -- 1. 被騎士S換（cancellation=1；str/dex+5, int+1, maxHP/MP+100, dmg+10, bowDmg+5, hit+10, bowHit+5, sp+5）
  ('被騎士S換',13450,0,1, 5,5,0,1,0,0, 0,100,100,0,0, 10,5,10,5, 0,0,0,5, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  -- 2-11. 騎乘道具系列（gfxId 13715~13733 奇數）：hpr+2, mpr+2, dmg+2, sp+1
  ('13715',13715,0,0, 0,0,0,0,0,0, 0,0,0,2,2, 2,0,0,0, 0,0,0,1, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  ('13717',13717,0,0, 0,0,0,0,0,0, 0,0,0,2,2, 2,0,0,0, 0,0,0,1, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  ('13719',13719,0,0, 0,0,0,0,0,0, 0,0,0,2,2, 2,0,0,0, 0,0,0,1, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  ('13721',13721,0,0, 0,0,0,0,0,0, 0,0,0,2,2, 2,0,0,0, 0,0,0,1, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  ('13723',13723,0,0, 0,0,0,0,0,0, 0,0,0,2,2, 2,0,0,0, 0,0,0,1, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  ('13725',13725,0,0, 0,0,0,0,0,0, 0,0,0,2,2, 2,0,0,0, 0,0,0,1, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  ('13727',13727,0,0, 0,0,0,0,0,0, 0,0,0,2,2, 2,0,0,0, 0,0,0,1, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  ('13729',13729,0,0, 0,0,0,0,0,0, 0,0,0,2,2, 2,0,0,0, 0,0,0,1, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  ('13731',13731,0,0, 0,0,0,0,0,0, 0,0,0,2,2, 2,0,0,0, 0,0,0,1, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  ('13733',13733,0,0, 0,0,0,0,0,0, 0,0,0,2,2, 2,0,0,0, 0,0,0,1, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  -- 12. 變形：召喚白馬卡片（maxHP+50, hpr+2）
  ('變形：召喚白馬卡片',23370,0,0, 0,0,0,0,0,0, 0,50,0,2,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  -- 13. 變形：白馬卡片（mpr+3）
  ('變形：白馬卡片',23375,0,0, 0,0,0,0,0,0, 0,0,0,0,3, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  -- 14. 變形：召喚騎士S（maxMP+50, hpr+3）
  ('變形：召喚騎士S',24014,0,0, 0,0,0,0,0,0, 0,0,50,3,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  -- 15. 變形：射手（dmg+3）
  ('變形：射手',24024,0,0, 0,0,0,0,0,0, 0,0,0,0,0, 3,0,0,0, 0,0,0,0, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0),
  -- 16. 變形：巨人系（ac+2, regist_stun+5）
  ('變形：巨人系',23797,0,0, 0,0,0,0,0,0, 2,0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0.00,0,0,0,0, 5,0,0,0,0,0),
  -- 17. 變形：面甲帽（mr+2, exp+10）
  ('變形：面甲帽',24004,0,0, 0,0,0,0,0,0, 0,0,0,0,0, 0,0,0,0, 0,0,2,0, 0,0,0,0, 10.00,0,0,0,0, 0,0,0,0,0,0),
  -- 18. 變形：惡（ac+3, regist_blind+5）
  ('變形：惡',24009,0,0, 0,0,0,0,0,0, 3,0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,5),
  -- 19. 變形：藍蛟龍明珠系（str+1, dex+1, int+1）
  ('變形：藍蛟龍明珠系',23648,0,0, 1,1,0,1,0,0, 0,0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0.00,0,0,0,0, 0,0,0,0,0,0);