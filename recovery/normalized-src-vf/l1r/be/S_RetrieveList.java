package l1r.be;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.l1j.server.Config;

public class S_RetrieveList extends ServerBasePacket {
   public S_RetrieveList(int var1, L1PcInstance var2) {
      if (var2.j().c() < 180) {
         int var3 = var2.au().c();
         if (var3 > 0) {
            this.c(162);
            this.a(var1);
            this.b(var3);
            this.c(3);

            for (Object var4 : var2.au().d()) {
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

            this.a(100);
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

   public S_RetrieveList(ConcurrentHashMap<Integer, L1ItemInstance> var1) {
      this.c(162);
      this.a(0);
      this.b(var1.size());
      this.c(25);

      for (int var2 : var1.keySet()) {
         L1ItemInstance var4 = var1.get(var2);
         this.a(var2);
         this.c(var4.a().U());
         this.b(var4.e());
         this.c(var4.F());
         this.a(1);
         this.c(var4.C() ? 1 : 0);
         this.a(var4.b());
      }

      this.a(0);
      this.a(0);
   }

   public S_RetrieveList(ArrayList<L1ItemInstance> var1) {
      this.c(162);
      this.a(var1.hashCode());
      this.b(var1.size());
      this.c(17);
      int var2 = 0;

      for (L1ItemInstance var3 : var1) {
         this.a(var2++);
         this.c(var3.a().U());
         this.b(var3.e());
         this.c(var3.F());
         this.a(1);
         this.c(var3.C() ? 1 : 0);
         this.a(var3.b());
      }

      this.a(0);
      this.a(0);
   }

   @Override
   public byte[] a() throws IOException {
      return this.d();
   }
}
