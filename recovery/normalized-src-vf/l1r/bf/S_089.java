package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.bh.L1Skills;

public class S_089 extends L1SkillExecutor {
   private final int a = 89;
   private final L1Skills b = SkillsTable.a().a(89);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      if (!var1.bB(89)) {
         var1.cm(6);
      }

      var1.j(89, var2 * 1000);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      this.a(var1, this.b);
      if (!var1.bB(89)) {
         var1.cm(6);
      }

      var1.j(89, this.b.v() * 1000);
   }

   @Override
   public void a(L1Character var1) {
      var1.cm(-6);
   }
}
