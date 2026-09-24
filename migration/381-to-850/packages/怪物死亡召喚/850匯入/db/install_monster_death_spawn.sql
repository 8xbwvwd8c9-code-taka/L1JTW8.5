-- =======================================================
-- 850匯入 / DB install — monster-death-spawn
-- 來源：381 atu381_0906.sql line 4808（SCHEMA_CONFIRMED）
-- 決策：HOLD
-- =======================================================
-- 381 原始 schema（MyISAM+sjis）→ 850-first 設計（InnoDB+utf8）
-- 欄位語意保留，新增企劃需求欄位（強化累積/掉落加成）

CREATE TABLE IF NOT EXISTS `w_monster_death_spawn` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dead_npc_id` int(11) NOT NULL COMMENT '死亡NPC編號',
  `note` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `spawn_npc_id` int(11) NOT NULL COMMENT '召喚NPC編號',
  `spawn_duration_min` int(11) NOT NULL DEFAULT 10 COMMENT '召喚NPC時間(分鐘)',
  `death_talk` varchar(1000) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '死亡NPC說話',
  `tele_x` int(11) DEFAULT 0 COMMENT '傳送X (-1=不傳送)',
  `tele_y` int(11) DEFAULT 0,
  `tele_mapid` int(11) DEFAULT -1 COMMENT '傳送地圖ID (-1=不傳送)',
  -- 企劃新增欄位（超出381原始功能，850-first extension）
  `is_boss` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0=小怪 1=BOSS',
  `trigger_chance_x10` int(5) NOT NULL DEFAULT 10
    COMMENT '觸發機率×10（10=1.0% 5=0.5%），×10避免浮點精度問題',
  `strength_pct_per_spawn` int(5) NOT NULL DEFAULT 150
    COMMENT '每次復活能力強化(%)，150=×1.5%',
  `max_strength_pct` int(5) NOT NULL DEFAULT 10000
    COMMENT '最大累積強化度上限(%)，10000=100%',
  `drop_bonus_pct_per_spawn` int(3) NOT NULL DEFAULT 1
    COMMENT '每次復活掉落率加成(%)，1=+1%',
  PRIMARY KEY (`id`),
  KEY `idx_dead_npc` (`dead_npc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
  COMMENT='怪物死亡召喚規則（381 w_怪物死亡召喚 + 企劃強化累積擴充）';
