-- =======================================================
-- 850匯入 / DB rollback — 裝武強化等級能力加成 (w_item_enchant_level_bonus)
-- =======================================================

DROP VIEW IF EXISTS `w_裝武強化lv`;
DROP TABLE IF EXISTS `w_item_enchant_level_bonus`;
DELETE FROM `_config` WHERE `key` = 'ItemEnchantLevelBonusSwitch';
