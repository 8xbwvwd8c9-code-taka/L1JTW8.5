package l1r.aj;

import l1r.ap.L1BoardInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bj.ClientThread;

public class C_BoardRead extends ClientBasePacket {
   private static final String a = "[C] C_BoardRead";

   public C_BoardRead(byte[] var1, ClientThread var2) {
      super(var1);
      int var3 = this.b();
      int var4 = this.b();
      L1Object var5 = L1World.a().a(var3);
      L1BoardInstance var6 = (L1BoardInstance)var5;
      var6.b(var2.f(), var4);
   }

   @Override
   public String a() {
      return "[C] C_BoardRead";
   }
}
