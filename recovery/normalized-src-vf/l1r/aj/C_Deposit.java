package l1r.aj;

import l1r.ao.CastleTable;
import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Castle;
import l1r.bj.ClientThread;

public class C_Deposit extends ClientBasePacket {
   public C_Deposit(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         int var5 = this.b();
         if (var4 == var3.fr()) {
            L1Clan var6 = ClanTable.a().a(var3.aF());
            if (var6 != null) {
               int var7 = var6.m();
               if (var7 != 0) {
                  L1Castle var8 = CastleTable.a().a(var7);
                  synchronized (var8) {
                     int var10 = var8.f();
                     if (var10 + var5 >= 2000000000) {
                        var3.a(new S_SystemMessage("存入的金幣超過了2000000000上限"));
                        return;
                     }

                     if (var3.j().b(40308, var5)) {
                        var8.b(var10 + var5);
                        CastleTable.a().a(var8);
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_Deposit";
   }
}
