package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.bj.ClientThread;

public class C_Teleport extends ClientBasePacket {
   public C_Teleport(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         L1Teleport.a(var3);
      }
   }

   @Override
   public String a() {
      return "C_Teleport";
   }
}
