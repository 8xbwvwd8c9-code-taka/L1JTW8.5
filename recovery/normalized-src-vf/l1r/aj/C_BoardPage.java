package l1r.aj;

import l1r.ap.L1BoardInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bj.ClientThread;

public class C_BoardPage extends ClientBasePacket {
   public C_BoardPage(byte[] var1, ClientThread var2) {
      super(var1);
      if (var2 != null) {
         L1PcInstance var3 = var2.f();
         if (var3 != null) {
            int var4 = this.b();
            int var5 = this.b();
            L1Object var6 = L1World.a().a(var4);
            if (!(var6 instanceof L1BoardInstance) || var3.fp() != var6.fp() || var3.f(var6) > 3) {
               return;
            }
            L1BoardInstance var7 = (L1BoardInstance)var6;
            var7.a(var3, var5);
         }
      }
   }

   @Override
   public String a() {
      return "C_BoardPage";
   }
}
