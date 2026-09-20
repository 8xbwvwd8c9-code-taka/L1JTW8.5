package l1r.be;

import java.io.IOException;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.l1j.server.Config;

public class S_RetrieveListElven extends ServerBasePacket {
   public S_RetrieveListElven(int var1, L1PcInstance var2) {
      if (var2.j().c() < 180) {
         int var3 = var2.av().c();
         if (var3 > 0) {
            this.c(162);
            this.a(var1);
            this.b(var3);
            this.c(9);

            for (Object var4 : var2.av().d()) {
               L1ItemInstance var6 = (L1ItemInstance)var4;
               this.a(var6.fr());
               this.c(0);
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

            this.a(4);
            this.a(Config.aq);
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
