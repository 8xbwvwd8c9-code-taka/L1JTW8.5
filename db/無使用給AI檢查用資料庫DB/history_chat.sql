CREATE TABLE `history_chat` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) NOT NULL,
  `char_name` varchar(20) NOT NULL,
  `type` varchar(45) NOT NULL,
  `description` varchar(255) NOT NULL,
  `record_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of history_chat
-- ----------------------------

-- ----------------------------
-- Table structure for `history_enchant`
-- ----------------------------
DROP TABLE IF EXISTS `history_enchant`;
