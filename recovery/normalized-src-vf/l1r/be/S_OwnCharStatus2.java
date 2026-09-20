package l1r.be;

import l1r.ap.L1PcInstance;

public class S_OwnCharStatus2 extends ServerBasePacket {
   public S_OwnCharStatus2(L1PcInstance var1) {
      this.c(164);
      this.b(var1.ez());
      this.b(var1.eD());
      this.b(var1.eE());
      this.b(var1.eB());
      this.b(var1.eA());
      this.b(var1.eC());
      this.c(var1.j().h());
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_OwnCharStatus2";
   }
}
