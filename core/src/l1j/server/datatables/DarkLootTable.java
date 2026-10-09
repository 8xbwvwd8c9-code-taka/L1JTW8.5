package l1j.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.DatabaseFactory;
import l1j.server.utils.SQLUtil;

/**
 * 暗黑打寶詞綴資料表與五大元素寶石數值矩陣載入器
 */
public class DarkLootTable {
   private static final Logger log = Logger.getLogger(DarkLootTable.class.getName());
   private static DarkLootTable instance;

   // 寶石能力模板: itemId -> GemTemplate
   private final Map<Integer, GemTemplate> gemTemplates = new HashMap<>();

   public static class GemTemplate {
      public int itemId;
      public String gemType;
      public int stage;
      public String gemName;
      public String targetType; // weapon, armor, both

      // 武器鑲嵌加成
      public int wDmg;
      public int wHit;
      public int wSp;
      public int wFireDmg;
      public int wWaterDmg;
      public int wAirDmg;
      public int wEarthDmg;
      public int wAllDmg;
      public int wCrit;
      public int wStr;
      public int wDex;
      public int wCon;
      public int wInt;

      // 防具鑲嵌加成
      public int aAc;
      public int aMr;
      public int aHp;
      public int aMp;
      public int aMpr;
      public int aDmgReduction;
      public int aFireRes;
      public int aWaterRes;
      public int aAirRes;
      public int aEarthRes;
      public int aAllRes;
      public int aAllStat; // 完美無瑕全能力+1
      public int aDmg;
      public int aHit;
      public int aSp;
      public int aStr;
      public int aDex;
      public int aCon;
      public int aInt;
   }

   public static DarkLootTable getInstance() {
      if (instance == null) {
         instance = new DarkLootTable();
      }
      return instance;
   }

   private DarkLootTable() {
      loadGemTemplates();
   }

   public void loadGemTemplates() {
      gemTemplates.clear();
      Connection con = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;

      try {
         con = DatabaseFactory.a().b();
         pstm = con.prepareStatement("SELECT * FROM dark_gem_template");
         rs = pstm.executeQuery();

         while (rs.next()) {
            GemTemplate t = new GemTemplate();
            t.itemId = rs.getInt("item_id");
            t.gemType = rs.getString("gem_type");
            t.stage = rs.getInt("stage");
            t.gemName = rs.getString("gem_name");
            t.targetType = rs.getString("target_type");

            t.wDmg = rs.getInt("w_dmg");
            t.wHit = rs.getInt("w_hit");
            t.wSp = rs.getInt("w_sp");
            t.wFireDmg = rs.getInt("w_fire_dmg");
            t.wWaterDmg = rs.getInt("w_water_dmg");
            t.wAirDmg = rs.getInt("w_air_dmg");
            t.wEarthDmg = rs.getInt("w_earth_dmg");
            t.wAllDmg = rs.getInt("w_all_dmg");
            t.wCrit = rs.getInt("w_crit");
            t.wStr = rs.getInt("w_str");
            t.wDex = rs.getInt("w_dex");
            t.wCon = rs.getInt("w_con");
            t.wInt = rs.getInt("w_int");

            t.aAc = rs.getInt("a_ac");
            t.aMr = rs.getInt("a_mr");
            t.aHp = rs.getInt("a_hp");
            t.aMp = rs.getInt("a_mp");
            t.aMpr = rs.getInt("a_mpr");
            t.aDmgReduction = rs.getInt("a_dmg_reduction");
            t.aFireRes = rs.getInt("a_fire_res");
            t.aWaterRes = rs.getInt("a_water_res");
            t.aAirRes = rs.getInt("a_air_res");
            t.aEarthRes = rs.getInt("a_earth_res");
            t.aAllRes = rs.getInt("a_all_res");
            t.aAllStat = rs.getInt("a_all_stat");
            t.aDmg = rs.getInt("a_dmg");
            t.aHit = rs.getInt("a_hit");
            t.aSp = rs.getInt("a_sp");
            t.aStr = rs.getInt("a_str");
            t.aDex = rs.getInt("a_dex");
            t.aCon = rs.getInt("a_con");
            t.aInt = rs.getInt("a_int");

            gemTemplates.put(t.itemId, t);
         }
      } catch (SQLException e) {
         log.log(Level.SEVERE, "Failed loading dark_gem_template: " + e.getMessage(), e);
      } finally {
         SQLUtil.a(rs, pstm, con);
      }
   }

   public GemTemplate getGemTemplate(int itemId) {
      return gemTemplates.get(itemId);
   }

   public boolean isGem(int itemId) {
      return gemTemplates.containsKey(itemId);
   }

   public boolean isFlawlessGem(int itemId) {
      return itemId == 50060;
   }
}
