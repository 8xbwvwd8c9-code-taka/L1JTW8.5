package l1r.be;

public class S_Poison extends ServerBasePacket {
   public static final int a = 0;
   public static final int b = 1;
   public static final int c = 2;

   public S_Poison(int var1, int var2) {
      this.c(175);
      this.a(var1);
      if (var2 == 0) {
         this.c(0);
         this.c(0);
      } else if (var2 == 1) {
         this.c(1);
         this.c(0);
      } else {
         if (var2 != 2) {
            throw new IllegalArgumentException("不正な引数です。type = " + var2);
         }

         this.c(0);
         this.c(1);
      }

      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Poison";
   }
}
