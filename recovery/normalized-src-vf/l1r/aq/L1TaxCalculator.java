package l1r.aq;

import l1r.ap.L1NpcInstance;

public class L1TaxCalculator {
   private static final int a = 15;
   private final int b;
   private final int c;

   public L1TaxCalculator(L1NpcInstance var1) {
      this.b = L1CastleLocation.b(var1);
      this.c = L1TownLocation.a(var1);
   }

   public int a(int var1) {
      return var1 + this.e(var1);
   }

   private int e(int var1) {
      int var2 = var1 * this.b;
      int var3 = var1 * this.c;
      int var4 = var1 * 15;
      return (var2 + var3 + var4) / 100;
   }

   public int b(int var1) {
      return var1 * this.b / 100;
   }

   public int c(int var1) {
      return var1 * this.c / 100;
   }

   public int d(int var1) {
      return var1 * 15 / 100;
   }
}
