package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_132 extends L1SkillExecutor {
   private final int a = 132;
   private final L1Skills b = SkillsTable.a().a(132);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var8 = (L1PcInstance)var1;
            L1ItemInstance var9 = var8.v();
            if (var9 == null || var9.a().aO() != 20) {
               return;
            }

            this.a(var1, this.b);
            var8.v(true);

            for (int var10 = 0; var10 < 3; var10++) {
               var7.c(var8);
            }

            var8.v(false);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
