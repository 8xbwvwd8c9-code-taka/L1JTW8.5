package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bj.ClientThread;

public class C_NpcTalk extends ClientBasePacket {
   public C_NpcTalk(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         L1Object var5 = L1World.a().a(var4);
         if (var5 != null) {
            var5.a(var3);
         }
      }
   }

   @Override
   public String a() {
      return "C_NpcTalk";
   }
}
