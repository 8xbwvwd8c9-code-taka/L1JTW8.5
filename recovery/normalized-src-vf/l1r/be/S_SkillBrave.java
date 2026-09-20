package l1r.be;

public class S_SkillBrave extends ServerBasePacket {
   public static final int a = 0;
   public static final int b = 1;
   public static final int c = 3;
   public static final int d = 4;
   public static final int e = 5;
   public static final int f = 1;
   public static final int g = 1;

   public S_SkillBrave(int var1, int var2, int var3) {
      this.c(241);
      this.a(var1);
      this.c(var2);
      this.b(var3);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_SkillBrave";
   }
}
