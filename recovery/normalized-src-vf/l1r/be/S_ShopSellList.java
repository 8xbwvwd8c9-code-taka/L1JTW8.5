package l1r.be;

import java.util.ArrayList;
import l1r.ao.ShopTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.as.L1BugBearRace;
import l1r.bh.L1Shop;
import l1r.bh.L1ShopItem;
import l1r.l1j.server.Config;

public class S_ShopSellList extends ServerBasePacket {
   public S_ShopSellList(L1NpcInstance var1, L1PcInstance var2) {
      L1Shop var3 = ShopTable.a().a(var1.z());
      if (var3 == null) {
         var2.a(new S_Html(var1.fr(), "nosell", var1.T()));
      } else {
         ArrayList var4 = new ArrayList<>();

         for (L1ShopItem var5 : var3.c()) {
            L1ItemInstance[] var10;
            int var9 = (var10 = var2.j().d(var5.a())).length;

            for (int var8 = 0; var8 < var9; var8++) {
               L1ItemInstance var7 = var10[var8];
               if (var7 != null && !var7.D() && var7.G() == 0 && var7.F() < 128) {
                  int var11 = (int)(var5.c() * Config.M / var5.d());
                  if (var7.N() == 40309) {
                     var11 = L1BugBearRace.a().a(var7.fr());
                  }

                  int[] var12 = new int[]{var7.fr(), var11};
                  var4.add(var12);
               }
            }
         }

         if (var4.isEmpty()) {
            var2.a(new S_Html(var1.fr(), "nosell", var1.T()));
         } else {
            this.c(55);
            this.a(var1.fr());
            this.b(var4.size());

            for (int[] var13 : var4) {
               this.a(var13[0]);
               this.a(var13[1]);
            }

            if (var1.z() == 190005) {
               this.b(14921);
            } else {
               this.b(7);
            }
         }
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ShopBuyList";
   }
}
