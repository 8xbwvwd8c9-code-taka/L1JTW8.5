-- =======================================================
-- 850匯入 / DB install — D系列怪物菁英化系統
-- 包含：
-- 1. _config 表開關與時段設定條目 (InnoDB)
-- 2. 怪物1~10級階梯施法規則表 w_monster_spell_tier_rule (InnoDB) (含攻擊/增益雙軌)
-- 3. 世界難度與菁英配置表 w_elite_monster_config (InnoDB)
-- 4. 怪物前後詞墜定義表 w_monster_affix_template (InnoDB) (暗黑系列60個詞墜)
-- =======================================================

-- 1. _config 表開關與時段控制條目 (INDEX 由 DB 自增，以參數名為唯一識別)
INSERT INTO `_config` (`parameter`, `value`, `note`)
VALUES
  ('EliteMonsterSwitch', '0', 'D系列怪物菁英化系統總開關 (0=關閉, 1=開啟, 預設0)'),
  ('EliteMonsterTimeSchedule', '23', '菁英化開放時段 (現實世界24小時制: 0=全天候開放, 預設23點, 可設如 1-2 或 18-20)')
ON DUPLICATE KEY UPDATE `note`=VALUES(`note`);

-- 2. 怪物1~10級階梯施法規則表 (支援攻擊與增益雙軌)
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

-- 預設十級階梯種子資料
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

-- 3. 世界難度與菁英生成配置
CREATE TABLE IF NOT EXISTS `w_elite_monster_config` (
  `difficulty_level` tinyint(2) NOT NULL DEFAULT 0 COMMENT '世界難度等級: 0=普通 1=困難 2=惡夢 3=地獄',
  `difficulty_name` varchar(32) NOT NULL DEFAULT '' COMMENT '難度名稱',
  `mob_elite_chance_pct` int(3) unsigned NOT NULL DEFAULT 10 COMMENT '小怪生成菁英機率(%): 預設10%',
  `mob_hp_multiplier_pct` int(5) unsigned NOT NULL DEFAULT 130 COMMENT '小怪血量倍率(%): 130=x1.3',
  `boss_hp_multiplier_pct` int(5) unsigned NOT NULL DEFAULT 100 COMMENT 'BOSS血量倍率(%): 100=不變',
  `drop_rate_multiplier_pct` int(5) unsigned NOT NULL DEFAULT 200 COMMENT '掉落率倍率(%): 200=加倍',
  `affix_count` tinyint(2) unsigned NOT NULL DEFAULT 1 COMMENT '增加詞墜數量(依難度累加)',
  `prefix_pool` text COMMENT '可選前綴ID清單(逗號分隔)',
  `suffix_pool` text COMMENT '可選後綴ID清單(逗號分隔)',
  `summon_ai_dmg_reduction_pct` int(3) unsigned NOT NULL DEFAULT 30 COMMENT '對召喚隊友AI傷害減免%',
  PRIMARY KEY (`difficulty_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='D系列菁英怪世界難度配置';

-- 種子資料：四大難度
INSERT INTO `w_elite_monster_config`
  (`difficulty_level`, `difficulty_name`, `mob_elite_chance_pct`, `mob_hp_multiplier_pct`, `boss_hp_multiplier_pct`, `drop_rate_multiplier_pct`, `affix_count`, `prefix_pool`, `suffix_pool`, `summon_ai_dmg_reduction_pct`)
VALUES
  (0, '普通難度', 10, 130, 100, 200, 1, '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15', '101,102,103,104,105,106,107,108,109,110', 20),
  (1, '困難難度', 10, 130, 100, 200, 2, '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20', '101,102,103,104,105,106,107,108,109,110,111,112,113,114,115', 30),
  (2, '惡夢難度', 10, 130, 100, 200, 3, '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25', '101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120', 40),
  (3, '地獄難度', 10, 130, 100, 200, 4, '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30', '101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130', 50)
ON DUPLICATE KEY UPDATE `difficulty_name`=VALUES(`difficulty_name`);

-- 4. 怪物前後詞墜庫 (支援暗黑系列60個詞墜)
CREATE TABLE IF NOT EXISTS `w_monster_affix_template` (
  `affix_id` int(10) unsigned NOT NULL COMMENT '詞墜ID',
  `diablo_name` varchar(45) NOT NULL DEFAULT '' COMMENT '暗黑破壞神原型名稱',
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
  `status` varchar(16) NOT NULL DEFAULT 'ACTIVE' COMMENT 'ACTIVE=啟用, NA=待適配',
  PRIMARY KEY (`affix_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='暗黑破壞神菁英怪前後詞墜庫';

-- 暗黑破壞神 60 個前後詞墜種子資料
INSERT INTO `w_monster_affix_template`
  (`affix_id`, `diablo_name`, `affix_name`, `affix_type`, `add_hp_pct`, `add_physical_reduction`, `add_magic_reduction`, `add_fire_resist`, `add_water_resist`, `add_wind_resist`, `add_earth_resist`, `proc_skill_id`, `proc_chance_pct`, `aura_gfx_id`, `status`)
VALUES
  -- 前綴 (1 ~ 30)
  (1, 'Stone Skin', '石化皮膚的', 'PREFIX', 20, 25, 0, 0, 0, 0, 20, 0, 0, 0, 'ACTIVE'),
  (2, 'Magic Resistant', '魔法抗性的', 'PREFIX', 0, 0, 30, 20, 20, 20, 20, 0, 0, 0, 'ACTIVE'),
  (3, 'Fire Enchanted', '火焰強化的', 'PREFIX', 15, 5, 0, 50, -20, 0, 0, 0, 0, 0, 'ACTIVE'),
  (4, 'Cold Enchanted', '冰霜強化的', 'PREFIX', 15, 5, 0, -20, 50, 0, 0, 0, 0, 0, 'ACTIVE'),
  (5, 'Lightning Enchanted', '雷電強化的', 'PREFIX', 15, 5, 0, 0, 0, 50, 0, 0, 0, 0, 'ACTIVE'),
  (6, 'Poison Enchanted', '毒素強化的', 'PREFIX', 15, 5, 0, 0, 0, 0, 50, 0, 0, 0, 'ACTIVE'),
  (7, 'Extra Strong', '特別強壯的', 'PREFIX', 30, 15, 5, 10, 10, 10, 10, 0, 0, 0, 'ACTIVE'),
  (8, 'Extra Fast', '特別迅速的', 'PREFIX', 0, 0, 0, 0, 0, 0, 43, 100, 0, 'ACTIVE'),
  (9, 'Mana Burn', '法力燃燒的', 'PREFIX', 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 'ACTIVE'),
  (10, 'Spectral Hit', '幽靈一擊的', 'PREFIX', 10, 0, 15, 15, 15, 15, 15, 0, 0, 0, 'ACTIVE'),
  (11, 'Shielding', '護盾加護的', 'PREFIX', 0, 0, 0, 0, 0, 0, 0, 78, 10, 0, 'ACTIVE'),
  (12, 'Arcane Enchanted', '秘術強化的', 'PREFIX', 10, 0, 25, 10, 10, 10, 10, 0, 0, 0, 'ACTIVE'),
  (13, 'Avenger', '復仇怒火的', 'PREFIX', 25, 10, 10, 10, 10, 10, 10, 102, 30, 0, 'ACTIVE'),
  (14, 'Health Link', '生命鏈接的', 'PREFIX', 50, 10, 10, 0, 0, 0, 0, 0, 0, 0, 'ACTIVE'),
  (15, 'Horde', '族群聚集的', 'PREFIX', 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'ACTIVE'),
  (16, 'Missile Dampening', '遠程抑制的', 'PREFIX', 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 'ACTIVE'),
  (17, 'Reflects Damage', '傷害反彈的', 'PREFIX', 0, 10, 10, 0, 0, 0, 0, 91, 20, 0, 'ACTIVE'),
  (18, 'Cursed', '詛咒印記的', 'PREFIX', 0, 0, 0, 0, 0, 0, 0, 56, 25, 0, 'ACTIVE'),
  (19, 'Berserker', '狂暴化身的', 'PREFIX', 20, 15, 0, 15, 15, 15, 15, 55, 100, 0, 'ACTIVE'),
  (20, 'Fanatic', '狂熱追隨的', 'PREFIX', 15, 0, 0, 0, 0, 0, 0, 54, 100, 0, 'ACTIVE'),
  (21, 'Ghostly', '虛無幽靈的', 'PREFIX', -10, 30, 0, 0, 30, 0, 0, 60, 10, 0, 'ACTIVE'),
  (22, 'Possessed', '狂亂著魔的', 'PREFIX', 60, 0, 20, 0, 0, 0, 0, 0, 0, 0, 'ACTIVE'),
  (23, 'Champion', '勇士頭目的', 'PREFIX', 40, 10, 10, 10, 10, 10, 10, 0, 0, 0, 'ACTIVE'),
  (24, 'Holy Freeze Aura', '神聖冰凍的', 'PREFIX', 10, 0, 0, 0, 40, 0, 0, 29, 25, 0, 'ACTIVE'),
  (25, 'Might Aura', '力量光環的', 'PREFIX', 20, 10, 0, 0, 0, 0, 0, 42, 100, 0, 'ACTIVE'),
  (26, 'Conviction Aura', '審判信念的', 'PREFIX', 0, 0, 0, -20, -20, -20, -20, 44, 15, 0, 'ACTIVE'),
  (27, 'Fanaticism Aura', '狂熱光環的', 'PREFIX', 15, 0, 0, 0, 0, 0, 0, 105, 20, 0, 'ACTIVE'),
  (28, 'Blessed Aim Aura', '精準指引的', 'PREFIX', 10, 0, 0, 0, 0, 0, 0, 8, 100, 0, 'ACTIVE'),
  (29, 'Iron Skin', '鋼鐵之膚的', 'PREFIX', 30, 30, 0, 0, 0, 0, 0, 168, 100, 0, 'ACTIVE'),
  (30, 'Prismatic', '稜鏡庇護的', 'PREFIX', 10, 0, 40, 25, 25, 25, 25, 31, 30, 0, 'ACTIVE'),

  -- 後綴 (101 ~ 130)
  (101, 'of Electrified', '之電弧', 'SUFFIX', 0, 0, 0, 0, 0, 20, 0, 17, 20, 0, 'ACTIVE'),
  (102, 'of Frozen', '之冰凍', 'SUFFIX', 0, 0, 0, 0, 30, 0, 0, 22, 20, 0, 'ACTIVE'),
  (103, 'of Molten', '之熔火', 'SUFFIX', 0, 0, 0, 30, 0, 0, 0, 46, 20, 0, 'ACTIVE'),
  (104, 'of Plagued', '之疫病', 'SUFFIX', 0, 0, 0, 0, 0, 0, 30, 11, 25, 0, 'ACTIVE'),
  (105, 'of Mortar', '之迫擊', 'SUFFIX', 0, 0, 0, 20, 0, 0, 0, 25, 20, 0, 'ACTIVE'),
  (106, 'of Thunderstorm', '之雷暴', 'SUFFIX', 0, 0, 0, 0, 0, 30, 0, 65, 15, 0, 'ACTIVE'),
  (107, 'of Desecrator', '之褻瀆', 'SUFFIX', 0, 0, 0, 25, 0, 0, 0, 58, 20, 0, 'ACTIVE'),
  (108, 'of Frozen Pulse', '之極凍脈衝', 'SUFFIX', 0, 0, 0, 0, 35, 0, 0, 80, 15, 0, 'ACTIVE'),
  (109, 'of Jailer', '之監禁', 'SUFFIX', 0, 0, 0, 0, 0, 0, 0, 87, 15, 0, 'ACTIVE'),
  (110, 'of Knockback', '之震退', 'SUFFIX', 0, 0, 0, 0, 0, 0, 0, 192, 15, 0, 'ACTIVE'),
  (111, 'of Nightmarish', '之夢魘', 'SUFFIX', 0, 0, 0, 0, 0, 0, 0, 66, 15, 0, 'ACTIVE'),
  (112, 'of Vortex', '之漩渦', 'SUFFIX', 0, 0, 0, 0, 0, 30, 0, 51, 15, 0, 'ACTIVE'),
  (113, 'of Waller', '之築牆', 'SUFFIX', 0, 0, 0, 0, 0, 0, 30, 157, 10, 0, 'ACTIVE'),
  (114, 'of Illusionist', '之幻象', 'SUFFIX', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'NA'),
  (115, 'of Teleporter', '之瞬移', 'SUFFIX', 0, 0, 0, 0, 0, 0, 0, 5, 20, 0, 'ACTIVE'),
  (116, 'of Wormhole', '之蟲洞', 'SUFFIX', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'NA'),
  (117, 'of Fire Chains', '之火鏈', 'SUFFIX', 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 'NA'),
  (118, 'of Orbiter', '之天體球', 'SUFFIX', 0, 0, 0, 0, 0, 25, 0, 0, 0, 0, 'NA'),
  (119, 'of Frost Nova', '之霜凍新星', 'SUFFIX', 0, 0, 0, 0, 30, 0, 0, 59, 15, 0, 'ACTIVE'),
  (120, 'of Corpse Explosion', '之屍爆', 'SUFFIX', 0, 0, 0, 25, 0, 0, 0, 0, 0, 0, 'NA'),
  (121, 'of Multishot', '之多重射擊', 'SUFFIX', 0, 0, 0, 0, 0, 0, 0, 148, 25, 0, 'ACTIVE'),
  (122, 'of Blindness', '之致盲', 'SUFFIX', 0, 0, 0, 0, 0, 0, 0, 20, 20, 0, 'ACTIVE'),
  (123, 'of Leech', '之吸取', 'SUFFIX', 0, 0, 0, 0, 0, 0, 0, 28, 25, 0, 'ACTIVE'),
  (124, 'of Petrify', '之石化', 'SUFFIX', 0, 0, 0, 0, 0, 0, 30, 33, 10, 0, 'ACTIVE'),
  (125, 'of Corrosive', '之腐蝕', 'SUFFIX', 0, 0, 0, 0, 0, 0, 0, 27, 20, 0, 'ACTIVE'),
  (126, 'of Meteor', '之流星', 'SUFFIX', 10, 5, 5, 20, 0, 0, 0, 74, 10, 0, 'ACTIVE'),
  (127, 'of Cataclysm', '之天譴', 'SUFFIX', 15, 10, 10, 15, 15, 15, 15, 77, 8, 0, 'ACTIVE'),
  (128, 'of Armor Piercing', '之破甲', 'SUFFIX', 0, 0, 0, 0, 0, 0, 0, 112, 15, 0, 'ACTIVE'),
  (129, 'of Savage Slaying', '之屠戮', 'SUFFIX', 10, 5, 0, 0, 0, 0, 0, 187, 25, 0, 'ACTIVE'),
  (130, 'of Nullification', '之虛無', 'SUFFIX', 0, 0, 20, 0, 0, 0, 0, 71, 15, 0, 'ACTIVE')
ON DUPLICATE KEY UPDATE 
  `diablo_name`=VALUES(`diablo_name`),
  `affix_name`=VALUES(`affix_name`),
  `add_hp_pct`=VALUES(`add_hp_pct`),
  `add_physical_reduction`=VALUES(`add_physical_reduction`),
  `add_magic_reduction`=VALUES(`add_magic_reduction`),
  `add_fire_resist`=VALUES(`add_fire_resist`),
  `add_water_resist`=VALUES(`add_water_resist`),
  `add_wind_resist`=VALUES(`add_wind_resist`),
  `add_earth_resist`=VALUES(`add_earth_resist`),
  `proc_skill_id`=VALUES(`proc_skill_id`),
  `proc_chance_pct`=VALUES(`proc_chance_pct`),
  `status`=VALUES(`status`);
