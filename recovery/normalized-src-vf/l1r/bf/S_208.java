package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1SpawnEffect;
import l1r.aq.L1World;
import l1r.be.S_Paralysis;
import l1r.bh.L1Skills;
import l1r.bi.Random;

public class S_208 extends L1SkillExecutor {
   private final int a = 208;
   private final L1Skills b = SkillsTable.a().a(208);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         var2 = this.b.v();
         this.a(var1, this.b);
      } else if (var2 == 0) {
         return;
      }

      int var3 = var2 * 1000 - Random.a(1000);
      var1.V(true);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var4 = (L1PcInstance)var1;
         var4.a(new S_Paralysis(5, true));
      }

      var1.j(208, var3);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var8 = (L1PcInstance)var1;
            var7.a(var8, 208);
            L1SpawnEffect.a().a(this.b.t(), 2500, var7.fs(), var7.ft(), var7.fp());
         }

         if (var7.bB(208)) {
            this.b(var1, 280);
         } else {
            L1Magic var12 = new L1Magic(var1, var7);
            boolean var9 = var12.a(208);
            if (var9) {
               int var10 = this.b.v() * 1000 - Random.a(1000);
               var7.j(208, var10);
               var7.V(true);
               if (var7 instanceof L1PcInstance) {
                  L1PcInstance var11 = (L1PcInstance)var7;
                  var11.a(new S_Paralysis(5, true));
               }
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
      } else if (var1 instanceof L1NpcInstance) {
         L1NpcInstance var3 = (L1NpcInstance)var1;
         var3.V(false);
      }
   }
}
