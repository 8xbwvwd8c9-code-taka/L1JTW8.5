package l1r.be;

public class S_News extends ServerBasePacket {
   public S_News(String var1) {
      this.c(67);
      this.a(var1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_News";
   }
}
