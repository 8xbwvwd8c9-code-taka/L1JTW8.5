package l1r.aq;

import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.bi.Point;
import l1r.bi.Random;

public class L1Location extends Point {
   private L1Map c = new L1Map();

   public L1Location() {
   }

   private L1Location(L1Location var1) {
      this(var1.a, var1.b, var1.c);
   }

   public L1Location(int var1, int var2, int var3) {
      super(var1, var2);
      this.a(var3);
   }

   private L1Location(int var1, int var2, L1Map var3) {
      super(var1, var2);
      this.c = var3;
   }

   public L1Location(Point var1, int var2) {
      super(var1);
      this.a(var2);
   }

   public L1Location(Point var1, L1Map var2) {
      super(var1);
      this.c = var2;
   }

   public void a(L1Location var1) {
      this.c = var1.c;
      this.a = var1.a;
      this.b = var1.b;
   }

   public void a(int var1, int var2, int var3) {
      this.a(var1, var2);
      this.a(var3);
   }

   public L1Map a() {
      return this.c;
   }

   public int b() {
      return this.c.b();
   }

   public void a(L1Map var1) {
      this.c = var1;
   }

   public void a(int var1) {
      this.c = L1WorldMap.b().a(var1);
   }

   public boolean c() {
      return this.c.d(this.a, this.b);
   }

   public boolean d() {
      return this.c.e(this.a, this.b);
   }

   public boolean e() {
      return this.c.f(this.a, this.b);
   }

   @Override
   public boolean equals(Object var1) {
      if (!(var1 instanceof L1Location)) {
         return false;
      }

      L1Location var2 = (L1Location)var1;
      return this.a() == var2.a() && this.f() == var2.f() && this.g() == var2.g();
   }

   @Override
   public int hashCode() {
      return 7 * this.c.b() + super.hashCode();
   }

   @Override
   public String toString() {
      return String.format("(%d, %d) on %d", this.a, this.b, this.c.b());
   }

   public L1Location a(int var1, boolean var2) {
      return this.a(0, var1, var2);
   }

   private L1Location a(int var1, int var2, boolean var3) {
      return a(this, var1, var2, var3);
   }

   public static L1Location a(L1Location var0, int var1, int var2, boolean var3) {
      if (var1 > var2) {
         throw new IllegalArgumentException("min > maxとなる引数は無効");
      }

      if (var2 <= 0) {
         return new L1Location(var0);
      }

      if (var1 < 0) {
         var1 = 0;
      }

      L1Location var4 = new L1Location();
      int var5 = 0;
      int var6 = 0;
      int var7 = var0.f();
      int var8 = var0.g();
      short var9 = (short)var0.b();
      L1Map var10 = var0.a();
      var4.a(var10);
      int var11 = var7 - var2;
      int var12 = var7 + var2;
      int var13 = var8 - var2;
      int var14 = var8 + var2;
      int var15 = var10.c();
      int var16 = var15 + var10.e();
      int var17 = var10.d();
      int var18 = var17 + var10.f();
      if (var11 < var15) {
         var11 = var15;
      }

      if (var12 > var16) {
         var12 = var16;
      }

      if (var13 < var17) {
         var13 = var17;
      }

      if (var14 > var18) {
         var14 = var18;
      }

      int var19 = var12 - var11;
      int var20 = var14 - var13;
      int var21 = 0;
      int var22 = (int)Math.pow(1 + var2 * 2, 2.0);
      int var23 = var1 == 0 ? 0 : (int)Math.pow(1 + (var1 - 1) * 2, 2.0);
      int var24 = 40 * var22 / (var22 - var23);

      do {
         if (var21 >= var24) {
            var4.a(var7, var8);
            break;
         }

         var21++;
         var5 = var11 + Random.a(var19 + 1);
         var6 = var13 + Random.a(var20 + 1);
         var4.a(var5, var6);
      } while (
         var0.c(var4) < var1
            || var3 && (L1CastleLocation.b(var5, var6, var9) || L1HouseLocation.a(var5, var6, var9))
            || !var10.b(var5, var6)
            || !var10.c(var5, var6)
      );

      return var4;
   }
}
