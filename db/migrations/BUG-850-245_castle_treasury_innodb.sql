-- BUG-850-245
-- Castle treasury transfers must move public_money and the player's Adena
-- in one durable transaction.

ALTER TABLE `castle` ENGINE=InnoDB;
ALTER TABLE `character_items` ENGINE=InnoDB;

SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME IN ('castle','character_items');
