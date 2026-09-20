package l1r.be;

public class S_DoActionShop extends ServerBasePacket {
   public S_DoActionShop(int var1, int var2, byte[] var3) {
      this.c(75);
      this.a(var1);
      this.c(var2);
      this.a(var3);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_DoActionShop";
   }
}
