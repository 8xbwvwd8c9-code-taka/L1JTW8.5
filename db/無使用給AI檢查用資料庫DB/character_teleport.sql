CREATE TABLE `character_teleport` (
  `id` int(10) unsigned NOT NULL,
  `char_id` int(10) unsigned NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL DEFAULT '',
  `locx` int(10) unsigned NOT NULL DEFAULT '0',
  `locy` int(10) unsigned NOT NULL DEFAULT '0',
  `mapid` int(10) unsigned NOT NULL DEFAULT '0',
  `order_id` int(3) NOT NULL,
  `order_id_fast` int(3) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `key_id` (`char_id`)
) ENGINE=MyISAM AUTO_INCREMENT=271827415 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_teleport
-- ----------------------------

-- ----------------------------
-- Table structure for `character_warehouse`
-- ----------------------------
DROP TABLE IF EXISTS `character_warehouse`;
