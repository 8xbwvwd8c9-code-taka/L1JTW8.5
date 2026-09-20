package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1Paralysis;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_033 extends L1SkillExecutor {
   private final int a = 33;
   private final L1Skills b = SkillsTable.a().a(33);

   @Override
   public void a(L1Character var1, int var2) {
      int var3 = 0;
      if (var2 == -1) {
         var2 = this.b.v();
         var3 = 5000;
         this.a(var1, this.b);
      } else if (var2 == 0) {
         return;
      }

      L1Paralysis.a(var1, var3, var2 * 1000);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         boolean var9 = var8.a(33);
         if (var9) {
            this.a(var7, this.b);
            L1Paralysis.a(var7, 5000, this.b.v() * 1000);
         } else {
            this.b(var1, 280);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.ef();
   }
}
