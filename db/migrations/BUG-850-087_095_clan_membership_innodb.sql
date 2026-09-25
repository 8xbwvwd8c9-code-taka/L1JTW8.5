-- BUG-850-087 / BUG-850-095
-- Clan merge/kick transaction boundaries require all participating tables to be transactional.
ALTER TABLE `characters` ENGINE=InnoDB;
ALTER TABLE `clan_members` ENGINE=InnoDB;
ALTER TABLE `clan_data` ENGINE=InnoDB;
ALTER TABLE `clan_warehouse_history` ENGINE=InnoDB;
