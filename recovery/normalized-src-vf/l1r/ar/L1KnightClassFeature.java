package l1r.ar;

import l1r.l1j.server.Config;

class L1KnightClassFeature extends L1ClassFeature {
   @Override
   public int b(int var1) {
      return var1 / 2;
   }

   @Override
   public int c(int var1) {
      return var1 / 50;
   }

   @Override
   public int[] a() {
      return new int[]{16, 12, 16, 8, 9, 10};
   }

   @Override
   public int b() {
      return 4;
   }

   @Override
   public int c() {
      return 0;
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
      return 21;
   }

   @Override
   public int f() {
      return Config.az;
   }

   @Override
   public int g() {
      return Config.aA;
   }

   @Override
   public int j(int var1) {
      if (var1 <= 9) {
         return 0;
      } else if (var1 >= 10 && var1 <= 14) {
         return 1;
      } else if (var1 >= 15 && var1 <= 17) {
         return 2;
      } else if (var1 >= 18 && var1 <= 23) {
         return 2;
      } else if (var1 >= 24 && var1 <= 24) {
         return 2;
      } else if (var1 >= 25 && var1 <= 26) {
         return 3;
      } else if (var1 >= 27 && var1 <= 29) {
         return 3;
      } else if (var1 >= 30 && var1 <= 32) {
         return 4;
      } else if (var1 >= 33 && var1 <= 35) {
         return 4;
      } else if (var1 >= 36 && var1 <= 39) {
         return 4;
      } else if (var1 >= 40 && var1 <= 41) {
         return 5;
      } else if (var1 >= 42 && var1 <= 44) {
         return 5;
      } else {
         return var1 >= 45 ? 6 : 0;
      }
   }

   @Override
   public int k(int var1) {
      if (var1 <= 9) {
         return 3;
      } else if (var1 >= 10 && var1 <= 14) {
         return 2;
      } else if (var1 >= 15 && var1 <= 17) {
         return 2;
      } else if (var1 >= 18 && var1 <= 23) {
         return 3;
      } else if (var1 >= 24 && var1 <= 24) {
         return 4;
      } else if (var1 >= 25 && var1 <= 26) {
         return 3;
      } else if (var1 >= 27 && var1 <= 29) {
         return 4;
      } else if (var1 >= 30 && var1 <= 32) {
         return 3;
      } else if (var1 >= 33 && var1 <= 35) {
         return 4;
      } else if (var1 >= 36 && var1 <= 39) {
         return 5;
      } else if (var1 >= 40 && var1 <= 41) {
         return 4;
      } else if (var1 >= 42 && var1 <= 44) {
         return 5;
      } else {
         return var1 >= 45 ? 5 : 0;
      }
   }

   @Override
   public String h() {
      return "K";
   }
}
