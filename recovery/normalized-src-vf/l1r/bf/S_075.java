package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1TowerInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_Message_YN;
import l1r.bh.L1Skills;

public class S_075 extends L1SkillExecutor {
   private final int a = 75;
   private final L1Skills b = SkillsTable.a().a(75);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         if (var5 == null) {
            this.b(var1, this.b);
         }

         this.a(var7, this.b);
         if (var7.eX() && !var7.bB(75) && var1.fr() != var7.fr() && !(var7 instanceof L1TowerInstance)) {
            for (L1PcInstance var8 : L1World.a().c(var7, 0)) {
               if (!var8.eX()) {
                  this.b(var1, 592);
                  return;
               }
            }

            if (var7 instanceof L1PcInstance) {
               L1PcInstance var10 = (L1PcInstance)var7;
               if (var10.fq().k()) {
                  var10.k(true);
                  var10.am(var1.fr());
                  var10.a(new S_Message_YN(322));
               }
            } else if (var7 instanceof L1NpcInstance) {
               L1NpcInstance var11 = (L1NpcInstance)var7;
               if (!var11.U_().ah() || var11 instanceof L1PetInstance) {
                  var11.j(var11.ew());
                  var11.i_(0);
                  if (var11 instanceof L1PetInstance) {
                     L1PetInstance var12 = (L1PetInstance)var11;
                     var12.aw();
                     var12.u();
                     var12.w();
                  }
               }
            }
         } else {
            this.b(var1, 79);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
