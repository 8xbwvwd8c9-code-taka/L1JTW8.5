package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_161 extends L1SkillExecutor {
   private final int a = 161;
   private final L1Skills b = SkillsTable.a().a(161);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      this.a(var1, this.b);

      for (L1Object var7 : L1World.a().b(var1, this.b.q())) {
         if (var7 instanceof L1Character) {
            L1Character var9 = (L1Character)var7;
            if (!var9.eX() && !var9.ff()) {
               L1Magic var10 = new L1Magic(var1, var9);
               boolean var11 = var10.a(161);
               if (var11) {
                  var9.j(161, this.b.v() * 1000);
                  this.c(var9, this.b.u());
                  this.b(var1, 715);
               }
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
