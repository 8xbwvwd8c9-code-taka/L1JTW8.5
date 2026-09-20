package l1r.bi;

import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ao.ExpTable;
import l1r.ao.PetTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.aq.L1HateList;
import l1r.aq.L1Master;
import l1r.aq.L1Object;
import l1r.be.S_PetPack;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Pet;
import l1r.l1j.server.Config;

public class CalcExp {
   private CalcExp() {
   }

   public static void a(L1PcInstance var0, L1NpcInstance var1, L1HateList var2) {
      if (!(var1 instanceof L1PetInstance) && !(var1 instanceof L1SummonInstance)) {
         int var3 = 0;
         int var4 = 0;
         CopyOnWriteArrayList var5 = var2.d();

         for (L1HateList.a var6 : var5) {
            L1Character var8 = var6.a;
            int var9 = var6.b;
            if (var8 != null && !var8.eX()) {
               var3 += var9;
               if (var8 instanceof L1PcInstance) {
                  var4 += var9;
               }
            } else {
               var5.remove(var6);
            }
         }

         if (var3 != 0) {
            if (var4 <= 0) {
               var4 = 1;
            }

            int var32 = var1.m();
            int var33 = var1.fa();
            int var34 = 0;
            int var40 = 0;
            if (!var0.q()) {
               for (L1HateList.a var43 : var5) {
                  L1Character var47 = var43.a;
                  int var49 = var43.b;
                  var34 = var32 * var49 / var3;
                  if (var47 instanceof L1PcInstance) {
                     var40 = var33 * var49 / var4;
                     a((L1PcInstance)var47, var34, var40);
                  } else if (var47 instanceof L1PetInstance) {
                     a((L1PetInstance)var47, var34);
                  }
               }
            } else {
               int var10 = 0;
               int var11 = 0;

               for (L1HateList.a var12 : var5) {
                  L1Character var14 = var12.a;
                  int var15 = var12.b;
                  if (var14 instanceof L1PcInstance) {
                     L1PcInstance var16 = (L1PcInstance)var14;
                     if (var16 == var0) {
                        var10 += var15;
                        var11 += var15;
                     } else if (var0.aL().d(var16)) {
                        var10 += var15;
                        var11 += var15;
                     } else {
                        var34 = var32 * var15 / var3;
                        var40 = var33 * var15 / var4;
                        a(var16, var34, var40);
                     }
                  } else if (var14 instanceof L1PetInstance) {
                     L1PetInstance var51 = (L1PetInstance)var14;
                     L1PcInstance var17 = (L1PcInstance)var51.M();
                     if (var17 == var0) {
                        var10 += var15;
                     } else if (var0.aL().d(var17)) {
                        var10 += var15;
                     } else {
                        var34 = var32 * var15 / var3;
                        a(var51, var34);
                     }
                  } else if (var14 instanceof L1SummonInstance) {
                     L1SummonInstance var52 = (L1SummonInstance)var14;
                     L1PcInstance var54 = (L1PcInstance)var52.M();
                     if (var54 == var0) {
                        var10 += var15;
                     } else if (var0.aL().d(var54)) {
                        var10 += var15;
                     }
                  }
               }

               int var45 = var32 * var10 / var3;
               int var48 = var33 * var11 / var4;
               double var50 = 0.0;
               double var53 = 1.0;

               for (L1PcInstance var18 : var0.aL().c()) {
                  if (var0.b((L1Object)var18) || var0.equals(var18)) {
                     var50 += var18.ev() * var18.ev();
                  }

                  if (var0.b((L1Object)var18)) {
                     var53 += 0.04;
                  }
               }

               L1PcInstance var55 = var0.aL().a();
               if (var55.x() && (var0.b((L1Object)var55) || var0.equals(var55))) {
                  var53 += 0.059;
               }

               var45 = (int)(var45 * var53);

               for (L1PcInstance var56 : var0.aL().c()) {
                  if (var56 == var0 || var0.b((L1Object)var56)) {
                     double var21 = var56.ev() * var56.ev() / var50;
                     int var23 = (int)(var45 * var21);
                     int var24 = (int)(var48 * var21);
                     int var25 = 0;

                     for (L1HateList.a var26 : var5) {
                        L1Character var28 = var26.a;
                        int var29 = var26.b;
                        if (var28 instanceof L1PcInstance) {
                           L1PcInstance var30 = (L1PcInstance)var28;
                           if (var30 == var56) {
                              var25 += var29;
                           }
                        } else if (var28 instanceof L1PetInstance) {
                           L1PetInstance var61 = (L1PetInstance)var28;
                           L1PcInstance var31 = (L1PcInstance)var61.M();
                           if (var31 == var56) {
                              var25 += var29;
                           }
                        }
                     }

                     if (var25 > 0) {
                        for (L1HateList.a var57 : var5) {
                           L1Character var59 = var57.a;
                           int var60 = var57.b;
                           if (var59 instanceof L1PcInstance) {
                              L1PcInstance var62 = (L1PcInstance)var59;
                              if (var62 == var56) {
                                 var34 = var23 * var60 / var25;
                                 a(var62, var34, var24);
                              }
                           } else if (var59 instanceof L1PetInstance) {
                              L1PetInstance var63 = (L1PetInstance)var59;
                              L1PcInstance var64 = (L1PcInstance)var63.M();
                              if (var64 == var56) {
                                 var34 = var23 * var60 / var25;
                                 a(var63, var34);
                              }
                           }
                        }
                     } else if (!var56.eX()) {
                        a(var56, var23, var24);
                     }
                  }
               }
            }
         }
      }
   }

   private static void a(L1PcInstance var0, int var1, int var2) {
      int var3 = (int)(var2 * Config.C) * -1;
      var0.cs(var3);
      if (var0.dW() > 0) {
         int var4 = Math.min(var0.dW(), 11);
         var1 += 100 + (var4 - 1) * 10;
      }

      int var14 = (int)(var1 * 0.77);
      if (var0.ev() >= 49) {
         if (var0.cC() < var14) {
            var14 = var0.cC();
         }

         var0.K(var0.cC() - var14);
         var1 += var14;
      }

      if (var0.cE() == -1) {
         var1 = (int)(var1 * (1.0 + 0.1 * L1Master.a().c(var0.fr())));
      }

      if (var0.bB(4084) || var0.bB(4092)) {
         var1 = (int)(var1 * 1.2);
      }

      if (var0.bB(4092)) {
         var1 = (int)(var1 * 1.2);
      }

      if (var0.bB(4076)) {
         var1 = (int)(var1 * 1.23);
      }

      if (var0.dN() > 0) {
         var1 = (int)(var1 * (1.0 + 0.1 * var0.dN()));
      }

      double var5 = ExpTable.d(var0.ev());
      double var7 = 1.0;
      double var9 = 1.0;
      if (var0.bB(3007) || var0.bB(3015)) {
         var7 = 1.01;
      }

      if (var0.bB(3023) || var0.bB(3031) || var0.bB(3049) || var0.bB(3050) || var0.bB(3051)) {
         var7 = 1.02;
      }

      if (var0.bB(3039) || var0.bB(3047)) {
         var7 = 1.03;
      }

      if (var0.bB(3052)) {
         var7 = 1.04;
      }

      if (var0.bB(3053) || var0.bB(3054) || var0.bB(3055)) {
         var7 = 1.04;
      }

      if (var0.bB(3056)) {
         var7 = 1.06;
      }

      if (var0.bB(4007)) {
         var9 = 1.2;
      } else if (var0.bB(4001)) {
         var9 = 2.5;
      } else if (var0.bB(4002)) {
         var9 = 2.75;
      } else if (var0.bB(4003)) {
         var9 = 3.0;
      } else if (var0.bB(4004)) {
         var9 = 3.25;
      } else if (var0.bB(4005)) {
         var9 = 3.5;
      } else if (var0.bB(4070)) {
         var9 = 2.0;
      } else if (var0.bB(4078)) {
         var9 = 1.3;
      }

      double var11 = var0.fq().h;
      int var13 = (int)(var1 * var5 * Config.B * var7 * var9 * var11);
      var0.x(var13);
      var0.H();
   }

   private static void a(L1PetInstance var0, int var1) {
      L1PcInstance var2 = (L1PcInstance)var0.M();
      int var3 = var0.k();
      int var4 = var0.ev();
      int var5 = (int)(var1 * Config.B + var0.m());
      if (var5 >= ExpTable.a(51)) {
         var5 = ExpTable.a(51) - 1;
      }

      var0.k(var5);
      var0.b(ExpTable.c(var5));
      int var6 = ExpTable.a(var0.ev(), var5);
      int var7 = var0.ev() - var4;

      for (int var8 = 1; var8 <= var7; var8++) {
         IntRange var9 = var0.av().e();
         IntRange var10 = var0.av().f();
         var0.bH(var9.a());
         var0.bJ(var10.a());
      }

      var0.f(var6);
      var2.a(new S_PetPack(var0, var2));
      if (var7 != 0) {
         L1Pet var11 = PetTable.a().b(var3);
         if (var11 == null) {
            return;
         }

         var11.g(var0.m());
         var11.d(var0.ev());
         var11.e(var0.ew());
         var11.f(var0.ex());
         PetTable.a().a(var11);
         var2.a(new S_ServerMessage(320, var0.et()));
      }
   }
}
