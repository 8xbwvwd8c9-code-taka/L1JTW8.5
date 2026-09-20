package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.bj.ClientThread;

public class C_ChatOnOff extends ClientBasePacket {
   public C_ChatOnOff(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.c();
         int var5 = this.c();
         switch (var4) {
            case 0:
               var3.q(var5 == 1);
            case 1:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            default:
               break;
            case 2:
               var3.m(var5 == 1);
               break;
            case 6:
               var3.n(var5 == 1);
               break;
            case 9:
               var3.o(var5 == 0);
               break;
            case 10:
               var3.p(var5 == 1);
         }
      }
   }

   @Override
   public String a() {
      return "C_ChatOnOff";
   }
}
