package l1r.aq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.FieldSpawnTable;
import l1r.ao.InnTable;
import l1r.ap.L1PcInstance;
import l1r.at.L1GameTimeClock;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class L1Dungeon {
   private static final Logger a = Logger.getLogger(L1Dungeon.class.getName());
   private static L1Dungeon b = null;
   private final HashMap<Long, L1Dungeon.L1R_a> c = new HashMap<>();
   private static long d = (long)Math.pow(10.0, 10.0);
   private static long e = (long)Math.pow(10.0, 5.0);

   public static L1Dungeon a() {
      if (b == null) {
         b = new L1Dungeon();
      }

      return b;
   }

   private L1Dungeon() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM dungeon");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("src_mapid");
            int var5 = var3.getInt("src_x");
            int var6 = var3.getInt("src_y");
            long var7 = var5 * d + var6 * L1Dungeon.e + var4;
            int var9 = var3.getInt("new_x");
            int var10 = var3.getInt("new_y");
            int var11 = var3.getInt("new_mapid");
            int var12 = var3.getInt("new_heading");
            L1Dungeon.L1R_b var13 = L1Dungeon.L1R_b.a;
            if ((var5 < 33430 || var5 > 33432 || var6 != 33503 || var4 != 4)
               && (var5 != 32733 && var5 != 32734 && var5 != 32735 && var5 != 32736 || var6 != 32794 || var4 != 83)) {
               if ((var5 != 32935 && var5 != 32936 && var5 != 32937 || var6 != 33058 || var4 != 70)
                  && (var5 != 32732 && var5 != 32733 && var5 != 32734 && var5 != 32735 || var6 != 32796 || var4 != 84)) {
                  if ((var5 != 32750 && var5 != 32751 && var5 != 32752 || var6 != 32874 || var4 != 445)
                     && (var5 != 32731 && var5 != 32732 && var5 != 32733 || var6 != 32796 || var4 != 447)) {
                     if ((var5 != 32296 && var5 != 32297 && var5 != 32298 || var6 != 33087 || var4 != 440)
                        && (var5 != 32735 && var5 != 32736 && var5 != 32737 || var6 != 32794 || var4 != 446)) {
                        if ((var5 != 32630 && var5 != 32631 && var5 != 32632 || var6 != 32983 || var4 != 0)
                           && (var5 != 32733 && var5 != 32734 && var5 != 32735 || var6 != 32796 || var4 != 5)) {
                           if ((var5 != 32540 && var5 != 32542 && var5 != 32543 && var5 != 32544 && var5 != 32545 || var6 != 32728 || var4 != 4)
                              && (var5 != 32734 && var5 != 32735 && var5 != 32736 && var5 != 32737 || var6 != 32794 || var4 != 6)) {
                              if (var5 == 32810 && var6 >= 32890 && var6 <= 32891 && var4 == 10500) {
                                 var13 = L1Dungeon.L1R_b.i;
                              } else if (InnTable.b(var5, var6, var4)) {
                                 var13 = L1Dungeon.L1R_b.h;
                              }
                           } else {
                              var13 = L1Dungeon.L1R_b.g;
                           }
                        } else {
                           var13 = L1Dungeon.L1R_b.f;
                        }
                     } else {
                        var13 = L1Dungeon.L1R_b.e;
                     }
                  } else {
                     var13 = L1Dungeon.L1R_b.d;
                  }
               } else {
                  var13 = L1Dungeon.L1R_b.c;
               }
            } else {
               var13 = L1Dungeon.L1R_b.b;
            }

            FieldSpawnTable.a().a(13135, var5, var6, var4);
            L1Dungeon.L1R_a var14 = new L1Dungeon.L1R_a(var9, var10, (short)var11, var12, var13, null);
            if (this.c.containsKey(var7)) {
               System.out.println("dungeon 傳送點重複。key=" + var7);
            }

            this.c.put(var7, var14);
         }
      } catch (SQLException var18) {
         a.log(Level.SEVERE, var18.getLocalizedMessage(), var18);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(L1Location var1, L1Location var2) {
      long var3 = var1.f() * d + var1.g() * e + var1.b();
      L1Dungeon.L1R_a var5 = new L1Dungeon.L1R_a(var2.f(), var2.g(), (short)var2.b(), 5, L1Dungeon.L1R_b.a, null);
      FieldSpawnTable.a().a(13135, var1.f(), var1.g(), var1.b());
      if (this.c.containsKey(var3)) {
         System.out.println("dungeon 傳送點重複。key=" + var3);
      }

      this.c.put(var3, var5);
   }

   public boolean a(int var1, int var2, int var3, L1PcInstance var4) {
      long var5 = var1 * d + var2 * e + var3;
      if (!this.c.containsKey(var5)) {
         return false;
      }

      L1Dungeon.L1R_a var7 = this.c.get(var5);
      int var8 = var7.d;
      int var9 = var7.b;
      int var10 = var7.c;
      int var11 = var7.e;
      L1Dungeon.L1R_b var12 = var7.f;
      boolean var13 = false;
      if (var12 == L1Dungeon.L1R_b.a) {
         var13 = true;
      } else if (var12 == L1Dungeon.L1R_b.i) {
         if (var4.dX() == 4) {
            var9 = 32774;
            var10 = 32816;
         } else if (var4.dX() == 5) {
            var9 = 32694;
            var10 = 32895;
         } else {
            var9 = 32774;
            var10 = 32975;
         }

         var13 = true;
      } else {
         if (var12 == L1Dungeon.L1R_b.h) {
            return InnTable.a().a(var4);
         }

         if (var12 == L1Dungeon.L1R_b.f && var4.j().g(40299, 1)
            || var12 == L1Dungeon.L1R_b.b && var4.j().g(40300, 1)
            || var12 == L1Dungeon.L1R_b.d && var4.j().g(40302, 1)) {
            int var15 = L1GameTimeClock.a().b().c() % 86400;
            if (var15 >= 5400 && var15 < 9000
               || var15 >= 16200 && var15 < 19800
               || var15 >= 27000 && var15 < 30600
               || var15 >= 37800 && var15 < 41400
               || var15 >= 48600 && var15 < 52200
               || var15 >= 59400 && var15 < 63000
               || var15 >= 70200 && var15 < 73800
               || var15 >= 81000 && var15 < 84600) {
               var13 = true;
            }
         } else if (var12 == L1Dungeon.L1R_b.g && var4.j().g(40298, 1)
            || var12 == L1Dungeon.L1R_b.c && var4.j().g(40308, 1000)
            || var12 == L1Dungeon.L1R_b.e && var4.j().g(40303, 1)) {
            int var14 = L1GameTimeClock.a().b().c() % 86400;
            if (var14 >= 0 && var14 < 360
               || var14 >= 10800 && var14 < 14400
               || var14 >= 21600 && var14 < 25200
               || var14 >= 32400 && var14 < 36000
               || var14 >= 43200 && var14 < 46800
               || var14 >= 54000 && var14 < 57600
               || var14 >= 64800 && var14 < 68400
               || var14 >= 75600 && var14 < 79200) {
               var13 = true;
            }
         }
      }

      if (var13) {
         var4.j(78, 2000);
         var4.b();
         var4.d();
         L1Teleport.a(var4, var9, var10, var8, var11, false);
         return true;
      } else {
         return false;
      }
   }

   class L1R_a {
      private final int b;
      private final int c;
      private final int d;
      private final int e;
      private final L1Dungeon.L1R_b f;

      private L1R_a(int var2, int var3, short var4, int var5, L1Dungeon.L1R_b var6) {
         this.b = var2;
         this.c = var3;
         this.d = var4;
         this.e = var5;
         this.f = var6;
      }

      // $VF: synthetic method
      L1R_a(int var2, int var3, short var4, int var5, L1Dungeon.L1R_b var6, L1Dungeon.L1R_a var7) {
         this(var2, var3, var4, var5, var6);
      }
   }

   private enum L1R_b {
      a,
      b,
      c,
      d,
      e,
      f,
      g,
      h,
      i;
   }
}
