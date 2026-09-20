package l1r.aj;

import l1r.ao.CharacterConfigTable;
import l1r.ap.L1PcInstance;
import l1r.bj.ClientThread;

public class C_CharcterConfig extends ClientBasePacket {
   public C_CharcterConfig(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         byte[] var5 = this.a(var4);
         if (var4 > 0) {
            CharacterConfigTable.a().a(var3.fr(), var5);
         }
      }
   }

   @Override
   public String a() {
      return "C_CharcterConfig";
   }
}
