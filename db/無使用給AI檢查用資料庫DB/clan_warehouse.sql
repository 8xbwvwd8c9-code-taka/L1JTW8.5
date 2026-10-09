CREATE TABLE `clan_warehouse` (
  `id` int(11) NOT NULL,
  `clan_name` varchar(45) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `is_equipped` int(11) DEFAULT NULL,
  `enchantlvl` int(11) DEFAULT NULL,
  `is_id` int(11) DEFAULT NULL,
  `durability` int(11) DEFAULT NULL,
  `charge_count` int(11) DEFAULT NULL,
  `temp_value` int(11) DEFAULT NULL,
  `last_used` datetime DEFAULT NULL,
  `bless` int(11) DEFAULT NULL,
  `attr_enchant_kind` int(11) DEFAULT NULL,
  `attr_enchant_level` int(11) DEFAULT NULL,
  `super_enchant_field_1` int(10) NOT NULL DEFAULT '0',
  `super_enchant_field_2` int(10) NOT NULL DEFAULT '0',
  `super_enchant_field_3` int(10) NOT NULL DEFAULT '0',
  `super_enchant_field_4` int(10) NOT NULL DEFAULT '0',
  `limit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `key_id` (`clan_name`)
) ENGINE=MyISAM AUTO_INCREMENT=277964894 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clan_warehouse
-- ----------------------------

-- ----------------------------
-- Table structure for `clan_warehouse_history`
-- ----------------------------
DROP TABLE IF EXISTS `clan_warehouse_history`;
