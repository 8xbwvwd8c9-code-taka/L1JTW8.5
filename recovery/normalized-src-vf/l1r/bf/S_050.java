package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1SpawnEffect;
import l1r.aq.L1World;
import l1r.be.S_Paralysis;
import l1r.be.S_Poison;
import l1r.bh.L1Skills;

public class S_050 extends L1SkillExecutor {
   private final int a = 50;
   private final L1Skills b = SkillsTable.a().a(50);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1 && !var1.bB(50)) {
         int var3 = this.b.v() * 1000;
         int var4 = 2;
         var1.j(50, var3);
         var1.V(true);
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var5 = (L1PcInstance)var1;
            var5.a(new S_Poison(var5.fr(), 2));
            var5.b(new S_Poison(var5.fr(), 2));
            var5.a(new S_Paralysis(4, true));
         } else if (var1 instanceof L1NpcInstance) {
            L1NpcInstance var6 = (L1NpcInstance)var1;
            var6.b(new S_Poison(var6.fr(), 2));
         }

         this.a(var1, this.b);
         L1SpawnEffect.a().a(this.b.u(), var3, var1.fs(), var1.ft(), var1.fp());
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         int var9 = var8.b(50);
         var8.a(var9, 0);
         this.b(var1, var7, this.b, var9);
         boolean var10 = var8.a(50);
         if (var10 && !var7.bB(50)) {
            int var11 = this.b.v() * 1000;
            int var12 = 2;
            var7.j(50, var11);
            var7.V(true);
            if (var7 instanceof L1PcInstance) {
               L1PcInstance var13 = (L1PcInstance)var7;
               var13.a(new S_Poison(var13.fr(), 2));
               var13.b(new S_Poison(var13.fr(), 2));
               var13.a(new S_Paralysis(4, true));
            } else if (var7 instanceof L1NpcInstance) {
               L1NpcInstance var14 = (L1NpcInstance)var7;
               var14.b(new S_Poison(var14.fr(), 2));
            }

            this.a(var7, this.b);
            L1SpawnEffect.a().a(this.b.u(), var11, var7.fs(), var7.ft(), var7.fp());
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.V(false);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_Poison(var2.fr(), 0));
         var2.b(new S_Poison(var2.fr(), 0));
         var2.a(new S_Paralysis(4, false));
      } else if (var1 instanceof L1MonsterInstance || var1 instanceof L1SummonInstance || var1 instanceof L1PetInstance) {
         L1NpcInstance var3 = (L1NpcInstance)var1;
         var3.b(new S_Poison(var3.fr(), 0));
      }
   }
}
