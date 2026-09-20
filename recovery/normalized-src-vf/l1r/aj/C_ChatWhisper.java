package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_ChatPacket;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class C_ChatWhisper extends ClientBasePacket {
   public C_ChatWhisper(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         String var4 = this.g();
         String var5 = this.g();
         if (var3.bB(1005)) {
            var3.a(new S_ServerMessage(242));
         } else if (var3.ev() < Config.O) {
            var3.a(new S_ServerMessage(404, String.valueOf(Config.O)));
         } else {
            L1PcInstance var6 = L1World.a().a(var4);
            if (var6 == null) {
               var3.a(new S_ServerMessage(73, var4));
            } else if (!var6.equals(var3)) {
               if (var6.cd().c(var3.et())) {
                  var3.a(new S_ServerMessage(117, var6.et()));
               } else if (!var6.ck()) {
                  var3.a(new S_ServerMessage(205, var6.et()));
               } else {
                  var3.a(new S_ChatPacket(var6, var5, 9));
                  var6.a(new S_ChatPacket(var3, var5, 16));
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_ChatWhisper";
   }
}
