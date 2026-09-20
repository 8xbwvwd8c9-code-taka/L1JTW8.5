package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_204 extends L1SkillExecutor {
   private final int a = 204;
   private final L1Skills b = SkillsTable.a().a(204);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == 0) {
         var2 = this.b.v();
         this.a(var1, this.b);
      }

      if (!var1.bB(204)) {
         var1.ck(4);
         var1.cm(4);
         var1.cl(4);
         var1.cn(4);
      }

      var1.j(204, var2 * 1000);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         if (!var7.bB(204)) {
            var7.ck(4);
            var7.cm(4);
            var7.cl(4);
            var7.cn(4);
         }

         var7.j(204, this.b.v() * 1000);
         this.b(var1, this.b);
         this.a(var7, this.b);
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.ck(-4);
      var1.cm(-4);
      var1.cl(-4);
      var1.cn(-4);
   }
}
