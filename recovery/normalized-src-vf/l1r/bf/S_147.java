package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_OwnCharAttrDef;
import l1r.bh.L1Skills;

public class S_147 extends L1SkillExecutor {
   private final int a = 147;
   private final L1Skills b = SkillsTable.a().a(147);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance && !var1.bB(147)) {
         L1PcInstance var6 = (L1PcInstance)var1;
         int var7 = var6.bC();
         if (var7 == 1) {
            var6.cb(50);
         } else if (var7 == 2) {
            var6.ca(50);
         } else if (var7 == 4) {
            var6.bZ(50);
         } else if (var7 == 8) {
            var6.bY(50);
         }

         var6.a(new S_OwnCharAttrDef(var6));
      }

      var1.j(147, this.b.v() * 1000);
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         int var3 = var2.bC();
         if (var3 == 1) {
            var1.cb(-50);
         } else if (var3 == 2) {
            var1.ca(-50);
         } else if (var3 == 4) {
            var1.bZ(-50);
         } else if (var3 == 8) {
            var1.bY(-50);
         }

         var2.a(new S_OwnCharAttrDef(var2));
      }
   }
}
