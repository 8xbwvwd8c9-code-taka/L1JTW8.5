-- =======================================================
-- 850匯入 / DB rollback — skill-grade-system
-- =======================================================

ALTER TABLE `skills` DROP INDEX IF EXISTS `idx_skill_grade`;
ALTER TABLE `skills` DROP COLUMN IF EXISTS `grade`;
