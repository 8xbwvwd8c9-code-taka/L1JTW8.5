CREATE TABLE `fishing` (
  `itemid` int(10) NOT NULL,
  `note` varchar(45) DEFAULT NULL,
  `count` int(10) NOT NULL DEFAULT '1',
  `prab_value` int(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fishing
-- ----------------------------
INSERT INTO `fishing` VALUES ('41252', '珍奇的烏龜', '1', '1');
INSERT INTO `fishing` VALUES ('41296', '鯛魚', '1', '200');
INSERT INTO `fishing` VALUES ('41297', '鮭魚', '1', '200');
INSERT INTO `fishing` VALUES ('41298', '鱈魚', '1', '200');
INSERT INTO `fishing` VALUES ('41299', '虎斑帶魚', '1', '200');
INSERT INTO `fishing` VALUES ('41300', '鮪魚', '1', '200');
INSERT INTO `fishing` VALUES ('41301', '發紅光的魚', '1', '100');
INSERT INTO `fishing` VALUES ('41302', '發綠光的魚', '1', '100');
INSERT INTO `fishing` VALUES ('41303', '發藍光的魚', '1', '100');
INSERT INTO `fishing` VALUES ('41304', '發白光的魚', '1', '100');
INSERT INTO `fishing` VALUES ('41305', '破碎的耳環', '1', '2');
INSERT INTO `fishing` VALUES ('41306', '破碎的戒指', '1', '2');
INSERT INTO `fishing` VALUES ('41307', '破碎的項練', '1', '2');
INSERT INTO `fishing` VALUES ('46001', '河豚', '1', '5');
INSERT INTO `fishing` VALUES ('47104', '閃爍的鱗片', '1', '5');
INSERT INTO `fishing` VALUES ('640283', '小丑魚', '1', '200');
INSERT INTO `fishing` VALUES ('640284', '藍色小丑魚', '1', '100');
INSERT INTO `fishing` VALUES ('640285', '鸚鵡小丑魚', '1', '50');
INSERT INTO `fishing` VALUES ('640286', '黃金箱', '1', '1');
INSERT INTO `fishing` VALUES ('640287', '黃金長靴', '1', '1');
INSERT INTO `fishing` VALUES ('640288', '濕濕的釣魚背包', '1', '50');
INSERT INTO `fishing` VALUES ('640289', '小銀光小丑魚', '1', '100');
INSERT INTO `fishing` VALUES ('640290', '大銀光小丑魚', '1', '100');
INSERT INTO `fishing` VALUES ('640291', '小黃金小丑魚', '1', '100');
INSERT INTO `fishing` VALUES ('640292', '大黃金小丑魚', '1', '100');
INSERT INTO `fishing` VALUES ('640623', '青龍馬石像', '1', '1');
INSERT INTO `fishing` VALUES ('640624', '銀龍馬石像', '1', '1');
INSERT INTO `fishing` VALUES ('640625', '金龍馬石像', '1', '1');

-- ----------------------------
-- Table structure for `getback`
-- ----------------------------
DROP TABLE IF EXISTS `getback`;
