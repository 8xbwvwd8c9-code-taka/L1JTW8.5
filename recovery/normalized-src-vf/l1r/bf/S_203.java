package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_203 extends L1SkillExecutor {
   private final int a = 203;
   private final L1Skills b = SkillsTable.a().a(203);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var8 = (L1PcInstance)var1;
            var7.a(var8, 203);
         }

         L1Magic var9 = new L1Magic(var1, var7);
         var9.a(var9.b(203), 0);
         this.a(var7, this.b);
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
