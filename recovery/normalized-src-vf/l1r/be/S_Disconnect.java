package l1r.be;

public class S_Disconnect extends ServerBasePacket {
   public static final int a = 0;
   public static final int b = 230;
   public static final int c = 231;

   public S_Disconnect(int var1) {
      this.c(245);
      this.c(var1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Disconnect";
   }
}
