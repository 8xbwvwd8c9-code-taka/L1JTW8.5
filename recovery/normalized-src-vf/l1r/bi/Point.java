package l1r.bi;

public class Point {
   protected int a = 0;
   protected int b = 0;
   private static final int[] c = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
   private static final int[] d = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};
   private static final int e = 30;

   public Point() {
   }

   public Point(int var1, int var2) {
      this.a = var1;
      this.b = var2;
   }

   public Point(Point var1) {
      this.a = var1.a;
      this.b = var1.b;
   }

   public int f() {
      return this.a;
   }

   public void b(int var1) {
      this.a = var1;
   }

   public int g() {
      return this.b;
   }

   public void c(int var1) {
      this.b = var1;
   }

   public void a(Point var1) {
      this.a = var1.a;
      this.b = var1.b;
   }

   public void a(int var1, int var2) {
      this.a = var1;
      this.b = var2;
   }

   public void d(int var1) {
      this.a = this.a + c[var1];
      this.b = this.b + d[var1];
   }

   public void e(int var1) {
      this.a = this.a - c[var1];
      this.b = this.b - d[var1];
   }

   public double b(Point var1) {
      long var2 = var1.f() - this.f();
      long var4 = var1.g() - this.g();
      return Math.sqrt(var2 * var2 + var4 * var4);
   }

   public int c(Point var1) {
      return Math.max(Math.abs(var1.f() - this.f()), Math.abs(var1.g() - this.g()));
   }

   public int d(Point var1) {
      return Math.abs(var1.f() - this.f()) + Math.abs(var1.g() - this.g());
   }

   public boolean e(Point var1) {
      return this.d(var1) < 30;
   }

   public boolean f(Point var1) {
      return var1.f() == this.f() && var1.g() == this.g();
   }

   @Override
   public int hashCode() {
      return 7 * this.f() + this.g();
   }

   @Override
   public boolean equals(Object var1) {
      if (!(var1 instanceof Point)) {
         return false;
      }

      Point var2 = (Point)var1;
      return this.f() == var2.f() && this.g() == var2.g();
   }

   @Override
   public String toString() {
      return String.format("(%d, %d)", this.a, this.b);
   }
}
