package l1r.bd;

import l1r.bi.Random;

class L1Trap__obf_a {
   private final int a;

   public L1Trap__obf_a(int var1) {
      this.a = var1;
   }

   public int a() {
      return this.a;
   }

   private int b() {
      return Random.a(this.a) + 1;
   }

   public int a(int var1) {
      int var2 = 0;

      for (int var3 = 0; var3 < var1; var3++) {
         var2 += this.b();
      }

      return var2;
   }
}
