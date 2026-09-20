package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_OwnCharAttrDef;
import l1r.bh.L1Skills;

public class S_138 extends L1SkillExecutor {
   private final int a = 138;
   private final L1Skills b = SkillsTable.a().a(138);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      if (!var1.bB(138)) {
         var1.bY(10);
         var1.bZ(10);
         var1.ca(10);
         var1.cb(10);
      }

      var1.j(138, var2 * 1000);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.a(new S_OwnCharAttrDef(var3));
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      if (!var1.bB(138)) {
         var1.bY(10);
         var1.bZ(10);
         var1.ca(10);
         var1.cb(10);
      }

      var1.j(138, this.b.v() * 1000);
      this.b(var1, this.b);
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         var6.a(new S_OwnCharAttrDef(var6));
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.bY(-10);
      var1.bZ(-10);
      var1.ca(-10);
      var1.cb(-10);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_OwnCharAttrDef(var2));
      }
   }
}
