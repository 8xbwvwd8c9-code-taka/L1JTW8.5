package l1r.aj;

import l1r.ao.CastleTable;
import l1r.ao.ClanTable;
import l1r.ao.ItemTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Castle;
import l1r.bj.ClientThread;

public class C_Drawal extends ClientBasePacket {
   public C_Drawal(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         int var5 = this.b();
         L1ItemInstance var6 = var3.j().b(40308);
         long var7 = 0L;
         if (var6 != null) {
            var7 = var6.E();
         }

         if (var7 + var5 > 2000000000L) {
            var3.a(new S_SystemMessage("你身上的金幣已經超過2000000000了，所以不能領取金幣。"));
         } else {
            L1Clan var9 = ClanTable.a().a(var3.aF());
            if (var9 != null) {
               int var10 = var9.m();
               if (var10 != 0) {
                  L1Castle var11 = CastleTable.a().a(var10);
                  if (var11 != null && var5 > 0 && var5 <= var11.f()) {
                     CastleTable.a().transferTreasuryAdena(var3, var10, var5, false);
                  }
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_Drawal";
   }
}
