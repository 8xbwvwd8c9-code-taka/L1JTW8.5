package l1r.be;

public class S_SystemMessage extends ServerBasePacket {
   public S_SystemMessage(String var1) {
      this.c(107);
      this.c(9);
      this.a(var1);
   }

   public S_SystemMessage(String var1, boolean var2) {
      this.c(27);
      this.c(2);
      this.a(0);
      this.a(var1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_SystemMessage";
   }
}
