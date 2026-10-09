CREATE TABLE `_config` (
  `index` int(10) NOT NULL AUTO_INCREMENT,
  `parameter` varchar(50) NOT NULL,
  `value` varchar(20) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`index`,`parameter`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of _config
-- ----------------------------
INSERT INTO `_config` VALUES ('1', 'ClientLanguage', '3', '客戶端語言');
INSERT INTO `_config` VALUES ('2', 'MaxOnlineUser', '200', '允許多少數量的玩家同時在線上');
INSERT INTO `_config` VALUES ('3', 'AutosaveInterval', '1200', '伺服器自動存檔時間間隔 (單位: 秒)');
INSERT INTO `_config` VALUES ('4', 'AutosaveInterval_Inventory', '300', '定時自動儲存角色裝備資料時間間隔 (單位: 秒)');
INSERT INTO `_config` VALUES ('5', 'CheckStrictness', '150', '加速器檢查嚴密度,為免除錯誤檢測設定數值允許幾%加速。(如果常常誤報，請將數值稍微調大)');
INSERT INTO `_config` VALUES ('6', 'InjusticeCount', '10', '加速器檢查設定不正常封包數值,滿足條件則切斷連線');
INSERT INTO `_config` VALUES ('7', 'JusticeCount', '4', '加速器檢查數值滿足時 InjusticeCount歸 0');
INSERT INTO `_config` VALUES ('8', 'AutoCreateAccount', 'true', '是否再登入畫面即可創建帳號 True=是 False=否');
INSERT INTO `_config` VALUES ('9', 'AllowIpCount', '3', '是否允許多開(同IP同時連線)的數量');
INSERT INTO `_config` VALUES ('10', 'ShowLoginNews', 'false', '登入公告 True or False');
INSERT INTO `_config` VALUES ('11', 'AnnounceCycleTime', '10', '循環公告時間設置 (單位:分鐘)  (AnnouncementsCycle .txt)');
INSERT INTO `_config` VALUES ('12', 'AnnounceShowModifyDate', 'true', '循環公告是否顯示日期時間');
INSERT INTO `_config` VALUES ('13', 'RateExp', '50', '經驗值倍率 1-32767');
INSERT INTO `_config` VALUES ('14', 'RateLawful', '1', '正義值倍率 1-32767');
INSERT INTO `_config` VALUES ('15', 'RateKarma', '100', '友好度倍率 1-32767');
INSERT INTO `_config` VALUES ('16', 'RateDropAdena', '100', '掉落金錢倍率 1-32767');
INSERT INTO `_config` VALUES ('17', 'RateDropItems', '1', '掉落物品倍率 1-32767');
INSERT INTO `_config` VALUES ('18', 'RateEnchantWeapon', '0', '衝武器成功率的額外加成 (%)');
INSERT INTO `_config` VALUES ('19', 'RateEnchantArmor', '0', '衝防具成功率的額外加成 (%)');
INSERT INTO `_config` VALUES ('20', 'RateWeightLimit', '5', '角色負重倍率 0-127');
INSERT INTO `_config` VALUES ('21', 'RateWeightLimitforPet', '5', '寵物負重倍率 0-127');
INSERT INTO `_config` VALUES ('22', 'RateShopSellingPrice', '1', '商店販賣價格倍率 0-127');
INSERT INTO `_config` VALUES ('23', 'RateShopPurchasingPrice', '1', '商店收購價格倍率 0-127');
INSERT INTO `_config` VALUES ('24', 'ChanceAttrEnchant', '30', '屬性強化成功率 (%)');
INSERT INTO `_config` VALUES ('25', 'ServerRestart', 'true', '伺服器是否自動重開');
INSERT INTO `_config` VALUES ('26', 'ServerRestartTime', '18', '現實時間幾點重開 (24小時制0~23)');
INSERT INTO `_config` VALUES ('27', 'ServerRestartBatName', 'ServerStart.bat', '重開的啟動檔');

-- ----------------------------
-- Table structure for `_config_other`
-- ----------------------------
DROP TABLE IF EXISTS `_config_other`;
