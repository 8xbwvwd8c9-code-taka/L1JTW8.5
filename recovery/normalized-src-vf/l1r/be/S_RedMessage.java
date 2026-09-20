package l1r.be;

public class S_RedMessage extends ServerBasePacket {
   public S_RedMessage(int var1, String... var2) {
      this.c(6);
      this.b(var1);
      this.c(var2.length);
      String[] var6 = var2;
      int var5 = var2.length;

      for (int var4 = 0; var4 < var5; var4++) {
         String var3 = var6[var4];
         this.a(var3);
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_RedMessage";
   }
}
