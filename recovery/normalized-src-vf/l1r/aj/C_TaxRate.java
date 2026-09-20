package l1r.aj;

import l1r.ao.CastleTable;
import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.bh.L1Castle;
import l1r.bj.ClientThread;

public class C_TaxRate extends ClientBasePacket {
   private static final String a = "[C] C_TaxRate";

   public C_TaxRate(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         int var5 = this.c();
         if (var4 == var3.fr()) {
            L1Clan var6 = ClanTable.a().a(var3.aF());
            if (var6 != null) {
               int var7 = var6.m();
               if (var7 != 0) {
                  L1Castle var8 = CastleTable.a().a(var7);
                  if (var5 >= 10 && var5 <= 50) {
                     var8.a(var5);
                     CastleTable.a().a(var8);
                  }
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_TaxRate";
   }
}
