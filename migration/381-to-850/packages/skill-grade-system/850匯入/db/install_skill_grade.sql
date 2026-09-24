-- =======================================================
-- 850匯入 / DB install — skill-grade-system
-- Target: skills table
-- Decision: HOLD（待 Java adapter 驗證後升級）
-- =======================================================

-- 前置確認：確認 grade 欄位尚未存在
-- 若已存在則此 ALTER 會失敗，屬預期行為（idempotent check）
ALTER TABLE `skills`
  ADD COLUMN `grade` tinyint(1) NOT NULL DEFAULT -1
    COMMENT '-1=未分級 0=白色Common 1=綠色Advanced 2=藍色Rare 3=紅色Hero 4=紫色Legendary 5=金色Mythic'
    AFTER `action_id`;

-- 索引：依 grade 查詢（合成/過濾用）
ALTER TABLE `skills`
  ADD INDEX `idx_skill_grade` (`grade`);
