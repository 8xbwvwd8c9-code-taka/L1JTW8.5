package l1r.be;

public class S_Light extends ServerBasePacket {
   public S_Light(int var1, int var2) {
      this.c(212);
      this.a(var1);
      this.c(var2);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Light";
   }
}
