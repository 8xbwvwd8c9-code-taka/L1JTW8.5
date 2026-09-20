package l1r.be;

public class S_Weather extends ServerBasePacket {
   public S_Weather(int var1) {
      this.c(233);
      this.c(var1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Weather";
   }
}
