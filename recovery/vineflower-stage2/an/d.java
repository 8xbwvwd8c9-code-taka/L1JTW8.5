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

public final class d {
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
         "\n\u0013PBMessageALL4.proto\u0012 l1j.server.server.datas.protobuf\"\u008d\u0002\n\u0006type11\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0003\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0003\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0003\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0003\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0003\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0003\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0003\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0003\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0003\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0003\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0003\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0003\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0003\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0003\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0003\"\u008d\u0002\n\u0006type12\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007array_2\u0018\u0002 \u0001(\f\u0012\u000f\n\u0007array_3\u0018\u0003 \u0001(\f\u0012\u000f\n\u0007value_4",
         "\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0003\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0003\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0003\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008d\u0002\n\u0006type13\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007array_5\u0018\u0005 \u0001(\f\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012",
         "\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008d\u0002\n\u0006type14\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007array_3\u0018\u0003 \u0001(\f\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007array_6\u0018\u0006 \u0001(\f\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007array_9\u0018\t \u0001(\f\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008d\u0002\n\u0006type15\u0012\u000f\n\u0007array_1\u0018\u0001 \u0003(\f\u0012\u000f\n\u0007array_2\u0018\u0002 \u0003(\f\u0012\u000f\n\u0007value_3\u0018",
         "\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005B1\n l1j.server.server.datas.protobufB\rPBMessageALL4"
      };
      a.k.g.a var1 = new a.k.g.a() {
         @Override
         public m a(a.k.g var1) {
            an.d.k = var1;
            an.d.a = an.d.a().e().get(0);
            an.d.b = new a.p.g(
               an.d.a,
               new String[]{
                  "Value1",
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
            an.d.c = an.d.a().e().get(1);
            an.d.d = new a.p.g(
               an.d.c,
               new String[]{
                  "Value1",
                  "Array2",
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
                  "Value15"
               }
            );
            an.d.e = an.d.a().e().get(2);
            an.d.f = new a.p.g(
               an.d.e,
               new String[]{
                  "Value1",
                  "Value2",
                  "Value3",
                  "Value4",
                  "Array5",
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
            an.d.g = an.d.a().e().get(3);
            an.d.h = new a.p.g(
               an.d.g,
               new String[]{
                  "Value1",
                  "Value2",
                  "Array3",
                  "Value4",
                  "Value5",
                  "Array6",
                  "Value7",
                  "Value8",
                  "Array9",
                  "Value10",
                  "Value11",
                  "Value12",
                  "Value13",
                  "Value14",
                  "Value15"
               }
            );
            an.d.i = an.d.a().e().get(4);
            an.d.j = new a.p.g(
               an.d.i,
               new String[]{
                  "Array1",
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

   private d() {
   }

   public static void a(m var0) {
   }

   public static a.k.g a() {
      return k;
   }

   public static final class a extends p implements an.d.b {
      private static final an.d.a r = new an.d.a(true);
      private final ap s;
      public static ab<an.d.a> a = new a.c<an.d.a>() {
         public an.d.a c(a.h var1, n var2) throws s {
            return new an.d.a(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int t;
      public static final int b = 1;
      private long u;
      public static final int c = 2;
      private long v;
      public static final int d = 3;
      private long w;
      public static final int e = 4;
      private long x;
      public static final int f = 5;
      private long y;
      public static final int g = 6;
      private long z;
      public static final int h = 7;
      private long A;
      public static final int i = 8;
      private long B;
      public static final int j = 9;
      private long C;
      public static final int k = 10;
      private long D;
      public static final int l = 11;
      private long E;
      public static final int n = 12;
      private long F;
      public static final int o = 13;
      private long G;
      public static final int p = 14;
      private long H;
      public static final int q = 15;
      private long I;
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

      public static an.d.a h() {
         return r;
      }

      public an.d.a k() {
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
                     this.u = var1.f();
                     break;
                  case 16:
                     this.t |= 2;
                     this.v = var1.f();
                     break;
                  case 24:
                     this.t |= 4;
                     this.w = var1.f();
                     break;
                  case 32:
                     this.t |= 8;
                     this.x = var1.f();
                     break;
                  case 40:
                     this.t |= 16;
                     this.y = var1.f();
                     break;
                  case 48:
                     this.t |= 32;
                     this.z = var1.f();
                     break;
                  case 56:
                     this.t |= 64;
                     this.A = var1.f();
                     break;
                  case 64:
                     this.t |= 128;
                     this.B = var1.f();
                     break;
                  case 72:
                     this.t |= 256;
                     this.C = var1.f();
                     break;
                  case 80:
                     this.t |= 512;
                     this.D = var1.f();
                     break;
                  case 88:
                     this.t |= 1024;
                     this.E = var1.f();
                     break;
                  case 96:
                     this.t |= 2048;
                     this.F = var1.f();
                     break;
                  case 104:
                     this.t |= 4096;
                     this.G = var1.f();
                     break;
                  case 112:
                     this.t |= 8192;
                     this.H = var1.f();
                     break;
                  case 120:
                     this.t |= 16384;
                     this.I = var1.f();
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
         return an.d.a;
      }

      @Override
      protected a.p.g l() {
         return an.d.b.a(an.d.a.class, an.d.a.a.class);
      }

      @Override
      public ab<an.d.a> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.t & 1) == 1;
      }

      @Override
      public long p() {
         return this.u;
      }

      @Override
      public boolean q() {
         return (this.t & 2) == 2;
      }

      @Override
      public long r() {
         return this.v;
      }

      @Override
      public boolean s() {
         return (this.t & 4) == 4;
      }

      @Override
      public long t() {
         return this.w;
      }

      @Override
      public boolean u() {
         return (this.t & 8) == 8;
      }

      @Override
      public long v() {
         return this.x;
      }

      @Override
      public boolean w() {
         return (this.t & 16) == 16;
      }

      @Override
      public long x() {
         return this.y;
      }

      @Override
      public boolean y() {
         return (this.t & 32) == 32;
      }

      @Override
      public long z() {
         return this.z;
      }

      @Override
      public boolean A() {
         return (this.t & 64) == 64;
      }

      @Override
      public long B() {
         return this.A;
      }

      @Override
      public boolean C() {
         return (this.t & 128) == 128;
      }

      @Override
      public long D() {
         return this.B;
      }

      @Override
      public boolean E() {
         return (this.t & 256) == 256;
      }

      @Override
      public long F() {
         return this.C;
      }

      @Override
      public boolean G() {
         return (this.t & 512) == 512;
      }

      @Override
      public long H() {
         return this.D;
      }

      @Override
      public boolean K() {
         return (this.t & 1024) == 1024;
      }

      @Override
      public long L() {
         return this.E;
      }

      @Override
      public boolean S() {
         return (this.t & 2048) == 2048;
      }

      @Override
      public long T() {
         return this.F;
      }

      @Override
      public boolean U() {
         return (this.t & 4096) == 4096;
      }

      @Override
      public long V() {
         return this.G;
      }

      @Override
      public boolean W() {
         return (this.t & 8192) == 8192;
      }

      @Override
      public long X() {
         return this.H;
      }

      @Override
      public boolean Y() {
         return (this.t & 16384) == 16384;
      }

      @Override
      public long Z() {
         return this.I;
      }

      private void ag() {
         this.u = 0L;
         this.v = 0L;
         this.w = 0L;
         this.x = 0L;
         this.y = 0L;
         this.z = 0L;
         this.A = 0L;
         this.B = 0L;
         this.C = 0L;
         this.D = 0L;
         this.E = 0L;
         this.F = 0L;
         this.G = 0L;
         this.H = 0L;
         this.I = 0L;
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
            var1.b(1, this.u);
         }

         if ((this.t & 2) == 2) {
            var1.b(2, this.v);
         }

         if ((this.t & 4) == 4) {
            var1.b(3, this.w);
         }

         if ((this.t & 8) == 8) {
            var1.b(4, this.x);
         }

         if ((this.t & 16) == 16) {
            var1.b(5, this.y);
         }

         if ((this.t & 32) == 32) {
            var1.b(6, this.z);
         }

         if ((this.t & 64) == 64) {
            var1.b(7, this.A);
         }

         if ((this.t & 128) == 128) {
            var1.b(8, this.B);
         }

         if ((this.t & 256) == 256) {
            var1.b(9, this.C);
         }

         if ((this.t & 512) == 512) {
            var1.b(10, this.D);
         }

         if ((this.t & 1024) == 1024) {
            var1.b(11, this.E);
         }

         if ((this.t & 2048) == 2048) {
            var1.b(12, this.F);
         }

         if ((this.t & 4096) == 4096) {
            var1.b(13, this.G);
         }

         if ((this.t & 8192) == 8192) {
            var1.b(14, this.H);
         }

         if ((this.t & 16384) == 16384) {
            var1.b(15, this.I);
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
            var1 += a.i.g(6, this.z);
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

      public static an.d.a a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.d.a a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.d.a a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.d.a a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.d.a a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.d.a a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.d.a b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.d.a b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.d.a a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.d.a a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.d.a.a aa() {
         return an.d.a.a.av();
      }

      public an.d.a.a ab() {
         return aa();
      }

      public static an.d.a.a a(an.d.a var0) {
         return aa().a(var0);
      }

      public an.d.a.a ae() {
         return a(this);
      }

      protected an.d.a.a a(a.p.b var1) {
         return new an.d.a.a(var1, null);
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
      a(a.h var1, n var2, an.d.a var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      a(a.p.a var1, an.d.a var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.d.a.a> implements an.d.b {
         private int a;
         private long b;
         private long c;
         private long d;
         private long e;
         private long f;
         private long g;
         private long h;
         private long i;
         private long j;
         private long k;
         private long l;
         private long m;
         private long n;
         private long o;
         private long p;

         public static final a.k.a k() {
            return an.d.a;
         }

         @Override
         protected a.p.g l() {
            return an.d.b.a(an.d.a.class, an.d.a.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            an.d.a.m;
         }

         private static an.d.a.a av() {
            return new an.d.a.a();
         }

         public an.d.a.a m() {
            super.ah();
            this.b = 0L;
            this.a &= -2;
            this.c = 0L;
            this.a &= -3;
            this.d = 0L;
            this.a &= -5;
            this.e = 0L;
            this.a &= -9;
            this.f = 0L;
            this.a &= -17;
            this.g = 0L;
            this.a &= -33;
            this.h = 0L;
            this.a &= -65;
            this.i = 0L;
            this.a &= -129;
            this.j = 0L;
            this.a &= -257;
            this.k = 0L;
            this.a &= -513;
            this.l = 0L;
            this.a &= -1025;
            this.m = 0L;
            this.a &= -2049;
            this.n = 0L;
            this.a &= -4097;
            this.o = 0L;
            this.a &= -8193;
            this.p = 0L;
            this.a &= -16385;
            return this;
         }

         public an.d.a.a n() {
            return av().a(this.N());
         }

         @Override
         public a.k.a J() {
            return an.d.a;
         }

         public an.d.a I() {
            return an.d.a.h();
         }

         public an.d.a M() {
            an.d.a var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.d.a N() {
            an.d.a var1 = new an.d.a(this, null);
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

         public an.d.a.a d(x var1) {
            if (var1 instanceof an.d.a) {
               return this.a((an.d.a)var1);
            }

            super.a(var1);
            return this;
         }

         public an.d.a.a a(an.d.a var1) {
            if (var1 == an.d.a.h()) {
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

         public an.d.a.a e(a.h var1, n var2) throws IOException {
            an.d.a var3 = null;

            try {
               var3 = an.d.a.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.d.a)var8.a();
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
         public long p() {
            return this.b;
         }

         public an.d.a.a a(long var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public an.d.a.a O() {
            this.a &= -2;
            this.b = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean q() {
            return (this.a & 2) == 2;
         }

         @Override
         public long r() {
            return this.c;
         }

         public an.d.a.a b(long var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public an.d.a.a P() {
            this.a &= -3;
            this.c = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean s() {
            return (this.a & 4) == 4;
         }

         @Override
         public long t() {
            return this.d;
         }

         public an.d.a.a c(long var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.d.a.a aa() {
            this.a &= -5;
            this.d = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean u() {
            return (this.a & 8) == 8;
         }

         @Override
         public long v() {
            return this.e;
         }

         public an.d.a.a d(long var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.d.a.a ab() {
            this.a &= -9;
            this.e = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean w() {
            return (this.a & 16) == 16;
         }

         @Override
         public long x() {
            return this.f;
         }

         public an.d.a.a e(long var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.d.a.a ac() {
            this.a &= -17;
            this.f = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean y() {
            return (this.a & 32) == 32;
         }

         @Override
         public long z() {
            return this.g;
         }

         public an.d.a.a f(long var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public an.d.a.a ad() {
            this.a &= -33;
            this.g = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean A() {
            return (this.a & 64) == 64;
         }

         @Override
         public long B() {
            return this.h;
         }

         public an.d.a.a g(long var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public an.d.a.a ae() {
            this.a &= -65;
            this.h = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean C() {
            return (this.a & 128) == 128;
         }

         @Override
         public long D() {
            return this.i;
         }

         public an.d.a.a h(long var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public an.d.a.a af() {
            this.a &= -129;
            this.i = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean E() {
            return (this.a & 256) == 256;
         }

         @Override
         public long F() {
            return this.j;
         }

         public an.d.a.a i(long var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public an.d.a.a ag() {
            this.a &= -257;
            this.j = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean G() {
            return (this.a & 512) == 512;
         }

         @Override
         public long H() {
            return this.k;
         }

         public an.d.a.a j(long var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public an.d.a.a an() {
            this.a &= -513;
            this.k = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean K() {
            return (this.a & 1024) == 1024;
         }

         @Override
         public long L() {
            return this.l;
         }

         public an.d.a.a k(long var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public an.d.a.a ao() {
            this.a &= -1025;
            this.l = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean S() {
            return (this.a & 2048) == 2048;
         }

         @Override
         public long T() {
            return this.m;
         }

         public an.d.a.a l(long var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public an.d.a.a ap() {
            this.a &= -2049;
            this.m = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean U() {
            return (this.a & 4096) == 4096;
         }

         @Override
         public long V() {
            return this.n;
         }

         public an.d.a.a m(long var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public an.d.a.a aq() {
            this.a &= -4097;
            this.n = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean W() {
            return (this.a & 8192) == 8192;
         }

         @Override
         public long X() {
            return this.o;
         }

         public an.d.a.a n(long var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public an.d.a.a ar() {
            this.a &= -8193;
            this.o = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean Y() {
            return (this.a & 16384) == 16384;
         }

         @Override
         public long Z() {
            return this.p;
         }

         public an.d.a.a o(long var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public an.d.a.a as() {
            this.a &= -16385;
            this.p = 0L;
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
         a(a.p.b var1, an.d.a.a var2) {
            this(var1);
         }
      }
   }

   public interface b extends aa {
      boolean o();

      long p();

      boolean q();

      long r();

      boolean s();

      long t();

      boolean u();

      long v();

      boolean w();

      long x();

      boolean y();

      long z();

      boolean A();

      long B();

      boolean C();

      long D();

      boolean E();

      long F();

      boolean G();

      long H();

      boolean K();

      long L();

      boolean S();

      long T();

      boolean U();

      long V();

      boolean W();

      long X();

      boolean Y();

      long Z();
   }

   public static final class c extends p implements an.d.d {
      private static final an.d.c r = new an.d.c(true);
      private final ap s;
      public static ab<an.d.c> a = new a.c<an.d.c>() {
         public an.d.c c(a.h var1, n var2) throws s {
            return new an.d.c(var1, var2, null);
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
      private a.g w;
      public static final int e = 4;
      private int x;
      public static final int f = 5;
      private long y;
      public static final int g = 6;
      private long z;
      public static final int h = 7;
      private long A;
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

      private c(a.p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private c(boolean var1) {
         this.s = ap.c();
      }

      public static an.d.c h() {
         return r;
      }

      public an.d.c k() {
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
                  case 26:
                     this.t |= 4;
                     this.w = var1.l();
                     break;
                  case 32:
                     this.t |= 8;
                     this.x = var1.g();
                     break;
                  case 40:
                     this.t |= 16;
                     this.y = var1.f();
                     break;
                  case 48:
                     this.t |= 32;
                     this.z = var1.f();
                     break;
                  case 56:
                     this.t |= 64;
                     this.A = var1.f();
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
         return an.d.c;
      }

      @Override
      protected a.p.g l() {
         return an.d.d.a(an.d.c.class, an.d.c.a.class);
      }

      @Override
      public ab<an.d.c> m() {
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
      public a.g t() {
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
      public long x() {
         return this.y;
      }

      @Override
      public boolean y() {
         return (this.t & 32) == 32;
      }

      @Override
      public long z() {
         return this.z;
      }

      @Override
      public boolean A() {
         return (this.t & 64) == 64;
      }

      @Override
      public long B() {
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
         this.v = a.g.d;
         this.w = a.g.d;
         this.x = 0;
         this.y = 0L;
         this.z = 0L;
         this.A = 0L;
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
            var1.b(5, this.y);
         }

         if ((this.t & 32) == 32) {
            var1.b(6, this.z);
         }

         if ((this.t & 64) == 64) {
            var1.b(7, this.A);
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
            var1 += a.i.c(3, this.w);
         }

         if ((this.t & 8) == 8) {
            var1 += a.i.g(4, this.x);
         }

         if ((this.t & 16) == 16) {
            var1 += a.i.g(5, this.y);
         }

         if ((this.t & 32) == 32) {
            var1 += a.i.g(6, this.z);
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

      public static an.d.c a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.d.c a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.d.c a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.d.c a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.d.c a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.d.c a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.d.c b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.d.c b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.d.c a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.d.c a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.d.c.a aa() {
         return an.d.c.a.av();
      }

      public an.d.c.a ab() {
         return aa();
      }

      public static an.d.c.a a(an.d.c var0) {
         return aa().a(var0);
      }

      public an.d.c.a ae() {
         return a(this);
      }

      protected an.d.c.a a(a.p.b var1) {
         return new an.d.c.a(var1, null);
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
      c(a.h var1, n var2, an.d.c var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      c(a.p.a var1, an.d.c var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.d.c.a> implements an.d.d {
         private int a;
         private int b;
         private a.g c;
         private a.g d;
         private int e;
         private long f;
         private long g;
         private long h;
         private int i;
         private int j;
         private int k;
         private int l;
         private int m;
         private int n;
         private int o;
         private int p;

         public static final a.k.a k() {
            return an.d.c;
         }

         @Override
         protected a.p.g l() {
            return an.d.d.a(an.d.c.class, an.d.c.a.class);
         }

         private a() {
            this.c = a.g.d;
            this.d = a.g.d;
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.c = a.g.d;
            this.d = a.g.d;
            this.au();
         }

         private void au() {
            an.d.c.m;
         }

         private static an.d.c.a av() {
            return new an.d.c.a();
         }

         public an.d.c.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = a.g.d;
            this.a &= -3;
            this.d = a.g.d;
            this.a &= -5;
            this.e = 0;
            this.a &= -9;
            this.f = 0L;
            this.a &= -17;
            this.g = 0L;
            this.a &= -33;
            this.h = 0L;
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

         public an.d.c.a n() {
            return av().a(this.N());
         }

         @Override
         public a.k.a J() {
            return an.d.c;
         }

         public an.d.c I() {
            return an.d.c.h();
         }

         public an.d.c M() {
            an.d.c var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.d.c N() {
            an.d.c var1 = new an.d.c(this, null);
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

         public an.d.c.a d(x var1) {
            if (var1 instanceof an.d.c) {
               return this.a((an.d.c)var1);
            }

            super.a(var1);
            return this;
         }

         public an.d.c.a a(an.d.c var1) {
            if (var1 == an.d.c.h()) {
               return this;
            }

            if (var1.o()) {
               this.a(var1.p());
            }

            if (var1.q()) {
               this.e(var1.r());
            }

            if (var1.s()) {
               this.f(var1.t());
            }

            if (var1.u()) {
               this.b(var1.v());
            }

            if (var1.w()) {
               this.a(var1.x());
            }

            if (var1.y()) {
               this.b(var1.z());
            }

            if (var1.A()) {
               this.c(var1.B());
            }

            if (var1.C()) {
               this.c(var1.D());
            }

            if (var1.E()) {
               this.d(var1.F());
            }

            if (var1.G()) {
               this.e(var1.H());
            }

            if (var1.K()) {
               this.f(var1.L());
            }

            if (var1.S()) {
               this.g(var1.T());
            }

            if (var1.U()) {
               this.h(var1.V());
            }

            if (var1.W()) {
               this.i(var1.X());
            }

            if (var1.Y()) {
               this.j(var1.Z());
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public an.d.c.a e(a.h var1, n var2) throws IOException {
            an.d.c var3 = null;

            try {
               var3 = an.d.c.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.d.c)var8.a();
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

         public an.d.c.a a(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public an.d.c.a O() {
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

         public an.d.c.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public an.d.c.a P() {
            this.a &= -3;
            this.c = an.d.c.h().r();
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

         public an.d.c.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.d.c.a aa() {
            this.a &= -5;
            this.d = an.d.c.h().t();
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

         public an.d.c.a b(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.d.c.a ab() {
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
         public long x() {
            return this.f;
         }

         public an.d.c.a a(long var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.d.c.a ac() {
            this.a &= -17;
            this.f = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean y() {
            return (this.a & 32) == 32;
         }

         @Override
         public long z() {
            return this.g;
         }

         public an.d.c.a b(long var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public an.d.c.a ad() {
            this.a &= -33;
            this.g = 0L;
            this.t_();
            return this;
         }

         @Override
         public boolean A() {
            return (this.a & 64) == 64;
         }

         @Override
         public long B() {
            return this.h;
         }

         public an.d.c.a c(long var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public an.d.c.a ae() {
            this.a &= -65;
            this.h = 0L;
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

         public an.d.c.a c(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public an.d.c.a af() {
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

         public an.d.c.a d(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public an.d.c.a ag() {
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

         public an.d.c.a e(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public an.d.c.a an() {
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

         public an.d.c.a f(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public an.d.c.a ao() {
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

         public an.d.c.a g(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public an.d.c.a ap() {
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

         public an.d.c.a h(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public an.d.c.a aq() {
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

         public an.d.c.a i(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public an.d.c.a ar() {
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

         public an.d.c.a j(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public an.d.c.a as() {
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
         a(a.p.b var1, an.d.c.a var2) {
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

      a.g t();

      boolean u();

      int v();

      boolean w();

      long x();

      boolean y();

      long z();

      boolean A();

      long B();

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

   public static final class e extends p implements an.d.f {
      private static final an.d.e r = new an.d.e(true);
      private final ap s;
      public static ab<an.d.e> a = new a.c<an.d.e>() {
         public an.d.e c(a.h var1, n var2) throws s {
            return new an.d.e(var1, var2, null);
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
      private a.g y;
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

      private e(a.p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private e(boolean var1) {
         this.s = ap.c();
      }

      public static an.d.e h() {
         return r;
      }

      public an.d.e k() {
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
                  case 42:
                     this.t |= 16;
                     this.y = var1.l();
                     break;
                  case 48:
                     this.t |= 32;
                     this.z = var1.g();
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
         return an.d.e;
      }

      @Override
      protected a.p.g l() {
         return an.d.f.a(an.d.e.class, an.d.e.a.class);
      }

      @Override
      public ab<an.d.e> m() {
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
      public a.g x() {
         return this.y;
      }

      @Override
      public boolean y() {
         return (this.t & 32) == 32;
      }

      @Override
      public int z() {
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
         this.y = a.g.d;
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
            var1 += a.i.c(5, this.y);
         }

         if ((this.t & 32) == 32) {
            var1 += a.i.g(6, this.z);
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

      public static an.d.e a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.d.e a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.d.e a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.d.e a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.d.e a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.d.e a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.d.e b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.d.e b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.d.e a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.d.e a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.d.e.a aa() {
         return an.d.e.a.av();
      }

      public an.d.e.a ab() {
         return aa();
      }

      public static an.d.e.a a(an.d.e var0) {
         return aa().a(var0);
      }

      public an.d.e.a ae() {
         return a(this);
      }

      protected an.d.e.a a(a.p.b var1) {
         return new an.d.e.a(var1, null);
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
      e(a.h var1, n var2, an.d.e var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      e(a.p.a var1, an.d.e var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.d.e.a> implements an.d.f {
         private int a;
         private int b;
         private int c;
         private int d;
         private int e;
         private a.g f = a.g.d;
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
            return an.d.e;
         }

         @Override
         protected a.p.g l() {
            return an.d.f.a(an.d.e.class, an.d.e.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            an.d.e.m;
         }

         private static an.d.e.a av() {
            return new an.d.e.a();
         }

         public an.d.e.a m() {
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

         public an.d.e.a n() {
            return av().a(this.N());
         }

         @Override
         public a.k.a J() {
            return an.d.e;
         }

         public an.d.e I() {
            return an.d.e.h();
         }

         public an.d.e M() {
            an.d.e var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.d.e N() {
            an.d.e var1 = new an.d.e(this, null);
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

         public an.d.e.a d(x var1) {
            if (var1 instanceof an.d.e) {
               return this.a((an.d.e)var1);
            }

            super.a(var1);
            return this;
         }

         public an.d.e.a a(an.d.e var1) {
            if (var1 == an.d.e.h()) {
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

         public an.d.e.a e(a.h var1, n var2) throws IOException {
            an.d.e var3 = null;

            try {
               var3 = an.d.e.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.d.e)var8.a();
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

         public an.d.e.a a(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public an.d.e.a O() {
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

         public an.d.e.a b(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public an.d.e.a P() {
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

         public an.d.e.a c(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.d.e.a aa() {
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

         public an.d.e.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.d.e.a ab() {
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

         public an.d.e.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.d.e.a ac() {
            this.a &= -17;
            this.f = an.d.e.h().x();
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

         public an.d.e.a e(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public an.d.e.a ad() {
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

         public an.d.e.a f(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public an.d.e.a ae() {
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

         public an.d.e.a g(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public an.d.e.a af() {
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

         public an.d.e.a h(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public an.d.e.a ag() {
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

         public an.d.e.a i(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public an.d.e.a an() {
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

         public an.d.e.a j(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public an.d.e.a ao() {
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

         public an.d.e.a k(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public an.d.e.a ap() {
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

         public an.d.e.a l(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public an.d.e.a aq() {
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

         public an.d.e.a m(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public an.d.e.a ar() {
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

         public an.d.e.a n(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public an.d.e.a as() {
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
         a(a.p.b var1, an.d.e.a var2) {
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

      a.g x();

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

   public static final class g extends p implements an.d.h {
      private static final an.d.g r = new an.d.g(true);
      private final ap s;
      public static ab<an.d.g> a = new a.c<an.d.g>() {
         public an.d.g c(a.h var1, n var2) throws s {
            return new an.d.g(var1, var2, null);
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
      private a.g w;
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
      private a.g C;
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

      public static an.d.g h() {
         return r;
      }

      public an.d.g k() {
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
                  case 8:
                     this.t |= 1;
                     this.u = var1.g();
                     break;
                  case 16:
                     this.t |= 2;
                     this.v = var1.g();
                     break;
                  case 26:
                     this.t |= 4;
                     this.w = var1.l();
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
                  case 74:
                     this.t |= 256;
                     this.C = var1.l();
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
         return an.d.g;
      }

      @Override
      protected a.p.g l() {
         return an.d.h.a(an.d.g.class, an.d.g.a.class);
      }

      @Override
      public ab<an.d.g> m() {
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
      public a.g t() {
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
      public a.g F() {
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
         this.w = a.g.d;
         this.x = 0;
         this.y = 0;
         this.z = a.g.d;
         this.A = 0;
         this.B = 0;
         this.C = a.g.d;
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
            var1 += a.i.c(3, this.w);
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
            var1 += a.i.c(9, this.C);
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

      public static an.d.g a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.d.g a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.d.g a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.d.g a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.d.g a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.d.g a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.d.g b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.d.g b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.d.g a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.d.g a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.d.g.a aa() {
         return an.d.g.a.av();
      }

      public an.d.g.a ab() {
         return aa();
      }

      public static an.d.g.a a(an.d.g var0) {
         return aa().a(var0);
      }

      public an.d.g.a ae() {
         return a(this);
      }

      protected an.d.g.a a(a.p.b var1) {
         return new an.d.g.a(var1, null);
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
      g(a.h var1, n var2, an.d.g var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      g(a.p.a var1, an.d.g var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.d.g.a> implements an.d.h {
         private int a;
         private int b;
         private int c;
         private a.g d;
         private int e;
         private int f;
         private a.g g;
         private int h;
         private int i;
         private a.g j;
         private int k;
         private int l;
         private int m;
         private int n;
         private int o;
         private int p;

         public static final a.k.a k() {
            return an.d.g;
         }

         @Override
         protected a.p.g l() {
            return an.d.h.a(an.d.g.class, an.d.g.a.class);
         }

         private a() {
            this.d = a.g.d;
            this.g = a.g.d;
            this.j = a.g.d;
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.d = a.g.d;
            this.g = a.g.d;
            this.j = a.g.d;
            this.au();
         }

         private void au() {
            an.d.g.m;
         }

         private static an.d.g.a av() {
            return new an.d.g.a();
         }

         public an.d.g.a m() {
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
            this.g = a.g.d;
            this.a &= -33;
            this.h = 0;
            this.a &= -65;
            this.i = 0;
            this.a &= -129;
            this.j = a.g.d;
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

         public an.d.g.a n() {
            return av().a(this.N());
         }

         @Override
         public a.k.a J() {
            return an.d.g;
         }

         public an.d.g I() {
            return an.d.g.h();
         }

         public an.d.g M() {
            an.d.g var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.d.g N() {
            an.d.g var1 = new an.d.g(this, null);
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

         public an.d.g.a d(x var1) {
            if (var1 instanceof an.d.g) {
               return this.a((an.d.g)var1);
            }

            super.a(var1);
            return this;
         }

         public an.d.g.a a(an.d.g var1) {
            if (var1 == an.d.g.h()) {
               return this;
            }

            if (var1.o()) {
               this.a(var1.p());
            }

            if (var1.q()) {
               this.b(var1.r());
            }

            if (var1.s()) {
               this.e(var1.t());
            }

            if (var1.u()) {
               this.c(var1.v());
            }

            if (var1.w()) {
               this.d(var1.x());
            }

            if (var1.y()) {
               this.f(var1.z());
            }

            if (var1.A()) {
               this.e(var1.B());
            }

            if (var1.C()) {
               this.f(var1.D());
            }

            if (var1.E()) {
               this.g(var1.F());
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

         public an.d.g.a e(a.h var1, n var2) throws IOException {
            an.d.g var3 = null;

            try {
               var3 = an.d.g.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.d.g)var8.a();
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

         public an.d.g.a a(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public an.d.g.a O() {
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

         public an.d.g.a b(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public an.d.g.a P() {
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

         public an.d.g.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.d.g.a aa() {
            this.a &= -5;
            this.d = an.d.g.h().t();
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

         public an.d.g.a c(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.d.g.a ab() {
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

         public an.d.g.a d(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.d.g.a ac() {
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

         public an.d.g.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public an.d.g.a ad() {
            this.a &= -33;
            this.g = an.d.g.h().z();
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

         public an.d.g.a e(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public an.d.g.a ae() {
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

         public an.d.g.a f(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public an.d.g.a af() {
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
         public a.g F() {
            return this.j;
         }

         public an.d.g.a g(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public an.d.g.a ag() {
            this.a &= -257;
            this.j = an.d.g.h().F();
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

         public an.d.g.a g(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public an.d.g.a an() {
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

         public an.d.g.a h(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public an.d.g.a ao() {
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

         public an.d.g.a i(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public an.d.g.a ap() {
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

         public an.d.g.a j(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public an.d.g.a aq() {
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

         public an.d.g.a k(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public an.d.g.a ar() {
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

         public an.d.g.a l(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public an.d.g.a as() {
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
         a(a.p.b var1, an.d.g.a var2) {
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

      a.g t();

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

      a.g F();

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

   public static final class i extends p implements an.d.j {
      private static final an.d.i r = new an.d.i(true);
      private final ap s;
      public static ab<an.d.i> a = new a.c<an.d.i>() {
         public an.d.i c(a.h var1, n var2) throws s {
            return new an.d.i(var1, var2, null);
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

      private i(a.p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private i(boolean var1) {
         this.s = ap.c();
      }

      public static an.d.i h() {
         return r;
      }

      public an.d.i k() {
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
                     if ((var3 & 1) != 1) {
                        this.u = new ArrayList<>();
                        var3 |= 1;
                     }

                     this.u.add(var1.l());
                     break;
                  case 18:
                     if ((var3 & 2) != 2) {
                        this.v = new ArrayList<>();
                        var3 |= 2;
                     }

                     this.v.add(var1.l());
                     break;
                  case 24:
                     this.t |= 1;
                     this.w = var1.g();
                     break;
                  case 32:
                     this.t |= 2;
                     this.x = var1.g();
                     break;
                  case 40:
                     this.t |= 4;
                     this.y = var1.g();
                     break;
                  case 48:
                     this.t |= 8;
                     this.z = var1.g();
                     break;
                  case 56:
                     this.t |= 16;
                     this.A = var1.g();
                     break;
                  case 64:
                     this.t |= 32;
                     this.B = var1.g();
                     break;
                  case 72:
                     this.t |= 64;
                     this.C = var1.g();
                     break;
                  case 80:
                     this.t |= 128;
                     this.D = var1.g();
                     break;
                  case 88:
                     this.t |= 256;
                     this.E = var1.g();
                     break;
                  case 96:
                     this.t |= 512;
                     this.F = var1.g();
                     break;
                  case 104:
                     this.t |= 1024;
                     this.G = var1.g();
                     break;
                  case 112:
                     this.t |= 2048;
                     this.H = var1.g();
                     break;
                  case 120:
                     this.t |= 4096;
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

            if ((var3 & 2) == 2) {
               this.v = Collections.unmodifiableList(this.v);
            }

            this.s = var4.b();
            this.ad();
         }
      }

      public static final a.k.a n() {
         return an.d.i;
      }

      @Override
      protected a.p.g l() {
         return an.d.j.a(an.d.i.class, an.d.i.a.class);
      }

      @Override
      public ab<an.d.i> m() {
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
      public List<a.g> q() {
         return this.v;
      }

      @Override
      public int r() {
         return this.v.size();
      }

      @Override
      public a.g b(int var1) {
         return this.v.get(var1);
      }

      @Override
      public boolean s() {
         return (this.t & 1) == 1;
      }

      @Override
      public int t() {
         return this.w;
      }

      @Override
      public boolean u() {
         return (this.t & 2) == 2;
      }

      @Override
      public int v() {
         return this.x;
      }

      @Override
      public boolean w() {
         return (this.t & 4) == 4;
      }

      @Override
      public int x() {
         return this.y;
      }

      @Override
      public boolean y() {
         return (this.t & 8) == 8;
      }

      @Override
      public int z() {
         return this.z;
      }

      @Override
      public boolean A() {
         return (this.t & 16) == 16;
      }

      @Override
      public int B() {
         return this.A;
      }

      @Override
      public boolean C() {
         return (this.t & 32) == 32;
      }

      @Override
      public int D() {
         return this.B;
      }

      @Override
      public boolean E() {
         return (this.t & 64) == 64;
      }

      @Override
      public int F() {
         return this.C;
      }

      @Override
      public boolean G() {
         return (this.t & 128) == 128;
      }

      @Override
      public int H() {
         return this.D;
      }

      @Override
      public boolean K() {
         return (this.t & 256) == 256;
      }

      @Override
      public int L() {
         return this.E;
      }

      @Override
      public boolean S() {
         return (this.t & 512) == 512;
      }

      @Override
      public int T() {
         return this.F;
      }

      @Override
      public boolean U() {
         return (this.t & 1024) == 1024;
      }

      @Override
      public int V() {
         return this.G;
      }

      @Override
      public boolean W() {
         return (this.t & 2048) == 2048;
      }

      @Override
      public int X() {
         return this.H;
      }

      @Override
      public boolean Y() {
         return (this.t & 4096) == 4096;
      }

      @Override
      public int Z() {
         return this.I;
      }

      private void ag() {
         this.u = Collections.emptyList();
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

         for (int var2 = 0; var2 < this.u.size(); var2++) {
            var1.a(1, this.u.get(var2));
         }

         for (int var3 = 0; var3 < this.v.size(); var3++) {
            var1.a(2, this.v.get(var3));
         }

         if ((this.t & 1) == 1) {
            var1.a(3, this.w);
         }

         if ((this.t & 2) == 2) {
            var1.a(4, this.x);
         }

         if ((this.t & 4) == 4) {
            var1.a(5, this.y);
         }

         if ((this.t & 8) == 8) {
            var1.a(6, this.z);
         }

         if ((this.t & 16) == 16) {
            var1.a(7, this.A);
         }

         if ((this.t & 32) == 32) {
            var1.a(8, this.B);
         }

         if ((this.t & 64) == 64) {
            var1.a(9, this.C);
         }

         if ((this.t & 128) == 128) {
            var1.a(10, this.D);
         }

         if ((this.t & 256) == 256) {
            var1.a(11, this.E);
         }

         if ((this.t & 512) == 512) {
            var1.a(12, this.F);
         }

         if ((this.t & 1024) == 1024) {
            var1.a(13, this.G);
         }

         if ((this.t & 2048) == 2048) {
            var1.a(14, this.H);
         }

         if ((this.t & 4096) == 4096) {
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
         var2 = 0;

         for (int var11 = 0; var11 < this.v.size(); var11++) {
            var2 += a.i.b(this.v.get(var11));
         }

         var4 += var2;
         var4 += 1 * this.q().size();
         if ((this.t & 1) == 1) {
            var4 += a.i.g(3, this.w);
         }

         if ((this.t & 2) == 2) {
            var4 += a.i.g(4, this.x);
         }

         if ((this.t & 4) == 4) {
            var4 += a.i.g(5, this.y);
         }

         if ((this.t & 8) == 8) {
            var4 += a.i.g(6, this.z);
         }

         if ((this.t & 16) == 16) {
            var4 += a.i.g(7, this.A);
         }

         if ((this.t & 32) == 32) {
            var4 += a.i.g(8, this.B);
         }

         if ((this.t & 64) == 64) {
            var4 += a.i.g(9, this.C);
         }

         if ((this.t & 128) == 128) {
            var4 += a.i.g(10, this.D);
         }

         if ((this.t & 256) == 256) {
            var4 += a.i.g(11, this.E);
         }

         if ((this.t & 512) == 512) {
            var4 += a.i.g(12, this.F);
         }

         if ((this.t & 1024) == 1024) {
            var4 += a.i.g(13, this.G);
         }

         if ((this.t & 2048) == 2048) {
            var4 += a.i.g(14, this.H);
         }

         if ((this.t & 4096) == 4096) {
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

      public static an.d.i a(a.g var0) throws s {
         return a.d(var0);
      }

      public static an.d.i a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.d.i a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static an.d.i a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static an.d.i a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static an.d.i a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static an.d.i b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static an.d.i b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static an.d.i a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static an.d.i a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static an.d.i.a aa() {
         return an.d.i.a.av();
      }

      public an.d.i.a ab() {
         return aa();
      }

      public static an.d.i.a a(an.d.i var0) {
         return aa().a(var0);
      }

      public an.d.i.a ae() {
         return a(this);
      }

      protected an.d.i.a a(a.p.b var1) {
         return new an.d.i.a(var1, null);
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
      i(a.h var1, n var2, an.d.i var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      i(a.p.a var1, an.d.i var2) {
         this(var1);
      }

      public static final class a extends a.p.a<an.d.i.a> implements an.d.j {
         private int a;
         private List<a.g> b = Collections.emptyList();
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
            return an.d.i;
         }

         @Override
         protected a.p.g l() {
            return an.d.j.a(an.d.i.class, an.d.i.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            an.d.i.m;
         }

         private static an.d.i.a av() {
            return new an.d.i.a();
         }

         public an.d.i.a m() {
            super.ah();
            this.b = Collections.emptyList();
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

         public an.d.i.a n() {
            return av().a(this.N());
         }

         @Override
         public a.k.a J() {
            return an.d.i;
         }

         public an.d.i I() {
            return an.d.i.h();
         }

         public an.d.i M() {
            an.d.i var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public an.d.i N() {
            an.d.i var1 = new an.d.i(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((this.a & 1) == 1) {
               this.b = Collections.unmodifiableList(this.b);
               this.a &= -2;
            }

            var1.u = this.b;
            if ((this.a & 2) == 2) {
               this.c = Collections.unmodifiableList(this.c);
               this.a &= -3;
            }

            var1.v = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 1;
            }

            var1.w = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 2;
            }

            var1.x = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 4;
            }

            var1.y = this.f;
            if ((var2 & 32) == 32) {
               var3 |= 8;
            }

            var1.z = this.g;
            if ((var2 & 64) == 64) {
               var3 |= 16;
            }

            var1.A = this.h;
            if ((var2 & 128) == 128) {
               var3 |= 32;
            }

            var1.B = this.i;
            if ((var2 & 256) == 256) {
               var3 |= 64;
            }

            var1.C = this.j;
            if ((var2 & 512) == 512) {
               var3 |= 128;
            }

            var1.D = this.k;
            if ((var2 & 1024) == 1024) {
               var3 |= 256;
            }

            var1.E = this.l;
            if ((var2 & 2048) == 2048) {
               var3 |= 512;
            }

            var1.F = this.m;
            if ((var2 & 4096) == 4096) {
               var3 |= 1024;
            }

            var1.G = this.n;
            if ((var2 & 8192) == 8192) {
               var3 |= 2048;
            }

            var1.H = this.o;
            if ((var2 & 16384) == 16384) {
               var3 |= 4096;
            }

            var1.I = this.p;
            var1.t = var3;
            this.q_();
            return var1;
         }

         public an.d.i.a d(x var1) {
            if (var1 instanceof an.d.i) {
               return this.a((an.d.i)var1);
            }

            super.a(var1);
            return this;
         }

         public an.d.i.a a(an.d.i var1) {
            if (var1 == an.d.i.h()) {
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

            if (!var1.v.isEmpty()) {
               if (this.c.isEmpty()) {
                  this.c = var1.v;
                  this.a &= -3;
               } else {
                  this.ax();
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

         public an.d.i.a e(a.h var1, n var2) throws IOException {
            an.d.i var3 = null;

            try {
               var3 = an.d.i.a.d(var1, var2);
            } catch (s var8) {
               var3 = (an.d.i)var8.a();
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

         public an.d.i.a a(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.b.set(var1, var2);
            this.t_();
            return this;
         }

         public an.d.i.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.b.add(var1);
            this.t_();
            return this;
         }

         public an.d.i.a a(Iterable<? extends a.g> var1) {
            this.aw();
            a.p.a.a(var1, this.b);
            this.t_();
            return this;
         }

         public an.d.i.a O() {
            this.b = Collections.emptyList();
            this.a &= -2;
            this.t_();
            return this;
         }

         private void ax() {
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
         public a.g b(int var1) {
            return this.c.get(var1);
         }

         public an.d.i.a b(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.ax();
            this.c.set(var1, var2);
            this.t_();
            return this;
         }

         public an.d.i.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.ax();
            this.c.add(var1);
            this.t_();
            return this;
         }

         public an.d.i.a b(Iterable<? extends a.g> var1) {
            this.ax();
            a.p.a.a(var1, this.c);
            this.t_();
            return this;
         }

         public an.d.i.a P() {
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

         public an.d.i.a c(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public an.d.i.a aa() {
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

         public an.d.i.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public an.d.i.a ab() {
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

         public an.d.i.a e(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public an.d.i.a ac() {
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

         public an.d.i.a f(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public an.d.i.a ad() {
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

         public an.d.i.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public an.d.i.a ae() {
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

         public an.d.i.a h(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public an.d.i.a af() {
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

         public an.d.i.a i(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public an.d.i.a ag() {
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

         public an.d.i.a j(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public an.d.i.a an() {
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

         public an.d.i.a k(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public an.d.i.a ao() {
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

         public an.d.i.a l(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public an.d.i.a ap() {
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

         public an.d.i.a m(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public an.d.i.a aq() {
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

         public an.d.i.a n(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public an.d.i.a ar() {
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

         public an.d.i.a o(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public an.d.i.a as() {
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
         a(a.p.b var1, an.d.i.a var2) {
            this(var1);
         }
      }
   }

   public interface j extends aa {
      List<a.g> o();

      int p();

      a.g a(int var1);

      List<a.g> q();

      int r();

      a.g b(int var1);

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
}
