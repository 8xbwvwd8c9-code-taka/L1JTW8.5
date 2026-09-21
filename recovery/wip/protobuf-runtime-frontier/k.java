package l1rpb;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class k {
   private static String b(k.g var0, k.a var1, String var2) {
      if (var1 != null) {
         return var1.d() + '.' + var2;
      } else {
         return var0.c().length() > 0 ? var0.c() + '.' + var2 : var2;
      }
   }

   public static final class a implements k.h {
      private final int a;
      private l1rpb.j.a b;
      private final String c;
      private final k.g d;
      private final k.a e;
      private final k.a[] f;
      private final k.d[] g;
      private final k.f[] h;
      private final k.f[] i;

      public int a() {
         return this.a;
      }

      public l1rpb.j.a b() {
         return this.b;
      }

      @Override
      public String c() {
         return this.b.o();
      }

      @Override
      public String d() {
         return this.c;
      }

      @Override
      public k.g e() {
         return this.d;
      }

      public k.a f() {
         return this.e;
      }

      public l1rpb.j.u g() {
         return this.b.G();
      }

      public List<k.f> h() {
         return Collections.unmodifiableList(Arrays.asList(this.h));
      }

      public List<k.f> i() {
         return Collections.unmodifiableList(Arrays.asList(this.i));
      }

      public List<k.a> j() {
         return Collections.unmodifiableList(Arrays.asList(this.f));
      }

      public List<k.d> k() {
         return Collections.unmodifiableList(Arrays.asList(this.g));
      }

      public boolean a(int var1) {
         for (l1rpb.j.a.b var3 : this.b.C()) {
            if (var3.o() <= var1 && var1 < var3.q()) {
               return true;
            }
         }

         return false;
      }

      public k.f a(String var1) {
         k.h var2 = this.d.h.a(this.c + '.' + var1);
         return var2 != null && var2 instanceof k.f ? (k.f)var2 : null;
      }

      public k.f b(int var1) {
         return this.d.h.d.get(new k.b.a(this, var1));
      }

      public k.a b(String var1) {
         k.h var2 = this.d.h.a(this.c + '.' + var1);
         return var2 != null && var2 instanceof k.a ? (k.a)var2 : null;
      }

      public k.d c(String var1) {
         k.h var2 = this.d.h.a(this.c + '.' + var1);
         return var2 != null && var2 instanceof k.d ? (k.d)var2 : null;
      }

      private a(l1rpb.j.a var1, k.g var2, k.a var3, int var4) throws k.c {
         this.a = var4;
         this.b = var1;
         this.c = k.b(var2, var3, var1.o());
         this.d = var2;
         this.e = var3;
         this.f = new k.a[var1.y()];

         for (int var5 = 0; var5 < var1.y(); var5++) {
            this.f[var5] = new k.a(var1.e(var5), var2, this, var5);
         }

         this.g = new k.d[var1.B()];

         for (int var6 = 0; var6 < var1.B(); var6++) {
            this.g[var6] = new k.d(var1.g(var6), var2, this, var6);
         }

         this.h = new k.f[var1.s()];

         for (int var7 = 0; var7 < var1.s(); var7++) {
            this.h[var7] = new k.f(var1.a(var7), var2, this, var7, false);
         }

         this.i = new k.f[var1.v()];

         for (int var8 = 0; var8 < var1.v(); var8++) {
            this.i[var8] = new k.f(var1.c(var8), var2, this, var8, true);
         }

         var2.h.c(this);
      }

      private void m() throws k.c {
         for (k.a var4 : this.f) {
            var4.m();
         }

         for (k.f var11 : this.h) {
            var11.z();
         }

         for (k.f var12 : this.i) {
            var12.z();
         }
      }

      private void a(l1rpb.j.a var1) {
         this.b = var1;

         for (int var2 = 0; var2 < this.f.length; var2++) {
            this.f[var2].a(var1.e(var2));
         }

         for (int var3 = 0; var3 < this.g.length; var3++) {
            this.g[var3].a(var1.g(var3));
         }

         for (int var4 = 0; var4 < this.h.length; var4++) {
            this.h[var4].a(var1.a(var4));
         }

         for (int var5 = 0; var5 < this.i.length; var5++) {
            this.i[var5].a(var1.c(var5));
         }
      }

      // $VF: synthetic method
      @Override
      public x l() {
         return this.b();
      }
   }

   private static final class b {
      private final Set<k.g> b;
      private final Map<String, k.h> c = new HashMap<>();
      private final Map<k.b.a, k.f> d = new HashMap<>();
      private final Map<k.b.a, k.e> e = new HashMap<>();
      // $VF: synthetic field
      static final boolean a = !k.class.desiredAssertionStatus();

      b(k.g[] var1) {
         this.b = new HashSet<>();

         for (int var2 = 0; var2 < var1.length; var2++) {
            this.b.add(var1[var2]);
            this.a(var1[var2]);
         }

         for (k.g var3 : this.b) {
            try {
               this.a(var3.c(), var3);
            } catch (k.c var5) {
               assert false;
            }
         }
      }

      private void a(k.g var1) {
         for (k.g var3 : var1.j()) {
            if (this.b.add(var3)) {
               this.a(var3);
            }
         }
      }

      k.h a(String var1) {
         return this.a(var1, k.b.c.c);
      }

      k.h a(String var1, k.b.c var2) {
         k.h var3 = this.c.get(var1);
         if (var3 == null || var2 != k.b.c.c && (var2 != k.b.c.a || !this.a(var3)) && (var2 != k.b.c.b || !this.b(var3))) {
            for (k.g var5 : this.b) {
               var3 = var5.h.c.get(var1);
               if (var3 != null && (var2 == k.b.c.c || var2 == k.b.c.a && this.a(var3) || var2 == k.b.c.b && this.b(var3))) {
                  return var3;
               }
            }

            return null;
         } else {
            return var3;
         }
      }

      boolean a(k.h var1) {
         return var1 instanceof k.a || var1 instanceof k.d;
      }

      boolean b(k.h var1) {
         return var1 instanceof k.a || var1 instanceof k.d || var1 instanceof k.b.b || var1 instanceof k.j;
      }

      k.h a(String var1, k.h var2, k.b.c var3) throws k.c {
         k.h var4;
         if (var1.startsWith(".")) {
            var4 = this.a(var1.substring(1), var3);
         } else {
            int var5 = var1.indexOf(46);
            String var6;
            if (var5 == -1) {
               var6 = var1;
            } else {
               var6 = var1.substring(0, var5);
            }

            StringBuilder var7 = new StringBuilder(var2.d());

            while (true) {
               int var8 = var7.lastIndexOf(".");
               if (var8 == -1) {
                  var4 = this.a(var1, var3);
                  break;
               }

               var7.setLength(var8 + 1);
               var7.append(var6);
               var4 = this.a(var7.toString(), k.b.c.b);
               if (var4 != null) {
                  if (var5 != -1) {
                     var7.setLength(var8 + 1);
                     var7.append(var1);
                     var4 = this.a(var7.toString(), var3);
                  }
                  break;
               }

               var7.setLength(var8);
            }
         }

         if (var4 == null) {
            throw new k.c(var2, '"' + var1 + "\" is not defined.");
         } else {
            return var4;
         }
      }

      void c(k.h var1) throws k.c {
         d(var1);
         String var2 = var1.d();
         int var3 = var2.lastIndexOf(46);
         k.h var4 = this.c.put(var2, var1);
         if (var4 != null) {
            this.c.put(var2, var4);
            if (var1.e() == var4.e()) {
               if (var3 == -1) {
                  throw new k.c(var1, '"' + var2 + "\" is already defined.");
               } else {
                  throw new k.c(var1, '"' + var2.substring(var3 + 1) + "\" is already defined in \"" + var2.substring(0, var3) + "\".");
               }
            } else {
               throw new k.c(var1, '"' + var2 + "\" is already defined in file \"" + var4.e().b() + "\".");
            }
         }
      }

      void a(String var1, k.g var2) throws k.c {
         int var3 = var1.lastIndexOf(46);
         String var4;
         if (var3 == -1) {
            var4 = var1;
         } else {
            this.a(var1.substring(0, var3), var2);
            var4 = var1.substring(var3 + 1);
         }

         k.h var5 = this.c.put(var1, new k.b.b(var4, var1, var2));
         if (var5 != null) {
            this.c.put(var1, var5);
            if (!(var5 instanceof k.b.b)) {
               throw new k.c(var2, '"' + var4 + "\" is already defined (as something other than a " + "package) in file \"" + var5.e().b() + "\".");
            }
         }
      }

      void a(k.f var1) throws k.c {
         k.b.a var2 = new k.b.a(var1.u(), var1.f());
         k.f var3 = this.d.put(var2, var1);
         if (var3 != null) {
            this.d.put(var2, var3);
            throw new k.c(var1, "Field number " + var1.f() + "has already been used in \"" + var1.u().d() + "\" by field \"" + var3.c() + "\".");
         }
      }

      void a(k.e var1) {
         k.b.a var2 = new k.b.a(var1.g(), var1.a());
         k.e var3 = this.e.put(var2, var1);
         if (var3 != null) {
            this.e.put(var2, var3);
         }
      }

      static void d(k.h var0) throws k.c {
         String var1 = var0.c();
         if (var1.length() == 0) {
            throw new k.c(var0, "Missing name.");
         }

         boolean var2 = true;

         for (int var3 = 0; var3 < var1.length(); var3++) {
            char var4 = var1.charAt(var3);
            if (var4 >= 128) {
               var2 = false;
            }

            if (!Character.isLetter(var4) && var4 != '_' && (!Character.isDigit(var4) || var3 <= 0)) {
               var2 = false;
            }
         }

         if (!var2) {
            throw new k.c(var0, '"' + var1 + "\" is not a valid identifier.");
         }
      }

      private static final class a {
         private final k.h a;
         private final int b;

         a(k.h var1, int var2) {
            this.a = var1;
            this.b = var2;
         }

         @Override
         public int hashCode() {
            return this.a.hashCode() * 65535 + this.b;
         }

         @Override
         public boolean equals(Object var1) {
            if (!(var1 instanceof k.b.a)) {
               return false;
            }

            k.b.a var2 = (k.b.a)var1;
            return this.a == var2.a && this.b == var2.b;
         }
      }

      private static final class b implements k.h {
         private final String a;
         private final String b;
         private final k.g c;

         @Override
         public x l() {
            return this.c.a();
         }

         @Override
         public String c() {
            return this.a;
         }

         @Override
         public String d() {
            return this.b;
         }

         @Override
         public k.g e() {
            return this.c;
         }

         b(String var1, String var2, k.g var3) {
            this.c = var3;
            this.b = var2;
            this.a = var1;
         }
      }

      enum c {
         a,
         b,
         c;
      }
   }

   public static class c extends Exception {
      private static final long a = 5750205775490483148L;
      private final String b;
      private final x c;
      private final String d;

      public String a() {
         return this.b;
      }

      public x b() {
         return this.c;
      }

      public String c() {
         return this.d;
      }

      private c(k.h var1, String var2) {
         super(var1.d() + ": " + var2);
         this.b = var1.d();
         this.c = var1.l();
         this.d = var2;
      }

      private c(k.h var1, String var2, Throwable var3) {
         this(var1, var2);
         this.initCause(var3);
      }

      private c(k.g var1, String var2) {
         super(var1.b() + ": " + var2);
         this.b = var1.b();
         this.c = var1.a();
         this.d = var2;
      }
   }

   public static final class d implements k.h, r.b<k.e> {
      private final int a;
      private l1rpb.j.c b;
      private final String c;
      private final k.g d;
      private final k.a e;
      private k.e[] f;

      public int a() {
         return this.a;
      }

      public l1rpb.j.c b() {
         return this.b;
      }

      @Override
      public String c() {
         return this.b.o();
      }

      @Override
      public String d() {
         return this.c;
      }

      @Override
      public k.g e() {
         return this.d;
      }

      public k.a f() {
         return this.e;
      }

      public l1rpb.j.e g() {
         return this.b.u();
      }

      public List<k.e> h() {
         return Collections.unmodifiableList(Arrays.asList(this.f));
      }

      public k.e a(String var1) {
         k.h var2 = this.d.h.a(this.c + '.' + var1);
         return var2 != null && var2 instanceof k.e ? (k.e)var2 : null;
      }

      public k.e a(int var1) {
         return this.d.h.e.get(new k.b.a(this, var1));
      }

      private d(l1rpb.j.c var1, k.g var2, k.a var3, int var4) throws k.c {
         this.a = var4;
         this.b = var1;
         this.c = k.b(var2, var3, var1.o());
         this.d = var2;
         this.e = var3;
         if (var1.s() == 0) {
            throw new k.c(this, "Enums must contain at least one value.");
         }

         this.f = new k.e[var1.s()];

         for (int var5 = 0; var5 < var1.s(); var5++) {
            this.f[var5] = new k.e(var1.a(var5), var2, this, var5);
         }

         var2.h.c(this);
      }

      private void a(l1rpb.j.c var1) {
         this.b = var1;

         for (int var2 = 0; var2 < this.f.length; var2++) {
            this.f[var2].a(var1.a(var2));
         }
      }

      // $VF: synthetic method
      @Override
      public x l() {
         return this.b();
      }

      // $VF: synthetic method
      @Override
      public r.a b(int var1) {
         return this.a(var1);
      }
   }

   public static final class e implements k.h, r.a {
      private final int a;
      private l1rpb.j.g b;
      private final String c;
      private final k.g d;
      private final k.d e;

      public int b() {
         return this.a;
      }

      public l1rpb.j.g f() {
         return this.b;
      }

      @Override
      public String c() {
         return this.b.o();
      }

      @Override
      public int a() {
         return this.b.r();
      }

      @Override
      public String d() {
         return this.c;
      }

      @Override
      public k.g e() {
         return this.d;
      }

      public k.d g() {
         return this.e;
      }

      public l1rpb.j.i h() {
         return this.b.t();
      }

      private e(l1rpb.j.g var1, k.g var2, k.d var3, int var4) throws k.c {
         this.a = var4;
         this.b = var1;
         this.d = var2;
         this.e = var3;
         this.c = var3.d() + '.' + var1.o();
         var2.h.c(this);
         var2.h.a(this);
      }

      private void a(l1rpb.j.g var1) {
         this.b = var1;
      }

      // $VF: synthetic method
      @Override
      public x l() {
         return this.f();
      }
   }

   public static final class f implements k.h, o.a<k.f>, Comparable<k.f> {
      private static final as.a[] a = as.a.values();
      private final int b;
      private l1rpb.j.k c;
      private final String d;
      private final k.g e;
      private final k.a f;
      private k.f.b g;
      private k.a h;
      private k.a i;
      private k.d j;
      private Object k;

      public int a() {
         return this.b;
      }

      public l1rpb.j.k b() {
         return this.c;
      }

      @Override
      public String c() {
         return this.c.o();
      }

      @Override
      public int f() {
         return this.c.r();
      }

      @Override
      public String d() {
         return this.d;
      }

      public k.f.a g() {
         return this.g.b();
      }

      @Override
      public as.b h() {
         return this.j().a();
      }

      @Override
      public k.g e() {
         return this.e;
      }

      public k.f.b i() {
         return this.g;
      }

      @Override
      public as.a j() {
         return a[this.g.ordinal()];
      }

      public boolean k() {
         return this.c.t() == l1rpb.j.k.b.b;
      }

      public boolean m() {
         return this.c.t() == l1rpb.j.k.b.a;
      }

      @Override
      public boolean n() {
         return this.c.t() == l1rpb.j.k.b.c;
      }

      @Override
      public boolean o() {
         return this.s().q();
      }

      public boolean p() {
         return this.n() && this.j().c();
      }

      public boolean q() {
         return this.c.C();
      }

      public Object r() {
         if (this.g() == k.f.a.i) {
            throw new UnsupportedOperationException("FieldDescriptor.getDefaultValue() called on an embedded message field.");
         } else {
            return this.k;
         }
      }

      public l1rpb.j.m s() {
         return this.c.G();
      }

      public boolean t() {
         return this.c.z();
      }

      public k.a u() {
         return this.h;
      }

      public k.a v() {
         if (!this.t()) {
            throw new UnsupportedOperationException("This field is not an extension.");
         } else {
            return this.f;
         }
      }

      public k.a w() {
         if (this.g() != k.f.a.i) {
            throw new UnsupportedOperationException("This field is not of message type.");
         } else {
            return this.i;
         }
      }

      public k.d x() {
         if (this.g() != k.f.a.h) {
            throw new UnsupportedOperationException("This field is not of enum type.");
         } else {
            return this.j;
         }
      }

      public int a(k.f var1) {
         if (var1.h != this.h) {
            throw new IllegalArgumentException("FieldDescriptors can only be compared to other FieldDescriptors for fields of the same message type.");
         } else {
            return this.f() - var1.f();
         }
      }

      private f(l1rpb.j.k var1, k.g var2, k.a var3, int var4, boolean var5) throws k.c {
         this.b = var4;
         this.c = var1;
         this.d = k.b(var2, var3, var1.o());
         this.e = var2;
         if (var1.u()) {
            this.g = k.f.b.a(var1.v());
         }

         if (this.f() <= 0) {
            throw new k.c(this, "Field numbers must be positive integers.");
         }

         if (var1.G().q() && !this.p()) {
            throw new k.c(this, "[packed = true] can only be specified for repeated primitive fields.");
         }

         if (var5) {
            if (!var1.z()) {
               throw new k.c(this, "FieldDescriptorProto.extendee not set for extension field.");
            }

            this.h = null;
            if (var3 != null) {
               this.f = var3;
            } else {
               this.f = null;
            }
         } else {
            if (var1.z()) {
               throw new k.c(this, "FieldDescriptorProto.extendee set for non-extension field.");
            }

            this.h = var3;
            this.f = null;
         }

         var2.h.c(this);
      }

      private void z() throws k.c {
         if (this.c.z()) {
            k.h var1 = this.e.h.a(this.c.A(), this, k.b.c.a);
            if (!(var1 instanceof k.a)) {
               throw new k.c(this, '"' + this.c.A() + "\" is not a message type.");
            }

            this.h = (k.a)var1;
            if (!this.u().a(this.f())) {
               throw new k.c(this, '"' + this.u().d() + "\" does not declare " + this.f() + " as an extension number.");
            }
         }

         if (this.c.w()) {
            k.h var4 = this.e.h.a(this.c.x(), this, k.b.c.a);
            if (!this.c.u()) {
               if (var4 instanceof k.a) {
                  this.g = k.f.b.k;
               } else {
                  if (!(var4 instanceof k.d)) {
                     throw new k.c(this, '"' + this.c.x() + "\" is not a type.");
                  }

                  this.g = k.f.b.n;
               }
            }

            if (this.g() == k.f.a.i) {
               if (!(var4 instanceof k.a)) {
                  throw new k.c(this, '"' + this.c.x() + "\" is not a message type.");
               }

               this.i = (k.a)var4;
               if (this.c.C()) {
                  throw new k.c(this, "Messages can't have default values.");
               }
            } else {
               if (this.g() != k.f.a.h) {
                  throw new k.c(this, "Field with primitive type has type_name.");
               }

               if (!(var4 instanceof k.d)) {
                  throw new k.c(this, '"' + this.c.x() + "\" is not an enum type.");
               }

               this.j = (k.d)var4;
            }
         } else if (this.g() == k.f.a.i || this.g() == k.f.a.h) {
            throw new k.c(this, "Field with message or enum type missing type_name.");
         }

         if (this.c.C()) {
            if (this.n()) {
               throw new k.c(this, "Repeated fields cannot have default values.");
            }

            try {
               switch (this.i()) {
                  case e:
                  case q:
                  case o:
                     this.k = an.c(this.c.D());
                     break;
                  case m:
                  case g:
                     this.k = an.d(this.c.D());
                     break;
                  case c:
                  case r:
                  case p:
                     this.k = an.e(this.c.D());
                     break;
                  case d:
                  case f:
                     this.k = an.f(this.c.D());
                     break;
                  case b:
                     if (this.c.D().equals("inf")) {
                        this.k = Float.POSITIVE_INFINITY;
                     } else if (this.c.D().equals("-inf")) {
                        this.k = Float.NEGATIVE_INFINITY;
                     } else if (this.c.D().equals("nan")) {
                        this.k = Float.NaN;
                     } else {
                        this.k = Float.valueOf(this.c.D());
                     }
                     break;
                  case a:
                     if (this.c.D().equals("inf")) {
                        this.k = Double.POSITIVE_INFINITY;
                     } else if (this.c.D().equals("-inf")) {
                        this.k = Double.NEGATIVE_INFINITY;
                     } else if (this.c.D().equals("nan")) {
                        this.k = Double.NaN;
                     } else {
                        this.k = Double.valueOf(this.c.D());
                     }
                     break;
                  case h:
                     this.k = Boolean.valueOf(this.c.D());
                     break;
                  case i:
                     this.k = this.c.D();
                     break;
                  case l:
                     try {
                        this.k = an.a((CharSequence)this.c.D());
                        break;
                     } catch (an.a var2) {
                        throw new k.c(this, "Couldn't parse default value: " + var2.getMessage(), var2);
                     }
                  case n:
                     this.k = this.j.a(this.c.D());
                     if (this.k == null) {
                        throw new k.c(this, "Unknown enum default value: \"" + this.c.D() + '"');
                     }
                     break;
                  case k:
                  case j:
                     throw new k.c(this, "Message type had default value.");
               }
            } catch (NumberFormatException var3) {
               throw new k.c(this, "Could not parse default value: \"" + this.c.D() + '"', var3);
            }
         } else if (this.n()) {
            this.k = Collections.emptyList();
         } else {
            switch (this.g()) {
               case h:
                  this.k = this.j.h().get(0);
                  break;
               case i:
                  this.k = null;
                  break;
               default:
                  this.k = this.g().j;
            }
         }

         if (!this.t()) {
            this.e.h.a(this);
         }

         if (this.h != null && this.h.g().o()) {
            if (!this.t()) {
               throw new k.c(this, "MessageSets cannot have fields, only extensions.");
            }

            if (!this.m() || this.i() != k.f.b.k) {
               throw new k.c(this, "Extensions of MessageSets must be optional messages.");
            }
         }
      }

      private void a(l1rpb.j.k var1) {
         this.c = var1;
      }

      @Override
      public y.a a(y.a var1, y var2) {
         return ((x.a)var1).c((x)var2);
      }

      // $VF: synthetic method
      @Override
      public x l() {
         return this.b();
      }

      // $VF: synthetic method
      @Override
      public int compareTo(Object var1) {
         return this.a((k.f)var1);
      }

      // $VF: synthetic method
      @Override
      public r.b y() {
         return this.x();
      }

      static {
         if (k.f.b.values().length != l1rpb.j.k.c.values().length) {
            throw new RuntimeException("descriptor.proto has a new declared type but Desrciptors.java wasn't updated.");
         }
      }

      public enum a {
         a(0),
         b(0L),
         c(0.0F),
         d(0.0),
         e(false),
         f(""),
         g(l1rpb.g.d),
         h(null),
         i(null);

         private final Object j;

         a(Object var3) {
            this.j = var3;
         }
      }

      public enum b {
         a(k.f.a.d),
         b(k.f.a.c),
         c(k.f.a.b),
         d(k.f.a.b),
         e(k.f.a.a),
         f(k.f.a.b),
         g(k.f.a.a),
         h(k.f.a.e),
         i(k.f.a.f),
         j(k.f.a.i),
         k(k.f.a.i),
         l(k.f.a.g),
         m(k.f.a.a),
         n(k.f.a.h),
         o(k.f.a.a),
         p(k.f.a.b),
         q(k.f.a.a),
         r(k.f.a.b);

         private k.f.a s;

         b(k.f.a var3) {
            this.s = var3;
         }

         public l1rpb.j.k.c a() {
            return l1rpb.j.k.c.a(this.ordinal() + 1);
         }

         public k.f.a b() {
            return this.s;
         }

         public static k.f.b a(l1rpb.j.k.c var0) {
            return values()[var0.a() - 1];
         }
      }
   }

   public static final class g {
      private l1rpb.j.o a;
      private final k.a[] b;
      private final k.d[] c;
      private final k.j[] d;
      private final k.f[] e;
      private final k.g[] f;
      private final k.g[] g;
      private final k.b h;

      public l1rpb.j.o a() {
         return this.a;
      }

      public String b() {
         return this.a.o();
      }

      public String c() {
         return this.a.r();
      }

      public l1rpb.j.s d() {
         return this.a.U();
      }

      public List<k.a> e() {
         return Collections.unmodifiableList(Arrays.asList(this.b));
      }

      public List<k.d> f() {
         return Collections.unmodifiableList(Arrays.asList(this.c));
      }

      public List<k.j> g() {
         return Collections.unmodifiableList(Arrays.asList(this.d));
      }

      public List<k.f> h() {
         return Collections.unmodifiableList(Arrays.asList(this.e));
      }

      public List<k.g> i() {
         return Collections.unmodifiableList(Arrays.asList(this.f));
      }

      public List<k.g> j() {
         return Collections.unmodifiableList(Arrays.asList(this.g));
      }

      public k.a a(String var1) {
         if (var1.indexOf(46) != -1) {
            return null;
         }

         if (this.c().length() > 0) {
            var1 = this.c() + '.' + var1;
         }

         k.h var2 = this.h.a(var1);
         return var2 != null && var2 instanceof k.a && var2.e() == this ? (k.a)var2 : null;
      }

      public k.d b(String var1) {
         if (var1.indexOf(46) != -1) {
            return null;
         }

         if (this.c().length() > 0) {
            var1 = this.c() + '.' + var1;
         }

         k.h var2 = this.h.a(var1);
         return var2 != null && var2 instanceof k.d && var2.e() == this ? (k.d)var2 : null;
      }

      public k.j c(String var1) {
         if (var1.indexOf(46) != -1) {
            return null;
         }

         if (this.c().length() > 0) {
            var1 = this.c() + '.' + var1;
         }

         k.h var2 = this.h.a(var1);
         return var2 != null && var2 instanceof k.j && var2.e() == this ? (k.j)var2 : null;
      }

      public k.f d(String var1) {
         if (var1.indexOf(46) != -1) {
            return null;
         }

         if (this.c().length() > 0) {
            var1 = this.c() + '.' + var1;
         }

         k.h var2 = this.h.a(var1);
         return var2 != null && var2 instanceof k.f && var2.e() == this ? (k.f)var2 : null;
      }

      public static k.g a(l1rpb.j.o var0, k.g[] var1) throws k.c {
         k.b var2 = new k.b(var1);
         k.g var3 = new k.g(var0, var1, var2);
         if (var1.length != var0.u()) {
            throw new k.c(var3, "Dependencies passed to FileDescriptor.buildFrom() don't match those listed in the FileDescriptorProto.");
         }

         for (int var4 = 0; var4 < var0.u(); var4++) {
            if (!var1[var4].b().equals(var0.a(var4))) {
               throw new k.c(var3, "Dependencies passed to FileDescriptor.buildFrom() don't match those listed in the FileDescriptorProto.");
            }
         }

         var3.k();
         return var3;
      }

      public static void a(String[] var0, k.g[] var1, k.g.a var2) {
         StringBuilder var3 = new StringBuilder();

         for (String var7 : var0) {
            var3.append(var7);
         }

         byte[] var13;
         try {
            var13 = var3.toString().getBytes("ISO-8859-1");
         } catch (UnsupportedEncodingException var12) {
            throw new RuntimeException("Standard encoding ISO-8859-1 not supported by JVM.", var12);
         }

         l1rpb.j.o var14;
         try {
            var14 = l1rpb.j.o.a(var13);
         } catch (s var11) {
            throw new IllegalArgumentException("Failed to parse protocol buffer descriptor for generated code.", var11);
         }

         k.g var16;
         try {
            var16 = a(var14, var1);
         } catch (k.c var10) {
            throw new IllegalArgumentException("Invalid embedded descriptor for \"" + var14.o() + "\".", var10);
         }

         m var17 = var2.a(var16);
         if (var17 != null) {
            try {
               var14 = l1rpb.j.o.a(var13, var17);
            } catch (s var9) {
               throw new IllegalArgumentException("Failed to parse protocol buffer descriptor for generated code.", var9);
            }

            var16.a(var14);
         }
      }

      private g(l1rpb.j.o var1, k.g[] var2, k.b var3) throws k.c {
         this.h = var3;
         this.a = var1;
         this.f = (k.g[])var2.clone();
         this.g = new k.g[var1.w()];

         for (int var4 = 0; var4 < var1.w(); var4++) {
            int var5 = var1.c(var4);
            if (var5 < 0 || var5 >= this.f.length) {
               throw new k.c(this, "Invalid public dependency index.");
            }

            this.g[var4] = this.f[var1.c(var4)];
         }

         var3.a(this.c(), this);
         this.b = new k.a[var1.B()];

         for (int var6 = 0; var6 < var1.B(); var6++) {
            this.b[var6] = new k.a(var1.e(var6), this, null, var6);
         }

         this.c = new k.d[var1.E()];

         for (int var7 = 0; var7 < var1.E(); var7++) {
            this.c[var7] = new k.d(var1.g(var7), this, null, var7);
         }

         this.d = new k.j[var1.H()];

         for (int var8 = 0; var8 < var1.H(); var8++) {
            this.d[var8] = new k.j(var1.i(var8), this, var8);
         }

         this.e = new k.f[var1.S()];

         for (int var9 = 0; var9 < var1.S(); var9++) {
            this.e[var9] = new k.f(var1.k(var9), this, null, var9, true);
         }
      }

      private void k() throws k.c {
         for (k.a var4 : this.b) {
            var4.m();
         }

         for (k.j var11 : this.d) {
            var11.h();
         }

         for (k.f var12 : this.e) {
            var12.z();
         }
      }

      private void a(l1rpb.j.o var1) {
         this.a = var1;

         for (int var2 = 0; var2 < this.b.length; var2++) {
            this.b[var2].a(var1.e(var2));
         }

         for (int var3 = 0; var3 < this.c.length; var3++) {
            this.c[var3].a(var1.g(var3));
         }

         for (int var4 = 0; var4 < this.d.length; var4++) {
            this.d[var4].a(var1.i(var4));
         }

         for (int var5 = 0; var5 < this.e.length; var5++) {
            this.e[var5].a(var1.k(var5));
         }
      }

      public interface a {
         m a(k.g var1);
      }
   }

   private interface h {
      x l();

      String c();

      String d();

      k.g e();
   }

   public static final class i implements k.h {
      private final int a;
      private l1rpb.j.w b;
      private final String c;
      private final k.g d;
      private final k.j e;
      private k.a f;
      private k.a g;

      public int a() {
         return this.a;
      }

      public l1rpb.j.w b() {
         return this.b;
      }

      @Override
      public String c() {
         return this.b.o();
      }

      @Override
      public String d() {
         return this.c;
      }

      @Override
      public k.g e() {
         return this.d;
      }

      public k.j f() {
         return this.e;
      }

      public k.a g() {
         return this.f;
      }

      public k.a h() {
         return this.g;
      }

      public l1rpb.j.y i() {
         return this.b.x();
      }

      private i(l1rpb.j.w var1, k.g var2, k.j var3, int var4) throws k.c {
         this.a = var4;
         this.b = var1;
         this.d = var2;
         this.e = var3;
         this.c = var3.d() + '.' + var1.o();
         var2.h.c(this);
      }

      private void j() throws k.c {
         k.h var1 = this.d.h.a(this.b.r(), this, k.b.c.a);
         if (!(var1 instanceof k.a)) {
            throw new k.c(this, '"' + this.b.r() + "\" is not a message type.");
         }

         this.f = (k.a)var1;
         k.h var2 = this.d.h.a(this.b.u(), this, k.b.c.a);
         if (!(var2 instanceof k.a)) {
            throw new k.c(this, '"' + this.b.u() + "\" is not a message type.");
         }

         this.g = (k.a)var2;
      }

      private void a(l1rpb.j.w var1) {
         this.b = var1;
      }

      // $VF: synthetic method
      @Override
      public x l() {
         return this.b();
      }
   }

   public static final class j implements k.h {
      private final int a;
      private l1rpb.j.aa b;
      private final String c;
      private final k.g d;
      private k.i[] e;

      public int a() {
         return this.a;
      }

      public l1rpb.j.aa b() {
         return this.b;
      }

      @Override
      public String c() {
         return this.b.o();
      }

      @Override
      public String d() {
         return this.c;
      }

      @Override
      public k.g e() {
         return this.d;
      }

      public l1rpb.j.ac f() {
         return this.b.u();
      }

      public List<k.i> g() {
         return Collections.unmodifiableList(Arrays.asList(this.e));
      }

      public k.i a(String var1) {
         k.h var2 = this.d.h.a(this.c + '.' + var1);
         return var2 != null && var2 instanceof k.i ? (k.i)var2 : null;
      }

      private j(l1rpb.j.aa var1, k.g var2, int var3) throws k.c {
         this.a = var3;
         this.b = var1;
         this.c = k.b(var2, null, var1.o());
         this.d = var2;
         this.e = new k.i[var1.s()];

         for (int var4 = 0; var4 < var1.s(); var4++) {
            this.e[var4] = new k.i(var1.a(var4), var2, this, var4);
         }

         var2.h.c(this);
      }

      private void h() throws k.c {
         for (k.i var4 : this.e) {
            var4.j();
         }
      }

      private void a(l1rpb.j.aa var1) {
         this.b = var1;

         for (int var2 = 0; var2 < this.e.length; var2++) {
            this.e[var2].a(var1.a(var2));
         }
      }

      // $VF: synthetic method
      @Override
      public x l() {
         return this.b();
      }
   }
}
