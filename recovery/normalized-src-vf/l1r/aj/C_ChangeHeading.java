package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.be.S_ChangeHeading;
import l1r.bj.ClientThread;

public class C_ChangeHeading extends ClientBasePacket {
   private static final String a = "[C] C_ChangeHeading";

   public C_ChangeHeading(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.c();
         var3.ct(var4);
         if (!var3.aA() && !var3.bN()) {
            if (var3.ff()) {
               var3.c(new S_ChangeHeading(var3));
            } else {
               var3.b(new S_ChangeHeading(var3));
            }
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_ChangeHeading";
   }
}
