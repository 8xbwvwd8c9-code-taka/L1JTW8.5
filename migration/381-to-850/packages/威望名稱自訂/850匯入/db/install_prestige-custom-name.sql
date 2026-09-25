-- =======================================================
-- 850匯入 / DB install — 威望名稱自訂 (Prestige Custom Name)
-- 來源：atu381.w_威望名稱自訂 (1 row)
-- 目標：850 原生正規化 InnoDB 結構
-- =======================================================

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_prestige_custom_name`;
CREATE TABLE `w_prestige_custom_name` (
  `type_name` varchar(100) NOT NULL COMMENT '威望系統自訂顯示名稱',
  `note` varchar(255) DEFAULT '' COMMENT '設定備註說明',
  PRIMARY KEY (`type_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='威望系統全服自訂名稱';

INSERT INTO `w_prestige_custom_name` (`type_name`, `note`) VALUES
	('威望積分','自訂名稱(有關威望的字都會變更您設定的)');
