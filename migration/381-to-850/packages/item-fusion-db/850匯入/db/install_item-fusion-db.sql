-- ============================================================
-- install_item-fusion-db.sql
-- 381→850 Migration Package: item-fusion-db (物品融合db化)
-- decision=HOLD / server_level=NOT_FINAL
-- ============================================================
-- BLOCKED: 381 source SQL is 0 bytes (no active data)
-- Schema sourced from: atu381_0906.sql (backup reference)
-- DO NOT EXECUTE until decision changes from HOLD
-- ============================================================

-- Step 1: Create table (381 schema reference port)
CREATE TABLE IF NOT EXISTS `w_物品融合db化` (
  `item_id`             int(11)          NOT NULL COMMENT '產出道具編號(主鍵)',
  `name`                varchar(45)      NOT NULL COMMENT '融合名稱說明',
  `checkClass`          int(11)          NOT NULL DEFAULT '0' COMMENT '職業限制(0=全職業)',
  `checkLevel`          int(11)          NOT NULL DEFAULT '0' COMMENT '等級限制(0=無限制)',
  `rnd`                 int(11)          NOT NULL DEFAULT '100' COMMENT '成功率(1-100)',
  `checkItem`           int(11)          NOT NULL COMMENT '需持有道具編號(0=不需要)',
  `hpConsume`           int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '消耗HP',
  `mpConsume`           int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '消耗MP',
  `material`            int(11)          NOT NULL COMMENT '材料1 道具編號',
  `material_count`      int(11)          NOT NULL COMMENT '材料1 數量',
  `material_2`          int(11)          NOT NULL COMMENT '材料2 道具編號(0=不需要)',
  `material_2_count`    int(11)          NOT NULL COMMENT '材料2 數量',
  `material_3`          int(11)          NOT NULL COMMENT '材料3 道具編號(0=不需要)',
  `material_3_count`    int(11)          NOT NULL COMMENT '材料3 數量',
  `material_4`          int(11)          NOT NULL COMMENT '材料4 道具編號(0=不需要)',
  `material_4_count`    int(11)          NOT NULL COMMENT '材料4 數量',
  `material_5`          int(11)          NOT NULL COMMENT '材料5 道具編號(0=不需要)',
  `material_5_count`    int(11)          NOT NULL COMMENT '材料5 數量',
  `new_item`            int(11)          NOT NULL COMMENT '產出道具編號',
  `new_item_counts`     int(11)          NOT NULL COMMENT '產出數量',
  `new_Enchantlvl_SW`   int(11)          NOT NULL COMMENT '是否繼承強化等級(1=是)',
  `new_item_Enchantlvl` int(11)          NOT NULL DEFAULT '0' COMMENT '指定強化等級',
  `removeItem`          int(11)          NOT NULL DEFAULT '1' COMMENT '失敗是否消耗材料(1=是)',
  `message`             varchar(1000)    DEFAULT NULL COMMENT '融合成功訊息',
  `item_Html`           int(11)          NOT NULL COMMENT '操作HTML編號',
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物品融合db化系統 [381→850 HOLD]';

-- Step 2: INSERT data placeholder
-- BLOCKED: No INSERT data available from 381 source (0 bytes SQL)
-- Data must be populated from live 381 server dump before going live
-- INSERT INTO `w_物品融合db化` ... ;
