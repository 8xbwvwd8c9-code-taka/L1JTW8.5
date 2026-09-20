package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1World;
import l1r.be.S_Message_YN;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Skills;

public class S_116 extends L1SkillExecutor {
   private final int a = 116;
   private final L1Skills b = SkillsTable.a().a(116);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         L1PcInstance var7 = L1World.a().a(var5);
         if (var7 == null) {
            var6.a(new S_ServerMessage(73, var5));
         } else if (var6.aF() != var7.aF()) {
            var6.a(new S_ServerMessage(414));
         } else {
            var6.aQ(var7.fr());
            var6.aR(var6.fb());
            var7.am(var6.fr());
            var7.a(new S_Message_YN(729));
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
