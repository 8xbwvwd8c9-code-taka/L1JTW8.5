package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_Paralysis;
import l1r.be.S_SkillSound;
import l1r.bh.L1Skills;

public class S_103 extends L1SkillExecutor {
   private final int a = 103;
   private final L1Skills b = SkillsTable.a().a(103);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         var1.b(new S_SkillSound(var1.fr(), this.b.u()));
         var1.j(66, this.b.v() * 1000);
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var3 = (L1PcInstance)var1;
            var3.a(new S_SkillSound(var3.fr(), this.b.u()));
            var3.a(new S_Paralysis(3, true));
         } else if (var1 instanceof L1NpcInstance) {
            L1NpcInstance var4 = (L1NpcInstance)var1;
            var4.U(true);
         }

         this.b(var1, 147);
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         this.b(var1, this.b);
         this.a(var7, this.b);
         L1Magic var8 = new L1Magic(var1, var7);
         if (!var7.bB(66)) {
            boolean var9 = var8.a(103);
            if (var9) {
               var7.b(new S_SkillSound(var7.fr(), this.b.u()));
               var7.j(66, this.b.v() * 1000);
               if (var7 instanceof L1PcInstance) {
                  L1PcInstance var10 = (L1PcInstance)var7;
                  var10.a(new S_SkillSound(var10.fr(), this.b.u()));
                  var10.a(new S_Paralysis(3, true));
               } else if (var7 instanceof L1NpcInstance) {
                  L1NpcInstance var11 = (L1NpcInstance)var7;
                  var11.U(true);
               }

               this.b(var7, 147);
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
   }
}
