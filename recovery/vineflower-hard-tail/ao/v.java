package ao;

import be.ds;
import be.ei;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class v {
   private static final Logger a = Logger.getLogger(v.class.getName());
   private static v b;
   private final HashMap<Integer, ArrayList<bh.h>> c = this.b();

   public static v a() {
      if (b == null) {
         b = new v();
      }

      return b;
   }

   private v() {
   }

   private HashMap<Integer, ArrayList<bh.h>> b() {
      HashMap var1 = new HashMap<>();
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = l1j.server.b.a().b();
         var3 = var2.prepareStatement("select * from droplist");
         var4 = var3.executeQuery();

         while (var4.next()) {
            int var5 = var4.getInt("mobId");
            int var6 = var4.getInt("itemId");
            int var7 = var4.getInt("min");
            int var8 = var4.getInt("max");
            int var9 = var4.getInt("chance");
            int var10 = var4.getInt("enchantlvl");
            int var11 = var4.getInt("bless_change");
            int var12 = var4.getInt("unbless_change");
            bh.h var13 = new bh.h(var5, var6, var7, var8, var9, var10, var11, var12);
            ArrayList var14 = var1.get(var13.e());
            if (var14 == null) {
               var14 = new ArrayList<>();
               var1.put(new Integer(var13.e()), var14);
            }

            var14.add(var13);
         }
      } catch (SQLException var18) {
         a.log(Level.SEVERE, var18.getLocalizedMessage(), var18);
      } finally {
         bi.j.a(var4, var3, var2);
      }

      return var1;
   }

   public void a(ap.t var1, au.f var2) {
      u.a().a(var1);
      int var3 = var1.U_().b();
      List var4 = this.c.get(var3);
      if (var4 != null) {
         double var5 = l1j.server.a.F;
         if (var5 <= 0.0) {
            var5 = 0.0;
         }

         double var7 = l1j.server.a.E;
         if (var7 <= 0.0) {
            var7 = 0.0;
         }

         if (!(var5 <= 0.0) || !(var7 <= 0.0)) {
            for (bh.h var15 : var4) {
               int var9 = var15.b();
               if (var7 != 0.0 || var9 != 40308) {
                  int var12 = bi.i.a(1000000) + 1;
                  double var17 = ao.a().b(var1.fp());
                  ak.a().a(var9, var15.a() * var5 * var17, var2);
                  if (var5 != 0.0 && !(var15.a() * var5 * var17 < var12)) {
                     int var10 = var15.d();
                     int var11 = var15.c() - var15.d() + 1;
                     if (var11 > 1) {
                        var10 += bi.i.a(var11);
                     }

                     if (var9 == 40308) {
                        var10 = (int)(var10 * var7);
                     }

                     if (var10 < 0) {
                        var10 = 0;
                     }

                     if (var10 > 2000000000) {
                        var10 = 2000000000;
                     }

                     int var13 = var15.f();
                     ap.q var14 = ah.a().b(var9);
                     if (var14.d()) {
                        var14.e(var10);
                        var14.a(var13);
                        if (var15.g() > 0 && bi.i.a(100) < var15.g()) {
                           var14.f(0);
                        } else if (var15.h() > 0 && bi.i.a(100) < var15.h()) {
                           var14.f(2);
                        }

                        var2.d(var14);
                     } else {
                        for (int var19 = 0; var19 < var10; var19++) {
                           ap.q var20 = ah.a().b(var9);
                           var20.a(var13);
                           var20.n();
                           if (var15.g() > 0 && bi.i.a(100) < var15.g()) {
                              var20.f(0);
                           } else if (var15.h() > 0 && bi.i.a(100) < var15.h()) {
                              var14.f(2);
                           }

                           var2.d(var20);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void a(ap.t var1, aq.q var2) {
      au.f var3 = var1.y();
      if (var3.c() != 0) {
         CopyOnWriteArrayList var4 = var2.d();
         int var5 = 0;

         for (aq.q.a var6 : var4) {
            aq.f var8 = var6.a;
            if (l1j.server.a.P != 2 || !(var8 instanceof ap.z) && !(var8 instanceof ap.v)) {
               if (var8 != null && var8.fp() == var1.fp() && var8.fu().c(var1.fu()) <= l1j.server.a.Q) {
                  var5 += var6.b;
               } else {
                  var4.remove(var6);
               }
            } else {
               var4.remove(var6);
            }
         }

         au.f var25 = null;

         for (ap.q var26 : var3.d()) {
            int var9 = var26.N();
            if (var26.f() && var26.a().aP() == 2) {
               var26.c(false);
            }

            if (var5 <= 0 || l1j.server.a.P == 0 && var9 != 40308) {
               int var28 = 0;
               int var29 = 0;
               int[][] var30 = new int[][]{{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};

               for (int var31 = 0; var31 < 8; var31++) {
                  int var32 = bi.i.a(8);
                  if (var1.fq().b(var1.fs(), var1.ft(), var32)) {
                     var28 = var30[var32][0];
                     var29 = var30[var32][1];
                     break;
                  }
               }

               var25 = aq.aq.a().a(var1.fs() + var28, var1.ft() + var29, var1.fp());
            } else {
               int var10 = bi.i.a(var5);
               int var11 = 0;

               for (aq.q.a var12 : var4) {
                  var11 += var12.b;
                  if (var11 > var10) {
                     aq.f var14 = var12.a;
                     if (var14 == null) {
                        var25 = aq.aq.a().a(var1.fs(), var1.ft(), var1.fp());
                        break;
                     }

                     var25 = var14.y();
                     if (var9 >= 40131 && var9 <= 40135) {
                        if (!(var14 instanceof ap.u) || var4.size() > 1) {
                           var25 = null;
                           break;
                        }

                        ap.u var15 = (ap.u)var14;
                        if (var15.bb().a(10) != 1) {
                           var25 = null;
                           break;
                        }
                     }

                     if (var25.a(var26, var26.E()) != 0) {
                        var25 = aq.aq.a().a(var14.fs(), var14.ft(), var14.fp());
                        break;
                     }

                     if (!(var14 instanceof ap.u)) {
                        break;
                     }

                     ap.u var33 = (ap.u)var14;
                     long var16 = var33.j().g(40308);
                     if (var16 + var26.E() > 2000000000L) {
                        var25 = aq.aq.a().a(var33.fs(), var33.ft(), var33.fp());
                        var33.a(new ei("\\aG所持有的金幣超過了2000000000上限"));
                        break;
                     }

                     if (!var33.q()) {
                        var33.a(new ds(143, var1.et(), var26.s()));
                        break;
                     }

                     if (var33.cz() != 1 && var33.cz() != 5) {
                        for (ap.u var34 : var33.aL().c()) {
                           var34.a(new ds(813, var1.et(), var26.s(), var33.et()));
                        }
                        break;
                     }

                     int var18 = 0;
                     int var19 = 0;

                     for (ap.u var20 : var33.aL().c()) {
                        if (var20 != null && var20.fp() == var1.fp() && var20.ea() > 0 && !var20.eX()) {
                           var18++;
                        }
                     }

                     if (var18 > 1 && var26.E() >= var18) {
                        var19 = var26.E() / var18;

                        for (ap.u var38 : var33.aL().c()) {
                           if (var38 != null && var38.fp() == var1.fp() && var38.ea() > 0 && !var38.eX()) {
                              ap.q var22 = ah.a(var38, var9, var19, 0, false);

                              for (ap.u var23 : var33.aL().c()) {
                                 var23.a(new ds(813, var1.et(), var22.s(), var38.et()));
                              }
                           }
                        }

                        var3.b(var26, var26.E());
                        break;
                     }

                     for (ap.u var37 : var33.aL().c()) {
                        var37.a(new ds(813, var1.et(), var26.s(), var33.et()));
                     }
                     break;
                  }
               }
            }

            if (var25 == null) {
               var3.b(var26, var26.E());
            } else {
               var3.a(var26, var26.E(), var25);
            }
         }

         var1.fg();
      }
   }
}
