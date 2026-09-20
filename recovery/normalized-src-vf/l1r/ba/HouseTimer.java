package l1r.ba;

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
      GeneralThreadPool.a().a(new HouseTimer.a(null), 100L, 600000L);
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

      for (L1Clan var3 : ClanTable.a().b().values()) {
         if (var3.n() == var2) {
            var3.h(0);
            ClanTable.a().b(var3);
         }
      }

      Timestamp var5 = new Timestamp(System.currentTimeMillis() + 432000000L);
      var1.b(var5);
      var1.d(100000);
      var1.c("");
      var1.e(0);
      var1.d("");
      var1.f(0);
      var1.a(true);
      var1.b(false);
      Timestamp var6 = new Timestamp(System.currentTimeMillis() + Config.an * 24 * 60 * 60 * 1000L);
      var1.a(var6);
      var1.a();
      HouseTable.a().a(var1);
   }

   private class a extends TimerTask {
      private a() {
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
      a(HouseTimer.a var2) {
         this();
      }
   }
}
