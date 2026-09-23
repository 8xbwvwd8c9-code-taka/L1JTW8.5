package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class CharacterMobsWeekTable {
   private static final Logger a = Logger.getLogger(CharacterMobsWeekTable.class.getName());
   private static CharacterMobsWeekTable b;

   public static CharacterMobsWeekTable a() {
      if (b == null) {
         b = new CharacterMobsWeekTable();
      }

      return b;
   }

   public void a(L1PcInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("SELECT * FROM character_mobs_week WHERE login=?");
         var3.setString(1, var1.bc());
         var4 = var3.executeQuery();
         if (!var4.next()) {
            return;
         }

         String[] var5 = var4.getString("numbers").split(",");
         if (var5.length >= 9) {
            int[][] var6 = new int[var5.length][4];
            String[] var7 = var4.getString("counts").split(",");
            String[] var8 = var4.getString("kills").split(",");
            String[] var9 = var4.getString("states").split(",");

            for (int var10 = 0; var10 < var5.length; var10++) {
               var6[var10][0] = Integer.parseInt(var5[var10]);
               var6[var10][1] = Integer.parseInt(var7[var10]);
               var6[var10][2] = Integer.parseInt(var8[var10]);
               var6[var10][3] = Integer.parseInt(var9[var10]);
            }

            var1.a(var6);
            return;
         }
      } catch (Exception var14) {
         a.log(Level.SEVERE, var14.getLocalizedMessage(), var14);
         return;
      } finally {
         SQLUtil.a(var4, var3, var2);
      }
   }

   public void b(L1PcInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("INSERT INTO character_mobs_week SET login=?, numbers=?, counts=?, kills=?, states=?");
         String var4 = "";
         String var5 = "";
         String var6 = "";
         String var7 = "";

         for (int var8 = 0; var8 < var1.dY().length; var8++) {
            var4 = var4 + var1.dY()[var8][0] + ",";
            var5 = var5 + var1.dY()[var8][1] + ",";
            var6 = var6 + var1.dY()[var8][2] + ",";
            var7 = var7 + var1.dY()[var8][3] + ",";
         }

         var3.setString(1, var1.bc());
         var3.setString(2, var4);
         var3.setString(3, var5);
         var3.setString(4, var6);
         var3.setString(5, var7);
         var3.execute();
      } catch (SQLException var12) {
         a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void c(L1PcInstance var1) {
      if (var1.dY() != null) {
         Connection var2 = null;
         PreparedStatement var3 = null;

         try {
            var2 = DatabaseFactory.a().b();
            var3 = var2.prepareStatement("INSERT INTO character_mobs_week (login,numbers,counts,kills,states) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE numbers=VALUES(numbers), counts=VALUES(counts), kills=VALUES(kills), states=VALUES(states)");
            String var4 = "";
            String var5 = "";
            String var6 = "";
            String var7 = "";

            for (int var8 = 0; var8 < var1.dY().length; var8++) {
               var4 = var4 + var1.dY()[var8][0] + ",";
               var5 = var5 + var1.dY()[var8][1] + ",";
               var6 = var6 + var1.dY()[var8][2] + ",";
               var7 = var7 + var1.dY()[var8][3] + ",";
            }

            var3.setString(1, var1.bc());
            var3.setString(2, var4);
            var3.setString(3, var5);
            var3.setString(4, var6);
            var3.setString(5, var7);
            var3.execute();
         } catch (SQLException var12) {
            a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
         } finally {
            SQLUtil.a(var3);
            SQLUtil.a(var2);
         }
      }
   }

   public void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("DELETE FROM character_mobs_week");
         var2.execute();
      } catch (SQLException var7) {
         a.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
      } finally {
         SQLUtil.a(var2);
         SQLUtil.a(var1);
      }
   }
}
