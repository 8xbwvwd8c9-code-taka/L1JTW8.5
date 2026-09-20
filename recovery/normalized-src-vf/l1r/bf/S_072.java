package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ao.TrapSpawnTable;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_DoActionGFX;
import l1r.bh.L1Skills;

public class S_072 extends L1SkillExecutor {
   private final int a = 72;
   private final L1Skills b = SkillsTable.a().a(72);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      this.a(var1, this.b);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         if (!var6.aA() && var6.ff()) {
            var6.s();
            var6.O();
         }

         for (L1Object var8 : L1World.a().b(var1, this.b.q())) {
            if (var8 instanceof L1Character) {
               L1Character var10 = (L1Character)var8;
               boolean var11 = false;
               if (var10 instanceof L1PcInstance) {
                  L1PcInstance var12 = (L1PcInstance)var10;
                  if (!var12.aA() && var12.ff()) {
                     var12.s();
                     var12.O();
                     if (!var6.a(var6, var12, true)) {
                        var11 = true;
                     }
                  }
               } else if (var10 instanceof L1MonsterInstance) {
                  L1MonsterInstance var15 = (L1MonsterInstance)var10;
                  if (var15.ac() == 1) {
                     var15.e(var6);
                     var11 = true;
                  }
               }

               if (var11) {
                  L1Magic var16 = new L1Magic(var1, var10);
                  int var13 = var16.b(72);
                  var16.a(var13, 0);
                  if (var13 > 0) {
                     var10.b(new S_DoActionGFX(var10.fr(), 2));
                     if (var10 instanceof L1PcInstance) {
                        L1PcInstance var14 = (L1PcInstance)var10;
                        var14.a(new S_DoActionGFX(var14.fr(), 2));
                     }
                  }
               }
            }
         }

         TrapSpawnTable.a().b(var6);
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
