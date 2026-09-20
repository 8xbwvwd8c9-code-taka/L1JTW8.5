package l1r.be;

import l1r.ap.L1PcInstance;

public class S_NewCharPacket extends ServerBasePacket {
   public S_NewCharPacket(L1PcInstance var1) {
      this.c(134);
      this.a(var1.et());
      this.a("");
      this.c(var1.ay());
      this.c(var1.aJ());
      this.b(var1.fa());
      this.b(var1.ew());
      this.b(var1.ex());
      this.c(var1.ey());
      this.c(var1.ev());
      this.c(var1.ez());
      this.c(var1.eB());
      this.c(var1.eA());
      this.c(var1.eE());
      this.c(var1.eC());
      this.c(var1.eD());
      this.c(0);
      this.a(var1.o());
      this.c((var1.ev() ^ var1.ez() ^ var1.eB() ^ var1.eA() ^ var1.eE() ^ var1.eC() ^ var1.eD()) & 0xFF);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_NewCharPacket";
   }
}
