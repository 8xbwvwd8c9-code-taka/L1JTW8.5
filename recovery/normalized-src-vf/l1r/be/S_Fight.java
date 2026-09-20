package l1r.be;

public class S_Fight extends ServerBasePacket {
   public static final int a = 1;
   public static final int b = 0;
   public static final int c = 0;
   public static final int d = 1;
   public static final int e = 2;
   public static final int f = 3;
   public static final int g = 4;
   public static final int h = 5;
   public static final int i = 6;

   public S_Fight(int var1, int var2) {
      this.c(121);
      this.c(114);
      this.a(var1);
      this.a(var2 == 0 ? 0 : 1);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Fight";
   }
}
