-- =====================================================================
-- D系列怪物菁英化、暗黑隨機詞綴打寶與五大元素寶石孔洞系統 — 回滾腳本
-- =====================================================================

DROP TABLE IF EXISTS `character_items_dark_affix`;
DROP TABLE IF EXISTS `dark_gem_template`;

DELETE FROM `etcitem` WHERE `item_id` IN (50052, 50053, 50054, 50055, 50056, 50057, 50058, 50059, 50060);
UPDATE `etcitem` SET `use_type`='none' WHERE `item_id` IN (40044,40045,40046,40047,40048,40049,40050,40051,40052,40053,40054,40055);

DELETE FROM `html_craft` WHERE `action` = 'request flawless gem';
DELETE FROM `_config` WHERE `key` LIKE 'EliteMonster%' OR `key` LIKE 'DarkLoot%';
