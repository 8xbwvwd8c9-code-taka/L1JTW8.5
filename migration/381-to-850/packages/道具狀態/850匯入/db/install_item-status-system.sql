-- =======================================================
-- 850匯入 / DB install — 道具時效狀態系統 (Item Status Buff)
-- 來源：atu381.w_道具狀態 (9 rows)
-- 目標：850 原生正規化 InnoDB 結構
-- =======================================================

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_item_status_buff`;
CREATE TABLE `w_item_status_buff` (
  `item_id` int NOT NULL COMMENT '道具編號',
  `name` varchar(100) NOT NULL COMMENT '道具名稱',
  `duration_sec` int NOT NULL DEFAULT 0 COMMENT '效果持續秒數',
  `note` varchar(255) DEFAULT '' COMMENT '註解說明',
  `buff_type` int NOT NULL DEFAULT 0 COMMENT '道具狀態類型分類',
  `is_override` tinyint NOT NULL DEFAULT 1 COMMENT '同類型效果是否覆蓋 (1=覆蓋, 0=不可共存)',
  `poly_id` int NOT NULL DEFAULT -1 COMMENT '變身編號 (-1為不變身)',
  `cancellation` tinyint NOT NULL DEFAULT 0 COMMENT '是否受相消影響 (1=可被消除, 0=不可消除)',
  `gfx_id` int NOT NULL DEFAULT 0 COMMENT '特效編號',
  `save_gfx` tinyint NOT NULL DEFAULT 1 COMMENT '特效是否保存',
  `add_str` smallint NOT NULL DEFAULT 0 COMMENT '力量增加',
  `add_dex` smallint NOT NULL DEFAULT 0 COMMENT '敏捷增加',
  `add_con` smallint NOT NULL DEFAULT 0 COMMENT '體質增加',
  `add_int` smallint NOT NULL DEFAULT 0 COMMENT '智力增加',
  `add_wis` smallint NOT NULL DEFAULT 0 COMMENT '精神增加',
  `add_cha` smallint NOT NULL DEFAULT 0 COMMENT '魅力增加',
  `add_ac` smallint NOT NULL DEFAULT 0 COMMENT '防禦增加',
  `add_hp` int NOT NULL DEFAULT 0 COMMENT '血量增加',
  `add_mp` int NOT NULL DEFAULT 0 COMMENT '魔量增加',
  `add_hpr` smallint NOT NULL DEFAULT 0 COMMENT '回血量增加',
  `add_mpr` smallint NOT NULL DEFAULT 0 COMMENT '回魔量增加',
  `add_dmg` smallint NOT NULL DEFAULT 0 COMMENT '近戰傷害增加',
  `add_hit` smallint NOT NULL DEFAULT 0 COMMENT '近戰命中增加',
  `add_bow_dmg` smallint NOT NULL DEFAULT 0 COMMENT '遠攻傷害增加',
  `add_bow_hit` smallint NOT NULL DEFAULT 0 COMMENT '遠攻命中增加',
  `add_dmg_r` smallint NOT NULL DEFAULT 0 COMMENT '物理減傷增加',
  `add_magic_r` smallint NOT NULL DEFAULT 0 COMMENT '魔法減傷增加',
  `add_mr` smallint NOT NULL DEFAULT 0 COMMENT '抗魔增加',
  `add_sp` smallint NOT NULL DEFAULT 0 COMMENT '魔攻增加',
  `add_fire` smallint NOT NULL DEFAULT 0 COMMENT '抗火屬性增加',
  `add_wind` smallint NOT NULL DEFAULT 0 COMMENT '抗風屬性增加',
  `add_earth` smallint NOT NULL DEFAULT 0 COMMENT '抗地屬性增加',
  `add_water` smallint NOT NULL DEFAULT 0 COMMENT '抗水屬性增加',
  `add_stun` smallint NOT NULL DEFAULT 0 COMMENT '昏迷耐性增加',
  `add_stone` smallint NOT NULL DEFAULT 0 COMMENT '石化耐性增加',
  `add_sleep` smallint NOT NULL DEFAULT 0 COMMENT '睡眠耐性增加',
  `add_freeze` smallint NOT NULL DEFAULT 0 COMMENT '寒冰耐性增加',
  `add_sustain` smallint NOT NULL DEFAULT 0 COMMENT '支撑耐性增加',
  `add_blind` smallint NOT NULL DEFAULT 0 COMMENT '暗黑耐性增加',
  `conflict_msg` varchar(255) DEFAULT '' COMMENT '類型重複時提示訊息',
  `faction_point_double` tinyint NOT NULL DEFAULT 0 COMMENT '陣營積分加倍',
  `delete_item` tinyint NOT NULL DEFAULT 1 COMMENT '使用後是否刪除道具 (1=扣除, 0=保留)',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='道具時效BUFF與能力設定';

INSERT INTO `w_item_status_buff` (
  `item_id`, `name`, `duration_sec`, `note`, `buff_type`, `is_override`, `poly_id`,
  `cancellation`, `gfx_id`, `save_gfx`,
  `add_str`, `add_dex`, `add_con`, `add_int`, `add_wis`, `add_cha`,
  `add_ac`, `add_hp`, `add_mp`, `add_hpr`, `add_mpr`,
  `add_dmg`, `add_hit`, `add_bow_dmg`, `add_bow_hit`, `add_dmg_r`, `add_magic_r`,
  `add_mr`, `add_sp`, `add_fire`, `add_wind`, `add_earth`, `add_water`,
  `add_stun`, `add_stone`, `add_sleep`, `add_freeze`, `add_sustain`, `add_blind`,
  `conflict_msg`, `faction_point_double`, `delete_item`
) VALUES
	(92183,'烤玉米力量',1200,'烤玉米的力量+1',1,1,'-1',0,0,1,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'您正在使用其他玉米當中!!',0,1)),
	(92184,'烤玉米敏捷',1200,'烤玉米的敏捷+1',2,1,'-1',0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'您正在使用其他玉米當中!!',0,1)),
	(92185,'烤玉米智力',1200,'烤玉米的智力+1',3,1,'-1',0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'您正在使用其他玉米當中!!',0,1)),
	(92186,'烤玉米攻擊',1200,'烤玉米的攻擊+5',4,1,'-1',0,0,1,0,0,0,0,0,0,0,0,0,500,300,50,15,70,15,12,10,0,0,0,0,0,0,15,0,5,0,0,0,'您正在使用其他玉米當中!!',0,1)),
	(92187,'烤玉米命中',1200,'烤玉米的命中+3',1,1,'-1',0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,3,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'您正在使用其他玉米當中!!',0,1)),
	(92188,'烤玉米魔攻',1200,'烤玉米的魔攻+2',1,1,'-1',0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,'您正在使用其他玉米當中!!',0,1)),
	(92199,'烤玉米抗魔',1200,'烤玉米的抗魔+10',1,1,'-1',0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,0,0,0,0,0,0,0,0,0,0,'您正在使用其他玉米當中!!',0,1)),
	(92200,'烤玉米血量',1200,'烤玉米的血量+400',1,1,'-1',0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'您正在使用其他玉米當中!!',0,1)),
	(92201,'烤玉米魔量',1200,'烤玉米的魔量+200',1,1,'-1',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'您正在使用其他玉米當中!!',0,1));
