package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.bh.L1Skills;

public class S_078 extends L1SkillExecutor {
   private final int a = 78;
   private final L1Skills b = SkillsTable.a().a(78);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         this.a(var1, this.b);
         var2 = this.b.v();
      }

      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.b();
         var3.d();
      }

      var1.j(78, var2 * 1000);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      var1.j(78, this.b.v() * 1000);
      this.b(var1, this.b);
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         var6.b();
         var6.d();
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a();
         var2.c();
      }
   }
}
