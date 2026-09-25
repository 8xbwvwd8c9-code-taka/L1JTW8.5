-- =======================================================
-- 850匯入 / DB install — 定時鐘點怪物活動施放 (Hourly Monster Spawn)
-- 來源：atu381.w_鐘點怪物施放 (20 rows)
-- 目標：850 原生正規化 InnoDB 結構
-- =======================================================

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_hourly_monster_spawn`;
CREATE TABLE `w_hourly_monster_spawn` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '唯一序號',
  `limit_day_of_week` int NOT NULL DEFAULT -1 COMMENT '限制星期 (-1=每天, 1=週日..7=週六)',
  `hour` int NOT NULL DEFAULT 0 COMMENT '時 (0~23)',
  `minute` int NOT NULL DEFAULT 0 COMMENT '分 (0~59)',
  `npc_id` int NOT NULL DEFAULT 0 COMMENT 'NPC/怪物編號',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '怪物名稱備註',
  `count` int NOT NULL DEFAULT 1 COMMENT '生成數量',
  `locx` int NOT NULL DEFAULT 0 COMMENT 'X座標',
  `locy` int NOT NULL DEFAULT 0 COMMENT 'Y座標',
  `random_range` int NOT NULL DEFAULT 0 COMMENT '中心隨機分佈範圍',
  `map_id` int NOT NULL DEFAULT 0 COMMENT '地圖編號',
  `msg_notice` varchar(255) DEFAULT NULL COMMENT '全服廣播內容',
  `msg_special` varchar(255) DEFAULT NULL COMMENT '特殊炫彩公告',
  `gfx_id` int DEFAULT NULL COMMENT '畫面全螢幕/座標特效',
  `is_teleport_portal` tinyint NOT NULL DEFAULT 0 COMMENT '是否為活動傳送門 (1=是, 0=否)',
  `tele_x` int NOT NULL DEFAULT 0 COMMENT '傳送目標X',
  `tele_y` int NOT NULL DEFAULT 0 COMMENT '傳送目標Y',
  `tele_m` int NOT NULL DEFAULT 0 COMMENT '傳送目標Map',
  `exist_time_sec` int NOT NULL DEFAULT 0 COMMENT '存在秒數 (0=不自動消失)',
  PRIMARY KEY (`id`),
  KEY `idx_time` (`hour`, `minute`, `limit_day_of_week`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='定時活動怪物排程施放設定';

INSERT INTO `w_hourly_monster_spawn` (
  `limit_day_of_week`, `hour`, `minute`, `npc_id`, `name`, `count`,
  `locx`, `locy`, `random_range`, `map_id`, `msg_notice`, `msg_special`,
  `gfx_id`, `is_teleport_portal`, `tele_x`, `tele_y`, `tele_m`, `exist_time_sec`
) VALUES
	(-1,20,30,2200564,'門票銷售NPC',1,33444,32797,0,4,'【無限大戰】銷售員出現在奇岩媽祖前面，將於30分鐘離開。','\\fY【無限大戰】\\f=銷售員出現在奇岩媽祖前面，將於30分鐘離開。',NULL,0,0,0,0,1800)),
	(-1,21,0,0,'',0,0,0,0,0,'【戰況廣播】無限大戰將於1分鐘後來襲','\\fY【公告】\\f=無限大戰將於1分鐘後來襲',NULL,0,0,0,0,10)),
	(-1,21,1,45479,'鋼鐵高倫',30,32699,32896,15,89,'【戰況廣播】第一波×恐怖鋼鐵高侖來襲','\\fY【戰況廣播】\\f=第一波×恐怖鋼鐵高侖來襲',NULL,0,0,0,0,0)),
	(-1,21,5,45545,'黑長者',2,32699,32896,15,89,'【戰況廣播】第二波×黑者長 來襲','\\fY【戰況廣播】\\f=第二波×黑者長 來襲',NULL,0,0,0,0,0)),
	(-1,21,10,45573,'雷巴風特',2,32699,32896,15,89,'【戰況廣播】第三波×雷燄巴風特 來襲','\\fY【戰況廣播】\\f=第三波×雷燄巴風特 來襲',NULL,0,0,0,0,0)),
	(-1,21,15,45451,'思客巴女皇',20,32669,32896,15,89,'【戰況廣播】第四波×思客巴女皇 來襲','\\fY【戰況廣播】\\f=第四波×思客巴女皇 來襲',NULL,0,0,0,0,0)),
	(-1,21,20,2200566,'火焰死騎',1,32699,32896,15,89,'【戰況廣播】第五波×火焰死亡騎士 來襲','\\fY【戰況廣播】\\f=第五波×死亡騎士 來襲',NULL,0,0,0,0,0)),
	(-1,21,25,45606,'吸血鬼',1,32699,32896,15,89,'【戰況廣播】第六波×吸血鬼 來襲','\\fY【戰況廣播】\\f=第六波×吸血鬼 來襲',NULL,0,0,0,0,0)),
	(-1,21,30,45583,'巴列斯',1,32699,32896,15,89,'【戰況廣播】第七波×巴列斯 來襲','\\fY【戰況廣播】\\f=第七波×巴列斯 來襲',NULL,0,0,0,0,0)),
	(-1,21,35,45600,'烈炎克特',2,32699,32896,15,89,'【戰況廣播】第八波×飛龍 來襲','\\fY【戰況廣播】\\f=第八波×飛龍 來襲',NULL,0,0,0,0,0)),
	(-1,21,40,45617,'不死鳥',1,32699,32896,15,89,'【戰況廣播】第九波×烈炎克特 來襲','\\fY【戰況廣播】\\f=第九波×烈炎克特來襲',NULL,0,0,0,0,0)),
	(-1,21,45,2200561,'九尾狐',1,32699,32896,15,89,'【戰況廣播】第十波×不死鳥 來襲','\\fY【戰況廣播】\\f=第十波×不死鳥 來襲',NULL,0,0,0,0,0)),
	(-1,21,50,2200562,'牛魔王',1,32699,32896,15,89,'【戰況廣播】倒數第二波×[大BOSS]涅槃九尾胡 來襲','\\fY【戰況廣播】\\f=倒數第二波×[大BOSS]涅槃九尾胡 來襲',NULL,0,0,0,0,1200)),
	(-1,21,55,2200513,'世界-巨棒守護者',1,32699,32896,15,89,'【戰況廣播】最後一波×[大BOSS]牛魔王 來襲','\\fY【戰況廣播】\\f=最後一波×[大BOSS]牛魔王 來襲',NULL,0,0,0,0,1200)),
	(-1,14,0,0,'',0,0,0,0,0,'【公告】感謝大家踴躍參與～感謝支持^^','\\fY【公告】\\f=感謝大家踴躍參與～感謝支持^^',NULL,0,0,0,0,0)),
	(-1,15,0,0,'',0,0,0,0,0,'【公告】感謝大家踴躍參與～感謝支持^^','\\fY【公告】\\f=感謝大家踴躍參與～感謝支持^^',NULL,0,0,0,0,0)),
	(-1,16,0,0,'',0,0,0,0,0,'【公告】感謝大家踴躍參與～感謝支持^^','\\fY【公告】\\f=感謝大家踴躍參與～感謝支持^^',NULL,0,0,0,0,0)),
	(-1,17,0,0,'',0,0,0,0,0,'【公告】感謝大家踴躍參與～感謝支持^^','\\fY【公告】\\f=請養成每天閱讀論壇更新歷程的好習慣…',NULL,0,0,0,0,0)),
	(-1,18,0,0,'',0,0,0,0,0,'【公告】感謝大家踴躍參與～感謝支持^^','\\fY【公告】\\f=別忘了去粉絲團分享活動文章領好禮喔…',NULL,0,0,0,0,0)),
	(-1,19,0,0,'',0,0,0,0,0,'【公告】感謝大家踴躍參與～感謝支持^^','\\fY【公告】\\f=請養成每天閱讀論壇更新歷程的好習慣…',NULL,0,0,0,0,0));
