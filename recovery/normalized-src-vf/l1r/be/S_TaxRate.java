package l1r.be;

public class S_TaxRate extends ServerBasePacket {
   public S_TaxRate(int var1) {
      this.c(176);
      this.a(var1);
      this.c(10);
      this.c(50);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_TaxRate";
   }
}
