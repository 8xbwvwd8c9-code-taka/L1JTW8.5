CREATE TABLE `character_config` (
  `object_id` int(10) NOT NULL DEFAULT '0',
  `data` blob,
  PRIMARY KEY (`object_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_config
-- ----------------------------

-- ----------------------------
-- Table structure for `character_elf_warehouse`
-- ----------------------------
DROP TABLE IF EXISTS `character_elf_warehouse`;
