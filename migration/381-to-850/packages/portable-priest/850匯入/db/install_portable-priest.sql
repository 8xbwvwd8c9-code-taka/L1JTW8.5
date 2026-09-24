-- ============================================================
-- install_portable-priest.sql
-- Module: portable-priest (隨身祭司)
-- Decision: HOLD — 未實作，此 SQL 為結構預備稿
-- Source: w_隨身祭司_202609221205.sql (381 DB, 520 bytes)
-- ============================================================

-- [HOLD] 以下 DDL 根據 381 INSERT 結構逆向推導，尚未在 850 驗證

CREATE TABLE IF NOT EXISTS `w_隨身祭司` (
  `npc_id`    INT          NOT NULL COMMENT '隨身祭司的 NPC ID',
  `name`      VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '隨身祭司顯示名稱',
  `skill_id`  VARCHAR(128) NOT NULL DEFAULT '' COMMENT '施放技能 ID 列表（逗號分隔）',
  `skill_mp`  VARCHAR(128) NOT NULL DEFAULT '' COMMENT '各技能消耗 MP（逗號分隔，對應 skill_id 順序）',
  `note`      VARCHAR(256) NOT NULL DEFAULT '' COMMENT '技能名稱備注（逗號分隔）',
  PRIMARY KEY (`npc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='隨身祭司設定表';

-- 381 來源資料（4 等級祭司）
-- 技能 ID 對照：26=通暢氣脈術, 42=體魄強健術, 151=大地防護, 168=鋼鐵防護, 79=靈魂昇華, 68=聖結界
INSERT INTO `w_隨身祭司` (`npc_id`, `name`, `skill_id`, `skill_mp`, `note`) VALUES
  (93001, '初級隨身祭司', '26,42',       '10,10',          '通暢氣脈術,體魄強健術'),
  (93002, '中級隨身祭司', '26,42,151',   '10,10,10',       '通暢氣脈術,體魄強健術,大地防護'),
  (93003, '高級隨身祭司', '26,42,168,79','10,10,10,10',    '通暢氣脈術,體魄強健術,鋼鐵防護,靈魂昇華'),
  (93004, '頂級隨身祭司', '26,42,168,79,68','10,10,10,10,10','通暢氣脈術,體魄強健術,鋼鐵防護,靈魂昇華,聖結界');
