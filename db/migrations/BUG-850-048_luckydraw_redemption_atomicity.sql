-- BUG-850-048
-- Lucky draw redemption must atomically consume character_luckydraw keys
-- and publish the stackable reward in character_items.
ALTER TABLE `character_luckydraw` ENGINE=InnoDB;
ALTER TABLE `character_items` ENGINE=InnoDB;

SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME IN ('character_luckydraw','character_items');
