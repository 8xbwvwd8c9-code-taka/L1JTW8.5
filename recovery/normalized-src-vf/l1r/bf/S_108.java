package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_108 extends L1SkillExecutor {
   private final int a = 108;
   private final L1Skills b = SkillsTable.a().a(108);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         this.b(var1, this.b);
         this.a(var7, this.b);
         L1Magic var8 = new L1Magic(var1, var7);
         int var9 = var8.b(108);
         var8.a(var9, 0);
         var1.a(100);
         var1.i_(1);
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
