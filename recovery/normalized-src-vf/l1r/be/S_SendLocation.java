package l1r.be;

public class S_SendLocation extends ServerBasePacket {
   public S_SendLocation(String var1, int var2, int var3, int var4, int var5) {
      this.c(121);
      this.c(111);
      this.a(var1);
      this.b(var2);
      this.b(var3);
      this.b(var4);
      this.c(var5);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_SendLocation";
   }
}
