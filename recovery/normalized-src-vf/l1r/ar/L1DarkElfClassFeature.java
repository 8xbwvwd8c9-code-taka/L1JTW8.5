package l1r.ar;

import l1r.l1j.server.Config;

class L1DarkElfClassFeature extends L1ClassFeature {
   @Override
   public int b(int var1) {
      return var1 / 4;
   }

   @Override
   public int c(int var1) {
      return Math.min(2, var1 / 12);
   }

   @Override
   public int[] a() {
      return new int[]{15, 12, 12, 11, 10, 8};
   }

   @Override
   public int b() {
      return 7;
   }

   @Override
   public int c() {
      return 10;
   }

   @Override
   public int d() {
      return 0;
   }

   @Override
   public int d(int var1) {
      return -(var1 / 6);
   }

   @Override
   public int e(int var1) {
      return var1 / 4;
   }

   @Override
   public int f(int var1) {
      return var1 / 10;
   }

   @Override
   public int g(int var1) {
      return 0;
   }

   @Override
   public int h(int var1) {
      return var1 / 3;
   }

   @Override
   public int i(int var1) {
      return var1 / 3;
   }

   @Override
   public int e() {
      return 11;
   }

   @Override
   public int f() {
      return Config.aF;
   }

   @Override
   public int g() {
      return Config.aG;
   }

   @Override
   public int j(int var1) {
      if (var1 <= 11) {
         return 4;
      } else if (var1 >= 12 && var1 <= 14) {
         return 4;
      } else if (var1 >= 15 && var1 <= 17) {
         return 5;
      } else if (var1 >= 18 && var1 <= 19) {
         return 5;
      } else if (var1 >= 20 && var1 <= 20) {
         return 7;
      } else if (var1 >= 21 && var1 <= 23) {
         return 7;
      } else if (var1 >= 24 && var1 <= 24) {
         return 7;
      } else if (var1 >= 25 && var1 <= 26) {
         return 8;
      } else if (var1 >= 27 && var1 <= 29) {
         return 8;
      } else if (var1 >= 30 && var1 <= 32) {
         return 10;
      } else if (var1 >= 33 && var1 <= 34) {
         return 10;
      } else if (var1 >= 35 && var1 <= 35) {
         return 11;
      } else if (var1 >= 36 && var1 <= 38) {
         return 11;
      } else if (var1 >= 39 && var1 <= 39) {
         return 11;
      } else if (var1 >= 40 && var1 <= 41) {
         return 13;
      } else if (var1 >= 42 && var1 <= 44) {
         return 13;
      } else {
         return var1 >= 45 ? 14 : 0;
      }
   }

   @Override
   public int k(int var1) {
      if (var1 <= 11) {
         return 2;
      } else if (var1 >= 12 && var1 <= 14) {
         return 4;
      } else if (var1 >= 15 && var1 <= 17) {
         return 4;
      } else if (var1 >= 18 && var1 <= 19) {
         return 6;
      } else if (var1 >= 20 && var1 <= 20) {
         return 4;
      } else if (var1 >= 21 && var1 <= 23) {
         return 5;
      } else if (var1 >= 24 && var1 <= 24) {
         return 7;
      } else if (var1 >= 25 && var1 <= 26) {
         return 6;
      } else if (var1 >= 27 && var1 <= 29) {
         return 7;
      } else if (var1 >= 30 && var1 <= 32) {
         return 7;
      } else if (var1 >= 33 && var1 <= 34) {
         return 8;
      } else if (var1 >= 35 && var1 <= 35) {
         return 7;
      } else if (var1 >= 36 && var1 <= 38) {
         return 9;
      } else if (var1 >= 39 && var1 <= 39) {
         return 10;
      } else if (var1 >= 40 && var1 <= 41) {
         return 8;
      } else if (var1 >= 42 && var1 <= 44) {
         return 10;
      } else {
         return var1 >= 45 ? 10 : 0;
      }
   }

   @Override
   public String h() {
      return "D";
   }
}
