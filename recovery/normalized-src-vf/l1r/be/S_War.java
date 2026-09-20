package l1r.be;

public class S_War extends ServerBasePacket {
   public S_War(int var1, String var2, String var3) {
      this.a(var1, var2, var3);
   }

   private void a(int var1, String var2, String var3) {
      this.c(114);
      this.c(var1);
      this.a(var2);
      this.a(var3);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_War";
   }
}
