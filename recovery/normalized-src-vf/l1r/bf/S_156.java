package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_SkillIconAura;
import l1r.bh.L1Skills;

public class S_156 extends L1SkillExecutor {
   private final int a = 156;
   private final L1Skills b = SkillsTable.a().a(156);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      var1.j(156, var2 * 1000);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.cn(2);
         var3.cl(3);
         var3.a(new S_SkillIconAura(155, var2, var3));
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         if (var6.q()) {
            for (L1PcInstance var7 : var6.aL().c()) {
               if (!var7.eX() && !var7.ff() && var7.fu().e(var6.fu())) {
                  var7.j(156, this.b.v() * 1000);
                  this.a(var7, this.b);
                  var7.cn(2);
                  var7.cl(3);
                  var7.a(new S_SkillIconAura(155, this.b.v(), var6));
               }
            }
         } else {
            var6.j(156, this.b.v() * 1000);
            this.a(var6, this.b);
            var6.cn(2);
            var6.cl(3);
            var6.a(new S_SkillIconAura(155, this.b.v(), var6));
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.cn(-2);
      var1.cl(-3);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_SkillIconAura(155, 0, var2));
      }
   }
}
