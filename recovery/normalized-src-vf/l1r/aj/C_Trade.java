package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.be.S_Message_YN;
import l1r.be.S_ServerMessage;
import l1r.bi.LineageUtil;
import l1r.bj.ClientThread;

public class C_Trade extends ClientBasePacket {
   public C_Trade(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         if (var3.ff()) {
            var3.a(new S_ServerMessage(334));
         } else {
            L1PcInstance var4 = LineageUtil.a(var3, false);
            if (var4 != null) {
               if (var4.aX()) {
                  var3.a(new S_ServerMessage(989));
               } else if (var4.aO() > 0) {
                  if (var4.aO() == var3.fr()) {
                     var3.a(new S_ServerMessage(590));
                  } else {
                     var3.a(new S_ServerMessage(259));
                  }
               } else if (var3.aO() > 0) {
                  var3.a(new S_ServerMessage(258));
               } else if (!var4.ed() && !var4.eX()) {
                  var3.al(var4.fr());
                  var4.al(var3.fr());
                  var4.a(new S_Message_YN(252, var3.et()));
               } else {
                  var3.a(new S_ServerMessage(256));
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_Trade";
   }
}
