package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.az.L1DamagePoison;
import l1r.bh.L1Skills;

public class S_011 extends L1SkillExecutor {
   private final int a = 11;
   private final L1Skills b = SkillsTable.a().a(11);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         var2 = this.b.v();
         this.a(var1, this.b);
      } else if (var2 == 0) {
         return;
      }

      L1DamagePoison.a(var1, var1, 3000, 5, var2);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         boolean var9 = var8.a(11);
         if (var9) {
            this.a(var7, this.b);
            L1DamagePoison.a(var1, var7, 3000, 5, this.b.v());
         } else {
            this.b(var1, 280);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.en();
   }
}
