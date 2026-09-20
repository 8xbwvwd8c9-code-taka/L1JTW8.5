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

public class S_192 extends L1SkillExecutor {
   private final int a = 192;
   private final L1Skills b = SkillsTable.a().a(192);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         int var9 = var8.b(192);
         var8.a(var9, 0);
         boolean var10 = var8.a(192);
         if (var10 && !var7.bB(192) && !var7.bB(1028)) {
            int[] var11 = new int[]{1000, 2000, 3000, 4000};
            int var12 = var11[Random.a(var11.length)];
            if (var7 instanceof L1PcInstance) {
               L1PcInstance var13 = (L1PcInstance)var7;
               var13.a(new S_Paralysis(6, true));
            } else if (var7 instanceof L1NpcInstance) {
               L1NpcInstance var14 = (L1NpcInstance)var7;
               var14.n(true);
            }

            var7.cC(var12);
            var7.j(192, 500);
         }

         this.b(var1, var7, this.b, var9);
      }
   }

   @Override
   public void a(L1Character var1) {
      int var2 = var1.fm();
      var1.j(1028, var2);
      L1SpawnEffect.a().a(4184, var2, var1.fs(), var1.ft(), var1.fp());
   }
}
