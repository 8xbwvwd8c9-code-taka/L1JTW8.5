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

public final class PBMessageALL8 {
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
         "\n\u0013PBMessageALL8.proto\u0012 l1j.server.server.datas.protobuf\"\u0094\u0002\n\rtypeQuestInfo\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007array_3\u0018\u0003 \u0001(\f\u0012\u000f\n\u0007array_4\u0018\u0004 \u0001(\f\u0012\u000f\n\u0007array_5\u0018\u0005 \u0001(\f\u0012\u000f\n\u0007array_6\u0018\u0006 \u0001(\f\u0012\u000f\n\u0007array_7\u0018\u0007 \u0001(\f\u0012\u000f\n\u0007array_8\u0018\b \u0001(\f\u0012\u000f\n\u0007array_9\u0018\t \u0001(\f\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u0094\u0002\n\rtypeSoulTower\u0012\u000f\n\u0007array_1\u0018\u0001 \u0001(\f\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 ",
         "\u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u0095\u0002\n\u000etypeActiveNews\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007array_3\u0018\u0003 \u0001(\f\u0012\u000f\n\u0007array_4\u0018\u0004 \u0001(\f\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007array_7\u0018\u0007 \u0001(\f\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n ",
         "\u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u0096\u0002\n\u000ftypeAchievement\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007array_3\u0018\u0003 \u0001(\f\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007array_5\u0018\u0005 \u0001(\f\u0012\u000f\n\u0007array_6\u0018\u0006 \u0001(\f\u0012\u000f\n\u0007array_7\u0018\u0007 \u0001(\f\u0012\u000f\n\u0007array_8\u0018\b \u0001(\f\u0012\u000f\n\u0007array_9\u0018\t \u0001(\f\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008c\u0002\n\u0005type2\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(",
         "\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007array_3\u0018\u0003 \u0003(\f\u0012\u000f\n\u0007array_4\u0018\u0004 \u0003(\f\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005B1\n l1j.server.server.datas.protobufB\rPBMessageALL8"
      };
      a.k.g.a var1 = new a.k.g.a() {
         @Override
         public m a(k.g var1) {
            PBMessageALL8.k = var1;
            PBMessageALL8.a = PBMessageALL8.a().e().get(0);
            PBMessageALL8.b = new p.g(
               PBMessageALL8.a,
               new String[]{
                  "Value1",
                  "Value2",
                  "Array3",
                  "Array4",
                  "Array5",
                  "Array6",
                  "Array7",
                  "Array8",
                  "Array9",
                  "Value10",
                  "Value11",
                  "Value12",
                  "Value13",
                  "Value14",
                  "Value15"
               }
            );
            PBMessageALL8.c = PBMessageALL8.a().e().get(1);
            PBMessageALL8.d = new p.g(
               PBMessageALL8.c,
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
            PBMessageALL8.e = PBMessageALL8.a().e().get(2);
            PBMessageALL8.f = new p.g(
               PBMessageALL8.e,
               new String[]{
                  "Value1",
                  "Value2",
                  "Array3",
                  "Array4",
                  "Value5",
                  "Value6",
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
            PBMessageALL8.g = PBMessageALL8.a().e().get(3);
            PBMessageALL8.h = new p.g(
               PBMessageALL8.g,
               new String[]{
                  "Value1",
                  "Value2",
                  "Array3",
                  "Value4",
                  "Array5",
                  "Array6",
                  "Array7",
                  "Array8",
                  "Array9",
                  "Value10",
                  "Value11",
                  "Value12",
                  "Value13",
                  "Value14",
                  "Value15"
               }
            );
            PBMessageALL8.i = PBMessageALL8.a().e().get(4);
            PBMessageALL8.j = new p.g(
               PBMessageALL8.i,
               new String[]{
                  "Value1",
                  "Value2",
                  "Array3",
                  "Array4",
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

   private PBMessageALL8() {
   }

   public static void a(m var0) {
   }

   public static k.g a() {
      return k;
   }

   public static final class a extends p implements PBMessageALL8.b {
      private static final PBMessageALL8.a r = new PBMessageALL8.a(true);
      private final ap s;
      public static ab<PBMessageALL8.a> a = new a.c<PBMessageALL8.a>() {
         public PBMessageALL8.a c(a.h var1, n var2) throws s {
            return new PBMessageALL8.a(var1, var2, null);
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
      private List<a.g> x;
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

      public static PBMessageALL8.a h() {
         return r;
      }

      public PBMessageALL8.a k() {
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
                  case 26:
                     if ((var3 & 4) != 4) {
                        this.w = new ArrayList<>();
                        var3 |= 4;
                     }

                     this.w.add(var1.l());
                     break;
                  case 34:
                     if ((var3 & 8) != 8) {
                        this.x = new ArrayList<>();
                        var3 |= 8;
                     }

                     this.x.add(var1.l());
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
            if ((var3 & 4) == 4) {
               this.w = Collections.unmodifiableList(this.w);
            }

            if ((var3 & 8) == 8) {
               this.x = Collections.unmodifiableList(this.x);
            }

            this.s = var4.b();
            this.ad();
         }
      }

      public static final k.a n() {
         return PBMessageALL8.i;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL8.j.a(PBMessageALL8.a.class, PBMessageALL8.a.a.class);
      }

      @Override
      public ab<PBMessageALL8.a> m() {
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
      public List<a.g> u() {
         return this.x;
      }

      @Override
      public int v() {
         return this.x.size();
      }

      @Override
      public a.g b(int var1) {
         return this.x.get(var1);
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
         this.v = 0;
         this.w = Collections.emptyList();
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

         for (int var2 = 0; var2 < this.w.size(); var2++) {
            var1.a(3, this.w.get(var2));
         }

         for (int var3 = 0; var3 < this.x.size(); var3++) {
            var1.a(4, this.x.get(var3));
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

         if ((this.t & 2) == 2) {
            var1 += a.i.g(2, this.v);
         }

         int var2 = 0;

         for (int var3 = 0; var3 < this.w.size(); var3++) {
            var2 += a.i.b(this.w.get(var3));
         }

         var1 += var2;
         var1 += 1 * this.s().size();
         var2 = 0;

         for (int var11 = 0; var11 < this.x.size(); var11++) {
            var2 += a.i.b(this.x.get(var11));
         }

         var1 += var2;
         var1 += 1 * this.u().size();
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

      public static PBMessageALL8.a a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL8.a a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL8.a a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL8.a a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL8.a a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL8.a a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL8.a b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL8.a b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL8.a a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL8.a a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL8.a.a aa() {
         return PBMessageALL8.a.a.av();
      }

      public PBMessageALL8.a.a ab() {
         return aa();
      }

      public static PBMessageALL8.a.a a(PBMessageALL8.a var0) {
         return aa().a(var0);
      }

      public PBMessageALL8.a.a ae() {
         return a(this);
      }

      protected PBMessageALL8.a.a a(a.p.b var1) {
         return new PBMessageALL8.a.a(var1, null);
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
      a(a.h var1, n var2, PBMessageALL8.a var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      a(p.a var1, PBMessageALL8.a var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL8.a.a> implements PBMessageALL8.b {
         private int a;
         private int b;
         private int c;
         private List<a.g> d = Collections.emptyList();
         private List<a.g> e = Collections.emptyList();
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
            return PBMessageALL8.i;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL8.j.a(PBMessageALL8.a.class, PBMessageALL8.a.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            PBMessageALL8.a.m;
         }

         private static PBMessageALL8.a.a av() {
            return new PBMessageALL8.a.a();
         }

         public PBMessageALL8.a.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = 0;
            this.a &= -3;
            this.d = Collections.emptyList();
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

         public PBMessageALL8.a.a n() {
            return av().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL8.i;
         }

         public PBMessageALL8.a I() {
            return PBMessageALL8.a.h();
         }

         public PBMessageALL8.a M() {
            PBMessageALL8.a var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL8.a N() {
            PBMessageALL8.a var1 = new PBMessageALL8.a(this, null);
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
            if ((this.a & 8) == 8) {
               this.e = Collections.unmodifiableList(this.e);
               this.a &= -9;
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

         public PBMessageALL8.a.a d(x var1) {
            if (var1 instanceof PBMessageALL8.a) {
               return this.a((PBMessageALL8.a)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL8.a.a a(PBMessageALL8.a var1) {
            if (var1 == PBMessageALL8.a.h()) {
               return this;
            }

            if (var1.o()) {
               this.c(var1.p());
            }

            if (var1.q()) {
               this.d(var1.r());
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

            if (!var1.x.isEmpty()) {
               if (this.e.isEmpty()) {
                  this.e = var1.x;
                  this.a &= -9;
               } else {
                  this.ax();
                  this.e.addAll(var1.x);
               }

               this.t_();
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

         public PBMessageALL8.a.a e(a.h var1, n var2) throws IOException {
            PBMessageALL8.a var3 = null;

            try {
               var3 = PBMessageALL8.a.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL8.a)var8.a();
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

         public PBMessageALL8.a.a c(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a O() {
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

         public PBMessageALL8.a.a d(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a P() {
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

         public PBMessageALL8.a.a a(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.d.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.aw();
            this.d.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a a(Iterable<? extends a.g> var1) {
            this.aw();
            p.a.a(var1, this.d);
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a aa() {
            this.d = Collections.emptyList();
            this.a &= -5;
            this.t_();
            return this;
         }

         private void ax() {
            if ((this.a & 8) != 8) {
               this.e = new ArrayList<>(this.e);
               this.a |= 8;
            }
         }

         @Override
         public List<a.g> u() {
            return Collections.unmodifiableList(this.e);
         }

         @Override
         public int v() {
            return this.e.size();
         }

         @Override
         public a.g b(int var1) {
            return this.e.get(var1);
         }

         public PBMessageALL8.a.a b(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.ax();
            this.e.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.ax();
            this.e.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a b(Iterable<? extends a.g> var1) {
            this.ax();
            p.a.a(var1, this.e);
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a ab() {
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

         public PBMessageALL8.a.a e(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a ac() {
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

         public PBMessageALL8.a.a f(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a ad() {
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

         public PBMessageALL8.a.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a ae() {
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

         public PBMessageALL8.a.a h(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a af() {
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

         public PBMessageALL8.a.a i(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a ag() {
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

         public PBMessageALL8.a.a j(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a an() {
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

         public PBMessageALL8.a.a k(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a ao() {
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

         public PBMessageALL8.a.a l(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a ap() {
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

         public PBMessageALL8.a.a m(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a aq() {
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

         public PBMessageALL8.a.a n(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a ar() {
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

         public PBMessageALL8.a.a o(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.a.a as() {
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
         a(a.p.b var1, PBMessageALL8.a.a var2) {
            this(var1);
         }
      }
   }

   public interface b extends aa {
      boolean o();

      int p();

      boolean q();

      int r();

      List<a.g> s();

      int t();

      a.g a(int var1);

      List<a.g> u();

      int v();

      a.g b(int var1);

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

   public static final class c extends p implements PBMessageALL8.d {
      private static final PBMessageALL8.c r = new PBMessageALL8.c(true);
      private final ap s;
      public static ab<PBMessageALL8.c> a = new a.c<PBMessageALL8.c>() {
         public PBMessageALL8.c c(a.h var1, n var2) throws s {
            return new PBMessageALL8.c(var1, var2, null);
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
      private a.g y;
      public static final int g = 6;
      private a.g z;
      public static final int h = 7;
      private a.g A;
      public static final int i = 8;
      private a.g B;
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

      private c(p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private c(boolean var1) {
         this.s = ap.c();
      }

      public static PBMessageALL8.c h() {
         return r;
      }

      public PBMessageALL8.c k() {
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
                     this.t |= 4;
                     this.w = var1.l();
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
                  case 66:
                     this.t |= 128;
                     this.B = var1.l();
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

      public static final k.a n() {
         return PBMessageALL8.g;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL8.h.a(PBMessageALL8.c.class, PBMessageALL8.c.a.class);
      }

      @Override
      public ab<PBMessageALL8.c> m() {
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
         this.y = a.g.d;
         this.z = a.g.d;
         this.A = a.g.d;
         this.B = a.g.d;
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

      public static PBMessageALL8.c a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL8.c a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL8.c a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL8.c a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL8.c a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL8.c a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL8.c b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL8.c b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL8.c a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL8.c a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL8.c.a aa() {
         return PBMessageALL8.c.a.av();
      }

      public PBMessageALL8.c.a ab() {
         return aa();
      }

      public static PBMessageALL8.c.a a(PBMessageALL8.c var0) {
         return aa().a(var0);
      }

      public PBMessageALL8.c.a ae() {
         return a(this);
      }

      protected PBMessageALL8.c.a a(a.p.b var1) {
         return new PBMessageALL8.c.a(var1, null);
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
      c(a.h var1, n var2, PBMessageALL8.c var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      c(p.a var1, PBMessageALL8.c var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL8.c.a> implements PBMessageALL8.d {
         private int a;
         private int b;
         private int c;
         private a.g d;
         private int e;
         private a.g f;
         private a.g g;
         private a.g h;
         private a.g i;
         private a.g j;
         private int k;
         private int l;
         private int m;
         private int n;
         private int o;
         private int p;

         public static final k.a k() {
            return PBMessageALL8.g;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL8.h.a(PBMessageALL8.c.class, PBMessageALL8.c.a.class);
         }

         private a() {
            this.d = a.g.d;
            this.f = a.g.d;
            this.g = a.g.d;
            this.h = a.g.d;
            this.i = a.g.d;
            this.j = a.g.d;
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.d = a.g.d;
            this.f = a.g.d;
            this.g = a.g.d;
            this.h = a.g.d;
            this.i = a.g.d;
            this.j = a.g.d;
            this.au();
         }

         private void au() {
            PBMessageALL8.c.m;
         }

         private static PBMessageALL8.c.a av() {
            return new PBMessageALL8.c.a();
         }

         public PBMessageALL8.c.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = 0;
            this.a &= -3;
            this.d = a.g.d;
            this.a &= -5;
            this.e = 0;
            this.a &= -9;
            this.f = a.g.d;
            this.a &= -17;
            this.g = a.g.d;
            this.a &= -33;
            this.h = a.g.d;
            this.a &= -65;
            this.i = a.g.d;
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

         public PBMessageALL8.c.a n() {
            return av().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL8.g;
         }

         public PBMessageALL8.c I() {
            return PBMessageALL8.c.h();
         }

         public PBMessageALL8.c M() {
            PBMessageALL8.c var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL8.c N() {
            PBMessageALL8.c var1 = new PBMessageALL8.c(this, null);
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

         public PBMessageALL8.c.a d(x var1) {
            if (var1 instanceof PBMessageALL8.c) {
               return this.a((PBMessageALL8.c)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL8.c.a a(PBMessageALL8.c var1) {
            if (var1 == PBMessageALL8.c.h()) {
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

         public PBMessageALL8.c.a e(a.h var1, n var2) throws IOException {
            PBMessageALL8.c var3 = null;

            try {
               var3 = PBMessageALL8.c.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL8.c)var8.a();
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

         public PBMessageALL8.c.a a(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a O() {
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

         public PBMessageALL8.c.a b(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a P() {
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

         public PBMessageALL8.c.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a aa() {
            this.a &= -5;
            this.d = PBMessageALL8.c.h().t();
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

         public PBMessageALL8.c.a c(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a ab() {
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

         public PBMessageALL8.c.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a ac() {
            this.a &= -17;
            this.f = PBMessageALL8.c.h().x();
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

         public PBMessageALL8.c.a g(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a ad() {
            this.a &= -33;
            this.g = PBMessageALL8.c.h().z();
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

         public PBMessageALL8.c.a h(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a ae() {
            this.a &= -65;
            this.h = PBMessageALL8.c.h().B();
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

         public PBMessageALL8.c.a i(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a af() {
            this.a &= -129;
            this.i = PBMessageALL8.c.h().D();
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

         public PBMessageALL8.c.a j(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a ag() {
            this.a &= -257;
            this.j = PBMessageALL8.c.h().F();
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

         public PBMessageALL8.c.a d(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a an() {
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

         public PBMessageALL8.c.a e(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a ao() {
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

         public PBMessageALL8.c.a f(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a ap() {
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

         public PBMessageALL8.c.a g(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a aq() {
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

         public PBMessageALL8.c.a h(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a ar() {
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

         public PBMessageALL8.c.a i(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.c.a as() {
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
         a(a.p.b var1, PBMessageALL8.c.a var2) {
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

      a.g x();

      boolean y();

      a.g z();

      boolean A();

      a.g B();

      boolean C();

      a.g D();

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

   public static final class e extends p implements PBMessageALL8.f {
      private static final PBMessageALL8.e r = new PBMessageALL8.e(true);
      private final ap s;
      public static ab<PBMessageALL8.e> a = new a.c<PBMessageALL8.e>() {
         public PBMessageALL8.e c(a.h var1, n var2) throws s {
            return new PBMessageALL8.e(var1, var2, null);
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
      private a.g x;
      public static final int f = 5;
      private int y;
      public static final int g = 6;
      private int z;
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

      private e(p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private e(boolean var1) {
         this.s = ap.c();
      }

      public static PBMessageALL8.e h() {
         return r;
      }

      public PBMessageALL8.e k() {
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
                  case 34:
                     this.t |= 8;
                     this.x = var1.l();
                     break;
                  case 40:
                     this.t |= 16;
                     this.y = var1.g();
                     break;
                  case 48:
                     this.t |= 32;
                     this.z = var1.g();
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
         return PBMessageALL8.e;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL8.f.a(PBMessageALL8.e.class, PBMessageALL8.e.a.class);
      }

      @Override
      public ab<PBMessageALL8.e> m() {
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
      public a.g v() {
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
         this.u = 0;
         this.v = 0;
         this.w = a.g.d;
         this.x = a.g.d;
         this.y = 0;
         this.z = 0;
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
            var1 += a.i.g(1, this.u);
         }

         if ((this.t & 2) == 2) {
            var1 += a.i.g(2, this.v);
         }

         if ((this.t & 4) == 4) {
            var1 += a.i.c(3, this.w);
         }

         if ((this.t & 8) == 8) {
            var1 += a.i.c(4, this.x);
         }

         if ((this.t & 16) == 16) {
            var1 += a.i.g(5, this.y);
         }

         if ((this.t & 32) == 32) {
            var1 += a.i.g(6, this.z);
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

      public static PBMessageALL8.e a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL8.e a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL8.e a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL8.e a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL8.e a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL8.e a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL8.e b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL8.e b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL8.e a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL8.e a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL8.e.a aa() {
         return PBMessageALL8.e.a.av();
      }

      public PBMessageALL8.e.a ab() {
         return aa();
      }

      public static PBMessageALL8.e.a a(PBMessageALL8.e var0) {
         return aa().a(var0);
      }

      public PBMessageALL8.e.a ae() {
         return a(this);
      }

      protected PBMessageALL8.e.a a(a.p.b var1) {
         return new PBMessageALL8.e.a(var1, null);
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
      e(a.h var1, n var2, PBMessageALL8.e var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      e(p.a var1, PBMessageALL8.e var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL8.e.a> implements PBMessageALL8.f {
         private int a;
         private int b;
         private int c;
         private a.g d;
         private a.g e;
         private int f;
         private int g;
         private a.g h;
         private int i;
         private int j;
         private int k;
         private int l;
         private int m;
         private int n;
         private int o;
         private int p;

         public static final k.a k() {
            return PBMessageALL8.e;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL8.f.a(PBMessageALL8.e.class, PBMessageALL8.e.a.class);
         }

         private a() {
            this.d = a.g.d;
            this.e = a.g.d;
            this.h = a.g.d;
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.d = a.g.d;
            this.e = a.g.d;
            this.h = a.g.d;
            this.au();
         }

         private void au() {
            PBMessageALL8.e.m;
         }

         private static PBMessageALL8.e.a av() {
            return new PBMessageALL8.e.a();
         }

         public PBMessageALL8.e.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = 0;
            this.a &= -3;
            this.d = a.g.d;
            this.a &= -5;
            this.e = a.g.d;
            this.a &= -9;
            this.f = 0;
            this.a &= -17;
            this.g = 0;
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

         public PBMessageALL8.e.a n() {
            return av().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL8.e;
         }

         public PBMessageALL8.e I() {
            return PBMessageALL8.e.h();
         }

         public PBMessageALL8.e M() {
            PBMessageALL8.e var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL8.e N() {
            PBMessageALL8.e var1 = new PBMessageALL8.e(this, null);
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

         public PBMessageALL8.e.a d(x var1) {
            if (var1 instanceof PBMessageALL8.e) {
               return this.a((PBMessageALL8.e)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL8.e.a a(PBMessageALL8.e var1) {
            if (var1 == PBMessageALL8.e.h()) {
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
               this.f(var1.v());
            }

            if (var1.w()) {
               this.c(var1.x());
            }

            if (var1.y()) {
               this.d(var1.z());
            }

            if (var1.A()) {
               this.g(var1.B());
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

         public PBMessageALL8.e.a e(a.h var1, n var2) throws IOException {
            PBMessageALL8.e var3 = null;

            try {
               var3 = PBMessageALL8.e.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL8.e)var8.a();
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

         public PBMessageALL8.e.a a(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a O() {
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

         public PBMessageALL8.e.a b(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a P() {
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

         public PBMessageALL8.e.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a aa() {
            this.a &= -5;
            this.d = PBMessageALL8.e.h().t();
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

         public PBMessageALL8.e.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a ab() {
            this.a &= -9;
            this.e = PBMessageALL8.e.h().v();
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

         public PBMessageALL8.e.a c(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a ac() {
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

         public PBMessageALL8.e.a d(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a ad() {
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
         public a.g B() {
            return this.h;
         }

         public PBMessageALL8.e.a g(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a ae() {
            this.a &= -65;
            this.h = PBMessageALL8.e.h().B();
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

         public PBMessageALL8.e.a e(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a af() {
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

         public PBMessageALL8.e.a f(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a ag() {
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

         public PBMessageALL8.e.a g(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a an() {
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

         public PBMessageALL8.e.a h(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a ao() {
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

         public PBMessageALL8.e.a i(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a ap() {
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

         public PBMessageALL8.e.a j(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a aq() {
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

         public PBMessageALL8.e.a k(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a ar() {
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

         public PBMessageALL8.e.a l(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.e.a as() {
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
         a(a.p.b var1, PBMessageALL8.e.a var2) {
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

      a.g t();

      boolean u();

      a.g v();

      boolean w();

      int x();

      boolean y();

      int z();

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

   public static final class g extends p implements PBMessageALL8.h {
      private static final PBMessageALL8.g r = new PBMessageALL8.g(true);
      private final ap s;
      public static ab<PBMessageALL8.g> a = new a.c<PBMessageALL8.g>() {
         public PBMessageALL8.g c(a.h var1, n var2) throws s {
            return new PBMessageALL8.g(var1, var2, null);
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

      private g(p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private g(boolean var1) {
         this.s = ap.c();
      }

      public static PBMessageALL8.g h() {
         return r;
      }

      public PBMessageALL8.g k() {
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
                  case 16:
                     this.t |= 2;
                     this.v = var1.g();
                     break;
                  case 26:
                     this.t |= 4;
                     this.w = var1.l();
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

      public static final k.a n() {
         return PBMessageALL8.a;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL8.b.a(PBMessageALL8.g.class, PBMessageALL8.g.a.class);
      }

      @Override
      public ab<PBMessageALL8.g> m() {
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
         this.x = a.g.d;
         this.y = a.g.d;
         this.z = a.g.d;
         this.A = a.g.d;
         this.B = a.g.d;
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

      public static PBMessageALL8.g a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL8.g a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL8.g a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL8.g a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL8.g a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL8.g a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL8.g b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL8.g b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL8.g a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL8.g a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL8.g.a aa() {
         return PBMessageALL8.g.a.av();
      }

      public PBMessageALL8.g.a ab() {
         return aa();
      }

      public static PBMessageALL8.g.a a(PBMessageALL8.g var0) {
         return aa().a(var0);
      }

      public PBMessageALL8.g.a ae() {
         return a(this);
      }

      protected PBMessageALL8.g.a a(a.p.b var1) {
         return new PBMessageALL8.g.a(var1, null);
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
      g(a.h var1, n var2, PBMessageALL8.g var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      g(p.a var1, PBMessageALL8.g var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL8.g.a> implements PBMessageALL8.h {
         private int a;
         private int b;
         private int c;
         private a.g d;
         private a.g e;
         private a.g f;
         private a.g g;
         private a.g h;
         private a.g i;
         private a.g j;
         private int k;
         private int l;
         private int m;
         private int n;
         private int o;
         private int p;

         public static final k.a k() {
            return PBMessageALL8.a;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL8.b.a(PBMessageALL8.g.class, PBMessageALL8.g.a.class);
         }

         private a() {
            this.d = a.g.d;
            this.e = a.g.d;
            this.f = a.g.d;
            this.g = a.g.d;
            this.h = a.g.d;
            this.i = a.g.d;
            this.j = a.g.d;
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.d = a.g.d;
            this.e = a.g.d;
            this.f = a.g.d;
            this.g = a.g.d;
            this.h = a.g.d;
            this.i = a.g.d;
            this.j = a.g.d;
            this.au();
         }

         private void au() {
            PBMessageALL8.g.m;
         }

         private static PBMessageALL8.g.a av() {
            return new PBMessageALL8.g.a();
         }

         public PBMessageALL8.g.a m() {
            super.ah();
            this.b = 0;
            this.a &= -2;
            this.c = 0;
            this.a &= -3;
            this.d = a.g.d;
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

         public PBMessageALL8.g.a n() {
            return av().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL8.a;
         }

         public PBMessageALL8.g I() {
            return PBMessageALL8.g.h();
         }

         public PBMessageALL8.g M() {
            PBMessageALL8.g var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL8.g N() {
            PBMessageALL8.g var1 = new PBMessageALL8.g(this, null);
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

         public PBMessageALL8.g.a d(x var1) {
            if (var1 instanceof PBMessageALL8.g) {
               return this.a((PBMessageALL8.g)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL8.g.a a(PBMessageALL8.g var1) {
            if (var1 == PBMessageALL8.g.h()) {
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
               this.k(var1.F());
            }

            if (var1.G()) {
               this.c(var1.H());
            }

            if (var1.K()) {
               this.d(var1.L());
            }

            if (var1.S()) {
               this.e(var1.T());
            }

            if (var1.U()) {
               this.f(var1.V());
            }

            if (var1.W()) {
               this.g(var1.X());
            }

            if (var1.Y()) {
               this.h(var1.Z());
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public PBMessageALL8.g.a e(a.h var1, n var2) throws IOException {
            PBMessageALL8.g var3 = null;

            try {
               var3 = PBMessageALL8.g.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL8.g)var8.a();
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

         public PBMessageALL8.g.a a(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a O() {
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

         public PBMessageALL8.g.a b(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a P() {
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

         public PBMessageALL8.g.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a aa() {
            this.a &= -5;
            this.d = PBMessageALL8.g.h().t();
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

         public PBMessageALL8.g.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a ab() {
            this.a &= -9;
            this.e = PBMessageALL8.g.h().v();
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

         public PBMessageALL8.g.a g(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a ac() {
            this.a &= -17;
            this.f = PBMessageALL8.g.h().x();
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

         public PBMessageALL8.g.a h(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a ad() {
            this.a &= -33;
            this.g = PBMessageALL8.g.h().z();
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

         public PBMessageALL8.g.a i(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a ae() {
            this.a &= -65;
            this.h = PBMessageALL8.g.h().B();
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

         public PBMessageALL8.g.a j(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a af() {
            this.a &= -129;
            this.i = PBMessageALL8.g.h().D();
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

         public PBMessageALL8.g.a k(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a ag() {
            this.a &= -257;
            this.j = PBMessageALL8.g.h().F();
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

         public PBMessageALL8.g.a c(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a an() {
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

         public PBMessageALL8.g.a d(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a ao() {
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

         public PBMessageALL8.g.a e(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a ap() {
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

         public PBMessageALL8.g.a f(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a aq() {
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

         public PBMessageALL8.g.a g(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a ar() {
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

         public PBMessageALL8.g.a h(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.g.a as() {
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
         a(a.p.b var1, PBMessageALL8.g.a var2) {
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

   public static final class i extends p implements PBMessageALL8.j {
      private static final PBMessageALL8.i r = new PBMessageALL8.i(true);
      private final ap s;
      public static ab<PBMessageALL8.i> a = new a.c<PBMessageALL8.i>() {
         public PBMessageALL8.i c(a.h var1, n var2) throws s {
            return new PBMessageALL8.i(var1, var2, null);
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

      private i(p.a<?> var1) {
         super(var1);
         this.s = var1.b_();
      }

      private i(boolean var1) {
         this.s = ap.c();
      }

      public static PBMessageALL8.i h() {
         return r;
      }

      public PBMessageALL8.i k() {
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
         return PBMessageALL8.c;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL8.d.a(PBMessageALL8.i.class, PBMessageALL8.i.a.class);
      }

      @Override
      public ab<PBMessageALL8.i> m() {
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
         this.u = a.g.d;
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
            var1 += a.i.c(1, this.u);
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

      public static PBMessageALL8.i a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL8.i a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL8.i a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL8.i a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL8.i a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL8.i a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL8.i b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL8.i b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL8.i a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL8.i a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL8.i.a aa() {
         return PBMessageALL8.i.a.av();
      }

      public PBMessageALL8.i.a ab() {
         return aa();
      }

      public static PBMessageALL8.i.a a(PBMessageALL8.i var0) {
         return aa().a(var0);
      }

      public PBMessageALL8.i.a ae() {
         return a(this);
      }

      protected PBMessageALL8.i.a a(a.p.b var1) {
         return new PBMessageALL8.i.a(var1, null);
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
      i(a.h var1, n var2, PBMessageALL8.i var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      i(p.a var1, PBMessageALL8.i var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL8.i.a> implements PBMessageALL8.j {
         private int a;
         private a.g b = a.g.d;
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
            return PBMessageALL8.c;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL8.d.a(PBMessageALL8.i.class, PBMessageALL8.i.a.class);
         }

         private a() {
            this.au();
         }

         private a(a.p.b var1) {
            super(var1);
            this.au();
         }

         private void au() {
            PBMessageALL8.i.m;
         }

         private static PBMessageALL8.i.a av() {
            return new PBMessageALL8.i.a();
         }

         public PBMessageALL8.i.a m() {
            super.ah();
            this.b = a.g.d;
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

         public PBMessageALL8.i.a n() {
            return av().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL8.c;
         }

         public PBMessageALL8.i I() {
            return PBMessageALL8.i.h();
         }

         public PBMessageALL8.i M() {
            PBMessageALL8.i var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL8.i N() {
            PBMessageALL8.i var1 = new PBMessageALL8.i(this, null);
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

         public PBMessageALL8.i.a d(x var1) {
            if (var1 instanceof PBMessageALL8.i) {
               return this.a((PBMessageALL8.i)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL8.i.a a(PBMessageALL8.i var1) {
            if (var1 == PBMessageALL8.i.h()) {
               return this;
            }

            if (var1.o()) {
               this.e(var1.p());
            }

            if (var1.q()) {
               this.a(var1.r());
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

         public PBMessageALL8.i.a e(a.h var1, n var2) throws IOException {
            PBMessageALL8.i var3 = null;

            try {
               var3 = PBMessageALL8.i.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL8.i)var8.a();
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

         public PBMessageALL8.i.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a O() {
            this.a &= -2;
            this.b = PBMessageALL8.i.h().p();
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

         public PBMessageALL8.i.a a(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a P() {
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

         public PBMessageALL8.i.a b(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a aa() {
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

         public PBMessageALL8.i.a c(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a ab() {
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

         public PBMessageALL8.i.a d(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a ac() {
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

         public PBMessageALL8.i.a e(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a ad() {
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

         public PBMessageALL8.i.a f(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a ae() {
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

         public PBMessageALL8.i.a g(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a af() {
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

         public PBMessageALL8.i.a h(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a ag() {
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

         public PBMessageALL8.i.a i(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a an() {
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

         public PBMessageALL8.i.a j(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a ao() {
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

         public PBMessageALL8.i.a k(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a ap() {
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

         public PBMessageALL8.i.a l(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a aq() {
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

         public PBMessageALL8.i.a m(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a ar() {
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

         public PBMessageALL8.i.a n(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL8.i.a as() {
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
         a(a.p.b var1, PBMessageALL8.i.a var2) {
            this(var1);
         }
      }
   }

   public interface j extends aa {
      boolean o();

      a.g p();

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
}
