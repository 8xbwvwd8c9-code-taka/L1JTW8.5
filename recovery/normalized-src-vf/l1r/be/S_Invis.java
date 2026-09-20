package l1r.be;

public class S_Invis extends ServerBasePacket {
   public S_Invis(int var1, int var2) {
      this.c(225);
      this.a(var1);
      this.c(var2);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Invis";
   }
}
