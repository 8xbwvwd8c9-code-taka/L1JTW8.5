package l1r.be;

public class S_LoginGame extends ServerBasePacket {
   public S_LoginGame() {
      this.c(80);
      this.c(3);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_LoginGame";
   }
}
