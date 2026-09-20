package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1SpawnEffect;
import l1r.aq.L1World;
import l1r.be.S_Paralysis;
import l1r.bh.L1Skills;
import l1r.bi.Random;

public class S_087 extends L1SkillExecutor {
   private final int a = 87;
   private final L1Skills b = SkillsTable.a().a(87);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         int var3 = 3000;
         if (var1.bB(87)) {
            return;
         }

         var1.j(87, 3000);
         L1SpawnEffect.a().a(11727, 3000, var1.fs(), var1.ft(), var1.fp());
         var1.V(true);
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var4 = (L1PcInstance)var1;
            var4.a(new S_Paralysis(5, true));
         }
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         boolean var9 = var8.a(87);
         if (var9) {
            int[] var10 = new int[]{1000, 1500, 2000, 2500, 3000, 3500};
            int var11 = Random.a(var10.length);
            int var12 = this.b.v() * 1000 + var10[var11];
            if (var7.bB(87)) {
               var12 += var7.bC(87) * 1000;
            }

            var7.j(87, var12);
            L1SpawnEffect.a().a(this.b.t(), var12, var7.fs(), var7.ft(), var7.fp());
            var7.V(true);
            if (var7 instanceof L1PcInstance) {
               L1PcInstance var13 = (L1PcInstance)var7;
               var13.a(new S_Paralysis(5, true));
            }

            if (var1 instanceof L1PcInstance) {
               var7.c((L1PcInstance)var1);
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.V(false);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_Paralysis(5, false));
      }
   }
}
