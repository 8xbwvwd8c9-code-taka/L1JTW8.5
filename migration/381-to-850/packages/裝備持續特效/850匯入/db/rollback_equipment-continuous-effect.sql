-- =======================================================
-- 850匯入 / DB rollback — 裝備持續特效 (w_item_continuous_effect)
-- =======================================================

DROP VIEW IF EXISTS `w_裝備持續特效`;
DROP TABLE IF EXISTS `w_item_continuous_effect`;
DELETE FROM `_config` WHERE `key` IN ('EquipmentContinuousEffectSwitch', 'EquipmentContinuousEffectInterval');
