package l1r.be;

public class S_Html extends ServerBasePacket {
   public static final String a = "party";
   public static final String b = "monlist";
   public static final String c = "anicom";
   public static final String d = "noseeb";
   public static final String e = "nosell";
   public static final String f = "agsel";
   public static final String g = "";

   public S_Html(int var1, String var2, String... var3) {
      this.a(var1, var2, var3);
   }

   public S_Html(int var1, String var2) {
      this.a(var1, var2, "");
   }

   private void a(int var1, String var2, String... var3) {
      this.c(110);
      this.a(var1);
      this.a(var2);
      this.c(0);
      if (var3 != null && var3.length != 0) {
         this.b(var3.length);
         String[] var7 = var3;
         int var6 = var3.length;

         for (int var5 = 0; var5 < var6; var5++) {
            String var4 = var7[var5];
            this.a(var4);
         }
      } else {
         this.b(0);
         this.b(0);
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Html";
   }
}
