package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_Strup;
import l1r.bh.L1Skills;

public class S_042 extends L1SkillExecutor {
   private final int a = 42;
   private final L1Skills b = SkillsTable.a().a(42);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      if (!var1.bB(42)) {
         var1.bN(5);
      }

      var1.j(42, var2 * 1000);
      this.b(var1, 292);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.a(new S_Strup(var3, 5, var2));
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      this.b(var1, this.b);
      if (var1 instanceof L1PcInstance && var6 instanceof L1PcInstance) {
         L1PcInstance var7 = (L1PcInstance)var1;
         L1PcInstance var8 = (L1PcInstance)var6;
         if (var8.fr() == var7.fr() || var7.aF() != 0 && var7.aF() == var8.aF()) {
            if (!var8.bB(42)) {
               var8.bN(5);
            }

            var8.j(42, this.b.v() * 1000);
            this.a(var8, this.b);
            var8.a(new S_Strup(var8, 5, this.b.v()));
            this.b(var8, 292);
         } else {
            this.b(var1, 79);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.bN(-5);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_Strup(var2, 5, 0));
      }
   }
}
