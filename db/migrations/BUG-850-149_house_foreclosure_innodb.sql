-- BUG-850-149
-- House foreclosure atomicity requires clan_data and house to participate
-- in the same transactional commit/rollback boundary.

ALTER TABLE `clan_data` ENGINE=InnoDB;
ALTER TABLE `house` ENGINE=InnoDB;

SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME IN ('clan_data','house');
