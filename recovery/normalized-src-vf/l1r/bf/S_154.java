package l1r.bf;

import l1r.ao.NpcTable;
import l1r.ao.SkillsTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.bh.L1Npc;
import l1r.bh.L1Skills;

public class S_154 extends L1SkillExecutor {
   private final int a = 154;
   private final L1Skills b = SkillsTable.a().a(154);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         int var7 = var6.bC();
         if (var7 == 0 || !var6.fq().o()) {
            this.b(var1, 79);
            return;
         }

         int var8 = 0;

         for (L1NpcInstance var9 : var6.ek().values()) {
            var8 += var9.Q();
         }

         if (var8 == 0) {
            int[] var13 = new int[]{45306, 45303, 45304, 0, 45305};
            int var14 = var13[var7 / 2];
            L1Npc var11 = NpcTable.a().a(var14);
            L1SummonInstance var12 = new L1SummonInstance(var11, var6);
            var12.o(var6.eC() + 7);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
