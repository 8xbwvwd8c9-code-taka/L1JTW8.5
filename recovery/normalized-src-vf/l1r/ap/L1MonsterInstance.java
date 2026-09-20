package l1r.ap;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.am.ListSprReader__obf_c;
import l1r.am.MonsterListReader;
import l1r.ao.DoorTable;
import l1r.ao.DropTable;
import l1r.ao.ItemTable;
import l1r.ao.SpawnTable;
import l1r.aq.L1Character;
import l1r.aq.L1GuardianSoul;
import l1r.aq.L1ItemQuestBuff;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.as.L1Dragon;
import l1r.as.L1ThebesBattle;
import l1r.be.S_ChangeName;
import l1r.be.S_CharVisualUpdate;
import l1r.be.S_DoActionGFX;
import l1r.be.S_Liquor;
import l1r.be.S_NPCPack;
import l1r.be.S_NpcChangeShape;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.bf.S_048;
import l1r.bf.S_057;
import l1r.bf.S_079;
import l1r.bf.S_150;
import l1r.bf.S_211;
import l1r.bh.L1Npc;
import l1r.bh.L1QuestNew;
import l1r.bi.CalcExp;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class L1MonsterInstance extends L1NpcInstance {
   private static final Logger y = Logger.getLogger(L1MonsterInstance.class.getName());
   private boolean z = false;
   private boolean A = false;
   private final Object B = new Object();
   private boolean C = false;

   public L1MonsterInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void Y_() {
      if (!this.w && this.m != null) {
         this.e(1, 40);
         this.a(true);
      }

      if (this.ea() * 100 / this.ew() < 40) {
         this.e(0, 50);
      }
   }

   @Override
   public void a(boolean var1) {
      if (this.U_().X()) {
         boolean var2 = false;
         if (!var1) {
            this.a(this.U_().A());
            this.cr(this.U_().p());
            this.cw(this.G());
            var2 = true;
         } else if (!this.z && this.m instanceof L1PcInstance) {
            this.v(300);
            L1PcInstance var3 = (L1PcInstance)this.m;
            this.e(var3.et());
            this.a(var3.et());
            this.cr(var3.fa());
            this.cw(var3.aB());
            if (var3.aB() != 6671 && var3.aB() != 48) {
               this.cq(4);
            } else {
               this.cq(11);
            }

            this.z = true;
            var2 = true;
         }

         this.m(ListSprReader__obf_c.a().a(this.fe(), this.eY()));
         this.n(ListSprReader__obf_c.a().a(this.fe(), this.eY() + 1));
         if (var2) {
            this.b(new S_ChangeName(this.fr(), this.T()));
            this.b(new S_NpcChangeShape(this.fr(), this.fe(), this.fa(), this.eY()));
         }
      }
   }

   @Override
   public void b(L1PcInstance var1) {
      if (this.ea() > 0 || this.fe() != 7864 && this.fe() != 7869) {
         var1.a(new S_NPCPack(this));
         var1.c(this);
         this.Z_();
      }
   }

   @Override
   public void c() {
      L1PcInstance var1 = null;
      if (this.m instanceof L1PcInstance) {
         var1 = (L1PcInstance)this.m;
         this.s();
      }

      L1PcInstance var2 = null;

      for (L1PcInstance var3 : L1World.a().f(this)) {
         if (var3 != var1 && var3.ea() > 0 && !var3.eX() && !var3.l() && !var3.bN()) {
            if (this.z() != 45600 || !var3.x() && !var3.C() && var3.fe() == var3.aB()) {
               if (this.U_().aa() < 0 && var3.Q() >= 1
                  || this.U_().aa() > 0 && var3.Q() <= -1
                  || var3.fe() == 6034 && this.U_().aa() < 0
                  || var3.fe() == 6035 && this.U_().aa() > 0
                  || var3.fe() == 6035 && this.U_().b() == 46070
                  || var3.fe() == 6035 && this.U_().b() == 46072) {
                  continue;
               }

               if (!this.V_() && !this.W() && this.U_().F() < 0 && this.U_().G() < 0) {
                  if (var3.fa() >= -1000) {
                     continue;
                  }

                  var2 = var3;
                  break;
               }

               if (var3.ff() && !this.V()) {
                  continue;
               }

               if (var3.bB(67)) {
                  if (this.W()) {
                     var2 = var3;
                     break;
                  }
               } else if (this.V_()) {
                  var2 = var3;
                  break;
               }

               if (var3.fe() != this.U_().F() && var3.fe() != this.U_().G()) {
                  continue;
               }

               var2 = var3;
               break;
            }

            var2 = var3;
            break;
         }
      }

      if (var2 != null) {
         this.n.a(var2, 0);
         this.m = var2;
      }
   }

   @Override
   public void a(L1Character var1) {
      if (var1 != null) {
         if (this.n.b()) {
            this.n.a(var1, 0);
            this.d();
         }
      }
   }

   @Override
   public void Z_() {
      if (!this.ae()) {
         if (!this.A) {
            DropTable.a().a(this, this.y());
            this.y().f();
            this.A = true;
         }

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
   public void a(L1Character var1, int var2) {
      if (var2 > 0 && !this.eX()) {
         this.c((L1Character)var1, var2);
         this.Z_();
         if (var1 instanceof L1PcInstance) {
            this.c((L1PcInstance)var1, this.U_().D());
         }

         int var3 = this.eb() - var2;
         if (var3 < 0) {
            var3 = 0;
         }

         this.i_(var3);
      }
   }

   @Override
   public void b(L1Character var1, int var2) {
      if (this.ea() <= 0) {
         if (!this.eX()) {
            this.cq(8);
            GeneralThreadPool.a().a(new L1MonsterInstance.L1R_a(var1));
         }
      } else if (this.ac() != 1 && this.ac() != 2) {
         if (var2 >= 0) {
            if (!(var1 instanceof L1EffectInstance)) {
               this.c((L1Character)var1, var2);
            }

            this.bz(66);
            this.bz(153);
         }

         this.Z_();
         if (var1 instanceof L1PcInstance) {
            if (var2 > 0) {
               ((L1PcInstance)var1).a(this);
            }

            this.c((L1PcInstance)var1, this.U_().D());
         }

         if (this.z() >= 97044 && this.z() <= 97046 && var1.bB(4011)) {
            var2 = (int)(var2 * 1.5);
         } else if (this.z() >= 97094 && this.z() <= 97096 && var1.bB(4012)) {
            var2 = (int)(var2 * 1.5);
         }

         int var3 = this.ea() - var2;
         if (var3 > 0) {
            this.a(var3);
            this.l();
         } else {
            int var4 = this.U_().ab();
            if (var4 != -1 && (this.fp() != 1931 || Random.a(1000) <= 3)) {
               this.g_(var4);
            } else {
               GeneralThreadPool.a().a(new L1MonsterInstance.L1R_a(var1));
            }
         }
      }
   }

   @Override
   public synchronized void a(int var1) {
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
   public synchronized void i_(int var1) {
      int var2 = var1;
      if (var2 >= this.ex()) {
         var2 = this.ex();
      }

      this.by(var2);
      if (this.ex() > this.eb()) {
         this.w();
      }
   }

   private void f(L1Character var1) {
      if (var1 != null) {
         if (var1 instanceof L1EffectInstance && !this.n.b()) {
            var1 = this.n.c();
         }

         L1PcInstance var2 = null;
         if (var1 instanceof L1PcInstance) {
            var2 = (L1PcInstance)var1;
         } else if (var1 instanceof L1PetInstance) {
            var2 = (L1PcInstance)((L1PetInstance)var1).M();
         } else if (var1 instanceof L1SummonInstance) {
            var2 = (L1PcInstance)((L1SummonInstance)var1).M();
         }

         if (var2 != null) {
            CalcExp.a(var2, this, this.n);
            this.k();
            this.f(var2);
            this.g(var2);
         }
      }
   }

   private void k() {
      int var1 = this.U_().b();
      if (var1 != 45640 || var1 == 45640 && this.fe() == 2332) {
         L1GuardianSoul.a().a(this);
         DropTable.a().a(this, this.l);
      }

      if (var1 == 190026) {
         for (L1Object var2 : L1World.a().b(this.fp()).values()) {
            if (var2 instanceof L1PcInstance) {
               L1PcInstance var4 = (L1PcInstance)var2;
               if (this.l.a(var4) && !var4.eX()) {
                  ItemTable.a(var4, 640305, 1, this.T());
               }
            }
         }
      } else if (var1 != 97008 && var1 != 97046 && var1 != 97096) {
         if (var1 == 46123 || var1 == 46124 || var1 == 190577) {
            for (L1Object var7 : L1World.a().b(this.fp()).values()) {
               if (var7 instanceof L1PcInstance) {
                  L1PcInstance var11 = (L1PcInstance)var7;
                  if (this.l.a(var11)) {
                     ItemTable.a(var11, 40308, 5000000, this.T());
                     ItemTable.a(var11, 640621, 50000, this.T());
                  }
               }
            }
         }
      } else {
         int var6 = 640639;
         if (var1 == 97046) {
            var6 = 640640;
         } else if (var1 == 97096) {
            var6 = 640641;
         }

         for (L1Object var8 : L1World.a().b(this.fp()).values()) {
            if (var8 instanceof L1PcInstance) {
               L1PcInstance var5 = (L1PcInstance)var8;
               if (this.l.a(var5) && !var5.eX()) {
                  ItemTable.a(var5, var6, 1, this.T());
               }
            }
         }
      }
   }

   private void f(L1PcInstance var1) {
      int var2 = this.P();
      if (var2 != 0) {
         int var3 = Integer.signum(var2);
         int var4 = var1.Q();
         int var5 = Integer.signum(var4);
         if (var5 != 0 && var3 != var5) {
            var2 *= 5;
         }

         var1.B((int)(var2 * Config.D));
      }
   }

   private void g(L1PcInstance var1) {
      if (var1.bB(5006)) {
         if (var1.dW() < 30) {
            var1.bv(var1.dW() + 1);
         }

         var1.a(new S_PacketBox(204, var1));
      } else if (var1.cC() > 0) {
         int var2 = Random.a(1000);
         if (var2 < 10) {
            var1.bv(1);
            var1.j(5006, 50000);
            var1.a(new S_PacketBox(204, var1));
         }
      }

      if (this.U_().an() > 0) {
         int var12 = this.U_().an() - 1;
         int var3 = var1.dQ()[var12] + 1;
         int var4 = MonsterListReader.a().a(var12, var1.dQ()[var12]);
         var1.dQ()[var12] = var3;
         var1.a(new S_ProtoBuffers(567, this.U_().an(), var3));
         int var5 = MonsterListReader.a().a(var12, var3);
         if (var5 > var4) {
            int var6 = var12 * 3 + var5 - 1;
            var1.a(new S_ProtoBuffers(568, var6 + 1, var1.fr()));
         }

         if (var1.dY() != null) {
            for (int var16 = 0; var16 < var1.dY().length; var16++) {
               int[] var7 = var1.dY()[var16];
               if (var7[0] == this.U_().an()) {
                  if (var7[2] < var7[1]) {
                     var7[2]++;
                     var1.a(new S_ProtoBuffers(813, var16 / 3, var16 % 3, var7[2]));
                     if (var7[2] >= var7[1]) {
                        boolean var8 = true;
                        int var9 = var16 / 3;

                        for (int var10 = 0; var10 < 3; var10++) {
                           int[] var11 = var1.dY()[var9 * 3 + var10];
                           if (var11[2] < var11[1]) {
                              var8 = false;
                           }
                        }

                        if (var8) {
                           var1.dY()[var9 * 3][3] = 3;
                           var1.a(new S_ProtoBuffers(814, var16 / 3, 3));
                        }
                     }
                  }
                  break;
               }
            }
         }
      }

      for (L1QuestNew var13 : var1.dS().values()) {
         for (int var15 = 0; var15 < var13.p().length; var15++) {
            if (var13.p()[var15] == this.z()) {
               var13.b(var15);
            }
         }
      }
   }

   public void d(L1PcInstance var1) {
      if (!var1.bB(60) && !var1.bB(97)) {
         if (this.ac() == 1) {
            if (this.ea() == this.ew() && var1.fu().c(this.fu()) <= 2) {
               this.e(var1);
            }
         } else if (this.ac() == 2) {
            if (this.ea() == this.ew()) {
               if (var1.fu().c(this.fu()) <= 1) {
                  this.e(var1);
               }
            } else {
               this.r();
            }
         } else if (this.ac() == 3 && this.ea() < this.ew()) {
            this.e(var1);
         }
      }
   }

   public void e(L1PcInstance var1) {
      int var2 = this.ac();
      if (var2 == 1) {
         if (ListSprReader__obf_c.a().c(this.fe())) {
            this.b(new S_DoActionGFX(this.fr(), 11));
         } else {
            this.b(new S_DoActionGFX(this.fr(), 4));
         }
      } else if (var2 == 2) {
         this.b(new S_DoActionGFX(this.fr(), 45));
      } else if (var2 == 3) {
         this.b(new S_DoActionGFX(this.fr(), 11));
      }

      this.t(0);
      this.cq(ListSprReader__obf_c.a().a(this));
      this.b(new S_CharVisualUpdate(this, this.eY()));
      if (!var1.bB(60) && !var1.bB(97) && !var1.l()) {
         this.n.a(var1, 0);
         this.m = var1;
      }

      this.Z_();
      this.a_(2);
   }

   @Override
   public void b(boolean var1) {
      if (this.fe() == 7548 || this.fe() == 7550 || this.fe() == 7552 || this.fe() == 7554 || this.fe() == 7585 || this.fe() == 7591) {
         for (L1PcInstance var6 : L1World.a().f(this)) {
            if (!var6.b(this)) {
               var6.c(this);
            }
         }

         this.b(new S_NPCPack(this));
         this.b(new S_DoActionGFX(this.fr(), 11));
      } else if (this.fe() == 7539
         || this.fe() == 7557
         || this.fe() == 7558
         || this.fe() == 7864
         || this.fe() == 7869
         || this.fe() == 7870
         || this.fe() == 8036
         || this.fe() == 8054
         || this.fe() == 8055) {
         for (L1PcInstance var5 : L1World.a().f(this)) {
            if (!var5.b(this)) {
               var5.c(this);
            }
         }

         this.cq(4);
         this.b(new S_NPCPack(this));
         this.b(new S_DoActionGFX(this.fr(), 11));
         this.b(11, 1);
         this.cq(0);
         this.b(new S_CharVisualUpdate(this, this.eY()));
      } else if (ListSprReader__obf_c.a().b(this.fe()) && this.ac() != 1) {
         for (L1PcInstance var4 : L1World.a().f(this)) {
            if (!var4.b(this)) {
               var4.c(this);
            }
         }

         this.m(var1);
         this.cq(11);
         this.b(new S_NPCPack(this));
         this.b(new S_DoActionGFX(this.fr(), 4));
         this.b(4, 1);
         this.cq(0);
         this.b(new S_CharVisualUpdate(this, this.eY()));
      } else if (this.fe() == 14036 || this.fe() == 14271) {
         this.cq(0);
      } else if (this.fe() == 10071) {
         for (L1PcInstance var2 : L1World.a().f(this)) {
            if (!var2.b(this)) {
               var2.c(this);
            }
         }

         this.cq(11);
         this.b(new S_NPCPack(this));
         this.b(new S_DoActionGFX(this.fr(), 4));
         this.b(4, 1);
         this.cq(4);
         this.b(new S_CharVisualUpdate(this, this.eY()));
      }

      if (var1) {
         this.Z_();
      }
   }

   private void l() {
      int var1 = this.U_().b();
      if (ListSprReader__obf_c.a().b(this.fe())) {
         if (this.ew() / 3 > this.ea()) {
            int var2 = Random.a(10);
            if (2 > var2) {
               this.t();
               this.t(1);
               this.b(new S_DoActionGFX(this.fr(), 11));
               this.cq(11);
               this.b(new S_CharVisualUpdate(this, this.eY()));
            }
         }
      } else if (this.fe() == 10071) {
         if (this.ew() / 3 > this.ea() && Random.a(100) < 2) {
            this.t();
            this.t(1);
            this.b(new S_DoActionGFX(this.fr(), 11));
            this.cq(11);
            this.b(new S_CharVisualUpdate(this, this.eY()));
         }
      } else if (this.fe() == 7558) {
         if (this.ew() / 3 > this.ea() && Random.a(100) < 1) {
            this.t();
            this.t(1);
            this.b(new S_DoActionGFX(this.fr(), 20));
            this.cq(20);
            this.b(new S_CharVisualUpdate(this, this.eY()));
         }
      } else if (ListSprReader__obf_c.a().a(this.fe())) {
         if (this.ew() / 3 > this.ea()) {
            int var3 = Random.a(10);
            if (2 > var3) {
               this.t();
               this.t(2);
               this.b(new S_DoActionGFX(this.fr(), 44));
            }
         }
      } else if ((var1 == 46107 || var1 == 46108) && this.ew() / 4 > this.ea()) {
         int var4 = Random.a(10);
         if (2 > var4) {
            this.t();
            this.t(1);
            this.b(new S_DoActionGFX(this.fr(), 11));
            this.cq(11);
            this.b(new S_CharVisualUpdate(this, this.eY()));
         }
      }
   }

   public void h() {
      int var1 = this.U_().b();
      if (ListSprReader__obf_c.a().b(this.fe()) && !this.ar()) {
         if (Random.a(100) < 33) {
            this.t(1);
         }
      } else if (ListSprReader__obf_c.a().c(this.fe())) {
         if (Random.a(100) < 33) {
            this.t(1);
            this.cq(4);
            return;
         }
      } else if (ListSprReader__obf_c.a().a(this.fe())) {
         this.t(2);
      } else if (this.fe() != 6555 && this.fe() != 6555) {
         if (var1 >= 46125 && var1 <= 46128) {
            this.t(3);
         } else if (ListSprReader__obf_c.a().d(this.fe())) {
            this.t(3);
         }
      } else if (Random.a(100) < 33) {
         this.t(1);
      }

      this.cq(ListSprReader__obf_c.a().a(this));
   }

   public void a(L1NpcInstance var1) {
      int var2 = this.U_().b();
      if (var1.ac() == 1) {
         if (ListSprReader__obf_c.a().b(this.fe())) {
            this.t(1);
         } else {
            if (ListSprReader__obf_c.a().c(this.fe())) {
               this.t(1);
               this.cq(4);
               return;
            }

            if (var2 == 46107 || var2 == 46108) {
               this.t(1);
            }
         }
      } else if (var1.ac() == 2) {
         if (ListSprReader__obf_c.a().a(this.fe())) {
            this.t(2);
         }
      } else if (var2 >= 46125 && var2 <= 46128) {
         this.t(3);
      } else if (ListSprReader__obf_c.a().d(this.fe())) {
         this.t(3);
      }

      this.cq(ListSprReader__obf_c.a().a(this));
   }

   @Override
   public void g_(int var1) {
      super.g_(var1);
      this.y().g();
      DropTable.a().a(this, this.y());
      this.y().f();
   }

   public boolean i() {
      return this.A;
   }

   public void c(boolean var1) {
      this.A = var1;
   }

   protected class L1R_a implements Runnable {
      private final L1Character b;

      public L1R_a(L1Character var2) {
         this.b = var2;
      }

      @Override
      public void run() {
         try {
            if (L1MonsterInstance.this.eX()) {
               return;
            }

            L1MonsterInstance.this.X(true);
            L1MonsterInstance.this.j(true);
            L1MonsterInstance.this.bx(0);
            L1MonsterInstance.this.a_(1);
            if (L1MonsterInstance.this.z() == 190044) {
               for (L1Object var11 : L1World.a().b(L1MonsterInstance.this, -1)) {
                  if (var11 instanceof L1MonsterInstance) {
                     L1MonsterInstance var15 = (L1MonsterInstance)var11;
                     var15.b(new S_DoActionGFX(var15.fr(), 2));
                     var15.b(this.b, 300);
                  }
               }

               L1MonsterInstance.this.b(new S_SkillSound(L1MonsterInstance.this.fr(), 7771));
            } else if (L1MonsterInstance.this.z() == 190078) {
               if (this.b instanceof L1PcInstance && this.b.fp() >= 807 && this.b.fp() <= 812) {
                  L1Teleport.a((L1PcInstance)this.b, 32769, 32764, this.b.fp() + 1, 5, true);
               }
            } else if (L1MonsterInstance.this.z() == 190079) {
               if (this.b instanceof L1PcInstance) {
                  L1Teleport.a((L1PcInstance)this.b, 200);
               }
            } else if (L1MonsterInstance.this.z() == 46142 && L1MonsterInstance.this.fp() >= 2101 && L1MonsterInstance.this.fp() <= 2150) {
               L1DoorInstance var10 = DoorTable.b().a(32852, 32920, L1MonsterInstance.this.fp());
               if (var10 != null) {
                  var10.f();
               }

               if (this.b instanceof L1PcInstance) {
                  ((L1PcInstance)this.b).a(new S_PacketBox(84, 2, "$13383"));
               }
            } else if (L1MonsterInstance.this.z() >= 190750 && L1MonsterInstance.this.z() <= 190762) {
               if (this.b instanceof L1PcInstance && Random.a(1000) < 3) {
                  L1Teleport.a((L1PcInstance)this.b, 33392, 32346, 4, 0, true);
               }
            } else if (L1MonsterInstance.this.z() == 97006
               || L1MonsterInstance.this.z() == 97007
               || L1MonsterInstance.this.z() == 97044
               || L1MonsterInstance.this.z() == 97045
               || L1MonsterInstance.this.z() == 97094
               || L1MonsterInstance.this.z() == 97095) {
               synchronized (L1MonsterInstance.this.B) {
                  if (!L1MonsterInstance.this.C) {
                     SpawnTable.a(L1MonsterInstance.this.z() + 1, L1MonsterInstance.this, 30000L);
                     L1MonsterInstance.this.C = true;
                  }
               }
            } else if (L1MonsterInstance.this.z() == 97008 || L1MonsterInstance.this.z() == 97046 || L1MonsterInstance.this.z() == 97096) {
               int var9 = 4011;
               if (L1MonsterInstance.this.z() == 97046) {
                  var9 = 4012;
               } else if (L1MonsterInstance.this.z() == 97096) {
                  var9 = 4077;
               }

               for (L1PcInstance var13 : L1World.a().c(L1MonsterInstance.this, 100)) {
                  Timestamp var4 = new Timestamp(System.currentTimeMillis() + 259200000L);
                  var13.a(new S_SkillSound(var13.fr(), 7783));
                  var13.b(new S_SkillSound(var13.fr(), 7783));
                  L1ItemQuestBuff.a(var13, var9, 259200, var4);
               }

               L1Dragon.a().a(L1MonsterInstance.this);
            } else if (L1MonsterInstance.this.z() >= 190574 && L1MonsterInstance.this.z() <= 190576) {
               int var8 = 0;
               if (this.b instanceof L1PcInstance) {
                  L1PcInstance var2 = (L1PcInstance)this.b;
                  var8 = var2.dX();
               }

               L1ThebesBattle.a().a(var8);
            } else if (L1MonsterInstance.this.z() >= 190237 && L1MonsterInstance.this.z() <= 190242 && this.b instanceof L1PcInstance) {
               switch (L1MonsterInstance.this.z()) {
                  case 190237:
                     L1PcInstance var1 = (L1PcInstance)this.b;
                     if (var1.bB(1038)) {
                        var1.a(new S_ServerMessage(79));
                     } else {
                        var1.j(1027, 300000);
                        var1.a(new S_Liquor(var1.fr(), 8));
                        var1.b(new S_Liquor(var1.fr(), 8));
                        var1.a(new S_SkillSound(var1.fr(), 8910));
                        var1.b(new S_SkillSound(var1.fr(), 8910));
                        var1.a(new S_ServerMessage(1065));
                        var1.a(new S_PacketBox(60, 300));
                     }
                     break;
                  case 190238:
                     new S_057().a(this.b, 0);
                     break;
                  case 190239:
                     new S_048().a(this.b, 0);
                     break;
                  case 190240:
                     new S_211().a(this.b, 0);
                     break;
                  case 190241:
                     new S_079().a(this.b, 0);
                     break;
                  case 190242:
                     new S_150().a(this.b, 0);
               }
            }

            if (L1MonsterInstance.this.an() > 0 && L1MonsterInstance.this.ao() > 0) {
               int var12 = -1;

               while (
                  var12++ < 30 && (L1MonsterInstance.this.fs() != L1MonsterInstance.this.an() || L1MonsterInstance.this.ft() != L1MonsterInstance.this.ao())
               ) {
                  L1MonsterInstance.this.g(L1MonsterInstance.this.a(L1MonsterInstance.this.an(), L1MonsterInstance.this.ao()));

                  try {
                     Thread.sleep(L1MonsterInstance.this.f(L1MonsterInstance.this.N(), 0) * 3 / 4);
                  } catch (InterruptedException var5) {
                     L1MonsterInstance.y.log(Level.SEVERE, var5.getLocalizedMessage(), var5);
                  }
               }
            }

            L1MonsterInstance.this.cq(8);
            L1MonsterInstance.this.fq().a(L1MonsterInstance.this.fu(), true);
            L1MonsterInstance.this.b(new S_DoActionGFX(L1MonsterInstance.this.fr(), 8));
            L1MonsterInstance.this.a(false);
            L1MonsterInstance.this.f(this.b);
            L1MonsterInstance.this.j(false);
            L1MonsterInstance.this.k(0);
            L1MonsterInstance.this.A(0);
            L1MonsterInstance.this.A();
         } catch (Exception var7) {
            L1MonsterInstance.y.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
         }
      }
   }
}
