package l1r.ax;

import l1r.ao.DoorTable;
import l1r.ap.L1DoorInstance;
import l1r.bi.Point;

public class L1Map implements Cloneable {
   public int a = 0;
   public int b = 0;
   public int c = 0;
   public int d = 0;
   public int e = 0;
   private short[][] u = new short[0][0];
   public double f = 1.0;
   public double g = 1.0;
   public double h = 1.0;
   public boolean i = false;
   public boolean j = false;
   public boolean k = false;
   public boolean l = false;
   public boolean m = false;
   public boolean n = false;
   public boolean o = false;
   public boolean p = false;
   public boolean q = false;
   public boolean r = false;
   public boolean s = false;
   public boolean t = false;
   private static final int v = 2048;
   private static final int w = 4096;
   private static final int x = 8192;
   private final int[][] y = new int[][]{{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};

   public L1Map() {
   }

   public L1Map(int var1, short[][] var2, int var3, int var4) {
      this.a = var1;
      this.u = var2;
      this.b = var3;
      this.c = var4;
      this.d = var3 + var2.length - 1;
      this.e = var4 + var2[0].length - 1;
   }

   public short[][] a() {
      return this.u;
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public int e() {
      return this.d - this.b + 1;
   }

   public int f() {
      return this.e - this.c + 1;
   }

   public void a(int var1, int var2, int var3) {
      if (this.b(var1, var2)) {
         this.u[var1 - this.b][var2 - this.c] = (short)var3;
      }
   }

   private int h(int var1, int var2) {
      return !this.b(var1, var2) ? 0 : this.u[var1 - this.b][var2 - this.c];
   }

   private int i(int var1, int var2) {
      return this.h(var1, var2) & -2049;
   }

   public int a(int var1, int var2) {
      return this.i(var1, var2);
   }

   public boolean a(Point var1) {
      return this.b(var1.f(), var1.g());
   }

   public boolean b(int var1, int var2) {
      return this.a != 4 || var1 >= 32520 && var2 >= 32070 && (var2 >= 32190 || var1 >= 33950)
         ? this.b <= var1 && var1 <= this.d && this.c <= var2 && var2 <= this.e
         : false;
   }

   public boolean c(int var1, int var2) {
      int var3 = this.h(var1, var2);
      return (var3 & 0xFF) > 0;
   }

   public boolean b(int var1, int var2, int var3) {
      int var4 = this.h(var1, var2);
      int var5 = var1 + this.y[var3][0];
      int var6 = var2 + this.y[var3][1];
      int var7 = this.h(var5, var6);
      if ((var4 & 4096) == 4096) {
         return var3 != 0 && var3 != 1 && var3 != 7;
      }

      if ((var7 & 4096) == 4096) {
         return var3 != 5 && var3 != 4 && var3 != 3;
      }

      if ((var4 & 8192) == 8192) {
         return var3 != 1 && var3 != 2 && var3 != 3;
      }

      if ((var7 & 8192) == 8192) {
         return var3 != 5 && var3 != 6 && var3 != 7;
      }

      int[] var8 = new int[]{1, 16, 4, 32, 2, 64, 8, 128};
      return (var4 & var8[var3]) == var8[var3];
   }

   public boolean c(int var1, int var2, int var3) {
      int var4 = this.h(var1, var2);
      int var5 = var1 + this.y[var3][0];
      int var6 = var2 + this.y[var3][1];
      return (this.h(var5, var6) & 2048) == 2048 ? false : this.b(var1, var2, var3);
   }

   public boolean d(int var1, int var2, int var3) {
      int[][] var4 = new int[][]{{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
      int var5 = var1 + var4[var3][0];
      int var6 = var2 + var4[var3][1];
      return this.j(var5, var6) ? false : this.c(var1, var2);
   }

   public void a(Point var1, boolean var2) {
      this.a(var1.f(), var1.g(), var2);
   }

   public void a(int var1, int var2, boolean var3) {
      if (this.b(var1, var2)) {
         if (var3) {
            this.u[var1 - this.b][var2 - this.c] = (short)(this.u[var1 - this.b][var2 - this.c] & -2049);
         } else {
            this.u[var1 - this.b][var2 - this.c] = (short)(this.u[var1 - this.b][var2 - this.c] | 2048);
         }
      }
   }

   public void a(int var1, int var2, boolean var3, int var4) {
      if (this.b(var1, var2)) {
         int var5 = var4 == 0 ? 4096 : 8192;
         if (var3) {
            this.u[var1 - this.b][var2 - this.c] = (short)(this.u[var1 - this.b][var2 - this.c] & ~var5);
         } else {
            this.u[var1 - this.b][var2 - this.c] = (short)(this.u[var1 - this.b][var2 - this.c] | var5);
         }
      }
   }

   public boolean b(Point var1) {
      return this.d(var1.f(), var1.g());
   }

   public boolean d(int var1, int var2) {
      int var3 = this.i(var1, var2);
      return (var3 & 512) == 512;
   }

   public boolean c(Point var1) {
      return this.e(var1.f(), var1.g());
   }

   public boolean e(int var1, int var2) {
      int var3 = this.i(var1, var2);
      return (var3 & 1024) == 1024;
   }

   public boolean d(Point var1) {
      return this.f(var1.f(), var1.g());
   }

   public boolean f(int var1, int var2) {
      int var3 = this.i(var1, var2);
      return (var3 & 256) == 256;
   }

   public boolean g(int var1, int var2) {
      return (this.a == 5300 || this.a == 5490) && this.i(var1, var2) == 512;
   }

   public boolean g() {
      return this.i;
   }

   public boolean h() {
      return this.j;
   }

   public boolean i() {
      return this.k;
   }

   public boolean j() {
      return this.l;
   }

   public boolean k() {
      return this.m;
   }

   public boolean l() {
      return this.n;
   }

   public boolean m() {
      return this.o;
   }

   public boolean n() {
      return this.p;
   }

   public boolean o() {
      return this.q;
   }

   public boolean p() {
      return this.r;
   }

   public boolean q() {
      return this.s;
   }

   public boolean r() {
      return this.t;
   }

   private boolean j(int var1, int var2) {
      L1DoorInstance[] var6;
      int var5 = (var6 = DoorTable.b().c()).length;

      for (int var4 = 0; var4 < var5; var4++) {
         L1DoorInstance var3 = var6[var4];
         if (this.a == var3.fp() && var3.o() != 28 && !var3.eX()) {
            int var7 = var3.ac_();
            int var8 = var3.n();
            int var9 = var8 - var7;
            if (var9 == 0) {
               if (var1 == var3.fs() && var2 == var3.ft()) {
                  return true;
               }
            } else if (var3.j() == 0) {
               for (int var11 = var7; var11 <= var8; var11++) {
                  if (var1 == var11 && var2 == var3.ft()) {
                     return true;
                  }
               }
            } else {
               for (int var10 = var7; var10 <= var8; var10++) {
                  if (var1 == var3.fs() && var2 == var10) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public L1Map s() throws CloneNotSupportedException {
      return (L1Map)super.clone();
   }

   public String e(Point var1) {
      return "" + this.a(var1.f(), var1.g());
   }
}
