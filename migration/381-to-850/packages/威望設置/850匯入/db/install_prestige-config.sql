-- =======================================================
-- 850匯入 / DB install — 威望階級設置 (Prestige Config)
-- 來源：atu381.w_威望設置 (20 rows)
-- 目標：850 原生正規化 InnoDB 結構
-- =======================================================

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_prestige_config`;
CREATE TABLE `w_prestige_config` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '階級編號',
  `note` varchar(100) NOT NULL DEFAULT '' COMMENT '階級稱號備註',
  `min_prestige` int NOT NULL DEFAULT 0 COMMENT '威望最小值',
  `max_prestige` int NOT NULL DEFAULT 0 COMMENT '威望最大值',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '炫彩封號',
  `max_hp` int NOT NULL DEFAULT 0 COMMENT 'HP加成',
  `max_mp` int NOT NULL DEFAULT 0 COMMENT 'MP加成',
  `dmg_up` smallint NOT NULL DEFAULT 0 COMMENT '近戰傷害加成',
  `bow_dmg_up` smallint NOT NULL DEFAULT 0 COMMENT '遠程傷害加成',
  `hit_up` smallint NOT NULL DEFAULT 0 COMMENT '近戰命中加成',
  `bow_hit_up` smallint NOT NULL DEFAULT 0 COMMENT '遠程命中加成',
  `mr` smallint NOT NULL DEFAULT 0 COMMENT '魔防加成',
  `sp` smallint NOT NULL DEFAULT 0 COMMENT '魔攻加成',
  `add_str` smallint NOT NULL DEFAULT 0 COMMENT '力量加成',
  `add_dex` smallint NOT NULL DEFAULT 0 COMMENT '敏捷加成',
  `add_con` smallint NOT NULL DEFAULT 0 COMMENT '體質加成',
  `add_wis` smallint NOT NULL DEFAULT 0 COMMENT '精神加成',
  `add_int` smallint NOT NULL DEFAULT 0 COMMENT '智力加成',
  `add_cha` smallint NOT NULL DEFAULT 0 COMMENT '魅力加成',
  `reduction_dmg` smallint NOT NULL DEFAULT 0 COMMENT '物理減傷',
  `magic_reduction` smallint NOT NULL DEFAULT 0 COMMENT '魔法減傷',
  `gfx_id` int NOT NULL DEFAULT 0 COMMENT '外觀特效GFX',
  `gfx_time` int NOT NULL DEFAULT 0 COMMENT '特效循環時間(秒)',
  PRIMARY KEY (`id`),
  KEY `idx_prestige_range` (`min_prestige`, `max_prestige`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='威望軍階屬性加成與特效設定';

INSERT INTO `w_prestige_config` (
  `note`, `min_prestige`, `max_prestige`, `title`,
  `max_hp`, `max_mp`, `dmg_up`, `bow_dmg_up`, `hit_up`, `bow_hit_up`,
  `mr`, `sp`, `add_str`, `add_dex`, `add_con`, `add_wis`, `add_int`, `add_cha`,
  `reduction_dmg`, `magic_reduction`, `gfx_id`, `gfx_time`
) VALUES
	('[新兵] ',1,15000,'\\f=[新兵] ',100,100,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0)),
	('[二兵] ',15001,25000,'\\f=[二兵] ',200,200,1,1,1,1,2,1,0,0,0,0,0,0,0,0,0,0)),
	('[一兵] ',25001,50000,'\\f=[一兵] ',300,300,2,2,2,2,3,2,0,0,0,0,0,0,0,0,0,0)),
	('[上兵] ',50001,100000,'\\f=[上兵] ',400,400,2,2,2,2,4,2,0,0,0,0,0,0,0,0,0,0)),
	('[下士] ',100001,150000,'\\f=[下士] ',500,500,3,3,3,3,5,3,0,0,0,0,0,0,0,0,0,0)),
	('[中士] ',150001,250000,'\\f=[中士] ',600,600,3,3,3,3,6,3,0,0,0,0,0,0,1,0,0,0)),
	('[上士] ',250001,500000,'\\f=[上士] ',700,700,4,4,4,4,7,4,0,0,0,0,0,0,1,0,0,0)),
	('[士官長] ',500001,750000,'\\f=[士官長] ',800,800,4,4,4,4,8,4,0,0,0,0,0,0,2,0,0,0)),
	('[少尉] ',750001,1000000,'\\f=[少尉] ',900,900,5,5,5,5,9,5,0,0,0,0,0,0,2,0,0,0)),
	('[中尉] ',1000001,1250000,'\\f=[中尉] ',1000,1000,5,5,5,5,10,5,0,0,0,0,0,0,3,0,0,0)),
	('[上尉] ',1250001,1500000,'\\f=[上尉] ',1100,1100,6,6,6,6,12,6,1,1,0,0,1,1,3,0,0,0)),
	('[少校] ',1500001,2000000,'\\f=[少校] ',1200,1200,7,7,7,7,14,6,2,2,0,0,2,2,4,0,0,0)),
	('[中校] ',2000001,2500000,'\\f=[中校] ',1300,1300,8,8,8,8,16,7,3,3,0,0,3,3,4,0,0,0)),
	('[上校] ',2500001,3000000,'\\f=[上校] ',1400,1400,9,9,9,9,18,7,4,4,0,0,4,4,5,0,0,0)),
	('[少將] ',3000001,3500000,'\\f=[少將] ',1500,1500,10,10,10,10,20,8,5,5,0,0,5,5,5,0,0,0)),
	('[中將] ',3500001,4000000,'\\f=[中將] ',1600,1600,11,11,11,11,22,8,6,6,0,0,6,6,6,0,0,0)),
	('[上將] ',4000001,4500000,'\\f=[上將] ',1700,1700,12,12,12,12,24,9,7,7,0,0,7,7,7,0,0,0)),
	('[元帥] ',6000001,99999999,'\\f=[元帥] ',2000,2000,15,15,15,15,30,10,10,10,0,0,10,10,10,0,0,0)),
	('[統帥] ',5000001,6000000,'\\f=[統帥] ',1900,1900,14,14,14,14,28,10,9,9,0,0,9,9,9,0,0,0)),
	('[總長] ',4500001,5000000,'\\f=[總長] ',1800,1800,13,13,13,13,26,9,8,8,0,0,8,8,8,0,0,0));
