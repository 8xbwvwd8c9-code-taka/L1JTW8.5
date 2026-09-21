CREATE TABLE `inns` (
  `keyid` int(10) NOT NULL DEFAULT '0',
  `note` varchar(45) DEFAULT NULL,
  `count` int(10) NOT NULL DEFAULT '0',
  `roomid` int(10) NOT NULL DEFAULT '0',
  `dueTime` datetime DEFAULT NULL,
  PRIMARY KEY (`keyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inns
-- ----------------------------

-- ----------------------------
-- Table structure for `letter`
-- ----------------------------
DROP TABLE IF EXISTS `letter`;
