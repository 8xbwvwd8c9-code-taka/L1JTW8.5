package l1r.aq;

import l1r.ao.TownTable;
import l1r.ap.L1NpcInstance;
import l1r.bh.L1Town;
import l1r.bi.Point;
import l1r.bi.Random;

public class L1TownLocation {
   public static final int a = 1;
   public static final int b = 2;
   public static final int c = 3;
   public static final int d = 4;
   public static final int e = 5;
   public static final int f = 6;
   public static final int g = 7;
   public static final int h = 8;
   public static final int i = 9;
   public static final int j = 10;
   private static final int x = 11;
   public static final int k = 12;
   private static final int y = 13;
   public static final int l = 14;
   private static final int z = 15;
   private static final int A = 16;
   private static final int B = 17;
   private static final short C = 0;
   private static final Point[] D = new Point[]{
      new Point(32600, 32942), new Point(32574, 32944), new Point(32580, 32923), new Point(32557, 32975), new Point(32597, 32914), new Point(32580, 32974)
   };
   private static final short E = 4;
   private static final Point[] F = new Point[]{
      new Point(33071, 33402), new Point(33091, 33396), new Point(33085, 33402), new Point(33097, 33366), new Point(33110, 33365), new Point(33072, 33392)
   };
   private static final short G = 4;
   private static final Point[] H = new Point[]{
      new Point(32601, 32757),
      new Point(32625, 32809),
      new Point(32611, 32726),
      new Point(32612, 32781),
      new Point(32605, 32761),
      new Point(32614, 32739),
      new Point(32612, 32775)
   };
   private static final short I = 4;
   private static final Point[] J = new Point[]{
      new Point(32750, 32435), new Point(32745, 32447), new Point(32738, 32452), new Point(32741, 32436), new Point(32749, 32446)
   };
   private static final short K = 4;
   private static final Point[] L = new Point[]{
      new Point(32608, 33178), new Point(32626, 33186), new Point(32630, 33179), new Point(32625, 33207), new Point(32638, 33203), new Point(32621, 33179)
   };
   private static final short M = 4;
   private static final Point[] N = new Point[]{
      new Point(33048, 32750),
      new Point(33059, 32768),
      new Point(33047, 32761),
      new Point(33059, 32759),
      new Point(33051, 32775),
      new Point(33048, 32778),
      new Point(33064, 32773),
      new Point(33057, 32748)
   };
   private static final short O = 4;
   private static final Point[] P = new Point[]{
      new Point(33435, 32803),
      new Point(33439, 32817),
      new Point(33440, 32809),
      new Point(33419, 32810),
      new Point(33426, 32823),
      new Point(33418, 32818),
      new Point(33432, 32824)
   };
   private static final short Q = 4;
   private static final Point[] R = new Point[]{
      new Point(33593, 33242),
      new Point(33593, 33248),
      new Point(33604, 33236),
      new Point(33599, 33236),
      new Point(33610, 33247),
      new Point(33610, 33241),
      new Point(33599, 33252),
      new Point(33605, 33252)
   };
   private static final short S = 4;
   private static final Point[] T = new Point[]{
      new Point(33702, 32492),
      new Point(33747, 32508),
      new Point(33696, 32498),
      new Point(33723, 32512),
      new Point(33710, 32521),
      new Point(33724, 32488),
      new Point(33693, 32513)
   };
   private static final short U = 4;
   private static final Point[] V = new Point[]{
      new Point(34086, 32280),
      new Point(34037, 32230),
      new Point(34022, 32254),
      new Point(34021, 32269),
      new Point(34044, 32290),
      new Point(34049, 32316),
      new Point(34081, 32249),
      new Point(34074, 32313),
      new Point(34064, 32230)
   };
   private static final short W = 4;
   private static final Point[] X = new Point[]{
      new Point(33065, 32358),
      new Point(33052, 32313),
      new Point(33030, 32342),
      new Point(33068, 32320),
      new Point(33071, 32314),
      new Point(33030, 32370),
      new Point(33076, 32324),
      new Point(33068, 32336)
   };
   private static final short Y = 4;
   private static final Point[] Z = new Point[]{
      new Point(33915, 33114),
      new Point(34061, 33115),
      new Point(34090, 33168),
      new Point(34011, 33136),
      new Point(34093, 33117),
      new Point(33959, 33156),
      new Point(33992, 33120),
      new Point(34047, 33156)
   };
   private static final short aa = 304;
   private static final Point[] ab = new Point[]{
      new Point(32856, 32898), new Point(32860, 32916), new Point(32868, 32893), new Point(32875, 32903), new Point(32855, 32898)
   };
   private static final short ac = 310;
   private static final Point[] ad = new Point[]{
      new Point(32818, 32805), new Point(32800, 32798), new Point(32815, 32819), new Point(32823, 32811), new Point(32817, 32828)
   };
   private static final short ae = 400;
   private static final Point[] af = new Point[]{
      new Point(32570, 32667), new Point(32559, 32678), new Point(32564, 32683), new Point(32574, 32661), new Point(32576, 32669), new Point(32572, 32662)
   };
   private static final short ag = 440;
   private static final Point[] ah = new Point[]{new Point(32431, 33058), new Point(32407, 33054)};
   private static final short ai = 400;
   private static final Point[] aj = new Point[]{
      new Point(32599, 32916), new Point(32599, 32923), new Point(32603, 32908), new Point(32595, 32908), new Point(32591, 32918)
   };
   public static final int[][] m = new int[][]{{32543, 32905, 0, 1}, {32671, 32987, 0, 1}};
   public static final int[][] n = new int[][]{{33024, 33330, 4, 2}, {33152, 33430, 4, 2}};
   public static final int[][] o = new int[][]{{32581, 32704, 4, 3}, {32655, 32834, 4, 3}};
   public static final int[][] p = new int[][]{{32708, 32412, 4, 4}, {32773, 32485, 4, 4}};
   public static final int[][] q = new int[][]{{32578, 33152, 4, 5}, {32659, 33233, 4, 5}};
   public static final int[][] r = new int[][]{{33027, 32718, 4, 6}, {33084, 32816, 4, 6}};
   public static final int[][] s = new int[][]{{33332, 32632, 4, 7}, {33548, 32895, 4, 7}};
   public static final int[][] t = new int[][]{{33556, 33197, 4, 8}, {33654, 33457, 4, 8}};
   public static final int[][] u = new int[][]{{33679, 32471, 4, 9}, {33755, 32531, 4, 9}};
   public static final int[][] v = new int[][]{{34013, 32222, 4, 10}, {34083, 32326, 4, 10}};
   private static final int[][] ak = new int[][]{{33034, 32304, 4, 11}, {33077, 32388, 4, 11}};
   public static final int[][] w = new int[][]{{33856, 33072, 4, 12}, {34229, 33435, 4, 12}};
   private static final int[][] al = new int[][]{{32380, 33018, 440, 16}, {32476, 33095, 440, 16}};

   private L1TownLocation() {
   }

   public static int[] a(int var0) {
      int[] var1 = new int[3];
      if (var0 == 1) {
         int var2 = Random.a(D.length);
         var1[0] = D[var2].f();
         var1[1] = D[var2].g();
         var1[2] = 0;
      } else if (var0 == 2) {
         int var3 = Random.a(F.length);
         var1[0] = F[var3].f();
         var1[1] = F[var3].g();
         var1[2] = 4;
      } else if (var0 == 6) {
         int var4 = Random.a(N.length);
         var1[0] = N[var4].f();
         var1[1] = N[var4].g();
         var1[2] = 4;
      } else if (var0 == 3) {
         int var5 = Random.a(H.length);
         var1[0] = H[var5].f();
         var1[1] = H[var5].g();
         var1[2] = 4;
      } else if (var0 == 4) {
         int var6 = Random.a(J.length);
         var1[0] = J[var6].f();
         var1[1] = J[var6].g();
         var1[2] = 4;
      } else if (var0 == 5) {
         int var7 = Random.a(L.length);
         var1[0] = L[var7].f();
         var1[1] = L[var7].g();
         var1[2] = 4;
      } else if (var0 == 7) {
         int var8 = Random.a(P.length);
         var1[0] = P[var8].f();
         var1[1] = P[var8].g();
         var1[2] = 4;
      } else if (var0 == 8) {
         int var9 = Random.a(R.length);
         var1[0] = R[var9].f();
         var1[1] = R[var9].g();
         var1[2] = 4;
      } else if (var0 == 9) {
         int var10 = Random.a(T.length);
         var1[0] = T[var10].f();
         var1[1] = T[var10].g();
         var1[2] = 4;
      } else if (var0 == 10) {
         int var11 = Random.a(V.length);
         var1[0] = V[var11].f();
         var1[1] = V[var11].g();
         var1[2] = 4;
      } else if (var0 == 11) {
         int var12 = Random.a(X.length);
         var1[0] = X[var12].f();
         var1[1] = X[var12].g();
         var1[2] = 4;
      } else if (var0 == 12) {
         int var13 = Random.a(Z.length);
         var1[0] = Z[var13].f();
         var1[1] = Z[var13].g();
         var1[2] = 4;
      } else if (var0 == 13) {
         int var14 = Random.a(ab.length);
         var1[0] = ab[var14].f();
         var1[1] = ab[var14].g();
         var1[2] = 304;
      } else if (var0 == 14) {
         int var15 = Random.a(ad.length);
         var1[0] = ad[var15].f();
         var1[1] = ad[var15].g();
         var1[2] = 310;
      } else if (var0 == 15) {
         int var16 = Random.a(af.length);
         var1[0] = af[var16].f();
         var1[1] = af[var16].g();
         var1[2] = 400;
      } else if (var0 == 16) {
         int var17 = Random.a(ah.length);
         var1[0] = ah[var17].f();
         var1[1] = ah[var17].g();
         var1[2] = 440;
      } else if (var0 == 17) {
         int var18 = Random.a(aj.length);
         var1[0] = aj[var18].f();
         var1[1] = aj[var18].g();
         var1[2] = 400;
      } else {
         int var19 = Random.a(F.length);
         var1[0] = F[var19].f();
         var1[1] = F[var19].g();
         var1[2] = 4;
      }

      return var1;
   }

   public static int a(L1NpcInstance var0) {
      int var1 = 0;
      int var2 = a((L1Character)var0);
      if (var2 >= 1 && var2 <= 10) {
         L1Town var3 = TownTable.a().a(var2);
         var1 = var3.e() + 2;
      }

      return var1;
   }

   public static int a(int var0, int var1, int var2) {
      int[][][] var3 = new int[][][]{m, n, o, p, q, r, s, t, u, v, ak, w, al};
      int[][][] var7 = var3;
      int var6 = var3.length;

      for (int var5 = 0; var5 < var6; var5++) {
         int[][] var4 = var7[var5];
         int[] var8 = var4[0];
         int[] var9 = var4[1];
         if (var2 == var8[2] && var0 >= var8[0] && var1 >= var8[1] && var0 <= var9[0] && var1 <= var9[1]) {
            return var8[3];
         }
      }

      return 0;
   }

   public static int a(L1Character var0) {
      return a(var0.fs(), var0.ft(), var0.fp());
   }
}
