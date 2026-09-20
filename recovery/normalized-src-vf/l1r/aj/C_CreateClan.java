package l1r.aj;

import l1r.ao.ClanMembersTable;
import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.be.S_ClanName;
import l1r.be.S_PacketBox;
import l1r.be.S_PledgeWatch;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_CreateClan extends ClientBasePacket {
   private static final String a = "[C] C_CreateClan";

   public C_CreateClan(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         String var4 = this.g();
         if (var3.x()) {
            if (var3.aF() == 0) {
               for (L1Clan var5 : ClanTable.a().b().values()) {
                  if (var5.f().toLowerCase().equals(var4.toLowerCase())) {
                     var3.a(new S_ServerMessage(99));
                     return;
                  }
               }

               if (var3.j().g(40308, 30000)) {
                  L1Clan var7 = ClanTable.a().a(var3, var4);
                  ClanMembersTable.a().a(var3);
                  if (var7 != null) {
                     var3.j().b(40308, 30000);
                     var3.a(new S_ServerMessage(84, var4));
                     var3.a(new S_ClanName(var3, true));
                     var3.a(new S_PacketBox(173, var7.j()));
                     var3.a(new S_PledgeWatch(var7));
                  }
               } else {
                  var3.a(new S_ServerMessage(189));
               }
            } else {
               var3.a(new S_ServerMessage(86));
            }
         } else {
            var3.a(new S_ServerMessage(85));
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_CreateClan";
   }
}
