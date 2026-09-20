package l1r.al;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.be.S_AddSkill;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_SkillSound;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Skills;

public class L1AddSkill implements L1CommandExecutor {
   private static final Logger a = Logger.getLogger(L1AddSkill.class.getName());

   private L1AddSkill() {
   }

   public static L1CommandExecutor a() {
      return new L1AddSkill();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         String var4 = "";
         int var5 = 0;
         int var6 = var1.fr();
         var1.a(new S_SkillSound(var6, 227));
         var1.b(new S_SkillSound(var6, 227));
         CopyOnWriteArrayList var7 = new CopyOnWriteArrayList<>();
         if (var1.x()) {
            for (int var55 = 1; var55 <= 16; var55++) {
               L1Skills var69 = SkillsTable.a().a(var55);
               var4 = var69.b();
               var5 = var69.a();
               SkillsTable.a().a(var6, var5, var4, 0, 0);
               var7.add(var55);
            }

            for (int var56 = 113; var56 <= 125; var56++) {
               L1Skills var70 = SkillsTable.a().a(var56);
               var4 = var70.b();
               var5 = var70.a();
               SkillsTable.a().a(var6, var5, var4, 0, 0);
               var7.add(var56);
            }
         } else if (var1.z()) {
            for (int var53 = 1; var53 <= 8; var53++) {
               L1Skills var67 = SkillsTable.a().a(var53);
               var4 = var67.b();
               var5 = var67.a();
               SkillsTable.a().a(var6, var5, var4, 0, 0);
               var7.add(var53);
            }

            for (int var54 = 87; var54 <= 92; var54++) {
               L1Skills var68 = SkillsTable.a().a(var54);
               var4 = var68.b();
               var5 = var68.a();
               SkillsTable.a().a(var6, var5, var4, 0, 0);
               var7.add(var54);
            }
         } else if (var1.A()) {
            for (int var51 = 1; var51 <= 48; var51++) {
               L1Skills var65 = SkillsTable.a().a(var51);
               var4 = var65.b();
               var5 = var65.a();
               SkillsTable.a().a(var6, var5, var4, 0, 0);
               var7.add(var51);
            }

            for (int var52 = 129; var52 <= 176; var52++) {
               L1Skills var66 = SkillsTable.a().a(var52);
               var4 = var66.b();
               var5 = var66.a();
               SkillsTable.a().a(var6, var5, var4, 0, 0);
               var7.add(var52);
            }
         } else if (var1.B()) {
            for (int var50 = 1; var50 <= 80; var50++) {
               L1Skills var64 = SkillsTable.a().a(var50);
               var4 = var64.b();
               var5 = var64.a();
               SkillsTable.a().a(var6, var5, var4, 0, 0);
               var7.add(var50);
            }
         } else if (var1.C()) {
            for (int var47 = 1; var47 <= 16; var47++) {
               L1Skills var61 = SkillsTable.a().a(var47);
               var4 = var61.b();
               var5 = var61.a();
               SkillsTable.a().a(var6, var5, var4, 0, 0);
               var7.add(var47);
            }

            for (int var48 = 97; var48 <= 112; var48++) {
               L1Skills var62 = SkillsTable.a().a(var48);
               var4 = var62.b();
               var5 = var62.a();
               SkillsTable.a().a(var6, var5, var4, 0, 0);
               var7.add(var48);
            }

            L1Skills var49 = SkillsTable.a().a(233);
            var4 = var49.b();
            var5 = var49.a();
            SkillsTable.a().a(var6, var5, var4, 0, 0);
            var7.add(233);
            L1Skills var63 = SkillsTable.a().a(609);
            var4 = var63.b();
            var5 = var63.a();
            SkillsTable.a().a(var6, var5, var4, 0, 0);
            var1.a(new S_ProtoBuffers(402, 9));
         } else if (var1.D()) {
            for (int var46 = 181; var46 <= 196; var46++) {
               L1Skills var60 = SkillsTable.a().a(var46);
               var4 = var60.b();
               var5 = var60.a();
               SkillsTable.a().a(var6, var5, var4, 0, 0);
               var7.add(var46);
            }
         } else if (var1.E()) {
            for (int var45 = 201; var45 <= 222; var45++) {
               L1Skills var59 = SkillsTable.a().a(var45);
               var4 = var59.b();
               var5 = var59.a();
               SkillsTable.a().a(var6, var5, var4, 0, 0);
               var7.add(var45);
            }
         } else if (var1.F()) {
            for (int var8 = 1; var8 <= 8; var8++) {
               L1Skills var9 = SkillsTable.a().a(var8);
               var4 = var9.b();
               var5 = var9.a();
               SkillsTable.a().a(var6, var5, var4, 0, 0);
               var7.add(var8);
            }

            for (int var43 = 225; var43 <= 231; var43++) {
               L1Skills var57 = SkillsTable.a().a(var43);
               var4 = var57.b();
               var5 = var57.a();
               SkillsTable.a().a(var6, var5, var4, 0, 0);
               var7.add(var43);
            }

            for (int var44 = 601; var44 <= 608; var44++) {
               if (var44 != 604) {
                  L1Skills var58 = SkillsTable.a().a(var44);
                  var4 = var58.b();
                  var5 = var58.a();
                  SkillsTable.a().a(var6, var5, var4, 0, 0);
                  var1.a(new S_ProtoBuffers(402, var44 - 600));
               }
            }
         }

         var1.a(new S_AddSkill(var1, var7));
      } catch (Exception var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
         var1.a(new S_SystemMessage(var2 + " 指令錯誤。"));
      }
   }
}
