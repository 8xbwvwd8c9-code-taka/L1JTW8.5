-- BUG-850-166
-- Clan creation atomicity requires clan_data, clan_members, characters,
-- and character_items to participate in one transaction.

ALTER TABLE `clan_data` ENGINE=InnoDB;
ALTER TABLE `clan_members` ENGINE=InnoDB;
ALTER TABLE `characters` ENGINE=InnoDB;
ALTER TABLE `character_items` ENGINE=InnoDB;

SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME IN ('clan_data','clan_members','characters','character_items');
