package l1r.be;

import l1r.ap.L1PcInstance;

public class S_Dexup extends ServerBasePacket {
   public S_Dexup(L1PcInstance var1, int var2, int var3) {
      this.c(124);
      this.b(var3);
      this.c(var1.eB());
      this.c(var2);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Dexup";
   }
}
