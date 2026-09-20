package l1r.as;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ao.CastleTable;
import l1r.ao.ClanTable;
import l1r.ao.DoorTable;
import l1r.ap.L1CrownInstance;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1KeeperInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1TowerInstance;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Character;
import l1r.aq.L1Clan;
import l1r.aq.L1Object;
import l1r.aq.L1SpawnWar;
import l1r.aq.L1Teleport;
import l1r.aq.L1War;
import l1r.aq.L1World;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Castle;
import l1r.bi.GeneralThreadPool;
import l1r.l1j.server.Config;

public class L1CastleWar {
   private static final Logger a = Logger.getLogger(L1CastleWar.class.getName());
   private static L1CastleWar b;

   private L1CastleWar() {
      int var1 = 0;

      for (L1Castle var2 : CastleTable.a().c().values()) {
         if (var2.g() != 0) {
            int var4 = (int)(var2.c().getTimeInMillis() / 1000L);
            if (var1 == 0 || var4 < var1) {
               var1 = var4;
            }
         }
      }

      L1World.a().b[3] = var1;
      GeneralThreadPool.a().a(new L1CastleWar.L1R_a(null), 1000L, 1000L);
   }

   public static L1CastleWar a() {
      if (b == null) {
         b = new L1CastleWar();
      }

      return b;
   }

   public boolean a(int var1) {
      L1Castle var2 = CastleTable.a().a(var1);
      return var2 == null ? false : var2.j();
   }

   public boolean a(L1Character var1) {
      int var2 = L1CastleLocation.a(var1);
      return var2 != 0 && this.a(var2);
   }

   public void a(L1PcInstance var1) {
      ArrayList var2 = new ArrayList<>();

      for (L1Castle var3 : CastleTable.a().c().values()) {
         if (var3.j()) {
            L1Clan var5 = ClanTable.a().c("安安妳好再見_" + var3.a());
            int var6 = var3.g();
            if (var6 > 0) {
               var5 = ClanTable.a().a(var6);
            }

            var2.add(var5.f());
         }
      }

      if (!var2.isEmpty()) {
         var1.a(new S_PacketBox(80, var2.toArray()));
      }
   }

   private void b(L1Castle var1) {
      var1.a(true);
      L1SpawnWar.a().c(var1.a());
      if (var1.g() == 0) {
         L1Clan var2 = new L1Clan();
         var2.c(IdFactory.a().c());
         var2.e("安安妳好再見_" + var1.a());
         var2.g(var1.a());
         ClanTable.a().a(var2);
         L1SpawnWar.a().d(var1.a());
      } else {
         L1Clan var6 = new L1Clan();
         var6.c(IdFactory.a().c());
         var6.e("安安妳好再見_" + var1.a());
         ClanTable.a().a(var6);
         L1SpawnWar.a().e(var1.a());

         for (L1Clan var3 : ClanTable.a().b().values()) {
            if (var3.m() == var1.a()) {
               L1War var5 = L1World.a().c(var3.f());
               if (var5 == null) {
                  new L1War(1, var6.f(), var3.f());
               } else {
                  var5.a(var6);
               }
               break;
            }
         }

         L1World.a().a(new S_ProtoBuffers(102, 16700 + var1.a(), "$16304"));
      }

      L1DoorInstance[] var14;
      int var12 = (var14 = DoorTable.b().c()).length;

      for (int var10 = 0; var10 < var12; var10++) {
         L1DoorInstance var7 = var14[var10];
         if (L1CastleLocation.a(var1.a(), var7)) {
            var7.h();
         }
      }

      L1World.a().a(new S_SystemMessage("\\aL" + var1.b() + "的攻城戰開始。"));

      for (L1PcInstance var8 : L1World.a().c()) {
         if (!var8.l() && L1CastleLocation.a(var1.a(), var8)) {
            L1Clan var13 = ClanTable.a().a(var8.aF());
            if (var13 == null || var13.m() != var1.a()) {
               int[] var15 = L1CastleLocation.e(var1.a());
               L1Teleport.a(var8, var15[0], var15[1], var15[2], 5, true);
            }
         }
      }

      SimpleDateFormat var9 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      System.out.println("[攻城戰開始]" + var1.b() + "..(結束時間:" + var9.format(var1.d().getTime()) + ")");
   }

   public void a(L1Castle var1) {
      if (var1.j()) {
         var1.a(false);
         L1World.a().a(new S_SystemMessage("\\aL" + var1.b() + "的攻城戰結束。"));
         this.c(var1);

         for (L1Object var2 : L1World.a().b()) {
            if (var2 instanceof L1NpcInstance) {
               L1NpcInstance var4 = (L1NpcInstance)var2;
               if (var4.z() == 81122 && L1CastleLocation.a(var1.a(), var4)) {
                  var4.aa_();
               }
            }

            if (var2 instanceof L1CrownInstance) {
               L1CrownInstance var8 = (L1CrownInstance)var2;
               if (L1CastleLocation.a(var1.a(), var8)) {
                  var8.aa_();
               }
            }

            if (var2 instanceof L1TowerInstance) {
               L1TowerInstance var9 = (L1TowerInstance)var2;
               if (L1CastleLocation.a(var1.a(), var9)) {
                  var9.aa_();
               }
            }

            if (var2 instanceof L1KeeperInstance) {
               L1KeeperInstance var10 = (L1KeeperInstance)var2;
               if (L1CastleLocation.a(var1.a(), var10)) {
                  var10.aa_();
               }
            }
         }

         L1SpawnWar.a().a(var1.a());
         L1DoorInstance[] var5;
         int var11 = (var5 = DoorTable.b().c()).length;

         for (int var7 = 0; var7 < var11; var7++) {
            L1DoorInstance var6 = var5[var7];
            if (L1CastleLocation.a(var1.a(), var6)) {
               var6.h();
            }
         }

         ClanTable.a().a("安安妳好再見_" + var1.a());
         System.out.println("[攻城戰結束]" + var1.b());
      } else {
         this.c(var1);
      }
   }

   private void c(L1Castle var1) {
      var1.c().add(Config.ak, Config.aj);
      var1.a(10);
      CastleTable.a().a(var1);
   }

   private class L1R_a extends TimerTask {
      private L1R_a() {
      }

      @Override
      public void run() {
         try {
            Calendar var1 = Calendar.getInstance();

            for (L1Castle var2 : CastleTable.a().c().values()) {
               if (!var2.j() && var1.after(var2.c()) && var1.before(var2.d())) {
                  L1CastleWar.this.b(var2);
               } else if (var1.after(var2.d())) {
                  L1CastleWar.this.a(var2);
               }
            }
         } catch (Exception var4) {
            L1CastleWar.a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
         }
      }

      // $VF: synthetic method
      L1R_a(L1CastleWar.L1R_a var2) {
         this();
      }
   }
}
