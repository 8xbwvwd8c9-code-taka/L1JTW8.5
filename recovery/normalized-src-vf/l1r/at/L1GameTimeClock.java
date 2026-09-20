package l1r.at;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.GeneralThreadPool;

public class L1GameTimeClock {
   private static final Logger a = Logger.getLogger(L1GameTimeClock.class.getName());
   private static L1GameTimeClock b;
   private volatile L1GameTime c = L1GameTime.a();
   private L1GameTime d = null;
   private final CopyOnWriteArrayList<L1GameTimeListener> e = new CopyOnWriteArrayList<>();

   public static L1GameTimeClock a() {
      if (b == null) {
         b = new L1GameTimeClock();
      }

      return b;
   }

   private L1GameTimeClock() {
      GeneralThreadPool.a().a(new L1GameTimeClock.L1R_a(null));
   }

   private boolean a(int var1) {
      return this.d.a(var1) != this.c.a(var1);
   }

   private void d() {
      if (this.a(2)) {
         for (L1GameTimeListener var1 : this.e) {
            var1.b(this.c);
         }
      }

      if (this.a(5)) {
         for (L1GameTimeListener var3 : this.e) {
            var3.a(this.c);
         }
      }

      if (this.a(11)) {
         for (L1GameTimeListener var4 : this.e) {
            var4.c(this.c);
         }
      }

      if (this.a(12)) {
         for (L1GameTimeListener var5 : this.e) {
            var5.d(this.c);
         }
      }
   }

   public L1GameTime b() {
      return this.c;
   }

   public void a(L1GameTimeListener var1) {
      this.e.add(var1);
   }

   private class L1R_a implements Runnable {
      private L1R_a() {
      }

      @Override
      public void run() {
         while (true) {
            L1GameTimeClock.this.d = L1GameTimeClock.this.c;
            L1GameTimeClock.this.c = L1GameTime.a();
            L1GameTimeClock.this.d();

            try {
               Thread.sleep(500L);
            } catch (InterruptedException var2) {
               L1GameTimeClock.a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
            }
         }
      }

      // $VF: synthetic method
      L1R_a(L1GameTimeClock.L1R_a var2) {
         this();
      }
   }
}
