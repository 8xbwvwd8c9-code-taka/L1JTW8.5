package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.bj.ClientThread;

public class C_GMTeleport extends ClientBasePacket {
   public C_GMTeleport(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.d();
         int var5 = this.d();
         int var6 = this.d();
         int var7 = var3.h(var5, var6);
         L1Teleport.a(var3, var5, var6, var4, var7, true);
      }
   }

   @Override
   public String a() {
      return "C_GMTeleport";
   }
}
