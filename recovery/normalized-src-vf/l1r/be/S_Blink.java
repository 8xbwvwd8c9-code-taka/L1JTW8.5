package l1r.be;

public class S_Blink extends ServerBasePacket {
   public S_Blink() {
      this.c(97);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Blink";
   }
}
