package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.bj.ClientThread;

public class C_GotoMap extends ClientBasePacket {
   public C_GotoMap(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.d();
         if (var4 == 65535) {
            L1Teleport.a(var3);
         } else {
            int var5 = this.d();
            int var6 = this.d();
            int var7 = var3.fp();
            if (var7 == 5) {
               var3.j().b(40299, 1);
            } else if (var7 == 6) {
               var3.j().b(40298, 1);
            } else if (var7 == 83) {
               var3.j().b(40300, 1);
            } else if (var7 == 84) {
               var3.j().b(40301, 1);
            } else if (var7 == 446) {
               var3.j().b(40303, 1);
            } else if (var7 == 447) {
               var3.j().b(40302, 1);
            }

            L1Teleport.a(var3, var5, var6, var4, 0, true);
         }
      }
   }

   @Override
   public String a() {
      return "C_Ship";
   }
}
