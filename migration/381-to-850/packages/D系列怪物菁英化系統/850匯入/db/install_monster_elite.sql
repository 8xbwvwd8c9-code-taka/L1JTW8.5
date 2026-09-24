-- =======================================================
-- 850匯入 / DB install — D系列怪物菁英化系統
-- 包含：
-- 1. 怪物1~10級階梯施法規則表 w_monster_spell_tier_rule (InnoDB) (含攻擊/增益雙軌)
-- 2. 世界難度與菁英配置表 w_elite_monster_config (InnoDB)
-- 3. 怪物前後詞墜定義表 w_monster_affix_template (InnoDB)
-- =======================================================

-- 1. 怪物1~10級階梯施法規則表 (支援攻擊與增益雙軌)
CREATE TABLE IF NOT EXISTS `w_monster_spell_tier_rule` (
  `tier` tinyint(2) unsigned NOT NULL COMMENT '怪物階級 (1~10)',
  `min_lvl` int(3) unsigned NOT NULL COMMENT '對應最低怪物等級',
  `max_lvl` int(3) unsigned NOT NULL COMMENT '對應最高怪物等級',
  `max_magic_level` tinyint(2) unsigned NOT NULL COMMENT '允許施放之魔法等級上限 (1~10)',
  `allow_class_skills` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否開放全職業技能對應階級施放',
  `attack_spell_chance_pct` int(3) unsigned NOT NULL DEFAULT 20 COMMENT '攻擊型施法觸發機率%',
  `buff_spell_chance_pct` int(3) unsigned NOT NULL DEFAULT 15 COMMENT '增益/自保型施法觸發機率%',
  `heal_hp_threshold_pct` int(3) unsigned NOT NULL DEFAULT 35 COMMENT '觸發補血治癒之HP百分比閥值',
  `tier_name` varchar(32) NOT NULL DEFAULT '' COMMENT '階級描述',
  PRIMARY KEY (`tier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='怪物1~10級階梯施法規則表';

-- 預設十級階梯種子資料 (包含雙軌施法機率與補血線)
INSERT INTO `w_monster_spell_tier_rule`
  (`tier`, `min_lvl`, `max_lvl`, `max_magic_level`, `allow_class_skills`, `attack_spell_chance_pct`, `buff_spell_chance_pct`, `heal_hp_threshold_pct`, `tier_name`)
VALUES
  (1, 1, 10, 1, 0, 10, 10, 30, '一階弱小怪 (僅1級光箭/保護罩/初治)'),
  (2, 11, 20, 2, 0, 12, 12, 30, '二階普通怪 (1~2級火箭/擬似武器/毒咒)'),
  (3, 21, 30, 3, 1, 15, 12, 35, '三階進階怪 (1~3級極光雷電/中治/鎧甲護持)'),
  (4, 31, 40, 4, 1, 18, 15, 35, '四階精銳怪 (1~4級火球/吸血/通暢/緩速)'),
  (5, 41, 50, 5, 1, 20, 15, 40, '五階統領怪 (1~5級落雷/高治/木乃伊/黑闇之影)'),
  (6, 51, 60, 6, 1, 22, 18, 40, '六階王國怪 (1~6級烈炎/地裂/加速/體魄/相消)'),
  (7, 61, 70, 7, 1, 25, 20, 45, '七階深淵怪 (1~7級冰矛/狂暴/體力回復/神疾)'),
  (8, 71, 80, 8, 1, 28, 22, 45, '八階傳奇怪 (1~8級冰雪暴/全治/火牢/反屏/衝暈)'),
  (9, 81, 90, 9, 1, 30, 25, 50, '九階神話怪 (1~9級雷霆/聖結界/沉睡/火風暴/雙重破壞)'),
  (10, 91, 127, 10, 1, 35, 30, 50, '十階滅世BOSS (全套1~10級流星雨/究光/絕屏/全職大招)')
ON DUPLICATE KEY UPDATE `tier_name`=VALUES(`tier_name`);

-- 2. 世界難度與菁英生成配置
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

-- 3. 怪物前後詞墜庫
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
