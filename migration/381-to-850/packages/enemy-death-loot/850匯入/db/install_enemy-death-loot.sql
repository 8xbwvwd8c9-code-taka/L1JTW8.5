-- ============================================================
-- install_enemy-death-loot.sql
-- Module: enemy-death-loot (敵人死亡奪寶)
-- Decision: HOLD — 未實作，此 SQL 為結構預備稿
-- Source: w_敵人死亡奪寶_202609221205.sql (381 DB, 273 bytes)
-- ============================================================

-- [HOLD] 以下 DDL 根據 381 INSERT 結構逆向推導，尚未在 850 驗證
-- 執行前請確認 850 DB 字元集與 381 相符 (utf8mb4)

CREATE TABLE IF NOT EXISTS `w_敵人死亡奪寶` (
  `item_id`           INT          NOT NULL COMMENT '可被奪取的物品 ID',
  `note`              VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '備注說明',
  `steal_chance`      INT          NOT NULL DEFAULT 0  COMMENT '奪取機率 (百分比 1~100)',
  `min_steal_count`   INT          NOT NULL DEFAULT 0  COMMENT '最小奪取數量',
  `max_steal_count`   INT          NOT NULL DEFAULT 0  COMMENT '最大奪取數量',
  `is_broadcast`      TINYINT(1)   NOT NULL DEFAULT 0  COMMENT '是否廣播 (0=否, 1=是)',
  `drop_on_floor`     TINYINT(1)   NOT NULL DEFAULT 0  COMMENT '是否丟落地板 (0=否, 1=是)',
  `anti_steal_item_id` INT         NOT NULL DEFAULT 0  COMMENT '防奪寶物品 ID (0=無)',
  `level`             INT          NOT NULL DEFAULT 0  COMMENT '等級限制 (0=不限)',
  `mete_level`        INT          NOT NULL DEFAULT 0  COMMENT '轉生等級限制 (0=不限)',
  `dropmsg`           VARCHAR(128) NOT NULL DEFAULT '' COMMENT '廣播訊息格式 (%S=名字佔位)',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='敵人死亡奪寶設定表';

-- 381 來源資料（原始單筆）
INSERT INTO `w_敵人死亡奪寶`
  (`item_id`, `note`, `steal_chance`, `min_steal_count`, `max_steal_count`,
   `is_broadcast`, `drop_on_floor`, `anti_steal_item_id`, `level`, `mete_level`, `dropmsg`)
VALUES
  (40308, '金幣', 100, 10000, 30000, 1, 1, 0, 0, 0, '玩家:[%S]死亡,被玩家[%S]搶奪了(%S)');
