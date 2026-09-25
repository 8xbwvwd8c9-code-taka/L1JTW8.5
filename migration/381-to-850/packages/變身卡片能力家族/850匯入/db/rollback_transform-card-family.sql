-- =======================================================
-- 850導入 / DB rollback ─ transform-card-family
-- =======================================================
-- 順序：先 junction → 再 set → 再 login（FK 依賴順序）
DROP TABLE IF EXISTS `w_transform_card_set_require`;
DROP TABLE IF EXISTS `w_transform_card_set`;
DROP TABLE IF EXISTS `w_transform_card_login`;