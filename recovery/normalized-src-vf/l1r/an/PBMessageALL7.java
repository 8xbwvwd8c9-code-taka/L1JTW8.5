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

public final class PBMessageALL7 {
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
         "\n\u0013PBMessageALL7.proto\u0012 l1j.server.server.datas.protobuf\"\u008d\u0002\n\u0006type26\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0003(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"ù\u0002\n\u0006type27\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007array_3\u0018\u0003 \u0001(\f\u0012\u000f\n\u0007value_4",
         "\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\u0012\u0010\n\bvalue_17\u0018\u0011 \u0001(\u0005\u0012\u0010\n\barray_18\u0018\u0012 \u0001(\f\u0012\u0010\n\bvalue_19\u0018\u0013 \u0001(\u0005\u0012\u0010\n\bvalue_20\u0018\u0014 \u0001(\u0005\u0012\u0010\n\barray_21\u0018\u0015 \u0003(\u0005\"\u008d\u0002\n\u0006type28\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 ",
         "\u0003(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0003(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0003(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0003(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0003(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008d\u0002\n\u0006type29\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007array_2\u0018\u0002 \u0003(\f\u0012\u000f\n\u0007array_3\u0018\u0003 \u0003(\f\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n",
         "\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008d\u0002\n\u0006type30\u0012\u000f\n\u0007array_1\u0018\u0001 \u0001(\f\u0012\u000f\n\u0007array_2\u0018\u0002 \u0003(\f\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005B1\n l1j.server.server.datas.protobufB\rPBMessageALL7"
      };
      a.k.g.a var1 = new a.k.g.a() {
         @Override
         public m a(k.g var1) {
            PBMessageALL7.k = var1;
            PBMessageALL7.a = PBMessageALL7.a().e().get(0);
            PBMessageALL7.b = new p.g(
               PBMessageALL7.a,
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
            PBMessageALL7.c = PBMessageALL7.a().e().get(1);
            PBMessageALL7.d = new p.g(
               PBMessageALL7.c,
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
            PBMessageALL7.e = PBMessageALL7.a().e().get(2);
            PBMessageALL7.f = new p.g(
               PBMessageALL7.e,
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
            PBMessageALL7.g = PBMessageALL7.a().e().get(3);
            PBMessageALL7.h = new p.g(
               PBMessageALL7.g,
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
            PBMessageALL7.i = PBMessageALL7.a().e().get(4);
            PBMessageALL7.j = new p.g(
               PBMessageALL7.i,
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

   private PBMessageALL7() {
   }

   public static void a(m var0) {
   }

   public static k.g a() {
      return k;
   }

   public static final class a extends p implements PBMessageALL7.b {
      private static final PBMessageALL7.a r = new PBMessageALL7.a(true);
      private final ap s;
      public static ab<PBMessageALL7.a> a = new a.c<PBMessageALL7.a>() {
         public PBMessageALL7.a c(a.h var1, n var2) throws s {
            return new PBMessageALL7.a(var1, var2, null);
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
      private List<Integer> x;
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

      public static PBMessageALL7.a h() {
         return r;
      }

      public PBMessageALL7.a k() {
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
               int var8;
               switch (var6) {
                  case 0:
                     var5 = true;
                     continue;
                  case 8:
                     this.t |= 1;
                     this.u = var1.g();
                     continue;
                  case 16:
                     this.t |= 2;
                     this.v = var1.g();
                     continue;
                  case 24:
                     this.t |= 4;
                     this.w = var1.g();
                     continue;
                  case 32:
                     if ((var3 & 8) != 8) {
                        this.x = new ArrayList<>();
                        var3 |= 8;
                     }

                     this.x.add(var1.g());
                     continue;
                  case 34:
                     int var7 = var1.s();
                     var8 = var1.f(var7);
                     if ((var3 & 8) != 8 && var1.x() > 0) {
                        this.x = new ArrayList<>();
                        var3 |= 8;
                     }
                     break;
                  case 40:
                     this.t |= 8;
                     this.y = var1.g();
                     continue;
                  case 48:
                     this.t |= 16;
                     this.z = var1.g();
                     continue;
                  case 56:
                     this.t |= 32;
                     this.A = var1.g();
                     continue;
                  case 64:
                     this.t |= 64;
                     this.B = var1.g();
                     continue;
                  case 72:
                     this.t |= 128;
                     this.C = var1.g();
                     continue;
                  case 80:
                     this.t |= 256;
                     this.D = var1.g();
                     continue;
                  case 88:
                     this.t |= 512;
                     this.E = var1.g();
                     continue;
                  case 96:
                     this.t |= 1024;
                     this.F = var1.g();
                     continue;
                  case 104:
                     this.t |= 2048;
                     this.G = var1.g();
                     continue;
                  case 112:
                     this.t |= 4096;
                     this.H = var1.g();
                     continue;
                  case 120:
                     this.t |= 8192;
                     this.I = var1.g();
                     continue;
                  default:
                     if (!this.a(var1, var4, var2, var6)) {
                        var5 = true;
                     }
                     continue;
               }

               while (var1.x() > 0) {
                  this.x.add(var1.g());
               }

               var1.g(var8);
            }
         } catch (s var13) {
            throw var13.a(this);
         } catch (IOException var14) {
            throw new s(var14.getMessage()).a(this);
         } finally {
            if ((var3 & 8) == 8) {
               this.x = Collections.unmodifiableList(this.x);
            }

            this.s = var4.b();
            this.ad();
         }
      }

      public static final k.a n() {
         return PBMessageALL7.a;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL7.b.a(PBMessageALL7.a.class, PBMessageALL7.a.a.class);
      }

      @Override
      public ab<PBMessageALL7.a> m() {
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
      public List<Integer> u() {
         return this.x;
      }

      @Override
      public int v() {
         return this.x.size();
      }

      @Override
      public int a(int var1) {
         return this.x.get(var1);
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
         this.w = 0;
         this.x = Collections.emptyList();
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

         for (int var2 = 0; var2 < this.x.size(); var2++) {
            var1.a(4, this.x.get(var2));
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

         if ((this.t & 4) == 4) {
            var1 += a.i.g(3, this.w);
         }

         int var2 = 0;

         for (int var3 = 0; var3 < this.x.size(); var3++) {
            var2 += a.i.h(this.x.get(var3));
         }

         var1 += var2;
         var1 += 1 * this.u().size();
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

      public static PBMessageALL7.a a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL7.a a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL7.a a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL7.a a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL7.a a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL7.a a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL7.a b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL7.a b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL7.a a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL7.a a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL7.a.a aa() {
         return PBMessageALL7.a.a.av();
      }

      public PBMessageALL7.a.a ab() {
         return aa();
      }

      public static PBMessageALL7.a.a a(PBMessageALL7.a var0) {
         return aa().a(var0);
      }

      public PBMessageALL7.a.a ae() {
         return a(this);
      }

      protected PBMessageALL7.a.a a(a.p.b var1) {
         return new PBMessageALL7.a.a(var1, null);
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
      a(a.h var1, n var2, PBMessageALL7.a var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      a(p.a var1, PBMessageALL7.a var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL7.a.a> implements PBMessageALL7.b {
         private int a;
         private int b;
         private int c;
         private int d;
         private List<Integer> e = Collections.emptyList();
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
            return PBMessageALL7.a;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL7.b.a(PBMessageALL7.a.class, PBMessageALL7.a.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            PBMessageALL7.a.af();
         }

         private static PBMessageALL7.a.a av() {
            return new PBMessageALL7.a.a();
         }

         public PBMessageALL7.a.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = 0;
            this.a &= -3;
            this.d = 0;
            this.a &= -5;
            this.e = Collections.emptyList();
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

         public PBMessageALL7.a.a n() {
            return av().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL7.a;
         }

         public PBMessageALL7.a I() {
            return PBMessageALL7.a.h();
         }

         public PBMessageALL7.a M() {
            PBMessageALL7.a var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL7.a N() {
            PBMessageALL7.a var1 = new PBMessageALL7.a(this, null);
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
            if ((this.a & 8) == 8) {
               this.e = Collections.unmodifiableList(this.e);
               this.a &= -9;
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

         public PBMessageALL7.a.a d(x var1) {
            if (var1 instanceof PBMessageALL7.a) {
               return this.a((PBMessageALL7.a)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL7.a.a a(PBMessageALL7.a var1) {
            if (var1 == PBMessageALL7.a.h()) {
               return this;
            }

            if (var1.o()) {
               this.b(var1.p());
            }

            if (var1.q()) {
               this.c(var1.r());
            }

            if (var1.s()) {
               this.d(var1.t());
            }

            if (!var1.x.isEmpty()) {
               if (this.e.isEmpty()) {
                  this.e = var1.x;
                  this.a &= -9;
               } else {
                  this.aw();
                  this.e.addAll(var1.x);
               }

               this.t_();
            }

            if (var1.w()) {
               this.f(var1.x());
            }

            if (var1.y()) {
               this.g(var1.z());
            }

            if (var1.A()) {
               this.h(var1.B());
            }

            if (var1.C()) {
               this.i(var1.D());
            }

            if (var1.E()) {
               this.j(var1.F());
            }

            if (var1.G()) {
               this.k(var1.H());
            }

            if (var1.K()) {
               this.l(var1.L());
            }

            if (var1.S()) {
               this.m(var1.T());
            }

            if (var1.U()) {
               this.n(var1.V());
            }

            if (var1.W()) {
               this.o(var1.X());
            }

            if (var1.Y()) {
               this.p(var1.Z());
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public PBMessageALL7.a.a e(a.h var1, n var2) throws IOException {
            PBMessageALL7.a var3 = null;

            try {
               var3 = PBMessageALL7.a.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL7.a)var8.a();
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

         public PBMessageALL7.a.a b(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a O() {
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

         public PBMessageALL7.a.a c(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a P() {
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

         public PBMessageALL7.a.a d(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a aa() {
            this.a &= -5;
            this.d = 0;
            this.t_();
            return this;
         }

         private void aw() {
            if ((this.a & 8) != 8) {
               this.e = new ArrayList<>(this.e);
               this.a |= 8;
            }
         }

         @Override
         public List<Integer> u() {
            return Collections.unmodifiableList(this.e);
         }

         @Override
         public int v() {
            return this.e.size();
         }

         @Override
         public int a(int var1) {
            return this.e.get(var1);
         }

         public PBMessageALL7.a.a a(int var1, int var2) {
            this.aw();
            this.e.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a e(int var1) {
            this.aw();
            this.e.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a a(Iterable<? extends Integer> var1) {
            this.aw();
            p.a.a(var1, this.e);
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a ab() {
            this.e = Collections.emptyList();
            this.a &= -9;
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

         public PBMessageALL7.a.a f(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a ac() {
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

         public PBMessageALL7.a.a g(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a ad() {
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

         public PBMessageALL7.a.a h(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a ae() {
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

         public PBMessageALL7.a.a i(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a af() {
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

         public PBMessageALL7.a.a j(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a ag() {
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

         public PBMessageALL7.a.a k(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a an() {
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

         public PBMessageALL7.a.a l(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a ao() {
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

         public PBMessageALL7.a.a m(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a ap() {
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

         public PBMessageALL7.a.a n(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a aq() {
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

         public PBMessageALL7.a.a o(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a ar() {
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

         public PBMessageALL7.a.a p(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.a.a as() {
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
         a(a.p.b var1, PBMessageALL7.a.a var2) {
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

      List<Integer> u();

      int v();

      int a(int var1);

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

   public static final class c extends p implements PBMessageALL7.d {
      private static final PBMessageALL7.c x = new PBMessageALL7.c(true);
      private final ap y;
      public static ab<PBMessageALL7.c> a = new a.c<PBMessageALL7.c>() {
         public PBMessageALL7.c c(a.h var1, n var2) throws s {
            return new PBMessageALL7.c(var1, var2, null);
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
      private List<Integer> U;
      private byte V = -1;
      private int W = -1;
      private static final long X = 0L;

      static {
         x.as();
      }

      private c(p.a<?> var1) {
         super(var1);
         this.y = var1.b_();
      }

      private c(boolean var1) {
         this.y = ap.c();
      }

      public static PBMessageALL7.c h() {
         return x;
      }

      public PBMessageALL7.c k() {
         return x;
      }

      @Override
      public final ap b_() {
         return this.y;
      }

      private c(a.h var1, n var2) throws s {
         this.as();
         int var3 = 0;
         ap.a var4 = ap.b();

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
                     this.z |= 1;
                     this.A = var1.g();
                     continue;
                  case 16:
                     this.z |= 2;
                     this.B = var1.g();
                     continue;
                  case 26:
                     this.z |= 4;
                     this.C = var1.l();
                     continue;
                  case 32:
                     this.z |= 8;
                     this.D = var1.g();
                     continue;
                  case 40:
                     this.z |= 16;
                     this.E = var1.g();
                     continue;
                  case 48:
                     this.z |= 32;
                     this.F = var1.g();
                     continue;
                  case 56:
                     this.z |= 64;
                     this.G = var1.g();
                     continue;
                  case 64:
                     this.z |= 128;
                     this.H = var1.g();
                     continue;
                  case 72:
                     this.z |= 256;
                     this.I = var1.g();
                     continue;
                  case 80:
                     this.z |= 512;
                     this.J = var1.g();
                     continue;
                  case 88:
                     this.z |= 1024;
                     this.K = var1.g();
                     continue;
                  case 96:
                     this.z |= 2048;
                     this.L = var1.g();
                     continue;
                  case 104:
                     this.z |= 4096;
                     this.M = var1.g();
                     continue;
                  case 112:
                     this.z |= 8192;
                     this.N = var1.g();
                     continue;
                  case 120:
                     this.z |= 16384;
                     this.O = var1.g();
                     continue;
                  case 128:
                     this.z |= 32768;
                     this.P = var1.g();
                     continue;
                  case 136:
                     this.z |= 65536;
                     this.Q = var1.g();
                     continue;
                  case 146:
                     this.z |= 131072;
                     this.R = var1.l();
                     continue;
                  case 152:
                     this.z |= 262144;
                     this.S = var1.g();
                     continue;
                  case 160:
                     this.z |= 524288;
                     this.T = var1.g();
                     continue;
                  case 168:
                     if ((var3 & 1048576) != 1048576) {
                        this.U = new ArrayList<>();
                        var3 |= 1048576;
                     }

                     this.U.add(var1.g());
                     continue;
                  case 170:
                     int var7 = var1.s();
                     var8 = var1.f(var7);
                     if ((var3 & 1048576) != 1048576 && var1.x() > 0) {
                        this.U = new ArrayList<>();
                        var3 |= 1048576;
                     }
                     break;
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
            if ((var3 & 1048576) == 1048576) {
               this.U = Collections.unmodifiableList(this.U);
            }

            this.y = var4.b();
            this.ad();
         }
      }

      public static final k.a n() {
         return PBMessageALL7.c;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL7.d.a(PBMessageALL7.c.class, PBMessageALL7.c.a.class);
      }

      @Override
      public ab<PBMessageALL7.c> m() {
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
      public a.g I_() {
         return this.R;
      }

      @Override
      public boolean J_() {
         return (this.z & 262144) == 262144;
      }

      @Override
      public int K_() {
         return this.S;
      }

      @Override
      public boolean L_() {
         return (this.z & 524288) == 524288;
      }

      @Override
      public int M_() {
         return this.T;
      }

      @Override
      public List<Integer> N_() {
         return this.U;
      }

      @Override
      public int an() {
         return this.U.size();
      }

      @Override
      public int a(int var1) {
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
            var2 += a.i.h(this.U.get(var3));
         }

         var1 += var2;
         var1 += 2 * this.N_().size();
         var1 += this.b_().d();
         this.W = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static PBMessageALL7.c a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL7.c a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL7.c a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL7.c a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL7.c a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL7.c a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL7.c b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL7.c b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL7.c a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL7.c a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL7.c.a ao() {
         return PBMessageALL7.c.a.aN();
      }

      public PBMessageALL7.c.a ap() {
         return ao();
      }

      public static PBMessageALL7.c.a a(PBMessageALL7.c var0) {
         return ao().a(var0);
      }

      public PBMessageALL7.c.a aq() {
         return a(this);
      }

      protected PBMessageALL7.c.a a(a.p.b var1) {
         return new PBMessageALL7.c.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.aq();
      }

      // $VF: synthetic method
      @Override
      public x.a M() {
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
      protected x.a b(a.p.b var1) {
         return this.a(var1);
      }

      // $VF: synthetic method
      @Override
      public a.y.a P() {
         return this.ap();
      }

      // $VF: synthetic method
      @Override
      public x.a N() {
         return this.ap();
      }

      // $VF: synthetic method
      c(a.h var1, n var2, PBMessageALL7.c var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      c(p.a var1, PBMessageALL7.c var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL7.c.a> implements PBMessageALL7.d {
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
         private List<Integer> v;

         public static final k.a k() {
            return PBMessageALL7.c;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL7.d.a(PBMessageALL7.c.class, PBMessageALL7.c.a.class);
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
            PBMessageALL7.c.ar();
         }

         private static PBMessageALL7.c.a aN() {
            return new PBMessageALL7.c.a();
         }

         public PBMessageALL7.c.a m() {
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

         public PBMessageALL7.c.a n() {
            return aN().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL7.c;
         }

         public PBMessageALL7.c I() {
            return PBMessageALL7.c.h();
         }

         public PBMessageALL7.c M() {
            PBMessageALL7.c var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL7.c N() {
            PBMessageALL7.c var1 = new PBMessageALL7.c(this, null);
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

         public PBMessageALL7.c.a d(x var1) {
            if (var1 instanceof PBMessageALL7.c) {
               return this.a((PBMessageALL7.c)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL7.c.a a(PBMessageALL7.c var1) {
            if (var1 == PBMessageALL7.c.h()) {
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
               this.f(var1.I_());
            }

            if (var1.J_()) {
               this.r(var1.K_());
            }

            if (var1.L_()) {
               this.s(var1.M_());
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

         public PBMessageALL7.c.a e(a.h var1, n var2) throws IOException {
            PBMessageALL7.c var3 = null;

            try {
               var3 = PBMessageALL7.c.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL7.c)var8.a();
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

         public PBMessageALL7.c.a b(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a O() {
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

         public PBMessageALL7.c.a c(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a P() {
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

         public PBMessageALL7.c.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a ac() {
            this.a &= -5;
            this.d = PBMessageALL7.c.h().t();
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

         public PBMessageALL7.c.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a ad() {
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

         public PBMessageALL7.c.a e(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a ao() {
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

         public PBMessageALL7.c.a f(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a ap() {
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

         public PBMessageALL7.c.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a aq() {
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

         public PBMessageALL7.c.a h(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a ar() {
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

         public PBMessageALL7.c.a i(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a as() {
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

         public PBMessageALL7.c.a j(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a at() {
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

         public PBMessageALL7.c.a k(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a au() {
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

         public PBMessageALL7.c.a l(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a av() {
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

         public PBMessageALL7.c.a m(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a aw() {
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

         public PBMessageALL7.c.a n(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a ax() {
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

         public PBMessageALL7.c.a o(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a ay() {
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

         public PBMessageALL7.c.a p(int var1) {
            this.a |= 32768;
            this.q = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a az() {
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

         public PBMessageALL7.c.a q(int var1) {
            this.a |= 65536;
            this.r = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a aG() {
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
         public a.g I_() {
            return this.s;
         }

         public PBMessageALL7.c.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 131072;
            this.s = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a aH() {
            this.a &= -131073;
            this.s = PBMessageALL7.c.h().I_();
            this.t_();
            return this;
         }

         @Override
         public boolean J_() {
            return (this.a & 262144) == 262144;
         }

         @Override
         public int K_() {
            return this.t;
         }

         public PBMessageALL7.c.a r(int var1) {
            this.a |= 262144;
            this.t = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a aI() {
            this.a &= -262145;
            this.t = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean L_() {
            return (this.a & 524288) == 524288;
         }

         @Override
         public int M_() {
            return this.u;
         }

         public PBMessageALL7.c.a s(int var1) {
            this.a |= 524288;
            this.u = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a aJ() {
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
         public List<Integer> N_() {
            return Collections.unmodifiableList(this.v);
         }

         @Override
         public int an() {
            return this.v.size();
         }

         @Override
         public int a(int var1) {
            return this.v.get(var1);
         }

         public PBMessageALL7.c.a a(int var1, int var2) {
            this.aO();
            this.v.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a t(int var1) {
            this.aO();
            this.v.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a a(Iterable<? extends Integer> var1) {
            this.aO();
            p.a.a(var1, this.v);
            this.t_();
            return this;
         }

         public PBMessageALL7.c.a aK() {
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
         a(a.p.b var1, PBMessageALL7.c.a var2) {
            this(var1);
         }
      }
   }

   public interface d extends aa {
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

      a.g I_();

      boolean J_();

      int K_();

      boolean L_();

      int M_();

      List<Integer> N_();

      int an();

      int a(int var1);
   }

   public static final class e extends p implements PBMessageALL7.f {
      private static final PBMessageALL7.e r = new PBMessageALL7.e(true);
      private final ap s;
      public static ab<PBMessageALL7.e> a = new a.c<PBMessageALL7.e>() {
         public PBMessageALL7.e c(a.h var1, n var2) throws s {
            return new PBMessageALL7.e(var1, var2, null);
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
      private List<Integer> y;
      public static final int g = 6;
      private List<Integer> z;
      public static final int h = 7;
      private List<Integer> A;
      public static final int i = 8;
      private List<Integer> B;
      public static final int j = 9;
      private List<Integer> C;
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

      private e(p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private e(boolean var1) {
         this.s = ap.c();
      }

      public static PBMessageALL7.e h() {
         return r;
      }

      public PBMessageALL7.e k() {
         return r;
      }

      @Override
      public final ap b_() {
         return this.s;
      }

      private e(a.h var1, n var2) throws s {
         this.ag();
         int var3 = 0;
         ap.a var4 = ap.b();

         try {
            boolean var5 = false;

            while (!var5) {
               int var6 = var1.a();
               int var23;
               switch (var6) {
                  case 0:
                     var5 = true;
                     continue;
                  case 8:
                     this.t |= 1;
                     this.u = var1.g();
                     continue;
                  case 16:
                     this.t |= 2;
                     this.v = var1.g();
                     continue;
                  case 24:
                     this.t |= 4;
                     this.w = var1.g();
                     continue;
                  case 32:
                     this.t |= 8;
                     this.x = var1.g();
                     continue;
                  case 40:
                     if ((var3 & 16) != 16) {
                        this.y = new ArrayList<>();
                        var3 |= 16;
                     }

                     this.y.add(var1.g());
                     continue;
                  case 42:
                     int var19 = var1.s();
                     var23 = var1.f(var19);
                     if ((var3 & 16) != 16 && var1.x() > 0) {
                        this.y = new ArrayList<>();
                        var3 |= 16;
                     }
                     break;
                  case 48:
                     if ((var3 & 32) != 32) {
                        this.z = new ArrayList<>();
                        var3 |= 32;
                     }

                     this.z.add(var1.g());
                     continue;
                  case 50:
                     int lengthx = var1.s();
                     var23 = var1.f(lengthx);
                     if ((var3 & 32) != 32 && var1.x() > 0) {
                        this.z = new ArrayList<>();
                        var3 |= 32;
                     }

                     while (var1.x() > 0) {
                        this.z.add(var1.g());
                     }

                     var1.g(var23);
                     continue;
                  case 56:
                     if ((var3 & 64) != 64) {
                        this.A = new ArrayList<>();
                        var3 |= 64;
                     }

                     this.A.add(var1.g());
                     continue;
                  case 58:
                     int lengthx = var1.s();
                     var23 = var1.f(lengthx);
                     if ((var3 & 64) != 64 && var1.x() > 0) {
                        this.A = new ArrayList<>();
                        var3 |= 64;
                     }

                     while (var1.x() > 0) {
                        this.A.add(var1.g());
                     }

                     var1.g(var23);
                     continue;
                  case 64:
                     if ((var3 & 128) != 128) {
                        this.B = new ArrayList<>();
                        var3 |= 128;
                     }

                     this.B.add(var1.g());
                     continue;
                  case 66:
                     int lengthx = var1.s();
                     var23 = var1.f(lengthx);
                     if ((var3 & 128) != 128 && var1.x() > 0) {
                        this.B = new ArrayList<>();
                        var3 |= 128;
                     }

                     while (var1.x() > 0) {
                        this.B.add(var1.g());
                     }

                     var1.g(var23);
                     continue;
                  case 72:
                     if ((var3 & 256) != 256) {
                        this.C = new ArrayList<>();
                        var3 |= 256;
                     }

                     this.C.add(var1.g());
                     continue;
                  case 74:
                     int lengthx = var1.s();
                     var23 = var1.f(lengthx);
                     if ((var3 & 256) != 256 && var1.x() > 0) {
                        this.C = new ArrayList<>();
                        var3 |= 256;
                     }

                     while (var1.x() > 0) {
                        this.C.add(var1.g());
                     }

                     var1.g(var23);
                     continue;
                  case 80:
                     this.t |= 16;
                     this.D = var1.g();
                     continue;
                  case 88:
                     this.t |= 32;
                     this.E = var1.g();
                     continue;
                  case 96:
                     this.t |= 64;
                     this.F = var1.g();
                     continue;
                  case 104:
                     this.t |= 128;
                     this.G = var1.g();
                     continue;
                  case 112:
                     this.t |= 256;
                     this.H = var1.g();
                     continue;
                  case 120:
                     this.t |= 512;
                     this.I = var1.g();
                     continue;
                  default:
                     if (!this.a(var1, var4, var2, var6)) {
                        var5 = true;
                     }
                     continue;
               }

               while (var1.x() > 0) {
                  this.y.add(var1.g());
               }

               var1.g(var23);
            }
         } catch (s var13) {
            throw var13.a(this);
         } catch (IOException var14) {
            throw new s(var14.getMessage()).a(this);
         } finally {
            if ((var3 & 16) == 16) {
               this.y = Collections.unmodifiableList(this.y);
            }

            if ((var3 & 32) == 32) {
               this.z = Collections.unmodifiableList(this.z);
            }

            if ((var3 & 64) == 64) {
               this.A = Collections.unmodifiableList(this.A);
            }

            if ((var3 & 128) == 128) {
               this.B = Collections.unmodifiableList(this.B);
            }

            if ((var3 & 256) == 256) {
               this.C = Collections.unmodifiableList(this.C);
            }

            this.s = var4.b();
            this.ad();
         }
      }

      public static final k.a n() {
         return PBMessageALL7.e;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL7.f.a(PBMessageALL7.e.class, PBMessageALL7.e.a.class);
      }

      @Override
      public ab<PBMessageALL7.e> m() {
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
      public List<Integer> w() {
         return this.y;
      }

      @Override
      public int x() {
         return this.y.size();
      }

      @Override
      public int a(int var1) {
         return this.y.get(var1);
      }

      @Override
      public List<Integer> y() {
         return this.z;
      }

      @Override
      public int z() {
         return this.z.size();
      }

      @Override
      public int b(int var1) {
         return this.z.get(var1);
      }

      @Override
      public List<Integer> A() {
         return this.A;
      }

      @Override
      public int B() {
         return this.A.size();
      }

      @Override
      public int c(int var1) {
         return this.A.get(var1);
      }

      @Override
      public List<Integer> C() {
         return this.B;
      }

      @Override
      public int D() {
         return this.B.size();
      }

      @Override
      public int d(int var1) {
         return this.B.get(var1);
      }

      @Override
      public List<Integer> E() {
         return this.C;
      }

      @Override
      public int F() {
         return this.C.size();
      }

      @Override
      public int e(int var1) {
         return this.C.get(var1);
      }

      @Override
      public boolean G() {
         return (this.t & 16) == 16;
      }

      @Override
      public int H() {
         return this.D;
      }

      @Override
      public boolean K() {
         return (this.t & 32) == 32;
      }

      @Override
      public int L() {
         return this.E;
      }

      @Override
      public boolean S() {
         return (this.t & 64) == 64;
      }

      @Override
      public int T() {
         return this.F;
      }

      @Override
      public boolean U() {
         return (this.t & 128) == 128;
      }

      @Override
      public int V() {
         return this.G;
      }

      @Override
      public boolean W() {
         return (this.t & 256) == 256;
      }

      @Override
      public int X() {
         return this.H;
      }

      @Override
      public boolean Y() {
         return (this.t & 512) == 512;
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
         this.y = Collections.emptyList();
         this.z = Collections.emptyList();
         this.A = Collections.emptyList();
         this.B = Collections.emptyList();
         this.C = Collections.emptyList();
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

         for (int var2 = 0; var2 < this.y.size(); var2++) {
            var1.a(5, this.y.get(var2));
         }

         for (int var3 = 0; var3 < this.z.size(); var3++) {
            var1.a(6, this.z.get(var3));
         }

         for (int var4 = 0; var4 < this.A.size(); var4++) {
            var1.a(7, this.A.get(var4));
         }

         for (int var5 = 0; var5 < this.B.size(); var5++) {
            var1.a(8, this.B.get(var5));
         }

         for (int var6 = 0; var6 < this.C.size(); var6++) {
            var1.a(9, this.C.get(var6));
         }

         if ((this.t & 16) == 16) {
            var1.a(10, this.D);
         }

         if ((this.t & 32) == 32) {
            var1.a(11, this.E);
         }

         if ((this.t & 64) == 64) {
            var1.a(12, this.F);
         }

         if ((this.t & 128) == 128) {
            var1.a(13, this.G);
         }

         if ((this.t & 256) == 256) {
            var1.a(14, this.H);
         }

         if ((this.t & 512) == 512) {
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

         int var2 = 0;

         for (int var3 = 0; var3 < this.y.size(); var3++) {
            var2 += a.i.h(this.y.get(var3));
         }

         var1 += var2;
         var1 += 1 * this.w().size();
         var2 = 0;

         for (int var20 = 0; var20 < this.z.size(); var20++) {
            var2 += a.i.h(this.z.get(var20));
         }

         var1 += var2;
         var1 += 1 * this.y().size();
         var2 = 0;

         for (int var21 = 0; var21 < this.A.size(); var21++) {
            var2 += a.i.h(this.A.get(var21));
         }

         var1 += var2;
         var1 += 1 * this.A().size();
         var2 = 0;

         for (int var22 = 0; var22 < this.B.size(); var22++) {
            var2 += a.i.h(this.B.get(var22));
         }

         var1 += var2;
         var1 += 1 * this.C().size();
         var2 = 0;

         for (int var23 = 0; var23 < this.C.size(); var23++) {
            var2 += a.i.h(this.C.get(var23));
         }

         var1 += var2;
         var1 += 1 * this.E().size();
         if ((this.t & 16) == 16) {
            var1 += a.i.g(10, this.D);
         }

         if ((this.t & 32) == 32) {
            var1 += a.i.g(11, this.E);
         }

         if ((this.t & 64) == 64) {
            var1 += a.i.g(12, this.F);
         }

         if ((this.t & 128) == 128) {
            var1 += a.i.g(13, this.G);
         }

         if ((this.t & 256) == 256) {
            var1 += a.i.g(14, this.H);
         }

         if ((this.t & 512) == 512) {
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

      public static PBMessageALL7.e a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL7.e a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL7.e a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL7.e a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL7.e a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL7.e a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL7.e b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL7.e b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL7.e a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL7.e a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL7.e.a aa() {
         return PBMessageALL7.e.a.av();
      }

      public PBMessageALL7.e.a ab() {
         return aa();
      }

      public static PBMessageALL7.e.a a(PBMessageALL7.e var0) {
         return aa().a(var0);
      }

      public PBMessageALL7.e.a ae() {
         return a(this);
      }

      protected PBMessageALL7.e.a a(a.p.b var1) {
         return new PBMessageALL7.e.a(var1, null);
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
      e(a.h var1, n var2, PBMessageALL7.e var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      e(p.a var1, PBMessageALL7.e var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL7.e.a> implements PBMessageALL7.f {
         private int a;
         private int b;
         private int c;
         private int d;
         private int e;
         private List<Integer> f = Collections.emptyList();
         private List<Integer> g = Collections.emptyList();
         private List<Integer> h = Collections.emptyList();
         private List<Integer> i = Collections.emptyList();
         private List<Integer> j = Collections.emptyList();
         private int k;
         private int l;
         private int m;
         private int n;
         private int o;
         private int p;

         public static final k.a k() {
            return PBMessageALL7.e;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL7.f.a(PBMessageALL7.e.class, PBMessageALL7.e.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            PBMessageALL7.e.af();
         }

         private static PBMessageALL7.e.a av() {
            return new PBMessageALL7.e.a();
         }

         public PBMessageALL7.e.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = 0;
            this.a &= -3;
            this.d = 0;
            this.a &= -5;
            this.e = 0;
            this.a &= -9;
            this.f = Collections.emptyList();
            this.a &= -17;
            this.g = Collections.emptyList();
            this.a &= -33;
            this.h = Collections.emptyList();
            this.a &= -65;
            this.i = Collections.emptyList();
            this.a &= -129;
            this.j = Collections.emptyList();
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

         public PBMessageALL7.e.a n() {
            return av().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL7.e;
         }

         public PBMessageALL7.e I() {
            return PBMessageALL7.e.h();
         }

         public PBMessageALL7.e M() {
            PBMessageALL7.e var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL7.e N() {
            PBMessageALL7.e var1 = new PBMessageALL7.e(this, null);
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
            if ((this.a & 16) == 16) {
               this.f = Collections.unmodifiableList(this.f);
               this.a &= -17;
            }

            var1.y = this.f;
            if ((this.a & 32) == 32) {
               this.g = Collections.unmodifiableList(this.g);
               this.a &= -33;
            }

            var1.z = this.g;
            if ((this.a & 64) == 64) {
               this.h = Collections.unmodifiableList(this.h);
               this.a &= -65;
            }

            var1.A = this.h;
            if ((this.a & 128) == 128) {
               this.i = Collections.unmodifiableList(this.i);
               this.a &= -129;
            }

            var1.B = this.i;
            if ((this.a & 256) == 256) {
               this.j = Collections.unmodifiableList(this.j);
               this.a &= -257;
            }

            var1.C = this.j;
            if ((var2 & 512) == 512) {
               var3 |= 16;
            }

            var1.D = this.k;
            if ((var2 & 1024) == 1024) {
               var3 |= 32;
            }

            var1.E = this.l;
            if ((var2 & 2048) == 2048) {
               var3 |= 64;
            }

            var1.F = this.m;
            if ((var2 & 4096) == 4096) {
               var3 |= 128;
            }

            var1.G = this.n;
            if ((var2 & 8192) == 8192) {
               var3 |= 256;
            }

            var1.H = this.o;
            if ((var2 & 16384) == 16384) {
               var3 |= 512;
            }

            var1.I = this.p;
            var1.t = var3;
            this.q_();
            return var1;
         }

         public PBMessageALL7.e.a d(x var1) {
            if (var1 instanceof PBMessageALL7.e) {
               return this.a((PBMessageALL7.e)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL7.e.a a(PBMessageALL7.e var1) {
            if (var1 == PBMessageALL7.e.h()) {
               return this;
            }

            if (var1.o()) {
               this.f(var1.p());
            }

            if (var1.q()) {
               this.g(var1.r());
            }

            if (var1.s()) {
               this.h(var1.t());
            }

            if (var1.u()) {
               this.i(var1.v());
            }

            if (!var1.y.isEmpty()) {
               if (this.f.isEmpty()) {
                  this.f = var1.y;
                  this.a &= -17;
               } else {
                  this.aw();
                  this.f.addAll(var1.y);
               }

               this.t_();
            }

            if (!var1.z.isEmpty()) {
               if (this.g.isEmpty()) {
                  this.g = var1.z;
                  this.a &= -33;
               } else {
                  this.ax();
                  this.g.addAll(var1.z);
               }

               this.t_();
            }

            if (!var1.A.isEmpty()) {
               if (this.h.isEmpty()) {
                  this.h = var1.A;
                  this.a &= -65;
               } else {
                  this.ay();
                  this.h.addAll(var1.A);
               }

               this.t_();
            }

            if (!var1.B.isEmpty()) {
               if (this.i.isEmpty()) {
                  this.i = var1.B;
                  this.a &= -129;
               } else {
                  this.az();
                  this.i.addAll(var1.B);
               }

               this.t_();
            }

            if (!var1.C.isEmpty()) {
               if (this.j.isEmpty()) {
                  this.j = var1.C;
                  this.a &= -257;
               } else {
                  this.aG();
                  this.j.addAll(var1.C);
               }

               this.t_();
            }

            if (var1.G()) {
               this.o(var1.H());
            }

            if (var1.K()) {
               this.p(var1.L());
            }

            if (var1.S()) {
               this.q(var1.T());
            }

            if (var1.U()) {
               this.r(var1.V());
            }

            if (var1.W()) {
               this.s(var1.X());
            }

            if (var1.Y()) {
               this.t(var1.Z());
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public PBMessageALL7.e.a e(a.h var1, n var2) throws IOException {
            PBMessageALL7.e var3 = null;

            try {
               var3 = PBMessageALL7.e.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL7.e)var8.a();
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

         public PBMessageALL7.e.a f(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a O() {
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

         public PBMessageALL7.e.a g(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a P() {
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

         public PBMessageALL7.e.a h(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a aa() {
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

         public PBMessageALL7.e.a i(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a ab() {
            this.a &= -9;
            this.e = 0;
            this.t_();
            return this;
         }

         private void aw() {
            if ((this.a & 16) != 16) {
               this.f = new ArrayList<>(this.f);
               this.a |= 16;
            }
         }

         @Override
         public List<Integer> w() {
            return Collections.unmodifiableList(this.f);
         }

         @Override
         public int x() {
            return this.f.size();
         }

         @Override
         public int a(int var1) {
            return this.f.get(var1);
         }

         public PBMessageALL7.e.a a(int var1, int var2) {
            this.aw();
            this.f.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a j(int var1) {
            this.aw();
            this.f.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a a(Iterable<? extends Integer> var1) {
            this.aw();
            p.a.a(var1, this.f);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a ac() {
            this.f = Collections.emptyList();
            this.a &= -17;
            this.t_();
            return this;
         }

         private void ax() {
            if ((this.a & 32) != 32) {
               this.g = new ArrayList<>(this.g);
               this.a |= 32;
            }
         }

         @Override
         public List<Integer> y() {
            return Collections.unmodifiableList(this.g);
         }

         @Override
         public int z() {
            return this.g.size();
         }

         @Override
         public int b(int var1) {
            return this.g.get(var1);
         }

         public PBMessageALL7.e.a b(int var1, int var2) {
            this.ax();
            this.g.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a k(int var1) {
            this.ax();
            this.g.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a b(Iterable<? extends Integer> var1) {
            this.ax();
            p.a.a(var1, this.g);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a ad() {
            this.g = Collections.emptyList();
            this.a &= -33;
            this.t_();
            return this;
         }

         private void ay() {
            if ((this.a & 64) != 64) {
               this.h = new ArrayList<>(this.h);
               this.a |= 64;
            }
         }

         @Override
         public List<Integer> A() {
            return Collections.unmodifiableList(this.h);
         }

         @Override
         public int B() {
            return this.h.size();
         }

         @Override
         public int c(int var1) {
            return this.h.get(var1);
         }

         public PBMessageALL7.e.a c(int var1, int var2) {
            this.ay();
            this.h.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a l(int var1) {
            this.ay();
            this.h.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a c(Iterable<? extends Integer> var1) {
            this.ay();
            p.a.a(var1, this.h);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a ae() {
            this.h = Collections.emptyList();
            this.a &= -65;
            this.t_();
            return this;
         }

         private void az() {
            if ((this.a & 128) != 128) {
               this.i = new ArrayList<>(this.i);
               this.a |= 128;
            }
         }

         @Override
         public List<Integer> C() {
            return Collections.unmodifiableList(this.i);
         }

         @Override
         public int D() {
            return this.i.size();
         }

         @Override
         public int d(int var1) {
            return this.i.get(var1);
         }

         public PBMessageALL7.e.a d(int var1, int var2) {
            this.az();
            this.i.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a m(int var1) {
            this.az();
            this.i.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a d(Iterable<? extends Integer> var1) {
            this.az();
            p.a.a(var1, this.i);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a af() {
            this.i = Collections.emptyList();
            this.a &= -129;
            this.t_();
            return this;
         }

         private void aG() {
            if ((this.a & 256) != 256) {
               this.j = new ArrayList<>(this.j);
               this.a |= 256;
            }
         }

         @Override
         public List<Integer> E() {
            return Collections.unmodifiableList(this.j);
         }

         @Override
         public int F() {
            return this.j.size();
         }

         @Override
         public int e(int var1) {
            return this.j.get(var1);
         }

         public PBMessageALL7.e.a e(int var1, int var2) {
            this.aG();
            this.j.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a n(int var1) {
            this.aG();
            this.j.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a e(Iterable<? extends Integer> var1) {
            this.aG();
            p.a.a(var1, this.j);
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a ag() {
            this.j = Collections.emptyList();
            this.a &= -257;
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

         public PBMessageALL7.e.a o(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a an() {
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

         public PBMessageALL7.e.a p(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a ao() {
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

         public PBMessageALL7.e.a q(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a ap() {
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

         public PBMessageALL7.e.a r(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a aq() {
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

         public PBMessageALL7.e.a s(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a ar() {
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

         public PBMessageALL7.e.a t(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.e.a as() {
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
         a(a.p.b var1, PBMessageALL7.e.a var2) {
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

      List<Integer> w();

      int x();

      int a(int var1);

      List<Integer> y();

      int z();

      int b(int var1);

      List<Integer> A();

      int B();

      int c(int var1);

      List<Integer> C();

      int D();

      int d(int var1);

      List<Integer> E();

      int F();

      int e(int var1);

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

   public static final class g extends p implements PBMessageALL7.h {
      private static final PBMessageALL7.g r = new PBMessageALL7.g(true);
      private final ap s;
      public static ab<PBMessageALL7.g> a = new a.c<PBMessageALL7.g>() {
         public PBMessageALL7.g c(a.h var1, n var2) throws s {
            return new PBMessageALL7.g(var1, var2, null);
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

      private g(p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private g(boolean var1) {
         this.s = ap.c();
      }

      public static PBMessageALL7.g h() {
         return r;
      }

      public PBMessageALL7.g k() {
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
                  case 26:
                     if ((var3 & 4) != 4) {
                        this.w = new ArrayList<>();
                        var3 |= 4;
                     }

                     this.w.add(var1.l());
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
            if ((var3 & 2) == 2) {
               this.v = Collections.unmodifiableList(this.v);
            }

            if ((var3 & 4) == 4) {
               this.w = Collections.unmodifiableList(this.w);
            }

            this.s = var4.b();
            this.ad();
         }
      }

      public static final k.a n() {
         return PBMessageALL7.g;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL7.h.a(PBMessageALL7.g.class, PBMessageALL7.g.a.class);
      }

      @Override
      public ab<PBMessageALL7.g> m() {
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
      public List<a.g> s() {
         return this.w;
      }

      @Override
      public int t() {
         return this.w.size();
      }

      @Override
      public a.g b(int var1) {
         return this.w.get(var1);
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
         this.u = 0;
         this.v = Collections.emptyList();
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

         for (int var2 = 0; var2 < this.v.size(); var2++) {
            var1.a(2, this.v.get(var2));
         }

         for (int var3 = 0; var3 < this.w.size(); var3++) {
            var1.a(3, this.w.get(var3));
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
         var2 = 0;

         for (int var11 = 0; var11 < this.w.size(); var11++) {
            var2 += a.i.b(this.w.get(var11));
         }

         var1 += var2;
         var1 += 1 * this.s().size();
         if ((this.t & 2) == 2) {
            var1 += a.i.g(4, this.x);
         }

         if ((this.t & 4) == 4) {
            var1 += a.i.g(5, this.y);
         }

         if ((this.t & 8) == 8) {
            var1 += a.i.g(6, this.z);
         }

         if ((this.t & 16) == 16) {
            var1 += a.i.g(7, this.A);
         }

         if ((this.t & 32) == 32) {
            var1 += a.i.g(8, this.B);
         }

         if ((this.t & 64) == 64) {
            var1 += a.i.g(9, this.C);
         }

         if ((this.t & 128) == 128) {
            var1 += a.i.g(10, this.D);
         }

         if ((this.t & 256) == 256) {
            var1 += a.i.g(11, this.E);
         }

         if ((this.t & 512) == 512) {
            var1 += a.i.g(12, this.F);
         }

         if ((this.t & 1024) == 1024) {
            var1 += a.i.g(13, this.G);
         }

         if ((this.t & 2048) == 2048) {
            var1 += a.i.g(14, this.H);
         }

         if ((this.t & 4096) == 4096) {
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

      public static PBMessageALL7.g a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL7.g a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL7.g a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL7.g a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL7.g a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL7.g a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL7.g b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL7.g b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL7.g a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL7.g a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL7.g.a aa() {
         return PBMessageALL7.g.a.av();
      }

      public PBMessageALL7.g.a ab() {
         return aa();
      }

      public static PBMessageALL7.g.a a(PBMessageALL7.g var0) {
         return aa().a(var0);
      }

      public PBMessageALL7.g.a ae() {
         return a(this);
      }

      protected PBMessageALL7.g.a a(a.p.b var1) {
         return new PBMessageALL7.g.a(var1, null);
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
      g(a.h var1, n var2, PBMessageALL7.g var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      g(p.a var1, PBMessageALL7.g var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL7.g.a> implements PBMessageALL7.h {
         private int a;
         private int b;
         private List<a.g> c = Collections.emptyList();
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
            return PBMessageALL7.g;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL7.h.a(PBMessageALL7.g.class, PBMessageALL7.g.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            PBMessageALL7.g.af();
         }

         private static PBMessageALL7.g.a av() {
            return new PBMessageALL7.g.a();
         }

         public PBMessageALL7.g.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = Collections.emptyList();
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

         public PBMessageALL7.g.a n() {
            return av().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL7.g;
         }

         public PBMessageALL7.g I() {
            return PBMessageALL7.g.h();
         }

         public PBMessageALL7.g M() {
            PBMessageALL7.g var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL7.g N() {
            PBMessageALL7.g var1 = new PBMessageALL7.g(this, null);
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
            if ((this.a & 4) == 4) {
               this.d = Collections.unmodifiableList(this.d);
               this.a &= -5;
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

         public PBMessageALL7.g.a d(x var1) {
            if (var1 instanceof PBMessageALL7.g) {
               return this.a((PBMessageALL7.g)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL7.g.a a(PBMessageALL7.g var1) {
            if (var1 == PBMessageALL7.g.h()) {
               return this;
            }

            if (var1.o()) {
               this.c(var1.p());
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

            if (!var1.w.isEmpty()) {
               if (this.d.isEmpty()) {
                  this.d = var1.w;
                  this.a &= -5;
               } else {
                  this.ax();
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

         public PBMessageALL7.g.a e(a.h var1, n var2) throws IOException {
            PBMessageALL7.g var3 = null;

            try {
               var3 = PBMessageALL7.g.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL7.g)var8.a();
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

         public PBMessageALL7.g.a c(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a O() {
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

         public PBMessageALL7.g.a a(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.c.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.c.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a a(Iterable<? extends a.g> var1) {
            this.aw();
            p.a.a(var1, this.c);
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a P() {
            this.c = Collections.emptyList();
            this.a &= -3;
            this.t_();
            return this;
         }

         private void ax() {
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
         public a.g b(int var1) {
            return this.d.get(var1);
         }

         public PBMessageALL7.g.a b(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.ax();
            this.d.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.ax();
            this.d.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a b(Iterable<? extends a.g> var1) {
            this.ax();
            p.a.a(var1, this.d);
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a aa() {
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

         public PBMessageALL7.g.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a ab() {
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

         public PBMessageALL7.g.a e(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a ac() {
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

         public PBMessageALL7.g.a f(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a ad() {
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

         public PBMessageALL7.g.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a ae() {
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

         public PBMessageALL7.g.a h(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a af() {
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

         public PBMessageALL7.g.a i(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a ag() {
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

         public PBMessageALL7.g.a j(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a an() {
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

         public PBMessageALL7.g.a k(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a ao() {
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

         public PBMessageALL7.g.a l(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a ap() {
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

         public PBMessageALL7.g.a m(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a aq() {
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

         public PBMessageALL7.g.a n(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a ar() {
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

         public PBMessageALL7.g.a o(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.g.a as() {
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
         a(a.p.b var1, PBMessageALL7.g.a var2) {
            this(var1);
         }
      }
   }

   public interface h extends aa {
      boolean o();

      int p();

      List<a.g> q();

      int r();

      a.g a(int var1);

      List<a.g> s();

      int t();

      a.g b(int var1);

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

   public static final class i extends p implements PBMessageALL7.j {
      private static final PBMessageALL7.i r = new PBMessageALL7.i(true);
      private final ap s;
      public static ab<PBMessageALL7.i> a = new a.c<PBMessageALL7.i>() {
         public PBMessageALL7.i c(a.h var1, n var2) throws s {
            return new PBMessageALL7.i(var1, var2, null);
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

      private i(p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private i(boolean var1) {
         this.s = ap.c();
      }

      public static PBMessageALL7.i h() {
         return r;
      }

      public PBMessageALL7.i k() {
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

      public static final k.a n() {
         return PBMessageALL7.i;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL7.j.a(PBMessageALL7.i.class, PBMessageALL7.i.a.class);
      }

      @Override
      public ab<PBMessageALL7.i> m() {
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
         this.u = a.g.d;
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
            var1 += a.i.c(1, this.u);
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

      public static PBMessageALL7.i a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL7.i a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL7.i a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL7.i a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL7.i a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL7.i a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL7.i b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL7.i b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL7.i a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL7.i a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL7.i.a aa() {
         return PBMessageALL7.i.a.av();
      }

      public PBMessageALL7.i.a ab() {
         return aa();
      }

      public static PBMessageALL7.i.a a(PBMessageALL7.i var0) {
         return aa().a(var0);
      }

      public PBMessageALL7.i.a ae() {
         return a(this);
      }

      protected PBMessageALL7.i.a a(a.p.b var1) {
         return new PBMessageALL7.i.a(var1, null);
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
      i(a.h var1, n var2, PBMessageALL7.i var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      i(p.a var1, PBMessageALL7.i var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL7.i.a> implements PBMessageALL7.j {
         private int a;
         private a.g b = a.g.d;
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

         public static final k.a k() {
            return PBMessageALL7.i;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL7.j.a(PBMessageALL7.i.class, PBMessageALL7.i.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            PBMessageALL7.i.af();
         }

         private static PBMessageALL7.i.a av() {
            return new PBMessageALL7.i.a();
         }

         public PBMessageALL7.i.a m() {
            super.ah();
            this.b = a.g.d;
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

         public PBMessageALL7.i.a n() {
            return av().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL7.i;
         }

         public PBMessageALL7.i I() {
            return PBMessageALL7.i.h();
         }

         public PBMessageALL7.i M() {
            PBMessageALL7.i var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL7.i N() {
            PBMessageALL7.i var1 = new PBMessageALL7.i(this, null);
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

         public PBMessageALL7.i.a d(x var1) {
            if (var1 instanceof PBMessageALL7.i) {
               return this.a((PBMessageALL7.i)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL7.i.a a(PBMessageALL7.i var1) {
            if (var1 == PBMessageALL7.i.h()) {
               return this;
            }

            if (var1.o()) {
               this.e(var1.p());
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
               this.b(var1.t());
            }

            if (var1.u()) {
               this.c(var1.v());
            }

            if (var1.w()) {
               this.d(var1.x());
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

         public PBMessageALL7.i.a e(a.h var1, n var2) throws IOException {
            PBMessageALL7.i var3 = null;

            try {
               var3 = PBMessageALL7.i.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL7.i)var8.a();
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

         public PBMessageALL7.i.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a O() {
            this.a &= -2;
            this.b = PBMessageALL7.i.h().p();
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

         public PBMessageALL7.i.a a(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.c.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.c.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a a(Iterable<? extends a.g> var1) {
            this.aw();
            p.a.a(var1, this.c);
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a P() {
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

         public PBMessageALL7.i.a b(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a aa() {
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

         public PBMessageALL7.i.a c(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a ab() {
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

         public PBMessageALL7.i.a d(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a ac() {
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

         public PBMessageALL7.i.a e(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a ad() {
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

         public PBMessageALL7.i.a f(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a ae() {
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

         public PBMessageALL7.i.a g(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a af() {
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

         public PBMessageALL7.i.a h(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a ag() {
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

         public PBMessageALL7.i.a i(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a an() {
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

         public PBMessageALL7.i.a j(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a ao() {
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

         public PBMessageALL7.i.a k(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a ap() {
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

         public PBMessageALL7.i.a l(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a aq() {
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

         public PBMessageALL7.i.a m(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a ar() {
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

         public PBMessageALL7.i.a n(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL7.i.a as() {
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
         a(a.p.b var1, PBMessageALL7.i.a var2) {
            this(var1);
         }
      }
   }

   public interface j extends aa {
      boolean o();

      a.g p();

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
}
