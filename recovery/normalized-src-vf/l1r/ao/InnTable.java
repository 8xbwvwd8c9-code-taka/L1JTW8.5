package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Dungeon;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.bi.SQLUtil;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

public class InnTable {
   private static final Logger d = Logger.getLogger(InnTable.class.getName());
   private static InnTable e;
   private final ConcurrentHashMap<Integer, InnTable.L1R_a> f = new ConcurrentHashMap<>();
   public static final int a = -1;
   public static final int b = -2;
   public static final int c = -3;

   public static InnTable a() {
      if (e == null) {
         e = new InnTable();
      }

      return e;
   }

   private InnTable() {
      this.b();
      this.c();
   }

   public boolean a(int var1, int var2, int var3) {
      if (var1 <= 0 || var2 <= 0 || var3 < 0 || this.f.containsKey(var1)) {
         return false;
      }

      InnTable.L1R_a var4 = new InnTable.L1R_a(null);
      var4.a = var1;
      var4.b = "note";
      var4.c = var2;
      var4.d = var3;
      var4.e = new Timestamp(System.currentTimeMillis() + 14400000L);
      Connection var5 = null;
      PreparedStatement var6 = null;

      try {
         var5 = DatabaseFactory.a().b();
         var6 = var5.prepareStatement("INSERT INTO inns SET keyid=?,note=?, count=?, roomid=?, dueTime=? ");
         var6.setInt(1, var4.a);
         var6.setString(2, var4.b);
         var6.setInt(3, var4.c);
         var6.setInt(4, var4.d);
         var6.setTimestamp(5, var4.e);
         var6.execute();
         this.f.put(var4.a, var4);
         return true;
      } catch (SQLException var10) {
         d.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
         return false;
      } finally {
         SQLUtil.a(var6);
         SQLUtil.a(var5);
      }
   }

   private void a(int var1, int var2) {
      InnTable.L1R_a var3 = this.f.get(var1);
      if (var3 != null) {
         var3.c -= var2;
         if (var3.c <= 0) {
            this.b(var1);
         } else {
            Connection var4 = null;
            PreparedStatement var5 = null;

            try {
               var4 = DatabaseFactory.a().b();
               var5 = var4.prepareStatement("UPDATE inns SET count=? WHERE keyid = ?");
               var5.setInt(1, var3.c);
               var5.setInt(2, var1);
               var5.execute();
            } catch (SQLException var10) {
               d.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
            } finally {
               SQLUtil.a(var5);
               SQLUtil.a(var4);
            }
         }
      }
   }

   private void b(int var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM inns WHERE keyid = ?");
         var3.setInt(1, var1);
         var3.execute();
         this.f.remove(var1);
      } catch (SQLException var8) {
         d.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM inns");
         var3 = var2.executeQuery();

         while (var3.next()) {
            InnTable.L1R_a var4 = new InnTable.L1R_a(null);
            var4.a = var3.getInt("keyid");
            var4.c = var3.getInt("count");
            var4.d = var3.getInt("roomid");
            var4.e = var3.getTimestamp("dueTime");
            Calendar var5 = Calendar.getInstance();
            if (var5.getTimeInMillis() > var4.e.getTime()) {
               this.b(var4.a);
            } else if (!this.f.containsKey(var4.a)) {
               this.f.put(var4.a, var4);
            }
         }
      } catch (SQLException var9) {
         d.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private void c() {
      int[] var1 = new int[]{16384, 16896, 17408, 17920, 18432, 18944, 19456, 19968, 20480, 20992, 21504, 22016, 22528, 23040, 23552, 24064, 24576, 25088};
      int[] var5 = var1;
      int var4 = var1.length;

      for (int var3 = 0; var3 < var4; var3++) {
         int var2 = var5[var3];
         L1Map var6 = L1WorldMap.b().a(var2);

         for (int var7 = 1; var7 < Config.aw; var7++) {
            try {
               L1Map var8 = var6.s();
               var8.a = var2 + var7;
               MapsTable.a().a(var8);
               L1WorldMap.b().a().put(var8.a, var8);
               GetBackRestartTable.a().a(var2, var8.a);
               if (var2 == 16384) {
                  L1Dungeon.a().a(new L1Location(32746, 32803, var8.a), new L1Location(32599, 32931, 0));
               } else if (var2 == 16896) {
                  L1Dungeon.a().a(new L1Location(32744, 32808, var8.a), new L1Location(32599, 32931, 0));
               } else if (var2 == 17408) {
                  L1Dungeon.a().a(new L1Location(32744, 32803, var8.a), new L1Location(32631, 32761, 4));
               } else if (var2 == 17920) {
                  L1Dungeon.a().a(new L1Location(32745, 32807, var8.a), new L1Location(32631, 32761, 4));
               } else if (var2 == 18432) {
                  L1Dungeon.a().a(new L1Location(32745, 32803, var8.a), new L1Location(33437, 32790, 4));
               } else if (var2 == 18944) {
                  L1Dungeon.a().a(new L1Location(32745, 32087, var8.a), new L1Location(33437, 32790, 4));
               } else if (var2 == 19456) {
                  L1Dungeon.a().a(new L1Location(32745, 32803, var8.a), new L1Location(34067, 32254, 4));
               } else if (var2 == 19968) {
                  L1Dungeon.a().a(new L1Location(32745, 32807, var8.a), new L1Location(34067, 32254, 4));
               } else if (var2 == 20480) {
                  L1Dungeon.a().a(new L1Location(32745, 32803, var8.a), new L1Location(32632, 33165, 4));
               } else if (var2 == 20992) {
                  L1Dungeon.a().a(new L1Location(32745, 32807, var8.a), new L1Location(32632, 33165, 4));
               } else if (var2 == 21504) {
                  L1Dungeon.a().a(new L1Location(32745, 32803, var8.a), new L1Location(33112, 33376, 4));
               } else if (var2 == 22016) {
                  L1Dungeon.a().a(new L1Location(32745, 32807, var8.a), new L1Location(33112, 33376, 4));
               } else if (var2 == 22528) {
                  L1Dungeon.a().a(new L1Location(32745, 32803, var8.a), new L1Location(33604, 33276, 4));
               } else if (var2 == 23040) {
                  L1Dungeon.a().a(new L1Location(32745, 32807, var8.a), new L1Location(33604, 33276, 4));
               } else if (var2 == 23552) {
                  L1Dungeon.a().a(new L1Location(32745, 32803, var8.a), new L1Location(33985, 33312, 4));
               } else if (var2 == 24064) {
                  L1Dungeon.a().a(new L1Location(32745, 32807, var8.a), new L1Location(33985, 33312, 4));
               } else if (var2 == 24576) {
                  L1Dungeon.a().a(new L1Location(32745, 32803, var8.a), new L1Location(32450, 33047, 440));
               } else if (var2 == 25088) {
                  L1Dungeon.a().a(new L1Location(32745, 32807, var8.a), new L1Location(32450, 33047, 440));
               }
            } catch (CloneNotSupportedException var9) {
               d.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
            }
         }
      }
   }

   public boolean a(L1PcInstance var1) {
      L1ItemInstance[] var5;
      int var4 = (var5 = var1.j().d(40312)).length;

      for (int var3 = 0; var3 < var4; var3++) {
         L1ItemInstance var2 = var5[var3];
         InnTable.L1R_a var6 = this.f.get(var2.M());
         if (var6 != null) {
            Timestamp var7 = var6.e;
            if (var7 != null) {
               Calendar var8 = Calendar.getInstance();
               if (var8.getTimeInMillis() <= var7.getTime()) {
                  int var9 = 0;
                  int var10 = 0;
                  int var11 = var6.d;
                  if (var11 >= 16384 && var11 <= 16895) {
                     var9 = 32745;
                     var10 = 32803;
                  } else if (var11 >= 16896 && var11 <= 17407) {
                     var9 = 32743;
                     var10 = 32808;
                  } else if (var11 >= 17408 && var11 <= 17919) {
                     var9 = 32743;
                     var10 = 32803;
                  } else if (var11 >= 17920 && var11 <= 18431) {
                     var9 = 32744;
                     var10 = 32807;
                  } else if (var11 >= 18432 && var11 <= 18943) {
                     var9 = 32744;
                     var10 = 32803;
                  } else if (var11 >= 18944 && var11 <= 19455) {
                     var9 = 32744;
                     var10 = 32807;
                  } else if (var11 >= 19456 && var11 <= 19967) {
                     var9 = 32744;
                     var10 = 32803;
                  } else if (var11 >= 19968 && var11 <= 20479) {
                     var9 = 32744;
                     var10 = 32807;
                  } else if (var11 >= 20480 && var11 <= 20991) {
                     var9 = 32744;
                     var10 = 32803;
                  } else if (var11 >= 20992 && var11 <= 21503) {
                     var9 = 32744;
                     var10 = 32807;
                  } else if (var11 >= 21504 && var11 <= 22016) {
                     var9 = 32744;
                     var10 = 32803;
                  } else if (var11 >= 22016 && var11 <= 22527) {
                     var9 = 32744;
                     var10 = 32807;
                  } else if (var11 >= 22528 && var11 <= 23039) {
                     var9 = 32744;
                     var10 = 32803;
                  } else if (var11 >= 23040 && var11 <= 23551) {
                     var9 = 32744;
                     var10 = 32807;
                  } else if (var11 >= 23552 && var11 <= 24063) {
                     var9 = 32745;
                     var10 = 32803;
                  } else if (var11 >= 24064 && var11 <= 24575) {
                     var9 = 32745;
                     var10 = 32807;
                  } else if (var11 >= 24576 && var11 <= 25087) {
                     var9 = 32745;
                     var10 = 32803;
                  } else if (var11 >= 25088 && var11 <= 25599) {
                     var9 = 32745;
                     var10 = 32807;
                  }

                  if (var9 * var10 == 0) {
                     return false;
                  }

                  L1Teleport.a(var1, var9, var10, var11, 6, true, false);
                  return true;
               }
            }
         }
      }

      return false;
   }

   public int b(L1PcInstance var1) {
      int var2 = 0;
      L1ItemInstance[] var6;
      int var5 = (var6 = var1.j().d(40312)).length;

      for (int var4 = 0; var4 < var5; var4++) {
         L1ItemInstance var3 = var6[var4];
         InnTable.L1R_a var7 = this.f.get(var3.M());
         if (var7 != null) {
            Timestamp var8 = var7.e;
            if (var8 != null) {
               Calendar var9 = Calendar.getInstance();
               if (var9.getTimeInMillis() < var8.getTime()) {
                  var2 += 60 * var3.E();
               }

               var1.j().f(var3);
               this.a(var3.M(), var3.E());
            }
         }
      }

      return var2;
   }

   public boolean a(int var1) {
      for (InnTable.L1R_a var2 : this.f.values()) {
         if (var2.d == var1) {
            Timestamp var4 = var2.e;
            if (var4 == null) {
               return true;
            }

            Calendar var5 = Calendar.getInstance();
            if (var5.getTimeInMillis() > var4.getTime()) {
               for (L1Object var6 : L1World.a().b(var1).values()) {
                  if (var6 instanceof L1PcInstance) {
                     L1PcInstance var8 = (L1PcInstance)var6;
                     GetBackRestartTable.a().b(var8);
                  }
               }

               return true;
            }

            return false;
         }
      }

      return true;
   }

   public int a(L1PcInstance var1, int var2, boolean var3) {
      L1ItemInstance[] var7;
      int var6 = (var7 = var1.j().d(40312)).length;

      for (int var5 = 0; var5 < var6; var5++) {
         L1ItemInstance var4 = var7[var5];
         InnTable.L1R_a var8 = this.f.get(var4.M());
         if (var8 != null && !this.a(var8.d)) {
            return this.c(var8.d) ? -2 : -1;
         }
      }

      int[] var9 = null;
      switch (var2) {
         case 70012:
            var9 = new int[]{16384, 16896};
            break;
         case 70019:
            var9 = new int[]{17408, 17920};
            break;
         case 70031:
            var9 = new int[]{18432, 18944};
            break;
         case 70054:
            var9 = new int[]{23552, 24064};
            break;
         case 70065:
            var9 = new int[]{19456, 19968};
            break;
         case 70070:
            var9 = new int[]{20480, 20992};
            break;
         case 70075:
            var9 = new int[]{21504, 22016};
            break;
         case 70084:
            var9 = new int[]{22528, 23040};
            break;
         case 70096:
            var9 = new int[]{24576, 25088};
            break;
         default:
            System.out.println("進入房間或會議廳 has some error");
            return -3;
      }

      int var11 = -3;

      for (int var12 = 0; var12 < Config.aw; var12++) {
         int var13 = var9[var3 ? 1 : 0];
         if (this.a(var13 + var12)) {
            var11 = var13 + var12;
            break;
         }
      }

      return var11;
   }

   private boolean c(int var1) {
      if (var1 >= 16896 && var1 <= 17407) {
         return true;
      } else if (var1 >= 17920 && var1 <= 18431) {
         return true;
      } else if (var1 >= 18944 && var1 <= 19455) {
         return true;
      } else if (var1 >= 19968 && var1 <= 20479) {
         return true;
      } else if (var1 >= 20992 && var1 <= 21503) {
         return true;
      } else if (var1 >= 22016 && var1 <= 22527) {
         return true;
      } else if (var1 >= 23040 && var1 <= 23551) {
         return true;
      } else {
         return var1 >= 24064 && var1 <= 24575 ? true : var1 >= 25088 && var1 <= 25599;
      }
   }

   public static boolean b(int var0, int var1, int var2) {
      if (var0 == 32600 && var1 == 32931 && var2 == 0) {
         return true;
      } else if (var0 == 32632 && var1 == 32761 && var2 == 4) {
         return true;
      } else if (var0 == 33112 && var1 == 33376 && var2 == 4) {
         return true;
      } else if (var0 == 32632 && var1 == 33165 && var2 == 4) {
         return true;
      } else if (var0 == 33605 && var1 == 33275 && var2 == 4) {
         return true;
      } else if (var0 == 33437 && var1 == 32789 && var2 == 4) {
         return true;
      } else if (var0 == 34068 && var1 == 32254 && var2 == 4) {
         return true;
      } else {
         return var0 == 33985 && var1 == 33312 && var2 == 4 ? true : var0 == 32450 && var1 == 33047 && var2 == 440;
      }
   }

   public static String a(L1ItemInstance var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append(" #");
      String var2 = String.valueOf(var0.M());
      String var3 = "";
      String var4 = "";

      for (int var5 = 0; var5 < var2.length() && var5 < 5; var5++) {
         var3 = var3 + String.valueOf(var2.charAt(var5));
      }

      var1.append(var3);

      for (int var7 = 0; var7 < var2.length(); var7++) {
         if (var7 % 2 == 0) {
            var3 = String.valueOf(var2.charAt(var7));
         } else {
            var4 = var3 + String.valueOf(var2.charAt(var7));
            var1.append(Integer.toHexString(Integer.valueOf(var4)).toLowerCase());
         }
      }

      return var1.toString();
   }

   private class L1R_a {
      public int a;
      public String b;
      public int c;
      public int d;
      public Timestamp e;

      private L1R_a() {
      }

      // $VF: synthetic method
      L1R_a(InnTable.L1R_a var2) {
         this();
      }
   }
}
