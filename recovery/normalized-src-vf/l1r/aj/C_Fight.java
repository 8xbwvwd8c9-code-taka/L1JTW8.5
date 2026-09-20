package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.be.S_Message_YN;
import l1r.be.S_ServerMessage;
import l1r.bi.LineageUtil;
import l1r.bj.ClientThread;

public class C_Fight extends ClientBasePacket {
   private static final String a = "[C] C_Fight";

   public C_Fight(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         L1PcInstance var4 = LineageUtil.a(var3, false);
         if (var4 != null && !var4.ed()) {
            if (var3.cp() != 0) {
               var3.a(new S_ServerMessage(633));
               return;
            }

            if (var4.cp() != 0) {
               var4.a(new S_ServerMessage(634));
               return;
            }

            var3.aN(var4.fr());
            var4.aN(var3.fr());
            var4.a(new S_Message_YN(630, var3.et()));
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_Fight";
   }
}
