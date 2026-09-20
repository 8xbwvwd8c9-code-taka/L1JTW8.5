package l1r.be;

import l1r.ap.L1PcInstance;

public class S_Resurrection extends ServerBasePacket {
   public S_Resurrection(L1PcInstance var1, L1PcInstance var2, int var3) {
      this.c(12);
      this.a(var1.fr());
      this.c(var3);
      this.a(var2.fr());
      this.a(var1.aB());
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Resurrection";
   }
}
