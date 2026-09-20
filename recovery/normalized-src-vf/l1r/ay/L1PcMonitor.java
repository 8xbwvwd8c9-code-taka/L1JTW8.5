package l1r.ay;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;

public abstract class L1PcMonitor implements Runnable {
   private static final Logger a = Logger.getLogger(L1PcMonitor.class.getName());
   private final int b;

   public L1PcMonitor(int var1) {
      this.b = var1;
   }

   @Override
   public final void run() {
      L1PcInstance var1 = (L1PcInstance)L1World.a().a(this.b);
      if (var1 != null && var1.aK() != null) {
         try {
            this.a(var1);
         } catch (Exception var3) {
            a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
         }
      }
   }

   public abstract void a(L1PcInstance var1);
}
