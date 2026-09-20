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

public final class c {
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
         "\n\u0013PBMessageALL3.proto\u0012 l1j.server.server.datas.protobuf\"\u008c\u0002\n\u0005type6\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007array_2\u0018\u0002 \u0001(\f\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007array_4\u0018\u0004 \u0001(\f\u0012\u000f\n\u0007array_5\u0018\u0005 \u0001(\f\u0012\u000f\n\u0007array_6\u0018\u0006 \u0001(\f\u0012\u000f\n\u0007array_7\u0018\u0007 \u0001(\f\u0012\u000f\n\u0007array_8\u0018\b \u0001(\f\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008c\u0002\n\u0005type7\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004",
         " \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007array_6\u0018\u0006 \u0001(\f\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008c\u0002\n\u0005type8\u0012\u000f\n\u0007array_1\u0018\u0001 \u0003(\f\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\b",
         "value_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008c\u0002\n\u0005type9\u0012\u000f\n\u0007array_1\u0018\u0001 \u0001(\f\u0012\u000f\n\u0007array_2\u0018\u0002 \u0001(\f\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007array_6\u0018\u0006 \u0001(\f\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008d\u0002\n\u0006type10\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007array_2\u0018\u0002 \u0003(\f\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(",
         "\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005B1\n l1j.server.server.datas.protobufB\rPBMessageALL3"
      };
      a.k.g.a var1 = new a.k.g.a() {
         @Override
         public m a(a.k.g var1) {
            an.c.k = var1;
            an.c.a = an.c.a().e().get(0);
            an.c.b = new a.p.g(
               an.c.a,
               new String[]{
                  "Value1",
                  "Array2",
                  "Value3",
                  "Array4",
                  "Array5",
                  "Array6",
                  "Array7",
                  "Array8",
                  "Value9",
                  "Value10",
                  "Value11",
                  "Value12",
                  "Value13",
                  "Value14",
                  "Value15"
               }
            );
            an.c.c = an.c.a().e().get(1);
            an.c.d = new a.p.g(
               an.c.c,
               new String[]{
                  "Value1",
                  "Value2",
                  "Value3",
                  "Value4",
                  "Value5",
                  "Array6",
                  "Value7",
                  "Value8",
                  "Value9",
                  "Value10",
                  "Value11",
                  "Value12",
                  "Value13",
                  "Value14",
                  "Value15"
               }
            );
            an.c.e = an.c.a().e().get(2);
            an.c.f = new a.p.g(
               an.c.e,
               new String[]{
                  "Array1",
                  "Value2",
                  "Value3",
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
                  "Value15"
               }
            );
            an.c.g = an.c.a().e().get(3);
            an.c.h = new a.p.g(
               an.c.g,
               new String[]{
                  "Array1",
                  "Array2",
                  "Value3",
                  "Value4",
                  "Value5",
                  "Array6",
                  "Value7",
                  "Value8",
                  "Value9",
                  "Value10",
                  "Value11",
                  "Value12",
                  "Value13",
                  "Value14",
                  "Value15"
               }
            );
            an.c.i = an.c.a().e().get(4);
            an.c.j = new a.p.g(
               an.c.i,
               new String[]{
                  "Value1",
                  "Array2",
                  "Value3",
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
                  "Value15"
               }
            );
            return null;
         }
      };
      a.k.g.a(var0, new a.k.g[0], var1);
   }

   private c() {
   }

   public static void a(m var0) {
   }

   public static a.k.g a() {
      return k;
   }

   public static final class a extends p implements an.c.b {
      private static final an.c.a r = new an.c.a(true);
      private final ap s;
      public static ab<an.c.a> a = new a.c<an.c.a>() {
         public an.c.a c(a.h var1, n var2) throws s {
            return new an.c.a(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int t;
      public static final int b = 1;
      private int u;
      public static final int c = 2;
      private List<a.g> v;
      public static final int d = 3;
      private int w;
      public static final int e = 4;
      private int x;
      public static final int f = 5;
      private int y;
      public static final int g = 6;
      private int z;
      public static final int h = 7;
      private int A;
      public static final int i = 8;
      private int B;
      public static final int j = 9;
      private int C;
      public static final int k = 10;
      private int D;
      public static final int l = 11;
      private int E;
      public static final int n = 12;
      private int F;
      public static final int o = 13;
      private int G;
      public static final int p = 14;
      private int H;
      public static final int q = 15;
      private int I;
      private byte J = -1;
      private int K = -1;
      private static final long L = 0L;

      static {
         r.ag();
      }

      private a(a.p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private a(boolean var1) {
         this.s = ap.c();
      }

      public static an.c.a h() {
         return r;
      }

      public an.c.a k() {
         return r;
      }

      @Override
      public final ap b_() {
         return this.s;
      }

      private a(a.h var1, n var2) throws s {
         this.ag();
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
                     this.t |= 1;
                     this.u = var1.g();
                     break;
                  case 18:
                     if ((var3 & 2) != 2) {
                        this.v = new ArrayList<>();
                        var3 |= 2;
                     }

                     this.v.add(var1.l());
                     break;
                  case 24:
                     this.t |= 2;
                     this.w = var1.g();
                     break;
                  case 32:
                     this.t |= 4;
                     this.x = var1.g();
                     break;
                  case 40:
                     this.t |= 8;
                     this.y = var1.g();
                     break;
                  case 48:
                     this.t |= 16;
                     this.z = var1.g();
                     break;
                  case 56:
                     this.t |= 32;
                     this.A = var1.g();
                     break;
                  case 64:
                     this.t |= 64;
                     this.B = var1.g();
                     break;
                  case 72:
                     this.t |= 128;
                     this.C = var1.g();
                     break;
                  case 80:
                     this.t |= 256;
                     this.D = var1.g();
                     break;
                  case 88:
                     this.t |= 512;
                     this.E = var1.g();
                     break;
                  case 96:
                     this.t |= 1024;
                     this.F = var1.g();
                     break;
                  case 104:
                     this.t |= 2048;
                     this.G = var1.g();
                     break;
                  case 112:
                     this.t |= 4096;
                     this.H = var1.g();
                     break;
                  case 120:
                     this.t |= 8192;
                     this.I = var1.g();
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
            if ((var3 & 2) == 2) {
               this.v = Collections.unmodifiableList(this.v);
            }

            this.s = var4.b();
            this.ad();
         }
      }

      public static final a.k.a n() {
         return an.c.i;
      }

      @Override
      protected a.p.g l() {
         return an.c.j.a(an.c.a.class, an.c.a.a.class);
      }

      @Override
      public ab<an.c.a> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.t & 1) == 1;
      }

      @Override
      public int p() {
         return this.u;
      }

      @Override
      public List<a.g> q() {
         return this.v;
      }

      @Override
      public int r() {
         return this.v.size();
      }

      @Override
      public a.g a(int var1) {
         return this.v.get(var1);
      }

      @Override
      public boolean s() {
         return (this.t & 2) == 2;
      }

      @Override
      public int t() {
         return this.w;
      }

      @Override
      public boolean u() {
         return (this.t & 4) == 4;
      }

      @Override
      public int v() {
         return this.x;
      }

      @Override
      public boolean w() {
         return (this.t & 8) == 8;
      }

      @Override
      public int x() {
         return this.y;
      }

      @Override
      public boolean y() {
         return (this.t & 16) == 16;
      }

      @Override
      public int z() {
         return this.z;
      }

      @Override
      public boolean A() {
         return (this.t & 32) == 32;
      }

      @Override
      public int B() {
         return this.A;
      }

      @Override
      public boolean C() {
         return (this.t & 64) == 64;
      }

      @Override
      public int D() {
         return this.B;
      }

      @Override
      public boolean E() {
         return (this.t & 128) == 128;
      }

      @Override
      public int F() {
         return this.C;
      }

      @Override
      public boolean G() {
         return (this.t & 256) == 256;
      }

      @Override
      public int H() {
         return this.D;
      }

      @Override
      public boolean K() {
         return (this.t & 512) == 512;
      }

      @Override
      public int L() {
         return this.E;
      }

      @Override
      public boolean S() {
         return (this.t & 1024) == 1024;
      }

      @Override
      public int T() {
         return this.F;
      }

      @Override
      public boolean U() {
         return (this.t & 2048) == 2048;
      }

      @Override
      public int V() {
         return this.G;
      }

      @Override
      public boolean W() {
         return (this.t & 4096) == 4096;
      }

      @Override
      public int X() {
         return this.H;
      }

      @Override
      public boolean Y() {
         return (this.t & 8192) == 8192;
      }

      @Override
      public int Z() {
         return this.I;
      }

      private void ag() {
         this.u = 0;
         this.v = Collections.emptyList();
         this.w = 0;
         this.x = 0;
         this.y = 0;
         this.z = 0;
         this.A = 0;
         this.B = 0;
         this.C = 0;
         this.D = 0;
         this.E = 0;
         this.F = 0;
         this.G = 0;
         this.H = 0;
         this.I = 0;
      }

      @Override
      public final boolean a() {
         byte var1 = this.J;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.J = 1;
         return true;
      }

      @Override
      public void a(a.i var1) throws IOException {
         this.d();
         if ((this.t & 1) == 1) {
            var1.a(1, this.u);
         }

         for (int var2 = 0; var2 < this.v.size(); var2++) {
            var1.a(2, this.v.get(var2));
         }

         if ((this.t & 2) == 2) {
            var1.a(3, this.w);
         }

         if ((this.t & 4) == 4) {
            var1.a(4, this.x);
         }

         if ((this.t & 8) == 8) {
            var1.a(5, this.y);
         }

         if ((this.t & 16) == 16) {
            var1.a(6, this.z);
         }

         if ((this.t & 32) == 32) {
            var1.a(7, this.A);
         }

         if ((this.t & 64) == 64) {
            var1.a(8, this.B);
         }

         if ((this.t & 128) == 128) {
            var1.a(9, this.C);
         }

         if ((this.t & 256) == 256) {
            var1.a(10, this.D);
         }

         if ((this.t & 512) == 512) {
            var1.a(11, this.E);
         }

         if ((this.t & 1024) == 1024) {
            var1.a(12, this.F);
         }

         if ((this.t & 2048) == 2048) {
            var1.a(13, this.G);
         }

         if ((this.t & 4096) == 4096) {
            var1.a(14, this.H);
         }

         if ((this.t & 8192) == 8192) {
            var1.a(15, this.I);
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.K;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.t & 1) == 1) {
            var1 += a.i.g(1, this.u);
         }

         int var2 = 0;

         for (int var3 = 0; var3 < this.v.size(); var3++) {
            var2 += a.i.b(this.v.get(var3));
         }

         var1 += var2;
         var1 += 1 * this.q().size();
         if ((this.t & 2) == 2) {
            var1 += a.i.g(3, this.w);
         }

         if ((this.t & 4) == 4) {
            var1 += a.i.g(4, this.x);
         }

         if ((this.t & 8) == 8) {
            var1 += a.i.g(5, this.y);
         }

         if ((this.t & 16) == 16) {
            var1 += a.i.g(6, this.z);
         }

         if ((this.t & 32) == 32) {
            var1 += a.i.g(7, this.A);
         }

         if ((this.t & 64) == 64) {
            var1 += a.i.g(8, this.B);
         }

         if ((this.t & 128) == 128) {
            var1 += a.i.g(9, this.C);
         }

         if ((this.t & 256) == 256) {
            var1 += a.i.g(10, this.D);
         }

         if ((this.t & 512) == 512) {
            var1 += a.i.g(11, this.E);
         }

         if ((this.t & 1024) == 1024) {
            var1 += a.i.g(12, this.F);
         }

         if ((this.t & 2048) == 2048) {
            var1 += a.i.g(13, this.G);
         }

         if ((this.t & 4096) == 4096) {
            var1 += a.i.g(14, this.H);
         }

         if ((this.t & 8192) == 8192) {
            var1 += a.i.g(15, this.I);
         }

         var1 += this.b_().d();
         this.K = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static an.c.a a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.c.a a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.c.a a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.c.a a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.c.a a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.c.a a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.c.a b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.c.a b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.c.a a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.c.a a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.c.a.a aa() {
         return an.c.a.a.av();
      }

      public an.c.a.a ab() {
         return aa();
      }

      public static an.c.a.a a(an.c.a var0) {
         return aa().a(var0);
      }

      public an.c.a.a ae() {
         return a(this);
      }

      protected an.c.a.a a(a.p.b var1) {
         return new an.c.a.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.ae();
      }

      // $VF: synthetic method
      @Override
      public a.x.a M() {
         return this.ae();
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
         return this.ab();
      }

      // $VF: synthetic method
      @Override
      public a.x.a N() {
         return this.ab();
      }

      // $VF: synthetic method
      a(a.h var1, n var2, an.c.a var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      a(a.p.a var1, an.c.a var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.c.a.a> implements an.c.b {
         private int a;
         private int b;
         private List<a.g> c = Collections.emptyList();
         private int d;
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

         public static final a.k.a k() {
            return an.c.i;
         }

         @Override
         protected a.p.g l() {
            return an.c.j.a(an.c.a.class, an.c.a.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            an.c.a.m;
         }

         private static an.c.a.a av() {
            return new an.c.a.a();
         }

         public an.c.a.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = Collections.emptyList();
            this.a &= -3;
            this.d = 0;
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
            return this;
         }

         public an.c.a.a n() {
            return av().a(this.N());
         }

         @Override
         public a.k.a J() {
            return an.c.i;
         }

         public an.c.a I() {
            return an.c.a.h();
         }

         public an.c.a M() {
            an.c.a var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.c.a N() {
            an.c.a var1 = new an.c.a(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((var2 & 1) == 1) {
               var3 |= 1;
            }

            var1.u = this.b;
            if ((this.a & 2) == 2) {
               this.c = Collections.unmodifiableList(this.c);
               this.a &= -3;
            }

            var1.v = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 2;
            }

            var1.w = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 4;
            }

            var1.x = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 8;
            }

            var1.y = this.f;
            if ((var2 & 32) == 32) {
               var3 |= 16;
            }

            var1.z = this.g;
            if ((var2 & 64) == 64) {
               var3 |= 32;
            }

            var1.A = this.h;
            if ((var2 & 128) == 128) {
               var3 |= 64;
            }

            var1.B = this.i;
            if ((var2 & 256) == 256) {
               var3 |= 128;
            }

            var1.C = this.j;
            if ((var2 & 512) == 512) {
               var3 |= 256;
            }

            var1.D = this.k;
            if ((var2 & 1024) == 1024) {
               var3 |= 512;
            }

            var1.E = this.l;
            if ((var2 & 2048) == 2048) {
               var3 |= 1024;
            }

            var1.F = this.m;
            if ((var2 & 4096) == 4096) {
               var3 |= 2048;
            }

            var1.G = this.n;
            if ((var2 & 8192) == 8192) {
               var3 |= 4096;
            }

            var1.H = this.o;
            if ((var2 & 16384) == 16384) {
               var3 |= 8192;
            }

            var1.I = this.p;
            var1.t = var3;
            this.q_();
            return var1;
         }

         public an.c.a.a d(x var1) {
            if (var1 instanceof an.c.a) {
               return this.a((an.c.a)var1);
            }

            super.a(var1);
            return this;
         }

         public an.c.a.a a(an.c.a var1) {
            if (var1 == an.c.a.h()) {
               return this;
            }

            if (var1.o()) {
               this.b(var1.p());
            }

            if (!var1.v.isEmpty()) {
               if (this.c.isEmpty()) {
                  this.c = var1.v;
                  this.a &= -3;
               } else {
                  this.aw();
                  this.c.addAll(var1.v);
               }

               this.t_();
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

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public an.c.a.a e(a.h var1, n var2) throws IOException {
            an.c.a var3 = null;

            try {
               var3 = an.c.a.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.c.a)var8.a();
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

         public an.c.a.a b(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public an.c.a.a O() {
            this.a &= -2;
            this.b = 0;
            this.t_();
            return this;
         }

         private void aw() {
            if ((this.a & 2) != 2) {
               this.c = new ArrayList<>(this.c);
               this.a |= 2;
            }
         }

         @Override
         public List<a.g> q() {
            return Collections.unmodifiableList(this.c);
         }

         @Override
         public int r() {
            return this.c.size();
         }

         @Override
         public a.g a(int var1) {
            return this.c.get(var1);
         }

         public an.c.a.a a(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.c.set(var1, var2);
            this.t_();
            return this;
         }

         public an.c.a.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.c.add(var1);
            this.t_();
            return this;
         }

         public an.c.a.a a(Iterable<? extends a.g> var1) {
            this.aw();
            a.p.a.a(var1, this.c);
            this.t_();
            return this;
         }

         public an.c.a.a P() {
            this.c = Collections.emptyList();
            this.a &= -3;
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

         public an.c.a.a c(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.c.a.a aa() {
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

         public an.c.a.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.c.a.a ab() {
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

         public an.c.a.a e(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.c.a.a ac() {
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

         public an.c.a.a f(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public an.c.a.a ad() {
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

         public an.c.a.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public an.c.a.a ae() {
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

         public an.c.a.a h(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public an.c.a.a af() {
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

         public an.c.a.a i(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public an.c.a.a ag() {
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

         public an.c.a.a j(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public an.c.a.a an() {
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

         public an.c.a.a k(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public an.c.a.a ao() {
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

         public an.c.a.a l(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public an.c.a.a ap() {
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

         public an.c.a.a m(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public an.c.a.a aq() {
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

         public an.c.a.a n(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public an.c.a.a ar() {
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

         public an.c.a.a o(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public an.c.a.a as() {
            this.a &= -16385;
            this.p = 0;
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
         a(a.p.b var1, an.c.a.a var2) {
            this(var1);
         }
      }
   }

   public interface b extends aa {
      boolean o();

      int p();

      List<a.g> q();

      int r();

      a.g a(int var1);

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
   }

   public static final class c extends p implements an.c.d {
      private static final an.c.c r = new an.c.c(true);
      private final ap s;
      public static ab<an.c.c> a = new a.c<an.c.c>() {
         public an.c.c c(a.h var1, n var2) throws s {
            return new an.c.c(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int t;
      public static final int b = 1;
      private int u;
      public static final int c = 2;
      private a.g v;
      public static final int d = 3;
      private int w;
      public static final int e = 4;
      private a.g x;
      public static final int f = 5;
      private a.g y;
      public static final int g = 6;
      private a.g z;
      public static final int h = 7;
      private a.g A;
      public static final int i = 8;
      private a.g B;
      public static final int j = 9;
      private int C;
      public static final int k = 10;
      private int D;
      public static final int l = 11;
      private int E;
      public static final int n = 12;
      private int F;
      public static final int o = 13;
      private int G;
      public static final int p = 14;
      private int H;
      public static final int q = 15;
      private int I;
      private byte J = -1;
      private int K = -1;
      private static final long L = 0L;

      static {
         r.ag();
      }

      private c(a.p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private c(boolean var1) {
         this.s = ap.c();
      }

      public static an.c.c h() {
         return r;
      }

      public an.c.c k() {
         return r;
      }

      @Override
      public final ap b_() {
         return this.s;
      }

      private c(a.h var1, n var2) throws s {
         this.ag();
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
                     this.t |= 1;
                     this.u = var1.g();
                     break;
                  case 18:
                     this.t |= 2;
                     this.v = var1.l();
                     break;
                  case 24:
                     this.t |= 4;
                     this.w = var1.g();
                     break;
                  case 34:
                     this.t |= 8;
                     this.x = var1.l();
                     break;
                  case 42:
                     this.t |= 16;
                     this.y = var1.l();
                     break;
                  case 50:
                     this.t |= 32;
                     this.z = var1.l();
                     break;
                  case 58:
                     this.t |= 64;
                     this.A = var1.l();
                     break;
                  case 66:
                     this.t |= 128;
                     this.B = var1.l();
                     break;
                  case 72:
                     this.t |= 256;
                     this.C = var1.g();
                     break;
                  case 80:
                     this.t |= 512;
                     this.D = var1.g();
                     break;
                  case 88:
                     this.t |= 1024;
                     this.E = var1.g();
                     break;
                  case 96:
                     this.t |= 2048;
                     this.F = var1.g();
                     break;
                  case 104:
                     this.t |= 4096;
                     this.G = var1.g();
                     break;
                  case 112:
                     this.t |= 8192;
                     this.H = var1.g();
                     break;
                  case 120:
                     this.t |= 16384;
                     this.I = var1.g();
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
            this.s = var4.b();
            this.ad();
         }
      }

      public static final a.k.a n() {
         return an.c.a;
      }

      @Override
      protected a.p.g l() {
         return an.c.b.a(an.c.c.class, an.c.c.a.class);
      }

      @Override
      public ab<an.c.c> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.t & 1) == 1;
      }

      @Override
      public int p() {
         return this.u;
      }

      @Override
      public boolean q() {
         return (this.t & 2) == 2;
      }

      @Override
      public a.g r() {
         return this.v;
      }

      @Override
      public boolean s() {
         return (this.t & 4) == 4;
      }

      @Override
      public int t() {
         return this.w;
      }

      @Override
      public boolean u() {
         return (this.t & 8) == 8;
      }

      @Override
      public a.g v() {
         return this.x;
      }

      @Override
      public boolean w() {
         return (this.t & 16) == 16;
      }

      @Override
      public a.g x() {
         return this.y;
      }

      @Override
      public boolean y() {
         return (this.t & 32) == 32;
      }

      @Override
      public a.g z() {
         return this.z;
      }

      @Override
      public boolean A() {
         return (this.t & 64) == 64;
      }

      @Override
      public a.g B() {
         return this.A;
      }

      @Override
      public boolean C() {
         return (this.t & 128) == 128;
      }

      @Override
      public a.g D() {
         return this.B;
      }

      @Override
      public boolean E() {
         return (this.t & 256) == 256;
      }

      @Override
      public int F() {
         return this.C;
      }

      @Override
      public boolean G() {
         return (this.t & 512) == 512;
      }

      @Override
      public int H() {
         return this.D;
      }

      @Override
      public boolean K() {
         return (this.t & 1024) == 1024;
      }

      @Override
      public int L() {
         return this.E;
      }

      @Override
      public boolean S() {
         return (this.t & 2048) == 2048;
      }

      @Override
      public int T() {
         return this.F;
      }

      @Override
      public boolean U() {
         return (this.t & 4096) == 4096;
      }

      @Override
      public int V() {
         return this.G;
      }

      @Override
      public boolean W() {
         return (this.t & 8192) == 8192;
      }

      @Override
      public int X() {
         return this.H;
      }

      @Override
      public boolean Y() {
         return (this.t & 16384) == 16384;
      }

      @Override
      public int Z() {
         return this.I;
      }

      private void ag() {
         this.u = 0;
         this.v = a.g.d;
         this.w = 0;
         this.x = a.g.d;
         this.y = a.g.d;
         this.z = a.g.d;
         this.A = a.g.d;
         this.B = a.g.d;
         this.C = 0;
         this.D = 0;
         this.E = 0;
         this.F = 0;
         this.G = 0;
         this.H = 0;
         this.I = 0;
      }

      @Override
      public final boolean a() {
         byte var1 = this.J;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.J = 1;
         return true;
      }

      @Override
      public void a(a.i var1) throws IOException {
         this.d();
         if ((this.t & 1) == 1) {
            var1.a(1, this.u);
         }

         if ((this.t & 2) == 2) {
            var1.a(2, this.v);
         }

         if ((this.t & 4) == 4) {
            var1.a(3, this.w);
         }

         if ((this.t & 8) == 8) {
            var1.a(4, this.x);
         }

         if ((this.t & 16) == 16) {
            var1.a(5, this.y);
         }

         if ((this.t & 32) == 32) {
            var1.a(6, this.z);
         }

         if ((this.t & 64) == 64) {
            var1.a(7, this.A);
         }

         if ((this.t & 128) == 128) {
            var1.a(8, this.B);
         }

         if ((this.t & 256) == 256) {
            var1.a(9, this.C);
         }

         if ((this.t & 512) == 512) {
            var1.a(10, this.D);
         }

         if ((this.t & 1024) == 1024) {
            var1.a(11, this.E);
         }

         if ((this.t & 2048) == 2048) {
            var1.a(12, this.F);
         }

         if ((this.t & 4096) == 4096) {
            var1.a(13, this.G);
         }

         if ((this.t & 8192) == 8192) {
            var1.a(14, this.H);
         }

         if ((this.t & 16384) == 16384) {
            var1.a(15, this.I);
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.K;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.t & 1) == 1) {
            var1 += a.i.g(1, this.u);
         }

         if ((this.t & 2) == 2) {
            var1 += a.i.c(2, this.v);
         }

         if ((this.t & 4) == 4) {
            var1 += a.i.g(3, this.w);
         }

         if ((this.t & 8) == 8) {
            var1 += a.i.c(4, this.x);
         }

         if ((this.t & 16) == 16) {
            var1 += a.i.c(5, this.y);
         }

         if ((this.t & 32) == 32) {
            var1 += a.i.c(6, this.z);
         }

         if ((this.t & 64) == 64) {
            var1 += a.i.c(7, this.A);
         }

         if ((this.t & 128) == 128) {
            var1 += a.i.c(8, this.B);
         }

         if ((this.t & 256) == 256) {
            var1 += a.i.g(9, this.C);
         }

         if ((this.t & 512) == 512) {
            var1 += a.i.g(10, this.D);
         }

         if ((this.t & 1024) == 1024) {
            var1 += a.i.g(11, this.E);
         }

         if ((this.t & 2048) == 2048) {
            var1 += a.i.g(12, this.F);
         }

         if ((this.t & 4096) == 4096) {
            var1 += a.i.g(13, this.G);
         }

         if ((this.t & 8192) == 8192) {
            var1 += a.i.g(14, this.H);
         }

         if ((this.t & 16384) == 16384) {
            var1 += a.i.g(15, this.I);
         }

         var1 += this.b_().d();
         this.K = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static an.c.c a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.c.c a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.c.c a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.c.c a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.c.c a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.c.c a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.c.c b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.c.c b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.c.c a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.c.c a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.c.c.a aa() {
         return an.c.c.a.av();
      }

      public an.c.c.a ab() {
         return aa();
      }

      public static an.c.c.a a(an.c.c var0) {
         return aa().a(var0);
      }

      public an.c.c.a ae() {
         return a(this);
      }

      protected an.c.c.a a(a.p.b var1) {
         return new an.c.c.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.ae();
      }

      // $VF: synthetic method
      @Override
      public a.x.a M() {
         return this.ae();
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
         return this.ab();
      }

      // $VF: synthetic method
      @Override
      public a.x.a N() {
         return this.ab();
      }

      // $VF: synthetic method
      c(a.h var1, n var2, an.c.c var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      c(a.p.a var1, an.c.c var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.c.c.a> implements an.c.d {
         private int a;
         private int b;
         private a.g c = a.g.d;
         private int d;
         private a.g e = a.g.d;
         private a.g f = a.g.d;
         private a.g g = a.g.d;
         private a.g h = a.g.d;
         private a.g i = a.g.d;
         private int j;
         private int k;
         private int l;
         private int m;
         private int n;
         private int o;
         private int p;

         public static final a.k.a k() {
            return an.c.a;
         }

         @Override
         protected a.p.g l() {
            return an.c.b.a(an.c.c.class, an.c.c.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            an.c.c.m;
         }

         private static an.c.c.a av() {
            return new an.c.c.a();
         }

         public an.c.c.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = a.g.d;
            this.a &= -3;
            this.d = 0;
            this.a &= -5;
            this.e = a.g.d;
            this.a &= -9;
            this.f = a.g.d;
            this.a &= -17;
            this.g = a.g.d;
            this.a &= -33;
            this.h = a.g.d;
            this.a &= -65;
            this.i = a.g.d;
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
            return this;
         }

         public an.c.c.a n() {
            return av().a(this.N());
         }

         @Override
         public a.k.a J() {
            return an.c.a;
         }

         public an.c.c I() {
            return an.c.c.h();
         }

         public an.c.c M() {
            an.c.c var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.c.c N() {
            an.c.c var1 = new an.c.c(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((var2 & 1) == 1) {
               var3 |= 1;
            }

            var1.u = this.b;
            if ((var2 & 2) == 2) {
               var3 |= 2;
            }

            var1.v = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 4;
            }

            var1.w = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 8;
            }

            var1.x = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 16;
            }

            var1.y = this.f;
            if ((var2 & 32) == 32) {
               var3 |= 32;
            }

            var1.z = this.g;
            if ((var2 & 64) == 64) {
               var3 |= 64;
            }

            var1.A = this.h;
            if ((var2 & 128) == 128) {
               var3 |= 128;
            }

            var1.B = this.i;
            if ((var2 & 256) == 256) {
               var3 |= 256;
            }

            var1.C = this.j;
            if ((var2 & 512) == 512) {
               var3 |= 512;
            }

            var1.D = this.k;
            if ((var2 & 1024) == 1024) {
               var3 |= 1024;
            }

            var1.E = this.l;
            if ((var2 & 2048) == 2048) {
               var3 |= 2048;
            }

            var1.F = this.m;
            if ((var2 & 4096) == 4096) {
               var3 |= 4096;
            }

            var1.G = this.n;
            if ((var2 & 8192) == 8192) {
               var3 |= 8192;
            }

            var1.H = this.o;
            if ((var2 & 16384) == 16384) {
               var3 |= 16384;
            }

            var1.I = this.p;
            var1.t = var3;
            this.q_();
            return var1;
         }

         public an.c.c.a d(x var1) {
            if (var1 instanceof an.c.c) {
               return this.a((an.c.c)var1);
            }

            super.a(var1);
            return this;
         }

         public an.c.c.a a(an.c.c var1) {
            if (var1 == an.c.c.h()) {
               return this;
            }

            if (var1.o()) {
               this.a(var1.p());
            }

            if (var1.q()) {
               this.e(var1.r());
            }

            if (var1.s()) {
               this.b(var1.t());
            }

            if (var1.u()) {
               this.f(var1.v());
            }

            if (var1.w()) {
               this.g(var1.x());
            }

            if (var1.y()) {
               this.h(var1.z());
            }

            if (var1.A()) {
               this.i(var1.B());
            }

            if (var1.C()) {
               this.j(var1.D());
            }

            if (var1.E()) {
               this.c(var1.F());
            }

            if (var1.G()) {
               this.d(var1.H());
            }

            if (var1.K()) {
               this.e(var1.L());
            }

            if (var1.S()) {
               this.f(var1.T());
            }

            if (var1.U()) {
               this.g(var1.V());
            }

            if (var1.W()) {
               this.h(var1.X());
            }

            if (var1.Y()) {
               this.i(var1.Z());
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public an.c.c.a e(a.h var1, n var2) throws IOException {
            an.c.c var3 = null;

            try {
               var3 = an.c.c.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.c.c)var8.a();
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

         public an.c.c.a a(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public an.c.c.a O() {
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
         public a.g r() {
            return this.c;
         }

         public an.c.c.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public an.c.c.a P() {
            this.a &= -3;
            this.c = an.c.c.h().r();
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

         public an.c.c.a b(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.c.c.a aa() {
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

         public an.c.c.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.c.c.a ab() {
            this.a &= -9;
            this.e = an.c.c.h().v();
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

         public an.c.c.a g(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.c.c.a ac() {
            this.a &= -17;
            this.f = an.c.c.h().x();
            this.t_();
            return this;
         }

         @Override
         public boolean y() {
            return (this.a & 32) == 32;
         }

         @Override
         public a.g z() {
            return this.g;
         }

         public an.c.c.a h(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public an.c.c.a ad() {
            this.a &= -33;
            this.g = an.c.c.h().z();
            this.t_();
            return this;
         }

         @Override
         public boolean A() {
            return (this.a & 64) == 64;
         }

         @Override
         public a.g B() {
            return this.h;
         }

         public an.c.c.a i(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public an.c.c.a ae() {
            this.a &= -65;
            this.h = an.c.c.h().B();
            this.t_();
            return this;
         }

         @Override
         public boolean C() {
            return (this.a & 128) == 128;
         }

         @Override
         public a.g D() {
            return this.i;
         }

         public an.c.c.a j(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public an.c.c.a af() {
            this.a &= -129;
            this.i = an.c.c.h().D();
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

         public an.c.c.a c(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public an.c.c.a ag() {
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

         public an.c.c.a d(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public an.c.c.a an() {
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

         public an.c.c.a e(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public an.c.c.a ao() {
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

         public an.c.c.a f(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public an.c.c.a ap() {
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

         public an.c.c.a g(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public an.c.c.a aq() {
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

         public an.c.c.a h(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public an.c.c.a ar() {
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

         public an.c.c.a i(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public an.c.c.a as() {
            this.a &= -16385;
            this.p = 0;
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
         a(a.p.b var1, an.c.c.a var2) {
            this(var1);
         }
      }
   }

   public interface d extends aa {
      boolean o();

      int p();

      boolean q();

      a.g r();

      boolean s();

      int t();

      boolean u();

      a.g v();

      boolean w();

      a.g x();

      boolean y();

      a.g z();

      boolean A();

      a.g B();

      boolean C();

      a.g D();

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
   }

   public static final class e extends p implements an.c.f {
      private static final an.c.e r = new an.c.e(true);
      private final ap s;
      public static ab<an.c.e> a = new a.c<an.c.e>() {
         public an.c.e c(a.h var1, n var2) throws s {
            return new an.c.e(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int t;
      public static final int b = 1;
      private int u;
      public static final int c = 2;
      private int v;
      public static final int d = 3;
      private int w;
      public static final int e = 4;
      private int x;
      public static final int f = 5;
      private int y;
      public static final int g = 6;
      private a.g z;
      public static final int h = 7;
      private int A;
      public static final int i = 8;
      private int B;
      public static final int j = 9;
      private int C;
      public static final int k = 10;
      private int D;
      public static final int l = 11;
      private int E;
      public static final int n = 12;
      private int F;
      public static final int o = 13;
      private int G;
      public static final int p = 14;
      private int H;
      public static final int q = 15;
      private int I;
      private byte J = -1;
      private int K = -1;
      private static final long L = 0L;

      static {
         r.ag();
      }

      private e(a.p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private e(boolean var1) {
         this.s = ap.c();
      }

      public static an.c.e h() {
         return r;
      }

      public an.c.e k() {
         return r;
      }

      @Override
      public final ap b_() {
         return this.s;
      }

      private e(a.h var1, n var2) throws s {
         this.ag();
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
                     this.t |= 1;
                     this.u = var1.g();
                     break;
                  case 16:
                     this.t |= 2;
                     this.v = var1.g();
                     break;
                  case 24:
                     this.t |= 4;
                     this.w = var1.g();
                     break;
                  case 32:
                     this.t |= 8;
                     this.x = var1.g();
                     break;
                  case 40:
                     this.t |= 16;
                     this.y = var1.g();
                     break;
                  case 50:
                     this.t |= 32;
                     this.z = var1.l();
                     break;
                  case 56:
                     this.t |= 64;
                     this.A = var1.g();
                     break;
                  case 64:
                     this.t |= 128;
                     this.B = var1.g();
                     break;
                  case 72:
                     this.t |= 256;
                     this.C = var1.g();
                     break;
                  case 80:
                     this.t |= 512;
                     this.D = var1.g();
                     break;
                  case 88:
                     this.t |= 1024;
                     this.E = var1.g();
                     break;
                  case 96:
                     this.t |= 2048;
                     this.F = var1.g();
                     break;
                  case 104:
                     this.t |= 4096;
                     this.G = var1.g();
                     break;
                  case 112:
                     this.t |= 8192;
                     this.H = var1.g();
                     break;
                  case 120:
                     this.t |= 16384;
                     this.I = var1.g();
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
            this.s = var4.b();
            this.ad();
         }
      }

      public static final a.k.a n() {
         return an.c.c;
      }

      @Override
      protected a.p.g l() {
         return an.c.d.a(an.c.e.class, an.c.e.a.class);
      }

      @Override
      public ab<an.c.e> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.t & 1) == 1;
      }

      @Override
      public int p() {
         return this.u;
      }

      @Override
      public boolean q() {
         return (this.t & 2) == 2;
      }

      @Override
      public int r() {
         return this.v;
      }

      @Override
      public boolean s() {
         return (this.t & 4) == 4;
      }

      @Override
      public int t() {
         return this.w;
      }

      @Override
      public boolean u() {
         return (this.t & 8) == 8;
      }

      @Override
      public int v() {
         return this.x;
      }

      @Override
      public boolean w() {
         return (this.t & 16) == 16;
      }

      @Override
      public int x() {
         return this.y;
      }

      @Override
      public boolean y() {
         return (this.t & 32) == 32;
      }

      @Override
      public a.g z() {
         return this.z;
      }

      @Override
      public boolean A() {
         return (this.t & 64) == 64;
      }

      @Override
      public int B() {
         return this.A;
      }

      @Override
      public boolean C() {
         return (this.t & 128) == 128;
      }

      @Override
      public int D() {
         return this.B;
      }

      @Override
      public boolean E() {
         return (this.t & 256) == 256;
      }

      @Override
      public int F() {
         return this.C;
      }

      @Override
      public boolean G() {
         return (this.t & 512) == 512;
      }

      @Override
      public int H() {
         return this.D;
      }

      @Override
      public boolean K() {
         return (this.t & 1024) == 1024;
      }

      @Override
      public int L() {
         return this.E;
      }

      @Override
      public boolean S() {
         return (this.t & 2048) == 2048;
      }

      @Override
      public int T() {
         return this.F;
      }

      @Override
      public boolean U() {
         return (this.t & 4096) == 4096;
      }

      @Override
      public int V() {
         return this.G;
      }

      @Override
      public boolean W() {
         return (this.t & 8192) == 8192;
      }

      @Override
      public int X() {
         return this.H;
      }

      @Override
      public boolean Y() {
         return (this.t & 16384) == 16384;
      }

      @Override
      public int Z() {
         return this.I;
      }

      private void ag() {
         this.u = 0;
         this.v = 0;
         this.w = 0;
         this.x = 0;
         this.y = 0;
         this.z = a.g.d;
         this.A = 0;
         this.B = 0;
         this.C = 0;
         this.D = 0;
         this.E = 0;
         this.F = 0;
         this.G = 0;
         this.H = 0;
         this.I = 0;
      }

      @Override
      public final boolean a() {
         byte var1 = this.J;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.J = 1;
         return true;
      }

      @Override
      public void a(a.i var1) throws IOException {
         this.d();
         if ((this.t & 1) == 1) {
            var1.a(1, this.u);
         }

         if ((this.t & 2) == 2) {
            var1.a(2, this.v);
         }

         if ((this.t & 4) == 4) {
            var1.a(3, this.w);
         }

         if ((this.t & 8) == 8) {
            var1.a(4, this.x);
         }

         if ((this.t & 16) == 16) {
            var1.a(5, this.y);
         }

         if ((this.t & 32) == 32) {
            var1.a(6, this.z);
         }

         if ((this.t & 64) == 64) {
            var1.a(7, this.A);
         }

         if ((this.t & 128) == 128) {
            var1.a(8, this.B);
         }

         if ((this.t & 256) == 256) {
            var1.a(9, this.C);
         }

         if ((this.t & 512) == 512) {
            var1.a(10, this.D);
         }

         if ((this.t & 1024) == 1024) {
            var1.a(11, this.E);
         }

         if ((this.t & 2048) == 2048) {
            var1.a(12, this.F);
         }

         if ((this.t & 4096) == 4096) {
            var1.a(13, this.G);
         }

         if ((this.t & 8192) == 8192) {
            var1.a(14, this.H);
         }

         if ((this.t & 16384) == 16384) {
            var1.a(15, this.I);
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.K;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.t & 1) == 1) {
            var1 += a.i.g(1, this.u);
         }

         if ((this.t & 2) == 2) {
            var1 += a.i.g(2, this.v);
         }

         if ((this.t & 4) == 4) {
            var1 += a.i.g(3, this.w);
         }

         if ((this.t & 8) == 8) {
            var1 += a.i.g(4, this.x);
         }

         if ((this.t & 16) == 16) {
            var1 += a.i.g(5, this.y);
         }

         if ((this.t & 32) == 32) {
            var1 += a.i.c(6, this.z);
         }

         if ((this.t & 64) == 64) {
            var1 += a.i.g(7, this.A);
         }

         if ((this.t & 128) == 128) {
            var1 += a.i.g(8, this.B);
         }

         if ((this.t & 256) == 256) {
            var1 += a.i.g(9, this.C);
         }

         if ((this.t & 512) == 512) {
            var1 += a.i.g(10, this.D);
         }

         if ((this.t & 1024) == 1024) {
            var1 += a.i.g(11, this.E);
         }

         if ((this.t & 2048) == 2048) {
            var1 += a.i.g(12, this.F);
         }

         if ((this.t & 4096) == 4096) {
            var1 += a.i.g(13, this.G);
         }

         if ((this.t & 8192) == 8192) {
            var1 += a.i.g(14, this.H);
         }

         if ((this.t & 16384) == 16384) {
            var1 += a.i.g(15, this.I);
         }

         var1 += this.b_().d();
         this.K = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static an.c.e a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.c.e a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.c.e a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.c.e a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.c.e a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.c.e a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.c.e b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.c.e b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.c.e a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.c.e a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.c.e.a aa() {
         return an.c.e.a.av();
      }

      public an.c.e.a ab() {
         return aa();
      }

      public static an.c.e.a a(an.c.e var0) {
         return aa().a(var0);
      }

      public an.c.e.a ae() {
         return a(this);
      }

      protected an.c.e.a a(a.p.b var1) {
         return new an.c.e.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.ae();
      }

      // $VF: synthetic method
      @Override
      public a.x.a M() {
         return this.ae();
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
         return this.ab();
      }

      // $VF: synthetic method
      @Override
      public a.x.a N() {
         return this.ab();
      }

      // $VF: synthetic method
      e(a.h var1, n var2, an.c.e var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      e(a.p.a var1, an.c.e var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.c.e.a> implements an.c.f {
         private int a;
         private int b;
         private int c;
         private int d;
         private int e;
         private int f;
         private a.g g = a.g.d;
         private int h;
         private int i;
         private int j;
         private int k;
         private int l;
         private int m;
         private int n;
         private int o;
         private int p;

         public static final a.k.a k() {
            return an.c.c;
         }

         @Override
         protected a.p.g l() {
            return an.c.d.a(an.c.e.class, an.c.e.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            an.c.e.m;
         }

         private static an.c.e.a av() {
            return new an.c.e.a();
         }

         public an.c.e.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = 0;
            this.a &= -3;
            this.d = 0;
            this.a &= -5;
            this.e = 0;
            this.a &= -9;
            this.f = 0;
            this.a &= -17;
            this.g = a.g.d;
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
            return this;
         }

         public an.c.e.a n() {
            return av().a(this.N());
         }

         @Override
         public a.k.a J() {
            return an.c.c;
         }

         public an.c.e I() {
            return an.c.e.h();
         }

         public an.c.e M() {
            an.c.e var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.c.e N() {
            an.c.e var1 = new an.c.e(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((var2 & 1) == 1) {
               var3 |= 1;
            }

            var1.u = this.b;
            if ((var2 & 2) == 2) {
               var3 |= 2;
            }

            var1.v = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 4;
            }

            var1.w = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 8;
            }

            var1.x = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 16;
            }

            var1.y = this.f;
            if ((var2 & 32) == 32) {
               var3 |= 32;
            }

            var1.z = this.g;
            if ((var2 & 64) == 64) {
               var3 |= 64;
            }

            var1.A = this.h;
            if ((var2 & 128) == 128) {
               var3 |= 128;
            }

            var1.B = this.i;
            if ((var2 & 256) == 256) {
               var3 |= 256;
            }

            var1.C = this.j;
            if ((var2 & 512) == 512) {
               var3 |= 512;
            }

            var1.D = this.k;
            if ((var2 & 1024) == 1024) {
               var3 |= 1024;
            }

            var1.E = this.l;
            if ((var2 & 2048) == 2048) {
               var3 |= 2048;
            }

            var1.F = this.m;
            if ((var2 & 4096) == 4096) {
               var3 |= 4096;
            }

            var1.G = this.n;
            if ((var2 & 8192) == 8192) {
               var3 |= 8192;
            }

            var1.H = this.o;
            if ((var2 & 16384) == 16384) {
               var3 |= 16384;
            }

            var1.I = this.p;
            var1.t = var3;
            this.q_();
            return var1;
         }

         public an.c.e.a d(x var1) {
            if (var1 instanceof an.c.e) {
               return this.a((an.c.e)var1);
            }

            super.a(var1);
            return this;
         }

         public an.c.e.a a(an.c.e var1) {
            if (var1 == an.c.e.h()) {
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
               this.e(var1.z());
            }

            if (var1.A()) {
               this.f(var1.B());
            }

            if (var1.C()) {
               this.g(var1.D());
            }

            if (var1.E()) {
               this.h(var1.F());
            }

            if (var1.G()) {
               this.i(var1.H());
            }

            if (var1.K()) {
               this.j(var1.L());
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

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public an.c.e.a e(a.h var1, n var2) throws IOException {
            an.c.e var3 = null;

            try {
               var3 = an.c.e.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.c.e)var8.a();
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

         public an.c.e.a a(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public an.c.e.a O() {
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

         public an.c.e.a b(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public an.c.e.a P() {
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

         public an.c.e.a c(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.c.e.a aa() {
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

         public an.c.e.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.c.e.a ab() {
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

         public an.c.e.a e(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.c.e.a ac() {
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
         public a.g z() {
            return this.g;
         }

         public an.c.e.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public an.c.e.a ad() {
            this.a &= -33;
            this.g = an.c.e.h().z();
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

         public an.c.e.a f(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public an.c.e.a ae() {
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

         public an.c.e.a g(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public an.c.e.a af() {
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

         public an.c.e.a h(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public an.c.e.a ag() {
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

         public an.c.e.a i(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public an.c.e.a an() {
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

         public an.c.e.a j(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public an.c.e.a ao() {
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

         public an.c.e.a k(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public an.c.e.a ap() {
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

         public an.c.e.a l(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public an.c.e.a aq() {
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

         public an.c.e.a m(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public an.c.e.a ar() {
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

         public an.c.e.a n(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public an.c.e.a as() {
            this.a &= -16385;
            this.p = 0;
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
         a(a.p.b var1, an.c.e.a var2) {
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

      a.g z();

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
   }

   public static final class g extends p implements an.c.h {
      private static final an.c.g r = new an.c.g(true);
      private final ap s;
      public static ab<an.c.g> a = new a.c<an.c.g>() {
         public an.c.g c(a.h var1, n var2) throws s {
            return new an.c.g(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int t;
      public static final int b = 1;
      private List<a.g> u;
      public static final int c = 2;
      private int v;
      public static final int d = 3;
      private int w;
      public static final int e = 4;
      private int x;
      public static final int f = 5;
      private int y;
      public static final int g = 6;
      private int z;
      public static final int h = 7;
      private int A;
      public static final int i = 8;
      private int B;
      public static final int j = 9;
      private int C;
      public static final int k = 10;
      private int D;
      public static final int l = 11;
      private int E;
      public static final int n = 12;
      private int F;
      public static final int o = 13;
      private int G;
      public static final int p = 14;
      private int H;
      public static final int q = 15;
      private int I;
      private byte J = -1;
      private int K = -1;
      private static final long L = 0L;

      static {
         r.ag();
      }

      private g(a.p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private g(boolean var1) {
         this.s = ap.c();
      }

      public static an.c.g h() {
         return r;
      }

      public an.c.g k() {
         return r;
      }

      @Override
      public final ap b_() {
         return this.s;
      }

      private g(a.h var1, n var2) throws s {
         this.ag();
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
                  case 10:
                     if ((var3 & 1) != 1) {
                        this.u = new ArrayList<>();
                        var3 |= 1;
                     }

                     this.u.add(var1.l());
                     break;
                  case 16:
                     this.t |= 1;
                     this.v = var1.g();
                     break;
                  case 24:
                     this.t |= 2;
                     this.w = var1.g();
                     break;
                  case 32:
                     this.t |= 4;
                     this.x = var1.g();
                     break;
                  case 40:
                     this.t |= 8;
                     this.y = var1.g();
                     break;
                  case 48:
                     this.t |= 16;
                     this.z = var1.g();
                     break;
                  case 56:
                     this.t |= 32;
                     this.A = var1.g();
                     break;
                  case 64:
                     this.t |= 64;
                     this.B = var1.g();
                     break;
                  case 72:
                     this.t |= 128;
                     this.C = var1.g();
                     break;
                  case 80:
                     this.t |= 256;
                     this.D = var1.g();
                     break;
                  case 88:
                     this.t |= 512;
                     this.E = var1.g();
                     break;
                  case 96:
                     this.t |= 1024;
                     this.F = var1.g();
                     break;
                  case 104:
                     this.t |= 2048;
                     this.G = var1.g();
                     break;
                  case 112:
                     this.t |= 4096;
                     this.H = var1.g();
                     break;
                  case 120:
                     this.t |= 8192;
                     this.I = var1.g();
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
            if ((var3 & 1) == 1) {
               this.u = Collections.unmodifiableList(this.u);
            }

            this.s = var4.b();
            this.ad();
         }
      }

      public static final a.k.a n() {
         return an.c.e;
      }

      @Override
      protected a.p.g l() {
         return an.c.f.a(an.c.g.class, an.c.g.a.class);
      }

      @Override
      public ab<an.c.g> m() {
         return a;
      }

      @Override
      public List<a.g> o() {
         return this.u;
      }

      @Override
      public int p() {
         return this.u.size();
      }

      @Override
      public a.g a(int var1) {
         return this.u.get(var1);
      }

      @Override
      public boolean q() {
         return (this.t & 1) == 1;
      }

      @Override
      public int r() {
         return this.v;
      }

      @Override
      public boolean s() {
         return (this.t & 2) == 2;
      }

      @Override
      public int t() {
         return this.w;
      }

      @Override
      public boolean u() {
         return (this.t & 4) == 4;
      }

      @Override
      public int v() {
         return this.x;
      }

      @Override
      public boolean w() {
         return (this.t & 8) == 8;
      }

      @Override
      public int x() {
         return this.y;
      }

      @Override
      public boolean y() {
         return (this.t & 16) == 16;
      }

      @Override
      public int z() {
         return this.z;
      }

      @Override
      public boolean A() {
         return (this.t & 32) == 32;
      }

      @Override
      public int B() {
         return this.A;
      }

      @Override
      public boolean C() {
         return (this.t & 64) == 64;
      }

      @Override
      public int D() {
         return this.B;
      }

      @Override
      public boolean E() {
         return (this.t & 128) == 128;
      }

      @Override
      public int F() {
         return this.C;
      }

      @Override
      public boolean G() {
         return (this.t & 256) == 256;
      }

      @Override
      public int H() {
         return this.D;
      }

      @Override
      public boolean K() {
         return (this.t & 512) == 512;
      }

      @Override
      public int L() {
         return this.E;
      }

      @Override
      public boolean S() {
         return (this.t & 1024) == 1024;
      }

      @Override
      public int T() {
         return this.F;
      }

      @Override
      public boolean U() {
         return (this.t & 2048) == 2048;
      }

      @Override
      public int V() {
         return this.G;
      }

      @Override
      public boolean W() {
         return (this.t & 4096) == 4096;
      }

      @Override
      public int X() {
         return this.H;
      }

      @Override
      public boolean Y() {
         return (this.t & 8192) == 8192;
      }

      @Override
      public int Z() {
         return this.I;
      }

      private void ag() {
         this.u = Collections.emptyList();
         this.v = 0;
         this.w = 0;
         this.x = 0;
         this.y = 0;
         this.z = 0;
         this.A = 0;
         this.B = 0;
         this.C = 0;
         this.D = 0;
         this.E = 0;
         this.F = 0;
         this.G = 0;
         this.H = 0;
         this.I = 0;
      }

      @Override
      public final boolean a() {
         byte var1 = this.J;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.J = 1;
         return true;
      }

      @Override
      public void a(a.i var1) throws IOException {
         this.d();

         for (int var2 = 0; var2 < this.u.size(); var2++) {
            var1.a(1, this.u.get(var2));
         }

         if ((this.t & 1) == 1) {
            var1.a(2, this.v);
         }

         if ((this.t & 2) == 2) {
            var1.a(3, this.w);
         }

         if ((this.t & 4) == 4) {
            var1.a(4, this.x);
         }

         if ((this.t & 8) == 8) {
            var1.a(5, this.y);
         }

         if ((this.t & 16) == 16) {
            var1.a(6, this.z);
         }

         if ((this.t & 32) == 32) {
            var1.a(7, this.A);
         }

         if ((this.t & 64) == 64) {
            var1.a(8, this.B);
         }

         if ((this.t & 128) == 128) {
            var1.a(9, this.C);
         }

         if ((this.t & 256) == 256) {
            var1.a(10, this.D);
         }

         if ((this.t & 512) == 512) {
            var1.a(11, this.E);
         }

         if ((this.t & 1024) == 1024) {
            var1.a(12, this.F);
         }

         if ((this.t & 2048) == 2048) {
            var1.a(13, this.G);
         }

         if ((this.t & 4096) == 4096) {
            var1.a(14, this.H);
         }

         if ((this.t & 8192) == 8192) {
            var1.a(15, this.I);
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.K;
         if (var1 != -1) {
            return var1;
         }

         int var4 = 0;
         int var2 = 0;

         for (int var3 = 0; var3 < this.u.size(); var3++) {
            var2 += a.i.b(this.u.get(var3));
         }

         var4 += var2;
         var4 += 1 * this.o().size();
         if ((this.t & 1) == 1) {
            var4 += a.i.g(2, this.v);
         }

         if ((this.t & 2) == 2) {
            var4 += a.i.g(3, this.w);
         }

         if ((this.t & 4) == 4) {
            var4 += a.i.g(4, this.x);
         }

         if ((this.t & 8) == 8) {
            var4 += a.i.g(5, this.y);
         }

         if ((this.t & 16) == 16) {
            var4 += a.i.g(6, this.z);
         }

         if ((this.t & 32) == 32) {
            var4 += a.i.g(7, this.A);
         }

         if ((this.t & 64) == 64) {
            var4 += a.i.g(8, this.B);
         }

         if ((this.t & 128) == 128) {
            var4 += a.i.g(9, this.C);
         }

         if ((this.t & 256) == 256) {
            var4 += a.i.g(10, this.D);
         }

         if ((this.t & 512) == 512) {
            var4 += a.i.g(11, this.E);
         }

         if ((this.t & 1024) == 1024) {
            var4 += a.i.g(12, this.F);
         }

         if ((this.t & 2048) == 2048) {
            var4 += a.i.g(13, this.G);
         }

         if ((this.t & 4096) == 4096) {
            var4 += a.i.g(14, this.H);
         }

         if ((this.t & 8192) == 8192) {
            var4 += a.i.g(15, this.I);
         }

         var4 += this.b_().d();
         this.K = var4;
         return var4;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static an.c.g a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.c.g a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.c.g a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.c.g a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.c.g a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.c.g a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.c.g b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.c.g b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.c.g a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.c.g a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.c.g.a aa() {
         return an.c.g.a.av();
      }

      public an.c.g.a ab() {
         return aa();
      }

      public static an.c.g.a a(an.c.g var0) {
         return aa().a(var0);
      }

      public an.c.g.a ae() {
         return a(this);
      }

      protected an.c.g.a a(a.p.b var1) {
         return new an.c.g.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.ae();
      }

      // $VF: synthetic method
      @Override
      public a.x.a M() {
         return this.ae();
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
         return this.ab();
      }

      // $VF: synthetic method
      @Override
      public a.x.a N() {
         return this.ab();
      }

      // $VF: synthetic method
      g(a.h var1, n var2, an.c.g var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      g(a.p.a var1, an.c.g var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.c.g.a> implements an.c.h {
         private int a;
         private List<a.g> b = Collections.emptyList();
         private int c;
         private int d;
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

         public static final a.k.a k() {
            return an.c.e;
         }

         @Override
         protected a.p.g l() {
            return an.c.f.a(an.c.g.class, an.c.g.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            an.c.g.m;
         }

         private static an.c.g.a av() {
            return new an.c.g.a();
         }

         public an.c.g.a m() {
            super.ah();
            this.b = Collections.emptyList();
            this.a &= -2;
            this.c = 0;
            this.a &= -3;
            this.d = 0;
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
            return this;
         }

         public an.c.g.a n() {
            return av().a(this.N());
         }

         @Override
         public a.k.a J() {
            return an.c.e;
         }

         public an.c.g I() {
            return an.c.g.h();
         }

         public an.c.g M() {
            an.c.g var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.c.g N() {
            an.c.g var1 = new an.c.g(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((this.a & 1) == 1) {
               this.b = Collections.unmodifiableList(this.b);
               this.a &= -2;
            }

            var1.u = this.b;
            if ((var2 & 2) == 2) {
               var3 |= 1;
            }

            var1.v = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 2;
            }

            var1.w = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 4;
            }

            var1.x = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 8;
            }

            var1.y = this.f;
            if ((var2 & 32) == 32) {
               var3 |= 16;
            }

            var1.z = this.g;
            if ((var2 & 64) == 64) {
               var3 |= 32;
            }

            var1.A = this.h;
            if ((var2 & 128) == 128) {
               var3 |= 64;
            }

            var1.B = this.i;
            if ((var2 & 256) == 256) {
               var3 |= 128;
            }

            var1.C = this.j;
            if ((var2 & 512) == 512) {
               var3 |= 256;
            }

            var1.D = this.k;
            if ((var2 & 1024) == 1024) {
               var3 |= 512;
            }

            var1.E = this.l;
            if ((var2 & 2048) == 2048) {
               var3 |= 1024;
            }

            var1.F = this.m;
            if ((var2 & 4096) == 4096) {
               var3 |= 2048;
            }

            var1.G = this.n;
            if ((var2 & 8192) == 8192) {
               var3 |= 4096;
            }

            var1.H = this.o;
            if ((var2 & 16384) == 16384) {
               var3 |= 8192;
            }

            var1.I = this.p;
            var1.t = var3;
            this.q_();
            return var1;
         }

         public an.c.g.a d(x var1) {
            if (var1 instanceof an.c.g) {
               return this.a((an.c.g)var1);
            }

            super.a(var1);
            return this;
         }

         public an.c.g.a a(an.c.g var1) {
            if (var1 == an.c.g.h()) {
               return this;
            }

            if (!var1.u.isEmpty()) {
               if (this.b.isEmpty()) {
                  this.b = var1.u;
                  this.a &= -2;
               } else {
                  this.aw();
                  this.b.addAll(var1.u);
               }

               this.t_();
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

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public an.c.g.a e(a.h var1, n var2) throws IOException {
            an.c.g var3 = null;

            try {
               var3 = an.c.g.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.c.g)var8.a();
               throw var8;
            } finally {
               if (var3 != null) {
                  this.a(var3);
               }
            }

            return this;
         }

         private void aw() {
            if ((this.a & 1) != 1) {
               this.b = new ArrayList<>(this.b);
               this.a |= 1;
            }
         }

         @Override
         public List<a.g> o() {
            return Collections.unmodifiableList(this.b);
         }

         @Override
         public int p() {
            return this.b.size();
         }

         @Override
         public a.g a(int var1) {
            return this.b.get(var1);
         }

         public an.c.g.a a(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.b.set(var1, var2);
            this.t_();
            return this;
         }

         public an.c.g.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.b.add(var1);
            this.t_();
            return this;
         }

         public an.c.g.a a(Iterable<? extends a.g> var1) {
            this.aw();
            a.p.a.a(var1, this.b);
            this.t_();
            return this;
         }

         public an.c.g.a O() {
            this.b = Collections.emptyList();
            this.a &= -2;
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

         public an.c.g.a b(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public an.c.g.a P() {
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

         public an.c.g.a c(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.c.g.a aa() {
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

         public an.c.g.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.c.g.a ab() {
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

         public an.c.g.a e(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.c.g.a ac() {
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

         public an.c.g.a f(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public an.c.g.a ad() {
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

         public an.c.g.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public an.c.g.a ae() {
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

         public an.c.g.a h(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public an.c.g.a af() {
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

         public an.c.g.a i(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public an.c.g.a ag() {
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

         public an.c.g.a j(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public an.c.g.a an() {
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

         public an.c.g.a k(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public an.c.g.a ao() {
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

         public an.c.g.a l(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public an.c.g.a ap() {
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

         public an.c.g.a m(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public an.c.g.a aq() {
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

         public an.c.g.a n(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public an.c.g.a ar() {
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

         public an.c.g.a o(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public an.c.g.a as() {
            this.a &= -16385;
            this.p = 0;
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
         a(a.p.b var1, an.c.g.a var2) {
            this(var1);
         }
      }
   }

   public interface h extends aa {
      List<a.g> o();

      int p();

      a.g a(int var1);

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
   }

   public static final class i extends p implements an.c.j {
      private static final an.c.i r = new an.c.i(true);
      private final ap s;
      public static ab<an.c.i> a = new a.c<an.c.i>() {
         public an.c.i c(a.h var1, n var2) throws s {
            return new an.c.i(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int t;
      public static final int b = 1;
      private a.g u;
      public static final int c = 2;
      private a.g v;
      public static final int d = 3;
      private int w;
      public static final int e = 4;
      private int x;
      public static final int f = 5;
      private int y;
      public static final int g = 6;
      private a.g z;
      public static final int h = 7;
      private int A;
      public static final int i = 8;
      private int B;
      public static final int j = 9;
      private int C;
      public static final int k = 10;
      private int D;
      public static final int l = 11;
      private int E;
      public static final int n = 12;
      private int F;
      public static final int o = 13;
      private int G;
      public static final int p = 14;
      private int H;
      public static final int q = 15;
      private int I;
      private byte J = -1;
      private int K = -1;
      private static final long L = 0L;

      static {
         r.ag();
      }

      private i(a.p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private i(boolean var1) {
         this.s = ap.c();
      }

      public static an.c.i h() {
         return r;
      }

      public an.c.i k() {
         return r;
      }

      @Override
      public final ap b_() {
         return this.s;
      }

      private i(a.h var1, n var2) throws s {
         this.ag();
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
                  case 10:
                     this.t |= 1;
                     this.u = var1.l();
                     break;
                  case 18:
                     this.t |= 2;
                     this.v = var1.l();
                     break;
                  case 24:
                     this.t |= 4;
                     this.w = var1.g();
                     break;
                  case 32:
                     this.t |= 8;
                     this.x = var1.g();
                     break;
                  case 40:
                     this.t |= 16;
                     this.y = var1.g();
                     break;
                  case 50:
                     this.t |= 32;
                     this.z = var1.l();
                     break;
                  case 56:
                     this.t |= 64;
                     this.A = var1.g();
                     break;
                  case 64:
                     this.t |= 128;
                     this.B = var1.g();
                     break;
                  case 72:
                     this.t |= 256;
                     this.C = var1.g();
                     break;
                  case 80:
                     this.t |= 512;
                     this.D = var1.g();
                     break;
                  case 88:
                     this.t |= 1024;
                     this.E = var1.g();
                     break;
                  case 96:
                     this.t |= 2048;
                     this.F = var1.g();
                     break;
                  case 104:
                     this.t |= 4096;
                     this.G = var1.g();
                     break;
                  case 112:
                     this.t |= 8192;
                     this.H = var1.g();
                     break;
                  case 120:
                     this.t |= 16384;
                     this.I = var1.g();
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
            this.s = var4.b();
            this.ad();
         }
      }

      public static final a.k.a n() {
         return an.c.g;
      }

      @Override
      protected a.p.g l() {
         return an.c.h.a(an.c.i.class, an.c.i.a.class);
      }

      @Override
      public ab<an.c.i> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.t & 1) == 1;
      }

      @Override
      public a.g p() {
         return this.u;
      }

      @Override
      public boolean q() {
         return (this.t & 2) == 2;
      }

      @Override
      public a.g r() {
         return this.v;
      }

      @Override
      public boolean s() {
         return (this.t & 4) == 4;
      }

      @Override
      public int t() {
         return this.w;
      }

      @Override
      public boolean u() {
         return (this.t & 8) == 8;
      }

      @Override
      public int v() {
         return this.x;
      }

      @Override
      public boolean w() {
         return (this.t & 16) == 16;
      }

      @Override
      public int x() {
         return this.y;
      }

      @Override
      public boolean y() {
         return (this.t & 32) == 32;
      }

      @Override
      public a.g z() {
         return this.z;
      }

      @Override
      public boolean A() {
         return (this.t & 64) == 64;
      }

      @Override
      public int B() {
         return this.A;
      }

      @Override
      public boolean C() {
         return (this.t & 128) == 128;
      }

      @Override
      public int D() {
         return this.B;
      }

      @Override
      public boolean E() {
         return (this.t & 256) == 256;
      }

      @Override
      public int F() {
         return this.C;
      }

      @Override
      public boolean G() {
         return (this.t & 512) == 512;
      }

      @Override
      public int H() {
         return this.D;
      }

      @Override
      public boolean K() {
         return (this.t & 1024) == 1024;
      }

      @Override
      public int L() {
         return this.E;
      }

      @Override
      public boolean S() {
         return (this.t & 2048) == 2048;
      }

      @Override
      public int T() {
         return this.F;
      }

      @Override
      public boolean U() {
         return (this.t & 4096) == 4096;
      }

      @Override
      public int V() {
         return this.G;
      }

      @Override
      public boolean W() {
         return (this.t & 8192) == 8192;
      }

      @Override
      public int X() {
         return this.H;
      }

      @Override
      public boolean Y() {
         return (this.t & 16384) == 16384;
      }

      @Override
      public int Z() {
         return this.I;
      }

      private void ag() {
         this.u = a.g.d;
         this.v = a.g.d;
         this.w = 0;
         this.x = 0;
         this.y = 0;
         this.z = a.g.d;
         this.A = 0;
         this.B = 0;
         this.C = 0;
         this.D = 0;
         this.E = 0;
         this.F = 0;
         this.G = 0;
         this.H = 0;
         this.I = 0;
      }

      @Override
      public final boolean a() {
         byte var1 = this.J;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.J = 1;
         return true;
      }

      @Override
      public void a(a.i var1) throws IOException {
         this.d();
         if ((this.t & 1) == 1) {
            var1.a(1, this.u);
         }

         if ((this.t & 2) == 2) {
            var1.a(2, this.v);
         }

         if ((this.t & 4) == 4) {
            var1.a(3, this.w);
         }

         if ((this.t & 8) == 8) {
            var1.a(4, this.x);
         }

         if ((this.t & 16) == 16) {
            var1.a(5, this.y);
         }

         if ((this.t & 32) == 32) {
            var1.a(6, this.z);
         }

         if ((this.t & 64) == 64) {
            var1.a(7, this.A);
         }

         if ((this.t & 128) == 128) {
            var1.a(8, this.B);
         }

         if ((this.t & 256) == 256) {
            var1.a(9, this.C);
         }

         if ((this.t & 512) == 512) {
            var1.a(10, this.D);
         }

         if ((this.t & 1024) == 1024) {
            var1.a(11, this.E);
         }

         if ((this.t & 2048) == 2048) {
            var1.a(12, this.F);
         }

         if ((this.t & 4096) == 4096) {
            var1.a(13, this.G);
         }

         if ((this.t & 8192) == 8192) {
            var1.a(14, this.H);
         }

         if ((this.t & 16384) == 16384) {
            var1.a(15, this.I);
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.K;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.t & 1) == 1) {
            var1 += a.i.c(1, this.u);
         }

         if ((this.t & 2) == 2) {
            var1 += a.i.c(2, this.v);
         }

         if ((this.t & 4) == 4) {
            var1 += a.i.g(3, this.w);
         }

         if ((this.t & 8) == 8) {
            var1 += a.i.g(4, this.x);
         }

         if ((this.t & 16) == 16) {
            var1 += a.i.g(5, this.y);
         }

         if ((this.t & 32) == 32) {
            var1 += a.i.c(6, this.z);
         }

         if ((this.t & 64) == 64) {
            var1 += a.i.g(7, this.A);
         }

         if ((this.t & 128) == 128) {
            var1 += a.i.g(8, this.B);
         }

         if ((this.t & 256) == 256) {
            var1 += a.i.g(9, this.C);
         }

         if ((this.t & 512) == 512) {
            var1 += a.i.g(10, this.D);
         }

         if ((this.t & 1024) == 1024) {
            var1 += a.i.g(11, this.E);
         }

         if ((this.t & 2048) == 2048) {
            var1 += a.i.g(12, this.F);
         }

         if ((this.t & 4096) == 4096) {
            var1 += a.i.g(13, this.G);
         }

         if ((this.t & 8192) == 8192) {
            var1 += a.i.g(14, this.H);
         }

         if ((this.t & 16384) == 16384) {
            var1 += a.i.g(15, this.I);
         }

         var1 += this.b_().d();
         this.K = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static an.c.i a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.c.i a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.c.i a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.c.i a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.c.i a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.c.i a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.c.i b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.c.i b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.c.i a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.c.i a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.c.i.a aa() {
         return an.c.i.a.av();
      }

      public an.c.i.a ab() {
         return aa();
      }

      public static an.c.i.a a(an.c.i var0) {
         return aa().a(var0);
      }

      public an.c.i.a ae() {
         return a(this);
      }

      protected an.c.i.a a(a.p.b var1) {
         return new an.c.i.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.ae();
      }

      // $VF: synthetic method
      @Override
      public a.x.a M() {
         return this.ae();
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
         return this.ab();
      }

      // $VF: synthetic method
      @Override
      public a.x.a N() {
         return this.ab();
      }

      // $VF: synthetic method
      i(a.h var1, n var2, an.c.i var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      i(a.p.a var1, an.c.i var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.c.i.a> implements an.c.j {
         private int a;
         private a.g b = a.g.d;
         private a.g c = a.g.d;
         private int d;
         private int e;
         private int f;
         private a.g g = a.g.d;
         private int h;
         private int i;
         private int j;
         private int k;
         private int l;
         private int m;
         private int n;
         private int o;
         private int p;

         public static final a.k.a k() {
            return an.c.g;
         }

         @Override
         protected a.p.g l() {
            return an.c.h.a(an.c.i.class, an.c.i.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            an.c.i.m;
         }

         private static an.c.i.a av() {
            return new an.c.i.a();
         }

         public an.c.i.a m() {
            super.ah();
            this.b = a.g.d;
            this.a &= -2;
            this.c = a.g.d;
            this.a &= -3;
            this.d = 0;
            this.a &= -5;
            this.e = 0;
            this.a &= -9;
            this.f = 0;
            this.a &= -17;
            this.g = a.g.d;
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
            return this;
         }

         public an.c.i.a n() {
            return av().a(this.N());
         }

         @Override
         public a.k.a J() {
            return an.c.g;
         }

         public an.c.i I() {
            return an.c.i.h();
         }

         public an.c.i M() {
            an.c.i var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.c.i N() {
            an.c.i var1 = new an.c.i(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((var2 & 1) == 1) {
               var3 |= 1;
            }

            var1.u = this.b;
            if ((var2 & 2) == 2) {
               var3 |= 2;
            }

            var1.v = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 4;
            }

            var1.w = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 8;
            }

            var1.x = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 16;
            }

            var1.y = this.f;
            if ((var2 & 32) == 32) {
               var3 |= 32;
            }

            var1.z = this.g;
            if ((var2 & 64) == 64) {
               var3 |= 64;
            }

            var1.A = this.h;
            if ((var2 & 128) == 128) {
               var3 |= 128;
            }

            var1.B = this.i;
            if ((var2 & 256) == 256) {
               var3 |= 256;
            }

            var1.C = this.j;
            if ((var2 & 512) == 512) {
               var3 |= 512;
            }

            var1.D = this.k;
            if ((var2 & 1024) == 1024) {
               var3 |= 1024;
            }

            var1.E = this.l;
            if ((var2 & 2048) == 2048) {
               var3 |= 2048;
            }

            var1.F = this.m;
            if ((var2 & 4096) == 4096) {
               var3 |= 4096;
            }

            var1.G = this.n;
            if ((var2 & 8192) == 8192) {
               var3 |= 8192;
            }

            var1.H = this.o;
            if ((var2 & 16384) == 16384) {
               var3 |= 16384;
            }

            var1.I = this.p;
            var1.t = var3;
            this.q_();
            return var1;
         }

         public an.c.i.a d(x var1) {
            if (var1 instanceof an.c.i) {
               return this.a((an.c.i)var1);
            }

            super.a(var1);
            return this;
         }

         public an.c.i.a a(an.c.i var1) {
            if (var1 == an.c.i.h()) {
               return this;
            }

            if (var1.o()) {
               this.e(var1.p());
            }

            if (var1.q()) {
               this.f(var1.r());
            }

            if (var1.s()) {
               this.a(var1.t());
            }

            if (var1.u()) {
               this.b(var1.v());
            }

            if (var1.w()) {
               this.c(var1.x());
            }

            if (var1.y()) {
               this.g(var1.z());
            }

            if (var1.A()) {
               this.d(var1.B());
            }

            if (var1.C()) {
               this.e(var1.D());
            }

            if (var1.E()) {
               this.f(var1.F());
            }

            if (var1.G()) {
               this.g(var1.H());
            }

            if (var1.K()) {
               this.h(var1.L());
            }

            if (var1.S()) {
               this.i(var1.T());
            }

            if (var1.U()) {
               this.j(var1.V());
            }

            if (var1.W()) {
               this.k(var1.X());
            }

            if (var1.Y()) {
               this.l(var1.Z());
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public an.c.i.a e(a.h var1, n var2) throws IOException {
            an.c.i var3 = null;

            try {
               var3 = an.c.i.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.c.i)var8.a();
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
         public a.g p() {
            return this.b;
         }

         public an.c.i.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public an.c.i.a O() {
            this.a &= -2;
            this.b = an.c.i.h().p();
            this.t_();
            return this;
         }

         @Override
         public boolean q() {
            return (this.a & 2) == 2;
         }

         @Override
         public a.g r() {
            return this.c;
         }

         public an.c.i.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public an.c.i.a P() {
            this.a &= -3;
            this.c = an.c.i.h().r();
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

         public an.c.i.a a(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.c.i.a aa() {
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

         public an.c.i.a b(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.c.i.a ab() {
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

         public an.c.i.a c(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.c.i.a ac() {
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
         public a.g z() {
            return this.g;
         }

         public an.c.i.a g(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public an.c.i.a ad() {
            this.a &= -33;
            this.g = an.c.i.h().z();
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

         public an.c.i.a d(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public an.c.i.a ae() {
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

         public an.c.i.a e(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public an.c.i.a af() {
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

         public an.c.i.a f(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public an.c.i.a ag() {
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

         public an.c.i.a g(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public an.c.i.a an() {
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

         public an.c.i.a h(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public an.c.i.a ao() {
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

         public an.c.i.a i(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public an.c.i.a ap() {
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

         public an.c.i.a j(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public an.c.i.a aq() {
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

         public an.c.i.a k(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public an.c.i.a ar() {
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

         public an.c.i.a l(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public an.c.i.a as() {
            this.a &= -16385;
            this.p = 0;
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
         a(a.p.b var1, an.c.i.a var2) {
            this(var1);
         }
      }
   }

   public interface j extends aa {
      boolean o();

      a.g p();

      boolean q();

      a.g r();

      boolean s();

      int t();

      boolean u();

      int v();

      boolean w();

      int x();

      boolean y();

      a.g z();

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
   }
}
