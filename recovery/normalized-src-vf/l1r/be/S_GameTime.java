package l1r.be;

public class S_GameTime extends ServerBasePacket {
   public S_GameTime(int var1) {
      this.c(242);
      this.a(var1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_GameTime";
   }
}
