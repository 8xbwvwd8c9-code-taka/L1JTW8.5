CREATE TABLE `luckydraw` (
  `itemid` int(10) NOT NULL,
  `note` varchar(45) DEFAULT NULL,
  `enchant` int(3) NOT NULL DEFAULT '0',
  `count` int(10) NOT NULL DEFAULT '1',
  `prab_value` int(4) NOT NULL DEFAULT '1',
  `bless_chance` int(3) NOT NULL DEFAULT '0' COMMENT '祝福機率',
  PRIMARY KEY (`itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of luckydraw
-- ----------------------------
INSERT INTO `luckydraw` VALUES ('9', '奧里哈魯根短劍', '9', '1', '11', '100');
INSERT INTO `luckydraw` VALUES ('11', '水晶短劍', '9', '1', '5', '100');
INSERT INTO `luckydraw` VALUES ('84', '暗黑雙刀', '9', '1', '5', '100');
INSERT INTO `luckydraw` VALUES ('119', '惡魔鐮刀', '0', '1', '1', '100');
INSERT INTO `luckydraw` VALUES ('260', '狂風之斧', '9', '1', '11', '100');
INSERT INTO `luckydraw` VALUES ('265', '底比斯歐西里斯雙刀', '9', '1', '5', '100');
INSERT INTO `luckydraw` VALUES ('266', '底比斯歐西里斯雙手劍', '9', '1', '5', '100');
INSERT INTO `luckydraw` VALUES ('267', '底比斯歐西里斯弓', '9', '1', '5', '100');
INSERT INTO `luckydraw` VALUES ('268', '底比斯歐西里斯魔杖', '9', '1', '5', '100');
INSERT INTO `luckydraw` VALUES ('271', '黑曜石奇古獸', '7', '1', '11', '100');
INSERT INTO `luckydraw` VALUES ('273', '破滅者鎖鏈劍', '7', '1', '11', '100');
INSERT INTO `luckydraw` VALUES ('276', '提卡爾庫庫爾坎之矛', '7', '1', '11', '100');
INSERT INTO `luckydraw` VALUES ('277', '提卡爾庫庫爾坎鐵手甲', '0', '1', '11', '100');
INSERT INTO `luckydraw` VALUES ('386', '戰神馬爾斯小刀', '9', '1', '1', '50');
INSERT INTO `luckydraw` VALUES ('387', '戰神馬爾斯神弓', '9', '1', '1', '50');
INSERT INTO `luckydraw` VALUES ('388', '戰神馬爾斯雙手劍', '9', '1', '1', '50');
INSERT INTO `luckydraw` VALUES ('389', '戰神馬爾斯雙刀', '9', '1', '1', '50');
INSERT INTO `luckydraw` VALUES ('390', '戰神馬爾斯法杖', '9', '1', '1', '50');
INSERT INTO `luckydraw` VALUES ('391', '戰神馬爾斯奇古獸', '9', '1', '1', '50');
INSERT INTO `luckydraw` VALUES ('392', '戰神馬爾斯鎖鏈劍', '9', '1', '1', '50');
INSERT INTO `luckydraw` VALUES ('20029', '西瑪之帽', '0', '1', '6', '100');
INSERT INTO `luckydraw` VALUES ('20049', '巨蟻女皇的金翅膀', '7', '1', '4', '100');
INSERT INTO `luckydraw` VALUES ('20050', '巨蟻女皇的銀翅膀', '7', '1', '2', '100');
INSERT INTO `luckydraw` VALUES ('20074', '銀光 斗篷', '0', '1', '17', '100');
INSERT INTO `luckydraw` VALUES ('20166', '死亡騎士手套', '0', '1', '5', '100');
INSERT INTO `luckydraw` VALUES ('20187', '力量手套', '0', '1', '5', '100');
INSERT INTO `luckydraw` VALUES ('20204', '巴列斯長靴', '0', '1', '9', '100');
INSERT INTO `luckydraw` VALUES ('20233', '神官魔法書', '0', '1', '9', '100');
INSERT INTO `luckydraw` VALUES ('20235', '伊娃之盾', '0', '1', '39', '100');
INSERT INTO `luckydraw` VALUES ('20252', '蕾雅項鍊', '0', '1', '17', '100');
INSERT INTO `luckydraw` VALUES ('20280', '滅魔戒指', '0', '1', '45', '100');
INSERT INTO `luckydraw` VALUES ('20284', '召喚控制戒指', '0', '1', '40', '100');
INSERT INTO `luckydraw` VALUES ('20285', '水靈戒指', '0', '1', '45', '100');
INSERT INTO `luckydraw` VALUES ('20300', '地靈戒指', '0', '1', '45', '100');
INSERT INTO `luckydraw` VALUES ('20302', '風靈戒指', '0', '1', '45', '100');
INSERT INTO `luckydraw` VALUES ('20304', '火靈戒指', '0', '1', '45', '100');
INSERT INTO `luckydraw` VALUES ('20317', '歐吉皮帶', '0', '1', '17', '100');
INSERT INTO `luckydraw` VALUES ('20422', '發光的古老項鍊', '0', '1', '5', '100');
INSERT INTO `luckydraw` VALUES ('21105', '古代神射臂甲', '0', '1', '9', '100');
INSERT INTO `luckydraw` VALUES ('21106', '古代鬥士臂甲', '0', '1', '9', '100');
INSERT INTO `luckydraw` VALUES ('21131', '提卡爾庫庫爾坎之盾', '0', '1', '8', '0');
INSERT INTO `luckydraw` VALUES ('21132', '提卡爾庫庫爾坎面具', '0', '1', '8', '0');
INSERT INTO `luckydraw` VALUES ('21133', '提卡爾杰弗雷庫尖牙', '0', '1', '8', '0');
INSERT INTO `luckydraw` VALUES ('21134', '提卡爾杰弗雷庫之眼', '0', '1', '8', '0');
INSERT INTO `luckydraw` VALUES ('21205', '反叛者的盾牌', '0', '1', '1', '0');
INSERT INTO `luckydraw` VALUES ('21363', '奪魂T恤(魔法)', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('21364', '奪魂T恤(近戰)', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('21365', '奪魂T恤(遠攻)', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('21366', '星星力量耳環', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('21367', '星星敏捷耳環', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('21368', '星星智力耳環', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('21369', '月亮力量項鍊', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('21370', '月亮敏捷項鍊', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('21371', '月亮智力項鍊', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('21384', '幻象眼魔的心眼', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('40010', '治癒藥水', '0', '100', '100', '100');
INSERT INTO `luckydraw` VALUES ('40011', '強力治癒藥水', '0', '100', '100', '100');
INSERT INTO `luckydraw` VALUES ('40012', '終極治癒藥水', '0', '100', '100', '100');
INSERT INTO `luckydraw` VALUES ('40013', '自我加速藥水', '0', '100', '100', '100');
INSERT INTO `luckydraw` VALUES ('40014', '勇敢藥水', '0', '100', '100', '100');
INSERT INTO `luckydraw` VALUES ('40033', '萬能藥(力量)', '0', '1', '1', '0');
INSERT INTO `luckydraw` VALUES ('40034', '萬能藥(體質)', '0', '1', '1', '0');
INSERT INTO `luckydraw` VALUES ('40035', '萬能藥(敏捷)', '0', '1', '1', '0');
INSERT INTO `luckydraw` VALUES ('40036', '萬能藥(智慧)', '0', '1', '1', '0');
INSERT INTO `luckydraw` VALUES ('40037', '萬能藥(精神)', '0', '1', '1', '0');
INSERT INTO `luckydraw` VALUES ('40038', '萬能藥(魅力)', '0', '1', '1', '0');
INSERT INTO `luckydraw` VALUES ('40074', '對盔甲施法的卷軸', '0', '5', '40', '100');
INSERT INTO `luckydraw` VALUES ('40076', '古代的卷軸', '0', '1', '11', '0');
INSERT INTO `luckydraw` VALUES ('40087', '對武器施法的卷軸', '0', '3', '40', '100');
INSERT INTO `luckydraw` VALUES ('40279', '封印的傲慢之塔傳送符(11F)', '0', '1', '17', '0');
INSERT INTO `luckydraw` VALUES ('40280', '封印的傲慢之塔傳送符(21F)', '0', '1', '17', '0');
INSERT INTO `luckydraw` VALUES ('40281', '封印的傲慢之塔傳送符(31F)', '0', '1', '15', '0');
INSERT INTO `luckydraw` VALUES ('40282', '封印的傲慢之塔傳送符(41F)', '0', '1', '11', '0');
INSERT INTO `luckydraw` VALUES ('40283', '封印的傲慢之塔傳送符(51F)', '0', '1', '6', '0');
INSERT INTO `luckydraw` VALUES ('40284', '封印的傲慢之塔傳送符(61F)', '0', '1', '6', '0');
INSERT INTO `luckydraw` VALUES ('40285', '封印的傲慢之塔傳送符(71F)', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('40286', '封印的傲慢之塔傳送符(81F)', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('40287', '封印的傲慢之塔傳送符(91F)', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('40677', '黑暗礦石鑄塊', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('40889', '魔法卷軸 (魔法屏障)', '0', '100', '100', '0');
INSERT INTO `luckydraw` VALUES ('40893', '魔法卷軸 (高級治癒術)', '0', '100', '100', '0');
INSERT INTO `luckydraw` VALUES ('41491', '魔法書(神聖疾走)', '0', '1', '15', '0');
INSERT INTO `luckydraw` VALUES ('41518', '魔法書(靈魂昇華)', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('41526', '技術書(衝擊之暈)', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('41527', '技術書(增幅防禦)', '0', '1', '4', '0');
INSERT INTO `luckydraw` VALUES ('41551', '黑暗精靈水晶(破壞盔甲)', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('41571', '精靈水晶(三重矢)', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('41585', '精靈水晶(魂體轉換)', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('41596', '精靈水晶(大地屏障)', '0', '1', '19', '0');
INSERT INTO `luckydraw` VALUES ('41599', '精靈水晶(水之防護)', '0', '1', '23', '0');
INSERT INTO `luckydraw` VALUES ('41603', '精靈水晶(生命的祝福)', '0', '1', '7', '0');
INSERT INTO `luckydraw` VALUES ('41610', '精靈水晶(屬性之火)', '0', '1', '4', '0');
INSERT INTO `luckydraw` VALUES ('41612', '精靈水晶(污濁之水)', '0', '1', '4', '0');
INSERT INTO `luckydraw` VALUES ('41615', '精靈水晶(能量激發)', '0', '1', '3', '0');
INSERT INTO `luckydraw` VALUES ('41626', '龍騎士書板(屠宰者)', '0', '1', '4', '0');
INSERT INTO `luckydraw` VALUES ('41664', '咆哮', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('41665', '體能強化', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('41666', '無名怒火 (未開放)', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('41667', '拘束移動 ', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('41668', '戰斧投擲 ', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('41669', '亡命之徒', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('41681', '粉碎', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('41682', '狂暴', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('41683', '迅猛雙斧', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('41685', '護甲身軀 ', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('41686', '泰坦：岩石 ', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('41687', '泰坦：子彈 ', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('41688', '泰坦：魔法 ', '0', '1', '5', '0');
INSERT INTO `luckydraw` VALUES ('47000', '150%神力藥水', '0', '30', '80', '0');
INSERT INTO `luckydraw` VALUES ('47001', '175%神力藥水', '0', '30', '80', '0');
INSERT INTO `luckydraw` VALUES ('47002', '200%神力藥水', '0', '30', '70', '0');
INSERT INTO `luckydraw` VALUES ('47003', '225%神力藥水', '0', '30', '60', '0');
INSERT INTO `luckydraw` VALUES ('47004', '250%神力藥水', '0', '30', '50', '0');
INSERT INTO `luckydraw` VALUES ('47005', '媽祖祝福平安符', '0', '30', '50', '0');
INSERT INTO `luckydraw` VALUES ('47006', '戰鬥藥水', '0', '30', '50', '0');
INSERT INTO `luckydraw` VALUES ('47007', '體力增強卷軸', '0', '30', '50', '0');
INSERT INTO `luckydraw` VALUES ('47008', '魔力增強卷軸', '0', '30', '50', '0');
INSERT INTO `luckydraw` VALUES ('47009', '強化戰鬥卷軸', '0', '30', '50', '0');

-- ----------------------------
-- Table structure for `magic_doll`
-- ----------------------------
DROP TABLE IF EXISTS `magic_doll`;
