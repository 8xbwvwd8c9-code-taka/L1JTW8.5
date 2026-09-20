package l1r.be;

public class S_NpcChangeShape extends ServerBasePacket {
   public S_NpcChangeShape(int var1, int var2, int var3, int var4) {
      this.c(146);
      this.a(var1);
      this.a(0);
      this.b(var2);
      this.b(var3);
      this.b(var4);
   }

   @Override
   public byte[] a() {
      return this.d();
   }
}
