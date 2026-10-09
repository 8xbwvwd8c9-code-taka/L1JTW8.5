CREATE TABLE `gift` (
  `index` int(5) NOT NULL,
  `itemid` int(10) NOT NULL,
  `note` varchar(255) DEFAULT '',
  `count` int(10) NOT NULL,
  `enchant` int(5) NOT NULL DEFAULT '0',
  `activate` varchar(45) NOT NULL DEFAULT 'A',
  PRIMARY KEY (`index`,`itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gift
-- ----------------------------
INSERT INTO `gift` VALUES ('3', '640380', 'Lv0: 救援證書箱', '1', '0', 'A');
INSERT INTO `gift` VALUES ('17', '40056', 'Lv1:奇怪的肉', '15', '0', 'A');
INSERT INTO `gift` VALUES ('39', '40117', 'Lv10:銀騎士村莊指定傳送卷軸(1) ', '1', '0', 'A');
INSERT INTO `gift` VALUES ('39', '41569', 'Lv10:妖精：精靈水晶(心靈轉換)', '1', '0', 'E');
INSERT INTO `gift` VALUES ('39', '41570', 'Lv10:妖精：精靈水晶(世界樹的呼喚)', '1', '0', 'E');
INSERT INTO `gift` VALUES ('39', '41640', 'Lv10:幻術士：記憶水晶(鏡像)', '1', '0', 'I');
INSERT INTO `gift` VALUES ('39', '41641', 'Lv10:幻術士：記憶水晶(混亂)', '1', '0', 'I');
INSERT INTO `gift` VALUES ('39', '41644', 'Lv10:幻術士：記憶水晶(立方：燃燒)', '1', '0', 'I');
INSERT INTO `gift` VALUES ('39', '49311', 'Lv10:修練者的對武器施法的卷軸(1)', '1', '0', 'A');
INSERT INTO `gift` VALUES ('39', '49312', 'Lv10:修練者的對盔甲施法的卷軸(4)', '4', '0', 'A');
INSERT INTO `gift` VALUES ('40', '321', 'Lv15:戰士：修練者的老舊斧頭', '1', '0', 'O');
INSERT INTO `gift` VALUES ('40', '21196', 'Lv15:修練者的腰帶', '1', '0', 'A');
INSERT INTO `gift` VALUES ('40', '40014', 'Lv15:騎士：修練者的勇敢藥水(10)', '10', '0', 'KO');
INSERT INTO `gift` VALUES ('40', '40016', 'Lv15:法師：修練者的慎重藥水(10)', '10', '0', 'W');
INSERT INTO `gift` VALUES ('40', '40031', 'Lv15:王族：修練者的惡魔之血(10)', '10', '0', 'P');
INSERT INTO `gift` VALUES ('40', '40068', 'Lv15:妖精：修練者的精靈餅乾(10)', '10', '0', 'E');
INSERT INTO `gift` VALUES ('40', '40321', 'Lv15:黑暗妖精：修練者的黑魔石(10) ', '10', '0', 'D');
INSERT INTO `gift` VALUES ('40', '41539', 'Lv15:黑暗妖精：黑暗精靈水晶(提煉魔石)', '1', '0', 'D');
INSERT INTO `gift` VALUES ('40', '41552', 'Lv15:王族：魔法書(精準目標)', '1', '0', 'P');
INSERT INTO `gift` VALUES ('40', '41620', 'Lv15:龍騎士：龍騎士書板(龍之護鎧)', '1', '0', 'R');
INSERT INTO `gift` VALUES ('40', '41621', 'Lv15:龍騎士：龍騎士書板(燃燒擊砍)', '1', '0', 'R');
INSERT INTO `gift` VALUES ('40', '41683', 'Lv15:戰士：戰士印記(迅猛雙斧)', '1', '0', 'O');
INSERT INTO `gift` VALUES ('40', '49157', 'Lv15:龍騎士：修練者的刻印的骨頭碎片(10)', '10', '0', 'R');
INSERT INTO `gift` VALUES ('40', '49158', 'Lv15:幻術士：修練者的生命之樹果實(10) ', '10', '0', 'I');
INSERT INTO `gift` VALUES ('40', '49311', 'Lv15:修練者的對武器施法的卷軸(1)', '1', '0', 'A');
INSERT INTO `gift` VALUES ('40', '49312', 'Lv15:修練者的對盔甲施法的卷軸(4)', '4', '0', 'A');
INSERT INTO `gift` VALUES ('41', '20282', 'Lv20:修練者的戒指', '1', '0', 'A');
INSERT INTO `gift` VALUES ('41', '40014', 'Lv20:騎士：修練者的勇敢藥水(10)', '10', '0', 'KO');
INSERT INTO `gift` VALUES ('41', '40016', 'Lv20:法師：修練者的慎重藥水(10)', '10', '0', 'W');
INSERT INTO `gift` VALUES ('41', '40031', 'Lv20:王族：修練者的惡魔之血(10)', '10', '0', 'P');
INSERT INTO `gift` VALUES ('41', '40068', 'Lv20:妖精：修練者的精靈餅乾(10)', '10', '0', 'E');
INSERT INTO `gift` VALUES ('41', '40321', 'Lv20:黑暗妖精：修練者的黑魔石(10) ', '10', '0', 'D');
INSERT INTO `gift` VALUES ('41', '41464', 'Lv20:法師：魔法書(燃燒的火球)', '1', '0', 'W');
INSERT INTO `gift` VALUES ('41', '49157', 'Lv20:龍騎士：修練者的刻印的骨頭碎片(10)', '10', '0', 'R');
INSERT INTO `gift` VALUES ('41', '49158', 'Lv20:幻術士：修練者的生命之樹果實(10) ', '10', '0', 'I');
INSERT INTO `gift` VALUES ('41', '49311', 'Lv20:修練者的對武器施法的卷軸(1)', '1', '0', 'A');
INSERT INTO `gift` VALUES ('41', '49312', 'Lv20:修練者的對盔甲施法的卷軸(4)', '4', '0', 'A');
INSERT INTO `gift` VALUES ('42', '20282', 'Lv25:修練者的戒指', '1', '0', 'A');
INSERT INTO `gift` VALUES ('42', '40014', 'Lv25:騎士：修練者的勇敢藥水(10)', '10', '0', 'KO');
INSERT INTO `gift` VALUES ('42', '40016', 'Lv25:法師：修練者的慎重藥水(10)', '10', '0', 'W');
INSERT INTO `gift` VALUES ('42', '40031', 'Lv25:王族：修練者的惡魔之血(10)', '10', '0', 'P');
INSERT INTO `gift` VALUES ('42', '40068', 'Lv25:妖精：修練者的精靈餅乾(10)', '10', '0', 'E');
INSERT INTO `gift` VALUES ('42', '40321', 'Lv25:黑暗妖精：修練者的黑魔石(10) ', '10', '0', 'D');
INSERT INTO `gift` VALUES ('42', '41482', 'Lv25:法師：魔法書(加速術)', '1', '0', 'W');
INSERT INTO `gift` VALUES ('42', '49157', 'Lv25:龍騎士：修練者的刻印的骨頭碎片(10)', '10', '0', 'R');
INSERT INTO `gift` VALUES ('42', '49158', 'Lv25:幻術士：修練者的生命之樹果實(10) ', '10', '0', 'I');
INSERT INTO `gift` VALUES ('42', '49311', 'Lv25:修練者的對武器施法的卷軸(1)', '1', '0', 'A');
INSERT INTO `gift` VALUES ('42', '49312', 'Lv25:修練者的對盔甲施法的卷軸(4)', '4', '0', 'A');
INSERT INTO `gift` VALUES ('43', '40014', 'Lv30:騎士：修練者的勇敢藥水(10)', '10', '0', 'KO');
INSERT INTO `gift` VALUES ('43', '40016', 'Lv30:法師：修練者的慎重藥水(10)', '10', '0', 'W');
INSERT INTO `gift` VALUES ('43', '40031', 'Lv30:王族：修練者的惡魔之血(10)', '10', '0', 'P');
INSERT INTO `gift` VALUES ('43', '40068', 'Lv30:妖精：修練者的精靈餅乾(10)', '10', '0', 'E');
INSERT INTO `gift` VALUES ('43', '40321', 'Lv30:黑暗妖精：修練者的黑魔石(10) ', '10', '0', 'D');
INSERT INTO `gift` VALUES ('43', '41541', 'Lv30:黑暗妖精：黑暗精靈水晶(行走加速)', '1', '0', 'D');
INSERT INTO `gift` VALUES ('43', '41554', 'Lv30:王族：魔法書(呼喚盟友) ', '1', '0', 'P');
INSERT INTO `gift` VALUES ('43', '41625', 'Lv30:龍騎士：龍騎士書板(血之渴望)', '1', '0', 'R');
INSERT INTO `gift` VALUES ('43', '41654', 'Lv30:幻術士：記憶水晶(立方：衝擊)', '1', '0', 'I');
INSERT INTO `gift` VALUES ('43', '41664', 'Lv30:戰士：戰士印記(咆哮)', '1', '0', 'O');
INSERT INTO `gift` VALUES ('43', '49157', 'Lv30:龍騎士：修練者的刻印的骨頭碎片(10)', '10', '0', 'R');
INSERT INTO `gift` VALUES ('43', '49158', 'Lv30:幻術士：修練者的生命之樹果實(10) ', '10', '0', 'I');
INSERT INTO `gift` VALUES ('43', '49311', 'Lv30:修練者的對武器施法的卷軸(1)', '1', '0', 'A');
INSERT INTO `gift` VALUES ('43', '49312', 'Lv30:修練者的對盔甲施法的卷軸(4)', '4', '0', 'A');
INSERT INTO `gift` VALUES ('44', '21197', 'Lv35:修練者的項鍊', '1', '0', 'A');
INSERT INTO `gift` VALUES ('44', '40014', 'Lv35:騎士：修練者的勇敢藥水(10)', '10', '0', 'KO');
INSERT INTO `gift` VALUES ('44', '40016', 'Lv35:法師：修練者的慎重藥水(10)', '10', '0', 'W');
INSERT INTO `gift` VALUES ('44', '40031', 'Lv35:王族：修練者的惡魔之血(10)', '10', '0', 'P');
INSERT INTO `gift` VALUES ('44', '40068', 'Lv35:妖精：修練者的精靈餅乾(10)', '10', '0', 'E');
INSERT INTO `gift` VALUES ('44', '40321', 'Lv35:黑暗妖精：修練者的黑魔石(10) ', '10', '0', 'D');
INSERT INTO `gift` VALUES ('44', '49157', 'Lv35:龍騎士：修練者的刻印的骨頭碎片(10)', '10', '0', 'R');
INSERT INTO `gift` VALUES ('44', '49158', 'Lv35:幻術士：修練者的生命之樹果實(10) ', '10', '0', 'I');
INSERT INTO `gift` VALUES ('44', '49311', 'Lv35:修練者的對武器施法的卷軸(1)', '1', '0', 'A');
INSERT INTO `gift` VALUES ('44', '49312', 'Lv35:修練者的對盔甲施法的卷軸(4)', '4', '0', 'A');
INSERT INTO `gift` VALUES ('45', '40014', 'Lv40:騎士：修練者的勇敢藥水(10)', '10', '0', 'KO');
INSERT INTO `gift` VALUES ('45', '40016', 'Lv40:法師：修練者的慎重藥水(10)', '10', '0', 'W');
INSERT INTO `gift` VALUES ('45', '40031', 'Lv40:王族：修練者的惡魔之血(10)', '10', '0', 'P');
INSERT INTO `gift` VALUES ('45', '40068', 'Lv40:妖精：修練者的精靈餅乾(10)', '10', '0', 'E');
INSERT INTO `gift` VALUES ('45', '40321', 'Lv40:黑暗妖精：修練者的黑魔石(10) ', '10', '0', 'D');
INSERT INTO `gift` VALUES ('45', '41593', 'Lv40:妖精：精靈水晶(召喚屬性精靈)', '1', '0', 'E');
INSERT INTO `gift` VALUES ('45', '47009', 'Lv40:強化戰鬥卷軸(2)', '2', '0', 'A');
INSERT INTO `gift` VALUES ('45', '49157', 'Lv40:龍騎士：修練者的刻印的骨頭碎片(10)', '10', '0', 'R');
INSERT INTO `gift` VALUES ('45', '49158', 'Lv40:幻術士：修練者的生命之樹果實(10) ', '10', '0', 'I');
INSERT INTO `gift` VALUES ('46', '21139', 'Lv45:修練者的耳環', '1', '0', 'A');
INSERT INTO `gift` VALUES ('46', '40014', 'Lv45:騎士：修練者的勇敢藥水(10)', '10', '0', 'KO');
INSERT INTO `gift` VALUES ('46', '40016', 'Lv45:法師：修練者的慎重藥水(10)', '10', '0', 'W');
INSERT INTO `gift` VALUES ('46', '40031', 'Lv45:王族：修練者的惡魔之血(10)', '10', '0', 'P');
INSERT INTO `gift` VALUES ('46', '40068', 'Lv45:妖精：修練者的精靈餅乾(10)', '10', '0', 'E');
INSERT INTO `gift` VALUES ('46', '40321', 'Lv45:黑暗妖精：修練者的黑魔石(10) ', '10', '0', 'D');
INSERT INTO `gift` VALUES ('46', '41547', 'Lv45:黑暗妖精：黑暗精靈水晶(暗影閃避)', '1', '0', 'D');
INSERT INTO `gift` VALUES ('46', '41681', 'Lv45:戰士：戰士印記(粉碎)', '1', '0', 'O');
INSERT INTO `gift` VALUES ('46', '49157', 'Lv45:龍騎士：修練者的刻印的骨頭碎片(10)', '10', '0', 'R');
INSERT INTO `gift` VALUES ('46', '49158', 'Lv45:幻術士：修練者的生命之樹果實(10) ', '10', '0', 'I');
INSERT INTO `gift` VALUES ('47', '40014', 'Lv50:騎士：修練者的勇敢藥水(10)', '10', '0', 'KO');
INSERT INTO `gift` VALUES ('47', '40016', 'Lv50:法師：修練者的慎重藥水(10)', '10', '0', 'W');
INSERT INTO `gift` VALUES ('47', '40031', 'Lv50:王族：修練者的惡魔之血(10)', '10', '0', 'P');
INSERT INTO `gift` VALUES ('47', '40068', 'Lv50:妖精：修練者的精靈餅乾(10)', '10', '0', 'E');
INSERT INTO `gift` VALUES ('47', '40321', 'Lv50:黑暗妖精：修練者的黑魔石(10) ', '10', '0', 'D');
INSERT INTO `gift` VALUES ('47', '49157', 'Lv50:龍騎士：修練者的刻印的骨頭碎片(10)', '10', '0', 'R');
INSERT INTO `gift` VALUES ('47', '49158', 'Lv50:幻術士：修練者的生命之樹果實(10) ', '10', '0', 'I');
INSERT INTO `gift` VALUES ('47', '640105', 'Lv50:魔法硬幣', '2', '0', 'A');
INSERT INTO `gift` VALUES ('48', '13', 'Lv52:黑暗妖精：死亡之指', '1', '0', 'D');
INSERT INTO `gift` VALUES ('48', '50', 'Lv52:妖精：赤焰之劍', '1', '0', 'E');
INSERT INTO `gift` VALUES ('48', '51', 'Lv52:王族：黃金權杖', '1', '0', 'P');
INSERT INTO `gift` VALUES ('48', '56', 'Lv52:騎士：黑燄之劍', '1', '0', 'K');
INSERT INTO `gift` VALUES ('48', '184', 'Lv52:妖精：赤焰之弓', '1', '0', 'E');
INSERT INTO `gift` VALUES ('48', '270', 'Lv52:幻術士：藍寶石奇古獸', '1', '0', 'I');
INSERT INTO `gift` VALUES ('48', '272', 'Lv52:龍騎士：消滅者鎖鏈劍', '1', '0', 'R');
INSERT INTO `gift` VALUES ('48', '323', 'Lv52:戰士：大匠斧頭', '1', '0', 'O');
INSERT INTO `gift` VALUES ('48', '20051', 'Lv52:王族：君主的威嚴', '1', '0', 'P');
INSERT INTO `gift` VALUES ('48', '20055', 'Lv52:法師：瑪那斗篷', '1', '0', 'W');
INSERT INTO `gift` VALUES ('48', '20195', 'Lv52:黑暗妖精：影子長靴', '1', '0', 'D');
INSERT INTO `gift` VALUES ('48', '20225', 'Lv52:法師：瑪那水晶球', '1', '0', 'W');
INSERT INTO `gift` VALUES ('48', '20318', 'Lv52:騎士：勇敢皮帶', '1', '0', 'K');
INSERT INTO `gift` VALUES ('48', '21101', 'Lv52:幻術士：幻術士法書', '1', '0', 'I');
INSERT INTO `gift` VALUES ('48', '21103', 'Lv52:龍騎士：龍鱗臂甲', '1', '0', 'R');
INSERT INTO `gift` VALUES ('48', '21198', 'Lv52:戰士：軍團頭盔', '1', '0', 'O');
INSERT INTO `gift` VALUES ('48', '40032', 'Lv52:伊娃的祝福(5)', '5', '0', 'A');
INSERT INTO `gift` VALUES ('48', '140100', 'Lv52:受祝福的 瞬間移動卷軸(10)', '10', '0', 'A');
INSERT INTO `gift` VALUES ('48', '640326', 'Lv52:亞丁的變形卷軸(10)', '10', '0', 'A');
INSERT INTO `gift` VALUES ('49', '640336', 'Lv53:成長藥水(精靈) ', '1', '0', 'A');
INSERT INTO `gift` VALUES ('50', '640336', 'Lv54:成長藥水(精靈) ', '1', '0', 'A');
INSERT INTO `gift` VALUES ('51', '40014', 'Lv55:騎士：修練者的勇敢藥水(10)', '10', '0', 'KO');
INSERT INTO `gift` VALUES ('51', '40016', 'Lv55:法師：修練者的慎重藥水(10)', '10', '0', 'W');
INSERT INTO `gift` VALUES ('51', '40031', 'Lv55:王族：修練者的惡魔之血(10)', '10', '0', 'P');
INSERT INTO `gift` VALUES ('51', '40068', 'Lv55:妖精：修練者的精靈餅乾(10)', '10', '0', 'E');
INSERT INTO `gift` VALUES ('51', '40321', 'Lv55:黑暗妖精：修練者的黑魔石(10) ', '10', '0', 'D');
INSERT INTO `gift` VALUES ('51', '49157', 'Lv55:龍騎士：修練者的刻印的骨頭碎片(10)', '10', '0', 'R');
INSERT INTO `gift` VALUES ('51', '49158', 'Lv55:幻術士：修練者的生命之樹果實(10) ', '10', '0', 'I');
INSERT INTO `gift` VALUES ('51', '640336', 'Lv55:成長藥水(精靈) ', '1', '0', 'A');
INSERT INTO `gift` VALUES ('52', '640336', 'Lv56:成長藥水(精靈) ', '1', '0', 'A');
INSERT INTO `gift` VALUES ('53', '640336', 'Lv57:成長藥水(精靈) ', '1', '0', 'A');
INSERT INTO `gift` VALUES ('54', '640336', 'Lv58:成長藥水(精靈) ', '1', '0', 'A');
INSERT INTO `gift` VALUES ('55', '640336', 'Lv59:成長藥水(精靈) ', '1', '0', 'A');
INSERT INTO `gift` VALUES ('56', '40014', 'Lv60:騎士：修練者的勇敢藥水(10)', '10', '0', 'KO');
INSERT INTO `gift` VALUES ('56', '40016', 'Lv60:法師：修練者的慎重藥水(10)', '10', '0', 'W');
INSERT INTO `gift` VALUES ('56', '40031', 'Lv60:王族：修練者的惡魔之血(10)', '10', '0', 'P');
INSERT INTO `gift` VALUES ('56', '40068', 'Lv60:妖精：修練者的精靈餅乾(10)', '10', '0', 'E');
INSERT INTO `gift` VALUES ('56', '40321', 'Lv60:黑暗妖精：修練者的黑魔石(10) ', '10', '0', 'D');
INSERT INTO `gift` VALUES ('56', '49157', 'Lv60:龍騎士：修練者的刻印的骨頭碎片(10)', '10', '0', 'R');
INSERT INTO `gift` VALUES ('56', '49158', 'Lv60:幻術士：修練者的生命之樹果實(10) ', '10', '0', 'I');
INSERT INTO `gift` VALUES ('56', '640336', 'Lv60:成長藥水(精靈) ', '1', '0', 'A');

-- ----------------------------
-- Table structure for `history_chat`
-- ----------------------------
DROP TABLE IF EXISTS `history_chat`;
