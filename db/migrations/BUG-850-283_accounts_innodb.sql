-- BUG-850-283
-- ShopWorld purchase atomicity requires both durable tables to participate
-- in the same InnoDB transaction. character_shop is already InnoDB.
-- Apply this once to existing 8.5 databases before enabling ShopWorld purchases.

ALTER TABLE `accounts` ENGINE=InnoDB;

SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME IN ('accounts', 'character_shop');
