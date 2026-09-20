package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_Paralysis;
import l1r.be.S_SkillSound;
import l1r.bh.L1Skills;

public class S_066 extends L1SkillExecutor {
   private final int a = 66;
   private final L1Skills b = SkillsTable.a().a(66);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         this.b(var1, this.b);
         this.a(var7, this.b);
         int var8 = this.b.q();

         for (L1Character var10 : this.a(var1, var7, var8)) {
            L1Magic var12 = new L1Magic(var1, var10);
            if (var12.a(66)) {
               var10.j(66, this.b.v() * 1000);
               var10.U(true);
               if (var10 instanceof L1PcInstance) {
                  L1PcInstance var13 = (L1PcInstance)var10;
                  var13.a(new S_Paralysis(3, true));
                  var13.a(new S_SkillSound(var13.fr(), this.b.u()));
               }

               var10.b(new S_SkillSound(var10.fr(), this.b.u()));
            } else {
               this.b(var10, 297);
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.U(false);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_Paralysis(3, false));
      }
   }
}
