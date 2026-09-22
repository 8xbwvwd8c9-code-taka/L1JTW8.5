-- BUG-850-142
-- Atomic bid replacement requires the auction row and Adena rows to
-- participate in one transaction.

ALTER TABLE `house` ENGINE=InnoDB;
ALTER TABLE `character_items` ENGINE=InnoDB;

SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME IN ('house','character_items');
