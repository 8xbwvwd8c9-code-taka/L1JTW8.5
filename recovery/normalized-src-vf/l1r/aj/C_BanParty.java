package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_BanParty extends ClientBasePacket {
   private static final String a = "[C] C_BanParty";

   public C_BanParty(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         String var4 = this.g();
         if (!var3.aL().e(var3)) {
            var3.a(new S_ServerMessage(427));
         } else {
            for (L1PcInstance var5 : var3.aL().c()) {
               if (var5.et().toLowerCase().equals(var4.toLowerCase())) {
                  var3.aL().c(var5);
                  return;
               }
            }

            var3.a(new S_ServerMessage(426, var4));
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_BanParty";
   }
}
