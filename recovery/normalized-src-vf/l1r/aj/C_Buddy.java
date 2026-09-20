package l1r.aj;

import l1r.ao.BuddyTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Buddy;
import l1r.be.S_ProtoBuffers;
import l1r.bj.ClientThread;

public class C_Buddy extends ClientBasePacket {
   public C_Buddy(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         L1Buddy var4 = BuddyTable.a().a(var3.fr());
         var3.a(new S_ProtoBuffers(337, var4.c()));
      }
   }

   @Override
   public String a() {
      return "C_Buddy";
   }
}
