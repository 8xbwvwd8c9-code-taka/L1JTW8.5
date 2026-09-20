package l1r.be;

import l1r.ao.HouseTable;
import l1r.bh.L1House;

public class S_ApplyAuction extends ServerBasePacket {
   public S_ApplyAuction(int var1, String var2) {
      int var3 = Integer.valueOf(var2);
      L1House var4 = HouseTable.a().a(var3);
      this.c(193);
      this.a(var1);
      this.a(0);
      if (var4.o() == 0) {
         this.a(var4.k());
         this.a(var4.k());
      } else {
         this.a(var4.k() + 1);
         this.a(var4.k() + 1);
      }

      this.a(2000000000);
      this.b(2);
      this.a("agapply");
      this.a("agapply " + var2);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ApplyAuction";
   }
}
