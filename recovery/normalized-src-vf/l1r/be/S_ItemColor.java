package l1r.be;

import l1r.ap.L1ItemInstance;

public class S_ItemColor extends ServerBasePacket {
   public S_ItemColor(L1ItemInstance var1) {
      if (var1 != null) {
         this.a(var1);
      }
   }

   private void a(L1ItemInstance var1) {
      this.c(18);
      this.a(var1.fr());
      this.c(var1.F());
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ItemColor";
   }
}
