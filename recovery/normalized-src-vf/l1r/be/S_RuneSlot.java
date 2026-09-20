package l1r.be;

public class S_RuneSlot extends ServerBasePacket {
   public static int a = 1;
   public static int b = 2;
   public static int c = 4;
   public static int d = 8;
   public static int e = 16;
   public static int f = 32;
   public static int g = 64;
   public static int h = 128;

   public S_RuneSlot(int var1) {
      this.c(42);
      this.c(var1);
      this.a(1);
      this.c(60);
      this.b(0);
   }

   public S_RuneSlot(int var1, int var2) {
      this.c(42);
      this.c(var1);
      this.a(1);
      this.c(var2);
      this.a(new byte[32]);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_RuneSlot";
   }
}
