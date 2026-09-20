package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1MonsterInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1Skills;

public class S_018 extends L1SkillExecutor {
   private final int a = 18;
   private final L1Skills b = SkillsTable.a().a(18);

   public void b(L1Character var1, L1Character var2, int var3) {
      if (var3 == -1 && var2 instanceof L1MonsterInstance) {
         L1MonsterInstance var4 = (L1MonsterInstance)var2;
         int var5 = var4.U_().B();
         if (var5 == 1 || var5 == 3) {
            int var6 = var4.ea();
            var4.b(var1, var6);
            this.a(var4, this.b);
         }
      }
   }

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (!(var6 instanceof L1MonsterInstance)) {
         this.b(var1, 280);
      } else {
         L1MonsterInstance var7 = (L1MonsterInstance)var6;
         int var8 = var7.U_().B();
         if (var8 != 1 && var8 != 3) {
            this.b(var1, 280);
         } else {
            L1Magic var9 = new L1Magic(var1, var7);
            boolean var10 = var9.a(18);
            if (var10) {
               int var11 = var7.ea();
               var9.a(var11, 0);
               this.a(var7, this.b);
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
