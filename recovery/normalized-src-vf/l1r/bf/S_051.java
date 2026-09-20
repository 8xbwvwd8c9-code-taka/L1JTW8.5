package l1r.bf;

import l1r.ao.NpcTable;
import l1r.ao.SkillsTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Npc;
import l1r.bh.L1Skills;
import l1r.bi.LineageUtil;

public class S_051 extends L1SkillExecutor {
   private final int a = 51;
   private final L1Skills b;
   private final String[] c = new String[]{"0100", "0200", "0700", "0d00", "1300", "1900", "1f00", "2500", "2b00", "3100", "3700"};
   private final int[] d = new int[]{190891, 190892, 190893, 190894, 190895, 190896, 190897, 190898, 190899, 190900, 190901};
   private final int[] e = new int[]{28, 28, 40, 52, 64, 76, 80, 82, 84, 86, 88};
   private final int[] f = new int[]{25, 25, 25, 25, 25, 25, 35, 35, 35, 35, 35};

   public S_051() {
      this.b = SkillsTable.a().a(51);
   }

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         int var7 = var2 & 0xFF;
         int var8 = var2 >> 8 & 0xFF;
         String var9 = LineageUtil.a(var7, 2) + LineageUtil.a(var8, 2);
         if (!var6.j().h(20284)) {
            var9 = "0100";
         }

         int var10 = 0;
         int var11 = 0;
         int var12 = 0;

         for (int var13 = 0; var13 < this.c.length; var13++) {
            if (var9.equalsIgnoreCase(this.c[var13])) {
               var10 = this.d[var13];
               var11 = this.e[var13];
               var12 = this.f[var13];
               break;
            }
         }

         if (var10 == 0) {
            var6.a(new S_ServerMessage(79));
            return;
         }

         if (var6.ev() < var11) {
            var6.a(new S_ServerMessage(743));
            return;
         }

         int var19 = 0;

         for (L1NpcInstance var14 : var6.ek().values()) {
            var19 += var14.Q();
         }

         int var20 = var6.eC() + 6 - var19;
         int var21 = Math.min(var20 / var12, 5);
         if (var21 == 0) {
            var6.a(new S_ServerMessage(3039));
            return;
         }

         L1Npc var16 = NpcTable.a().a(var10);

         for (int var17 = 0; var17 < var21; var17++) {
            L1SummonInstance var18 = new L1SummonInstance(var16, var6);
            var18.o(var12);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
