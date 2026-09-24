-- BUG-850-246
-- CharBuffTable rewrites the complete per-character buff snapshot.
-- Transactional DELETE + INSERT rollback requires InnoDB.

ALTER TABLE `character_buff` ENGINE=InnoDB;

SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 'character_buff';
