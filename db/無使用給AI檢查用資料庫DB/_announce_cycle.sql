CREATE TABLE `_announce_cycle` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of _announce_cycle
-- ----------------------------
INSERT INTO `_announce_cycle` VALUES ('1', '貼心小提醒: 火神鍊化工匠有新製作道具囉');
INSERT INTO `_announce_cycle` VALUES ('2', '貼心小提醒: 遊戲時間30分鐘可獲得商城點數2~4點');
INSERT INTO `_announce_cycle` VALUES ('3', '貼心小提醒: 部分道具可在奇岩煉化成製作材料');
INSERT INTO `_announce_cycle` VALUES ('4', '貼心小提醒: 魔法書/水晶學習技能現在只要點兩下就可以了');
INSERT INTO `_announce_cycle` VALUES ('5', '貼心小提醒: 商城內的潘朵拉武器包/防具包新手時期可以多加利用');

-- ----------------------------
-- Table structure for `_announce_login`
-- ----------------------------
DROP TABLE IF EXISTS `_announce_login`;
