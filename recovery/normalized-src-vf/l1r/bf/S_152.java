package l1r.bf;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_SkillHaste;
import l1r.bh.L1Skills;

public class S_152 extends L1SkillExecutor {
   private final int a = 152;
   private final L1Skills b = SkillsTable.a().a(152);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         var1.j(152, this.b.v() * 1000);
         switch (var1.fc()) {
            case 0:
            case 2:
               int var8 = 2;
               int var9 = this.b.v();
               if (var1 instanceof L1PcInstance) {
                  L1PcInstance var10 = (L1PcInstance)var1;
                  var10.a(new S_SkillHaste(var10.fr(), 2, var9));
               }

               var1.b(new S_SkillHaste(var1.fr(), 2, var9));
               var1.cu(2);
               break;
            case 1:
               int[] var3 = new int[]{43, 54, 1001};
               int[] var7 = var3;
               int var6 = var3.length;

               for (int var5 = 0; var5 < var6; var5++) {
                  int var4 = var7[var5];
                  if (var1.bB(var4)) {
                     var1.bz(var4);
                     var1.cu(0);
                  }
               }
         }

         this.a(var1, this.b);
      }
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         if (var7 instanceof L1PcInstance && ((L1PcInstance)var7).bU() > 0) {
            this.b(var1, 280);
         } else {
            L1Magic var8 = new L1Magic(var1, var7);
            boolean var9 = var8.a(152);
            if (var9) {
               var7.j(152, this.b.v() * 1000);
               switch (var7.fc()) {
                  case 0:
                  case 2:
                     int var15 = 2;
                     int var16 = this.b.v();
                     if (var7 instanceof L1PcInstance) {
                        L1PcInstance var17 = (L1PcInstance)var7;
                        var17.a(new S_SkillHaste(var17.fr(), 2, var16));
                     }

                     var7.b(new S_SkillHaste(var7.fr(), 2, var16));
                     var7.cu(2);
                     break;
                  case 1:
                     int[] var10 = new int[]{43, 54, 1001};
                     int[] var14 = var10;
                     int var13 = var10.length;

                     for (int var12 = 0; var12 < var13; var12++) {
                        int var11 = var14[var12];
                        if (var7.bB(var11)) {
                           var7.bz(var11);
                           var7.cu(0);
                        }
                     }
               }

               this.a(var7, this.b);
            } else {
               this.b(var1, 280);
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
