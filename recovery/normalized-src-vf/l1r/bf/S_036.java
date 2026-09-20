package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Skills;

public class S_036 extends L1SkillExecutor {
   private final int a = 36;
   private final L1Skills b = SkillsTable.a().a(36);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         this.b(var1, this.b);
         L1Magic var8 = new L1Magic(var1, var7);
         boolean var9 = var8.a(36);
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var10 = (L1PcInstance)var1;
            if (!(var7 instanceof L1MonsterInstance)) {
               this.b(var1, 79);
               return;
            }

            L1MonsterInstance var11 = (L1MonsterInstance)var7;
            if (!var11.U_().v()) {
               this.b(var1, 79);
               return;
            }

            if (!var9) {
               this.b(var1, 280);
               return;
            }

            this.a(var7, this.b);
            int var12 = 0;

            for (L1NpcInstance var13 : var1.ek().values()) {
               var12 += var13.Q();
            }

            int var15 = var1.eC();
            if (var10.A()) {
               var15 = Math.min(var15, 30) + 12;
            } else if (var10.B()) {
               var15 = Math.min(var15, 36) + 6;
            }

            var15 -= var12;
            if (var15 >= 6) {
               L1SummonInstance var17 = new L1SummonInstance(var11, var1, false);
            } else {
               var10.a(new S_ServerMessage(319));
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
