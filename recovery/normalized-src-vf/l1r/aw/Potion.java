package l1r.aw;

import java.util.Random;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_CurseBlind;
import l1r.be.S_Liquor;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillBrave;
import l1r.be.S_SkillHaste;
import l1r.be.S_SkillIconBlessOfEva;
import l1r.be.S_SkillSound;
import l1r.bi.CalcStat;

public class Potion {
   public static void a(L1PcInstance var0, int var1, L1ItemInstance var2, boolean var3) {
      if (!var3) {
         var0.j().b(var2, 1);
         var0.a(new S_ServerMessage(79));
      } else {
         int var4 = 1;
         if (var1 == 1016) {
            var4 = 3;
         } else if (var1 == 1017) {
            var4 = 4;
         }

         int var5 = var1 == 1017 ? 7110 : 751;
         int var6 = var2.a().V();
         if (var2.F() == 0) {
            var6 = (int)(var6 * 1.5);
         } else if (var2.F() == 2) {
            var6 = (int)(var6 * 0.5);
         }

         var0.j(var1, var6 * 1000);
         var0.a(new S_SkillBrave(var0.fr(), var4, var6));
         var0.b(new S_SkillBrave(var0.fr(), var4, var6));
         var0.a(new S_SkillSound(var0.fr(), var5));
         var0.b(new S_SkillSound(var0.fr(), var5));
         var0.cv(var4);
         var0.j().b(var2, 1);
      }
   }

   public static void a(L1PcInstance var0, L1ItemInstance var1, int var2) {
      if (var0.bB(1038)) {
         var0.a(new S_ServerMessage(79));
      } else {
         if (var1.F() == 0) {
            var2 = (int)(var2 * 1.5);
         } else if (var1.F() == 2) {
            var2 = (int)(var2 * 0.5);
         }

         var0.j(1027, var2 * 1000);
         var0.a(new S_Liquor(var0.fr(), 8));
         var0.b(new S_Liquor(var0.fr(), 8));
         var0.a(new S_SkillSound(var0.fr(), 7976));
         var0.b(new S_SkillSound(var0.fr(), 7976));
         var0.a(new S_ServerMessage(1065));
         var0.j().b(var1, 1);
      }
   }

   public static void a(L1PcInstance var0, L1ItemInstance var1) {
      int var2 = 189;
      if (var1.a().aP() == 24) {
         var2 = 194;
      } else if (var1.a().aP() == 25) {
         var2 = 197;
      }

      int var3 = var1.a().V();
      if (var1.F() == 0) {
         var3 = (int)(var3 * 1.5);
      } else if (var1.F() == 2) {
         var3 = (int)(var3 * 0.5);
      }

      var3 = (int)(var3 * (new Random().nextGaussian() / 5.0 + 1.0));
      var3 += var0.dz() + CalcStat.l(var0.bg(), var0.eA());
      if (var0.bB(173)) {
         var3 /= 2;
      }

      var0.a(var0.ea() + var3);
      var0.a(new S_SkillSound(var0.fr(), var2));
      var0.b(new S_SkillSound(var0.fr(), var2));
      var0.a(new S_ServerMessage(77));
      var0.j().b(var1, 1);
   }

   public static void b(L1PcInstance var0, L1ItemInstance var1) {
      int var2 = var1.a().V();
      if (var1.F() == 0) {
         var2 = (int)(var2 * 1.5);
      } else if (var1.F() == 2) {
         var2 = (int)(var2 * 0.5);
      }

      var2 -= l1r.bi.Random.a(var2 / 3);
      var2 += CalcStat.n(var0.bk(), var0.eE());
      var0.a(new S_SkillSound(var0.fr(), 190));
      var0.b(new S_SkillSound(var0.fr(), 190));
      var0.a(new S_ServerMessage(338, "$1084"));
      var0.i_(var0.eb() + var2);
      var0.j().b(var1, 1);
   }

   public static void c(L1PcInstance var0, L1ItemInstance var1) {
      int var2 = var1.a().V();
      if (var1.F() == 0) {
         var2 = (int)(var2 * 1.5);
      } else if (var1.F() == 2) {
         var2 = (int)(var2 * 0.5);
      }

      var0.a(new S_SkillSound(var0.fr(), 191));
      var0.b(new S_SkillSound(var0.fr(), 191));
      if (var0.bU() <= 0) {
         var0.e(false);
         switch (var0.fc()) {
            case 0:
            case 1:
               var0.j(1001, var2 * 1000);
               var0.cu(1);
               var0.a(new S_SkillHaste(var0.fr(), 1, var2));
               var0.b(new S_SkillHaste(var0.fr(), 1, var2));
               break;
            case 2:
               int[] var3 = new int[]{29, 76, 152};
               int[] var7 = var3;
               int var6 = var3.length;

               for (int var5 = 0; var5 < var6; var5++) {
                  int var4 = var7[var5];
                  if (var0.bB(var4)) {
                     var0.bz(var4);
                  }
               }
         }

         var0.j().b(var1, 1);
      }
   }

   public static void d(L1PcInstance var0, L1ItemInstance var1) {
      int var2 = var1.a().V();
      if (var1.F() == 0) {
         var2 = (int)(var2 * 1.5);
      } else if (var1.F() == 2) {
         var2 = (int)(var2 * 0.5);
      }

      var0.a(new S_SkillSound(var0.fr(), 190));
      var0.b(new S_SkillSound(var0.fr(), 190));
      var0.a(new S_ServerMessage(1007));
      var0.a(new S_PacketBox(34, var2));
      var0.j(1002, var2 * 1000);
      var0.j().b(var1, 1);
   }

   public static void e(L1PcInstance var0, L1ItemInstance var1) {
      var0.j().b(var1, 1);
      if (!var0.B() && !var0.E()) {
         var0.a(new S_ServerMessage(79));
      } else {
         int var2 = var1.a().V();
         if (var1.F() == 0) {
            var2 = (int)(var2 * 1.5);
         } else if (var1.F() == 2) {
            var2 = (int)(var2 * 0.5);
         }

         if (!var0.bB(1004)) {
            var0.cp(2);
         }

         var0.a(new S_PacketBox(57, var2));
         var0.a(new S_SkillSound(var0.fr(), 750));
         var0.b(new S_SkillSound(var0.fr(), 750));
         var0.j(1004, var2 * 1000);
      }
   }

   public static void f(L1PcInstance var0, L1ItemInstance var1) {
      int var2 = var1.a().V();
      if (var1.F() == 0) {
         var2 = (int)(var2 * 1.5);
      } else if (var1.F() == 2) {
         var2 = (int)(var2 * 0.5);
      }

      if (var0.bB(1003)) {
         var2 += var0.bC(1003);
         var2 = Math.min(var2, 7200);
      }

      var0.a(new S_SkillIconBlessOfEva(var0.fr(), var2));
      var0.a(new S_SkillSound(var0.fr(), 190));
      var0.b(new S_SkillSound(var0.fr(), 190));
      var0.j(1003, var2 * 1000);
      var0.j().b(var1, 1);
   }

   public static void g(L1PcInstance var0, L1ItemInstance var1) {
      if (var0.bB(1012)) {
         var0.a(new S_CurseBlind(2));
      } else {
         var0.a(new S_CurseBlind(1));
      }

      var0.j(20, 16000);
      var0.j().b(var1, 1);
   }
}
