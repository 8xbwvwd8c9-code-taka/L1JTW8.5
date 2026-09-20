package l1r.be;

public class S_Sound extends ServerBasePacket {
   public S_Sound(int var1) {
      this.c(206);
      this.c(0);
      this.b(var1);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Sound";
   }
}
