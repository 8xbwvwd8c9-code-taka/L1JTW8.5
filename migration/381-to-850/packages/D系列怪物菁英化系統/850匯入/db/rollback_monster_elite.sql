-- =======================================================
-- 850匯入 / DB rollback — D系列怪物菁英化系統
-- =======================================================

DELETE FROM `_config` WHERE `parameter` IN ('EliteMonsterSwitch', 'EliteMonsterTimeSchedule');

DROP TABLE IF EXISTS `w_monster_affix_template`;
DROP TABLE IF EXISTS `w_elite_monster_config`;
DROP TABLE IF EXISTS `w_monster_spell_tier_rule`;
