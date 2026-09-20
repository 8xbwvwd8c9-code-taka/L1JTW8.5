package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.bh.L1BookMark;
import l1r.bj.ClientThread;

public class C_DeleteBookmark extends ClientBasePacket {
   private static final String a = "[C] C_DeleteBookmark";

   public C_DeleteBookmark(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         String var4 = this.g();
         L1BookMark.a(var3, var4);
      }
   }

   @Override
   public String a() {
      return "[C] C_DeleteBookmark";
   }
}
