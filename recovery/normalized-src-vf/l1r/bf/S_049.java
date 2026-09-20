package l1r.bf;

import l1r.ao.ClanTable;
import l1r.ao.SkillsTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Clan;
import l1r.aq.L1Magic;
import l1r.bh.L1Skills;

public class S_049 extends L1SkillExecutor {
   private final int a = 49;
   private final L1Skills b = SkillsTable.a().a(49);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         if (var6.aF() != 0) {
            L1Clan var7 = ClanTable.a().a(var6.aF());

            for (L1PcInstance var8 : var7.b()) {
               if (var8.fu().e(var6.fu()) && !var8.eX() && var8.fr() != var6.fr() && var6.i(var8.fs(), var8.ft())) {
                  L1Magic var10 = new L1Magic(var6, var8);
                  int var11 = var10.c(49);
                  if (var8.bB(73)) {
                     var8.a(var1, var11 * 0.3, true);
                  } else {
                     var8.a(var8.ea() + var11);
                  }
               }
            }
         }

         for (L1NpcInstance var12 : var6.ek().values()) {
            if (var12.fu().e(var6.fu()) && !var12.eX() && var6.i(var12.fs(), var12.ft())) {
               L1Magic var14 = new L1Magic(var6, var12);
               int var15 = var14.c(49);
               var12.a(var12.ea() + var15);
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
