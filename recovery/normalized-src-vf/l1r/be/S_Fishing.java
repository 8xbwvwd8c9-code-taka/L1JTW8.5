package l1r.be;

public class S_Fishing extends ServerBasePacket {
   public S_Fishing(int var1, int var2, int var3, int var4) {
      this.c(75);
      this.a(var1);
      this.c(var2);
      this.b(var3);
      this.b(var4);
      this.a(0);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Fishing";
   }
}
