package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.bj.ClientThread;

public class C_ExitGhost extends ClientBasePacket {
   private static final String a = "[C] C_ExitGhost";

   public C_ExitGhost(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 == null || !var3.bN()) {
         return;
      }
      var3.makeReadyEndGhost();
   }

   @Override
   public String a() {
      return "[C] C_ExitGhost";
   }
}
