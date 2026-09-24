package l1r.aj;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1DollInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.aq.L1PolyMorph;
import l1r.be.S_DoActionGFX;
import l1r.be.S_DoActionShop;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;
import l1r.bh.L1PrivateShopBuyList;
import l1r.bh.L1PrivateShopSellList;
import l1r.bj.ClientThread;

public class C_Shop extends ClientBasePacket {
   private static final Logger a = Logger.getLogger(C_Shop.class.getName());
   private static final String b = "[C] C_Shop";

   public C_Shop(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         int var4 = var3.fp();
         if (var4 != 340 && var4 != 350 && var4 != 360 && var4 != 370 && var4 != 800) {
            var3.a(new S_ServerMessage(876));
         } else {
            List var5 = var3.aU();
            List var6 = var3.aV();
            boolean var8 = true;
            int var9 = this.c();
            if (var9 == 0) {
               int var10 = this.d();
               if (var10 < 0 || var10 > 8) {
                  var8 = false;
               }

               for (int var14 = 0; var14 < var10 && var14 < 8; var14++) {
                  int var11 = this.b();
                  int var12 = this.b();
                  int var13 = this.b();
                  long var33 = (long)var12 * (long)var13;
                  if (var12 < 0 || var13 <= 0 || var33 < 0L || var33 > 2000000000L) {
                     var8 = false;
                     continue;
                  }

                  L1ItemInstance var7 = var3.j().e(var11);
                  if (var7 != null) {
                     if (!var7.a().s()) {
                        var8 = false;
                        var3.a(new S_SystemMessage(var7.a().h() + "這是不可能處理。"));
                     }

                     for (L1NpcInstance var15 : var3.ek().values()) {
                        if (var15 instanceof L1PetInstance) {
                           L1PetInstance var17 = (L1PetInstance)var15;
                           if (var7.fr() == var17.k()) {
                              var8 = false;
                              var3.a(new S_SystemMessage(var7.a().h() + "這是不可能處理。"));
                              break;
                           }
                        }
                     }

                     L1PrivateShopSellList var25 = new L1PrivateShopSellList();
                     var25.a(var11);
                     var25.c(var12);
                     var25.b(var13);
                     var5.add(var25);
                  }
               }

               int var24 = this.d();
               if (var24 < 0 || var24 > 8) {
                  var8 = false;
               }

               for (int var18 = 0; var18 < var24 && var18 < 8; var18++) {
                  int var26 = this.b();
                  int var27 = this.b();
                  int var28 = this.b();
                  long var34 = (long)var27 * (long)var28;
                  if (var27 < 0 || var28 <= 0 || var34 < 0L || var34 > 2000000000L) {
                     var8 = false;
                     continue;
                  }

                  L1ItemInstance var23 = var3.j().e(var26);
                  if (var23 != null) {
                     if (!var23.a().s()) {
                        var8 = false;
                        var3.a(new S_SystemMessage(var23.a().h() + "這是不可能處理。"));
                     }

                     if (var23.F() >= 128) {
                        var3.a(new S_ServerMessage(210, var23.a().h()));
                        return;
                     }

                     if (var23.E() > 1 && !var23.a().aF()) {
                        var3.a(new S_SystemMessage("此物品非堆疊，但異常堆疊無法交易。"));
                        return;
                     }

                     for (L1NpcInstance var19 : var3.ek().values()) {
                        if (var19 instanceof L1PetInstance) {
                           L1PetInstance var21 = (L1PetInstance)var19;
                           if (var23.fr() == var21.k()) {
                              var8 = false;
                              var3.a(new S_ServerMessage(1187));
                              break;
                           }
                        }
                     }

                     for (L1DollInstance var30 : var3.el().values()) {
                        if (var30.f() == var23.fr()) {
                           var8 = false;
                           var3.a(new S_ServerMessage(1181));
                           break;
                        }
                     }

                     L1PrivateShopBuyList var31 = new L1PrivateShopBuyList();
                     var31.a(var26);
                     var31.c(var27);
                     var31.b(var28);
                     var6.add(var31);
                  }
               }

               if (!var8) {
                  var5.clear();
                  var6.clear();
                  var3.g(false);
                  var3.a(new S_DoActionGFX(var3.fr(), 3));
                  var3.b(new S_DoActionGFX(var3.fr(), 3));
                  return;
               }

               byte[] var29 = this.h();
               var3.a(var29);
               var3.g(true);
               var3.a(new S_DoActionShop(var3.fr(), 70, var29));
               var3.b(new S_DoActionShop(var3.fr(), 70, var29));
               int var32 = 0;

               try {
                  var32 = Integer.parseInt(new String(var29, "utf8").split("tradezone")[1].substring(0, 1));
               } catch (Exception var22) {
                  a.log(Level.SEVERE, var22.getLocalizedMessage(), var22);
               }

               L1PolyMorph.a(var3, var32);
            } else if (var9 == 1) {
               var5.clear();
               var6.clear();
               var3.g(false);
               var3.a(new S_DoActionGFX(var3.fr(), 3));
               var3.b(new S_DoActionGFX(var3.fr(), 3));
               L1PolyMorph.a(var3);
            }
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_Shop";
   }
}
