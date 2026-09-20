package l1r.aq;

import java.util.HashMap;
import java.util.Map.Entry;
import l1r.ao.CastleTable;
import l1r.ap.L1NpcInstance;
import l1r.at.L1GameTime;
import l1r.at.L1GameTimeAdapter;
import l1r.at.L1GameTimeClock;
import l1r.ax.L1MapArea;
import l1r.bh.L1Castle;
import l1r.bi.Random;

public class L1CastleLocation {
   public static final int a = 1;
   public static final int b = 2;
   public static final int c = 3;
   public static final int d = 4;
   public static final int e = 5;
   public static final int f = 6;
   public static final int g = 7;
   public static final int h = 8;
   private static final int i = 33169;
   private static final int j = 32778;
   private static final short k = 4;
   private static final int l = 33089;
   private static final int m = 33219;
   private static final int n = 32717;
   private static final int o = 32827;
   private static final int p = 4;
   private static final int q = 15;
   private static final int r = 32799;
   private static final int s = 32284;
   private static final int t = 4;
   private static final int u = 32750;
   private static final int v = 32850;
   private static final int w = 32250;
   private static final int x = 32350;
   private static final int y = 4;
   private static final int z = 32623;
   private static final int A = 33379;
   private static final int B = 4;
   private static final int C = 32571;
   private static final int D = 32721;
   private static final int E = 33350;
   private static final int F = 33460;
   private static final int G = 4;
   private static final int H = 29;
   private static final int I = 33631;
   private static final int J = 32678;
   private static final int K = 4;
   private static final int L = 33559;
   private static final int M = 33686;
   private static final int N = 32615;
   private static final int O = 32755;
   private static final int P = 4;
   private static final int Q = 52;
   private static final int R = 33524;
   private static final int S = 33396;
   private static final int T = 4;
   private static final int U = 33458;
   private static final int V = 33583;
   private static final int W = 33315;
   private static final int X = 33490;
   private static final int Y = 4;
   private static final int Z = 64;
   private static final int aa = 32828;
   private static final int ab = 32818;
   private static final int ac = 66;
   private static final int ad = 32755;
   private static final int ae = 32870;
   private static final int af = 32790;
   private static final int ag = 32920;
   private static final int ah = 66;
   private static final int ai = 34090;
   private static final int aj = 33260;
   private static final int ak = 4;
   private static final int al = 34007;
   private static final int am = 34162;
   private static final int an = 33172;
   private static final int ao = 33332;
   private static final int ap = 4;
   private static final int aq = 300;
   private static final int ar = 34057;
   private static final int as = 33291;
   private static final int at = 34123;
   private static final int au = 33291;
   private static final int av = 34057;
   private static final int aw = 33230;
   private static final int ax = 34123;
   private static final int ay = 33230;
   private static final int az = 33033;
   private static final int aA = 32895;
   private static final int aB = 320;
   private static final int aC = 32888;
   private static final int aD = 33070;
   private static final int aE = 32839;
   private static final int aF = 32953;
   private static final int aG = 320;
   private static final int aH = 330;
   private static final HashMap<Integer, L1Location> aI = new HashMap<>();
   private static final HashMap<Integer, L1MapArea> aJ = new HashMap<>();
   private static final HashMap<Integer, Integer> aK = new HashMap<>();
   private static final HashMap<Integer, L1Location> aL = new HashMap<>();
   private static HashMap<Integer, Integer> aM = new HashMap<>();
   private static L1CastleLocation.a aN;

   static {
      aI.put(1, new L1Location(33169, 32778, 4));
      aI.put(2, new L1Location(32799, 32284, 4));
      aI.put(3, new L1Location(32623, 33379, 4));
      aI.put(4, new L1Location(33631, 32678, 4));
      aI.put(5, new L1Location(33524, 33396, 4));
      aI.put(6, new L1Location(32828, 32818, 66));
      aI.put(7, new L1Location(34090, 33260, 4));
      aI.put(8, new L1Location(33033, 32895, 320));
      aJ.put(1, new L1MapArea(33089, 32717, 33219, 32827, 4));
      aJ.put(2, new L1MapArea(32750, 32250, 32850, 32350, 4));
      aJ.put(3, new L1MapArea(32571, 33350, 32721, 33460, 4));
      aJ.put(4, new L1MapArea(33559, 32615, 33686, 32755, 4));
      aJ.put(5, new L1MapArea(33458, 33315, 33583, 33490, 4));
      aJ.put(6, new L1MapArea(32755, 32790, 32870, 32920, 66));
      aJ.put(7, new L1MapArea(34007, 33172, 34162, 33332, 4));
      aJ.put(8, new L1MapArea(32888, 32839, 33070, 32953, 320));
      aK.put(1, 15);
      aK.put(3, 29);
      aK.put(4, 52);
      aK.put(5, 64);
      aK.put(7, 300);
      aK.put(8, 330);
      aL.put(1, new L1Location(34057, 33291, 4));
      aL.put(2, new L1Location(34123, 33291, 4));
      aL.put(3, new L1Location(34057, 33230, 4));
      aL.put(4, new L1Location(34123, 33230, 4));
   }

   private L1CastleLocation() {
   }

   private static int b(L1Location var0) {
      for (Entry var1 : aI.entrySet()) {
         if (var1.getValue().equals(var0)) {
            return var1.getKey();
         }
      }

      return 0;
   }

   public static int a(int var0, int var1, int var2) {
      return b(new L1Location(var0, var1, var2));
   }

   private static int c(L1Location var0) {
      for (Entry var1 : aJ.entrySet()) {
         if (var1.getValue().a(var0)) {
            return var1.getKey();
         }
      }

      for (Entry var3 : aK.entrySet()) {
         if (var3.getValue() == var0.b()) {
            return var3.getKey();
         }
      }

      return 0;
   }

   public static int a(L1Character var0) {
      return c(var0.fu());
   }

   private static boolean a(int var0, L1Location var1) {
      return var0 == c(var1);
   }

   public static boolean a(int var0, L1Character var1) {
      return a(var0, var1.fu());
   }

   public static boolean a(L1Location var0) {
      return c(var0) != 0;
   }

   public static boolean b(int var0, int var1, int var2) {
      return a(new L1Location(var0, var1, var2));
   }

   public static int[] a(int var0) {
      int[] var1 = new int[3];
      L1Location var2 = aI.get(var0);
      if (var2 != null) {
         var1[0] = var2.f();
         var1[1] = var2.g();
         var1[2] = var2.b();
      }

      return var1;
   }

   public static int[] b(int var0) {
      int[] var1 = new int[]{1284, 1284};
      if (var0 == 1) {
         var1[0] = 10457;
         var1[1] = 10459;
      } else if (var0 == 2) {
         var1[0] = 10453;
         var1[1] = 10455;
      } else if (var0 == 3) {
         var1[0] = 10461;
         var1[1] = 10463;
      } else if (var0 == 4) {
         var1[0] = 10477;
         var1[1] = 10479;
      } else if (var0 == 5) {
         var1[0] = 10449;
         var1[1] = 10451;
      } else if (var0 == 6) {
         var1[0] = 10473;
         var1[1] = 10475;
      } else if (var0 == 7) {
         var1[0] = 10469;
         var1[1] = 10471;
      } else if (var0 == 8) {
         var1[0] = 10465;
         var1[1] = 10467;
      }

      return var1;
   }

   public static int[] c(int var0) {
      int[] var1 = new int[5];
      if (var0 == 1) {
         var1[0] = 33089;
         var1[1] = 33219;
         var1[2] = 32717;
         var1[3] = 32827;
         var1[4] = 4;
      } else if (var0 == 2) {
         var1[0] = 32750;
         var1[1] = 32850;
         var1[2] = 32250;
         var1[3] = 32350;
         var1[4] = 4;
      } else if (var0 == 3) {
         var1[0] = 32571;
         var1[1] = 32721;
         var1[2] = 33350;
         var1[3] = 33460;
         var1[4] = 4;
      } else if (var0 == 4) {
         var1[0] = 33559;
         var1[1] = 33686;
         var1[2] = 32615;
         var1[3] = 32755;
         var1[4] = 4;
      } else if (var0 == 5) {
         var1[0] = 33458;
         var1[1] = 33583;
         var1[2] = 33315;
         var1[3] = 33490;
         var1[4] = 4;
      } else if (var0 == 6) {
         var1[0] = 32755;
         var1[1] = 32870;
         var1[2] = 32790;
         var1[3] = 32920;
         var1[4] = 66;
      } else if (var0 == 7) {
         var1[0] = 34007;
         var1[1] = 34162;
         var1[2] = 33172;
         var1[3] = 33332;
         var1[4] = 4;
      } else if (var0 == 8) {
         var1[0] = 32888;
         var1[1] = 33070;
         var1[2] = 32839;
         var1[3] = 32953;
         var1[4] = 320;
      }

      return var1;
   }

   public static int[] d(int var0) {
      int[] var1 = new int[3];
      if (var0 == 1) {
         var1[0] = 32731;
         var1[1] = 32810;
         var1[2] = 15;
      } else if (var0 == 2) {
         var1[0] = 32800;
         var1[1] = 32277;
         var1[2] = 4;
      } else if (var0 == 3) {
         var1[0] = 32730;
         var1[1] = 32814;
         var1[2] = 29;
      } else if (var0 == 4) {
         var1[0] = 32724;
         var1[1] = 32827;
         var1[2] = 52;
      } else if (var0 == 5) {
         var1[0] = 32568;
         var1[1] = 32855;
         var1[2] = 64;
      } else if (var0 == 6) {
         var1[0] = 32853;
         var1[1] = 32810;
         var1[2] = 66;
      } else if (var0 == 7) {
         var1[0] = 32892;
         var1[1] = 32572;
         var1[2] = 300;
      } else if (var0 == 8) {
         var1[0] = 32733;
         var1[1] = 32985;
         var1[2] = 330;
      }

      return var1;
   }

   public static int[] e(int var0) {
      int[] var1;
      if (var0 == 1) {
         var1 = L1TownLocation.a(6);
      } else if (var0 == 2) {
         var1 = L1TownLocation.a(4);
      } else if (var0 == 3) {
         var1 = L1TownLocation.a(5);
      } else if (var0 == 4) {
         var1 = L1TownLocation.a(7);
      } else if (var0 == 5) {
         var1 = L1TownLocation.a(8);
      } else if (var0 == 6) {
         var1 = L1TownLocation.a(9);
      } else if (var0 == 7) {
         var1 = L1TownLocation.a(12);
      } else if (var0 == 8) {
         int var2 = Random.a(3);
         var1 = new int[3];
         if (var2 == 0) {
            var1[0] = 32792;
            var1[1] = 32807;
            var1[2] = 310;
         } else if (var2 == 1) {
            var1[0] = 32816;
            var1[1] = 32820;
            var1[2] = 310;
         } else if (var2 == 2) {
            var1[0] = 32823;
            var1[1] = 32797;
            var1[2] = 310;
         }
      } else {
         var1 = L1TownLocation.a(2);
      }

      return var1;
   }

   public static int a(L1NpcInstance var0) {
      int var1 = 0;
      int var2 = L1TownLocation.a((L1Character)var0);
      byte var3;
      switch (var2) {
         case 1:
         case 7:
            var3 = 4;
            break;
         case 2:
         case 5:
            var3 = 3;
            break;
         case 3:
         case 6:
            var3 = 1;
            break;
         case 4:
            var3 = 2;
            break;
         case 8:
            var3 = 5;
            break;
         case 9:
         case 10:
            var3 = 6;
            break;
         case 11:
         case 13:
         default:
            var3 = 1;
            break;
         case 12:
            var3 = 7;
            break;
         case 14:
            var3 = 8;
      }

      return var3;
   }

   public static int b(L1NpcInstance var0) {
      int var1 = a(var0);
      return var1 != 0 ? aM.get(var1) : 0;
   }

   public static void a() {
      L1Castle[] var3;
      int var2 = (var3 = CastleTable.a().b()).length;

      for (int var1 = 0; var1 < var2; var1++) {
         L1Castle var0 = var3[var1];
         aM.put(var0.a(), var0.e());
      }

      if (aN == null) {
         aN = new L1CastleLocation.a(null);
         L1GameTimeClock.a().a(aN);
      }
   }

   public static int[] f(int var0) {
      int[] var1 = new int[3];
      L1Location var2 = aL.get(var0);
      if (var2 != null) {
         var1[0] = var2.f();
         var1[1] = var2.g();
         var1[2] = var2.b();
      }

      return var1;
   }

   private static class a extends L1GameTimeAdapter {
      private a() {
      }

      @Override
      public void a(L1GameTime var1) {
         L1CastleLocation.a();
      }

      // $VF: synthetic method
      a(L1CastleLocation.a var1) {
         this();
      }
   }
}
