package l1r.aj;

import l1r.ao.CastleTable;
import l1r.ao.NpcTable;
import l1r.ap.L1PcInstance;
import l1r.ap.L1SummonInstance;
import l1r.bh.L1Castle;
import l1r.bh.L1Npc;
import l1r.bj.ClientThread;

public class C_MercenaryArrange extends ClientBasePacket {
   public C_MercenaryArrange(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         int var5 = this.d();
         int var6 = this.d();
         int var7 = this.b();
         int var8 = this.d();
         L1Castle var9 = CastleTable.a().a(var6);
         if (var9 != null) {
            for (int var10 = 0; var10 < var8; var10++) {
               int var11 = 0;

               for (L1Castle.a var12 : var9.k()) {
                  if (var12.c > 0) {
                     var11 = var12.a;
                     var12.c--;
                     break;
                  }
               }

               if (var11 == 0) {
                  return;
               }

               L1Npc var14 = NpcTable.a().a(var11);
               L1SummonInstance var15 = new L1SummonInstance(var14, var3);
               var15.o(6);
            }

            CastleTable.a().a(var9);
         }
      }
   }

   @Override
   public String a() {
      return "C_MercenaryArrange";
   }
}
