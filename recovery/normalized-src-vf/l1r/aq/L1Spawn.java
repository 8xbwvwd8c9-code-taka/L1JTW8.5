package l1r.aq;

import java.util.HashMap;
import l1r.ai.IdFactory;
import l1r.ao.MobGroupTable;
import l1r.ao.NpcTable;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.at.L1GameTime;
import l1r.at.L1GameTimeClock;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;
import l1r.bi.LineageUtil;
import l1r.bi.Point;
import l1r.bi.Random;

public class L1Spawn {
   private final L1Npc b;
   private boolean c = false;
   private HashMap<Integer, Point> d = null;
   public static final int a = 2;
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
   private boolean u;
   private int v;
   private boolean w;
   private int x;
   private boolean y;

   public L1Spawn(L1Npc var1) {
      this.b = var1;
   }

   public void a() {
      this.c = true;
      if (this.f >= 2 && this.r <= 100 && this.t()) {
         this.d = new HashMap<>();
      }

      int var1 = 0;

      while (var1 < this.f) {
         this.s(++var1);
      }

      this.c = false;
   }

   private boolean t() {
      return this.m != 0 && this.n != 0 && this.o != 0 && this.p != 0;
   }

   private boolean u() {
      return this.k != 0 || this.l != 0;
   }

   private void s(int var1) {
      if (this.y && !L1GameTimeClock.a().b().e()) {
         this.a(var1, 0);
      } else {
         this.b(var1, 0);
      }
   }

   public void a(int var1, int var2) {
      GeneralThreadPool.a().a(new L1Spawn.L1R_a(var1, var2, null), this.v() * 1000);
   }

   private int v() {
      int var1 = this.r;
      int var2 = this.s - this.r;
      if (var2 > 0) {
         var1 += Random.a(var2);
      }

      L1GameTime var3 = L1GameTimeClock.a().b();
      if (this.y && !var3.e()) {
         int var4 = var3.a(11);
         int var5 = var3.a(12);
         var1 = ((17 - var4) * 60 - var5) * 60 / 6;
      }

      return Math.max(var1, 1);
   }

   protected void b(int var1, int var2) {
      L1NpcInstance var3 = LineageUtil.a(this.b);
      if (var3.z() == 97370) {
         L1Npc var4 = NpcTable.a().a(97370 + Random.a(8));
         var3 = LineageUtil.a(var4);
      }

      if (var2 == 0) {
         var3.cF(IdFactory.a().c());
      } else {
         var3.cF(var2);
      }

      if (this.q >= 0 && this.q <= 7) {
         var3.ct(this.q);
      }

      int var10 = var3.z();
      if (var10 == 45488 && this.t == 809) {
         var3.cE(this.t + Random.a(2));
      } else if (var10 == 45601 && this.t == 811) {
         var3.cE(this.t + Random.a(3));
      } else if (var10 == 81322 && this.t == 25) {
         var3.cE(this.t + Random.a(2));
      } else {
         var3.cE(this.t);
      }

      var3.u(this.v);
      var3.l(this.w);
      int var5 = this.i;
      int var6 = this.j;
      int var7 = 0;

      while (var7++ <= 50) {
         if (var7 > 49) {
            var5 = this.i;
            var6 = this.j;
         } else if (this.t()) {
            if (this.d != null && this.d.containsKey(var1)) {
               Point var11 = this.d.get(var1);
               L1Location var17 = new L1Location(var11, this.t).a(15, false);
               var5 = var17.f();
               var6 = var17.g();
            } else {
               int var8 = this.o - this.m;
               int var9 = this.p - this.n;
               var5 = this.m + Random.a(var8);
               var6 = this.n + Random.a(var9);
            }
         } else if (this.u()) {
            var5 = this.i + (Random.a(this.k) - Random.a(this.k));
            var6 = this.j + (Random.a(this.l) - Random.a(this.l));
         }

         var3.cG(var5);
         var3.cH(var6);
         var3.q(var5);
         var3.r(var6);
         if (var3.fq().a(var3.fu()) && var3.fq().c(var3.fs(), var3.ft())) {
            if (var3 instanceof L1MonsterInstance && !this.u) {
               L1MonsterInstance var12 = (L1MonsterInstance)var3;
               if (!L1World.a().f(var12).isEmpty()) {
                  GeneralThreadPool.a().a(new L1Spawn.L1R_a(var1, var3.fr(), null), 3000L);
                  return;
               }
            }
            break;
         }
      }

      var3.a(this);
      var3.g(true);
      var3.p(var1);
      if (this.c && this.d != null) {
         this.d.put(var1, new Point(var3.fs(), var3.ft()));
      }

      if (var3 instanceof L1MonsterInstance) {
         L1MonsterInstance var13 = (L1MonsterInstance)var3;
         var13.h();
         var13.b(false);
         if (var13.fp() == 666) {
            var13.c(true);
         } else if (var13.fp() >= 72 && var13.fp() <= 74) {
            t(var10);
         }
      }

      if (var10 == 45573 && var3.fp() == 2) {
         for (L1PcInstance var15 : L1World.a().c()) {
            if (var15.fp() == 2) {
               L1Teleport.a(var15, 32664, 32797, 2, 0, true);
            }
         }
      } else if (var10 == 46142 && var3.fp() == 73 || var10 == 46141 && var3.fp() == 74) {
         for (L1PcInstance var14 : L1World.a().c()) {
            if (var14.fp() >= 72 && var14.fp() <= 74) {
               L1Teleport.a(var14, 32840, 32833, 72, var14.fb(), true);
            }
         }
      }

      L1World.a().a(var3);
      L1World.a().c(var3);
      if (var3 instanceof L1MonsterInstance) {
         L1MonsterInstance var16 = (L1MonsterInstance)var3;
         if (!this.c && var16.ac() == 0) {
            var16.Z_();
         }
      }

      if (this.h != 0) {
         MobGroupTable.a().a(var3, this.h, this.o(), this.c);
      }

      var3.fg();
      var3.a_(0);
   }

   private static void t(int var0) {
      int[] var1 = new int[]{46143, 46144, 46145, 46146, 46147, 46148, 46149, 46150, 46151, 46152};
      int[] var2 = new int[]{5001, 5002, 5003, 5004, 5005, 5006, 5007, 5008, 5009, 5010};

      for (int var3 = 0; var3 < var1.length; var3++) {
         if (var0 == var1[var3]) {
            u(var2[var3]);
         }
      }
   }

   private static void u(int var0) {
      for (L1Object var1 : L1World.a().b()) {
         if (var1 instanceof L1DoorInstance) {
            L1DoorInstance var3 = (L1DoorInstance)var1;
            if (var3.i() == var0) {
               var3.g();
            }
         }
      }
   }

   public int b() {
      return this.e;
   }

   public void a(int var1) {
      this.e = var1;
   }

   public int c() {
      return this.f;
   }

   public void b(int var1) {
      this.f = var1;
   }

   public int d() {
      return this.g;
   }

   public void c(int var1) {
      this.g = var1;
   }

   public int e() {
      return this.h;
   }

   public void d(int var1) {
      this.h = var1;
   }

   public int f() {
      return this.i;
   }

   public void e(int var1) {
      this.i = var1;
   }

   public int g() {
      return this.j;
   }

   public void f(int var1) {
      this.j = var1;
   }

   public int h() {
      return this.k;
   }

   public void g(int var1) {
      this.k = var1;
   }

   public int i() {
      return this.l;
   }

   public void h(int var1) {
      this.l = var1;
   }

   public int j() {
      return this.m;
   }

   public void i(int var1) {
      this.m = var1;
   }

   public void j(int var1) {
      this.n = var1;
   }

   public void k(int var1) {
      this.o = var1;
   }

   public void l(int var1) {
      this.p = var1;
   }

   public int k() {
      return this.q;
   }

   public void m(int var1) {
      this.q = var1;
   }

   public int l() {
      return this.r;
   }

   public void n(int var1) {
      this.r = var1;
   }

   public int m() {
      return this.s;
   }

   public void o(int var1) {
      this.s = var1;
   }

   public int n() {
      return this.t;
   }

   public void p(int var1) {
      this.t = var1;
   }

   public boolean o() {
      return this.u;
   }

   public void a(boolean var1) {
      this.u = var1;
   }

   public int p() {
      return this.v;
   }

   public void q(int var1) {
      this.v = var1;
   }

   public boolean q() {
      return this.w;
   }

   public void b(boolean var1) {
      this.w = var1;
   }

   public int r() {
      return this.x;
   }

   public void r(int var1) {
      this.x = var1;
   }

   public boolean s() {
      return this.y;
   }

   public void c(boolean var1) {
      this.y = var1;
   }

   private class L1R_a implements Runnable {
      private final int b;
      private final int c;

      private L1R_a(int var2, int var3) {
         this.b = var2;
         this.c = var3;
      }

      @Override
      public void run() {
         L1Spawn.this.b(this.b, this.c);
      }

      // $VF: synthetic method
      L1R_a(int var2, int var3, L1Spawn.L1R_a var4) {
         this(var2, var3);
      }
   }
}
