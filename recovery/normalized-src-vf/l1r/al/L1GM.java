package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.be.S_SystemMessage;

public class L1GM implements L1CommandExecutor {
   private L1GM() {
   }

   public static L1CommandExecutor a() {
      return new L1GM();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      var1.ae(var1.l() ? 0 : 200);
      var1.a(new S_SystemMessage("setGm = " + var1.l()));
   }
}
