CREATE TABLE `character_mobs_week` (
  `login` varchar(15) NOT NULL,
  `numbers` varchar(60) NOT NULL,
  `counts` varchar(60) NOT NULL,
  `kills` varchar(60) NOT NULL,
  `states` varchar(60) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_mobs_week
-- ----------------------------

-- ----------------------------
-- Table structure for `character_quests`
-- ----------------------------
DROP TABLE IF EXISTS `character_quests`;
