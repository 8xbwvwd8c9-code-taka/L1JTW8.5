-- =======================================================
-- 850匯入 / DB install — D系列怪物菁英化系統
-- 包含：
-- 1. 世界難度與菁英配置表 w_elite_monster_config (InnoDB)
-- 2. 怪物前後詞墜定義表 w_monster_affix_template (InnoDB)
-- =======================================================

-- 1. 世界難度與菁英生成配置
CREATE TABLE IF NOT EXISTS `w_elite_monster_config` (
  `difficulty_level` tinyint(2) NOT NULL DEFAULT 0 COMMENT '世界難度等級: 0=普通 1=困難 2=惡夢 3=地獄',
  `difficulty_name` varchar(32) NOT NULL DEFAULT '' COMMENT '難度名稱',
  `mob_elite_chance_pct` int(3) unsigned NOT NULL DEFAULT 10 COMMENT '小怪生成菁英機率(%): 預設10%',
  `mob_hp_multiplier_pct` int(5) unsigned NOT NULL DEFAULT 130 COMMENT '小怪血量倍率(%): 130=x1.3',
  `boss_hp_multiplier_pct` int(5) unsigned NOT NULL DEFAULT 100 COMMENT 'BOSS血量倍率(%): 100=不變',
  `drop_rate_multiplier_pct` int(5) unsigned NOT NULL DEFAULT 200 COMMENT '掉落率倍率(%): 200=加倍',
  `affix_count` tinyint(2) unsigned NOT NULL DEFAULT 1 COMMENT '增加詞墜數量(依難度累加)',
  `prefix_pool` varchar(255) NOT NULL DEFAULT '' COMMENT '可選前綴ID清單(逗號分隔)',
  `suffix_pool` varchar(255) NOT NULL DEFAULT '' COMMENT '可選後綴ID清單(逗號分隔)',
  `summon_ai_dmg_reduction_pct` int(3) unsigned NOT NULL DEFAULT 30 COMMENT '對召喚隊友AI傷害減免%',
  PRIMARY KEY (`difficulty_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='D系列菁英怪世界難度配置';

-- 種子資料：四大難度
INSERT INTO `w_elite_monster_config`
  (`difficulty_level`, `difficulty_name`, `mob_elite_chance_pct`, `mob_hp_multiplier_pct`, `boss_hp_multiplier_pct`, `drop_rate_multiplier_pct`, `affix_count`, `prefix_pool`, `suffix_pool`, `summon_ai_dmg_reduction_pct`)
VALUES
  (0, '普通難度', 10, 130, 100, 200, 1, '1,2,3', '101,102', 20),
  (1, '困難難度', 10, 130, 100, 200, 2, '1,2,3,4', '101,102,103', 30),
  (2, '惡夢難度', 10, 130, 100, 200, 3, '1,2,3,4,5', '101,102,103,104', 40),
  (3, '地獄難度', 10, 130, 100, 200, 4, '1,2,3,4,5,6', '101,102,103,104,105', 50)
ON DUPLICATE KEY UPDATE `difficulty_name`=VALUES(`difficulty_name`);

-- 2. 怪物前後詞墜庫 (前綴增強屬性抗性/體質，後綴附加技能/異常)
CREATE TABLE IF NOT EXISTS `w_monster_affix_template` (
  `affix_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '詞墜ID',
  `affix_name` varchar(32) NOT NULL DEFAULT '' COMMENT '詞墜顯示名稱',
  `affix_type` enum('PREFIX','SUFFIX') NOT NULL DEFAULT 'PREFIX' COMMENT '前綴或後綴',
  `add_hp_pct` int(5) NOT NULL DEFAULT 0 COMMENT '額外HP%',
  `add_physical_reduction` int(5) NOT NULL DEFAULT 0 COMMENT '物理抗性/傷害減免',
  `add_magic_reduction` int(5) NOT NULL DEFAULT 0 COMMENT '魔法抗性減免',
  `add_fire_resist` int(3) NOT NULL DEFAULT 0 COMMENT '火屬性抗性',
  `add_water_resist` int(3) NOT NULL DEFAULT 0 COMMENT '水屬性抗性',
  `add_wind_resist` int(3) NOT NULL DEFAULT 0 COMMENT '風屬性抗性',
  `add_earth_resist` int(3) NOT NULL DEFAULT 0 COMMENT '地屬性抗性',
  `proc_skill_id` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '附加發動技能ID (0=無)',
  `proc_chance_pct` int(3) unsigned NOT NULL DEFAULT 0 COMMENT '發動機率%',
  `aura_gfx_id` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '怪物外觀光圈/特效GFX (0=無)',
  PRIMARY KEY (`affix_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菁英怪與BOSS前後詞墜庫';

-- 預設詞墜種子
INSERT INTO `w_monster_affix_template`
  (`affix_id`, `affix_name`, `affix_type`, `add_hp_pct`, `add_physical_reduction`, `add_magic_reduction`, `add_fire_resist`, `add_water_resist`, `add_wind_resist`, `add_earth_resist`, `proc_skill_id`, `proc_chance_pct`, `aura_gfx_id`)
VALUES
  (1, '堅韌的', 'PREFIX', 20, 10, 0, 0, 0, 0, 0, 0, 0, 0),
  (2, '魔抗的', 'PREFIX', 0, 0, 15, 10, 10, 10, 10, 0, 0, 0),
  (3, '熔岩的', 'PREFIX', 10, 5, 0, 30, -10, 0, 0, 0, 0, 0),
  (4, '冰霜的', 'PREFIX', 10, 5, 0, -10, 30, 0, 0, 0, 0, 0),
  (5, '狂暴的', 'PREFIX', 0, 5, 5, 10, 10, 10, 10, 0, 0, 0),
  (6, '泰坦的', 'PREFIX', 30, 20, 10, 15, 15, 15, 15, 0, 0, 0),
  (101, '之雷擊', 'SUFFIX', 0, 0, 0, 0, 0, 20, 0, 17, 15, 0),
  (102, '之劇毒', 'SUFFIX', 0, 0, 0, 0, 0, 0, 20, 11, 20, 0),
  (103, '之吸血', 'SUFFIX', 0, 5, 0, 0, 0, 0, 0, 0, 0, 0),
  (104, '之燃燒', 'SUFFIX', 0, 0, 0, 25, 0, 0, 0, 4, 15, 0),
  (105, '之毀滅', 'SUFFIX', 15, 10, 10, 10, 10, 10, 10, 0, 0, 0)
ON DUPLICATE KEY UPDATE `affix_name`=VALUES(`affix_name`);
