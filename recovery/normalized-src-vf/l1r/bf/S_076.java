package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_SkillHaste;
import l1r.bh.L1Skills;

public class S_076 extends L1SkillExecutor {
   private final int a = 76;
   private final L1Skills b = SkillsTable.a().a(76);

   @Override
   public void a(L1Character var1, int var2) {
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         this.b(var1, this.b);
         this.a(var7, this.b);
         int var8 = this.b.q();

         for (L1Character var10 : this.a(var1, var7, var8)) {
            if (!(var10 instanceof L1PcInstance) || ((L1PcInstance)var10).bU() <= 0) {
               L1Magic var12 = new L1Magic(var1, var10);
               boolean var13 = var12.a(76);
               if (var13) {
                  var10.j(76, this.b.v() * 1000);
                  this.b(var10, 277);
                  switch (var10.fc()) {
                     case 0:
                     case 2:
                        int var19 = 2;
                        int var20 = this.b.v();
                        if (var10 instanceof L1PcInstance) {
                           L1PcInstance var21 = (L1PcInstance)var10;
                           var21.a(new S_SkillHaste(var21.fr(), 2, var20));
                        }

                        var10.b(new S_SkillHaste(var10.fr(), 2, var20));
                        var10.cu(2);
                        break;
                     case 1:
                        int[] var14 = new int[]{43, 54, 1001};
                        int[] var18 = var14;
                        int var17 = var14.length;

                        for (int var16 = 0; var16 < var17; var16++) {
                           int var15 = var18[var16];
                           if (var10.bB(var15)) {
                              var10.bz(var15);
                              var10.cu(0);
                           }
                        }
                  }
               }
            }
         }
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_SkillHaste(var2.fr(), 0, 0));
         var2.b(new S_SkillHaste(var2.fr(), 0, 0));
      }

      var1.cu(0);
   }
}
