package l1r.be;

import l1r.ap.L1ItemInstance;

public class S_ItemAttribute extends ServerBasePacket {
   public S_ItemAttribute(L1ItemInstance var1) {
      this.c(121);
      this.c(149);
      this.a(var1.fr());
      this.c(var1.v());
      this.c(24);
      this.c(0);
      this.b(0);
      this.b(0);
      if (var1.a().aP() == 28) {
         this.c(var1.a().V() - 1);
      } else {
         this.c(var1.G());
      }

      this.a(var1.fr());
      this.a(8);
      this.a(0);
      this.c(var1.F() >= 128 ? 3 : (var1.a().s() ? 7 : 2));
      if (var1.a().aP() == 15) {
         this.a(7738);
      } else if (var1.a().aP() == 18) {
         this.a(7739);
      } else if (var1.a().aP() == 16) {
         this.a(7740);
      } else if (var1.G() > 0) {
         int var2 = var1.G() + 8042;
         this.a(Math.max(8042, Math.min(var2, 8056)));
      } else {
         this.a(0);
      }

      this.c(0);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ItemAttribute";
   }
}
