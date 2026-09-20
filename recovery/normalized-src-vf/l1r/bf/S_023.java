package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1NpcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_SkillSound;
import l1r.bh.L1Skills;

public class S_023 extends L1SkillExecutor {
   private final int a = 23;
   private final L1Skills b = SkillsTable.a().a(23);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (!(var6 instanceof L1NpcInstance)) {
         this.b(var1, 79);
      } else {
         L1NpcInstance var7 = (L1NpcInstance)var6;
         int var8 = var7.U_().r();
         if ((var8 & 1) == 1) {
            var7.b(new S_SkillSound(var2, 2169));
         } else if ((var8 & 2) == 2) {
            var7.b(new S_SkillSound(var2, 2166));
         } else if ((var8 & 4) == 4) {
            var7.b(new S_SkillSound(var2, 2167));
         } else if ((var8 & 8) == 8) {
            var7.b(new S_SkillSound(var2, 2168));
         } else {
            this.b(var1, 79);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
