package l1r.be;

public class S_PinkName extends ServerBasePacket {
   public S_PinkName(int var1, int var2) {
      this.c(201);
      this.a(var1);
      this.a(var2);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_PinkName";
   }
}
