package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_071 extends L1SkillExecutor {
   private final int a = 71;
   private final L1Skills b = SkillsTable.a().a(71);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         var2 = this.b.v();
         this.a(var1, this.b);
      } else if (var2 == 0) {
         return;
      }

      var1.j(71, var2 * 1000);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         this.b(var1, this.b);
         L1Magic var8 = new L1Magic(var1, var7);
         if (var8.a(71)) {
            var7.j(71, this.b.v() * 1000);
            this.a(var7, this.b);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
