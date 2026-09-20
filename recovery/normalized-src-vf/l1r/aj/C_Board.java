package l1r.aj;

import l1r.ap.L1AuctionBoardInstance;
import l1r.ap.L1BoardInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bj.ClientThread;

public class C_Board extends ClientBasePacket {
   private static final String a = "[C] C_Board";

   private boolean a(L1Object var1) {
      return var1 instanceof L1BoardInstance || var1 instanceof L1AuctionBoardInstance;
   }

   public C_Board(byte[] var1, ClientThread var2) {
      super(var1);
      int var3 = this.b();
      L1Object var4 = L1World.a().a(var3);
      if (this.a(var4)) {
         var4.c(var2.f());
      }
   }

   @Override
   public String a() {
      return "[C] C_Board";
   }
}
