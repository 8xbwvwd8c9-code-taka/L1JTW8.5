package l1r.bf;

import java.util.HashMap;
import l1r.ao.SkillsTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1World;
import l1r.be.S_ChangeName;
import l1r.be.S_DoActionShop;
import l1r.be.S_NpcChangeShape;
import l1r.be.S_SkillBrave;
import l1r.be.S_SkillHaste;
import l1r.bg.L1SkillTimer__obf_d;
import l1r.bh.L1Skills;

public class S_044 extends L1SkillExecutor {
   private final int a = 44;
   private final L1Skills b = SkillsTable.a().a(44);

   @Override
   public void a(L1Character var1, int var2) {
      if (var2 == -1) {
         this.a(var1, this.b);
      } else if (var2 == 0) {
         return;
      }

      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         L1PolyMorph.b(var3);
         if (var3.bU() > 0) {
            var3.cu(0);
            var3.a(new S_SkillHaste(var3.fr(), 0, 0));
            var3.b(new S_SkillHaste(var3.fr(), 0, 0));
         }

         if (var3.aX()) {
            var3.a(new S_DoActionShop(var3.fr(), 70, var3.aW()));
            var3.b(new S_DoActionShop(var3.fr(), 70, var3.aW()));
         }
      }

      HashMap var6 = new HashMap<>(var1.eh());

      for (int var4 : var6.keySet()) {
         if (var4 < 3999 && !a(var4)) {
            var1.bz(var4);
         }
      }

      var1.en();
      var1.ef();
   }

   @Override
   public void a(L1Character var1, int var2, int var3, int var4, String var5) {
      this.b(var1, this.b);
      L1Object var6 = L1World.a().a(var2);
      if (var6 instanceof L1Character) {
         L1Character var7 = (L1Character)var6;
         L1Magic var8 = new L1Magic(var1, var7);
         if (var8.a(44)) {
            if (var7 instanceof L1PcInstance) {
               L1PcInstance var9 = (L1PcInstance)var7;
               if (var9.bU() > 0) {
                  var9.cu(0);
                  var9.a(new S_SkillHaste(var9.fr(), 0, 0));
                  var9.b(new S_SkillHaste(var9.fr(), 0, 0));
               }

               if (var9.aX()) {
                  var9.a(new S_DoActionShop(var9.fr(), 70, var9.aW()));
                  var9.b(new S_DoActionShop(var9.fr(), 70, var9.aW()));
               }
            } else if (var7 instanceof L1NpcInstance) {
               L1NpcInstance var12 = (L1NpcInstance)var7;
               this.a(var12);
               var12.cu(0);
               var12.cv(0);
               var12.b(new S_SkillHaste(var7.fr(), 0, 0));
               var12.b(new S_SkillBrave(var7.fr(), 0, 0));
               var12.h(false);
               var12.V(false);
               var12.l(0);
            }

            HashMap var13 = new HashMap<>(var7.eh());

            for (int var10 : var13.keySet()) {
               if (var10 < 3999 && !a(var10)) {
                  var7.bz(var10);
               }
            }

            var7.en();
            var7.ef();
            this.a(var7, this.b);
         } else {
            this.b(var1, 280);
         }
      }
   }

   private void a(L1NpcInstance var1) {
      int var2 = var1.U_().b();
      if (var2 == 71092 && var1.G() == var1.fe()) {
         var1.cw(1314);
         var1.b(new S_NpcChangeShape(var1.fr(), 1314, var1.fa(), var1.eY()));
      }

      if (var2 == 45640) {
         if (var1.G() == var1.fe()) {
            var1.a(var1.ew());
            var1.cw(2332);
            var1.b(new S_NpcChangeShape(var1.fr(), 2332, var1.fa(), var1.eY()));
            var1.e("$2103");
            var1.a("$2103");
            var1.b(new S_ChangeName(var1.fr(), "$2103"));
         } else if (var1.fe() == 2332) {
            var1.a(var1.ew());
            var1.cw(2755);
            var1.b(new S_NpcChangeShape(var1.fr(), 2755, var1.fa(), var1.eY()));
            var1.e("$2488");
            var1.a("$2488");
            var1.b(new S_ChangeName(var1.fr(), "$2488"));
         }
      }

      if (var2 == 81209 && var1.G() == var1.fe()) {
         var1.cw(4310);
         var1.b(new S_NpcChangeShape(var1.fr(), 4310, var1.fa(), var1.eY()));
      }

      if (var2 == 81352 && var1.G() == var1.fe()) {
         var1.cw(148);
         var1.b(new S_NpcChangeShape(var1.fr(), 148, var1.fa(), var1.eY()));
         var1.e("$6068");
         var1.a("$6068");
         var1.b(new S_ChangeName(var1.fr(), "$6068"));
      }
   }

   private static boolean a(int var0) {
      int[] var1 = new int[]{121, 12, 21, 8, 26, 42, 78, 79, 107, 99, 106, 111, 87, 88, 89, 90, 91, 185, 190, 195, 204, 209, 214, 219, 226, 1005};
      if (var0 > 600 && var0 < 620) {
         return true;
      }

      int[] var5 = var1;
      int var4 = var1.length;

      for (int var3 = 0; var3 < var4; var3++) {
         int var2 = var5[var3];
         if (var0 == var2) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void a(L1Character var1) {
   }
}
