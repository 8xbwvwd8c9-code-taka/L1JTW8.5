package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.be.S_Emblem;
import l1r.bj.ClientThread;

public class C_EmblemDownload extends ClientBasePacket {
   public C_EmblemDownload(byte[] var1, ClientThread var2) {
      super(var1);
      int var3 = this.b();
      L1PcInstance var4 = var2.f();
      if (var4 != null) {
         var4.a(new S_Emblem(var3));
      }
   }

   @Override
   public String a() {
      return "C_EmblemDownload";
   }
}
