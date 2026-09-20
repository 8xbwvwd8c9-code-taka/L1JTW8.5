package l1r.be;

import l1r.ap.L1ItemInstance;

public class S_DeleteInventoryItem extends ServerBasePacket {
   public S_DeleteInventoryItem(L1ItemInstance var1) {
      if (var1 != null) {
         this.c(112);
         this.a(var1.fr());
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_DeleteInventoryItem";
   }
}
