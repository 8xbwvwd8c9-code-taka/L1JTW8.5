package l1r.ap;

import java.util.Arrays;
import java.util.concurrent.ScheduledFuture;
import l1r.ai.IdFactory;
import l1r.ao.DropTable;
import l1r.ao.NpcTable;
import l1r.ao.PetTypeTable;
import l1r.aq.L1Attack;
import l1r.aq.L1Character;
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
import l1r.bh.L1PetType;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;

public class L1SummonInstance extends L1NpcInstance {
   private ScheduledFuture<?> y;
   private static final long z = 3600000L;
   private int A;
   private final boolean B;
   private boolean C = false;
   private int D;

   @Override
   public boolean a() {
      switch (this.A) {
         case 3:
            return true;
         case 4:
            if (this.k != null && this.k.fp() == this.fp() && this.fu().c(this.k.fu()) < 5) {
               this.D = this.c(this.k.fs(), this.k.ft());
               this.D = this.a(this.fs(), this.ft(), this.fp(), this.D);
               this.g(this.D);
               this.v(this.f(this.N(), 0));
               return false;
            }

            this.A = 3;
            return true;
         case 5:
            if (Math.abs(this.X() - this.fs()) > 1 || Math.abs(this.Y() - this.ft()) > 1) {
               this.D = this.a(this.X(), this.Y());
               if (this.D == -1) {
                  this.q(this.fs());
                  this.r(this.ft());
               } else {
                  this.g(this.D);
                  this.v(this.f(this.N(), 0));
               }
            }

            return false;
         default:
            if (this.k != null && this.k.fp() == this.fp()) {
               if (this.fu().c(this.k.fu()) > 2) {
                  this.D = this.a(this.k.fs(), this.k.ft());
                  this.g(this.D);
                  this.v(this.f(this.N(), 0));
               }

               return false;
            } else {
               this.A = 3;
               return true;
            }
      }
   }

   public L1SummonInstance(L1Npc var1, L1Character var2) {
      super(var1);
      this.cF(IdFactory.a().c());
      this.y = GeneralThreadPool.a().a(new L1SummonInstance.L1R_a(null), 3600000L);
      this.e(var2);
      this.cG(var2.fs() + Random.a(5) - 2);
      this.cH(var2.ft() + Random.a(5) - 2);
      this.cE(var2.fp());
      this.ct(5);
      this.s(var1.ae());
      this.A = 3;
      this.B = false;
      L1World.a().a(this);
      L1World.a().c(this);

      for (L1PcInstance var3 : L1World.a().f(this)) {
         this.b(var3);
      }

      var2.e(this);
   }

   public L1SummonInstance(L1MonsterInstance var1, L1Character var2, boolean var3) {
      super(null);
      this.cF(IdFactory.a().c());
      if (var3) {
         int var4 = 45065;
         L1PcInstance var5 = (L1PcInstance)var2;
         int var6 = var5.ev();
         if (var5.B()) {
            if (var6 >= 24 && var6 <= 31) {
               var4 = 81183;
            } else if (var6 >= 32 && var6 <= 39) {
               var4 = 81184;
            } else if (var6 >= 40 && var6 <= 43) {
               var4 = 81185;
            } else if (var6 >= 44 && var6 <= 47) {
               var4 = 81186;
            } else if (var6 >= 48 && var6 <= 51) {
               var4 = 81187;
            } else if (var6 >= 52) {
               var4 = 81188;
            }
         } else if (var5.A() && var6 >= 48) {
            var4 = 81183;
         }

         L1Npc var7 = NpcTable.a().a(var4).a();
         this.a(var7);
      } else {
         this.a(var1.U_());
         this.bx(var1.ea());
         this.by(var1.eb());
      }

      this.y = GeneralThreadPool.a().a(new L1SummonInstance.L1R_a(null), 3600000L);
      this.e(var2);
      this.cG(var1.fs());
      this.cH(var1.ft());
      this.cE(var1.fp());
      this.ct(var1.fb());
      this.s(var1.aa());
      this.o(6);
      if (!var1.i()) {
         DropTable.a().a(var1, var1.y());
      }

      this.a(var1.y());
      var1.a((L1Inventory)null);
      this.A = 3;
      this.B = true;

      for (L1NpcInstance var8 : var2.ek().values()) {
         var8.c(var1);
      }

      var1.aa_();
      L1World.a().a(this);
      L1World.a().c(this);

      for (L1PcInstance var9 : L1World.a().f(this)) {
         this.b(var9);
      }

      var2.e(this);
   }

   @Override
   public void b(L1Character var1, int var2) {
      if (this.ea() > 0) {
         if (var2 > 0) {
            this.c((L1Character)var1, 0);
            this.bz(66);
            this.bz(153);
            if (!this.L()) {
               this.A = 1;
               this.f(var1);
            }
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
            this.j();
         } else {
            this.a(var6);
         }
      } else if (!this.eX()) {
         System.out.println("警告：サモンのＨＰ減少処理が正しく行われていない箇所があります。※もしくは最初からＨＰ０");
         this.j();
      }
   }

   private synchronized void j() {
      if (!this.eX()) {
         this.X(true);
         this.a(0);
         this.cq(8);
         this.fq().a(this.fu(), true);
         L1Inventory var1 = this.k.y();

         for (L1ItemInstance var3 : this.o.d()) {
            if (this.k.y().a(var3, var3.E()) == 0) {
               this.o.a(var3, var3.E(), var1);
               ((L1PcInstance)this.k).a(new S_ServerMessage(143, this.et(), var3.s()));
            } else {
               var1 = L1World.a().a(this.fs(), this.ft(), this.fp());
               this.o.a(var3, var3.E(), var1);
            }
         }

         if (this.B) {
            this.b(new S_DoActionGFX(this.fr(), 8));
            this.A();
         } else {
            this.aa_();
         }
      }
   }

   public synchronized void h() {
      this.C = true;
      if (!this.B) {
         this.fq().a(this.fu(), true);
         L1Inventory var1 = this.k.y();

         for (L1ItemInstance var3 : this.o.d()) {
            if (this.k.y().a(var3, var3.E()) == 0) {
               this.o.a(var3, var3.E(), var1);
               ((L1PcInstance)this.k).a(new S_ServerMessage(143, this.et(), var3.s()));
            } else {
               var1 = L1World.a().a(this.fs(), this.ft(), this.fp());
               this.o.a(var3, var3.E(), var1);
            }
         }

         this.aa_();
      } else {
         this.k();
      }
   }

   private void k() {
      L1MonsterInstance var1 = new L1MonsterInstance(this.U_());
      var1.cF(IdFactory.a().c());
      var1.cG(this.fs());
      var1.cH(this.ft());
      var1.cE(this.fp());
      var1.ct(this.fb());
      var1.c(true);
      L1Inventory var2 = new L1Inventory();

      for (L1ItemInstance var3 : this.y().d()) {
         var2.d(var3);
      }

      var1.a(var2);
      var1.bx(this.ea());
      var1.by(this.eb());
      var1.k(0);
      if (this.k instanceof L1PcInstance) {
         L1PcInstance var5 = (L1PcInstance)this.k;
         var5.a(new S_ServerMessage(666, this.T()));
      }

      if (!this.eX()) {
         this.X(true);
         this.a(0);
         this.fq().a(this.fu(), true);
      }

      this.aa_();
      L1World.a().a(var1);
      L1World.a().c(var1);
   }

   @Override
   public synchronized void aa_() {
      if (!this.ah()) {
         if (!this.B && !this.C) {
            this.b(new S_SkillSound(this.fr(), 169));
         }

         if (this.k instanceof L1PcInstance) {
            L1PcInstance var1 = (L1PcInstance)this.k;
            var1.a(new S_PetCtrlMenu(var1, this, false));
         }

         this.k.ek().remove(this.fr());
         super.aa_();
         if (this.y != null) {
            this.y.cancel(true);
            this.y = null;
         }
      }
   }

   public void f(L1Character var1) {
      if (var1 != null && (this.A == 1 || this.A == 2 || this.A == 5)) {
         this.c((L1Character)var1, 0);
         if (!this.ae()) {
            this.q();
         }
      }
   }

   public void g(L1Character var1) {
      if (var1 != null && (this.A == 1 || this.A == 5)) {
         this.c((L1Character)var1, 0);
         if (!this.ae()) {
            this.q();
         }
      }
   }

   @Override
   public void a(L1PcInstance var1, int var2) {
      if (this.ea() > 0 && !this.eX()) {
         if (var1 != null) {
            L1Character var3 = this.M();
            if (var3 != null) {
               L1PcInstance var4 = (L1PcInstance)var3;
               if (!var4.aR()) {
                  if ((this.ep() == 1 || var1.ep() == 1) && this.L()) {
                     L1Attack var5 = new L1Attack(var1, this, var2);
                     var5.c();
                  } else if (!var1.a(var1, this, false)) {
                     super.a(var1, var2);
                  }
               }
            }
         }
      }
   }

   @Override
   public void a(L1PcInstance var1) {
      if (!this.eX()) {
         if (this.k.equals(var1)) {
            var1.a(new S_PetMenuPacket(this, 0));
         }
      }
   }

   @Override
   public void a(L1PcInstance var1, String var2) {
      int var3 = this.c(var2);
      if (var3 != 0) {
         if (var3 == 6) {
            L1PcInstance var4 = (L1PcInstance)this.k;
            if (this.B) {
               this.k();
            } else {
               this.j();
            }

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
            Object[] var12 = this.k.ek().values().toArray();
            Object[] var16 = var12;
            int var15 = var12.length;

            for (int var14 = 0; var14 < var15; var14++) {
               Object var13 = var16[var14];
               if (var13 instanceof L1SummonInstance) {
                  L1SummonInstance var17 = (L1SummonInstance)var13;
                  var17.d(var3);
               } else if (var13 instanceof L1PetInstance) {
                  L1PetInstance var18 = (L1PetInstance)var13;
                  if (var1 != null && var1.ev() >= var18.ev() && var18.fj() > 0) {
                     var18.e(var3);
                  } else if (!var18.eX()) {
                     L1PetType var20 = PetTypeTable.b().a(var18.U_().b());
                     int var11 = var20.h();
                     if (var11 != 0) {
                        var18.b(new S_NpcChatPacket(var18, "$" + var11, 0));
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void b(L1PcInstance var1) {
      var1.c(this);
      var1.a(new S_SummonPack(this, var1));
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

      if (this.k instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)this.k;
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

   @Override
   public void d(int var1) {
      this.A = var1;
      if (this.A == 5) {
         this.q(this.fs());
         this.r(this.ft());
      }

      if (this.A == 3) {
         this.t();
      } else if (!this.ae()) {
         this.q();
      }
   }

   public int i() {
      return this.A;
   }

   private class L1R_a implements Runnable {
      private L1R_a() {
      }

      @Override
      public void run() {
         if (!L1SummonInstance.this.ah()) {
            if (L1SummonInstance.this.B) {
               L1SummonInstance.this.k();
            } else {
               L1SummonInstance.this.j();
            }
         }
      }

      // $VF: synthetic method
      L1R_a(L1SummonInstance.L1R_a var2) {
         this();
      }
   }
}
