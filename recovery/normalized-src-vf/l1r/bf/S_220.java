package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1SpawnEffect;
import l1r.bh.L1Skills;

public class S_220 extends L1SkillExecutor {
   private final int a = 220;
   private final L1Skills b = SkillsTable.a().a(220);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      this.a(var1, this.b);
      int[][] var6 = new int[][]{{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
      int var7 = var1.fb();
      int[] var8 = var6[var7];
      if (var1 instanceof L1PcInstance) {
         int var9 = var1.fs() + var8[0];
         int var10 = var1.ft() + var8[1];
         int var11 = this.b.v() * 1000;
         L1SpawnEffect.a().a(6724, var11, var9, var10, var1.fp(), (L1PcInstance)var1, 220);
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
