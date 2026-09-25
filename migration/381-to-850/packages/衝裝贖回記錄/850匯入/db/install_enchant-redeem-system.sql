-- =======================================================
-- 850匯入 / DB install — 衝裝失敗贖回記錄系統 (Item Redeem Record)
-- 來源：atu381.w_衝裝贖回記錄 (5 rows)
-- 目標：850 原生正規化 InnoDB 結構
-- =======================================================

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_enchant_redeem_config`;
CREATE TABLE `w_enchant_redeem_config` (
  `id` int NOT NULL COMMENT '序號',
  `npc_id` int NOT NULL COMMENT '贖回NPC編號',
  `item_id` int NOT NULL COMMENT '贖回憑證/目標道具編號',
  `count` int NOT NULL DEFAULT 1 COMMENT '所需消耗數量',
  PRIMARY KEY (`id`, `npc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='衝裝贖回NPC與憑證配置';

INSERT INTO `w_enchant_redeem_config` (`id`, `npc_id`, `item_id`, `count`) VALUES
	(0,93073,44070,10)),
	(1,93073,44070,20)),
	(2,93073,44070,30)),
	(3,93073,44070,40)),
	(4,93073,44070,50));
