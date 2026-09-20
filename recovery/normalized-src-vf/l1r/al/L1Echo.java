package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.be.S_SystemMessage;

public class L1Echo implements L1CommandExecutor {
   private L1Echo() {
   }

   public static L1CommandExecutor a() {
      return new L1Echo();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      var1.a(new S_SystemMessage(var3));
   }
}
