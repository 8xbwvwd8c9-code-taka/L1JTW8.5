package l1r.ap;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.DropTable;
import l1r.ao.ItemTable;
import l1r.aq.L1Attack;
import l1r.aq.L1Character;
import l1r.aq.L1World;
import l1r.be.S_DoActionGFX;
import l1r.be.S_NpcChatPacket;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Npc;
import l1r.bi.CalcExp;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class L1GuardianInstance extends L1NpcInstance {
   private static final Logger y = Logger.getLogger(L1GuardianInstance.class.getName());
   private final L1GuardianInstance z = this;
   private boolean A = false;
   private L1Character B;

   public L1GuardianInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void c() {
      L1PcInstance var1 = null;

      for (L1PcInstance var2 : L1World.a().f(this)) {
         if (var2.ea() > 0 && !var2.eX() && !var2.l() && !var2.bN() && (!var2.ff() || this.V())) {
            if (!var2.A()) {
               var1 = var2;
               this.d(new S_NpcChatPacket(this, "$804", 2));
               break;
            }

            if (var2.A() && var2.T()) {
               var1 = var2;
               this.d(new S_NpcChatPacket(this, "$815", 1));
               break;
            }
         }
      }

      if (var1 != null) {
         this.n.a(var1, 0);
         this.m = var1;
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
   public void Z_() {
      if (!this.ae()) {
         if (!this.A && this.o.c() == 0) {
            if (this.z() == 70848) {
               this.o.a(40506, 6);
               this.o.a(40507, 36);
            } else if (this.z() == 70850) {
               this.o.a(40519, 18);
            }
         }

         this.w = false;
         this.q();
      }
   }

   @Override
   public void a(L1PcInstance var1, int var2) {
      if (this.ea() > 0 && !this.eX()) {
         L1Attack var3 = new L1Attack(var1, this, var2);
         if (var3.a()) {
            int var4 = var3.b();
            if (var1.A() && (var1.k() == 0 || var4 <= 3 || var1.v() != null && (var1.v().N() == 5 || var1.v().N() == 100005))) {
               if (this.o.c() > 0) {
                  for (L1ItemInstance var5 : this.o.d()) {
                     if (this.z() == 70848 && var5.N() == 40499) {
                        this.o.f(var5);
                        ItemTable.a(var1, 40505, var5.E(), this.T());
                        break;
                     }

                     if (this.z() == 70846 && var5.N() == 40507) {
                        this.o.f(var5);
                        ItemTable.a(var1, 40503, var5.E(), this.T());
                        break;
                     }

                     if (Random.a(100) < 30) {
                        this.o.a(var5, 1, var1.j());
                        var1.a(new S_ServerMessage(143, this.T(), var5.b()));
                        break;
                     }
                  }
               } else {
                  if (this.z() == 70848) {
                     this.b(new S_NpcChatPacket(this.z, "$822", 0));
                  } else if (this.z() == 70846) {
                     this.b(new S_NpcChatPacket(this.z, "$823", 0));
                  } else if (this.z() == 70850) {
                     this.b(new S_NpcChatPacket(this.z, "$824", 0));
                  }

                  if (!this.A) {
                     new L1GuardianInstance.L1R_a(null).a();
                  }
               }
            }

            var3.a(var1, this);
         }

         var3.c();
         var3.d();
      }
   }

   @Override
   public void b(L1Character var1, int var2) {
      if (var1 instanceof L1PcInstance && var2 > 0) {
         L1PcInstance var3 = (L1PcInstance)var1;
         if (var3.A() && (var3.k() == 0 || var2 <= 3 || var3.v() != null && (var3.v().N() == 5 || var3.v().N() == 100005))) {
            return;
         }

         if (this.ea() > 0 && !this.eX()) {
            if (var2 >= 0) {
               this.c((L1Character)var1, var2);
            }

            if (var2 > 0) {
               this.bz(66);
               this.bz(153);
            }

            this.Z_();
            this.c(var3, this.U_().D());
            if (var2 > 0) {
               var3.a(this);
            }

            int var6 = this.ea() - var2;
            if (var6 <= 0 && !this.eX()) {
               this.bx(0);
               this.X(true);
               this.cq(8);
               this.B = var1;
               L1GuardianInstance.L1R_b var5 = new L1GuardianInstance.L1R_b(null);
               GeneralThreadPool.a().a(var5);
            }

            if (var6 > 0) {
               this.a(var6);
            }
         } else if (!this.eX()) {
            this.X(true);
            this.cq(8);
            this.B = var1;
            L1GuardianInstance.L1R_b var4 = new L1GuardianInstance.L1R_b(null);
            GeneralThreadPool.a().a(var4);
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

   @Override
   public void i_(int var1) {
      int var2 = var1;
      if (var2 >= this.ex()) {
         var2 = this.ex();
      }

      this.by(var2);
      if (this.ex() > this.eb()) {
         this.w();
      }
   }

   private class L1R_a implements Runnable {
      private L1R_a() {
      }

      public void a() {
         L1GuardianInstance.this.A = true;
         GeneralThreadPool.a().a(this, Config.av * 1000);
      }

      @Override
      public void run() {
         try {
            if (L1GuardianInstance.this.z() == 70848) {
               L1GuardianInstance.this.o.a(Random.a(100) < 10 ? 40506 : 40507, 1);
            } else {
               if (L1GuardianInstance.this.z() != 70850) {
                  return;
               }

               L1GuardianInstance.this.o.a(40519, 1);
            }

            int var1 = 0;

            for (L1ItemInstance var2 : L1GuardianInstance.this.o.d()) {
               var1 += var2.E();
            }

            if (var1 < 30) {
               this.a();
               return;
            }

            L1GuardianInstance.this.A = false;
         } catch (Exception var4) {
            L1GuardianInstance.y.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
         }
      }

      // $VF: synthetic method
      L1R_a(L1GuardianInstance.L1R_a var2) {
         this();
      }
   }

   private class L1R_b implements Runnable {
      L1Character a = L1GuardianInstance.this.B;

      private L1R_b() {
      }

      @Override
      public void run() {
         L1GuardianInstance.this.j(true);
         L1GuardianInstance.this.bx(0);
         L1GuardianInstance.this.X(true);
         L1GuardianInstance.this.cq(8);
         int var1 = L1GuardianInstance.this.fr();
         L1GuardianInstance.this.fq().a(L1GuardianInstance.this.fu(), true);
         L1GuardianInstance.this.b(new S_DoActionGFX(var1, 8));
         L1PcInstance var2 = null;
         if (this.a instanceof L1PcInstance) {
            var2 = (L1PcInstance)this.a;
         } else if (this.a instanceof L1PetInstance) {
            var2 = (L1PcInstance)((L1PetInstance)this.a).M();
         } else if (this.a instanceof L1SummonInstance) {
            var2 = (L1PcInstance)((L1SummonInstance)this.a).M();
         }

         if (var2 != null) {
            CalcExp.a(var2, L1GuardianInstance.this, L1GuardianInstance.this.n);

            try {
               DropTable.a().a(L1GuardianInstance.this.z, L1GuardianInstance.this.l);
            } catch (Exception var4) {
               L1GuardianInstance.y.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
            }

            var2.B((int)(L1GuardianInstance.this.P() * Config.D));
         }

         L1GuardianInstance.this.j(false);
         L1GuardianInstance.this.A(0);
         L1GuardianInstance.this.k(0);
         L1GuardianInstance.this.t();
         L1GuardianInstance.this.A();
      }

      // $VF: synthetic method
      L1R_b(L1GuardianInstance.L1R_b var2) {
         this();
      }
   }
}
