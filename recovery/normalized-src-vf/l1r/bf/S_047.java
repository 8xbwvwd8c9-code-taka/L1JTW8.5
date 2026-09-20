package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_047 extends L1SkillExecutor {
   private final int a = 47;
   private final L1Skills b = SkillsTable.a().a(47);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         var2 = this.b.v();
         this.a(var1, this.b);
      } else if (var2 == 0) {
         return;
      }

      if (!var1.bB(47)) {
         var1.ck(-5);
         var1.cm(-1);
      }

      var1.j(47, var2 * 1000);
      this.b(var1, 692);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         if (var8.a(47)) {
            if (!var7.bB(47)) {
               var7.ck(-5);
               var7.cm(-1);
            }

            var7.j(47, this.b.v() * 1000);
            this.a(var7, this.b);
            this.b(var7, 692);
         } else {
            this.b(var1, 280);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.ck(5);
      var1.cm(1);
   }
}
