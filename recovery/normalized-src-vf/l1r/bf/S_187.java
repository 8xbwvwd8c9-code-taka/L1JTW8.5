package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_EffectLocation;
import l1r.be.S_PacketBox;
import l1r.bh.L1Skills;

public class S_187 extends L1SkillExecutor {
   private final int a = 187;
   private final L1Skills b = SkillsTable.a().a(187);

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
            var8.w(true);

            for (int var9 = 0; var9 < 3; var9++) {
               var7.c(var8);
            }

            var8.w(false);
            int[] var14 = new int[]{5001, 5002, 5003};
            int[] var13 = var14;
            int var12 = var14.length;

            for (int var11 = 0; var11 < var12; var11++) {
               int var10 = var13[var11];
               if (var8.bB(var10)) {
                  var8.bA(var10);
                  var8.a(new S_PacketBox(75, 0));
               }
            }

            var8.a(new S_EffectLocation(var7.fs(), var7.ft(), this.b.u()));
            var8.b(new S_EffectLocation(var7.fs(), var7.ft(), this.b.u()));
            this.a(var1, this.b);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
