package l1r.bf;

import l1r.ao.ClanTable;
import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Clan;
import l1r.be.S_Message_YN;
import l1r.bh.L1Skills;

public class S_119 extends L1SkillExecutor {
   private final int a = 119;
   private final L1Skills b = SkillsTable.a().a(119);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         if (var6.aF() == 0 || !var6.fq().k()) {
            return;
         }

         try {
            Thread.sleep(1300L);
            var6.a(0);
            var6.b((L1Character)null);
         } catch (Exception var10) {
            return;
         }

         L1Clan var7 = ClanTable.a().a(var6.aF());

         for (L1PcInstance var8 : var7.b()) {
            if (var8.eX() && var1.fr() != var8.fr()) {
               var8.k(false);
               var8.am(var1.fr());
               var8.a(new S_Message_YN(322));
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
