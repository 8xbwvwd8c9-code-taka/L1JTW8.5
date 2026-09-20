package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.be.S_PrivateShop;
import l1r.bj.ClientThread;

public class C_ShopList extends ClientBasePacket {
   private static final String a = "[C] C_ShopList";

   public C_ShopList(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         int var4 = this.c();
         int var5 = this.b();
         var3.a(new S_PrivateShop(var3, var5, var4));
      }
   }

   @Override
   public String a() {
      return "[C] C_ShopList";
   }
}
