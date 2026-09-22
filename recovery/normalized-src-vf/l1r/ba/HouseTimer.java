package l1r.ba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
      if (var3 < 100000 || var3 > 2000000000) {
         a.log(Level.WARNING, "Invalid house auction price; settlement blocked. houseId={0}, price={1}", new Object[]{var2, var3});
         return;
      }

      L1Clan var17 = this.findOwnerClan(var2);
      L1Clan var18 = var6 == 0 ? null : this.findEligibleBidderClan(var6);

      if (var6 != 0 && var18 == null) {
         if (var3 > 0 && !this.refundBidder(var6, var3)) {
            return;
         }

         String var19 = var1.n();
         int var20 = var1.o();
         var1.d("");
         var1.f(0);
         if (!this.a((L1Clan)null, var1)) {
            var1.d(var19);
            var1.f(var20);
            if (var3 > 0) {
               this.reclaimBidderRefund(var6, var3);
            }
         }
         return;
      }

      if (var4 != 0 && var6 != 0) {
         int var8 = (int)(var3 * 0.9);
         if (var8 <= 0 || !this.refundBidder(var4, var8)) {
            return;
         }

         int var9 = var17 == null ? 0 : var17.n();
         int var10 = var18.n();
         boolean var11 = var1.g();
         Timestamp var12 = var1.i();

         if (var17 != null) {
            var17.h(0);
         }
         var18.h(var2);
         var1.a(false);
         var1.a(new Timestamp(System.currentTimeMillis() + Config.an * 24 * 60 * 60 * 1000L));

         if (!this.settleAtomic(var17, var18, var1)) {
            if (var17 != null) {
               var17.h(var9);
            }
            var18.h(var10);
            var1.a(var11);
            var1.a(var12);
            this.reclaimBidderRefund(var4, var8);
            return;
         }

         L1PcInstance var13 = (L1PcInstance)L1World.a().a(var4);
         if (var13 != null) {
            var13.a(new S_ServerMessage(527, String.valueOf(var8)));
         }

         L1PcInstance var14 = (L1PcInstance)L1World.a().a(var6);
         if (var14 != null) {
            var14.a(new S_ServerMessage(524, String.valueOf(var3), var5));
         }
         return;
      }

      if (var4 == 0 && var6 != 0) {
         int var15 = var18.n();
         boolean var16 = var1.g();
         Timestamp var21 = var1.i();

         var18.h(var2);
         var1.a(false);
         var1.a(new Timestamp(System.currentTimeMillis() + Config.an * 24 * 60 * 60 * 1000L));

         if (!this.settleAtomic(null, var18, var1)) {
            var18.h(var15);
            var1.a(var16);
            var1.a(var21);
            return;
         }

         L1PcInstance var22 = (L1PcInstance)L1World.a().a(var6);
         if (var22 != null) {
            var22.a(new S_ServerMessage(524, String.valueOf(var3), var5));
         }
         return;
      }

      if (var4 != 0) {
         boolean var23 = var1.g();
         Timestamp var24 = var1.i();
         var1.a(false);
         var1.a(new Timestamp(System.currentTimeMillis() + Config.an * 24 * 60 * 60 * 1000L));

         if (!this.settleAtomic(null, null, var1)) {
            var1.a(var23);
            var1.a(var24);
            return;
         }

         L1PcInstance var25 = (L1PcInstance)L1World.a().a(var4);
         if (var25 != null) {
            var25.a(new S_ServerMessage(528));
         }
         return;
      }

      Timestamp var26 = var1.j();
      int var27 = var1.k();
      var1.b(new Timestamp(System.currentTimeMillis() + 432000000L));
      var1.d(100000);
      if (!this.settleAtomic(null, null, var1)) {
         var1.b(var26);
         var1.d(var27);
      }
   }

   private L1Clan findOwnerClan(int var1) {
      for (L1Clan var2 : ClanTable.a().b().values()) {
         if (var2.n() == var1) {
            return var2;
         }
      }

      return null;
   }

   private boolean settleAtomic(L1Clan var1, L1Clan var2, L1House var3) {
      Connection var4 = null;
      PreparedStatement var5 = null;
      PreparedStatement var6 = null;
      PreparedStatement var7 = null;

      try {
         var4 = l1r.l1j.server.DatabaseFactory.a().b();
         var4.setAutoCommit(false);

         if (var1 != null) {
            var5 = var4.prepareStatement("UPDATE clan_data SET hashouse=? WHERE clan_name=?");
            var5.setInt(1, var1.n());
            var5.setString(2, var1.f());
            if (var5.executeUpdate() != 1) {
               throw new SQLException("BUG-850-145 old clan ownership update failed");
            }
         }

         if (var2 != null) {
            var6 = var4.prepareStatement("UPDATE clan_data SET hashouse=? WHERE clan_name=?");
            var6.setInt(1, var2.n());
            var6.setString(2, var2.f());
            if (var6.executeUpdate() != 1) {
               throw new SQLException("BUG-850-145 bidder clan ownership update failed");
            }
         }

         var7 = var4.prepareStatement(
            "UPDATE house SET house_name=?, house_area=?, location=?, keeper_id=?, is_on_sale=?, is_purchase_basement=?, tax_deadline=?, deadline=?, price=?, old_owner=?, old_owner_id=?, bidder=?, bidder_id=? WHERE house_id=?"
         );
         var7.setString(1, var3.c());
         var7.setInt(2, var3.d());
         var7.setString(3, var3.e());
         var7.setInt(4, var3.f());
         var7.setBoolean(5, var3.g());
         var7.setBoolean(6, var3.h());
         var7.setTimestamp(7, var3.i());
         var7.setTimestamp(8, var3.j());
         var7.setInt(9, var3.k());
         var7.setString(10, var3.l());
         var7.setInt(11, var3.m());
         var7.setString(12, var3.n());
         var7.setInt(13, var3.o());
         var7.setInt(14, var3.b());
         if (var7.executeUpdate() != 1) {
            throw new SQLException("BUG-850-145 house settlement update failed");
         }

         var4.commit();
         return true;
      } catch (SQLException var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
         if (var4 != null) {
            try {
               var4.rollback();
            } catch (SQLException var10) {
               a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
            }
         }
         return false;
      } finally {
         if (var7 != null) {
            try {
               var7.close();
            } catch (SQLException ignored) {
            }
         }
         if (var6 != null) {
            try {
               var6.close();
            } catch (SQLException ignored) {
            }
         }
         if (var5 != null) {
            try {
               var5.close();
            } catch (SQLException ignored) {
            }
         }
         if (var4 != null) {
            try {
               var4.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
            try {
               var4.close();
            } catch (SQLException ignored) {
            }
         }
      }
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

   private L1Clan findEligibleBidderClan(int var1) {
      for (L1Clan var2 : ClanTable.a().b().values()) {
         if (var2.k() == var1 && var2.n() == 0) {
            return var2;
         }
      }

      return null;
   }

   private boolean refundBidder(int var1, int var2) {
      if (var2 <= 0) {
         return false;
      }

      L1PcInstance var3 = (L1PcInstance)L1World.a().a(var1);
      if (var3 != null) {
         return ItemTable.a(var3, 40308, var2) != null;
      }

      L1ItemInstance var4 = ItemTable.a().b(40308);
      if (var4 == null) {
         return false;
      }

      var4.e(var2);
      try {
         CharacterItemTable.a().a(var1, var4);
         return true;
      } catch (Exception var6) {
         a.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
         return false;
      }
   }

   private boolean reclaimBidderRefund(int var1, int var2) {
      if (var2 <= 0) {
         return true;
      }

      L1PcInstance var3 = (L1PcInstance)L1World.a().a(var1);
      if (var3 != null) {
         return var3.j().b(40308, var2);
      }

      Connection var4 = null;
      PreparedStatement var5 = null;
      ResultSet var6 = null;

      try {
         var4 = l1r.l1j.server.DatabaseFactory.a().b();
         var5 = var4.prepareStatement("SELECT id,count FROM character_items WHERE char_id=? AND item_id=40308 ORDER BY id DESC LIMIT 1");
         var5.setInt(1, var1);
         var6 = var5.executeQuery();
         if (!var6.next()) {
            return false;
         }

         int var7 = var6.getInt("id");
         int var8 = var6.getInt("count");
         if (var8 < var2) {
            return false;
         }

         var6.close();
         var6 = null;
         var5.close();
         var5 = null;

         if (var8 == var2) {
            var5 = var4.prepareStatement("DELETE FROM character_items WHERE id=?");
            var5.setInt(1, var7);
         } else {
            var5 = var4.prepareStatement("UPDATE character_items SET count=? WHERE id=?");
            var5.setInt(1, var8 - var2);
            var5.setInt(2, var7);
         }

         return var5.executeUpdate() == 1;
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
         return false;
      } finally {
         if (var6 != null) {
            try {
               var6.close();
            } catch (SQLException ignored) {
            }
         }
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
