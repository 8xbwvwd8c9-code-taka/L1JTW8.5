-- BUG-850-275
-- New-quest reward claim atomicity requires all durable participants to share
-- the same transactional engine. character_quests_new is already InnoDB.
-- Apply once before enabling the repaired cmd524 claim path.

ALTER TABLE `character_items` ENGINE=InnoDB;
ALTER TABLE `characters` ENGINE=InnoDB;

SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME IN ('character_items', 'character_quests_new', 'characters');
