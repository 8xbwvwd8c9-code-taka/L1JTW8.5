package l1r.be;

public class S_Lawful extends ServerBasePacket {
   public S_Lawful(int var1, int var2) {
      this.a(var1, var2);
   }

   private void a(int var1, int var2) {
      this.c(238);
      this.a(var1);
      this.b(var2);
      this.a(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Lawful";
   }
}
