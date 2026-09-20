package l1r.be;

public class S_ChangeName extends ServerBasePacket {
   public S_ChangeName(int var1, String var2) {
      this.c(219);
      this.a(var1);
      this.a(var2);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ChangeName";
   }
}
