package l1r.ao;

import a.g;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.am.MonsterListReader;
import l1r.an.PBMessageALL6;
import l1r.ap.L1PcInstance;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class CharacterMobsTable {
   private static final Logger a = Logger.getLogger(CharacterMobsTable.class.getName());
   private static CharacterMobsTable b;

   public static CharacterMobsTable a() {
      if (b == null) {
         b = new CharacterMobsTable();
      }

      return b;
   }

   private void a(String var1, int[] var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("INSERT INTO character_mobs SET login=?, data=?");
         var4.setString(1, var1);
         PBMessageALL6.L1R_e.L1R_a var5 = PBMessageALL6.L1R_e.aa();

         for (int var6 = 0; var6 < var2.length; var6++) {
            var5.b(var2[var6]);
         }

         var4.setBytes(2, var5.M().g());
         var4.execute();
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var3);
      }
   }

   public void a(L1PcInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("INSERT INTO character_mobs (login,data) VALUES (?,?) ON DUPLICATE KEY UPDATE data=VALUES(data)");
         PBMessageALL6.L1R_e.L1R_a var4 = PBMessageALL6.L1R_e.aa();

         for (int var5 = 0; var5 < var1.dQ().length; var5++) {
            var4.b(var1.dQ()[var5]);
         }

         byte[] var12 = new byte[var1.dR().length];

         for (int var6 = 0; var6 < var12.length; var6++) {
            var12[var6] = (byte)var1.dR()[var6];
         }

         var4.e(g.a(var12));
         var3.setString(1, var1.bc());
         var3.setBytes(2, var4.M().g());
         var3.execute();
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void b(L1PcInstance var1) {
      int[] var2 = new int[MonsterListReader.a().b()];
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("SELECT * FROM character_mobs WHERE login=?");
         var4.setString(1, var1.bc());
         var5 = var4.executeQuery();
         if (!var5.next()) {
            this.a(var1.bc(), var2);
         } else {
            byte[] var6 = var5.getBytes("data");
            PBMessageALL6.L1R_e var7 = PBMessageALL6.L1R_e.a(var6);

            for (int var8 = 0; var8 < var2.length && var8 < var7.p(); var8++) {
               var2[var8] = var7.a(var8);
            }

            byte[] var15 = var7.t().e();

            for (int var9 = 0; var9 < var15.length; var9++) {
               var1.dR()[var9] = var15[var9];
            }
         }

         var1.a(var2);
      } catch (Exception var13) {
         a.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
      } finally {
         SQLUtil.a(var5, var4, var3);
      }
   }
}
