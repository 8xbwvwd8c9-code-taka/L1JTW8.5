package l1r.aq;

import l1r.ai.IdFactory;
import l1r.ao.NpcTable;
import l1r.ao.SkillsTable;
import l1r.ap.L1EffectInstance;
import l1r.ap.L1PcInstance;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.be.S_NPCPack;
import l1r.be.S_SkillSound;
import l1r.bh.L1Npc;

public class L1SpawnEffect {
   private static L1SpawnEffect a;

   private L1SpawnEffect() {
   }

   public static L1SpawnEffect a() {
      if (a == null) {
         a = new L1SpawnEffect();
      }

      return a;
   }

   public L1EffectInstance a(int var1, int var2, int var3, int var4, int var5) {
      return this.a(var1, var2, var3, var4, var5, null, 0);
   }

   public L1EffectInstance a(int var1, int var2, int var3, int var4, int var5, L1PcInstance var6, int var7) {
      L1Npc var8 = NpcTable.a().a(189999);
      var8.k(var1);
      L1EffectInstance var9 = new L1EffectInstance(var8);
      var9.cF(IdFactory.a().c());
      var9.cG(var3);
      var9.cH(var4);
      var9.ct(0);
      var9.cE(var5);
      var9.d(var6);
      var9.h_(var7);
      var9.f();
      L1World.a().a(var9);
      L1World.a().c(var9);

      for (L1PcInstance var10 : L1World.a().f(var9)) {
         var9.c((L1Object)var10);
         var10.c(var9);
         var10.a(new S_NPCPack(var9));
         var10.a(new S_SkillSound(var9.fr(), var9.fe()));
      }

      var9.a((long)var2);
      return var9;
   }

   public void a(L1PcInstance var1, int var2, int var3) {
      int var4 = SkillsTable.a().a(58).v();
      L1Character var5 = var1;
      L1Map var6 = L1WorldMap.b().a(var1.fp());

      for (int var7 = 0; var7 < 8; var7++) {
         int var8 = var5.h(var2, var3);
         int var9 = var5.fs();
         int var10 = var5.ft();
         if (var8 == 1) {
            var9++;
            var10--;
         } else if (var8 == 2) {
            var9++;
         } else if (var8 == 3) {
            var9++;
            var10++;
         } else if (var8 == 4) {
            var10++;
         } else if (var8 == 5) {
            var9--;
            var10++;
         } else if (var8 == 6) {
            var9--;
         } else if (var8 == 7) {
            var9--;
            var10--;
         } else if (var8 == 0) {
            var10--;
         }

         if (!var5.c(var9, var10, 1)) {
            var9 = var5.fs();
            var10 = var5.ft();
         }

         if (!var6.d(var9, var10, var1.fb())) {
            break;
         }

         L1EffectInstance var11 = this.a(168, var4 * 1000, var9, var10, var1.fp(), var1, 58);
         if (var11 == null) {
            break;
         }

         for (L1EffectInstance var12 : L1World.a().d().values()) {
            if (!var12.equals(var11) && var12.fq() == var6 && var11.fu().f(var12.fu()) && var12.fe() == 168) {
               var12.aa_();
            }
         }

         if (var2 == var9 && var3 == var10) {
            break;
         }

         var5 = var11;
      }
   }
}
