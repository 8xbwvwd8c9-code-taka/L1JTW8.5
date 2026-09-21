CREATE TABLE `clan_data` (
  `clan_id` int(10) unsigned NOT NULL,
  `clan_name` varchar(45) NOT NULL DEFAULT '',
  `leader_id` int(10) unsigned NOT NULL DEFAULT '0',
  `leader_name` varchar(45) NOT NULL DEFAULT '',
  `hascastle` int(10) unsigned NOT NULL DEFAULT '0',
  `hashouse` int(10) unsigned NOT NULL DEFAULT '0',
  `found_date` datetime NOT NULL,
  `announcement` varchar(160) NOT NULL,
  `emblem_id` int(10) NOT NULL DEFAULT '0',
  `emblem_status` tinyint(1) NOT NULL DEFAULT '0',
  `watch_clanid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`clan_id`)
) ENGINE=MyISAM AUTO_INCREMENT=277772895 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clan_data
-- ----------------------------

-- ----------------------------
-- Table structure for `clan_members`
-- ----------------------------
DROP TABLE IF EXISTS `clan_members`;
