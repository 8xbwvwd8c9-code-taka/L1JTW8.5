package l1r.bi;

public class IntRange {
   private final int a;
   private final int b;

   public IntRange(int var1, int var2) {
      this.a = var1;
      this.b = var2;
   }

   public static boolean a(int var0, int var1, int var2) {
      return var1 <= var0 && var0 <= var2;
   }

   public int a(int var1) {
      int var2 = var1;
      var2 = this.a <= var2 ? var2 : this.a;
      return var2 <= this.b ? var2 : this.b;
   }

   public static int b(int var0, int var1, int var2) {
      int var3 = var0;
      var3 = var1 <= var3 ? var3 : var1;
      return var3 <= var2 ? var3 : var2;
   }

   public int a() {
      return Random.a(this.d() + 1) + this.a;
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.b - this.a;
   }

   @Override
   public boolean equals(Object var1) {
      if (!(var1 instanceof IntRange)) {
         return false;
      }

      IntRange var2 = (IntRange)var1;
      return this.a == var2.a && this.b == var2.b;
   }

   @Override
   public String toString() {
      return "low=" + this.a + ", high=" + this.b;
   }

   @Override
   public int hashCode() {
      return super.hashCode();
   }
}
