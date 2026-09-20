package l1r.be;

public class S_CastleMaster extends ServerBasePacket {
   public S_CastleMaster(int var1, int var2) {
      this.c(167);
      this.c(var1);
      this.a(var2);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_CastleMaster";
   }
}
