package l1r.bf;

import java.util.ArrayList;
import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_025 extends L1SkillExecutor {
   private final int a = 25;
   private final L1Skills b = SkillsTable.a().a(25);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         int var8 = this.b.q();
         ArrayList var9 = this.a(var1, var7, var8);

         for (L1Character var10 : var9) {
            L1Magic var12 = new L1Magic(var1, var10);
            int var13 = var12.b(25);
            var12.a(var13, 0);
         }

         this.a(var1, this.b, var9);
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
