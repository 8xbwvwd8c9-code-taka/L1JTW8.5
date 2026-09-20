package l1r.aq;

import l1r.ao.SkillsTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.as.L1CastleWar;
import l1r.be.S_DoActionGFX;
import l1r.be.S_NpcChatPacket;
import l1r.be.S_PacketBox;
import l1r.be.S_SkillSound;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Skills;
import l1r.bi.CalcStat;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class L1Magic {
   private static final int a = 1;
   private static final int b = 2;
   private static final int c = 3;
   private static final int d = 4;
   private int e = 0;
   private L1Character f = null;
   private L1Character g = null;
   private L1PcInstance h = null;
   private L1PcInstance i = null;
   private L1NpcInstance j = null;
   private L1NpcInstance k = null;
   private static int[] l = new int[]{44, 27, 29, 33, 39, 40, 47, 56, 71, 76, 66, 50, 152, 153, 157, 161, 167, 174, 173, 133, 145, 87, 15003, 15004};
   private static final int[] m = new int[]{78, 50, 157, 15003, 15004, 120};
   private boolean n = false;

   public L1Magic(L1Character var1, L1Character var2) {
      this.g = var1;
      this.f = var2;
      if (var1 instanceof L1PcInstance) {
         if (var2 instanceof L1PcInstance) {
            this.e = 1;
            this.h = (L1PcInstance)var1;
            this.i = (L1PcInstance)var2;
         } else if (var2 instanceof L1NpcInstance) {
            this.e = 2;
            this.h = (L1PcInstance)var1;
            this.k = (L1NpcInstance)var2;
         }
      } else if (var2 instanceof L1PcInstance) {
         this.e = 3;
         this.j = (L1NpcInstance)var1;
         this.i = (L1PcInstance)var2;
      } else if (var2 instanceof L1NpcInstance) {
         this.e = 4;
         this.j = (L1NpcInstance)var1;
         this.k = (L1NpcInstance)var2;
      }
   }

   public boolean a(int var1) {
      if (this.g == null || this.f == null) {
         return false;
      }

      if (this.f.bB(31)) {
         this.f.bz(31);
         return false;
      }

      if (var1 == 44) {
         if (this.e != 1) {
            return true;
         }

         if (this.h.fr() == this.i.fr()) {
            return true;
         }

         if (this.h.aF() > 0 && this.h.aF() == this.i.aF()) {
            return true;
         }

         if (this.h.q() && this.h.aL().d(this.i)) {
            return true;
         }
      }

      if (this.e == 1) {
         if (this.h.fu().c() || this.i.fu().c()) {
            int[] var5 = l;
            int var4 = l.length;

            for (int var3 = 0; var3 < var4; var3++) {
               int var2 = var5[var3];
               if (var1 == var2) {
                  return false;
               }
            }
         }
      } else if (this.e == 2 && this.h.d(this.h, this.k.U_().b())) {
         return false;
      }

      if (this.f.bB(157) && var1 != 27 && var1 != 44) {
         return false;
      }

      if (this.f.bB(120)) {
         return false;
      }

      int var6 = this.d(var1);
      if (var6 > 90) {
         var6 = 90;
      }

      boolean var7 = Random.a(100) < var6;
      if (Config.S) {
         if (this.g instanceof L1PcInstance && this.h.l()) {
            this.h.a(new S_SystemMessage("對" + this.f.et() + " 施放魔法" + (var7 ? "\\aL成功" : "\\aG失敗") + " (機率=" + var6 + "%)"));
         } else if (this.f instanceof L1PcInstance && this.i.l()) {
            this.i.a(new S_SystemMessage(this.g.et() + "對你施放魔法" + (var7 ? "\\aL成功" : "\\aG失敗") + " (機率=" + var6 + "%)"));
         }
      }

      return var7;
   }

   private int d(int var1) {
      L1Skills var2 = SkillsTable.a().a(var1);
      int var3 = this.g.ev();
      int var4 = this.f.ev();
      int var5 = 0;
      if (var1 == 230 || var1 == 228) {
         var5 = var2.l() + (var3 - var4) * 5;
         if (this.h.bB(222)) {
            var5 += 7;
         }
      } else if (var1 == 87) {
         var5 = var2.l() + (var3 - var4) * 2;
         if (this.h.bB(222)) {
            var5 += 7;
         }
      } else if (var1 == 91) {
         var5 = var2.l() + var3 - var4;
      } else if (var1 != 183 && var1 != 188 && var1 != 192 && var1 != 193) {
         var5 = var2.l();
         var5 += (this.g.U() + CalcStat.c(this.g.eD())) * var2.m();
         int var6 = 0;
         if (this.g instanceof L1PcInstance) {
            var6 = this.h.dD() + CalcStat.h(this.h.bj(), this.h.eD());
         }

         int var7 = this.f.W_();
         if (var7 > 150) {
            var7 = (int)(150.0 + (var7 - 150) * 0.3);
         }

         var5 += var6 - var7;
         if (var1 == 36) {
            double var8 = 0.8 + (double)(this.k.ew() - this.k.ea()) / this.k.ew();
            var5 = (int)(var5 * var8);
         } else if (var1 == 208 && this.h.bB(222)) {
            var5 += 7;
         }
      } else {
         var5 = var2.l() + (var3 - var4) * 3;
         var5 += this.g.eD() * var2.m();
         var5 = (int)(var5 - this.f.W_() / 2.5);
      }

      if (var1 == 157) {
         if (this.e == 1 || this.e == 3) {
            var5 -= this.i.eO();
         }
      } else if (var1 == 87) {
         if (this.e == 1 || this.e == 3) {
            var5 -= 2 * this.i.eK();
         }

         if (this.e == 1 || this.e == 2) {
            var5 += this.h.dK();
         }
      } else if (var1 == 33) {
         if (this.e == 1 || this.e == 3) {
            var5 -= this.i.eL();
         }
      } else if (var1 == 66) {
         if (this.e == 1 || this.e == 3) {
            var5 -= this.i.eM();
         }
      } else if (var1 != 50 && var1 != 15003 && var1 != 15004) {
         if (var1 != 20 && var1 != 40) {
            if (var1 == 230 && (this.e == 1 || this.e == 3)) {
               var5 -= this.i.eQ();
            }
         } else if (this.e == 1 || this.e == 3) {
            var5 -= this.i.eP();
         }
      } else if (this.e == 1 || this.e == 3) {
         var5 -= this.i.eN();
         if (a(this.i)) {
            return 0;
         }
      }

      return var5;
   }

   public static boolean a(L1Character var0) {
      int[] var4 = m;
      int var3 = m.length;

      for (int var2 = 0; var2 < var3; var2++) {
         int var1 = var4[var2];
         if (var0.bB(var1)) {
            return true;
         }
      }

      if (var0.bB(31)) {
         var0.bz(31);
         return true;
      } else {
         return false;
      }
   }

   public int b(int var1) {
      if (this.g == null || this.f == null) {
         return 0;
      }

      if (a(this.f)) {
         return 0;
      }

      if (this.f.bB(608)) {
         int var2 = 0;
         double var3 = 0.4;
         if (this.f instanceof L1PcInstance) {
            L1PcInstance var5 = (L1PcInstance)this.f;
            var3 += var5.dH() * 0.01;
         }

         if (this.f.bB(231) && this.f.ev() >= 80) {
            var2 = Math.min(5 + this.f.ev() - 80, 10);
         }

         if (this.f.ea() < this.f.ew() * var3 && Random.a(100) < 34 + var2) {
            if (this.e == 1 || this.e == 2) {
               this.h.a(this.f, this.f.ev() * 2, true);
               this.h.a(new S_SkillSound(this.h.fr(), 12559));
               this.h.b(new S_SkillSound(this.h.fr(), 12559));
               this.h.a(new S_DoActionGFX(this.h.fr(), 2));
               this.h.b(new S_DoActionGFX(this.h.fr(), 2));
            } else if (this.e == 3 || this.e == 4) {
               this.j.b(this.f, this.f.ev() * 2);
               this.j.b(new S_SkillSound(this.j.fr(), 12559));
               this.j.b(new S_DoActionGFX(this.j.fr(), 2));
            }

            return 0;
         }
      }

      return this.e(var1);
   }

   public int a() {
      if (a(this.f)) {
         return 0;
      }

      double var1 = a(this.f, 2);
      L1Skills var3 = SkillsTable.a().a(58);
      int var4 = (int)(var1 * var3.i());
      if (var4 < 0) {
         var4 = 0;
      }

      return var4;
   }

   private int e(int var1) {
      if (this.e == 1 && this.i.a(this.h, this.i, false)) {
         return 0;
      }

      if (this.e == 1) {
         if (this.i.ep() == 1 || this.h.ep() == 1) {
            return 0;
         }
      } else if (this.e == 2) {
         if (this.h.d(this.h, this.k.U_().b())) {
            return 0;
         }
      } else if (this.e == 3) {
         if ((this.j instanceof L1PetInstance || this.j instanceof L1SummonInstance)
            && (this.i.ep() == 1 || this.j.ep() == 1 || this.i.a(this.i, this.j, false))) {
            return 0;
         }
      } else if (this.e == 4
         && (this.j instanceof L1PetInstance || this.j instanceof L1SummonInstance)
         && (this.k instanceof L1PetInstance || this.k instanceof L1SummonInstance)
         && (this.k.ep() == 1 || this.j.ep() == 1)) {
         return 0;
      }

      double var2 = this.f(var1);
      if (var1 == 207 && this.i.eb() >= 5) {
         this.i.i_(this.i.eb() - 5);
         var2 += this.g.eE() * 5;
      }

      var2 = a(this.g, this.f, var2);
      if (this.e == 2 && (this.k instanceof L1PetInstance || this.k instanceof L1SummonInstance) && this.k.L() && !L1CastleWar.a().a(this.k)) {
         var2 /= 8.0;
      }

      if (var2 < 0.0) {
         var2 = 0.0;
      }

      return (int)var2;
   }

   private static double a(L1Character var0, L1Character var1, double var2) {
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var4 = (L1PcInstance)var1;
         var2 -= var4.bV();
         if (var4.dC() > 0 && Random.a(100) < 5) {
            var2 -= var4.dC();
         }

         var2 -= var4.ah();
         if (var4.bB(3008)
            || var4.bB(3009)
            || var4.bB(3010)
            || var4.bB(3011)
            || var4.bB(3012)
            || var4.bB(3013)
            || var4.bB(3014)
            || var4.bB(3024)
            || var4.bB(3025)
            || var4.bB(3026)
            || var4.bB(3027)
            || var4.bB(3028)
            || var4.bB(3029)
            || var4.bB(3030)
            || var4.bB(3040)
            || var4.bB(3041)
            || var4.bB(3042)
            || var4.bB(3043)
            || var4.bB(3044)
            || var4.bB(3045)
            || var4.bB(3046)) {
            var2 -= 5.0;
         }

         if (var4.bB(3015) || var4.bB(3031) || var4.bB(3047)) {
            var2 -= 5.0;
         }

         if (var4.bB(88)) {
            int var5 = var4.ev();
            if (var5 < 50) {
               var5 = 50;
            }

            var2 -= (var5 - 50) / 5 + 1;
         }

         if (var4.bB(181)) {
            var2 -= 2.0;
         }

         if (var4.bB(211)) {
            var2 -= 2.0;
         }

         if (var4.bB(159)) {
            var2 -= 2.0;
         }

         if (var4.bB(4058)) {
            var2 -= 60.0;
         }

         if (var4.bB(219)) {
            var2 *= 1.05;
         }

         if (var4.bB(68)) {
            var2 /= 2.0;
         }
      } else if (var1 instanceof L1NpcInstance) {
         L1NpcInstance var7 = (L1NpcInstance)var1;
      }

      return var2;
   }

   public int c(int var1) {
      L1Skills var2 = SkillsTable.a().a(var1);
      int var3 = var2.j();
      int var4 = var2.i();
      int var5 = 0;
      int var6 = CalcStat.c(this.g.eD());
      if (var6 > 10) {
         var6 = 10;
      }

      int var7 = var4 + var6;

      for (int var8 = 0; var8 < var7; var8++) {
         var5 += Random.a(var3) + 1;
      }

      double var11 = 1.0;
      if (this.g.fa() > 0) {
         var11 += this.g.fa() / 32768.0;
      }

      var5 = (int)(var5 * var11);
      if (this.f.bB(170)) {
         var5 *= 2;
         this.f.bA(170);
         if (this.f instanceof L1PcInstance) {
            this.i.a(new S_PacketBox(59));
         }
      }

      if (this.f.bB(173)) {
         var5 /= 2;
      }

      return var5;
   }

   public void a(int var1, int var2) {
      if (this.e == 1 || this.e == 3) {
         this.b(var1, var2);
      } else if (this.e == 2 || this.e == 4) {
         this.c(var1, var2);
      }

      if (Config.S) {
         if ((this.e == 1 || this.e == 2) && this.h.l()) {
            this.h.a(new S_NpcChatPacket(this.f, "\\\\fRf4↓ 魔法傷害\\\\fRfM (" + var1 + ")"));
            this.h.a(new S_SystemMessage("對" + this.f.et() + "造成魔法傷害= " + var1));
         } else if (this.e == 1 || this.e == 3) {
            this.i.l();
         }
      }
   }

   private void b(int var1, int var2) {
      if (this.e == 1) {
         if (var2 > 0 && this.i.eb() > 0) {
            if (var2 > this.i.eb()) {
               var2 = this.i.eb();
            }

            int var3 = this.h.eb() + var2;
            this.h.i_(var3);
         }

         this.i.a((L1Character)this.h, var2);
         this.i.a(this.h, var1, true);
      } else if (this.e == 3) {
         this.i.a(this.j, var1, true);
      }
   }

   private void c(int var1, int var2) {
      if (this.e == 2) {
         if (var2 > 0) {
            int var3 = this.k.i(var2);
            int var4 = this.h.eb() + var3;
            this.h.i_(var4);
         }

         this.k.a((L1Character)this.h, var2);
         this.k.b(this.h, var1);
      } else if (this.e == 4) {
         this.k.b(this.j, var1);
      }
   }

   private double f(int var1) {
      L1Skills var2 = SkillsTable.a().a(var1);
      int var3 = var2.j();
      int var4 = var2.k();
      double var5 = var2.i();

      for (int var7 = 0; var7 < var4; var7++) {
         var5 += Random.a(var3) + 1;
      }

      int var17 = this.g.eV() - this.g.eW();
      double var8 = this.g.eD() + var17;
      if (var8 < 1.0) {
         var8 = 1.0;
      }

      int var10 = 0;
      if (var2.n() == 1) {
         var10 = this.f.eI();
      } else if (var2.n() == 2) {
         var10 = this.f.eH();
      } else if (var2.n() == 4) {
         var10 = this.f.eG();
      } else if (var2.n() == 8) {
         var10 = this.f.eF();
      }

      double var11 = 75.0 * var8 - 100.0 - 8 * var10;
      if (var11 < 0.0) {
         var11 = 0.0;
      }

      double var13 = var5 * var11 / 800.0;
      double var15 = CalcStat.i(this.h.bj(), this.h.eD()) + this.h.dG() + this.h.aC().d();
      if (Random.a(100) < var15) {
         var13 *= 1.5;
         this.n = true;
      }

      if (this.e == 1 || this.e == 2) {
         var13 += CalcStat.g(this.h.bj(), this.h.eD());
         var13 += CalcStat.c(this.h.eD());
      }

      var13 *= a(this.f, this.g);
      if (this.g.bB(219)) {
         var13 += 10.0;
      }

      if (var1 == 108) {
         if (this.e == 1) {
            var13 = this.g.ea() * 0.76 + this.g.eb() * 0.66;
         } else {
            var13 = this.g.ex();
         }
      }

      return var13;
   }

   public static double a(L1Character var0, L1Character var1, double var2, int var4) {
      int var5 = var0.eV() - var0.eW();
      double var6 = var0.eD() + var5;
      if (var6 < 1.0) {
         var6 = 1.0;
      }

      int var8 = 0;
      if (var4 == 1) {
         var8 = var1.eI();
      } else if (var4 == 2) {
         var8 = var1.eH();
      } else if (var4 == 4) {
         var8 = var1.eG();
      } else if (var4 == 8) {
         var8 = var1.eF();
      }

      double var9 = 75.0 * var6 - 100.0 - 8 * var8;
      if (var9 < 0.0) {
         var9 = 0.0;
      }

      double var11 = var2 * var9 / 800.0;
      if (var0 instanceof L1NpcInstance) {
         var11 *= 1.76;
      }

      var11 *= a(var1, var0);
      var11 = a(var0, var1, var11);
      return (int)var11;
   }

   private static double a(L1Character var0, L1Character var1) {
      if (a(var0)) {
         return 0.0;
      }

      int var2 = 0;
      if (var1 instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)var1;
         var2 = var3.dD() + CalcStat.h(var3.bj(), var3.eD());
      }

      double var7 = var0.W_() - var2;
      double var5 = (100.0 - Math.min(Math.max(0.0, var7), 100.0) / 2.0 - Math.max(0.0, var7 - 100.0) / 10.0) / 100.0;
      return Math.max(var5, 0.3);
   }

   private static double a(L1Character var0, int var1) {
      int var2 = 0;
      if (var1 == 1) {
         var2 = var0.eI();
      } else if (var1 == 2) {
         var2 = var0.eH();
      } else if (var1 == 4) {
         var2 = var0.eG();
      } else if (var1 == 8) {
         var2 = var0.eF();
      }

      double var3 = 0.0;
      if (var2 < 10) {
         var3 = 0.01;
      } else if (var2 < 20) {
         var3 = 0.02;
      } else if (var2 < 30) {
         var3 = 0.03;
      } else if (var2 < 40) {
         var3 = 0.04;
      } else if (var2 < 50) {
         var3 = 0.05;
      } else if (var2 < 60) {
         var3 = 0.06;
      } else if (var2 < 70) {
         var3 = 0.1;
      } else if (var2 < 80) {
         var3 = 0.15;
      } else if (var2 < 90) {
         var3 = 0.2;
      } else if (var2 < 100) {
         var3 = 0.25;
      } else {
         var3 = 0.3;
      }

      return 1.0 - var3;
   }

   public boolean b() {
      return this.n;
   }
}
