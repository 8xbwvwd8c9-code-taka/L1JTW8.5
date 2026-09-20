package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_ProtoBuffers;
import l1r.bh.L1Skills;

public class S_233 extends L1SkillExecutor {
   private final int a = 233;
   private final L1Skills b = SkillsTable.a().a(233);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      var1.j(233, this.b.v() * 1000);
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         var6.a(new S_ProtoBuffers(233, this.b.v(), 6, 7444, 7445, 4738, 4738, 4745, 5));
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_ProtoBuffers(110, 233));
      }
   }
}
