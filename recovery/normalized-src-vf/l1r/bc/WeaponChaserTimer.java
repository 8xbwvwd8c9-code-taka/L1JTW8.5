package l1r.bc;

import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_DoActionGFX;
import l1r.be.S_EffectLocation;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;

public class WeaponChaserTimer extends TimerTask {
   private static final Logger a = Logger.getLogger(WeaponChaserTimer.class.getName());
   private ScheduledFuture<?> b = null;
   private int c = 0;
   private final int d;
   private final L1PcInstance e;
   private final L1Character f;

   public WeaponChaserTimer(L1PcInstance var1, L1Character var2, int var3) {
      this.f = var2;
      this.e = var1;
      this.d = var3;
   }

   @Override
   public void run() {
      try {
         if (this.f == null || this.f.eX()) {
            this.b();
            return;
         }

         this.c();
         this.c++;
         if (this.c >= 3) {
            this.b();
            return;
         }
      } catch (Throwable var2) {
         a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
      }
   }

   public void a() {
      this.b = GeneralThreadPool.a().a(this, 0L, 1000L);
   }

   private void b() {
      if (this.b != null) {
         this.b.cancel(true);
      }
   }

   private void c() {
      double var1 = this.a(this.e, this.f);
      if (this.f.ea() - (int)var1 <= 0 && this.f.ea() != 1) {
         var1 = this.f.ea() - 1;
      } else if (this.f.ea() == 1) {
         var1 = 0.0;
      }

      if (var1 >= 400.0) {
         var1 = 400.0;
      }

      this.e.a(new S_EffectLocation(this.f.fs(), this.f.ft(), this.d));
      this.e.b(new S_EffectLocation(this.f.fs(), this.f.ft(), this.d));
      if (this.f instanceof L1PcInstance) {
         L1PcInstance var3 = (L1PcInstance)this.f;
         var3.a(new S_DoActionGFX(var3.fr(), 2));
         var3.b(new S_DoActionGFX(var3.fr(), 2));
         var3.a(this.e, var1, false);
      } else if (this.f instanceof L1NpcInstance) {
         L1NpcInstance var4 = (L1NpcInstance)this.f;
         var4.b(new S_DoActionGFX(var4.fr(), 2));
         var4.b(this.e, (int)var1);
      }
   }

   private double a(L1PcInstance var1, L1Character var2) {
      double var3 = 0.0;
      int var5 = var1.eV() - var1.eW();
      int var6 = var1.eD();
      double var7 = var1.eD() - 15 + var5 * 0.46;
      double var9 = 1.0 + 3.0 * var7 / 32.0;
      if (var9 < 1.0) {
         var9 = 1.0;
      }

      double var11 = 0.0;
      if (var6 > 18) {
         var11 = (var6 + 2.0) / var6;
      } else if (var6 <= 12) {
         var11 = 0.78;
      } else {
         var11 = var6 * 0.065;
      }

      double var13 = 0.0;
      if (var6 <= 12) {
         var13 = 12.0;
      } else {
         var13 = var6;
      }

      var3 = (Random.a(6) + 1 + 7) * var9 * var11 / 10.5 * var13 * 2.0;
      if (var2.bB(68)) {
         var3 /= 2.0;
      }

      return var3;
   }
}
