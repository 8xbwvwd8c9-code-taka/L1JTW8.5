package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_206 extends L1SkillExecutor {
   private final int a = 206;
   private final L1Skills b = SkillsTable.a().a(206);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         if (!var3.bB(206)) {
            var3.d(4);
         }
      }

      var1.j(206, var2 * 1000);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1PcInstance) {
         L1PcInstance var7 = (L1PcInstance)var6;
         if (!var7.bB(206)) {
            var7.d(4);
         }

         var7.j(206, this.b.v() * 1000);
         this.b(var1, this.b);
         this.a(var7, this.b);
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.d(-4);
      }
   }
}
