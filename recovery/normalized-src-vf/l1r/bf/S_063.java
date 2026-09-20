package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1SpawnEffect;
import l1r.bh.L1Skills;

public class S_063 extends L1SkillExecutor {
   private final int a = 63;
   private final L1Skills b = SkillsTable.a().a(63);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1SpawnEffect.a().a(this.b.t(), this.b.v() * 1000, var3, var4, var1.fp());
   }

   @Override
   public void a(L1Character var1) {
   }
}
