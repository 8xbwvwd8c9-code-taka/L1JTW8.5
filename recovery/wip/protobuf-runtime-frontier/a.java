package l1rpb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class a extends b implements x {
   private int a = -1;

   @Override
   public boolean a() {
      for (k.f var2 : this.J().h()) {
         if (var2.k() && !this.a_(var2)) {
            return false;
         }
      }

      for (Entry var7 : this.a_().entrySet()) {
         k.f var3 = var7.getKey();
         if (var3.g() == k.f.a.i) {
            if (var3.n()) {
               for (x var5 : (List)var7.getValue()) {
                  if (!var5.a()) {
                     return false;
                  }
               }
            } else if (!((x)var7.getValue()).a()) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public List<String> b() {
      return l1rpb.a.a.b(this);
   }

   @Override
   public String c() {
      return c(this.b());
   }

   private static String c(List<String> var0) {
      StringBuilder var1 = new StringBuilder();

      for (String var3 : var0) {
         if (var1.length() > 0) {
            var1.append(", ");
         }

         var1.append(var3);
      }

      return var1.toString();
   }

   @Override
   public final String toString() {
      return an.b(this);
   }

   @Override
   public void a(i var1) throws IOException {
      boolean var2 = this.J().g().o();

      for (Entry var4 : this.a_().entrySet()) {
         k.f var5 = var4.getKey();
         Object var6 = var4.getValue();
         if (var2 && var5.t() && var5.i() == k.f.b.k && !var5.n()) {
            var1.d(var5.f(), (x)var6);
         } else {
            o.a(var5, var6, var1);
         }
      }

      ap var7 = this.b_();
      if (var2) {
         var7.b(var1);
      } else {
         var7.a(var1);
      }
   }

   @Override
   public int d() {
      int var1 = this.a;
      if (var1 != -1) {
         return var1;
      }

      var1 = 0;
      boolean var2 = this.J().g().o();

      for (Entry var4 : this.a_().entrySet()) {
         k.f var5 = var4.getKey();
         Object var6 = var4.getValue();
         if (var2 && var5.t() && var5.i() == k.f.b.k && !var5.n()) {
            var1 += i.h(var5.f(), (x)var6);
         } else {
            var1 += o.c(var5, var6);
         }
      }

      ap var9 = this.b_();
      if (var2) {
         var1 += var9.i();
      } else {
         var1 += var9.d();
      }

      this.a = var1;
      return var1;
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      }

      if (!(var1 instanceof x)) {
         return false;
      }

      x var2 = (x)var1;
      return this.J() != var2.J() ? false : this.a_().equals(var2.a_()) && this.b_().equals(var2.b_());
   }

   @Override
   public int hashCode() {
      int var1 = 41;
      var1 = 19 * var1 + this.J().hashCode();
      var1 = this.a(var1, this.a_());
      return 29 * var1 + this.b_().hashCode();
   }

   protected int a(int var1, Map<k.f, Object> var2) {
      for (Entry var4 : var2.entrySet()) {
         k.f var5 = var4.getKey();
         Object var6 = var4.getValue();
         var1 = 37 * var1 + var5.f();
         if (var5.i() != k.f.b.n) {
            var1 = 53 * var1 + var6.hashCode();
         } else if (var5.n()) {
            List var7 = (List<? extends r.a>)var6;
            var1 = 53 * var1 + a(var7);
         } else {
            var1 = 53 * var1 + a((r.a)var6);
         }
      }

      return var1;
   }

   protected static int a(long var0) {
      return (int)(var0 ^ var0 >>> 32);
   }

   protected static int a(boolean var0) {
      return var0 ? 1231 : 1237;
   }

   @Override
   ao e() {
      return l1rpb.a.a.b(this);
   }

   protected static int a(r.a var0) {
      return var0.a();
   }

   protected static int a(List<? extends r.a> var0) {
      int var1 = 1;

      for (r.a var3 : var0) {
         var1 = 31 * var1 + a(var3);
      }

      return var1;
   }

   public abstract static class a<BuilderType extends l1rpb.a.a> extends b.a<BuilderType> implements x.a {
      public abstract BuilderType d();

      public BuilderType e() {
         for (Entry var2 : this.a_().entrySet()) {
            this.f(var2.getKey());
         }

         return (BuilderType)this;
      }

      @Override
      public List<String> b() {
         return b(this);
      }

      @Override
      public String c() {
         return l1rpb.a.c(this.b());
      }

      public BuilderType a(x var1) {
         if (var1.J() != this.J()) {
            throw new IllegalArgumentException("mergeFrom(Message) can only merge messages of the same type.");
         }

         for (Entry var3 : var1.a_().entrySet()) {
            k.f var4 = var3.getKey();
            if (var4.n()) {
               for (Object var6 : (List)var3.getValue()) {
                  this.c(var4, var6);
               }
            } else if (var4.g() == k.f.a.i) {
               x var5 = (x)this.b(var4);
               if (var5 == var5.R()) {
                  this.d(var4, var3.getValue());
               } else {
                  this.d(var4, var5.N().c(var5).c((x)var3.getValue()).ak());
               }
            } else {
               this.d(var4, var3.getValue());
            }
         }

         this.a(var1.b_());
         return (BuilderType)this;
      }

      public BuilderType a(h var1) throws IOException {
         return this.a(var1, m.b());
      }

      public BuilderType a(h var1, n var2) throws IOException {
         ap.a var3 = ap.a(this.b_());

         int var4;
         do {
            var4 = var1.a();
         } while (var4 != 0 && a(var1, var3, var2, this.J(), this, null, var4));

         this.e(var3.b());
         return (BuilderType)this;
      }

      private static void a(x.a var0, o<k.f> var1, k.f var2, Object var3) {
         if (var0 != null) {
            var0.c(var2, var3);
         } else {
            var1.b(var2, var3);
         }
      }

      private static void b(x.a var0, o<k.f> var1, k.f var2, Object var3) {
         if (var0 != null) {
            var0.d(var2, var3);
         } else {
            var1.a(var2, var3);
         }
      }

      private static boolean a(x.a var0, o<k.f> var1, k.f var2) {
         return var0 != null ? var0.a_(var2) : var1.a(var2);
      }

      private static x b(x.a var0, o<k.f> var1, k.f var2) {
         return var0 != null ? (x)var0.b(var2) : (x)var1.b(var2);
      }

      private static void a(x.a var0, o<k.f> var1, k.f var2, x.a var3) {
         x var4 = b(var0, var1, var2);
         if (var4 != null) {
            var3.c(var4);
         }
      }

      static boolean a(h var0, ap.a var1, n var2, k.a var3, x.a var4, o<k.f> var5, int var6) throws IOException {
         if (var3.g().o() && var6 == as.l) {
            a(var0, var1, var2, var3, var4, var5);
            return true;
         }

         int var7 = as.a(var6);
         int var8 = as.b(var6);
         x var10 = null;
         k.f var9;
         if (var3.a(var8)) {
            if (var2 instanceof m) {
               m.b var11 = ((m)var2).a(var3, var8);
               if (var11 == null) {
                  var9 = null;
               } else {
                  var9 = var11.a;
                  var10 = var11.b;
                  if (var10 == null && var9.g() == k.f.a.i) {
                     throw new IllegalStateException("Message-typed extension lacked default instance: " + var9.d());
                  }
               }
            } else {
               var9 = null;
            }
         } else if (var4 != null) {
            var9 = var3.b(var8);
         } else {
            var9 = null;
         }

         boolean var17 = false;
         boolean var12 = false;
         if (var9 == null) {
            var17 = true;
         } else if (var7 == o.a(var9.j(), false)) {
            var12 = false;
         } else if (var9.p() && var7 == o.a(var9.j(), true)) {
            var12 = true;
         } else {
            var17 = true;
         }

         if (var17) {
            return var1.a(var6, var0);
         }

         if (var12) {
            int var13 = var0.s();
            int var14 = var0.f(var13);
            if (var9.j() == as.a.n) {
               while (var0.x() > 0) {
                  int var22 = var0.n();
                  Object var16 = var9.x().a(var22);
                  if (var16 == null) {
                     return true;
                  }

                  a(var4, var5, var9, var16);
               }
            } else {
               while (var0.x() > 0) {
                  Object var15 = o.a(var0, var9.j());
                  a(var4, var5, var9, var15);
               }
            }

            var0.g(var14);
         } else {
            Object var18;
            switch (var9.i()) {
               case j:
                  x.a var21;
                  if (var10 != null) {
                     var21 = var10.N();
                  } else {
                     var21 = var4.g(var9);
                  }

                  if (!var9.n()) {
                     a(var4, var5, var9, var21);
                  }

                  var0.a(var9.f(), var21, var2);
                  var18 = var21.aj();
                  break;
               case k:
                  x.a var20;
                  if (var10 != null) {
                     var20 = var10.N();
                  } else {
                     var20 = var4.g(var9);
                  }

                  if (!var9.n()) {
                     a(var4, var5, var9, var20);
                  }

                  var0.a(var20, var2);
                  var18 = var20.aj();
                  break;
               case n:
                  int var19 = var0.n();
                  var18 = var9.x().a(var19);
                  if (var18 == null) {
                     var1.a(var8, var19);
                     return true;
                  }
                  break;
               default:
                  var18 = o.a(var0, var9.j());
            }

            if (var9.n()) {
               a(var4, var5, var9, var18);
            } else {
               b(var4, var5, var9, var18);
            }
         }

         return true;
      }

      private static void a(h var0, ap.a var1, n var2, k.a var3, x.a var4, o<k.f> var5) throws IOException {
         int var6 = 0;
         g var7 = null;
         m.b var8 = null;

         while (true) {
            int var9 = var0.a();
            if (var9 == 0) {
               break;
            }

            if (var9 == as.n) {
               var6 = var0.m();
               if (var6 != 0 && var2 instanceof m) {
                  var8 = ((m)var2).a(var3, var6);
               }
            } else if (var9 == as.o) {
               if (var6 != 0 && var8 != null && n.e()) {
                  a(var0, var8, var2, var4, var5);
                  var7 = null;
               } else {
                  var7 = var0.l();
               }
            } else if (!var0.b(var9)) {
               break;
            }
         }

         var0.a(as.m);
         if (var7 != null && var6 != 0) {
            if (var8 != null) {
               a(var7, var8, var2, var4, var5);
            } else if (var7 != null) {
               var1.a(var6, ap.b.a().a(var7).a());
            }
         }
      }

      private static void a(h var0, m.b var1, n var2, x.a var3, o<k.f> var4) throws IOException {
         k.f var5 = var1.a;
         x var6 = null;
         if (a(var3, var4, var5)) {
            x var7 = b(var3, var4, var5);
            x.a var8 = var7.M();
            var0.a(var8, var2);
            var6 = var8.aj();
         } else {
            var6 = var0.a(var1.b.m(), var2);
         }

         if (var3 != null) {
            var3.d(var5, var6);
         } else {
            var4.a(var5, var6);
         }
      }

      private static void a(g var0, m.b var1, n var2, x.a var3, o<k.f> var4) throws IOException {
         k.f var5 = var1.a;
         boolean var6 = a(var3, var4, var5);
         if (!var6 && !n.e()) {
            t var11 = new t(var1.b, var2, var0);
            if (var3 != null) {
               if (var3 instanceof p.c) {
                  var3.d(var5, var11);
               } else {
                  var3.d(var5, var11.a());
               }
            } else {
               var4.a(var5, var11);
            }
         } else {
            x var7 = null;
            if (var6) {
               x var8 = b(var3, var4, var5);
               x.a var9 = var8.M();
               var9.d(var0, var2);
               var7 = var9.aj();
            } else {
               var7 = var1.b.m().c(var0, var2);
            }

            b(var3, var4, var5, var7);
         }
      }

      public BuilderType a(ap var1) {
         this.e(ap.a(this.b_()).a(var1).b());
         return (BuilderType)this;
      }

      @Override
      public x.a a(k.f var1) {
         throw new UnsupportedOperationException("getFieldBuilder() called on an unsupported message type.");
      }

      protected static ao b(x var0) {
         return new ao(b((aa)var0));
      }

      private static List<String> b(aa var0) {
         List var1 = new ArrayList<>();
         a(var0, "", var1);
         return var1;
      }

      private static void a(aa var0, String var1, List<String> var2) {
         for (k.f var4 : var0.J().h()) {
            if (var4.k() && !var0.a_(var4)) {
               var2.add(var1 + var4.c());
            }
         }

         for (Entry var11 : var0.a_().entrySet()) {
            k.f var5 = var11.getKey();
            Object var6 = var11.getValue();
            if (var5.g() == k.f.a.i) {
               if (var5.n()) {
                  int var7 = 0;

                  for (Object var9 : (List)var6) {
                     a((aa)var9, a(var1, var5, var7++), var2);
                  }
               } else if (var0.a_(var5)) {
                  a((aa)var6, a(var1, var5, -1), var2);
               }
            }
         }
      }

      private static String a(String var0, k.f var1, int var2) {
         StringBuilder var3 = new StringBuilder(var0);
         if (var1.t()) {
            var3.append('(').append(var1.d()).append(')');
         } else {
            var3.append(var1.c());
         }

         if (var2 != -1) {
            var3.append('[').append(var2).append(']');
         }

         var3.append('.');
         return var3.toString();
      }

      public BuilderType a(g var1) throws s {
         return super.b(var1);
      }

      public BuilderType a(g var1, n var2) throws s {
         return super.b(var1, var2);
      }

      public BuilderType a(byte[] var1) throws s {
         return super.b(var1);
      }

      public BuilderType a(byte[] var1, int var2, int var3) throws s {
         return super.b(var1, var2, var3);
      }

      public BuilderType a(byte[] var1, n var2) throws s {
         return super.b(var1, var2);
      }

      public BuilderType a(byte[] var1, int var2, int var3, n var4) throws s {
         return super.b(var1, var2, var3, var4);
      }

      public BuilderType a(InputStream var1) throws IOException {
         return super.c(var1);
      }

      public BuilderType a(InputStream var1, n var2) throws IOException {
         return super.c(var1, var2);
      }

      @Override
      public boolean b(InputStream var1) throws IOException {
         return super.b(var1);
      }

      @Override
      public boolean b(InputStream var1, n var2) throws IOException {
         return super.b(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public b.a c(InputStream var1, n var2) throws IOException {
         return this.a(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public b.a c(InputStream var1) throws IOException {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public b.a b(byte[] var1, int var2, int var3, n var4) throws s {
         return this.a(var1, var2, var3, var4);
      }

      // $VF: synthetic method
      @Override
      public b.a b(byte[] var1, n var2) throws s {
         return this.a(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public b.a b(byte[] var1, int var2, int var3) throws s {
         return this.a(var1, var2, var3);
      }

      // $VF: synthetic method
      @Override
      public b.a b(byte[] var1) throws s {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public b.a b(g var1, n var2) throws s {
         return this.a(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public b.a b(g var1) throws s {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public b.a b(h var1, n var2) throws IOException {
         return this.a(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public b.a b(h var1) throws IOException {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public b.a f() {
         return this.d();
      }

      // $VF: synthetic method
      @Override
      public y.a d(InputStream var1, n var2) throws IOException {
         return this.a(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public y.a d(InputStream var1) throws IOException {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public y.a c(byte[] var1, int var2, int var3, n var4) throws s {
         return this.a(var1, var2, var3, var4);
      }

      // $VF: synthetic method
      @Override
      public y.a c(byte[] var1, n var2) throws s {
         return this.a(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public y.a c(byte[] var1, int var2, int var3) throws s {
         return this.a(var1, var2, var3);
      }

      // $VF: synthetic method
      @Override
      public y.a c(byte[] var1) throws s {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public y.a c(g var1, n var2) throws s {
         return this.a(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public y.a c(g var1) throws s {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public y.a c(h var1, n var2) throws IOException {
         return this.a(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public y.a c(h var1) throws IOException {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public y.a g() {
         return this.d();
      }

      // $VF: synthetic method
      @Override
      public y.a h() {
         return this.e();
      }

      // $VF: synthetic method
      @Override
      public Object clone() throws CloneNotSupportedException {
         return this.d();
      }

      // $VF: synthetic method
      @Override
      public x.a e(InputStream var1, n var2) throws IOException {
         return this.a(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public x.a e(InputStream var1) throws IOException {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public x.a d(byte[] var1, int var2, int var3, n var4) throws s {
         return this.a(var1, var2, var3, var4);
      }

      // $VF: synthetic method
      @Override
      public x.a d(byte[] var1, n var2) throws s {
         return this.a(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public x.a d(byte[] var1, int var2, int var3) throws s {
         return this.a(var1, var2, var3);
      }

      // $VF: synthetic method
      @Override
      public x.a d(byte[] var1) throws s {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public x.a d(g var1, n var2) throws s {
         return this.a(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public x.a d(g var1) throws s {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public x.a b(ap var1) {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public x.a d(h var1, n var2) throws IOException {
         return this.a(var1, var2);
      }

      // $VF: synthetic method
      @Override
      public x.a d(h var1) throws IOException {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public x.a i() {
         return this.d();
      }

      // $VF: synthetic method
      @Override
      public x.a c(x var1) {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public x.a j() {
         return this.e();
      }
   }
}
