package l1r.be;

public class S_TrueTarget extends ServerBasePacket {
   public S_TrueTarget(int var1, boolean var2) {
      this.c(121);
      this.c(194);
      this.a(var1);
      this.a(12299);
      this.a(var2 ? 1 : 0);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_TrueTarget";
   }
}
