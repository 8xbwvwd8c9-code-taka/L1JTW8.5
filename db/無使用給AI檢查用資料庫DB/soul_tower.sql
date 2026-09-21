CREATE TABLE `soul_tower` (
  `rank` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `class` int(2) NOT NULL DEFAULT '0',
  `time` int(10) NOT NULL DEFAULT '0',
  `date` date NOT NULL,
  PRIMARY KEY (`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of soul_tower
-- ----------------------------

-- ----------------------------
-- Table structure for `spawnlist`
-- ----------------------------
DROP TABLE IF EXISTS `spawnlist`;
