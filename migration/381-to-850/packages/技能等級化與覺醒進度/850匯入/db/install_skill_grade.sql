-- =======================================================
-- 850匯入 / DB install — skill-grade-system
-- 包含：
-- 1. skills 表增加 grade 欄位 (MyISAM)
-- 2. 技能升階規則表 skill_upgrade_chain (InnoDB)
-- 3. 角色技能進度與覺醒紀錄表 character_skill_progress (InnoDB)
-- =======================================================

-- 1. 原生 skills 表擴展稀有度 grade 欄位
ALTER TABLE `skills`
  ADD COLUMN `grade` tinyint(1) NOT NULL DEFAULT -1
    COMMENT '-1=未分級 0=白色Common 1=綠色Advanced 2=藍色Rare 3=紅色Hero 4=紫色Legendary 5=金色Mythic'
    AFTER `action_id`;

ALTER TABLE `skills`
  ADD INDEX `idx_skill_grade` (`grade`);

-- 2. 技能階級鏈與覺醒規則表 (InnoDB)
CREATE TABLE IF NOT EXISTS `skill_upgrade_chain` (
  `skill_chain_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '技能鏈主鍵ID',
  `chain_name` varchar(64) NOT NULL DEFAULT '' COMMENT '技能鏈名稱',
  `book_item_id` int(10) unsigned NOT NULL COMMENT '對應技能書道具ID',
  `skill_id_lv1` int(10) unsigned NOT NULL COMMENT 'LV1技能ID',
  `skill_id_lv2` int(10) unsigned NOT NULL COMMENT 'LV2技能ID',
  `skill_id_lv3` int(10) unsigned NOT NULL COMMENT 'LV3技能ID',
  `upgrade_cost_books` int(5) unsigned NOT NULL DEFAULT 5 COMMENT '每階升級所需技能書數量(預設5本)',
  `awakening_a_skill_id` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '覺醒A技能ID',
  `awakening_a_materials` varchar(255) NOT NULL DEFAULT '' COMMENT '覺醒A所需材料(格式: item_id:count,item_id:count)',
  `awakening_b_skill_id` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '覺醒B技能ID',
  `awakening_b_materials` varchar(255) NOT NULL DEFAULT '' COMMENT '覺醒B所需材料(格式: item_id:count,item_id:count)',
  PRIMARY KEY (`skill_chain_id`),
  UNIQUE KEY `uk_book_item` (`book_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='技能階級鏈與覺醒規則表';

-- 3. 角色技能等級與覺醒進度表 (InnoDB)
CREATE TABLE IF NOT EXISTS `character_skill_progress` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `char_id` int(10) unsigned NOT NULL COMMENT '角色ID',
  `skill_chain_id` int(10) unsigned NOT NULL COMMENT '對應skill_upgrade_chain主鍵',
  `current_skill_id` int(10) unsigned NOT NULL COMMENT '目前生效技能ID',
  `stage` tinyint(2) unsigned NOT NULL DEFAULT 1 COMMENT '當前階級: 1=LV1, 2=LV2, 3=LV3',
  `duplicate_book_count` int(5) unsigned NOT NULL DEFAULT 0 COMMENT '累積同技能書本數(0~4)',
  `awakening` enum('NONE','A','B') NOT NULL DEFAULT 'NONE' COMMENT '覺醒狀態: 永久互斥',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_char_chain` (`char_id`, `skill_chain_id`),
  KEY `idx_char_id` (`char_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色技能等級與覺醒進度表';
