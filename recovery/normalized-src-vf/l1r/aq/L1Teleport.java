package l1r.aq;

import java.util.HashSet;
import l1r.ao.ClanTable;
import l1r.ap.L1DollInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.as.L1ThebesBattle;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.be.S_DollPack;
import l1r.be.S_MapID;
import l1r.be.S_OwnCharPack;
import l1r.be.S_PacketBox;
import l1r.be.S_PetPack;
import l1r.be.S_Portal;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.be.S_SummonPack;
import l1r.be.S_Teleport;
import l1r.bi.Random;

public class L1Teleport {
   private static final int a = 169;

   public static void a(L1PcInstance var0, int var1, int var2, int var3, int var4, boolean var5) {
      a(var0, var1, var2, var3, var4, var5, true);
   }

   public static void a(L1PcInstance var0, int var1, int var2, int var3, int var4, boolean var5, boolean var6) {
      if (var0.aO() != 0) {
         L1Trade.b(var0);
      }

      var0.aI(var1);
      var0.aJ(var2);
      var0.aK(var3);
      var0.aL(var4);
      if (var5) {
         if (var6) {
            var0.a(new S_ProtoBuffers(145));
         } else {
            var0.a(new S_Teleport());
         }

         var0.a(new S_SkillSound(var0.fr(), 169));
         var0.b(new S_SkillSound(var0.fr(), 169));
         if (var6) {
            a(var0);
         }
      } else {
         var0.a(new S_Portal(var1, var2, var3));
      }
   }

   public static void a(L1PcInstance var0) {
      if (!var0.eX() && !var0.aR()) {
         if (var0.bB(230)) {
            var0.a(new S_ServerMessage(276));
         } else {
            int var1 = var0.cf();
            int var2 = var0.cg();
            int var3 = var0.ch();
            int var4 = var0.ci();
            L1Map var5 = L1WorldMap.b().a(var3);
            if (!var5.b(var1, var2) && !var0.l()) {
               var1 = var0.fs();
               var2 = var0.ft();
               var3 = var0.fp();
            }

            var0.d(true);
            if (var0.fp() != var3) {
               var0.y(false);
            }

            L1Clan var6 = ClanTable.a().a(var0.aF());
            if (var6 != null && var6.o() == var0.fr()) {
               var6.i(0);
            }

            L1World.a().a(var0, var3);
            var0.d(var1, var2, var3);
            var0.ct(var4);
            var0.a(new S_MapID(var0.fp(), var0.fq().g()));
            var0.a(new S_OwnCharPack(var0));
            var0.es();
            var0.i();
            var0.bA(32);
            var0.aQ(0);
            if (var3 != 1700 && var3 != 1703 && var0.j().m(21397)) {
               var0.a(new S_ServerMessage(123, "\\aG$22171"));
            }

            if (var3 != 6311 && !var0.l() && var0.j().m(413)) {
               var0.a(new S_ServerMessage(123, "\\aG$26512"));
            }

            HashSet<L1PcInstance> var7 = new HashSet<>();
            var7.add(var0);
            if (!var0.bN() && !var0.aA()) {
               for (L1PcInstance var8 : L1World.a().f(var0)) {
                  var8.d(var0);
                  var7.add(var8);
               }

               if (var0.fq().n()) {
                  for (L1NpcInstance var15 : var0.ek().values()) {
                     L1Location var10 = var0.fu().a(3, false);
                     int var11 = var10.f();
                     int var12 = var10.g();
                     if (var0.fp() >= 5125 && var0.fp() <= 5134) {
                        var11 = 32799 + Random.a(5) - 3;
                        var12 = 32864 + Random.a(5) - 3;
                     }

                     a(var15, var11, var12, var3, var4);
                     if (var15 instanceof L1SummonInstance) {
                        var0.a(new S_SummonPack((L1SummonInstance)var15, var0));
                     } else if (var15 instanceof L1PetInstance) {
                        var0.a(new S_PetPack((L1PetInstance)var15, var0));
                     }

                     for (L1PcInstance var13 : L1World.a().f(var15)) {
                        var13.d(var15);
                        var7.add(var13);
                     }
                  }
               }

               for (L1DollInstance var16 : var0.el().values()) {
                  L1Location var22 = var0.fu().a(3, false);
                  int var23 = var22.f();
                  int var24 = var22.g();
                  a(var16, var23, var24, var3, var4);
                  var0.a(new S_DollPack(var16));

                  for (L1PcInstance var25 : L1World.a().f(var16)) {
                     var25.d(var16);
                     var7.add(var25);
                  }
               }
            }

            for (L1PcInstance var17 : var7) {
               var17.h();
            }

            var0.d(false);
            if (var0.bB(167)) {
               int var18 = var0.bC(167);
               var0.a(new S_PacketBox(44, var0.fr(), var18));
               var0.b(new S_PacketBox(44, var0.fr(), var18));
            }

            L1ThebesBattle.a().b(var0);
         }
      }
   }

   public static void a(L1Character var0, L1Character var1, int var2) {
      int var3 = var1.fs();
      int var4 = var1.ft();
      int var5 = var1.fb();
      L1Map var6 = var1.fq();
      int var7 = var1.fp();
      int[][] var8 = new int[][]{{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
      var3 += var8[var5][0] * Random.a(var2);
      var4 += var8[var5][1] * Random.a(var2);
      if (var6.c(var3, var4)) {
         if (var0 instanceof L1PcInstance) {
            a((L1PcInstance)var0, var3, var4, var7, var0.fb(), true);
         } else if (var0 instanceof L1NpcInstance) {
            ((L1NpcInstance)var0).a(var3, var4, var0.fb());
         }
      }
   }

   public static void a(L1PcInstance var0, int var1) {
      L1Location var2 = var0.fu().a(var1, true);
      int var3 = var2.f();
      int var4 = var2.g();
      int var5 = var2.b();
      a(var0, var3, var4, var5, 5, true, false);
   }

   private static void a(L1NpcInstance var0, int var1, int var2, int var3, int var4) {
      L1World.a().a(var0, var3);
      L1WorldMap.b().a(var0.fp()).a(var0.fs(), var0.ft(), true);
      var0.cG(var1);
      var0.cH(var2);
      var0.cE(var3);
      var0.ct(var4);
      L1WorldMap.b().a(var0.fp()).a(var0.fs(), var0.ft(), false);
   }
}
