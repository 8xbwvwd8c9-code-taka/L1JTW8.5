package l1r.be;

public class S_CurseBlind extends ServerBasePacket {
   public static final int a = 0;
   public static final int b = 1;
   public static final int c = 2;

   public S_CurseBlind(int var1) {
      this.e(var1);
   }

   private void e(int var1) {
      this.c(246);
      this.b(var1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_CurseBlind";
   }
}
