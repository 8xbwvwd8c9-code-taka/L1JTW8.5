package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_207 extends L1SkillExecutor {
   private final int a = 207;
   private final L1Skills b = SkillsTable.a().a(207);

   public void b(L1Character var1, L1Character var2, int var3) {
      if (var3 == -1) {
         if (var2.eb() >= 5) {
            var2.i_(var2.eb() - 5);
            int var4 = var1.eE() * 5;
            if (var2 instanceof L1PcInstance) {
               ((L1PcInstance)var2).a(var1, var4, true);
            } else if (var2 instanceof L1NpcInstance) {
               ((L1NpcInstance)var2).b(var1, var4);
            }
         }

         this.a(var2, this.b);
      }
   }

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         int var8 = 0;
         if (var7.eb() >= 5) {
            L1Magic var9 = new L1Magic(var1, var7);
            var8 = var9.b(207);
            var9.a(var8, 0);
            this.a(var7, this.b);
         } else {
            this.b(var1, 280);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
