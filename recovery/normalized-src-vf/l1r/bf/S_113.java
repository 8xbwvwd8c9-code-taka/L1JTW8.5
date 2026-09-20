package l1r.bf;

import l1r.ao.ClanTable;
import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Clan;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_TrueTarget;
import l1r.bh.L1Skills;

public class S_113 extends L1SkillExecutor {
   private final int a = 113;
   private final L1Skills b = SkillsTable.a().a(113);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         var7.j(113, this.b.v() * 1000);
         this.b(var1, this.b);
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var8 = (L1PcInstance)var1;
            if (var8.aF() != 0) {
               L1Clan var9 = ClanTable.a().a(var8.aF());

               for (L1PcInstance var10 : var9.b()) {
                  var10.a(new S_TrueTarget(var2, true));
               }
            } else {
               var8.a(new S_TrueTarget(var2, true));
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.b(new S_TrueTarget(var1.fr(), false));
   }
}
