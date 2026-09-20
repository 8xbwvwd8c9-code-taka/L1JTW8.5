package l1r.aq;

import l1r.bi.IntRange;

public class L1Karma {
   private static final int[] a = new int[]{10000, 20000, 100000, 500000, 1500000, 3000000, 5000000, 10000000, 15500000};
   private static IntRange b = new IntRange(-15500000, 15500000);
   private int c = 0;

   public int a() {
      return this.c;
   }

   public void a(int var1) {
      this.c = b.a(var1);
   }

   public void b(int var1) {
      this.a(this.c + var1);
   }

   public int b() {
      boolean var1 = false;
      int var2 = 0;
      int var3 = this.a();
      if (var3 < 0) {
         var1 = true;
         var3 *= -1;
      }

      int[] var7 = a;
      int var6 = a.length;

      for (int var5 = 0; var5 < var6; var5++) {
         int var4 = var7[var5];
         if (var3 < var4 || ++var2 >= 8) {
            break;
         }
      }

      if (var1) {
         var2 *= -1;
      }

      return var2;
   }

   public int c() {
      int var1 = this.a();
      int var2 = this.b();
      if (var2 == 0) {
         return 0;
      }

      if (var1 < 0) {
         var1 *= -1;
         var2 *= -1;
      }

      return 100 * (var1 - a[var2 - 1]) / (a[var2] - a[var2 - 1]);
   }
}
