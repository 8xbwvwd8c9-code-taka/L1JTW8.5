package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Teleport;
import l1r.be.S_Paralysis;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Skills;

public class S_131 extends L1SkillExecutor {
   private final int a = 131;
   private final L1Skills b = SkillsTable.a().a(131);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         if (!var6.fq().j() && !var6.l()) {
            var6.a(new S_ServerMessage(276));
            var6.a(new S_Paralysis(7, true));
         } else {
            L1Teleport.a(var6, 33051, 32337, 4, 5, true);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
