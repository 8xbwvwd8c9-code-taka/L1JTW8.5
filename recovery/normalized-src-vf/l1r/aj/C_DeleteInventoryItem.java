package l1r.aj;

import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_DeleteInventoryItem extends ClientBasePacket {
   public C_DeleteInventoryItem(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();

         for (int var5 = 0; var5 < var4; var5++) {
            int var6 = this.b();
            int var7 = this.b();
            L1ItemInstance var8 = var3.j().e(var6);
            if (var8 != null) {
               if (var8.a().t()) {
                  var3.a(new S_ServerMessage(125));
               } else if (var3.N(var8.fr())) {
                  var3.a(new S_ServerMessage(1187));
               } else if (var3.O(var8.fr())) {
                  var3.a(new S_ServerMessage(1181));
               } else if (var8.D()) {
                  var3.a(new S_ServerMessage(125));
               } else if (var8.F() >= 128) {
                  var3.a(new S_ServerMessage(210, var8.a().h()));
               } else {
                  var3.j().b(var8, var7 > 0 ? var7 : var8.E());
               }
            }
         }

         var3.fg();
      }
   }

   @Override
   public String a() {
      return "C_DeleteInventoryItem";
   }
}
