package l1r.be;

public class S_ChangeShape extends ServerBasePacket {
   public S_ChangeShape(int var1, int var2, int var3) {
      this.c(144);
      this.a(var1);
      this.b(var2);
      this.c(var3);
      this.b(65535);
      this.c(0);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ChangeShape";
   }
}
