-- =======================================================
-- 850匯入 / DB install — 道具怒氣爆氣系統 (Item Outburst)
-- 來源：atu381.w_道具爆氣系統 (3 rows)
-- 目標：850 原生正規化 InnoDB 結構
-- =======================================================

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_item_outburst`;
CREATE TABLE `w_item_outburst` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '階級ID',
  `item_id` int NOT NULL COMMENT '爆氣開關道具編號',
  `min_level` int NOT NULL DEFAULT 1 COMMENT '觸發最低角色等級',
  `max_level` int NOT NULL DEFAULT 99 COMMENT '觸發最高角色等級',
  `short_dmg` smallint NOT NULL DEFAULT 0 COMMENT '近戰增傷',
  `long_dmg` smallint NOT NULL DEFAULT 0 COMMENT '遠程增傷',
  `reduction_dmg` smallint NOT NULL DEFAULT 0 COMMENT '減傷加成',
  `add_hp` int NOT NULL DEFAULT 0 COMMENT '血量增加',
  `add_mp` int NOT NULL DEFAULT 0 COMMENT '魔量增加',
  `add_sp` smallint NOT NULL DEFAULT 0 COMMENT '魔攻增加',
  `gfx_id` int NOT NULL DEFAULT 0 COMMENT '爆氣特效ID',
  `drain_rage_per_sec` smallint NOT NULL DEFAULT 1 COMMENT '每秒扣除怒氣值',
  `msg_enable` varchar(100) NOT NULL DEFAULT '啟動爆氣' COMMENT '啟動提示訊息',
  `msg_disable` varchar(100) NOT NULL DEFAULT '關閉爆氣' COMMENT '關閉提示訊息',
  PRIMARY KEY (`id`),
  KEY `idx_item_level` (`item_id`, `min_level`, `max_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='道具爆氣怒氣消耗與爆發加成';

INSERT INTO `w_item_outburst` (
  `item_id`, `min_level`, `max_level`, `short_dmg`, `long_dmg`,
  `reduction_dmg`, `add_hp`, `add_mp`, `add_sp`, `gfx_id`,
  `drain_rage_per_sec`, `msg_enable`, `msg_disable`
) VALUES
	(92508,40,50,15,15,15,15,15,15,0,1,'啟動爆氣','關閉爆氣')),
	(92508,20,29,5,5,5,5,5,5,0,1,'啟動爆氣','關閉爆氣')),
	(92508,30,39,10,10,10,10,10,10,0,1,'啟動爆氣','關閉爆氣'));
