package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.be.S_PacketBox;
import l1r.bj.ClientThread;

public class C_Restart extends ClientBasePacket {
   public C_Restart(byte[] var1, ClientThread var2) {
      super(var1);
      var2.a(new S_PacketBox(42));
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         synchronized (var3) {
            var3.p();
            var2.a((L1PcInstance)null);
         }
      }
   }

   @Override
   public String a() {
      return "C_Restart";
   }
}
