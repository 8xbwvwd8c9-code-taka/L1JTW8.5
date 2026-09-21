CREATE TABLE `droplist_map` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mapid` int(5) NOT NULL,
  `itemid` int(10) NOT NULL,
  `note` varchar(45) NOT NULL DEFAULT '',
  `min` int(5) NOT NULL DEFAULT '1',
  `max` int(5) NOT NULL DEFAULT '1',
  `chance` int(5) NOT NULL DEFAULT '1000000',
  `enchantlvl` int(5) NOT NULL DEFAULT '0',
  `bless_change` int(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of droplist_map
-- ----------------------------
INSERT INTO `droplist_map` VALUES ('1', '430', '640658', '封印的惡魔鐮刀', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('2', '430', '640659', '封印的惡魔手套', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('3', '430', '640660', '封印的惡魔長靴', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('4', '430', '640661', '封印的石製手套', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('5', '430', '640662', '封印的奧里哈魯根短劍', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('6', '430', '640670', '封印的冰之女王魔杖', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('7', '275', '640696', '結晶的女性眼淚', '2', '4', '1000000', '0', '0');
INSERT INTO `droplist_map` VALUES ('8', '278', '640696', '結晶的女性眼淚', '2', '4', '1000000', '0', '0');
INSERT INTO `droplist_map` VALUES ('9', '121', '640653', '封印的潔尼斯戒指', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('10', '122', '640656', '封印的幻象眼魔的心眼', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('11', '123', '640657', '封印的馬昆斯斗篷', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('12', '124', '640707', '封印的激怒手套', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('13', '125', '640706', '封印的咆哮雙刀', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('14', '125', '640661', '封印的石製手套', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('15', '125', '640708', '封印的銀光斗篷', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('16', '126', '640655', '封印的木乃伊王的王冠', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('17', '127', '640660', '封印的惡魔長靴', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('18', '128', '640651', '封印的騎士范德之雙手劍', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('19', '129', '640654', '封印的巫妖斗篷', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('20', '130', '640705', '封印的殲滅者鎖鏈劍', '1', '1', '6000', '0', '0');
INSERT INTO `droplist_map` VALUES ('21', '7000', '40071', '烤焦的麵包屑', '1', '5', '150000', '0', '0');
INSERT INTO `droplist_map` VALUES ('22', '7000', '40043', '兔子的肝', '1', '3', '130000', '0', '25');
INSERT INTO `droplist_map` VALUES ('23', '7000', '640769', 'NC紀念幣', '1', '1', '500', '0', '0');
INSERT INTO `droplist_map` VALUES ('24', '7000', '640811', '香蕉蛋糕', '1', '1', '20000', '0', '0');
INSERT INTO `droplist_map` VALUES ('25', '7000', '640813', '櫻桃蛋糕', '1', '1', '20000', '0', '0');
INSERT INTO `droplist_map` VALUES ('26', '7000', '640815', '草莓蛋糕', '1', '1', '20000', '0', '0');
INSERT INTO `droplist_map` VALUES ('27', '7000', '640810', '聖誕香蕉蛋糕塊', '1', '1', '30000', '0', '0');
INSERT INTO `droplist_map` VALUES ('28', '7000', '640812', '聖誕櫻桃蛋糕塊', '1', '1', '30000', '0', '0');
INSERT INTO `droplist_map` VALUES ('29', '7000', '640814', '聖誕草莓蛋糕塊', '1', '1', '30000', '0', '0');
INSERT INTO `droplist_map` VALUES ('30', '1931', '640769', 'NC紀念幣', '1', '1', '100', '0', '0');
INSERT INTO `droplist_map` VALUES ('31', '7000', '405', '亞瓦隆匕首', '1', '1', '5000', '0', '0');
INSERT INTO `droplist_map` VALUES ('32', '7000', '406', '亞瓦隆長弓', '1', '1', '5000', '0', '0');
INSERT INTO `droplist_map` VALUES ('33', '7000', '407', '亞瓦隆法杖', '1', '1', '5000', '0', '0');
INSERT INTO `droplist_map` VALUES ('34', '7000', '408', '亞瓦隆雙刀', '1', '1', '5000', '0', '0');
INSERT INTO `droplist_map` VALUES ('35', '7000', '409', '亞瓦隆雙手劍', '1', '1', '5000', '0', '0');
INSERT INTO `droplist_map` VALUES ('36', '7000', '410', '亞瓦隆鎖鍊劍', '1', '1', '5000', '0', '0');
INSERT INTO `droplist_map` VALUES ('37', '7000', '411', '亞瓦隆奇古獸', '1', '1', '5000', '0', '0');
INSERT INTO `droplist_map` VALUES ('38', '7000', '412', '亞瓦隆單手斧', '1', '1', '5000', '0', '0');

-- ----------------------------
-- Table structure for `dungeon`
-- ----------------------------
DROP TABLE IF EXISTS `dungeon`;
