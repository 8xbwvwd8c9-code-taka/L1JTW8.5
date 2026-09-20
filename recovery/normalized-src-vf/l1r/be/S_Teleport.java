package l1r.be;

public class S_Teleport extends ServerBasePacket {
   public S_Teleport() {
      this.c(29);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Teleport";
   }
}
