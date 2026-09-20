package l1r.an;

import a.aa;
import a.ab;
import a.ap;
import a.i;
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

public final class PBMessageALL9 {
   private static k.a a;
   private static p.g b;
   private static k.a c;
   private static p.g d;
   private static k.a e;
   private static p.g f;
   private static k.a g;
   private static p.g h;
   private static k.g i;

   static {
      String[] var0 = new String[]{
         "\n\u0013PBMessageALL9.proto\u0012 l1j.server.server.datas.protobuf\"Ú\u0002\n\u000btypeInvList\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\u0012\u0010\n\bvalue_17\u0018\u0011 \u0001(\u0005\u0012\u0010\n\barray_18\u0018\u0012 \u0001(\f\u0012\u0010\n\barray_19\u0018\u0013",
         " \u0001(\f\"Ú\u0002\n\u000btypeVersion\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\u0012\u0010\n\bvalue_17\u0018\u0011 \u0001(\u0005\u0012\u0010\n\bvalue_18\u0018\u0012 \u0001(\u0005\u0012\u0010\n\bvalue_19\u0018\u0013 \u0001(\u0005\"£\u0001\n\btypeRank\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005",
         "\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007array_6\u0018\u0006 \u0003(\f\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\"¡\u0001\n\u0006type31\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007array_5\u0018\u0005 \u0001(\f\u0012\u000f\n\u0007array_6\u0018\u0006 \u0003(\f\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005B1\n l1j.server.server.datas.protobufB\rPBMessageALL9"
      };
      a.k.g.a var1 = new a.k.g.a() {
         @Override
         public m a(k.g var1) {
            PBMessageALL9.i = var1;
            PBMessageALL9.a = PBMessageALL9.a().e().get(0);
            PBMessageALL9.b = new p.g(
               PBMessageALL9.a,
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
                  "Value15",
                  "Value16",
                  "Value17",
                  "Array18",
                  "Array19"
               }
            );
            PBMessageALL9.c = PBMessageALL9.a().e().get(1);
            PBMessageALL9.d = new p.g(
               PBMessageALL9.c,
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
                  "Value15",
                  "Value16",
                  "Value17",
                  "Value18",
                  "Value19"
               }
            );
            PBMessageALL9.e = PBMessageALL9.a().e().get(2);
            PBMessageALL9.f = new p.g(PBMessageALL9.e, new String[]{"Value1", "Value2", "Value3", "Value4", "Value5", "Array6", "Value7", "Value8", "Value9"});
            PBMessageALL9.g = PBMessageALL9.a().e().get(3);
            PBMessageALL9.h = new p.g(PBMessageALL9.g, new String[]{"Value1", "Value2", "Value3", "Value4", "Array5", "Array6", "Value7", "Value8", "Value9"});
            return null;
         }
      };
      k.g.a(var0, new k.g[0], var1);
   }

   private PBMessageALL9() {
   }

   public static void a(m var0) {
   }

   public static k.g a() {
      return i;
   }

   public static final class a extends p implements PBMessageALL9.b {
      private static final PBMessageALL9.a k = new PBMessageALL9.a(true);
      private final ap l;
      public static ab<PBMessageALL9.a> a = new a.c<PBMessageALL9.a>() {
         public PBMessageALL9.a c(a.h var1, n var2) throws s {
            return new PBMessageALL9.a(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int n;
      public static final int b = 1;
      private int o;
      public static final int c = 2;
      private int p;
      public static final int d = 3;
      private int q;
      public static final int e = 4;
      private int r;
      public static final int f = 5;
      private a.g s;
      public static final int g = 6;
      private List<a.g> t;
      public static final int h = 7;
      private int u;
      public static final int i = 8;
      private int v;
      public static final int j = 9;
      private int w;
      private byte x = -1;
      private int y = -1;
      private static final long z = 0L;

      static {
         k.S();
      }

      private a(p.a<?> var1) {
         super(var1);
         this.l = var1.b_();
      }

      private a(boolean var1) {
         this.l = ap.c();
      }

      public static PBMessageALL9.a h() {
         return k;
      }

      public PBMessageALL9.a k() {
         return k;
      }

      @Override
      public final ap b_() {
         return this.l;
      }

      private a(a.h var1, n var2) throws s {
         this.S();
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
                     this.n |= 1;
                     this.o = var1.g();
                     break;
                  case 16:
                     this.n |= 2;
                     this.p = var1.g();
                     break;
                  case 24:
                     this.n |= 4;
                     this.q = var1.g();
                     break;
                  case 32:
                     this.n |= 8;
                     this.r = var1.g();
                     break;
                  case 42:
                     this.n |= 16;
                     this.s = var1.l();
                     break;
                  case 50:
                     if ((var3 & 32) != 32) {
                        this.t = new ArrayList<>();
                        var3 |= 32;
                     }

                     this.t.add(var1.l());
                     break;
                  case 56:
                     this.n |= 32;
                     this.u = var1.g();
                     break;
                  case 64:
                     this.n |= 64;
                     this.v = var1.g();
                     break;
                  case 72:
                     this.n |= 128;
                     this.w = var1.g();
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
            if ((var3 & 32) == 32) {
               this.t = Collections.unmodifiableList(this.t);
            }

            this.l = var4.b();
            this.ad();
         }
      }

      public static final k.a n() {
         return PBMessageALL9.g;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL9.h.a(PBMessageALL9.a.class, PBMessageALL9.a.a.class);
      }

      @Override
      public ab<PBMessageALL9.a> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.n & 1) == 1;
      }

      @Override
      public int p() {
         return this.o;
      }

      @Override
      public boolean q() {
         return (this.n & 2) == 2;
      }

      @Override
      public int r() {
         return this.p;
      }

      @Override
      public boolean s() {
         return (this.n & 4) == 4;
      }

      @Override
      public int t() {
         return this.q;
      }

      @Override
      public boolean u() {
         return (this.n & 8) == 8;
      }

      @Override
      public int v() {
         return this.r;
      }

      @Override
      public boolean w() {
         return (this.n & 16) == 16;
      }

      @Override
      public a.g x() {
         return this.s;
      }

      @Override
      public List<a.g> y() {
         return this.t;
      }

      @Override
      public int z() {
         return this.t.size();
      }

      @Override
      public a.g a(int var1) {
         return this.t.get(var1);
      }

      @Override
      public boolean A() {
         return (this.n & 32) == 32;
      }

      @Override
      public int B() {
         return this.u;
      }

      @Override
      public boolean C() {
         return (this.n & 64) == 64;
      }

      @Override
      public int D() {
         return this.v;
      }

      @Override
      public boolean E() {
         return (this.n & 128) == 128;
      }

      @Override
      public int F() {
         return this.w;
      }

      private void S() {
         this.o = 0;
         this.p = 0;
         this.q = 0;
         this.r = 0;
         this.s = a.g.d;
         this.t = Collections.emptyList();
         this.u = 0;
         this.v = 0;
         this.w = 0;
      }

      @Override
      public final boolean a() {
         byte var1 = this.x;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.x = 1;
         return true;
      }

      @Override
      public void a(i var1) throws IOException {
         this.d();
         if ((this.n & 1) == 1) {
            var1.a(1, this.o);
         }

         if ((this.n & 2) == 2) {
            var1.a(2, this.p);
         }

         if ((this.n & 4) == 4) {
            var1.a(3, this.q);
         }

         if ((this.n & 8) == 8) {
            var1.a(4, this.r);
         }

         if ((this.n & 16) == 16) {
            var1.a(5, this.s);
         }

         for (int var2 = 0; var2 < this.t.size(); var2++) {
            var1.a(6, this.t.get(var2));
         }

         if ((this.n & 32) == 32) {
            var1.a(7, this.u);
         }

         if ((this.n & 64) == 64) {
            var1.a(8, this.v);
         }

         if ((this.n & 128) == 128) {
            var1.a(9, this.w);
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.y;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.n & 1) == 1) {
            var1 += a.i.g(1, this.o);
         }

         if ((this.n & 2) == 2) {
            var1 += a.i.g(2, this.p);
         }

         if ((this.n & 4) == 4) {
            var1 += a.i.g(3, this.q);
         }

         if ((this.n & 8) == 8) {
            var1 += a.i.g(4, this.r);
         }

         if ((this.n & 16) == 16) {
            var1 += a.i.c(5, this.s);
         }

         int var2 = 0;

         for (int var3 = 0; var3 < this.t.size(); var3++) {
            var2 += a.i.b(this.t.get(var3));
         }

         var1 += var2;
         var1 += 1 * this.y().size();
         if ((this.n & 32) == 32) {
            var1 += a.i.g(7, this.u);
         }

         if ((this.n & 64) == 64) {
            var1 += a.i.g(8, this.v);
         }

         if ((this.n & 128) == 128) {
            var1 += a.i.g(9, this.w);
         }

         var1 += this.b_().d();
         this.y = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static PBMessageALL9.a a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL9.a a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL9.a a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL9.a a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL9.a a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL9.a a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL9.a b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL9.a b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL9.a a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL9.a a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL9.a.a G() {
         return PBMessageALL9.a.a.X();
      }

      public PBMessageALL9.a.a H() {
         return G();
      }

      public static PBMessageALL9.a.a a(PBMessageALL9.a var0) {
         return G().a(var0);
      }

      public PBMessageALL9.a.a K() {
         return a(this);
      }

      protected PBMessageALL9.a.a a(a.p.b var1) {
         return new PBMessageALL9.a.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.K();
      }

      // $VF: synthetic method
      @Override
      public x.a M() {
         return this.K();
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
         return this.H();
      }

      // $VF: synthetic method
      @Override
      public x.a N() {
         return this.H();
      }

      // $VF: synthetic method
      a(a.h var1, n var2, PBMessageALL9.a var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      a(p.a var1, PBMessageALL9.a var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL9.a.a> implements PBMessageALL9.b {
         private int a;
         private int b;
         private int c;
         private int d;
         private int e;
         private a.g f = a.g.d;
         private List<a.g> g = Collections.emptyList();
         private int h;
         private int i;
         private int j;

         public static final k.a k() {
            return PBMessageALL9.g;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL9.h.a(PBMessageALL9.a.class, PBMessageALL9.a.a.class);
         }

         private a() {
            this.W();
         }

         private a(a.p.b var1) {
            super(var1);
            this.W();
         }

         private void W() {
            PBMessageALL9.a.m;
         }

         private static PBMessageALL9.a.a X() {
            return new PBMessageALL9.a.a();
         }

         public PBMessageALL9.a.a m() {
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
            this.g = Collections.emptyList();
            this.a &= -33;
            this.h = 0;
            this.a &= -65;
            this.i = 0;
            this.a &= -129;
            this.j = 0;
            this.a &= -257;
            return this;
         }

         public PBMessageALL9.a.a n() {
            return X().a(this.I());
         }

         @Override
         public k.a J() {
            return PBMessageALL9.g;
         }

         public PBMessageALL9.a G() {
            return PBMessageALL9.a.h();
         }

         public PBMessageALL9.a H() {
            PBMessageALL9.a var1 = this.I();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL9.a I() {
            PBMessageALL9.a var1 = new PBMessageALL9.a(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((var2 & 1) == 1) {
               var3 |= 1;
            }

            var1.o = this.b;
            if ((var2 & 2) == 2) {
               var3 |= 2;
            }

            var1.p = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 4;
            }

            var1.q = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 8;
            }

            var1.r = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 16;
            }

            var1.s = this.f;
            if ((this.a & 32) == 32) {
               this.g = Collections.unmodifiableList(this.g);
               this.a &= -33;
            }

            var1.t = this.g;
            if ((var2 & 64) == 64) {
               var3 |= 32;
            }

            var1.u = this.h;
            if ((var2 & 128) == 128) {
               var3 |= 64;
            }

            var1.v = this.i;
            if ((var2 & 256) == 256) {
               var3 |= 128;
            }

            var1.w = this.j;
            var1.n = var3;
            this.q_();
            return var1;
         }

         public PBMessageALL9.a.a d(x var1) {
            if (var1 instanceof PBMessageALL9.a) {
               return this.a((PBMessageALL9.a)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL9.a.a a(PBMessageALL9.a var1) {
            if (var1 == PBMessageALL9.a.h()) {
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

            if (var1.u()) {
               this.e(var1.v());
            }

            if (var1.w()) {
               this.e(var1.x());
            }

            if (!var1.t.isEmpty()) {
               if (this.g.isEmpty()) {
                  this.g = var1.t;
                  this.a &= -33;
               } else {
                  this.Y();
                  this.g.addAll(var1.t);
               }

               this.t_();
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

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public PBMessageALL9.a.a e(a.h var1, n var2) throws IOException {
            PBMessageALL9.a var3 = null;

            try {
               var3 = PBMessageALL9.a.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL9.a)var8.a();
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

         public PBMessageALL9.a.a b(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.a.a K() {
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

         public PBMessageALL9.a.a c(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.a.a L() {
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

         public PBMessageALL9.a.a d(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.a.a M() {
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

         public PBMessageALL9.a.a e(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.a.a N() {
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

         public PBMessageALL9.a.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.a.a O() {
            this.a &= -17;
            this.f = PBMessageALL9.a.h().x();
            this.t_();
            return this;
         }

         private void Y() {
            if ((this.a & 32) != 32) {
               this.g = new ArrayList<>(this.g);
               this.a |= 32;
            }
         }

         @Override
         public List<a.g> y() {
            return Collections.unmodifiableList(this.g);
         }

         @Override
         public int z() {
            return this.g.size();
         }

         @Override
         public a.g a(int var1) {
            return this.g.get(var1);
         }

         public PBMessageALL9.a.a a(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.Y();
            this.g.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL9.a.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.Y();
            this.g.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL9.a.a a(Iterable<? extends a.g> var1) {
            this.Y();
            p.a.a(var1, this.g);
            this.t_();
            return this;
         }

         public PBMessageALL9.a.a P() {
            this.g = Collections.emptyList();
            this.a &= -33;
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

         public PBMessageALL9.a.a f(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.a.a S() {
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

         public PBMessageALL9.a.a g(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.a.a T() {
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

         public PBMessageALL9.a.a h(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.a.a U() {
            this.a &= -257;
            this.j = 0;
            this.t_();
            return this;
         }

         // $VF: synthetic method
         @Override
         public y al() {
            return this.I();
         }

         // $VF: synthetic method
         @Override
         public x aj() {
            return this.I();
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
            return this.G();
         }

         // $VF: synthetic method
         @Override
         public x R() {
            return this.G();
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
            return this.H();
         }

         // $VF: synthetic method
         @Override
         public x ak() {
            return this.H();
         }

         // $VF: synthetic method
         a(a.p.b var1, PBMessageALL9.a.a var2) {
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

      List<a.g> y();

      int z();

      a.g a(int var1);

      boolean A();

      int B();

      boolean C();

      int D();

      boolean E();

      int F();
   }

   public static final class c extends p implements PBMessageALL9.d {
      private static final PBMessageALL9.c v = new PBMessageALL9.c(true);
      private final ap w;
      public static ab<PBMessageALL9.c> a = new a.c<PBMessageALL9.c>() {
         public PBMessageALL9.c c(a.h var1, n var2) throws s {
            return new PBMessageALL9.c(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int x;
      public static final int b = 1;
      private int y;
      public static final int c = 2;
      private int z;
      public static final int d = 3;
      private int A;
      public static final int e = 4;
      private int B;
      public static final int f = 5;
      private int C;
      public static final int g = 6;
      private int D;
      public static final int h = 7;
      private int E;
      public static final int i = 8;
      private int F;
      public static final int j = 9;
      private int G;
      public static final int k = 10;
      private int H;
      public static final int l = 11;
      private int I;
      public static final int n = 12;
      private int J;
      public static final int o = 13;
      private int K;
      public static final int p = 14;
      private int L;
      public static final int q = 15;
      private int M;
      public static final int r = 16;
      private int N;
      public static final int s = 17;
      private int O;
      public static final int t = 18;
      private a.g P;
      public static final int u = 19;
      private a.g Q;
      private byte R = -1;
      private int S = -1;
      private static final long T = 0L;

      static {
         v.ao();
      }

      private c(p.a<?> var1) {
         super(var1);
         this.w = var1.b_();
      }

      private c(boolean var1) {
         this.w = ap.c();
      }

      public static PBMessageALL9.c h() {
         return v;
      }

      public PBMessageALL9.c k() {
         return v;
      }

      @Override
      public final ap b_() {
         return this.w;
      }

      private c(a.h var1, n var2) throws s {
         this.ao();
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
                     this.x |= 1;
                     this.y = var1.g();
                     break;
                  case 16:
                     this.x |= 2;
                     this.z = var1.g();
                     break;
                  case 24:
                     this.x |= 4;
                     this.A = var1.g();
                     break;
                  case 32:
                     this.x |= 8;
                     this.B = var1.g();
                     break;
                  case 40:
                     this.x |= 16;
                     this.C = var1.g();
                     break;
                  case 48:
                     this.x |= 32;
                     this.D = var1.g();
                     break;
                  case 56:
                     this.x |= 64;
                     this.E = var1.g();
                     break;
                  case 64:
                     this.x |= 128;
                     this.F = var1.g();
                     break;
                  case 72:
                     this.x |= 256;
                     this.G = var1.g();
                     break;
                  case 80:
                     this.x |= 512;
                     this.H = var1.g();
                     break;
                  case 88:
                     this.x |= 1024;
                     this.I = var1.g();
                     break;
                  case 96:
                     this.x |= 2048;
                     this.J = var1.g();
                     break;
                  case 104:
                     this.x |= 4096;
                     this.K = var1.g();
                     break;
                  case 112:
                     this.x |= 8192;
                     this.L = var1.g();
                     break;
                  case 120:
                     this.x |= 16384;
                     this.M = var1.g();
                     break;
                  case 128:
                     this.x |= 32768;
                     this.N = var1.g();
                     break;
                  case 136:
                     this.x |= 65536;
                     this.O = var1.g();
                     break;
                  case 146:
                     this.x |= 131072;
                     this.P = var1.l();
                     break;
                  case 154:
                     this.x |= 262144;
                     this.Q = var1.l();
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
            this.w = var4.b();
            this.ad();
         }
      }

      public static final k.a n() {
         return PBMessageALL9.a;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL9.b.a(PBMessageALL9.c.class, PBMessageALL9.c.a.class);
      }

      @Override
      public ab<PBMessageALL9.c> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.x & 1) == 1;
      }

      @Override
      public int p() {
         return this.y;
      }

      @Override
      public boolean q() {
         return (this.x & 2) == 2;
      }

      @Override
      public int r() {
         return this.z;
      }

      @Override
      public boolean s() {
         return (this.x & 4) == 4;
      }

      @Override
      public int t() {
         return this.A;
      }

      @Override
      public boolean u() {
         return (this.x & 8) == 8;
      }

      @Override
      public int v() {
         return this.B;
      }

      @Override
      public boolean w() {
         return (this.x & 16) == 16;
      }

      @Override
      public int x() {
         return this.C;
      }

      @Override
      public boolean y() {
         return (this.x & 32) == 32;
      }

      @Override
      public int z() {
         return this.D;
      }

      @Override
      public boolean A() {
         return (this.x & 64) == 64;
      }

      @Override
      public int B() {
         return this.E;
      }

      @Override
      public boolean C() {
         return (this.x & 128) == 128;
      }

      @Override
      public int D() {
         return this.F;
      }

      @Override
      public boolean E() {
         return (this.x & 256) == 256;
      }

      @Override
      public int F() {
         return this.G;
      }

      @Override
      public boolean G() {
         return (this.x & 512) == 512;
      }

      @Override
      public int H() {
         return this.H;
      }

      @Override
      public boolean K() {
         return (this.x & 1024) == 1024;
      }

      @Override
      public int L() {
         return this.I;
      }

      @Override
      public boolean S() {
         return (this.x & 2048) == 2048;
      }

      @Override
      public int T() {
         return this.J;
      }

      @Override
      public boolean U() {
         return (this.x & 4096) == 4096;
      }

      @Override
      public int V() {
         return this.K;
      }

      @Override
      public boolean W() {
         return (this.x & 8192) == 8192;
      }

      @Override
      public int X() {
         return this.L;
      }

      @Override
      public boolean Y() {
         return (this.x & 16384) == 16384;
      }

      @Override
      public int Z() {
         return this.M;
      }

      @Override
      public boolean aa() {
         return (this.x & 32768) == 32768;
      }

      @Override
      public int ab() {
         return this.N;
      }

      @Override
      public boolean ae() {
         return (this.x & 65536) == 65536;
      }

      @Override
      public int af() {
         return this.O;
      }

      @Override
      public boolean ag() {
         return (this.x & 131072) == 131072;
      }

      @Override
      public a.g O_() {
         return this.P;
      }

      @Override
      public boolean P_() {
         return (this.x & 262144) == 262144;
      }

      @Override
      public a.g Q_() {
         return this.Q;
      }

      private void ao() {
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
         this.J = 0;
         this.K = 0;
         this.L = 0;
         this.M = 0;
         this.N = 0;
         this.O = 0;
         this.P = a.g.d;
         this.Q = a.g.d;
      }

      @Override
      public final boolean a() {
         byte var1 = this.R;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.R = 1;
         return true;
      }

      @Override
      public void a(i var1) throws IOException {
         this.d();
         if ((this.x & 1) == 1) {
            var1.a(1, this.y);
         }

         if ((this.x & 2) == 2) {
            var1.a(2, this.z);
         }

         if ((this.x & 4) == 4) {
            var1.a(3, this.A);
         }

         if ((this.x & 8) == 8) {
            var1.a(4, this.B);
         }

         if ((this.x & 16) == 16) {
            var1.a(5, this.C);
         }

         if ((this.x & 32) == 32) {
            var1.a(6, this.D);
         }

         if ((this.x & 64) == 64) {
            var1.a(7, this.E);
         }

         if ((this.x & 128) == 128) {
            var1.a(8, this.F);
         }

         if ((this.x & 256) == 256) {
            var1.a(9, this.G);
         }

         if ((this.x & 512) == 512) {
            var1.a(10, this.H);
         }

         if ((this.x & 1024) == 1024) {
            var1.a(11, this.I);
         }

         if ((this.x & 2048) == 2048) {
            var1.a(12, this.J);
         }

         if ((this.x & 4096) == 4096) {
            var1.a(13, this.K);
         }

         if ((this.x & 8192) == 8192) {
            var1.a(14, this.L);
         }

         if ((this.x & 16384) == 16384) {
            var1.a(15, this.M);
         }

         if ((this.x & 32768) == 32768) {
            var1.a(16, this.N);
         }

         if ((this.x & 65536) == 65536) {
            var1.a(17, this.O);
         }

         if ((this.x & 131072) == 131072) {
            var1.a(18, this.P);
         }

         if ((this.x & 262144) == 262144) {
            var1.a(19, this.Q);
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.S;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.x & 1) == 1) {
            var1 += a.i.g(1, this.y);
         }

         if ((this.x & 2) == 2) {
            var1 += a.i.g(2, this.z);
         }

         if ((this.x & 4) == 4) {
            var1 += a.i.g(3, this.A);
         }

         if ((this.x & 8) == 8) {
            var1 += a.i.g(4, this.B);
         }

         if ((this.x & 16) == 16) {
            var1 += a.i.g(5, this.C);
         }

         if ((this.x & 32) == 32) {
            var1 += a.i.g(6, this.D);
         }

         if ((this.x & 64) == 64) {
            var1 += a.i.g(7, this.E);
         }

         if ((this.x & 128) == 128) {
            var1 += a.i.g(8, this.F);
         }

         if ((this.x & 256) == 256) {
            var1 += a.i.g(9, this.G);
         }

         if ((this.x & 512) == 512) {
            var1 += a.i.g(10, this.H);
         }

         if ((this.x & 1024) == 1024) {
            var1 += a.i.g(11, this.I);
         }

         if ((this.x & 2048) == 2048) {
            var1 += a.i.g(12, this.J);
         }

         if ((this.x & 4096) == 4096) {
            var1 += a.i.g(13, this.K);
         }

         if ((this.x & 8192) == 8192) {
            var1 += a.i.g(14, this.L);
         }

         if ((this.x & 16384) == 16384) {
            var1 += a.i.g(15, this.M);
         }

         if ((this.x & 32768) == 32768) {
            var1 += a.i.g(16, this.N);
         }

         if ((this.x & 65536) == 65536) {
            var1 += a.i.g(17, this.O);
         }

         if ((this.x & 131072) == 131072) {
            var1 += a.i.c(18, this.P);
         }

         if ((this.x & 262144) == 262144) {
            var1 += a.i.c(19, this.Q);
         }

         var1 += this.b_().d();
         this.S = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static PBMessageALL9.c a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL9.c a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL9.c a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL9.c a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL9.c a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL9.c a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL9.c b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL9.c b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL9.c a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL9.c a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL9.c.a ak() {
         return PBMessageALL9.c.a.aK();
      }

      public PBMessageALL9.c.a al() {
         return ak();
      }

      public static PBMessageALL9.c.a a(PBMessageALL9.c var0) {
         return ak().a(var0);
      }

      public PBMessageALL9.c.a am() {
         return a(this);
      }

      protected PBMessageALL9.c.a a(a.p.b var1) {
         return new PBMessageALL9.c.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.am();
      }

      // $VF: synthetic method
      @Override
      public x.a M() {
         return this.am();
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
         return this.al();
      }

      // $VF: synthetic method
      @Override
      public x.a N() {
         return this.al();
      }

      // $VF: synthetic method
      c(a.h var1, n var2, PBMessageALL9.c var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      c(p.a var1, PBMessageALL9.c var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL9.c.a> implements PBMessageALL9.d {
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
         private int q;
         private int r;
         private a.g s = a.g.d;
         private a.g t = a.g.d;

         public static final k.a k() {
            return PBMessageALL9.a;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL9.b.a(PBMessageALL9.c.class, PBMessageALL9.c.a.class);
         }

         private a() {
            this.aJ();
         }

         private a(a.p.b var1) {
            super(var1);
            this.aJ();
         }

         private void aJ() {
            PBMessageALL9.c.m;
         }

         private static PBMessageALL9.c.a aK() {
            return new PBMessageALL9.c.a();
         }

         public PBMessageALL9.c.a m() {
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
            this.q = 0;
            this.a &= -32769;
            this.r = 0;
            this.a &= -65537;
            this.s = a.g.d;
            this.a &= -131073;
            this.t = a.g.d;
            this.a &= -262145;
            return this;
         }

         public PBMessageALL9.c.a n() {
            return aK().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL9.a;
         }

         public PBMessageALL9.c I() {
            return PBMessageALL9.c.h();
         }

         public PBMessageALL9.c M() {
            PBMessageALL9.c var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL9.c N() {
            PBMessageALL9.c var1 = new PBMessageALL9.c(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((var2 & 1) == 1) {
               var3 |= 1;
            }

            var1.y = this.b;
            if ((var2 & 2) == 2) {
               var3 |= 2;
            }

            var1.z = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 4;
            }

            var1.A = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 8;
            }

            var1.B = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 16;
            }

            var1.C = this.f;
            if ((var2 & 32) == 32) {
               var3 |= 32;
            }

            var1.D = this.g;
            if ((var2 & 64) == 64) {
               var3 |= 64;
            }

            var1.E = this.h;
            if ((var2 & 128) == 128) {
               var3 |= 128;
            }

            var1.F = this.i;
            if ((var2 & 256) == 256) {
               var3 |= 256;
            }

            var1.G = this.j;
            if ((var2 & 512) == 512) {
               var3 |= 512;
            }

            var1.H = this.k;
            if ((var2 & 1024) == 1024) {
               var3 |= 1024;
            }

            var1.I = this.l;
            if ((var2 & 2048) == 2048) {
               var3 |= 2048;
            }

            var1.J = this.m;
            if ((var2 & 4096) == 4096) {
               var3 |= 4096;
            }

            var1.K = this.n;
            if ((var2 & 8192) == 8192) {
               var3 |= 8192;
            }

            var1.L = this.o;
            if ((var2 & 16384) == 16384) {
               var3 |= 16384;
            }

            var1.M = this.p;
            if ((var2 & 32768) == 32768) {
               var3 |= 32768;
            }

            var1.N = this.q;
            if ((var2 & 65536) == 65536) {
               var3 |= 65536;
            }

            var1.O = this.r;
            if ((var2 & 131072) == 131072) {
               var3 |= 131072;
            }

            var1.P = this.s;
            if ((var2 & 262144) == 262144) {
               var3 |= 262144;
            }

            var1.Q = this.t;
            var1.x = var3;
            this.q_();
            return var1;
         }

         public PBMessageALL9.c.a d(x var1) {
            if (var1 instanceof PBMessageALL9.c) {
               return this.a((PBMessageALL9.c)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL9.c.a a(PBMessageALL9.c var1) {
            if (var1 == PBMessageALL9.c.h()) {
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

            if (var1.aa()) {
               this.p(var1.ab());
            }

            if (var1.ae()) {
               this.q(var1.af());
            }

            if (var1.ag()) {
               this.e(var1.O_());
            }

            if (var1.P_()) {
               this.f(var1.Q_());
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public PBMessageALL9.c.a e(a.h var1, n var2) throws IOException {
            PBMessageALL9.c var3 = null;

            try {
               var3 = PBMessageALL9.c.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL9.c)var8.a();
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

         public PBMessageALL9.c.a a(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a O() {
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

         public PBMessageALL9.c.a b(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a P() {
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

         public PBMessageALL9.c.a c(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a ac() {
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

         public PBMessageALL9.c.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a ad() {
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

         public PBMessageALL9.c.a e(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a an() {
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

         public PBMessageALL9.c.a f(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a ao() {
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

         public PBMessageALL9.c.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a ap() {
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

         public PBMessageALL9.c.a h(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a aq() {
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

         public PBMessageALL9.c.a i(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a ar() {
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

         public PBMessageALL9.c.a j(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a as() {
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

         public PBMessageALL9.c.a k(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a at() {
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

         public PBMessageALL9.c.a l(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a au() {
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

         public PBMessageALL9.c.a m(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a av() {
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

         public PBMessageALL9.c.a n(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a aw() {
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

         public PBMessageALL9.c.a o(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a ax() {
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

         public PBMessageALL9.c.a p(int var1) {
            this.a |= 32768;
            this.q = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a ay() {
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

         public PBMessageALL9.c.a q(int var1) {
            this.a |= 65536;
            this.r = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a az() {
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
         public a.g O_() {
            return this.s;
         }

         public PBMessageALL9.c.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 131072;
            this.s = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a aG() {
            this.a &= -131073;
            this.s = PBMessageALL9.c.h().O_();
            this.t_();
            return this;
         }

         @Override
         public boolean P_() {
            return (this.a & 262144) == 262144;
         }

         @Override
         public a.g Q_() {
            return this.t;
         }

         public PBMessageALL9.c.a f(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.a |= 262144;
            this.t = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.c.a aH() {
            this.a &= -262145;
            this.t = PBMessageALL9.c.h().Q_();
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
         a(a.p.b var1, PBMessageALL9.c.a var2) {
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

      boolean aa();

      int ab();

      boolean ae();

      int af();

      boolean ag();

      a.g O_();

      boolean P_();

      a.g Q_();
   }

   public static final class e extends p implements PBMessageALL9.f {
      private static final PBMessageALL9.e k = new PBMessageALL9.e(true);
      private final ap l;
      public static ab<PBMessageALL9.e> a = new a.c<PBMessageALL9.e>() {
         public PBMessageALL9.e c(a.h var1, n var2) throws s {
            return new PBMessageALL9.e(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int n;
      public static final int b = 1;
      private int o;
      public static final int c = 2;
      private int p;
      public static final int d = 3;
      private int q;
      public static final int e = 4;
      private int r;
      public static final int f = 5;
      private int s;
      public static final int g = 6;
      private List<a.g> t;
      public static final int h = 7;
      private int u;
      public static final int i = 8;
      private int v;
      public static final int j = 9;
      private int w;
      private byte x = -1;
      private int y = -1;
      private static final long z = 0L;

      static {
         k.S();
      }

      private e(p.a<?> var1) {
         super(var1);
         this.l = var1.b_();
      }

      private e(boolean var1) {
         this.l = ap.c();
      }

      public static PBMessageALL9.e h() {
         return k;
      }

      public PBMessageALL9.e k() {
         return k;
      }

      @Override
      public final ap b_() {
         return this.l;
      }

      private e(a.h var1, n var2) throws s {
         this.S();
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
                     this.n |= 1;
                     this.o = var1.g();
                     break;
                  case 16:
                     this.n |= 2;
                     this.p = var1.g();
                     break;
                  case 24:
                     this.n |= 4;
                     this.q = var1.g();
                     break;
                  case 32:
                     this.n |= 8;
                     this.r = var1.g();
                     break;
                  case 40:
                     this.n |= 16;
                     this.s = var1.g();
                     break;
                  case 50:
                     if ((var3 & 32) != 32) {
                        this.t = new ArrayList<>();
                        var3 |= 32;
                     }

                     this.t.add(var1.l());
                     break;
                  case 56:
                     this.n |= 32;
                     this.u = var1.g();
                     break;
                  case 64:
                     this.n |= 64;
                     this.v = var1.g();
                     break;
                  case 72:
                     this.n |= 128;
                     this.w = var1.g();
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
            if ((var3 & 32) == 32) {
               this.t = Collections.unmodifiableList(this.t);
            }

            this.l = var4.b();
            this.ad();
         }
      }

      public static final k.a n() {
         return PBMessageALL9.e;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL9.f.a(PBMessageALL9.e.class, PBMessageALL9.e.a.class);
      }

      @Override
      public ab<PBMessageALL9.e> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.n & 1) == 1;
      }

      @Override
      public int p() {
         return this.o;
      }

      @Override
      public boolean q() {
         return (this.n & 2) == 2;
      }

      @Override
      public int r() {
         return this.p;
      }

      @Override
      public boolean s() {
         return (this.n & 4) == 4;
      }

      @Override
      public int t() {
         return this.q;
      }

      @Override
      public boolean u() {
         return (this.n & 8) == 8;
      }

      @Override
      public int v() {
         return this.r;
      }

      @Override
      public boolean w() {
         return (this.n & 16) == 16;
      }

      @Override
      public int x() {
         return this.s;
      }

      @Override
      public List<a.g> y() {
         return this.t;
      }

      @Override
      public int z() {
         return this.t.size();
      }

      @Override
      public a.g a(int var1) {
         return this.t.get(var1);
      }

      @Override
      public boolean A() {
         return (this.n & 32) == 32;
      }

      @Override
      public int B() {
         return this.u;
      }

      @Override
      public boolean C() {
         return (this.n & 64) == 64;
      }

      @Override
      public int D() {
         return this.v;
      }

      @Override
      public boolean E() {
         return (this.n & 128) == 128;
      }

      @Override
      public int F() {
         return this.w;
      }

      private void S() {
         this.o = 0;
         this.p = 0;
         this.q = 0;
         this.r = 0;
         this.s = 0;
         this.t = Collections.emptyList();
         this.u = 0;
         this.v = 0;
         this.w = 0;
      }

      @Override
      public final boolean a() {
         byte var1 = this.x;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.x = 1;
         return true;
      }

      @Override
      public void a(i var1) throws IOException {
         this.d();
         if ((this.n & 1) == 1) {
            var1.a(1, this.o);
         }

         if ((this.n & 2) == 2) {
            var1.a(2, this.p);
         }

         if ((this.n & 4) == 4) {
            var1.a(3, this.q);
         }

         if ((this.n & 8) == 8) {
            var1.a(4, this.r);
         }

         if ((this.n & 16) == 16) {
            var1.a(5, this.s);
         }

         for (int var2 = 0; var2 < this.t.size(); var2++) {
            var1.a(6, this.t.get(var2));
         }

         if ((this.n & 32) == 32) {
            var1.a(7, this.u);
         }

         if ((this.n & 64) == 64) {
            var1.a(8, this.v);
         }

         if ((this.n & 128) == 128) {
            var1.a(9, this.w);
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.y;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.n & 1) == 1) {
            var1 += a.i.g(1, this.o);
         }

         if ((this.n & 2) == 2) {
            var1 += a.i.g(2, this.p);
         }

         if ((this.n & 4) == 4) {
            var1 += a.i.g(3, this.q);
         }

         if ((this.n & 8) == 8) {
            var1 += a.i.g(4, this.r);
         }

         if ((this.n & 16) == 16) {
            var1 += a.i.g(5, this.s);
         }

         int var2 = 0;

         for (int var3 = 0; var3 < this.t.size(); var3++) {
            var2 += a.i.b(this.t.get(var3));
         }

         var1 += var2;
         var1 += 1 * this.y().size();
         if ((this.n & 32) == 32) {
            var1 += a.i.g(7, this.u);
         }

         if ((this.n & 64) == 64) {
            var1 += a.i.g(8, this.v);
         }

         if ((this.n & 128) == 128) {
            var1 += a.i.g(9, this.w);
         }

         var1 += this.b_().d();
         this.y = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static PBMessageALL9.e a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL9.e a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL9.e a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL9.e a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL9.e a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL9.e a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL9.e b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL9.e b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL9.e a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL9.e a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL9.e.a G() {
         return PBMessageALL9.e.a.X();
      }

      public PBMessageALL9.e.a H() {
         return G();
      }

      public static PBMessageALL9.e.a a(PBMessageALL9.e var0) {
         return G().a(var0);
      }

      public PBMessageALL9.e.a K() {
         return a(this);
      }

      protected PBMessageALL9.e.a a(a.p.b var1) {
         return new PBMessageALL9.e.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.K();
      }

      // $VF: synthetic method
      @Override
      public x.a M() {
         return this.K();
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
         return this.H();
      }

      // $VF: synthetic method
      @Override
      public x.a N() {
         return this.H();
      }

      // $VF: synthetic method
      e(a.h var1, n var2, PBMessageALL9.e var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      e(p.a var1, PBMessageALL9.e var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL9.e.a> implements PBMessageALL9.f {
         private int a;
         private int b;
         private int c;
         private int d;
         private int e;
         private int f;
         private List<a.g> g = Collections.emptyList();
         private int h;
         private int i;
         private int j;

         public static final k.a k() {
            return PBMessageALL9.e;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL9.f.a(PBMessageALL9.e.class, PBMessageALL9.e.a.class);
         }

         private a() {
            this.W();
         }

         private a(a.p.b var1) {
            super(var1);
            this.W();
         }

         private void W() {
            PBMessageALL9.e.m;
         }

         private static PBMessageALL9.e.a X() {
            return new PBMessageALL9.e.a();
         }

         public PBMessageALL9.e.a m() {
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
            this.g = Collections.emptyList();
            this.a &= -33;
            this.h = 0;
            this.a &= -65;
            this.i = 0;
            this.a &= -129;
            this.j = 0;
            this.a &= -257;
            return this;
         }

         public PBMessageALL9.e.a n() {
            return X().a(this.I());
         }

         @Override
         public k.a J() {
            return PBMessageALL9.e;
         }

         public PBMessageALL9.e G() {
            return PBMessageALL9.e.h();
         }

         public PBMessageALL9.e H() {
            PBMessageALL9.e var1 = this.I();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL9.e I() {
            PBMessageALL9.e var1 = new PBMessageALL9.e(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((var2 & 1) == 1) {
               var3 |= 1;
            }

            var1.o = this.b;
            if ((var2 & 2) == 2) {
               var3 |= 2;
            }

            var1.p = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 4;
            }

            var1.q = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 8;
            }

            var1.r = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 16;
            }

            var1.s = this.f;
            if ((this.a & 32) == 32) {
               this.g = Collections.unmodifiableList(this.g);
               this.a &= -33;
            }

            var1.t = this.g;
            if ((var2 & 64) == 64) {
               var3 |= 32;
            }

            var1.u = this.h;
            if ((var2 & 128) == 128) {
               var3 |= 64;
            }

            var1.v = this.i;
            if ((var2 & 256) == 256) {
               var3 |= 128;
            }

            var1.w = this.j;
            var1.n = var3;
            this.q_();
            return var1;
         }

         public PBMessageALL9.e.a d(x var1) {
            if (var1 instanceof PBMessageALL9.e) {
               return this.a((PBMessageALL9.e)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL9.e.a a(PBMessageALL9.e var1) {
            if (var1 == PBMessageALL9.e.h()) {
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

            if (var1.u()) {
               this.e(var1.v());
            }

            if (var1.w()) {
               this.f(var1.x());
            }

            if (!var1.t.isEmpty()) {
               if (this.g.isEmpty()) {
                  this.g = var1.t;
                  this.a &= -33;
               } else {
                  this.Y();
                  this.g.addAll(var1.t);
               }

               this.t_();
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

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public PBMessageALL9.e.a e(a.h var1, n var2) throws IOException {
            PBMessageALL9.e var3 = null;

            try {
               var3 = PBMessageALL9.e.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL9.e)var8.a();
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

         public PBMessageALL9.e.a b(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.e.a K() {
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

         public PBMessageALL9.e.a c(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.e.a L() {
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

         public PBMessageALL9.e.a d(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.e.a M() {
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

         public PBMessageALL9.e.a e(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.e.a N() {
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

         public PBMessageALL9.e.a f(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.e.a O() {
            this.a &= -17;
            this.f = 0;
            this.t_();
            return this;
         }

         private void Y() {
            if ((this.a & 32) != 32) {
               this.g = new ArrayList<>(this.g);
               this.a |= 32;
            }
         }

         @Override
         public List<a.g> y() {
            return Collections.unmodifiableList(this.g);
         }

         @Override
         public int z() {
            return this.g.size();
         }

         @Override
         public a.g a(int var1) {
            return this.g.get(var1);
         }

         public PBMessageALL9.e.a a(int var1, a.g var2) {
            if (var2 == null) {
               throw new NullPointerException();
            }

            this.Y();
            this.g.set(var1, var2);
            this.t_();
            return this;
         }

         public PBMessageALL9.e.a e(a.g var1) {
            if (var1 == null) {
               throw new NullPointerException();
            }

            this.Y();
            this.g.add(var1);
            this.t_();
            return this;
         }

         public PBMessageALL9.e.a a(Iterable<? extends a.g> var1) {
            this.Y();
            p.a.a(var1, this.g);
            this.t_();
            return this;
         }

         public PBMessageALL9.e.a P() {
            this.g = Collections.emptyList();
            this.a &= -33;
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

         public PBMessageALL9.e.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.e.a S() {
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

         public PBMessageALL9.e.a h(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.e.a T() {
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

         public PBMessageALL9.e.a i(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.e.a U() {
            this.a &= -257;
            this.j = 0;
            this.t_();
            return this;
         }

         // $VF: synthetic method
         @Override
         public y al() {
            return this.I();
         }

         // $VF: synthetic method
         @Override
         public x aj() {
            return this.I();
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
            return this.G();
         }

         // $VF: synthetic method
         @Override
         public x R() {
            return this.G();
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
            return this.H();
         }

         // $VF: synthetic method
         @Override
         public x ak() {
            return this.H();
         }

         // $VF: synthetic method
         a(a.p.b var1, PBMessageALL9.e.a var2) {
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

      List<a.g> y();

      int z();

      a.g a(int var1);

      boolean A();

      int B();

      boolean C();

      int D();

      boolean E();

      int F();
   }

   public static final class g extends p implements PBMessageALL9.h {
      private static final PBMessageALL9.g v = new PBMessageALL9.g(true);
      private final ap w;
      public static ab<PBMessageALL9.g> a = new a.c<PBMessageALL9.g>() {
         public PBMessageALL9.g c(a.h var1, n var2) throws s {
            return new PBMessageALL9.g(var1, var2, null);
         }

         // $VF: synthetic method
         @Override
         public Object d(a.h var1, n var2) throws s {
            return this.c(var1, var2);
         }
      };
      private int x;
      public static final int b = 1;
      private int y;
      public static final int c = 2;
      private int z;
      public static final int d = 3;
      private int A;
      public static final int e = 4;
      private int B;
      public static final int f = 5;
      private int C;
      public static final int g = 6;
      private int D;
      public static final int h = 7;
      private int E;
      public static final int i = 8;
      private int F;
      public static final int j = 9;
      private int G;
      public static final int k = 10;
      private int H;
      public static final int l = 11;
      private int I;
      public static final int n = 12;
      private int J;
      public static final int o = 13;
      private int K;
      public static final int p = 14;
      private int L;
      public static final int q = 15;
      private int M;
      public static final int r = 16;
      private int N;
      public static final int s = 17;
      private int O;
      public static final int t = 18;
      private int P;
      public static final int u = 19;
      private int Q;
      private byte R = -1;
      private int S = -1;
      private static final long T = 0L;

      static {
         v.ao();
      }

      private g(p.a<?> var1) {
         super(var1);
         this.w = var1.b_();
      }

      private g(boolean var1) {
         this.w = ap.c();
      }

      public static PBMessageALL9.g h() {
         return v;
      }

      public PBMessageALL9.g k() {
         return v;
      }

      @Override
      public final ap b_() {
         return this.w;
      }

      private g(a.h var1, n var2) throws s {
         this.ao();
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
                     this.x |= 1;
                     this.y = var1.g();
                     break;
                  case 16:
                     this.x |= 2;
                     this.z = var1.g();
                     break;
                  case 24:
                     this.x |= 4;
                     this.A = var1.g();
                     break;
                  case 32:
                     this.x |= 8;
                     this.B = var1.g();
                     break;
                  case 40:
                     this.x |= 16;
                     this.C = var1.g();
                     break;
                  case 48:
                     this.x |= 32;
                     this.D = var1.g();
                     break;
                  case 56:
                     this.x |= 64;
                     this.E = var1.g();
                     break;
                  case 64:
                     this.x |= 128;
                     this.F = var1.g();
                     break;
                  case 72:
                     this.x |= 256;
                     this.G = var1.g();
                     break;
                  case 80:
                     this.x |= 512;
                     this.H = var1.g();
                     break;
                  case 88:
                     this.x |= 1024;
                     this.I = var1.g();
                     break;
                  case 96:
                     this.x |= 2048;
                     this.J = var1.g();
                     break;
                  case 104:
                     this.x |= 4096;
                     this.K = var1.g();
                     break;
                  case 112:
                     this.x |= 8192;
                     this.L = var1.g();
                     break;
                  case 120:
                     this.x |= 16384;
                     this.M = var1.g();
                     break;
                  case 128:
                     this.x |= 32768;
                     this.N = var1.g();
                     break;
                  case 136:
                     this.x |= 65536;
                     this.O = var1.g();
                     break;
                  case 144:
                     this.x |= 131072;
                     this.P = var1.g();
                     break;
                  case 152:
                     this.x |= 262144;
                     this.Q = var1.g();
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
            this.w = var4.b();
            this.ad();
         }
      }

      public static final k.a n() {
         return PBMessageALL9.c;
      }

      @Override
      protected a.p.g l() {
         return PBMessageALL9.d.a(PBMessageALL9.g.class, PBMessageALL9.g.a.class);
      }

      @Override
      public ab<PBMessageALL9.g> m() {
         return a;
      }

      @Override
      public boolean o() {
         return (this.x & 1) == 1;
      }

      @Override
      public int p() {
         return this.y;
      }

      @Override
      public boolean q() {
         return (this.x & 2) == 2;
      }

      @Override
      public int r() {
         return this.z;
      }

      @Override
      public boolean s() {
         return (this.x & 4) == 4;
      }

      @Override
      public int t() {
         return this.A;
      }

      @Override
      public boolean u() {
         return (this.x & 8) == 8;
      }

      @Override
      public int v() {
         return this.B;
      }

      @Override
      public boolean w() {
         return (this.x & 16) == 16;
      }

      @Override
      public int x() {
         return this.C;
      }

      @Override
      public boolean y() {
         return (this.x & 32) == 32;
      }

      @Override
      public int z() {
         return this.D;
      }

      @Override
      public boolean A() {
         return (this.x & 64) == 64;
      }

      @Override
      public int B() {
         return this.E;
      }

      @Override
      public boolean C() {
         return (this.x & 128) == 128;
      }

      @Override
      public int D() {
         return this.F;
      }

      @Override
      public boolean E() {
         return (this.x & 256) == 256;
      }

      @Override
      public int F() {
         return this.G;
      }

      @Override
      public boolean G() {
         return (this.x & 512) == 512;
      }

      @Override
      public int H() {
         return this.H;
      }

      @Override
      public boolean K() {
         return (this.x & 1024) == 1024;
      }

      @Override
      public int L() {
         return this.I;
      }

      @Override
      public boolean S() {
         return (this.x & 2048) == 2048;
      }

      @Override
      public int T() {
         return this.J;
      }

      @Override
      public boolean U() {
         return (this.x & 4096) == 4096;
      }

      @Override
      public int V() {
         return this.K;
      }

      @Override
      public boolean W() {
         return (this.x & 8192) == 8192;
      }

      @Override
      public int X() {
         return this.L;
      }

      @Override
      public boolean Y() {
         return (this.x & 16384) == 16384;
      }

      @Override
      public int Z() {
         return this.M;
      }

      @Override
      public boolean aa() {
         return (this.x & 32768) == 32768;
      }

      @Override
      public int ab() {
         return this.N;
      }

      @Override
      public boolean ae() {
         return (this.x & 65536) == 65536;
      }

      @Override
      public int af() {
         return this.O;
      }

      @Override
      public boolean ag() {
         return (this.x & 131072) == 131072;
      }

      @Override
      public int R_() {
         return this.P;
      }

      @Override
      public boolean S_() {
         return (this.x & 262144) == 262144;
      }

      @Override
      public int T_() {
         return this.Q;
      }

      private void ao() {
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
         this.J = 0;
         this.K = 0;
         this.L = 0;
         this.M = 0;
         this.N = 0;
         this.O = 0;
         this.P = 0;
         this.Q = 0;
      }

      @Override
      public final boolean a() {
         byte var1 = this.R;
         if (var1 != -1) {
            return var1 == 1;
         }

         this.R = 1;
         return true;
      }

      @Override
      public void a(i var1) throws IOException {
         this.d();
         if ((this.x & 1) == 1) {
            var1.a(1, this.y);
         }

         if ((this.x & 2) == 2) {
            var1.a(2, this.z);
         }

         if ((this.x & 4) == 4) {
            var1.a(3, this.A);
         }

         if ((this.x & 8) == 8) {
            var1.a(4, this.B);
         }

         if ((this.x & 16) == 16) {
            var1.a(5, this.C);
         }

         if ((this.x & 32) == 32) {
            var1.a(6, this.D);
         }

         if ((this.x & 64) == 64) {
            var1.a(7, this.E);
         }

         if ((this.x & 128) == 128) {
            var1.a(8, this.F);
         }

         if ((this.x & 256) == 256) {
            var1.a(9, this.G);
         }

         if ((this.x & 512) == 512) {
            var1.a(10, this.H);
         }

         if ((this.x & 1024) == 1024) {
            var1.a(11, this.I);
         }

         if ((this.x & 2048) == 2048) {
            var1.a(12, this.J);
         }

         if ((this.x & 4096) == 4096) {
            var1.a(13, this.K);
         }

         if ((this.x & 8192) == 8192) {
            var1.a(14, this.L);
         }

         if ((this.x & 16384) == 16384) {
            var1.a(15, this.M);
         }

         if ((this.x & 32768) == 32768) {
            var1.a(16, this.N);
         }

         if ((this.x & 65536) == 65536) {
            var1.a(17, this.O);
         }

         if ((this.x & 131072) == 131072) {
            var1.a(18, this.P);
         }

         if ((this.x & 262144) == 262144) {
            var1.a(19, this.Q);
         }

         this.b_().a(var1);
      }

      @Override
      public int d() {
         int var1 = this.S;
         if (var1 != -1) {
            return var1;
         }

         var1 = 0;
         if ((this.x & 1) == 1) {
            var1 += a.i.g(1, this.y);
         }

         if ((this.x & 2) == 2) {
            var1 += a.i.g(2, this.z);
         }

         if ((this.x & 4) == 4) {
            var1 += a.i.g(3, this.A);
         }

         if ((this.x & 8) == 8) {
            var1 += a.i.g(4, this.B);
         }

         if ((this.x & 16) == 16) {
            var1 += a.i.g(5, this.C);
         }

         if ((this.x & 32) == 32) {
            var1 += a.i.g(6, this.D);
         }

         if ((this.x & 64) == 64) {
            var1 += a.i.g(7, this.E);
         }

         if ((this.x & 128) == 128) {
            var1 += a.i.g(8, this.F);
         }

         if ((this.x & 256) == 256) {
            var1 += a.i.g(9, this.G);
         }

         if ((this.x & 512) == 512) {
            var1 += a.i.g(10, this.H);
         }

         if ((this.x & 1024) == 1024) {
            var1 += a.i.g(11, this.I);
         }

         if ((this.x & 2048) == 2048) {
            var1 += a.i.g(12, this.J);
         }

         if ((this.x & 4096) == 4096) {
            var1 += a.i.g(13, this.K);
         }

         if ((this.x & 8192) == 8192) {
            var1 += a.i.g(14, this.L);
         }

         if ((this.x & 16384) == 16384) {
            var1 += a.i.g(15, this.M);
         }

         if ((this.x & 32768) == 32768) {
            var1 += a.i.g(16, this.N);
         }

         if ((this.x & 65536) == 65536) {
            var1 += a.i.g(17, this.O);
         }

         if ((this.x & 131072) == 131072) {
            var1 += a.i.g(18, this.P);
         }

         if ((this.x & 262144) == 262144) {
            var1 += a.i.g(19, this.Q);
         }

         var1 += this.b_().d();
         this.S = var1;
         return var1;
      }

      @Override
      protected Object I() throws ObjectStreamException {
         return super.I();
      }

      public static PBMessageALL9.g a(a.g var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL9.g a(a.g var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL9.g a(byte[] var0) throws s {
         return a.d(var0);
      }

      public static PBMessageALL9.g a(byte[] var0, n var1) throws s {
         return a.d(var0, var1);
      }

      public static PBMessageALL9.g a(InputStream var0) throws IOException {
         return a.h(var0);
      }

      public static PBMessageALL9.g a(InputStream var0, n var1) throws IOException {
         return a.h(var0, var1);
      }

      public static PBMessageALL9.g b(InputStream var0) throws IOException {
         return a.f(var0);
      }

      public static PBMessageALL9.g b(InputStream var0, n var1) throws IOException {
         return a.f(var0, var1);
      }

      public static PBMessageALL9.g a(a.h var0) throws IOException {
         return a.d(var0);
      }

      public static PBMessageALL9.g a(a.h var0, n var1) throws IOException {
         return a.b(var0, var1);
      }

      public static PBMessageALL9.g.a ak() {
         return PBMessageALL9.g.a.aK();
      }

      public PBMessageALL9.g.a al() {
         return ak();
      }

      public static PBMessageALL9.g.a a(PBMessageALL9.g var0) {
         return ak().a(var0);
      }

      public PBMessageALL9.g.a am() {
         return a(this);
      }

      protected PBMessageALL9.g.a a(a.p.b var1) {
         return new PBMessageALL9.g.a(var1, null);
      }

      // $VF: synthetic method
      @Override
      public a.y.a O() {
         return this.am();
      }

      // $VF: synthetic method
      @Override
      public x.a M() {
         return this.am();
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
         return this.al();
      }

      // $VF: synthetic method
      @Override
      public x.a N() {
         return this.al();
      }

      // $VF: synthetic method
      g(a.h var1, n var2, PBMessageALL9.g var3) throws s {
         this(var1, var2);
      }

      // $VF: synthetic method
      g(p.a var1, PBMessageALL9.g var2) {
         this(var1);
      }

      public static final class a extends p.a<PBMessageALL9.g.a> implements PBMessageALL9.h {
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
         private int q;
         private int r;
         private int s;
         private int t;

         public static final k.a k() {
            return PBMessageALL9.c;
         }

         @Override
         protected a.p.g l() {
            return PBMessageALL9.d.a(PBMessageALL9.g.class, PBMessageALL9.g.a.class);
         }

         private a() {
            this.aJ();
         }

         private a(a.p.b var1) {
            super(var1);
            this.aJ();
         }

         private void aJ() {
            PBMessageALL9.g.m;
         }

         private static PBMessageALL9.g.a aK() {
            return new PBMessageALL9.g.a();
         }

         public PBMessageALL9.g.a m() {
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
            this.q = 0;
            this.a &= -32769;
            this.r = 0;
            this.a &= -65537;
            this.s = 0;
            this.a &= -131073;
            this.t = 0;
            this.a &= -262145;
            return this;
         }

         public PBMessageALL9.g.a n() {
            return aK().a(this.N());
         }

         @Override
         public k.a J() {
            return PBMessageALL9.c;
         }

         public PBMessageALL9.g I() {
            return PBMessageALL9.g.h();
         }

         public PBMessageALL9.g M() {
            PBMessageALL9.g var1 = this.N();
            if (!var1.a()) {
               throw b(var1);
            } else {
               return var1;
            }
         }

         public PBMessageALL9.g N() {
            PBMessageALL9.g var1 = new PBMessageALL9.g(this, null);
            int var2 = this.a;
            int var3 = 0;
            if ((var2 & 1) == 1) {
               var3 |= 1;
            }

            var1.y = this.b;
            if ((var2 & 2) == 2) {
               var3 |= 2;
            }

            var1.z = this.c;
            if ((var2 & 4) == 4) {
               var3 |= 4;
            }

            var1.A = this.d;
            if ((var2 & 8) == 8) {
               var3 |= 8;
            }

            var1.B = this.e;
            if ((var2 & 16) == 16) {
               var3 |= 16;
            }

            var1.C = this.f;
            if ((var2 & 32) == 32) {
               var3 |= 32;
            }

            var1.D = this.g;
            if ((var2 & 64) == 64) {
               var3 |= 64;
            }

            var1.E = this.h;
            if ((var2 & 128) == 128) {
               var3 |= 128;
            }

            var1.F = this.i;
            if ((var2 & 256) == 256) {
               var3 |= 256;
            }

            var1.G = this.j;
            if ((var2 & 512) == 512) {
               var3 |= 512;
            }

            var1.H = this.k;
            if ((var2 & 1024) == 1024) {
               var3 |= 1024;
            }

            var1.I = this.l;
            if ((var2 & 2048) == 2048) {
               var3 |= 2048;
            }

            var1.J = this.m;
            if ((var2 & 4096) == 4096) {
               var3 |= 4096;
            }

            var1.K = this.n;
            if ((var2 & 8192) == 8192) {
               var3 |= 8192;
            }

            var1.L = this.o;
            if ((var2 & 16384) == 16384) {
               var3 |= 16384;
            }

            var1.M = this.p;
            if ((var2 & 32768) == 32768) {
               var3 |= 32768;
            }

            var1.N = this.q;
            if ((var2 & 65536) == 65536) {
               var3 |= 65536;
            }

            var1.O = this.r;
            if ((var2 & 131072) == 131072) {
               var3 |= 131072;
            }

            var1.P = this.s;
            if ((var2 & 262144) == 262144) {
               var3 |= 262144;
            }

            var1.Q = this.t;
            var1.x = var3;
            this.q_();
            return var1;
         }

         public PBMessageALL9.g.a d(x var1) {
            if (var1 instanceof PBMessageALL9.g) {
               return this.a((PBMessageALL9.g)var1);
            }

            super.a(var1);
            return this;
         }

         public PBMessageALL9.g.a a(PBMessageALL9.g var1) {
            if (var1 == PBMessageALL9.g.h()) {
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

            if (var1.aa()) {
               this.p(var1.ab());
            }

            if (var1.ae()) {
               this.q(var1.af());
            }

            if (var1.ag()) {
               this.r(var1.R_());
            }

            if (var1.S_()) {
               this.s(var1.T_());
            }

            this.d(var1.b_());
            return this;
         }

         @Override
         public final boolean a() {
            return true;
         }

         public PBMessageALL9.g.a e(a.h var1, n var2) throws IOException {
            PBMessageALL9.g var3 = null;

            try {
               var3 = PBMessageALL9.g.a.d(var1, var2);
            } catch (s var8) {
               var3 = (PBMessageALL9.g)var8.a();
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

         public PBMessageALL9.g.a a(int var1) {
            this.a |= 1;
            this.b = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a O() {
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

         public PBMessageALL9.g.a b(int var1) {
            this.a |= 2;
            this.c = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a P() {
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

         public PBMessageALL9.g.a c(int var1) {
            this.a |= 4;
            this.d = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a ac() {
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

         public PBMessageALL9.g.a d(int var1) {
            this.a |= 8;
            this.e = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a ad() {
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

         public PBMessageALL9.g.a e(int var1) {
            this.a |= 16;
            this.f = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a an() {
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

         public PBMessageALL9.g.a f(int var1) {
            this.a |= 32;
            this.g = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a ao() {
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

         public PBMessageALL9.g.a g(int var1) {
            this.a |= 64;
            this.h = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a ap() {
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

         public PBMessageALL9.g.a h(int var1) {
            this.a |= 128;
            this.i = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a aq() {
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

         public PBMessageALL9.g.a i(int var1) {
            this.a |= 256;
            this.j = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a ar() {
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

         public PBMessageALL9.g.a j(int var1) {
            this.a |= 512;
            this.k = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a as() {
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

         public PBMessageALL9.g.a k(int var1) {
            this.a |= 1024;
            this.l = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a at() {
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

         public PBMessageALL9.g.a l(int var1) {
            this.a |= 2048;
            this.m = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a au() {
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

         public PBMessageALL9.g.a m(int var1) {
            this.a |= 4096;
            this.n = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a av() {
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

         public PBMessageALL9.g.a n(int var1) {
            this.a |= 8192;
            this.o = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a aw() {
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

         public PBMessageALL9.g.a o(int var1) {
            this.a |= 16384;
            this.p = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a ax() {
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

         public PBMessageALL9.g.a p(int var1) {
            this.a |= 32768;
            this.q = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a ay() {
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

         public PBMessageALL9.g.a q(int var1) {
            this.a |= 65536;
            this.r = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a az() {
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
         public int R_() {
            return this.s;
         }

         public PBMessageALL9.g.a r(int var1) {
            this.a |= 131072;
            this.s = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a aG() {
            this.a &= -131073;
            this.s = 0;
            this.t_();
            return this;
         }

         @Override
         public boolean S_() {
            return (this.a & 262144) == 262144;
         }

         @Override
         public int T_() {
            return this.t;
         }

         public PBMessageALL9.g.a s(int var1) {
            this.a |= 262144;
            this.t = var1;
            this.t_();
            return this;
         }

         public PBMessageALL9.g.a aH() {
            this.a &= -262145;
            this.t = 0;
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
         a(a.p.b var1, PBMessageALL9.g.a var2) {
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

      int R_();

      boolean S_();

      int T_();
   }
}
