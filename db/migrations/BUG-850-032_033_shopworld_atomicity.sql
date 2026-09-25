-- BUG-850-032 / BUG-850-033
-- ShopWorld claim/pending durability requires both tables to participate
-- in one transactional authority boundary.

ALTER TABLE `character_shop` ENGINE=InnoDB;
ALTER TABLE `character_items` ENGINE=InnoDB;

SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME IN ('character_shop', 'character_items');
