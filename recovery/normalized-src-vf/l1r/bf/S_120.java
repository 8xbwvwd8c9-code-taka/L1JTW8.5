package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.bh.L1Skills;

public class S_120 extends L1SkillExecutor {
   private final int a = 120;
   private final L1Skills b = SkillsTable.a().a(120);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      var1.j(120, var2 * 1000);
      this.b(var1, 737);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      if (var1.bB(120)) {
         this.b(var1, 79);
      } else {
         var1.j(120, this.b.v() * 1000);
         this.a(var1, this.b);
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(0);
         var2.b((L1Character)null);
      }
   }
}
