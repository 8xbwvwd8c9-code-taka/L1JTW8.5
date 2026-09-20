package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1TowerInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_DoActionGFX;
import l1r.bh.L1Skills;

public class S_035 extends L1SkillExecutor {
   private final int a = 35;
   private final L1Skills b = SkillsTable.a().a(35);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character && !(var6 instanceof L1TowerInstance) && !(var6 instanceof L1DoorInstance)) {
         L1Character var7 = (L1Character)var6;
         this.a(var7, this.b);
         this.b(var1, this.b);
         L1Magic var8 = new L1Magic(var1, var7);
         if (var7 instanceof L1MonsterInstance && ((L1MonsterInstance)var7).U_().B() == 1) {
            int var11 = var8.b(35);
            var8.a(var11, 0);
            var7.b(new S_DoActionGFX(var7.fr(), 2));
         } else {
            int var9 = var8.c(35);
            if (var7.bB(73) && var7 instanceof L1PcInstance) {
               L1PcInstance var10 = (L1PcInstance)var7;
               var10.a(var1, var9 * 0.3, true);
            } else {
               var7.a(var7.ea() + var9);
               this.b(var7, 77);
            }
         }
      } else {
         this.b(var1, 79);
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
