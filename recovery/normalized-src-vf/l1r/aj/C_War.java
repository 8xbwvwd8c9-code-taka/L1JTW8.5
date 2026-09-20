package l1r.aj;

import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Clan;
import l1r.aq.L1War;
import l1r.aq.L1World;
import l1r.as.L1CastleWar;
import l1r.be.S_Message_YN;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_War extends ClientBasePacket {
   public C_War(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.c();
         String var5 = this.g();
         if (!var3.x()) {
            var3.a(new S_ServerMessage(478));
         } else if (var3.aF() == 0) {
            var3.a(new S_ServerMessage(272));
         } else {
            L1Clan var6 = ClanTable.a().a(var3.aF());
            if (var6 != null) {
               String var7 = var6.f();
               if (var3.fr() != var6.k()) {
                  var3.a(new S_ServerMessage(478));
               } else if (!var7.toLowerCase().equals(var5.toLowerCase())) {
                  if (var6.a()) {
                     if (var4 == 0) {
                        var3.a(new S_ServerMessage(474));
                     }
                  } else {
                     L1Clan var8 = ClanTable.a().c(var5);
                     if (var8 == null) {
                        var3.a(new S_ServerMessage(3982));
                     } else {
                        if (var3.ev() < 25) {
                           var3.a(new S_ServerMessage(var8.a() ? 475 : 232));
                        }

                        if (var4 == 0 && L1World.a().b(var7)) {
                           var3.a(new S_ServerMessage(234));
                        } else if (var4 != 2 && var4 != 3 || L1World.a().b(var7)) {
                           if (var8.a()) {
                              if (var4 == 0 && !L1CastleWar.a().a(var8.m())) {
                                 var3.a(new S_ServerMessage(476));
                                 return;
                              }

                              for (L1PcInstance var9 : var6.b()) {
                                 if (L1CastleLocation.a(var8.m(), var9)) {
                                    var3.a(new S_ServerMessage(477));
                                    return;
                                 }
                              }

                              L1War var11 = L1World.a().c(var5);
                              if (var4 == 0) {
                                 if (var11 == null) {
                                    new L1War(1, var7, var5);
                                 } else {
                                    var11.a(var6);
                                 }
                              } else if (var4 == 2 || var4 == 3) {
                                 if (var11 == null) {
                                    return;
                                 }

                                 if (!var11.c(var7, var5)) {
                                    return;
                                 }

                                 if (var4 == 2) {
                                    var11.a(var7, var5);
                                 } else if (var4 == 3) {
                                    var11.b(var7, var5);
                                 }
                              }
                           } else {
                              if (var4 == 0 && L1World.a().b(var5)) {
                                 var3.a(new S_ServerMessage(236, var5));
                                 return;
                              }

                              if (var4 == 2 || var4 == 3) {
                                 if (!L1World.a().b(var5)) {
                                    return;
                                 }

                                 L1War var12 = L1World.a().c(var5);
                                 if (!var12.c(var7, var5)) {
                                    return;
                                 }
                              }

                              if (var8.l() == null) {
                                 var3.a(new S_ServerMessage(218, var5));
                                 return;
                              }

                              L1PcInstance var13 = L1World.a().a(var8.l());
                              if (var13 == null) {
                                 var3.a(new S_ServerMessage(218, var5));
                                 return;
                              }

                              var13.am(var3.fr());
                              if (var4 == 0) {
                                 var13.a(new S_Message_YN(217, var7, var3.et()));
                              } else if (var4 == 2) {
                                 var13.a(new S_Message_YN(221, var7));
                              } else if (var4 == 3) {
                                 var13.a(new S_Message_YN(222, var7));
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_War";
   }
}
