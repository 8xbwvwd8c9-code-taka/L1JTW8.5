package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

/**
 * D系列怪物菁英化系統 — 世界難度、詞墜庫與施法階梯規則資料載入器
 */
public class EliteConfigTable {
   private static final Logger log = Logger.getLogger(EliteConfigTable.class.getName());
   private static EliteConfigTable instance;

   // 難度配置
   private final Map<Integer, EliteDifficultyConfig> difficultyConfigs = new HashMap<>();
   // 詞墜庫
   private final Map<Integer, MonsterAffix> affixTemplates = new HashMap<>();
   // 怪物 1~10 級施法規則
   private final Map<Integer, MonsterSpellTierRule> tierRules = new HashMap<>();

   public static class EliteDifficultyConfig {
      public int difficultyLevel;
      public String difficultyName;
      public int mobEliteChancePct;
      public int mobHpMultiplierPct;
      public int bossHpMultiplierPct;
      public int dropRateMultiplierPct;
      public int affixCount;
      public List<Integer> prefixPool = new ArrayList<>();
      public List<Integer> suffixPool = new ArrayList<>();
      public int summonAiDmgReductionPct;
   }

   public static class MonsterAffix {
      public int affixId;
      public String diabloName;
      public String affixName;
      public String affixType; // PREFIX or SUFFIX
      public int addHpPct;
      public int addPhysicalReduction;
      public int addMagicReduction;
      public int addFireResist;
      public int addWaterResist;
      public int addWindResist;
      public int addEarthResist;
      public int procSkillId;
      public int procChancePct;
      public int auraGfxId;
      public boolean isActive;
   }

   public static class MonsterSpellTierRule {
      public int tier;
      public int minLvl;
      public int maxLvl;
      public int maxMagicLevel;
      public boolean allowClassSkills;
      public int attackSpellChancePct;
      public int buffSpellChancePct;
      public int healHpThresholdPct;
      public String tierName;
   }

   public static EliteConfigTable getInstance() {
      if (instance == null) {
         instance = new EliteConfigTable();
      }
      return instance;
   }

   private EliteConfigTable() {
      loadAll();
   }

   public void loadAll() {
      loadDifficultyConfig();
      loadAffixes();
      loadTierRules();
   }

   private void loadDifficultyConfig() {
      difficultyConfigs.clear();
      Connection conn = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;
      try {
         conn = DatabaseFactory.a().b();
         pstm = conn.prepareStatement("SELECT * FROM w_elite_monster_config");
         rs = pstm.executeQuery();
         while (rs.next()) {
            EliteDifficultyConfig cfg = new EliteDifficultyConfig();
            cfg.difficultyLevel = rs.getInt("difficulty_level");
            cfg.difficultyName = rs.getString("difficulty_name");
            cfg.mobEliteChancePct = rs.getInt("mob_elite_chance_pct");
            cfg.mobHpMultiplierPct = rs.getInt("mob_hp_multiplier_pct");
            cfg.bossHpMultiplierPct = rs.getInt("boss_hp_multiplier_pct");
            cfg.dropRateMultiplierPct = rs.getInt("drop_rate_multiplier_pct");
            cfg.affixCount = rs.getInt("affix_count");
            cfg.summonAiDmgReductionPct = rs.getInt("summon_ai_dmg_reduction_pct");

            String prefixes = rs.getString("prefix_pool");
            if (prefixes != null && !prefixes.trim().isEmpty()) {
               for (String idStr : prefixes.split(",")) {
                  try {
                     cfg.prefixPool.add(Integer.parseInt(idStr.trim()));
                  } catch (Exception ignored) {}
               }
            }

            String suffixes = rs.getString("suffix_pool");
            if (suffixes != null && !suffixes.trim().isEmpty()) {
               for (String idStr : suffixes.split(",")) {
                  try {
                     cfg.suffixPool.add(Integer.parseInt(idStr.trim()));
                  } catch (Exception ignored) {}
               }
            }
            difficultyConfigs.put(cfg.difficultyLevel, cfg);
         }
      } catch (SQLException e) {
         log.log(Level.SEVERE, "Failed loading w_elite_monster_config: " + e.getLocalizedMessage(), e);
      } finally {
         SQLUtil.a(rs, pstm, conn);
      }
   }

   private void loadAffixes() {
      affixTemplates.clear();
      Connection conn = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;
      try {
         conn = DatabaseFactory.a().b();
         pstm = conn.prepareStatement("SELECT * FROM w_monster_affix_template");
         rs = pstm.executeQuery();
         while (rs.next()) {
            MonsterAffix af = new MonsterAffix();
            af.affixId = rs.getInt("affix_id");
            af.diabloName = rs.getString("diablo_name");
            af.affixName = rs.getString("affix_name");
            af.affixType = rs.getString("affix_type");
            af.addHpPct = rs.getInt("add_hp_pct");
            af.addPhysicalReduction = rs.getInt("add_physical_reduction");
            af.addMagicReduction = rs.getInt("add_magic_reduction");
            af.addFireResist = rs.getInt("add_fire_resist");
            af.addWaterResist = rs.getInt("add_water_resist");
            af.addWindResist = rs.getInt("add_wind_resist");
            af.addEarthResist = rs.getInt("add_earth_resist");
            af.procSkillId = rs.getInt("proc_skill_id");
            af.procChancePct = rs.getInt("proc_chance_pct");
            af.auraGfxId = rs.getInt("aura_gfx_id");
            af.isActive = "ACTIVE".equalsIgnoreCase(rs.getString("status"));
            affixTemplates.put(af.affixId, af);
         }
      } catch (SQLException e) {
         log.log(Level.SEVERE, "Failed loading w_monster_affix_template: " + e.getLocalizedMessage(), e);
      } finally {
         SQLUtil.a(rs, pstm, conn);
      }
   }

   private void loadTierRules() {
      tierRules.clear();
      Connection conn = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;
      try {
         conn = DatabaseFactory.a().b();
         pstm = conn.prepareStatement("SELECT * FROM w_monster_spell_tier_rule");
         rs = pstm.executeQuery();
         while (rs.next()) {
            MonsterSpellTierRule tr = new MonsterSpellTierRule();
            tr.tier = rs.getInt("tier");
            tr.minLvl = rs.getInt("min_lvl");
            tr.maxLvl = rs.getInt("max_lvl");
            tr.maxMagicLevel = rs.getInt("max_magic_level");
            tr.allowClassSkills = rs.getInt("allow_class_skills") == 1;
            tr.attackSpellChancePct = rs.getInt("attack_spell_chance_pct");
            tr.buffSpellChancePct = rs.getInt("buff_spell_chance_pct");
            tr.healHpThresholdPct = rs.getInt("heal_hp_threshold_pct");
            tr.tierName = rs.getString("tier_name");
            tierRules.put(tr.tier, tr);
         }
      } catch (SQLException e) {
         log.log(Level.SEVERE, "Failed loading w_monster_spell_tier_rule: " + e.getLocalizedMessage(), e);
      } finally {
         SQLUtil.a(rs, pstm, conn);
      }
   }

   private static final Map<Integer, int[]> TIER_ATTACK_SPELLS = new HashMap<>();
   private static final Map<Integer, int[]> TIER_BUFF_SPELLS = new HashMap<>();

   static {
      // 攻擊魔法階梯 (全職業技能依等級區分)
      TIER_ATTACK_SPELLS.put(1, new int[]{1, 5});           // 光箭, 風刃
      TIER_ATTACK_SPELLS.put(2, new int[]{8, 11, 12});      // 火箭, 寒冰氣息, 毒咒
      TIER_ATTACK_SPELLS.put(3, new int[]{19, 20});         // 極光雷電, 起死回生
      TIER_ATTACK_SPELLS.put(4, new int[]{26, 28, 29});     // 火球術, 吸血鬼之吻, 緩速術
      TIER_ATTACK_SPELLS.put(5, new int[]{37, 38, 40});     // 木乃伊, 極道落雷, 黑闇之影
      TIER_ATTACK_SPELLS.put(6, new int[]{44, 46});         // 烈炎術, 地裂術
      TIER_ATTACK_SPELLS.put(7, new int[]{49, 52});         // 冰矛圍籬, 疾病術
      TIER_ATTACK_SPELLS.put(8, new int[]{57, 59, 87});     // 冰雪暴, 火牢, 衝擊之暈 (騎士)
      TIER_ATTACK_SPELLS.put(9, new int[]{65, 66});         // 雷霆風暴, 沉睡之霧
      TIER_ATTACK_SPELLS.put(10, new int[]{73, 74});        // 流星雨, 終極光裂術

      // 增益/治癒/自保魔法階梯
      TIER_BUFF_SPELLS.put(1, new int[]{2, 4});             // 保護罩, 初治
      TIER_BUFF_SPELLS.put(2, new int[]{10, 14});           // 擬似武器, 中治
      TIER_BUFF_SPELLS.put(3, new int[]{21});               // 鎧甲護持
      TIER_BUFF_SPELLS.put(4, new int[]{31});               // 通暢氣脈
      TIER_BUFF_SPELLS.put(5, new int[]{35});               // 高級治癒術
      TIER_BUFF_SPELLS.put(6, new int[]{42, 43, 48});       // 體魄強健, 加速術, 魔法消除
      TIER_BUFF_SPELLS.put(7, new int[]{51, 54});           // 狂暴術, 體力回復術
      TIER_BUFF_SPELLS.put(8, new int[]{58, 91, 105});      // 全部治癒術, 反擊屏障, 雙重破壞 (黑妖)
      TIER_BUFF_SPELLS.put(9, new int[]{68});               // 聖結界
      TIER_BUFF_SPELLS.put(10, new int[]{77});              // 絕對屏障
   }

   public List<Integer> getAttackSpellsUpToTier(int tier) {
      List<Integer> list = new ArrayList<>();
      for (int t = 1; t <= Math.min(tier, 10); t++) {
         int[] spells = TIER_ATTACK_SPELLS.get(t);
         if (spells != null) {
            for (int s : spells) {
               list.add(s);
            }
         }
      }
      return list;
   }

   public List<Integer> getBuffSpellsUpToTier(int tier) {
      List<Integer> list = new ArrayList<>();
      for (int t = 1; t <= Math.min(tier, 10); t++) {
         int[] spells = TIER_BUFF_SPELLS.get(t);
         if (spells != null) {
            for (int s : spells) {
               list.add(s);
            }
         }
      }
      return list;
   }

   public int getBestHealSpell(int tier) {
      if (tier >= 8) return 58; // 全治
      if (tier >= 5) return 35; // 高治
      if (tier >= 2) return 14; // 中治
      return 4; // 初治
   }

   public EliteDifficultyConfig getDifficultyConfig(int difficultyLevel) {
      EliteDifficultyConfig cfg = difficultyConfigs.get(difficultyLevel);
      if (cfg == null && !difficultyConfigs.isEmpty()) {
         cfg = difficultyConfigs.get(0);
      }
      return cfg;
   }

   public MonsterAffix getAffix(int affixId) {
      return affixTemplates.get(affixId);
   }

   public MonsterSpellTierRule getTierRule(int tier) {
      MonsterSpellTierRule r = tierRules.get(tier);
      if (r == null && !tierRules.isEmpty()) {
         r = tierRules.get(1);
      }
      return r;
   }

   public int calculateTier(int level) {
      if (level <= 10) return 1;
      int t = (level - 1) / 10 + 1;
      if (t > 10) return 10;
      return t;
   }
}
