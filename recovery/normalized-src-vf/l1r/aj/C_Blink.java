package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.bj.ClientThread;

public class C_Blink extends ClientBasePacket {
   public C_Blink(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         var3.aI(var3.fs());
         var3.aJ(var3.ft());
         var3.aK(var3.fp());
         var3.aL(var3.fb());
         L1Teleport.a(var3);
      }
   }

   @Override
   public String a() {
      return "C_Blink";
   }
}
