package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_OwnCharStatus;
import l1r.bh.L1Skills;

public class S_193 extends L1SkillExecutor {
   private final int a = 193;
   private final L1Skills b = SkillsTable.a().a(193);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         var2 = this.b.v();
         this.a(var1, this.b);
      } else if (var2 == 0) {
         return;
      }

      if (!var1.bB(193)) {
         var1.bN(-5);
         var1.bV(-5);
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var3 = (L1PcInstance)var1;
            var3.a(new S_OwnCharStatus(var3));
         }
      }

      var1.j(193, var2 * 1000);
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         boolean var9 = var8.a(193);
         if (var9 && !var7.bB(193)) {
            var7.j(193, this.b.v() * 1000);
            this.a(var7, this.b);
            var7.bN(-5);
            var7.bV(-5);
            if (var7 instanceof L1PcInstance) {
               L1PcInstance var10 = (L1PcInstance)var7;
               var10.a(new S_OwnCharStatus(var10));
            }
         } else {
            this.b(var1, 280);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.bN(5);
      var1.bV(5);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_OwnCharStatus(var2));
      }
   }
}
