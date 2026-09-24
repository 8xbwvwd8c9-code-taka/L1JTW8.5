-- =======================================================
-- 850匯入 / DB rollback — 怪物死亡召喚
-- =======================================================
DROP TABLE IF EXISTS `w_monster_death_spawn`;
DELETE FROM `_config` WHERE `parameter` IN ('MonsterDeathSpawnSwitch', 'MonsterDeathSpawnGlobalMobChance', 'MonsterDeathSpawnGlobalBossChance');
