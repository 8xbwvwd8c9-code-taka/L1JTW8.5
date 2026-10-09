CREATE TABLE `accounts` (
  `login` varchar(50) NOT NULL DEFAULT '',
  `password` varchar(50) DEFAULT NULL,
  `lastactive` datetime DEFAULT NULL,
  `access_level` int(3) NOT NULL DEFAULT '0' COMMENT 'Vip設定',
  `ip` varchar(20) NOT NULL DEFAULT '',
  `host` varchar(255) NOT NULL DEFAULT '',
  `online` int(1) NOT NULL DEFAULT '0',
  `banned` int(1) unsigned NOT NULL DEFAULT '0',
  `character_slot` int(2) unsigned NOT NULL DEFAULT '0',
  `warepassword` int(6) unsigned NOT NULL DEFAULT '0',
  `OnlineStatus` int(1) unsigned NOT NULL DEFAULT '0',
  `WorldShopAdena` int(10) NOT NULL DEFAULT '0',
  `TamPoint` int(10) NOT NULL DEFAULT '0' COMMENT 'Tam點數',
  PRIMARY KEY (`login`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of accounts
-- ----------------------------

-- ----------------------------
-- Table structure for `armor`
-- ----------------------------
DROP TABLE IF EXISTS `armor`;
