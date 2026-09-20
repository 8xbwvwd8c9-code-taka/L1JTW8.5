package l1r.be;

public class S_Attribute extends ServerBasePacket {
   private static final int a = 0;
   private static final int b = 1;

   public S_Attribute(int var1, int var2, int var3, boolean var4) {
      this.c(99);
      this.b(var1);
      this.b(var2);
      this.c(var3);
      this.c(var4 ? 0 : 1);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Attribute";
   }
}
