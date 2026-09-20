package l1r.ap;

import l1r.aq.L1Character;
import l1r.aq.L1World;
import l1r.be.S_DoActionGFX;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Point;

public class L1GuardInstance extends L1NpcInstance {
   @Override
   public void c() {
      L1PcInstance var1 = null;

      for (L1PcInstance var2 : L1World.a().f(this)) {
         if (var2.ea() > 0 && !var2.eX() && !var2.l() && !var2.bN() && (!var2.ff() || this.V()) && var2.S()) {
            var1 = var2;
            break;
         }
      }

      if (var1 != null) {
         this.n.a(var1, 0);
         this.m = var1;
      }
   }

   public void d(L1PcInstance var1) {
      if (var1 != null) {
         this.n.a(var1, 0);
         this.m = var1;
      }
   }

   @Override
   public boolean a() {
      if (this.fu().c(new Point(this.X(), this.Y())) > 0) {
         int var1 = this.a(this.X(), this.Y());
         if (var1 != -1) {
            this.g(var1);
            this.v(this.f(this.N(), 0));
         } else {
            this.a(this.X(), this.Y(), 1);
         }
      } else if (L1World.a().f(this).isEmpty()) {
         return true;
      }

      return false;
   }

   public L1GuardInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void Z_() {
      if (!this.ae()) {
         this.w = false;
         this.q();
      }
   }

   @Override
   public void a(L1PcInstance var1, int var2) {
      if (this.ea() > 0 && !this.eX()) {
         super.a(var1, var2);
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 != null && this.n.b()) {
         this.n.a(var1, 0);
         this.d();
      }
   }

   @Override
   public void b(L1Character var1, int var2) {
      if (this.ea() != 0 || this.eX()) {
         if (this.ea() > 0 && !this.eX()) {
            if (var2 >= 0 && !(var1 instanceof L1EffectInstance)) {
               this.c((L1Character)var1, var2);
            }

            if (var2 > 0) {
               this.bz(66);
               this.bz(153);
            }

            this.Z_();
            if (var1 instanceof L1PcInstance && var2 > 0) {
               L1PcInstance var3 = (L1PcInstance)var1;
               var3.a(this);
               this.c(var3, this.U_().D());
            }

            int var4 = this.ea() - var2;
            if (var4 <= 0 && !this.eX()) {
               this.bx(0);
               this.X(true);
               this.cq(8);
               GeneralThreadPool.a().a(new L1GuardInstance.L1R_a(null));
            }

            if (var4 > 0) {
               this.a(var4);
            }
         } else if (!this.eX()) {
            this.X(true);
            this.cq(8);
            GeneralThreadPool.a().a(new L1GuardInstance.L1R_a(null));
         }
      }
   }

   @Override
   public void a(int var1) {
      int var2 = var1;
      if (var2 >= this.ew()) {
         var2 = this.ew();
      }

      this.bx(var2);
      if (this.ew() > this.ea()) {
         this.u();
      }
   }

   private class L1R_a implements Runnable {
      private L1R_a() {
      }

      @Override
      public void run() {
         L1GuardInstance.this.j(true);
         L1GuardInstance.this.bx(0);
         L1GuardInstance.this.X(true);
         L1GuardInstance.this.cq(8);
         L1GuardInstance.this.fq().a(L1GuardInstance.this.fu(), true);
         L1GuardInstance.this.b(new S_DoActionGFX(L1GuardInstance.this.fr(), 8));
         L1GuardInstance.this.a_(1);
         L1GuardInstance.this.j(false);
         L1GuardInstance.this.t();
         L1GuardInstance.this.A();
      }

      // $VF: synthetic method
      L1R_a(L1GuardInstance.L1R_a var2) {
         this();
      }
   }
}
