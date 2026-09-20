package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.bh.L1Skills;

public class S_164 extends L1SkillExecutor {
   private final int a = 164;
   private final L1Skills b = SkillsTable.a().a(164);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         if (var6.q()) {
            for (L1PcInstance var7 : var6.aL().c()) {
               if (!var7.eX() && var7.fu().e(var6.fu())) {
                  L1Magic var9 = new L1Magic(var1, var7);
                  int var10 = var9.c(164);
                  if (var7.bB(73)) {
                     var7.a(var1, var10 * 0.3, true);
                  } else {
                     var7.a(var7.ea() + var10);
                  }
               }
            }
         } else {
            L1Magic var11 = new L1Magic(var1, var1);
            int var12 = var11.c(164);
            if (var6.bB(73)) {
               var6.a(var1, var12 * 0.3, true);
               return;
            }

            var1.a(var1.ea() + var12);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
