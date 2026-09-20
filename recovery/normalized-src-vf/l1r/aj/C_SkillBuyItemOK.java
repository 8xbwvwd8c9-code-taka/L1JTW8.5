package l1r.aj;

import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.be.S_AddSkill;
import l1r.be.S_SkillBuyItem;
import l1r.be.S_SkillSound;
import l1r.bh.L1Skills;
import l1r.bj.ClientThread;

public class C_SkillBuyItemOK extends ClientBasePacket {
   public C_SkillBuyItemOK(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         int var4 = this.d();
         CopyOnWriteArrayList var5 = new CopyOnWriteArrayList<>();

         for (int var6 = 0; var6 < var4; var6++) {
            int var7 = this.b() + 1;
            L1Skills var8 = SkillsTable.a().a(var7);
            int var9 = var8.c();
            if (var3.U() >= var9) {
               int[] var10;
               int[] var11;
               if (var9 == 1) {
                  var10 = new int[]{40503, 40494, 40520, 40519};
                  var11 = new int[]{10, 50, 100, 10};
               } else if (var9 == 2) {
                  var10 = new int[]{40495, 40499, 88, 40505};
                  var11 = new int[]{10, 8, 1, 3};
               } else {
                  if (var9 != 3) {
                     continue;
                  }

                  var10 = new int[]{40508, 40504, 40521, 88};
                  var11 = new int[]{45, 3, 3, 3};
               }

               boolean var12 = true;

               for (int var13 = 0; var13 < var10.length; var13++) {
                  if (!var3.j().g(var10[var13], var11[var13])) {
                     var12 = false;
                     break;
                  }
               }

               if (!var12) {
                  var3.a(new S_SkillBuyItem(var7));
               } else {
                  var5.add(var7);

                  for (int var17 = 0; var17 < var10.length; var17++) {
                     var3.j().b(var10[var17], var11[var17]);
                  }
               }
            }
         }

         if (!var5.isEmpty()) {
            for (int var14 : var5) {
               L1Skills var16 = SkillsTable.a().a(var14);
               SkillsTable.a().a(var3.fr(), var14, var16.b(), 0, 0);
            }

            var3.a(new S_SkillSound(var3.fr(), 224));
            var3.b(new S_SkillSound(var3.fr(), 224));
            var3.a(new S_AddSkill(var3, var5));
         }
      }
   }

   @Override
   public String a() {
      return "C_SkillBuyItemOK";
   }
}
