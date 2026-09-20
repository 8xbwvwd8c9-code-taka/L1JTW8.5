package l1r.an;

import a.aa;
import a.ab;
import a.ap;
import a.k;
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

public final class PBMessageALL {
   private static k.a a;
   private static p.g b;
   private static k.a c;
   private static p.g d;
   private static k.a e;
   private static p.g f;
   private static k.a g;
   private static p.g h;
   private static k.a i;
   private static p.g j;
   private static k.g k;

   static {
      String[] var0 = new String[]{
         "\n\u0012PBMessageALL.proto\u0012 l1j.server.server.datas.protobuf\"\u008c\u0002\n\u0005type1\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008c\u0002\n\u0005type2\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007array_3\u0018\u0003 \u0003(\f\u0012\u000f\n\u0007value_4\u0018\u0004 ",
         "\u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u009e\u0002\n\u0005type3\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007array_8\u0018\b \u0001(\f\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\ba",
         "rray_12\u0018\f \u0001(\f\u0012\u0010\n\barray_13\u0018\r \u0001(\f\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\"\u008c\u0002\n\u0005type4\u0012\u000f\n\u0007array_1\u0018\u0001 \u0001(\f\u0012\u000f\n\u0007array_2\u0018\u0002 \u0001(\f\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007array_5\u0018\u0005 \u0001(\f\u0012\u000f\n\u0007array_6\u0018\u0006 \u0001(\f\u0012\u000f\n\u0007array_7\u0018\u0007 \u0001(\f\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008c\u0002\n\u0005type5\u0012\u000f\n\u0007array_1\u0018\u0001 \u0001(\f\u0012\u000f\n\u0007array_2\u0018\u0002 \u0001(\f",
         "\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005B0\n l1j.server.server.datas.protobufB\fPBMessageALL"
      };
      a.k.g.a var1 = new a.k.g.a() {
         @Override
         public m a(k.g var1) {
            PBMessageALL.k = var1;
            PBMessageALL.a = PBMessageALL.a().e().get(0);
            PBMessageALL.b = new p.g(
               PBMessageALL.a,
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
            PBMessageALL.c = PBMessageALL.a().e().get(1);
            PBMessageALL.d = new p.g(
               PBMessageALL.c,
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
                  "Value15"
               }
            );
            PBMessageALL.e = PBMessageALL.a().e().get(2);
            PBMessageALL.f = new p.g(
               PBMessageALL.e,
               new String[]{
                  "Value1",
                  "Value2",
                  "Value3",
                  "Value4",
                  "Value5",
                  "Value6",
                  "Value7",
                  "Array8",
                  "Value9",
                  "Value10",
                  "Value11",
                  "Array12",
                  "Array13",
                  "Value14",
                  "Value15",
                  "Value16"
               }
            );
            PBMessageALL.g = PBMessageALL.a().e().get(3);
            PBMessageALL.h = new p.g(
               PBMessageALL.g,
               new String[]{
                  "Array1",
                  "Array2",
                  "Value3",
                  "Value4",
                  "Array5",
                  "Array6",
                  "Array7",
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
            PBMessageALL.i = PBMessageALL.a().e().get(4);
            PBMessageALL.j = new p.g(
               PBMessageALL.i,
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
      k.g.a(var0, new k.g[0], var1);
   }

   private PBMessageALL() {
   }

   public static void a(m var0) {
   }

   public static k.g a() {
      return k;
   }

   public static final class a extends p implements PBMessageALL.b {
      private static final PBMessageALL.a r = new PBMessageALL.a(true);
      private final ap s;
      public static ab<PBMessageALL.a> a = new a.c<PBMessageALL.a>() {
         public PBMessageALL.a c(a.h var1, n var2) throws s {
            return new PBMessageALL.a(var1, var2, null);
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

      private a(p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private a(boolean var1) {
         this.s = ap.c();
      }

      public static PBMessageALL.a h() {
         return r;
      }

      public PBMessageALL.a k() {
         return r;
      }

      @Override
      public final ap b_() {
         return this.s;
      }

      private a(a.h var1, n var2) throws s {
         this.ag();
         int var3 = 0;
         ap.a var4 = ap.b();

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

      public static final k.a n() {
         return PBMessageALL.a;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL.b.a(PBMessageALL.a.class, PBMessageALL.a.a.class);
      }

      @Override
      public ab<PBMessageALL.a> m() {
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

      public static PBMessageALL.a a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL.a a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL.a a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL.a a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL.a a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL.a a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL.a b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL.a b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL.a a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL.a a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL.a.a aa() {
         return PBMessageALL.a.a.av();
      }

      public PBMessageALL.a.a ab() {
         return aa();
      }

      public static PBMessageALL.a.a a(PBMessageALL.a var0) {
         return aa().a(var0);
      }

      public PBMessageALL.a.a ae() {
         return a(this);
      }

      protected PBMessageALL.a.a a(a.p.b var1) {
         return new PBMessageALL.a.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.ae();
      }

      // $VF: synthetic method
      @Override
      public x.a M() {
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
      protected x.a b(a.p.b var1) {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public a.y.a P() {
         return this.ab();
      }

      // $VF: synthetic method
      @Override
      public x.a N() {
         return this.ab();
      }

      // $VF: synthetic method
      a(a.h var1, n var2, PBMessageALL.a var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      a(p.a var1, PBMessageALL.a var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL.a.a> implements PBMessageALL.b {
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
         private int k;
         private int l;
         private int m;
         private int n;
         private int o;
         private int p;

         public static final k.a k() {
            return PBMessageALL.a;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL.b.a(PBMessageALL.a.class, PBMessageALL.a.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            PBMessageALL.a.m;
         }

         private static PBMessageALL.a.a av() {
            return new PBMessageALL.a.a();
         }

         public PBMessageALL.a.a m() {
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

         public PBMessageALL.a.a n() {
            return av().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL.a;
         }

         public PBMessageALL.a I() {
            return PBMessageALL.a.h();
         }

         public PBMessageALL.a M() {
            PBMessageALL.a var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL.a N() {
            PBMessageALL.a var1 = new PBMessageALL.a(this, null);
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

         public PBMessageALL.a.a d(x var1) {
            if (var1 instanceof PBMessageALL.a) {
               return this.a((PBMessageALL.a)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL.a.a a(PBMessageALL.a var1) {
            if (var1 == PBMessageALL.a.h()) {
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

         public PBMessageALL.a.a e(a.h var1, n var2) throws IOException {
            PBMessageALL.a var3 = null;

            try {
               var3 = PBMessageALL.a.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL.a)var8.a();
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

         public PBMessageALL.a.a a(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a O() {
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

         public PBMessageALL.a.a b(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a P() {
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

         public PBMessageALL.a.a c(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a aa() {
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

         public PBMessageALL.a.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a ab() {
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

         public PBMessageALL.a.a e(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a ac() {
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

         public PBMessageALL.a.a f(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a ad() {
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

         public PBMessageALL.a.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a ae() {
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

         public PBMessageALL.a.a h(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a af() {
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

         public PBMessageALL.a.a i(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a ag() {
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

         public PBMessageALL.a.a j(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a an() {
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

         public PBMessageALL.a.a k(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a ao() {
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

         public PBMessageALL.a.a l(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a ap() {
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

         public PBMessageALL.a.a m(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a aq() {
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

         public PBMessageALL.a.a n(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a ar() {
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

         public PBMessageALL.a.a o(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.a.a as() {
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
         public y.a g() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public x.a i() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public p.a ai() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public y.a c(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public x.a d(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public x.a c(x var1) {
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
         public y.a h() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public x.a j() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public p.a ah() {
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
         a(a.p.b var1, PBMessageALL.a.a var2) {
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

   public static final class c extends p implements PBMessageALL.d {
      private static final PBMessageALL.c r = new PBMessageALL.c(true);
      private final ap s;
      public static ab<PBMessageALL.c> a = new a.c<PBMessageALL.c>() {
         public PBMessageALL.c c(a.h var1, n var2) throws s {
            return new PBMessageALL.c(var1, var2, null);
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
      private List<a.g> w;
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

      private c(p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private c(boolean var1) {
         this.s = ap.c();
      }

      public static PBMessageALL.c h() {
         return r;
      }

      public PBMessageALL.c k() {
         return r;
      }

      @Override
      public final ap b_() {
         return this.s;
      }

      private c(a.h var1, n var2) throws s {
         this.ag();
         int var3 = 0;
         ap.a var4 = ap.b();

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
                     if ((var3 & 4) != 4) {
                        this.w = new ArrayList<>();
                        var3 |= 4;
                     }

                     this.w.add(var1.l());
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
            if ((var3 & 4) == 4) {
               this.w = Collections.unmodifiableList(this.w);
            }

            this.s = var4.b();
            this.ad();
         }
      }

      public static final k.a n() {
         return PBMessageALL.c;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL.d.a(PBMessageALL.c.class, PBMessageALL.c.a.class);
      }

      @Override
      public ab<PBMessageALL.c> m() {
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
      public List<a.g> s() {
         return this.w;
      }

      @Override
      public int t() {
         return this.w.size();
      }

      @Override
      public a.g a(int var1) {
         return this.w.get(var1);
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
         this.v = 0;
         this.w = Collections.emptyList();
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

         if ((this.t & 2) == 2) {
            var1.a(2, this.v);
         }

         for (int var2 = 0; var2 < this.w.size(); var2++) {
            var1.a(3, this.w.get(var2));
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

         if ((this.t & 2) == 2) {
            var1 += a.i.g(2, this.v);
         }

         int var2 = 0;

         for (int var3 = 0; var3 < this.w.size(); var3++) {
            var2 += a.i.b(this.w.get(var3));
         }

         var1 += var2;
         var1 += 1 * this.s().size();
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

      public static PBMessageALL.c a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL.c a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL.c a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL.c a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL.c a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL.c a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL.c b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL.c b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL.c a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL.c a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL.c.a aa() {
         return PBMessageALL.c.a.av();
      }

      public PBMessageALL.c.a ab() {
         return aa();
      }

      public static PBMessageALL.c.a a(PBMessageALL.c var0) {
         return aa().a(var0);
      }

      public PBMessageALL.c.a ae() {
         return a(this);
      }

      protected PBMessageALL.c.a a(a.p.b var1) {
         return new PBMessageALL.c.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.ae();
      }

      // $VF: synthetic method
      @Override
      public x.a M() {
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
      protected x.a b(a.p.b var1) {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public a.y.a P() {
         return this.ab();
      }

      // $VF: synthetic method
      @Override
      public x.a N() {
         return this.ab();
      }

      // $VF: synthetic method
      c(a.h var1, n var2, PBMessageALL.c var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      c(p.a var1, PBMessageALL.c var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL.c.a> implements PBMessageALL.d {
         private int a;
         private int b;
         private int c;
         private List<a.g> d = Collections.emptyList();
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

         public static final k.a k() {
            return PBMessageALL.c;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL.d.a(PBMessageALL.c.class, PBMessageALL.c.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            PBMessageALL.c.m;
         }

         private static PBMessageALL.c.a av() {
            return new PBMessageALL.c.a();
         }

         public PBMessageALL.c.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = 0;
            this.a &= -3;
            this.d = Collections.emptyList();
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

         public PBMessageALL.c.a n() {
            return av().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL.c;
         }

         public PBMessageALL.c I() {
            return PBMessageALL.c.h();
         }

         public PBMessageALL.c M() {
            PBMessageALL.c var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL.c N() {
            PBMessageALL.c var1 = new PBMessageALL.c(this, null);
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
            if ((this.a & 4) == 4) {
               this.d = Collections.unmodifiableList(this.d);
               this.a &= -5;
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

         public PBMessageALL.c.a d(x var1) {
            if (var1 instanceof PBMessageALL.c) {
               return this.a((PBMessageALL.c)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL.c.a a(PBMessageALL.c var1) {
            if (var1 == PBMessageALL.c.h()) {
               return this;
            }

            if (var1.o()) {
               this.b(var1.p());
            }

            if (var1.q()) {
               this.c(var1.r());
            }

            if (!var1.w.isEmpty()) {
               if (this.d.isEmpty()) {
                  this.d = var1.w;
                  this.a &= -5;
               } else {
                  this.aw();
                  this.d.addAll(var1.w);
               }

               this.t_();
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

         public PBMessageALL.c.a e(a.h var1, n var2) throws IOException {
            PBMessageALL.c var3 = null;

            try {
               var3 = PBMessageALL.c.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL.c)var8.a();
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

         public PBMessageALL.c.a b(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a O() {
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

         public PBMessageALL.c.a c(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a P() {
            this.a &= -3;
            this.c = 0;
            this.t_();
            return this;
         }

         private void aw() {
            if ((this.a & 4) != 4) {
               this.d = new ArrayList<>(this.d);
               this.a |= 4;
            }
         }

         @Override
         public List<a.g> s() {
            return Collections.unmodifiableList(this.d);
         }

         @Override
         public int t() {
            return this.d.size();
         }

         @Override
         public a.g a(int var1) {
            return this.d.get(var1);
         }

         public PBMessageALL.c.a a(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.d.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL.c.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.d.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL.c.a a(Iterable<? extends a.g> var1) {
            this.aw();
            p.a.a(var1, this.d);
            this.t_();
            return this;
         }

         public PBMessageALL.c.a aa() {
            this.d = Collections.emptyList();
            this.a &= -5;
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

         public PBMessageALL.c.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a ab() {
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

         public PBMessageALL.c.a e(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a ac() {
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

         public PBMessageALL.c.a f(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a ad() {
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

         public PBMessageALL.c.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a ae() {
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

         public PBMessageALL.c.a h(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a af() {
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

         public PBMessageALL.c.a i(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a ag() {
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

         public PBMessageALL.c.a j(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a an() {
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

         public PBMessageALL.c.a k(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a ao() {
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

         public PBMessageALL.c.a l(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a ap() {
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

         public PBMessageALL.c.a m(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a aq() {
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

         public PBMessageALL.c.a n(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a ar() {
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

         public PBMessageALL.c.a o(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.c.a as() {
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
         public y.a g() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public x.a i() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public p.a ai() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public y.a c(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public x.a d(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public x.a c(x var1) {
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
         public y.a h() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public x.a j() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public p.a ah() {
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
         a(a.p.b var1, PBMessageALL.c.a var2) {
            this(var1);
         }
      }
   }

   public interface d extends aa {
      boolean o();

      int p();

      boolean q();

      int r();

      List<a.g> s();

      int t();

      a.g a(int var1);

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

   public static final class e extends p implements PBMessageALL.f {
      private static final PBMessageALL.e s = new PBMessageALL.e(true);
      private final ap t;
      public static ab<PBMessageALL.e> a = new a.c<PBMessageALL.e>() {
         public PBMessageALL.e c(a.h var1, n var2) throws s {
            return new PBMessageALL.e(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int u;
      public static final int b = 1;
      private int v;
      public static final int c = 2;
      private int w;
      public static final int d = 3;
      private int x;
      public static final int e = 4;
      private int y;
      public static final int f = 5;
      private int z;
      public static final int g = 6;
      private int A;
      public static final int h = 7;
      private int B;
      public static final int i = 8;
      private a.g C;
      public static final int j = 9;
      private int D;
      public static final int k = 10;
      private int E;
      public static final int l = 11;
      private int F;
      public static final int n = 12;
      private a.g G;
      public static final int o = 13;
      private a.g H;
      public static final int p = 14;
      private int I;
      public static final int q = 15;
      private int J;
      public static final int r = 16;
      private int K;
      private byte L = -1;
      private int M = -1;
      private static final long N = 0L;

      static {
         s.ai();
      }

      private e(p.a<?> var1) {
         super(var1);
         this.t = var1.b_();
      }

      private e(boolean var1) {
         this.t = ap.c();
      }

      public static PBMessageALL.e h() {
         return s;
      }

      public PBMessageALL.e k() {
         return s;
      }

      @Override
      public final ap b_() {
         return this.t;
      }

      private e(a.h var1, n var2) throws s {
         this.ai();
         int var3 = 0;
         ap.a var4 = ap.b();

         try {
            boolean var5 = false;

            while (!var5) {
               int var6 = var1.a();
               switch (var6) {
                  case 0:
                     var5 = true;
                     break;
                  case 8:
                     this.u |= 1;
                     this.v = var1.g();
                     break;
                  case 16:
                     this.u |= 2;
                     this.w = var1.g();
                     break;
                  case 24:
                     this.u |= 4;
                     this.x = var1.g();
                     break;
                  case 32:
                     this.u |= 8;
                     this.y = var1.g();
                     break;
                  case 40:
                     this.u |= 16;
                     this.z = var1.g();
                     break;
                  case 48:
                     this.u |= 32;
                     this.A = var1.g();
                     break;
                  case 56:
                     this.u |= 64;
                     this.B = var1.g();
                     break;
                  case 66:
                     this.u |= 128;
                     this.C = var1.l();
                     break;
                  case 72:
                     this.u |= 256;
                     this.D = var1.g();
                     break;
                  case 80:
                     this.u |= 512;
                     this.E = var1.g();
                     break;
                  case 88:
                     this.u |= 1024;
                     this.F = var1.g();
                     break;
                  case 98:
                     this.u |= 2048;
                     this.G = var1.l();
                     break;
                  case 106:
                     this.u |= 4096;
                     this.H = var1.l();
                     break;
                  case 112:
                     this.u |= 8192;
                     this.I = var1.g();
                     break;
                  case 120:
                     this.u |= 16384;
                     this.J = var1.g();
                     break;
                  case 128:
                     this.u |= 32768;
                     this.K = var1.g();
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
            this.t = var4.b();
            this.ad();
         }
      }

      public static final k.a n() {
         return PBMessageALL.e;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL.f.a(PBMessageALL.e.class, PBMessageALL.e.a.class);
      }

      @Override
      public ab<PBMessageALL.e> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.u & 1) == 1;
      }

      @Override
      public int p() {
         return this.v;
      }

      @Override
      public boolean q() {
         return (this.u & 2) == 2;
      }

      @Override
      public int r() {
         return this.w;
      }

      @Override
      public boolean s() {
         return (this.u & 4) == 4;
      }

      @Override
      public int t() {
         return this.x;
      }

      @Override
      public boolean u() {
         return (this.u & 8) == 8;
      }

      @Override
      public int v() {
         return this.y;
      }

      @Override
      public boolean w() {
         return (this.u & 16) == 16;
      }

      @Override
      public int x() {
         return this.z;
      }

      @Override
      public boolean y() {
         return (this.u & 32) == 32;
      }

      @Override
      public int z() {
         return this.A;
      }

      @Override
      public boolean A() {
         return (this.u & 64) == 64;
      }

      @Override
      public int B() {
         return this.B;
      }

      @Override
      public boolean C() {
         return (this.u & 128) == 128;
      }

      @Override
      public a.g D() {
         return this.C;
      }

      @Override
      public boolean E() {
         return (this.u & 256) == 256;
      }

      @Override
      public int F() {
         return this.D;
      }

      @Override
      public boolean G() {
         return (this.u & 512) == 512;
      }

      @Override
      public int H() {
         return this.E;
      }

      @Override
      public boolean K() {
         return (this.u & 1024) == 1024;
      }

      @Override
      public int L() {
         return this.F;
      }

      @Override
      public boolean S() {
         return (this.u & 2048) == 2048;
      }

      @Override
      public a.g T() {
         return this.G;
      }

      @Override
      public boolean U() {
         return (this.u & 4096) == 4096;
      }

      @Override
      public a.g V() {
         return this.H;
      }

      @Override
      public boolean W() {
         return (this.u & 8192) == 8192;
      }

      @Override
      public int X() {
         return this.I;
      }

      @Override
      public boolean Y() {
         return (this.u & 16384) == 16384;
      }

      @Override
      public int Z() {
         return this.J;
      }

      @Override
      public boolean aa() {
         return (this.u & 32768) == 32768;
      }

      @Override
      public int ab() {
         return this.K;
      }

      private void ai() {
         this.v = 0;
         this.w = 0;
         this.x = 0;
         this.y = 0;
         this.z = 0;
         this.A = 0;
         this.B = 0;
         this.C = a.g.d;
         this.D = 0;
         this.E = 0;
         this.F = 0;
         this.G = a.g.d;
         this.H = a.g.d;
         this.I = 0;
         this.J = 0;
         this.K = 0;
      }

      @Override
      public final boolean a() {
         byte var1 = this.L;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.L = 1;
         return true;
      }

      @Override
      public void a(a.i var1) throws IOException {
         this.d();
         if ((this.u & 1) == 1) {
            var1.a(1, this.v);
         }

         if ((this.u & 2) == 2) {
            var1.a(2, this.w);
         }

         if ((this.u & 4) == 4) {
            var1.a(3, this.x);
         }

         if ((this.u & 8) == 8) {
            var1.a(4, this.y);
         }

         if ((this.u & 16) == 16) {
            var1.a(5, this.z);
         }

         if ((this.u & 32) == 32) {
            var1.a(6, this.A);
         }

         if ((this.u & 64) == 64) {
            var1.a(7, this.B);
         }

         if ((this.u & 128) == 128) {
            var1.a(8, this.C);
         }

         if ((this.u & 256) == 256) {
            var1.a(9, this.D);
         }

         if ((this.u & 512) == 512) {
            var1.a(10, this.E);
         }

         if ((this.u & 1024) == 1024) {
            var1.a(11, this.F);
         }

         if ((this.u & 2048) == 2048) {
            var1.a(12, this.G);
         }

         if ((this.u & 4096) == 4096) {
            var1.a(13, this.H);
         }

         if ((this.u & 8192) == 8192) {
            var1.a(14, this.I);
         }

         if ((this.u & 16384) == 16384) {
            var1.a(15, this.J);
         }

         if ((this.u & 32768) == 32768) {
            var1.a(16, this.K);
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.M;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.u & 1) == 1) {
            var1 += a.i.g(1, this.v);
         }

         if ((this.u & 2) == 2) {
            var1 += a.i.g(2, this.w);
         }

         if ((this.u & 4) == 4) {
            var1 += a.i.g(3, this.x);
         }

         if ((this.u & 8) == 8) {
            var1 += a.i.g(4, this.y);
         }

         if ((this.u & 16) == 16) {
            var1 += a.i.g(5, this.z);
         }

         if ((this.u & 32) == 32) {
            var1 += a.i.g(6, this.A);
         }

         if ((this.u & 64) == 64) {
            var1 += a.i.g(7, this.B);
         }

         if ((this.u & 128) == 128) {
            var1 += a.i.c(8, this.C);
         }

         if ((this.u & 256) == 256) {
            var1 += a.i.g(9, this.D);
         }

         if ((this.u & 512) == 512) {
            var1 += a.i.g(10, this.E);
         }

         if ((this.u & 1024) == 1024) {
            var1 += a.i.g(11, this.F);
         }

         if ((this.u & 2048) == 2048) {
            var1 += a.i.c(12, this.G);
         }

         if ((this.u & 4096) == 4096) {
            var1 += a.i.c(13, this.H);
         }

         if ((this.u & 8192) == 8192) {
            var1 += a.i.g(14, this.I);
         }

         if ((this.u & 16384) == 16384) {
            var1 += a.i.g(15, this.J);
         }

         if ((this.u & 32768) == 32768) {
            var1 += a.i.g(16, this.K);
         }

         var1 += this.b_().d();
         this.M = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static PBMessageALL.e a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL.e a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL.e a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL.e a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL.e a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL.e a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL.e b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL.e b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL.e a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL.e a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL.e.a ae() {
         return PBMessageALL.e.a.ay();
      }

      public PBMessageALL.e.a af() {
         return ae();
      }

      public static PBMessageALL.e.a a(PBMessageALL.e var0) {
         return ae().a(var0);
      }

      public PBMessageALL.e.a ag() {
         return a(this);
      }

      protected PBMessageALL.e.a a(a.p.b var1) {
         return new PBMessageALL.e.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.ag();
      }

      // $VF: synthetic method
      @Override
      public x.a M() {
         return this.ag();
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
      protected x.a b(a.p.b var1) {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public a.y.a P() {
         return this.af();
      }

      // $VF: synthetic method
      @Override
      public x.a N() {
         return this.af();
      }

      // $VF: synthetic method
      e(a.h var1, n var2, PBMessageALL.e var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      e(p.a var1, PBMessageALL.e var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL.e.a> implements PBMessageALL.f {
         private int a;
         private int b;
         private int c;
         private int d;
         private int e;
         private int f;
         private int g;
         private int h;
         private a.g i = a.g.d;
         private int j;
         private int k;
         private int l;
         private a.g m = a.g.d;
         private a.g n = a.g.d;
         private int o;
         private int p;
         private int q;

         public static final k.a k() {
            return PBMessageALL.e;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL.f.a(PBMessageALL.e.class, PBMessageALL.e.a.class);
         }

         private a() {
            this.ax();
         }

         private a(a.p.b var1) {
            super(var1);
            this.ax();
         }

         private void ax() {
            PBMessageALL.e.m;
         }

         private static PBMessageALL.e.a ay() {
            return new PBMessageALL.e.a();
         }

         public PBMessageALL.e.a m() {
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
            this.g = 0;
            this.a &= -33;
            this.h = 0;
            this.a &= -65;
            this.i = a.g.d;
            this.a &= -129;
            this.j = 0;
            this.a &= -257;
            this.k = 0;
            this.a &= -513;
            this.l = 0;
            this.a &= -1025;
            this.m = a.g.d;
            this.a &= -2049;
            this.n = a.g.d;
            this.a &= -4097;
            this.o = 0;
            this.a &= -8193;
            this.p = 0;
            this.a &= -16385;
            this.q = 0;
            this.a &= -32769;
            return this;
         }

         public PBMessageALL.e.a n() {
            return ay().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL.e;
         }

         public PBMessageALL.e I() {
            return PBMessageALL.e.h();
         }

         public PBMessageALL.e M() {
            PBMessageALL.e var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL.e N() {
            PBMessageALL.e var1 = new PBMessageALL.e(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((var2 & 1) == 1) {
               var3 |= 1;
            }

            var1.v = this.b;
            if ((var2 & 2) == 2) {
               var3 |= 2;
            }

            var1.w = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 4;
            }

            var1.x = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 8;
            }

            var1.y = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 16;
            }

            var1.z = this.f;
            if ((var2 & 32) == 32) {
               var3 |= 32;
            }

            var1.A = this.g;
            if ((var2 & 64) == 64) {
               var3 |= 64;
            }

            var1.B = this.h;
            if ((var2 & 128) == 128) {
               var3 |= 128;
            }

            var1.C = this.i;
            if ((var2 & 256) == 256) {
               var3 |= 256;
            }

            var1.D = this.j;
            if ((var2 & 512) == 512) {
               var3 |= 512;
            }

            var1.E = this.k;
            if ((var2 & 1024) == 1024) {
               var3 |= 1024;
            }

            var1.F = this.l;
            if ((var2 & 2048) == 2048) {
               var3 |= 2048;
            }

            var1.G = this.m;
            if ((var2 & 4096) == 4096) {
               var3 |= 4096;
            }

            var1.H = this.n;
            if ((var2 & 8192) == 8192) {
               var3 |= 8192;
            }

            var1.I = this.o;
            if ((var2 & 16384) == 16384) {
               var3 |= 16384;
            }

            var1.J = this.p;
            if ((var2 & 32768) == 32768) {
               var3 |= 32768;
            }

            var1.K = this.q;
            var1.u = var3;
            this.q_();
            return var1;
         }

         public PBMessageALL.e.a d(x var1) {
            if (var1 instanceof PBMessageALL.e) {
               return this.a((PBMessageALL.e)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL.e.a a(PBMessageALL.e var1) {
            if (var1 == PBMessageALL.e.h()) {
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
               this.e(var1.D());
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
               this.f(var1.T());
            }

            if (var1.U()) {
               this.g(var1.V());
            }

            if (var1.W()) {
               this.k(var1.X());
            }

            if (var1.Y()) {
               this.l(var1.Z());
            }

            if (var1.aa()) {
               this.m(var1.ab());
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public PBMessageALL.e.a e(a.h var1, n var2) throws IOException {
            PBMessageALL.e var3 = null;

            try {
               var3 = PBMessageALL.e.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL.e)var8.a();
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

         public PBMessageALL.e.a a(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a O() {
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

         public PBMessageALL.e.a b(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a P() {
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

         public PBMessageALL.e.a c(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a ac() {
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

         public PBMessageALL.e.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a ad() {
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

         public PBMessageALL.e.a e(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a ae() {
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

         public PBMessageALL.e.a f(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a af() {
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

         public PBMessageALL.e.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a ag() {
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
         public a.g D() {
            return this.i;
         }

         public PBMessageALL.e.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a an() {
            this.a &= -129;
            this.i = PBMessageALL.e.h().D();
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

         public PBMessageALL.e.a h(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a ao() {
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

         public PBMessageALL.e.a i(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a ap() {
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

         public PBMessageALL.e.a j(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a aq() {
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
         public a.g T() {
            return this.m;
         }

         public PBMessageALL.e.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a ar() {
            this.a &= -2049;
            this.m = PBMessageALL.e.h().T();
            this.t_();
            return this;
         }

         @Override
         public boolean U() {
            return (this.a & 4096) == 4096;
         }

         @Override
         public a.g V() {
            return this.n;
         }

         public PBMessageALL.e.a g(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a as() {
            this.a &= -4097;
            this.n = PBMessageALL.e.h().V();
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

         public PBMessageALL.e.a k(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a at() {
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

         public PBMessageALL.e.a l(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a au() {
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

         public PBMessageALL.e.a m(int var1) {
            this.a |= 32768;
            this.q = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.e.a av() {
            this.a &= -32769;
            this.q = 0;
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
         public y.a g() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public x.a i() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public p.a ai() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public y.a c(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public x.a d(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public x.a c(x var1) {
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
         public y.a h() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public x.a j() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public p.a ah() {
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
         a(a.p.b var1, PBMessageALL.e.a var2) {
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

      a.g D();

      boolean E();

      int F();

      boolean G();

      int H();

      boolean K();

      int L();

      boolean S();

      a.g T();

      boolean U();

      a.g V();

      boolean W();

      int X();

      boolean Y();

      int Z();

      boolean aa();

      int ab();
   }

   public static final class g extends p implements PBMessageALL.h {
      private static final PBMessageALL.g r = new PBMessageALL.g(true);
      private final ap s;
      public static ab<PBMessageALL.g> a = new a.c<PBMessageALL.g>() {
         public PBMessageALL.g c(a.h var1, n var2) throws s {
            return new PBMessageALL.g(var1, var2, null);
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
      private a.g y;
      public static final int g = 6;
      private a.g z;
      public static final int h = 7;
      private a.g A;
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

      private g(p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private g(boolean var1) {
         this.s = ap.c();
      }

      public static PBMessageALL.g h() {
         return r;
      }

      public PBMessageALL.g k() {
         return r;
      }

      @Override
      public final ap b_() {
         return this.s;
      }

      private g(a.h var1, n var2) throws s {
         this.ag();
         int var3 = 0;
         ap.a var4 = ap.b();

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

      public static final k.a n() {
         return PBMessageALL.g;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL.h.a(PBMessageALL.g.class, PBMessageALL.g.a.class);
      }

      @Override
      public ab<PBMessageALL.g> m() {
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
         this.y = a.g.d;
         this.z = a.g.d;
         this.A = a.g.d;
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
            var1 += a.i.c(5, this.y);
         }

         if ((this.t & 32) == 32) {
            var1 += a.i.c(6, this.z);
         }

         if ((this.t & 64) == 64) {
            var1 += a.i.c(7, this.A);
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

      public static PBMessageALL.g a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL.g a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL.g a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL.g a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL.g a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL.g a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL.g b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL.g b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL.g a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL.g a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL.g.a aa() {
         return PBMessageALL.g.a.av();
      }

      public PBMessageALL.g.a ab() {
         return aa();
      }

      public static PBMessageALL.g.a a(PBMessageALL.g var0) {
         return aa().a(var0);
      }

      public PBMessageALL.g.a ae() {
         return a(this);
      }

      protected PBMessageALL.g.a a(a.p.b var1) {
         return new PBMessageALL.g.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.ae();
      }

      // $VF: synthetic method
      @Override
      public x.a M() {
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
      protected x.a b(a.p.b var1) {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public a.y.a P() {
         return this.ab();
      }

      // $VF: synthetic method
      @Override
      public x.a N() {
         return this.ab();
      }

      // $VF: synthetic method
      g(a.h var1, n var2, PBMessageALL.g var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      g(p.a var1, PBMessageALL.g var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL.g.a> implements PBMessageALL.h {
         private int a;
         private a.g b = a.g.d;
         private a.g c = a.g.d;
         private int d;
         private int e;
         private a.g f = a.g.d;
         private a.g g = a.g.d;
         private a.g h = a.g.d;
         private int i;
         private int j;
         private int k;
         private int l;
         private int m;
         private int n;
         private int o;
         private int p;

         public static final k.a k() {
            return PBMessageALL.g;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL.h.a(PBMessageALL.g.class, PBMessageALL.g.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            PBMessageALL.g.m;
         }

         private static PBMessageALL.g.a av() {
            return new PBMessageALL.g.a();
         }

         public PBMessageALL.g.a m() {
            super.ah();
            this.b = a.g.d;
            this.a &= -2;
            this.c = a.g.d;
            this.a &= -3;
            this.d = 0;
            this.a &= -5;
            this.e = 0;
            this.a &= -9;
            this.f = a.g.d;
            this.a &= -17;
            this.g = a.g.d;
            this.a &= -33;
            this.h = a.g.d;
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

         public PBMessageALL.g.a n() {
            return av().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL.g;
         }

         public PBMessageALL.g I() {
            return PBMessageALL.g.h();
         }

         public PBMessageALL.g M() {
            PBMessageALL.g var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL.g N() {
            PBMessageALL.g var1 = new PBMessageALL.g(this, null);
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

         public PBMessageALL.g.a d(x var1) {
            if (var1 instanceof PBMessageALL.g) {
               return this.a((PBMessageALL.g)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL.g.a a(PBMessageALL.g var1) {
            if (var1 == PBMessageALL.g.h()) {
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
               this.g(var1.x());
            }

            if (var1.y()) {
               this.h(var1.z());
            }

            if (var1.A()) {
               this.i(var1.B());
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

         public PBMessageALL.g.a e(a.h var1, n var2) throws IOException {
            PBMessageALL.g var3 = null;

            try {
               var3 = PBMessageALL.g.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL.g)var8.a();
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

         public PBMessageALL.g.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a O() {
            this.a &= -2;
            this.b = PBMessageALL.g.h().p();
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

         public PBMessageALL.g.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a P() {
            this.a &= -3;
            this.c = PBMessageALL.g.h().r();
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

         public PBMessageALL.g.a a(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a aa() {
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

         public PBMessageALL.g.a b(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a ab() {
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

         public PBMessageALL.g.a g(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a ac() {
            this.a &= -17;
            this.f = PBMessageALL.g.h().x();
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

         public PBMessageALL.g.a h(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a ad() {
            this.a &= -33;
            this.g = PBMessageALL.g.h().z();
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

         public PBMessageALL.g.a i(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a ae() {
            this.a &= -65;
            this.h = PBMessageALL.g.h().B();
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

         public PBMessageALL.g.a c(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a af() {
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

         public PBMessageALL.g.a d(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a ag() {
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

         public PBMessageALL.g.a e(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a an() {
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

         public PBMessageALL.g.a f(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a ao() {
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

         public PBMessageALL.g.a g(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a ap() {
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

         public PBMessageALL.g.a h(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a aq() {
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

         public PBMessageALL.g.a i(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a ar() {
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

         public PBMessageALL.g.a j(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.g.a as() {
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
         public y.a g() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public x.a i() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public p.a ai() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public y.a c(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public x.a d(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public x.a c(x var1) {
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
         public y.a h() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public x.a j() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public p.a ah() {
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
         a(a.p.b var1, PBMessageALL.g.a var2) {
            this(var1);
         }
      }
   }

   public interface h extends aa {
      boolean o();

      a.g p();

      boolean q();

      a.g r();

      boolean s();

      int t();

      boolean u();

      int v();

      boolean w();

      a.g x();

      boolean y();

      a.g z();

      boolean A();

      a.g B();

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

   public static final class i extends p implements PBMessageALL.j {
      private static final PBMessageALL.i r = new PBMessageALL.i(true);
      private final ap s;
      public static ab<PBMessageALL.i> a = new a.c<PBMessageALL.i>() {
         public PBMessageALL.i c(a.h var1, n var2) throws s {
            return new PBMessageALL.i(var1, var2, null);
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

      private i(p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private i(boolean var1) {
         this.s = ap.c();
      }

      public static PBMessageALL.i h() {
         return r;
      }

      public PBMessageALL.i k() {
         return r;
      }

      @Override
      public final ap b_() {
         return this.s;
      }

      private i(a.h var1, n var2) throws s {
         this.ag();
         int var3 = 0;
         ap.a var4 = ap.b();

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

      public static final k.a n() {
         return PBMessageALL.i;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL.j.a(PBMessageALL.i.class, PBMessageALL.i.a.class);
      }

      @Override
      public ab<PBMessageALL.i> m() {
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
         this.u = a.g.d;
         this.v = a.g.d;
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

      public static PBMessageALL.i a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL.i a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL.i a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL.i a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL.i a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL.i a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL.i b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL.i b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL.i a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL.i a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL.i.a aa() {
         return PBMessageALL.i.a.av();
      }

      public PBMessageALL.i.a ab() {
         return aa();
      }

      public static PBMessageALL.i.a a(PBMessageALL.i var0) {
         return aa().a(var0);
      }

      public PBMessageALL.i.a ae() {
         return a(this);
      }

      protected PBMessageALL.i.a a(a.p.b var1) {
         return new PBMessageALL.i.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.ae();
      }

      // $VF: synthetic method
      @Override
      public x.a M() {
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
      protected x.a b(a.p.b var1) {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public a.y.a P() {
         return this.ab();
      }

      // $VF: synthetic method
      @Override
      public x.a N() {
         return this.ab();
      }

      // $VF: synthetic method
      i(a.h var1, n var2, PBMessageALL.i var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      i(p.a var1, PBMessageALL.i var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL.i.a> implements PBMessageALL.j {
         private int a;
         private a.g b = a.g.d;
         private a.g c = a.g.d;
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

         public static final k.a k() {
            return PBMessageALL.i;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL.j.a(PBMessageALL.i.class, PBMessageALL.i.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            PBMessageALL.i.m;
         }

         private static PBMessageALL.i.a av() {
            return new PBMessageALL.i.a();
         }

         public PBMessageALL.i.a m() {
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

         public PBMessageALL.i.a n() {
            return av().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL.i;
         }

         public PBMessageALL.i I() {
            return PBMessageALL.i.h();
         }

         public PBMessageALL.i M() {
            PBMessageALL.i var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL.i N() {
            PBMessageALL.i var1 = new PBMessageALL.i(this, null);
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

         public PBMessageALL.i.a d(x var1) {
            if (var1 instanceof PBMessageALL.i) {
               return this.a((PBMessageALL.i)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL.i.a a(PBMessageALL.i var1) {
            if (var1 == PBMessageALL.i.h()) {
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
               this.d(var1.z());
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
               this.h(var1.H());
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

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public PBMessageALL.i.a e(a.h var1, n var2) throws IOException {
            PBMessageALL.i var3 = null;

            try {
               var3 = PBMessageALL.i.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL.i)var8.a();
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

         public PBMessageALL.i.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a O() {
            this.a &= -2;
            this.b = PBMessageALL.i.h().p();
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

         public PBMessageALL.i.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a P() {
            this.a &= -3;
            this.c = PBMessageALL.i.h().r();
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

         public PBMessageALL.i.a a(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a aa() {
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

         public PBMessageALL.i.a b(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a ab() {
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

         public PBMessageALL.i.a c(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a ac() {
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

         public PBMessageALL.i.a d(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a ad() {
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

         public PBMessageALL.i.a e(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a ae() {
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

         public PBMessageALL.i.a f(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a af() {
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

         public PBMessageALL.i.a g(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a ag() {
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

         public PBMessageALL.i.a h(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a an() {
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

         public PBMessageALL.i.a i(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a ao() {
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

         public PBMessageALL.i.a j(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a ap() {
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

         public PBMessageALL.i.a k(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a aq() {
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

         public PBMessageALL.i.a l(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a ar() {
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

         public PBMessageALL.i.a m(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL.i.a as() {
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
         public y.a g() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public x.a i() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public p.a ai() {
            return this.n();
         }

         // $VF: synthetic method
         @Override
         public y.a c(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public x.a d(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public a.a.a a(a.h var1, n var2) throws IOException {
            return this.e(var1, var2);
         }

         // $VF: synthetic method
         @Override
         public x.a c(x var1) {
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
         public y.a h() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public x.a j() {
            return this.m();
         }

         // $VF: synthetic method
         @Override
         public p.a ah() {
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
         a(a.p.b var1, PBMessageALL.i.a var2) {
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
