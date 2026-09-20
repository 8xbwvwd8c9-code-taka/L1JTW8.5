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
import l1r.bh.L1Skills;

public class S_230 extends L1SkillExecutor {
   private final int a = 230;
   private final L1Skills b = SkillsTable.a().a(230);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         this.b(var1, this.b);
         this.a(var7, this.b);
         L1Magic var8 = new L1Magic(var1, var7);
         boolean var9 = var8.a(230);
         if (var9 && !var7.bB(230)) {
            var7.j(230, this.b.v() * 1000);
            if (var7 instanceof L1PcInstance) {
               L1PcInstance var10 = (L1PcInstance)var7;
               var10.a(new S_Paralysis(9, true));
            } else if (var7 instanceof L1NpcInstance) {
               L1NpcInstance var11 = (L1NpcInstance)var7;
               var11.V(true);
            }

            L1SpawnEffect.a().a(this.b.u(), 6000, var7.fs(), var7.ft(), var7.fp());
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_Paralysis(9, false));
      } else if (var1 instanceof L1MonsterInstance || var1 instanceof L1SummonInstance || var1 instanceof L1PetInstance) {
         L1NpcInstance var3 = (L1NpcInstance)var1;
         var3.V(false);
      }
   }
}
