-- =======================================================
-- 850匯入 / DB install — 血盟等級系統 (Clan Level System)
-- 來源：atu381.w_血盟等級 (10 rows)
-- 目標：850 原生正規化 InnoDB 結構
-- =======================================================

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_clan_level`;
CREATE TABLE `w_clan_level` (
  `clan_level` int NOT NULL COMMENT '血盟等級 1~10',
  `note` varchar(100) DEFAULT '' COMMENT '等級備註',
  `contribution` int NOT NULL DEFAULT 0 COMMENT '升級所需貢獻度/經驗',
  `add_max_hp` int NOT NULL DEFAULT 0 COMMENT 'HP加成',
  `add_max_mp` int NOT NULL DEFAULT 0 COMMENT 'MP加成',
  `add_dmg` smallint NOT NULL DEFAULT 0 COMMENT '近戰傷害加成',
  `add_bow_dmg` smallint NOT NULL DEFAULT 0 COMMENT '遠程傷害加成',
  `add_hit` smallint NOT NULL DEFAULT 0 COMMENT '近戰命中加成',
  `add_bow_hit` smallint NOT NULL DEFAULT 0 COMMENT '遠程命中加成',
  `add_mr` smallint NOT NULL DEFAULT 0 COMMENT '魔防加成',
  `add_sp` smallint NOT NULL DEFAULT 0 COMMENT '魔攻加成',
  `add_ac` smallint NOT NULL DEFAULT 0 COMMENT '防禦加成',
  `add_fire` smallint NOT NULL DEFAULT 0 COMMENT '火抗',
  `add_wind` smallint NOT NULL DEFAULT 0 COMMENT '風抗',
  `add_earth` smallint NOT NULL DEFAULT 0 COMMENT '地抗',
  `add_water` smallint NOT NULL DEFAULT 0 COMMENT '水抗',
  `add_str` smallint NOT NULL DEFAULT 0 COMMENT '力量',
  `add_dex` smallint NOT NULL DEFAULT 0 COMMENT '敏捷',
  `add_con` smallint NOT NULL DEFAULT 0 COMMENT '體質',
  `add_wis` smallint NOT NULL DEFAULT 0 COMMENT '精神',
  `add_int` smallint NOT NULL DEFAULT 0 COMMENT '智力',
  `add_cha` smallint NOT NULL DEFAULT 0 COMMENT '魅力',
  `reduction_dmg` smallint NOT NULL DEFAULT 0 COMMENT '物理減傷',
  `reduction_magic_dmg` smallint NOT NULL DEFAULT 0 COMMENT '魔法減傷',
  `exp_rate` decimal(5,2) NOT NULL DEFAULT 0.00 COMMENT '經驗加成%',
  `add_hpr` smallint NOT NULL DEFAULT 0 COMMENT '回血',
  `add_mpr` smallint NOT NULL DEFAULT 0 COMMENT '回魔',
  `add_weight` int NOT NULL DEFAULT 0 COMMENT '負重加成',
  PRIMARY KEY (`clan_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='血盟等級加成設定';

INSERT INTO `w_clan_level` (
  `note`, `clan_level`, `contribution`, `add_max_hp`, `add_max_mp`,
  `add_dmg`, `add_bow_dmg`, `add_hit`, `add_bow_hit`, `add_mr`, `add_sp`,
  `add_ac`, `add_fire`, `add_wind`, `add_earth`, `add_water`,
  `add_str`, `add_dex`, `add_con`, `add_wis`, `add_int`, `add_cha`,
  `reduction_dmg`, `reduction_magic_dmg`, `exp_rate`, `add_hpr`, `add_mpr`, `add_weight`
) VALUES
	(note,ClanLevel,Contribution,AddMaxHp,AddMaxMp,AddDmg,AddBowDmg,AddHit,AddBowHit,AddMr,AddSp,AddAc,AddFire,AddWind,AddEarth,AddWater,AddStr,AddDex,AddCon,AddWis,AddInt,AddCha,reduction_dmg,reduction_magic_dmg,ExpRate,AddHpr,AddMpr,AddWeight) VALUES
	 ('血盟等級1',1,0,50,50,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'0',1,1,0),
	('血盟等級2',2,0,100,100,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'0',2,2,0),
	('血盟等級3',3,0,150,150,1,1,0,0,0,1,0,0,0,0,0,1,1,0,0,1,0,2,2,'10',3,3,10),
	('血盟等級4',4,0,175,200,1,1,1,1,0,1,0,0,0,0,0,2,2,0,0,2,0,4,4,'15',4,4,15),
	('血盟等級5',5,0,200,250,2,2,1,1,2,1,0,0,0,0,0,3,3,0,0,3,0,6,6,'20',5,5,20),
	('血盟等級6',6,0,300,300,2,2,2,2,2,2,1,0,0,0,0,4,4,0,0,4,0,8,8,'25',6,6,25),
	('血盟等級7',7,0,350,350,3,3,2,2,4,2,1,0,0,0,0,5,5,0,0,5,0,9,9,'30',7,7,30),
	('血盟等級8',8,0,400,400,3,3,2,2,4,2,2,0,0,0,0,6,6,0,0,6,0,10,10,'35',8,8,35),
	('血盟等級9',9,0,450,450,3,3,3,3,6,3,2,0,0,0,0,7,7,0,0,7,0,11,11,'40',9,9,40),
	('血盟等級10',10,0,500,500,5,5,3,3,8,3,3,0,0,0,0,8,8,0,0,8,0,12,12,'50',10,10,45);
