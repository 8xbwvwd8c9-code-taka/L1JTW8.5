package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.aq.L1Alchemy;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class MagicDollTable {
   private static final Logger a = Logger.getLogger(MagicDollTable.class.getName());
   private static MagicDollTable b;
   private final HashMap<Integer, MagicDollTable.L1R_a> c = new HashMap<>();

   public HashMap<Integer, MagicDollTable.L1R_a> a() {
      return this.c;
   }

   public static MagicDollTable b() {
      if (b == null) {
         b = new MagicDollTable();
      }

      return b;
   }

   private MagicDollTable() {
      this.c();
      L1Alchemy.a().a(this.c);
   }

   private void c() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM magic_doll");
         var3 = var2.executeQuery();

         while (var3.next()) {
            MagicDollTable.L1R_a var4 = new MagicDollTable.L1R_a();
            int var5 = var3.getInt("item_id");
            var4.a = var5;
            var4.b = var3.getString("nameid");
            var4.c = var3.getInt("gfxid");
            var4.d = var3.getInt("level");
            var4.e = var3.getInt("ac");
            var4.f = var3.getInt("mr");
            var4.g = var3.getInt("hp");
            var4.h = var3.getInt("mp");
            var4.i = var3.getInt("hpr");
            var4.j = var3.getInt("mpr");
            var4.n = var3.getInt("sp");
            var4.o = var3.getInt("hit");
            var4.k = var3.getInt("dmg");
            var4.m = var3.getInt("dmg_chance");
            var4.p = var3.getInt("bow_hit");
            var4.l = var3.getInt("bow_dmg");
            var4.q = var3.getInt("dmg_reduction");
            var4.r = var3.getInt("dmg_reduction_chance");
            var4.s = var3.getInt("dmg_evasion_chance");
            var4.t = var3.getInt("weight_reduction");
            var4.u = var3.getInt("str");
            var4.v = var3.getInt("con");
            var4.w = var3.getInt("dex");
            var4.x = var3.getInt("cha");
            var4.y = var3.getInt("intel");
            var4.z = var3.getInt("wis");
            var4.A = var3.getInt("regist_stun");
            var4.B = var3.getInt("regist_stone");
            var4.C = var3.getInt("regist_sleep");
            var4.D = var3.getInt("regist_freeze");
            var4.E = var3.getInt("regist_sustain");
            var4.F = var3.getInt("regist_blind");
            var4.G = var3.getInt("make_itemid");
            var4.H = var3.getInt("poison_chance");
            var4.I = var3.getInt("slow_chance");
            var4.J = var3.getInt("speed_up");
            var4.K = var3.getInt("breath_water");
            var4.L = var3.getInt("curse_chance");
            var4.M = var3.getInt("exp");
            this.c.put(var5, var4);
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(L1ItemInstance var1) {
      if (this.c.containsKey(var1.N())) {
         MagicDollTable.L1R_a var2 = this.c.get(var1.N());
         var1.a(var2.b);
         var1.az(var2.c);
         var1.r(var2.e);
         var1.t(var2.f);
         var1.p(var2.g);
         var1.q(var2.h);
         var1.aJ(var2.i);
         var1.aI(var2.j);
         var1.s(var2.n);
         var1.G(var2.o);
         var1.I(var2.k);
         var1.aA(var2.m);
         var1.H(var2.p);
         var1.J(var2.l);
         var1.D(var2.q);
         var1.aF(var2.r);
         var1.aB(var2.s);
         var1.aG(var2.t);
         var1.w(var2.u);
         var1.y(var2.v);
         var1.x(var2.w);
         var1.B(var2.x);
         var1.z(var2.y);
         var1.A(var2.z);
         var1.T(var2.A);
         var1.O(var2.B);
         var1.P(var2.C);
         var1.Q(var2.D);
         var1.S(var2.E);
         var1.R(var2.F);
         var1.aH(var2.G);
         var1.aC(var2.H);
         var1.aD(var2.I);
         var1.g(var2.J > 0);
         var1.h(var2.K > 0);
         var1.aE(var2.L);
         var1.aK(var2.M);
         if (var1.N() == 640771) {
            var1.ai(5);
         } else if (var1.N() == 640770) {
            var1.ak(10);
         }
      }
   }

   public class L1R_a {
      public int a;
      public String b;
      public int c;
      public int d;
      public int e;
      public int f;
      public int g;
      public int h;
      public int i;
      public int j;
      public int k;
      public int l;
      public int m;
      public int n;
      public int o;
      public int p;
      public int q;
      public int r;
      public int s;
      public int t;
      public int u;
      public int v;
      public int w;
      public int x;
      public int y;
      public int z;
      public int A;
      public int B;
      public int C;
      public int D;
      public int E;
      public int F;
      public int G;
      public int H;
      public int I;
      public int J;
      public int K;
      public int L;
      public int M;
   }
}
