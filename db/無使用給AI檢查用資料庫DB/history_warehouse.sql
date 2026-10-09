CREATE TABLE `history_warehouse` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) NOT NULL,
  `char_name` varchar(20) NOT NULL,
  `type` varchar(45) NOT NULL,
  `itemid` int(10) NOT NULL,
  `description` varchar(255) NOT NULL,
  `record_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of history_warehouse
-- ----------------------------

-- ----------------------------
-- Table structure for `history_warehouse_clan`
-- ----------------------------
DROP TABLE IF EXISTS `history_warehouse_clan`;
