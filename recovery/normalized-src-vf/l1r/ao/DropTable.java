package l1r.ao;

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
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.aq.L1HateList;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Drop;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

public class DropTable {
   private static final Logger a = Logger.getLogger(DropTable.class.getName());
   private static DropTable b;
   private final HashMap<Integer, ArrayList<L1Drop>> c = this.b();

   public static DropTable a() {
      if (b == null) {
         b = new DropTable();
      }

      return b;
   }

   private DropTable() {
   }

   private HashMap<Integer, ArrayList<L1Drop>> b() {
      HashMap<Integer, ArrayList<L1Drop>> var1 = new HashMap<>();
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
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
            L1Drop var13 = new L1Drop(var5, var6, var7, var8, var9, var10, var11, var12);
            ArrayList<L1Drop> var14 = var1.get(var13.e());
            if (var14 == null) {
               var14 = new ArrayList<>();
               var1.put(new Integer(var13.e()), var14);
            }

            var14.add(var13);
         }
      } catch (SQLException var18) {
         a.log(Level.SEVERE, var18.getLocalizedMessage(), var18);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }

      return var1;
   }

   public void a(L1NpcInstance var1, L1Inventory var2) {
      DropMapTable.a().a(var1);
      int var3 = var1.U_().b();
      List<L1Drop> var4 = this.c.get(var3);
      if (var4 != null) {
         double var5 = Config.F;
         if (var5 <= 0.0) {
            var5 = 0.0;
         }

         double var7 = Config.E;
         if (var7 <= 0.0) {
            var7 = 0.0;
         }

         if (!(var5 <= 0.0) || !(var7 <= 0.0)) {
            for (L1Drop var15 : var4) {
               int var9 = var15.b();
               if (var7 != 0.0 || var9 != 40308) {
                  int var12 = Random.a(1000000) + 1;
                  double var17 = MapsTable.a().b(var1.fp());
                  LostPowerItemTable.a().a(var9, var15.a() * var5 * var17, var2);
                  if (var5 != 0.0 && !(var15.a() * var5 * var17 < var12)) {
                     int var10 = var15.d();
                     int var11 = var15.c() - var15.d() + 1;
                     if (var11 > 1) {
                        var10 += Random.a(var11);
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
                     L1ItemInstance var14 = ItemTable.a().b(var9);
                     if (var14.d()) {
                        var14.e(var10);
                        var14.a(var13);
                        if (var15.g() > 0 && Random.a(100) < var15.g()) {
                           var14.f(0);
                        } else if (var15.h() > 0 && Random.a(100) < var15.h()) {
                           var14.f(2);
                        }

                        var2.d(var14);
                     } else {
                        for (int var19 = 0; var19 < var10; var19++) {
                           L1ItemInstance var20 = ItemTable.a().b(var9);
                           var20.a(var13);
                           var20.n();
                           if (var15.g() > 0 && Random.a(100) < var15.g()) {
                              var20.f(0);
                           } else if (var15.h() > 0 && Random.a(100) < var15.h()) {
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

   public void a(L1NpcInstance var1, L1HateList var2) {
      L1Inventory var3 = var1.y();
      if (var3.c() != 0) {
         CopyOnWriteArrayList var4 = var2.d();
         int var5 = 0;

         for (L1HateList.L1R_a var6 : var4) {
            L1Character var8 = var6.a;
            if (Config.P != 2 || !(var8 instanceof L1SummonInstance) && !(var8 instanceof L1PetInstance)) {
               if (var8 != null && var8.fp() == var1.fp() && var8.fu().c(var1.fu()) <= Config.Q) {
                  var5 += var6.b;
               } else {
                  var4.remove(var6);
               }
            } else {
               var4.remove(var6);
            }
         }

         L1Inventory var25 = null;

         for (L1ItemInstance var26 : var3.d()) {
            int var9 = var26.N();
            if (var26.f() && var26.a().aP() == 2) {
               var26.c(false);
            }

            if (var5 <= 0 || Config.P == 0 && var9 != 40308) {
               int var28 = 0;
               int var29 = 0;
               int[][] var30 = new int[][]{{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};

               for (int var31 = 0; var31 < 8; var31++) {
                  int var32 = Random.a(8);
                  if (var1.fq().b(var1.fs(), var1.ft(), var32)) {
                     var28 = var30[var32][0];
                     var29 = var30[var32][1];
                     break;
                  }
               }

               var25 = L1World.a().a(var1.fs() + var28, var1.ft() + var29, var1.fp());
            } else {
               int var10 = Random.a(var5);
               int var11 = 0;

               for (L1HateList.L1R_a var12 : var4) {
                  var11 += var12.b;
                  if (var11 > var10) {
                     L1Character var14 = var12.a;
                     if (var14 == null) {
                        var25 = L1World.a().a(var1.fs(), var1.ft(), var1.fp());
                        break;
                     }

                     var25 = var14.y();
                     if (var9 >= 40131 && var9 <= 40135) {
                        if (!(var14 instanceof L1PcInstance) || var4.size() > 1) {
                           var25 = null;
                           break;
                        }

                        L1PcInstance var15 = (L1PcInstance)var14;
                        if (var15.bb().a(10) != 1) {
                           var25 = null;
                           break;
                        }
                     }

                     if (var25.a(var26, var26.E()) != 0) {
                        var25 = L1World.a().a(var14.fs(), var14.ft(), var14.fp());
                        break;
                     }

                     if (!(var14 instanceof L1PcInstance)) {
                        break;
                     }

                     L1PcInstance var33 = (L1PcInstance)var14;
                     long var16 = var33.j().g(40308);
                     if (var16 + var26.E() > 2000000000L) {
                        var25 = L1World.a().a(var33.fs(), var33.ft(), var33.fp());
                        var33.a(new S_SystemMessage("\\aG所持有的金幣超過了2000000000上限"));
                        break;
                     }

                     if (!var33.q()) {
                        var33.a(new S_ServerMessage(143, var1.et(), var26.s()));
                        break;
                     }

                     if (var33.cz() != 1 && var33.cz() != 5) {
                        for (L1PcInstance var34 : var33.aL().c()) {
                           var34.a(new S_ServerMessage(813, var1.et(), var26.s(), var33.et()));
                        }
                        break;
                     }

                     int var18 = 0;
                     int var19 = 0;

                     for (L1PcInstance var20 : var33.aL().c()) {
                        if (var20 != null && var20.fp() == var1.fp() && var20.ea() > 0 && !var20.eX()) {
                           var18++;
                        }
                     }

                     if (var18 > 1 && var26.E() >= var18) {
                        var19 = var26.E() / var18;

                        for (L1PcInstance var38 : var33.aL().c()) {
                           if (var38 != null && var38.fp() == var1.fp() && var38.ea() > 0 && !var38.eX()) {
                              L1ItemInstance var22 = ItemTable.a(var38, var9, var19, 0, false);

                              for (L1PcInstance var23 : var33.aL().c()) {
                                 var23.a(new S_ServerMessage(813, var1.et(), var22.s(), var38.et()));
                              }
                           }
                        }

                        var3.b(var26, var26.E());
                        break;
                     }

                     for (L1PcInstance var37 : var33.aL().c()) {
                        var37.a(new S_ServerMessage(813, var1.et(), var26.s(), var33.et()));
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
