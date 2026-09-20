package l1r.aq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class L1Quest {
   private static final Logger o = Logger.getLogger(L1Quest.class.getName());
   public static final int a = 1;
   public static final int b = 10;
   public static final int c = 11;
   public static final int d = 35;
   public static final int e = 36;
   public static final int f = 37;
   public static final int g = 38;
   public static final int h = 40;
   public static final int i = 41;
   public static final int j = 42;
   public static final int k = 43;
   public static final int l = 44;
   public static final int m = 45;
   public static final int n = 255;
   private final L1PcInstance p;
   private final HashMap<Integer, Integer> q = new HashMap<>();

   public L1Quest(L1PcInstance var1) {
      this.p = var1;
   }

   public void a() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM character_quests WHERE char_id=?");
         var2.setInt(1, this.p.fr());
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("quest_id");
            int var5 = var3.getInt("quest_step");
            this.q.put(var4, var5);
         }
      } catch (SQLException var9) {
         o.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public int a(int var1) {
      return !this.q.containsKey(var1) ? 0 : this.q.get(var1);
   }

   public void a(int var1, int var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;

      try {
         var3 = DatabaseFactory.a().b();
         if (this.q.containsKey(var1)) {
            var4 = var3.prepareStatement("UPDATE character_quests SET quest_step = ? WHERE char_id = ? AND quest_id = ?");
            var4.setInt(1, var2);
            var4.setInt(2, this.p.fr());
            var4.setInt(3, var1);
            var4.execute();
         } else {
            var4 = var3.prepareStatement("INSERT INTO character_quests SET char_id = ?, quest_id = ?, quest_step = ?");
            var4.setInt(1, this.p.fr());
            var4.setInt(2, var1);
            var4.setInt(3, var2);
            var4.execute();
         }
      } catch (SQLException var9) {
         o.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var3);
      }

      this.q.put(var1, var2);
   }

   public void b(int var1) {
      this.a(var1, 255);
   }

   public boolean c(int var1) {
      return this.a(var1) == 255;
   }
}
