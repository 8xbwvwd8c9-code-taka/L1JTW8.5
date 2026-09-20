package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1ExcludingList;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_Exclude extends ClientBasePacket {
   public C_Exclude(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         String var4 = this.g();
         if (!var4.isEmpty()) {
            L1ExcludingList var5 = var3.cd();
            if (var5.b()) {
               var3.a(new S_ServerMessage(472));
            } else {
               if (var5.c(var4)) {
                  String var6 = var5.b(var4);
                  var3.a(new S_PacketBox(19, 0, var6));
               } else {
                  var5.a(var4);
                  var3.a(new S_PacketBox(18, 0, var4));
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_Exclude";
   }
}
