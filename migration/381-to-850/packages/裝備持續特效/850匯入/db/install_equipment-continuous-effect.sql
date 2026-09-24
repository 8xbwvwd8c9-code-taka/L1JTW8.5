-- =======================================================
-- 850匯入 / DB install — 裝備持續特效 (w_item_continuous_effect)
-- 企劃來源：L4,w_裝備持續特效,N/A
-- 與裝武強化lv一併施工，支援特定道具穿戴特效與全域高強化等級視覺光環
-- =======================================================

DROP TABLE IF EXISTS `w_item_continuous_effect`;
CREATE TABLE `w_item_continuous_effect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL DEFAULT 0 COMMENT '裝備ID (0=全域所有裝備門檻)',
  `enchant_level` int(11) NOT NULL DEFAULT 0 COMMENT '需求強化等級(>=該等級生效, 0=任意強化)',
  `effect_gfx` int(11) NOT NULL COMMENT '特效GFX ID (S_SkillSound)',
  `interval_sec` int(11) NOT NULL DEFAULT 15 COMMENT '播放間隔(秒)',
  `note` varchar(255) DEFAULT '' COMMENT '備註說明',
  PRIMARY KEY (`id`),
  KEY `idx_item_enchant` (`item_id`, `enchant_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='裝備持續視覺光環特效配置表';

-- 種子資料：
-- 1. 高強化全域視覺特效 (+8 紫光, +9 金光, +10 熾天光環)
-- 2. 特殊神裝穿戴光環
INSERT INTO `w_item_continuous_effect` (`item_id`, `enchant_level`, `effect_gfx`, `interval_sec`, `note`) VALUES
 (0, 8, 7531, 15, '全域裝備+8光環特效'),
 (0, 9, 8684, 15, '全域裝備+9金黃光環特效'),
 (0, 10, 11685, 12, '全域裝備+10耀眼神聖光環特效'),
 (20079, 7, 4842, 10, '隱身斗篷高強化專屬暗影波紋'),
 (190051, 8, 8685, 10, '傳說防具穿戴專屬龍魂特效'),
 (190054, 8, 7013, 10, '傳奇首飾專屬星輝特效');

-- 建立 381 既有中文表名相容視圖
CREATE OR REPLACE VIEW `w_裝備持續特效` AS 
SELECT 
  `id`,
  CAST(`item_id` AS CHAR) AS `armor_id`,
  `effect_gfx` AS `gfxId`,
  `note`
FROM `w_item_continuous_effect`;

-- _config 系統控制開關與播放間隔 (預設開關=1, 間隔=15秒)
INSERT INTO `_config` (`key`, `val`) VALUES ('EquipmentContinuousEffectSwitch', '1')
ON DUPLICATE KEY UPDATE `val` = VALUES(`val`);

INSERT INTO `_config` (`key`, `val`) VALUES ('EquipmentContinuousEffectInterval', '15')
ON DUPLICATE KEY UPDATE `val` = VALUES(`val`);
