CREATE TABLE `character_mobs` (
  `login` varchar(20) NOT NULL,
  `data` blob NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_mobs
-- ----------------------------

-- ----------------------------
-- Table structure for `character_mobs_week`
-- ----------------------------
DROP TABLE IF EXISTS `character_mobs_week`;
