package l1rpb;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public final class ap implements y {
   private static final ap a = new ap(Collections.emptyMap());
   private Map<Integer, ap.b> b;
   private static final ap.c c = new ap.c();

   private ap() {
   }

   public static ap.a b() {
      return ap.a.k();
   }

   public static ap.a a(ap var0) {
      return b().a(var0);
   }

   public static ap c() {
      return a;
   }

   public ap e() {
      return a;
   }

   private ap(Map<Integer, ap.b> var1) {
      this.b = var1;
   }

   @Override
   public boolean equals(Object var1) {
      return this == var1 ? true : var1 instanceof ap && this.b.equals(((ap)var1).b);
   }

   @Override
   public int hashCode() {
      return this.b.hashCode();
   }

   public Map<Integer, ap.b> h() {
      return this.b;
   }

   public boolean a(int var1) {
      return this.b.containsKey(var1);
   }

   public ap.b b(int var1) {
      ap.b var2 = this.b.get(var1);
      return var2 == null ? ap.b.b() : var2;
   }

   @Override
   public void a(i var1) throws IOException {
      for (Entry var3 : this.b.entrySet()) {
         var3.getValue().a(var3.getKey(), var1);
      }
   }

   @Override
   public String toString() {
      return an.b(this);
   }

   @Override
   public g f() {
      try {
         g.b var1 = g.d(this.d());
         this.a(var1.b());
         return var1.a();
      } catch (IOException var2) {
         throw new RuntimeException("Serializing to a ByteString threw an IOException (should never happen).", var2);
      }
   }

   @Override
   public byte[] g() {
      try {
         byte[] var1 = new byte[this.d()];
         i var2 = i.a(var1);
         this.a(var2);
         var2.c();
         return var1;
      } catch (IOException var3) {
         throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", var3);
      }
   }

   @Override
   public void a(OutputStream var1) throws IOException {
      i var2 = i.a(var1);
      this.a(var2);
      var2.a();
   }

   @Override
   public void b(OutputStream var1) throws IOException {
      i var2 = i.a(var1);
      var2.p(this.d());
      this.a(var2);
      var2.a();
   }

   @Override
   public int d() {
      int var1 = 0;

      for (Entry var3 : this.b.entrySet()) {
         var1 += var3.getValue().a(var3.getKey());
      }

      return var1;
   }

   public void b(i var1) throws IOException {
      for (Entry var3 : this.b.entrySet()) {
         var3.getValue().b(var3.getKey(), var1);
      }
   }

   public int i() {
      int var1 = 0;

      for (Entry var3 : this.b.entrySet()) {
         var1 += var3.getValue().b(var3.getKey());
      }

      return var1;
   }

   @Override
   public boolean a() {
      return true;
   }

   public static ap a(h var0) throws IOException {
      return b().a(var0).b();
   }

   public static ap a(g var0) throws s {
      return b().a(var0).b();
   }

   public static ap a(byte[] var0) throws s {
      return b().a(var0).b();
   }

   public static ap a(InputStream var0) throws IOException {
      return b().a(var0).b();
   }

   public ap.a j() {
      return b();
   }

   public ap.a k() {
      return b().a(this);
   }

   public final ap.c l() {
      return c;
   }

   // $VF: synthetic method
   @Override
   public y.a O() {
      return this.k();
   }

   // $VF: synthetic method
   @Override
   public y.a P() {
      return this.j();
   }

   // $VF: synthetic method
   @Override
   public ab m() {
      return this.l();
   }

   // $VF: synthetic method
   @Override
   public y Q() {
      return this.e();
   }

   public static final class a implements y.a {
      private Map<Integer, ap.b> a;
      private int b;
      private ap.b.a c;

      private a() {
      }

      private static ap.a k() {
         ap.a var0 = new ap.a();
         var0.l();
         return var0;
      }

      private ap.b.a b(int var1) {
         if (this.c != null) {
            if (var1 == this.b) {
               return this.c;
            }

            this.b(this.b, this.c.a());
         }

         if (var1 == 0) {
            return null;
         }

         ap.b var2 = this.a.get(var1);
         this.b = var1;
         this.c = ap.b.a();
         if (var2 != null) {
            this.c.a(var2);
         }

         return this.c;
      }

      public ap b() {
         this.b(0);
         ap var1;
         if (this.a.isEmpty()) {
            var1 = ap.c();
         } else {
            var1 = new ap(Collections.unmodifiableMap(this.a));
         }

         this.a = null;
         return var1;
      }

      public ap c() {
         return this.b();
      }

      public ap.a d() {
         this.b(0);
         return ap.b().a(new ap(this.a));
      }

      public ap e() {
         return ap.c();
      }

      private void l() {
         this.a = Collections.emptyMap();
         this.b = 0;
         this.c = null;
      }

      public ap.a f() {
         this.l();
         return this;
      }

      public ap.a a(ap var1) {
         if (var1 != ap.c()) {
            for (Entry var3 : var1.b.entrySet()) {
               this.a(var3.getKey(), var3.getValue());
            }
         }

         return this;
      }

      public ap.a a(int var1, ap.b var2) {
         if (var1 == 0) {
            throw new IllegalArgumentException("Zero is not a valid field number.");
         }

         if (this.a(var1)) {
            this.b(var1).a(var2);
         } else {
            this.b(var1, var2);
         }

         return this;
      }

      public ap.a a(int var1, int var2) {
         if (var1 == 0) {
            throw new IllegalArgumentException("Zero is not a valid field number.");
         }

         this.b(var1).a((long)var2);
         return this;
      }

      public boolean a(int var1) {
         if (var1 == 0) {
            throw new IllegalArgumentException("Zero is not a valid field number.");
         } else {
            return var1 == this.b || this.a.containsKey(var1);
         }
      }

      public ap.a b(int var1, ap.b var2) {
         if (var1 == 0) {
            throw new IllegalArgumentException("Zero is not a valid field number.");
         }

         if (this.c != null && this.b == var1) {
            this.c = null;
            this.b = 0;
         }

         if (this.a.isEmpty()) {
            this.a = new TreeMap<>();
         }

         this.a.put(var1, var2);
         return this;
      }

      public Map<Integer, ap.b> i() {
         this.b(0);
         return Collections.unmodifiableMap(this.a);
      }

      public ap.a a(h var1) throws IOException {
         int var2;
         do {
            var2 = var1.a();
         } while (var2 != 0 && this.a(var2, var1));

         return this;
      }

      public boolean a(int var1, h var2) throws IOException {
         int var3 = as.b(var1);
         switch (as.a(var1)) {
            case 0:
               this.b(var3).a(var2.f());
               return true;
            case 1:
               this.b(var3).b(var2.h());
               return true;
            case 2:
               this.b(var3).a(var2.l());
               return true;
            case 3:
               ap.a var4 = ap.b();
               var2.a(var3, var4, m.b());
               this.b(var3).a(var4.b());
               return true;
            case 4:
               return false;
            case 5:
               this.b(var3).a(var2.i());
               return true;
            default:
               throw s.g();
         }
      }

      public ap.a a(g var1) throws s {
         try {
            h var2 = var1.k();
            this.a(var2);
            var2.a(0);
            return this;
         } catch (s var3) {
            throw var3;
         } catch (IOException var4) {
            throw new RuntimeException("Reading from a ByteString threw an IOException (should never happen).", var4);
         }
      }

      public ap.a a(byte[] var1) throws s {
         try {
            h var2 = h.a(var1);
            this.a(var2);
            var2.a(0);
            return this;
         } catch (s var3) {
            throw var3;
         } catch (IOException var4) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", var4);
         }
      }

      public ap.a a(InputStream var1) throws IOException {
         h var2 = h.a(var1);
         this.a(var2);
         var2.a(0);
         return this;
      }

      @Override
      public boolean b(InputStream var1) throws IOException {
         int var2 = var1.read();
         if (var2 == -1) {
            return false;
         }

         int var3 = h.a(var2, var1);
         InputStream var4 = new l1rpb.b.a.a(var1, var3);
         this.a(var4);
         return true;
      }

      @Override
      public boolean b(InputStream var1, n var2) throws IOException {
         return this.b(var1);
      }

      public ap.a a(h var1, n var2) throws IOException {
         return this.a(var1);
      }

      public ap.a a(g var1, n var2) throws s {
         return this.a(var1);
      }

      public ap.a a(byte[] var1, int var2, int var3) throws s {
         try {
            h var4 = h.a(var1, var2, var3);
            this.a(var4);
            var4.a(0);
            return this;
         } catch (s var5) {
            throw var5;
         } catch (IOException var6) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", var6);
         }
      }

      public ap.a a(byte[] var1, n var2) throws s {
         return this.a(var1);
      }

      public ap.a a(byte[] var1, int var2, int var3, n var4) throws s {
         return this.a(var1, var2, var3);
      }

      public ap.a a(InputStream var1, n var2) throws IOException {
         return this.a(var1);
      }

      @Override
      public boolean a() {
         return true;
      }

      // $VF: synthetic method
      @Override
      public Object clone() throws CloneNotSupportedException {
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
      public y al() {
         return this.c();
      }

      // $VF: synthetic method
      @Override
      public y am() {
         return this.b();
      }

      // $VF: synthetic method
      @Override
      public y.a h() {
         return this.f();
      }

      // $VF: synthetic method
      @Override
      public y Q() {
         return this.e();
      }
   }

   public static final class b {
      private static final ap.b a = a().a();
      private List<Long> b;
      private List<Integer> c;
      private List<Long> d;
      private List<g> e;
      private List<ap> f;

      private b() {
      }

      public static ap.b.a a() {
         return ap.b.a.d();
      }

      public static ap.b.a a(ap.b var0) {
         return a().a(var0);
      }

      public static ap.b b() {
         return a;
      }

      public List<Long> c() {
         return this.b;
      }

      public List<Integer> d() {
         return this.c;
      }

      public List<Long> e() {
         return this.d;
      }

      public List<g> f() {
         return this.e;
      }

      public List<ap> g() {
         return this.f;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else {
            return !(var1 instanceof ap.b) ? false : Arrays.equals(this.h(), ((ap.b)var1).h());
         }
      }

      @Override
      public int hashCode() {
         return Arrays.hashCode(this.h());
      }

      private Object[] h() {
         return new Object[]{this.b, this.c, this.d, this.e, this.f};
      }

      public void a(int var1, i var2) throws IOException {
         for (long var4 : this.b) {
            var2.a(var1, var4);
         }

         for (int var10 : this.c) {
            var2.b(var1, var10);
         }

         for (long var11 : this.d) {
            var2.c(var1, var11);
         }

         for (g var12 : this.e) {
            var2.a(var1, var12);
         }

         for (ap var13 : this.f) {
            var2.a(var1, var13);
         }
      }

      public int a(int var1) {
         int var2 = 0;

         for (long var4 : this.b) {
            var2 += i.f(var1, var4);
         }

         for (int var10 : this.c) {
            var2 += i.h(var1, var10);
         }

         for (long var11 : this.d) {
            var2 += i.h(var1, var11);
         }

         for (g var12 : this.e) {
            var2 += i.c(var1, var12);
         }

         for (ap var13 : this.f) {
            var2 += i.e(var1, var13);
         }

         return var2;
      }

      public void b(int var1, i var2) throws IOException {
         for (g var4 : this.e) {
            var2.b(var1, var4);
         }
      }

      public int b(int var1) {
         int var2 = 0;

         for (g var4 : this.e) {
            var2 += i.d(var1, var4);
         }

         return var2;
      }

      public static final class a {
         private ap.b a;

         private a() {
         }

         private static ap.b.a d() {
            ap.b.a var0 = new ap.b.a();
            var0.a = new ap.b();
            return var0;
         }

         public ap.b a() {
            if (this.a.b == null) {
               this.a.b = Collections.emptyList();
            } else {
               this.a.b = Collections.unmodifiableList(this.a.b);
            }

            if (this.a.c == null) {
               this.a.c = Collections.emptyList();
            } else {
               this.a.c = Collections.unmodifiableList(this.a.c);
            }

            if (this.a.d == null) {
               this.a.d = Collections.emptyList();
            } else {
               this.a.d = Collections.unmodifiableList(this.a.d);
            }

            if (this.a.e == null) {
               this.a.e = Collections.emptyList();
            } else {
               this.a.e = Collections.unmodifiableList(this.a.e);
            }

            if (this.a.f == null) {
               this.a.f = Collections.emptyList();
            } else {
               this.a.f = Collections.unmodifiableList(this.a.f);
            }

            ap.b var1 = this.a;
            this.a = null;
            return var1;
         }

         public ap.b.a b() {
            this.a = new ap.b();
            return this;
         }

         public ap.b.a a(ap.b var1) {
            if (!var1.b.isEmpty()) {
               if (this.a.b == null) {
                  this.a.b = new ArrayList<>();
               }

               this.a.b.addAll(var1.b);
            }

            if (!var1.c.isEmpty()) {
               if (this.a.c == null) {
                  this.a.c = new ArrayList<>();
               }

               this.a.c.addAll(var1.c);
            }

            if (!var1.d.isEmpty()) {
               if (this.a.d == null) {
                  this.a.d = new ArrayList<>();
               }

               this.a.d.addAll(var1.d);
            }

            if (!var1.e.isEmpty()) {
               if (this.a.e == null) {
                  this.a.e = new ArrayList<>();
               }

               this.a.e.addAll(var1.e);
            }

            if (!var1.f.isEmpty()) {
               if (this.a.f == null) {
                  this.a.f = new ArrayList<>();
               }

               this.a.f.addAll(var1.f);
            }

            return this;
         }

         public ap.b.a a(long var1) {
            if (this.a.b == null) {
               this.a.b = new ArrayList<>();
            }

            this.a.b.add(var1);
            return this;
         }

         public ap.b.a a(int var1) {
            if (this.a.c == null) {
               this.a.c = new ArrayList<>();
            }

            this.a.c.add(var1);
            return this;
         }

         public ap.b.a b(long var1) {
            if (this.a.d == null) {
               this.a.d = new ArrayList<>();
            }

            this.a.d.add(var1);
            return this;
         }

         public ap.b.a a(g var1) {
            if (this.a.e == null) {
               this.a.e = new ArrayList<>();
            }

            this.a.e.add(var1);
            return this;
         }

         public ap.b.a a(ap var1) {
            if (this.a.f == null) {
               this.a.f = new ArrayList<>();
            }

            this.a.f.add(var1);
            return this;
         }
      }
   }

   public static final class c extends l1rpb.c<ap> {
      public ap c(h var1, n var2) throws s {
         ap.a var3 = ap.b();

         try {
            var3.a(var1);
         } catch (s var5) {
            throw var5.a(var3.c());
         } catch (IOException var6) {
            throw new s(var6.getMessage()).a(var3.c());
         }

         return var3.c();
      }

      // $VF: synthetic method
      @Override
      public Object d(h var1, n var2) throws s {
         return this.c(var1, var2);
      }
   }
}
