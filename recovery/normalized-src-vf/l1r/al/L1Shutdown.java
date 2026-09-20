package l1r.al;

import l1r.ai.GameServer;
import l1r.ap.L1PcInstance;
import l1r.be.S_SystemMessage;

public class L1Shutdown implements L1CommandExecutor {
   private L1Shutdown() {
   }

   public static L1CommandExecutor a() {
      return new L1Shutdown();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         if (var3.equalsIgnoreCase("now")) {
            GameServer.a().a(false);
            return;
         }

         if (var3.equalsIgnoreCase("abort")) {
            GameServer.a().d();
            return;
         }

         int var4 = Math.max(5, Integer.parseInt(var3));
         GameServer.a().a(var4, false);
      } catch (Exception var5) {
         var1.a(new S_SystemMessage("請輸入: .shutdown sec|now|abort 。"));
      }
   }
}
