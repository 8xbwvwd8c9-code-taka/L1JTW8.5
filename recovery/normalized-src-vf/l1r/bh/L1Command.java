package l1r.bh;

public class L1Command {
   private final String a;
   private final int b;
   private final String c;

   public L1Command(String var1, int var2, String var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public String a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   public String c() {
      return this.c;
   }
}
