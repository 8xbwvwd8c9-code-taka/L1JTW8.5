package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.bh.L1Skills;

public class S_191 extends L1SkillExecutor {
   private final int a = 191;
   private final L1Skills b = SkillsTable.a().a(191);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      var1.j(191, var2 * 1000);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      var1.j(191, this.b.v() * 1000);
      this.a(var1, this.b);
      this.b(var1, this.b);
   }

   @Override
   public void a(L1Character var1) {
   }
}
