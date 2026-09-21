CREATE TABLE `clan_members` (
  `clan_id` int(10) unsigned NOT NULL DEFAULT '0',
  `char_id` int(11) unsigned NOT NULL DEFAULT '0',
  `char_name` varchar(45) NOT NULL,
  `date` date NOT NULL,
  `notes` varchar(60) NOT NULL,
  PRIMARY KEY (`char_id`),
  KEY `char_id` (`char_id`),
  KEY `clan_id` (`clan_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='血盟成員記錄';

-- ----------------------------
-- Records of clan_members
-- ----------------------------

-- ----------------------------
-- Table structure for `clan_warehouse`
-- ----------------------------
DROP TABLE IF EXISTS `clan_warehouse`;
