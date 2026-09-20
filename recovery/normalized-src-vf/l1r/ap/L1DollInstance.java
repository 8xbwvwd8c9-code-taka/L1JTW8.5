package l1r.ap;

import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.am.ListSprReader__obf_c;
import l1r.ao.ItemTable;
import l1r.aq.L1World;
import l1r.be.S_DoActionGFX;
import l1r.be.S_DollPack;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_PacketBox;
import l1r.be.S_SPMR;
import l1r.be.S_SkillHaste;
import l1r.be.S_SkillSound;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;

public class L1DollInstance extends L1NpcInstance {
   private static final Logger y = Logger.getLogger(L1DollInstance.class.getName());
   private static final int z = 1800000;
   private final L1ItemInstance A;
   private ScheduledFuture<?> B;
   private int C;
   private int D;

   @Override
   public boolean a() {
      if (this.k != null && !this.k.eX() && this.k.fp() == this.fp()) {
         if (this.fu().c(this.k.fu()) > 2) {
            int var1 = this.a(this.k.fs(), this.k.ft());
            this.g(var1);
            this.v(this.f(this.N(), 0));
         } else {
            this.i();
         }

         return false;
      } else {
         this.e();
         return true;
      }
   }

   public L1ItemInstance X_() {
      return this.A;
   }

   public L1DollInstance(L1Npc var1, L1PcInstance var2, L1ItemInstance var3) {
      super(var1);
      this.cF(IdFactory.a().c());
      this.A = var3;
      this.e_(this.A.N());
      this.d_(this.A.fr());
      GeneralThreadPool.a().a(new L1DollInstance.b(null), 1800000L);
      this.a(this.A.bq());
      this.cw(this.A.br());
      this.e(var2);
      this.cG(var2.fs() + Random.a(5) - 2);
      this.cH(var2.ft() + Random.a(5) - 2);
      this.cE(var2.fp());
      this.ct(5);
      this.s(var1.ae());
      this.cu(1);
      this.cv(1);
      L1World.a().a(this);
      L1World.a().c(this);

      for (L1PcInstance var4 : L1World.a().f(this)) {
         this.b(var4);
      }

      var2.b(this);
      if (!this.ae()) {
         this.q();
      }

      var2.cm(this.A.as());
      var2.cn(this.A.at());
      var2.cl(this.A.av());
      var2.bL(-this.A.ad());
      var2.co(this.A.af());
      var2.cp(this.A.ae());
      var2.bH(this.A.ab());
      var2.bJ(this.A.ac());
      var2.bN(this.A.ai());
      var2.bP(this.A.ak());
      var2.bR(this.A.aj());
      var2.bT(this.A.an());
      var2.bV(this.A.al());
      var2.bX(this.A.am());
      var2.ce(this.A.aA());
      var2.cd(this.A.aF());
      var2.ch(this.A.aE());
      var2.ci(this.A.aD());
      var2.cg(this.A.aC());
      var2.cf(this.A.aB());
      var2.C(this.A.by());
      var2.L(this.A.bD());
      var2.M(this.A.bC());
      var2.ac(this.A.bE());
      var2.Y(this.A.aY());
      var2.Z(this.A.ba());
      if (this.A.au() > 0 && this.A.bs() == 0) {
         var2.H(this.A.au());
      }

      if (this.A.ap() > 0 && this.A.bx() == 0) {
         var2.F(this.A.ap());
      }

      if (this.A.bz() > 0) {
         this.B = GeneralThreadPool.a().a(new L1DollInstance.a(null), 240000L, 240000L);
      }

      var2.aW(this.A.bu());
      var2.aX(this.A.bv());
      var2.aY(this.A.bw());
      var2.u(this.A.bB());
      if (this.A.bA()) {
         var2.E(1);
         var2.V();
         if (var2.fc() != 1) {
            var2.cu(1);
            var2.a(new S_SkillHaste(var2.fr(), 1, -1));
            var2.b(new S_SkillHaste(var2.fr(), 1, -1));
         }
      }

      if (this.A.ae() > 0 || this.A.af() > 0) {
         var2.a(new S_SPMR(var2));
      }

      this.A.d(true);
      var2.j().b(this.A);
   }

   public void e() {
      this.b(new S_SkillSound(this.fr(), 5936));
      if (this.k != null) {
         L1PcInstance var1 = (L1PcInstance)this.k;
         var1.cm(-this.A.as());
         var1.cn(-this.A.at());
         var1.cl(-this.A.av());
         var1.bL(this.A.ad());
         var1.co(-this.A.af());
         var1.cp(-this.A.ae());
         var1.bH(-this.A.ab());
         var1.bJ(-this.A.ac());
         var1.bN(-this.A.ai());
         var1.bP(-this.A.ak());
         var1.bR(-this.A.aj());
         var1.bT(-this.A.an());
         var1.bV(-this.A.al());
         var1.bX(-this.A.am());
         var1.ce(-this.A.aA());
         var1.cd(-this.A.aF());
         var1.ch(-this.A.aE());
         var1.ci(-this.A.aD());
         var1.cg(-this.A.aC());
         var1.cf(-this.A.aB());
         var1.C(-this.A.by());
         var1.L(-this.A.bD());
         var1.M(-this.A.bC());
         var1.ac(-this.A.bE());
         var1.Y(-this.A.aY());
         var1.Z(-this.A.ba());
         if (this.A.au() > 0 && this.A.bs() == 0) {
            var1.H(-this.A.au());
         }

         if (this.A.ap() > 0 && this.A.bx() == 0) {
            var1.F(-this.A.ap());
         }

         if (this.B != null) {
            this.B.cancel(true);
         }

         var1.aW(0);
         var1.aX(0);
         var1.aY(0);
         var1.u(false);
         if (this.A.bA()) {
            var1.E(-1);
            if (var1.bU() == 0) {
               var1.cu(0);
               var1.a(new S_SkillHaste(var1.fr(), 0, 0));
               var1.b(new S_SkillHaste(var1.fr(), 0, 0));
            }
         }

         var1.a(new S_PacketBox(56, 0));
         var1.a(new S_OwnCharStatus(var1));
         if (this.A.ae() > 0 || this.A.af() > 0) {
            var1.a(new S_SPMR(var1));
         }

         this.k.el().remove(this.fr());
         if (this.A != null) {
            this.A.d(false);
            var1.j().b(this.A);
         }
      }

      this.aa_();
   }

   @Override
   public void b(L1PcInstance var1) {
      var1.c(this);
      var1.a(new S_DollPack(this));
   }

   @Override
   public void a(L1ItemInstance var1) {
   }

   public int f() {
      return this.C;
   }

   public void d_(int var1) {
      this.C = var1;
   }

   public int g() {
      return this.D;
   }

   public void e_(int var1) {
      this.D = var1;
   }

   private void i() {
      int var1 = Random.a(100) + 1;
      if (var1 <= 10) {
         int[] var2 = new int[]{67, 68, 69, 98, 99};
         int var3 = var1 <= 5 ? 66 : 67;
         int var4 = var2[Random.a(var2.length)];
         if (ListSprReader__obf_c.a().b(this.fe(), var4)) {
            var3 = var4;
         }

         this.b(new S_DoActionGFX(this.fr(), var3));
         this.v(this.f(ListSprReader__obf_c.a().a(this.fe(), var3), 0));
      }
   }

   private class a extends TimerTask {
      private a() {
      }

      @Override
      public void run() {
         try {
            ItemTable.a((L1PcInstance)L1DollInstance.this.M(), L1DollInstance.this.A.bz(), 1);
         } catch (Throwable var2) {
            L1DollInstance.y.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }

      // $VF: synthetic method
      a(L1DollInstance.a var2) {
         this();
      }
   }

   private class b implements Runnable {
      private b() {
      }

      @Override
      public void run() {
         if (!L1DollInstance.this.ah()) {
            L1DollInstance.this.e();
         }
      }

      // $VF: synthetic method
      b(L1DollInstance.b var2) {
         this();
      }
   }
}
