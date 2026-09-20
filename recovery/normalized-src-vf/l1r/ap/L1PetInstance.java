package l1r.ap;

import java.util.Arrays;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import l1r.ai.IdFactory;
import l1r.ao.ExpTable;
import l1r.ao.PetItemTable;
import l1r.ao.PetTable;
import l1r.ao.PetTypeTable;
import l1r.aq.L1Attack;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.be.S_DoActionGFX;
import l1r.be.S_HPMeter;
import l1r.be.S_NpcChatPacket;
import l1r.be.S_PetCtrlMenu;
import l1r.be.S_PetMenuPacket;
import l1r.be.S_PetPack;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.be.S_SummonPack;
import l1r.bh.L1Npc;
import l1r.bh.L1Pet;
import l1r.bh.L1PetItem;
import l1r.bh.L1PetType;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;

public class L1PetInstance extends L1NpcInstance {
   private int y;
   private L1ItemInstance z;
   private L1ItemInstance A;
   private int B;
   private int C;
   private int D;
   private final L1PcInstance E;
   private int F;
   private L1PetType G;
   private int H;
   private ScheduledFuture<?> I;

   @Override
   public boolean a() {
      switch (this.D) {
         case 3:
            return true;
         case 4:
            if (this.E != null && this.E.fp() == this.fp() && this.fu().c(this.E.fu()) < 5) {
               this.y = this.c(this.E.fs(), this.E.ft());
               this.y = this.a(this.fs(), this.ft(), this.fp(), this.y);
               this.g(this.y);
               this.v(this.f(this.N(), 0));
               return false;
            }

            this.D = 3;
            return true;
         case 5:
            if (Math.abs(this.X() - this.fs()) > 1 || Math.abs(this.Y() - this.ft()) > 1) {
               int var3 = this.a(this.X(), this.Y());
               if (var3 == -1) {
                  this.q(this.fs());
                  this.r(this.ft());
               } else {
                  this.g(var3);
                  this.v(this.f(this.N(), 0));
               }
            }

            return false;
         case 6:
         default:
            if (this.E != null && this.E.fp() == this.fp()) {
               if (this.fu().c(this.E.fu()) > 2) {
                  this.y = this.a(this.E.fs(), this.E.ft());
                  this.g(this.y);
                  this.v(this.f(this.N(), 0));
               }

               return false;
            }

            this.D = 3;
            return true;
         case 7:
            if (this.E != null && this.E.fp() == this.fp() && this.fu().c(this.E.fu()) <= 1) {
               this.D = 3;
               return true;
            } else {
               int var1 = this.E.fs() + Random.a(1);
               int var2 = this.E.ft() + Random.a(1);
               this.y = this.a(var1, var2);
               if (this.y == -1) {
                  this.D = 3;
                  return true;
               } else {
                  this.g(this.y);
                  this.v(this.f(this.N(), 0));
                  return false;
               }
            }
      }
   }

   public L1PetInstance(L1Npc var1, L1PcInstance var2, L1Pet var3) {
      super(var1);
      this.E = var2;
      this.F = var3.a();
      this.G = PetTypeTable.b().a(var1.b());
      this.cF(var3.b());
      this.e(var3.d());
      this.b(var3.e());
      this.bG(var3.f());
      this.bx(var3.f());
      this.bI(var3.g());
      this.by(var3.g());
      this.k(var3.h());
      this.f(ExpTable.a(var3.e(), var3.h()));
      this.cr(var3.i());
      this.c_(var3.j());
      this.aw();
      this.e(var2);
      this.cG(var2.fs() + Random.a(5) - 2);
      this.cH(var2.ft() + Random.a(5) - 2);
      this.cE(var2.fp());
      this.ct(5);
      this.s(var1.ae());
      this.D = 3;
      L1World.a().a(this);
      L1World.a().c(this);

      for (L1PcInstance var4 : L1World.a().f(this)) {
         this.b(var4);
      }

      var2.e(this);
   }

   public L1PetInstance(L1NpcInstance var1, L1PcInstance var2, int var3) {
      super(var1.U_());
      this.E = var2;
      this.F = var3;
      this.G = PetTypeTable.b().a(this.z());
      this.cF(IdFactory.a().c());
      this.bx(var1.ea());
      this.by(var1.eb());
      this.k(750);
      this.f(0);
      this.cr(0);
      this.c_(50);
      this.aw();
      this.e(var2);
      this.cG(var1.fs());
      this.cH(var1.ft());
      this.cE(var1.fp());
      this.ct(var1.fb());
      this.s(var1.aa());
      this.o(6);
      this.a(var1.y());
      var1.a((L1Inventory)null);
      this.D = 3;
      this.v();
      if (this.ew() > this.ea()) {
         this.u();
      }

      this.x();
      if (this.ex() > this.eb()) {
         this.w();
      }

      var1.aa_();
      L1World.a().a(this);
      L1World.a().c(this);

      for (L1PcInstance var4 : L1World.a().f(this)) {
         this.b(var4);
      }

      var2.e(this);
      PetTable.a().a(var1, this.fr(), var3);
   }

   @Override
   public void b(L1Character var1, int var2) {
      if (this.ea() > 0) {
         if (var2 > 0) {
            this.c((L1Character)var1, 0);
            this.bz(66);
            this.bz(153);
         }

         if (var1 instanceof L1PcInstance && var2 > 0) {
            L1PcInstance var3 = (L1PcInstance)var1;
            var3.a(this);
         }

         if (var1 instanceof L1PetInstance) {
            L1PetInstance var5 = (L1PetInstance)var1;
            if (this.ep() == 1 || var5.ep() == 1) {
               var2 = 0;
            }
         } else if (var1 instanceof L1SummonInstance) {
            L1SummonInstance var4 = (L1SummonInstance)var1;
            if (this.ep() == 1 || var4.ep() == 1) {
               var2 = 0;
            }
         }

         int var6 = this.ea() - var2;
         if (var6 <= 0) {
            this.ay();
         } else {
            this.a(var6);
         }
      } else if (!this.eX()) {
         this.ay();
      }
   }

   private synchronized void ay() {
      if (!this.eX()) {
         this.X(true);
         this.ax();
         this.cq(8);
         this.a(0);
         this.fq().a(this.fu(), true);
         this.b(new S_DoActionGFX(this.fr(), 8));
      }
   }

   @Override
   public void d(int var1) {
      L1Pet var2 = PetTable.a().b(this.F);
      if (var2 != null) {
         int var3 = this.G.g();
         int var4 = this.G.i();
         int var5 = this.ew();
         int var6 = this.ex();
         this.g_(var3);
         this.G = PetTypeTable.b().a(var3);
         this.b(1L);
         this.bG(var5 / 2);
         this.bI(var6 / 2);
         this.bx(this.ew());
         this.by(this.ex());
         this.k(0);
         this.f(0);
         this.y().b(var4, 1);
         L1Object var7 = L1World.a().a(var2.b());
         if (var7 != null && var7 instanceof L1NpcInstance) {
            L1PetInstance var8 = (L1PetInstance)var7;
            L1Inventory var9 = var8.y();

            for (Object var11 : this.y().d()) {
               L1ItemInstance var13 = (L1ItemInstance)var11;
               if (var13 != null) {
                  if (var13.D()) {
                     var13.b(false);
                     L1PetItem var14 = PetItemTable.a().a(var13.N());
                     if (var14.n() == 1) {
                        this.b((L1ItemInstance)null);
                        var8.a(this, var13);
                     } else if (var14.n() == 0) {
                        this.c((L1ItemInstance)null);
                        var8.b(this, var13);
                     }
                  }

                  if (var8.y().a(var13, var13.E()) == 0) {
                     this.y().a(var13, var13.E(), var9);
                  } else {
                     var9 = L1World.a().a(this.fs(), this.ft(), this.fp());
                     this.y().a(var13, var13.E(), var9);
                  }
               }
            }

            var8.b(new S_SkillSound(var8.fr(), 2127));
         }

         PetTable.a().a(this.F);
         var2.a(var1);
         var2.c(var3);
         var2.a(this.et());
         var2.d(this.ev());
         var2.e(this.ew());
         var2.f(this.ex());
         var2.g(this.m());
         var2.i(this.fj());
         PetTable.a().a(this, this.fr(), var1);
         this.F = var1;
         if (var7 != null && var7 instanceof L1NpcInstance) {
            L1PetInstance var15 = (L1PetInstance)var7;
            this.aw();
         }
      }
   }

   private void az() {
      L1MonsterInstance var1 = new L1MonsterInstance(this.U_());
      var1.cF(IdFactory.a().c());
      var1.cG(this.fs());
      var1.cH(this.ft());
      var1.cE(this.fp());
      var1.ct(this.fb());
      var1.c(true);
      var1.a(this.y());
      this.a((L1Inventory)null);
      var1.b(this.ev());
      var1.bG(this.ew());
      var1.bx(this.ea());
      var1.bI(this.ex());
      var1.by(this.eb());
      this.E.ek().remove(this.fr());
      if (this.E.ek().isEmpty()) {
         this.E.a(new S_PetCtrlMenu(this.E, var1, false));
      }

      this.aa_();
      this.E.j().c(this.F, 1);
      PetTable.a().a(this.F);
      L1World.a().a(var1);
      L1World.a().c(var1);

      for (L1PcInstance var2 : L1World.a().f(var1)) {
         this.b(var2);
      }
   }

   @Override
   public void b(boolean var1) {
      L1Inventory var2 = this.E.j();

      for (Object var4 : this.y().d()) {
         L1ItemInstance var6 = (L1ItemInstance)var4;
         if (var6 != null) {
            if (var6.D()) {
               if (!var1) {
                  continue;
               }

               L1PetItem var7 = PetItemTable.a().a(var6.N());
               if (var7.n() == 1) {
                  this.b((L1ItemInstance)null);
               } else if (var7.n() == 0) {
                  this.c((L1ItemInstance)null);
               }

               var6.b(false);
            }

            if (this.E.j().a(var6, var6.E()) == 0) {
               this.y().a(var6, var6.E(), var2);
               this.E.a(new S_ServerMessage(143, this.et(), var6.s()));
            } else {
               var2 = L1World.a().a(this.fs(), this.ft(), this.fp());
               this.y().a(var6, var6.E(), var2);
            }
         }
      }
   }

   public void h() {
      L1Inventory var1 = L1World.a().a(this.fs(), this.ft(), this.fp());

      for (L1ItemInstance var2 : this.o.d()) {
         if (var2.D()) {
            L1PetItem var4 = PetItemTable.a().a(var2.N());
            if (var4.n() == 1) {
               this.b((L1ItemInstance)null);
            } else if (var4.n() == 0) {
               this.c((L1ItemInstance)null);
            }

            var2.b(false);
         }

         this.o.a(var2, var2.E(), var1);
      }
   }

   @Override
   public void i() {
      int var1 = this.G.a(L1PetType.b(this.ev()));
      if (var1 != 0 && !this.eX()) {
         if (this.fj() == 0) {
            var1 = this.G.h();
         }

         this.b(new S_NpcChatPacket(this, "$" + var1, 0));
      }

      if (this.fj() > 0) {
         this.e(7);
      } else {
         this.e(3);
      }
   }

   public void f(L1Character var1) {
      if (var1 != null && (this.D == 1 || this.D == 2 || this.D == 5) && this.fj() > 0) {
         this.c((L1Character)var1, 0);
         if (!this.ae()) {
            this.q();
         }
      }
   }

   public void g(L1Character var1) {
      if (var1 != null && (this.D == 1 || this.D == 5) && this.fj() > 0) {
         this.c((L1Character)var1, 0);
         if (!this.ae()) {
            this.q();
         }
      }
   }

   @Override
   public void b(L1PcInstance var1) {
      var1.c(this);
      var1.a(new S_PetPack(this, var1));
      if (this.eX()) {
         var1.a(new S_DoActionGFX(this.fr(), 8));
      }
   }

   @Override
   public void a(L1PcInstance var1, int var2) {
      if (this.ea() > 0 && !this.eX()) {
         L1Character var3 = this.M();
         L1PcInstance var4 = (L1PcInstance)var3;
         if (!var4.aR()) {
            if (this.ep() == 1) {
               L1Attack var5 = new L1Attack(var1, this, var2);
               var5.c();
            } else if (!var1.a(var1, this, false)) {
               super.a(var1, var2);
            }
         }
      }
   }

   @Override
   public void a(L1PcInstance var1) {
      if (!this.eX()) {
         if (this.E.equals(var1)) {
            var1.a(new S_PetMenuPacket(this, this.l()));
            L1Pet var2 = PetTable.a().b(this.F);
            if (var2 != null) {
               var2.g(this.m());
               var2.d(this.ev());
               var2.e(this.ew());
               var2.f(this.ex());
               var2.i(this.fj());
               PetTable.a().a(var2);
            }
         }
      }
   }

   @Override
   public void a(L1PcInstance var1, String var2) {
      int var3 = this.c(var2);
      if (var3 != 0) {
         if (var3 == 6) {
            L1PcInstance var4 = (L1PcInstance)this.k;
            this.az();
            Object[] var5 = var4.ek().values().toArray();
            Object[] var9 = var5;
            int var8 = var5.length;

            for (int var7 = 0; var7 < var8; var7++) {
               Object var6 = var9[var7];
               if (var6 instanceof L1SummonInstance) {
                  L1SummonInstance var19 = (L1SummonInstance)var6;
                  var4.a(new S_SummonPack(var19, var4));
                  return;
               }

               if (var6 instanceof L1PetInstance) {
                  L1PetInstance var10 = (L1PetInstance)var6;
                  var4.a(new S_PetPack(var10, var4));
                  return;
               }
            }
         } else {
            Object[] var12 = this.E.ek().values().toArray();
            Object[] var16 = var12;
            int var15 = var12.length;

            for (int var14 = 0; var14 < var15; var14++) {
               Object var13 = var16[var14];
               if (var13 instanceof L1PetInstance) {
                  L1PetInstance var17 = (L1PetInstance)var13;
                  if (this.E != null && this.E.ev() >= var17.ev() && var17.fj() > 0) {
                     var17.e(var3);
                  } else if (!var17.eX()) {
                     L1PetType var20 = PetTypeTable.b().a(var17.U_().b());
                     int var11 = var20.h();
                     if (var11 != 0) {
                        var17.b(new S_NpcChatPacket(var17, "$" + var11, 0));
                     }
                  }
               } else if (var13 instanceof L1SummonInstance) {
                  L1SummonInstance var18 = (L1SummonInstance)var13;
                  var18.d(var3);
               }
            }
         }
      }
   }

   @Override
   public void Y_() {
      if (!this.w) {
         this.e(1, 100);
      }

      if (this.ea() * 100 / this.ew() < 40) {
         this.e(0, 100);
      }
   }

   @Override
   public void a(L1ItemInstance var1) {
      Arrays.sort(r);
      Arrays.sort(s);
      if (Arrays.binarySearch(r, var1.N()) >= 0) {
         if (this.ea() != this.ew()) {
            this.e(0, 100);
         }
      } else if (Arrays.binarySearch(s, var1.N()) >= 0) {
         this.e(1, 100);
      }
   }

   private int c(String var1) {
      int var2 = 0;
      if (var1.equalsIgnoreCase("aggressive")) {
         var2 = 1;
      } else if (var1.equalsIgnoreCase("defensive")) {
         var2 = 2;
      } else if (var1.equalsIgnoreCase("stay")) {
         var2 = 3;
      } else if (var1.equalsIgnoreCase("extend")) {
         var2 = 4;
      } else if (var1.equalsIgnoreCase("alert")) {
         var2 = 5;
      } else if (var1.equalsIgnoreCase("dismiss")) {
         var2 = 6;
      } else if (var1.equalsIgnoreCase("getitem")) {
         this.b(false);
      }

      return var2;
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

      if (this.E != null) {
         L1PcInstance var3 = this.E;
         var3.a(new S_HPMeter(this));
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

   public void e(int var1) {
      this.D = var1;
      if (this.D == 5) {
         this.q(this.fs());
         this.r(this.ft());
      }

      if (this.D == 7) {
         this.t();
      }

      if (this.D == 3) {
         this.t();
      } else if (!this.ae()) {
         this.q();
      }
   }

   public int j() {
      return this.D;
   }

   public int k() {
      return this.F;
   }

   public void f(int var1) {
      this.H = var1;
   }

   public int l() {
      return this.H;
   }

   @Override
   public void b(L1ItemInstance var1) {
      this.z = var1;
   }

   public L1ItemInstance n() {
      return this.z;
   }

   public void c(L1ItemInstance var1) {
      this.A = var1;
   }

   public L1ItemInstance o() {
      return this.A;
   }

   public void B(int var1) {
      this.B = var1;
   }

   public int p() {
      return this.B;
   }

   public void C(int var1) {
      this.C = var1;
   }

   public int au() {
      return this.C;
   }

   public L1PetType av() {
      return this.G;
   }

   public void aw() {
      this.I = GeneralThreadPool.a().a(new L1PetInstance.L1R_a(null), 1000L, 200000L);
   }

   public void ax() {
      if (this.I != null) {
         this.I.cancel(true);
      }
   }

   public void a(L1PetInstance var1, L1ItemInstance var2) {
      if (var1.n() == null) {
         this.c(var1, var2);
      } else if (var1.n().equals(var2)) {
         this.d(var1, var1.n());
      } else {
         this.d(var1, var1.n());
         this.c(var1, var2);
      }
   }

   public void b(L1PetInstance var1, L1ItemInstance var2) {
      if (var1.o() == null) {
         this.e(var1, var2);
      } else if (var1.o().equals(var2)) {
         this.f(var1, var1.o());
      } else {
         this.f(var1, var1.o());
         this.e(var1, var2);
      }
   }

   private void c(L1PetInstance var1, L1ItemInstance var2) {
      int var3 = var2.N();
      L1PetItem var4 = PetItemTable.a().a(var3);
      if (var4 != null) {
         var1.B(var4.b());
         var1.C(var4.c());
         var1.bN(var4.e());
         var1.bP(var4.f());
         var1.bR(var4.g());
         var1.bV(var4.h());
         var1.bX(var4.i());
         var1.bH(var4.j());
         var1.bJ(var4.k());
         var1.cp(var4.l());
         var1.co(var4.m());
         var1.b(var2);
         var2.b(true);
      }
   }

   private void d(L1PetInstance var1, L1ItemInstance var2) {
      int var3 = var2.N();
      L1PetItem var4 = PetItemTable.a().a(var3);
      if (var4 != null) {
         var1.B(0);
         var1.C(0);
         var1.bN(-var4.e());
         var1.bP(-var4.f());
         var1.bR(-var4.g());
         var1.bV(-var4.h());
         var1.bX(-var4.i());
         var1.bH(-var4.j());
         var1.bJ(-var4.k());
         var1.cp(-var4.l());
         var1.co(-var4.m());
         var1.b((L1ItemInstance)null);
         var2.b(false);
      }
   }

   private void e(L1PetInstance var1, L1ItemInstance var2) {
      int var3 = var2.N();
      L1PetItem var4 = PetItemTable.a().a(var3);
      if (var4 != null) {
         var1.bL(var4.d());
         var1.bN(var4.e());
         var1.bP(var4.f());
         var1.bR(var4.g());
         var1.bV(var4.h());
         var1.bX(var4.i());
         var1.bH(var4.j());
         var1.bJ(var4.k());
         var1.cp(var4.l());
         var1.co(var4.m());
         var1.c(var2);
         var2.b(true);
      }
   }

   private void f(L1PetInstance var1, L1ItemInstance var2) {
      int var3 = var2.N();
      L1PetItem var4 = PetItemTable.a().a(var3);
      if (var4 != null) {
         var1.bL(-var4.d());
         var1.bN(-var4.e());
         var1.bP(-var4.f());
         var1.bR(-var4.g());
         var1.bV(-var4.h());
         var1.bX(-var4.i());
         var1.bH(-var4.j());
         var1.bJ(-var4.k());
         var1.cp(-var4.l());
         var1.co(-var4.m());
         var1.c((L1ItemInstance)null);
         var2.b(false);
      }
   }

   private class L1R_a extends TimerTask {
      private L1R_a() {
      }

      @Override
      public void run() {
         if (!L1PetInstance.this.ah() && !L1PetInstance.this.eX()) {
            int var1 = L1PetInstance.this.fj() - 2;
            if (var1 <= 0) {
               L1PetInstance.this.c_(0);
               L1PetInstance.this.e(3);
               L1PetType var2 = PetTypeTable.b().a(L1PetInstance.this.z());
               int var3 = var2.h();
               if (var3 != 0) {
                  L1PetInstance.this.b(new S_NpcChatPacket(L1PetInstance.this, "$" + var3, 0));
               }
            } else {
               L1PetInstance.this.c_(var1);
            }

            PetTable.a().a(L1PetInstance.this);
         } else {
            L1PetInstance.this.I.cancel(true);
         }
      }

      // $VF: synthetic method
      L1R_a(L1PetInstance.L1R_a var2) {
         this();
      }
   }
}
