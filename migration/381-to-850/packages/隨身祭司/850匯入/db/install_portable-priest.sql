-- =======================================================
-- 850匯入 / DB install — 隨身祭司輔助系統 (Portable Priest)
-- 來源：atu381.w_隨身祭司 (4 rows)
-- 目標：850 原生正規化 InnoDB 結構
-- =======================================================

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_portable_priest`;
CREATE TABLE `w_portable_priest` (
  `npc_id` int NOT NULL COMMENT '祭司NPC編號',
  `name` varchar(100) NOT NULL COMMENT '祭司名稱',
  `skill_ids` varchar(255) NOT NULL COMMENT '施放技能ID清單(逗號分隔)',
  `skill_mps` varchar(255) NOT NULL COMMENT '技能消耗MP清單(逗號分隔)',
  `note` varchar(255) DEFAULT '' COMMENT '技能名稱備註',
  PRIMARY KEY (`npc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='隨身祭司技能配置';

INSERT INTO `w_portable_priest` (`npc_id`, `name`, `skill_ids`, `skill_mps`, `note`) VALUES
	(93001,'初級隨身祭司','26,42','10,10','通暢氣脈術,體魄強健術')),
	(93002,'中級隨身祭司','26,42,151','10,10,10','通暢氣脈術,體魄強健術,大地防護')),
	(93003,'高級隨身祭司','26,42,168,79','10,10,10,10','通暢氣脈術,體魄強健術,鋼鐵防護,靈魂昇華')),
	(93004,'頂級隨身祭司','26,42,168,79,68','10,10,10,10,10','通暢氣脈術,體魄強健術,鋼鐵防護,靈魂昇華,聖結界'));
