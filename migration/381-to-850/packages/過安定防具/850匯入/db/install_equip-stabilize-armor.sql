-- =======================================================
-- 850導入 / DB install ─ 過安定防具強化加成
-- 表名：w_over_stabilize_armor
-- =======================================================
SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_over_stabilize_armor`;
CREATE TABLE `w_over_stabilize_armor` (
  `over_level` int(11) NOT NULL COMMENT '過安定階級 (1~6)',
  `note` varchar(50) NOT NULL DEFAULT '' COMMENT '說明',
  `dmg_reduction` smallint(6) NOT NULL DEFAULT 0 COMMENT '物理減傷',
  `magic_dmg_reduction` smallint(6) NOT NULL DEFAULT 0 COMMENT '魔法減傷',
  `hp` int(11) NOT NULL DEFAULT 0 COMMENT 'HP加成',
  `mp` int(11) NOT NULL DEFAULT 0 COMMENT 'MP加成',
  PRIMARY KEY (`over_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='過安定防具額外屬性加成表';

INSERT INTO `w_over_stabilize_armor` (`over_level`, `note`, `dmg_reduction`, `magic_dmg_reduction`, `hp`, `mp`) VALUES
(1, '過安定1', 0, 0, 20, 0),
(2, '過安定2', 1, 1, 50, 0),
(3, '過安定3', 2, 2, 75, 0),
(4, '過安定4', 3, 3, 100, 0),
(5, '過安定5', 4, 4, 125, 0),
(6, '過安定6', 5, 5, 150, 0);