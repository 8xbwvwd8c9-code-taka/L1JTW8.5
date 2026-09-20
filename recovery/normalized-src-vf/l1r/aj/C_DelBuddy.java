package l1r.aj;

import l1r.ao.BuddyTable;
import l1r.ap.L1PcInstance;
import l1r.bj.ClientThread;

public class C_DelBuddy extends ClientBasePacket {
   private static final String a = "[C] C_DelBuddy";

   public C_DelBuddy(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         String var4 = this.g();
         BuddyTable.a().a(var3.fr(), var4);
      }
   }

   @Override
   public String a() {
      return "[C] C_DelBuddy";
   }
}
