package l1j.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.DatabaseFactory;
import l1j.server.utils.SQLUtil;

/**
 * D系列怪物菁英化配置、60詞綴與時段排程管理器
 */
public class EliteConfigTable {
   private static final Logger log = Logger.getLogger(EliteConfigTable.class.getName());
   private static EliteConfigTable instance;

   private final Map<String, String> configs = new HashMap<>();

   public static EliteConfigTable getInstance() {
      if (instance == null) {
         instance = new EliteConfigTable();
      }
      return instance;
   }

   private EliteConfigTable() {
      loadConfigs();
   }

   public void loadConfigs() {
      configs.clear();
      Connection con = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;

      try {
         con = DatabaseFactory.a().b();
         pstm = con.prepareStatement("SELECT `key`, `val` FROM `_config`");
         rs = pstm.executeQuery();

         while (rs.next()) {
            configs.put(rs.getString("key"), rs.getString("val"));
         }
      } catch (SQLException e) {
         log.log(Level.WARNING, "Load _config failed (table may not exist yet): " + e.getMessage());
      } finally {
         SQLUtil.a(rs, pstm, con);
      }
   }

   public boolean isEliteEnabled() {
      String val = configs.getOrDefault("EliteMonsterSwitch", "true");
      return "true".equalsIgnoreCase(val) && isWithinSchedule();
   }

   public boolean isWithinSchedule() {
      String schedule = configs.getOrDefault("EliteMonsterTimeSchedule", "00:00-24:00");
      if ("00:00-24:00".equals(schedule) || "all".equalsIgnoreCase(schedule)) {
         return true;
      }
      try {
         String[] parts = schedule.split("-");
         if (parts.length != 2) return true;
         SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
         String nowStr = sdf.format(new Date());
         return nowStr.compareTo(parts[0].trim()) >= 0 && nowStr.compareTo(parts[1].trim()) <= 0;
      } catch (Exception e) {
         return true;
      }
   }

   public double getHpMultiplier(int difficulty) {
      switch (difficulty) {
         case 1: // Hard
            return Double.parseDouble(configs.getOrDefault("EliteHardHpRate", "2.5"));
         case 2: // Nightmare
            return Double.parseDouble(configs.getOrDefault("EliteNightmareHpRate", "4.0"));
         case 3: // Hell
            return Double.parseDouble(configs.getOrDefault("EliteHellHpRate", "7.0"));
         default: // Normal
            return Double.parseDouble(configs.getOrDefault("EliteNormalHpRate", "1.5"));
      }
   }

   public double getDarkLootDropRate() {
      return Double.parseDouble(configs.getOrDefault("DarkLootDropRate", "1.0"));
   }
}
