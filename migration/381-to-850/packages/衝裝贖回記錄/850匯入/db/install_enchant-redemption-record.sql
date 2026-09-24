-- ============================================================
-- install_enchant-redemption-record.sql
-- Module: enchant-redemption-record (衝裝贖回記錄)
-- Decision: HOLD — 未實作，此 SQL 為結構預備稿
-- Source: w_衝裝贖回記錄_202609221205.sql (381 DB, 181 bytes)
-- ============================================================

-- [HOLD] 以下 DDL 根據 381 INSERT 結構逆向推導，尚未在 850 驗證

CREATE TABLE IF NOT EXISTS `w_衝裝贖回記錄` (
  `id`     INT NOT NULL COMMENT '強化等級索引 (0 起算)',
  `npcid`  INT NOT NULL COMMENT '提供贖回服務的 NPC ID',
  `itemid` INT NOT NULL COMMENT '贖回所需物品 ID',
  `count`  INT NOT NULL DEFAULT 0 COMMENT '所需物品數量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='衝裝贖回費用設定表';

-- 381 來源資料（NPC 93073，物品 44070）
INSERT INTO `w_衝裝贖回記錄` (`id`, `npcid`, `itemid`, `count`) VALUES
  (0, 93073, 44070, 10),
  (1, 93073, 44070, 20),
  (2, 93073, 44070, 30),
  (3, 93073, 44070, 40),
  (4, 93073, 44070, 50);
