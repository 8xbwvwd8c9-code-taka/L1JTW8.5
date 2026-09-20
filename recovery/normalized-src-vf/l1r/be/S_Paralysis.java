package l1r.be;

public class S_Paralysis extends ServerBasePacket {
   public static final int a = 1;
   public static final int b = 2;
   public static final int c = 3;
   public static final int d = 4;
   public static final int e = 5;
   public static final int f = 6;
   public static final int g = 7;
   public static final int h = 8;
   public static final int i = 9;

   public S_Paralysis(int var1, boolean var2) {
      this.c(156);
      int var3 = 0;
      switch (var1) {
         case 1:
            var3 = var2 ? 2 : 3;
            break;
         case 2:
            var3 = var2 ? 4 : 5;
            break;
         case 3:
            var3 = var2 ? 10 : 11;
            break;
         case 4:
            var3 = var2 ? 12 : 13;
            break;
         case 5:
            var3 = var2 ? 22 : 23;
            break;
         case 6:
            var3 = var2 ? 24 : 25;
            break;
         case 7:
            var3 = var2 ? 7 : 7;
            break;
         case 8:
            var3 = var2 ? 26 : 27;
            break;
         case 9:
            var3 = var2 ? 30 : 31;
      }

      this.c(var3);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Paralysis";
   }
}
