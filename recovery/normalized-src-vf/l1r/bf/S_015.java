package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_015 extends L1SkillExecutor {
   private final int a = 15;
   private final L1Skills b = SkillsTable.a().a(15);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         int var9 = var8.b(15);
         var8.a(var9, 0);
         this.b(var1, var7, this.b, var9);
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
