CREATE TABLE `beginner` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `item_id` int(6) NOT NULL DEFAULT '0',
  `count` int(10) NOT NULL DEFAULT '0',
  `charge_count` int(10) NOT NULL DEFAULT '0',
  `enchantlvl` int(6) NOT NULL DEFAULT '0',
  `item_name` varchar(50) NOT NULL DEFAULT '',
  `activate` varchar(45) NOT NULL DEFAULT 'A',
  `bless` int(3) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of beginner
-- ----------------------------
INSERT INTO `beginner` VALUES ('1', '40005', '1', '0', '0', '蠟燭', 'A', '1');
INSERT INTO `beginner` VALUES ('2', '640330', '1000', '0', '0', '修練者的箭', 'E', '1');
INSERT INTO `beginner` VALUES ('3', '640740', '1', '0', '0', '特勞雷迪移動符', 'A', '1');
INSERT INTO `beginner` VALUES ('4', '40029', '100', '0', '0', '修練者治癒藥水', 'A', '1');
INSERT INTO `beginner` VALUES ('5', '640728', '10', '0', '0', '修練者強化加速藥水', 'A', '1');
INSERT INTO `beginner` VALUES ('6', '640729', '10', '0', '0', '修練者的勇敢藥水', 'KO', '1');
INSERT INTO `beginner` VALUES ('7', '640730', '10', '0', '0', '修練者的精靈餅乾', 'E', '1');
INSERT INTO `beginner` VALUES ('8', '640731', '10', '0', '0', '修練者的生命之樹果實', 'RI', '1');
INSERT INTO `beginner` VALUES ('9', '640732', '10', '0', '0', '修練者的惡魔之血', 'P', '1');
INSERT INTO `beginner` VALUES ('10', '640733', '10', '0', '0', '修練者的慎重藥水', 'W', '1');
INSERT INTO `beginner` VALUES ('11', '640739', '3', '0', '0', '修練者英雄變身卷軸', 'A', '1');
INSERT INTO `beginner` VALUES ('12', '21179', '1', '0', '4', '屍魂頭盔', 'A', '1');
INSERT INTO `beginner` VALUES ('13', '21180', '1', '0', '4', '屍魂手套', 'A', '1');
INSERT INTO `beginner` VALUES ('14', '21181', '1', '0', '4', '屍魂靴子', 'A', '1');
INSERT INTO `beginner` VALUES ('15', '21182', '1', '0', '4', '屍魂盔甲', 'A', '1');
INSERT INTO `beginner` VALUES ('16', '21183', '1', '0', '4', '屍魂披風', 'A', '1');
INSERT INTO `beginner` VALUES ('17', '124', '1', '0', '3', '巴風特魔杖', 'W', '1');
INSERT INTO `beginner` VALUES ('18', '326', '1', '0', '7', '弒神者之弓', 'E', '1');
INSERT INTO `beginner` VALUES ('19', '262', '1', '0', '7', '毀滅巨劍', 'K', '1');
INSERT INTO `beginner` VALUES ('20', '264', '1', '0', '7', '雷雨之劍', 'P', '1');
INSERT INTO `beginner` VALUES ('21', '329', '1', '0', '7', '破壞雙刀', 'D', '1');
INSERT INTO `beginner` VALUES ('22', '332', '1', '0', '7', '共鳴奇古獸', 'I', '1');
INSERT INTO `beginner` VALUES ('23', '334', '1', '0', '7', '共鳴鎖鏈劍', 'R', '1');
INSERT INTO `beginner` VALUES ('24', '346', '1', '0', '7', '疾風斧頭', 'O', '1');

-- ----------------------------
-- Table structure for `board`
-- ----------------------------
DROP TABLE IF EXISTS `board`;
