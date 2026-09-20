package l1r.aq;

import l1r.ap.L1PcInstance;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;

public class L1Object {
   private final L1Location a = new L1Location();
   private int b = 0;

   public int fp() {
      return this.a.a().b();
   }

   public void cE(int var1) {
      this.a.a(L1WorldMap.b().a(var1));
   }

   public L1Map fq() {
      return this.a.a();
   }

   public void a(L1Map var1) {
      if (var1 == null) {
         throw new NullPointerException();
      }

      this.a.a(var1);
   }

   public int fr() {
      return this.b;
   }

   public void cF(int var1) {
      this.b = var1;
   }

   public int fs() {
      return this.a.f();
   }

   public void cG(int var1) {
      this.a.b(var1);
   }

   public int ft() {
      return this.a.g();
   }

   public void cH(int var1) {
      this.a.c(var1);
   }

   public L1Location fu() {
      return this.a;
   }

   public void a(L1Location var1) {
      this.a.b(var1.f());
      this.a.c(var1.g());
      this.a.a(var1.b());
   }

   public void d(int var1, int var2, int var3) {
      this.a.b(var1);
      this.a.c(var2);
      this.a.a(var3);
   }

   public double e(L1Object var1) {
      return this.fu().b(var1.fu());
   }

   public int f(L1Object var1) {
      return this.fu().c(var1.fu());
   }

   public int g(L1Object var1) {
      return this.fu().d(var1.fu());
   }

   public void b(L1PcInstance var1) {
   }

   public void c(L1PcInstance var1) {
   }

   public void a(L1PcInstance var1) {
   }

   public void a(L1PcInstance var1, int var2) {
   }
}
