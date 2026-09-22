package l1r.ba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.CharacterItemTable;
import l1r.ao.ClanTable;
import l1r.ao.HouseTable;
import l1r.ao.ItemTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bh.L1House;
import l1r.bi.GeneralThreadPool;
import l1r.l1j.server.Config;

public class HouseTimer {
   private static final Logger a = Logger.getLogger(HouseTimer.class.getName());
   private static HouseTimer b;

   public static HouseTimer a() {
      if (b == null) {
         b = new HouseTimer();
      }

      return b;
   }

   public HouseTimer() {
      GeneralThreadPool.a().a(new HouseTimer.L1R_a(null), 100L, 600000L);
   }

   private void c() throws Exception {
      Timestamp var1 = new Timestamp(System.currentTimeMillis());

      for (L1House var2 : HouseTable.a().c().values()) {
         if (var2.g() && var2.j().before(var1)) {
            this.b(var2);
         }
      }
   }

   private void b(L1House var1) throws Exception {
      int var2 = var1.b();
      int var3 = var1.k();
      int var4 = var1.m();
      String var5 = var1.n();
      int var6 = var1.o();
      if (var4 != 0 && var6 != 0) {
         L1PcInstance var12 = (L1PcInstance)L1World.a().a(var4);
         int var8 = (int)(var3 * 0.9);
         if (var12 != null) {
            ItemTable.a(var12, 40308, var8);
            var12.a(new S_ServerMessage(527, String.valueOf(var8)));
         } else {
            L1ItemInstance var9 = ItemTable.a().b(40308);
            var9.e(var8);
            CharacterItemTable.a().a(var4, var9);
         }

         L1PcInstance var13 = (L1PcInstance)L1World.a().a(var6);
         if (var13 != null) {
            var13.a(new S_ServerMessage(524, String.valueOf(var3), var5));
         }

         this.a(var2);
         this.a(var2, var6);
         this.b(var2);
      } else if (var4 == 0 && var6 != 0) {
         L1PcInstance var11 = (L1PcInstance)L1World.a().a(var6);
         if (var11 != null) {
            var11.a(new S_ServerMessage(524, String.valueOf(var3), var5));
         }

         this.a(var2, var6);
         this.b(var2);
      } else if (var4 != 0 && var6 == 0) {
         L1PcInstance var10 = (L1PcInstance)L1World.a().a(var4);
         if (var10 != null) {
            var10.a(new S_ServerMessage(528));
         }

         this.b(var2);
      } else if (var4 == 0 && var6 == 0) {
         Timestamp var7 = new Timestamp(System.currentTimeMillis() + 432000000L);
         var1.b(var7);
         var1.d(100000);
         HouseTable.a().a(var1);
      }
   }

   private void a(int var1) {
      for (L1Clan var2 : ClanTable.a().b().values()) {
         if (var2.n() == var1) {
            var2.h(0);
            ClanTable.a().b(var2);
         }
      }
   }

   private void a(int var1, int var2) {
      for (L1Clan var3 : ClanTable.a().b().values()) {
         if (var3.k() == var2) {
            var3.h(var1);
            ClanTable.a().b(var3);
            break;
         }
      }
   }

   private void b(int var1) {
      L1House var2 = HouseTable.a().a(var1);
      var2.a(false);
      Timestamp var3 = new Timestamp(System.currentTimeMillis() + Config.an * 24 * 60 * 60 * 1000L);
      var2.a(var3);
      HouseTable.a().a(var2);
   }

   private void d() {
      Timestamp var1 = new Timestamp(System.currentTimeMillis());

      for (L1House var2 : HouseTable.a().c().values()) {
         if (!var2.g() && var2.i().before(var1) && var2.o() > 0) {
            this.a(var2);
         }
      }
   }

   public void a(L1House var1) {
      int var2 = var1.b();
      L1Clan var3 = null;

      for (L1Clan var4 : ClanTable.a().b().values()) {
         if (var4.n() == var2) {
            var3 = var4;
            break;
         }
      }

      Timestamp var5 = var1.j();
      int var6 = var1.k();
      String var7 = var1.l();
      int var8 = var1.m();
      String var9 = var1.n();
      int var10 = var1.o();
      boolean var11 = var1.g();
      boolean var12 = var1.h();
      Timestamp var13 = var1.i();
      int var14 = var3 == null ? 0 : var3.n();

      if (var3 != null) {
         var3.h(0);
      }

      var1.b(new Timestamp(System.currentTimeMillis() + 432000000L));
      var1.d(100000);
      var1.c("");
      var1.e(0);
      var1.d("");
      var1.f(0);
      var1.a(true);
      var1.b(false);
      var1.a(new Timestamp(System.currentTimeMillis() + Config.an * 24 * 60 * 60 * 1000L));
      var1.a();

      if (!this.a(var3, var1)) {
         if (var3 != null) {
            var3.h(var14);
         }

         var1.b(var5);
         var1.d(var6);
         var1.c(var7);
         var1.e(var8);
         var1.d(var9);
         var1.f(var10);
         var1.a(var11);
         var1.b(var12);
         var1.a(var13);
      }
   }

   private boolean a(L1Clan var1, L1House var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;
      PreparedStatement var5 = null;

      try {
         var3 = l1r.l1j.server.DatabaseFactory.a().b();
         var3.setAutoCommit(false);
         if (var1 != null) {
            var4 = var3.prepareStatement("UPDATE clan_data SET hashouse=? WHERE clan_name=?");
            var4.setInt(1, var1.n());
            var4.setString(2, var1.f());
            if (var4.executeUpdate() != 1) {
               throw new SQLException("BUG-850-149 clan ownership update failed");
            }
         }

         var5 = var3.prepareStatement(
            "UPDATE house SET house_name=?, house_area=?, location=?, keeper_id=?, is_on_sale=?, is_purchase_basement=?, tax_deadline=?, deadline=?, price=?, old_owner=?, old_owner_id=?, bidder=?, bidder_id=? WHERE house_id=?"
         );
         var5.setString(1, var2.c());
         var5.setInt(2, var2.d());
         var5.setString(3, var2.e());
         var5.setInt(4, var2.f());
         var5.setBoolean(5, var2.g());
         var5.setBoolean(6, var2.h());
         var5.setTimestamp(7, var2.i());
         var5.setTimestamp(8, var2.j());
         var5.setInt(9, var2.k());
         var5.setString(10, var2.l());
         var5.setInt(11, var2.m());
         var5.setString(12, var2.n());
         var5.setInt(13, var2.o());
         var5.setInt(14, var2.b());
         if (var5.executeUpdate() != 1) {
            throw new SQLException("BUG-850-149 house foreclosure update failed");
         }

         var3.commit();
         return true;
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
         if (var3 != null) {
            try {
               var3.rollback();
            } catch (SQLException var8) {
               a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
            }
         }
         return false;
      } finally {
         if (var5 != null) {
            try {
               var5.close();
            } catch (SQLException ignored) {
            }
         }
         if (var4 != null) {
            try {
               var4.close();
            } catch (SQLException ignored) {
            }
         }
         if (var3 != null) {
            try {
               var3.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
            try {
               var3.close();
            } catch (SQLException ignored) {
            }
         }
      }
   }

   private class L1R_a extends TimerTask {
      private L1R_a() {
      }

      @Override
      public void run() {
         try {
            HouseTimer.this.c();
            HouseTimer.this.d();
         } catch (Exception var2) {
            HouseTimer.a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }

      // $VF: synthetic method
      L1R_a(HouseTimer.L1R_a var2) {
         this();
      }
   }
}
