package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class TreasureBoxTable {
   private static final Logger a = Logger.getLogger(TreasureBoxTable.class.getName());
   private static TreasureBoxTable b;
   private static final HashMap<Integer, TreasureBoxTable.L1R_b> c = new HashMap<>();

   public static TreasureBoxTable a() {
      if (b == null) {
         b = new TreasureBoxTable();
      }

      return b;
   }

   private TreasureBoxTable() {
      long var1 = System.currentTimeMillis();
      System.out.print("loading TreasureBoxTable...");
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("SELECT *FROM treasure_box");
         var5 = var4.executeQuery();

         while (var5.next()) {
            int var6 = var5.getInt("box_itemid");
            TreasureBoxTable.L1R_b var7 = null;
            if (c.containsKey(var6)) {
               var7 = c.get(var6);
            } else {
               var7 = new TreasureBoxTable.L1R_b(var6);
               c.put(var6, var7);
            }

            TreasureBoxTable.L1R_a var8 = new TreasureBoxTable.L1R_a(null);
            var8.a = var5.getInt("itemid");
            var8.b = var5.getInt("count");
            var8.c = var5.getInt("enchant");
            var8.d = var5.getInt("chance");
            var8.e = var5.getInt("use_day");
            var8.f = var5.getInt("bless_change");
            var8.g = var5.getInt("unbless_change");
            var7.c.add(var8);
         }
      } catch (SQLException var12) {
         a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
      } finally {
         SQLUtil.a(var5, var4, var3);
      }

      for (TreasureBoxTable.L1R_b var14 : c.values()) {
         var14.a();
      }

      System.out.println("OK! " + (System.currentTimeMillis() - var1) + "ms");
   }

   public boolean a(int var1, L1PcInstance var2) {
      return !c.containsKey(var1) ? false : c.get(var1).a(var2);
   }

   private class L1R_a {
      public int a;
      public int b;
      public int c;
      public int d;
      public int e;
      public int f;
      public int g;

      private L1R_a() {
      }

      // $VF: synthetic method
      L1R_a(TreasureBoxTable.L1R_a var2) {
         this();
      }
   }

   private class L1R_b {
      public int a;
      public int b;
      public CopyOnWriteArrayList<TreasureBoxTable.L1R_a> c = new CopyOnWriteArrayList<>();

      public L1R_b(int var2) {
         this.a = var2;
      }

      private void a() {
         for (TreasureBoxTable.L1R_a var1 : this.c) {
            this.b = this.b + var1.d;
            if (ItemTable.a().a(var1.a) == null) {
               this.c.remove(var1);
               System.out.println("item ID " + var1.a + " is not found。");
            }
         }

         if (this.b != 0 && this.b != 1000000) {
            System.out.println("ID " + this.a + " 的總機率不等於100%。" + this.b);
         }

         Collections.sort(this.c, new Comparator<TreasureBoxTable.L1R_a>() {
            public int a(TreasureBoxTable.L1R_a var1, TreasureBoxTable.L1R_a var2) {
               return var1.d - var2.d;
            }

            @Override
            public int compare(TreasureBoxTable.L1R_a var1, TreasureBoxTable.L1R_a var2) {
               return this.a(var1, var2);
            }
         });
      }

      private boolean a(L1PcInstance var1) {
         L1ItemInstance var2 = null;
         if (this.b == 0) {
            for (TreasureBoxTable.L1R_a var3 : this.c) {
               int var5 = 1;
               if (Random.a(100) < var3.f) {
                  var5 = 0;
               } else if (Random.a(100) < var3.g) {
                  var5 = 2;
               }

               var2 = ItemTable.a(var1, var3.a, var3.b, var3.c, var5, false, var3.e);
            }
         } else {
            int var9 = 0;
            int var11 = Random.a(this.b);

            for (TreasureBoxTable.L1R_a var12 : this.c) {
               var9 += var12.d;
               if (var11 < var9 || var12.d == 0) {
                  int var7 = 1;
                  if (Random.a(100) < var12.f) {
                     var7 = 0;
                  } else if (Random.a(100) < var12.g) {
                     var7 = 2;
                  }

                  if (this.a == 640353) {
                     if (var12.a == 40308) {
                        int[] var8 = new int[]{50000, 50000, 50000, 100000, 100000, 500000};
                        var12.b = var8[Random.a(var8.length)];
                     } else if (var12.a == 640369) {
                        int[] var13 = new int[]{3, 5, 10, 20};
                        var12.b = var13[Random.a(var13.length)];
                     }
                  }

                  var2 = ItemTable.a(var1, var12.a, var12.b, var12.c, var7, false, var12.e);
                  if (var12.d != 0) {
                     break;
                  }
               }
            }
         }

         if (var2 == null) {
            return false;
         }

         if (this.a == 40576 || this.a == 40577 || this.a == 40578 || this.a == 40411 || this.a == 49013) {
            var1.b((L1Character)null);
         }

         if (this.a == 46000 || this.a >= 640503 && this.a <= 640506) {
            L1ItemInstance var10 = var1.j().b(this.a);
            var10.g(var10.I() - 1);
            var1.j().b(var10);
            if (var10.I() < 1) {
               var1.j().b(var10, 1);
            }
         }

         if (this.a == 640353) {
            ItemTable.a(var1, 41352, 1);
         }

         return true;
      }
   }
}
