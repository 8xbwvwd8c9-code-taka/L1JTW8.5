-- BUG-850-027/089/100/102/103
-- Character deletion is only atomic when every table in its durable boundary is transactional.
ALTER TABLE `characters` ENGINE=InnoDB;
ALTER TABLE `character_buddys` ENGINE=InnoDB;
ALTER TABLE `character_buff` ENGINE=InnoDB;
ALTER TABLE `character_config` ENGINE=InnoDB;
ALTER TABLE `character_equip` ENGINE=InnoDB;
ALTER TABLE `character_gift` ENGINE=InnoDB;
ALTER TABLE `character_items` ENGINE=InnoDB;
ALTER TABLE `character_quests` ENGINE=InnoDB;
ALTER TABLE `character_quests_new` ENGINE=InnoDB;
ALTER TABLE `character_skills` ENGINE=InnoDB;
ALTER TABLE `character_teleport` ENGINE=InnoDB;
ALTER TABLE `character_warehouse_only` ENGINE=InnoDB;
ALTER TABLE `clan_members` ENGINE=InnoDB;
ALTER TABLE `mail` ENGINE=InnoDB;
ALTER TABLE `soul_tower` ENGINE=InnoDB;
