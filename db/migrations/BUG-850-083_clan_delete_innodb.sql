-- BUG-850-083
-- Clan deletion deletes clan_warehouse_history and clan_data in one JDBC transaction.
-- Both tables must therefore use a transactional engine or rollback cannot restore atomicity.
ALTER TABLE `clan_warehouse_history` ENGINE=InnoDB;
ALTER TABLE `clan_data` ENGINE=InnoDB;
