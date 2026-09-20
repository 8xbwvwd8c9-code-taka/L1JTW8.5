package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.bj.ClientThread;

public class C_AttackContinue extends ClientBasePacket {
   public C_AttackContinue(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         var3.bb(var4);
      }
   }
}
