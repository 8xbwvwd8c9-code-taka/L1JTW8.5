package l1r.aj;

import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.aq.L1World;
import l1r.be.S_Message_YN;
import l1r.be.S_PledgeWatch;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_PledgeWatch extends ClientBasePacket {
   public C_PledgeWatch(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         if (var3.aF() == 0) {
            var3.a(new S_ServerMessage(518));
         } else {
            L1Clan var4 = ClanTable.a().a(var3.aF());
            if (var4 == null || !var3.x() || var3.fr() != var4.k()) {
               var3.a(new S_ServerMessage(518));
               return;
            }

            int var5 = this.c();
            if (var5 == 0) {
               String var6 = this.g();
               if (var4.f().equalsIgnoreCase(var6)) {
                  return;
               }

               L1Clan var7 = ClanTable.a().c(var6);
               if (var7 == null) {
                  var3.a(new S_ServerMessage(3982));
                  return;
               }

               L1PcInstance var8 = L1World.a().a(var7.l());
               if (var8 == null) {
                  var3.a(new S_ServerMessage(3349));
                  return;
               }

               var8.a(new S_Message_YN(3348, var4.f()));
               var8.am(var4.e());
            } else if (var5 == 1) {
               String var10 = this.g();
               L1Clan var11 = ClanTable.a().c(var10);
               if (var11 == null) {
                  var3.a(new S_ServerMessage(3982));
                  return;
               }

               var4.t().remove(Integer.valueOf(var11.e()));

               for (L1PcInstance var12 : var4.b()) {
                  var12.a(new S_ServerMessage(3359, var11.f()));
                  var12.a(new S_PledgeWatch(var4));
               }

               ClanTable.a().b(var4);
               var11.t().remove(Integer.valueOf(var4.e()));

               for (L1PcInstance var13 : var11.b()) {
                  var13.a(new S_ServerMessage(3359, var4.f()));
                  var13.a(new S_PledgeWatch(var11));
               }

               ClanTable.a().b(var11);
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_PledgeWatch";
   }
}
