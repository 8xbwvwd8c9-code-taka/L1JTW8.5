package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_OwnCharStatus;
import l1r.bh.L1Skills;

public class S_146 extends L1SkillExecutor {
   private final int a = 146;
   private final L1Skills b = SkillsTable.a().a(146);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      this.a(var1, this.b);
      var1.i_(var1.eb() + 15);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         var6.a(new S_OwnCharStatus(var6));
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
