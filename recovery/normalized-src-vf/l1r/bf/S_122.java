package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_ProtoBuffers;
import l1r.bh.L1Skills;

public class S_122 extends L1SkillExecutor {
   private final int a = 122;
   private final L1Skills b = SkillsTable.a().a(122);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      if (!var1.bB(122)) {
         var1.ci(10);
         var1.cd(10);
         var1.ch(10);
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var3 = (L1PcInstance)var1;
            var3.a(new S_ProtoBuffers(122, this.b.v(), 6, 7427, 7428, 4734, 4734, 4741, 5));
         }
      }

      var1.j(122, var2 * 1000);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         if (var6.q()) {
            for (L1PcInstance var7 : var6.aL().c()) {
               if (!var7.eX() && var7.fu().e(var6.fu())) {
                  var7.ci(10);
                  var7.cd(10);
                  var7.ch(10);
                  var7.j(122, this.b.v() * 1000);
                  this.a(var7, this.b);
                  var7.a(new S_ProtoBuffers(122, this.b.v(), 6, 7427, 7428, 4734, 4734, 4741, 5));
               }
            }
         } else {
            var6.ci(10);
            var6.cd(10);
            var6.ch(10);
            var6.j(122, this.b.v() * 1000);
            this.a(var6, this.b);
            var6.a(new S_ProtoBuffers(122, this.b.v(), 6, 7427, 7428, 4734, 4734, 4741, 5));
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.ci(-10);
         var2.cd(-10);
         var2.ch(-10);
         var2.a(new S_ProtoBuffers(110, 122));
      }
   }
}
