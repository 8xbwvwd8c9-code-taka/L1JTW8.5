package l1r.be;

import java.util.List;
import l1r.ao.ItemTable;
import l1r.ao.ShopTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1TaxCalculator;
import l1r.as.L1BugBearRace;
import l1r.bh.L1Item;
import l1r.bh.L1Shop;
import l1r.bh.L1ShopItem;
import l1r.l1j.server.Config;

public class S_ShopBuyList extends ServerBasePacket {
   public static int a = 0;
   public static int b = 1;
   public static int c = 2;
   public static int d = 3;

   public S_ShopBuyList(L1NpcInstance var1, int var2) {
      if (var2 == a) {
         this.a(var1);
      } else if (var2 == b) {
         this.b(var1);
      } else if (var2 == c) {
         this.c(var1);
      }
   }

   public S_ShopBuyList(L1NpcInstance var1, L1PcInstance var2, int var3) {
      if (var3 == d) {
         this.a(var1, var2);
      }
   }

   private void a(L1NpcInstance var1) {
      this.c(51);
      this.a(var1.fr());
      L1TaxCalculator var2 = new L1TaxCalculator(var1);
      L1Shop var3 = ShopTable.a().a(var1.z());
      if (var3 == null) {
         this.b(0);
      } else {
         List var4 = var3.b();
         this.b(var4.size());

         for (int var5 = 0; var5 < var4.size(); var5++) {
            L1ShopItem var6 = var4.get(var5);
            L1Item var7 = var6.b();
            int var8 = var2.a((int)(var6.c() * Config.L));
            this.a(var6.b().g());
            this.b(var6.b().m());
            this.a(var8);
            if (var6.d() > 1) {
               this.a(var7.j() + " (" + var6.d() + ")");
            } else if (var7.aM() > 0) {
               this.a(var7.j() + " (" + var7.aM() + ")");
            } else {
               this.a(var7.j());
            }

            this.a(var7.U());
            L1Item var9 = ItemTable.a().a(var7.g());
            if (var9 == null) {
               this.c(0);
            } else {
               L1ItemInstance var10 = new L1ItemInstance(var9, 0);
               byte[] var11 = var10.t();
               this.c(var11.length);
               this.a(var11);
            }
         }

         this.b(7);
      }
   }

   private void b(L1NpcInstance var1) {
      this.c(51);
      this.a(var1.fr());
      L1Shop var2 = ShopTable.a().a(var1.z());
      this.b(var2.b().size());

      for (int var3 = 0; var3 < var2.b().size(); var3++) {
         L1ShopItem var4 = var2.b().get(var3);
         L1Item var5 = var4.b();
         this.a(var4.b().g());
         this.b(var4.b().m());
         this.a(var4.c());
         if (var4.d() > 1) {
            this.a(var5.j() + " (" + var4.d() + ")");
         } else {
            this.a(var5.j());
         }

         this.a(var5.U());
         L1Item var6 = ItemTable.a().a(var5.g());
         if (var6 == null) {
            this.c(0);
         } else {
            L1ItemInstance var7 = new L1ItemInstance(var6, 0);
            byte[] var8 = var7.t();
            this.c(var8.length);
            this.a(var8);
         }
      }

      if (var1.z() == 190005) {
         this.b(14921);
      } else if (var1.z() == 190045) {
         this.b(13258);
      } else if (var1.z() == 190095) {
         this.b(65533);
      } else if (var1.z() == 190353) {
         this.b(20222);
      }
   }

   private void c(L1NpcInstance var1) {
      this.c(51);
      this.a(var1.fr());
      L1Shop var2 = ShopTable.a().a(var1.z());
      if (var2 == null) {
         this.b(0);
      } else {
         List var3 = var2.b();
         this.b(var3.size());

         for (int var4 = 0; var4 < var3.size(); var4++) {
            L1ShopItem var5 = var3.get(var4);
            this.a(var4);
            this.b(var5.b().m());
            this.a((int)(var5.c() * Config.L));
            this.a(L1BugBearRace.a().a(var5.b().h()));
            this.a(var5.b().U());
            L1Item var6 = ItemTable.a().a(var5.b().g());
            L1ItemInstance var7 = new L1ItemInstance(var6, 0);
            byte[] var8 = var7.t();
            this.c(var8.length);
            this.a(var8);
         }

         this.b(7);
      }
   }

   private void a(L1NpcInstance var1, L1PcInstance var2) {
      this.c(51);
      this.a(var1.fr());
      L1TaxCalculator var3 = new L1TaxCalculator(var1);
      L1Shop var4 = ShopTable.a().a(var1.z());
      if (var4 == null) {
         this.b(0);
      } else {
         List var5 = var4.b();
         int[] var6 = new int[0];
         if (var1.z() == 190142) {
            if (var2.x()) {
               var6 = new int[]{27, 28, 29, 30, 31, 32, 33};
            } else if (var2.A()) {
               var6 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
            } else if (var2.B()) {
               var6 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
            } else if (var2.C()) {
               var6 = new int[]{24, 25, 26};
            } else if (var2.D()) {
               var6 = new int[]{34, 35, 36, 37, 38};
            } else if (var2.E()) {
               var6 = new int[]{39, 40, 41, 42, 43, 44, 45, 46};
            } else if (var2.F()) {
               var6 = new int[]{47, 48, 49, 50};
            }
         } else if (var1.z() == 190488) {
            if (var2.x()) {
               var6 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22};
            } else if (var2.z()) {
               var6 = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
            } else if (var2.A()) {
               var6 = new int[]{
                  0,
                  1,
                  2,
                  3,
                  4,
                  5,
                  6,
                  7,
                  8,
                  9,
                  10,
                  11,
                  12,
                  13,
                  14,
                  15,
                  23,
                  24,
                  25,
                  26,
                  27,
                  28,
                  29,
                  30,
                  31,
                  32,
                  33,
                  34,
                  35,
                  36,
                  37,
                  38,
                  39,
                  40,
                  41,
                  42,
                  43,
                  44,
                  45,
                  46,
                  47,
                  48,
                  49,
                  50,
                  51,
                  52
               };
            } else if (var2.B()) {
               var6 = new int[]{
                  0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 53
               };
            } else if (var2.C()) {
               var6 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 54, 55, 56, 57};
            } else if (var2.D()) {
               var6 = new int[]{58, 59, 60, 61, 62};
            } else if (var2.E()) {
               var6 = new int[]{63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73};
            } else if (var2.F()) {
               var6 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 74, 75, 76, 77, 78};
            }
         }

         this.b(var6.length);
         int[] var10 = var6;
         int var9 = var6.length;

         for (int var8 = 0; var8 < var9; var8++) {
            int var7 = var10[var8];
            L1ShopItem var11 = var5.get(var7);
            L1Item var12 = var11.b();
            int var13 = var3.a((int)(var11.c() * Config.L));
            this.a(var11.b().g());
            this.b(var11.b().m());
            this.a(var13);
            if (var11.d() > 1) {
               this.a(var12.j() + " (" + var11.d() + ")");
            } else {
               this.a(var12.j());
            }

            this.a(var12.U());
            L1Item var14 = ItemTable.a().a(var12.g());
            if (var14 == null) {
               this.c(0);
            } else {
               L1ItemInstance var15 = new L1ItemInstance(var14, 0);
               byte[] var16 = var15.t();
               this.c(var16.length);
               this.a(var16);
            }
         }

         this.b(7);
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ShopSellList";
   }
}
