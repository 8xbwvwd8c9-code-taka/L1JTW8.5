-- =======================================================
-- 850匯入 / DB install — 狩獵怪物任務全家族 (Hunt Monster Quest Family)
-- 包含：
-- 1. w_hunt_quest (4 rows) — 任務目標怪物與進度
-- 2. w_hunt_quest_map (2 rows) — 地圖殺怪計數與完成給予
-- 3. w_hunt_quest_system (8 rows) — NPC對話觸發與等級檢查
-- =======================================================

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_hunt_quest`;
CREATE TABLE `w_hunt_quest` (
  `quest_id` int NOT NULL COMMENT '任務編號',
  `quest_step` int NOT NULL COMMENT '任務階段',
  `note` varchar(100) DEFAULT '' COMMENT '任務說明備註',
  `lv` int NOT NULL DEFAULT 1 COMMENT '等級限制',
  `mob_ids` varchar(255) NOT NULL COMMENT '目標怪物ID清單(逗號分隔)',
  `mob_counts` varchar(255) NOT NULL COMMENT '目標怪物數量清單(逗號分隔)',
  `item_id` varchar(255) DEFAULT '0' COMMENT '獎勵道具ID',
  `item_lv` varchar(50) DEFAULT '0' COMMENT '獎勵道具強化度',
  `item_count` varchar(50) DEFAULT '1' COMMENT '獎勵道具數量',
  `save_quest_step` int NOT NULL DEFAULT 255 COMMENT '完成後保存階段',
  `tele_x` int NOT NULL DEFAULT 0,
  `tele_y` int NOT NULL DEFAULT 0,
  `tele_m` int NOT NULL DEFAULT 0,
  `tele_delay` int NOT NULL DEFAULT 0,
  `addexp` bigint NOT NULL DEFAULT 0 COMMENT '完成獎勵經驗值',
  PRIMARY KEY (`quest_id`, `quest_step`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='狩獵怪物任務目標與獎勵設定';

INSERT INTO `w_hunt_quest` (
  `quest_id`, `quest_step`, `note`, `lv`, `mob_ids`, `mob_counts`,
  `item_id`, `item_lv`, `item_count`, `save_quest_step`,
  `tele_x`, `tele_y`, `tele_m`, `tele_delay`, `addexp`
) VALUES
	(9002,1,'每日狩獵任務-[火窟地區]',52,'45203,45206,45284,45291,45365','40,50,60,60,20','240314','0','1',255,0,0,0,0,200000)),
	(9005,1,'每日狩獵任務-[龍之谷地區]',52,'45286,45270,45269','50,50,50','240317','0','1',255,0,0,0,0,200000)),
	(9004,1,'每日狩獵任務-[象牙塔地區]',52,'45141,45221,45162,45372,45322','50,50,50,50,50','240316','0','1',255,0,0,0,0,200000)),
	(9007,1,'每日狩獵任務-[遺忘地區]',52,'45392,45401,45457,45390,45387,45509,45505,45538,45470','40,45,40,40,50,50,35,35,25','240319','0','1',255,0,0,0,0,200000));

DROP TABLE IF EXISTS `w_hunt_quest_map`;
CREATE TABLE `w_hunt_quest_map` (
  `id` int NOT NULL AUTO_INCREMENT,
  `note` varchar(100) NOT NULL COMMENT '地圖任務說明',
  `npc_id` int NOT NULL COMMENT '接取NPC編號',
  `action` varchar(50) NOT NULL COMMENT '對話Action代碼',
  `level` int NOT NULL DEFAULT 1 COMMENT '等級限制',
  `need_item_id` int NOT NULL DEFAULT 0 COMMENT '任務需求道具',
  `need_item_count` int NOT NULL DEFAULT 0 COMMENT '需求道具數量',
  `quest_record_id` int NOT NULL DEFAULT 0 COMMENT '任務記錄QuestId',
  `map_id_1` int NOT NULL DEFAULT 0,
  `map_id_2` int NOT NULL DEFAULT 0,
  `map_id_3` int NOT NULL DEFAULT 0,
  `map_id_4` int NOT NULL DEFAULT 0,
  `map_id_5` int NOT NULL DEFAULT 0,
  `kill_mob_count` int NOT NULL DEFAULT 0 COMMENT '擊殺怪物總數',
  `give_item_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '完成獎勵道具',
  `give_item_count` varchar(50) NOT NULL DEFAULT '0' COMMENT '完成獎勵數量',
  `msg_accept` varchar(255) DEFAULT '' COMMENT '接取訊息',
  `msg_complete` varchar(255) DEFAULT '' COMMENT '完成訊息',
  `display_text` varchar(255) DEFAULT '' COMMENT '顯示狩獵進度文字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='地圖殺怪計數任務設定';

INSERT INTO `w_hunt_quest_map` (
  `note`, `npc_id`, `action`, `level`, `need_item_id`, `need_item_count`,
  `quest_record_id`, `map_id_1`, `map_id_2`, `map_id_3`, `map_id_4`, `map_id_5`,
  `kill_mob_count`, `give_item_id`, `give_item_count`, `msg_accept`, `msg_complete`, `display_text`
) VALUES
	('古魯丁地監',93065,'555000',15,44070,10,9050,9,10,11,12,13,10,'44070','100','接受古魯丁地監狩獵任務','恭喜完成古魯丁地監任務','狩獵該地圖任務怪物剩餘:')),
	('象牙塔',93065,'555001',30,44070,10,9051,78,79,80,81,82,10,'44070','100','接受象牙塔狩獵任務','恭喜完成象牙塔任務','狩獵該地圖任務怪物剩餘:'));

DROP TABLE IF EXISTS `w_hunt_quest_system`;
CREATE TABLE `w_hunt_quest_system` (
  `id` int NOT NULL AUTO_INCREMENT,
  `npc_id` int NOT NULL,
  `note` varchar(100) DEFAULT '',
  `action` varchar(50) NOT NULL,
  `check_level_min` int NOT NULL DEFAULT 1,
  `check_level_max` int NOT NULL DEFAULT 99,
  `check_class` int NOT NULL DEFAULT 0,
  `check_poly` int NOT NULL DEFAULT 0,
  `check_quest_id` int NOT NULL DEFAULT 0,
  `check_quest_set` int NOT NULL DEFAULT 0,
  `not_have_quest_id` int NOT NULL DEFAULT 0,
  `not_have_quest_order` int NOT NULL DEFAULT 0,
  `check_item` varchar(255) DEFAULT '',
  `check_item_count` varchar(255) DEFAULT '',
  `not_have_item` varchar(255) DEFAULT '',
  `not_have_item_count` varchar(255) DEFAULT '',
  `material` varchar(255) DEFAULT '',
  `material_count` varchar(255) DEFAULT '',
  `just_check_material` int NOT NULL DEFAULT 0,
  `give_item` varchar(255) DEFAULT '',
  `give_item_count` varchar(255) DEFAULT '',
  `save_quest_id` int NOT NULL DEFAULT 0,
  `save_quest_set` int NOT NULL DEFAULT 0,
  `show_html` varchar(100) DEFAULT '',
  `show_html_data` varchar(100) DEFAULT '',
  `show_not_have_html` varchar(100) DEFAULT '',
  `show_not_have_html_data` varchar(100) DEFAULT '',
  `tele_x` int NOT NULL DEFAULT 0,
  `tele_y` int NOT NULL DEFAULT 0,
  `tele_map_id` int NOT NULL DEFAULT 0,
  `time` int NOT NULL DEFAULT 0,
  `pc_message` varchar(255) DEFAULT '',
  `week` int NOT NULL DEFAULT 0,
  `start_time` int NOT NULL DEFAULT 0,
  `end_time` int NOT NULL DEFAULT 0,
  `add_exp` bigint NOT NULL DEFAULT 0,
  `delete_trigger_item` int NOT NULL DEFAULT 0,
  `quest_end` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='狩獵怪物任務NPC對話與條件派發';

INSERT INTO `w_hunt_quest_system` (
  `npc_id`, `note`, `action`, `check_level_min`, `check_level_max`,
  `check_class`, `check_poly`, `check_quest_id`, `check_quest_set`,
  `not_have_quest_id`, `not_have_quest_order`, `check_item`, `check_item_count`,
  `not_have_item`, `not_have_item_count`, `material`, `material_count`,
  `just_check_material`, `give_item`, `give_item_count`, `save_quest_id`, `save_quest_set`,
  `show_html`, `show_html_data`, `show_not_have_html`, `show_not_have_html_data`,
  `tele_x`, `tele_y`, `tele_map_id`, `time`, `pc_message`, `week`, `start_time`, `end_time`,
  `add_exp`, `delete_trigger_item`, `quest_end`
) VALUES
	(93059,'火窟地區','6003',52,99,0,0,9002,1,0,0,'','','','','','',0,'','',9002,1,'','','','',0,0,0,0,'成功接取狩獵任務',-1,-1,-1,0,0,0)),
	(93059,'火窟地區自動完成','6004',52,99,0,0,9002,255,0,0,'','','','','44070','75',0,'240490','10',9002,255,'','','','',0,0,0,0,'任務已完成',-1,-1,-1,200000,1,1)),
	(93059,'象牙塔區','6007',52,99,0,0,9004,1,0,0,'','','','','','',0,'','',9004,1,'','','','',0,0,0,0,'成功接取狩獵任務',-1,-1,-1,0,0,0)),
	(93059,'象牙塔區自動完成','6008',52,99,0,0,9004,255,0,0,'','','','','44070','50',0,'240490','10',9004,255,'','','','',0,0,0,0,'任務已完成',-1,-1,-1,200000,1,1)),
	(93059,'龍之谷區','6009',52,99,0,0,9005,1,0,0,'','','','','','',0,'','',9005,1,'','','','',0,0,0,0,'成功接取狩獵任務',-1,-1,-1,0,0,0)),
	(93059,'龍之谷區自動完成','6010',52,99,0,0,9005,255,0,0,'','','','','44070','25',0,'240490','10',9005,255,'','','','',0,0,0,0,'任務已完成',-1,-1,-1,200000,1,1)),
	(93059,'遺忘之島','6013',52,99,0,0,9007,1,0,0,'','','','','','',0,'','',9007,1,'','','','',0,0,0,0,'成功接取狩獵任務',-1,-1,-1,0,0,0)),
	(93059,'遺忘之島地區自動完成','6014',52,99,0,0,9007,255,0,0,'','','','','44070','100',0,'240490','10',9007,255,'','','','',0,0,0,0,'任務已完成',-1,-1,-1,200000,1,1));
