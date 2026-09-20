package l1r.bh;

import l1r.ao.ItemTable;

public class L1ShopItem {
   private final int a;
   private final L1Item b;
   private final int c;
   private final int d;

   public L1ShopItem(int var1, int var2, int var3) {
      this.a = var1;
      this.b = ItemTable.a().a(var1);
      this.c = var2;
      this.d = var3;
   }

   public int a() {
      return this.a;
   }

   public L1Item b() {
      return this.b;
   }

   public int c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   public L1ShopItem(L1Item var1, int var2) {
      this.a = var1.g();
      this.b = var1;
      this.c = var2;
      this.d = 1;
   }
}
