CREATE TABLE `castle` (
  `castle_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL DEFAULT '',
  `war_time` datetime DEFAULT NULL,
  `tax_rate` int(11) NOT NULL DEFAULT '0',
  `public_money` int(11) NOT NULL DEFAULT '0',
  `mercenary_count_0` int(3) NOT NULL DEFAULT '0',
  `mercenary_count_1` int(3) NOT NULL DEFAULT '0',
  `mercenary_count_2` int(3) NOT NULL DEFAULT '0',
  `mercenary_count_3` int(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`castle_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of castle
-- ----------------------------
INSERT INTO `castle` VALUES ('1', '肯特城', '2018-01-10 19:08:31', '10', '2000000000', '0', '0', '0', '0');
INSERT INTO `castle` VALUES ('2', '妖魔城', '2018-01-03 22:21:31', '10', '708264', '0', '0', '0', '0');
INSERT INTO `castle` VALUES ('3', '風木城', '2018-01-03 22:21:31', '10', '67402832', '145', '125', '125', '125');
INSERT INTO `castle` VALUES ('4', '奇岩城', '2018-01-03 22:21:31', '10', '9309849', '0', '0', '0', '0');
INSERT INTO `castle` VALUES ('5', '海音城', '2018-11-02 02:24:21', '10', '38083344', '0', '0', '0', '0');
INSERT INTO `castle` VALUES ('6', '侏儒城', '2018-11-02 02:24:21', '10', '303490211', '0', '0', '0', '0');
INSERT INTO `castle` VALUES ('7', '亞丁城', '2018-11-02 02:24:21', '10', '58086673', '0', '0', '0', '0');
INSERT INTO `castle` VALUES ('8', '狄亞得要塞', '2018-11-02 02:24:21', '10', '1150026497', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `characters`
-- ----------------------------
DROP TABLE IF EXISTS `characters`;
