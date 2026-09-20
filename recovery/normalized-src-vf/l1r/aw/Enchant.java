package l1r.aw;

import l1r.ao.HistoryTable;
import l1r.ao.ItemTable;
import l1r.ap.L1DollInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class Enchant {
   public static void a(L1PcInstance var0, L1ItemInstance var1, L1ItemInstance var2) {
      if (var2 == null) {
         var0.a(new S_ServerMessage(156));
      } else if (var2.a().aP() != 17 && var2.a().aP() != 22) {
         var0.a(new S_ServerMessage(2477));
      } else if (var2.a().aP() != 22) {
         var0.a(new S_ServerMessage(79));
      } else if (var0.O(var2.fr())) {
         var0.a(new S_ServerMessage(1181));
      } else {
         int var3 = Random.a(100) + 1;
         int var4 = 55;
         int var5 = 10;
         int var6 = 0;
         if (var3 >= 45) {
            int[] var7 = new int[]{47142, 47148, 47154, 47160, 47166, 47172, 47178, 47184, 47190, 640430, 640436};
            int[] var11 = var7;
            int var10 = var7.length;

            for (int var9 = 0; var9 < var10; var9++) {
               int var8 = var11[var9];
               if (var2.N() == var8) {
                  var0.a(new S_ServerMessage(79));
                  return;
               }
            }

            var6 = var2.N() + 1;
            var0.a(new S_ServerMessage(403, var2.b()));
         } else if (var3 <= 10) {
            int[] var12 = new int[]{47137, 47143, 47149, 47155, 47161, 47167, 47173, 47179, 47185, 640425, 640431};
            int[] var18 = var12;
            int var17 = var12.length;

            for (int var16 = 0; var16 < var17; var16++) {
               int var14 = var18[var16];
               if (var2.N() == var14) {
                  var0.a(new S_ServerMessage(79));
                  return;
               }
            }

            var6 = var2.N() - 1;
            var0.a(new S_ServerMessage(403, var2.b()));
         } else {
            var0.a(new S_ServerMessage(79));
         }

         if (var6 > 0) {
            ItemTable.a(var0, var6, 1);

            for (L1DollInstance var13 : var0.el().values()) {
               if (var13.f() == var2.fr()) {
                  var13.e();
               }
            }

            var0.j().f(var2);
         }

         var0.j().b(var1, 1);
      }
   }

   private static boolean a(int var0, int var1) {
      int[] var2 = new int[]{49311, 40660, 40128, 49312, 40127};
      int[][] var3 = new int[][]{
         {311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321},
         {246, 247, 248, 249},
         {36, 183, 250, 251, 252, 253, 254, 255},
         {20028, 20082, 20126, 20173, 20206, 20232, 21138, 21184, 21185, 21186, 21187, 21188, 21189, 21190, 21191, 21192, 21193, 21194, 21195},
         {20161, 21035, 21038}
      };

      for (int var4 = 0; var4 < var2.length; var4++) {
         int[] var8;
         int var7 = (var8 = var3[var4]).length;

         for (int var6 = 0; var6 < var7; var6++) {
            int var5 = var8[var6];
            if (var1 == var5) {
               if (var0 == var2[var4]) {
                  return true;
               }

               return false;
            }
         }

         if (var0 == var2[var4]) {
            return false;
         }
      }

      return true;
   }

   public static void b(L1PcInstance var0, L1ItemInstance var1, L1ItemInstance var2) {
      if (var2 == null) {
         var0.a(new S_ServerMessage(156));
      } else {
         int var3 = var1.N();
         int var4 = var2.a().x();
         if (!var2.g() || var4 < 0 || var2.F() >= 128 || !a(var3, var2.N())) {
            var0.a(new S_ServerMessage(79));
         } else if (var3 == 49311 && var2.G() >= var4) {
            var0.a(new S_ServerMessage(1453));
         } else {
            int var5 = var2.G();
            if (var1.F() == 2) {
               if (var5 < -6) {
                  a(var0, var2);
               } else {
                  a(var0, var2, -1);
               }
            } else if (var5 < var4) {
               a(var0, var2, a(var1.F() == 0, var2));
            } else {
               int var6 = Random.a(100) + 1;
               int var7 = (100 + 3 * Config.G) / (var5 >= 10 ? 6 : 3);
               if (var3 == 640142) {
                  var7 = (int)(var7 * 2.5);
               }

               if (var6 < var7) {
                  a(var0, var2, a(var1.F() == 0, var2));
               } else if ((var5 < 9 || var6 >= var7 * 2) && (var3 < 640370 || var3 > 640372)) {
                  a(var0, var2);
               } else {
                  var0.a(new S_ServerMessage(160, var2.s(), "$245", "$248"));
               }
            }

            var0.j().b(var1, 1);
         }
      }
   }

   public static void c(L1PcInstance var0, L1ItemInstance var1, L1ItemInstance var2) {
      if (var2 == null) {
         var0.a(new S_ServerMessage(156));
      } else {
         int var3 = var1.N();
         int var4 = var2.a().x();
         if (!var2.h() || var4 < 0 || var2.F() >= 128 || !a(var3, var2.N())) {
            var0.a(new S_ServerMessage(79));
         } else if (var2.i() || var2.j()) {
            var0.a(new S_ServerMessage(79));
         } else if (var3 == 49312 && var2.G() >= var4) {
            var0.a(new S_ServerMessage(1453));
         } else {
            int var5 = var2.G();
            if (var1.F() == 2) {
               if (var5 < -4) {
                  a(var0, var2);
               } else {
                  a(var0, var2, -1);
               }
            } else if (var5 < var4) {
               a(var0, var2, a(var1.F() == 0, var2));
            } else {
               int var6 = Random.a(100) + 1;
               int var7 = var5 + (var4 == 0 ? 2 : 0);
               int var8 = (100 + var7 * Config.H) / var7 * (var5 >= 9 ? 2 : 1);
               if (var3 == 640141) {
                  var8 = (int)(var8 * 2.5);
               }

               if (var6 < var8) {
                  a(var0, var2, a(var1.F() == 0, var2));
               } else if (var5 >= 9 && var6 < var8 * 2) {
                  var0.a(new S_ServerMessage(160, var2.s(), "$252", "$248"));
               } else {
                  a(var0, var2);
               }
            }

            var0.j().b(var1, 1);
         }
      }
   }

   public static void a(L1PcInstance var0, L1ItemInstance var1, L1ItemInstance var2, boolean var3) {
      if (var2 == null) {
         var0.a(new S_ServerMessage(156));
      } else {
         int var4 = var2.G();
         if (!var2.i() || var2.F() >= 128 || var4 >= 10) {
            var0.a(new S_ServerMessage(79));
         } else if (!var3 && var2.a().x() <= -1) {
            var0.a(new S_ServerMessage(1453));
         } else {
            int var5 = Random.a(100) + 1;
            int var6 = 45 + (50 + var4) / (var4 + 1);
            if (var1.F() == 0) {
               var6 += 5;
            } else if (var1.F() == 2) {
               var6 -= 5;
            }

            var0.j().b(var1, 1);
            if (var5 < var6) {
               a(var0, var2, 1);
            } else {
               if (var1.N() != 640614 && var1.N() != 640727) {
                  a(var0, var2);
               } else {
                  f(var0, var1, var2);
               }
            }
         }
      }
   }

   private static void f(L1PcInstance var0, L1ItemInstance var1, L1ItemInstance var2) {
      int var3 = var2.G() - 1;
      if (var1.F() != 0 && var3 >= 0) {
         boolean var4 = var2.D();
         var0.j().a(var2, false);
         var2.a(var3);
         var0.j().j(var2);
         var0.j().a(var2, var4);
         var0.a(new S_ServerMessage(161, var2.s(), "$246", "$247"));
      } else {
         var0.a(new S_ServerMessage(160, var2.s(), "$246"));
      }
   }

   public static void d(L1PcInstance var0, L1ItemInstance var1, L1ItemInstance var2) {
      int var3 = var1.a().V();
      if (!var2.g()) {
         var0.a(new S_ServerMessage(79));
      } else if (var2.F() < 128 && var2.a().x() >= 0) {
         var2.h(var3);
         var0.j().j(var2);
         var0.a(new S_ServerMessage(3296, var2.s()));
         var0.j().b(var1, 1);
      } else {
         var0.a(new S_ServerMessage(3298));
      }
   }

   public static void e(L1PcInstance var0, L1ItemInstance var1, L1ItemInstance var2) {
      int var3 = var2.K();
      int var4 = var2.L();
      int var5 = var1.a().V();
      if (!var2.g()) {
         var0.a(new S_ServerMessage(79));
      } else if (var2.F() < 128 && var2.a().x() >= 0) {
         if (var3 != var5 || (var2.G() > 8 || var4 < 3) && (var2.G() > 9 || var4 < 4) && (var2.G() > 10 || var4 < 5) && (var2.G() <= 10 || var4 < 5)) {
            int var6 = Random.a(100) + 1;
            int var7 = Config.I;
            if (var1.F() == 0) {
               var7 += 5;
            } else if (var1.F() == 2) {
               var7 -= 5;
            }

            if (var6 <= var7) {
               var4 = (var3 == var5 ? var4 : 0) + 1;
               var2.h(var5);
               var2.i(var4);
               var0.j().j(var2);
               var0.a(new S_ServerMessage(1410, var2.s()));
            } else {
               var0.a(new S_ServerMessage(1411, var2.s()));
            }

            var0.j().b(var1, 1);
         } else {
            var0.a(new S_ServerMessage(3319));
         }
      } else {
         var0.a(new S_ServerMessage(1453));
      }
   }

   public static void a(L1PcInstance var0, L1ItemInstance var1, int var2) {
      int var3 = var1.a().f();
      String[][] var4 = new String[][]{{"", "", "", "", ""}, {"$246", "", "$245", "$245", "$245"}, {"$246", "", "$252", "$252", "$252"}};
      String[][] var5 = new String[][]{{"", "", "", "", ""}, {"$247", "", "$247", "$248", "$248"}, {"$247", "", "$247", "$248", "$248"}};
      String var6 = var4[var3][var2 + 1];
      String var7 = var5[var3][var2 + 1];
      var0.a(new S_ServerMessage(161, var1.s(), var6, var7));
      boolean var8 = var1.D();
      var0.j().a(var1, false);
      int var9 = var1.G() + var2;
      if (var9 >= 9) {
         L1World.a().a(new S_ServerMessage(var1.g() ? 4444 : 4445, var1.s()));
      }

      var1.a(var9);
      var0.j().j(var1);
      var0.j().a(var1, var8);
      if (var1.G() - var1.a().x() >= 3) {
         HistoryTable.a().a(var0, "強化成功，獲得", var1);
      }
   }

   private static void a(L1PcInstance var0, L1ItemInstance var1) {
      String[] var2 = new String[]{"", "$245", "$252"};
      int var3 = var1.a().f();
      if (var1.G() < 0) {
         var2[var3] = "$246";
      }

      var0.a(new S_ServerMessage(164, var1.s(), var2[var3]));
      var0.j().b(var1, var1.E());
      if (var1.G() - var1.a().x() >= 2) {
         HistoryTable.a().a(var0, "強化失敗，失去", var1);
      }
   }

   private static int a(boolean var0, L1ItemInstance var1) {
      if (var0) {
         int var2 = Random.a(100) + 1;
         if (var1.G() <= 2) {
            if (var2 >= 33 && var2 <= 76) {
               return 2;
            }

            if (var2 >= 77 && var2 <= 100) {
               return 3;
            }
         } else if (var1.G() >= 3 && var1.G() <= 5 && var2 < 50) {
            return 2;
         }
      }

      return 1;
   }
}
