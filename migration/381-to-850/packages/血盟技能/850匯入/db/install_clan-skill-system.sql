-- =======================================================
-- 850匯入 / DB install — 血盟技能系統 (Clan Skill System)
-- 來源：atu381.w_血盟技能 (30 rows)
-- 目標：850 原生正規化 InnoDB 結構
-- =======================================================

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `w_clan_skill`;
CREATE TABLE `w_clan_skill` (
  `clan_skill_id` int NOT NULL COMMENT '血盟技能ID',
  `clan_skill_lv` int NOT NULL COMMENT '血盟技能等級',
  `clan_skill_name` varchar(100) NOT NULL COMMENT '技能名稱',
  `note` varchar(255) DEFAULT '' COMMENT '能力說明',
  `material` int NOT NULL DEFAULT 0 COMMENT '學習消耗材料道具ID',
  `material_count` bigint NOT NULL DEFAULT 0 COMMENT '學習消耗材料數量',
  `material_level` int NOT NULL DEFAULT 0 COMMENT '所需材料等級/額外條件',
  `check_lvturn` int NOT NULL DEFAULT 0 COMMENT '轉生等級限制',
  `check_level` int NOT NULL DEFAULT 0 COMMENT '角色等級限制',
  `add_max_hp` int NOT NULL DEFAULT 0,
  `add_max_mp` int NOT NULL DEFAULT 0,
  `add_hpr` smallint NOT NULL DEFAULT 0,
  `add_mpr` smallint NOT NULL DEFAULT 0,
  `add_str` smallint NOT NULL DEFAULT 0,
  `add_con` smallint NOT NULL DEFAULT 0,
  `add_dex` smallint NOT NULL DEFAULT 0,
  `add_int` smallint NOT NULL DEFAULT 0,
  `add_wis` smallint NOT NULL DEFAULT 0,
  `add_cha` smallint NOT NULL DEFAULT 0,
  `reduction_dmg` smallint NOT NULL DEFAULT 0,
  `reduction_magic_dmg` smallint NOT NULL DEFAULT 0,
  `add_water` smallint NOT NULL DEFAULT 0,
  `add_wind` smallint NOT NULL DEFAULT 0,
  `add_ac` smallint NOT NULL DEFAULT 0,
  `add_sp` smallint NOT NULL DEFAULT 0,
  `add_mr` smallint NOT NULL DEFAULT 0,
  `add_dmg` smallint NOT NULL DEFAULT 0,
  `add_bow_dmg` smallint NOT NULL DEFAULT 0,
  `add_hit` smallint NOT NULL DEFAULT 0,
  `add_bow_hit` smallint NOT NULL DEFAULT 0,
  `add_fire` smallint NOT NULL DEFAULT 0,
  `add_earth` smallint NOT NULL DEFAULT 0,
  PRIMARY KEY (`clan_skill_id`, `clan_skill_lv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='血盟技能與升級條件';

INSERT INTO `w_clan_skill` (
  `clan_skill_id`, `clan_skill_lv`, `clan_skill_name`, `note`,
  `material`, `material_count`, `material_level`, `check_lvturn`, `check_level`,
  `add_max_hp`, `add_max_mp`, `add_hpr`, `add_mpr`,
  `add_str`, `add_con`, `add_dex`, `add_int`, `add_wis`, `add_cha`,
  `reduction_dmg`, `reduction_magic_dmg`,
  `add_water`, `add_wind`, `add_ac`, `add_sp`, `add_mr`,
  `add_dmg`, `add_bow_dmg`, `add_hit`, `add_bow_hit`, `add_fire`, `add_earth`
) VALUES
	(ClanSkillId,ClanSkillLv,ClanSkillName,Note,Material,MaterialCount,MaterialLevel,CheckLvturn,CheckLevel,AddMaxHp,AddMaxMp,AddHpr,AddMpr,AddStr,AddCon,AddDex,AddInt,AddWis,AddCha,ReductionDmg,ReductionMagicDmg,AddWater,AddWind,AddAc,AddSp,AddMr,AddDmg,AddBowDmg,AddHit,AddBowHit,AddFire,AddEarth) VALUES
	 (1,1,'盟魂-帝權之擊Lv.1','力量+1.敏捷+1.智力+1.PVP傷害+1','1','1','0',0,0,0,0,0,0,1,0,1,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0),
	(1,2,'盟魂-帝權之擊Lv.2','力量+2.敏捷+2.智力+2.PVP傷害+2','40308','10000001','0',0,0,0,0,0,0,2,0,2,2,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0),
	(1,3,'盟魂-帝權之擊Lv.3','力量+3.敏捷+3.智力+3.PVP傷害+3','40308','10000002','0',0,0,0,0,0,0,3,0,3,3,0,0,0,0,0,0,0,0,0,3,3,0,0,0,0),
	(1,4,'盟魂-帝權之擊Lv.4','力量+4.敏捷+4.智力+4.PVP傷害+4','40308','10000003','0',0,0,0,0,0,0,4,0,4,4,0,0,0,0,0,0,0,0,0,4,4,0,0,0,0),
	(1,5,'盟魂-帝權之擊Lv.5','力量+5.敏捷+5.智力+5.PVP傷害+5','40308','10000004','0',0,0,0,0,0,0,5,0,5,5,0,0,0,0,0,0,0,0,0,5,5,0,0,0,0),
	(1,6,'盟魂-帝權之擊Lv.3','力量+6.敏捷+6.智力+6.PVP傷害+6','40308','10000005','0',0,0,0,0,0,0,6,0,6,6,0,0,0,0,0,0,0,0,0,6,6,0,0,0,0),
	(1,7,'盟魂-帝權之擊Lv.7','力量+7.敏捷+7.智力+7.PVP傷害+7','40308','10000006','0',0,0,0,0,0,0,7,0,7,7,0,0,0,0,0,0,0,0,0,7,7,0,0,0,0),
	(1,8,'盟魂-帝權之擊Lv.8','力量+8.敏捷+8.智力+8.PVP傷害+8','40308','10000007','0',0,0,0,0,0,0,8,0,8,8,0,0,0,0,0,0,0,0,0,8,8,0,0,0,0),
	(1,9,'盟魂-帝權之擊Lv.9','力量+9.敏捷+9.智力+9.PVP傷害+9','40308','10000008','0',0,0,0,0,0,0,9,0,9,9,0,0,0,0,0,0,0,0,0,9,9,0,0,0,0),
	(1,10,'盟魂-帝權之擊Lv.10','力量+10.敏捷+10.智力+10.PVP傷害+10','40308','10000009','0',0,0,0,0,0,0,10,0,10,10,0,0,0,0,0,0,0,0,0,10,10,0,0,0,0),
	(ClanSkillId,ClanSkillLv,ClanSkillName,Note,Material,MaterialCount,MaterialLevel,CheckLvturn,CheckLevel,AddMaxHp,AddMaxMp,AddHpr,AddMpr,AddStr,AddCon,AddDex,AddInt,AddWis,AddCha,ReductionDmg,ReductionMagicDmg,AddWater,AddWind,AddAc,AddSp,AddMr,AddDmg,AddBowDmg,AddHit,AddBowHit,AddFire,AddEarth) VALUES
	 (2,1,'盟魂-血戰鋼鐵Lv.1','HP+500 MP+500','40308','50000000','0',0,0,500,500,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
	(2,4,'盟魂-血戰鋼鐵Lv.4','HP+2000 MP+2000','40308','10000001','0',0,0,2000,2000,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
	(2,3,'盟魂-血戰鋼鐵Lv.3','HP+1500 MP+1500','40308','10000002','0',0,0,1500,1500,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
	(2,2,'盟魂-血戰鋼鐵Lv.2','HP+1000 MP+1000','40308','10000003','0',0,0,1000,1000,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
	(2,5,'盟魂-血戰鋼鐵Lv.5','HP+2500 MP+2500','40308','10000004','0',0,0,2500,2500,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
	(2,6,'盟魂-血戰鋼鐵Lv.6','HP+3000 MP+3000','40308','10000005','0',0,0,3000,3000,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
	(2,7,'盟魂-血戰鋼鐵Lv.7','HP+3500 MP+3500','40308','10000006','0',0,0,3500,3500,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
	(2,8,'盟魂-血戰鋼鐵Lv.8','HP+4000 MP+4000','40308','10000007','0',0,0,4000,4000,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
	(2,9,'盟魂-血戰鋼鐵Lv.9','HP+4500 MP+4500','40308','10000008','0',0,0,4500,4500,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
	(2,10,'盟魂-血戰鋼鐵Lv.10','HP+5000 MP+5000','40308','10000009','0',0,0,5000,5000,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
	(ClanSkillId,ClanSkillLv,ClanSkillName,Note,Material,MaterialCount,MaterialLevel,CheckLvturn,CheckLevel,AddMaxHp,AddMaxMp,AddHpr,AddMpr,AddStr,AddCon,AddDex,AddInt,AddWis,AddCha,ReductionDmg,ReductionMagicDmg,AddWater,AddWind,AddAc,AddSp,AddMr,AddDmg,AddBowDmg,AddHit,AddBowHit,AddFire,AddEarth) VALUES
	 (3,1,'盟魂-聖戰之禦Lv.1','魔法防禦+5 PVP傷害減免+2','40308','70000000','0',0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,5,0,0,0,0,0,0),
	(3,2,'盟魂-聖戰之禦Lv.2','魔法防禦+10 PVP傷害減免+4','40308','10000001','0',0,0,0,0,0,0,0,0,0,0,0,0,4,4,0,0,0,0,10,0,0,0,0,0,0),
	(3,3,'盟魂-聖戰之禦Lv.3','魔法防禦+15 PVP傷害減免+6','40308','10000002','0',0,0,0,0,0,0,0,0,0,0,0,0,6,6,0,0,0,0,15,0,0,0,0,0,0),
	(3,4,'盟魂-聖戰之禦Lv.4','魔法防禦+20 PVP傷害減免+8','40308','10000003','0',0,0,0,0,0,0,0,0,0,0,0,0,8,8,0,0,0,0,20,0,0,0,0,0,0),
	(3,5,'盟魂-聖戰之禦Lv.5','魔法防禦+25 PVP傷害減免+10','40308','10000004','0',0,0,0,0,0,0,0,0,0,0,0,0,10,10,0,0,0,0,25,0,0,0,0,0,0),
	(3,6,'盟魂-聖戰之禦Lv.6','魔法防禦+30 PVP傷害減免+12','40308','10000005','0',0,0,0,0,0,0,0,0,0,0,0,0,12,12,0,0,0,0,30,0,0,0,0,0,0),
	(3,7,'盟魂-聖戰之禦Lv.7','魔法防禦+35 PVP傷害減免+14','40308','10000006','0',0,0,0,0,0,0,0,0,0,0,0,0,14,14,0,0,0,0,35,0,0,0,0,0,0),
	(3,8,'盟魂-聖戰之禦Lv.8','魔法防禦+40 PVP傷害減免+16','40308','10000007','0',0,0,0,0,0,0,0,0,0,0,0,0,16,16,0,0,0,0,40,0,0,0,0,0,0),
	(3,9,'盟魂-聖戰之禦Lv.9','魔法防禦+45 PVP傷害減免+18','40308','10000008','0',0,0,0,0,0,0,0,0,0,0,0,0,18,18,0,0,0,0,45,0,0,0,0,0,0),
	(3,10,'盟魂-聖戰之禦Lv.10','魔法防禦+50 PVP傷害減免+20','40308','10000009','0',0,0,0,0,0,0,0,0,0,0,0,0,20,20,0,0,0,0,50,0,0,0,0,0,0);
