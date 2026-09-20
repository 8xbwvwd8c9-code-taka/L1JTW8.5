package l1r.bb;

import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Character;
import l1r.be.S_DoActionGFX;
import l1r.be.S_Paralysis;
import l1r.bi.GeneralThreadPool;

public class CubeEffectTimer extends TimerTask {
   private static final Logger a = Logger.getLogger(CubeEffectTimer.class.getName());
   private ScheduledFuture<?> b = null;
   private int c = 0;
   private final L1Character d;
   private final L1Character e;
   private final int f;

   public CubeEffectTimer(L1Character var1, L1Character var2, int var3) {
      this.d = var1;
      this.e = var2;
      this.f = var3;
   }

   @Override
   public void run() {
      try {
         if (this.e.eX()) {
            this.b();
            return;
         }

         if (!this.e.bB(this.f)) {
            this.b();
            return;
         }

         this.c++;
         this.c();
      } catch (Throwable var2) {
         a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
      }
   }

   public void a() {
      this.b = GeneralThreadPool.a().a(this, 900L, 1000L);
   }

   private void b() {
      if (this.b != null) {
         this.b.cancel(true);
      }
   }

   private void c() {
      if (this.f == 1019) {
         if (this.c % 4 != 0) {
            return;
         }

         if (this.e.bB(1028)) {
            return;
         }

         if (this.e.bB(78)) {
            return;
         }

         if (this.e.bB(50)) {
            return;
         }

         if (this.e.bB(157)) {
            return;
         }

         if (this.e instanceof L1PcInstance) {
            L1PcInstance var1 = (L1PcInstance)this.e;
            var1.a(new S_DoActionGFX(var1.fr(), 2));
            var1.b(new S_DoActionGFX(var1.fr(), 2));
            var1.a(this.d, 10.0, false);
         } else if (this.e instanceof L1MonsterInstance) {
            L1MonsterInstance var2 = (L1MonsterInstance)this.e;
            var2.b(new S_DoActionGFX(var2.fr(), 2));
            var2.b(this.d, 10);
         }
      } else if (this.f == 1021) {
         if (this.c % 4 != 0) {
            return;
         }

         if (this.e.bB(1028)) {
            return;
         }

         if (this.e.bB(78)) {
            return;
         }

         if (this.e.bB(50)) {
            return;
         }

         if (this.e.bB(157)) {
            return;
         }

         if (this.e instanceof L1PcInstance) {
            L1PcInstance var3 = (L1PcInstance)this.e;
            var3.j(1028, 1000);
            var3.a(new S_Paralysis(6, true));
         } else if (this.e instanceof L1MonsterInstance) {
            L1MonsterInstance var4 = (L1MonsterInstance)this.e;
            var4.j(1028, 1000);
            var4.V(true);
         }
      } else if (this.f == 1023) {
         this.e.j(1024, 4000);
      } else if (this.f == 1025) {
         if (this.c % 4 == 0) {
            int var5 = this.e.eb() + 5;
            if (var5 < 0) {
               var5 = 0;
            }

            this.e.i_(var5);
         }

         if (this.c % 5 == 0) {
            if (this.e instanceof L1PcInstance) {
               L1PcInstance var6 = (L1PcInstance)this.e;
               var6.a(this.d, 25.0, false);
            } else if (this.e instanceof L1MonsterInstance) {
               L1MonsterInstance var7 = (L1MonsterInstance)this.e;
               var7.b(this.d, 25);
            }
         }
      }
   }
}
