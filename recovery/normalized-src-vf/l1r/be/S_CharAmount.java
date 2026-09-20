package l1r.be;

import l1r.bh.L1Account;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class S_CharAmount extends ServerBasePacket {
   public S_CharAmount(int var1, ClientThread var2) {
      L1Account var3 = var2.e();
      int var4 = var3.k();
      int var5 = Config.au + var4;
      this.c(50);
      this.c(var1);
      this.c(var5);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_CharAmount";
   }
}
