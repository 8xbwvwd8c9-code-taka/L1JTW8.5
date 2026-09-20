package aj;

import be.ds;

public class aw extends cv {
   private static final String a = "[C] C_GiveItem";
   private static final String[] b = new String[]{"L1Npc", "L1Monster", "L1Guardian", "L1Guard"};

   public aw(byte[] var1, bj.d var2) {
      super(var1);
      ap.u var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         int var4 = this.b();
         this.d();
         this.d();
         int var5 = this.b();
         int var6 = this.b();
         aq.aa var7 = aq.aq.a().a(var4);
         if (var7 != null && var7 instanceof ap.t) {
            ap.t var8 = (ap.t)var7;
            if (this.a(var8.U_())) {
               au.f var9 = var8.y();
               au.f var10 = var3.j();
               ap.q var11 = var10.e(var5);
               if (var11 != null) {
                  if (var11.D()) {
                     var3.a(new ds(141));
                  } else if (!var11.a().s()) {
                     var3.a(new ds(210, var11.a().h()));
                  } else if (var11.F() >= 128) {
                     var3.a(new ds(210, var11.a().h()));
                  } else {
                     for (ap.t var12 : var3.ek().values()) {
                        if (var12 instanceof ap.v) {
                           ap.v var14 = (ap.v)var12;
                           if (var11.fr() == var14.k()) {
                              var3.a(new ds(1187));
                              return;
                           }
                        }
                     }

                     for (ap.e var16 : var3.el().values()) {
                        if (var16.f() == var11.fr()) {
                           var3.a(new ds(1181));
                           return;
                        }
                     }

                     if (var9.a(var11, var6) != 0) {
                        var3.a(new ds(942));
                     } else {
                        var11 = var10.a(var11, var6, var9);
                        var8.a(var11);
                        var8.fg();
                        var3.fg();
                        ao.aa.a().a(var3, "給予(" + var8.et() + ")", var11, var6);
                        bh.p var17 = ao.ax.b().a(var8.U_().b());
                        if (var17 != null && !var8.eX()) {
                           if (var11.N() == var17.d()) {
                              this.a(var3, var8);
                           } else if (var11.N() == var17.i()) {
                              this.a(var3, var8, var11.N());
                           }

                           if (var11.f()) {
                              if (var11.a().aP() == 7) {
                                 this.a(var8, var11);
                              } else if (var11.a().aP() == 11 && var17.j()) {
                                 this.b(var8, var11);
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

   private void a(ap.t var1, ap.q var2) {
      if (var1 instanceof ap.v && var2.a().V() != 0) {
         ap.v var3 = (ap.v)var1;
         if (var3.fj() < 100) {
            while (var3.fj() < 100 && var3.y().b(var2.N(), 1)) {
               int var4 = var3.fj() + var2.a().V() / 10;
               var3.c_(var4 > 100 ? 100 : var4);
            }

            ao.aw.a().a(var3);
         }
      }
   }

   private void b(ap.t var1, ap.q var2) {
      if (var1 instanceof ap.v) {
         ap.v var3 = (ap.v)var1;
         bh.o var4 = ao.av.a().a(var2.N());
         if (var4.n() == 1) {
            var3.a(var3, var2);
         } else if (var4.n() == 0) {
            var3.b(var3, var2);
         }
      }
   }

   private boolean a(bh.l var1) {
      String[] var5 = b;
      int var4 = b.length;

      for (int var3 = 0; var3 < var4; var3++) {
         String var2 = var5[var3];
         if (var1.d().equals(var2)) {
            return true;
         }
      }

      return false;
   }

   private void a(ap.u var1, ap.t var2) {
      if (!(var2 instanceof ap.v) && !(var2 instanceof ap.z)) {
         int var3 = 0;

         for (ap.t var4 : var1.ek().values()) {
            var3 += var4.Q();
         }

         int var7 = var1.eC();
         if (var1.x()) {
            var7 += 6;
         } else if (var1.A()) {
            var7 += 12;
         } else if (var1.B()) {
            var7 += 6;
         } else if (var1.C()) {
            var7 += 6;
         } else if (var1.D()) {
            var7 += 6;
         } else if (var1.E()) {
            var7 += 6;
         }

         var7 -= var3;
         au.g var9 = var1.j();
         if (var7 >= 6 && var9.c() < 180) {
            if (this.b(var1, var2)) {
               ap.q var6 = ao.ah.a(var1, 40314, 1, 0, false);
               if (var6 != null) {
                  new ap.v(var2, var1, var6.fr());
                  var1.a(new be.bm(var6));
               }
            } else {
               var1.a(new ds(324));
            }
         }
      }
   }

   private void a(ap.u var1, ap.t var2, int var3) {
      if (var2 instanceof ap.v) {
         au.g var4 = var1.j();
         ap.v var5 = (ap.v)var2;
         ap.q var6 = var4.e(var5.k());
         if (var6 != null) {
            if ((var5.ev() >= 30 || var3 == 41310) && var1 == var5.M()) {
               ap.q var7 = ao.ah.a(var1, 40316, 1, 0, false);
               if (var7 != null) {
                  var5.d(var7.fr());
                  var1.a(new be.bm(var7));
                  var4.b(var6, 1);
               }
            }
         }
      }
   }

   private boolean b(ap.u var1, ap.t var2) {
      if (var1.l()) {
         return true;
      }

      boolean var3 = false;
      int var4 = var2.U_().b();
      if (var4 == 45313) {
         if (var2.ew() / 3 > var2.ea() && bi.i.a(16) == 15) {
            var3 = true;
         }
      } else if (var2.ew() / 3 > var2.ea()) {
         var3 = true;
      }

      return var3;
   }

   @Override
   public String a() {
      return "[C] C_GiveItem";
   }
}
