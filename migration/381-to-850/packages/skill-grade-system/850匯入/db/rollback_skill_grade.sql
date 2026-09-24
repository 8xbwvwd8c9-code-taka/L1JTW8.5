-- =======================================================
-- 850匯入 / DB rollback — skill-grade-system
-- =======================================================

DROP TABLE IF EXISTS `character_skill_progress`;
DROP TABLE IF EXISTS `skill_upgrade_chain`;

ALTER TABLE `skills` DROP INDEX IF EXISTS `idx_skill_grade`;
ALTER TABLE `skills` DROP COLUMN IF EXISTS `grade`;
