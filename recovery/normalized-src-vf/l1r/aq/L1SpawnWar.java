package l1r.aq;

import l1r.ao.SpawnTable;
import l1r.bi.Random;

public class L1SpawnWar {
   private static L1SpawnWar a;

   private L1SpawnWar() {
   }

   public static L1SpawnWar a() {
      if (a == null) {
         a = new L1SpawnWar();
      }

      return a;
   }

   public void a(int var1) {
      int var2 = 81111;
      if (var1 == 7) {
         var2 = 81189;
      }

      int[] var3 = L1CastleLocation.a(var1);
      SpawnTable.a(var2, var3[0], var3[1], var3[2], 0, "");
      if (var1 == 7) {
         this.b();
      }
   }

   private void b() {
      for (int var1 = 1; var1 <= 4; var1++) {
         int[] var2 = L1CastleLocation.f(var1);
         SpawnTable.a(81189 + var1, var2[0], var2[1], var2[2], 0, "");
      }
   }

   public void b(int var1) {
      int[] var2 = L1CastleLocation.a(var1);
      SpawnTable.a(81125, var2[0], var2[1], var2[2], 0, "");
   }

   public void c(int var1) {
      int[] var2 = new int[5];
      var2 = L1CastleLocation.c(var1);
      int var3 = 0;
      int var4 = 0;
      int var5 = var2[0];
      int var6 = var2[1];
      int var7 = var2[2];
      int var8 = var2[3];
      int var9 = var2[4];
      int[] var10 = L1CastleLocation.b(var1);
      var3 = var5;
      var4 = var7;

      while (var3 <= var6) {
         SpawnTable.a(81122, var3, var4, var9, 0, null, var10[0]);
         var3 += 8;
      }

      var3 = var6;

      for (int var17 = var7; var17 <= var8; var17 += 8) {
         SpawnTable.a(81122, var3, var17, var9, 0, null, var10[1]);
      }

      var3 = var6;
      var4 = var8;

      while (var3 >= var5) {
         SpawnTable.a(81122, var3, var4, var9, 0, null, var10[0]);
         var3 -= 8;
      }

      var3 = var5;

      for (int var19 = var8; var19 >= var7; var19 -= 8) {
         SpawnTable.a(81122, var3, var19, var9, 0, null, var10[1]);
      }
   }

   public void d(int var1) {
      String var2 = "安安妳好再見_" + var1;
      if (var1 == 1) {
         for (int var3 = 33112; var3 <= 33115; var3++) {
            for (int var4 = 32768; var4 <= 32772; var4++) {
               SpawnTable.a(190047, var3, var4, 4, 1, var2);
            }
         }

         for (int var5 = 33116; var5 <= 33118; var5++) {
            for (int var18 = 32763; var18 <= 32766; var18++) {
               SpawnTable.a(190048, var5, var18, 4, 1, var2);
            }
         }

         for (int var6 = 33116; var6 <= 33118; var6++) {
            for (int var19 = 32774; var19 <= 32777; var19++) {
               SpawnTable.a(190048, var6, var19, 4, 1, var2);
            }
         }
      } else if (var1 == 2) {
         for (int var7 = 32792; var7 <= 32797; var7++) {
            for (int var20 = 32317; var20 <= 32318; var20++) {
               SpawnTable.a(190050, var7, var20, 4, 1, var2);
            }
         }

         for (int var8 = 32792; var8 <= 32797; var8++) {
            for (int var21 = 32308; var21 <= 32309; var21++) {
               SpawnTable.a(190051, var8, var21, 4, 1, var2);
            }
         }
      } else if (var1 == 3) {
         for (int var9 = 32591; var9 <= 32593; var9++) {
            for (int var22 = 33407; var22 <= 33410; var22++) {
               SpawnTable.a(190053, var9, var22, 4, 1, var2);
            }
         }

         for (int var10 = 32596; var10 <= 32598; var10++) {
            for (int var23 = 33407; var23 <= 33410; var23++) {
               SpawnTable.a(190053, var10, var23, 4, 1, var2);
            }
         }

         for (int var11 = 32596; var11 <= 32598; var11++) {
            for (int var24 = 33402; var24 <= 33405; var24++) {
               SpawnTable.a(190054, var11, var24, 4, 1, var2);
            }
         }

         for (int var12 = 32596; var12 <= 32598; var12++) {
            for (int var25 = 33412; var25 <= 33415; var25++) {
               SpawnTable.a(190054, var12, var25, 4, 1, var2);
            }
         }
      } else if (var1 == 4) {
         for (int var13 = 33629; var13 <= 33634; var13++) {
            for (int var26 = 32732; var26 <= 32733; var26++) {
               SpawnTable.a(190056, var13, var26, 4, 1, var2);
            }
         }

         for (int var14 = 33629; var14 <= 33636; var14++) {
            for (int var27 = 32700; var27 <= 32701; var27++) {
               SpawnTable.a(190056, var14, var27, 4, 1, var2);
            }
         }

         for (int var15 = 33623; var15 <= 33626; var15++) {
            for (int var28 = 32727; var28 <= 32729; var28++) {
               SpawnTable.a(190057, var15, var28, 4, 1, var2);
            }
         }

         for (int var16 = 33637; var16 <= 33640; var16++) {
            for (int var29 = 32727; var29 <= 32729; var29++) {
               SpawnTable.a(190057, var16, var29, 4, 1, var2);
            }
         }
      }

      int[] var17 = L1CastleLocation.a(var1);

      for (int var30 = -5; var30 <= 6; var30++) {
         SpawnTable.a(190046 + var1 * 3, var17[0] + var30, var17[1] + var30, var17[2], 1, var2);
      }
   }

   public void e(int var1) {
      String var2 = "安安妳好再見_" + var1;
      if (var1 == 1) {
         SpawnTable.a(190355 + Random.a(6), 33099, 32770, 4, 2, 60000L, var2);

         for (int var3 = 33092; var3 <= 33096; var3++) {
            SpawnTable.a(190361, var3, 32768, 4, 2, 60500L, var2);
         }

         for (int var4 = 33092; var4 <= 33096; var4++) {
            SpawnTable.a(190362, var4, 32769, 4, 2, 60500L, var2);
         }

         for (int var5 = 33092; var5 <= 33096; var5++) {
            SpawnTable.a(190363, var5, 32770, 4, 2, 60500L, var2);
         }

         for (int var6 = 33092; var6 <= 33096; var6++) {
            SpawnTable.a(190364, var6, 32771, 4, 2, 60500L, var2);
         }
      } else if (var1 == 2) {
         SpawnTable.a(190355 + Random.a(6), 32785, 32331, 4, 0, 60000L, var2);

         for (int var7 = 32334; var7 <= 32338; var7++) {
            SpawnTable.a(190361, 32782, var7, 4, 0, 60500L, var2);
         }

         for (int var8 = 32334; var8 <= 32338; var8++) {
            SpawnTable.a(190362, 32783, var8, 4, 0, 60500L, var2);
         }

         for (int var9 = 32334; var9 <= 32338; var9++) {
            SpawnTable.a(190363, 32784, var9, 4, 0, 60500L, var2);
         }

         for (int var10 = 32334; var10 <= 32338; var10++) {
            SpawnTable.a(190364, 32785, var10, 4, 0, 60500L, var2);
         }
      } else if (var1 == 4) {
         SpawnTable.a(190355 + Random.a(6), 33630, 32748, 4, 0, 60000L, var2);

         for (int var11 = 32748; var11 <= 32752; var11++) {
            SpawnTable.a(190361, 33628, var11, 4, 0, 60500L, var2);
         }

         for (int var12 = 32748; var12 <= 32752; var12++) {
            SpawnTable.a(190362, 33629, var12, 4, 0, 60500L, var2);
         }

         for (int var13 = 32748; var13 <= 32752; var13++) {
            SpawnTable.a(190363, 33630, var13, 4, 0, 60500L, var2);
         }

         for (int var14 = 32748; var14 <= 32752; var14++) {
            SpawnTable.a(190364, 33631, var14, 4, 0, 60500L, var2);
         }
      }
   }
}
