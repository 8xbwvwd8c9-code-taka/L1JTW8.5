package l1r.aj;

import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.be.S_Message_YN;
import l1r.be.S_ServerMessage;
import l1r.bi.LineageUtil;
import l1r.bj.ClientThread;

public class C_JoinClan extends ClientBasePacket {
   public C_JoinClan(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         L1PcInstance var4 = LineageUtil.a(var3, true);
         if (var4 != null) {
            if (var4.aF() == 0) {
               var3.a(new S_ServerMessage(90, var4.et()));
            } else if (var3.aF() == var4.aF()) {
               var3.a(new S_ServerMessage(1201));
            } else if (!L1Clan.b(var4.aH())) {
               var3.a(new S_ServerMessage(92, var4.et()));
            } else {
               if (var3.aF() != 0) {
                  if (var3.aH() == 4) {
                     var3.a(new S_ServerMessage(1206));
                     return;
                  }

                  L1Clan var5 = ClanTable.a().a(var3.aF());
                  if (var5 == null) {
                     return;
                  }

                  if (var5.m() != 0 || var5.n() != 0) {
                     var3.a(new S_ServerMessage(665));
                     return;
                  }

                  if (var3.aH() != 10) {
                     var3.a(new S_ServerMessage(89));
                     return;
                  }

                  if (var4.aH() != 4) {
                     if (var4.aH() == 3) {
                        var3.a(new S_ServerMessage(2504));
                     } else {
                        var3.a(new S_ServerMessage(2498));
                     }

                     return;
                  }
               }

               var4.am(var3.fr());
               var4.a(new S_Message_YN(97, var3.et()));
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_JoinClan";
   }
}
