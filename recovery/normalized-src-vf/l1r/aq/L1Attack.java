package l1r.aq;

import l1r.am.ListSprReader__obf_c;
import l1r.ao.WeaponSkillTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.as.L1CastleWar;
import l1r.at.L1GameTimeClock;
import l1r.az.L1DamagePoison;
import l1r.az.L1ParalysisPoison;
import l1r.az.L1SilencePoison;
import l1r.be.S_AttackPacket;
import l1r.be.S_DoActionGFX;
import l1r.be.S_EffectLocation;
import l1r.be.S_Liquor;
import l1r.be.S_NpcChatPacket;
import l1r.be.S_PacketBox;
import l1r.be.S_Paralysis;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.be.S_SystemMessage;
import l1r.bf.S_029;
import l1r.bf.S_033;
import l1r.bf.S_135;
import l1r.bi.CalcStat;
import l1r.bi.Point;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class L1Attack {
   private L1PcInstance a = null;
   private L1Character b = null;
   private L1Character c = null;
   private L1PcInstance d = null;
   private L1NpcInstance e = null;
   private L1NpcInstance f = null;
   private int g = 0;
   private static final int h = 1;
   private static final int i = 2;
   private static final int j = 3;
   private static final int k = 4;
   private boolean l = false;
   private int m = 0;
   private int n = 0;
   private int o = 0;
   private L1ItemInstance p = null;
   private L1ItemInstance q = null;
   private int r = 0;
   private int s = 0;
   private int t = 0;
   private int u = 0;
   private int v = 0;
   private int w = 1;
   private int x = 1;
   private int y = 0;
   private int z = 0;
   private int A = 0;
   private int B = 0;
   private int C = 0;
   private final int D;
   private static final int[] E = new int[]{78, 50, 157, 15003, 15004, 120};
   private static int[] F = new int[]{
      3008, 3009, 3010, 3011, 3012, 3013, 3014, 3024, 3025, 3026, 3027, 3028, 3029, 3030, 3040, 3041, 3042, 3043, 3044, 3045, 3046
   };

   public L1Attack(L1Character var1, L1Character var2) {
      this(var1, var2, 0);
   }

   public L1Attack(L1Character var1, L1Character var2, int var3) {
      this.D = var3;
      if (var1 instanceof L1PcInstance) {
         this.a = (L1PcInstance)var1;
         if (var2 instanceof L1PcInstance) {
            this.d = (L1PcInstance)var2;
            this.g = 1;
         } else if (var2 instanceof L1NpcInstance) {
            this.f = (L1NpcInstance)var2;
            this.g = 2;
         }

         this.p = this.a.v();
         if (this.p != null) {
            this.r = this.p.N();
            this.s = this.p.a().aO();
            this.t = this.p.a().ac() + this.p.a().ad() + this.p.P();
            this.u = this.p.a().v();
            this.v = this.p.a().w();
            this.w = this.p.a().aB();
            this.x = this.p.F();
            this.y = this.p.G();
            this.z = this.p.a().k();
            if (this.s != 20 && this.s != 62) {
               this.y = this.p.G() - this.p.H();
            } else {
               this.q = this.a.j().n(this.s);
               if (this.q != null) {
                  this.x = this.q.F();
                  this.z = this.q.a().k();
               }
            }

            this.A = this.p.a().aC();
            if (this.p.N() == 353) {
               this.A = this.A + this.p.G();
            }

            this.B = this.p.K();
            this.C = this.p.L();
         }
      } else if (var1 instanceof L1NpcInstance) {
         this.e = (L1NpcInstance)var1;
         if (var2 instanceof L1PcInstance) {
            this.d = (L1PcInstance)var2;
            this.g = 3;
         } else if (var2 instanceof L1NpcInstance) {
            this.f = (L1NpcInstance)var2;
            this.g = 4;
         }
      }

      this.c = var1;
      this.b = var2;
   }

   public boolean a() {
      if (this.g == 1 || this.g == 2) {
         if (this.w == -1) {
            if (!this.a.fu().e(this.b.fu())) {
               return this.l = false;
            }
         } else if (this.a.fu().c(this.b.fu()) > this.w + 1) {
            return this.l = false;
         }

         if ((this.s == 20 || this.s == 62) && this.q == null && this.r != 190 && this.r != 399) {
            return this.l = false;
         }

         if (this.w != 1 && !this.a.i(this.b.fs(), this.b.ft())) {
            return this.l = false;
         }
      }

      if (this.g == 1) {
         this.l = this.h();
      } else if (this.g == 2) {
         this.l = this.i();
      } else if (this.g == 3) {
         this.l = this.j();
      } else if (this.g == 4) {
         this.l = this.k();
      }

      if (this.g == 1 && this.l && this.a.bB(92) && this.d.bB(78) && Random.a(100) < this.a.ev() - 80 + 1) {
         this.d.bz(78);
         this.d.a(new S_SkillSound(this.d.fr(), 14539));
         this.d.b(new S_SkillSound(this.d.fr(), 14539));
      }

      int[] var4 = E;
      int var3 = E.length;

      for (int var2 = 0; var2 < var3; var2++) {
         int var1 = var4[var2];
         if (this.b.bB(var1)) {
            return this.l = false;
         }
      }

      return this.l;
   }

   private boolean h() {
      int var1 = this.a.ev();
      var1 += this.a.aC().h(this.a.ev());
      if (this.s != 20 && this.s != 62) {
         var1 += CalcStat.b(this.a.bf(), this.a.ez());
         var1 += this.a.eT() + this.a.bW();
      } else {
         var1 += CalcStat.e(this.a.bh(), this.a.eB());
         var1 += this.a.eU() + this.a.bY();
      }

      if (this.a.j().h() > 33 && this.a.j().h() <= 50) {
         var1--;
      } else if (this.a.j().h() >= 51 && this.a.j().h() <= 66) {
         var1 -= 3;
      } else if (this.a.j().h() >= 67 && this.a.j().h() <= 82) {
         var1 -= 5;
      }

      var1 += this.y / 2;
      if (this.a.E() && this.s == 58) {
         return true;
      }

      if (this.d.ai()) {
         return false;
      }

      int var2 = 20;
      var2 += this.d.fk();
      var2 -= this.d.fl();
      int var3 = Random.a(var2) + 1;
      if (var3 >= 20 + this.d.fk()) {
         return false;
      }

      if (this.d.ey() < 0) {
         var1 -= Random.a((int)(-this.d.ey() * 1.5));
      } else {
         var1 += this.d.ey();
      }

      return this.s == 20 && var1 > var3 ? this.d.u() < Random.a(100) : var1 > var3;
   }

   private boolean i() {
      int var1 = this.a.ev();
      var1 += this.a.aC().h(this.a.ev());
      if (this.s != 20 && this.s != 62) {
         var1 += CalcStat.b(this.a.bf(), this.a.ez());
         var1 += this.a.eT() + this.a.bW();
      } else {
         var1 += CalcStat.e(this.a.bh(), this.a.eB());
         var1 += this.a.eU() + this.a.bY();
      }

      if (this.a.j().h() > 33 && this.a.j().h() <= 50) {
         var1--;
      } else if (this.a.j().h() >= 51 && this.a.j().h() <= 66) {
         var1 -= 3;
      } else if (this.a.j().h() >= 67 && this.a.j().h() <= 82) {
         var1 -= 5;
      }

      var1 += this.y / 2;
      if (this.a.E() && this.s == 58) {
         return true;
      }

      if (this.a.d(this.a, this.f.z())) {
         return false;
      }

      int var2 = 20;
      var2 += this.f.fk();
      var2 -= this.f.fl();
      int var3 = Random.a(var2) + 1;
      if (var3 >= 20 + this.f.fk()) {
         return false;
      }

      if (this.f.ey() < 0) {
         var1 -= Random.a((int)(-this.f.ey() * 1.5));
      } else {
         var1 += this.f.ey();
      }

      return var1 > var3;
   }

   private boolean j() {
      int var1 = this.e.ev();
      if (this.e instanceof L1PetInstance) {
         var1 += ((L1PetInstance)this.e).p();
      }

      var1 += this.e.eT() + this.e.J() + this.e.ez();
      if (!(this.e instanceof L1PetInstance) && !(this.e instanceof L1SummonInstance)
         || this.d.ep() != 1 && this.e.ep() != 1 && !this.d.a(this.d, this.e, false)) {
         if (this.d.ai()) {
            return false;
         }

         int var2 = 20;
         var2 += this.d.fk();
         var2 -= this.d.fl();
         int var3 = Random.a(var2) + 1;
         if (var3 >= 20 + this.d.fk()) {
            return false;
         }

         if (this.d.ey() < 0) {
            var1 -= Random.a(-this.d.ey());
         } else {
            var1 += this.d.ey();
         }

         return this.e.C() >= 10 && var1 > var3 && this.e.fu().c(new Point(this.b.fs(), this.b.ft())) >= 2 ? this.d.u() < Random.a(100) : var1 > var3;
      } else {
         return false;
      }
   }

   private boolean k() {
      int var1 = this.e.ev();
      if (this.e instanceof L1PetInstance) {
         var1 += ((L1PetInstance)this.e).p();
      }

      var1 += this.e.eT() + this.e.J() + this.e.ez();
      if (!(this.e instanceof L1PetInstance) && !(this.e instanceof L1SummonInstance)
         || !(this.f instanceof L1PetInstance) && !(this.f instanceof L1SummonInstance)
         || this.f.ep() != 1 && this.e.ep() != 1) {
         int var2 = 20;
         var2 += this.f.fk();
         var2 -= this.f.fl();
         int var3 = Random.a(var2) + 1;
         if (var3 >= 20 + this.f.fk()) {
            return false;
         }

         if (this.f.ey() < 0) {
            var1 -= Random.a(-this.f.ey());
         } else {
            var1 += this.f.ey();
         }

         return var1 > var3;
      } else {
         return false;
      }
   }

   public int b() {
      if (!this.c.i(this.b.fs(), this.b.ft())) {
         return this.m = 0;
      }

      if (this.g == 1) {
         this.m = this.l();
      } else if (this.g == 2) {
         this.m = this.m();
      } else if (this.g == 3) {
         this.m = this.o();
      } else if (this.g == 4) {
         this.m = this.p();
      }

      return this.m;
   }

   private int a(int var1) {
      int var2 = Random.a(var1) + 1;
      if (this.a.bB(175)) {
         var2 = var1;
      }

      var2 += this.t + this.y;
      if (this.y >= 10) {
         var2 += this.y - 9;
      }

      if (this.g == 2) {
         var2 += this.q();
      }

      var2 += this.r();
      int var3 = 1 + CalcStat.c(this.a.bf(), this.a.ez()) + this.a.dE();
      if (!this.a.C() || this.s != 58 && this.s != 54) {
         if (this.s == 20 || this.s == 62) {
            return this.r();
         }

         if (Random.a(100) < var3) {
            var2 = (int)(var1 * 2.5);
            this.n = 2;
         }
      } else if (Random.a(100) < this.A || this.a.bB(609) || Random.a(100) < var3) {
         var2 *= 3;
         this.n = this.s == 58 ? 2 : 4;
         if (this.a.bB(105) && Random.a(100) <= 33) {
            var2 *= 2;
         }

         if (this.a.bB(97) && this.a.bB(233) && this.a.h(609) && !this.a.bB(609)) {
            int var4 = 5;
            if (this.a.ev() >= 85 && this.a.ev() < 90) {
               var4++;
            } else if (this.a.ev() >= 90) {
               var4 += 2;
            }

            this.a.j(609, var4 * 1000);
            this.a.bz(97);
            this.a.bz(233);
            this.a.a(new S_SkillSound(this.a.fr(), 14547));
            this.a.b(new S_SkillSound(this.a.fr(), 14547));
            this.a.a(new S_ProtoBuffers(609, var4, 6, 7447, 7448, 4750, 4750, 4751, 1));
         }
      }

      return var2;
   }

   private double a(double var1) {
      int var3 = 1;
      if (this.s == 20 || this.s == 62) {
         if (this.q != null) {
            var3 = this.q.a().v();
            if (this.g == 2) {
               if (this.f.U_().q().equalsIgnoreCase("large")) {
                  var3 = this.q.a().w();
               }

               if (this.f.U_().W()) {
                  var3 /= 2;
               }
            }
         } else if (this.r == 190) {
            var3 = 15;
         } else if (this.r == 399) {
            var3 = 2;
         }
      }

      int var4 = 1 + CalcStat.f(this.a.bh(), this.a.eB()) + this.a.dF();
      double var5 = var1 + this.a.eS();
      if (Random.a(100) < var4) {
         var5 += var3 * 2 + 1;
         this.n = 2;
      } else {
         var5 += Random.a(var3) + 1;
      }

      var5 += this.a.bZ();
      if (this.a.bB(114)) {
         var5 += 5.0;
      }

      if (this.a.bB(4058)) {
         var5 += 100.0;
      }

      if (this.a.aj()) {
         L1DamagePoison.a(this.a, this.b, 3000, 5, 30);
      }

      if (this.a.ak()) {
         new S_029().a(this.b, -1);
      }

      if (this.a.al() && this.b instanceof L1NpcInstance) {
         new S_033().a(this.b, -1);
      }

      return var5;
   }

   private double b(double var1) {
      double var3 = var1 + this.a.eR() + this.a.ag();
      if (this.a.D()) {
         this.B();
      }

      var3 = this.c(var3);
      var3 += this.a.bX();
      if (this.s == 0) {
         var3 = (Random.a(5) + 4) / 4;
      } else if (this.a.E() && this.s == 58) {
         var3 = this.a(this.a, this.b);
      }

      int var5 = 0;
      if (this.a.bB(601) && Random.a(100) < 15) {
         var5++;
      }

      if (this.a.bB(601) && this.a.bB(602) && Random.a(100) < 5) {
         var5 += 2;
      }

      if (var5 > 0) {
         var3 += (2 + Math.max(this.a.ev() - 45, 0)) * var5;
         if (this.d != null) {
            this.d.a(new S_SkillSound(this.d.fr(), 12486 + var5));
         }

         this.b.b(new S_SkillSound(this.b.fr(), 12486 + var5));
      }

      if (this.d != null && this.d.bB(112)) {
         int var6 = 38 + (this.a.ev() - this.d.ev()) * (Random.a(3) + 2);
         if (this.a.bB(222)) {
            var6 += 7;
         }

         if (Random.a(100) < var6) {
            var3 *= 1.58;
         }
      }

      if (this.a.aj()) {
         L1DamagePoison.a(this.a, this.b, 3000, 5, 30);
      }

      if (this.a.ak()) {
         new S_029().a(this.b, -1);
      }

      if (this.a.al() && this.b instanceof L1NpcInstance) {
         new S_033().a(this.b, -1);
      }

      return var3;
   }

   private int l() {
      double var1 = this.a(this.u);
      if (this.s != 20 && this.s != 62) {
         var1 = this.b(var1 + CalcStat.a(this.a.bf(), this.a.ez()));
      } else {
         var1 = this.a(var1 + CalcStat.d(this.a.bh(), this.a.eB()));
      }

      if (this.r == 2) {
         var1 += this.a(this.a, this.d, this.p);
      } else {
         var1 += WeaponSkillTable.a().a(this.a, this.b, this.r);
      }

      double var3 = var1;
      var1 -= this.d.bV();
      if (this.d.dC() > 0 && Random.a(100) < 5) {
         var1 -= this.d.dC();
      }

      var1 -= this.d.ah();
      if (this.n()) {
         var1 -= 5.0;
      }

      if (this.d.bB(3015) || this.d.bB(3031) || this.d.bB(3047)) {
         var1 -= 5.0;
      }

      if (this.d.bB(88)) {
         int var5 = this.d.ev();
         if (var5 < 50) {
            var5 = 50;
         }

         var1 -= (var5 - 50) / 5 + 1;
      }

      if (this.d.bB(181)) {
         var1 -= 2.0;
      }

      if (this.d.bB(211)) {
         var1 -= 2.0;
      }

      if (this.d.bB(159)) {
         var1 -= 2.0;
      }

      if (this.d.bB(4058)) {
         var1 -= 60.0;
      }

      if (this.d.bB(68)) {
         var1 /= 2.0;
      } else if (this.d.dg() && Random.a(100) < 5) {
         this.d.a(new S_SkillSound(this.d.fr(), 11101));
         this.d.b(new S_SkillSound(this.d.fr(), 11101));
         this.d.j(68, 3000);
         this.d.a(new S_ServerMessage(314));
         this.d.a(new S_PacketBox(40, 3));
         var1 /= 2.0;
      } else if (this.d.di() && Random.a(100) < 5) {
         this.d.a(new S_SkillSound(this.d.fr(), 11101));
         this.d.b(new S_SkillSound(this.d.fr(), 11101));
         this.d.j(68, 10000);
         this.d.a(new S_PacketBox(40, 10));
         this.d.a(new S_ServerMessage(314));
         var1 /= 2.0;
      }

      var1 += this.a.dJ();
      var1 = var1 > var3 ? var3 : var1;
      if (this.d.dj() && Random.a(100) < 5) {
         this.d.a(new S_SkillSound(this.d.fr(), 14646));
         this.d.b(new S_SkillSound(this.d.fr(), 14646));
         this.d.a(this.d.ea() + 20 + Random.a(20));
      }

      if (this.d.dk() && Random.a(100) < 7) {
         this.d.a(new S_SkillSound(this.d.fr(), 14649));
         this.d.b(new S_SkillSound(this.d.fr(), 14649));
         this.d.a(this.d.ea() + 130 + Random.a(40));
      }

      if (this.d.l1r_do_effect_heal() > 0 && Random.a(100) < this.d.l1r_do_effect_heal()) {
         this.d.a(new S_SkillSound(this.d.fr(), 8909));
         this.d.b(new S_SkillSound(this.d.fr(), 8909));
         this.d.a(this.d.ea() + 130 + Random.a(40));
      }

      if (this.d.dh() && Random.a(100) < 7) {
         this.d.a(new S_SkillSound(this.d.fr(), 12357));
         this.d.b(new S_SkillSound(this.d.fr(), 12357));
         this.d.i_(this.d.eb() + 10);
      }

      if (this.d.dp() > 0 && Random.a(100) < this.d.dp()) {
         new S_135().a(this.d, 5);
      }

      var1 -= this.d.dB();
      var1 += this.a.dA();
      if (this.D == 203) {
         var1 += 15.0;
         if (this.s == 58) {
            var1 = 15.0;
         }
      } else if (this.D == 208) {
         var1 += 10.0;
         if (this.s == 58) {
            var1 = 10.0;
         }
      }

      if (this.a.du() && Random.a(100) < 7) {
         var1 += 100.0;
         this.a.a(new S_SkillSound(this.a.fr(), 10243));
         this.a.b(new S_SkillSound(this.a.fr(), 10243));
      }

      if (this.a.dv() && Random.a(100) < 8) {
         var1 += 150.0;
         this.a.a(new S_SkillSound(this.a.fr(), 10243));
         this.a.b(new S_SkillSound(this.a.fr(), 10243));
      }

      if (this.a.dw() && Random.a(100) < 9) {
         var1 += 200.0;
         this.a.a(new S_SkillSound(this.a.fr(), 10243));
         this.a.b(new S_SkillSound(this.a.fr(), 10243));
      }

      if (this.a.dm() && Random.a(100) < 7) {
         var1 += 80.0;
         this.d.a(new S_SkillSound(this.d.fr(), 13542));
         this.d.b(new S_SkillSound(this.d.fr(), 13542));
      }

      if (this.a.dt() && Random.a(100) < 3) {
         var1 += 220.0;
         this.d.a(new S_SkillSound(this.d.fr(), 12157));
         this.d.b(new S_SkillSound(this.d.fr(), 12157));
      }

      if (this.a.dq() > 0 && Random.a(100) < this.a.dq()) {
         var1 += 180.0;
         this.d.a(new S_SkillSound(this.d.fr(), 13338));
         this.d.b(new S_SkillSound(this.d.fr(), 13338));
      }

      if (this.a.dx() && Random.a(100) < 10) {
         var1 += WeaponSkillTable.a().a(this.a, this.d, 14449, 4, 50.0);
      }

      if ((this.a.dr() || this.a.ds()) && Random.a(100) < 7) {
         int var16 = this.a.dr() ? 160 : 60;
         int var6 = this.a.dr() ? 11677 : 11673;
         if (!this.a.A() && !this.a.B() && !this.a.E()) {
            var16 += 80;
         }

         L1Magic var7 = new L1Magic(this.a, this.d);
         var7.a(var16, 0);
         this.a.a(var16 + this.a.ea());
         this.d.a(new S_SkillSound(this.d.fr(), var6));
         this.d.b(new S_SkillSound(this.d.fr(), var6));
      }

      if (this.a.dn() > 0 && Random.a(100) < this.a.dn()) {
         L1SpawnEffect.a().a(4184, 1000, this.d.fs(), this.d.ft(), this.d.fp());
         this.d.j(1028, 1000);
         this.d.a(new S_Paralysis(6, true));
      }

      if (this.a.da() && Random.a(100) < 5) {
         this.a.a(new S_SkillSound(this.a.fr(), 13749));
         this.a.b(new S_SkillSound(this.a.fr(), 13749));
         if (!this.a.bB(1027) && !this.a.bB(1038)) {
            this.a.j(1027, 5000);
            this.a.a(new S_Liquor(this.a.fr(), 8));
            this.a.b(new S_Liquor(this.a.fr(), 8));
         }
      }

      if (this.d.db() && Random.a(100) < 10) {
         this.d.a(new S_SkillSound(this.d.fr(), 13702));
         this.d.b(new S_SkillSound(this.d.fr(), 13702));
         var1 -= 30.0;
      }

      if (this.d.df() > Random.a(100)) {
         this.d.a(new S_SkillSound(this.d.fr(), 13702));
         this.d.b(new S_SkillSound(this.d.fr(), 13702));
         var1 -= 50.0;
      }

      if (this.d.dc() && var1 > 0.0 && Random.a(100) < 7) {
         this.d.a(new S_SkillSound(this.a.fr(), 9801));
         this.d.b(new S_SkillSound(this.a.fr(), 9801));
         L1Magic var17 = new L1Magic(this.d, this.a);
         var17.a(80, 0);
         this.a.a(new S_DoActionGFX(this.a.fr(), 2));
         this.a.b(new S_DoActionGFX(this.a.fr(), 2));
      }

      if (this.d.dl() && var1 > 0.0 && Random.a(100) < 7) {
         this.d.a(new S_SkillSound(this.d.fr(), 14453));
         this.d.b(new S_SkillSound(this.d.fr(), 14453));
         L1Magic var18 = new L1Magic(this.d, this.a);
         var18.a(120, 0);
         this.a.a(new S_DoActionGFX(this.a.fr(), 2));
         this.a.b(new S_DoActionGFX(this.a.fr(), 2));
      }

      if (var1 < 0.0) {
         var1 = 1.0;
      }

      return (int)var1;
   }

   private int m() {
      int var1 = 0;
      if (this.f.U_().q().equalsIgnoreCase("small") && this.u > 0) {
         var1 = this.u;
      } else if (this.f.U_().q().equalsIgnoreCase("large") && this.v > 0) {
         var1 = this.v;
      }

      double var2 = this.a(var1);
      if (this.s != 20 && this.s != 62) {
         var2 = this.b(var2 + CalcStat.a(this.a.bf(), this.a.ez()));
      } else {
         var2 = this.a(var2 + CalcStat.d(this.a.bh(), this.a.eB()));
      }

      if (this.r == 2) {
         var2 += this.a(this.a, this.f, this.p);
      } else {
         var2 += WeaponSkillTable.a().a(this.a, this.b, this.r);
      }

      var2 -= this.f.U_().V();
      if (this.D == 203) {
         var2 += 15.0;
         if (this.a.E() && this.s == 58) {
            var2 = 15.0;
         }
      } else if (this.D == 208) {
         var2 += 10.0;
         if (this.a.E() && this.s == 58) {
            var2 = 10.0;
         }
      }

      if (this.a.du() && Random.a(100) < 7) {
         var2 += 100.0;
         this.a.a(new S_SkillSound(this.a.fr(), 10243));
         this.a.b(new S_SkillSound(this.a.fr(), 10243));
      }

      if (this.a.dv() && Random.a(100) < 8) {
         var2 += 150.0;
         this.a.a(new S_SkillSound(this.a.fr(), 10243));
         this.a.b(new S_SkillSound(this.a.fr(), 10243));
      }

      if (this.a.dw() && Random.a(100) < 9) {
         var2 += 200.0;
         this.a.a(new S_SkillSound(this.a.fr(), 10243));
         this.a.b(new S_SkillSound(this.a.fr(), 10243));
      }

      if (this.a.dm() && Random.a(100) < 7) {
         var2 += 80.0;
         this.f.b(new S_SkillSound(this.f.fr(), 13542));
      }

      if (this.a.dt() && Random.a(100) < 3) {
         var2 += 220.0;
         this.f.b(new S_SkillSound(this.f.fr(), 12157));
      }

      if (this.a.dx() && Random.a(100) < 10) {
         var2 += WeaponSkillTable.a().a(this.a, this.f, 14449, 4, 50.0);
      }

      if ((this.a.dr() || this.a.ds()) && Random.a(100) < 7) {
         int var4 = this.a.dr() ? 160 : 60;
         int var5 = this.a.dr() ? 11677 : 11673;
         L1Magic var6 = new L1Magic(this.a, this.f);
         var6.a(var4, 0);
         this.a.a(var4 + this.a.ea());
         this.f.b(new S_SkillSound(this.f.fr(), var5));
      }

      if (this.a.dn() > 0 && Random.a(100) < this.a.dn()) {
         L1SpawnEffect.a().a(4184, 1000, this.f.fs(), this.f.ft(), this.f.fp());
         this.f.j(1028, 2000);
         this.f.n(true);
      }

      if (this.a.dq() > 0 && Random.a(100) < this.a.dq()) {
         var2 += 180.0;
         this.f.b(new S_SkillSound(this.f.fr(), 13338));
      }

      if (this.a.da() && Random.a(100) < 5) {
         this.a.a(new S_SkillSound(this.a.fr(), 13749));
         this.a.b(new S_SkillSound(this.a.fr(), 13749));
         if (!this.a.bB(1027) && !this.a.bB(1038)) {
            this.a.j(1027, 5000);
            this.a.a(new S_Liquor(this.a.fr(), 8));
            this.a.b(new S_Liquor(this.a.fr(), 8));
         }
      }

      if ((this.f instanceof L1PetInstance || this.f instanceof L1SummonInstance) && this.f.L() && !L1CastleWar.a().a(this.f)) {
         var2 /= 8.0;
      }

      if (var2 <= 0.0) {
         this.l = false;
      }

      return (int)var2;
   }

   private boolean n() {
      int[] var4 = F;
      int var3 = F.length;

      for (int var2 = 0; var2 < var3; var2++) {
         int var1 = var4[var2];
         if (this.d.bB(var1)) {
            return true;
         }
      }

      return false;
   }

   private int o() {
      double var1 = this.e.J() + Random.a(this.e.K());
      if (this.e instanceof L1PetInstance) {
         var1 += this.e.ev() / 16;
         var1 += ((L1PetInstance)this.e).au();
      }

      var1 += this.e.eR();
      if (this.s()) {
         var1 *= 1.1;
      }

      int var3 = Math.max(0, 10 - this.d.ey());
      int var4 = this.d.aC().b(var3);
      var1 -= Random.a(var4 + 1);
      if (this.e.ab()) {
         var1 /= 2.0;
      }

      var1 -= this.d.bV();
      var1 -= this.d.ah();
      if (this.n()) {
         var1 -= 5.0;
      }

      if (this.d.bB(3015) || this.d.bB(3031) || this.d.bB(3047)) {
         var1 -= 5.0;
      }

      if (this.d.bB(88)) {
         int var5 = this.d.ev();
         if (var5 < 50) {
            var5 = 50;
         }

         var1 -= (var5 - 50) / 5 + 1;
      }

      if (this.d.bB(181)) {
         var1 -= 2.0;
      }

      if (this.d.bB(211)) {
         var1 -= 2.0;
      }

      if (this.d.bB(159)) {
         var1 -= 2.0;
      }

      if (this.d.dC() > 0 && Random.a(100) < 5) {
         var1 -= this.d.dC();
      }

      if (this.d.bB(605) && Random.a(100) < 5) {
         var1 -= Math.abs(this.d.ey()) / 10;
         this.d.a(new S_SkillSound(this.d.fr(), 12536));
         this.d.b(new S_SkillSound(this.d.fr(), 12536));
      }

      if (this.d.db() && Random.a(100) < 10) {
         this.d.a(new S_SkillSound(this.d.fr(), 13702));
         this.d.b(new S_SkillSound(this.d.fr(), 13702));
         var1 -= 30.0;
      }

      if (this.d.df() > Random.a(100)) {
         this.d.a(new S_SkillSound(this.d.fr(), 13702));
         this.d.b(new S_SkillSound(this.d.fr(), 13702));
         var1 -= 50.0;
      }

      if (this.d.bB(4058)) {
         var1 -= 60.0;
      }

      if (this.d.bB(68)) {
         var1 /= 2.0;
      } else if (this.d.dg() && Random.a(100) < 5) {
         this.d.a(new S_SkillSound(this.d.fr(), 11101));
         this.d.b(new S_SkillSound(this.d.fr(), 11101));
         this.d.j(68, 3000);
         this.d.a(new S_ServerMessage(314));
         this.d.a(new S_PacketBox(40, 3));
         var1 /= 2.0;
      } else if (this.d.di() && Random.a(100) < 5) {
         this.d.a(new S_SkillSound(this.d.fr(), 11101));
         this.d.b(new S_SkillSound(this.d.fr(), 11101));
         this.d.j(68, 10000);
         this.d.a(new S_ServerMessage(314));
         this.d.a(new S_PacketBox(40, 10));
         var1 /= 2.0;
      }

      if (this.d.dj() && Random.a(100) < 5) {
         this.d.a(new S_SkillSound(this.d.fr(), 14646));
         this.d.b(new S_SkillSound(this.d.fr(), 14646));
         this.d.a(this.d.ea() + 20 + Random.a(20));
      }

      if (this.d.dk() && Random.a(100) < 7) {
         this.d.a(new S_SkillSound(this.d.fr(), 14649));
         this.d.b(new S_SkillSound(this.d.fr(), 14649));
         this.d.a(this.d.ea() + 130 + Random.a(40));
      }

      if (this.d.l1r_do_effect_heal() > 0 && Random.a(100) < this.d.l1r_do_effect_heal()) {
         this.d.a(new S_SkillSound(this.d.fr(), 8909));
         this.d.b(new S_SkillSound(this.d.fr(), 8909));
         this.d.a(this.d.ea() + 130 + Random.a(40));
      }

      if (this.d.dp() > 0 && Random.a(100) < this.d.dp()) {
         new S_135().a(this.d, 5);
      }

      if (this.d.dh() && Random.a(100) < 7) {
         this.d.a(new S_SkillSound(this.d.fr(), 12357));
         this.d.b(new S_SkillSound(this.d.fr(), 12357));
         this.d.i_(this.d.eb() + 10);
      }

      if (this.d.dc() && var1 > 0.0 && Random.a(100) < 7) {
         this.d.a(new S_SkillSound(this.e.fr(), 9801));
         this.d.b(new S_SkillSound(this.e.fr(), 9801));
         L1Magic var11 = new L1Magic(this.d, this.e);
         var11.a(80, 0);
         this.e.b(new S_DoActionGFX(this.e.fr(), 2));
      }

      if (this.d.dl() && var1 > 0.0 && Random.a(100) < 7) {
         this.d.a(new S_SkillSound(this.d.fr(), 14453));
         this.d.b(new S_SkillSound(this.d.fr(), 14453));
         L1Magic var12 = new L1Magic(this.d, this.e);
         var12.a(120, 0);
         this.e.b(new S_DoActionGFX(this.e.fr(), 2));
      }

      if (var1 <= 0.0) {
         this.l = false;
      }

      this.b(this.e, this.d);
      return (int)var1;
   }

   private int p() {
      double var1 = this.e.J() + Random.a(this.e.K());
      if (this.e instanceof L1PetInstance) {
         var1 += this.e.ev() / 16;
         var1 += ((L1PetInstance)this.e).au();
      }

      if (this.s()) {
         var1 *= 1.1;
      }

      var1 -= this.f.U_().V();
      if (this.e.ab()) {
         var1 /= 2.0;
      }

      this.b(this.e, this.f);
      if (var1 <= 0.0) {
         this.l = false;
      }

      return (int)var1;
   }

   private double c(double var1) {
      if ((this.a.bB(102) || this.a.bB(171) || this.a.bB(117)) && Random.a(100) < 33) {
         var1 *= 1.5;
      }

      if (this.a.D() && this.s == 24) {
         var1 += this.a.dI();
         if (this.a.bB(5003)) {
            var1 += 9.0;
         } else if (this.a.bB(5002)) {
            var1 += 6.0;
         } else if (this.a.bB(5001)) {
            var1 += 3.0;
         }
      }

      if (this.a.cM() && this.a.bB(5003)) {
         var1 *= 1.3;
      }

      if (this.a.bB(182)) {
         var1 += 10.0;
         this.a.a(new S_EffectLocation(this.b.fs(), this.b.ft(), 6591));
         this.a.b(new S_EffectLocation(this.b.fs(), this.b.ft(), 6591));
         this.a.bA(182);
      }

      if (this.a.bB(114)) {
         var1 += 5.0;
      }

      if (this.a.bB(4058)) {
         var1 += 100.0;
      }

      return var1;
   }

   private int q() {
      int var1 = 0;
      int var2 = this.f.U_().B();
      if (this.z != 14 && this.z != 17 && this.z != 22 || var2 != 1 && var2 != 3 && var2 != 5) {
         if ((this.z == 17 || this.z == 22) && var2 == 2) {
            var1 += Random.a(3) + 1;
         }
      } else {
         var1 += Random.a(20) + 1;
      }

      if (this.x == 0 && (var2 == 1 || var2 == 2 || var2 == 3)) {
         var1 += Random.a(4) + 1;
      }

      if (this.a.v() != null && this.s != 20 && this.s != 62 && this.p.Q() != 0 && (var2 == 1 || var2 == 3)) {
         var1 += this.p.Q();
      }

      return var1;
   }

   private int r() {
      if (this.C <= 0) {
         return 0;
      }

      int var1 = this.C * 2 - 1;
      int var2 = 0;
      if (this.g == 1) {
         if (this.B == 1) {
            var2 = this.d.eI();
         } else if (this.B == 2) {
            var2 = this.d.eH();
         } else if (this.B == 4) {
            var2 = this.d.eG();
         } else if (this.B == 8) {
            var2 = this.d.eF();
         }
      } else if (this.g == 2 && this.B == this.f.U_().r()) {
         var2 = -50;
      }

      double var3 = 0.32 * Math.abs(var2);
      if (var2 >= 0) {
         var3 *= 1.0;
      } else {
         var3 *= -1.0;
      }

      double var5 = 1.0 - var3 / 32.0;
      return (int)(var1 * var5);
   }

   private boolean s() {
      int var1 = this.e.U_().B();
      return (var1 == 1 || var1 == 3 || var1 == 4) && L1GameTimeClock.a().b().e();
   }

   private void b(L1Character var1, L1Character var2) {
      if (this.e.U_().C() != 0) {
         if (Random.a(100) < 15) {
            if (this.e.U_().C() == 1) {
               L1DamagePoison.a(var1, var2, 3000, 5, 30);
            } else if (this.e.U_().C() == 2) {
               L1SilencePoison.b(var2, 120);
            } else if (this.e.U_().C() == 4) {
               L1ParalysisPoison.a(var2, 20000, 15000);
            }
         }
      }
   }

   public void a(L1Character var1, L1Character var2) {
      if ((this.r == 13 || this.r == 44 || this.r != 0 && this.a.bB(98)) && Random.a(100) < 10) {
         L1DamagePoison.a(var1, var2, 3000, 5, 30);
      }
   }

   public void c() {
      if (this.g == 0 || this.g == 1 || this.g == 2) {
         this.t();
      } else if (this.g == 3 || this.g == 4) {
         this.u();
      }
   }

   private void t() {
      this.a.ct(this.a.h(this.b.fs(), this.b.ft()));
      if (this.s == 20 && (this.q != null || this.r == 190 || this.r == 399)) {
         if (this.q != null) {
            this.a.j().b(this.q, 1);
            this.o = 66;
            if (this.a.fe() == 8719) {
               this.o = 8721;
            } else if (this.a.fe() == 8900) {
               this.o = 8904;
            } else if (this.a.fe() == 8913) {
               this.o = 8916;
            } else if (this.a.fe() == 11402) {
               this.o = 8904;
            } else if (this.a.fe() == 11406) {
               this.o = 8916;
            } else if (this.a.fe() == 13635) {
               this.o = 13656;
            } else if (this.a.fe() == 13635) {
               this.o = 13658;
            } else if (this.a.fe() == 12314) {
               this.o = 8916;
            } else if (this.a.fe() == 15814) {
               this.o = 8916;
            }

            if (this.a.cL()) {
               this.o = 11762;
               if (this.a.fe() == 13635) {
                  this.o = 13657;
               } else if (this.a.fe() == 13635) {
                  this.o = 13659;
               } else if (this.a.fe() == 11402) {
                  this.o = 8904;
               } else if (this.a.fe() == 11406) {
                  this.o = 8916;
               } else if (this.a.fe() == 12314) {
                  this.o = 8916;
               } else if (this.a.fe() == 15814) {
                  this.o = 8916;
               }
            }
         } else if (this.r == 190 || this.r == 399) {
            this.o = 2349;
         }
      } else if (this.s == 62 && this.q != null) {
         this.a.j().b(this.q, 1);
         this.o = 2989;
      }

      if (!this.l) {
         this.m = 0;
      }

      if (this.o > 0) {
         this.a.a(new S_AttackPacket(this.a, this.b, 1, this.o, this.m, 0, this.n));
         this.a.b(new S_AttackPacket(this.a, this.b, 1, this.o, this.m, 0, this.n));
      } else {
         this.a.a(new S_AttackPacket(this.a, this.b.fr(), 1, this.m, this.n));
         this.a.b(new S_AttackPacket(this.a, this.b.fr(), 1, this.m, this.n));
      }

      if (this.l) {
         this.b.a(new S_DoActionGFX(this.b.fr(), 2), this.a);
      }
   }

   private void u() {
      int var1 = ListSprReader__obf_c.a().a(this.e.fe(), this.e.U_().Z() > 0);
      this.e.ct(this.e.h(this.b.fs(), this.b.ft()));
      boolean var2 = false;
      if (this.e.C() > 1) {
         var2 = this.e.fu().c(new Point(this.b.fs(), this.b.ft())) > 1;
      }

      int var3 = this.e.am();
      if (var3 == 0) {
         var3 = this.e.U_().Z();
      }

      if (Random.a(100) < 40) {
         this.m = (int)(this.m * 1.2);
      } else if (!var2 || var3 == 0) {
         this.m = (int)(this.m * (var3 > 0 ? 1.2 : 1.0));
      }

      if (!this.l) {
         this.m = 0;
      }

      if (var3 > 0 && var2) {
         this.e.b(new S_AttackPacket(this.e, this.b, var1, var3, this.m, 0, 0));
      } else {
         this.e.b(new S_AttackPacket(this.e, this.b.fr(), var1, this.m, 0));
      }

      if (this.l) {
         this.b.a(new S_DoActionGFX(this.b.fr(), 2), this.e);
      }
   }

   public void d() {
      if (this.l) {
         if (this.g == 1 || this.g == 3) {
            this.v();
         } else if (this.g == 2 || this.g == 4) {
            this.w();
         }
      }

      if ((this.g == 1 || this.g == 2) && this.m > this.a.bz() && this.a.fp() != 413 && (this.a.fp() < 2600 || this.a.fp() > 2698)) {
         this.a.ax(this.m);
      }

      if (Config.S) {
         if ((this.g == 1 || this.g == 2) && this.a.l()) {
            this.a.a(new S_NpcChatPacket(this.b, "\\\\fRfU↓ 普攻傷害\\\\fRfM (" + (this.l ? this.m : "miss") + ")"));
            this.a.a(new S_SystemMessage("對" + this.b.et() + "造成普攻傷害= " + (this.l ? this.m : "miss")));
         } else if (this.g == 1 || this.g == 3) {
            this.d.l();
         }
      }
   }

   private void v() {
      if (this.g == 1) {
         this.x();
         if (this.a.bB(196)) {
            this.y();
         }

         this.a(this.a, this.d, this.m);
         this.d.a(this.a, this.m, false);
      } else if (this.g == 3) {
         this.d.a(this.e, this.m, false);
      }
   }

   private void w() {
      if (this.g == 2) {
         if (this.f.U_().W()) {
            this.z();
         }

         this.a(this.a, this.f, this.m);
         this.f.b(this.a, this.m);
      } else if (this.g == 4) {
         this.f.b(this.e, this.m);
      }
   }

   private void x() {
      if (this.s != 0 && this.s != 20 && this.s != 62) {
         if (this.d.bB(89) && !this.a.bB(175)) {
            if (Random.a(100) < 10) {
               this.a.a(new S_ServerMessage(268, this.p.s()));
               this.a.j().g(this.p);
            }
         }
      }
   }

   private void y() {
      L1ItemInstance var1 = this.d.j().j(2);
      if (var1 != null) {
         if (Random.a(100) < 5) {
            this.d.a(new S_ServerMessage(268, var1.s()));
            this.d.j().g(var1);
         }
      }
   }

   private void z() {
      if (this.s != 0 && this.s != 20 && this.s != 62 && this.s != 40 && this.s != 58 && this.s != 58) {
         if (this.p.a().aE() && !this.a.bB(175)) {
            if ((this.x == 1 || this.x == 2) && Random.a(100) < 10 || this.x == 0 && Random.a(100) < 3) {
               this.a.a(new S_ServerMessage(268, this.p.s()));
               this.a.j().g(this.p);
            }
         }
      }
   }

   public void e() {
      int var1 = 0;
      if (this.g == 1) {
         this.a.ct(this.a.h(this.b.fs(), this.b.ft()));
         if (this.d.z()) {
            var1 = 5846;
         } else if (this.d.D()) {
            var1 = 9802;
         } else if (this.d.F()) {
            var1 = this.f() ? 12555 : 12557;
         }

         this.a.a(new S_AttackPacket(this.a, this.b.fr(), 1, 0, 0));
         this.a.b(new S_AttackPacket(this.a, this.b.fr(), 1, 0, 0));
         this.a.a(new S_SkillSound(this.a.fr(), var1));
         this.a.b(new S_SkillSound(this.a.fr(), var1));
         this.a.a(new S_DoActionGFX(this.a.fr(), 2));
         this.a.b(new S_DoActionGFX(this.a.fr(), 2));
      } else if (this.g == 3) {
         this.e.ct(this.e.h(this.b.fs(), this.b.ft()));
         if (this.d.z()) {
            var1 = 5846;
         } else if (this.d.D()) {
            var1 = 9802;
         } else if (this.d.F()) {
            var1 = this.f() ? 12555 : 12557;
         }

         this.e.b(new S_AttackPacket(this.e, this.b.fr(), 1, 0, 0));
         this.e.b(new S_SkillSound(this.e.fr(), var1));
         this.e.b(new S_DoActionGFX(this.e.fr(), 2));
      }
   }

   public boolean f() {
      if (this.g == 1) {
         if (this.s == 20 || this.s == 62 || this.s == 58) {
            return false;
         }
      } else if (this.g == 3) {
         boolean var1 = this.e.fu().c(new Point(this.b.fs(), this.b.ft())) > 1;
         int var2 = this.e.am();
         if (var2 == 0) {
            var2 = this.e.U_().Z();
         }

         if (var1 && var2 > 0) {
            return false;
         }
      }

      return true;
   }

   public void g() {
      int var1 = this.m + this.A();
      if (var1 != 0) {
         if (this.g == 1) {
            this.a.a(this.d, var1, false);
         } else if (this.g == 3) {
            this.e.b(this.d, var1);
         }
      }
   }

   private int A() {
      int var1 = 0;
      if (this.s == 257) {
         var1 = (this.p.a().w() + this.p.G() + this.p.a().ac()) * 2;
      }

      return var1;
   }

   private void B() {
      if (this.s == 24) {
         int var1 = Random.a(100) + 1;
         if (this.a.bB(5003)) {
            if (var1 > 30 && var1 <= 60) {
               this.a.j(5003, 16000);
               this.a.a(new S_PacketBox(75, 3));
            }
         } else if (this.a.bB(5002)) {
            if (var1 <= 30) {
               this.a.j(5002, 16000);
               this.a.a(new S_PacketBox(75, 2));
            } else if (var1 >= 70) {
               this.a.j(5003, 16000);
               this.a.a(new S_PacketBox(75, 3));
            }
         } else if (this.a.bB(5001)) {
            if (var1 <= 40) {
               this.a.j(5001, 16000);
               this.a.a(new S_PacketBox(75, 1));
            } else if (var1 >= 70) {
               this.a.j(5002, 16000);
               this.a.a(new S_PacketBox(75, 2));
            }
         } else {
            int var2 = this.p.N() == 354 ? 18 : 12;
            if (var1 <= var2) {
               this.a.j(5001, 16000);
               this.a.a(new S_PacketBox(75, 1));
            }
         }
      }
   }

   private double a(L1PcInstance var1, L1Character var2, L1ItemInstance var3) {
      double var4 = 0.0;
      if (Random.a(100) < 3) {
         var4 = var2.ea() * 2 / 3;
         var1.a(new S_ServerMessage(158, var3.s()));
         var1.j().f(var3);
      }

      return var4;
   }

   private double a(L1PcInstance var1, L1Character var2) {
      int var3 = 0;
      int var4 = 2 + var1.v().a().J() > 0 ? 1 : 0;

      for (int var5 = 0; var5 < var4; var5++) {
         var3 += Random.a(5) + 1;
      }

      int var8 = var1.v().a().v() / 3 + var3 + CalcStat.g(var1.bj(), var1.eD());
      double var6 = var8 + var1.v().G();
      var6 = L1Magic.a(var1, var2, var6, 0);
      if (var1.bB(219)) {
         var6 += 10.0;
      }

      if (var1.v().N() == 270) {
         var1.a(new S_SkillSound(var1.fr(), 6983));
         var1.b(new S_SkillSound(var1.fr(), 6983));
      } else {
         var1.a(new S_SkillSound(var1.fr(), 7049));
         var1.b(new S_SkillSound(var1.fr(), 7049));
      }

      return var6;
   }

   private void a(L1PcInstance var1, L1Character var2, int var3) {
      L1ItemInstance var4 = var1.v();
      if (var4 != null) {
         int var5 = 100;
         int var6 = var4.G();
         int[] var7 = new int[]{10966, 11760, 11758, 8531, 5295, 5377, 6703, 7066, 10779, 5627, 5550, 7974, 10698, 11525, 11541, 11557, 11449, 8110};
         if (var6 >= 100) {
            int var8 = var6 - 100 + 1;
            int var9 = var6 - 100;
            var9 = var9 < var7.length ? var9 : var7.length - 1;
            int var10 = var7[var9];
            if (var2 instanceof L1PcInstance) {
               ((L1PcInstance)var2).a(new S_SkillSound(var2.fr(), var10));
            }

            var2.b(new S_SkillSound(var2.fr(), var10));

            for (L1Object var11 : var1.eq()) {
               if (var11 instanceof L1MonsterInstance && var11.fu().c(var2.fu()) <= var8) {
                  ((L1MonsterInstance)var11).b(var1, var3);
               }
            }
         }
      }
   }
}
