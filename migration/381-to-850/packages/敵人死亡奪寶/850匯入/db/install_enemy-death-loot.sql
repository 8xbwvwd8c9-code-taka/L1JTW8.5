-- =======================================================
-- 850導入 / DB install ─ 敵人死亡奪寶 (紅人假人邊緣刷新追殺與奪寶)
-- 表名：w_enemy_death_loot
-- =======================================================
SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_enemy_death_loot`;
CREATE TABLE `w_enemy_death_loot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL COMMENT '掠奪之道具ID (如金幣40308)',
  `note` varchar(45) NOT NULL DEFAULT '' COMMENT '道具說明',
  `steal_chance` int(11) NOT NULL DEFAULT 100 COMMENT '掠奪觸發機率 (%)',
  `min_steal_count` int(11) NOT NULL DEFAULT 10000 COMMENT '最低搶奪數量',
  `max_steal_count` int(11) NOT NULL DEFAULT 30000 COMMENT '最高搶奪數量',
  `is_broadcast` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否全服廣播',
  `drop_on_floor` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1:掉落地上 0:直接進殺手背包',
  `anti_steal_item_id` int(11) NOT NULL DEFAULT 0 COMMENT '免死/防奪寶道具ID (0=無)',
  `min_level` int(11) NOT NULL DEFAULT 0 COMMENT '限制最低等級',
  `mete_level` int(11) NOT NULL DEFAULT 0 COMMENT '限制轉生等級',
  `drop_msg` varchar(255) NOT NULL DEFAULT '玩家:[%s]死亡,被紅人假人[%s]搶奪了(%s)' COMMENT '廣播公告內容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='敵人死亡奪寶與賞金設定';

INSERT INTO `w_enemy_death_loot` (`item_id`, `note`, `steal_chance`, `min_steal_count`, `max_steal_count`, `is_broadcast`, `drop_on_floor`, `anti_steal_item_id`, `min_level`, `mete_level`, `drop_msg`) VALUES
(40308, '金幣', 100, 10000, 30000, 1, 1, 0, 0, 0, '玩家:[%s]死亡,被紅人假人[%s]搶奪了(%s)');