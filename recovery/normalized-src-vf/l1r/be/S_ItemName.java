package l1r.be;

import l1r.ap.L1ItemInstance;

public class S_ItemName extends ServerBasePacket {
   public S_ItemName(L1ItemInstance var1) {
      if (var1 != null) {
         this.c(14);
         this.a(var1.fr());
         this.a(var1.r());
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ItemName";
   }
}
