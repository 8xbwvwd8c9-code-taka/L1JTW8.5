CREATE TABLE `_config_other` (
  `index` int(10) NOT NULL AUTO_INCREMENT,
  `parameter` varchar(50) NOT NULL,
  `value` varchar(120) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`index`,`parameter`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of _config_other
-- ----------------------------
INSERT INTO `_config_other` VALUES ('1', 'PrinceMaxHP', '14500', '王族最大HP');
INSERT INTO `_config_other` VALUES ('2', 'PrinceMaxMP', '1800', '王族最大MP');
INSERT INTO `_config_other` VALUES ('3', 'KnightMaxHP', '15200', '騎士最大HP');
INSERT INTO `_config_other` VALUES ('4', 'KnightMaxMP', '1200', '騎士最大MP');
INSERT INTO `_config_other` VALUES ('5', 'ElfMaxHP', '13000', '妖精最大HP');
INSERT INTO `_config_other` VALUES ('6', 'ElfMaxMP', '1900', '妖精最大MP');
INSERT INTO `_config_other` VALUES ('7', 'WizardMaxHP', '12000', '法師最大HP');
INSERT INTO `_config_other` VALUES ('8', 'WizardMaxMP', '2200', '法師最大MP');
INSERT INTO `_config_other` VALUES ('9', 'DarkelfMaxHP', '13000', '黑妖最大HP');
INSERT INTO `_config_other` VALUES ('10', 'DarkelfMaxMP', '1900', '黑妖最大MP');
INSERT INTO `_config_other` VALUES ('11', 'DragonKnightMaxHP', '14000', '龍騎士最大HP');
INSERT INTO `_config_other` VALUES ('12', 'DragonKnightMaxMP', '1600', '龍騎士最大MP');
INSERT INTO `_config_other` VALUES ('13', 'IllusionistMaxHP', '12100', '幻術師最大HP');
INSERT INTO `_config_other` VALUES ('14', 'IllusionistMaxMP', '2100', '幻術師最大MP');
INSERT INTO `_config_other` VALUES ('15', 'WarriorMaxHP', '14800', '戰士最大HP');
INSERT INTO `_config_other` VALUES ('16', 'WarriorMaxMP', '1300', '戰士最大MP');
INSERT INTO `_config_other` VALUES ('17', 'LoginTestCheck', 'false', '是否開啟登入器版本驗證');
INSERT INTO `_config_other` VALUES ('18', 'LoginTestVersion', 'v170518', '登入器的版本');

-- ----------------------------
-- Table structure for `_config_world`
-- ----------------------------
DROP TABLE IF EXISTS `_config_world`;
