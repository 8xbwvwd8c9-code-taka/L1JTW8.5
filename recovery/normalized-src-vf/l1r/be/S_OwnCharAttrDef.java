package l1r.be;

import l1r.ap.L1PcInstance;

public class S_OwnCharAttrDef extends ServerBasePacket {
   public S_OwnCharAttrDef(L1PcInstance var1) {
      this.c(235);
      this.a(var1.ey());
      this.b(var1.eH());
      this.b(var1.eG());
      this.b(var1.eF());
      this.b(var1.eI());
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_OwnCharAttrDef";
   }
}
