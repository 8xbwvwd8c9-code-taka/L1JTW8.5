package l1r.be;

import l1r.ap.L1ItemInstance;

public class S_TradeAddItem extends ServerBasePacket {
   public S_TradeAddItem(L1ItemInstance var1, int var2, int var3) {
      this.c(123);
      this.c(var3);
      this.b(var1.a().m());
      this.a(var1.c(var2));
      if (!var1.C()) {
         this.c(3);
         this.c(0);
      } else {
         this.c(var1.F());
         byte[] var4 = var1.t();
         this.c(var4.length);
         this.a(var4);
      }

      this.b(var1.m());
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_TradeAddItem";
   }
}
