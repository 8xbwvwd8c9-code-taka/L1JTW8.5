CREATE TABLE `pets` (
  `item_obj_id` int(10) unsigned NOT NULL DEFAULT '0',
  `objid` int(10) unsigned NOT NULL DEFAULT '0',
  `npcid` int(10) unsigned NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL DEFAULT '',
  `lvl` int(10) unsigned NOT NULL DEFAULT '0',
  `hp` int(10) unsigned NOT NULL DEFAULT '0',
  `mp` int(10) unsigned NOT NULL DEFAULT '0',
  `exp` int(10) unsigned NOT NULL DEFAULT '0',
  `lawful` int(10) unsigned NOT NULL DEFAULT '0',
  `food` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_obj_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pets
-- ----------------------------
INSERT INTO `pets` VALUES ('271967320', '271967321', '97022', '頑皮龍', '5', '55', '22', '1296', '0', '50');
INSERT INTO `pets` VALUES ('272038816', '272038817', '97022', '頑皮龍', '13', '137', '52', '34296', '0', '0');
INSERT INTO `pets` VALUES ('272040204', '272040205', '97022', '頑皮龍', '24', '260', '101', '369096', '0', '34');
INSERT INTO `pets` VALUES ('272040209', '272040210', '97022', '頑皮龍', '25', '291', '104', '418896', '0', '34');
INSERT INTO `pets` VALUES ('272936548', '272936549', '97023', '淘氣龍', '8', '87', '34', '5696', '0', '42');
INSERT INTO `pets` VALUES ('272936567', '272936568', '97023', '淘氣龍', '8', '94', '33', '4696', '0', '42');
INSERT INTO `pets` VALUES ('275409413', '38834', '45040', '熊', '26', '283', '33', '464050', '0', '44');
INSERT INTO `pets` VALUES ('276408165', '41685', '45053', '大吉', '48', '507', '64', '16666250', '0', '88');
INSERT INTO `pets` VALUES ('276408306', '41690', '45040', '熊', '5', '50', '0', '750', '0', '48');
INSERT INTO `pets` VALUES ('276408319', '41691', '45053', '中吉', '48', '502', '76', '16860150', '0', '88');
INSERT INTO `pets` VALUES ('276408440', '41697', '45053', '哈士奇', '48', '493', '64', '11172850', '0', '60');
INSERT INTO `pets` VALUES ('276408609', '41698', '45053', '小吉', '48', '495', '74', '16290150', '0', '88');
INSERT INTO `pets` VALUES ('276409622', '41723', '45046', '小獵犬', '39', '221', '137', '2532750', '0', '22');
INSERT INTO `pets` VALUES ('276572517', '36583', '45043', '狼', '5', '30', '5', '750', '0', '48');
INSERT INTO `pets` VALUES ('276572982', '36601', '45043', '狼', '8', '52', '10', '5650', '0', '40');
INSERT INTO `pets` VALUES ('276573327', '36608', '45043', '狼', '8', '45', '11', '4350', '0', '44');
INSERT INTO `pets` VALUES ('276574152', '37040', '45043', '謀狼', '51', '327', '75', '53192050', '0', '62');
INSERT INTO `pets` VALUES ('276574157', '37041', '45043', '憨狼', '51', '291', '80', '49429050', '0', '62');
INSERT INTO `pets` VALUES ('276574297', '37042', '45043', '色狼', '51', '294', '70', '53028050', '0', '64');
INSERT INTO `pets` VALUES ('277547205', '36955', '45053', '哈士奇', '21', '232', '27', '201050', '0', '66');
INSERT INTO `pets` VALUES ('277548100', '36961', '45042', '杜賓狗', '27', '117', '40', '551950', '0', '50');
INSERT INTO `pets` VALUES ('277548166', '36962', '45042', '杜賓狗', '27', '118', '39', '566250', '0', '50');
INSERT INTO `pets` VALUES ('276806747', '35017', '97023', '淘氣龍', '5', '86', '25', '1150', '0', '46');

-- ----------------------------
-- Table structure for `pettypes`
-- ----------------------------
DROP TABLE IF EXISTS `pettypes`;
