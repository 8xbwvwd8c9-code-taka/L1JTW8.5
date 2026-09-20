package l1r.be;

import l1r.ap.L1PcInstance;

public class S_Karma extends ServerBasePacket {
   public S_Karma(L1PcInstance var1) {
      this.c(121);
      this.c(87);
      this.a(var1.P());
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Karma";
   }
}
