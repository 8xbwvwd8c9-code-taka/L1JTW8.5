package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Trade;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_TradeOK extends ClientBasePacket {
   public C_TradeOK(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         L1PcInstance var4 = (L1PcInstance)L1World.a().a(var3.aO());
         if (var4 != null) {
            var3.c(true);
            if (var3.aP() && var4.aP()) {
               if (var3.j().c() < 180 - var4.ax().c() && var4.j().c() < 180 - var3.ax().c()) {
                  L1Trade.a(var3);
               } else {
                  var3.a(new S_ServerMessage(263));
                  var4.a(new S_ServerMessage(263));
                  L1Trade.b(var3);
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_TradeOK";
   }
}
