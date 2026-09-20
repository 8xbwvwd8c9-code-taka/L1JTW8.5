package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_CheckPK extends ClientBasePacket {
   private static final String a = "[C] C_CheckPK";

   public C_CheckPK(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         var3.a(new S_ServerMessage(562, String.valueOf(var3.aD())));
      }
   }

   @Override
   public String a() {
      return "[C] C_CheckPK";
   }
}
