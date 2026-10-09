CREATE TABLE `character_luckydraw` (
  `acc_name` varchar(45) NOT NULL,
  `indexid` int(10) NOT NULL,
  `itemid` int(10) NOT NULL,
  `enchant` int(3) NOT NULL DEFAULT '0',
  `count` int(10) NOT NULL,
  `bless` int(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (`acc_name`,`indexid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_luckydraw
-- ----------------------------

-- ----------------------------
-- Table structure for `character_mobs`
-- ----------------------------
DROP TABLE IF EXISTS `character_mobs`;
