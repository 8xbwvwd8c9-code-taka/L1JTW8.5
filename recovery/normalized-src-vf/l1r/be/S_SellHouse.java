package l1r.be;

public class S_SellHouse extends ServerBasePacket {
   public S_SellHouse(int var1, String var2) {
      this.c(193);
      this.a(var1);
      this.a(0);
      this.a(100000);
      this.a(100000);
      this.a(2000000000);
      this.b(2);
      this.a("agsell");
      this.a("agsell " + var2);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_SellHouse";
   }
}
