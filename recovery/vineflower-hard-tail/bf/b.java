package bf;

import java.util.ArrayList;

public class b extends a {
   @Override
   public void a(aq.f var1, int var2) {
   }

   public void a(ap.t var1, aq.f var2, ao.ar.b var3) {
      aq.f var4 = var3.g ? var2 : var1;
      ArrayList var5 = new ArrayList<>();
      if (var3.e == 0) {
         var5.add(var4);
      } else {
         var5 = this.a(var1, var4, var3.e);
      }

      if (var3.n > 0) {
         ao.bg.a(var3.n, var1, 5, var2);
      } else if (var3.s) {
         this.b(var2);
      } else {
         for (aq.f var6 : var5) {
            if (var3.q) {
               this.c(var6);
            } else if (var3.r && var6.e(var1) > 5.0) {
               aq.am.a(var6, var1, 1);
            } else if (var3.o > 0) {
               this.b(var1, var6, var3.o);
            } else if (var3.p > 0) {
               this.d(var6, var3.p);
            } else {
               int var8 = var3.b + bi.i.a(var3.c);
               var8 = (int)aq.w.a(var1, var2, var8, var3.m);
               var6.cD(var8);
               if (var6 instanceof ap.u) {
                  ap.u var9 = (ap.u)var6;
                  var9.a(var1, var8, true);
               } else if (var6 instanceof ap.t) {
                  ap.t var15 = (ap.t)var6;
                  var15.b(var1, var8);
               }
            }
         }
      }

      if (var3.e != 0) {
         if (var3.g) {
            var1.b(new be.de(var1, var5, var3.f, var3.i, 8));
         } else {
            var1.b(new be.de(var1, var5, var3.f, var3.i, 0));

            for (int var10 = 1; var10 < var5.size(); var10++) {
               aq.f var12 = var5.get(var10);
               if (var12.fo() != 0) {
                  var12.b(new be.ak(var12.fr(), 2));
                  if (var12 instanceof ap.u) {
                     ap.u var14 = (ap.u)var12;
                     var14.a(new be.ak(var14.fr(), 2));
                  }
               }
            }
         }
      } else if (var3.g) {
         if (var3.b <= 0) {
            var1.b(new be.ee(var2.fr(), var3.f));
            var1.b(new be.ak(var1.fr(), var3.i));
            return;
         }

         int var11 = var3.h ? 0 : 6;
         var1.ct(var1.a(var2));
         var1.b(new be.g(var1, var2, var3.i, var3.f, var2.fo(), var11, 0));
         if (var2.fo() > 0) {
            var2.a(new be.ak(var2.fr(), 2), var1);
         }
      } else {
         var1.b(new be.ee(var1.fr(), var3.f));
         var1.b(new be.ak(var1.fr(), var3.i));
      }
   }

   private void d(aq.f var1, int var2) {
      if (bi.i.a(127) > var1.W_()) {
         bi.g.a(var2).a(var1, -1);
      }
   }

   private void b(aq.f var1, aq.f var2, int var3) {
      if (bi.i.a(127) > var2.W_()) {
         var1.b(new be.ee(var2.fr(), 230));
         aq.ae.a(var2, var3, 300, 4);
      }
   }

   private void b(aq.f var1) {
      int var2 = var1.fs();
      int var3 = var1.ft();
      int[][] var4 = new int[][]{{0, -2}, {1, -1}, {2, 0}, {-1, -1}, new int[2], {1, 1}, {-2, 0}, {-1, 1}, {0, 2}};
      int[][] var8 = var4;
      int var7 = var4.length;

      for (int var6 = 0; var6 < var7; var6++) {
         int[] var5 = var8[var6];
         ap.h var9 = aq.ai.a().a(1263, 9000, var2 + var5[0], var3 + var5[1], var1.fp());

         for (ap.h var10 : aq.aq.a().d().values()) {
            if (!var10.equals(var9) && var10.fq() == var9.fq() && var9.fu().f(var10.fu()) && var10.fe() == 1263) {
               var10.aa_();
            }
         }
      }
   }

   private void c(aq.f var1) {
      if (bi.i.a(127) > var1.W_()) {
         int var2 = 8000;
         if (!aq.w.a(var1)) {
            aq.ai.a().a(4184, 8000, var1.fs(), var1.ft(), var1.fp());
            if (var1 instanceof ap.u) {
               ap.u var3 = (ap.u)var1;
               var3.j(1028, 8000);
               var3.a(new be.cn(6, true));
            } else if (var1 instanceof ap.t) {
               ap.t var4 = (ap.t)var1;
               var4.j(1028, 8000);
               var4.n(true);
            }
         }
      }
   }

   @Override
   public void a(aq.f var1, int var2, int var3, int var4, String var5) {
   }

   @Override
   public void a(aq.f var1) {
   }
}
