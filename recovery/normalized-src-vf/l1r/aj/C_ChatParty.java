package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1ChatParty;
import l1r.aq.L1World;
import l1r.be.S_Html;
import l1r.be.S_Message_YN;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_ChatParty extends ClientBasePacket {
   public C_ChatParty(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         int var4 = this.c();
         if (var4 == 0) {
            String var5 = this.g();
            if (!var3.r()) {
               var3.a(new S_ServerMessage(425));
               return;
            }

            if (!var3.aM().b(var3)) {
               var3.a(new S_ServerMessage(427));
               return;
            }

            L1PcInstance var6 = L1World.a().a(var5);
            if (var6 == null) {
               var3.a(new S_ServerMessage(109));
               return;
            }

            if (var3.fr() == var6.fr()) {
               return;
            }

            L1PcInstance[] var10;
            int var9 = (var10 = var3.aM().e()).length;

            for (int var8 = 0; var8 < var9; var8++) {
               L1PcInstance var7 = var10[var8];
               if (var7.et().toLowerCase().equals(var5.toLowerCase())) {
                  var3.aM().d(var7);
                  return;
               }
            }

            var3.a(new S_ServerMessage(426, var5));
         } else if (var4 == 1) {
            if (var3.r()) {
               var3.aM().c(var3);
            }
         } else if (var4 == 2) {
            L1ChatParty var11 = var3.aM();
            if (var3.r()) {
               var3.a(new S_Html(var3.fr(), "party", var11.c().et(), var11.d()));
            } else {
               var3.a(new S_ServerMessage(425));
            }
         } else if (var4 == 3) {
            String var12 = this.g();
            L1PcInstance var13 = L1World.a().a(var12);
            if (var13 == null) {
               var3.a(new S_ServerMessage(109));
               return;
            }

            if (var3.fr() == var13.fr()) {
               return;
            }

            if (!var3.fu().e(var13.fu()) || var3.fu().c(var13.fu()) > 7) {
               var3.a(new S_ServerMessage(952));
               return;
            }

            if (var13.r()) {
               var3.a(new S_ServerMessage(415));
               return;
            }

            if (var3.r()) {
               if (var3.aM().b(var3)) {
                  var13.ak(var3.fr());
                  var13.a(new S_Message_YN(951, var3.et()));
               } else {
                  var3.a(new S_ServerMessage(416));
               }
            } else {
               var13.ak(var3.fr());
               var13.a(new S_Message_YN(951, var3.et()));
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_ChatParty";
   }
}
