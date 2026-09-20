package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_202 extends L1SkillExecutor {
   private final int a = 202;
   private final L1Skills b = SkillsTable.a().a(202);

   @Override
   public void a(L1Character var1, int var2) {
      if (!var1.bB(202) || var1.bC(202) <= 6) {
         if (var2 == -1) {
            var2 = this.b.v();
            this.a(var1, this.b);
         } else if (var2 == 0) {
            return;
         }

         this.b(var1, 1339);
         var1.j(202, var2 * 1000);
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         int var9 = var8.b(202);
         var8.a(var9, 0);
         this.a(var1, var7, this.b, var9);
         if (!var7.bB(202) || var7.bC(202) <= 6) {
            boolean var10 = var8.a(202);
            if (var10) {
               this.b(var7, 1339);
               this.a(var7, this.b);
               var7.j(202, this.b.v() * 1000);
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
