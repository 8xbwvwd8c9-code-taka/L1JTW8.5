-- =====================================================================
-- D系列怪物菁英化、暗黑隨機詞綴打寶與五大元素寶石孔洞系統 — 安裝腳本
-- 適用版本：L1JTW 8.50c (MySQL 5.7+ / MariaDB)
-- 特性：獨立副表設計 (InnoDB)，安全外鍵與索引，完全不破壞原表結構
-- =====================================================================

-- 1. 裝備暗黑詞綴與寶石孔洞獨立副表
CREATE TABLE IF NOT EXISTS `character_items_dark_affix` (
  `item_obj_id` INT(11) NOT NULL COMMENT '裝備唯一物品ID (對應 character_items.id)',
  `affix_grade` TINYINT(2) NOT NULL DEFAULT '0' COMMENT '詞綴階級 (0:無, 1:暗綠, 2:綠, 3:暗藍, 4:紅, 5:土黃, 6:紫, 7:紫紅)',
  `affix_prefix` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '暗黑前綴名稱',
  `affix_suffix` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '暗黑後綴名稱',
  `full_display_name` VARCHAR(90) NOT NULL DEFAULT '' COMMENT '完整炫彩顯示名稱',
  `is_identified` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否已鑑定 (0:開盲盒未鑑定, 1:已鑑定)',
  `equip_slot_type` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '裝備部位類型',
  
  -- 暗黑詞綴部位差異化屬性加成
  `affix_str` SMALLINT(4) NOT NULL DEFAULT '0',
  `affix_dex` SMALLINT(4) NOT NULL DEFAULT '0',
  `affix_con` SMALLINT(4) NOT NULL DEFAULT '0',
  `affix_int` SMALLINT(4) NOT NULL DEFAULT '0',
  `affix_wis` SMALLINT(4) NOT NULL DEFAULT '0',
  `affix_cha` SMALLINT(4) NOT NULL DEFAULT '0',
  `affix_dmg` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '物理近戰攻擊',
  `affix_hit` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '近戰命中',
  `affix_bow_dmg` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '遠攻',
  `affix_bow_hit` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '遠命中',
  `affix_sp` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '魔攻 SP',
  `affix_ac` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '物理防禦 AC (負值代表更強)',
  `affix_mr` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '魔法防禦 MR',
  `affix_hp` INT(11) NOT NULL DEFAULT '0' COMMENT '額外HP',
  `affix_mp` INT(11) NOT NULL DEFAULT '0' COMMENT '額外MP',
  `affix_hpr` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '回血',
  `affix_mpr` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '回魔',
  `affix_dmg_reduction` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '傷害減免',
  `affix_fire_res` SMALLINT(4) NOT NULL DEFAULT '0',
  `affix_water_res` SMALLINT(4) NOT NULL DEFAULT '0',
  `affix_air_res` SMALLINT(4) NOT NULL DEFAULT '0',
  `affix_earth_res` SMALLINT(4) NOT NULL DEFAULT '0',
  `affix_crit_chance` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '暴擊率百分比',

  -- 隨機孔洞與鑲嵌寶石系統 (限定9大部位: 防具0~1洞, 武器0~3洞, 飾品0洞)
  `total_sockets` TINYINT(2) NOT NULL DEFAULT '0' COMMENT '裝備總孔洞數 (0~3)',
  `gem_socket_0` INT(11) NOT NULL DEFAULT '0' COMMENT '第0孔鑲嵌之寶石itemId (0為空)',
  `gem_socket_1` INT(11) NOT NULL DEFAULT '0' COMMENT '第1孔鑲嵌之寶石itemId (0為空)',
  `gem_socket_2` INT(11) NOT NULL DEFAULT '0' COMMENT '第2孔鑲嵌之寶石itemId (0為空)',
  `replace_index` TINYINT(2) NOT NULL DEFAULT '0' COMMENT '循環替換指標 (0 -> 1 -> 2 -> 0)',

  PRIMARY KEY (`item_obj_id`),
  INDEX `idx_grade` (`affix_grade`),
  INDEX `idx_identified` (`is_identified`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='裝備暗黑詞綴與寶石孔洞副表';

-- 2. 五大寶石 4 階段與完美無瑕寶石能力模板表
CREATE TABLE IF NOT EXISTS `dark_gem_template` (
  `item_id` INT(11) NOT NULL COMMENT '寶石道具ID',
  `gem_type` VARCHAR(20) NOT NULL COMMENT '寶石類型: diamond/ruby/sapphire/emerald/topaz/flawless',
  `stage` TINYINT(2) NOT NULL DEFAULT '1' COMMENT '階級 1~4',
  `gem_name` VARCHAR(45) NOT NULL COMMENT '寶石名稱',
  `target_type` VARCHAR(20) NOT NULL DEFAULT 'both' COMMENT '鑲嵌部位限制: weapon/armor/both',
  
  -- 鑲嵌在武器上之能力
  `w_dmg` SMALLINT(4) NOT NULL DEFAULT '0',
  `w_hit` SMALLINT(4) NOT NULL DEFAULT '0',
  `w_sp` SMALLINT(4) NOT NULL DEFAULT '0',
  `w_fire_dmg` SMALLINT(4) NOT NULL DEFAULT '0',
  `w_water_dmg` SMALLINT(4) NOT NULL DEFAULT '0',
  `w_air_dmg` SMALLINT(4) NOT NULL DEFAULT '0',
  `w_earth_dmg` SMALLINT(4) NOT NULL DEFAULT '0',
  `w_all_dmg` SMALLINT(4) NOT NULL DEFAULT '0',
  `w_crit` SMALLINT(4) NOT NULL DEFAULT '0',
  `w_str` SMALLINT(4) NOT NULL DEFAULT '0',
  `w_dex` SMALLINT(4) NOT NULL DEFAULT '0',
  `w_con` SMALLINT(4) NOT NULL DEFAULT '0',
  `w_int` SMALLINT(4) NOT NULL DEFAULT '0',

  -- 鑲嵌在防具上之能力
  `a_ac` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_mr` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_hp` INT(11) NOT NULL DEFAULT '0',
  `a_mp` INT(11) NOT NULL DEFAULT '0',
  `a_mpr` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_dmg_reduction` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_fire_res` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_water_res` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_air_res` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_earth_res` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_all_res` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_all_stat` SMALLINT(4) NOT NULL DEFAULT '0' COMMENT '全能力+1',
  `a_dmg` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_hit` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_sp` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_str` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_dex` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_con` SMALLINT(4) NOT NULL DEFAULT '0',
  `a_int` SMALLINT(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='五大元素寶石與完美寶石數值矩陣';

-- 3. 匯入五大寶石 4 階段與完美無瑕寶石能力矩陣
INSERT INTO `dark_gem_template` VALUES
  -- 鑽石 (Diamond) 1~4階 (4階物攻+5 命中+5 魔攻+5 全傷+5 / MR+5 全抗+5 AC-5)
  (40044, 'diamond', 1, '鑽石', 'both', 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
  (40048, 'diamond', 2, '品質 鑽石', 'both', 2, 2, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, -2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0),
  (40052, 'diamond', 3, '高品質 鑽石', 'both', 3, 3, 3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, -3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0),
  (50052, 'diamond', 4, '精工的最高級 鑽石', 'both', 5, 5, 5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, -5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0),

  -- 紅寶石 (Ruby) 1~4階 (4階力量+1 物攻+10 火傷+10 爆擊+5% / 力量+1 HP+50 火抗+5)
  (40045, 'ruby', 1, '紅寶石', 'both', 2, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
  (40049, 'ruby', 2, '品質 紅寶石', 'both', 4, 0, 0, 4, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
  (40053, 'ruby', 3, '高品質 紅寶石', 'both', 7, 0, 0, 7, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 35, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
  (50053, 'ruby', 4, '精工的最高級 紅寶石', 'both', 10, 0, 0, 10, 0, 0, 0, 0, 5, 1, 0, 0, 0, 0, 0, 50, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),

  -- 藍寶石 (Sapphire) 1~4階 (4階智力+1 SP+5 水傷+10 / 智力+1 MP+25 MPR+10 水抗+5)
  (40046, 'sapphire', 1, '藍寶石', 'both', 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
  (40050, 'sapphire', 2, '品質 藍寶石', 'both', 0, 0, 2, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 4, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
  (40054, 'sapphire', 3, '高品質 藍寶石', 'both', 0, 0, 3, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 7, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
  (50054, 'sapphire', 4, '精工的最高級 藍寶石', 'both', 0, 0, 5, 0, 10, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 25, 10, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),

  -- 綠寶石 (Emerald) 1~4階 (4階敏捷+1 雙攻+5 雙命中+10 風傷+5 / 敏捷+1 風抗+5 AC-10)
  (40047, 'emerald', 1, '綠寶石', 'both', 1, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, -2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
  (40051, 'emerald', 2, '品質 綠寶石', 'both', 2, 4, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, -4, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
  (40055, 'emerald', 3, '高品質 綠寶石', 'both', 3, 7, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, -7, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
  (50055, 'emerald', 4, '精工的最高級 綠寶石', 'both', 5, 10, 0, 0, 0, 5, 0, 0, 0, 0, 1, 0, 0, -10, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0),

  -- 黃寶石 (Topaz) 1~4階 (4階體質+1 物攻+5 命中+5 地傷+5 / 體質+1 HP+100 減免+10)
  (50056, 'topaz', 1, '精工的 黃寶石', 'both', 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
  (50057, 'topaz', 2, '精工的品質 黃寶石', 'both', 2, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 40, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
  (50058, 'topaz', 3, '精工的高品質 黃寶石', 'both', 3, 3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 70, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
  (50059, 'topaz', 4, '精工的最高級 黃寶石', 'both', 5, 5, 0, 0, 0, 0, 5, 0, 0, 0, 1, 0, 0, 0, 100, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),

  -- 完美無瑕的寶石 (Flawless) 全身唯一限制, 只能防具: 全能力+1, 雙攻雙命SP+8, AC-8
  (50060, 'flawless', 4, '完美無瑕的寶石', 'armor', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 8, 8, 8, 0, 0, 0, 0)
ON DUPLICATE KEY UPDATE `gem_name`=VALUES(`gem_name`), `target_type`=VALUES(`target_type`);

-- 4. 登錄新道具至 `etcitem` 並開放雙擊魔杖游標 (use_type = 'choice')
UPDATE `etcitem` SET `use_type`='choice' WHERE `item_id` IN (40044,40045,40046,40047,40048,40049,40050,40051,40052,40053,40054,40055);

INSERT INTO `etcitem` (`item_id`, `name`, `unidentified_name_id`, `identified_name_id`, `item_type`, `use_type`, `material`, `weight`, `invgfx`, `grdgfx`, `stackable`, `save_at_once`) VALUES
  -- 鑽石/紅/藍/綠 4階頂級
  (50052, '精工的最高級 鑽石', '$800 $512', '$800 $512', 'gem', 'choice', 'gemstone', '40', '238', '771', '1', '1'),
  (50053, '精工的最高級 紅寶石', '$800 $513', '$800 $513', 'gem', 'choice', 'gemstone', '40', '239', '771', '1', '1'),
  (50054, '精工的最高級 藍寶石', '$800 $514', '$800 $514', 'gem', 'choice', 'gemstone', '40', '240', '771', '1', '1'),
  (50055, '精工的最高級 綠寶石', '$800 $515', '$800 $515', 'gem', 'choice', 'gemstone', '40', '241', '771', '1', '1'),
  -- 黃寶石 1~4階 (invgfx: 2500)
  (50056, '精工的 黃寶石', '$800 $515', '$800 $515', 'gem', 'choice', 'gemstone', '40', '2500', '772', '1', '1'),
  (50057, '精工的品質 黃寶石', '$800 $515', '$800 $515', 'gem', 'choice', 'gemstone', '40', '2500', '772', '1', '1'),
  (50058, '精工的高品質 黃寶石', '$800 $515', '$800 $515', 'gem', 'choice', 'gemstone', '40', '2500', '772', '1', '1'),
  (50059, '精工的最高級 黃寶石', '$800 $515', '$800 $515', 'gem', 'choice', 'gemstone', '40', '2500', '772', '1', '1'),
  -- 完美無瑕的寶石 (invgfx: 2501)
  (50060, '完美無瑕的寶石', '$800 $515', '$800 $515', 'gem', 'choice', 'gemstone', '50', '2501', '772', '1', '1')
ON DUPLICATE KEY UPDATE `name`=VALUES(`name`), `use_type`='choice', `invgfx`=VALUES(`invgfx`);

-- 5. 登錄 NPC 合成兌換配方至 `html_craft` (兌換完美無瑕的寶石)
INSERT INTO `html_craft` (`action`, `npcid`, `craft_itemid`, `craft_count`, `material`, `material_count`, `success_html`, `fail_html`, `isInputable`) VALUES
  ('request flawless gem', '0', '50060,', '1,', '50052,50053,50054,50055,50059,', '1,1,1,1,1,', 'flawless_s', 'flawless_f', '1')
ON DUPLICATE KEY UPDATE `craft_itemid`=VALUES(`craft_itemid`), `material`=VALUES(`material`);

-- 6. 菁英怪系統營運控制參數 (支援 DB _config 熱更新)
CREATE TABLE IF NOT EXISTS `_config` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `key` VARCHAR(64) NOT NULL,
  `val` VARCHAR(255) NOT NULL,
  `note` VARCHAR(255) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key_uniq` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `_config` (`key`, `val`, `note`) VALUES
  ('EliteMonsterSwitch', 'true', '菁英怪系統總開關'),
  ('EliteMonsterTimeSchedule', '00:00-24:00', '菁英怪出沒時段 (全天生效)'),
  ('EliteNormalHpRate', '1.5', '普通難度怪物血量倍率'),
  ('EliteHardHpRate', '2.5', '困難難度怪物血量倍率'),
  ('EliteNightmareHpRate', '4.0', '惡夢難度怪物血量倍率'),
  ('EliteHellHpRate', '7.0', '地獄難度怪物血量倍率'),
  ('DarkLootDropRate', '1.0', '暗黑詞綴掉落倍率')
ON DUPLICATE KEY UPDATE `val`=VALUES(`val`);
