package l1r.aj;

import l1r.be.S_ServerVersion;
import l1r.bj.ClientThread;

public class C_ServerVersion extends ClientBasePacket {
   private static final String a = "[C] C_ServerVersion";

   public C_ServerVersion(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      this.d();
      this.c();
      int var3 = this.b();
      int var4 = this.d();
      int var5 = this.d();
      int var6 = this.b();
      var2.a(new S_ServerVersion());
   }

   @Override
   public String a() {
      return "[C] C_ServerVersion";
   }
}
