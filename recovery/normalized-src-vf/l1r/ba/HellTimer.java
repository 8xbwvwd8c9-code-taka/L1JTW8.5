package l1r.ba;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.aq.L1TownLocation;
import l1r.aq.L1World;
import l1r.be.S_RedMessage;
import l1r.bi.GeneralThreadPool;

public class HellTimer {
   private static final Logger a = Logger.getLogger(HellTimer.class.getName());
   private static HellTimer b;

   public static HellTimer a() {
      if (b == null) {
         b = new HellTimer();
      }

      return b;
   }

   private HellTimer() {
      GeneralThreadPool.a().a(new HellTimer.a(null), 60000L, 60000L);
   }

   public void a(L1PcInstance var1, boolean var2) {
      if (var1.fp() != 666) {
         int var3 = 32701;
         int var4 = 32777;
         int var5 = 666;
         L1Teleport.a(var1, 32701, 32777, 666, 5, true);
      }

      if (var2) {
         if (var1.aD() <= 10) {
            var1.aG(300);
         } else {
            var1.aG(300 * (var1.aD() - 10) + 300);
         }

         var1.a(new S_RedMessage(552, String.valueOf(var1.aD()), String.valueOf(var1.bI() / 60)));
      } else {
         var1.a(new S_RedMessage(637, String.valueOf(var1.bI())));
      }
   }

   private class a extends TimerTask {
      private a() {
      }

      @Override
      public void run() {
         try {
            for (L1PcInstance var1 : L1World.a().c()) {
               if (var1 != null && var1.bE() != 0 && !var1.eX() && var1.bI() > 0) {
                  var1.aG(var1.bI() - 60);
                  if (var1.bI() <= 0) {
                     int[] var3 = L1TownLocation.a(4);
                     L1Teleport.a(var1, var3[0], var3[1], var3[2], 5, true);
                  }
               }
            }
         } catch (Exception var4) {
            HellTimer.a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
         }
      }

      // $VF: synthetic method
      a(HellTimer.a var2) {
         this();
      }
   }
}
