package l1r.be;

public class S_Extended extends ServerBasePacket {
   public static final int a = 15;

   public S_Extended(boolean var1) {
      this.c(186);
      this.b(15);
      this.a(var1 ? 1 : 0);
      this.a(1800);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Extended";
   }
}
