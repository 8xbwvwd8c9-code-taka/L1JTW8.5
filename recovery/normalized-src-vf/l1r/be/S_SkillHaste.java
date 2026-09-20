package l1r.be;

public class S_SkillHaste extends ServerBasePacket {
   public static final int a = 0;
   public static final int b = 1;
   public static final int c = 2;

   public S_SkillHaste(int var1, int var2, int var3) {
      this.c(88);
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
      return "S_SkillHaste";
   }
}
