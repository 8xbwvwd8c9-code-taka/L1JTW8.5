package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_Message_YN;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_CreateParty extends ClientBasePacket {
   private static final String a = "[C] C_CreateParty";

   public C_CreateParty(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.c();
         if (var4 == 0 || var4 == 1) {
            int var10 = this.b();
            L1Object var13 = L1World.a().a(var10);
            if (var13 instanceof L1PcInstance) {
               L1PcInstance var14 = (L1PcInstance)var13;
               if (var3.fr() == var14.fr()) {
                  return;
               }

               if (!var3.fu().e(var14.fu()) || var3.fu().c(var14.fu()) > 7) {
                  var3.a(new S_ServerMessage(952));
                  return;
               }

               if (var14.q()) {
                  var3.a(new S_ServerMessage(415));
                  return;
               }

               if (var3.q()) {
                  if (var3.aL().e(var3)) {
                     var14.aU(var4);
                     var14.ak(var3.fr());
                     switch (var4) {
                        case 0:
                           var14.a(new S_Message_YN(953, var3.et()));
                           break;
                        case 1:
                           var14.a(new S_Message_YN(954, var3.et()));
                     }
                  } else {
                     var3.a(new S_ServerMessage(416));
                  }
               } else {
                  var3.aU(var4);
                  var14.ak(var3.fr());
                  switch (var4) {
                     case 0:
                        var14.a(new S_Message_YN(953, var3.et()));
                        break;
                     case 1:
                        var14.a(new S_Message_YN(954, var3.et()));
                  }
               }
            }
         } else if (var4 == 4 || var4 == 5) {
            String var9 = this.g();
            L1PcInstance var12 = L1World.a().a(var9);
            if (var12 == null) {
               var3.a(new S_ServerMessage(109));
               return;
            }

            if (var3.fr() == var12.fr()) {
               return;
            }

            if (!var3.fu().e(var12.fu()) || var3.fu().c(var12.fu()) > 7) {
               var3.a(new S_ServerMessage(952));
               return;
            }

            if (var12.q()) {
               var3.a(new S_ServerMessage(415));
               return;
            }

            if (var3.q()) {
               if (var3.aL().e(var3)) {
                  var12.aU(var4);
                  var12.ak(var3.fr());
                  switch (var4) {
                     case 4:
                        var12.a(new S_Message_YN(953, var3.et()));
                        break;
                     case 5:
                        var12.a(new S_Message_YN(954, var3.et()));
                  }
               } else {
                  var3.a(new S_ServerMessage(416));
               }
            } else {
               var3.aU(var4);
               var12.ak(var3.fr());
               switch (var4) {
                  case 4:
                     var12.a(new S_Message_YN(953, var3.et()));
                     break;
                  case 5:
                     var12.a(new S_Message_YN(954, var3.et()));
               }
            }
         } else if (var4 == 2) {
            String var5 = this.g();
            L1PcInstance var6 = L1World.a().a(var5);
            if (var6 == null) {
               var3.a(new S_ServerMessage(109));
               return;
            }

            if (var3.fr() == var6.fr()) {
               return;
            }

            if (!var3.fu().e(var6.fu()) || var3.fu().c(var6.fu()) > 7) {
               var3.a(new S_ServerMessage(952));
               return;
            }

            if (var6.r()) {
               var3.a(new S_ServerMessage(415));
               return;
            }

            if (var3.r()) {
               if (var3.aM().b(var3)) {
                  var6.ak(var3.fr());
                  var6.a(new S_Message_YN(951, var3.et()));
               } else {
                  var3.a(new S_ServerMessage(416));
               }
            } else {
               var6.ak(var3.fr());
               var6.a(new S_Message_YN(951, var3.et()));
            }
         } else if (var4 == 3) {
            if (var3.aL() == null || !var3.aL().e(var3)) {
               var3.a(new S_ServerMessage(1697));
               return;
            }

            int var8 = this.b();
            L1Object var11 = L1World.a().a(var8);
            if (var11 == null || var3.fr() == var11.fr() || !(var11 instanceof L1PcInstance)) {
               return;
            }

            if (!var3.fu().e(var11.fu()) || var3.fu().c(var11.fu()) > 7) {
               var3.a(new S_ServerMessage(1695));
               return;
            }

            L1PcInstance var7 = (L1PcInstance)var11;
            if (!var7.q()) {
               var3.a(new S_ServerMessage(1696));
               return;
            }

            var3.a(new S_Message_YN(1703, ""));
            var3.aL().g(var7);
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_CreateParty";
   }
}
