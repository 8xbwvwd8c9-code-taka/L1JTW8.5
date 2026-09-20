package l1r.ba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.TownTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.at.L1GameTime;
import l1r.at.L1GameTimeAdapter;
import l1r.at.L1GameTimeClock;
import l1r.be.S_PacketBox;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class HomeTownTimer {
   private static final Logger a = Logger.getLogger(HomeTownTimer.class.getName());
   private static HomeTownTimer b;
   private static HomeTownTimer.L1R_a c;

   public static HomeTownTimer a() {
      if (b == null) {
         b = new HomeTownTimer();
      }

      return b;
   }

   private HomeTownTimer() {
      this.d();
   }

   private void d() {
      if (c == null) {
         c = new HomeTownTimer.L1R_a(null);
         L1GameTimeClock.a().a(c);
      }
   }

   private void a(L1GameTime var1) {
      Calendar var2 = var1.d();
      int var3 = var2.get(5);
      if (var3 == 25) {
         this.c();
      } else {
         this.b();
      }
   }

   public void b() {
      System.out.println("城鎮系統：開始處理每日事項");
      TownTable.a().d();
      TownTable.a().e();
      TownTable.a().b();
   }

   public void c() {
      System.out.println("城鎮系統：開始處理每月事項");
      L1World.a().b(true);
      Collection<L1PcInstance> var1 = L1World.a().c();

      for (L1PcInstance var2 : var1) {
         var2.I();
      }

      for (int var7 = 1; var7 <= 10; var7++) {
         String var9 = b(var7);
         if (var9 != null) {
            S_PacketBox var4 = new S_PacketBox(23, var9);

            for (L1PcInstance var5 : var1) {
               if (var5.bF() == var7) {
                  var5.aE(0);
                  var5.a(var4);
               }
            }
         }
      }

      TownTable.a().b();

      for (L1PcInstance var8 : var1) {
         if (var8.bF() == -1) {
            var8.aD(0);
         }

         var8.aE(0);
         var8.I();
      }

      e();
      L1World.a().b(false);
   }

   private static String b(int var0) {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;
      PreparedStatement var6 = null;
      ResultSet var7 = null;
      PreparedStatement var8 = null;
      PreparedStatement var9 = null;
      int var10 = 0;
      String var11 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT objid, char_name FROM characters WHERE HomeTownID = ? ORDER BY Contribution DESC");
         var2.setInt(1, var0);
         var3 = var2.executeQuery();
         if (var3.next()) {
            var10 = var3.getInt("objid");
            var11 = var3.getString("char_name");
         }

         double var12 = 0.0;
         var4 = var1.prepareStatement("SELECT SUM(Contribution) AS TotalContribution FROM characters WHERE HomeTownID = ?");
         var4.setInt(1, var0);
         var5 = var4.executeQuery();
         if (var5.next()) {
            var12 = var5.getInt("TotalContribution");
         }

         double var14 = 0.0;
         var6 = var1.prepareStatement("SELECT town_fix_tax FROM town WHERE town_id = ?");
         var6.setInt(1, var0);
         var7 = var6.executeQuery();
         if (var7.next()) {
            var14 = var7.getInt("town_fix_tax");
         }

         double var16 = 0.0;
         if (var12 != 0.0) {
            var16 = Math.floor(var14 / var12 * 100.0) / 100.0;
         }

         var8 = var1.prepareStatement("UPDATE characters SET Contribution = 0, Pay = Contribution * ? WHERE HomeTownID = ?");
         var8.setDouble(1, var16);
         var8.setInt(2, var0);
         var8.execute();
         var9 = var1.prepareStatement(
            "UPDATE town SET leader_id = ?, leader_name = ?, tax_rate = 0, tax_rate_reserved = 0, sales_money = 0, sales_money_yesterday = sales_money, town_tax = 0, town_fix_tax = 0 WHERE town_id = ?"
         );
         var9.setInt(1, var10);
         var9.setString(2, var11);
         var9.setInt(3, var0);
         var9.execute();
      } catch (SQLException var21) {
         a.log(Level.SEVERE, var21.getLocalizedMessage(), var21);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
         SQLUtil.a(var5);
         SQLUtil.a(var4);
         SQLUtil.a(var7);
         SQLUtil.a(var6);
         SQLUtil.a(var8);
         SQLUtil.a(var9);
         SQLUtil.a(var1);
      }

      return var11;
   }

   private static void e() {
      Connection var0 = null;
      PreparedStatement var1 = null;

      try {
         var0 = DatabaseFactory.a().b();
         var1 = var0.prepareStatement("UPDATE characters SET HomeTownID = 0 WHERE HomeTownID = -1");
         var1.execute();
      } catch (SQLException var6) {
         a.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
      } finally {
         SQLUtil.a(var1);
         SQLUtil.a(var0);
      }
   }

   public static int a(int var0) {
      Connection var1 = null;
      PreparedStatement var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;
      int var5 = 0;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT Pay FROM characters WHERE objid = ? FOR UPDATE");
         var2.setInt(1, var0);
         var4 = var2.executeQuery();
         if (var4.next()) {
            var5 = var4.getInt("Pay");
         }

         var3 = var1.prepareStatement("UPDATE characters SET Pay = 0 WHERE objid = ?");
         var3.setInt(1, var0);
         var3.execute();
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var4, var2, var1);
      }

      return var5;
   }

   private class L1R_a extends L1GameTimeAdapter {
      private L1R_a() {
      }

      @Override
      public void a(L1GameTime var1) {
         HomeTownTimer.this.a(var1);
      }

      // $VF: synthetic method
      L1R_a(HomeTownTimer.L1R_a var2) {
         this();
      }
   }
}
