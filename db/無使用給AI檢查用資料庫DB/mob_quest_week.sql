CREATE TABLE `mob_quest_week` (
  `mob_number` int(4) NOT NULL,
  `note` varchar(45) DEFAULT NULL,
  `count` int(4) NOT NULL DEFAULT '10',
  PRIMARY KEY (`mob_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mob_quest_week
-- ----------------------------
INSERT INTO `mob_quest_week` VALUES ('14', '冰魔', '1');
INSERT INTO `mob_quest_week` VALUES ('18', '冰之女王', '1');
INSERT INTO `mob_quest_week` VALUES ('60', '巨大牛人', '10');
INSERT INTO `mob_quest_week` VALUES ('61', '哈維女皇', '10');
INSERT INTO `mob_quest_week` VALUES ('62', '亞力安王', '10');
INSERT INTO `mob_quest_week` VALUES ('63', '歐吉王', '10');
INSERT INTO `mob_quest_week` VALUES ('64', '巨翼飛龍', '10');
INSERT INTO `mob_quest_week` VALUES ('126', '阿勒尼亞', '10');
INSERT INTO `mob_quest_week` VALUES ('133', '克特', '10');
INSERT INTO `mob_quest_week` VALUES ('134', '紅色妖魔', '10');
INSERT INTO `mob_quest_week` VALUES ('149', '亡者的監督官', '10');
INSERT INTO `mob_quest_week` VALUES ('151', '黑騎士隊長', '10');
INSERT INTO `mob_quest_week` VALUES ('159', '奈克偌斯', '10');
INSERT INTO `mob_quest_week` VALUES ('160', '烏勒庫斯', '10');
INSERT INTO `mob_quest_week` VALUES ('162', '死亡的亡靈', '10');
INSERT INTO `mob_quest_week` VALUES ('163', '渾沌的亡靈', '10');
INSERT INTO `mob_quest_week` VALUES ('177', '巨蟻女皇', '1');
INSERT INTO `mob_quest_week` VALUES ('178', '沙蟲', '1');
INSERT INTO `mob_quest_week` VALUES ('189', '強盜頭目', '10');
INSERT INTO `mob_quest_week` VALUES ('209', '巨大鱷魚', '10');
INSERT INTO `mob_quest_week` VALUES ('210', '變形怪首領', '10');
INSERT INTO `mob_quest_week` VALUES ('222', '飛龍', '10');
INSERT INTO `mob_quest_week` VALUES ('223', '大黑長者', '10');
INSERT INTO `mob_quest_week` VALUES ('224', '巨大飛龍', '10');
INSERT INTO `mob_quest_week` VALUES ('225', '傑羅斯', '1');
INSERT INTO `mob_quest_week` VALUES ('235', '火龍的守護者', '10');
INSERT INTO `mob_quest_week` VALUES ('236', '火焰的支配者', '10');
INSERT INTO `mob_quest_week` VALUES ('237', '不死鳥', '10');
INSERT INTO `mob_quest_week` VALUES ('254', '狂風的夏斯奇', '10');
INSERT INTO `mob_quest_week` VALUES ('255', '狂風的夏斯奇', '10');
INSERT INTO `mob_quest_week` VALUES ('263', '大腳的瑪幽', '10');
INSERT INTO `mob_quest_week` VALUES ('267', '卡爾克', '10');
INSERT INTO `mob_quest_week` VALUES ('271', '泰坦 米洛斯', '10');
INSERT INTO `mob_quest_week` VALUES ('281', '瑪依奴夏門', '10');
INSERT INTO `mob_quest_week` VALUES ('291', '巴風特', '10');
INSERT INTO `mob_quest_week` VALUES ('294', '西瑪', '10');
INSERT INTO `mob_quest_week` VALUES ('295', '馬庫爾', '10');
INSERT INTO `mob_quest_week` VALUES ('296', '巴土瑟', '10');
INSERT INTO `mob_quest_week` VALUES ('297', '卡士柏', '10');
INSERT INTO `mob_quest_week` VALUES ('304', '死亡騎士', '10');
INSERT INTO `mob_quest_week` VALUES ('309', '魔法師', '10');
INSERT INTO `mob_quest_week` VALUES ('328', '翼魔', '10');
INSERT INTO `mob_quest_week` VALUES ('331', '奇美拉爾德', '10');
INSERT INTO `mob_quest_week` VALUES ('348', '炎魔', '10');
INSERT INTO `mob_quest_week` VALUES ('355', '浮士德', '10');
INSERT INTO `mob_quest_week` VALUES ('360', '塔洛斯伯爵', '10');
INSERT INTO `mob_quest_week` VALUES ('388', '水靈的守護者', '10');
INSERT INTO `mob_quest_week` VALUES ('389', '卡普', '10');
INSERT INTO `mob_quest_week` VALUES ('390', '巨大蜈蚣', '10');
INSERT INTO `mob_quest_week` VALUES ('398', '地靈的守護者', '10');
INSERT INTO `mob_quest_week` VALUES ('408', '亞克魔', '10');
INSERT INTO `mob_quest_week` VALUES ('425', '黑魔法師', '10');
INSERT INTO `mob_quest_week` VALUES ('431', '哈汀之影', '10');
INSERT INTO `mob_quest_week` VALUES ('432', '惡魔', '10');
INSERT INTO `mob_quest_week` VALUES ('464', '歪曲的潔尼斯女王', '10');
INSERT INTO `mob_quest_week` VALUES ('471', '不幸的幻象眼魔', '10');
INSERT INTO `mob_quest_week` VALUES ('478', '恐怖的吸血鬼', '10');
INSERT INTO `mob_quest_week` VALUES ('484', '死亡的殭屍王', '10');
INSERT INTO `mob_quest_week` VALUES ('491', '地獄的黑豹', '10');
INSERT INTO `mob_quest_week` VALUES ('496', '不死的木乃伊王', '10');
INSERT INTO `mob_quest_week` VALUES ('501', '殘忍的艾莉絲', '10');
INSERT INTO `mob_quest_week` VALUES ('506', '黑暗的騎士范德', '10');
INSERT INTO `mob_quest_week` VALUES ('511', '不滅的巫妖', '10');
INSERT INTO `mob_quest_week` VALUES ('516', '傲慢的烏格奴斯', '10');
INSERT INTO `mob_quest_week` VALUES ('527', '邪惡的鐮刀死神', '10');
INSERT INTO `mob_quest_week` VALUES ('549', '闇黑君王', '10');

-- ----------------------------
-- Table structure for `npc`
-- ----------------------------
DROP TABLE IF EXISTS `npc`;
