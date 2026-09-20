package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.bj.ClientThread;

public class C_LeaveParty extends ClientBasePacket {
   private static final String a = "[C] C_LeaveParty";

   public C_LeaveParty(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         if (var3.q()) {
            var3.aL().b(var3);
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_LeaveParty";
   }
}
