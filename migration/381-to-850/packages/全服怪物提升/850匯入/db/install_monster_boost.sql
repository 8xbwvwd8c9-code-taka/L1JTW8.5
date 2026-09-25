/*
 Navicat Premium Dump SQL

 Source Server         : My專用筆電
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB)
 Source Host           : 26.181.156.204:3306
 Source Schema         : 381

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB)
 File Encoding         : 65001

 Date: 20/08/2026 09:35:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for w_全服怪物提升
-- ----------------------------
DROP TABLE IF EXISTS `w_全服怪物提升`;
CREATE TABLE `w_全服怪物提升`  (
  `id` int(11) NOT NULL,
  `hp_rate` int(11) NOT NULL DEFAULT 100 COMMENT '血量倍率(%)：100=正常1倍, 500=5倍。警告:勿設為0',
  `mp_rate` int(11) NOT NULL DEFAULT 100 COMMENT '魔量倍率(%)：100=正常1倍, 200=2倍。警告:勿設為0',
  `mr_rate` int(11) NOT NULL DEFAULT 100 COMMENT '抗魔倍率(%)：100=正常1倍, 200=2倍。警告:勿設為0',
  `ac_add` int(11) NOT NULL DEFAULT 0 COMMENT '防禦增減：0=正常, 負數(如-10)=變硬, 正數(如10)=變軟',
  `dmg_rate` int(11) NOT NULL DEFAULT 100 COMMENT '攻擊倍率(%)：100=正常1倍, 500=5倍。警告:勿設為0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_全服怪物提升
-- ----------------------------
INSERT INTO `w_全服怪物提升` VALUES (1, 100, 100, 100, 0, 100);

SET FOREIGN_KEY_CHECKS = 1;
