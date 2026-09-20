package l1r.aq;

import a.g;
import java.util.ArrayList;
import java.util.HashMap;
import l1r.an.PBMessageALL;
import l1r.an.PBMessageALL3;
import l1r.an.PBMessageALL4;
import l1r.an.PBMessageALL6;
import l1r.ao.ItemTable;
import l1r.ap.L1ItemInstance;
import l1r.bi.LineageUtil;
import l1r.bi.Random;

public class L1Craft {
   private int a = 0;
   private int b = 0;
   private final ArrayList<L1ItemInstance> c = new ArrayList<>();
   private final HashMap<Integer, L1ItemInstance> d = new HashMap<>();
   private final HashMap<Integer, ArrayList<L1ItemInstance>> e = new HashMap<>();
   private final HashMap<Integer, L1ItemInstance> f = new HashMap<>();
   private final ArrayList<Integer> g = new ArrayList<>();
   private L1ItemInstance h = null;
   private L1ItemInstance i = null;
   private int j = 0;
   private int k = 0;
   private int l = 1;
   private int m = 99;
   private int n = 32767;
   private int o = -32768;
   private int p = 100;
   private int q = Integer.MIN_VALUE;
   private int r = Integer.MAX_VALUE;
   private int s = 100;

   public int a() {
      return this.a;
   }

   public L1Craft(int var1) {
      this.a = var1;
      if (var1 == 576) {
         this.b = 128;
      } else if (var1 == 577) {
         this.b = 32;
      } else if (var1 == 578) {
         this.b = 4;
      } else if (var1 == 616) {
         L1ItemInstance var2 = ItemTable.a().b(413);
         this.f.put(var2.fr(), var2);
      }
   }

   public void a(int var1, int var2, int var3) {
      L1ItemInstance var4 = ItemTable.a().b(var1);
      var4.e(var2);
      var4.a(var3);
      var4.n();
      this.c.add(var4);
   }

   public L1ItemInstance b() {
      return this.c.get(Random.a(this.c.size()));
   }

   public void a(int var1) {
      this.j = var1;
   }

   public int c() {
      return this.j;
   }

   public void b(int var1) {
      this.k = var1;
   }

   public int d() {
      return this.k;
   }

   public L1ItemInstance e() {
      return this.i;
   }

   public void a(int var1, int var2) {
      if (var1 > 0 && var2 > 0) {
         L1ItemInstance var3 = ItemTable.a().b(var1);
         var3.e(var2);
         var3.f(1);
         this.i = var3;
      }
   }

   public void c(int var1) {
      if (var1 != 0) {
         L1ItemInstance var2 = ItemTable.a().b(var1);
         var2.e(10);
         var2.f(1);
         this.h = var2;
      }
   }

   public L1ItemInstance f() {
      return this.h;
   }

   public void a(int var1, int var2, int var3, int var4) {
      L1ItemInstance var5 = ItemTable.a().b(var1);
      if (var5 == null) {
         System.out.println("L1Craft addMaterialItem is null id=" + var1 + "craftid=" + this.a);
      } else {
         var5.e(var2);
         var5.f(var4);
         var5.a(var3);
         var5.a(true);
         this.d.put(var5.N(), var5);
         this.e.put(var1, new ArrayList<>());
         this.g.add(var1);
      }
   }

   public void a(int var1, int var2, int var3, int var4, int var5) {
      ArrayList var6 = this.e.get(var1);
      L1ItemInstance var7 = ItemTable.a().b(var2);
      var7.e(var3);
      var7.f(var5);
      var7.a(var4);
      var7.a(true);
      var6.add(var7);
   }

   public HashMap<Integer, L1ItemInstance> g() {
      return this.d;
   }

   public HashMap<Integer, ArrayList<L1ItemInstance>> h() {
      return this.e;
   }

   public g i() {
      PBMessageALL3.L1R_c.L1R_a var1 = PBMessageALL3.L1R_c.aa();
      var1.a(this.a);
      var1.e(this.n());
      var1.b(this.b);
      var1.f(this.q());
      var1.g(this.f(0));
      var1.h(this.m());
      var1.i(this.p());
      var1.j(this.o());
      var1.c(3);
      return g.a(var1.M().g());
   }

   private g m() {
      PBMessageALL.L1R_c.L1R_a var1 = PBMessageALL.L1R_c.aa();
      var1.b(1);
      var1.c(this.f.size());

      for (L1ItemInstance var2 : this.f.values()) {
         PBMessageALL.L1R_a.L1R_a var4 = PBMessageALL.L1R_a.aa();
         var4.a(var2.m());
         var4.b(var2.E());
         var4.c(1);
         var1.e(var4.M().f());
      }

      return g.a(var1.M().g());
   }

   private g n() {
      PBMessageALL.L1R_a.L1R_a var1 = PBMessageALL.L1R_a.aa();
      String var2 = this.c.get(0).a().j().trim();
      int var3 = 994;
      if (!var2.isEmpty() && var2.contains("$")) {
         String[] var4 = var2.split("\\$");
         var3 = Integer.parseInt(var4[var4.length - 1].trim());
      }

      var1.a(this.k > 0 ? this.k : var3);
      var1.b(this.l);
      var1.c(this.m);
      var1.d(2);
      var1.e(this.o);
      var1.f(this.n);
      var1.g(this.q);
      var1.h(this.r);
      var1.i(this.p);
      return g.a(var1.M().g());
   }

   public g a(L1ItemInstance var1, boolean var2) {
      PBMessageALL.L1R_e.L1R_a var3 = PBMessageALL.L1R_e.ae();
      var3.a(var1.N());
      var3.b(var1.E());
      var3.c(-1);
      var3.d(var1.G());
      var3.e(var2 ? 0 : var1.F());
      var3.f(0);
      var3.g(0);
      var3.e(LineageUtil.a(var1.b()));
      var3.h(0);
      var3.i(0);
      var3.j(var1.e());
      var3.f(g.a(""));
      var3.g(g.a(var1.t()));
      var3.k(0);
      var3.l(0);
      var3.m(this.j > 0 ? 1 : 0);
      return g.a(var3.M().g());
   }

   public g j() {
      PBMessageALL4.L1R_e.L1R_a var1 = PBMessageALL4.L1R_e.aa();
      var1.a(1);
      var1.b(0);
      var1.c(1);
      var1.e(this.a(this.c.get(0), true));
      return g.a(var1.M().g());
   }

   private g a(int var1, L1ItemInstance var2) {
      PBMessageALL3.L1R_e.L1R_a var3 = PBMessageALL3.L1R_e.aa();
      var3.a(var2.m());
      var3.b(var2.E());
      var3.c(var1);
      var3.d(var2.G());
      var3.e(var2.F());
      var3.e(LineageUtil.a(var2.s()));
      var3.f(var2.e());
      return g.a(var3.M().g());
   }

   private g o() {
      PBMessageALL.L1R_g.L1R_a var1 = PBMessageALL.L1R_g.aa();
      var1.e(this.s());
      var1.f(this.r());
      var1.a(this.s * 10000);
      return g.a(var1.M().g());
   }

   private g p() {
      PBMessageALL4.L1R_i.L1R_a var1 = PBMessageALL4.L1R_i.aa();
      int var2 = 0;

      for (int var3 : this.g) {
         L1ItemInstance var5 = this.d.get(var3);
         var1.e(this.a(++var2, var5));
         if (this.e.containsKey(var5.N())) {
            for (L1ItemInstance var6 : this.e.get(var5.N())) {
               var1.e(this.a(var2, var6));
            }
         }
      }

      if (this.h != null) {
         var1.f(this.a(++var2, this.h));
      }

      return g.a(var1.M().g());
   }

   private g q() {
      PBMessageALL.L1R_c.L1R_a var1 = PBMessageALL.L1R_c.aa();
      var1.b(1);
      var1.c(0);
      var1.e(this.a(0L, 0L));
      return g.a(var1.M().g());
   }

   private g r() {
      PBMessageALL.L1R_g.L1R_a var1 = PBMessageALL.L1R_g.aa();
      var1.e(this.a(0L, 0L));
      var1.f(this.a(4294967295L, 4294967295L));
      var1.a(0);
      var1.b(0);
      if (this.i != null) {
         var1.b(1);
         var1.h(this.k());
      }

      var1.c(0);
      return g.a(var1.M().g());
   }

   public g k() {
      PBMessageALL.L1R_e.L1R_a var1 = PBMessageALL.L1R_e.ae();
      var1.a(this.i.N());
      var1.b(this.i.E());
      var1.c(-1);
      var1.d(this.i.G());
      var1.e(this.i.F());
      var1.f(0);
      var1.g(0);
      var1.e(LineageUtil.a(this.i.b()));
      var1.h(0);
      var1.i(0);
      var1.j(this.i.e());
      var1.f(g.a(""));
      var1.g(g.a(this.i.t()));
      return g.a(var1.M().g());
   }

   private g s() {
      PBMessageALL6.L1R_i.L1R_a var1 = PBMessageALL6.L1R_i.aa();
      var1.e(this.a(0L, 0L));
      var1.f(this.a(4294967295L, 4294967295L));
      int var2 = this.c.size();
      var1.b(var2 > 1 ? var2 : 0);
      var1.c(1);
      if (var2 > 1) {
         for (L1ItemInstance var3 : this.c) {
            var1.g(this.a(var3, false));
         }
      } else {
         var1.h(this.a(this.c.get(0), false));
         var1.i(this.j());
      }

      var1.d(0);
      return g.a(var1.M().g());
   }

   private g f(int var1) {
      PBMessageALL4.L1R_a.L1R_a var2 = PBMessageALL4.L1R_a.aa();
      var2.a(var1);
      return g.a(var2.M().g());
   }

   public g a(long var1, long var3) {
      PBMessageALL4.L1R_a.L1R_a var5 = PBMessageALL4.L1R_a.aa();
      var5.a(var1);
      var5.b(var3);
      return g.a(var5.M().g());
   }

   public void d(int var1) {
      this.s = var1;
   }

   public int l() {
      return this.s;
   }

   public void b(int var1, int var2) {
      this.l = var1;
      this.m = var2;
   }

   public void c(int var1, int var2) {
      this.o = var1;
      this.n = var2;
   }

   public void d(int var1, int var2) {
      this.q = var1;
      this.r = var2;
   }

   public void e(int var1) {
      this.p = var1;
   }
}
