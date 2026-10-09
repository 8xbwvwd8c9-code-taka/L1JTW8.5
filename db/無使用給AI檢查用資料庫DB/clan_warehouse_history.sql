CREATE TABLE `clan_warehouse_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水號',
  `clan_id` int(10) NOT NULL COMMENT '角色id',
  `char_name` varchar(45) NOT NULL COMMENT '角色名稱',
  `type` tinyint(2) NOT NULL COMMENT '領出: 1, 存入:0',
  `item_name` varchar(45) NOT NULL COMMENT '物品名稱',
  `item_count` int(10) NOT NULL COMMENT '數量',
  `record_time` datetime NOT NULL COMMENT '提領時間',
  PRIMARY KEY (`id`),
  KEY `char_name` (`char_name`),
  KEY `clan_id` (`clan_id`)
) ENGINE=MyISAM AUTO_INCREMENT=16467 DEFAULT CHARSET=utf8 COMMENT='盟倉使用記錄';

-- ----------------------------
-- Records of clan_warehouse_history
-- ----------------------------

-- ----------------------------
-- Table structure for `commands`
-- ----------------------------
DROP TABLE IF EXISTS `commands`;
