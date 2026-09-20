package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_SPMR;
import l1r.be.S_SkillIconShield;
import l1r.bh.L1Skills;

public class S_099 extends L1SkillExecutor {
   private final int a = 99;
   private final L1Skills b = SkillsTable.a().a(99);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      if (!var1.bB(99)) {
         var1.co(5);
      }

      var1.j(99, var2 * 1000);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.a(new S_SPMR(var3));
         var3.a(new S_SkillIconShield(3, var2));
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.a(var1, this.b);
      this.b(var1, this.b);
      if (!var1.bB(99)) {
         var1.co(5);
      }

      var1.j(99, this.b.v() * 1000);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         var6.a(new S_SPMR(var6));
         var6.a(new S_SkillIconShield(3, this.b.v()));
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.co(-5);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_SPMR(var2));
         var2.a(new S_SkillIconShield(3, 0));
      }
   }
}
