package l1r.bh;

import java.util.List;

public class L1Shop {
   private final int a;
   private final List<L1ShopItem> b;
   private final List<L1ShopItem> c;

   public L1Shop(int var1, List<L1ShopItem> var2, List<L1ShopItem> var3) {
      if (var2 != null && var3 != null) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
      } else {
         throw new NullPointerException();
      }
   }

   public int a() {
      return this.a;
   }

   public List<L1ShopItem> b() {
      return this.b;
   }

   public List<L1ShopItem> c() {
      return this.c;
   }
}
