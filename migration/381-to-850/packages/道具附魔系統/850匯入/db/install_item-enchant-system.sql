-- =======================================================
-- 850匯入 / DB install — 道具附魔附靈系統 (Item Enchant Power)
-- 來源：atu381.w_道具附魔系統 (29 rows)
-- 目標：850 原生正規化 InnoDB 結構
-- =======================================================

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_item_enchant_power`;
CREATE TABLE `w_item_enchant_power` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '唯一序號',
  `item_type` varchar(50) NOT NULL COMMENT '附魔類型 (Weapon/armor)',
  `item_id` int NOT NULL COMMENT '附魔石/卡片道具編號',
  `note` varchar(255) DEFAULT '' COMMENT '附魔說明備註',
  `power_count` int NOT NULL DEFAULT 1 COMMENT '最大附魔次數/孔位',
  `power_name` varchar(50) NOT NULL COMMENT '詞綴字首簡稱',
  `prob_unequip` int NOT NULL DEFAULT 0 COMMENT '脫裝機率',
  `unequipment` int NOT NULL DEFAULT 0 COMMENT '卸除部位限制',
  `prob_poly` int NOT NULL DEFAULT 0 COMMENT '觸發變身機率',
  `poly_id` int NOT NULL DEFAULT 0 COMMENT '觸發變身外觀ID',
  `poly_time` int NOT NULL DEFAULT 0 COMMENT '變身持續時間(秒)',
  `probability` int NOT NULL DEFAULT 0 COMMENT '魔法/技能觸發機率%',
  `skill_id` int NOT NULL DEFAULT 0 COMMENT '觸發技能ID',
  `target_to` int NOT NULL DEFAULT 0 COMMENT '作用目標 (0=自身, 1=目標)',
  `add_max_hp` int NOT NULL DEFAULT 0 COMMENT 'MaxHP增加',
  `add_max_mp` int NOT NULL DEFAULT 0 COMMENT 'MaxMP增加',
  `add_str` smallint NOT NULL DEFAULT 0 COMMENT '力量增加',
  `add_con` smallint NOT NULL DEFAULT 0 COMMENT '體質增加',
  `add_dex` smallint NOT NULL DEFAULT 0 COMMENT '敏捷增加',
  `add_int` smallint NOT NULL DEFAULT 0 COMMENT '智力增加',
  `add_wis` smallint NOT NULL DEFAULT 0 COMMENT '精神增加',
  `add_cha` smallint NOT NULL DEFAULT 0 COMMENT '魅力增加',
  `add_hp` int NOT NULL DEFAULT 0,
  `add_mp` int NOT NULL DEFAULT 0,
  `add_hpr` smallint NOT NULL DEFAULT 0,
  `add_mpr` smallint NOT NULL DEFAULT 0,
  `add_sp` smallint NOT NULL DEFAULT 0,
  `hit_modifier` smallint NOT NULL DEFAULT 0,
  `dmg_modifier` smallint NOT NULL DEFAULT 0,
  `bow_hit_modifier` smallint NOT NULL DEFAULT 0,
  `bow_dmg_modifier` smallint NOT NULL DEFAULT 0,
  `double_dmg_chance` smallint NOT NULL DEFAULT 0,
  `add_ac` smallint NOT NULL DEFAULT 0,
  `m_def` smallint NOT NULL DEFAULT 0,
  `dmg_reduction` smallint NOT NULL DEFAULT 0,
  `gfx_id` int DEFAULT NULL COMMENT '發動特效ID',
  PRIMARY KEY (`id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='道具附魔與卡片屬性';

INSERT INTO `w_item_enchant_power` (
  `item_type`, `item_id`, `note`, `power_count`, `power_name`,
  `prob_unequip`, `unequipment`, `prob_poly`, `poly_id`, `poly_time`,
  `probability`, `skill_id`, `target_to`, `add_max_hp`, `add_max_mp`,
  `add_str`, `add_con`, `add_dex`, `add_int`, `add_wis`, `add_cha`,
  `add_hp`, `add_mp`, `add_hpr`, `add_mpr`, `add_sp`,
  `hit_modifier`, `dmg_modifier`, `bow_hit_modifier`, `bow_dmg_modifier`,
  `double_dmg_chance`, `add_ac`, `m_def`, `dmg_reduction`, `gfx_id`
) VALUES
	('Weapon',90055,'潔尼斯女王卡(力量+1)',5,'潔',0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,10,20,0,0,0,0,0,0,0,0,0,NULL)),
	('Weapon',90056,'幻象眼魔卡(敏捷+1)',5,'幻',0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL)),
	('Weapon',90057,'吸血鬼卡(智力+1)',5,'吸',0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL)),
	('Weapon',90058,'殭屍王卡(攻擊+3)',5,'殭',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,3,0,0,0,0,NULL)),
	('armor',90059,'黑豹卡(減傷+1)',3,'豹',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,NULL)),
	('armor',90060,'木乃伊王卡(防禦-2)',3,'木',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,NULL)),
	('armor',90061,'艾莉絲卡(魔防+2)',3,'艾',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,NULL)),
	('Weapon',90062,'騎士范德卡(魔攻+1)',5,'范',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,NULL)),
	('All',90063,'巫妖卡(HP+100)',5,'巫',0,0,0,0,0,0,0,0,100,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL)),
	('All',90064,'鐮刀死神卡(MP+100)',5,'鐮',0,0,0,0,0,0,0,0,0,100,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL)),
	('All',90065,'阿利歐克卡(HP+50 MP+50)',5,'歐',0,0,0,0,0,0,0,0,50,50,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)),
	('Weapon',90067,'惡魔卡(力量+2)',5,'惡',0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL)),
	('Weapon',90068,'古代巨人卡(敏捷+2)',5,'古',0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL)),
	('Weapon',90069,'巴風特卡(智力+2)',5,'風',0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL)),
	('Weapon',90070,'巴列斯卡(攻擊+7)',5,'列',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,7,0,0,0,0,NULL)),
	('armor',90071,'巨蟻女皇卡(減傷+2)',3,'蟻',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,NULL)),
	('armor',90072,'冰之女王卡(防禦-3)',3,'冰',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,NULL)),
	('armor',90073,'不死鳥卡(魔防+3)',3,'鳥',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,NULL)),
	('Weapon',90074,'安塔瑞斯卡(魔攻+2)',5,'塔',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,NULL)),
	('All',90075,'法力卡(HP+250)',5,'法',0,0,0,0,0,0,0,0,250,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL)),
	('All',90076,'巴拉卡斯卡(MP+250)',5,'巴',0,0,0,0,0,0,0,0,0,250,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL)),
	('All',90077,'林德拜爾卡(HP+150 MP+150)',5,'德',0,0,0,0,0,0,0,0,150,150,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)),
	('All',90079,'巨型骷髏(HP+400)',5,'骷',0,0,0,0,0,0,0,0,400,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL)),
	('All',90080,'死亡騎士(MP+400)',5,'死',0,0,0,0,0,0,0,0,0,400,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL)),
	('Weapon',90081,'阿勒尼亞(全能+2)',1,'蛛',0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL)),
	('Weapon',90082,'力卡溫(攻擊+12)',5,'溫',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,0,0,0,0,NULL)),
	('armor',90083,'沙蟲卡(減傷+4)',3,'蟲',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,NULL)),
	('armor',61021,'涅槃九尾狐(減傷+3)防具',3,'狐',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,NULL)),
	('Weapon',61022,'亂世牛魔王(HP+500)武器',5,'牛',0,0,0,0,0,0,0,0,500,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL));
