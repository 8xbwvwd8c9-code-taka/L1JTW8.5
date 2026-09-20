package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

public class DropMapTable {
   private static final Logger a = Logger.getLogger(DropMapTable.class.getName());
   private static DropMapTable b;
   private final HashMap<Integer, DropMapTable.L1R_b> c = new HashMap<>();

   public static DropMapTable a() {
      if (b == null) {
         b = new DropMapTable();
      }

      return b;
   }

   private DropMapTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM droplist_map");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var5 = var3.getInt("mapid");
            DropMapTable.L1R_b var4;
            if (this.c.containsKey(var5)) {
               var4 = this.c.get(var5);
            } else {
               var4 = new DropMapTable.L1R_b(null);
               this.c.put(var5, var4);
            }

            int var6 = var3.getInt("itemid");
            if (var4.b.containsKey(var6)) {
               System.out.println("[DropMapTable]: mapid=" + var5 + " itemid=" + var6 + " is repeate");
            } else {
               DropMapTable.L1R_a var7 = new DropMapTable.L1R_a(null);
               var7.b = var6;
               var7.c = var3.getInt("min");
               var7.d = var3.getInt("max");
               var7.e = var3.getInt("chance");
               var7.f = var3.getInt("enchantlvl");
               var7.g = var3.getInt("bless_change");
               var4.b.put(var6, var7);
            }
         }
      } catch (SQLException var12) {
         a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
      } catch (Exception var13) {
         a.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(L1NpcInstance var1) {
      if (this.c.containsKey(var1.fp())) {
         if (var1.y() != null) {
            double var2 = Config.F;
            if (var2 <= 0.0) {
               var2 = 0.0;
            }

            double var4 = Config.E;
            if (var4 <= 0.0) {
               var4 = 0.0;
            }

            if (!(var2 <= 0.0) || !(var4 <= 0.0)) {
               for (DropMapTable.L1R_a var6 : this.c.get(var1.fp()).b.values()) {
                  L1ItemInstance var8 = ItemTable.a().b(var6.b);
                  if (var4 != 0.0 || var6.b != 40308) {
                     int var9 = Random.a(1000000) + 1;
                     double var10 = MapsTable.a().b(var1.fp());
                     if (var2 != 0.0 && !(var6.e * var2 * var10 < var9)) {
                        int var12 = var6.c;
                        int var13 = var6.d - var6.c + 1;
                        if (var13 > 1) {
                           var12 += Random.a(var13);
                        }

                        if (var6.b == 40308) {
                           var12 = (int)(var12 * var4);
                        }

                        if (var12 < 0) {
                           var12 = 0;
                        }

                        if (var12 > 2000000000) {
                           var12 = 2000000000;
                        }

                        if (var8.d()) {
                           var8.e(var12);
                           var8.a(var6.f);
                           if (var6.g > 0 && Random.a(100) < var6.g) {
                              var8.f(0);
                           }

                           var1.y().d(var8);
                        } else {
                           for (int var14 = 0; var14 < var12; var14++) {
                              L1ItemInstance var15 = ItemTable.a().b(var6.b);
                              var15.a(var6.f);
                              if (var6.g > 0 && Random.a(100) < var6.g) {
                                 var15.f(0);
                              }

                              var1.y().d(var15);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private class L1R_a {
      private int b;
      private int c;
      private int d;
      private int e;
      private int f;
      private int g;

      private L1R_a() {
      }

      // $VF: synthetic method
      L1R_a(DropMapTable.L1R_a var2) {
         this();
      }
   }

   private class L1R_b {
      private final HashMap<Integer, DropMapTable.L1R_a> b = new HashMap<>();

      private L1R_b() {
      }

      // $VF: synthetic method
      L1R_b(DropMapTable.L1R_b var2) {
         this();
      }
   }
}
