package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_HPUpdate;
import l1r.bh.L1Skills;

public class S_226 extends L1SkillExecutor {
   private final int a = 226;
   private final L1Skills b = SkillsTable.a().a(226);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      if (var1 instanceof L1PcInstance && !var1.bB(226)) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.av(var3.bd() * (var3.ev() / 2) / 100);
         var3.bH(var3.bx());
         var3.a(new S_HPUpdate(var3.ea(), var3.ew()));
         if (var3.q()) {
            var3.aL().f(var3);
         }
      }

      var1.j(226, var2 * 1000);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance && !var1.bB(226)) {
         L1PcInstance var6 = (L1PcInstance)var1;
         var6.av(var6.bd() * (var6.ev() / 2) / 100);
         var6.bH(var6.bx());
         var6.a(new S_HPUpdate(var6.ea(), var6.ew()));
         if (var6.q()) {
            var6.aL().f(var6);
         }
      }

      var1.j(226, this.b.v() * 1000);
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.bH(-var2.bx());
         var2.av(0);
         var2.a(new S_HPUpdate(var2.ea(), var2.ew()));
         if (var2.q()) {
            var2.aL().f(var2);
         }
      }
   }
}
