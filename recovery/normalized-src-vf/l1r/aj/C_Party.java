package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Party;
import l1r.be.S_Html;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_Party extends ClientBasePacket {
   public C_Party(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         L1Party var4 = var3.aL();
         if (var3.q()) {
            String var5 = "";

            for (L1PcInstance var6 : var4.c()) {
               var5 = var5 + var6.et() + " ";
            }

            var3.a(new S_Html(var3.fr(), "party", var4.a().et(), var5));
         } else {
            var3.a(new S_ServerMessage(425));
         }
      }
   }

   @Override
   public String a() {
      return "C_Party";
   }
}
