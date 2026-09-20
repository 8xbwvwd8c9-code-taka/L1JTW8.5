package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_SkillHaste;
import l1r.bh.L1Skills;

public class S_054 extends L1SkillExecutor {
   private final int a = 54;
   private final L1Skills b = SkillsTable.a().a(54);

   @Override
   public void a(L1Character var1, int var2) {
      if (var1 instanceof L1PcInstance && ((L1PcInstance)var1).bU() > 0) {
         this.a(var1, this.b);
      } else {
         switch (var1.fc()) {
            case 0:
            case 1:
               if (var2 == 0) {
                  var2 = this.b.v();
                  this.a(var1, this.b);
               }

               var1.j(54, var2 * 1000);
               int var3 = var1.fc() == 1 ? 183 : 184;
               this.b(var1, var3);
               int var4 = 1;
               if (var1 instanceof L1PcInstance) {
                  L1PcInstance var10 = (L1PcInstance)var1;
                  var10.e(false);
                  var10.a(new S_SkillHaste(var10.fr(), 1, var2));
               }

               var1.b(new S_SkillHaste(var1.fr(), 1, var2));
               var1.cu(1);
               break;
            case 2:
               int[] var5 = new int[]{29, 76, 152};
               int[] var9 = var5;
               int var8 = var5.length;

               for (int var7 = 0; var7 < var8; var7++) {
                  int var6 = var9[var7];
                  if (var1.bB(var6)) {
                     var1.bz(var6);
                     var1.cu(0);
                  }
               }

               this.a(var1, this.b);
         }
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      if (var1 instanceof L1PcInstance && ((L1PcInstance)var1).bU() > 0) {
         this.a(var1, this.b);
      } else {
         switch (var1.fc()) {
            case 0:
            case 1:
               int var6 = var1.fc() == 1 ? 183 : 184;
               this.b(var1, var6);
               int var7 = 1;
               if (var1 instanceof L1PcInstance) {
                  L1PcInstance var13 = (L1PcInstance)var1;
                  if (var13.bU() > 0) {
                     return;
                  }

                  var13.e(false);
                  var13.a(new S_SkillHaste(var13.fr(), 1, this.b.v()));
               }

               var1.b(new S_SkillHaste(var1.fr(), 1, this.b.v()));
               var1.cu(1);
               break;
            case 2:
               int[] var8 = new int[]{29, 76, 152};
               int[] var12 = var8;
               int var11 = var8.length;

               for (int var10 = 0; var10 < var11; var10++) {
                  int var9 = var12[var10];
                  if (var1.bB(var9)) {
                     var1.bz(var9);
                     var1.cu(0);
                  }
               }
         }

         this.a(var1, this.b);
      }
   }

   @Override
   public void a(L1Character var1) {
      var1.cu(0);
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)var1;
         var2.a(new S_SkillHaste(var2.fr(), 0, 0));
         var2.b(new S_SkillHaste(var2.fr(), 0, 0));
      }
   }
}
