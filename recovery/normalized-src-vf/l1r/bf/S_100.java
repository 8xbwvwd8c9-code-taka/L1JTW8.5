package l1r.bf;

import l1r.ao.ItemTable;
import l1r.ao.SkillsTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Skills;
import l1r.bi.Random;

public class S_100 extends L1SkillExecutor {
   private final int a = 100;
   private final L1Skills b = SkillsTable.a().a(100);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.a(var1, this.b);
      this.b(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         L1ItemInstance var7 = var6.j().e(var2);
         if (var7 != null) {
            int var8 = var7.N();
            int var9 = (int)(10.0 + var6.ev() * 0.8 + (var6.eE() - 6) * 1.2);
            int var10 = (int)(var9 / 2.1);
            int var11 = (int)(var10 / 2.0);
            int var12 = (int)(var11 / 1.9);
            int var13 = Random.a(100) + 1;
            int var14 = 0;
            String var15 = null;
            if (var8 == 40320) {
               var14 = var9;
               var15 = "$2475";
            } else if (var8 == 40321) {
               var14 = var10;
               var15 = "$2476";
            } else if (var8 == 40322) {
               var14 = var11;
               var15 = "$2477";
            } else {
               if (var8 != 40323) {
                  var6.a(new S_ServerMessage(79));
                  return;
               }

               var14 = var12;
               var15 = "$2478";
            }

            if (var14 >= var13) {
               ItemTable.a(var6, var8 + 1, 1);
            } else {
               var6.a(new S_ServerMessage(280));
            }

            var6.j().b(var7, 1);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
