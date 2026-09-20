package an;

import a.aa;
import a.ab;
import a.ap;
import a.m;
import a.n;
import a.p;
import a.s;
import a.x;
import a.y;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class b {
   private static a.k.a a;
   private static a.p.g b;
   private static a.k.a c;
   private static a.p.g d;
   private static a.k.a e;
   private static a.p.g f;
   private static a.k.a g;
   private static a.p.g h;
   private static a.k.a i;
   private static a.p.g j;
   private static a.k.g k;

   static {
      String[] var0 = new String[]{
         "\n\u0013PBMessageALL2.proto\u0012 l1j.server.server.datas.protobuf\"`\n\tLuckyDraw\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007array_5\u0018\u0005 \u0001(\f\"1\n\rLuckyDrawRead\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0003(\u0005\"é\u0004\n\ftypeCharPack\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\r\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007array_9\u0018\t \u0001(\f\u0012\u0010\n\barray_10\u0018\n \u0001(\f\u0012\u0010\n\bvalu",
         "e_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\u0012\u0010\n\bvalue_17\u0018\u0011 \u0001(\u0005\u0012\u0010\n\bvalue_18\u0018\u0012 \u0001(\u0005\u0012\u0010\n\bvalue_19\u0018\u0013 \u0001(\u0005\u0012\u0010\n\barray_20\u0018\u0014 \u0001(\f\u0012\u0010\n\barray_21\u0018\u0015 \u0001(\f\u0012\u0010\n\bvalue_22\u0018\u0016 \u0001(\u0005\u0012\u0010\n\bvalue_23\u0018\u0017 \u0001(\u0005\u0012\u0010\n\bvalue_24\u0018\u0018 \u0001(\u0005\u0012\u0010\n\barray_25\u0018\u0019 \u0001(\f\u0012\u0010\n\bvalue_26\u0018\u001a \u0001(\u0005\u0012\u0010\n\bvalue_27\u0018\u001b \u0001(\u0005\u0012\u0010\n\bvalue_28\u0018\u001c \u0001(\u0005\u0012\u0010\n\bvalue_29\u0018\u001d \u0001(\u0005\u0012\u0010\n\bvalue_30\u0018\u001e \u0001(\u0005\u0012\u0010\n\bvalue_31\u0018\u001f \u0001(\u0005\u0012\u0010\n\bvalue_32\u0018  \u0001(\u0005\u0012\u0010\n\bvalue_33",
         "\u0018! \u0001(\u0005\u0012\u0010\n\bvalue_34\u0018\" \u0001(\u0005\"Å\u0004\n\ftypeItemInfo\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007array_4\u0018\u0004 \u0001(\f\u0012\u000f\n\u0007array_5\u0018\u0005 \u0001(\f\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0003(\u0005\u0012\u0010\n\barray_11\u0018\u000b \u0003(\f\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\u0012\u0010\n\bvalue_17\u0018\u0011 \u0001(\u0005\u0012\u0010\n\bvalue_18\u0018\u0012 \u0001(\u0005\u0012\u0010\n\bvalue_19\u0018\u0013 \u0001(\u0005\u0012\u0010\n\bvalue_20\u0018\u0014 \u0001(\u0005\u0012\u0010\n\bvalu",
         "e_21\u0018\u0015 \u0001(\u0005\u0012\u0010\n\bvalue_22\u0018\u0016 \u0001(\u0005\u0012\u0010\n\bvalue_23\u0018\u0017 \u0001(\u0005\u0012\u0010\n\bvalue_24\u0018\u0018 \u0001(\u0005\u0012\u0010\n\bvalue_25\u0018\u0019 \u0001(\u0005\u0012\u0010\n\bvalue_26\u0018\u001a \u0001(\u0005\u0012\u0010\n\bvalue_27\u0018\u001b \u0001(\u0005\u0012\u0010\n\bvalue_28\u0018\u001c \u0001(\u0005\u0012\u0010\n\bvalue_29\u0018\u001d \u0001(\u0005\u0012\u0010\n\bvalue_30\u0018\u001e \u0001(\u0005\u0012\u0010\n\bvalue_31\u0018\u001f \u0001(\u0005\u0012\u0010\n\bvalue_32\u0018  \u0001(\u0005\"þ\u0002\n\u000btypeNpcInfo\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007array_3\u0018\u0003 \u0001(\f\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bv",
         "alue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\u0012\u0010\n\bvalue_17\u0018\u0011 \u0001(\u0005\u0012\u0010\n\barray_18\u0018\u0012 \u0001(\f\u0012\u0010\n\bvalue_19\u0018\u0013 \u0001(\u0005\u0012\u0010\n\bvalue_20\u0018\u0014 \u0001(\u0005\u0012\u0010\n\barray_21\u0018\u0015 \u0003(\fB1\n l1j.server.server.datas.protobufB\rPBMessageALL2"
      };
      a.k.g.a var1 = new a.k.g.a() {
         @Override
         public m a(a.k.g var1) {
            an.b.k = var1;
            an.b.a = an.b.a().e().get(0);
            an.b.b = new a.p.g(an.b.a, new String[]{"Value1", "Value2", "Value3", "Value4", "Array5"});
            an.b.c = an.b.a().e().get(1);
            an.b.d = new a.p.g(an.b.c, new String[]{"Value1", "Value2"});
            an.b.e = an.b.a().e().get(2);
            an.b.f = new a.p.g(
               an.b.e,
               new String[]{
                  "Value1",
                  "Value2",
                  "Value3",
                  "Value4",
                  "Value5",
                  "Value6",
                  "Value7",
                  "Value8",
                  "Array9",
                  "Array10",
                  "Value11",
                  "Value12",
                  "Value13",
                  "Value14",
                  "Value15",
                  "Value16",
                  "Value17",
                  "Value18",
                  "Value19",
                  "Array20",
                  "Array21",
                  "Value22",
                  "Value23",
                  "Value24",
                  "Array25",
                  "Value26",
                  "Value27",
                  "Value28",
                  "Value29",
                  "Value30",
                  "Value31",
                  "Value32",
                  "Value33",
                  "Value34"
               }
            );
            an.b.g = an.b.a().e().get(3);
            an.b.h = new a.p.g(
               an.b.g,
               new String[]{
                  "Value1",
                  "Value2",
                  "Value3",
                  "Array4",
                  "Array5",
                  "Value6",
                  "Value7",
                  "Value8",
                  "Value9",
                  "Value10",
                  "Array11",
                  "Value12",
                  "Value13",
                  "Value14",
                  "Value15",
                  "Value16",
                  "Value17",
                  "Value18",
                  "Value19",
                  "Value20",
                  "Value21",
                  "Value22",
                  "Value23",
                  "Value24",
                  "Value25",
                  "Value26",
                  "Value27",
                  "Value28",
                  "Value29",
                  "Value30",
                  "Value31",
                  "Value32"
               }
            );
            an.b.i = an.b.a().e().get(4);
            an.b.j = new a.p.g(
               an.b.i,
               new String[]{
                  "Value1",
                  "Value2",
                  "Array3",
                  "Value4",
                  "Value5",
                  "Value6",
                  "Value7",
                  "Value8",
                  "Value9",
                  "Value10",
                  "Value11",
                  "Value12",
                  "Value13",
                  "Value14",
                  "Value15",
                  "Value16",
                  "Value17",
                  "Array18",
                  "Value19",
                  "Value20",
                  "Array21"
               }
            );
            return null;
         }
      };
      a.k.g.a(var0, new a.k.g[0], var1);
   }

   private b() {
   }

   public static void a(m var0) {
   }

   public static a.k.g a() {
      return k;
   }

   public static final class a extends p implements an.b.b {
      private static final an.b.a g = new an.b.a(true);
      private final ap h;
      public static ab<an.b.a> a = new a.c<an.b.a>() {
         public an.b.a c(a.h var1, n var2) throws s {
            return new an.b.a(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int i;
      public static final int b = 1;
      private int j;
      public static final int c = 2;
      private int k;
      public static final int d = 3;
      private int l;
      public static final int e = 4;
      private int n;
      public static final int f = 5;
      private a.g o;
      private byte p = -1;
      private int q = -1;
      private static final long r = 0L;

      static {
         g.C();
      }

      private a(a.p.a<?> var1) {
         super(var1);
         this.h = var1.b_();
      }

      private a(boolean var1) {
         this.h = ap.c();
      }

      public static an.b.a h() {
         return g;
      }

      public an.b.a k() {
         return g;
      }

      @Override
      public final ap b_() {
         return this.h;
      }

      private a(a.h var1, n var2) throws s {
         this.C();
         int var3 = 0;
         a.ap.a var4 = ap.b();

         try {
            boolean var5 = false;

            while (!var5) {
               int var6 = var1.a();
               switch (var6) {
                  case 0:
                     var5 = true;
                     break;
                  case 8:
                     this.i |= 1;
                     this.j = var1.g();
                     break;
                  case 16:
                     this.i |= 2;
                     this.k = var1.g();
                     break;
                  case 24:
                     this.i |= 4;
                     this.l = var1.g();
                     break;
                  case 32:
                     this.i |= 8;
                     this.n = var1.g();
                     break;
                  case 42:
                     this.i |= 16;
                     this.o = var1.l();
                     break;
                  default:
                     if (!this.a(var1, var4, var2, var6)) {
                        var5 = true;
                     }
               }
            }
         } catch (s var11) {
            throw var11.a(this);
         } catch (IOException var12) {
            throw new s(var12.getMessage()).a(this);
         } finally {
            this.h = var4.b();
            this.ad();
         }
      }

      public static final a.k.a n() {
         return an.b.a;
      }

      @Override
      protected a.p.g l() {
         return an.b.b.a(an.b.a.class, an.b.a.a.class);
      }

      @Override
      public ab<an.b.a> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.i & 1) == 1;
      }

      @Override
      public int p() {
         return this.j;
      }

      @Override
      public boolean q() {
         return (this.i & 2) == 2;
      }

      @Override
      public int r() {
         return this.k;
      }

      @Override
      public boolean s() {
         return (this.i & 4) == 4;
      }

      @Override
      public int t() {
         return this.l;
      }

      @Override
      public boolean u() {
         return (this.i & 8) == 8;
      }

      @Override
      public int v() {
         return this.n;
      }

      @Override
      public boolean w() {
         return (this.i & 16) == 16;
      }

      @Override
      public a.g x() {
         return this.o;
      }

      private void C() {
         this.j = 0;
         this.k = 0;
         this.l = 0;
         this.n = 0;
         this.o = a.g.d;
      }

      @Override
      public final boolean a() {
         byte var1 = this.p;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.p = 1;
         return true;
      }

      @Override
      public void a(a.i var1) throws IOException {
         this.d();
         if ((this.i & 1) == 1) {
            var1.a(1, this.j);
         }

         if ((this.i & 2) == 2) {
            var1.a(2, this.k);
         }

         if ((this.i & 4) == 4) {
            var1.a(3, this.l);
         }

         if ((this.i & 8) == 8) {
            var1.a(4, this.n);
         }

         if ((this.i & 16) == 16) {
            var1.a(5, this.o);
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.q;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.i & 1) == 1) {
            var1 += a.i.g(1, this.j);
         }

         if ((this.i & 2) == 2) {
            var1 += a.i.g(2, this.k);
         }

         if ((this.i & 4) == 4) {
            var1 += a.i.g(3, this.l);
         }

         if ((this.i & 8) == 8) {
            var1 += a.i.g(4, this.n);
         }

         if ((this.i & 16) == 16) {
            var1 += a.i.c(5, this.o);
         }

         var1 += this.b_().d();
         this.q = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static an.b.a a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.b.a a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.b.a a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.b.a a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.b.a a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.b.a a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.b.a b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.b.a b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.b.a a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.b.a a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.b.a.a y() {
         return an.b.a.a.I();
      }

      public an.b.a.a z() {
         return y();
      }

      public static an.b.a.a a(an.b.a var0) {
         return y().a(var0);
      }

      public an.b.a.a A() {
         return a(this);
      }

      protected an.b.a.a a(a.p.b var1) {
         return new an.b.a.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      protected a.x.a b(a.p.b var1) {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public a.y.a P() {
         return this.z();
      }

      // $VF: synthetic method
      @Override
      public a.x.a N() {
         return this.z();
      }

      // $VF: synthetic method
      @Override
      public y Q() {
         return this.k();
      }

      // $VF: synthetic method
      @Override
      public x R() {
         return this.k();
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.A();
      }

      // $VF: synthetic method
      @Override
      public a.x.a M() {
         return this.A();
      }

      // $VF: synthetic method
      a(a.h var1, n var2, an.b.a var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      a(a.p.a var1, an.b.a var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.b.a.a> implements an.b.b {
         private int a;
         private int b;
         private int c;
         private int d;
         private int e;
         private a.g f = a.g.d;

         public static final a.k.a k() {
            return an.b.a;
         }

         @Override
         protected a.p.g l() {
            return an.b.b.a(an.b.a.class, an.b.a.a.class);
         }

         private a() {
            this.H();
         }

         private a(a.p.b var1) {
            super(var1);
            this.H();
         }

         private void H() {
            an.b.a.m;
         }

         private static an.b.a.a I() {
            return new an.b.a.a();
         }

         public an.b.a.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = 0;
            this.a &= -3;
            this.d = 0;
            this.a &= -5;
            this.e = 0;
            this.a &= -9;
            this.f = a.g.d;
            this.a &= -17;
            return this;
         }

         public an.b.a.a n() {
            return I().a(this.A());
         }

         @Override
         public a.k.a J() {
            return an.b.a;
         }

         public an.b.a y() {
            return an.b.a.h();
         }

         public an.b.a z() {
            an.b.a var1 = this.A();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.b.a A() {
            an.b.a var1 = new an.b.a(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((var2 & 1) == 1) {
               var3 |= 1;
            }

            var1.j = this.b;
            if ((var2 & 2) == 2) {
               var3 |= 2;
            }

            var1.k = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 4;
            }

            var1.l = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 8;
            }

            var1.n = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 16;
            }

            var1.o = this.f;
            var1.i = var3;
            this.q_();
            return var1;
         }

         public an.b.a.a d(x var1) {
            if (var1 instanceof an.b.a) {
               return this.a((an.b.a)var1);
            }

            super.a(var1);
            return this;
         }

         public an.b.a.a a(an.b.a var1) {
            if (var1 == an.b.a.h()) {
               return this;
            }

            if (var1.o()) {
               this.a(var1.p());
            }

            if (var1.q()) {
               this.b(var1.r());
            }

            if (var1.s()) {
               this.c(var1.t());
            }

            if (var1.u()) {
               this.d(var1.v());
            }

            if (var1.w()) {
               this.e(var1.x());
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public an.b.a.a e(a.h var1, n var2) throws IOException {
            an.b.a var3 = null;

            try {
               var3 = an.b.a.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.b.a)var8.a();
               throw var8;
            } finally {
               if (var3 != null) {
                  this.a(var3);
               }
            }

            return this;
         }

         @Override
         public boolean o() {
            return (this.a & 1) == 1;
         }

         @Override
         public int p() {
            return this.b;
         }

         public an.b.a.a a(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public an.b.a.a B() {
            this.a &= -2;
            this.b = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean q() {
            return (this.a & 2) == 2;
         }

         @Override
         public int r() {
            return this.c;
         }

         public an.b.a.a b(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public an.b.a.a C() {
            this.a &= -3;
            this.c = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean s() {
            return (this.a & 4) == 4;
         }

         @Override
         public int t() {
            return this.d;
         }

         public an.b.a.a c(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.b.a.a D() {
            this.a &= -5;
            this.d = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean u() {
            return (this.a & 8) == 8;
         }

         @Override
         public int v() {
            return this.e;
         }

         public an.b.a.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.b.a.a E() {
            this.a &= -9;
            this.e = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean w() {
            return (this.a & 16) == 16;
         }

         @Override
         public a.g x() {
            return this.f;
         }

         public an.b.a.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.b.a.a F() {
            this.a &= -17;
            this.f = an.b.a.h().x();
            this.t_();
            return this;
         }

         // $VF: synthetic method
         @Override
         public y al() {
            return this.A();
         }

         // $VF: synthetic method
         @Override
         public x aj() {
            return this.A();
         }

         // $VF: synthetic method
         @Override
         public a.y.a g() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public a.x.a i() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public a.p.a ai() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public a.y.a c(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.x.a d(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.x.a c(x var1) {
            return this.d(var1);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(x var1) {
            return this.d(var1);
         }

         // $VF: synthetic method
         @Override
         public y Q() {
            return this.y();
         }

         // $VF: synthetic method
         @Override
         public x R() {
            return this.y();
         }

         // $VF: synthetic method
         @Override
         public a.y.a h() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public a.x.a j() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public a.p.a ah() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public y am() {
            return this.z();
         }

         // $VF: synthetic method
         @Override
         public x ak() {
            return this.z();
         }

         // $VF: synthetic method
         a(a.p.b var1, an.b.a.a var2) {
            this(var1);
         }
      }
   }

   public interface b extends aa {
      boolean o();

      int p();

      boolean q();

      int r();

      boolean s();

      int t();

      boolean u();

      int v();

      boolean w();

      a.g x();
   }

   public static final class c extends p implements an.b.d {
      private static final an.b.c d = new an.b.c(true);
      private final ap e;
      public static ab<an.b.c> a = new a.c<an.b.c>() {
         public an.b.c c(a.h var1, n var2) throws s {
            return new an.b.c(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int f;
      public static final int b = 1;
      private int g;
      public static final int c = 2;
      private List<Integer> h;
      private byte i = -1;
      private int j = -1;
      private static final long k = 0L;

      static {
         d.w();
      }

      private c(a.p.a<?> var1) {
         super(var1);
         this.e = var1.b_();
      }

      private c(boolean var1) {
         this.e = ap.c();
      }

      public static an.b.c h() {
         return d;
      }

      public an.b.c k() {
         return d;
      }

      @Override
      public final ap b_() {
         return this.e;
      }

      private c(a.h var1, n var2) throws s {
         this.w();
         int var3 = 0;
         a.ap.a var4 = ap.b();

         try {
            boolean var5 = false;

            while (!var5) {
               int var6 = var1.a();
               int var8;
               switch (var6) {
                  case 0:
                     var5 = true;
                     continue;
                  case 8:
                     this.f |= 1;
                     this.g = var1.g();
                     continue;
                  case 16:
                     if ((var3 & 2) != 2) {
                        this.h = new ArrayList<>();
                        var3 |= 2;
                     }

                     this.h.add(var1.g());
                     continue;
                  case 18:
                     int var7 = var1.s();
                     var8 = var1.f(var7);
                     if ((var3 & 2) != 2 && var1.x() > 0) {
                        this.h = new ArrayList<>();
                        var3 |= 2;
                     }
                     break;
                  default:
                     if (!this.a(var1, var4, var2, var6)) {
                        var5 = true;
                     }
                     continue;
               }

               while (var1.x() > 0) {
                  this.h.add(var1.g());
               }

               var1.g(var8);
            }
         } catch (s var13) {
            throw var13.a(this);
         } catch (IOException var14) {
            throw new s(var14.getMessage()).a(this);
         } finally {
            if ((var3 & 2) == 2) {
               this.h = Collections.unmodifiableList(this.h);
            }

            this.e = var4.b();
            this.ad();
         }
      }

      public static final a.k.a n() {
         return an.b.c;
      }

      @Override
      protected a.p.g l() {
         return an.b.d.a(an.b.c.class, an.b.c.a.class);
      }

      @Override
      public ab<an.b.c> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.f & 1) == 1;
      }

      @Override
      public int p() {
         return this.g;
      }

      @Override
      public List<Integer> q() {
         return this.h;
      }

      @Override
      public int r() {
         return this.h.size();
      }

      @Override
      public int a(int var1) {
         return this.h.get(var1);
      }

      private void w() {
         this.g = 0;
         this.h = Collections.emptyList();
      }

      @Override
      public final boolean a() {
         byte var1 = this.i;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.i = 1;
         return true;
      }

      @Override
      public void a(a.i var1) throws IOException {
         this.d();
         if ((this.f & 1) == 1) {
            var1.a(1, this.g);
         }

         for (int var2 = 0; var2 < this.h.size(); var2++) {
            var1.a(2, this.h.get(var2));
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.j;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.f & 1) == 1) {
            var1 += a.i.g(1, this.g);
         }

         int var2 = 0;

         for (int var3 = 0; var3 < this.h.size(); var3++) {
            var2 += a.i.h(this.h.get(var3));
         }

         var1 += var2;
         var1 += 1 * this.q().size();
         var1 += this.b_().d();
         this.j = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static an.b.c a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.b.c a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.b.c a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.b.c a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.b.c a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.b.c a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.b.c b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.b.c b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.b.c a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.b.c a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.b.c.a s() {
         return an.b.c.a.z();
      }

      public an.b.c.a t() {
         return s();
      }

      public static an.b.c.a a(an.b.c var0) {
         return s().a(var0);
      }

      public an.b.c.a u() {
         return a(this);
      }

      protected an.b.c.a a(a.p.b var1) {
         return new an.b.c.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      protected a.x.a b(a.p.b var1) {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public a.y.a P() {
         return this.t();
      }

      // $VF: synthetic method
      @Override
      public a.x.a N() {
         return this.t();
      }

      // $VF: synthetic method
      @Override
      public y Q() {
         return this.k();
      }

      // $VF: synthetic method
      @Override
      public x R() {
         return this.k();
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.u();
      }

      // $VF: synthetic method
      @Override
      public a.x.a M() {
         return this.u();
      }

      // $VF: synthetic method
      c(a.h var1, n var2, an.b.c var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      c(a.p.a var1, an.b.c var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.b.c.a> implements an.b.d {
         private int a;
         private int b;
         private List<Integer> c = Collections.emptyList();

         public static final a.k.a k() {
            return an.b.c;
         }

         @Override
         protected a.p.g l() {
            return an.b.d.a(an.b.c.class, an.b.c.a.class);
         }

         private a() {
            this.y();
         }

         private a(a.p.b var1) {
            super(var1);
            this.y();
         }

         private void y() {
            an.b.c.m;
         }

         private static an.b.c.a z() {
            return new an.b.c.a();
         }

         public an.b.c.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = Collections.emptyList();
            this.a &= -3;
            return this;
         }

         public an.b.c.a n() {
            return z().a(this.u());
         }

         @Override
         public a.k.a J() {
            return an.b.c;
         }

         public an.b.c s() {
            return an.b.c.h();
         }

         public an.b.c t() {
            an.b.c var1 = this.u();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.b.c u() {
            an.b.c var1 = new an.b.c(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((var2 & 1) == 1) {
               var3 |= 1;
            }

            var1.g = this.b;
            if ((this.a & 2) == 2) {
               this.c = Collections.unmodifiableList(this.c);
               this.a &= -3;
            }

            var1.h = this.c;
            var1.f = var3;
            this.q_();
            return var1;
         }

         public an.b.c.a d(x var1) {
            if (var1 instanceof an.b.c) {
               return this.a((an.b.c)var1);
            }

            super.a(var1);
            return this;
         }

         public an.b.c.a a(an.b.c var1) {
            if (var1 == an.b.c.h()) {
               return this;
            }

            if (var1.o()) {
               this.b(var1.p());
            }

            if (!var1.h.isEmpty()) {
               if (this.c.isEmpty()) {
                  this.c = var1.h;
                  this.a &= -3;
               } else {
                  this.A();
                  this.c.addAll(var1.h);
               }

               this.t_();
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public an.b.c.a e(a.h var1, n var2) throws IOException {
            an.b.c var3 = null;

            try {
               var3 = an.b.c.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.b.c)var8.a();
               throw var8;
            } finally {
               if (var3 != null) {
                  this.a(var3);
               }
            }

            return this;
         }

         @Override
         public boolean o() {
            return (this.a & 1) == 1;
         }

         @Override
         public int p() {
            return this.b;
         }

         public an.b.c.a b(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public an.b.c.a v() {
            this.a &= -2;
            this.b = 0;
            this.t_();
            return this;
         }

         private void A() {
            if ((this.a & 2) != 2) {
               this.c = new ArrayList<>(this.c);
               this.a |= 2;
            }
         }

         @Override
         public List<Integer> q() {
            return Collections.unmodifiableList(this.c);
         }

         @Override
         public int r() {
            return this.c.size();
         }

         @Override
         public int a(int var1) {
            return this.c.get(var1);
         }

         public an.b.c.a a(int var1, int var2) {
            this.A();
            this.c.set(var1, var2);
            this.t_();
            return this;
         }

         public an.b.c.a c(int var1) {
            this.A();
            this.c.add(var1);
            this.t_();
            return this;
         }

         public an.b.c.a a(Iterable<? extends Integer> var1) {
            this.A();
            a.p.a.a(var1, this.c);
            this.t_();
            return this;
         }

         public an.b.c.a w() {
            this.c = Collections.emptyList();
            this.a &= -3;
            this.t_();
            return this;
         }

         // $VF: synthetic method
         @Override
         public y Q() {
            return this.s();
         }

         // $VF: synthetic method
         @Override
         public x R() {
            return this.s();
         }

         // $VF: synthetic method
         @Override
         public y al() {
            return this.u();
         }

         // $VF: synthetic method
         @Override
         public x aj() {
            return this.u();
         }

         // $VF: synthetic method
         @Override
         public y am() {
            return this.t();
         }

         // $VF: synthetic method
         @Override
         public x ak() {
            return this.t();
         }

         // $VF: synthetic method
         @Override
         public a.y.a c(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.x.a d(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.x.a c(x var1) {
            return this.d(var1);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(x var1) {
            return this.d(var1);
         }

         // $VF: synthetic method
         @Override
         public a.y.a g() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public a.x.a i() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public a.p.a ai() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public a.y.a h() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public a.x.a j() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public a.p.a ah() {
            return this.m();
         }

         // $VF: synthetic method
         a(a.p.b var1, an.b.c.a var2) {
            this(var1);
         }
      }
   }

   public interface d extends aa {
      boolean o();

      int p();

      List<Integer> q();

      int r();

      int a(int var1);
   }

   public static final class e extends p implements an.b.f {
      private static final an.b.e K = new an.b.e(true);
      private final ap L;
      public static ab<an.b.e> a = new a.c<an.b.e>() {
         public an.b.e c(a.h var1, n var2) throws s {
            return new an.b.e(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int M;
      private int N;
      public static final int b = 1;
      private int O;
      public static final int c = 2;
      private int P;
      public static final int d = 3;
      private int Q;
      public static final int e = 4;
      private int R;
      public static final int f = 5;
      private int S;
      public static final int g = 6;
      private int T;
      public static final int h = 7;
      private int U;
      public static final int i = 8;
      private int V;
      public static final int j = 9;
      private a.g W;
      public static final int k = 10;
      private a.g X;
      public static final int l = 11;
      private int Y;
      public static final int n = 12;
      private int Z;
      public static final int o = 13;
      private int aa;
      public static final int p = 14;
      private int ab;
      public static final int q = 15;
      private int ac;
      public static final int r = 16;
      private int ad;
      public static final int s = 17;
      private int ae;
      public static final int t = 18;
      private int af;
      public static final int u = 19;
      private int ag;
      public static final int v = 20;
      private a.g ah;
      public static final int w = 21;
      private a.g ai;
      public static final int x = 22;
      private int aj;
      public static final int y = 23;
      private int ak;
      public static final int z = 24;
      private int al;
      public static final int A = 25;
      private a.g am;
      public static final int B = 26;
      private int an;
      public static final int C = 27;
      private int ao;
      public static final int D = 28;
      private int ap;
      public static final int E = 29;
      private int aq;
      public static final int F = 30;
      private int ar;
      public static final int G = 31;
      private int as;
      public static final int H = 32;
      private int at;
      public static final int I = 33;
      private int au;
      public static final int J = 34;
      private int av;
      private byte aw = -1;
      private int ax = -1;
      private static final long ay = 0L;

      static {
         K.aS();
      }

      private e(a.p.a<?> var1) {
         super(var1);
         this.L = var1.b_();
      }

      private e(boolean var1) {
         this.L = ap.c();
      }

      public static an.b.e h() {
         return K;
      }

      public an.b.e k() {
         return K;
      }

      @Override
      public final ap b_() {
         return this.L;
      }

      private e(a.h var1, n var2) throws s {
         this.aS();
         int var3 = 0;
         int var4 = 0;
         a.ap.a var5 = ap.b();

         try {
            boolean var6 = false;

            while (!var6) {
               int var7 = var1.a();
               switch (var7) {
                  case 0:
                     var6 = true;
                     break;
                  case 8:
                     this.M |= 1;
                     this.O = var1.m();
                     break;
                  case 16:
                     this.M |= 2;
                     this.P = var1.g();
                     break;
                  case 24:
                     this.M |= 4;
                     this.Q = var1.g();
                     break;
                  case 32:
                     this.M |= 8;
                     this.R = var1.g();
                     break;
                  case 40:
                     this.M |= 16;
                     this.S = var1.g();
                     break;
                  case 48:
                     this.M |= 32;
                     this.T = var1.g();
                     break;
                  case 56:
                     this.M |= 64;
                     this.U = var1.g();
                     break;
                  case 64:
                     this.M |= 128;
                     this.V = var1.g();
                     break;
                  case 74:
                     this.M |= 256;
                     this.W = var1.l();
                     break;
                  case 82:
                     this.M |= 512;
                     this.X = var1.l();
                     break;
                  case 88:
                     this.M |= 1024;
                     this.Y = var1.g();
                     break;
                  case 96:
                     this.M |= 2048;
                     this.Z = var1.g();
                     break;
                  case 104:
                     this.M |= 4096;
                     this.aa = var1.g();
                     break;
                  case 112:
                     this.M |= 8192;
                     this.ab = var1.g();
                     break;
                  case 120:
                     this.M |= 16384;
                     this.ac = var1.g();
                     break;
                  case 128:
                     this.M |= 32768;
                     this.ad = var1.g();
                     break;
                  case 136:
                     this.M |= 65536;
                     this.ae = var1.g();
                     break;
                  case 144:
                     this.M |= 131072;
                     this.af = var1.g();
                     break;
                  case 152:
                     this.M |= 262144;
                     this.ag = var1.g();
                     break;
                  case 162:
                     this.M |= 524288;
                     this.ah = var1.l();
                     break;
                  case 170:
                     this.M |= 1048576;
                     this.ai = var1.l();
                     break;
                  case 176:
                     this.M |= 2097152;
                     this.aj = var1.g();
                     break;
                  case 184:
                     this.M |= 4194304;
                     this.ak = var1.g();
                     break;
                  case 192:
                     this.M |= 8388608;
                     this.al = var1.g();
                     break;
                  case 202:
                     this.M |= 16777216;
                     this.am = var1.l();
                     break;
                  case 208:
                     this.M |= 33554432;
                     this.an = var1.g();
                     break;
                  case 216:
                     this.M |= 67108864;
                     this.ao = var1.g();
                     break;
                  case 224:
                     this.M |= 134217728;
                     this.ap = var1.g();
                     break;
                  case 232:
                     this.M |= 268435456;
                     this.aq = var1.g();
                     break;
                  case 240:
                     this.M |= 536870912;
                     this.ar = var1.g();
                     break;
                  case 248:
                     this.M |= 1073741824;
                     this.as = var1.g();
                     break;
                  case 256:
                     this.M |= Integer.MIN_VALUE;
                     this.at = var1.g();
                     break;
                  case 264:
                     this.N |= 1;
                     this.au = var1.g();
                     break;
                  case 272:
                     this.N |= 2;
                     this.av = var1.g();
                     break;
                  default:
                     if (!this.a(var1, var5, var2, var7)) {
                        var6 = true;
                     }
               }
            }
         } catch (s var12) {
            throw var12.a(this);
         } catch (IOException var13) {
            throw new s(var13.getMessage()).a(this);
         } finally {
            this.L = var5.b();
            this.ad();
         }
      }

      public static final a.k.a n() {
         return an.b.e;
      }

      @Override
      protected a.p.g l() {
         return an.b.f.a(an.b.e.class, an.b.e.a.class);
      }

      @Override
      public ab<an.b.e> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.M & 1) == 1;
      }

      @Override
      public int p() {
         return this.O;
      }

      @Override
      public boolean q() {
         return (this.M & 2) == 2;
      }

      @Override
      public int r() {
         return this.P;
      }

      @Override
      public boolean s() {
         return (this.M & 4) == 4;
      }

      @Override
      public int t() {
         return this.Q;
      }

      @Override
      public boolean u() {
         return (this.M & 8) == 8;
      }

      @Override
      public int v() {
         return this.R;
      }

      @Override
      public boolean w() {
         return (this.M & 16) == 16;
      }

      @Override
      public int x() {
         return this.S;
      }

      @Override
      public boolean y() {
         return (this.M & 32) == 32;
      }

      @Override
      public int z() {
         return this.T;
      }

      @Override
      public boolean A() {
         return (this.M & 64) == 64;
      }

      @Override
      public int B() {
         return this.U;
      }

      @Override
      public boolean C() {
         return (this.M & 128) == 128;
      }

      @Override
      public int D() {
         return this.V;
      }

      @Override
      public boolean E() {
         return (this.M & 256) == 256;
      }

      @Override
      public a.g F() {
         return this.W;
      }

      @Override
      public boolean G() {
         return (this.M & 512) == 512;
      }

      @Override
      public a.g H() {
         return this.X;
      }

      @Override
      public boolean K() {
         return (this.M & 1024) == 1024;
      }

      @Override
      public int L() {
         return this.Y;
      }

      @Override
      public boolean S() {
         return (this.M & 2048) == 2048;
      }

      @Override
      public int T() {
         return this.Z;
      }

      @Override
      public boolean U() {
         return (this.M & 4096) == 4096;
      }

      @Override
      public int V() {
         return this.aa;
      }

      @Override
      public boolean W() {
         return (this.M & 8192) == 8192;
      }

      @Override
      public int X() {
         return this.ab;
      }

      @Override
      public boolean Y() {
         return (this.M & 16384) == 16384;
      }

      @Override
      public int Z() {
         return this.ac;
      }

      @Override
      public boolean aa() {
         return (this.M & 32768) == 32768;
      }

      @Override
      public int ab() {
         return this.ad;
      }

      @Override
      public boolean ae() {
         return (this.M & 65536) == 65536;
      }

      @Override
      public int af() {
         return this.ae;
      }

      @Override
      public boolean ag() {
         return (this.M & 131072) == 131072;
      }

      @Override
      public int i_() {
         return this.af;
      }

      @Override
      public boolean j_() {
         return (this.M & 262144) == 262144;
      }

      @Override
      public int k_() {
         return this.ag;
      }

      @Override
      public boolean l_() {
         return (this.M & 524288) == 524288;
      }

      @Override
      public a.g m_() {
         return this.ah;
      }

      @Override
      public boolean n_() {
         return (this.M & 1048576) == 1048576;
      }

      @Override
      public a.g an() {
         return this.ai;
      }

      @Override
      public boolean ao() {
         return (this.M & 2097152) == 2097152;
      }

      @Override
      public int ap() {
         return this.aj;
      }

      @Override
      public boolean aq() {
         return (this.M & 4194304) == 4194304;
      }

      @Override
      public int ar() {
         return this.ak;
      }

      @Override
      public boolean as() {
         return (this.M & 8388608) == 8388608;
      }

      @Override
      public int at() {
         return this.al;
      }

      @Override
      public boolean au() {
         return (this.M & 16777216) == 16777216;
      }

      @Override
      public a.g av() {
         return this.am;
      }

      @Override
      public boolean aw() {
         return (this.M & 33554432) == 33554432;
      }

      @Override
      public int ax() {
         return this.an;
      }

      @Override
      public boolean ay() {
         return (this.M & 67108864) == 67108864;
      }

      @Override
      public int az() {
         return this.ao;
      }

      @Override
      public boolean o_() {
         return (this.M & 134217728) == 134217728;
      }

      @Override
      public int aB() {
         return this.ap;
      }

      @Override
      public boolean aC() {
         return (this.M & 268435456) == 268435456;
      }

      @Override
      public int aD() {
         return this.aq;
      }

      @Override
      public boolean p_() {
         return (this.M & 536870912) == 536870912;
      }

      @Override
      public int aF() {
         return this.ar;
      }

      @Override
      public boolean aG() {
         return (this.M & 1073741824) == 1073741824;
      }

      @Override
      public int aH() {
         return this.as;
      }

      @Override
      public boolean aI() {
         return (this.M & -2147483648) == Integer.MIN_VALUE;
      }

      @Override
      public int aJ() {
         return this.at;
      }

      @Override
      public boolean aK() {
         return (this.N & 1) == 1;
      }

      @Override
      public int aL() {
         return this.au;
      }

      @Override
      public boolean aM() {
         return (this.N & 2) == 2;
      }

      @Override
      public int aN() {
         return this.av;
      }

      private void aS() {
         this.O = 0;
         this.P = 0;
         this.Q = 0;
         this.R = 0;
         this.S = 0;
         this.T = 0;
         this.U = 0;
         this.V = 0;
         this.W = a.g.d;
         this.X = a.g.d;
         this.Y = 0;
         this.Z = 0;
         this.aa = 0;
         this.ab = 0;
         this.ac = 0;
         this.ad = 0;
         this.ae = 0;
         this.af = 0;
         this.ag = 0;
         this.ah = a.g.d;
         this.ai = a.g.d;
         this.aj = 0;
         this.ak = 0;
         this.al = 0;
         this.am = a.g.d;
         this.an = 0;
         this.ao = 0;
         this.ap = 0;
         this.aq = 0;
         this.ar = 0;
         this.as = 0;
         this.at = 0;
         this.au = 0;
         this.av = 0;
      }

      @Override
      public final boolean a() {
         byte var1 = this.aw;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.aw = 1;
         return true;
      }

      @Override
      public void a(a.i var1) throws IOException {
         this.d();
         if ((this.M & 1) == 1) {
            var1.c(1, this.O);
         }

         if ((this.M & 2) == 2) {
            var1.a(2, this.P);
         }

         if ((this.M & 4) == 4) {
            var1.a(3, this.Q);
         }

         if ((this.M & 8) == 8) {
            var1.a(4, this.R);
         }

         if ((this.M & 16) == 16) {
            var1.a(5, this.S);
         }

         if ((this.M & 32) == 32) {
            var1.a(6, this.T);
         }

         if ((this.M & 64) == 64) {
            var1.a(7, this.U);
         }

         if ((this.M & 128) == 128) {
            var1.a(8, this.V);
         }

         if ((this.M & 256) == 256) {
            var1.a(9, this.W);
         }

         if ((this.M & 512) == 512) {
            var1.a(10, this.X);
         }

         if ((this.M & 1024) == 1024) {
            var1.a(11, this.Y);
         }

         if ((this.M & 2048) == 2048) {
            var1.a(12, this.Z);
         }

         if ((this.M & 4096) == 4096) {
            var1.a(13, this.aa);
         }

         if ((this.M & 8192) == 8192) {
            var1.a(14, this.ab);
         }

         if ((this.M & 16384) == 16384) {
            var1.a(15, this.ac);
         }

         if ((this.M & 32768) == 32768) {
            var1.a(16, this.ad);
         }

         if ((this.M & 65536) == 65536) {
            var1.a(17, this.ae);
         }

         if ((this.M & 131072) == 131072) {
            var1.a(18, this.af);
         }

         if ((this.M & 262144) == 262144) {
            var1.a(19, this.ag);
         }

         if ((this.M & 524288) == 524288) {
            var1.a(20, this.ah);
         }

         if ((this.M & 1048576) == 1048576) {
            var1.a(21, this.ai);
         }

         if ((this.M & 2097152) == 2097152) {
            var1.a(22, this.aj);
         }

         if ((this.M & 4194304) == 4194304) {
            var1.a(23, this.ak);
         }

         if ((this.M & 8388608) == 8388608) {
            var1.a(24, this.al);
         }

         if ((this.M & 16777216) == 16777216) {
            var1.a(25, this.am);
         }

         if ((this.M & 33554432) == 33554432) {
            var1.a(26, this.an);
         }

         if ((this.M & 67108864) == 67108864) {
            var1.a(27, this.ao);
         }

         if ((this.M & 134217728) == 134217728) {
            var1.a(28, this.ap);
         }

         if ((this.M & 268435456) == 268435456) {
            var1.a(29, this.aq);
         }

         if ((this.M & 536870912) == 536870912) {
            var1.a(30, this.ar);
         }

         if ((this.M & 1073741824) == 1073741824) {
            var1.a(31, this.as);
         }

         if ((this.M & -2147483648) == Integer.MIN_VALUE) {
            var1.a(32, this.at);
         }

         if ((this.N & 1) == 1) {
            var1.a(33, this.au);
         }

         if ((this.N & 2) == 2) {
            var1.a(34, this.av);
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.ax;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.M & 1) == 1) {
            var1 += a.i.i(1, this.O);
         }

         if ((this.M & 2) == 2) {
            var1 += a.i.g(2, this.P);
         }

         if ((this.M & 4) == 4) {
            var1 += a.i.g(3, this.Q);
         }

         if ((this.M & 8) == 8) {
            var1 += a.i.g(4, this.R);
         }

         if ((this.M & 16) == 16) {
            var1 += a.i.g(5, this.S);
         }

         if ((this.M & 32) == 32) {
            var1 += a.i.g(6, this.T);
         }

         if ((this.M & 64) == 64) {
            var1 += a.i.g(7, this.U);
         }

         if ((this.M & 128) == 128) {
            var1 += a.i.g(8, this.V);
         }

         if ((this.M & 256) == 256) {
            var1 += a.i.c(9, this.W);
         }

         if ((this.M & 512) == 512) {
            var1 += a.i.c(10, this.X);
         }

         if ((this.M & 1024) == 1024) {
            var1 += a.i.g(11, this.Y);
         }

         if ((this.M & 2048) == 2048) {
            var1 += a.i.g(12, this.Z);
         }

         if ((this.M & 4096) == 4096) {
            var1 += a.i.g(13, this.aa);
         }

         if ((this.M & 8192) == 8192) {
            var1 += a.i.g(14, this.ab);
         }

         if ((this.M & 16384) == 16384) {
            var1 += a.i.g(15, this.ac);
         }

         if ((this.M & 32768) == 32768) {
            var1 += a.i.g(16, this.ad);
         }

         if ((this.M & 65536) == 65536) {
            var1 += a.i.g(17, this.ae);
         }

         if ((this.M & 131072) == 131072) {
            var1 += a.i.g(18, this.af);
         }

         if ((this.M & 262144) == 262144) {
            var1 += a.i.g(19, this.ag);
         }

         if ((this.M & 524288) == 524288) {
            var1 += a.i.c(20, this.ah);
         }

         if ((this.M & 1048576) == 1048576) {
            var1 += a.i.c(21, this.ai);
         }

         if ((this.M & 2097152) == 2097152) {
            var1 += a.i.g(22, this.aj);
         }

         if ((this.M & 4194304) == 4194304) {
            var1 += a.i.g(23, this.ak);
         }

         if ((this.M & 8388608) == 8388608) {
            var1 += a.i.g(24, this.al);
         }

         if ((this.M & 16777216) == 16777216) {
            var1 += a.i.c(25, this.am);
         }

         if ((this.M & 33554432) == 33554432) {
            var1 += a.i.g(26, this.an);
         }

         if ((this.M & 67108864) == 67108864) {
            var1 += a.i.g(27, this.ao);
         }

         if ((this.M & 134217728) == 134217728) {
            var1 += a.i.g(28, this.ap);
         }

         if ((this.M & 268435456) == 268435456) {
            var1 += a.i.g(29, this.aq);
         }

         if ((this.M & 536870912) == 536870912) {
            var1 += a.i.g(30, this.ar);
         }

         if ((this.M & 1073741824) == 1073741824) {
            var1 += a.i.g(31, this.as);
         }

         if ((this.M & -2147483648) == Integer.MIN_VALUE) {
            var1 += a.i.g(32, this.at);
         }

         if ((this.N & 1) == 1) {
            var1 += a.i.g(33, this.au);
         }

         if ((this.N & 2) == 2) {
            var1 += a.i.g(34, this.av);
         }

         var1 += this.b_().d();
         this.ax = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static an.b.e a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.b.e a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.b.e a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.b.e a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.b.e a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.b.e a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.b.e b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.b.e b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.b.e a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.b.e a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.b.e.a aO() {
         return an.b.e.a.bu();
      }

      public an.b.e.a aP() {
         return aO();
      }

      public static an.b.e.a a(an.b.e var0) {
         return aO().a(var0);
      }

      public an.b.e.a aQ() {
         return a(this);
      }

      protected an.b.e.a a(a.p.b var1) {
         return new an.b.e.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.aQ();
      }

      // $VF: synthetic method
      @Override
      public a.x.a M() {
         return this.aQ();
      }

      // $VF: synthetic method
      @Override
      protected a.x.a b(a.p.b var1) {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public a.y.a P() {
         return this.aP();
      }

      // $VF: synthetic method
      @Override
      public a.x.a N() {
         return this.aP();
      }

      // $VF: synthetic method
      @Override
      public y Q() {
         return this.k();
      }

      // $VF: synthetic method
      @Override
      public x R() {
         return this.k();
      }

      // $VF: synthetic method
      e(a.h var1, n var2, an.b.e var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      e(a.p.a var1, an.b.e var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.b.e.a> implements an.b.f {
         private int a;
         private int b;
         private int c;
         private int d;
         private int e;
         private int f;
         private int g;
         private int h;
         private int i;
         private int j;
         private a.g k = a.g.d;
         private a.g l = a.g.d;
         private int m;
         private int n;
         private int o;
         private int p;
         private int q;
         private int r;
         private int s;
         private int t;
         private int u;
         private a.g v = a.g.d;
         private a.g w = a.g.d;
         private int x;
         private int y;
         private int z;
         private a.g A = a.g.d;
         private int B;
         private int C;
         private int D;
         private int E;
         private int F;
         private int G;
         private int H;
         private int I;
         private int J;

         public static final a.k.a k() {
            return an.b.e;
         }

         @Override
         protected a.p.g l() {
            return an.b.f.a(an.b.e.class, an.b.e.a.class);
         }

         private a() {
            this.bt();
         }

         private a(a.p.b var1) {
            super(var1);
            this.bt();
         }

         private void bt() {
            an.b.e.m;
         }

         private static an.b.e.a bu() {
            return new an.b.e.a();
         }

         public an.b.e.a m() {
            super.ah();
            this.c = 0;
            this.a &= -2;
            this.d = 0;
            this.a &= -3;
            this.e = 0;
            this.a &= -5;
            this.f = 0;
            this.a &= -9;
            this.g = 0;
            this.a &= -17;
            this.h = 0;
            this.a &= -33;
            this.i = 0;
            this.a &= -65;
            this.j = 0;
            this.a &= -129;
            this.k = a.g.d;
            this.a &= -257;
            this.l = a.g.d;
            this.a &= -513;
            this.m = 0;
            this.a &= -1025;
            this.n = 0;
            this.a &= -2049;
            this.o = 0;
            this.a &= -4097;
            this.p = 0;
            this.a &= -8193;
            this.q = 0;
            this.a &= -16385;
            this.r = 0;
            this.a &= -32769;
            this.s = 0;
            this.a &= -65537;
            this.t = 0;
            this.a &= -131073;
            this.u = 0;
            this.a &= -262145;
            this.v = a.g.d;
            this.a &= -524289;
            this.w = a.g.d;
            this.a &= -1048577;
            this.x = 0;
            this.a &= -2097153;
            this.y = 0;
            this.a &= -4194305;
            this.z = 0;
            this.a &= -8388609;
            this.A = a.g.d;
            this.a &= -16777217;
            this.B = 0;
            this.a &= -33554433;
            this.C = 0;
            this.a &= -67108865;
            this.D = 0;
            this.a &= -134217729;
            this.E = 0;
            this.a &= -268435457;
            this.F = 0;
            this.a &= -536870913;
            this.G = 0;
            this.a &= -1073741825;
            this.H = 0;
            this.a &= Integer.MAX_VALUE;
            this.I = 0;
            this.b &= -2;
            this.J = 0;
            this.b &= -3;
            return this;
         }

         public an.b.e.a n() {
            return bu().a(this.N());
         }

         @Override
         public a.k.a J() {
            return an.b.e;
         }

         public an.b.e I() {
            return an.b.e.h();
         }

         public an.b.e M() {
            an.b.e var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.b.e N() {
            an.b.e var1 = new an.b.e(this, null);
            int var2 = this.a;
            int var3 = this.b;
            int var4 = 0;
            int var5 = 0;
            if ((var2 & 1) == 1) {
               var4 |= 1;
            }

            var1.O = this.c;
            if ((var2 & 2) == 2) {
               var4 |= 2;
            }

            var1.P = this.d;
            if ((var2 & 4) == 4) {
               var4 |= 4;
            }

            var1.Q = this.e;
            if ((var2 & 8) == 8) {
               var4 |= 8;
            }

            var1.R = this.f;
            if ((var2 & 16) == 16) {
               var4 |= 16;
            }

            var1.S = this.g;
            if ((var2 & 32) == 32) {
               var4 |= 32;
            }

            var1.T = this.h;
            if ((var2 & 64) == 64) {
               var4 |= 64;
            }

            var1.U = this.i;
            if ((var2 & 128) == 128) {
               var4 |= 128;
            }

            var1.V = this.j;
            if ((var2 & 256) == 256) {
               var4 |= 256;
            }

            var1.W = this.k;
            if ((var2 & 512) == 512) {
               var4 |= 512;
            }

            var1.X = this.l;
            if ((var2 & 1024) == 1024) {
               var4 |= 1024;
            }

            var1.Y = this.m;
            if ((var2 & 2048) == 2048) {
               var4 |= 2048;
            }

            var1.Z = this.n;
            if ((var2 & 4096) == 4096) {
               var4 |= 4096;
            }

            var1.aa = this.o;
            if ((var2 & 8192) == 8192) {
               var4 |= 8192;
            }

            var1.ab = this.p;
            if ((var2 & 16384) == 16384) {
               var4 |= 16384;
            }

            var1.ac = this.q;
            if ((var2 & 32768) == 32768) {
               var4 |= 32768;
            }

            var1.ad = this.r;
            if ((var2 & 65536) == 65536) {
               var4 |= 65536;
            }

            var1.ae = this.s;
            if ((var2 & 131072) == 131072) {
               var4 |= 131072;
            }

            var1.af = this.t;
            if ((var2 & 262144) == 262144) {
               var4 |= 262144;
            }

            var1.ag = this.u;
            if ((var2 & 524288) == 524288) {
               var4 |= 524288;
            }

            var1.ah = this.v;
            if ((var2 & 1048576) == 1048576) {
               var4 |= 1048576;
            }

            var1.ai = this.w;
            if ((var2 & 2097152) == 2097152) {
               var4 |= 2097152;
            }

            var1.aj = this.x;
            if ((var2 & 4194304) == 4194304) {
               var4 |= 4194304;
            }

            var1.ak = this.y;
            if ((var2 & 8388608) == 8388608) {
               var4 |= 8388608;
            }

            var1.al = this.z;
            if ((var2 & 16777216) == 16777216) {
               var4 |= 16777216;
            }

            var1.am = this.A;
            if ((var2 & 33554432) == 33554432) {
               var4 |= 33554432;
            }

            var1.an = this.B;
            if ((var2 & 67108864) == 67108864) {
               var4 |= 67108864;
            }

            var1.ao = this.C;
            if ((var2 & 134217728) == 134217728) {
               var4 |= 134217728;
            }

            var1.ap = this.D;
            if ((var2 & 268435456) == 268435456) {
               var4 |= 268435456;
            }

            var1.aq = this.E;
            if ((var2 & 536870912) == 536870912) {
               var4 |= 536870912;
            }

            var1.ar = this.F;
            if ((var2 & 1073741824) == 1073741824) {
               var4 |= 1073741824;
            }

            var1.as = this.G;
            if ((var2 & -2147483648) == Integer.MIN_VALUE) {
               var4 |= Integer.MIN_VALUE;
            }

            var1.at = this.H;
            if ((var3 & 1) == 1) {
               var5 |= 1;
            }

            var1.au = this.I;
            if ((var3 & 2) == 2) {
               var5 |= 2;
            }

            var1.av = this.J;
            var1.M = var4;
            var1.N = var5;
            this.q_();
            return var1;
         }

         public an.b.e.a d(x var1) {
            if (var1 instanceof an.b.e) {
               return this.a((an.b.e)var1);
            }

            super.a(var1);
            return this;
         }

         public an.b.e.a a(an.b.e var1) {
            if (var1 == an.b.e.h()) {
               return this;
            }

            if (var1.o()) {
               this.a(var1.p());
            }

            if (var1.q()) {
               this.b(var1.r());
            }

            if (var1.s()) {
               this.c(var1.t());
            }

            if (var1.u()) {
               this.d(var1.v());
            }

            if (var1.w()) {
               this.e(var1.x());
            }

            if (var1.y()) {
               this.f(var1.z());
            }

            if (var1.A()) {
               this.g(var1.B());
            }

            if (var1.C()) {
               this.h(var1.D());
            }

            if (var1.E()) {
               this.e(var1.F());
            }

            if (var1.G()) {
               this.f(var1.H());
            }

            if (var1.K()) {
               this.i(var1.L());
            }

            if (var1.S()) {
               this.j(var1.T());
            }

            if (var1.U()) {
               this.k(var1.V());
            }

            if (var1.W()) {
               this.l(var1.X());
            }

            if (var1.Y()) {
               this.m(var1.Z());
            }

            if (var1.aa()) {
               this.n(var1.ab());
            }

            if (var1.ae()) {
               this.o(var1.af());
            }

            if (var1.ag()) {
               this.p(var1.i_());
            }

            if (var1.j_()) {
               this.q(var1.k_());
            }

            if (var1.l_()) {
               this.g(var1.m_());
            }

            if (var1.n_()) {
               this.h(var1.an());
            }

            if (var1.ao()) {
               this.r(var1.ap());
            }

            if (var1.aq()) {
               this.s(var1.ar());
            }

            if (var1.as()) {
               this.t(var1.at());
            }

            if (var1.au()) {
               this.i(var1.av());
            }

            if (var1.aw()) {
               this.u(var1.ax());
            }

            if (var1.ay()) {
               this.v(var1.az());
            }

            if (var1.o_()) {
               this.w(var1.aB());
            }

            if (var1.aC()) {
               this.x(var1.aD());
            }

            if (var1.p_()) {
               this.y(var1.aF());
            }

            if (var1.aG()) {
               this.z(var1.aH());
            }

            if (var1.aI()) {
               this.A(var1.aJ());
            }

            if (var1.aK()) {
               this.B(var1.aL());
            }

            if (var1.aM()) {
               this.C(var1.aN());
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public an.b.e.a e(a.h var1, n var2) throws IOException {
            an.b.e var3 = null;

            try {
               var3 = an.b.e.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.b.e)var8.a();
               throw var8;
            } finally {
               if (var3 != null) {
                  this.a(var3);
               }
            }

            return this;
         }

         @Override
         public boolean o() {
            return (this.a & 1) == 1;
         }

         @Override
         public int p() {
            return this.c;
         }

         public an.b.e.a a(int var1) {
            this.a |= 1;
            this.c = var1;
            this.t_();
            return this;
         }

         public an.b.e.a O() {
            this.a &= -2;
            this.c = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean q() {
            return (this.a & 2) == 2;
         }

         @Override
         public int r() {
            return this.d;
         }

         public an.b.e.a b(int var1) {
            this.a |= 2;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.b.e.a P() {
            this.a &= -3;
            this.d = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean s() {
            return (this.a & 4) == 4;
         }

         @Override
         public int t() {
            return this.e;
         }

         public an.b.e.a c(int var1) {
            this.a |= 4;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.b.e.a ac() {
            this.a &= -5;
            this.e = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean u() {
            return (this.a & 8) == 8;
         }

         @Override
         public int v() {
            return this.f;
         }

         public an.b.e.a d(int var1) {
            this.a |= 8;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.b.e.a ad() {
            this.a &= -9;
            this.f = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean w() {
            return (this.a & 16) == 16;
         }

         @Override
         public int x() {
            return this.g;
         }

         public an.b.e.a e(int var1) {
            this.a |= 16;
            this.g = var1;
            this.t_();
            return this;
         }

         public an.b.e.a aO() {
            this.a &= -17;
            this.g = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean y() {
            return (this.a & 32) == 32;
         }

         @Override
         public int z() {
            return this.h;
         }

         public an.b.e.a f(int var1) {
            this.a |= 32;
            this.h = var1;
            this.t_();
            return this;
         }

         public an.b.e.a aP() {
            this.a &= -33;
            this.h = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean A() {
            return (this.a & 64) == 64;
         }

         @Override
         public int B() {
            return this.i;
         }

         public an.b.e.a g(int var1) {
            this.a |= 64;
            this.i = var1;
            this.t_();
            return this;
         }

         public an.b.e.a aQ() {
            this.a &= -65;
            this.i = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean C() {
            return (this.a & 128) == 128;
         }

         @Override
         public int D() {
            return this.j;
         }

         public an.b.e.a h(int var1) {
            this.a |= 128;
            this.j = var1;
            this.t_();
            return this;
         }

         public an.b.e.a aR() {
            this.a &= -129;
            this.j = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean E() {
            return (this.a & 256) == 256;
         }

         @Override
         public a.g F() {
            return this.k;
         }

         public an.b.e.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 256;
            this.k = var1;
            this.t_();
            return this;
         }

         public an.b.e.a aS() {
            this.a &= -257;
            this.k = an.b.e.h().F();
            this.t_();
            return this;
         }

         @Override
         public boolean G() {
            return (this.a & 512) == 512;
         }

         @Override
         public a.g H() {
            return this.l;
         }

         public an.b.e.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 512;
            this.l = var1;
            this.t_();
            return this;
         }

         public an.b.e.a aT() {
            this.a &= -513;
            this.l = an.b.e.h().H();
            this.t_();
            return this;
         }

         @Override
         public boolean K() {
            return (this.a & 1024) == 1024;
         }

         @Override
         public int L() {
            return this.m;
         }

         public an.b.e.a i(int var1) {
            this.a |= 1024;
            this.m = var1;
            this.t_();
            return this;
         }

         public an.b.e.a aU() {
            this.a &= -1025;
            this.m = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean S() {
            return (this.a & 2048) == 2048;
         }

         @Override
         public int T() {
            return this.n;
         }

         public an.b.e.a j(int var1) {
            this.a |= 2048;
            this.n = var1;
            this.t_();
            return this;
         }

         public an.b.e.a aV() {
            this.a &= -2049;
            this.n = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean U() {
            return (this.a & 4096) == 4096;
         }

         @Override
         public int V() {
            return this.o;
         }

         public an.b.e.a k(int var1) {
            this.a |= 4096;
            this.o = var1;
            this.t_();
            return this;
         }

         public an.b.e.a aW() {
            this.a &= -4097;
            this.o = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean W() {
            return (this.a & 8192) == 8192;
         }

         @Override
         public int X() {
            return this.p;
         }

         public an.b.e.a l(int var1) {
            this.a |= 8192;
            this.p = var1;
            this.t_();
            return this;
         }

         public an.b.e.a aX() {
            this.a &= -8193;
            this.p = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean Y() {
            return (this.a & 16384) == 16384;
         }

         @Override
         public int Z() {
            return this.q;
         }

         public an.b.e.a m(int var1) {
            this.a |= 16384;
            this.q = var1;
            this.t_();
            return this;
         }

         public an.b.e.a aY() {
            this.a &= -16385;
            this.q = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aa() {
            return (this.a & 32768) == 32768;
         }

         @Override
         public int ab() {
            return this.r;
         }

         public an.b.e.a n(int var1) {
            this.a |= 32768;
            this.r = var1;
            this.t_();
            return this;
         }

         public an.b.e.a aZ() {
            this.a &= -32769;
            this.r = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean ae() {
            return (this.a & 65536) == 65536;
         }

         @Override
         public int af() {
            return this.s;
         }

         public an.b.e.a o(int var1) {
            this.a |= 65536;
            this.s = var1;
            this.t_();
            return this;
         }

         public an.b.e.a ba() {
            this.a &= -65537;
            this.s = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean ag() {
            return (this.a & 131072) == 131072;
         }

         @Override
         public int i_() {
            return this.t;
         }

         public an.b.e.a p(int var1) {
            this.a |= 131072;
            this.t = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bb() {
            this.a &= -131073;
            this.t = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean j_() {
            return (this.a & 262144) == 262144;
         }

         @Override
         public int k_() {
            return this.u;
         }

         public an.b.e.a q(int var1) {
            this.a |= 262144;
            this.u = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bc() {
            this.a &= -262145;
            this.u = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean l_() {
            return (this.a & 524288) == 524288;
         }

         @Override
         public a.g m_() {
            return this.v;
         }

         public an.b.e.a g(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 524288;
            this.v = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bd() {
            this.a &= -524289;
            this.v = an.b.e.h().m_();
            this.t_();
            return this;
         }

         @Override
         public boolean n_() {
            return (this.a & 1048576) == 1048576;
         }

         @Override
         public a.g an() {
            return this.w;
         }

         public an.b.e.a h(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 1048576;
            this.w = var1;
            this.t_();
            return this;
         }

         public an.b.e.a be() {
            this.a &= -1048577;
            this.w = an.b.e.h().an();
            this.t_();
            return this;
         }

         @Override
         public boolean ao() {
            return (this.a & 2097152) == 2097152;
         }

         @Override
         public int ap() {
            return this.x;
         }

         public an.b.e.a r(int var1) {
            this.a |= 2097152;
            this.x = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bf() {
            this.a &= -2097153;
            this.x = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aq() {
            return (this.a & 4194304) == 4194304;
         }

         @Override
         public int ar() {
            return this.y;
         }

         public an.b.e.a s(int var1) {
            this.a |= 4194304;
            this.y = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bg() {
            this.a &= -4194305;
            this.y = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean as() {
            return (this.a & 8388608) == 8388608;
         }

         @Override
         public int at() {
            return this.z;
         }

         public an.b.e.a t(int var1) {
            this.a |= 8388608;
            this.z = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bh() {
            this.a &= -8388609;
            this.z = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean au() {
            return (this.a & 16777216) == 16777216;
         }

         @Override
         public a.g av() {
            return this.A;
         }

         public an.b.e.a i(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 16777216;
            this.A = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bi() {
            this.a &= -16777217;
            this.A = an.b.e.h().av();
            this.t_();
            return this;
         }

         @Override
         public boolean aw() {
            return (this.a & 33554432) == 33554432;
         }

         @Override
         public int ax() {
            return this.B;
         }

         public an.b.e.a u(int var1) {
            this.a |= 33554432;
            this.B = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bj() {
            this.a &= -33554433;
            this.B = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean ay() {
            return (this.a & 67108864) == 67108864;
         }

         @Override
         public int az() {
            return this.C;
         }

         public an.b.e.a v(int var1) {
            this.a |= 67108864;
            this.C = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bk() {
            this.a &= -67108865;
            this.C = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean o_() {
            return (this.a & 134217728) == 134217728;
         }

         @Override
         public int aB() {
            return this.D;
         }

         public an.b.e.a w(int var1) {
            this.a |= 134217728;
            this.D = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bl() {
            this.a &= -134217729;
            this.D = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aC() {
            return (this.a & 268435456) == 268435456;
         }

         @Override
         public int aD() {
            return this.E;
         }

         public an.b.e.a x(int var1) {
            this.a |= 268435456;
            this.E = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bm() {
            this.a &= -268435457;
            this.E = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean p_() {
            return (this.a & 536870912) == 536870912;
         }

         @Override
         public int aF() {
            return this.F;
         }

         public an.b.e.a y(int var1) {
            this.a |= 536870912;
            this.F = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bn() {
            this.a &= -536870913;
            this.F = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aG() {
            return (this.a & 1073741824) == 1073741824;
         }

         @Override
         public int aH() {
            return this.G;
         }

         public an.b.e.a z(int var1) {
            this.a |= 1073741824;
            this.G = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bo() {
            this.a &= -1073741825;
            this.G = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aI() {
            return (this.a & -2147483648) == Integer.MIN_VALUE;
         }

         @Override
         public int aJ() {
            return this.H;
         }

         public an.b.e.a A(int var1) {
            this.a |= Integer.MIN_VALUE;
            this.H = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bp() {
            this.a &= Integer.MAX_VALUE;
            this.H = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aK() {
            return (this.b & 1) == 1;
         }

         @Override
         public int aL() {
            return this.I;
         }

         public an.b.e.a B(int var1) {
            this.b |= 1;
            this.I = var1;
            this.t_();
            return this;
         }

         public an.b.e.a bq() {
            this.b &= -2;
            this.I = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aM() {
            return (this.b & 2) == 2;
         }

         @Override
         public int aN() {
            return this.J;
         }

         public an.b.e.a C(int var1) {
            this.b |= 2;
            this.J = var1;
            this.t_();
            return this;
         }

         public an.b.e.a br() {
            this.b &= -3;
            this.J = 0;
            this.t_();
            return this;
         }

         // $VF: synthetic method
         @Override
         public a.y.a h() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public a.x.a j() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public a.p.a ah() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public a.y.a c(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.x.a d(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.x.a c(x var1) {
            return this.d(var1);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(x var1) {
            return this.d(var1);
         }

         // $VF: synthetic method
         @Override
         public a.y.a g() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public a.x.a i() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public a.p.a ai() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public y am() {
            return this.M();
         }

         // $VF: synthetic method
         @Override
         public x ak() {
            return this.M();
         }

         // $VF: synthetic method
         @Override
         public y Q() {
            return this.I();
         }

         // $VF: synthetic method
         @Override
         public x R() {
            return this.I();
         }

         // $VF: synthetic method
         @Override
         public y al() {
            return this.N();
         }

         // $VF: synthetic method
         @Override
         public x aj() {
            return this.N();
         }

         // $VF: synthetic method
         a(a.p.b var1, an.b.e.a var2) {
            this(var1);
         }
      }
   }

   public interface f extends aa {
      boolean o();

      int p();

      boolean q();

      int r();

      boolean s();

      int t();

      boolean u();

      int v();

      boolean w();

      int x();

      boolean y();

      int z();

      boolean A();

      int B();

      boolean C();

      int D();

      boolean E();

      a.g F();

      boolean G();

      a.g H();

      boolean K();

      int L();

      boolean S();

      int T();

      boolean U();

      int V();

      boolean W();

      int X();

      boolean Y();

      int Z();

      boolean aa();

      int ab();

      boolean ae();

      int af();

      boolean ag();

      int i_();

      boolean j_();

      int k_();

      boolean l_();

      a.g m_();

      boolean n_();

      a.g an();

      boolean ao();

      int ap();

      boolean aq();

      int ar();

      boolean as();

      int at();

      boolean au();

      a.g av();

      boolean aw();

      int ax();

      boolean ay();

      int az();

      boolean o_();

      int aB();

      boolean aC();

      int aD();

      boolean p_();

      int aF();

      boolean aG();

      int aH();

      boolean aI();

      int aJ();

      boolean aK();

      int aL();

      boolean aM();

      int aN();
   }

   public static final class g extends p implements an.b.h {
      private static final an.b.g I = new an.b.g(true);
      private final ap J;
      public static ab<an.b.g> a = new a.c<an.b.g>() {
         public an.b.g c(a.h var1, n var2) throws s {
            return new an.b.g(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int K;
      public static final int b = 1;
      private int L;
      public static final int c = 2;
      private int M;
      public static final int d = 3;
      private int N;
      public static final int e = 4;
      private a.g O;
      public static final int f = 5;
      private a.g P;
      public static final int g = 6;
      private int Q;
      public static final int h = 7;
      private int R;
      public static final int i = 8;
      private int S;
      public static final int j = 9;
      private int T;
      public static final int k = 10;
      private List<Integer> U;
      public static final int l = 11;
      private List<a.g> V;
      public static final int n = 12;
      private int W;
      public static final int o = 13;
      private int X;
      public static final int p = 14;
      private int Y;
      public static final int q = 15;
      private int Z;
      public static final int r = 16;
      private int aa;
      public static final int s = 17;
      private int ab;
      public static final int t = 18;
      private int ac;
      public static final int u = 19;
      private int ad;
      public static final int v = 20;
      private int ae;
      public static final int w = 21;
      private int af;
      public static final int x = 22;
      private int ag;
      public static final int y = 23;
      private int ah;
      public static final int z = 24;
      private int ai;
      public static final int A = 25;
      private int aj;
      public static final int B = 26;
      private int ak;
      public static final int C = 27;
      private int al;
      public static final int D = 28;
      private int am;
      public static final int E = 29;
      private int an;
      public static final int F = 30;
      private int ao;
      public static final int G = 31;
      private int ap;
      public static final int H = 32;
      private int aq;
      private byte ar = -1;
      private int as = -1;
      private static final long at = 0L;

      static {
         I.aO();
      }

      private g(a.p.a<?> var1) {
         super(var1);
         this.J = var1.b_();
      }

      private g(boolean var1) {
         this.J = ap.c();
      }

      public static an.b.g h() {
         return I;
      }

      public an.b.g k() {
         return I;
      }

      @Override
      public final ap b_() {
         return this.J;
      }

      private g(a.h var1, n var2) throws s {
         this.aO();
         int var3 = 0;
         a.ap.a var4 = ap.b();

         try {
            boolean var5 = false;

            while (!var5) {
               int var6 = var1.a();
               int var8;
               switch (var6) {
                  case 0:
                     var5 = true;
                     continue;
                  case 8:
                     this.K |= 1;
                     this.L = var1.g();
                     continue;
                  case 16:
                     this.K |= 2;
                     this.M = var1.g();
                     continue;
                  case 24:
                     this.K |= 4;
                     this.N = var1.g();
                     continue;
                  case 34:
                     this.K |= 8;
                     this.O = var1.l();
                     continue;
                  case 42:
                     this.K |= 16;
                     this.P = var1.l();
                     continue;
                  case 48:
                     this.K |= 32;
                     this.Q = var1.g();
                     continue;
                  case 56:
                     this.K |= 64;
                     this.R = var1.g();
                     continue;
                  case 64:
                     this.K |= 128;
                     this.S = var1.g();
                     continue;
                  case 72:
                     this.K |= 256;
                     this.T = var1.g();
                     continue;
                  case 80:
                     if ((var3 & 512) != 512) {
                        this.U = new ArrayList<>();
                        var3 |= 512;
                     }

                     this.U.add(var1.g());
                     continue;
                  case 82:
                     int var7 = var1.s();
                     var8 = var1.f(var7);
                     if ((var3 & 512) != 512 && var1.x() > 0) {
                        this.U = new ArrayList<>();
                        var3 |= 512;
                     }
                     break;
                  case 90:
                     if ((var3 & 1024) != 1024) {
                        this.V = new ArrayList<>();
                        var3 |= 1024;
                     }

                     this.V.add(var1.l());
                     continue;
                  case 96:
                     this.K |= 512;
                     this.W = var1.g();
                     continue;
                  case 104:
                     this.K |= 1024;
                     this.X = var1.g();
                     continue;
                  case 112:
                     this.K |= 2048;
                     this.Y = var1.g();
                     continue;
                  case 120:
                     this.K |= 4096;
                     this.Z = var1.g();
                     continue;
                  case 128:
                     this.K |= 8192;
                     this.aa = var1.g();
                     continue;
                  case 136:
                     this.K |= 16384;
                     this.ab = var1.g();
                     continue;
                  case 144:
                     this.K |= 32768;
                     this.ac = var1.g();
                     continue;
                  case 152:
                     this.K |= 65536;
                     this.ad = var1.g();
                     continue;
                  case 160:
                     this.K |= 131072;
                     this.ae = var1.g();
                     continue;
                  case 168:
                     this.K |= 262144;
                     this.af = var1.g();
                     continue;
                  case 176:
                     this.K |= 524288;
                     this.ag = var1.g();
                     continue;
                  case 184:
                     this.K |= 1048576;
                     this.ah = var1.g();
                     continue;
                  case 192:
                     this.K |= 2097152;
                     this.ai = var1.g();
                     continue;
                  case 200:
                     this.K |= 4194304;
                     this.aj = var1.g();
                     continue;
                  case 208:
                     this.K |= 8388608;
                     this.ak = var1.g();
                     continue;
                  case 216:
                     this.K |= 16777216;
                     this.al = var1.g();
                     continue;
                  case 224:
                     this.K |= 33554432;
                     this.am = var1.g();
                     continue;
                  case 232:
                     this.K |= 67108864;
                     this.an = var1.g();
                     continue;
                  case 240:
                     this.K |= 134217728;
                     this.ao = var1.g();
                     continue;
                  case 248:
                     this.K |= 268435456;
                     this.ap = var1.g();
                     continue;
                  case 256:
                     this.K |= 536870912;
                     this.aq = var1.g();
                     continue;
                  default:
                     if (!this.a(var1, var4, var2, var6)) {
                        var5 = true;
                     }
                     continue;
               }

               while (var1.x() > 0) {
                  this.U.add(var1.g());
               }

               var1.g(var8);
            }
         } catch (s var13) {
            throw var13.a(this);
         } catch (IOException var14) {
            throw new s(var14.getMessage()).a(this);
         } finally {
            if ((var3 & 512) == 512) {
               this.U = Collections.unmodifiableList(this.U);
            }

            if ((var3 & 1024) == 1024) {
               this.V = Collections.unmodifiableList(this.V);
            }

            this.J = var4.b();
            this.ad();
         }
      }

      public static final a.k.a n() {
         return an.b.g;
      }

      @Override
      protected a.p.g l() {
         return an.b.h.a(an.b.g.class, an.b.g.a.class);
      }

      @Override
      public ab<an.b.g> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.K & 1) == 1;
      }

      @Override
      public int p() {
         return this.L;
      }

      @Override
      public boolean q() {
         return (this.K & 2) == 2;
      }

      @Override
      public int r() {
         return this.M;
      }

      @Override
      public boolean s() {
         return (this.K & 4) == 4;
      }

      @Override
      public int t() {
         return this.N;
      }

      @Override
      public boolean u() {
         return (this.K & 8) == 8;
      }

      @Override
      public a.g v() {
         return this.O;
      }

      @Override
      public boolean w() {
         return (this.K & 16) == 16;
      }

      @Override
      public a.g x() {
         return this.P;
      }

      @Override
      public boolean y() {
         return (this.K & 32) == 32;
      }

      @Override
      public int z() {
         return this.Q;
      }

      @Override
      public boolean A() {
         return (this.K & 64) == 64;
      }

      @Override
      public int B() {
         return this.R;
      }

      @Override
      public boolean C() {
         return (this.K & 128) == 128;
      }

      @Override
      public int D() {
         return this.S;
      }

      @Override
      public boolean E() {
         return (this.K & 256) == 256;
      }

      @Override
      public int F() {
         return this.T;
      }

      @Override
      public List<Integer> G() {
         return this.U;
      }

      @Override
      public int H() {
         return this.U.size();
      }

      @Override
      public int a(int var1) {
         return this.U.get(var1);
      }

      @Override
      public List<a.g> K() {
         return this.V;
      }

      @Override
      public int L() {
         return this.V.size();
      }

      @Override
      public a.g b(int var1) {
         return this.V.get(var1);
      }

      @Override
      public boolean S() {
         return (this.K & 512) == 512;
      }

      @Override
      public int T() {
         return this.W;
      }

      @Override
      public boolean U() {
         return (this.K & 1024) == 1024;
      }

      @Override
      public int V() {
         return this.X;
      }

      @Override
      public boolean W() {
         return (this.K & 2048) == 2048;
      }

      @Override
      public int X() {
         return this.Y;
      }

      @Override
      public boolean Y() {
         return (this.K & 4096) == 4096;
      }

      @Override
      public int Z() {
         return this.Z;
      }

      @Override
      public boolean aa() {
         return (this.K & 8192) == 8192;
      }

      @Override
      public int ab() {
         return this.aa;
      }

      @Override
      public boolean ae() {
         return (this.K & 16384) == 16384;
      }

      @Override
      public int af() {
         return this.ab;
      }

      @Override
      public boolean ag() {
         return (this.K & 32768) == 32768;
      }

      @Override
      public int u_() {
         return this.ac;
      }

      @Override
      public boolean v_() {
         return (this.K & 65536) == 65536;
      }

      @Override
      public int w_() {
         return this.ad;
      }

      @Override
      public boolean x_() {
         return (this.K & 131072) == 131072;
      }

      @Override
      public int y_() {
         return this.ae;
      }

      @Override
      public boolean z_() {
         return (this.K & 262144) == 262144;
      }

      @Override
      public int an() {
         return this.af;
      }

      @Override
      public boolean ao() {
         return (this.K & 524288) == 524288;
      }

      @Override
      public int ap() {
         return this.ag;
      }

      @Override
      public boolean aq() {
         return (this.K & 1048576) == 1048576;
      }

      @Override
      public int ar() {
         return this.ah;
      }

      @Override
      public boolean as() {
         return (this.K & 2097152) == 2097152;
      }

      @Override
      public int at() {
         return this.ai;
      }

      @Override
      public boolean au() {
         return (this.K & 4194304) == 4194304;
      }

      @Override
      public int av() {
         return this.aj;
      }

      @Override
      public boolean aw() {
         return (this.K & 8388608) == 8388608;
      }

      @Override
      public int ax() {
         return this.ak;
      }

      @Override
      public boolean ay() {
         return (this.K & 16777216) == 16777216;
      }

      @Override
      public int az() {
         return this.al;
      }

      @Override
      public boolean A_() {
         return (this.K & 33554432) == 33554432;
      }

      @Override
      public int aB() {
         return this.am;
      }

      @Override
      public boolean aC() {
         return (this.K & 67108864) == 67108864;
      }

      @Override
      public int aD() {
         return this.an;
      }

      @Override
      public boolean B_() {
         return (this.K & 134217728) == 134217728;
      }

      @Override
      public int aF() {
         return this.ao;
      }

      @Override
      public boolean aG() {
         return (this.K & 268435456) == 268435456;
      }

      @Override
      public int aH() {
         return this.ap;
      }

      @Override
      public boolean aI() {
         return (this.K & 536870912) == 536870912;
      }

      @Override
      public int aJ() {
         return this.aq;
      }

      private void aO() {
         this.L = 0;
         this.M = 0;
         this.N = 0;
         this.O = a.g.d;
         this.P = a.g.d;
         this.Q = 0;
         this.R = 0;
         this.S = 0;
         this.T = 0;
         this.U = Collections.emptyList();
         this.V = Collections.emptyList();
         this.W = 0;
         this.X = 0;
         this.Y = 0;
         this.Z = 0;
         this.aa = 0;
         this.ab = 0;
         this.ac = 0;
         this.ad = 0;
         this.ae = 0;
         this.af = 0;
         this.ag = 0;
         this.ah = 0;
         this.ai = 0;
         this.aj = 0;
         this.ak = 0;
         this.al = 0;
         this.am = 0;
         this.an = 0;
         this.ao = 0;
         this.ap = 0;
         this.aq = 0;
      }

      @Override
      public final boolean a() {
         byte var1 = this.ar;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.ar = 1;
         return true;
      }

      @Override
      public void a(a.i var1) throws IOException {
         this.d();
         if ((this.K & 1) == 1) {
            var1.a(1, this.L);
         }

         if ((this.K & 2) == 2) {
            var1.a(2, this.M);
         }

         if ((this.K & 4) == 4) {
            var1.a(3, this.N);
         }

         if ((this.K & 8) == 8) {
            var1.a(4, this.O);
         }

         if ((this.K & 16) == 16) {
            var1.a(5, this.P);
         }

         if ((this.K & 32) == 32) {
            var1.a(6, this.Q);
         }

         if ((this.K & 64) == 64) {
            var1.a(7, this.R);
         }

         if ((this.K & 128) == 128) {
            var1.a(8, this.S);
         }

         if ((this.K & 256) == 256) {
            var1.a(9, this.T);
         }

         for (int var2 = 0; var2 < this.U.size(); var2++) {
            var1.a(10, this.U.get(var2));
         }

         for (int var3 = 0; var3 < this.V.size(); var3++) {
            var1.a(11, this.V.get(var3));
         }

         if ((this.K & 512) == 512) {
            var1.a(12, this.W);
         }

         if ((this.K & 1024) == 1024) {
            var1.a(13, this.X);
         }

         if ((this.K & 2048) == 2048) {
            var1.a(14, this.Y);
         }

         if ((this.K & 4096) == 4096) {
            var1.a(15, this.Z);
         }

         if ((this.K & 8192) == 8192) {
            var1.a(16, this.aa);
         }

         if ((this.K & 16384) == 16384) {
            var1.a(17, this.ab);
         }

         if ((this.K & 32768) == 32768) {
            var1.a(18, this.ac);
         }

         if ((this.K & 65536) == 65536) {
            var1.a(19, this.ad);
         }

         if ((this.K & 131072) == 131072) {
            var1.a(20, this.ae);
         }

         if ((this.K & 262144) == 262144) {
            var1.a(21, this.af);
         }

         if ((this.K & 524288) == 524288) {
            var1.a(22, this.ag);
         }

         if ((this.K & 1048576) == 1048576) {
            var1.a(23, this.ah);
         }

         if ((this.K & 2097152) == 2097152) {
            var1.a(24, this.ai);
         }

         if ((this.K & 4194304) == 4194304) {
            var1.a(25, this.aj);
         }

         if ((this.K & 8388608) == 8388608) {
            var1.a(26, this.ak);
         }

         if ((this.K & 16777216) == 16777216) {
            var1.a(27, this.al);
         }

         if ((this.K & 33554432) == 33554432) {
            var1.a(28, this.am);
         }

         if ((this.K & 67108864) == 67108864) {
            var1.a(29, this.an);
         }

         if ((this.K & 134217728) == 134217728) {
            var1.a(30, this.ao);
         }

         if ((this.K & 268435456) == 268435456) {
            var1.a(31, this.ap);
         }

         if ((this.K & 536870912) == 536870912) {
            var1.a(32, this.aq);
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.as;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.K & 1) == 1) {
            var1 += a.i.g(1, this.L);
         }

         if ((this.K & 2) == 2) {
            var1 += a.i.g(2, this.M);
         }

         if ((this.K & 4) == 4) {
            var1 += a.i.g(3, this.N);
         }

         if ((this.K & 8) == 8) {
            var1 += a.i.c(4, this.O);
         }

         if ((this.K & 16) == 16) {
            var1 += a.i.c(5, this.P);
         }

         if ((this.K & 32) == 32) {
            var1 += a.i.g(6, this.Q);
         }

         if ((this.K & 64) == 64) {
            var1 += a.i.g(7, this.R);
         }

         if ((this.K & 128) == 128) {
            var1 += a.i.g(8, this.S);
         }

         if ((this.K & 256) == 256) {
            var1 += a.i.g(9, this.T);
         }

         int var2 = 0;

         for (int var3 = 0; var3 < this.U.size(); var3++) {
            var2 += a.i.h(this.U.get(var3));
         }

         var1 += var2;
         var1 += 1 * this.G().size();
         var2 = 0;

         for (int var11 = 0; var11 < this.V.size(); var11++) {
            var2 += a.i.b(this.V.get(var11));
         }

         var1 += var2;
         var1 += 1 * this.K().size();
         if ((this.K & 512) == 512) {
            var1 += a.i.g(12, this.W);
         }

         if ((this.K & 1024) == 1024) {
            var1 += a.i.g(13, this.X);
         }

         if ((this.K & 2048) == 2048) {
            var1 += a.i.g(14, this.Y);
         }

         if ((this.K & 4096) == 4096) {
            var1 += a.i.g(15, this.Z);
         }

         if ((this.K & 8192) == 8192) {
            var1 += a.i.g(16, this.aa);
         }

         if ((this.K & 16384) == 16384) {
            var1 += a.i.g(17, this.ab);
         }

         if ((this.K & 32768) == 32768) {
            var1 += a.i.g(18, this.ac);
         }

         if ((this.K & 65536) == 65536) {
            var1 += a.i.g(19, this.ad);
         }

         if ((this.K & 131072) == 131072) {
            var1 += a.i.g(20, this.ae);
         }

         if ((this.K & 262144) == 262144) {
            var1 += a.i.g(21, this.af);
         }

         if ((this.K & 524288) == 524288) {
            var1 += a.i.g(22, this.ag);
         }

         if ((this.K & 1048576) == 1048576) {
            var1 += a.i.g(23, this.ah);
         }

         if ((this.K & 2097152) == 2097152) {
            var1 += a.i.g(24, this.ai);
         }

         if ((this.K & 4194304) == 4194304) {
            var1 += a.i.g(25, this.aj);
         }

         if ((this.K & 8388608) == 8388608) {
            var1 += a.i.g(26, this.ak);
         }

         if ((this.K & 16777216) == 16777216) {
            var1 += a.i.g(27, this.al);
         }

         if ((this.K & 33554432) == 33554432) {
            var1 += a.i.g(28, this.am);
         }

         if ((this.K & 67108864) == 67108864) {
            var1 += a.i.g(29, this.an);
         }

         if ((this.K & 134217728) == 134217728) {
            var1 += a.i.g(30, this.ao);
         }

         if ((this.K & 268435456) == 268435456) {
            var1 += a.i.g(31, this.ap);
         }

         if ((this.K & 536870912) == 536870912) {
            var1 += a.i.g(32, this.aq);
         }

         var1 += this.b_().d();
         this.as = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static an.b.g a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.b.g a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.b.g a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.b.g a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.b.g a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.b.g a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.b.g b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.b.g b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.b.g a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.b.g a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.b.g.a aK() {
         return an.b.g.a.bo();
      }

      public an.b.g.a aL() {
         return aK();
      }

      public static an.b.g.a a(an.b.g var0) {
         return aK().a(var0);
      }

      public an.b.g.a aM() {
         return a(this);
      }

      protected an.b.g.a a(a.p.b var1) {
         return new an.b.g.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.aM();
      }

      // $VF: synthetic method
      @Override
      public a.x.a M() {
         return this.aM();
      }

      // $VF: synthetic method
      @Override
      protected a.x.a b(a.p.b var1) {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public a.y.a P() {
         return this.aL();
      }

      // $VF: synthetic method
      @Override
      public a.x.a N() {
         return this.aL();
      }

      // $VF: synthetic method
      @Override
      public y Q() {
         return this.k();
      }

      // $VF: synthetic method
      @Override
      public x R() {
         return this.k();
      }

      // $VF: synthetic method
      g(a.h var1, n var2, an.b.g var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      g(a.p.a var1, an.b.g var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.b.g.a> implements an.b.h {
         private int a;
         private int b;
         private int c;
         private int d;
         private a.g e = a.g.d;
         private a.g f = a.g.d;
         private int g;
         private int h;
         private int i;
         private int j;
         private List<Integer> k = Collections.emptyList();
         private List<a.g> l = Collections.emptyList();
         private int m;
         private int n;
         private int o;
         private int p;
         private int q;
         private int r;
         private int s;
         private int t;
         private int u;
         private int v;
         private int w;
         private int x;
         private int y;
         private int z;
         private int A;
         private int B;
         private int C;
         private int D;
         private int E;
         private int F;
         private int G;

         public static final a.k.a k() {
            return an.b.g;
         }

         @Override
         protected a.p.g l() {
            return an.b.h.a(an.b.g.class, an.b.g.a.class);
         }

         private a() {
            this.bn();
         }

         private a(a.p.b var1) {
            super(var1);
            this.bn();
         }

         private void bn() {
            an.b.g.m;
         }

         private static an.b.g.a bo() {
            return new an.b.g.a();
         }

         public an.b.g.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = 0;
            this.a &= -3;
            this.d = 0;
            this.a &= -5;
            this.e = a.g.d;
            this.a &= -9;
            this.f = a.g.d;
            this.a &= -17;
            this.g = 0;
            this.a &= -33;
            this.h = 0;
            this.a &= -65;
            this.i = 0;
            this.a &= -129;
            this.j = 0;
            this.a &= -257;
            this.k = Collections.emptyList();
            this.a &= -513;
            this.l = Collections.emptyList();
            this.a &= -1025;
            this.m = 0;
            this.a &= -2049;
            this.n = 0;
            this.a &= -4097;
            this.o = 0;
            this.a &= -8193;
            this.p = 0;
            this.a &= -16385;
            this.q = 0;
            this.a &= -32769;
            this.r = 0;
            this.a &= -65537;
            this.s = 0;
            this.a &= -131073;
            this.t = 0;
            this.a &= -262145;
            this.u = 0;
            this.a &= -524289;
            this.v = 0;
            this.a &= -1048577;
            this.w = 0;
            this.a &= -2097153;
            this.x = 0;
            this.a &= -4194305;
            this.y = 0;
            this.a &= -8388609;
            this.z = 0;
            this.a &= -16777217;
            this.A = 0;
            this.a &= -33554433;
            this.B = 0;
            this.a &= -67108865;
            this.C = 0;
            this.a &= -134217729;
            this.D = 0;
            this.a &= -268435457;
            this.E = 0;
            this.a &= -536870913;
            this.F = 0;
            this.a &= -1073741825;
            this.G = 0;
            this.a &= Integer.MAX_VALUE;
            return this;
         }

         public an.b.g.a n() {
            return bo().a(this.N());
         }

         @Override
         public a.k.a J() {
            return an.b.g;
         }

         public an.b.g I() {
            return an.b.g.h();
         }

         public an.b.g M() {
            an.b.g var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.b.g N() {
            an.b.g var1 = new an.b.g(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((var2 & 1) == 1) {
               var3 |= 1;
            }

            var1.L = this.b;
            if ((var2 & 2) == 2) {
               var3 |= 2;
            }

            var1.M = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 4;
            }

            var1.N = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 8;
            }

            var1.O = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 16;
            }

            var1.P = this.f;
            if ((var2 & 32) == 32) {
               var3 |= 32;
            }

            var1.Q = this.g;
            if ((var2 & 64) == 64) {
               var3 |= 64;
            }

            var1.R = this.h;
            if ((var2 & 128) == 128) {
               var3 |= 128;
            }

            var1.S = this.i;
            if ((var2 & 256) == 256) {
               var3 |= 256;
            }

            var1.T = this.j;
            if ((this.a & 512) == 512) {
               this.k = Collections.unmodifiableList(this.k);
               this.a &= -513;
            }

            var1.U = this.k;
            if ((this.a & 1024) == 1024) {
               this.l = Collections.unmodifiableList(this.l);
               this.a &= -1025;
            }

            var1.V = this.l;
            if ((var2 & 2048) == 2048) {
               var3 |= 512;
            }

            var1.W = this.m;
            if ((var2 & 4096) == 4096) {
               var3 |= 1024;
            }

            var1.X = this.n;
            if ((var2 & 8192) == 8192) {
               var3 |= 2048;
            }

            var1.Y = this.o;
            if ((var2 & 16384) == 16384) {
               var3 |= 4096;
            }

            var1.Z = this.p;
            if ((var2 & 32768) == 32768) {
               var3 |= 8192;
            }

            var1.aa = this.q;
            if ((var2 & 65536) == 65536) {
               var3 |= 16384;
            }

            var1.ab = this.r;
            if ((var2 & 131072) == 131072) {
               var3 |= 32768;
            }

            var1.ac = this.s;
            if ((var2 & 262144) == 262144) {
               var3 |= 65536;
            }

            var1.ad = this.t;
            if ((var2 & 524288) == 524288) {
               var3 |= 131072;
            }

            var1.ae = this.u;
            if ((var2 & 1048576) == 1048576) {
               var3 |= 262144;
            }

            var1.af = this.v;
            if ((var2 & 2097152) == 2097152) {
               var3 |= 524288;
            }

            var1.ag = this.w;
            if ((var2 & 4194304) == 4194304) {
               var3 |= 1048576;
            }

            var1.ah = this.x;
            if ((var2 & 8388608) == 8388608) {
               var3 |= 2097152;
            }

            var1.ai = this.y;
            if ((var2 & 16777216) == 16777216) {
               var3 |= 4194304;
            }

            var1.aj = this.z;
            if ((var2 & 33554432) == 33554432) {
               var3 |= 8388608;
            }

            var1.ak = this.A;
            if ((var2 & 67108864) == 67108864) {
               var3 |= 16777216;
            }

            var1.al = this.B;
            if ((var2 & 134217728) == 134217728) {
               var3 |= 33554432;
            }

            var1.am = this.C;
            if ((var2 & 268435456) == 268435456) {
               var3 |= 67108864;
            }

            var1.an = this.D;
            if ((var2 & 536870912) == 536870912) {
               var3 |= 134217728;
            }

            var1.ao = this.E;
            if ((var2 & 1073741824) == 1073741824) {
               var3 |= 268435456;
            }

            var1.ap = this.F;
            if ((var2 & -2147483648) == Integer.MIN_VALUE) {
               var3 |= 536870912;
            }

            var1.aq = this.G;
            var1.K = var3;
            this.q_();
            return var1;
         }

         public an.b.g.a d(x var1) {
            if (var1 instanceof an.b.g) {
               return this.a((an.b.g)var1);
            }

            super.a(var1);
            return this;
         }

         public an.b.g.a a(an.b.g var1) {
            if (var1 == an.b.g.h()) {
               return this;
            }

            if (var1.o()) {
               this.c(var1.p());
            }

            if (var1.q()) {
               this.d(var1.r());
            }

            if (var1.s()) {
               this.e(var1.t());
            }

            if (var1.u()) {
               this.e(var1.v());
            }

            if (var1.w()) {
               this.f(var1.x());
            }

            if (var1.y()) {
               this.f(var1.z());
            }

            if (var1.A()) {
               this.g(var1.B());
            }

            if (var1.C()) {
               this.h(var1.D());
            }

            if (var1.E()) {
               this.i(var1.F());
            }

            if (!var1.U.isEmpty()) {
               if (this.k.isEmpty()) {
                  this.k = var1.U;
                  this.a &= -513;
               } else {
                  this.bp();
                  this.k.addAll(var1.U);
               }

               this.t_();
            }

            if (!var1.V.isEmpty()) {
               if (this.l.isEmpty()) {
                  this.l = var1.V;
                  this.a &= -1025;
               } else {
                  this.bq();
                  this.l.addAll(var1.V);
               }

               this.t_();
            }

            if (var1.S()) {
               this.k(var1.T());
            }

            if (var1.U()) {
               this.l(var1.V());
            }

            if (var1.W()) {
               this.m(var1.X());
            }

            if (var1.Y()) {
               this.n(var1.Z());
            }

            if (var1.aa()) {
               this.o(var1.ab());
            }

            if (var1.ae()) {
               this.p(var1.af());
            }

            if (var1.ag()) {
               this.q(var1.u_());
            }

            if (var1.v_()) {
               this.r(var1.w_());
            }

            if (var1.x_()) {
               this.s(var1.y_());
            }

            if (var1.z_()) {
               this.t(var1.an());
            }

            if (var1.ao()) {
               this.u(var1.ap());
            }

            if (var1.aq()) {
               this.v(var1.ar());
            }

            if (var1.as()) {
               this.w(var1.at());
            }

            if (var1.au()) {
               this.x(var1.av());
            }

            if (var1.aw()) {
               this.y(var1.ax());
            }

            if (var1.ay()) {
               this.z(var1.az());
            }

            if (var1.A_()) {
               this.A(var1.aB());
            }

            if (var1.aC()) {
               this.B(var1.aD());
            }

            if (var1.B_()) {
               this.C(var1.aF());
            }

            if (var1.aG()) {
               this.D(var1.aH());
            }

            if (var1.aI()) {
               this.E(var1.aJ());
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public an.b.g.a e(a.h var1, n var2) throws IOException {
            an.b.g var3 = null;

            try {
               var3 = an.b.g.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.b.g)var8.a();
               throw var8;
            } finally {
               if (var3 != null) {
                  this.a(var3);
               }
            }

            return this;
         }

         @Override
         public boolean o() {
            return (this.a & 1) == 1;
         }

         @Override
         public int p() {
            return this.b;
         }

         public an.b.g.a c(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public an.b.g.a O() {
            this.a &= -2;
            this.b = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean q() {
            return (this.a & 2) == 2;
         }

         @Override
         public int r() {
            return this.c;
         }

         public an.b.g.a d(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public an.b.g.a P() {
            this.a &= -3;
            this.c = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean s() {
            return (this.a & 4) == 4;
         }

         @Override
         public int t() {
            return this.d;
         }

         public an.b.g.a e(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.b.g.a ac() {
            this.a &= -5;
            this.d = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean u() {
            return (this.a & 8) == 8;
         }

         @Override
         public a.g v() {
            return this.e;
         }

         public an.b.g.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.b.g.a ad() {
            this.a &= -9;
            this.e = an.b.g.h().v();
            this.t_();
            return this;
         }

         @Override
         public boolean w() {
            return (this.a & 16) == 16;
         }

         @Override
         public a.g x() {
            return this.f;
         }

         public an.b.g.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aK() {
            this.a &= -17;
            this.f = an.b.g.h().x();
            this.t_();
            return this;
         }

         @Override
         public boolean y() {
            return (this.a & 32) == 32;
         }

         @Override
         public int z() {
            return this.g;
         }

         public an.b.g.a f(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aL() {
            this.a &= -33;
            this.g = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean A() {
            return (this.a & 64) == 64;
         }

         @Override
         public int B() {
            return this.h;
         }

         public an.b.g.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aM() {
            this.a &= -65;
            this.h = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean C() {
            return (this.a & 128) == 128;
         }

         @Override
         public int D() {
            return this.i;
         }

         public an.b.g.a h(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aN() {
            this.a &= -129;
            this.i = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean E() {
            return (this.a & 256) == 256;
         }

         @Override
         public int F() {
            return this.j;
         }

         public an.b.g.a i(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aO() {
            this.a &= -257;
            this.j = 0;
            this.t_();
            return this;
         }

         private void bp() {
            if ((this.a & 512) != 512) {
               this.k = new ArrayList<>(this.k);
               this.a |= 512;
            }
         }

         @Override
         public List<Integer> G() {
            return Collections.unmodifiableList(this.k);
         }

         @Override
         public int H() {
            return this.k.size();
         }

         @Override
         public int a(int var1) {
            return this.k.get(var1);
         }

         public an.b.g.a a(int var1, int var2) {
            this.bp();
            this.k.set(var1, var2);
            this.t_();
            return this;
         }

         public an.b.g.a j(int var1) {
            this.bp();
            this.k.add(var1);
            this.t_();
            return this;
         }

         public an.b.g.a a(Iterable<? extends Integer> var1) {
            this.bp();
            a.p.a.a(var1, this.k);
            this.t_();
            return this;
         }

         public an.b.g.a aP() {
            this.k = Collections.emptyList();
            this.a &= -513;
            this.t_();
            return this;
         }

         private void bq() {
            if ((this.a & 1024) != 1024) {
               this.l = new ArrayList<>(this.l);
               this.a |= 1024;
            }
         }

         @Override
         public List<a.g> K() {
            return Collections.unmodifiableList(this.l);
         }

         @Override
         public int L() {
            return this.l.size();
         }

         @Override
         public a.g b(int var1) {
            return this.l.get(var1);
         }

         public an.b.g.a a(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.bq();
            this.l.set(var1, var2);
            this.t_();
            return this;
         }

         public an.b.g.a g(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.bq();
            this.l.add(var1);
            this.t_();
            return this;
         }

         public an.b.g.a b(Iterable<? extends a.g> var1) {
            this.bq();
            a.p.a.a(var1, this.l);
            this.t_();
            return this;
         }

         public an.b.g.a aQ() {
            this.l = Collections.emptyList();
            this.a &= -1025;
            this.t_();
            return this;
         }

         @Override
         public boolean S() {
            return (this.a & 2048) == 2048;
         }

         @Override
         public int T() {
            return this.m;
         }

         public an.b.g.a k(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aR() {
            this.a &= -2049;
            this.m = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean U() {
            return (this.a & 4096) == 4096;
         }

         @Override
         public int V() {
            return this.n;
         }

         public an.b.g.a l(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aS() {
            this.a &= -4097;
            this.n = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean W() {
            return (this.a & 8192) == 8192;
         }

         @Override
         public int X() {
            return this.o;
         }

         public an.b.g.a m(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aT() {
            this.a &= -8193;
            this.o = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean Y() {
            return (this.a & 16384) == 16384;
         }

         @Override
         public int Z() {
            return this.p;
         }

         public an.b.g.a n(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aU() {
            this.a &= -16385;
            this.p = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aa() {
            return (this.a & 32768) == 32768;
         }

         @Override
         public int ab() {
            return this.q;
         }

         public an.b.g.a o(int var1) {
            this.a |= 32768;
            this.q = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aV() {
            this.a &= -32769;
            this.q = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean ae() {
            return (this.a & 65536) == 65536;
         }

         @Override
         public int af() {
            return this.r;
         }

         public an.b.g.a p(int var1) {
            this.a |= 65536;
            this.r = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aW() {
            this.a &= -65537;
            this.r = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean ag() {
            return (this.a & 131072) == 131072;
         }

         @Override
         public int u_() {
            return this.s;
         }

         public an.b.g.a q(int var1) {
            this.a |= 131072;
            this.s = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aX() {
            this.a &= -131073;
            this.s = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean v_() {
            return (this.a & 262144) == 262144;
         }

         @Override
         public int w_() {
            return this.t;
         }

         public an.b.g.a r(int var1) {
            this.a |= 262144;
            this.t = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aY() {
            this.a &= -262145;
            this.t = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean x_() {
            return (this.a & 524288) == 524288;
         }

         @Override
         public int y_() {
            return this.u;
         }

         public an.b.g.a s(int var1) {
            this.a |= 524288;
            this.u = var1;
            this.t_();
            return this;
         }

         public an.b.g.a aZ() {
            this.a &= -524289;
            this.u = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean z_() {
            return (this.a & 1048576) == 1048576;
         }

         @Override
         public int an() {
            return this.v;
         }

         public an.b.g.a t(int var1) {
            this.a |= 1048576;
            this.v = var1;
            this.t_();
            return this;
         }

         public an.b.g.a ba() {
            this.a &= -1048577;
            this.v = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean ao() {
            return (this.a & 2097152) == 2097152;
         }

         @Override
         public int ap() {
            return this.w;
         }

         public an.b.g.a u(int var1) {
            this.a |= 2097152;
            this.w = var1;
            this.t_();
            return this;
         }

         public an.b.g.a bb() {
            this.a &= -2097153;
            this.w = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aq() {
            return (this.a & 4194304) == 4194304;
         }

         @Override
         public int ar() {
            return this.x;
         }

         public an.b.g.a v(int var1) {
            this.a |= 4194304;
            this.x = var1;
            this.t_();
            return this;
         }

         public an.b.g.a bc() {
            this.a &= -4194305;
            this.x = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean as() {
            return (this.a & 8388608) == 8388608;
         }

         @Override
         public int at() {
            return this.y;
         }

         public an.b.g.a w(int var1) {
            this.a |= 8388608;
            this.y = var1;
            this.t_();
            return this;
         }

         public an.b.g.a bd() {
            this.a &= -8388609;
            this.y = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean au() {
            return (this.a & 16777216) == 16777216;
         }

         @Override
         public int av() {
            return this.z;
         }

         public an.b.g.a x(int var1) {
            this.a |= 16777216;
            this.z = var1;
            this.t_();
            return this;
         }

         public an.b.g.a be() {
            this.a &= -16777217;
            this.z = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aw() {
            return (this.a & 33554432) == 33554432;
         }

         @Override
         public int ax() {
            return this.A;
         }

         public an.b.g.a y(int var1) {
            this.a |= 33554432;
            this.A = var1;
            this.t_();
            return this;
         }

         public an.b.g.a bf() {
            this.a &= -33554433;
            this.A = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean ay() {
            return (this.a & 67108864) == 67108864;
         }

         @Override
         public int az() {
            return this.B;
         }

         public an.b.g.a z(int var1) {
            this.a |= 67108864;
            this.B = var1;
            this.t_();
            return this;
         }

         public an.b.g.a bg() {
            this.a &= -67108865;
            this.B = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean A_() {
            return (this.a & 134217728) == 134217728;
         }

         @Override
         public int aB() {
            return this.C;
         }

         public an.b.g.a A(int var1) {
            this.a |= 134217728;
            this.C = var1;
            this.t_();
            return this;
         }

         public an.b.g.a bh() {
            this.a &= -134217729;
            this.C = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aC() {
            return (this.a & 268435456) == 268435456;
         }

         @Override
         public int aD() {
            return this.D;
         }

         public an.b.g.a B(int var1) {
            this.a |= 268435456;
            this.D = var1;
            this.t_();
            return this;
         }

         public an.b.g.a bi() {
            this.a &= -268435457;
            this.D = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean B_() {
            return (this.a & 536870912) == 536870912;
         }

         @Override
         public int aF() {
            return this.E;
         }

         public an.b.g.a C(int var1) {
            this.a |= 536870912;
            this.E = var1;
            this.t_();
            return this;
         }

         public an.b.g.a bj() {
            this.a &= -536870913;
            this.E = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aG() {
            return (this.a & 1073741824) == 1073741824;
         }

         @Override
         public int aH() {
            return this.F;
         }

         public an.b.g.a D(int var1) {
            this.a |= 1073741824;
            this.F = var1;
            this.t_();
            return this;
         }

         public an.b.g.a bk() {
            this.a &= -1073741825;
            this.F = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aI() {
            return (this.a & -2147483648) == Integer.MIN_VALUE;
         }

         @Override
         public int aJ() {
            return this.G;
         }

         public an.b.g.a E(int var1) {
            this.a |= Integer.MIN_VALUE;
            this.G = var1;
            this.t_();
            return this;
         }

         public an.b.g.a bl() {
            this.a &= Integer.MAX_VALUE;
            this.G = 0;
            this.t_();
            return this;
         }

         // $VF: synthetic method
         @Override
         public a.y.a h() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public a.x.a j() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public a.p.a ah() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public a.y.a c(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.x.a d(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.x.a c(x var1) {
            return this.d(var1);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(x var1) {
            return this.d(var1);
         }

         // $VF: synthetic method
         @Override
         public a.y.a g() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public a.x.a i() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public a.p.a ai() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public y am() {
            return this.M();
         }

         // $VF: synthetic method
         @Override
         public x ak() {
            return this.M();
         }

         // $VF: synthetic method
         @Override
         public y Q() {
            return this.I();
         }

         // $VF: synthetic method
         @Override
         public x R() {
            return this.I();
         }

         // $VF: synthetic method
         @Override
         public y al() {
            return this.N();
         }

         // $VF: synthetic method
         @Override
         public x aj() {
            return this.N();
         }

         // $VF: synthetic method
         a(a.p.b var1, an.b.g.a var2) {
            this(var1);
         }
      }
   }

   public interface h extends aa {
      boolean o();

      int p();

      boolean q();

      int r();

      boolean s();

      int t();

      boolean u();

      a.g v();

      boolean w();

      a.g x();

      boolean y();

      int z();

      boolean A();

      int B();

      boolean C();

      int D();

      boolean E();

      int F();

      List<Integer> G();

      int H();

      int a(int var1);

      List<a.g> K();

      int L();

      a.g b(int var1);

      boolean S();

      int T();

      boolean U();

      int V();

      boolean W();

      int X();

      boolean Y();

      int Z();

      boolean aa();

      int ab();

      boolean ae();

      int af();

      boolean ag();

      int u_();

      boolean v_();

      int w_();

      boolean x_();

      int y_();

      boolean z_();

      int an();

      boolean ao();

      int ap();

      boolean aq();

      int ar();

      boolean as();

      int at();

      boolean au();

      int av();

      boolean aw();

      int ax();

      boolean ay();

      int az();

      boolean A_();

      int aB();

      boolean aC();

      int aD();

      boolean B_();

      int aF();

      boolean aG();

      int aH();

      boolean aI();

      int aJ();
   }

   public static final class i extends p implements an.b.j {
      private static final an.b.i x = new an.b.i(true);
      private final ap y;
      public static ab<an.b.i> a = new a.c<an.b.i>() {
         public an.b.i c(a.h var1, n var2) throws s {
            return new an.b.i(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int z;
      public static final int b = 1;
      private int A;
      public static final int c = 2;
      private int B;
      public static final int d = 3;
      private a.g C;
      public static final int e = 4;
      private int D;
      public static final int f = 5;
      private int E;
      public static final int g = 6;
      private int F;
      public static final int h = 7;
      private int G;
      public static final int i = 8;
      private int H;
      public static final int j = 9;
      private int I;
      public static final int k = 10;
      private int J;
      public static final int l = 11;
      private int K;
      public static final int n = 12;
      private int L;
      public static final int o = 13;
      private int M;
      public static final int p = 14;
      private int N;
      public static final int q = 15;
      private int O;
      public static final int r = 16;
      private int P;
      public static final int s = 17;
      private int Q;
      public static final int t = 18;
      private a.g R;
      public static final int u = 19;
      private int S;
      public static final int v = 20;
      private int T;
      public static final int w = 21;
      private List<a.g> U;
      private byte V = -1;
      private int W = -1;
      private static final long X = 0L;

      static {
         x.as();
      }

      private i(a.p.a<?> var1) {
         super(var1);
         this.y = var1.b_();
      }

      private i(boolean var1) {
         this.y = ap.c();
      }

      public static an.b.i h() {
         return x;
      }

      public an.b.i k() {
         return x;
      }

      @Override
      public final ap b_() {
         return this.y;
      }

      private i(a.h var1, n var2) throws s {
         this.as();
         int var3 = 0;
         a.ap.a var4 = ap.b();

         try {
            boolean var5 = false;

            while (!var5) {
               int var6 = var1.a();
               switch (var6) {
                  case 0:
                     var5 = true;
                     break;
                  case 8:
                     this.z |= 1;
                     this.A = var1.g();
                     break;
                  case 16:
                     this.z |= 2;
                     this.B = var1.g();
                     break;
                  case 26:
                     this.z |= 4;
                     this.C = var1.l();
                     break;
                  case 32:
                     this.z |= 8;
                     this.D = var1.g();
                     break;
                  case 40:
                     this.z |= 16;
                     this.E = var1.g();
                     break;
                  case 48:
                     this.z |= 32;
                     this.F = var1.g();
                     break;
                  case 56:
                     this.z |= 64;
                     this.G = var1.g();
                     break;
                  case 64:
                     this.z |= 128;
                     this.H = var1.g();
                     break;
                  case 72:
                     this.z |= 256;
                     this.I = var1.g();
                     break;
                  case 80:
                     this.z |= 512;
                     this.J = var1.g();
                     break;
                  case 88:
                     this.z |= 1024;
                     this.K = var1.g();
                     break;
                  case 96:
                     this.z |= 2048;
                     this.L = var1.g();
                     break;
                  case 104:
                     this.z |= 4096;
                     this.M = var1.g();
                     break;
                  case 112:
                     this.z |= 8192;
                     this.N = var1.g();
                     break;
                  case 120:
                     this.z |= 16384;
                     this.O = var1.g();
                     break;
                  case 128:
                     this.z |= 32768;
                     this.P = var1.g();
                     break;
                  case 136:
                     this.z |= 65536;
                     this.Q = var1.g();
                     break;
                  case 146:
                     this.z |= 131072;
                     this.R = var1.l();
                     break;
                  case 152:
                     this.z |= 262144;
                     this.S = var1.g();
                     break;
                  case 160:
                     this.z |= 524288;
                     this.T = var1.g();
                     break;
                  case 170:
                     if ((var3 & 1048576) != 1048576) {
                        this.U = new ArrayList<>();
                        var3 |= 1048576;
                     }

                     this.U.add(var1.l());
                     break;
                  default:
                     if (!this.a(var1, var4, var2, var6)) {
                        var5 = true;
                     }
               }
            }
         } catch (s var11) {
            throw var11.a(this);
         } catch (IOException var12) {
            throw new s(var12.getMessage()).a(this);
         } finally {
            if ((var3 & 1048576) == 1048576) {
               this.U = Collections.unmodifiableList(this.U);
            }

            this.y = var4.b();
            this.ad();
         }
      }

      public static final a.k.a n() {
         return an.b.i;
      }

      @Override
      protected a.p.g l() {
         return an.b.j.a(an.b.i.class, an.b.i.a.class);
      }

      @Override
      public ab<an.b.i> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.z & 1) == 1;
      }

      @Override
      public int p() {
         return this.A;
      }

      @Override
      public boolean q() {
         return (this.z & 2) == 2;
      }

      @Override
      public int r() {
         return this.B;
      }

      @Override
      public boolean s() {
         return (this.z & 4) == 4;
      }

      @Override
      public a.g t() {
         return this.C;
      }

      @Override
      public boolean u() {
         return (this.z & 8) == 8;
      }

      @Override
      public int v() {
         return this.D;
      }

      @Override
      public boolean w() {
         return (this.z & 16) == 16;
      }

      @Override
      public int x() {
         return this.E;
      }

      @Override
      public boolean y() {
         return (this.z & 32) == 32;
      }

      @Override
      public int z() {
         return this.F;
      }

      @Override
      public boolean A() {
         return (this.z & 64) == 64;
      }

      @Override
      public int B() {
         return this.G;
      }

      @Override
      public boolean C() {
         return (this.z & 128) == 128;
      }

      @Override
      public int D() {
         return this.H;
      }

      @Override
      public boolean E() {
         return (this.z & 256) == 256;
      }

      @Override
      public int F() {
         return this.I;
      }

      @Override
      public boolean G() {
         return (this.z & 512) == 512;
      }

      @Override
      public int H() {
         return this.J;
      }

      @Override
      public boolean K() {
         return (this.z & 1024) == 1024;
      }

      @Override
      public int L() {
         return this.K;
      }

      @Override
      public boolean S() {
         return (this.z & 2048) == 2048;
      }

      @Override
      public int T() {
         return this.L;
      }

      @Override
      public boolean U() {
         return (this.z & 4096) == 4096;
      }

      @Override
      public int V() {
         return this.M;
      }

      @Override
      public boolean W() {
         return (this.z & 8192) == 8192;
      }

      @Override
      public int X() {
         return this.N;
      }

      @Override
      public boolean Y() {
         return (this.z & 16384) == 16384;
      }

      @Override
      public int Z() {
         return this.O;
      }

      @Override
      public boolean aa() {
         return (this.z & 32768) == 32768;
      }

      @Override
      public int ab() {
         return this.P;
      }

      @Override
      public boolean ae() {
         return (this.z & 65536) == 65536;
      }

      @Override
      public int af() {
         return this.Q;
      }

      @Override
      public boolean ag() {
         return (this.z & 131072) == 131072;
      }

      @Override
      public a.g C_() {
         return this.R;
      }

      @Override
      public boolean D_() {
         return (this.z & 262144) == 262144;
      }

      @Override
      public int E_() {
         return this.S;
      }

      @Override
      public boolean F_() {
         return (this.z & 524288) == 524288;
      }

      @Override
      public int G_() {
         return this.T;
      }

      @Override
      public List<a.g> H_() {
         return this.U;
      }

      @Override
      public int an() {
         return this.U.size();
      }

      @Override
      public a.g a(int var1) {
         return this.U.get(var1);
      }

      private void as() {
         this.A = 0;
         this.B = 0;
         this.C = a.g.d;
         this.D = 0;
         this.E = 0;
         this.F = 0;
         this.G = 0;
         this.H = 0;
         this.I = 0;
         this.J = 0;
         this.K = 0;
         this.L = 0;
         this.M = 0;
         this.N = 0;
         this.O = 0;
         this.P = 0;
         this.Q = 0;
         this.R = a.g.d;
         this.S = 0;
         this.T = 0;
         this.U = Collections.emptyList();
      }

      @Override
      public final boolean a() {
         byte var1 = this.V;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.V = 1;
         return true;
      }

      @Override
      public void a(a.i var1) throws IOException {
         this.d();
         if ((this.z & 1) == 1) {
            var1.a(1, this.A);
         }

         if ((this.z & 2) == 2) {
            var1.a(2, this.B);
         }

         if ((this.z & 4) == 4) {
            var1.a(3, this.C);
         }

         if ((this.z & 8) == 8) {
            var1.a(4, this.D);
         }

         if ((this.z & 16) == 16) {
            var1.a(5, this.E);
         }

         if ((this.z & 32) == 32) {
            var1.a(6, this.F);
         }

         if ((this.z & 64) == 64) {
            var1.a(7, this.G);
         }

         if ((this.z & 128) == 128) {
            var1.a(8, this.H);
         }

         if ((this.z & 256) == 256) {
            var1.a(9, this.I);
         }

         if ((this.z & 512) == 512) {
            var1.a(10, this.J);
         }

         if ((this.z & 1024) == 1024) {
            var1.a(11, this.K);
         }

         if ((this.z & 2048) == 2048) {
            var1.a(12, this.L);
         }

         if ((this.z & 4096) == 4096) {
            var1.a(13, this.M);
         }

         if ((this.z & 8192) == 8192) {
            var1.a(14, this.N);
         }

         if ((this.z & 16384) == 16384) {
            var1.a(15, this.O);
         }

         if ((this.z & 32768) == 32768) {
            var1.a(16, this.P);
         }

         if ((this.z & 65536) == 65536) {
            var1.a(17, this.Q);
         }

         if ((this.z & 131072) == 131072) {
            var1.a(18, this.R);
         }

         if ((this.z & 262144) == 262144) {
            var1.a(19, this.S);
         }

         if ((this.z & 524288) == 524288) {
            var1.a(20, this.T);
         }

         for (int var2 = 0; var2 < this.U.size(); var2++) {
            var1.a(21, this.U.get(var2));
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.W;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.z & 1) == 1) {
            var1 += a.i.g(1, this.A);
         }

         if ((this.z & 2) == 2) {
            var1 += a.i.g(2, this.B);
         }

         if ((this.z & 4) == 4) {
            var1 += a.i.c(3, this.C);
         }

         if ((this.z & 8) == 8) {
            var1 += a.i.g(4, this.D);
         }

         if ((this.z & 16) == 16) {
            var1 += a.i.g(5, this.E);
         }

         if ((this.z & 32) == 32) {
            var1 += a.i.g(6, this.F);
         }

         if ((this.z & 64) == 64) {
            var1 += a.i.g(7, this.G);
         }

         if ((this.z & 128) == 128) {
            var1 += a.i.g(8, this.H);
         }

         if ((this.z & 256) == 256) {
            var1 += a.i.g(9, this.I);
         }

         if ((this.z & 512) == 512) {
            var1 += a.i.g(10, this.J);
         }

         if ((this.z & 1024) == 1024) {
            var1 += a.i.g(11, this.K);
         }

         if ((this.z & 2048) == 2048) {
            var1 += a.i.g(12, this.L);
         }

         if ((this.z & 4096) == 4096) {
            var1 += a.i.g(13, this.M);
         }

         if ((this.z & 8192) == 8192) {
            var1 += a.i.g(14, this.N);
         }

         if ((this.z & 16384) == 16384) {
            var1 += a.i.g(15, this.O);
         }

         if ((this.z & 32768) == 32768) {
            var1 += a.i.g(16, this.P);
         }

         if ((this.z & 65536) == 65536) {
            var1 += a.i.g(17, this.Q);
         }

         if ((this.z & 131072) == 131072) {
            var1 += a.i.c(18, this.R);
         }

         if ((this.z & 262144) == 262144) {
            var1 += a.i.g(19, this.S);
         }

         if ((this.z & 524288) == 524288) {
            var1 += a.i.g(20, this.T);
         }

         int var2 = 0;

         for (int var3 = 0; var3 < this.U.size(); var3++) {
            var2 += a.i.b(this.U.get(var3));
         }

         var1 += var2;
         var1 += 2 * this.H_().size();
         var1 += this.b_().d();
         this.W = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static an.b.i a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.b.i a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.b.i a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.b.i a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.b.i a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.b.i a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.b.i b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.b.i b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.b.i a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.b.i a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.b.i.a ao() {
         return an.b.i.a.aN();
      }

      public an.b.i.a ap() {
         return ao();
      }

      public static an.b.i.a a(an.b.i var0) {
         return ao().a(var0);
      }

      public an.b.i.a aq() {
         return a(this);
      }

      protected an.b.i.a a(a.p.b var1) {
         return new an.b.i.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.aq();
      }

      // $VF: synthetic method
      @Override
      public a.x.a M() {
         return this.aq();
      }

      // $VF: synthetic method
      @Override
      public y Q() {
         return this.k();
      }

      // $VF: synthetic method
      @Override
      public x R() {
         return this.k();
      }

      // $VF: synthetic method
      @Override
      protected a.x.a b(a.p.b var1) {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public a.y.a P() {
         return this.ap();
      }

      // $VF: synthetic method
      @Override
      public a.x.a N() {
         return this.ap();
      }

      // $VF: synthetic method
      i(a.h var1, n var2, an.b.i var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      i(a.p.a var1, an.b.i var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.b.i.a> implements an.b.j {
         private int a;
         private int b;
         private int c;
         private a.g d;
         private int e;
         private int f;
         private int g;
         private int h;
         private int i;
         private int j;
         private int k;
         private int l;
         private int m;
         private int n;
         private int o;
         private int p;
         private int q;
         private int r;
         private a.g s;
         private int t;
         private int u;
         private List<a.g> v;

         public static final a.k.a k() {
            return an.b.i;
         }

         @Override
         protected a.p.g l() {
            return an.b.j.a(an.b.i.class, an.b.i.a.class);
         }

         private a() {
            this.d = a.g.d;
            this.s = a.g.d;
            this.v = Collections.emptyList();
            this.aM();
         }

         private a(a.p.b var1) {
            super(var1);
            this.d = a.g.d;
            this.s = a.g.d;
            this.v = Collections.emptyList();
            this.aM();
         }

         private void aM() {
            an.b.i.m;
         }

         private static an.b.i.a aN() {
            return new an.b.i.a();
         }

         public an.b.i.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = 0;
            this.a &= -3;
            this.d = a.g.d;
            this.a &= -5;
            this.e = 0;
            this.a &= -9;
            this.f = 0;
            this.a &= -17;
            this.g = 0;
            this.a &= -33;
            this.h = 0;
            this.a &= -65;
            this.i = 0;
            this.a &= -129;
            this.j = 0;
            this.a &= -257;
            this.k = 0;
            this.a &= -513;
            this.l = 0;
            this.a &= -1025;
            this.m = 0;
            this.a &= -2049;
            this.n = 0;
            this.a &= -4097;
            this.o = 0;
            this.a &= -8193;
            this.p = 0;
            this.a &= -16385;
            this.q = 0;
            this.a &= -32769;
            this.r = 0;
            this.a &= -65537;
            this.s = a.g.d;
            this.a &= -131073;
            this.t = 0;
            this.a &= -262145;
            this.u = 0;
            this.a &= -524289;
            this.v = Collections.emptyList();
            this.a &= -1048577;
            return this;
         }

         public an.b.i.a n() {
            return aN().a(this.N());
         }

         @Override
         public a.k.a J() {
            return an.b.i;
         }

         public an.b.i I() {
            return an.b.i.h();
         }

         public an.b.i M() {
            an.b.i var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.b.i N() {
            an.b.i var1 = new an.b.i(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((var2 & 1) == 1) {
               var3 |= 1;
            }

            var1.A = this.b;
            if ((var2 & 2) == 2) {
               var3 |= 2;
            }

            var1.B = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 4;
            }

            var1.C = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 8;
            }

            var1.D = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 16;
            }

            var1.E = this.f;
            if ((var2 & 32) == 32) {
               var3 |= 32;
            }

            var1.F = this.g;
            if ((var2 & 64) == 64) {
               var3 |= 64;
            }

            var1.G = this.h;
            if ((var2 & 128) == 128) {
               var3 |= 128;
            }

            var1.H = this.i;
            if ((var2 & 256) == 256) {
               var3 |= 256;
            }

            var1.I = this.j;
            if ((var2 & 512) == 512) {
               var3 |= 512;
            }

            var1.J = this.k;
            if ((var2 & 1024) == 1024) {
               var3 |= 1024;
            }

            var1.K = this.l;
            if ((var2 & 2048) == 2048) {
               var3 |= 2048;
            }

            var1.L = this.m;
            if ((var2 & 4096) == 4096) {
               var3 |= 4096;
            }

            var1.M = this.n;
            if ((var2 & 8192) == 8192) {
               var3 |= 8192;
            }

            var1.N = this.o;
            if ((var2 & 16384) == 16384) {
               var3 |= 16384;
            }

            var1.O = this.p;
            if ((var2 & 32768) == 32768) {
               var3 |= 32768;
            }

            var1.P = this.q;
            if ((var2 & 65536) == 65536) {
               var3 |= 65536;
            }

            var1.Q = this.r;
            if ((var2 & 131072) == 131072) {
               var3 |= 131072;
            }

            var1.R = this.s;
            if ((var2 & 262144) == 262144) {
               var3 |= 262144;
            }

            var1.S = this.t;
            if ((var2 & 524288) == 524288) {
               var3 |= 524288;
            }

            var1.T = this.u;
            if ((this.a & 1048576) == 1048576) {
               this.v = Collections.unmodifiableList(this.v);
               this.a &= -1048577;
            }

            var1.U = this.v;
            var1.z = var3;
            this.q_();
            return var1;
         }

         public an.b.i.a d(x var1) {
            if (var1 instanceof an.b.i) {
               return this.a((an.b.i)var1);
            }

            super.a(var1);
            return this;
         }

         public an.b.i.a a(an.b.i var1) {
            if (var1 == an.b.i.h()) {
               return this;
            }

            if (var1.o()) {
               this.b(var1.p());
            }

            if (var1.q()) {
               this.c(var1.r());
            }

            if (var1.s()) {
               this.e(var1.t());
            }

            if (var1.u()) {
               this.d(var1.v());
            }

            if (var1.w()) {
               this.e(var1.x());
            }

            if (var1.y()) {
               this.f(var1.z());
            }

            if (var1.A()) {
               this.g(var1.B());
            }

            if (var1.C()) {
               this.h(var1.D());
            }

            if (var1.E()) {
               this.i(var1.F());
            }

            if (var1.G()) {
               this.j(var1.H());
            }

            if (var1.K()) {
               this.k(var1.L());
            }

            if (var1.S()) {
               this.l(var1.T());
            }

            if (var1.U()) {
               this.m(var1.V());
            }

            if (var1.W()) {
               this.n(var1.X());
            }

            if (var1.Y()) {
               this.o(var1.Z());
            }

            if (var1.aa()) {
               this.p(var1.ab());
            }

            if (var1.ae()) {
               this.q(var1.af());
            }

            if (var1.ag()) {
               this.f(var1.C_());
            }

            if (var1.D_()) {
               this.r(var1.E_());
            }

            if (var1.F_()) {
               this.s(var1.G_());
            }

            if (!var1.U.isEmpty()) {
               if (this.v.isEmpty()) {
                  this.v = var1.U;
                  this.a &= -1048577;
               } else {
                  this.aO();
                  this.v.addAll(var1.U);
               }

               this.t_();
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public an.b.i.a e(a.h var1, n var2) throws IOException {
            an.b.i var3 = null;

            try {
               var3 = an.b.i.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.b.i)var8.a();
               throw var8;
            } finally {
               if (var3 != null) {
                  this.a(var3);
               }
            }

            return this;
         }

         @Override
         public boolean o() {
            return (this.a & 1) == 1;
         }

         @Override
         public int p() {
            return this.b;
         }

         public an.b.i.a b(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public an.b.i.a O() {
            this.a &= -2;
            this.b = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean q() {
            return (this.a & 2) == 2;
         }

         @Override
         public int r() {
            return this.c;
         }

         public an.b.i.a c(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public an.b.i.a P() {
            this.a &= -3;
            this.c = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean s() {
            return (this.a & 4) == 4;
         }

         @Override
         public a.g t() {
            return this.d;
         }

         public an.b.i.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.b.i.a ac() {
            this.a &= -5;
            this.d = an.b.i.h().t();
            this.t_();
            return this;
         }

         @Override
         public boolean u() {
            return (this.a & 8) == 8;
         }

         @Override
         public int v() {
            return this.e;
         }

         public an.b.i.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.b.i.a ad() {
            this.a &= -9;
            this.e = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean w() {
            return (this.a & 16) == 16;
         }

         @Override
         public int x() {
            return this.f;
         }

         public an.b.i.a e(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.b.i.a ao() {
            this.a &= -17;
            this.f = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean y() {
            return (this.a & 32) == 32;
         }

         @Override
         public int z() {
            return this.g;
         }

         public an.b.i.a f(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public an.b.i.a ap() {
            this.a &= -33;
            this.g = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean A() {
            return (this.a & 64) == 64;
         }

         @Override
         public int B() {
            return this.h;
         }

         public an.b.i.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public an.b.i.a aq() {
            this.a &= -65;
            this.h = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean C() {
            return (this.a & 128) == 128;
         }

         @Override
         public int D() {
            return this.i;
         }

         public an.b.i.a h(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public an.b.i.a ar() {
            this.a &= -129;
            this.i = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean E() {
            return (this.a & 256) == 256;
         }

         @Override
         public int F() {
            return this.j;
         }

         public an.b.i.a i(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public an.b.i.a as() {
            this.a &= -257;
            this.j = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean G() {
            return (this.a & 512) == 512;
         }

         @Override
         public int H() {
            return this.k;
         }

         public an.b.i.a j(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public an.b.i.a at() {
            this.a &= -513;
            this.k = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean K() {
            return (this.a & 1024) == 1024;
         }

         @Override
         public int L() {
            return this.l;
         }

         public an.b.i.a k(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public an.b.i.a au() {
            this.a &= -1025;
            this.l = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean S() {
            return (this.a & 2048) == 2048;
         }

         @Override
         public int T() {
            return this.m;
         }

         public an.b.i.a l(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public an.b.i.a av() {
            this.a &= -2049;
            this.m = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean U() {
            return (this.a & 4096) == 4096;
         }

         @Override
         public int V() {
            return this.n;
         }

         public an.b.i.a m(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public an.b.i.a aw() {
            this.a &= -4097;
            this.n = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean W() {
            return (this.a & 8192) == 8192;
         }

         @Override
         public int X() {
            return this.o;
         }

         public an.b.i.a n(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public an.b.i.a ax() {
            this.a &= -8193;
            this.o = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean Y() {
            return (this.a & 16384) == 16384;
         }

         @Override
         public int Z() {
            return this.p;
         }

         public an.b.i.a o(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public an.b.i.a ay() {
            this.a &= -16385;
            this.p = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean aa() {
            return (this.a & 32768) == 32768;
         }

         @Override
         public int ab() {
            return this.q;
         }

         public an.b.i.a p(int var1) {
            this.a |= 32768;
            this.q = var1;
            this.t_();
            return this;
         }

         public an.b.i.a az() {
            this.a &= -32769;
            this.q = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean ae() {
            return (this.a & 65536) == 65536;
         }

         @Override
         public int af() {
            return this.r;
         }

         public an.b.i.a q(int var1) {
            this.a |= 65536;
            this.r = var1;
            this.t_();
            return this;
         }

         public an.b.i.a aG() {
            this.a &= -65537;
            this.r = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean ag() {
            return (this.a & 131072) == 131072;
         }

         @Override
         public a.g C_() {
            return this.s;
         }

         public an.b.i.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 131072;
            this.s = var1;
            this.t_();
            return this;
         }

         public an.b.i.a aH() {
            this.a &= -131073;
            this.s = an.b.i.h().C_();
            this.t_();
            return this;
         }

         @Override
         public boolean D_() {
            return (this.a & 262144) == 262144;
         }

         @Override
         public int E_() {
            return this.t;
         }

         public an.b.i.a r(int var1) {
            this.a |= 262144;
            this.t = var1;
            this.t_();
            return this;
         }

         public an.b.i.a aI() {
            this.a &= -262145;
            this.t = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean F_() {
            return (this.a & 524288) == 524288;
         }

         @Override
         public int G_() {
            return this.u;
         }

         public an.b.i.a s(int var1) {
            this.a |= 524288;
            this.u = var1;
            this.t_();
            return this;
         }

         public an.b.i.a aJ() {
            this.a &= -524289;
            this.u = 0;
            this.t_();
            return this;
         }

         private void aO() {
            if ((this.a & 1048576) != 1048576) {
               this.v = new ArrayList<>(this.v);
               this.a |= 1048576;
            }
         }

         @Override
         public List<a.g> H_() {
            return Collections.unmodifiableList(this.v);
         }

         @Override
         public int an() {
            return this.v.size();
         }

         @Override
         public a.g a(int var1) {
            return this.v.get(var1);
         }

         public an.b.i.a a(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.aO();
            this.v.set(var1, var2);
            this.t_();
            return this;
         }

         public an.b.i.a g(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.aO();
            this.v.add(var1);
            this.t_();
            return this;
         }

         public an.b.i.a a(Iterable<? extends a.g> var1) {
            this.aO();
            a.p.a.a(var1, this.v);
            this.t_();
            return this;
         }

         public an.b.i.a aK() {
            this.v = Collections.emptyList();
            this.a &= -1048577;
            this.t_();
            return this;
         }

         // $VF: synthetic method
         @Override
         public y al() {
            return this.N();
         }

         // $VF: synthetic method
         @Override
         public x aj() {
            return this.N();
         }

         // $VF: synthetic method
         @Override
         public a.y.a g() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public a.x.a i() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public a.p.a ai() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public a.y.a c(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.x.a d(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.x.a c(x var1) {
            return this.d(var1);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(x var1) {
            return this.d(var1);
         }

         // $VF: synthetic method
         @Override
         public y Q() {
            return this.I();
         }

         // $VF: synthetic method
         @Override
         public x R() {
            return this.I();
         }

         // $VF: synthetic method
         @Override
         public a.y.a h() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public a.x.a j() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public a.p.a ah() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public y am() {
            return this.M();
         }

         // $VF: synthetic method
         @Override
         public x ak() {
            return this.M();
         }

         // $VF: synthetic method
         a(a.p.b var1, an.b.i.a var2) {
            this(var1);
         }
      }
   }

   public interface j extends aa {
      boolean o();

      int p();

      boolean q();

      int r();

      boolean s();

      a.g t();

      boolean u();

      int v();

      boolean w();

      int x();

      boolean y();

      int z();

      boolean A();

      int B();

      boolean C();

      int D();

      boolean E();

      int F();

      boolean G();

      int H();

      boolean K();

      int L();

      boolean S();

      int T();

      boolean U();

      int V();

      boolean W();

      int X();

      boolean Y();

      int Z();

      boolean aa();

      int ab();

      boolean ae();

      int af();

      boolean ag();

      a.g C_();

      boolean D_();

      int E_();

      boolean F_();

      int G_();

      List<a.g> H_();

      int an();

      a.g a(int var1);
   }
}
