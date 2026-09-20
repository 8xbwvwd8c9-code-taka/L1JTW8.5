package l1r.ap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.am.ListSprReader__obf_c;
import l1r.ao.MobSkillsTable;
import l1r.ao.NpcChatTable;
import l1r.ao.NpcTable;
import l1r.aq.L1Attack;
import l1r.aq.L1Character;
import l1r.aq.L1HateList;
import l1r.aq.L1Magic;
import l1r.aq.L1MobGroupInfo;
import l1r.aq.L1NpcTalkData;
import l1r.aq.L1Object;
import l1r.aq.L1Spawn;
import l1r.aq.L1World;
import l1r.as.L1Dragon;
import l1r.as.L1ThebesBattle;
import l1r.au.L1GroundInventory;
import l1r.au.L1Inventory;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.bb.NpcChatTimer;
import l1r.be.S_ChangeHeading;
import l1r.be.S_DoActionGFX;
import l1r.be.S_MoveCharPacket;
import l1r.be.S_NPCPack;
import l1r.be.S_NpcChangeShape;
import l1r.be.S_RemoveObject;
import l1r.be.S_SkillHaste;
import l1r.be.S_SkillSound;
import l1r.bf.S_044;
import l1r.bh.L1Npc;
import l1r.bh.L1NpcChat;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Point;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class L1NpcInstance extends L1Character {
   private static final Logger y = Logger.getLogger(L1NpcInstance.class.getName());
   public static final int a = 0;
   public static final int b = 1;
   public static final int c = 2;
   public static final int d = 3;
   public static final int e = 0;
   public static final int f = 1;
   public static final int g = 2;
   private boolean z = true;
   protected static int h = 15;
   private int A = 0;
   private int B = 0;
   private int C = 0;
   private final int[][] D = new int[][]{{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
   protected CopyOnWriteArrayList<L1ItemInstance> i = new CopyOnWriteArrayList<>();
   protected L1ItemInstance j = null;
   protected L1Character k = null;
   protected L1HateList l = new L1HateList();
   protected L1Character m = null;
   private int E = 0;
   protected L1HateList n = new L1HateList();
   private ScheduledFuture<?> F;
   private ScheduledFuture<?> G;
   private ConcurrentHashMap<Integer, Integer> H;
   private boolean I = false;
   private int J;
   private int K;
   protected L1Inventory o = new L1Inventory();
   private L1Npc L;
   private int M;
   private L1Spawn N;
   private int O;
   public static final int p = 0;
   public static final int q = 1;
   public static int[] r = new int[]{40012, 40011, 40010};
   public static int[] s = new int[]{40018, 40013};
   private String P = "";
   private boolean Q;
   private boolean R;
   private boolean S;
   private int T;
   private int U;
   private boolean V = false;
   private int W;
   private boolean X;
   private int Y;
   private int Z = 0;
   public static final int t = 0;
   public static final int u = 1;
   public static final int v = 2;
   private boolean aa = false;
   protected boolean w = false;
   private int ab;
   private boolean ac = false;
   private boolean ad = false;
   private boolean ae = false;
   private ScheduledFuture<?> af = null;
   private L1MobGroupInfo ag = null;
   private int ah = 0;
   private int ai = -1;
   private int aj = 0;
   private int ak = 0;
   private int al = 0;
   private String am = "";
   private int an = 0;
   private boolean ao = false;
   private boolean ap = false;

   protected void q() {
      new L1NpcInstance.f(null).a();
   }

   private boolean h() {
      this.v(300);
      this.d();
      if (this.m == null && this.k == null) {
         this.c();
      }

      this.a(true);
      this.Y_();
      if (this.m != null) {
         if (this.ac() != 0) {
            return true;
         }

         this.b();
         return false;
      } else {
         if (this.L.H()) {
            this.i();
            if (this.j == null) {
               this.j();
            }

            if (this.j != null) {
               L1Inventory var1 = L1World.a().a(this.j.fs(), this.j.ft(), this.j.fp());
               if (var1.f(this.j.N())) {
                  this.k();
               } else {
                  this.i.remove(this.j);
                  this.j = null;
                  this.v(1000);
               }

               return false;
            }
         }

         return this.a();
      }
   }

   public void a(boolean var1) {
   }

   public void Y_() {
   }

   @Override
   public void c(L1PcInstance var1) {
      this.a(var1, 0);
   }

   @Override
   public void a(L1PcInstance var1, int var2) {
      L1Attack var3 = new L1Attack(var1, this, var2);
      if (var3.a()) {
         var3.b();
         var3.a(var1, this);
      }

      var3.c();
      var3.d();
   }

   public void c() {
   }

   public void d() {
      if (this.m == null || this.m.fp() != this.fp() || this.m.ea() <= 0 || this.m.eX() || this.m.ff() && !this.V() && !this.n.a(this.m) || this.m.f(this) > 30
         )
       {
         this.s();
         if (!this.n.b()) {
            this.m = this.n.c();
            this.d();
         }
      }
   }

   public void c(L1Character var1, int var2) {
      if (var1 != null && var1.fr() != this.fr()) {
         if (this.n.b() && var2 != 0) {
            var2 += this.ew() / 10;
         }

         this.n.a(var1, var2);
         this.l.a(var1, var2);
         this.m = this.n.c();
         this.d();
      }
   }

   public void a(L1Character var1) {
   }

   public void c(L1PcInstance var1, int var2) {
      for (Object var3 : var1.eq()) {
         if (var3 instanceof L1NpcInstance) {
            L1NpcInstance var5 = (L1NpcInstance)var3;
            if (var5.U_().E() > 0 && (var5.U_().D() == var2 || var5.U_().E() > 1)) {
               var5.a((L1Character)var1);
            }

            if (this.ag != null && this.ah != 0 && var5.ak() == this.ah) {
               var5.a((L1Character)var1);
            }
         }
      }
   }

   public void b() {
      this.w = true;
      this.i.clear();
      this.j = null;
      L1Character var1 = this.m;
      if (this.O() == 0) {
         if (this.N() > 0) {
            int var4 = this.bB(40) ? 1 : 15;
            if (this.fu().c(var1.fu()) >= var4) {
               this.s();
               return;
            }

            int var6 = this.c(var1.fs(), var1.ft());
            var6 = this.a(this.fs(), this.ft(), this.fp(), var6);
            this.g(var6);
            this.v(this.f(this.N(), 0));
         }
      } else {
         int var2 = MobSkillsTable.a().a(this, var1);
         if (var2 > 0) {
            this.v(this.f(var2, 2));
         } else if (this.c(var1.fs(), var1.ft(), this.C())) {
            this.ct(this.h(var1.fs(), var1.ft()));
            this.b(var1);
         } else if (this.N() <= 0) {
            this.s();
         } else {
            if (this.U_().O()) {
               int var3 = this.fu().d(var1.fu());
               if (this.z && var3 > 3 && var3 < 15 && this.l(var1.fs(), var1.ft())) {
                  this.z = false;
                  return;
               }

               if (Random.a(100) < 20 && this.eb() >= 10 && var3 > 6 && var3 < 15 && this.l(var1.fs(), var1.ft())) {
                  return;
               }
            }

            if (this.as()) {
               this.c();
            } else {
               int var5 = this.a(var1.fs(), var1.ft());
               if (var5 == -1) {
                  this.c();
               } else {
                  this.g(var5);
                  this.v(this.f(this.N(), 0));
               }
            }
         }
      }
   }

   public void b(L1Character var1) {
      if (this.k instanceof L1PcInstance) {
         L1PcInstance var2 = (L1PcInstance)this.k;
         if (var2.aR()) {
            return;
         }
      }

      if (var1 instanceof L1PcInstance) {
         L1PcInstance var8 = (L1PcInstance)var1;
         if (var8.aR()) {
            return;
         }
      } else if (var1 instanceof L1PetInstance || var1 instanceof L1SummonInstance) {
         L1Character var9 = ((L1NpcInstance)var1).M();
         if (var9 instanceof L1PcInstance) {
            L1PcInstance var3 = (L1PcInstance)var9;
            if (var3.aR()) {
               return;
            }
         }
      }

      if (var1 instanceof L1NpcInstance) {
         L1NpcInstance var10 = (L1NpcInstance)var1;
         if (var10.ac() != 0) {
            this.t();
            return;
         }
      }

      boolean var11 = false;
      L1Attack var12 = new L1Attack(this, var1);
      if (var12.a()) {
         if (var1.bB(91)) {
            L1Magic var4 = new L1Magic(var1, this);
            boolean var5 = var4.a(91);
            boolean var6 = var12.f();
            if (var5 && var6) {
               var11 = true;
            }
         }

         if (var1.bB(191) && Random.a(100) < 10) {
            var11 = true;
         }

         int var13 = 0;
         double var14 = 0.4;
         if (var1 instanceof L1PcInstance) {
            L1PcInstance var7 = (L1PcInstance)var1;
            var14 += var7.dH() * 0.01;
         }

         if (var1.bB(231) && var1.ev() >= 80) {
            var13 = Math.min(5 + var1.ev() - 80, 10);
         }

         if (var1.bB(606) && var1.ea() < var1.ew() * var14 && var12.f() && Random.a(100) < 20 + var13) {
            var11 = true;
         }

         if (var1.bB(607) && var1.ea() < var1.ew() * var14 && !var12.f() && Random.a(100) < 20 + var13) {
            var11 = true;
         }

         if (!var11) {
            var12.b();
         }
      }

      if (var11) {
         var12.b();
         var12.e();
         var12.g();
      } else {
         var12.c();
         var12.d();
      }

      this.v(this.f(this.O(), 1));
   }

   private void i() {
      if (this.j == null || this.j.fp() != this.fp() || this.fu().d(this.j.fu()) > h) {
         if (this.i.isEmpty()) {
            this.j = null;
            return;
         }

         this.j = this.i.get(0);
         this.i.remove(0);
         this.i();
      }
   }

   private void j() {
      ArrayList var1 = new ArrayList<>();

      for (L1Object var2 : L1World.a().e(this)) {
         if (var2 instanceof L1GroundInventory) {
            var1.add((L1GroundInventory)var2);
         }
      }

      if (!var1.isEmpty()) {
         int var5 = Random.a(var1.size());

         for (L1ItemInstance var6 : var1.get(var5).d()) {
            if (this.y().a(var6, var6.E()) == 0) {
               this.j = var6;
               this.i.add(this.j);
            }
         }
      }
   }

   protected void r() {
      ArrayList var1 = new ArrayList<>();

      for (L1Object var2 : L1World.a().b(this, 3)) {
         if (var2 instanceof L1GroundInventory) {
            var1.add((L1GroundInventory)var2);
         }
      }

      if (!var1.isEmpty()) {
         int var5 = Random.a(var1.size());

         for (L1ItemInstance var6 : var1.get(var5).d()) {
            if (var6.f() && (var6.k() || var6.a().aP() == 7) && this.y().a(var6, var6.E()) == 0) {
               this.j = var6;
               this.i.add(this.j);
            }
         }

         if (this.j != null) {
            this.t(0);
            this.cq(ListSprReader__obf_c.a().a(this));
            this.b(new S_DoActionGFX(this.fr(), 45));
            this.Z_();
            this.a_(2);
         }
      }
   }

   private void k() {
      if (this.fu().f(this.j.fu())) {
         L1Inventory var3 = L1World.a().a(this.j.fs(), this.j.ft(), this.j.fp());
         L1ItemInstance var2 = var3.a(this.j, this.j.E(), this.y());
         this.i.remove(this.j);
         this.j = null;
         if (var2 != null) {
            this.fg();
            this.a(var2);
            this.v(1000);
         }
      } else {
         int var1 = this.a(this.j.fs(), this.j.ft());
         if (var1 == -1) {
            this.i.remove(this.j);
            this.j = null;
         } else {
            this.g(var1);
            this.v(this.f(this.N(), 0));
         }
      }
   }

   public boolean a() {
      if (L1World.a().f(this).isEmpty()) {
         return true;
      }

      if (this.k != null && this.k.fp() == this.fp() && this.fu().c(this.k.fu()) > 2) {
         int var4 = this.a(this.k.fs(), this.k.ft());
         if (var4 == -1) {
            return true;
         }

         this.g(var4);
         this.v(this.f(this.N(), 0));
         return false;
      } else {
         if (this.k == null && this.N() > 0 && !this.ai()) {
            if (this.ag == null || this.ag.b(this)) {
               if (this.B == 0) {
                  this.B = Random.a(5) + 1;
                  this.C = Random.a(20);
                  if (this.X() != 0 && this.Y() != 0 && this.C < 8 && Random.a(3) == 0) {
                     this.C = this.a(this.X(), this.Y());
                  }

                  return false;
               }

               this.B--;
               int var3 = this.a(this.fs(), this.ft(), this.fp(), this.C);
               if (var3 != -1) {
                  this.g(var3);
                  this.v(this.f(this.N(), 0));
               }

               return false;
            }

            L1NpcInstance var1 = this.ag.a();
            if (this.fu().c(var1.fu()) > 2) {
               int var2 = this.a(var1.fs(), var1.ft());
               if (var2 != -1) {
                  this.g(var2);
                  this.v(this.f(this.N(), 0));
               }
            }
         }

         return false;
      }
   }

   public void a(L1PcInstance var1, String var2) {
   }

   public void s() {
      if (this.m != null) {
         this.n.b(this.m);
         this.m = null;
      }
   }

   public void c(L1Character var1) {
      this.n.b(var1);
      if (this.m != null && this.m.equals(var1)) {
         this.m = null;
      }
   }

   public void t() {
      this.n.a();
      this.l.a();
      this.m = null;
      this.i.clear();
      this.j = null;
   }

   public void Z_() {
      if (!this.ae()) {
         this.w = false;
         this.q();
      }
   }

   public synchronized void u() {
      if (this.F == null) {
         int var1 = this.U_().K();
         int var2 = this.U_().L();
         if (var2 > 0 && var1 >= 500) {
            this.F = GeneralThreadPool.a().a(new L1NpcInstance.d(var2), var1, var1);
         }
      }
   }

   public void v() {
      if (this.F != null) {
         this.F.cancel(true);
         this.F = null;
      }
   }

   public synchronized void w() {
      if (this.G == null) {
         int var1 = this.U_().M();
         int var2 = this.U_().N();
         if (var2 > 0 && var1 >= 500) {
            this.G = GeneralThreadPool.a().a(new L1NpcInstance.e(var2, null), var1, var1);
         }
      }
   }

   public void x() {
      if (this.G != null) {
         this.G.cancel(true);
         this.G = null;
      }
   }

   public void a(L1ItemInstance var1) {
      if (this.m() > 0 && (this.L.b() == 45032 || this.L.b() == 190864)) {
         this.a(20, 1, new int[]{40508, 40521, 40045}, new int[]{150, 3, 3});
         this.a(19, 1, new int[]{40494, 40521}, new int[]{150, 3});
         this.a(3, 1, new int[]{40494, 40521}, new int[]{50, 1});
         this.a(100, 1, new int[]{88, 40508, 40045}, new int[]{4, 80, 3});
         this.a(89, 1, new int[]{88, 40494}, new int[]{2, 80});
      }

      this.b(var1);
      this.y().f();
   }

   private void b(L1ItemInstance var1) {
      if (this.U_().I() > 0) {
         this.H.put(var1.fr(), this.U_().I());
         if (!this.I) {
            this.I = true;
            GeneralThreadPool.a().a(new L1NpcInstance.c(null));
         }
      }
   }

   private void a(int var1, int var2, int[] var3, int[] var4) {
      if (!this.o.f(var1) && this.o.a(var3, var4)) {
         for (int var5 = 0; var5 < var3.length; var5++) {
            this.o.b(var3[var5], var4[var5]);
         }

         L1ItemInstance var6 = this.o.a(var1, var2);
         this.b(var6);
      }
   }

   public L1NpcInstance(L1Npc var1) {
      if (var1 != null) {
         this.a(var1);
      }
   }

   public void a(L1Npc var1) {
      this.L = var1;
      this.e(var1.c());
      this.a(var1.A());
      int var2 = 0;
      double var3 = 0.0;
      if (var1.P() == 0) {
         this.b(var1.e());
      } else {
         int var5 = Random.a(var1.P() - var1.e() + 1);
         var2 = var1.P() - var1.e();
         var3 = var5 / var2;
         this.b(var1.e() + var5);
      }

      if (var1.Q() == 0) {
         this.bG(var1.f());
         this.bx(var1.f());
      } else {
         int var6 = (int)(var3 * (var1.Q() - var1.f()));
         this.bG(var1.f() + var6);
         this.bx(var1.f() + var6);
      }

      if (var1.R() == 0) {
         this.bI(var1.g());
         this.by(var1.g());
      } else {
         int var7 = (int)(var3 * (var1.R() - var1.g()));
         this.bI(var1.g() + var7);
         this.by(var1.g() + var7);
      }

      if (var1.S() == 0) {
         this.bK(var1.h());
      } else {
         int var8 = (int)(var3 * (var1.S() - var1.h()));
         this.bK(var1.h() + var8);
      }

      if (var1.P() == 0) {
         this.bM(var1.i());
         this.bO(var1.j());
         this.bQ(var1.k());
         this.bU(var1.m());
         this.bW(var1.l());
         this.cx(var1.n());
      } else {
         this.bM(Math.min(var1.i() + var2, 127));
         this.bO(Math.min(var1.j() + var2, 127));
         this.bQ(Math.min(var1.k() + var2, 127));
         this.bU(Math.min(var1.m() + var2, 127));
         this.bW(Math.min(var1.l() + var2, 127));
         this.cx(Math.min(var1.n() + var2, 127));
         this.cm(var2 * 2);
         this.ck(var2 * 2);
      }

      if (var1.T() == 0) {
         this.k(var1.o());
      } else {
         this.k(this.ev() * this.ev() + 1);
      }

      if (var1.U() == 0) {
         this.cr(var1.p());
      } else {
         int var9 = (int)(var3 * (var1.U() - var1.p()));
         this.cr(var1.p() + var9);
      }

      this.d(var1.y());
      this.e(var1.u());
      this.f(var1.t());
      this.cw(var1.z());
      this.cq(ListSprReader__obf_c.a().a(this));
      this.x(var1.s());
      this.b_(var1.Z());
      this.cv(var1.J() ? 1 : 0);
      this.A(var1.aa());
      this.s(var1.ae());
      if (var1.w()) {
         this.m(ListSprReader__obf_c.a().a(this.fe(), this.eY()));
      } else {
         this.m(0);
      }

      if (var1.x()) {
         int var10 = 1;
         if (ListSprReader__obf_c.a().b(this.fe(), this.eY() + 1)) {
            var10 = this.eY() + 1;
         }

         this.n(ListSprReader__obf_c.a().a(this.fe(), var10));
      } else {
         this.n(0);
      }

      if (var1.I() > 0) {
         this.H = new ConcurrentHashMap<>();
      }
   }

   public void b(int var1, int var2) {
      this.v(this.f(ListSprReader__obf_c.a().a(this.fe(), var1), var2));
   }

   @Override
   public L1Inventory y() {
      return this.o;
   }

   public int z() {
      return this.L.b();
   }

   private void b(boolean var1) {
      int var2 = 0;
      if (var1) {
         var2 = this.fr();
      } else {
         var2 = 0;
      }

      this.N.a(this.O, var2);
   }

   @Override
   public void b(L1PcInstance var1) {
      var1.c(this);
      var1.a(new S_NPCPack(this));
      this.Z_();
   }

   public synchronized void aa_() {
      this.k(true);
      if (this.y() != null) {
         this.y().g();
      }

      this.t();
      this.k = null;
      L1World.a().d(this);
      L1World.a().b(this);

      for (L1PcInstance var1 : L1World.a().f(this)) {
         var1.d(this);
         var1.a(new S_RemoveObject(this));
      }

      this.es();
      this.fq().a(this.fu(), true);
      if (this.ag == null) {
         if (this.Z()) {
            this.b(true);
         }
      } else if (this.ag.d(this) == 0) {
         this.ag = null;
         if (this.Z()) {
            this.b(false);
         }
      }

      if (this.z() >= 190331 && this.z() <= 190342) {
         L1Dragon.a().a(this.z());
      } else if (this.z() == 190554) {
         L1ThebesBattle.a().b();
      }
   }

   public void a(long var1) {
      GeneralThreadPool.a().a(new L1NpcInstance.a(null), var1);
   }

   public void a(L1Character var1, int var2) {
   }

   public void b(L1Character var1, int var2) {
   }

   public void g(int var1) {
      if (var1 >= 0) {
         this.ct(var1);
         this.fq().a(this.fu(), true);
         this.cG(this.fs() + this.D[var1][0]);
         this.cH(this.ft() + this.D[var1][1]);
         this.fq().a(this.fu(), false);
         this.b(new S_MoveCharPacket(this));
         if (this.Z > 0 && this.X() > 0 && this.Y() > 0 && this.fu().b(new Point(this.X(), this.Y())) > this.Z) {
            this.a(this.X(), this.Y(), this.fb());
         }
      }
   }

   protected int a(int var1, int var2) {
      double var3 = this.fu().b(new Point(var1, var2));
      if (this.bB(40) && var3 >= 2.0) {
         return -1;
      }

      if (var3 > h * 2) {
         return -1;
      }

      if (var3 > h) {
         return this.a(this.fs(), this.ft(), this.fp(), this.h(var1, var2));
      }

      int var5 = this.d(var1, var2);
      if (var5 == -1) {
         var5 = this.h(var1, var2);
         if (!this.h(var5)) {
            var5 = this.a(this.fs(), this.ft(), this.fp(), var5);
         }
      }

      return var5;
   }

   public int c(int var1, int var2) {
      int var3 = this.h(var1, var2);
      var3 += 4;
      if (var3 > 7) {
         var3 -= 8;
      }

      return var3;
   }

   protected int a(int var1, int var2, int var3, int var4) {
      if (var4 <= 7 && var4 >= 0) {
         L1Map var5 = L1WorldMap.b().a(var3);
         int var6 = var4 == 0 ? 7 : var4 - 1;
         int var7 = var4 == 7 ? 0 : var4 + 1;
         if (var5.c(var1, var2, var4)) {
            return var4;
         } else if (var5.c(var1, var2, var6)) {
            return var6;
         } else {
            return var5.c(var1, var2, var7) ? var7 : -1;
         }
      } else {
         return -1;
      }
   }

   protected boolean h(int var1) {
      if (!(this instanceof L1MonsterInstance)) {
         return false;
      }

      if (this.m == null) {
         return false;
      }

      int var2 = this.fs() + this.D[var1][0];
      int var3 = this.ft() + this.D[var1][1];

      for (L1Object var4 : this.m.eq()) {
         if (var4 instanceof L1PcInstance || var4 instanceof L1SummonInstance || var4 instanceof L1PetInstance) {
            L1Character var6 = (L1Character)var4;
            if (!var6.eX()
               && var6.fs() == var2
               && var6.ft() == var3
               && var6.fp() == this.fp()
               && (!(var4 instanceof L1PcInstance) || !((L1PcInstance)var4).bN())) {
               this.n.a(var6, 0);
               this.m = var6;
               return true;
            }
         }
      }

      return false;
   }

   protected int d(int var1, int var2) {
      int var3 = h + 1;
      int var4 = var1 - var3;
      int var5 = var2 - var3;
      int[] var6 = new int[]{this.fs() - var4, this.ft() - var5, 0, 0};
      boolean[][] var7 = new boolean[var3 * 2 + 1][var3 * 2 + 1];

      for (int var8 = h * 2 + 1; var8 > 0; var8--) {
         for (int var9 = h - Math.abs(var3 - var8); var9 >= 0; var9--) {
            var7[var8][var3 + var9] = true;
            var7[var8][var3 - var9] = true;
         }
      }

      LinkedList var18 = new LinkedList<>();
      int[] var19 = new int[]{2, 4, 6, 0, 1, 3, 5, 7};
      int[] var13 = var19;
      int var12 = var19.length;

      for (int var11 = 0; var11 < var12; var11++) {
         int var10 = var13[var11];
         int[] var14 = (int[])var6.clone();
         var14[0] += this.D[var10][0];
         var14[1] += this.D[var10][1];
         var14[2] = var10;
         if (var14[0] + var4 == var1 && var14[1] + var5 == var2) {
            return var10;
         }

         if (var7[var14[0]][var14[1]]) {
            if (this.fq().c(this.fs(), this.ft(), var10)) {
               var14[2] = var10;
               var14[3] = var10;
               var18.add((int[])var14.clone());
            }

            var7[var14[0]][var14[1]] = false;
         }
      }

      while (!var18.isEmpty()) {
         var6 = var18.removeFirst();
         var13 = var19;
         var12 = var19.length;

         for (int var21 = 0; var21 < var12; var21++) {
            int var20 = var13[var21];
            int[] var24 = (int[])var6.clone();
            var24[0] += this.D[var20][0];
            var24[1] += this.D[var20][1];
            var24[2] = var20;
            if (var24[0] + var4 == var1 && var24[1] + var5 == var2) {
               return var24[3];
            }

            if (var7[var24[0]][var24[1]]) {
               int var15 = var6[0] + var4;
               int var16 = var6[1] + var5;
               if (this.fq().c(var15, var16, var20)) {
                  var24[2] = var20;
                  var18.add((int[])var24.clone());
               }

               var7[var24[0]][var24[1]] = false;
            }
         }
      }

      return -1;
   }

   private void k(int var1, int var2) {
      this.b(new S_SkillSound(this.fr(), var2));
      if (this.bB(173)) {
         var1 /= 2;
      }

      if (this instanceof L1PetInstance) {
         ((L1PetInstance)this).a(this.ea() + var1);
      } else if (this instanceof L1SummonInstance) {
         ((L1SummonInstance)this).a(this.ea() + var1);
      } else {
         this.bx(this.ea() + var1);
      }
   }

   private void d(int var1) {
      this.b(new S_SkillHaste(this.fr(), 1, var1));
      this.b(new S_SkillSound(this.fr(), 191));
      this.cu(1);
      this.j(1001, var1 * 1000);
   }

   public void e(int var1, int var2) {
      if (!this.bB(71)) {
         if (Random.a(100) <= var2) {
            if (var1 == 0) {
               if (this.y().b(40012, 1)) {
                  this.k(75, 197);
               } else if (this.y().b(40011, 1)) {
                  this.k(45, 194);
               } else if (this.y().b(40010, 1)) {
                  this.k(15, 189);
               }
            } else if (var1 == 1) {
               if (this.bB(1001)) {
                  return;
               }

               if (this.y().b(40018, 1)) {
                  this.d(1800);
               } else if (this.y().b(40013, 1)) {
                  this.d(300);
               }
            }
         }
      }
   }

   private boolean l(int var1, int var2) {
      int var3 = Random.a(8);

      for (int var5 = 0; var5 < 8; var5++) {
         int var4 = var3 + var5;
         if (var4 > 7) {
            var4 -= 8;
         }

         var1 += this.D[var4][0];
         var2 += this.D[var4][1];
         if (this.fq().c(var1, var2)) {
            var4 += 4;
            if (var4 > 7) {
               var4 -= 8;
            }

            this.a(var1, var2, var4);
            this.i_(this.eb() - 10);
            return true;
         }
      }

      return false;
   }

   public void a(int var1, int var2, int var3) {
      for (L1PcInstance var4 : L1World.a().f(this)) {
         var4.a(new S_SkillSound(this.fr(), 169));
         var4.a(new S_RemoveObject(this));
         var4.d(this);
      }

      this.cG(var1);
      this.cH(var2);
      this.ct(var3);
   }

   protected int f(int var1, int var2) {
      if (this.fc() == 1) {
         var1 = (int)(var1 * 0.75);
      } else if (this.fc() == 2) {
         var1 = (int)(var1 / 0.75);
      }

      if (this.fd() == 1) {
         var1 = (int)(var1 * 0.75);
      }

      if (this.bB(167) && (var2 == 1 || var2 == 2)) {
         var1 = (int)(var1 / 0.75);
      }

      return var1;
   }

   public int i(int var1) {
      int var2 = 40;
      if (this.A >= 40) {
         return 0;
      }

      int var3 = Math.min(var1, this.eb());
      if (this.A + var3 > 40) {
         var3 = 40 - this.A;
      }

      this.A += var3;
      return var3;
   }

   public void g_(int var1) {
      this.v();
      this.x();
      int var2 = this.U_().ac();
      if (var2 != 0) {
         this.b(new S_SkillSound(this.fr(), var2));
      }

      L1Npc var3 = NpcTable.a().a(var1);
      this.a(var3);
      this.b(new S_NpcChangeShape(this.fr(), this.fe(), this.fa(), this.eY()));

      for (L1PcInstance var4 : L1World.a().f(this)) {
         this.b(var4);
      }
   }

   @Override
   public synchronized void j(int var1) {
      if (!this.ah()) {
         if (this.af != null) {
            if (this.af.cancel(true)) {
               return;
            }

            this.af = null;
         }

         super.j(var1);
         new S_044().a(this, 1);
      }
   }

   protected synchronized void A() {
      if (this.af == null) {
         this.af = GeneralThreadPool.a().a(new L1NpcInstance.b(null), Config.at * 1000);
      }
   }

   public boolean B() {
      return this.ag != null;
   }

   public void a_(int var1) {
      if (var1 != 0 || !this.eX()) {
         if (var1 != 1 || this.eX()) {
            if (var1 != 2 || !this.eX()) {
               L1NpcChat var2 = null;
               if (var1 == 0) {
                  var2 = NpcChatTable.a().a(this.z());
               } else if (var1 == 1) {
                  var2 = NpcChatTable.a().b(this.z());
               } else if (var1 == 2) {
                  var2 = NpcChatTable.a().c(this.z());
               }

               if (var2 != null) {
                  if (Random.a(100) <= var2.n()) {
                     new NpcChatTimer(this, var2).a();
                  }
               }
            }
         }
      }
   }

   public int C() {
      return this.ai == -1 ? this.U_().s() : this.ai;
   }

   public String D() {
      return this.L.ai();
   }

   public String E() {
      return this.L.aj();
   }

   public String[] F() {
      return this.L.ak();
   }

   public int G() {
      return this.L.z();
   }

   @Override
   public void a(L1PcInstance var1) {
      if (this.U_().ag()) {
         this.ct(this.h(var1.fs(), var1.ft()));
         this.b(new S_ChangeHeading(this));
         if (this.U_().w() && !this.ai()) {
            this.l(true);
            GeneralThreadPool.a().a(new L1NpcInstance.g(null), 10000L);
         }
      }

      L1NpcTalkData.b(this, var1);
   }

   public void g(int var1, int var2) {
      this.ak = var1;
      this.al = var2;
   }

   public boolean H() {
      return this.U_().ad();
   }

   public void I() {
      this.an++;
   }

   public int J() {
      return this.U_().al();
   }

   public int K() {
      return this.U_().am();
   }

   public boolean d(L1Character var1) {
      return this.l.a(var1);
   }

   public boolean L() {
      if (this.M() instanceof L1PcInstance) {
         L1PcInstance var1 = (L1PcInstance)this.M();
         if (var1.bE() == 1) {
            return true;
         }
      }

      return false;
   }

   public L1Character M() {
      return this.k;
   }

   public void e(L1Character var1) {
      this.k = var1;
   }

   public void l(int var1) {
      this.E = var1;
   }

   public int N() {
      return this.J;
   }

   public void m(int var1) {
      this.J = var1;
   }

   public int O() {
      return this.K;
   }

   public void n(int var1) {
      this.K = var1;
   }

   public void a(L1Inventory var1) {
      this.o = var1;
   }

   public L1Npc U_() {
      return this.L;
   }

   public int Q() {
      return this.M;
   }

   public void o(int var1) {
      this.M = var1;
   }

   public L1Spawn R() {
      return this.N;
   }

   public void a(L1Spawn var1) {
      this.N = var1;
   }

   public int S() {
      return this.O;
   }

   public void p(int var1) {
      this.O = var1;
   }

   public String T() {
      return this.P;
   }

   public void a(String var1) {
      this.P = var1;
   }

   public boolean V_() {
      return this.Q;
   }

   public void d(boolean var1) {
      this.Q = var1;
   }

   public boolean V() {
      return this.R;
   }

   public void e(boolean var1) {
      this.R = var1;
   }

   public boolean W() {
      return this.S;
   }

   public void f(boolean var1) {
      this.S = var1;
   }

   public int X() {
      return this.T;
   }

   public void q(int var1) {
      this.T = var1;
   }

   public int Y() {
      return this.U;
   }

   public void r(int var1) {
      this.U = var1;
   }

   public boolean Z() {
      return this.V;
   }

   public void g(boolean var1) {
      this.V = var1;
   }

   public int aa() {
      return this.W;
   }

   public void s(int var1) {
      this.W = var1;
   }

   public boolean ab() {
      return this.X;
   }

   public void h(boolean var1) {
      this.X = var1;
   }

   public int ac() {
      return this.Y;
   }

   public void t(int var1) {
      this.Y = var1;
   }

   public int ad() {
      return this.Z;
   }

   public void u(int var1) {
      this.Z = var1;
   }

   public boolean ae() {
      return this.aa;
   }

   public void i(boolean var1) {
      this.aa = var1;
   }

   public int af() {
      return this.ab;
   }

   public void v(int var1) {
      this.ab = var1;
   }

   public boolean ag() {
      return this.ac;
   }

   public void j(boolean var1) {
      this.ac = var1;
   }

   public boolean ah() {
      return this.ad;
   }

   public void k(boolean var1) {
      this.ad = var1;
   }

   public boolean ai() {
      return this.ae;
   }

   public void l(boolean var1) {
      this.ae = var1;
   }

   public L1MobGroupInfo aj() {
      return this.ag;
   }

   public void a(L1MobGroupInfo var1) {
      this.ag = var1;
   }

   public int ak() {
      return this.ah;
   }

   public void w(int var1) {
      this.ah = var1;
   }

   public int al() {
      return this.ai;
   }

   public void x(int var1) {
      this.ai = var1;
   }

   public int am() {
      return this.aj;
   }

   public void b_(int var1) {
      this.aj = var1;
   }

   public int an() {
      return this.ak;
   }

   public int ao() {
      return this.al;
   }

   public String ap() {
      return this.am;
   }

   public void b(String var1) {
      this.am = var1;
   }

   public int aq() {
      return this.an;
   }

   public void m(boolean var1) {
      this.ao = var1;
   }

   public boolean ar() {
      return this.ao;
   }

   public void n(boolean var1) {
      this.ap = var1;
   }

   public boolean as() {
      return this.ap;
   }

   private class a extends TimerTask {
      private a() {
      }

      @Override
      public void run() {
         if (!L1NpcInstance.this.ah()) {
            L1NpcInstance.this.aa_();
         }
      }

      // $VF: synthetic method
      a(L1NpcInstance.a var2) {
         this();
      }
   }

   private class b extends TimerTask {
      private b() {
      }

      @Override
      public void run() {
         if (L1NpcInstance.this.eX() && !L1NpcInstance.this.ah()) {
            L1NpcInstance.this.aa_();
         }
      }

      // $VF: synthetic method
      b(L1NpcInstance.b var2) {
         this();
      }
   }

   private class c implements Runnable {
      private c() {
      }

      @Override
      public void run() {
         try {
            while (!L1NpcInstance.this.H.isEmpty()) {
               Thread.sleep(1000L);
               if (!L1NpcInstance.this.ah()) {
                  for (int var1 : L1NpcInstance.this.H.keySet()) {
                     int var3 = L1NpcInstance.this.H.get(var1) - 1;
                     if (var3 > 0) {
                        L1NpcInstance.this.H.put(var1, var3);
                     } else {
                        L1NpcInstance.this.H.remove(var1);
                        L1ItemInstance var4 = L1NpcInstance.this.o.e(var1);
                        if (var4 != null) {
                           L1NpcInstance.this.o.f(var4);
                        }
                     }
                  }
                  continue;
               }
               break;
            }
         } catch (Exception var8) {
            L1NpcInstance.y.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
         } finally {
            L1NpcInstance.this.I = false;
         }
      }

      // $VF: synthetic method
      c(L1NpcInstance.c var2) {
         this();
      }
   }

   private class d extends TimerTask {
      private final int b;

      @Override
      public void run() {
         try {
            if (L1NpcInstance.this.ah() || L1NpcInstance.this.eX()) {
               L1NpcInstance.this.v();
               return;
            }

            if (L1NpcInstance.this.ea() <= 0 || L1NpcInstance.this.ea() >= L1NpcInstance.this.ew()) {
               L1NpcInstance.this.v();
               return;
            }

            L1NpcInstance.this.a(L1NpcInstance.this.ea() + this.b);
         } catch (Exception var2) {
            L1NpcInstance.y.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }

      public d(int var2) {
         this.b = var2;
      }
   }

   private class e extends TimerTask {
      private final int b;

      @Override
      public void run() {
         try {
            if (L1NpcInstance.this.ah() || L1NpcInstance.this.eX()) {
               L1NpcInstance.this.x();
               return;
            }

            if (L1NpcInstance.this.eb() <= 0 || L1NpcInstance.this.eb() >= L1NpcInstance.this.ex()) {
               L1NpcInstance.this.x();
               return;
            }

            L1NpcInstance.this.i_(L1NpcInstance.this.eb() + this.b);
         } catch (Exception var2) {
            L1NpcInstance.y.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }

      private e(int var2) {
         this.b = var2;
      }

      // $VF: synthetic method
      e(int var2, L1NpcInstance.e var3) {
         this(var2);
      }
   }

   private class f extends TimerTask {
      private f() {
      }

      private void b() {
         L1NpcInstance.this.an = 0;
         L1NpcInstance.this.z = false;
         if (!L1NpcInstance.this.ag()) {
            L1NpcInstance.this.t();
         }

         L1NpcInstance.this.i(false);
      }

      public void a() {
         L1NpcInstance.this.i(true);
         GeneralThreadPool.a().a(this, 0L);
      }

      private void a(int var1) {
         GeneralThreadPool.a().a(L1NpcInstance.this.new f(), var1);
      }

      @Override
      public void run() {
         try {
            if (this.c()) {
               this.b();
               return;
            }

            if (L1NpcInstance.this.E > 0) {
               this.a(L1NpcInstance.this.E);
               L1NpcInstance.this.E = 0;
               return;
            }

            if (L1NpcInstance.this.ed() || L1NpcInstance.this.ec()) {
               this.a(200);
               return;
            }

            if (!L1NpcInstance.this.h()) {
               this.a(L1NpcInstance.this.af());
               return;
            }

            this.b();
         } catch (Exception var2) {
            L1NpcInstance.y.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }

      private boolean c() {
         return L1NpcInstance.this.ah() || L1NpcInstance.this.eX() || L1NpcInstance.this.ea() <= 0 || L1NpcInstance.this.ac() != 0;
      }

      // $VF: synthetic method
      f(L1NpcInstance.f var2) {
         this();
      }
   }

   private class g extends TimerTask {
      private g() {
      }

      @Override
      public void run() {
         L1NpcInstance.this.l(false);
      }

      // $VF: synthetic method
      g(L1NpcInstance.g var2) {
         this();
      }
   }
}
