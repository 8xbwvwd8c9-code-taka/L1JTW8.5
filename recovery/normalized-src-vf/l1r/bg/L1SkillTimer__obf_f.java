package l1r.bg;

import java.sql.Timestamp;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.aq.L1Character;
import l1r.bi.GeneralThreadPool;

class L1SkillTimer__obf_f implements L1SkillTimer__obf_d, Runnable {
   private static final Logger a = Logger.getLogger(L1SkillTimer__obf_f.class.getName());
   private ScheduledFuture<?> b = null;
   private final L1Character c;
   private final int d;
   private final int e;
   private int f;
   private boolean g = false;
   private Timestamp h = null;

   public L1SkillTimer__obf_f(L1Character var1, int var2, int var3, Timestamp var4) {
      this.c = var1;
      this.e = var2;
      this.d = var3;
      this.f = this.d / 1000;
      this.h = var4;
   }

   @Override
   public void run() {
      if (!this.g) {
         this.f--;
         if (this.f <= 0) {
            this.c.bz(this.e);
         }
      }
   }

   @Override
   public void a(boolean var1) {
      this.g = var1;
   }

   @Override
   public void c() {
      this.b = GeneralThreadPool.a().a(this, 1000L, 1000L);
   }

   @Override
   public void d() {
      this.e();

      try {
         L1SkillTimer__obf_c.a(this.c, this.e);
      } catch (Throwable var2) {
         a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
      }
   }

   @Override
   public void e() {
      if (this.b != null) {
         this.b.cancel(true);
      }
   }

   @Override
   public int a() {
      return this.f;
   }

   @Override
   public Timestamp b() {
      return this.h;
   }
}
