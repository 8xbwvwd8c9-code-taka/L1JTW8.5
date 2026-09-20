package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.be.S_Message_YN;
import l1r.be.S_ServerMessage;
import l1r.bi.LineageUtil;
import l1r.bj.ClientThread;

public class C_Propose extends ClientBasePacket {
   private static final String a = "[C] C_Propose";

   public C_Propose(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.c();
         if (var4 == 0) {
            if (var3.bN()) {
               return;
            }

            L1PcInstance var5 = LineageUtil.a(var3, false);
            if (var5 != null) {
               if (var3.bD() != 0) {
                  var3.a(new S_ServerMessage(657));
                  return;
               }

               if (var5.bD() != 0) {
                  var3.a(new S_ServerMessage(658));
                  return;
               }

               if (var3.aJ() == var5.aJ()) {
                  var3.a(new S_ServerMessage(661));
                  return;
               }

               if (var3.fs() >= 33974
                  && var3.fs() <= 33976
                  && var3.ft() >= 33362
                  && var3.ft() <= 33365
                  && var3.fp() == 4
                  && var5.fs() >= 33974
                  && var5.fs() <= 33976
                  && var5.ft() >= 33362
                  && var5.ft() <= 33365
                  && var5.fp() == 4) {
                  var5.am(var3.fr());
                  var5.a(new S_Message_YN(654, var3.et()));
               }
            }
         } else if (var4 == 1) {
            if (var3.bD() == 0) {
               var3.a(new S_ServerMessage(662));
               return;
            }

            var3.a(new S_Message_YN(653, ""));
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_Propose";
   }
}
