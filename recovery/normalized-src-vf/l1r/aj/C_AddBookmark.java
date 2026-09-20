package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1HouseLocation;
import l1r.be.S_ServerMessage;
import l1r.bh.L1BookMark;
import l1r.bj.ClientThread;

public class C_AddBookmark extends ClientBasePacket {
   public C_AddBookmark(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         String var4 = this.g();
         if (!var3.fq().h()) {
            var3.a(new S_ServerMessage(214));
         } else if (!L1BookMark.a(var3.fs(), var3.ft(), var3.fp())) {
            var3.a(new S_ServerMessage(214));
         } else if (!L1CastleLocation.b(var3.fs(), var3.ft(), var3.fp()) && !L1HouseLocation.a(var3.fs(), var3.ft(), var3.fp())) {
            L1BookMark.b(var3, var4);
         } else {
            var3.a(new S_ServerMessage(214));
         }
      }
   }

   @Override
   public String a() {
      return "C_AddBookmark";
   }
}
