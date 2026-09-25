-- =======================================================
-- 850導入 / DB install ─ 角色天賦系統 (character_talent)
-- =======================================================
SET NAMES utf8mb4;

DROP TABLE IF EXISTS `character_talent`;
CREATE TABLE `character_talent` (
  `char_obj_id`       int(11) NOT NULL COMMENT '角色唯一物件ID',
  `available_points`  int(11) NOT NULL DEFAULT 0 COMMENT '可用天賦點數 (tfcount)',
  `spent_points`      int(11) NOT NULL DEFAULT 0 COMMENT '已配點數 (tfcount1)',
  `str_point`         smallint(6) NOT NULL DEFAULT 0 COMMENT '力量配點 (上限5)',
  `dex_point`         smallint(6) NOT NULL DEFAULT 0 COMMENT '敏捷配點 (上限5)',
  `int_point`         smallint(6) NOT NULL DEFAULT 0 COMMENT '智力配點 (上限5)',
  `attack_point`      smallint(6) NOT NULL DEFAULT 0 COMMENT '近戰攻擊配點 (上限20)',
  `bow_attack_point`  smallint(6) NOT NULL DEFAULT 0 COMMENT '遠程攻擊配點 (上限20)',
  `hit_point`         smallint(6) NOT NULL DEFAULT 0 COMMENT '近戰命中配點 (上限20)',
  `bow_hit_point`     smallint(6) NOT NULL DEFAULT 0 COMMENT '遠程命中配點 (上限20)',
  `sp_point`          smallint(6) NOT NULL DEFAULT 0 COMMENT '魔攻配點',
  `con_point`         smallint(6) NOT NULL DEFAULT 0,
  `wis_point`         smallint(6) NOT NULL DEFAULT 0,
  `cha_point`         smallint(6) NOT NULL DEFAULT 0,
  `hp_point`          int(11) NOT NULL DEFAULT 0,
  `mp_point`          int(11) NOT NULL DEFAULT 0,
  `mr_point`          smallint(6) NOT NULL DEFAULT 0,
  `reduction_dmg`     smallint(6) NOT NULL DEFAULT 0,
  `hpr_point`         smallint(6) NOT NULL DEFAULT 0,
  `mpr_point`         smallint(6) NOT NULL DEFAULT 0,
  `potion_point`      smallint(6) NOT NULL DEFAULT 0,
  `exp_point`         smallint(6) NOT NULL DEFAULT 0,
  `ac_point`          smallint(6) NOT NULL DEFAULT 0,
  `weight_point`      smallint(6) NOT NULL DEFAULT 0,
  `pvp_dmg_point`     smallint(6) NOT NULL DEFAULT 0,
  `bow_pvp_dmg_point` smallint(6) NOT NULL DEFAULT 0,
  PRIMARY KEY (`char_obj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色天賦加成與配置點數存檔';