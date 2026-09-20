package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Skills;

public class S_041 extends L1SkillExecutor {
   private final int a = 41;
   private final L1Skills b = SkillsTable.a().a(41);

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
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var8 = (L1PcInstance)var1;
            if (!(var7 instanceof L1MonsterInstance) || !var7.eX()) {
               this.b(var1, 79);
               return;
            }

            L1MonsterInstance var9 = (L1MonsterInstance)var7;
            int var10 = 0;

            for (L1NpcInstance var11 : var1.ek().values()) {
               var10 += var11.Q();
            }

            int var13 = var1.eC();
            if (var8.A()) {
               var13 = Math.min(var13, 30) + 12;
            } else if (var8.B()) {
               var13 = Math.min(var13, 36) + 6;
            }

            var13 -= var10;
            if (var13 >= 6) {
               L1SummonInstance var15 = new L1SummonInstance(var9, var1, true);
            } else {
               var8.a(new S_ServerMessage(319));
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
