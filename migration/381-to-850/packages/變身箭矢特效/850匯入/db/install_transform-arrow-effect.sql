-- =======================================================
-- 850導入 / DB install ─ transform-arrow-effect
-- 涵蓋：w_transform_arrow_effect
-- 來源：w_變身箭矢特效（1 row）
-- 決定：HOLD（等待 下游消費者封閉 + 客戶端 GFX 資源確認）
-- =======================================================

CREATE TABLE IF NOT EXISTS `w_transform_arrow_effect` (
  `poly_id`      INT          NOT NULL,
  `arrow_gfx_id` INT          NOT NULL,
  `note`         VARCHAR(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`poly_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 原始資料：polyid=6611 → arrowgfxid=8121（備='變身編號-箭矢特效對應'）
INSERT INTO `w_transform_arrow_effect` (`poly_id`, `arrow_gfx_id`, `note`) VALUES
  (6611, 8121, '變身編號-箭矢特效對應');