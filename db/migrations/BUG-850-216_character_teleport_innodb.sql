-- BUG-850-216
-- Bookmark reorder must persist the complete ordering snapshot atomically
-- before publishing the new order/favorite state to RAM.

ALTER TABLE `character_teleport` ENGINE=InnoDB;

SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 'character_teleport';
