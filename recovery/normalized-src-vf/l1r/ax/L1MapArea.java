package l1r.ax;

import l1r.aq.L1Location;

public class L1MapArea {
   private L1Map a;
   private final int b;
   private final int c;
   private final int d;
   private final int e;

   public L1Map a() {
      return this.a;
   }

   public void a(L1Map var1) {
      this.a = var1;
   }

   public int b() {
      return this.a.b();
   }

   public L1MapArea(int var1, int var2, int var3, int var4, int var5) {
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.a = L1WorldMap.b().a((short)var5);
   }

   public boolean a(L1Location var1) {
      return this.a.b() == var1.a().b() && this.a(var1.f(), var1.g());
   }

   private boolean a(int var1, int var2) {
      return this.b <= var1 && var1 <= this.d && this.c <= var2 && var2 <= this.e;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public int e() {
      return this.d;
   }

   public int f() {
      return this.e;
   }

   public int g() {
      return this.d - this.b;
   }

   public int h() {
      return this.e - this.c;
   }
}
