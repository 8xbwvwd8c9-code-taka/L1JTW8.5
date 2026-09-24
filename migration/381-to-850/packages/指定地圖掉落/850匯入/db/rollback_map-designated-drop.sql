-- =======================================================
-- 850匯入 / DB rollback — 指定地圖掉落與全地圖掉落
-- =======================================================
DROP TABLE IF EXISTS `w_map_designated_drop`;
DELETE FROM `_config` WHERE `parameter` IN ('MapDesignatedDropSwitch', 'GlobalMapDropSwitch');
