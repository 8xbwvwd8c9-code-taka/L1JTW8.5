package l1r.be;

import java.io.IOException;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;

public class S_RetrieveListChar extends ServerBasePacket {
   public S_RetrieveListChar(int var1, L1PcInstance var2) {
      if (var2.j().c() < 180) {
         int var3 = var2.aw().c();
         if (var3 > 0) {
            this.c(162);
            this.a(var1);
            this.b(var3);
            this.c(18);

            for (Object var4 : var2.aw().d()) {
               L1ItemInstance var6 = (L1ItemInstance)var4;
               this.a(var6.fr());
               this.c(var6.a().U());
               this.b(var6.e());
               this.c(var6.F());
               this.a(var6.E());
               this.c(var6.C() ? 1 : 0);
               this.a(var6.r());
               byte[] var7 = var6.t();
               this.c(var7.length);
               byte[] var11 = var7;
               int var10 = var7.length;

               for (int var9 = 0; var9 < var10; var9++) {
                  byte var8 = var11[var9];
                  this.c(var8);
               }
            }

            this.a(0);
            this.a(var2.cJ());
            this.b(0);
            this.b(0);
         } else {
            var2.a(new S_ServerMessage(1625));
         }
      } else {
         var2.a(new S_ServerMessage(263));
      }
   }

   @Override
   public byte[] a() throws IOException {
      return this.d();
   }
}
