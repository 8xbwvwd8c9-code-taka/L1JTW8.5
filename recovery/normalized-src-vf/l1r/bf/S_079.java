package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_HPUpdate;
import l1r.be.S_MPUpdate;
import l1r.bh.L1Skills;

public class S_079 extends L1SkillExecutor {
   private final int a = 79;
   private final L1Skills b = SkillsTable.a().a(79);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      if (var1 instanceof L1PcInstance && !var1.bB(79)) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.au(var3.bd() / 5);
         var3.aw(var3.be() / 5);
         var3.bH(var3.bw());
         var3.bJ(var3.by());
         var3.a(new S_MPUpdate(var3.eb(), var3.ex()));
         var3.a(new S_HPUpdate(var3.ea(), var3.ew()));
         if (var3.q()) {
            var3.aL().f(var3);
         }
      }

      var1.j(79, var2 * 1000);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance && !var1.bB(79)) {
         L1PcInstance var6 = (L1PcInstance)var1;
         var6.au(var6.bd() / 5);
         var6.aw(var6.be() / 5);
         var6.bH(var6.bw());
         var6.bJ(var6.by());
         var6.a(new S_MPUpdate(var6.eb(), var6.ex()));
         var6.a(new S_HPUpdate(var6.ea(), var6.ew()));
         if (var6.q()) {
            var6.aL().f(var6);
         }
      }

      var1.j(79, this.b.v() * 1000);
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.bH(-var2.bw());
         var2.bJ(-var2.by());
         var2.au(0);
         var2.aw(0);
         var2.a(new S_HPUpdate(var2.ea(), var2.ew()));
         if (var2.q()) {
            var2.aL().f(var2);
         }

         var2.a(new S_MPUpdate(var2.eb(), var2.ex()));
      }
   }
}
