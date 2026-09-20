package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_SkillSound;
import l1r.bh.L1Skills;

public class S_145 extends L1SkillExecutor {
   private final int a = 145;
   private final L1Skills b = SkillsTable.a().a(145);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         if (var7 instanceof L1SummonInstance) {
            L1SummonInstance var8 = (L1SummonInstance)var7;
            var8.b(new S_SkillSound(var8.fr(), this.b.t()));
            var8.h();
         } else {
            this.b(var1, 79);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
