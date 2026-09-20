package l1r.be;

public class S_DeleteCharOK extends ServerBasePacket {
   public static final int a = 5;
   public static final int b = 81;

   public S_DeleteCharOK(int var1) {
      this.c(197);
      this.c(var1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_DeleteCharOK";
   }
}
