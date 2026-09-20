package aj;

import be.dc;
import be.ds;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class bx extends cv {
   public bx(byte[] var1, bj.d var2) throws Exception {
      super(var1);
      ap.u var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         int var5 = this.c();
         int var6 = this.d();
         if (var5 == 29) {
            for (int var29 = 0; var29 < var6; var29++) {
               int var30 = this.b();
               int var31 = this.b();
               HashMap var32 = ao.al.a().c(var2.e().d());
               ap.q var39 = var32.get(var30);
               if (var3.j().a(var39, var31) != 0) {
                  break;
               }

               var3.j().d(var39);
               var3.a(new ds(403, var39.s()));
               ao.al.a().a(var2.e().d(), var30);
               var3.a(new dc(ao.al.a().c(var2.e().d()), 0));
            }
         } else {
            int var7 = 0;
            boolean var8 = false;
            boolean var9 = false;
            aq.aa var10 = aq.aq.a().a(var4);
            if (var10 != null) {
               if (var6 == 0) {
                  if (var5 == 5) {
                     aq.i var38 = ao.q.a().a(var3.aF());
                     if (var38 != null) {
                        var38.i(0);
                     }
                  }
               } else if (var10.fu().c(var3.fu()) <= 11) {
                  if (var10 instanceof ap.t) {
                     ap.t var11 = (ap.t)var10;
                     var7 = var11.U_().b();
                     var8 = var11.H();
                  } else {
                     if (!(var10 instanceof ap.u)) {
                        return;
                     }

                     var9 = true;
                  }

                  switch (var5) {
                     case 0:
                        if (var9) {
                           ap.u var37 = (ap.u)var10;
                           if (var37.aY()) {
                              return;
                           }

                           CopyOnWriteArrayList var44 = var37.aU();
                           boolean[] var50 = new boolean[8];
                           synchronized (var44) {
                              if (var3.aZ() != var44.size()) {
                                 return;
                              }

                              var37.h(true);

                              for (int var55 = 0; var55 < var6; var55++) {
                                 int var60 = this.b();
                                 int var63 = this.b();
                                 bh.r var65 = var44.get(var60);
                                 int var67 = var65.a();
                                 int var69 = var65.c();
                                 int var70 = var65.b();
                                 int var71 = var65.d();
                                 ap.q var73 = var37.j().e(var67);
                                 if (var73 != null) {
                                    if (var63 > var70 - var71) {
                                       var63 = var70 - var71;
                                    }

                                    if (var63 != 0) {
                                       if (var3.j().a(var73, var63) != 0) {
                                          var3.a(new ds(270));
                                          break;
                                       }

                                       for (int var24 = 0; var24 < var63; var24++) {
                                          if (var69 * var24 > 2000000000) {
                                             var3.a(new ds(904, "2000000000"));
                                             var37.h(false);
                                             return;
                                          }
                                       }

                                       int var74 = var63 * var69;
                                       if (!var3.j().g(40308, var74)) {
                                          var3.a(new ds(189));
                                          break;
                                       }

                                       ap.q var25 = var3.j().b(40308);
                                       if (var25 != null) {
                                          if (var37.j().a(var73, var63, var3.j()) == null) {
                                             var37.h(false);
                                             return;
                                          }

                                          var3.j().a(var25, var74, var37.j());
                                          String var26 = var73.a().h() + " (" + var63 + ")";
                                          var37.a(new ds(877, var3.et(), var26));
                                          var65.d(var63 + var71);
                                          var44.set(var60, var65);
                                          if (var65.d() == var65.b()) {
                                             var50[var60] = true;
                                          }
                                       }
                                    }
                                 }
                              }

                              for (int var56 = 7; var56 >= 0; var56--) {
                                 if (var50[var56]) {
                                    var44.remove(var56);
                                 }
                              }

                              var37.h(false);
                              break;
                           }
                        }

                        ArrayList var36 = new ArrayList<>();

                        for (int var42 = 0; var42 < var6; var42++) {
                           int[] var49 = new int[]{this.b(), this.b()};
                           var36.add(var49);
                        }

                        if (var7 == 190095) {
                           ao.bc.a().b(var3, var36, (ap.t)var10);
                           return;
                        }

                        if (var7 == 70035 || var7 == 70041 || var7 == 70042) {
                           ao.bc.a().a(var3, var36, (ap.t)var10);
                           return;
                        }

                        int var43 = 40308;
                        if (var7 == 190005) {
                           var43 = 640268;
                        } else if (var7 == 190045) {
                           var43 = 640312;
                        } else if (var7 == 190353) {
                           var43 = 640621;
                        }

                        ao.bc.a().b(var3, var36, (ap.t)var10, var43);
                        break;
                     case 1:
                        if (!var9) {
                           bh.t var34 = ao.bc.a().a(var7);
                           ArrayList var40 = new ArrayList<>();

                           for (int var46 = 0; var46 < var6; var46++) {
                              int[] var51 = new int[]{this.b(), this.b()};
                              var40.add(var51);
                           }

                           int var47 = 40308;
                           if (var7 == 190005) {
                              var47 = 640268;
                           }

                           ao.bc.a().a(var3, var40, (ap.t)var10, var47);
                        } else {
                           ap.u var35 = (ap.u)var10;
                           if (var35.aY()) {
                              return;
                           }

                           var35.h(true);
                           List var41 = var35.aV();
                           boolean[] var48 = new boolean[8];

                           for (int var52 = 0; var52 < var6; var52++) {
                              int var54 = this.b();
                              int var59 = this.e();
                              int var62 = this.c();
                              ap.q var64 = var3.j().e(var54);
                              if (var64 != null) {
                                 bh.q var66 = var41.get(var62);
                                 int var68 = var66.c();
                                 int var21 = var66.b();
                                 int var22 = var66.d();
                                 if (var59 > var21 - var22) {
                                    var59 = var21 - var22;
                                 }

                                 if (var64.D()) {
                                    var3.a(new ds(905));
                                 } else if (var64.F() < 128) {
                                    if (var35.j().a(var64, var59) != 0) {
                                       var3.a(new ds(271));
                                       break;
                                    }

                                    for (int var23 = 0; var23 < var59; var23++) {
                                       if (var68 * var23 > 2000000000) {
                                          var35.a(new ds(904, "2000000000"));
                                          return;
                                       }
                                    }

                                    if (!var35.j().g(40308, var59 * var68)) {
                                       var35.a(new ds(189));
                                       break;
                                    }

                                    ap.q var72 = var35.j().b(40308);
                                    if (var72 != null) {
                                       var35.j().a(var72, var59 * var68, var3.j());
                                       var3.j().a(var64, var59, var35.j());
                                       var66.d(var59 + var22);
                                       var41.set(var62, var66);
                                       if (var66.d() == var66.b()) {
                                          var48[var62] = true;
                                       }
                                    }
                                 }
                              }
                           }

                           for (int var53 = 7; var53 >= 0; var53--) {
                              if (var48[var53]) {
                                 var41.remove(var53);
                              }
                           }

                           var35.h(false);
                        }
                        break;
                     case 2:
                     case 4:
                     case 8:
                     case 17:
                        if (var8 && var3.ev() >= 5) {
                           this.b(var3, var6, var5);
                           var3.J();
                        }
                        break;
                     case 3:
                     case 5:
                     case 9:
                     case 18:
                        if (var8 && var3.ev() >= 5) {
                           this.a(var3, var6, var5);
                        }
                     case 6:
                     case 7:
                     case 10:
                     case 11:
                     case 13:
                     case 14:
                     case 15:
                     case 16:
                     default:
                        break;
                     case 12:
                        if (!var9) {
                           if (!var3.j().g(40308, 115 * var6)) {
                              var3.a(new ds(189));
                              return;
                           }

                           for (int var33 = 0; var33 < var6; var33++) {
                              int var12 = 0;
                              int var13 = 6;
                              int var14 = this.b();
                              int var15 = this.b();
                              if (var15 != 0) {
                                 for (ap.t var16 : var3.ek().values()) {
                                    var12 += var16.Q();
                                 }

                                 int var57 = var3.eC() + (var3.A() ? 12 : 6);
                                 bh.n var61 = ao.aw.a().b(var14);
                                 if (var61 != null) {
                                    var7 = var61.c();
                                    var57 -= var12;
                                    byte var45;
                                    if (var7 != 45313 && var7 != 45710 && var7 != 45711 && var7 != 45712) {
                                       var45 = 6;
                                    } else {
                                       var45 = 12;
                                    }

                                    int var18 = var57 / var45;
                                    if (var18 <= 0) {
                                       var3.a(new ds(489));
                                       return;
                                    }

                                    bh.l var19 = ao.au.a().a(var7);
                                    ap.v var20 = new ap.v(var19, var3, var61);
                                    var20.o(var45);
                                 }
                              }
                           }
                        }
                  }
               }
            }
         }
      }
   }

   private void a(ap.u var1, int var2, int var3) {
      aq.i var4 = null;
      au.f var5 = var1.au();
      if (var3 == 5) {
         var4 = ao.q.a().a(var1.aF());
         if (var4 == null) {
            var1.a(new ds(208));
            return;
         }

         var5 = var4.c();
      } else if (var3 == 9) {
         if (!var1.A()) {
            var1.a(new ds(1238));
            return;
         }

         var5 = var1.av();
      } else if (var3 == 18) {
         var5 = var1.aw();
      }

      for (int var6 = 0; var6 < var2; var6++) {
         int var7 = this.b();
         int var8 = this.b();
         ap.q var9 = var5.e(var7);
         if (var9 != null) {
            if (var1.j().a(var9, var8) != 0) {
               var1.a(new ds(270));
               break;
            }

            if (var3 == 9) {
               if (!var1.j().b(40494, 4)) {
                  var1.a(new ds(337, "$767"));
                  break;
               }
            } else if (var3 == 5) {
               if (!var1.j().b(40308, 500)) {
                  var1.a(new ds(189));
                  break;
               }
            } else if (!var1.j().b(40308, 100)) {
               var1.a(new ds(189));
               break;
            }

            var5.a(var9, var8, var1.j());
            if (var3 == 3) {
               ao.aa.a().e(var1, "領出", var9, var8);
            } else if (var3 == 5) {
               ao.aa.a().f(var1, "領出", var9, var8);
            } else if (var3 == 9) {
               ao.aa.a().g(var1, "領出", var9, var8);
            }

            if (var3 == 5) {
               ((au.c)var5).a(var1, var9, var8, 1);
            }
         }
      }

      if (var4 != null) {
         var4.i(0);
      }
   }

   private void b(ap.u var1, int var2, int var3) {
      au.f var4 = var1.au();
      if (var3 == 4) {
         aq.i var5 = ao.q.a().a(var1.aF());
         if (var5 == null) {
            var1.a(new ds(208));
            return;
         }

         var4 = var5.c();
      } else if (var3 == 8) {
         if (!var1.A()) {
            var1.a(new ds(1238));
            return;
         }

         var4 = var1.av();
      } else if (var3 == 17) {
         var4 = var1.aw();
         if (var4.c() >= var1.cJ()) {
            var1.a(new ds(1623));
            return;
         }
      }

      for (int var9 = 0; var9 < var2; var9++) {
         int var6 = this.b();
         int var7 = this.b();
         ap.q var8 = var1.j().e(var6);
         if (var8 != null) {
            if (!var8.a().s() && var3 != 17) {
               var1.a(new ds(210, var8.a().h()));
            } else if (var8.F() >= 128 && var3 == 4) {
               var1.a(new ds(210, var8.a().h()));
            } else if (var1.N(var8.fr())) {
               var1.a(new ds(1187));
            } else if (var1.O(var8.fr())) {
               var1.a(new ds(1181));
            } else {
               if (var4.a(var1, var8, var7) == 1) {
                  var1.a(new ds(var3 == 17 ? 1623 : 75));
                  break;
               }

               var1.j().a(var6, var7, var4);
               var1.fg();
               if (var3 == 2) {
                  ao.aa.a().e(var1, "存入", var8, var7);
               } else if (var3 == 4) {
                  ao.aa.a().f(var1, "存入", var8, var7);
               } else if (var3 == 8) {
                  ao.aa.a().g(var1, "存入", var8, var7);
               }

               if (var3 == 4) {
                  ((au.c)var4).a(var1, var8, var7, 0);
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_Result";
   }
}
