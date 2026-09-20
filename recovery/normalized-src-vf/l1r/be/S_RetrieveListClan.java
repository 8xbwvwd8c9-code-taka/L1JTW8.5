package l1r.be;

import java.io.IOException;
import l1r.ao.ClanTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.l1j.server.Config;

public class S_RetrieveListClan extends ServerBasePacket {
   public S_RetrieveListClan(int var1, L1PcInstance var2) {
      L1Clan var3 = ClanTable.a().a(var2.aF());
      if (var3 != null) {
         if (var3.o() != 0 && var3.o() != var2.fr()) {
            var2.a(new S_ServerMessage(209));
         } else {
            if (var2.j().c() < 180) {
               int var4 = var3.c().c();
               if (var4 > 0) {
                  var3.i(var2.fr());
                  this.c(162);
                  this.a(var1);
                  this.b(var4);
                  this.c(5);

                  for (Object var5 : var3.c().d()) {
                     L1ItemInstance var7 = (L1ItemInstance)var5;
                     this.a(var7.fr());
                     this.c(var7.a().U());
                     this.b(var7.e());
                     this.c(var7.F());
                     this.a(var7.E());
                     this.c(var7.C() ? 1 : 0);
                     this.a(var7.r());
                     byte[] var8 = var7.t();
                     this.c(var8.length);
                     byte[] var12 = var8;
                     int var11 = var8.length;

                     for (int var10 = 0; var10 < var11; var10++) {
                        byte var9 = var12[var10];
                        this.c(var9);
                     }
                  }

                  this.a(500);
                  this.a(Config.ar);
                  this.b(0);
                  this.b(0);
               } else {
                  var2.a(new S_ServerMessage(1625));
               }
            } else {
               var2.a(new S_ServerMessage(263));
            }
         }
      }
   }

   @Override
   public byte[] a() throws IOException {
      return this.d();
   }
}
