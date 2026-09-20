package l1r.be;

public class S_Trade extends ServerBasePacket {
   public S_Trade(String var1) {
      this.c(10);
      this.a(var1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Trade";
   }
}
