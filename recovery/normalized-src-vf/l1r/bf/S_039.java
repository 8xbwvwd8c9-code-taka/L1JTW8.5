package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;
import l1r.bi.Random;

public class S_039 extends L1SkillExecutor {
   private final int a = 39;
   private final L1Skills b = SkillsTable.a().a(39);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         if (var7.eX()) {
            this.b(var1, 79);
         } else {
            L1Magic var8 = new L1Magic(var1, var7);
            if (var8.a(39)) {
               this.a(var7, this.b);
               int var9 = Random.a(10) + 5;
               int var10 = var9 + var1.eD() / 2;
               if (var7.eb() < var10) {
                  var10 = var7.eb();
               }

               var8.a(0, var10);
            } else {
               this.b(var1, 280);
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
