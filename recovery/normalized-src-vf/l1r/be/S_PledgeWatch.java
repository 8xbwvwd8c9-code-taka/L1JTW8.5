package l1r.be;

import l1r.ao.ClanTable;
import l1r.aq.L1Clan;

public class S_PledgeWatch extends ServerBasePacket {
   public S_PledgeWatch(L1Clan var1) {
      this.c(44);
      this.b(2);
      this.a(var1.t().size());

      for (int var2 : var1.t()) {
         L1Clan var4 = ClanTable.a().a(var2);
         if (var4 == null) {
            this.a("null");
         } else {
            this.a(var4.f());
         }
      }
   }

   public S_PledgeWatch() {
      this.c(44);
      this.b(2);
      this.a(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ClanAttention";
   }
}
