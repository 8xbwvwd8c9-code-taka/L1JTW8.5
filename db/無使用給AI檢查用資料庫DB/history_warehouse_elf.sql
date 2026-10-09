CREATE TABLE `history_warehouse_elf` (
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
-- Records of history_warehouse_elf
-- ----------------------------

-- ----------------------------
-- Table structure for `history_world_shop`
-- ----------------------------
DROP TABLE IF EXISTS `history_world_shop`;
