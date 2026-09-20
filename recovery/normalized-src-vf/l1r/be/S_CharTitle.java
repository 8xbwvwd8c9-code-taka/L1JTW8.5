package l1r.be;

public class S_CharTitle extends ServerBasePacket {
   public S_CharTitle(int var1, String var2) {
      this.c(130);
      this.a(var1);
      this.a(var2);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_CharTitle";
   }
}
