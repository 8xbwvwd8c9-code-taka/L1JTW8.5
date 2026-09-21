CREATE TABLE `_config_world` (
  `index` int(10) NOT NULL AUTO_INCREMENT,
  `parameter` varchar(50) NOT NULL,
  `value` varchar(20) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`index`,`parameter`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of _config_world
-- ----------------------------
INSERT INTO `_config_world` VALUES ('1', 'GlobalChatLevel', '80', '全體聊天最低等級限制');
INSERT INTO `_config_world` VALUES ('2', 'WhisperChatLevel', '5', '密語最低等級限制');
INSERT INTO `_config_world` VALUES ('3', 'AutoLoot', '1', '設定取得道具的方式 0-掉落地上, 1-掉落寵物身上, 2-掉落角色身上');
INSERT INTO `_config_world` VALUES ('4', 'LootingRange', '4', '設定道具掉落的範圍大小');
INSERT INTO `_config_world` VALUES ('5', 'NonPvP', 'false', 'NonPvP設定  (false = 可以PvP)');
INSERT INTO `_config_world` VALUES ('6', 'ChangeTitleByOneself', 'false', '是否允許自己更改稱號');
INSERT INTO `_config_world` VALUES ('7', 'MaxClanMember', '0', '血盟人數上限 ( 0的話，按照王的魅力計算)');
INSERT INTO `_config_world` VALUES ('8', 'ClanAlliance', 'true', '是否開啟血盟聯盟系統(加入另一個王子的血盟)');
INSERT INTO `_config_world` VALUES ('9', 'MaxPartyMember', '8', '組隊人數上限');
INSERT INTO `_config_world` VALUES ('10', 'MaxChatPartyMember', '8', '組隊聊天人數上限');
INSERT INTO `_config_world` VALUES ('11', 'SimWarPenalty', 'false', '設定攻城戰中死亡後是否會受到處罰');
INSERT INTO `_config_world` VALUES ('12', 'GetBackRestart', 'false', '設定重新登入時是否自動回村');
INSERT INTO `_config_world` VALUES ('13', 'GroundClearTime', '10', '設定物品在地面自動清除掉的時間(分) (0=關閉自動清除地面物品)');
INSERT INTO `_config_world` VALUES ('14', 'GroundClearRange', '5', '設定人物周圍不清除物品範圍大小');
INSERT INTO `_config_world` VALUES ('15', 'GM_AttackMessage', 'true', '設定GM是否顯示傷害訊息 True=顯示, False=不顯示');
INSERT INTO `_config_world` VALUES ('16', 'GM_Shop', 'true', '設定是否開啟GM商店');
INSERT INTO `_config_world` VALUES ('17', 'GM_ShopMinID', '1', '設定GM商店編號最小值設定，可查看在spawnlist_npc內的編號進行設定');
INSERT INTO `_config_world` VALUES ('18', 'GM_ShopMaxID', '37', '設定GM商店編號最大值設定，可查看在spawnlist_npc內的編號進行設定');
INSERT INTO `_config_world` VALUES ('19', 'WhoCommand', 'true', '設定 /who 指令是否可以使用 True=可以, False=不可以');
INSERT INTO `_config_world` VALUES ('20', 'RevivalPotion', 'false', '設定99級是否可以獲得返生藥水 True=可以, False=不可以');
INSERT INTO `_config_world` VALUES ('21', 'WarDuring', '1h', '設定攻城戰持續時間 (d:日 h:時 m:分)');
INSERT INTO `_config_world` VALUES ('22', 'WarInterval', '7d', '設定攻城日的間隔 (d:日 h:時 m:分)');
INSERT INTO `_config_world` VALUES ('23', 'InitBossSpawn', 'true', '伺服器啟動時Boss是否出現');
INSERT INTO `_config_world` VALUES ('24', 'ElementalStoneAmount', '300', '妖精森林元素石的總數量');
INSERT INTO `_config_world` VALUES ('25', 'HouseTaxInterval', '10', '盟屋稅金的支付期限(日)');
INSERT INTO `_config_world` VALUES ('26', 'MaxDollCount', '1', '設定魔法娃娃召喚數量上限');
INSERT INTO `_config_world` VALUES ('27', 'MaxNpcItem', '8', 'NPC(怪物,召喚, 寵物)身上可以持有的最大物品數量');
INSERT INTO `_config_world` VALUES ('28', 'NpcDeathTime', '10', 'NPC死亡後屍體消失時間（秒）');
INSERT INTO `_config_world` VALUES ('29', 'GDropItemTime', '10', '妖精森林NPC道具重置時間  #預設 10');
INSERT INTO `_config_world` VALUES ('30', 'InnRoomCount', '3', '旅館房間可租用數量 0~512');
INSERT INTO `_config_world` VALUES ('31', 'DefaultCharacterSlot', '8', '預設角色數量');
INSERT INTO `_config_world` VALUES ('32', 'MaxAccountWarehouseSize', '150', '倉庫物品上限數量');
INSERT INTO `_config_world` VALUES ('33', 'MaxClanWarehouseSize', '200', '血盟倉庫物品上限數量');
INSERT INTO `_config_world` VALUES ('34', 'DeleteCharacterAfter7Days', 'false', '角色等級30以上，刪除角色是否要等待7天');
INSERT INTO `_config_world` VALUES ('35', 'FightIsActive', 'true', '是否啟動戰鬥特化系統');
INSERT INTO `_config_world` VALUES ('36', 'NoviceProtectionIsActive', 'true', '是否啟動新手保護系統(遭遇的守護)');
INSERT INTO `_config_world` VALUES ('37', 'NoviceMaxLevel', '70', '被歸類為新手的等級');
INSERT INTO `_config_world` VALUES ('38', 'NoviceProtectionRange', '10', '被超過多少等級的玩家殺死時, 將啟動新手保護機制');
