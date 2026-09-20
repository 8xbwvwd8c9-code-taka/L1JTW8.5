package l1r.be;

public class S_SelectTarget extends ServerBasePacket {
   public S_SelectTarget(int var1) {
      this.c(213);
      this.a(var1);
      this.c(0);
      this.c(0);
      this.c(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_SelectTarget";
   }
}
