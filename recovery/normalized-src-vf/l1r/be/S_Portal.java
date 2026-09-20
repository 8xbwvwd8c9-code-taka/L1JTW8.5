package l1r.be;

public class S_Portal extends ServerBasePacket {
   public S_Portal(int var1, int var2, int var3) {
      this.c(244);
      this.b(var3);
      this.b(var1 * 2);
      this.b(var2 + 32768);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Portal";
   }
}
