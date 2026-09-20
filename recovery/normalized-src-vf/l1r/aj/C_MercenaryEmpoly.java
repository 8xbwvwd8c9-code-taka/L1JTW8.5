package l1r.aj;

import l1r.ao.CastleTable;
import l1r.ap.L1PcInstance;
import l1r.bh.L1Castle;
import l1r.bj.ClientThread;

public class C_MercenaryEmpoly extends ClientBasePacket {
   public C_MercenaryEmpoly(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         int var5 = this.d();
         L1Castle var6 = CastleTable.a().a(var4);
         if (var6 != null) {
            for (int var7 = 0; var7 < var5; var7++) {
               int var8 = this.d();
               int var9 = this.d();
               int var10 = this.d();
               if (var6.f() < var10 * var9) {
                  break;
               }

               if (var6.k().get(var7) != null) {
                  var6.k().get(var7).c += var9;
                  var6.b(var6.f() - var10 * var9);
               }
            }

            CastleTable.a().a(var6);
         }
      }
   }

   @Override
   public String a() {
      return "C_MercenaryEmpoly";
   }
}
