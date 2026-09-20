package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.be.S_WhoCharinfo;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class C_Who extends ClientBasePacket {
   public C_Who(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         String var4 = this.g();
         L1PcInstance var5 = L1World.a().a(var4);
         if (var5 != null) {
            var3.a(new S_WhoCharinfo(var5));
         } else if (Config.af) {
            String var6 = String.valueOf(L1World.a().c().size());
            var3.a(new S_ServerMessage(81, var6));
         }
      }
   }

   @Override
   public String a() {
      return "C_Who";
   }
}
