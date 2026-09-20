package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_SkillBrave;
import l1r.bh.L1Skills;

public class S_186 extends L1SkillExecutor {
   private final int a = 186;
   private final L1Skills b = SkillsTable.a().a(186);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      var1.j(186, var2 * 1000);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.cv(1);
         var3.a(new S_SkillBrave(var3.fr(), 1, var2));
      }

      var1.b(new S_SkillBrave(var1.fr(), 1, var2));
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      var1.j(186, this.b.v() * 1000);
      this.b(var1, this.b);
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         var6.cv(1);
         var6.a(new S_SkillBrave(var6.fr(), 1, this.b.v()));
      }

      var1.b(new S_SkillBrave(var1.fr(), 1, this.b.v()));
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.cv(0);
         var2.a(new S_SkillBrave(var2.fr(), 0, 0));
         var2.b(new S_SkillBrave(var2.fr(), 0, 0));
      }
   }
}
