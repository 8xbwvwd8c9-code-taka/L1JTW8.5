-- =======================================================
-- 850匯入 / DB install — 怪物死亡召喚 (Monster Death Spawn)
-- 包含：
-- 1. _config 表開關與全域機率參數 (InnoDB)
-- 2. 怪物死亡召喚規則表 w_monster_death_spawn (InnoDB)
-- 3. 全域小怪/BOSS 與特定怪物預設種子資料
-- =======================================================

-- 1. _config 表控制條目 (INDEX 由 DB 自增，以參數名為唯一識別)
INSERT INTO `_config` (`parameter`, `value`, `note`)
VALUES
  ('MonsterDeathSpawnSwitch', '1', '怪物死亡召喚系統總開關 (0=關閉, 1=開啟, 預設1)'),
  ('MonsterDeathSpawnGlobalMobChance', '10', '全域小怪死亡召喚機率x10 (10=1.0%)'),
  ('MonsterDeathSpawnGlobalBossChance', '5', '全域BOSS死亡召喚機率x10 (5=0.5%)')
ON DUPLICATE KEY UPDATE `note`=VALUES(`note`);

-- 2. 怪物死亡召喚規則表 (支援全域預設與指定NPC規則)
CREATE TABLE IF NOT EXISTS `w_monster_death_spawn` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dead_npc_id` int(11) NOT NULL COMMENT '死亡NPC編號 (-1=全域小怪預設, -2=全域BOSS預設, >0為指定NPC)',
  `note` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '備註說明',
  `spawn_npc_id` int(11) NOT NULL DEFAULT 0 COMMENT '召喚NPC編號 (0=原地召喚同一種怪物)',
  `spawn_duration_min` int(11) NOT NULL DEFAULT 0 COMMENT '召喚怪物存活時間(分鐘, 0=自然存活)',
  `death_talk` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '怪物死亡時的喊話或廣播',
  `tele_x` int(11) DEFAULT 0 COMMENT '指定傳送X (-1=不傳送, 0=在擊殺者身邊隨機召喚)',
  `tele_y` int(11) DEFAULT 0 COMMENT '指定傳送Y (-1=不傳送, 0=在擊殺者身邊隨機召喚)',
  `tele_mapid` int(11) DEFAULT -1 COMMENT '指定傳送地圖ID (-1=擊殺者當前地圖)',
  `is_boss` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0=一般小怪, 1=BOSS級怪物',
  `trigger_chance_x10` int(5) NOT NULL DEFAULT 10 COMMENT '觸發機率x10 (10=1.0%, 5=0.5%)',
  `strength_pct_per_spawn` int(5) NOT NULL DEFAULT 150 COMMENT '每次復活能力強化值%x100 (150 = +1.5%)',
  `max_strength_pct` int(5) NOT NULL DEFAULT 10000 COMMENT '最大累積強化度上限%x100 (10000 = +100%)',
  `drop_bonus_pct_per_spawn` int(3) NOT NULL DEFAULT 1 COMMENT '每次復活掉落率加成% (1 = +1%)',
  PRIMARY KEY (`id`),
  KEY `idx_dead_npc` (`dead_npc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='怪物死亡召喚規則表 (381升級 + 企劃強化累積)';

-- 3. 種子資料：全域小怪與全域BOSS預設規則
INSERT INTO `w_monster_death_spawn`
  (`dead_npc_id`, `note`, `spawn_npc_id`, `spawn_duration_min`, `death_talk`, `tele_x`, `tele_y`, `tele_mapid`, `is_boss`, `trigger_chance_x10`, `strength_pct_per_spawn`, `max_strength_pct`, `drop_bonus_pct_per_spawn`)
VALUES
  (-1, '全域小怪預設 (1%機率在玩家周邊復活，每次+1.5%能力，上限100%，掉落+1%)', 0, 0, '我...還會再站起來的！', 0, 0, -1, 0, 10, 150, 10000, 1),
  (-2, '全域BOSS預設 (0.5%機率在玩家周邊復活，每次+1.5%能力，上限100%，掉落+1%)', 0, 0, '愚蠢的凡人，這只是我力量的一部分！', 0, 0, -1, 1, 5, 150, 10000, 1)
ON DUPLICATE KEY UPDATE `note`=VALUES(`note`);
