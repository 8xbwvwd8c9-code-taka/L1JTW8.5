-- BUG-850-132
-- Town salary claim uses SELECT ... FOR UPDATE + rollback/commit.
-- Transactional storage is therefore a hard precondition.

ALTER TABLE `characters` ENGINE=InnoDB;

SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 'characters';
