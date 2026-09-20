package l1r.bf;

import java.util.ArrayList;
import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_017 extends L1SkillExecutor {
   private final int a = 17;
   private final L1Skills b = SkillsTable.a().a(17);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         ArrayList var8 = this.a(var1, var7, this.b.q());

         for (L1Character var9 : var8) {
            L1Magic var11 = new L1Magic(var1, var9);
            int var12 = var11.b(17);
            var11.a(var12, 0);
         }

         this.a(var1, this.b, var8);
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
