package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class MobQuestWeekTable {
   private static final Logger a = Logger.getLogger(MobQuestWeekTable.class.getName());
   private static MobQuestWeekTable b;
   private final ArrayList<MobQuestWeekTable.a> c = new ArrayList<>();

   public static MobQuestWeekTable a() {
      if (b == null) {
         b = new MobQuestWeekTable();
      }

      return b;
   }

   private MobQuestWeekTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM mob_quest_week");
         var3 = var2.executeQuery();

         while (var3.next()) {
            MobQuestWeekTable.a var4 = new MobQuestWeekTable.a();
            var4.a = var3.getInt("mob_number");
            var4.b = var3.getInt("count");
            this.c.add(var4);
         }
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public int[][] b() {
      int[][] var1 = new int[9][4];
      ArrayList var2 = new ArrayList<>();

      while (var2.size() < 9) {
         MobQuestWeekTable.a var3 = this.c.get(Random.a(this.c.size()));
         if (!var2.contains(var3)) {
            var2.add(var3);
         }
      }

      for (int var5 = 0; var5 < var1.length; var5++) {
         MobQuestWeekTable.a var4 = var2.get(var5);
         var1[var5][0] = var4.a;
         var1[var5][1] = var4.b;
         var1[var5][2] = 0;
         var1[var5][3] = 1;
      }

      return var1;
   }

   public class a {
      public int a;
      public int b;
   }
}
