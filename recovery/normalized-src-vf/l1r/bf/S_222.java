package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_ProtoBuffers;
import l1r.bh.L1Skills;

public class S_222 extends L1SkillExecutor {
   private final int a = 222;
   private final L1Skills b = SkillsTable.a().a(222);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      if (!var1.bB(222) && var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var3.a(new S_ProtoBuffers(222, this.b.v(), 6, 7456, 7457, 4761, 4761, 4754, 5));
      }

      var1.j(222, var2 * 1000);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      var1.j(222, this.b.v() * 1000);
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         var6.a(new S_ProtoBuffers(222, this.b.v(), 6, 7456, 7457, 4761, 4761, 4754, 5));
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_ProtoBuffers(110, 222));
      }
   }
}
