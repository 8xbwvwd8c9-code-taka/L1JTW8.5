package l1r.be;

import l1r.ap.L1PcInstance;

public class S_Strup extends ServerBasePacket {
   public S_Strup(L1PcInstance var1, int var2, int var3) {
      this.c(209);
      this.b(var3);
      this.c(var1.ez());
      this.c(var1.j().h());
      this.c(var2);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Strup";
   }
}
