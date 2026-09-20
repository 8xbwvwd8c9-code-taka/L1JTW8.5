package l1r.bc;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1EffectInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.aq.L1HouseLocation;
import l1r.aq.L1Object;
import l1r.be.S_SkillSound;
import l1r.bi.CalcStat;
import l1r.bi.Point;
import l1r.bi.Random;

public class HpRegenerationTimer extends TimerTask {
   private static final Logger a = Logger.getLogger(HpRegenerationTimer.class.getName());
   private final L1PcInstance b;
   private int c = 0;
   private int d = 0;
   private int e = 4;
   private int f = 0;

   public HpRegenerationTimer(L1PcInstance var1) {
      this.b = var1;
      this.a();
   }

   public void a(int var1) {
      if (this.e >= var1) {
         this.e = var1;
      }
   }

   @Override
   public void run() {
      try {
         if (this.b.eX()) {
            return;
         }

         this.d = this.d + this.e;
         this.e = 4;
         synchronized (this) {
            if (this.c <= this.d) {
               this.d = 0;
               this.c();
            }
         }

         if (this.b.cF() > 0 && this.f++ >= 64) {
            this.f = 0;
            this.b();
         }
      } catch (Exception var3) {
         a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
      }
   }

   private void b() {
      int var1 = this.b.ea() + this.b.cF();
      if (var1 < 0) {
         var1 = 0;
      }

      this.b.a(new S_SkillSound(this.b.fr(), 744));
      this.b.b(new S_SkillSound(this.b.fr(), 744));
      this.b.a(var1);
   }

   public void a() {
      int[] var1 = new int[]{30, 25, 20, 16, 14, 12, 11, 10, 9, 3, 2};
      int var2 = Math.min(10, this.b.ev());
      if (30 <= this.b.ev() && this.b.z()) {
         var2 = 11;
      }

      synchronized (this) {
         this.c = var1[var2 - 1] * 4;
      }
   }

   private void c() {
      if (!this.b.eX()) {
         int var1 = CalcStat.k(this.b.bg(), this.b.eA());
         int var2 = Random.a(var1) + 1;
         int var3 = 0;
         if (this.b.bB(158)) {
            var2 += 15;
         }

         if (L1HouseLocation.a(this.b.fs(), this.b.ft(), this.b.fp())) {
            var3 += 5;
         }

         if (this.b.fp() >= 16384 && this.b.fp() <= 25599) {
            var3 += 5;
         }

         if (this.b.fu().e(new Point(33055, 32336)) && this.b.fp() == 4 && this.b.A()) {
            var3 += 5;
         }

         boolean var4 = false;
         if (c(this.b)) {
            var4 = true;
            var3 += 3;
         }

         var2 += this.b.j().l() + this.b.ar();
         if (this.b.bB(55)) {
            var2 = 0;
         }

         if (this.b.bB(5017)) {
            var2 = -100;
         }

         if (this.b.fj() < 3 || this.b(this.b)) {
            if (this.b.bg() >= 45) {
               var2 /= 2;
            } else {
               var2 = 0;
            }
         }

         int var5 = this.b.ea() + var2 + var3;
         if (var5 < 1) {
            var5 = 1;
         }

         if (this.a(this.b)) {
            var5 -= 20;
            if (var5 < 1) {
               if (this.b.l()) {
                  var5 = 1;
               } else {
                  this.b.b((L1Character)null);
               }
            }
         }

         if (this.b.fp() == 410 && !var4) {
            var5 -= 10;
            if (var5 < 1) {
               if (this.b.l()) {
                  var5 = 1;
               } else {
                  this.b.b((L1Character)null);
               }
            }
         }

         if (!this.b.eX()) {
            this.b.a(Math.min(var5, this.b.ew()));
         }
      }
   }

   private boolean a(L1PcInstance var1) {
      if (var1.j().h(20207)) {
         return false;
      } else if (!var1.bB(1003) && !var1.cH()) {
         return var1.j().h(21048) && var1.j().h(21049) && var1.j().h(21050) ? false : var1.fq().g();
      } else {
         return false;
      }
   }

   private boolean b(L1PcInstance var1) {
      if (!var1.bB(169) && !var1.bB(176)) {
         return var1.j().h(20049) ? false : var1.j().h() > 49;
      } else {
         return false;
      }
   }

   private static boolean c(L1PcInstance var0) {
      for (L1Object var1 : var0.eq()) {
         if (var1 instanceof L1EffectInstance) {
            L1EffectInstance var3 = (L1EffectInstance)var1;
            if (var3.z() == 81169 && var3.fu().c(var0.fu()) < 4) {
               return true;
            }
         }
      }

      return false;
   }
}
