CREATE TABLE `trap` (
  `id` int(8) NOT NULL,
  `note` varchar(64) DEFAULT NULL,
  `type` varchar(64) NOT NULL,
  `gfxId` int(4) NOT NULL,
  `isDetectionable` tinyint(1) NOT NULL,
  `base` int(4) NOT NULL,
  `dice` int(4) NOT NULL,
  `diceCount` int(4) NOT NULL,
  `poisonType` char(1) NOT NULL DEFAULT 'n',
  `poisonDelay` int(4) NOT NULL DEFAULT '0',
  `poisonTime` int(4) NOT NULL DEFAULT '0',
  `poisonDamage` int(4) NOT NULL DEFAULT '0',
  `monsterNpcId` int(4) NOT NULL DEFAULT '0',
  `monsterCount` int(4) NOT NULL DEFAULT '0',
  `teleportX` int(4) NOT NULL DEFAULT '0',
  `teleportY` int(4) NOT NULL DEFAULT '0',
  `teleportMapId` int(4) NOT NULL DEFAULT '0',
  `skillId` int(4) NOT NULL DEFAULT '0',
  `skillTimeSeconds` int(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of trap
-- ----------------------------
INSERT INTO `trap` VALUES ('1', 'トラバサミ', 'L1DamageTrap', '1065', '1', '10', '10', '1', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('2', 'ヒール', 'L1HealingTrap', '1074', '0', '10', '10', '1', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('3', 'ダメージ毒', 'L1PoisonTrap', '1066', '1', '0', '0', '0', 'd', '0', '5000', '10', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('4', '沈黙毒', 'L1PoisonTrap', '1066', '1', '0', '0', '0', 's', '0', '0', '10', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('5', '麻痺毒', 'L1PoisonTrap', '1066', '1', '0', '0', '0', 'p', '5000', '5000', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('6', 'メテオ', 'L1DamageTrap', '1085', '1', '10', '10', '1', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('7', '針(横)', 'L1DamageTrap', '1070', '1', '10', '10', '1', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('8', 'TOI4Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45348', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('9', 'TOI8Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45407', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('10', 'TOI14Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45394', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('11', 'TOI18Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45406', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('12', 'TOI24Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45384', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('13', 'TOI28Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45471', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('14', 'TOI34Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45403', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('15', 'TOI38Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45455', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('16', 'TOI44Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45514', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('17', 'TOI48Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45522', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('18', 'TOI54Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45524', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('19', 'TOI64Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45528', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('20', 'TOI74Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45503', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('21', 'TOI78Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45532', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('22', 'TOI84Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45586', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('23', 'TOI94Fモンスター', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45621', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('24', '象牙塔 4樓-閃電球', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190824', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('25', '象牙塔 4樓-活鎧甲(劍)', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190821', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('26', '象牙塔 4樓-死亡之劍', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190826', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('27', '象牙塔 5樓-象牙塔奇美拉', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190829', '3', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('28', '象牙塔 5樓-象牙塔蛇女', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190830', '3', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('29', '象牙塔 5樓-密密', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190820', '1', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('30', '象牙塔 6樓-影魔', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190834', '3', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('31', '象牙塔 6樓-象牙塔鬼魂', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190835', '3', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('32', '象牙塔 7樓-炎魔的奴僕', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190840', '3', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('33', '象牙塔 7樓-象牙塔小惡魔', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190841', '3', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('34', '象牙塔 8樓-炎魔之影', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190846', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('35', '象牙塔 8樓-惡魔之影', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190845', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('36', '象牙塔 8樓-巴風特之影', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190844', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('37', '象牙塔 8樓-巴列斯之影', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '190838', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('38', 'フランコ迷路ラバーボーン ソルジャー', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '46057', '4', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('39', 'フランコ迷路ラバーボーン  アーチャー', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '46058', '4', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('40', 'フランコ迷路ラバーボーン  ヘッド', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '46059', '4', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('41', 'フランコ迷路ラバーボーン  ナイフ', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '46056', '4', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('42', 'フランコ迷路スタート地点', 'L1TeleportTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '4', '32679', '32742', '482', '0', '0');
INSERT INTO `trap` VALUES ('43', 'ディエゴの閉ざされた牢スタート地点', 'L1TeleportTrap', '0', '0', '0', '0', '0', 'n', '0', '0', '0', '0', '0', '32736', '32800', '483', '0', '0');
INSERT INTO `trap` VALUES ('44', 'お化け屋敷パラライズ', 'L1SkillTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '33', '0');
INSERT INTO `trap` VALUES ('45', 'お化け屋敷ブラインド', 'L1SkillTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '20', '5');
INSERT INTO `trap` VALUES ('46', 'お化け屋敷スロー', 'L1SkillTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '29', '5');
INSERT INTO `trap` VALUES ('47', 'お化け屋敷ヘイスト', 'L1SkillTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '43', '5');
INSERT INTO `trap` VALUES ('48', 'お化け屋敷テレポート', 'L1TeleportTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '32730', '32829', '5140', '0', '0');
INSERT INTO `trap` VALUES ('49', 'お化け屋敷テレポート', 'L1TeleportTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '32749', '32813', '5140', '0', '0');
INSERT INTO `trap` VALUES ('50', 'お化け屋敷テレポート', 'L1TeleportTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '32747', '32867', '5140', '0', '0');
INSERT INTO `trap` VALUES ('51', 'お化け屋敷テレポート', 'L1TeleportTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '32785', '32819', '5140', '0', '0');
INSERT INTO `trap` VALUES ('52', 'お化け屋敷テレポート', 'L1TeleportTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '32797', '32869', '5140', '0', '0');
INSERT INTO `trap` VALUES ('53', '混沌之塔怪物陷阱', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '97414', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('54', '海音傳送陷阱(霧月島)', 'L1TeleportTrap', '12876', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '33516', '33190', '4', '0', '0');
INSERT INTO `trap` VALUES ('55', '海音傳送陷阱(隨機)', 'L1TeleportTrap', '12876', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `treasure_box`
-- ----------------------------
DROP TABLE IF EXISTS `treasure_box`;
