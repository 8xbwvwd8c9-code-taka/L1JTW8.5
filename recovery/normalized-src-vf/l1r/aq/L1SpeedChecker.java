package l1r.aq;

import java.util.EnumMap;
import l1r.am.ListSprReader__obf_c;
import l1r.ap.L1PcInstance;
import l1r.be.S_Blink;
import l1r.be.S_SystemMessage;
import l1r.l1j.server.Config;

public class L1SpeedChecker {
   private final L1PcInstance f;
   private int g;
   private int h;
   private final EnumMap<L1SpeedChecker.L1R_a, Long> i = new EnumMap<>(L1SpeedChecker.L1R_a.class);
   public static final double a = 0.75;
   public static final double b = 0.87;
   public static final double c = 0.375;
   private static final int j = 0;
   private static final int k = 1;
   public static final int d = 2;
   public static int[] e = new int[]{
      6142,
      6145,
      8806,
      11328,
      11329,
      11330,
      11331,
      11332,
      11333,
      11334,
      11335,
      11336,
      11337,
      11338,
      11339,
      11340,
      11341,
      11342,
      11343,
      11344,
      11345,
      11346,
      11347,
      11348,
      11349,
      11350,
      11351,
      11352,
      11353,
      11354,
      11355,
      11356,
      11357,
      11358,
      11359,
      11360,
      11361,
      11362,
      11363,
      11364,
      11365,
      11366,
      11367,
      11368,
      11369,
      11370,
      11371,
      11372,
      11373,
      11374,
      11375,
      11377,
      11378,
      11379,
      11380,
      11381,
      11382,
      11383,
      11384,
      11385,
      11386,
      11387,
      11388,
      11389,
      11390,
      11391,
      11392,
      11393,
      11394,
      11395,
      11396,
      11397,
      11398,
      11399,
      11400,
      11401,
      11402,
      11403,
      11404,
      11405,
      11406,
      11407,
      11408,
      11409,
      11410,
      11411,
      11412,
      11413,
      11414,
      11415,
      11416,
      11417,
      11418,
      11419,
      11420,
      11421,
      11446,
      11447,
      12225,
      12226,
      12227,
      12237,
      12240,
      12541,
      12542,
      12681,
      12702,
      13152,
      13153,
      13216,
      13217,
      13218,
      13219,
      13220,
      13388,
      13389,
      13450,
      11635,
      11650,
      13346,
      13631,
      13635,
      13715,
      13717,
      13719,
      13721,
      13723,
      13725,
      13727,
      13729,
      13731,
      13733,
      13735,
      13737,
      13739,
      13741,
      13743,
      13745,
      14461,
      14462,
      14478,
      14927,
      14928,
      15115,
      15539,
      15537,
      15534,
      15814,
      15550,
      15548,
      15545,
      15526,
      15528,
      15531,
      15532,
      15534,
      15537,
      15539,
      15545,
      15548,
      15550,
      15814,
      15830,
      15831,
      15832,
      15833,
      15834,
      15846,
      15847,
      15848,
      15849,
      15850,
      15599,
      15868,
      15866,
      15865
   };
   // $VF: synthetic field
   private static int[] l;

   public L1SpeedChecker(L1PcInstance var1) {
      this.f = var1;
      this.g = 0;
      this.h = 0;
      long var2 = System.currentTimeMillis();
      L1SpeedChecker.L1R_a[] var7;
      int var6 = (var7 = L1SpeedChecker.L1R_a.values()).length;

      for (int var5 = 0; var5 < var6; var5++) {
         L1SpeedChecker.L1R_a var4 = var7[var5];
         this.i.put(var4, var2);
      }
   }

   public int a(L1SpeedChecker.L1R_a var1) {
      int var2 = 0;
      long var3 = System.currentTimeMillis();
      long var5 = var3 - this.i.get(var1);
      double var7 = (Config.r - 5) / 100.0;
      var5 = (long)(var5 * var7);
      int var9 = this.b(var1);
      if (0L < var5 && var5 < var9) {
         this.g++;
         this.h = 0;
         if (this.g >= Config.p) {
            this.b();
            return 2;
         }

         var2 = 1;
      } else if (var5 >= var9) {
         this.h++;
         if (this.h >= Config.q) {
            this.g = 0;
            this.h = 0;
         }
      }

      this.i.put(var1, var3);
      return var2;
   }

   private void b() {
      this.f.a(new S_SystemMessage("遊戲管理員在遊戲中使用加速器檢測中。"));
      this.f.a(new S_Blink());
      this.h = 3;
   }

   public int b(L1SpeedChecker.L1R_a var1) {
      double[] var3 = new double[]{18.0, 19.0, 20.0, 21.2, 22.5, 24.0, 25.7, 25.7, 25.7, 25.7, 27.7, 30.0, 30.6, 31.1};
      int var2;
      switch (a()[var1.ordinal()]) {
         case 1:
            var2 = ListSprReader__obf_c.a().a(this.f.fe(), this.f.k());
            if (var2 == 0 || var2 == 640) {
               var2 = ListSprReader__obf_c.a().a(this.f.fe(), 0);
            }

            var3 = new double[]{24.0, 24.4, 24.9, 25.5, 26.2, 27.0, 27.9, 28.9, 30.0, 31.2, 31.2, 31.2, 31.2, 31.2};
            break;
         case 2:
            var2 = ListSprReader__obf_c.a().a(this.f.fe(), this.f.k() + 1);
            if (var2 == 0 || var2 == 640) {
               var2 = ListSprReader__obf_c.a().a(this.f.fe(), 1);
            }

            int var4 = this.f.k() + 1;
            if (var4 == 1 || var4 == 47 || var4 == 30 || var4 == 72 || var4 == 78) {
               var3 = new double[]{17.7, 18.7, 19.8, 21.0, 22.4, 24.0, 25.9, 25.9, 25.9, 25.9, 28.0, 30.6, 31.1, 31.6};
            } else if (var4 == 25 || var4 == 84 || var4 == 76 || var4 == 87) {
               var3 = new double[]{18.3, 19.2, 20.2, 21.4, 22.6, 24.0, 25.6, 25.6, 25.6, 25.6, 27.5, 29.6, 30.2, 30.8};
            } else if (var4 == 12 || var4 == 51 || var4 == 74 || var4 == 79 || var4 == 94) {
               var3 = new double[]{18.5, 19.5, 20.4, 21.5, 22.7, 24.0, 25.5, 25.5, 25.5, 25.5, 27.2, 29.2, 29.8, 30.4};
            } else if (var4 == 21 || var4 == 63 || var4 == 75 || var4 == 82) {
               var3 = new double[]{19.0, 19.9, 20.8, 21.8, 22.8, 24.0, 25.4, 25.4, 25.4, 25.4, 26.9, 28.5, 29.1, 29.7};
            }
            break;
         case 3:
            var2 = ListSprReader__obf_c.a().a(this.f.fe(), 18);
            var3 = new double[]{19.4, 20.4, 21.4, 22.6, 24.0, 24.0, 24.0, 24.0, 24.0, 25.5, 25.5, 25.5, 25.5, 25.5};
            break;
         case 4:
            var2 = ListSprReader__obf_c.a().a(this.f.fe(), 19);
            var3 = new double[]{19.8, 20.7, 21.7, 22.8, 24.0, 24.0, 24.0, 24.0, 24.0, 25.3, 25.3, 25.3, 25.3, 25.3};
            break;
         default:
            return 0;
      }

      int[] var7 = e;
      int var6 = e.length;

      for (int var5 = 0; var5 < var6; var5++) {
         int var8 = var7[var5];
         if (this.f.fe() == var8) {
            var2 = (int)(var2 * (24.0 / var3[this.f.aq()]));
            break;
         }
      }

      switch (this.f.fc()) {
         case 1:
            var2 = (int)(var2 * 0.75);
            break;
         case 2:
            var2 = (int)(var2 / 0.75);
      }

      switch (this.f.fd()) {
         case 1:
            var2 = (int)(var2 * 0.75);
         case 2:
         default:
            break;
         case 3:
            if (var1.equals(L1SpeedChecker.L1R_a.a)) {
               var2 = (int)(var2 * 0.75);
            } else {
               var2 = (int)(var2 * 0.87);
            }
            break;
         case 4:
            if (var1.equals(L1SpeedChecker.L1R_a.a)) {
               var2 = (int)(var2 * 0.75);
            }
            break;
         case 5:
            var2 = (int)(var2 * 0.375);
      }

      if (this.f.L()) {
         var2 = (int)(var2 * 0.87);
      }

      if (this.f.M() && !var1.equals(L1SpeedChecker.L1R_a.a)) {
         var2 /= 2;
      }

      if (this.f.fp() == 5143) {
         var2 = (int)(var2 * 0.1);
      }

      return var2;
   }

   // $VF: synthetic method
   static int[] a() {
      if (l != null) {
         return l;
      }

      int[] var0 = new int[L1SpeedChecker.L1R_a.values().length];

      try {
         var0[L1SpeedChecker.L1R_a.b.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
      }

      try {
         var0[L1SpeedChecker.L1R_a.a.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         var0[L1SpeedChecker.L1R_a.c.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
      }

      try {
         var0[L1SpeedChecker.L1R_a.d.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
      }

      l = var0;
      return var0;
   }

   public enum L1R_a {
      a,
      b,
      c,
      d;
   }
}
