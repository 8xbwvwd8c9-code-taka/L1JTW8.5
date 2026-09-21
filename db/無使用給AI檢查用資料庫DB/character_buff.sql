CREATE TABLE `character_buff` (
  `char_obj_id` int(10) NOT NULL DEFAULT '0',
  `skill_id` int(10) unsigned NOT NULL DEFAULT '0',
  `remaining_time` int(10) NOT NULL DEFAULT '0',
  `poly_id` int(10) DEFAULT '0',
  `limit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`char_obj_id`,`skill_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_buff
-- ----------------------------

-- ----------------------------
-- Table structure for `character_config`
-- ----------------------------
DROP TABLE IF EXISTS `character_config`;
