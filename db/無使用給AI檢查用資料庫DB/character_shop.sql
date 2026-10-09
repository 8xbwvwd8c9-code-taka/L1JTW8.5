CREATE TABLE `character_shop` (
  `acc_name` varchar(45) NOT NULL,
  `indexid` int(10) NOT NULL,
  `itemid` int(10) NOT NULL,
  PRIMARY KEY (`acc_name`,`indexid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_shop
-- ----------------------------

-- ----------------------------
-- Table structure for `character_skills`
-- ----------------------------
DROP TABLE IF EXISTS `character_skills`;
