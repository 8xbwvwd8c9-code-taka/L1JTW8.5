package l1r.be;

import java.util.List;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.bh.L1PrivateShopBuyList;
import l1r.bh.L1PrivateShopSellList;

public class S_PrivateShop extends ServerBasePacket {
   public S_PrivateShop(L1PcInstance var1, int var2, int var3) {
      L1PcInstance var4 = (L1PcInstance)L1World.a().a(var2);
      if (var4 != null) {
         this.c(39);
         this.c(var3);
         this.a(var2);
         if (var3 == 0) {
            List<L1PrivateShopSellList> var5 = var4.aU();
            int var6 = var5.size();
            var1.an(var6);
            this.b(var6);

            for (int var7 = 0; var7 < var6; var7++) {
               L1PrivateShopSellList var8 = var5.get(var7);
               int var9 = var8.a();
               int var10 = var8.b() - var8.d();
               int var11 = var8.c();
               L1ItemInstance var12 = var4.j().e(var9);
               if (var12 != null) {
                  this.c(0);
                  this.a(var10);
                  this.a(var11);
                  this.b(var12.a().m());
                  this.c(var12.G());
                  this.c(var12.C() ? 1 : 0);
                  this.c(var12.F());
                  this.a(var12.c(var10));
                  byte[] var13 = var12.t();
                  this.c(var13.length);
                  byte[] var17 = var13;
                  int var16 = var13.length;

                  for (int var15 = 0; var15 < var16; var15++) {
                     byte var14 = var17[var15];
                     this.c(var14);
                  }
               }
            }
         } else if (var3 == 1) {
            List<L1PrivateShopBuyList> var18 = var4.aV();
            int var19 = var18.size();
            this.b(var19);

            for (int var20 = 0; var20 < var19; var20++) {
               L1PrivateShopBuyList var21 = var18.get(var20);
               int var22 = var21.a();
               int var23 = var21.b();
               int var24 = var21.c();
               L1ItemInstance var25 = var4.j().e(var22);

               for (L1ItemInstance var26 : var1.j().d()) {
                  if (var25.N() == var26.N() && var25.G() == var26.G()) {
                     this.c(var20);
                     this.a(var23);
                     this.a(var24);
                     this.a(var26.fr());
                     this.c(0);
                  }
               }
            }
         }

         this.b(0);
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }
}
