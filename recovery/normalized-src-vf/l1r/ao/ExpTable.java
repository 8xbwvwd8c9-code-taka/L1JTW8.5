package l1r.ao;

public final class ExpTable {
   private static final int b = 99;
   public static final int a = 1859065562;
   private static final int[] c = new int[]{
      0,
      125,
      300,
      500,
      750,
      1296,
      2401,
      4096,
      6561,
      10000,
      14641,
      20736,
      28561,
      38416,
      50625,
      65536,
      83521,
      104976,
      130321,
      160000,
      194481,
      234256,
      279841,
      331776,
      390625,
      456976,
      531441,
      614656,
      707281,
      810000,
      923521,
      1048576,
      1185921,
      1336336,
      1500625,
      1679616,
      1874161,
      2085136,
      2313441,
      2560000,
      2825761,
      3111696,
      3418801,
      3748096,
      4100625,
      4829985,
      6338401,
      9833664,
      19745853,
      31292598,
      44473900,
      59289759,
      75740173,
      93825145,
      113544672,
      134898756,
      157887397,
      182510594,
      208768347,
      236660657,
      266187523,
      297348946,
      330144925,
      364575461,
      400640553,
      436705645,
      472770737,
      508835829,
      544900921,
      580966013,
      617031105,
      653096197,
      689161289,
      725226381,
      761291473,
      797356565,
      833421657,
      869486749,
      905551841,
      941616933,
      977682025,
      1013747117,
      1049812209,
      1085877301,
      1121942393,
      1158007485,
      1194072577,
      1230137669,
      1266202761,
      1302267853,
      1338332945,
      1374398037,
      1410463129,
      1446528221,
      1482593313,
      1518658405,
      1554723497,
      1590788589,
      1626853681,
      1662918773,
      1698983865
   };
   private static int d = 1;
   private static int e = 1;
   private static int f = 1;
   private static int g = 1;
   private static int h = 1;
   private static int i = 1;
   private static int j = 1;
   private static int k = 1;
   private static int l = 1;
   private static int m = 1;
   private static int n = 1;
   private static int o = 1;
   private static int p = 1;
   private static int q = 1;
   private static int r = 1;
   private static int s = 2;
   private static int t = 2;
   private static int u = 2;
   private static int v = 2;
   private static int w = 2;
   private static int x = 4;
   private static int y = 4;
   private static int z = 4;
   private static int A = 4;
   private static int B = 4;
   private static int C = 8;
   private static int D = 8;
   private static int E = 8;
   private static int F = 8;
   private static int G = 16;
   private static int H = 32;
   private static int I = 64;
   private static int J = 128;
   private static int K = 256;
   private static int L = 512;
   private static int M = 1024;
   private static int N = 2048;
   private static int O = 4096;
   private static int P = 8192;
   private static int Q = 16384;
   private static int R = 32768;
   private static int S = 65536;
   private static int T = 131072;
   private static int U = 262144;
   private static int V = 524288;
   private static int W = 1048576;
   private static int X = 2097152;
   private static int Y = 4194304;
   private static int Z = 8388608;
   private static int aa = 16777216;
   private static final int[] ab = new int[]{
      d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, aa
   };

   private ExpTable() {
   }

   public static int a(int var0) {
      return c[var0];
   }

   public static int b(int var0) {
      return a(var0) - a(var0 - 1);
   }

   public static int c(int var0) {
      int var1 = 1;

      while (var1 < c.length && var0 >= c[var1]) {
         var1++;
      }

      return Math.min(var1, 99);
   }

   public static int a(int var0, int var1) {
      return (int)(100.0 * ((double)(var1 - a(var0 - 1)) / b(var0)));
   }

   public static double d(int var0) {
      if (var0 < 50) {
         return 1.0;
      }

      double var1 = 1.0;
      return 1.0 / ab[var0 - 50];
   }
}
