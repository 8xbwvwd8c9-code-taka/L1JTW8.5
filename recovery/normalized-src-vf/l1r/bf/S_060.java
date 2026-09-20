package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1World;
import l1r.be.S_Invis;
import l1r.be.S_OtherCharPacks;
import l1r.be.S_RemoveObject;
import l1r.be.S_TrueTarget;
import l1r.bh.L1Skills;

public class S_060 extends L1SkillExecutor {
   private final int a = 60;
   private final L1Skills b = SkillsTable.a().a(60);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         var1.j(60, this.b.v() * 1000);
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var3 = (L1PcInstance)var1;
            var3.a(new S_Invis(var3.fr(), 1));

            for (L1PcInstance var4 : L1World.a().f(var3)) {
               if (var4.bB(26003)) {
                  var4.a(new S_TrueTarget(var3.fr(), true));
               } else {
                  var4.a(new S_RemoveObject(var3));
               }
            }
         }
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      var1.j(60, this.b.v() * 1000);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var6 = (L1PcInstance)var1;
         var6.a(new S_Invis(var6.fr(), 1));

         for (L1PcInstance var7 : L1World.a().f(var6)) {
            if (var7.bB(26003)) {
               var7.a(new S_TrueTarget(var6.fr(), true));
            } else {
               var7.a(new S_RemoveObject(var6));
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_Invis(var2.fr(), 0));

         for (L1PcInstance var3 : L1World.a().f(var2)) {
            if (var3.bB(26003)) {
               var3.a(new S_TrueTarget(var2.fr(), false));
            } else {
               var3.a(new S_OtherCharPacks(var2));
            }
         }
      }
   }
}
