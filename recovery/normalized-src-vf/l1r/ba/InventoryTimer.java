package l1r.ba;

import java.sql.Timestamp;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bi.GeneralThreadPool;

public class InventoryTimer {
   private static final Logger a = Logger.getLogger(InventoryTimer.class.getName());
   private final int[] b = new int[]{310, 640699, 640700, 640319, 640320, 640321, 640322, 640323, 640324, 640325, 640354, 640355, 640701, 640820};
   private static InventoryTimer c;

   public static InventoryTimer a() {
      if (c == null) {
         c = new InventoryTimer();
      }

      return c;
   }

   private InventoryTimer() {
      GeneralThreadPool.a().a(new InventoryTimer.a(null), 30000L, 30000L);
   }

   private class a extends TimerTask {
      private a() {
      }

      @Override
      public void run() {
         try {
            for (L1PcInstance var1 : L1World.a().c()) {
               if (var1 != null && var1.bE() != 0) {
                  for (L1ItemInstance var3 : var1.j().d()) {
                     int[] var8;
                     int var7 = (var8 = InventoryTimer.this.b).length;

                     for (int var6 = 0; var6 < var7; var6++) {
                        int var5 = var8[var6];
                        if (var3.N() == var5 && (var1.fp() == 4 || var1.fp() >= 100 && var1.fp() <= 111)) {
                           var1.j().f(var3);
                        }
                     }

                     if (var3.bb() != null) {
                        Timestamp var10 = new Timestamp(System.currentTimeMillis());
                        if (var3.bb().before(var10)) {
                           var1.a(new S_ServerMessage(2535, var3.b(), "0"));
                           if ((var3.N() < 21246 || var3.N() > 21251) && (var3.N() < 21252 || var3.N() > 21257) && (var3.N() < 21261 || var3.N() > 21300)) {
                              var1.j().f(var3);
                           }
                        }
                     }
                  }
               }
            }
         } catch (Exception var9) {
            InventoryTimer.a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
         }
      }

      // $VF: synthetic method
      a(InventoryTimer.a var2) {
         this();
      }
   }
}
