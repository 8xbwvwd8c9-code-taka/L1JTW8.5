package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_Paralysis;
import l1r.be.S_Poison;
import l1r.bh.L1Skills;

public class S_157 extends L1SkillExecutor {
   private final int a = 157;
   private final L1Skills b = SkillsTable.a().a(157);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         var1.j(157, this.b.v() * 1000);
         this.a(var1, this.b);
         var1.V(true);
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var3 = (L1PcInstance)var1;
            var3.a(new S_Poison(var3.fr(), 2));
            var3.b(new S_Poison(var3.fr(), 2));
            var3.a(new S_Paralysis(4, true));
         } else if (var1 instanceof L1NpcInstance) {
            L1NpcInstance var4 = (L1NpcInstance)var1;
            var4.b(new S_Poison(var4.fr(), 2));
         }
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         boolean var9 = var8.a(157);
         if (var9) {
            var7.j(157, this.b.v() * 1000);
            this.a(var7, this.b);
            var7.V(true);
            if (var7 instanceof L1PcInstance) {
               L1PcInstance var10 = (L1PcInstance)var7;
               var10.a(new S_Poison(var10.fr(), 2));
               var10.b(new S_Poison(var10.fr(), 2));
               var10.a(new S_Paralysis(4, true));
            } else if (var7 instanceof L1NpcInstance) {
               L1NpcInstance var11 = (L1NpcInstance)var7;
               var11.b(new S_Poison(var11.fr(), 2));
            }
         } else {
            this.b(var1, 280);
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.V(false);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_Poison(var2.fr(), 0));
         var2.b(new S_Poison(var2.fr(), 0));
         var2.a(new S_Paralysis(4, false));
      } else if (var1 instanceof L1NpcInstance) {
         L1NpcInstance var3 = (L1NpcInstance)var1;
         var3.b(new S_Poison(var3.fr(), 0));
         var3.V(false);
      }
   }
}
