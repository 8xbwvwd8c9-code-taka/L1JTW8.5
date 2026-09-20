package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.be.S_DoActionGFX;
import l1r.bh.L1Skills;

public class S_070 extends L1SkillExecutor {
   private final int a = 70;
   private final L1Skills b = SkillsTable.a().a(70);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      this.a(var1, this.b);
      int var6 = this.b.q();

      for (L1Character var8 : this.a(var1, var1, var6)) {
         L1Magic var10 = new L1Magic(var1, var8);
         int var11 = var10.b(70);
         var10.a(var11, 0);
         if (var11 > 0) {
            var8.b(new S_DoActionGFX(var8.fr(), 2));
            if (var8 instanceof L1PcInstance) {
               L1PcInstance var12 = (L1PcInstance)var8;
               var12.a(new S_DoActionGFX(var12.fr(), 2));
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
