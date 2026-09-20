package l1r.ba;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.GameServer;
import l1r.bi.GeneralThreadPool;
import l1r.bj.ClientThread;

public class ClientAliveTimer {
   private static final Logger a = Logger.getLogger(ClientAliveTimer.class.getName());
   private static ClientAliveTimer b;

   public static ClientAliveTimer a() {
      if (b == null) {
         b = new ClientAliveTimer();
      }

      return b;
   }

   private ClientAliveTimer() {
      int var1 = 1000;
      GeneralThreadPool.a().a(new ClientAliveTimer.L1R_a(null), 1000L, 1000L);
   }

   private class L1R_a extends TimerTask {
      private L1R_a() {
      }

      @Override
      public void run() {
         try {
            long var1 = System.currentTimeMillis();

            for (ClientThread var3 : GameServer.a().c()) {
               if (var3.b != 0L && var1 - var3.b > 180000L) {
                  var3.a(230);
                  GameServer.a().b(var3);
               }
            }
         } catch (Exception var5) {
            ClientAliveTimer.a.log(Level.SEVERE, var5.getLocalizedMessage(), var5);
         }
      }

      // $VF: synthetic method
      L1R_a(ClientAliveTimer.L1R_a var2) {
         this();
      }
   }
}
