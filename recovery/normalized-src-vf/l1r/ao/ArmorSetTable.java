package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1PolyMorph;
import l1r.be.S_ItemDesc;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class ArmorSetTable {
   private static final Logger a = Logger.getLogger(ArmorSetTable.class.getName());
   private static ArmorSetTable b;
   private final ArrayList<ArmorSetTable.L1R_a> c = new ArrayList<>();

   public static ArmorSetTable a() {
      if (b == null) {
         b = new ArmorSetTable();
      }

      return b;
   }

   private ArmorSetTable() {
      this.c();
   }

   private void c() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM armor_set");
         var3 = var2.executeQuery();
         this.a(var3);
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private void a(ResultSet var1) throws SQLException {
      while (var1.next()) {
         ArmorSetTable.L1R_a var2 = new ArmorSetTable.L1R_a();
         var2.b(var1.getInt("id"));
         var2.a(var1.getString("note"));
         var2.a(a(var1.getString("sets"), ","));
         var2.c(var1.getInt("polyid"));
         var2.d(var1.getInt("poly_desc"));
         var2.e(var1.getInt("ac"));
         var2.f(var1.getInt("hp"));
         var2.g(var1.getInt("mp"));
         var2.h(var1.getInt("hpr"));
         var2.i(var1.getInt("mpr"));
         var2.j(var1.getInt("mr"));
         var2.k(var1.getInt("str"));
         var2.l(var1.getInt("dex"));
         var2.m(var1.getInt("con"));
         var2.n(var1.getInt("wis"));
         var2.o(var1.getInt("cha"));
         var2.p(var1.getInt("intl"));
         var2.u(var1.getInt("hit_modifier"));
         var2.v(var1.getInt("dmg_modifier"));
         var2.w(var1.getInt("bow_hit_modifier"));
         var2.x(var1.getInt("bow_dmg_modifier"));
         var2.y(var1.getInt("sp"));
         var2.q(var1.getInt("defense_water"));
         var2.r(var1.getInt("defense_wind"));
         var2.s(var1.getInt("defense_fire"));
         var2.t(var1.getInt("defense_earth"));
         var2.z(var1.getInt("damage_reduction"));
         this.c.add(var2);
      }
   }

   public ArrayList<ArmorSetTable.L1R_a> b() {
      return this.c;
   }

   private static int[] a(String var0, String var1) {
      StringTokenizer var2 = new StringTokenizer(var0, var1);
      int var3 = var2.countTokens();
      String var4 = null;
      int[] var5 = new int[var3];

      for (int var6 = 0; var6 < var3; var6++) {
         var4 = var2.nextToken();
         var5[var6] = Integer.parseInt(var4);
      }

      return var5;
   }

   public ArrayList<ArmorSetTable.L1R_a> a(int var1) {
      ArrayList var2 = new ArrayList<>();

      for (ArmorSetTable.L1R_a var3 : this.c) {
         if (var3.a(var1)) {
            var2.add(var3);
         }
      }

      return var2;
   }

   public class L1R_a {
      private int b;
      private int[] c;
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
      private int u;
      private int v;
      private int w;
      private int x;
      private int y;
      private int z;
      private int A;
      private String B;

      public boolean a(int var1) {
         int[] var5 = this.c;
         int var4 = this.c.length;

         for (int var3 = 0; var3 < var4; var3++) {
            int var2 = var5[var3];
            if (var1 == var2) {
               return true;
            }
         }

         return false;
      }

      public boolean a(L1PcInstance var1) {
         return var1.j().b(this.c);
      }

      public void a(L1PcInstance var1, L1ItemInstance var2, boolean var3) {
         var2.e(false);
         var1.a(new S_ItemDesc(var2));
         int[] var7 = this.c;
         int var6 = this.c.length;

         for (int var5 = 0; var5 < var6; var5++) {
            int var4 = var7[var5];
            L1ItemInstance var8 = var1.j().b(var4);
            if (var8.D()) {
               var8.e(var3);
               var1.a(new S_ItemDesc(var8));
            }
         }
      }

      public void b(L1PcInstance var1) {
         var1.bL(this.f);
         var1.bH(this.g);
         var1.bJ(this.h);
         var1.c(this.i);
         var1.d(this.j);
         var1.co(this.k);
         var1.cp(this.z);
         var1.bN((byte)this.l);
         var1.bR((byte)this.m);
         var1.bP((byte)this.n);
         var1.bX((byte)this.o);
         var1.bT((byte)this.p);
         var1.bV((byte)this.q);
         var1.bZ(this.r);
         var1.bY(this.s);
         var1.ca(this.t);
         var1.cb(this.u);
         var1.G(this.v);
         var1.H(this.w);
         var1.I(this.x);
         var1.J(this.y);
         var1.F(this.A);
         if (this.d > -1) {
            if (this.d == 6080 || this.d == 6094) {
               this.d = var1.aJ() == 0 ? 6094 : 6080;
               if (!this.e(var1)) {
                  return;
               }
            }

            L1PolyMorph.a(var1, this.d, 0, 1);
         }
      }

      public void c(L1PcInstance var1) {
         var1.bL(-this.f);
         var1.bH(-this.g);
         var1.bJ(-this.h);
         var1.c(-this.i);
         var1.d(-this.j);
         var1.co(-this.k);
         var1.cp(-this.z);
         var1.bN(-((byte)this.l));
         var1.bR(-((byte)this.m));
         var1.bP(-((byte)this.n));
         var1.bX(-((byte)this.o));
         var1.bT(-((byte)this.p));
         var1.bV(-((byte)this.q));
         var1.bZ(-this.r);
         var1.bY(-this.s);
         var1.ca(-this.t);
         var1.cb(-this.u);
         var1.G(-this.v);
         var1.H(-this.w);
         var1.I(-this.x);
         var1.J(-this.y);
         var1.F(-this.A);
         if (this.d > -1) {
            if (this.d == 6080 || this.d == 6094) {
               this.d = var1.aJ() == 0 ? 6094 : 6080;
            }

            if (var1.fe() != this.d) {
               return;
            }

            L1PolyMorph.b(var1);
         }
      }

      private boolean e(L1PcInstance var1) {
         boolean var2 = false;
         if (var1.j().g(20383, 1)) {
            L1ItemInstance var3 = var1.j().b(20383);
            if (var3 != null && var3.I() != 0) {
               var2 = true;
            }
         }

         return var2;
      }

      public boolean d(L1PcInstance var1) {
         int var2 = 0;
         int[] var6 = this.c;
         int var5 = this.c.length;

         for (int var4 = 0; var4 < var5; var4++) {
            int var3 = var6[var4];

            for (L1ItemInstance var7 : var1.j().i()) {
               if (var7.N() == var3) {
                  var2++;
               }

               if (var2 >= 2) {
                  return true;
               }
            }
         }

         return false;
      }

      public int a() {
         return this.b;
      }

      public void b(int var1) {
         this.b = var1;
      }

      public int[] b() {
         return this.c;
      }

      public void a(int[] var1) {
         this.c = var1;
      }

      public int c() {
         return this.d;
      }

      public void c(int var1) {
         this.d = var1;
      }

      public int d() {
         return this.e;
      }

      public void d(int var1) {
         this.e = var1;
      }

      public int e() {
         return this.f;
      }

      public void e(int var1) {
         this.f = var1;
      }

      public int f() {
         return this.g;
      }

      public void f(int var1) {
         this.g = var1;
      }

      public int g() {
         return this.h;
      }

      public void g(int var1) {
         this.h = var1;
      }

      public int h() {
         return this.i;
      }

      public void h(int var1) {
         this.i = var1;
      }

      public int i() {
         return this.j;
      }

      public void i(int var1) {
         this.j = var1;
      }

      public int j() {
         return this.k;
      }

      public void j(int var1) {
         this.k = var1;
      }

      public int k() {
         return this.l;
      }

      public void k(int var1) {
         this.l = var1;
      }

      public int l() {
         return this.m;
      }

      public void l(int var1) {
         this.m = var1;
      }

      public int m() {
         return this.n;
      }

      public void m(int var1) {
         this.n = var1;
      }

      public int n() {
         return this.o;
      }

      public void n(int var1) {
         this.o = var1;
      }

      public int o() {
         return this.p;
      }

      public void o(int var1) {
         this.p = var1;
      }

      public int p() {
         return this.q;
      }

      public void p(int var1) {
         this.q = var1;
      }

      public int q() {
         return this.r;
      }

      public void q(int var1) {
         this.r = var1;
      }

      public int r() {
         return this.s;
      }

      public void r(int var1) {
         this.s = var1;
      }

      public int s() {
         return this.t;
      }

      public void s(int var1) {
         this.t = var1;
      }

      public int t() {
         return this.u;
      }

      public void t(int var1) {
         this.u = var1;
      }

      public int u() {
         return this.v;
      }

      public void u(int var1) {
         this.v = var1;
      }

      public int v() {
         return this.w;
      }

      public void v(int var1) {
         this.w = var1;
      }

      public int w() {
         return this.x;
      }

      public void w(int var1) {
         this.x = var1;
      }

      public int x() {
         return this.y;
      }

      public void x(int var1) {
         this.y = var1;
      }

      public int y() {
         return this.z;
      }

      public void y(int var1) {
         this.z = var1;
      }

      public int z() {
         return this.A;
      }

      public void z(int var1) {
         this.A = var1;
      }

      public String A() {
         return this.B;
      }

      public void a(String var1) {
         this.B = var1;
      }
   }
}
