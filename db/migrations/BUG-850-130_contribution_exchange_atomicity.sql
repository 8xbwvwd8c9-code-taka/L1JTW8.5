-- BUG-850-130
-- NPC 81260 concentrated-potion exchange updates character_items and
-- characters.Contribution in one transaction. Both tables must be transactional.
-- Safe to run even if BUG-850-275 migration was already applied.

ALTER TABLE `character_items` ENGINE=InnoDB;
ALTER TABLE `characters` ENGINE=InnoDB;

SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME IN ('character_items', 'characters');
