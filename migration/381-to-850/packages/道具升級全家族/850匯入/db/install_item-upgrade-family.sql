-- =======================================================
-- 850匯入 / DB install — 道具升級置換全家族 (Item Upgrade Family)
-- 包含：
-- 1. w_item_upgrade (1 row) — 道具強化升級公式
-- 2. w_item_upgrade_system (2 rows) — 道具升級系統與材料檢查
-- =======================================================

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_item_upgrade`;
CREATE TABLE `w_item_upgrade` (
  `item_id` int NOT NULL COMMENT '觸發升級道具ID',
  `name` varchar(100) NOT NULL COMMENT '道具名稱',
  `check_class` int NOT NULL DEFAULT 0,
  `level` int NOT NULL DEFAULT 0,
  `need_count` int NOT NULL DEFAULT 1,
  `integration_id` int NOT NULL DEFAULT 0 COMMENT '升級目標基底物品ID',
  `integration_name` varchar(100) NOT NULL COMMENT '基底物品名稱',
  `integration_count` int NOT NULL DEFAULT 1,
  `enchant_item` int NOT NULL DEFAULT 0,
  `random` int NOT NULL DEFAULT 100 COMMENT '成功機率%',
  `materials` varchar(255) DEFAULT '',
  `counts` varchar(255) DEFAULT '',
  `new_item` varchar(50) NOT NULL COMMENT '成功產出新道具ID',
  `new_item_name` varchar(100) NOT NULL COMMENT '產出新道具名稱',
  `new_item_counts` varchar(50) NOT NULL DEFAULT '1',
  `msg` varchar(255) DEFAULT '恭喜你，升級成功',
  `gfx_id` int NOT NULL DEFAULT 0,
  `failure_down` int NOT NULL DEFAULT 0,
  `failure_msg` varchar(255) DEFAULT '升級失敗',
  `failure_keep` int NOT NULL DEFAULT 1,
  `keep` int NOT NULL DEFAULT 1,
  `keep_materials` int NOT NULL DEFAULT 1,
  `all_message` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`item_id`, `integration_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='道具升級規則';

INSERT INTO `w_item_upgrade` (
  `item_id`, `name`, `check_class`, `level`, `need_count`,
  `integration_id`, `integration_name`, `integration_count`, `enchant_item`,
  `random`, `materials`, `counts`, `new_item`, `new_item_name`, `new_item_counts`,
  `msg`, `gfx_id`, `failure_down`, `failure_msg`, `failure_keep`, `keep`, `keep_materials`, `all_message`
) VALUES
	(92506,'測試',0,0,1,1,'歐西斯匕首',1,0,10,'','','2','骰子匕首','1','恭喜你，升級成功',763,0,'升級失敗',1,1,1,'\\fR玩家:%S - \\fT強化成功[%S]'));

DROP TABLE IF EXISTS `w_item_upgrade_system`;
CREATE TABLE `w_item_upgrade_system` (
  `item` int NOT NULL COMMENT '觸發道具編號',
  `note` varchar(100) DEFAULT '',
  `check_level` int NOT NULL DEFAULT 0,
  `check_item` varchar(255) NOT NULL COMMENT '所需材料道具ID',
  `check_count` varchar(255) NOT NULL COMMENT '所需材料數量',
  `ne_item` int NOT NULL COMMENT '基底道具ID',
  `note1` varchar(100) DEFAULT '',
  `random` int NOT NULL DEFAULT 100,
  `give_item` int NOT NULL COMMENT '產出物品ID',
  `note2` varchar(100) DEFAULT '',
  `give_count` int NOT NULL DEFAULT 1,
  `success` varchar(255) DEFAULT '升級成功',
  `fail` varchar(255) DEFAULT '升級失敗',
  `save_type` int NOT NULL DEFAULT 1,
  PRIMARY KEY (`item`, `ne_item`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='道具升級系統配置';

INSERT INTO `w_item_upgrade_system` (
  `item`, `note`, `check_level`, `check_item`, `check_count`,
  `ne_item`, `note1`, `random`, `give_item`, `note2`, `give_count`,
  `success`, `fail`, `save_type`
) VALUES
	(92402,'道具升級輸入classname:add.Item_up',0,'40308','10000',1,'歐西斯匕首',10,42,'細劍',1,'升級成功','升級失敗',1)),
	(92402,'升級石',0,'44070','100',4,'匕首',100,9,'奧裡哈魯根短劍',1,'升級成功','升級失敗',1));
