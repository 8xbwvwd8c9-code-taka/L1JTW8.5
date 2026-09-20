package l1r.ba;

import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1HouseLocation;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.be.S_SystemMessage;
import l1r.bi.GeneralThreadPool;
import l1r.l1j.server.Config;

public class ClearGroundTimer {
   private static final Logger a = Logger.getLogger(ClearGroundTimer.class.getName());
   private static ClearGroundTimer b;

   public static ClearGroundTimer a() {
      if (b == null) {
         b = new ClearGroundTimer();
      }

      return b;
   }

   private ClearGroundTimer() {
      int var1 = Config.aa * 60 * 1000 - 10000;
      GeneralThreadPool.a().a(new ClearGroundTimer.L1R_a(null), var1, var1);
   }

   private void c() {
      int var1 = 0;

      for (L1Object var2 : L1World.a().b()) {
         if (var2 instanceof L1ItemInstance) {
            L1ItemInstance var4 = (L1ItemInstance)var2;
            if ((var4.fs() != 0 || var4.ft() != 0)
               && var4.N() != 40515
               && !L1HouseLocation.a(var4.fs(), var4.ft(), var4.fp())
               && (var4.fp() < 1400 || var4.fp() > 1498)
               && var4.fp() < 16384) {
               List var5 = L1World.a().c(var4, Config.ab);
               if (var5.isEmpty()) {
                  L1Inventory var6 = L1World.a().a(var4.fs(), var4.ft(), var4.fp());
                  var6.f(var4);
                  var1++;
               }
            }
         }
      }
   }

   private class L1R_a extends TimerTask {
      private L1R_a() {
      }

      @Override
      public void run() {
         try {
            L1World.a().a(new S_SystemMessage("地上的物品，10秒後將被清除。"));
            Thread.sleep(10000L);
            ClearGroundTimer.this.c();
            L1World.a().a(new S_SystemMessage("地上的物品，已經被清除了。"));
         } catch (Exception var2) {
            ClearGroundTimer.a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }

      // $VF: synthetic method
      L1R_a(ClearGroundTimer.L1R_a var2) {
         this();
      }
   }
}
