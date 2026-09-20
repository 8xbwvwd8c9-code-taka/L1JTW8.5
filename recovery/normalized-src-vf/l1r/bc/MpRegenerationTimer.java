package l1r.bc;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.aq.L1HouseLocation;
import l1r.be.S_SkillSound;
import l1r.bi.CalcStat;
import l1r.bi.Point;

public class MpRegenerationTimer extends TimerTask {
   private static final Logger a = Logger.getLogger(MpRegenerationTimer.class.getName());
   private final L1PcInstance b;
   private int c = 0;
   private int d = 4;
   private int e = 0;

   public MpRegenerationTimer(L1PcInstance var1) {
      this.b = var1;
   }

   public void a(int var1) {
      if (this.d >= var1) {
         this.d = var1;
      }
   }

   @Override
   public void run() {
      try {
         if (this.b.eX()) {
            return;
         }

         this.c = this.c + this.d;
         this.d = 4;
         if (this.c >= 64) {
            this.c = 0;
            this.b();
         }

         if (this.b.cG() > 0 && this.e++ >= 64) {
            this.e = 0;
            this.a();
         }
      } catch (Exception var2) {
         a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
      }
   }

   private void a() {
      int var1 = this.b.eb() + this.b.cG();
      if (var1 < 0) {
         var1 = 0;
      }

      this.b.a(new S_SkillSound(this.b.fr(), 6321));
      this.b.b(new S_SkillSound(this.b.fr(), 6321));
      this.b.i_(var1);
   }

   private void b() {
      int var1 = CalcStat.m(this.b.bk(), this.b.eE());
      int var2 = 0;
      if (this.b.bB(1002)) {
         int var3 = this.b.eE() - 10;
         var1 += var3 <= 0 ? 1 : var3;
         if (this.b.bB(32)) {
            int var4 = 640 - this.b.bC(32);
            int var5 = Math.min(var4 / 16, 8);
            var1 += 2 * var5;
         }
      }

      if (L1HouseLocation.a(this.b.fs(), this.b.ft(), this.b.fp())) {
         var2 += 3;
      }

      if (this.b.fp() >= 16384 && this.b.fp() <= 25599) {
         var2 += 3;
      }

      if (this.b.fu().e(new Point(33055, 32336)) && this.b.fp() == 4 && this.b.A()) {
         var2 += 3;
      }

      var1 += this.b.j().m() + this.b.as();
      if (this.b.fj() < 3 || this.a(this.b)) {
         if (this.b.bk() >= 45) {
            var1 /= 2;
         } else {
            var1 = 0;
         }
      }

      int var7 = this.b.eb() + var1 + var2;
      if (var7 < 0) {
         var7 = 0;
      }

      this.b.i_(var7);
   }

   private boolean a(L1PcInstance var1) {
      return !var1.bB(169) && !var1.bB(176) ? var1.j().h() > 49 : false;
   }
}
