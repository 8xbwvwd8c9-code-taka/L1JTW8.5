package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.bj.ClientThread;

public class C_FishClick extends ClientBasePacket {
   private static final String a = "[C] C_FishClick";

   public C_FishClick(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.eX()) {
         if (var3.cO() != null) {
            var3.cO().a();
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_FishClick";
   }
}
