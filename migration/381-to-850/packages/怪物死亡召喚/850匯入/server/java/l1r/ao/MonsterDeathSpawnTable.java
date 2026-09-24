package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

/**
 * 怪物死亡召喚規則表快取管理器 (w_monster_death_spawn)
 */
public class MonsterDeathSpawnTable {
   private static final Logger log = Logger.getLogger(MonsterDeathSpawnTable.class.getName());
   private static MonsterDeathSpawnTable instance;

   private final Map<Integer, DeathSpawnRule> rules = new HashMap<>();

   public static class DeathSpawnRule {
      public int id;
      public int deadNpcId;
      public String note;
      public int spawnNpcId;
      public int spawnDurationMin;
      public String deathTalk;
      public int teleX;
      public int teleY;
      public int teleMapId;
      public boolean isBoss;
      public int triggerChanceX10;      // 10 = 1.0%, 5 = 0.5%
      public int strengthPctPerSpawn;   // 150 = +1.5%
      public int maxStrengthPct;        // 10000 = +100%
      public int dropBonusPctPerSpawn;  // 1 = +1%
   }

   public static MonsterDeathSpawnTable getInstance() {
      if (instance == null) {
         instance = new MonsterDeathSpawnTable();
      }
      return instance;
   }

   private MonsterDeathSpawnTable() {
      load();
   }

   public void load() {
      rules.clear();
      Connection conn = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;
      try {
         conn = DatabaseFactory.a().b();
         pstm = conn.prepareStatement("SELECT * FROM w_monster_death_spawn");
         rs = pstm.executeQuery();
         while (rs.next()) {
            DeathSpawnRule rule = new DeathSpawnRule();
            rule.id = rs.getInt("id");
            rule.deadNpcId = rs.getInt("dead_npc_id");
            rule.note = rs.getString("note");
            rule.spawnNpcId = rs.getInt("spawn_npc_id");
            rule.spawnDurationMin = rs.getInt("spawn_duration_min");
            rule.deathTalk = rs.getString("death_talk");
            rule.teleX = rs.getInt("tele_x");
            rule.teleY = rs.getInt("tele_y");
            rule.teleMapId = rs.getInt("tele_mapid");
            rule.isBoss = rs.getInt("is_boss") == 1;
            rule.triggerChanceX10 = rs.getInt("trigger_chance_x10");
            rule.strengthPctPerSpawn = rs.getInt("strength_pct_per_spawn");
            rule.maxStrengthPct = rs.getInt("max_strength_pct");
            rule.dropBonusPctPerSpawn = rs.getInt("drop_bonus_pct_per_spawn");

            rules.put(rule.deadNpcId, rule);
         }
      } catch (SQLException e) {
         log.log(Level.SEVERE, "Failed loading w_monster_death_spawn: " + e.getLocalizedMessage(), e);
      } finally {
         SQLUtil.a(rs, pstm, conn);
      }
   }

   public DeathSpawnRule findRule(int deadNpcId, boolean isBoss) {
      if (rules.containsKey(deadNpcId)) {
         return rules.get(deadNpcId);
      }
      int fallbackKey = isBoss ? -2 : -1;
      if (rules.containsKey(fallbackKey)) {
         return rules.get(fallbackKey);
      }

      // 記憶體中最後備援預設規則 (若未灌DB資料)
      DeathSpawnRule def = new DeathSpawnRule();
      def.deadNpcId = fallbackKey;
      def.spawnNpcId = 0;
      def.spawnDurationMin = 0;
      def.deathTalk = isBoss ? "愚蠢的凡人，這只是我力量的一部分！" : "我...還會再站起來的！";
      def.teleX = 0;
      def.teleY = 0;
      def.teleMapId = -1;
      def.isBoss = isBoss;
      def.triggerChanceX10 = isBoss ? 5 : 10;   // BOSS 0.5%, 小怪 1%
      def.strengthPctPerSpawn = 150;            // 每次 +1.5%
      def.maxStrengthPct = 10000;               // 上限 100%
      def.dropBonusPctPerSpawn = 1;             // 每次掉落 +1%
      return def;
   }
}
