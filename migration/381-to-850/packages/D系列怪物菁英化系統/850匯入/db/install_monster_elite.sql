-- =======================================================
-- 850匯入 / DB install — monster-elite-system
-- 決策：HOLD
-- =======================================================

CREATE TABLE IF NOT EXISTS `w_elite_monster_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `world_difficulty` tinyint(2) NOT NULL DEFAULT 0
    COMMENT '0=普通 1=困難 2=地獄',
  `elite_spawn_chance` int(3) unsigned NOT NULL DEFAULT 10
    COMMENT '小怪生成菁英機率(%)，預設10',
  `hp_multiplier_pct` int(5) unsigned NOT NULL DEFAULT 130
    COMMENT '血量倍率(%)，130=x1.3，BOSS不套用',
  `drop_rate_multiplier_pct` int(5) unsigned NOT NULL DEFAULT 200
    COMMENT '掉落率倍率(%)，200=加倍',
  `prefix_affix_pool` varchar(255) NOT NULL DEFAULT ''
    COMMENT '前綴詞墜ID清單(逗號分隔)，空=不附加',
  `suffix_affix_pool` varchar(255) NOT NULL DEFAULT ''
    COMMENT '後綴詞墜ID清單(逗號分隔)，空=不附加',
  `note` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_world_difficulty` (`world_difficulty`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='D系列菁英怪世界難度設定';

-- 預設種子資料
INSERT INTO `w_elite_monster_config`
  (world_difficulty, elite_spawn_chance, hp_multiplier_pct, drop_rate_multiplier_pct, prefix_affix_pool, suffix_affix_pool, note)
VALUES
  (0, 10, 130, 200, '', '', '普通難度預設'),
  (1, 10, 130, 200, '', '', '困難難度（前後詞墜待填入）'),
  (2, 10, 130, 200, '', '', '地獄難度（前後詞墜待填入）');
