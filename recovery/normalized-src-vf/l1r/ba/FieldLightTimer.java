package l1r.ba;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.LightSpawnTable;
import l1r.ap.L1FieldObjectInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.at.L1GameTimeClock;

public class FieldLightTimer implements Runnable {
   private static final Logger a = Logger.getLogger(FieldLightTimer.class.getName());
   private static FieldLightTimer b;
   private boolean c = false;

   public static FieldLightTimer a() {
      if (b == null) {
         b = new FieldLightTimer();
      }

      return b;
   }

   @Override
   public void run() {
      try {
         while (true) {
            this.b();
            Thread.sleep(60000L);
         }
      } catch (Exception var2) {
         a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
      }
   }

   private void b() {
      int var1 = L1GameTimeClock.a().b().c();
      int var2 = var1 % 86400;
      if (var2 >= 21300 && var2 < 64500) {
         if (this.c) {
            this.c = false;

            for (L1Object var3 : L1World.a().b()) {
               if (var3 instanceof L1FieldObjectInstance) {
                  L1FieldObjectInstance var5 = (L1FieldObjectInstance)var3;
                  if ((var5.U_().b() == 81177 || var5.U_().b() == 81178 || var5.U_().b() == 81179 || var5.U_().b() == 81180 || var5.U_().b() == 81181)
                     && (var5.fp() == 0 || var5.fp() == 4)) {
                     var5.aa_();
                  }
               }
            }
         }
      } else if ((var2 >= 64500 && var2 <= 86400 || var2 >= 0 && var2 < 21300) && !this.c) {
         this.c = true;
         LightSpawnTable.a();
      }
   }
}
