-- =======================================================
-- 850導入 / DB install ─ 假人系統 (381+880融合單一架構)
-- 涵蓋：w_robot_config, w_robot_clan, w_robot_spawn, w_robot_chat
-- =======================================================

SET NAMES utf8mb4;

-- 1. 假人全域配置表
DROP TABLE IF EXISTS `w_robot_config`;
CREATE TABLE `w_robot_config` (
  `id` int(11) NOT NULL DEFAULT 1,
  `system_enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '系統總開關 1=開 0=關',
  `total_count` int(11) NOT NULL DEFAULT 500 COMMENT '野外總假人人數',
  `min_level` int(11) NOT NULL DEFAULT 65 COMMENT '假人最低等級',
  `max_level` int(11) NOT NULL DEFAULT 95 COMMENT '假人最高等級',
  `map_filter_mode` tinyint(1) NOT NULL DEFAULT 1 COMMENT '地圖過濾 1=黑名單 2=白名單',
  `excluded_maps` text COMMENT '排除地圖清單 (黑名單)',
  `included_maps` text COMMENT '允許地圖清單 (白名單)',
  `potion_red_ratio` double NOT NULL DEFAULT 0.9 COMMENT '紅水判定比例 (90%)',
  `potion_orange_ratio` double NOT NULL DEFAULT 0.7 COMMENT '橘水判定比例 (70%)',
  `potion_white_ratio` double NOT NULL DEFAULT 0.4 COMMENT '古白判定比例 (40%)',
  `flee_hp_ratio` double NOT NULL DEFAULT 0.25 COMMENT '殘血逃跑血量比例 (25%)',
  `flee_chance` int(11) NOT NULL DEFAULT 85 COMMENT '逃跑觸發回卷機率 (%)',
  `town_shop_enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '城鎮擺攤開關',
  `town_shop_chance` int(11) NOT NULL DEFAULT 60 COMMENT '城鎮商人比例 (%)',
  `resupply_enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '野外回村補給開關',
  `resupply_interval_min` int(11) NOT NULL DEFAULT 300 COMMENT '補給週期下限(秒)',
  `resupply_interval_max` int(11) NOT NULL DEFAULT 900 COMMENT '補給週期上限(秒)',
  `siege_enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '定時攻城開關',
  `siege_target_castle` int(11) NOT NULL DEFAULT 1 COMMENT '攻城目標城堡ID (1=肯特, 4=奇岩)',
  `siege_schedule_cron` varchar(50) NOT NULL DEFAULT '20:00' COMMENT '攻城定時觸發時間',
  `siege_attacker_count` int(11) NOT NULL DEFAULT 80 COMMENT '攻城假人動員數量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='假人系統全域配置';

INSERT INTO `w_robot_config` VALUES (
  1, 1, 500, 65, 95, 1,
  '15,29,52,64,260,300,340,350,360,370,620,800,99,5,6,83,84,446,447,16384,16896,17408,17920,18432,18944,19456,19968,20480,20992,21504,22016,22528,23040,23552,24064,24576,25088',
  '0,4,57,68,69,70',
  0.9, 0.7, 0.4, 0.25, 85,
  1, 60, 1, 300, 900,
  1, 1, '20:00', 80
);

-- 2. 假人專屬管理血盟表
DROP TABLE IF EXISTS `w_robot_clan`;
CREATE TABLE `w_robot_clan` (
  `clan_id` int(11) NOT NULL,
  `clan_name` varchar(45) NOT NULL DEFAULT '【王者之師】',
  `leader_id` int(11) NOT NULL DEFAULT 0,
  `leader_name` varchar(45) NOT NULL DEFAULT '王者之王',
  `castle_id` int(11) NOT NULL DEFAULT 0,
  `house_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`clan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='假人專屬管理血盟';

INSERT INTO `w_robot_clan` VALUES (99999, '【王者之師】', 999990, '王者之王', 0, 0);

-- 3. 假人名稱與模板表
DROP TABLE IF EXISTS `w_robot_spawn`;
CREATE TABLE `w_robot_spawn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `char_name` varchar(45) NOT NULL COMMENT '假人角色名稱',
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '職業: 0=王族 1=騎士 2=妖精 3=法師 4=黑妖',
  `sex` int(11) NOT NULL DEFAULT 0 COMMENT '性別: 0=男 1=女',
  `poly_id` int(11) NOT NULL DEFAULT 0 COMMENT '變身外觀ID (0=預設職業外觀)',
  `weapon_glow` tinyint(2) NOT NULL DEFAULT 8 COMMENT '武器發光 8=藍光 9=紅光 10=紫光',
  `doll_id` int(11) NOT NULL DEFAULT 0 COMMENT '跟隨娃娃ID',
  `is_siege_member` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否參與定時攻城',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='假人角色名稱與模板庫';

INSERT INTO `w_robot_spawn` (`char_name`, `type`, `sex`, `poly_id`, `weapon_glow`, `doll_id`, `is_siege_member`) VALUES
('王者之王', 0, 0, 0, 10, 41255, 1),
('狂斬天下', 1, 0, 6658, 9, 41256, 1),
('劍氣凌霄', 1, 1, 6671, 9, 41257, 1),
('弒魂狂徒', 1, 0, 61, 8, 41258, 1),
('百步穿楊', 2, 1, 138, 9, 41259, 1),
('落日追風', 2, 0, 734, 8, 41260, 1),
('烈焰焚天', 3, 0, 1186, 9, 41261, 1),
('冰霜挽歌', 3, 1, 2786, 10, 41262, 1),
('暗影匿行', 4, 0, 6658, 9, 41263, 1),
('幽冥收割', 4, 1, 6671, 10, 41264, 1);

-- 4. 假人社交與對話庫
DROP TABLE IF EXISTS `w_robot_chat`;
CREATE TABLE `w_robot_chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chat_type` varchar(20) NOT NULL COMMENT '類型: IDLE, ATTACKED, CALL_CLAN, KILL_PLAYER, FLEE, SIEGE',
  `content` varchar(255) NOT NULL COMMENT '對話內容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='假人對話庫';

INSERT INTO `w_robot_chat` (`chat_type`, `content`) VALUES
('IDLE', '這地圖掉寶率真不錯！'),
('IDLE', '收防卷、武卷，意者密我～'),
('ATTACKED', '敢偷襲我？找死！'),
('ATTACKED', '哪來的小白，吃我一刀！'),
('CALL_CLAN', '敢掃到我們血盟？全軍集火打死他！'),
('CALL_CLAN', '兄弟們上！把這傢伙送回村莊！'),
('KILL_PLAYER', '技術這麼差還敢出來混？躺好吧！'),
('KILL_PLAYER', '回去多練練再來挑戰本大爺！'),
('FLEE', '今天水喝光了，改天再找你算帳！'),
('FLEE', '好漢不吃眼前虧，先閃了！'),
('SIEGE', '【王者之師】全軍突擊！突破外城門！'),
('SIEGE', '保護君主！集火守護塔！城堡是我們的！');