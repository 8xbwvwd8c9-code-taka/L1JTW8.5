package l1r.be;

import l1r.ap.L1ItemInstance;

public class S_ItemDesc extends ServerBasePacket {
   public S_ItemDesc(L1ItemInstance var1) {
      this.c(198);
      this.a(var1.fr());
      this.a(var1.r());
      this.a(var1.E());
      if (!var1.C()) {
         this.c(0);
      } else {
         byte[] var2 = var1.t();
         this.c(var2.length);
         byte[] var6 = var2;
         int var5 = var2.length;

         for (int var4 = 0; var4 < var5; var4++) {
            byte var3 = var6[var4];
            this.c(var3);
         }
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ItemDesc";
   }
}
